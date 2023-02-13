package com.tlv8.code;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;

import com.tlv8.base.db.DBUtils;

public class CodeUtils {

	public static String getSquare(String key) {
		long square = 1L;
		SqlSession session  = DBUtils.getSession("system");
		Connection connection = null;
		String sql = "select V+1 as V from SA_KVSEQUENCE where K='" + key + "'";
		// 构建树的json
		try {
			connection = session.getConnection();
			ResultSet reslut = connection.createStatement().executeQuery(sql);
			if (reslut.next()) {
				square = reslut.getLong("V");
				String sql_update = "update SA_KVSEQUENCE set V=V+1 where K ='"
						+ key + "'";
				DBUtils.execUpdateQuery("system", sql_update);
			} else {
				String sql_insert = "insert into SA_KVSEQUENCE(K,V) values('"
						+ key + "',1)";
				DBUtils.execInsertQuery("system", sql_insert);
				square = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.closeConn(session,connection, null, null);
		}
		return key + formatNumber("0000", square);
	}

	public static String formatNumber(String format, long num) {
		DecimalFormat df2 = new DecimalFormat(format);
		return df2.format(num);
	}

	public static String getOrderCode() {
		return getSquare("SPEDU"
				+ new SimpleDateFormat("yyyyMM").format(new Date()));
	}

	public static void main(String[] args) {
		System.out.println(getSquare("M"
				+ new SimpleDateFormat("yyyyMM").format(new Date())));
	}
}
