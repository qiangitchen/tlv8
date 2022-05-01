package com.tlv8.core.tree;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.db.DBUtils;

@Controller
@Scope("prototype")
public class JqueryOrgTreeAction extends ActionSupport {

	private String orgKind;
	private String rootFilter;

	@ResponseBody
	@RequestMapping(value = "/JqueryOrgTreeAction")
	public void execute(HttpServletResponse response, String orgKind, String rootFilter) throws Exception {
		this.orgKind = getDecode(orgKind);
		this.rootFilter = getDecode(rootFilter);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		String sql = "select SID,SCODE,SNAME from SA_OPORG where SORGKINDID = '" + this.orgKind + "' and "
				+ this.rootFilter + " order by SSEQUENCE asc";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		JSONArray jsonar = new JSONArray();
		try {
			conn = session.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("id", rs.getString("SID"));
				json.put("code", rs.getString("SCODE"));
				json.put("text", rs.getString("SNAME"));
				getChildren(conn, json, rs.getString("SID"));
				jsonar.add(json);
			}
		} catch (Exception e) {
		} finally {
			DBUtils.CloseConn(session, conn, stm, rs);
		}
		PrintWriter writer = response.getWriter();
		writer.write(jsonar.toString());
		writer.close();
	}

	private void getChildren(Connection conn, JSONObject pjob, String pId) {
		String sql = "select SID,SCODE,SNAME from SA_OPORG where SORGKINDID = '" + orgKind + "' and SPARENT='" + pId
				+ "' order by SSEQUENCE asc";
		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			JSONArray jsonar = new JSONArray();
			while (rs.next()) {
				JSONObject json = new JSONObject();
				json.put("id", rs.getString("SID"));
				json.put("code", rs.getString("SCODE"));
				json.put("text", rs.getString("SNAME"));
				jsonar.add(json);
			}
			pjob.put("children", jsonar);
		} catch (Exception e) {
		} finally {
			try {
				DBUtils.CloseConn(null, stm, rs);
			} catch (SQLException e) {
			}
		}
	}

}
