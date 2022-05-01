package com.tlv8.core.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.tlv8.base.utils.FuncTreeController;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FuncTree extends FuncTreeController {
	private static Element root = null;

	public Element getElement() throws HttpException, IOException,
			DocumentException {
		if (root != null) {
			return root;
		}
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");
		String filePathDir = getFunctionTreeFilePath("WEB-INF/funtree");
		if (!"".equals(filePathDir)) {
			List fileElementList = new ArrayList();
			List filePathList = generateFileList(filePathDir);
			for (int i = 0; i < filePathList.size(); i++) {
				String filePath = (String) filePathList.get(i);
				List tempList = generateNewByFile(filePath);
				fileElementList.addAll(tempList);
			}
			docRoot.clearContent();
			docRoot.setContent(fileElementList);

		}
		doc.add(docRoot);
		root = doc.getRootElement();
		transRowData(root, "");
		return root;
	}

	public void transRowData(Element parentElement, String parentFName) {
		List<Element> childrenElements = parentElement.elements();
		for (Element childElement : childrenElements) {
			String activityFName = parentFName + "/"
					+ childElement.attributeValue("label");
			childElement.addAttribute("activityFName", activityFName);
			transRowData(childElement, activityFName);
		}
	}

}
