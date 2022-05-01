package com.tlv8.core.report;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MappingConfig {
	@SuppressWarnings("unused")
	private Element mappingE = null;
	@SuppressWarnings("unused")
	private static final String mappingConfig = "impConfirm.xml";// 默认配置文件名

	public MappingConfig(Element mappingElement) throws Exception {
		mappingE = mappingElement;
	}

	public static Element getConfig(String url) throws DocumentException {
		SAXReader saxreader = new SAXReader();
		Document doc = saxreader.read(url);
		if (doc.hasContent()) {
			Element root = doc.getRootElement();
			return root;
		}
		return null;
	}

	public static String getAttributeValue(Element root, String name, String attrib) {
		for (Object o : root.elements()) {
			Element e = (Element) o;
			String elementname = e.getName();
			if (elementname.equals(name)) {
				String value = e.attributeValue(attrib);
				return value;
			}
		}
		return null;

	}
	public static int getAttributeInt(Element root, String name, String attrib) {
		for (Object o : root.elements()) {
			Element e = (Element) o;
			String elementname = e.getName();
			if (elementname.equals(name)) {
				String value = e.attributeValue(attrib);
				return Integer.parseInt(value);
			}
		}
		return -9999;
		
	}
}