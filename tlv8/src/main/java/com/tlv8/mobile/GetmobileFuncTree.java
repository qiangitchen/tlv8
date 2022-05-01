package com.tlv8.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.action.FunctreeControl;
import com.tlv8.system.bean.ContextBean;

/**
 * 获取手机功能权限
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GetmobileFuncTree extends ActionSupport {
	protected Map haveAutherMap = new HashMap();

	@ResponseBody
	@RequestMapping("/getmobileFuncTree")
	public void execute1() throws Exception {
		String filePathDir = getFunctionTreeFilePath();
		Document doc = DocumentHelper.createDocument();
		Element docRoot = DocumentHelper.createElement("root");

		ContextBean context = ContextBean.getContext(request);
		String spersonfID = context.getCurrentPersonFullID();

		String psnid = context.getCurrentPersonID();
		haveAutherMap = FunctreeControl.gethaveAuther(spersonfID, psnid);
		if (!"".equals(filePathDir)) {
			List fileElementList = new ArrayList();
			List tempList = generateNewByFile(filePathDir + "/mobile.fun");
			fileElementList.addAll(tempList);
			docRoot.clearContent();
			docRoot.setContent(fileElementList);
		}
		doc.add(docRoot);

		Element root = doc.getRootElement();
		String funmenu = this.readElement(root);
		JSONObject res = new JSONObject();
		if (!"".equals(funmenu)) {
			res.put("data", JSON.toJSONString(JSON.parseObject(funmenu)));// 转换一些防止结构错误
		} else {
			res.put("data", "");
		}
		// funmenu=funmenu.replace("label", "\"label\"");
		// funmenu=funmenu.replace("activity", "\"activity\"");
		// funmenu=funmenu.replace("display", "\"display\"");
		// funmenu=funmenu.replace("icon", "\"icon\"");
		// funmenu=funmenu.replace("process:", "\"process\":");
		// funmenu=funmenu.replace("url", "\"url\"");
		// funmenu=funmenu.replace(":[,", ":[");
		// renderData(funmenu);
		res.put("status", "SUCCESS");
		try {
			response.setHeader("Content-Type", "text/json;charset=UTF-8");
			response.getWriter().print(res.toString());
			response.getWriter().close();
		} catch (Exception e) {
		}
	}

	public String getFunctionTreeFilePath() {
		String filePathDir = "";
		try {
			filePathDir = request.getServletContext().getRealPath("/WEB-INF/funtree");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePathDir;
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

	protected String readElement(Element ele) {
		StringBuffer str = new StringBuffer();
		StringBuffer tempStr = new StringBuffer();
		tempStr.append("{");
		tempStr.append("\"nodeName\":\"");
		tempStr.append(ele.getName());
		String attrID = ele.attributeValue("id");
		tempStr.append("\",\"id\":\"" + nulltsGUID(attrID));
		tempStr.append("\"");
		List attrs = ele.attributes();
		int atsz = attrs.size();
		String url = "";
		String authurl = "";
		String process = "";
		String activity = "";
		boolean isEff = isEffective(ele);
		if (atsz > 0) {
			for (int i = 0; i < atsz; i++) {
				Attribute attr = (Attribute) attrs.get(i);
				if (!"id".equals(attr.getName())) {
					tempStr.append(", ");
					tempStr.append(attr.getName());
					tempStr.append(":\"");
					tempStr.append(attr.getValue());
					tempStr.append("\"");
				}
			}
			url = ele.attributeValue("url");
			process = ele.attributeValue("process");
			activity = ele.attributeValue("activity");
			if (url == null) {
				url = "";
			}
			if (activity != null && !"".equals(activity) && process != null && !"".equals(process)) {
				authurl = process + activity;
			} else {
				authurl = url;
			}
		}
		tempStr.append(", \"childNodes\":[");
		if (("".equals(url) && isEff) || this.isHaveAuther(authurl)) {
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
			str.append("]}");
		}
		return str.toString();
	}

	public static String nulltsGUID(String nid) {
		if (nid == null || "".equals(nid)) {
			return ceateUUID();
		}
		return nid;
	}

	public static String ceateUUID() {
		String id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
		return id;
	}

	public boolean isEffective(Element ele) {
		boolean eff = false;
		List eles = ele.elements();
		String url = ele.attributeValue("url");
		String process = ele.attributeValue("process");
		String activity = ele.attributeValue("activity");
		String authurl = "";
		if (url == null) {
			url = "";
		}
		if (activity != null && !"".equals(activity) && process != null && !"".equals(process)) {
			authurl = process + activity;
		} else {
			authurl = url;
		}
		if ((!"".equals(url) && this.isHaveAuther(authurl))) {
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

	public static void main(String[] args) {
		try {
			new GetmobileFuncTree().execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
