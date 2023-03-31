package com.tlv8.system.online;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.mac.License;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.utils.IPUtils;
import com.tlv8.system.bean.ContextBean;

@SuppressWarnings("rawtypes")
public class InitOnlineInfoAction {

	public static void execute(ContextBean context) throws Exception {
		if (context.getCurrentPersonID() == null || "".equals(context.getCurrentPersonID())) {
			return;
		}
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			List list = DBUtils.execQueryforList("system",
					"select SID from SA_ONLINEINFO where SSESSIONID = '" + context.getToken() + "'");
			String sql = "insert into SA_ONLINEINFO(SID,SUSERID,SUSERNAME,SUSERFID,SUSERFNAME,"
					+ "SLOGINIP,SLOGINDATE,SSESSIONID,SSERVICEIP,SMACHINECODE,VERSION)values(':JFID',?,?,?,?,?,?,?,?,?,0)";
			if (list.size() > 0) {
				sql = "update SA_ONLINEINFO set SUSERID=?,SUSERNAME=?,SUSERFID=?,SUSERFNAME=?,"
						+ "SLOGINIP=?,SLOGINDATE=?,SSESSIONID=?,SSERVICEIP=?,SMACHINECODE=?,VERSION=0 where SSESSIONID = '"
						+ context.getToken() + "'";
			}
			conn = session.getConnection();
			String MachineCode = License.getMachineCode();
			sql = sql.replace(":JFID", IDUtils.getGUID());
			ps = conn.prepareStatement(sql);
			ps.setString(1, context.getCurrentPersonID());
			ps.setString(2, context.getCurrentPersonName());
			ps.setString(3, context.getCurrentPersonFullID());
			ps.setString(4, context.getCurrentPersonFullName());
			ps.setString(5, context.getIp());
			ps.setTimestamp(6, new Timestamp(new Date().getTime()));
			ps.setString(7, context.getToken());
			ps.setString(8, IPUtils.getHostIP());
			ps.setString(9, MachineCode);
			ps.executeUpdate();
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
	}

}
