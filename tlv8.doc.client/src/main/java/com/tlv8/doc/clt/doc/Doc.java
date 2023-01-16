package com.tlv8.doc.clt.doc;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.db.DBUtils;

public class Doc extends AbstractDoc {

	private Docs docs;

	public Doc(Docinfo info, Docs docs) {
		super(info);
		this.docs = docs;
	}

	public Docs getDocs() {
		return docs;
	}

	protected void checkAccess() {
		if (Utils.isNotNull(docs) && Utils.isNotNull(docs.getPermissions())) {
			DocPermission p = docs.getPermissions().queryPermissionById(
					getsID(), getsDocPath());
			if (p.getsAccess() <= 1)
				throw new DocRTException("没有下载的权限");
		}
	}

	public void commitData() {
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		String docSql = "update SA_DOCNODE set SFILEID = ?, SKIND = ?, SSIZE=?, SCACHENAME=? where SID = ?";
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(docSql);
			ps.setString(1, this.getsFileID());
			ps.setString(2, this.getsKind());
			ps.setFloat(3, this.getsSize());
			ps.setString(4, this.getScacheName());
			ps.setString(5, this.getsID());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session,conn, ps, null);
		}
	}

}
