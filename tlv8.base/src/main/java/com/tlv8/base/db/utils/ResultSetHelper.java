package com.tlv8.base.db.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ResultSetHelper {
	/**
	 * 将ResultSet转换成List
	 * @param rs
	 * @return List
	 */
	public static List ResultSettoList(ResultSet rs) {
		ResultSetMetaData rsmd = null;
		List li = new ArrayList();
		try {
			rsmd = rs.getMetaData();
			int size = rsmd.getColumnCount();
			Map sm = new HashMap();
			while (rs.next()) {
				for (int i = 1; i <= size; i++) {
					String cellType = rsmd.getColumnTypeName(i);
					String columnName = rsmd.getColumnLabel(i);
					if ("NUMBER".equals(cellType))
						sm.put(columnName, Float.valueOf(rs.getFloat(i)));
					else if ("DATE".equals(cellType))
						sm.put(columnName, rs.getDate(i));
					else if ("BLOB".equals(cellType))
						sm.put(columnName, rs.getBlob(i));
					else {
						sm.put(columnName, rs.getString(i));
					}
				}

				li.add(sm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return li;
	}
}
