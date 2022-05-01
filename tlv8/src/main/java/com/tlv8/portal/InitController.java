package com.tlv8.portal;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.system.action.FunctreeControl;
import com.tlv8.system.bean.ContextBean;

@RequestMapping("/portal")
@Controller
@SuppressWarnings({ "unchecked", "rawtypes" })
public class InitController {
	private static final String FILE_POSTFIX = ".fun.xml";
	private static final String FILE_POSTFIXF = ".fun";
	protected String spersonfID = "";
	protected Map haveAutherMap = new HashMap();

	public String getFunctionTreeFilePath(HttpServletRequest request) {
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

	@ResponseBody
	@RequestMapping("/initMenu")
	public Object initMenu(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> hmap = new HashMap<String, Object>();
		hmap.put("title", "首页");
		hmap.put("href", "home/console.html");
		res.put("homeInfo", hmap);
		Map<String, Object> logmap = new HashMap<String, Object>();
		logmap.put("title", "TLv8 平台");
		logmap.put("image", "images/logo.png");
		logmap.put("href", "");
		res.put("logoInfo", logmap);
		String filePathDir = getFunctionTreeFilePath(request);
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");

		ContextBean context = ContextBean.getContext(request);
		this.spersonfID = context.getCurrentPersonFullID();
		String psnid = context.getCurrentPersonID();
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
		res.put("menuInfo", getMenu(root));
		return res;
	}

	private List<Map<String, Object>> getMenu(Element root) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		boolean isEff = isEffective(root);
		if (isEff) {
			List<Element> eles = root.elements();
			for (int i = 0; i < eles.size(); i++) {
				Element ele = eles.get(i);
				if (isEffective(ele)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("title", ele.attributeValue("label"));
					map.put("icon", ele.attributeValue("layuiIcon"));
					map.put("href", ele.attributeValue("url"));
					map.put("process", ele.attributeValue("process"));
					map.put("activity", ele.attributeValue("activity"));
					map.put("target", ele.attributeValue("target"));
					map.put("child", getMenu(ele));
					list.add(map);
				}
			}
		}
		return list;
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
