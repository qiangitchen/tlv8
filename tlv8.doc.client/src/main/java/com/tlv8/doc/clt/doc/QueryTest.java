package com.tlv8.doc.clt.doc;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;

@SuppressWarnings("rawtypes")
public class QueryTest {
	public static void main(String[] args) {
		String sql = "select * from SA_OPPerson where sName='system'";
		List table = null;
		try {
			table = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (!table.isEmpty()) {
			Docinfo row = new Docinfo((Map) table.get(0));
			System.out.println(row.getString("sName"));
		}
	}
}
