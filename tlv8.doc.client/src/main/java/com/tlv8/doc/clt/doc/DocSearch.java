package com.tlv8.doc.clt.doc;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

import com.tlv8.base.db.DBUtils;

@SuppressWarnings("rawtypes")
public class DocSearch {
	private final String SEARCHRESULT_TAGNAME = "searchResult";
	@SuppressWarnings("unused")
	private boolean ishttps = false;
	List<String> urls = new ArrayList<String>();
	private Namespace NS_NSSearch = new Namespace("ns",
			"http://outerx.org/daisy/1.0");
	private final String QUERYSTR = "id, name,lastModified,#sDocId,#sDocPath,#sDocDisplayPath,#sEditorName,#sCreatorName,#sKeywords,#sLastWriterName,#sDescription";
	private final String SEARCHHANDLER = "/repository/file/query";

	public Document performQuery(String url, String query) throws Exception {

		return performQuery(url, query, null);
	}

	public Document performQuery(String url, String query, String extraCond)
			throws Exception {
		return performQuery(url, query, extraCond, null);
	}

	public Document performQuery(String url, String query, String extraCond,
			String extraOrderBy) throws Exception {
		return performQuery(url, query, extraCond, extraOrderBy, null);
	}

	public Document performQuery(String url, String query, String extraCond,
			String extraOrderBy, int chunkOffset, int chunkLength)
			throws Exception {
		try {
			Utils.check(Utils.isNotNull(chunkOffset)
					&& Utils.isNotNull(chunkLength),
					"performQuery的chunkOffset和chunkLength参数不能为空！");
		} catch (ModelException e) {
			e.printStackTrace();
		}
		String queryOptions = "chunk_offset = " + String.valueOf(chunkOffset)
				+ ",chunk_length = " + String.valueOf(chunkLength);
		return performQuery(url, query, extraCond, extraOrderBy, queryOptions);
	}

	public Document performQuery(String url, String query, String extraCond,
			String extraOrderBy, String queryOptions) throws Exception {
		try {
			Utils.check(Utils.isNotEmptyString(url),
					"performQuery的url和query参数不能为空！");
		} catch (ModelException e) {
			e.printStackTrace();
		}
		query = Utils.isEmptyString(query) ? QUERYSTR : query;
		String ssql = " select " + query + " where "
				+ (Utils.isEmptyString(extraCond) ? "true" : extraCond);
		// String ssql = " select " + query + " where " + "true";
		if (Utils.isNotEmptyString(extraOrderBy))
			ssql += " order by " + extraOrderBy;
		if (Utils.isNotEmptyString(queryOptions)) {
			ssql += " option " + queryOptions;
		}
		String param = "<form><querySql>" + ssql + "</querySql></form>";
		return DocUtils.excutePostAction(url,
				new ByteArrayInputStream(param.getBytes("UTF-8")));
	}

	public Document performAllQuery(List<String> urls, String query,
			String extraCond, String extraOrderBy, int chunkOffset,
			int chunkLength) throws Exception {
		/* 初始化xml */
		Document resultXml = DocumentHelper.createDocument();
		Element search = resultXml.addElement(new QName(SEARCHRESULT_TAGNAME,
				NS_NSSearch));
		int totalSize = 0;
		int i = 0;
		String serversCount = "";
		int restLength = chunkLength;

		for (String url : urls) {
			Document item = performQuery(url, query, extraCond, extraOrderBy,
					chunkOffset, chunkLength);
			// System.out.println(item.asXML());
			int tempSize = item.selectNodes("//ns:rows/ns:row").size();
			// System.out.println(tempSize);
			totalSize += Integer.valueOf(((Element) item
					.selectSingleNode("//ns:resultInfo"))
					.attributeValue("size"));
			if (i == 0) {
				/* 增加标题 */
				Element titles = (Element) item
						.selectSingleNode("//ns:searchResult/ns:titles");
				Element ele = (Element) resultXml
						.selectSingleNode(".//*[local-name()='searchResult']");
				ele.add((Element) titles.clone());
				ele.addElement("ns:rows");
				i++;
			}

			serversCount += url + ":" + String.valueOf(totalSize) + ",";
			if (restLength <= 0)
				continue;

			List<?> items = item
					.selectNodes("//ns:searchResult/ns:rows/ns:row");
			Element ele = (Element) resultXml
					.selectSingleNode("//*[local-name()='rows']");

			int j = 1;
			for (Iterator<?> it = items.iterator(); it.hasNext();) {
				if (j > restLength)
					break;
				Element element = (Element) it.next();
				ele.add((Element) element.clone());
				j++;
			}
			if (totalSize > chunkOffset)
				chunkOffset = 1;
			restLength = tempSize - chunkOffset - restLength >= 0 ? 0
					: restLength - (tempSize - chunkOffset);
		}
		Element servers = search.addElement("ns:servers");
		servers.addAttribute("size", String.valueOf(totalSize));
		servers.addAttribute("serverCounts", serversCount.substring(0,
				serversCount.length() - 1));

		return resultXml;
	}

