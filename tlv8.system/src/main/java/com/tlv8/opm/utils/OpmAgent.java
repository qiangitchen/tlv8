package com.tlv8.opm.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.opm.bean.SaOpAgent;
import com.tlv8.system.action.FunctreeControl;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.help.OnlineHelper;
import com.tlv8.system.help.SessionListener;
import com.tlv8.system.utils.OrgUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class OpmAgent {
	private static Map<String, List<SaOpAgent>> agentpslMap = new HashMap<String, List<SaOpAgent>>();

	/*
	 * 获取人员的委托代理信息
	 */
	public static List<SaOpAgent> getAgentList(ContextBean context) {
		String loginid = context.getLoginID();
		if (agentpslMap.get(loginid) != null) {
			return agentpslMap.get(loginid);
		}
		List<SaOpAgent> agt = new ArrayList<SaOpAgent>();
		SqlSession session = DBUtils.getSession("system");
		try {
			Map param = new HashMap();
			param.put("personid", context.getCurrentPersonID());
			param.put("starttime", new Date());
			param.put("finishtime", new Date());
			agt = session.selectList("opm.getPsmAgnetInfo", param);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		agentpslMap.put(loginid, agt);
		return agt;
	}

	public static Map getAgentFuncAuthorMap(ContextBean context) {
		Map rmap = new HashMap();
		List<SaOpAgent> agentList = getAgentList(context);
		for (int i = 0; i < agentList.size(); i++) {
			OrgUtils ops = new OrgUtils(agentList.get(i).getSagentid());
			Map m = FunctreeControl.gethaveAuther(ops.getPersonFullID(), ops.getPersonID());
			rmap.putAll(m);
		}
		return rmap;
	}

	static {
		OnlineHelper.addListener(new SessionListener() {
			public void sessionDestroyed(ContextBean centext) {
				agentpslMap.remove(centext.getLoginID());
				Sys.printMsg("代理信息已清除.sessionid:" + centext.getSessionID());
			}
		});
	}
}
