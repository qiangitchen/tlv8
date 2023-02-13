package com.tlv8.system.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.BaseController;

@Controller
@Scope("prototype")
public class GetPersonLinksAction {
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/getPersonLinksAction")
	public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sql = "select SID,STITLE,SURL,SUSERNAME,SPASSWORD,SEXPARAMS,SOPENTYPE from SA_LINKS where SCREATID='"
				+ new BaseController().getContext().getCurrentPersonID() + "'";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		JSONArray json = new JSONArray();
		SqlSession session = DBUtils.getSession("system");
		try {
			conn = session.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				String id = rs.getString("SID");
				String title = rs.getString("STITLE");
				String url = rs.getString("SURL");
				String username = rs.getString("SUSERNAME");
				String password = rs.getString("SPASSWORD");
				String params = rs.getString("SEXPARAMS");
				String openType = rs.getString("SOPENTYPE");
				if (params != null && username == null && password == null) {
					params = params.replace("username={username}&password={password}", "");
				}
				if (params != null && username != null) {
					params = params.replace("{username}", username.trim());
				}
				if (params != null && password != null) {
					params = params.replace("{password}", password.trim());
				}
				if (params != null && !"".equals(params.trim())) {
					if (url.indexOf("?") > 0) {
						url = url + "&";
					} else {
						url = url + "?";
					}
					url = url + params;
				}
				JSONObject obj = new JSONObject();
				obj.put("id", id);
				obj.put("title", title);
				obj.put("url", url);
				obj.put("openType", openType);
				json.add(obj);
			}
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		} finally {
			try {
				DBUtils.closeConn(session, conn, stm, rs);
			} catch (Exception e) {
			}
		}
		data.setData(json.toString());
		return data;
	}
}
