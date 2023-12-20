package com.tlv8.sa.docnode;

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
@RequestMapping("/persondocnode")
public class PersonDocNodeController {

	/**
	 * 删除文件夹
	 * 
	 * @param rowid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteFolder")
	public Object deleteFolder(String treeRowid) {
		Map<String, Object> res = new HashMap<String, Object>();
		String sql = "DELETE FROM PERSONALDOCNODE WHERE SID = ?";
		SqlSession session = DBUtils.getSession("oa");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, treeRowid);
			ps.executeUpdate();
			session.commit(true);
			res.put("state", true);
		} catch (Exception e) {
			res.put("state", false);
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
		return res;
	}

	/**
	 * 删除文件
	 * 
	 * @param rowid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteFileData")
	public Object deleteFileData(String rowid, String fileid) {
		Map<String, Object> res = new HashMap<String, Object>();
		String sql1 = "delete from SA_DOCNODE where SFILEID = ?";
		String sql2 = "delete from PERSONAL_FILE where SID = ?";
		SqlSession session = DBUtils.getSession("oa");
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql1);
			ps.setString(1, fileid);
			ps.executeUpdate();
			ps1 = conn.prepareStatement(sql2);
			ps1.setString(1, rowid);
			ps1.executeUpdate();
			DBUtils.closeConn(null, null, ps1, null);
			session.commit(true);
			res.put("state", true);
		} catch (Exception e) {
			res.put("state", false);
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
		return res;
	}

}
