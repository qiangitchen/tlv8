package com.tlv8.doc.clt.doc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;
import com.tlv8.doc.clt.doc.attachment.Attachments;

public class DocUtils {

	public static final String DATA_MODEL = "/SA/doc/data";
	public static final String EXP_MODEL = "/system/logic/fn";
	public final static String DOCNODE_CONCEPT = "SA_DocNode";

	protected static void check(boolean b, String msg, Object... objects) {
		if (!b)
			throw new DocRTException(String.format(msg, objects));
	}

	protected static void registHttps(String url) {
		if ("https".equals(url.substring(0, url.indexOf(":")))) {
			@SuppressWarnings("deprecation")
			Protocol myhttps = new Protocol("https", new SslSecureProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", myhttps);
		}
	}

	public static String lock(String docID) {
		int affectNum = DocDBHelper.updateDocState(true, docID);
		if (affectNum == 0) {
			int resultNum = DocDBHelper.checkLocker(docID);
			if (resultNum == 0) {
				return "failure";

			} else if (resultNum == 1) {
				return "noNeedLock";
			}
		} else if (affectNum == 1) {
			return "success";
		}
		return "failure";
	}

	public static String unlock(String docID) {
		int affectNum = DocDBHelper.updateDocState(false, docID);
		// TODO :提示出来谁解锁的
		return affectNum != 1 ? "success" : "failure";

	}

	public static void createVersionFromJsonStr(String billID, String jsonStr, Boolean isHttps, String process,
			String activity) throws Exception {
		Attachments atts = new Attachments(process, activity, jsonStr, true, null, isHttps);
		atts.createVersion();
	}

	public static String createVersion(String sDocID, Boolean isHttps) throws Exception {
		int lockNum = DocDBHelper.checkLocker(sDocID);
		if (lockNum != 1) {
			return "lockFailure";
		}

		Docs docs = new Docs(null, isHttps).query(sDocID, null, null, null, null);

		Doc doc = docs.get(sDocID);
		if (doc.getsFlag() != 1) {
			return "deletedDoc";
		}
		// TODO:利用Utils.isNotEmptyString 判断
		if ((doc.getCacheName() == null || doc.getCacheName().equals(""))
				&& (doc.getRevisionCacheName() == null || doc.getRevisionCacheName().equals(""))) {
			DocUtils.unlock(sDocID);
			return "commitCacheFailure";
		}
		int affectNum = docs.createVersion();
		if (affectNum == 1) {
			return "success";
		} else {
			throw new DocRTException("成文异常：sDocID" + sDocID + "影响的记录数不唯一");
		}
	}

	public static void saveDocFlag(String docPath, String kind, String fileID, String cacheName, Boolean isHttps)
			throws Exception {
		String host = DocDBHelper.queryDocHost();
		String url = host + "/repository/file/cache/commit";
		if (Utils.isNotEmptyString(fileID)) {
			StringBuffer sb = new StringBuffer();
			sb.append("<data><item>");
			sb.append("<operation-type>flagCommit</operation-type>");
			sb.append("<kind>");
			sb.append(kind);
			sb.append("</kind>");
			sb.append("<file-id>");
			sb.append(fileID);
			sb.append("</file-id>");
			sb.append("<cache-name>");
			sb.append(cacheName);
			sb.append("</cache-name>");
			sb.append("</item></data>");
			Document result = excutePostAction(url, new ByteArrayInputStream(sb.toString().getBytes("UTF-8")));
			System.out.println(result.asXML());
			if (!"true".equals(result.selectSingleNode("//flag").getText())) {
				throw new DocRTException("DocServer flagCommit error ", new Exception());
			}
		}
	}

	public static String excutePostAction(String url) throws Exception {
		registHttps(url);
		HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		// System.out.println(addBsessionId(getUrlAssign(url)));
		PostMethod postMethod = new PostMethod(addBsessionId(getUrlAssign(url)));
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				if (statusCode == 202) {
					InputStream is = postMethod.getResponseBodyAsStream();
					SAXReader reader = new SAXReader();
					Document responseDoc = reader.read(is);
					postMethod.releaseConnection();
					Element rootElement = responseDoc.getRootElement();
					Element ns_exception = (Element) rootElement
							.selectSingleNode("//*[local-name()= 'cause']/*[local-name()= 'exception']");
					throw new DocRTException(
							"Failed to send request to DocServer: " + ns_exception.attributeValue("message"),
							new Exception());
				}
				throw new DocRTException("Failed to send request to DocServer: " + postMethod.getStatusLine(),
						new Exception());
			} else {
				InputStream is = postMethod.getResponseBodyAsStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(is));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = in.readLine()) != null) {
					buffer.append(line);
				}
				return buffer.toString();
			}
		} finally {
			postMethod.releaseConnection();
		}
	}

	public static Document excutePostAction(String url, InputStream param) throws Exception {
		registHttps(url);
		// System.out.println("test0:"+url);
		HttpClient httpClient = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		// System.out.println(getUrlAssign(url));
		PostMethod postMethod = new PostMethod(addBsessionId(getUrlAssign(url)));
		if (Utils.isNotNull(param)) {
			RequestEntity requestEntity = new InputStreamRequestEntity(param);
			postMethod.setRequestEntity(requestEntity);
			postMethod.setRequestHeader("Content-type", "text/xml");
		}
		try {
			int statusCode = httpClient.executeMethod(postMethod);
			if (statusCode != HttpStatus.SC_OK) {
				if (statusCode == 202) {
					// System.out.println("test1:"+postMethod.getResponseBodyAsString());
					InputStream is = postMethod.getResponseBodyAsStream();
					SAXReader reader = new SAXReader();
					Document responseDoc = reader.read(is);
					postMethod.releaseConnection();
					Element rootElement = responseDoc.getRootElement();
					Element ns_exception = (Element) rootElement
							.selectSingleNode("//*[local-name()= 'cause']/*[local-name()= 'exception']");
					throw new DocRTException(
							"Failed to send request to DocServer: " + ns_exception.attributeValue("message"),
							new Exception());
				}
				throw new DocRTException("Failed to send request to DocServer: " + postMethod.getStatusLine(),
						new Exception());
			} else {
				InputStream is = postMethod.getResponseBodyAsStream();
				// System.out.println(is.available());
				SAXReader reader = new SAXReader();
				if (Utils.isNotNull(is)) {
					Document responseDoc = reader.read(is);
					return responseDoc;
				}
			}
		} finally {
			postMethod.releaseConnection();
		}
		return null;
	}

	public static String getNodeText(Node node) {
		return node == null ? "" : node.getText();
	}

	protected static String getNodeText(Document document, String xpath) {
		Element elt = (Element) document.selectNodes(xpath).get(0);
		return elt.getText();
	}

	protected static String getNodeAttribute(Document document, String xpath) {
		Attribute attr = (Attribute) document.selectNodes(xpath).get(0);
		return attr.getValue();
	}

	public static String getValue(String obj, String defaultValue) {
		if (obj == null) {
			return defaultValue;
		} else {
			return obj;
		}
	}

	public static String convertExpression(String expression) {
		String result = "";
		if (Utils.isNotEmptyString(expression)) {
			// result = (String) Expression.evaluate(expression, new
			// HashMap<String, Object>(), model);
		}
		return result;
	}

	protected static String getJsonString(JSONObject json, String key) {
		return json.containsKey(key) ? json.getString(key) : "";
	}

	public static String getRootId(String docPath) {
		String[] pathList = docPath.split("/");
		if (pathList.length >= 1)
			return pathList[pathList.length - 1];
		else
			return null;
	}

	public static String getDocServerParam(List<Map<String, String>> items) {
		StringBuffer sb = new StringBuffer("<data>");
		for (Map<String, String> item : items) {
			sb.append("<item>");
			sb.append("<operation-type>" + getValue(item.get("operation-type"), "") + "</operation-type>");
			sb.append("<process/>");
			sb.append("<activity/>");
			sb.append("<person>" + getValue(item.get("person"), "") + "</person>");
			sb.append("<person-name>" + getValue(item.get("person-name"), "") + "</person-name>");
			sb.append("<dept-name/>");
			sb.append("<bill-id/>");
			sb.append("<doc-id>" + getValue(item.get("doc-id"), "") + "</doc-id>");
			sb.append("<version>" + getValue(item.get("version"), "") + "</version>");
			sb.append("<file-id/>");
			sb.append("<doc-version-id></doc-version-id>");
			sb.append("<doc-name><![CDATA[" + getValue(item.get("doc-name"), "") + "]]></doc-name>");
			sb.append("<kind>" + getValue(item.get("kind"), "") + "</kind>");
			sb.append("<size>" + getValue(item.get("size"), "") + "</size>");
			sb.append("<parent-id>" + getValue(item.get("parent-id"), "") + "</parent-id>");
			sb.append("<doc-path><![CDATA[" + getValue(item.get("doc-path"), "") + "]]></doc-path>");
			sb.append("<doc-display-path><![CDATA[" + getValue(item.get("doc-display-path"), "")
					+ "]]></doc-display-path>");
			sb.append("<description>" + getValue(item.get("description"), "") + "</description>");
			sb.append("<classification>" + getValue(item.get("classification"), "") + "</classification>");
			sb.append("<keywords><![CDATA[" + getValue(item.get("keywords"), "") + "]]></keywords>");
			sb.append("<finish-time/>");
			sb.append("<serial-number/>");
			sb.append("<doc-type>" + getValue(item.get("doc-type"), "") + "</doc-type>");
			sb.append("<cache-name>" + getValue(item.get("cache-name"), "") + "</cache-name>");
			sb.append(
					"<revision-cache-name>" + getValue(item.get("revision-cache-name"), "") + "</revision-cache-name>");
			sb.append("<comment-file-content/>");
			sb.append("<link-id/>");
			sb.append("<access-record-id/>");
			sb.append("</item>");

		}
		sb.append("</data>");
		return sb.toString();
	}

	protected static String getBsessionid() {
		return ActionUtils.getSessionID();
	}

	protected static String getSecureKey(String urlPattern) throws Exception {
		DesUtils des = new DesUtils();
		String key = des.encrypt(urlPattern + "," + new java.util.Date().getTime());
		return key;
	}

	protected static String getUrlAssign(String urlPattern) throws Exception {
		if (Utils.isEmptyString(urlPattern)) {
			return null;
		}
		String key = getSecureKey(urlPattern);

		if (urlPattern.contains("?")) {
			return urlPattern + "&key=" + key;
		} else {
			return urlPattern + "?key=" + key;
		}
	}

	protected static String addBsessionId(String url) throws Exception {
		if (Utils.isEmptyString(url)) {
			return null;
		}
		if (url.contains("?")) {
			return url + "&bsessionid=" + getBsessionid();
		} else {
			return url + "?bsessionid=" + getBsessionid();
		}
	}

	public static String getBizSecureParams() throws Exception {
		InetAddress localhost = InetAddress.getLocalHost();
		String ipStr = localhost.getHostAddress();
		DesUtils des = new DesUtils();
		String key = des.encrypt(ipStr);
		return "bizAddress=" + key;
	}

	public static List<String> getDepts() {
		List<String> depts = new ArrayList<String>();
		depts.add(ContextHelper.getPersonMember().getCurrentPersonFullID());
		return depts;
	}

}

/* 附件类型 */
enum AttachType {
	attachment, attachmentEditor, picture
};

enum OperaterType {
	newDoc, editDoc, deleteDoc, linkDoc
};
