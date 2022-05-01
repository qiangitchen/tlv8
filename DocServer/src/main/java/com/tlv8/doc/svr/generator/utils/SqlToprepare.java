package com.tlv8.doc.svr.generator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlToprepare {
	public static StringArray getParamValues(String sql) {
		StringArray rearray = new StringArray();
		Pattern p = Pattern.compile("'(.+?)'");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			rearray.push(m.group(1));
		}
		return rearray;
	}

	public static String transeSql(String sql) {
		String reSql = sql;
		Pattern p = Pattern.compile("'(.+?)'");
		Matcher m = p.matcher(sql);
		while (m.find()) {
			reSql = reSql.replace("'" + m.group(1) + "'", "?");
		}
		return reSql;
	}

}
