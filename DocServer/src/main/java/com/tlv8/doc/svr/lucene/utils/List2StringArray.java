package com.tlv8.doc.svr.lucene.utils;

import java.util.List;

public class List2StringArray {
	public static String[] transe(List<String> list) {
		String[] res = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
}
