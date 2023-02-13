package com.tlv8.flw.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.StringArray;
import com.tlv8.system.BaseController;
import com.tlv8.system.bean.ContextBean;

public class ExecutorGroupFilter {
	/**
	 * 转义执行规则
	 */
	public static String parseFilter(Object object) {
		String result = "";
		String exp = object.toString();
		if (exp.indexOf(",") > 0) {
			String[] ags = exp.split(",");
			for (int i = 0; i < ags.length; i++) {
				if (i > 0)
					result += " or ";
				result += " SFID like '%" + ags[i] + "%' ";
			}
		} else {
			result = "SFID like '%" + exp + "%'";
		}
		return result.trim();
	}

	/**
	 * 过滤下属单位【排除下属机构的id，保留当前机构下的组织（人员）】
	 * 
	 * @param orgids
	 * @return
	 */
	public static String filterSubOgn(String orgids) {
		StringArray pps = new StringArray();
		ContextBean context = new BaseController().getContext();
		String ognfid = context.getCurrentOgnFullID();
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		try {
			conn = session.getConnection();
			String[] orgs = orgids.split(",");
			for (int i = 0; i < orgs.length; i++) {
				String orgid = orgs[i];
				if (!"".equals(orgid)) {
					String sql = "select SFID from SA_OPORG where (SID ='" + orgid + "' or SPERSONID ='" + orgid
							+ "' or SFID = '" + orgid + "') and SFID like '" + ognfid + "%'";// 取当前机构下的数据
					Statement stm = conn.createStatement();
					ResultSet rs = stm.executeQuery(sql);
					if (rs.next()) {
						String ssfid = rs.getString("SFID").replace(ognfid, "");
						if (ssfid.indexOf(".ogn") > 0 || ssfid.indexOf(".org") > 0) {// 忽略子(下属)单位
							// continue;
						} else {
							pps.push(orgid);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, null, null);
		}
		return pps.join(",");
	}
}