	public Document queryPages(String keyWord, int pageSize, int currentPage,
			boolean isHttps) throws Exception {
		Document sendDoc = initSearchDoc(keyWord, pageSize, -1, isHttps);
		Document init = queryDocSearch(sendDoc);
		Element ele = (Element) init
				.selectSingleNode(".//*[local-name()='servers']");
		String size = ele.attributeValue("size");
		String pages = ele.attributeValue("pages");
		sendDoc.selectSingleNode("//querySql").setText(QUERYSTR);
		sendDoc.selectSingleNode("//currentPage").setText(
				String.valueOf(currentPage));
		sendDoc.selectSingleNode("//servers").setText(size);
		sendDoc.selectSingleNode("//recordCount").setText(size);
		sendDoc.selectSingleNode("//pageCount").setText(pages);
		return init;//queryDocSearch(sendDoc);
	}

	private Document initSearchDoc(String keyWord, int pageSize,
			int currentPage, boolean isHttps) throws DocumentException {
		StringBuffer docStr = new StringBuffer();
		docStr.append("<data>");
		docStr.append("<operate>queryDocSearch</operate>");
		docStr.append("<form>");
		docStr.append("<keyword>" + keyWord + "</keyword>");
		docStr
				.append("<querySql>select id, name,lastModified,#sDocId,#sDocPath,#sDocDisplayPath,#sEditorName,#sCreatorName,#sKeywords,#sLastWriterName,#sDescription</querySql>");
		docStr.append("<extraCond>FullText('" + keyWord + "')</extraCond>");
		docStr.append("<extraOrderBy>creationTime desc</extraOrderBy>");
		docStr.append("<words>");
		docStr.append("<word>" + keyWord + "</word>");
		docStr.append("</words>");
		docStr.append("<pageCount/>");
		docStr.append("<pageSize>" + pageSize + "</pageSize>");
		docStr.append("<currentPage>" + currentPage + "</currentPage>");
		docStr.append("<servers></servers>");
		docStr.append("<recordCount/>");
		docStr.append("<deptPath>" + new ContextHelper().getPersonFID()
				+ "</deptPath>");
		docStr.append("<personId>" + new ContextHelper().getPersonName()
				+ "</personId>");
		docStr.append("<ishttps>" + isHttps + "</ishttps>");
		docStr.append("</form>");
		docStr.append("</data>");
		return DocumentHelper.parseText(docStr.toString());
	}

	private String getDocQuerySql(String key) {
		return "select id, name,lastModified,#sDocId,#sDocPath,#sDocDisplayPath,#sEditorName,#sCreatorName,#sKeywords,#sLastWriterName,#sDescription";
	}

	// 文档搜索
	public Document queryDocSearch(Document document) throws Exception {
		Document result = null;
		//System.out.println(document.asXML());
		int pageSize = Integer.valueOf(DocUtils.getNodeText(document,
				"//form/pageSize"));
		int currentPage = Integer.valueOf(DocUtils.getNodeText(document,
				"//form/currentPage"));
		boolean ishttps = DocUtils.getNodeText(document, "//form/ishttps")
				.equals("true") ? true : false;

		List<String> urls = getUrls(ishttps);
		if (urls.size() < 1) {
			throw new Exception("SA_DOCNAMESPACE查询结果为空，请进行文档配置！");
		}
		result = initSearchXml(result);

		// 第一次初始化信息
		if (currentPage == -1) {
			// System.out.println(document.asXML());
			result = firstInitPage(pageSize, urls, document, result);
		} else if (currentPage > 0) {
			result = commonInitPage(pageSize, currentPage, urls, document,
					result);
		}
		return result;
	}

