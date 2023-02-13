package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;

@Controller
@Scope("prototype")
public class DeleteAuthorize extends ActionSupport {
	private String checkedIDs;
	private Data data;

	/**
	 * 删除授权
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAuthorize", produces = "application/json;charset=UTF-8")
	public Object execute() throws Exception {
		setData(new Data());
		String sql = "delete from SA_OPAUTHORIZE where sID = ?";
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			String[] ids = checkedIDs.split(",");
			for (int i = 0; i < ids.length; i++) {
				ps = conn.prepareStatement(sql);
				ps.setString(1, ids[i]);
				ps.executeUpdate();
			}
			session.commit(true);
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.getMessage());
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
		return data;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getCheckedIDs() {
		return checkedIDs;
	}

	public void setCheckedIDs(String checkedIDs) {
		this.checkedIDs = checkedIDs;
		try {
			this.checkedIDs = URLDecoder.decode(checkedIDs, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
