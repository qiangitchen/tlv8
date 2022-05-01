package com.tlv8.base.utils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tlv8.base.Sys;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FuncTreeController {
	private static final String FILE_POSTFIX = ".fun.xml";
	private static final String FILE_POSTFIXF = ".fun";
	private static int init = 0;
	private String ParentID = "";
	private String tempParentID = "";

	public static String getFunctionTreeFilePath(String filePathDir) {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes()))
					.getRequest();
			filePathDir = request.getServletContext().getRealPath("/" + filePathDir);
		} catch (Exception e) {
			/** 当前环境绝对路径 */
			String realPath = System.getProperty("user.dir");
			try {
				realPath = URLDecoder.decode(realPath, "UTF-8");
			} catch (Exception er) {
			}
			if (realPath.indexOf("workspace") > 0) {
				realPath = realPath.substring(0, realPath.indexOf("workspace"));
			}
			if (realPath.indexOf("apache-tomcat") > 0) {
				realPath = realPath.substring(0, realPath.indexOf("apache-tomcat"));
			}
			/** 文件目录 */
			filePathDir = realPath + "workspace/tlv8/WebContent/" + filePathDir;
		}
		return filePathDir;
	}

	public String index(String filePathDir) throws IOException, DocumentException {
		init = 0;
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");
		filePathDir = getFunctionTreeFilePath(filePathDir);
		// Sys.printMsg(filePathDir);
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
		// String funmenu = readElement(root); JSON
		transRowData(root, "");
		String funmenu = "<ul id='tree'>" + readElementUI(root); // UL
		return funmenu;
	}

	public void transRowData(Element parentElement, String parentFName) {
		List<Element> childrenElements = parentElement.elements();
		for (Element childElement : childrenElements) {
			String activityFName = parentFName + "/" + childElement.attributeValue("label");
			childElement.addAttribute("activityFName", activityFName);
			transRowData(childElement, activityFName);
		}
	}

	/**
	 * 创建临时菜单集合
	 * 
	 * @param filePath
	 *            菜单文件
	 * @return
	 */
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

	/**
	 * 菜单文件路径列表
	 * 
	 * @param fileDir
	 * @return
	 */
	protected List generateFileList(String fileDir) {
		ArrayList fileList = new ArrayList();
		File file = new File(fileDir);
		File[] subFiles = file.listFiles();
		for (File subFile : subFiles) {
			if (subFile.isFile()) {
				if (subFile.getName().endsWith(FILE_POSTFIX) || subFile.getName().endsWith(FILE_POSTFIXF)) {
					fileList.add(subFile.getAbsolutePath());
				}
			}
		}
		return fileList;
	}

	/*
	 * 构建树：JSON
	 */
	@SuppressWarnings("unused")
	private String readElement(Element ele) {
		StringBuffer str = new StringBuffer();
		str.append("{");
		str.append("nodeName:'");
		str.append(ele.getName());
		str.append("' ");
		List attrs = ele.attributes();
		int atsz = attrs.size();
		if (atsz > 0) {
			for (int i = 0; i < atsz; i++) {
				str.append(", ");
				Attribute attr = (Attribute) attrs.get(i);
				str.append(attr.getName());
				str.append(":'");
				str.append(attr.getValue());
				str.append("' ");
			}
		}
		str.append(", childNodes:[");
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
		str.append("]} ");
		return str.toString();
	}

	/*
	 * 构建树:UL
	 */
	private String readElementUI(Element ele) {
		StringBuffer str = new StringBuffer();
		List attrs = ele.attributes();
		int atsz = attrs.size();
		String key = (0 != init) ? UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() : "";
		String La = "";
		if (atsz > 0) {
			str.append("<li><button id='" + key + "_exp'></button><input id='" + key
					+ "_check' type='checkbox'/><a href='javascript:void(0);' id='" + key + "' parentID='" + ParentID
					+ "' ");
			for (int i = 0; i < atsz; i++) {
				Attribute attr = (Attribute) attrs.get(i);
				if ("label".equals(attr.getName())) {
					str.append(" title='");
					str.append(attr.getValue());
					La = attr.getValue();
					str.append("'");
					str.append(" label='");
					str.append(attr.getValue());
					La = attr.getValue();
					str.append("'");
				} else {
					str.append(" " + attr.getName() + "='");
					str.append(attr.getValue());
					str.append("'");
				}
			}
			str.append(">" + La);
			str.append("</a>");
		} else {
			tempParentID = ParentID;
			if (0 != init) {
				str.append("</li>");
			}
		}
		List eles = ele.elements();
		if (!eles.isEmpty()) {
			int elesz = eles.size();
			if (init != 0) {
				str.append("<ul>");
			}
			ParentID = key;
			for (int i = 0; i < elesz; i++) {
				Element e = (Element) eles.get(i);
				if (i > 0 && init != 0) {
					str.append("</li>");
				}
				init++;
				str.append(readElementUI(e));
			}
			ParentID = tempParentID;
			if (init != 0) {
				str.append("</li></ul>");
			}
			init++;
		}
		return str.toString();
	}

	public static void main(String[] args) {
		try {
			String res = new FuncTreeController().index("WEB-INF/funtree/");
			Sys.printMsg(res);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
