package com.tlv8.system.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;

public class Pwd {
	public static void changePassword(String username, String password,
			String new_password) throws NamingException, SQLException {
		String ssql = "select SID from SA_OPPerson where "
				+ "(upper(sCode) = upper('%s') or upper(sloginname) "
				+ "= upper('%s')) and spassword = '%s'";
		String usql = "update SA_OPPerson set spassword = '%s' where "
				+ "(upper(sCode) = upper('%s') or upper(sloginname) "
				+ "= upper('%s')) and spassword = '%s'";
		// password = MD5Util.encode(password);
		// new_password = MD5Util.encode(new_password);
		if (ssql.indexOf("%s") > 0)
			ssql = String.format(ssql, username, username, password);
		if (usql.indexOf("%s") > 0)
			usql = String.format(usql, new_password, username, username,
					password);
		try {
			Sys.printMsg(ssql);
			List<Map<String, String>> li = DBUtils.execQueryforList("system", ssql);
			if (li.size() > 0) {
				System.out.println("密码验证成功！");
				try {
					DBUtils.execUpdateQuery("system", usql);
					System.out.println("密码修改成功！");
				} catch (SQLException e) {
					System.out.println("修改密码失败:" + e.toString());
					throw new SQLException(e);
				}
			} else {
				System.out.println("密码验证失败！");
				throw new SQLException("密码验证失败！");
			}
		} catch (SQLException e) {
			throw new SQLException(e);
		}
	}
}
