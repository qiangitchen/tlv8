package com.tlv8.doc.clt.doc;

import java.sql.Connection;
import java.sql.PreparedStatement;

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

	@SuppressWarnings("deprecation")
	public void commitData() {
		Connection conn = null;
		PreparedStatement ps = null;
		String docSql = "update SA_DOCNODE set SFILEID = ?, SKIND = ?, SSIZE=?, SCACHENAME=? where SID = '"
				+ this.getsID() + "'";
		try {
			conn = DBUtils.getAppConn("system");
			ps = conn.prepareStatement(docSql);
			ps.setString(1, this.getsFileID());
			ps.setString(2, this.getsKind());
			ps.setFloat(3, this.getsSize());
			ps.setString(4, this.getScacheName());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				DBUtils.CloseConn(conn, null, null);
			} catch (Exception se) {
			}
		}
	}

}
