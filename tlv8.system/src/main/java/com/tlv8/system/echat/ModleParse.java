package com.tlv8.system.echat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ModleParse {
	HttpServletRequest request;

	String sources;
	Document doc;

	Element baseData;
	Element chartEl;

	Parameters parameter;

	public ModleParse(Document doc, HttpServletRequest request) throws Exception {
		this.doc = doc;
		this.request = request;
		initDom();
	}

	private void initDom() throws Exception {
		baseData = doc.getRootElement().element("data");
		chartEl = doc.getRootElement().element("chart");
		if (baseData != null) {
			parameter = new Parameters(baseData, request);
			DataBaseSQLParse.transeData(baseData, parameter);
		}
	}

	/**
	 * Element属性和或内容转换传JSON对象
	 * 
	 * @param el
	 * @return
	 */
	protected final Object element2Json(Element el) {
		try {
			List<Attribute> list = el.attributes();
			if (list.size() < 1) {
				return elementgetText(el);
			}
			JSONObject json = new JSONObject();
			for (int i = 0; i < list.size(); i++) {
				Attribute attr = list.get(i);
				String svalue = attr.getValue();
				json.put(attr.getName(), valueTranse(svalue));
			}
			return json;
		} catch (Exception e) {
			return elementgetText(el);
		}
	}

	protected final Object elementgetText(Element element) {
		String stext = element.getText();
		if (stext != null && !"".equals(stext)) {
			stext = stext.replace("\t", " ");
			stext = stext.replace("\n", " ");
			return valueTranse(stext);
		} else {
			return "{}";
		}
	}

	public final Object valueTranse(String svalue) {
		Pattern pattern = Pattern.compile("(?<=\\{\\{)(.+?)(?=\\}\\})");
		Matcher matcher = pattern.matcher(svalue);
		while (matcher.find()) {
			String src = matcher.group(1);
			String val = baseData.elementText(src);
			if (val == null) {
				val = "";
			}
			svalue = svalue.replace("{{" + src + "}}", val);
		}
		Object sovalue = svalue;
		try {
			sovalue = JSON.parseArray(svalue);
		} catch (Exception e) {
			try {
				sovalue = JSON.parseObject(svalue);
			} catch (Exception e2) {
			}
		}
		return sovalue;
	}

	protected final String valueTranseStr(String svalue) {
		Pattern pattern = Pattern.compile("(?<=\\{\\{)(.+?)(?=\\}\\})");
		Matcher matcher = pattern.matcher(svalue);
		while (matcher.find()) {
			String src = matcher.group(1);
			String val = baseData.elementText(src);
			if (val == null) {
				val = "";
			}
			svalue = svalue.replace("{{" + src + "}}", val);
		}
		return svalue;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			List<Element> clist = chartEl.elements();
			List<Element> series = new ArrayList<Element>();
			for (int i = 0; i < clist.size(); i++) {
				Element chartproEl = clist.get(i);
				if (!"series".equals(chartproEl.getName())) {
					json.put(chartproEl.getName(), element2Json(chartproEl));
				} else {
					series.add(chartproEl);
				}
			}
			JSONArray seriesarr = new JSONArray();
			for (int i = 0; i < series.size(); i++) {
				Element chartproEl = series.get(i);
				Object object = element2Json(chartproEl);
				if (object.getClass() == JSONArray.class) {
					JSONArray popjson = (JSONArray) object;
					for (int n = 0; n < popjson.size(); n++) {
						seriesarr.add(popjson.get(n));
					}
				} else {
					seriesarr.add(object);
				}
			}
			if (seriesarr.size() > 0) {
				json.put("series", seriesarr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public String toJSONString() {
		return toJSON().toString();
	}

	public String getChartText() {
		String text = chartEl.getText();
		text = valueTranseStr(text);
		return text;
	}
}
