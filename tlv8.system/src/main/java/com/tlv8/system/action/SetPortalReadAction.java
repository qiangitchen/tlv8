package com.tlv8.system.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;

public class SetPortalReadAction {

	private String mid;
	private int state;

	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	public String execute() throws Exception {
		String upSql = "update SA_MSNALERT set SSTATE = ?, SRDATE=? where SID = ?";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(upSql);
			ps.setInt(1, state);
			ps.setTimestamp(2, new Timestamp(new Date().getTime()));
			ps.setString(3, mid);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, null);
		}
		return JSON.toJSONString(data);
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getMid() {
		return mid;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getState() {
		return state;
	}

}
