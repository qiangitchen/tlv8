package com.tlv8.oa.mail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;

@Controller
@RequestMapping("/receiveEmail")
public class ReceiveEmailController {

	/**
	 * 标记邮件已查看
	 * 
	 * @param rowid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upQurey")
	public Object upQurey(String rowid) {
		Map<String, Object> res = new HashMap<String, Object>();
		String sql = "update OA_EM_ReceiveEmail set FQUREY = '已查看' where FID = ?";
		SqlSession session = DBUtils.getSession("oa");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, rowid);
			ps.executeUpdate();
			session.commit(true);
			res.put("state", true);
		} catch (Exception e) {
			res.put("state", false);
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, null);
		}
		return res;
	}

	/**
	 * 标记所有‘未查看’邮件已查看
	 * 
	 * @param rowid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/martAlltoRead")
	public Object martAlltoRead(String consigneeid) {
		Map<String, Object> res = new HashMap<String, Object>();
		String sql = "update OA_EM_ReceiveEmail set FQUREY = '已查看' where fConsigneeID = ? and FQUREY = '未查看'";
		SqlSession session = DBUtils.getSession("oa");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, consigneeid);
			ps.executeUpdate();
			session.commit(true);
			res.put("state", true);
		} catch (Exception e) {
			res.put("state", false);
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, null);
		}
		return res;
	}

}
