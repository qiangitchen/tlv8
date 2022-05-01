package com.tlv8.core.grid;

import java.net.URLDecoder;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GridCoreController {
	protected DateFormat dataTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	protected DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");

	protected String deCode(String ss) {
		try {
			ss = URLDecoder.decode(ss, "UTF-8");
		} catch (Exception e) {
		}
		return ss;
	}

	protected int getPages(int total, int rows) {
		int p = Math.round(total / rows);
		float t = (float) total / (float) rows - p;
		if (t > 0)
			p = p + 1;
		p = (p == 0) ? 1 : p;
		return p;
	}

	/**
	 * 根据数据库字段类型填充数据
	 * 
	 * @param ps
	 * @param i
	 * @param addval
	 * @param dataType
	 * @throws Exception
	 */
	protected void praperTimeVal(PreparedStatement ps, int i, String addval, String dataType) throws Exception {
		if (("DATE".equals(dataType.toUpperCase()) || "DATETIME".equals(dataType.toUpperCase())
				|| "TIMESTAMP".equals(dataType.toUpperCase())) && !"".equals(addval)) {
			try {
				Date vl1 = dataTimeFormat.parse(addval);
				ps.setTimestamp(i, new java.sql.Timestamp(vl1.getTime()));
			} catch (Exception e) {
				try {
					Date vl2 = dataFormat.parse(addval);
					ps.setDate(i, new java.sql.Date(vl2.getTime()));
				} catch (Exception e2) {
					ps.setNull(i, Types.NULL);
				}
			}
		} else {
			if (addval != null && !"".equals(addval.trim()) && !"null".equals(addval)) {
				if (dataType.contains("int")) {
					ps.setInt(i, Integer.parseInt(addval));
				} else if (dataType.contains("float")) {
					ps.setFloat(i, Float.parseFloat(addval));
				} else {
					ps.setString(i, addval);
				}
			} else {
				ps.setNull(i, Types.NULL);
			}
		}
	}
}
