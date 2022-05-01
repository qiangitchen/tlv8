package com.tlv8.system.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.HttpException;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.utils.StringArray;
import com.tlv8.system.BaseController;
import com.tlv8.system.action.FunctreeControl;

@Controller
@Scope("prototype")
@RequestMapping("/system")
@SuppressWarnings({ "rawtypes" })
public class FuncTreeController extends BaseController {
	private static final String FILE_POSTFIX = ".fun.xml";
	private static final String FILE_POSTFIXF = ".fun";
	protected String spersonfID = "";
	protected Map haveAutherMap = new HashMap();

	public String getFunctionTreeFilePath() {
		String filePathDir = "";
		try {
			filePathDir = request.getServletContext().getRealPath("/WEB-INF/funtree");
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

	public String getMobileFunctionTreeFilePath() {
		String filePathDir = "";
		try {
			filePathDir = request.getServletContext().getRealPath("/WEB-INF/mfuntree");
		} catch (Exception e) {
			/** 当前环境绝对路径 */
			String realPath = System.getProperty("user.dir");
			try {
				realPath = URLDecoder.decode(realPath, "UTF-8");
			} catch (Exception er2) {
			}
			if (realPath.indexOf("workspace") > 0) {
				realPath = realPath.substring(0, realPath.indexOf("workspace"));
			}
			if (realPath.indexOf("apache-tomcat") > 0) {
				realPath = realPath.substring(0, realPath.indexOf("apache-tomcat"));
			}
			/** 文件目录 */
			filePathDir = realPath + "workspace/tlv8/WebContent/WEB-INF/mfuntree";
		}
		return filePathDir;
	}

	@ResponseBody
	@RequestMapping("/loadHmFunTreeAtion")
	@SuppressWarnings("unchecked")
	public void loadHmFun() throws HttpException, IOException, DocumentException {
		String filePathDir = getFunctionTreeFilePath();
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");

		this.spersonfID = this.getContext().getCurrentPersonFullID();
		String psnid = this.getContext().getCurrentPersonID();
		this.haveAutherMap = FunctreeControl.gethaveAuther(spersonfID, psnid);
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
		// ====获取功能树配置信息end===
		String funmenu = "";
		List<Element> elems = root.elements();
		// 读取配置信息 封装成需要展示的结构数据
		StringBuilder ssb = new StringBuilder();
		for (int i = 0; i < elems.size(); i++) {
			ssb.append(readHmElement(elems.get(i)));
		}
		funmenu = ssb.toString();
		this.renderData(funmenu);
	}

	@ResponseBody
	@RequestMapping("/Func")
	@SuppressWarnings("unchecked")
	public void index() throws HttpException, IOException, DocumentException {
		String filePathDir = getFunctionTreeFilePath();
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");

		this.spersonfID = this.getContext().getCurrentPersonFullID();
		String psnid = this.getContext().getCurrentPersonID();
		this.haveAutherMap = FunctreeControl.gethaveAuther(spersonfID, psnid);
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
		String funmenu = this.readElement(root);
		this.renderData(funmenu);
	}

	@ResponseBody
	@RequestMapping("/FuncTree")
	public void funcTree() throws HttpException, IOException, DocumentException {
		index();
	}

	@ResponseBody
	@RequestMapping("/FuncTree2")
	@SuppressWarnings("unchecked")
	public void index2() throws HttpException, IOException, DocumentException {
		String filePathDir = getFunctionTreeFilePath();
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");

		this.spersonfID = this.getContext().getCurrentPersonFullID();
		String psnid = this.getContext().getCurrentPersonID();
		this.haveAutherMap = FunctreeControl.gethaveAuther(spersonfID, psnid);
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
		String funmenu = "[" + this.readElement2(root, "") + "]";
		this.renderData(funmenu);
	}

	@ResponseBody
	@RequestMapping("/FuncTree3")
	@SuppressWarnings("unchecked")
	public void index3() throws HttpException, IOException, DocumentException {
		String filePathDir = getFunctionTreeFilePath();
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");

		this.spersonfID = this.getContext().getCurrentPersonFullID();
		String psnid = this.getContext().getCurrentPersonID();
		this.haveAutherMap = FunctreeControl.gethaveAuther(spersonfID, psnid);
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
		JSONObject json = new JSONObject();
		try {
			json = readElement3(root);
		} catch (Exception e) {
		}
		this.renderData(json.toString());
	}

	@ResponseBody
	@RequestMapping("/MFuncTree")
	@SuppressWarnings("unchecked")
	public void mindex() throws HttpException, IOException, DocumentException {
		String filePathDir = getMobileFunctionTreeFilePath();
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");

		this.spersonfID = this.getContext().getCurrentPersonFullID();
		String psnid = this.getContext().getCurrentPersonID();
		this.haveAutherMap = FunctreeControl.gethaveAuther(spersonfID, psnid);
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
		String funmenu = this.readElement(root);
		this.renderData(funmenu);
	}

	/**
	 * 创建临时菜单集合
	 * 
	 * @param filePath 菜单文件
	 * @return
	 */
	public List generateNewByFile(String filePath) {
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
	@SuppressWarnings("unchecked")
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

	public static String ceateUUID() {
		String id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
		return id;
	}

	protected String readHmElement(Element ele) {
		StringBuffer str = new StringBuffer();
		StringBuffer tempStr = new StringBuffer();
		tempStr.append("<div class=\"list-group\">");
		if ("系统管理".equals(ele.attributeValue("label"))) {
			tempStr.append(
					"<h1 title=\"" + ele.attributeValue("label") + "\"><img src=\"skin/default/nav/sys.png\" /></h1>");
		} else if ("个人办公".equals(ele.attributeValue("label"))) {
			tempStr.append(
					"<h1 title=\"" + ele.attributeValue("label") + "\"><img src=\"skin/default/nav/user.png\" /></h1>");
		} else {
			tempStr.append(
					"<h1 title=\"" + ele.attributeValue("label") + "\"><img src=\"skin/default/nav/pus.png\" /></h1>");
		}
		tempStr.append("<div class=\"list-wrap\">");
		tempStr.append("<h2>" + ele.attributeValue("label") + "<i></i></h2>");
		List attrs = ele.attributes();
		int atsz = attrs.size();
		String url = "";
		String authurl = "";
		String process = "";
		String activity = "";
		boolean isEff = isEffective(ele);
		boolean isHiden = false;
		if (atsz > 0) {
			url = ele.attributeValue("url");
			process = ele.attributeValue("process");
			activity = ele.attributeValue("activity");
			String display = ele.attributeValue("display");
			if (url == null) {
				url = "";
			}
			if ("hide".equals(display)) {
				isHiden = true;
			}
			if ((activity != null) && (!"".equals(activity)) && (process != null) && (!"".equals(process)))
				authurl = process + activity;
			else {
				authurl = url;
			}
		}
		// 判断功能树信息是否掩藏和有权限展示
		// isHaveAuther:判断是否有权限(根据地址判断权限，如果是文件夹不限制权限)
		if ((!isHiden) && ((("".equals(url)) && (isEff)) || (isHaveAuther(authurl)))) {
			str.append(tempStr);
			List eles = ele.elements();
			if (!eles.isEmpty()) {
				int elesz = eles.size();
				str.append("<ul>");
				for (int i = 0; i < elesz; i++) {
					Element e = (Element) eles.get(i);
					if (isEffective(e)) {
						readElementChl(e, str);
					}
				}
				str.append("</ul>");
			}
			str.append("</div></div>");
		}
		return str.toString();
	}

	/*
	 * 读取子目录的功能配置信息
	 */
	private void readElementChl(Element ele, StringBuffer str) {
		List<Element> attrs = ele.elements();
		int atsz = attrs.size();
		String url = ele.attributeValue("url");
		String process = ele.attributeValue("process");
		String activity = ele.attributeValue("activity");
		String display = ele.attributeValue("display");
		if (url == null) {
			url = "";
		}
		boolean isHiden = false;
		if ("hide".equals(display)) {
			isHiden = true;
		}
		String authurl = "";
		if ((activity != null) && (!"".equals(activity)) && (process != null) && (!"".equals(process)))
			authurl = process + activity;
		else {
			authurl = url;
		}
		if (atsz > 0) {
			if ((!isHiden) && ((("".equals(url)) && (isEffective(ele))) || (isHaveAuther(authurl)))) {
				str.append("<li>");
				str.append("<a navid=\"" + ceateUUID() + "\">");
				str.append("<span>" + ele.attributeValue("label") + "</span>");
				str.append("</a>");
				for (int i = 0; i < attrs.size(); i++) {
					str.append("<ul>");
					readElementChl(attrs.get(i), str);
					str.append("</ul>");
				}
				str.append("</li>");
			}
		} else {
			if (!isHiden && isHaveAuther(authurl)) {
				str.append("<li>");
				str.append("<a navid=\"" + ceateUUID() + "\" label=\"" + ele.attributeValue("label")
						+ "\" onclick=\"openTabs(this)\" href=\"javascript:void(0);\" ");
				List attributes = ele.attributes();
				for (int i = 0; i < attributes.size(); i++) {
					Attribute attr = (Attribute) attributes.get(i);
					str.append(attr.getName() + "=\"" + attr.getValue() + "\" ");
				}
				str.append(">");
				str.append("<span>" + ele.attributeValue("label") + "</span>");
				str.append("</a>");
				str.append("</li>");
			}
		}
	}

	protected String readElement(Element ele) {
		StringBuffer str = new StringBuffer();
		StringBuffer tempStr = new StringBuffer();
		tempStr.append("{");
		tempStr.append("nodeName:'");
		tempStr.append(ele.getName());
		tempStr.append("',id:'" + ceateUUID());
		tempStr.append("' ");
		List attrs = ele.attributes();
		int atsz = attrs.size();
		String url = "";
		String authurl = "";
		String process = "";
		String activity = "";
		boolean isEff = isEffective(ele);
		boolean isHiden = false;
		if (atsz > 0) {
			for (int i = 0; i < atsz; i++) {
				tempStr.append(", ");
				Attribute attr = (Attribute) attrs.get(i);
				tempStr.append(attr.getName());
				tempStr.append(":'");
				tempStr.append(attr.getValue());
				tempStr.append("' ");
			}
			url = ele.attributeValue("url");
			process = ele.attributeValue("process");
			activity = ele.attributeValue("activity");
			String display = ele.attributeValue("display");
			if (url == null) {
				url = "";
			}
			if ("hide".equals(display)) {
				isHiden = true;
			}
			if (activity != null && !"".equals(activity) && process != null && !"".equals(process)) {
				authurl = process + activity;
			} else {
				authurl = url;
			}
		}
		tempStr.append(", childNodes:[");
		if (!isHiden && (("".equals(url) && isEff) || this.isHaveAuther(authurl))) {
			str.append(tempStr);
			List eles = ele.elements();
			if (!eles.isEmpty()) {
				int elesz = eles.size();
				for (int i = 0; i < elesz; i++) {
					Element e = (Element) eles.get(i);
					if (isEffective(e)) {
						if (i > 0) {
							str.append(", ");
						}
						str.append(this.readElement(e));
					}
				}
			}
			str.append("]} ");
		}
		return str.toString();
	}

	protected String readElement2(Element ele, String pid) {
		StringArray temparray = new StringArray();
		StringBuffer tempStr = new StringBuffer();
		List attrs = ele.attributes();
		int atsz = attrs.size();
		String url = "";
		String authurl = "";
		String process = "";
		String activity = "";
		boolean isEff = isEffective(ele);
		boolean isHiden = false;
		String id = "";
		if (!"root".equals(ele.getName())) {
			id = ceateUUID();
			tempStr = new StringBuffer();
			tempStr.append("{");
			tempStr.append("id:'" + id + "',");
			tempStr.append("pId:'" + pid + "'");
			if (atsz > 0) {
				for (int i = 0; i < atsz; i++) {
					Attribute attr = (Attribute) attrs.get(i);
					tempStr.append(",");
					if ("url".equals(attr.getName())) {
						tempStr.append("path");
						tempStr.append(":'");
						tempStr.append(attr.getValue());
						tempStr.append("'");
					} else {
						tempStr.append(attr.getName());
						tempStr.append(":'");
						tempStr.append(attr.getValue());
						tempStr.append("'");
					}
				}
				url = ele.attributeValue("url");
				process = ele.attributeValue("process");
				activity = ele.attributeValue("activity");
				String display = ele.attributeValue("display");
				if (url == null) {
					url = "";
				}
				if ("hide".equals(display)) {
					isHiden = true;
				}
				if (activity != null && !"".equals(activity) && process != null && !"".equals(process)) {
					authurl = process + activity;
				} else {
					authurl = url;
				}
			}
			tempStr.append("}");
			temparray.push(tempStr.toString());
		}
		if (!isHiden && (("".equals(url) && isEff) || this.isHaveAuther(authurl))) {
			List eles = ele.elements();
			if (!eles.isEmpty()) {
				int elesz = eles.size();
				for (int i = 0; i < elesz; i++) {
					Element e = (Element) eles.get(i);
					if (isEffective(e)) {
						tempStr = new StringBuffer();
						tempStr.append(this.readElement2(e, id));
						temparray.push(tempStr.toString());
					}
				}
			}
		}
		return temparray.join(",");
	}

	protected JSONObject readElement3(Element ele) throws Exception {
		JSONObject json = new JSONObject();
		json.put("text", ele.attributeValue("label"));
		List attrs = ele.attributes();
		int atsz = attrs.size();
		String url = "";
		String authurl = "";
		String process = "";
		String activity = "";
		boolean isEff = isEffective(ele);
		boolean isHiden = false;
		if (atsz > 0) {
			for (int i = 0; i < atsz; i++) {
				Attribute attr = (Attribute) attrs.get(i);
				json.put(attr.getName(), attr.getValue());
			}
			url = ele.attributeValue("url");
			process = ele.attributeValue("process");
			activity = ele.attributeValue("activity");
			String display = ele.attributeValue("display");
			if (url == null) {
				url = "";
			}
			if ("hide".equals(display)) {
				isHiden = true;
			}
			if (activity != null && !"".equals(activity) && process != null && !"".equals(process)) {
				authurl = process + activity;
			} else {
				authurl = url;
			}
		}
		List eles = ele.elements();
		if (!eles.isEmpty()) {
			if (!isHiden && (("".equals(url) && isEff) || this.isHaveAuther(authurl))) {
				int elesz = eles.size();
				JSONArray children = new JSONArray();
				for (int i = 0; i < elesz; i++) {
					Element e = (Element) eles.get(i);
					if (isEffective(e)) {
						children.add(this.readElement3(e));
					}
				}
				json.put("children", children);
				if (ele.attributeValue("iconCls") == null) {
					json.put("iconCls", "icon-sum");
				}
			}
		} else if (this.isHaveAuther(authurl)) {
			if (ele.attributeValue("iconCls") == null) {
				json.put("iconCls", "icon-file");
			}
		}
		return json;
	}

	public boolean isEffective(Element ele) {
		if ("root".equals(ele.getName())) {
			return true;
		}
		boolean eff = false;
		List eles = ele.elements();
		String url = ele.attributeValue("url");
		String process = ele.attributeValue("process");
		String activity = ele.attributeValue("activity");
		String display = ele.attributeValue("display");
		String authurl = "";
		if (url == null) {
			url = "";
		}
		if (activity != null && !"".equals(activity) && process != null && !"".equals(process)) {
			authurl = process + activity;
		} else {
			authurl = url;
		}
		if ((!"".equals(url) && this.isHaveAuther(authurl) && !"hide".equals(display))) {
			return true;
		} else if ("".equals(url)) {
			if (eles.isEmpty()) {
				return false;
			} else {
				int elesz = eles.size();
				for (int i = 0; i < elesz; i++) {
					Element e = (Element) eles.get(i);
					eff = isEffective(e);
					if (eff == true)
						return true;
				}
			}
		}
		return eff;
	}

	protected boolean isHaveAuther(String key) {
		return this.haveAutherMap.containsKey(key);
	}

}
