package com.tlv8.layim;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.system.controller.UserController;

public class IMDBUtils {
	/*
	 * 保存消息记录
	 */
	public static void saveMessage(JSONObject data, int state) {
		Connection conn = null;
		PreparedStatement ps = null;
		SqlSession session = DBUtils.getSession("system");
		String sql = "insert into im_message(sID,fid,fusername,favatar,content,tid,tname,tavatar,stype,tsign,tusername,groupname,state,stime,VERSION)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			JSONObject mine = data.getJSONObject("mine");
			JSONObject to = data.getJSONObject("to");
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, IDUtils.getGUID());
			ps.setString(2, mine.getString("id"));
			ps.setString(3, mine.getString("username"));
			ps.setString(4, mine.getString("avatar"));
			ps.setString(5, mine.getString("content"));
			ps.setString(6, to.getString("id"));
			ps.setString(7, to.getString("name"));
			ps.setString(8, to.getString("avatar"));
			ps.setString(9, to.getString("type"));
			ps.setString(10, to.containsKey("sign") ? to.getString("sign") : null);
			ps.setString(11, to.containsKey("username") ? to.getString("username") : null);
			ps.setString(12, to.containsKey("groupname") ? to.getString("groupname") : null);
			ps.setInt(13, state);
			long timestamp = new Date().getTime();
			if (to.containsKey("historyTime")) {
				timestamp = to.getLong("historyTime");
			}
			ps.setTimestamp(14, new Timestamp(timestamp));
			ps.setInt(15, 0);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
	}

	/**
	 * 
	 * @param msgid
	 * @param state
	 */
	public static void updateStatus(String msgid, int state) {
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "update im_message set state = ? where sid = ?";
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, state);
			ps.setString(2, msgid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
	}

	/**
	 * @author 获取好友未读信息
	 * @param userid
	 * @return
	 */
	public static JSONArray getUnreadFriendsMsg(String userid) {
		JSONArray res = new JSONArray();
		String sql = "select SID,FID,FUSERNAME,FAVATAR,CONTENT,STIME from IM_MESSAGE where tid='" + userid
				+ "' and stype = 'friend' and state = 0 order by stime";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				JSONObject data = new JSONObject();
				data.put("cid", rs.getString("SID"));
				data.put("id", rs.getString("FID"));
				data.put("username", rs.getString("FUSERNAME"));
				data.put("avatar", rs.getString("FAVATAR"));
				data.put("type", "friend");
				data.put("content", rs.getString("CONTENT"));
				data.put("timestamp", rs.getTimestamp("STIME").getTime());
				res.add(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, stm, rs);
		}
		return res;
	}

	/**
	 * @获取记录
	 * @param talkid
	 * @param stype
	 * @return
	 */
	public static List<Map<String, Object>> getMessageHistoryList(String talkid, String stype) {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		String sql = "";
		try {
			String psmid = new UserController().getContext().getCurrentPersonID();
			if ("group".equals(stype)) {
				sql = "select FID,FUSERNAME,FAVATAR,CONTENT,STIME from IM_MESSAGE where tid = '" + talkid
						+ "' and stype = 'group' order by stime";
			} else {
				sql = "select FID,FUSERNAME,FAVATAR,CONTENT,STIME from IM_MESSAGE where ((fid = '" + psmid
						+ "' and tid = '" + talkid + "' ) or (fid = '" + talkid + "' and tid = '" + psmid
						+ "')) and stype = 'friend' order by stime";
			}
			conn = session.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				Map<String, Object> json = new HashMap<String, Object>();
				json.put("id", rs.getString("FID"));
				json.put("username", rs.getString("FUSERNAME"));
				json.put("avatar", rs.getString("FAVATAR"));
				json.put("content", rs.getString("CONTENT"));
				json.put("timestamp", rs.getTimestamp("STIME").getTime());
				data.add(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, stm, rs);
		}
		return data;
	}
}
