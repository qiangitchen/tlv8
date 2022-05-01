package com.tlv8.opm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.utils.StringArray;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FuncTreeController extends com.tlv8.base.utils.FuncTreeController {

	public String zTree(String filePathDir) throws HttpException, IOException, DocumentException {
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");
		/** 文件目录 */
		filePathDir = getFunctionTreeFilePath(filePathDir);
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

		Element root = doc.getRootElement();
		transRowData(root, "");
		List eles = root.elements();
		StringArray re = new StringArray();
		for (Object obj : eles) {
			re.push(readElementzTree((Element) obj, ""));
		}
		String funmenu = "[" + re.join(",") + "]";
		return funmenu;
	}
	
	/*
	 * 构建树：JSON for zTree
	 */
	private String readElementzTree(Element ele, String pid) {
		StringBuffer str = new StringBuffer();
		String id = IDUtils.getGUID();
		str.append("{id:'" + id + "', name:'" + ele.attributeValue("label")
				+ "',pid:'" + pid + "',surl:'" + ele.attributeValue("url")
				+ "',process:'" + ele.attributeValue("process")
				+ "',activity:'" + ele.attributeValue("activity") + "'}");
		List eles = ele.elements();
		if (!eles.isEmpty()) {
			int elesz = eles.size();
			for (int i = 0; i < elesz; i++) {
				Element e = (Element) eles.get(i);
				str.append("," + readElementzTree(e, id));
			}
		}
		return str.toString();
	}
}
