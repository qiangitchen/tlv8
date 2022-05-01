package com.tlv8.flw.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.flw.bean.ExpressionBean;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ExpressionTreeHelper {
	private final static String FILE_POSTFIX = ".fn.xml";
	private final static String FILE_POSTFIXN = ".fn";

	/*
	 * 获取表达式配置
	 * 
	 * @returnTYpe String
	 */
	public String getExpressionTree(HttpServletRequest request) {
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");
		String filePathDir = getRealFilePath(request);
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
		String funmenu = "[" + readElement(root) + "]";
		return funmenu;
	}

	/*
	 * 获取表达式配置
	 * 
	 * @returnTYpe String
	 */
	public List<Map<String, String>> getExpressionTreeMapList(HttpServletRequest request) {
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");
		String filePathDir = getRealFilePath(request);
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
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<Element> eles = root.elements();
		for (int i = 0; i < eles.size(); i++) {
			Element e = (Element) eles.get(i);
			readElementAttr(e, list);
		}
		return list;
	}

	/*
	 * 获取表达式配置
	 * 
	 * @returnType JSONArray
	 */
	public JSONArray getExpressionArray(HttpServletRequest request) {
		String jsonStr = getExpressionTree(request);
		try {
			JSONArray jsonarray = JSON.parseArray(jsonStr);
			return jsonarray;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 获取表达式信息
	 * 
	 * @returnType ExpressionBean
	 */
	public ExpressionBean getExpression(String expressID, HttpServletRequest request) {
		String jsonStr = getExpressionTree(request);
		try {
			JSONArray jsonarray = JSON.parseArray(jsonStr);
			for (int i = 0; i < jsonarray.size(); i++) {
				JSONObject json = jsonarray.getJSONObject(i);
				if (expressID.equals(json.getString("id"))) {
					return new ExpressionBean(json);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getRealFilePath(HttpServletRequest request) {
		String filePathDir = "";
		try {
			filePathDir = request.getServletContext().getRealPath("/WEB-INF/fn");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePathDir;

	}

	public List generateNewByFile(String filePath) {
		// System.out.println(filePath);
		ArrayList elementList = new ArrayList();
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(filePath);
			Element root = doc.getRootElement();
			elementList = (ArrayList) root.elements();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return elementList;
	}

	private static List generateFileList(String fileDir) {
		// System.out.println(fileDir);
		ArrayList fileList = new ArrayList();
		File file = new File(fileDir);
		File[] subFiles = file.listFiles();
		for (File subFile : subFiles) {
			if (subFile.isFile()) {
				if (subFile.getName().endsWith(FILE_POSTFIX) || subFile.getName().endsWith(FILE_POSTFIXN)) {
					fileList.add(fileDir + "/" + subFile.getName());
				}
			}
		}
		return fileList;
	}

	private String readElement(Element ele) {
		StringBuffer str = new StringBuffer();
		str.append("{");
		str.append("nodeName:\"");
		str.append(ele.getName());
		str.append("\" ");
		List attrs = ele.attributes();
		int atsz = attrs.size();
		if (atsz > 0) {
			for (int i = 0; i < atsz; i++) {
				str.append(", ");
				Attribute attr = (Attribute) attrs.get(i);
				str.append(attr.getName());
				str.append(":\"");
				str.append(attr.getValue());
				str.append("\" ");
			}
		}
		str.append("},");
		List eles = ele.elements();
		if (!eles.isEmpty()) {
			int elesz = eles.size();
			for (int i = 0; i < elesz; i++) {
				Element e = (Element) eles.get(i);
				if (i > 0)
					str.append(", ");
				str.append(readElement(e));
			}
		}
		String resutl = str.toString().replace("{nodeName:\"root\" },", "").replaceAll(",,", ",").trim();
		resutl = replaceLast(resutl, ",", "");
		return resutl;
	}

	private void readElementAttr(Element ele, List<Map<String, String>> list) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("nodeName", ele.getName());
		List attrs = ele.attributes();
		int atsz = attrs.size();
		if (atsz > 0) {
			for (int i = 0; i < atsz; i++) {
				Attribute attr = (Attribute) attrs.get(i);
				m.put(attr.getName(), attr.getValue());
			}
			list.add(m);
		}
		List<Element> eles = ele.elements();
		if (!eles.isEmpty()) {
			int elesz = eles.size();
			for (int i = 0; i < elesz; i++) {
				Element e = (Element) eles.get(i);
				readElementAttr(e, list);
			}
		}
	}

	private String replaceLast(String str, String r1, String r2) {
		if (str.lastIndexOf(r1) == str.length() - 1) {
			return str.subSequence(0, str.length() - 1) + r2;
		} else {
			return str;
		}
	}

}