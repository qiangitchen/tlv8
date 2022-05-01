package com.tlv8.layim;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@ServerEndpoint("/IM/websocket/{userid}")
public class WebSocketServer {
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static Map<String, WebSocketServer> clients = new ConcurrentHashMap<String, WebSocketServer>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	private String userid;

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(@PathParam("userid") String userid, Session session) {
		this.userid = userid;
		this.session = session;
		clients.put(userid, this); // 加入set中
		addOnlineCount(); // 在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
		try {
			sendStatus("online");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray unreads = IMDBUtils.getUnreadFriendsMsg(userid);
		for (int i = 0; i < unreads.size(); i++) {
			try {
				JSONObject data = unreads.getJSONObject(i);
				JSONObject res = new JSONObject();
				res.put("emit", "chatMessage");
				res.put("data", data);
				sendMessage(res.toString());
				IMDBUtils.updateStatus(data.getString("cid"), 1);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		clients.remove(userid); // 从set中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
		try {
			sendStatus("offline");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		// System.out.println("来自客户端的消息:" + message);
		try {
			JSONObject msg = JSON.parseObject(message);
			if ("chatMessage".equals(msg.get("type"))) {
				JSONObject fdata = msg.getJSONObject("data");
				JSONObject mine = fdata.getJSONObject("mine");
				JSONObject to = fdata.getJSONObject("to");
				JSONObject res = new JSONObject();
				res.put("emit", "chatMessage");
				JSONObject data = new JSONObject();
				data.put("username", mine.get("username"));
				data.put("avatar", mine.get("avatar"));
				if ("group".equals(to.get("type"))) {
					data.put("id", to.get("id"));
				} else {
					data.put("id", mine.get("id"));
				}
				data.put("type", to.get("type"));
				data.put("content", mine.get("content"));
				// data.put("cid", 0);
				data.put("mine", userid.equals(to.get("id")));
				data.put("fromid", userid);
				if (to.containsKey("historyTime")) {
					data.put("timestamp", to.get("historyTime"));
				} else {
					data.put("timestamp", new Date().getTime());
				}
				res.put("data", data);
				if ("group".equals(to.get("type"))) {
					List<Map<String, String>> plist = GetMembers.getPersonlist(to.getString("id"), "tlv8");
					for (int i = 0; i < plist.size(); i++) {
						Map<String, String> psm = plist.get(i);
						// 不给自己发送群信息
						if (!userid.equals(psm.get("id"))) {
							sendMessageTo(res.toString(), psm.get("id"));
						}
					}
					IMDBUtils.saveMessage(fdata, 1);
				} else {
					int state = sendMessageTo(res.toString(), to.getString("id"));
					IMDBUtils.saveMessage(fdata, state);
				}
			} else if ("userstatus".equals(msg.get("type"))) {
				sendStatus(msg.getString("data"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		// System.out.println("发生错误");
		// error.printStackTrace();
	}

	public void sendStatus(String status) throws Exception {
		JSONObject res = new JSONObject();
		res.put("emit", "userstatus");
		JSONObject data = new JSONObject();
		data.put("id", userid);
		data.put("status", status);
		res.put("data", data);
		sendMessageAll(res.toString());
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
		// this.session.getAsyncRemote().sendText(message);
	}

	public int sendMessageTo(String message, String To) {
		try {
			if (clients.containsKey(To)) {
				clients.get(To).sendMessage(message);
				return 1;
			}
		} catch (Exception e) {
		}
		return 0;
	}

	public void sendMessageAll(String message) throws IOException {
		for (WebSocketServer item : clients.values()) {
			// item.session.getAsyncRemote().sendText(message);
			// 想所有人发送信息不包含自己
			if (!userid.equals(item.userid) && item != this) {
				item.sendMessage(message);
			}
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketServer.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocketServer.onlineCount--;
	}

}
