package com.tlv8.system.online;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.utils.IPUtils;
import com.tlv8.mac.License;
import com.tlv8.system.BaseController;
import com.tlv8.system.bean.ContextBean;

@SuppressWarnings("rawtypes")
public class InitOnlineInfoAction {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ContextBean context = new BaseController().getContext();
		if (context.getCurrentPersonID() == null || "".equals(context.getCurrentPersonID())) {
			return;
		}
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			List list = DBUtils.execQueryforList("system",
					"select SID from SA_ONLINEINFO where SSESSIONID = '" + context.getSessionID() + "'");
			String sql = "insert into SA_ONLINEINFO(SID,SUSERID,SUSERNAME,SUSERFID,SUSERFNAME,"
					+ "SLOGINIP,SLOGINDATE,SSESSIONID,SSERVICEIP,SMACHINECODE,VERSION)values(':JFID',?,?,?,?,?,?,?,?,?,0)";
			if (list.size() > 0) {
				sql = "update SA_ONLINEINFO set SUSERID=?,SUSERNAME=?,SUSERFID=?,SUSERFNAME=?,"
						+ "SLOGINIP=?,SLOGINDATE=?,SSESSIONID=?,SSERVICEIP=?,SMACHINECODE=?,VERSION=0 where SSESSIONID = '"
						+ context.getSessionID() + "'";
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
			ps.setString(7, context.getSessionID());
			ps.setString(8, IPUtils.getPermisServerIP(request));
			ps.setString(9, MachineCode);
			ps.executeUpdate();
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, null);
		}
	}

}
