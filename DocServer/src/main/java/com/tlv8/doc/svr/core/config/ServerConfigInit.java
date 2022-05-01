package com.tlv8.doc.svr.core.config;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tlv8.doc.svr.core.TransePath;

public class ServerConfigInit {
	public static String DOC_HOME;
	private static String configFilePath;

	public static String getConfigFilePath() {
		return configFilePath;
	}

	public static void setConfigFilePath(String configFilePath) {
		ServerConfigInit.configFilePath = configFilePath;
	}

	public static void init() {
		String docDir = "/data/doc";
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new File(configFilePath));
			Element element = doc.getRootElement();
			Element DirEl = element.element("doc-dir");
			if (DirEl != null) {
				docDir = DirEl.getTextTrim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (docDir.startsWith("/")) {
			docDir = DOC_HOME + docDir;
		}
		TransePath.setBaseDocPath(docDir);
	}
}
