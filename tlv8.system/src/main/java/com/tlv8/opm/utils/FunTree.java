package com.tlv8.opm.utils;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FunTree {
	private static final String FILE_POSTFIX = ".fun.xml";
	private static final String FILE_POSTFIXF = ".fun";
	protected ServletContext sevcontex = null;
	protected static Map<String, String> funmap = new HashMap<String, String>();
	protected static Element root;

	public FunTree() {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
			ServletContext svlcontext = request.getServletContext();
			new FunTree(svlcontext);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FunTree(ServletContext sevContex) {
		sevcontex = sevContex;
		if (funmap.isEmpty()) {
			String filePathDir = getFunctionTreeFilePath();
			Document doc = DocumentHelper.createDocument();
			Element docRoot = DocumentHelper.createElement("root");
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
			loadPer(root);
		}
	}

	protected String getFunctionTreeFilePath() {
		String filePathDir = "";
		try {
			filePathDir = sevcontex.getRealPath("/WEB-INF/funtree");
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
			filePathDir = realPath + "workspace/tlv8/WebContent/WEB-INF/funtree";
		}
		return filePathDir;
	}

	/**
	 * 创建临时菜单集合
	 * 
	 * @param filePath
	 *            菜单文件
	 * @return
	 */
	protected List generateNewByFile(String filePath) {
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

	protected void loadPer(Element ele) {
		String process = ele.attributeValue("process");
		String activity = ele.attributeValue("activity");
		String url = ele.attributeValue("url");
		if (url != null && !"".equals(url)) {
			if (activity != null && !"".equals(activity) && process != null && !"".equals(process)) {
				funmap.put(process + activity, url);
			} else {
				funmap.put(url, ele.attributeValue("label"));
			}
		}
		List eles = ele.elements();
		if (!eles.isEmpty()) {
			int elesz = eles.size();
			for (int i = 0; i < elesz; i++) {
				Element e = (Element) eles.get(i);
				loadPer(e);
			}
		}
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

	public void setFunmap(Map<String, String> funcmap) {
		funmap = funcmap;
	}

	public Map<String, String> getFunmap() {
		return funmap;
	}

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element element) {
		root = element;
	}

}