	private Document firstInitPage(int pageSize, List<String> urls,
			Document postDocument, Document result) throws Exception {
		int chunkOffset = 1, i = 0, recordCount = 0;
		String serverCounts = "";
		int restSize;
		restSize = pageSize;
		for (String url : urls) {
			try {
				Document tempQuery;
				postDocument = setNodePaged(String.valueOf(chunkOffset), String
						.valueOf(pageSize), postDocument);
				String param = postDocument.asXML();
				tempQuery = DocUtils.excutePostAction(url,
						new ByteArrayInputStream(param.getBytes("UTF-8")));
				int tempRecordCount = Integer.valueOf(DocUtils
						.getNodeAttribute(tempQuery, "//ns:resultInfo/@size"));
				int requestedChunkLength = tempQuery.selectNodes(
						"//ns:rows/ns:row").size();
				if (i == 0) {
					// 增加列标题
					result = CreateSearchTitle(tempQuery, result);
				}
				int temp = restSize - requestedChunkLength;
				if (requestedChunkLength > 0 && temp >= 0) {
					// 添加分页信息
					result = addSearchItem(requestedChunkLength, tempQuery,
							result);
					restSize = temp;
				}

				// 添加服务器信息
				serverCounts = i == (urls.size() - 1) ? String
						.valueOf(tempRecordCount) : String
						.valueOf(tempRecordCount)
						+ ",";

				recordCount += tempRecordCount;
				// requestedChunkLength += requestedChunkLength;
				i++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Element eltRecordCount = (Element) result.selectNodes(
				"//ns:searchResult/ns:servers").get(0);
		eltRecordCount.addAttribute("size", String.valueOf(recordCount));
		eltRecordCount.addAttribute("pages", String.valueOf(recordCount
				% pageSize == 0 ? recordCount / pageSize : recordCount
				/ pageSize + 1));
		eltRecordCount.addAttribute("serverCounts", serverCounts);
		eltRecordCount.addAttribute("currentPage", "1");

		return result;
	}

	public Document commonInitPage(int pageSize, int currentPage,
			List<String> urls, Document postDocument, Document result)
			throws Exception {
		int dataCount = Integer.valueOf(DocUtils.getNodeText(postDocument,
				"//form/recordCount"));
		int startRecord = ((currentPage - 1) * pageSize) + 1;
		String[] serverCountArr = DocUtils.getNodeText(postDocument,
				"//form/servers").split(",");
		int stateServer = 0;
		int restSize;
		restSize = pageSize;
		for (int m = 0; m < serverCountArr.length; m++) {
			int server = Integer.valueOf(serverCountArr[m]);
			startRecord = startRecord - server;
			if (startRecord <= 0) {
				startRecord = startRecord + server;
				stateServer = m;
				break;
			}
		}
		int j = 0, z = 0;

		for (String url : urls) {
			try {
				Document tempQuery;
				if (j >= stateServer) {
					postDocument = setNodePaged(String.valueOf(startRecord),
							String.valueOf(pageSize), postDocument);
					String param = postDocument.asXML();
					tempQuery = DocUtils.excutePostAction(url,
							new ByteArrayInputStream(param.getBytes("UTF-8")));
					int requestedChunkLength = tempQuery.selectNodes(
							"//ns:rows/ns:row").size();
					if (z == 0) {
						// 增加列标题
						result = CreateSearchTitle(tempQuery, result);
					}
					int temp = restSize - requestedChunkLength;
					if (requestedChunkLength > 0 && temp >= 0) {
						// 添加分页信息
						result = addSearchItem(requestedChunkLength, tempQuery,
								result);
						restSize = temp;
					}
					if (restSize <= 0)
						break;
					z++;
				}
				j++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Element eltRecordCount = (Element) result.selectNodes(
				"//ns:searchResult/ns:servers").get(0);
		eltRecordCount.addAttribute("size", String.valueOf(dataCount));
		eltRecordCount.addAttribute("pages", DocUtils.getNodeText(postDocument,
				"//form/pageCount"));
		eltRecordCount.addAttribute("serverCounts", DocUtils.getNodeText(
				postDocument, "//form/servers"));
		eltRecordCount.addAttribute("currentPage", String.valueOf(currentPage));

		return result;
	}

	private Document addSearchItem(int count, Document inputXml,
			Document resultXml) {
		List<?> items = inputXml
				.selectNodes("//ns:searchResult/ns:rows/ns:row");
		Element ele = (Element) resultXml.selectNodes(
				"//*[local-name()='rows']").get(0);
		for (Iterator<?> it = items.iterator(); it.hasNext();) {
			Element element = (Element) it.next();
			ele.add((Element) element.clone());
		}
		return resultXml;
	}

	private Document CreateSearchTitle(Document inputXml, Document resultXml) {
		Element titles = (Element) inputXml.selectNodes(
				"//ns:searchResult/ns:titles").get(0);
		Element ele = (Element) resultXml.selectNodes(
				".//*[local-name()='searchResult']").get(0);
		ele.add((Element) titles.clone());
		ele.addElement("ns:rows");
		return resultXml;
	}

	private Document setNodePaged(String chunkOffset, String chunkLength,
			Document document) throws Exception {
		Element queryElt = (Element) document.selectNodes("//form/querySql")
				.get(0);
		String sQuery = getDocQuerySql(queryElt.getTextTrim());
		Element extraCondElt = (Element) document.selectNodes(
				"//form/extraCond").get(0);
		String sCond = extraCondElt.getTextTrim();
		Element extraOrderByElt = (Element) document.selectNodes(
				"//form/extraOrderBy").get(0);
		String sOrderBy = extraOrderByElt.getTextTrim();
		Element deptPathElt = (Element) document.selectNodes("//form/deptPath")
				.get(0);
		String deptPath = deptPathElt.getTextTrim();
		Element personIdElt = (Element) document.selectNodes("//form/personId")
				.get(0);
		String personId = personIdElt.getTextTrim();
		/* 添加权限 */
		List<String> list = queryDisplayNonePermission(deptPath, personId);
		if (list != null) {
			for (String docFullPath : list) {
				String docId = docFullPath.substring(docFullPath
						.lastIndexOf("/") + 1);
				sCond += " and #sDocId!='" + docId
						+ "' and #sDocPath not like '" + docFullPath + "%' ";
			}
		}
		queryElt.setText(sQuery + " where " + sCond + " order by " + sOrderBy
				+ " option chunk_offset = " + chunkOffset + ",chunk_length = "
				+ chunkLength);
		return document;
	}

	private List<String> queryDisplayNonePermission(String deptPath,
			String personId) throws Exception {
		String[] pathList = deptPath.split("/");
		String sqlLike = "";
		String parent = "";
		for (int i = 0; i < pathList.length; i++) {
			String id = pathList[i];
			if ("".equals(id)) {
				if (i == 0) {
					continue;
				} else {
					throw new Exception(String.format("“%s”路径中不能包含空字符串",
							deptPath));
				}
			}
			parent = i == 0 ? id : parent + "/" + id;
			if (i == pathList.length - 1) {
				sqlLike = sqlLike + " p.sAuthorizeeFID = '" + parent + "' ";
				continue;
			}
			sqlLike = sqlLike + " p.sAuthorizeeFID = '" + parent + "' or";
		}

		String ksql = "select p.sID, p.sDocPath, p.sAuthorizeeFID, p.sAccess from SA_DocAuth p where "
				+ sqlLike + " order by p.sDocPath , p.sAuthorizeeFID desc ";

		List<?> table = DBUtils.execQueryforList("system", ksql);
		List<String> displayNonelist = new ArrayList<String>();

		String displayFullPath = "";
		if (table != null) {
			for (int i = 0; i < table.size(); i++) {
				Docinfo r = new Docinfo((Map) table.get(i));
				if ((!displayFullPath.equals(r.getString("sDocPath")))
						&& 0 == r.getInt("sAccess")) {
					displayNonelist.add(r.getString("sDocPath"));
				}
				displayFullPath = r.getString("sDocPath");
			}
		}
		return displayNonelist;
	}

	private List<String> getUrls(boolean isHttps) {
		List table = DocDBHelper.queryNameSpace();
		for (int i = 0; i < table.size(); i++) {
			Docinfo r = new Docinfo((Map) table.get(i));
			String urlString = r.getString("sUrl") + SEARCHHANDLER;
			urlString = Utils.moveRepeatStr(urlString, SEARCHHANDLER);
			boolean hasItem = false;
			for (String urlItem : urls)
				if (urlString.equals(urlItem))
					hasItem = true;
			if (hasItem)
				continue;
			urls.add(urlString);
		}
		return urls;
	}

	private Document initSearchXml(Document resultXml) {
		resultXml = DocumentHelper.createDocument();
		Element search = resultXml.addElement(new QName(SEARCHRESULT_TAGNAME,
				NS_NSSearch));
		search.addElement("ns:servers");
		return resultXml;
	}

	public static void main(String[] args) throws Exception {
		DocSearch docSearch = new DocSearch();
		System.out.println(docSearch.queryPages("数据", 1, 100, false).asXML());
	}

}
