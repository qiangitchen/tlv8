package com.tlv8.system.controller;

import java.io.File;
import java.io.IOException;
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

import com.tlv8.system.BaseController;
import com.tlv8.system.action.FunctreeControl;

@Controller
@Scope("prototype")
@RequestMapping("/system")
@SuppressWarnings({ "rawtypes" })
public class FuncTreeEnController extends BaseController {
	private static final String FILE_POSTFIX = ".xml";
	private String spersonfID = "";
	private Map haveAutherMap = new HashMap();

	@ResponseBody
	@RequestMapping("/en_FuncTree")
	@SuppressWarnings("unchecked")
	// @Validate( { UserValidator.class })
	public void index() throws HttpException, IOException, DocumentException {
		/** 当前环境绝对路径 */
		String realPath = Thread.currentThread().getContextClassLoader().getResource("").toString();
		realPath = realPath.replace("file:/", "");
		realPath = realPath.substring(0, realPath.indexOf("WEB-INF"));
		realPath = realPath.replaceAll("%20", " ");

		/** 文件目录 */
		String filePathDir = "WEB-INF/funtree/en_US/";
		if (filePathDir != null && !"".equals(filePathDir))
			filePathDir = realPath + filePathDir;
		else
			filePathDir = "";
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
	private List generateFileList(String fileDir) {
		ArrayList fileList = new ArrayList();
		File file = new File(fileDir);
		File[] subFiles = file.listFiles();
		for (File subFile : subFiles) {
			if (subFile.isFile()) {
				if (subFile.getName().contains(FILE_POSTFIX)) {
					fileList.add(fileDir + subFile.getName());
				}
			}
		}
		return fileList;
	}

	private static String ceateUUID() {
		String id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
		return id;
	}

	private String readElement(Element ele) {
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

	private boolean isEffective(Element ele) {
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
		if ((!"".equals(url) && this.isHaveAuther(authurl) && !"hide".equals(display))) {// || (!"".equals(url) &&
			// "solid".equals(display))
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

	private boolean isHaveAuther(String key) {
		return this.haveAutherMap.containsKey(key);
	}
}
