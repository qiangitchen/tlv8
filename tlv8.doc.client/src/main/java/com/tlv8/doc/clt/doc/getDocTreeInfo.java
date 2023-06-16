package com.tlv8.doc.clt.doc;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.Tree;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class getDocTreeInfo extends ActionSupport {
	private Data data = new Data();
	private String master = null;
	private int page;
	private int rows;
	private String viewID;

	public Data getdata() {
		return this.data;
	}

	public void setdata(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/getDocTreeInfo")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		String r = "true";
		String m = "success";
		String f = "";
		try {
			r = getPerson(viewID);
			// Sys.printMsg(r);
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.toString();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return data;
	}

	@SuppressWarnings("deprecation")
	public String getPerson(String viewID) throws SQLException, NamingException {
		String result = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		String sql = "select SID,SDOCNAME,SPARENTID from SA_DOCNODE where SFILEID is null";

		try {
			conn = DBUtils.getAppConn("system");
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			result = Tree.createTree(rs, "SPARENTID", "SDOCNAME", master);
			// System.out.println(result);
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			try {
				DBUtils.CloseConn(conn, stm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		return rows;
	}

	public void setViewID(String viewID) {
		try {
			this.viewID = URLDecoder.decode(viewID, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getViewID() {
		return viewID;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getMaster() {
		return master;
	}
}
