package com.tlv8.base.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://www.baidu.com/");
		InputStreamReader reader = new InputStreamReader(url.openStream());
		BufferedReader br = new BufferedReader(reader);
		String s = null;
		while ((s = br.readLine()) != null) {
			if (s != null) {
				String s1 = GetLabel(s);
				String s2 = GetContent(s);
				System.out.println(s1);
				System.out.println(s2);
			}
		}
		br.close();
		reader.close();
	}

	public static String GetContent(String html) {
		StringBuilder result = new StringBuilder();
		if (html != null) {
			try {
				html = html.replace("&lt;", "<");
				html = html.replace("&gt;", ">");
				String ss = ">[^<]+<";
				String temp = null;
				Pattern pa = Pattern.compile(ss);
				Matcher ma = null;
				ma = pa.matcher(html);
				while (ma.find()) {
					temp = ma.group();
					if (temp != null) {
						if (temp.startsWith(">")) {
							temp = temp.substring(1);
						}
						// if (temp.endsWith("<")) {
						// temp = temp.substring(0, temp.length() - 1);
						// }
						// String stxt = temp.replace(">", "");
						// stxt = stxt.replace("<", "");
						// System.out.println(stxt);
						result.append(temp);
					}
				}
				if (result.length() < 1) {
					result.append(html);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result.toString();
	}

	public static String GetLabel(String html) {
		// String html = "<ul><li>1.hehe</li><li>2.hi</li><li>3.hei</li></ul>";
		String ss = "<[^>]+>";
		String temp = null;
		Pattern pa = Pattern.compile(ss);
		Matcher ma = null;
		ma = pa.matcher(html);
		String result = null;
		while (ma.find()) {
			temp = ma.group();
			if (temp != null) {
				if (temp.startsWith(">")) {
					temp = temp.substring(1);
				}
				if (temp.endsWith("<")) {
					temp = temp.substring(0, temp.length() - 1);
				}
				if (!temp.equalsIgnoreCase("")) {
					if (result == null) {
						result = temp;
					} else {
						result += temp;
					}
				}
			}
		}
		return result;
	}

}
