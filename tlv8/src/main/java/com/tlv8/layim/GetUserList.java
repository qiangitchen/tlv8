package com.tlv8.layim;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.system.bean.ContextBean;

@Controller
@RequestMapping("/layim")
public class GetUserList {

	@ResponseBody
	@RequestMapping("/getUserList")
	public Object execute(HttpServletRequest request) throws Exception {
		String cpath = request.getContextPath();
		ContextBean centext = ContextBean.getContext(request);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("code", 0);
		json.put("msg", "");
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, String> mine = new HashMap<String, String>();
		mine.put("status", "online");
		mine.put("id", centext.getCurrentPersonID());
		mine.put("username", centext.getCurrentPersonName());
		mine.put("sign", centext.getCurrentPersonFullName());
		mine.put("avatar", cpath + "/comon/picCompant/personhead.jsp?id=" + centext.getCurrentPersonID());
		data.put("mine", mine);

		List<Object> friend = new ArrayList<Object>();
		SqlSession session = null;
		Connection conn = null;
		try {
			session = DBUtils.getSession("system");
			conn = session.getConnection();
			// 按部门加载
			List<Map<String, String>> groups = getOgnList(conn, centext.getCurrentOgnID());
			for (int i = 0; i < groups.size(); i++) {
				Map<String, String> org = groups.get(i);
				Map<String, Object> group = new HashMap<String, Object>();
				group.put("groupname", org.get("SNAME"));
				group.put("id", org.get("SID"));
				List<Map<String, String>> list = new ArrayList<Map<String,String>>();
				List<Map<String, String>> persons = getPersonList(conn, org.get("SFID"), centext.getCurrentPersonID());
				for (int p = 0; p < persons.size(); p++) {
					Map<String, String> psm = persons.get(p);
					Map<String, String> per = new HashMap<String, String>();
					per.put("id", psm.get("SID"));
					per.put("username", psm.get("SNAME"));
					per.put("sign", psm.get("SFNAME"));
					per.put("avatar", cpath + "/comon/picCompant/personhead.jsp?id=" + psm.get("SID"));
					per.put("status", isOnline(conn, psm.get("SID")) ? "online" : "offline");
					list.add(per);
				}
				group.put("list", list);
				friend.add(group);
			}
			// 直接挂在机构下的人员
			List<Map<String, String>> getCuPersonList = getCuPersonList(conn, centext.getCurrentOgnID(),
					centext.getCurrentPersonID());
			if (getCuPersonList.size() > 0) {
				Map<String, Object> group0 = new HashMap<String, Object>();
				List<Map<String,String>> list0 = new ArrayList<Map<String,String>>();
				for (int n = 0; n < getCuPersonList.size(); n++) {
					Map<String, String> psm = getCuPersonList.get(n);
					Map<String, String> per = new HashMap<String, String>();
					per.put("id", psm.get("SID"));
					per.put("username", psm.get("SNAME"));
					per.put("sign", psm.get("SFNAME"));
					per.put("avatar", cpath + "/comon/picCompant/personhead.jsp?id=" + psm.get("SID"));
					per.put("status", isOnline(conn, psm.get("SID")) ? "online" : "");
					list0.add(per);
				}
				group0.put("list", list0);
				friend.add(group0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, null, null);
		}
		data.put("friend", friend);

		loadGroup(data, centext.getCurrentPersonID()); // 加载群组（OA群组公用）

		json.put("data", data);
		
		return json;
	}

	private List<Map<String, String>> getOgnList(Connection conn, String ognid) {
		List<Map<String, String>> ogns = new ArrayList<Map<String, String>>();
		Statement stm = null;
		ResultSet rs = null;
		try {
			String sql = "select SID,SNAME,SCODE,SFID from SA_OPORG where SPARENT='" + ognid
					+ "' and SORGKINDID <> 'psm' order by SSEQUENCE asc";
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("SID", rs.getString("SID"));
				m.put("SNAME", rs.getString("SNAME"));
				m.put("SCODE", rs.getString("SCODE"));
				m.put("SFID", rs.getString("SFID"));
				ogns.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.closeConn(null, stm, rs);
			} catch (SQLException e) {
			}
		}
		return ogns;
	}

	private List<Map<String, String>> getPersonList(Connection conn, String ognfid, String myid) {
		List<Map<String, String>> persons = new ArrayList<Map<String, String>>();
		Statement stm = null;
		ResultSet rs = null;
		try {
			String sql = "select SID,SNAME,SFNAME,SPERSONID from SA_OPORG where SFID like '" + ognfid
					+ "%' and SORGKINDID = 'psm' and SPERSONID <> '" + myid + "' order by SSEQUENCE asc";
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("SID", rs.getString("SPERSONID"));
				m.put("SNAME", rs.getString("SNAME"));
				m.put("SFNAME", rs.getString("SFNAME"));
				persons.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.closeConn(null, stm, rs);
			} catch (SQLException e) {
			}
		}
		return persons;
	}

	private List<Map<String, String>> getCuPersonList(Connection conn, String ognid, String myid) {
		List<Map<String, String>> persons = new ArrayList<Map<String, String>>();
		Statement stm = null;
		ResultSet rs = null;
		try {
			String sql = "select SID,SNAME,SFNAME,SPERSONID from SA_OPORG where SPARENT = '" + ognid
					+ "' and SORGKINDID = 'psm' and SPERSONID <> '" + myid + "' order by SSEQUENCE asc";
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				Map<String, String> m = new HashMap<String, String>();
				m.put("SID", rs.getString("SPERSONID"));
				m.put("SNAME", rs.getString("SNAME"));
				m.put("SFNAME", rs.getString("SFNAME"));
				persons.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBUtils.closeConn(null, stm, rs);
			} catch (SQLException e) {
			}
		}
		return persons;
	}

	private void loadGroup(Map<String, Object> data, String psmid) {
		SqlSession session = null;
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			String sql = "select FID,FGROUPNAME from OA_ADM_MYGROUPMAIN where FCREATORID = '" + psmid
					+ "' or FID in (select FOUTKEY from OA_ADM_MYGROUPFROM where FPERSONID = '" + psmid + "')";
			session = DBUtils.getSession("oa");
			conn = session.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			List<Map<String,String>> groupjson = new ArrayList<Map<String,String>>();
			while (rs.next()) {
				Map<String, String> group = new HashMap<String, String>();
				group.put("groupname", rs.getString("FGROUPNAME"));
				group.put("id", rs.getString("FID"));
				group.put("avatar", "image/group.jpg");
				groupjson.add(group);
			}
			data.put("group", groupjson);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, stm, rs);
		}
	}

	public static boolean isOnline(Connection conn, String psmid) {
		Statement stm = null;
		ResultSet rs = null;
		boolean rsb = false;
		try {
			String sql = "select SID from sa_onlineinfo where SUSERID='" + psmid + "'";
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				rsb = true;
			}
		} catch (Exception e) {
		} finally {
			try {
				DBUtils.closeConn(null, stm, rs);
			} catch (SQLException e) {
			}
		}
		return rsb;
	}
}
