package com.tlv8.core.utils;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.dom4j.DocumentException;
import org.dom4j.Element;

@SuppressWarnings("rawtypes")
public class GetProcessFullName {
	public Element root;
	private FuncTree functreectl;

	public GetProcessFullName() {
		functreectl = new FuncTree();
		try {
			root = functreectl.getElement();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public String getFullName(String path) {
		String fullName = "";
		String temName = path;
		fullName = getPar(root, temName);
		return fullName;
	}

	public String getNameByPath(String path) {
		String Name = "";
		String temName = path;
		Name = getParUrl(root, temName);
		return Name;
	}

	public String getParUrl(Element root, String temName) {
		String name = "";
		String url = root.attributeValue("url");
		if (temName.equals(url) || temName.replace(".html", ".w").equals(url)
				|| temName.replace(".jsp", ".w").equals(url)) {
			name = root.attributeValue("label");
		} else {
			List nl = root.elements();
			for (int j = 0; j < nl.size(); j++) {
				name = getParUrl((Element) nl.get(j), temName);
				if (name != null && !"".equals(name)) {
					break;
				}
			}
		}
		return name;
	}

	public String getPar(Element root, String temName) {
		String url = root.attributeValue("url");
		if (temName.equals(url) || temName.replace(".html", ".w").equals(url)
				|| temName.replace(".jsp", ".w").equals(url)) {
			return root.attributeValue("activityFName");
		} else {
			List nl = root.elements();
			for (int j = 0; j < nl.size(); j++) {
				String fname = getPar((Element) nl.get(j), temName);
				if (fname != null && !"".equals(fname)) {
					return fname;
				}
			}
		}
		return "";
	}

	public static void main(String args[]) {
		System.out.println(new GetProcessFullName().getFullName("/SA/doc/docCenter/mainActivity.html"));
		System.out.println(new GetProcessFullName().getNameByPath("/SA/doc/docCenter/mainActivity.html"));
	}
}
