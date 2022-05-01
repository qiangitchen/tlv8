package com.tlv8.doc.clt.doc;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.controller.UserController;

/**
 * 全文检索
 * 
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class queryDocSearch extends ActionSupport {
	private Data data = new Data();
	private int page = 1;
	private int rows = 20;
	private String keyWord = null;

	public Data getdata() {
		return this.data;
	}

	public void setdata(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/queryDocSearch")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		String r = "";
		String m = "success";
		String f = "";
		try {
			DocSearch docSearch = new DocSearch();
			Document doc = docSearch.queryPages(keyWord, rows, page, false);
			r = docToHTML(doc);
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.toString();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		data.setPage(page);
		return this;
	}

	@SuppressWarnings({ "rawtypes" })
	private String docToHTML(Document doc) throws DocumentException, SQLException {
		String result = "";
		String docStr = doc.asXML();
		docStr = docStr.replace("xmlns:ns=\"http://outerx.org/daisy/1.0\"", "");
		docStr = docStr.replaceAll("ns:", "");
		// System.out.println(docStr);
		doc = DocumentHelper.parseText(docStr);
		Element servers = doc.getRootElement().element("servers");
		int size = Integer.parseInt(servers.attributeValue("size"));
		if (size == 0) {
			result = "<div style='font-size:12px;background-color:#f0f7f9;height:24px;border-top:#6b90da 1px solid;'>搜索 "
					+ keyWord + " 获得 0 条结果。</div>";
		} else {
			size = 0;
			result = "<div style='font-size:12px;background-color:#f0f7f9;height:24px;border-top:#6b90da 1px solid;'>搜索 "
					+ keyWord + " 获得resultSize条结果。</div>";
			StringBuilder str = new StringBuilder();
			str.append(
					"<table style='width:100%;font-size:12px;'><tr style='background:#eee;font-weight: bold;'><td>文档ID</td><td>文档名称</td><td>文档位置</td><td>作者</td><td>关键字</td></tr>");
			List<?> rowsNode = doc.getRootElement().selectNodes("rows");
			int rows = rowsNode.size();
			UserController user = new UserController();
			String currentDptName = user.getContext().getCurrentDeptName();
			String currentPsnName = user.getContext().getCurrentPersonName();
			for (int i = 0; i < rows; i++) {
				Element titles = (Element) rowsNode.get(i);
				List<?> rowNode = titles.selectNodes("row");
				int row = rowNode.size();
				// System.out.println(row);
				for (int j = 0; j < row; j++) {
					str.append("<tr style='border-top:#6b90da 1px solid;'>");
					List<?> TitlenodeLi = doc.getRootElement().selectNodes("titles");
					List<?> rowNodeLi = ((Element) rowNode.get(j)).selectNodes("value");
					@SuppressWarnings("unused")
					List<?> TitlerowNode = ((Element) TitlenodeLi.get(0)).selectNodes("title");
					String fileID = ((Node) rowNodeLi.get(0)).getText();
					String sql = "select SDOCNAME,SDOCPATH,SDOCDISPLAYPATH,SCREATORNAME,SKEYWORDS from SA_DOCNODE where SFILEID='"
							+ fileID + "'";
					List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
					if (list.size() > 0) {
						Map m = list.get(0);
						String asql = "select SACCESS from SA_DOCAUTH where '" + m.get("DOCPATH")
								+ "' = SDOCPATH  and (sAuthorizeeDeptName ='" + currentDptName
								+ "' or sAuthorizeeName ='" + currentPsnName + "')";
						List ap = DBUtils.execQueryforList("system", asql);
						if (ap.size() > 0 && Integer.valueOf((String) ((Map) ap.get(0)).get("SACCESS")) < 3) {
							continue;
						} else {
							str.append("<td>" + fileID + "</td>");
							str.append("<td>" + "<a href='javaScript:justep.yn.dowloadfile(\"" + fileID + "\", \""
									+ m.get("SDOCNAME") + "\")'>" + m.get("SDOCNAME") + "</td>");
							str.append("<td>" + m.get("SDOCDISPLAYPATH") + "</td>");
							str.append("<td>" + m.get("SCREATORNAME") + "</td>");
							str.append("<td>" + keyWord + "</td>");
							size = size + 1;
						}
					}
					str.append("</tr>");
				}
			}
			str.append("</table>");
			result = result.replace("resultSize", String.valueOf(size));
			result += str.toString();
		}
		return result;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		return rows;
	}

	public void setKeyWord(String keyWord) {
		try {
			this.keyWord = URLDecoder.decode(keyWord, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getKeyWord() {
		return keyWord;
	}
}
