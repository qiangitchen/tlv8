package com.tlv8.doc.clt.doc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class AbstractDoc {
	private String commentFileContent = "";
	protected Docinfo info;

	public AbstractDoc(Docinfo info) {
		this.info = info;
	}

	public AbstractDoc() {
	}

	protected String getHost(Boolean isHttps) {
		return DocDBHelper.queryDocHost();
	}

	public String getState() {
		return info.getState();
	}

	public void setState(String state) {
		info.setState(state);
	}

	public String getDocFullPath() {
		return getsDocPath() + "/" + getsID();
	}

	public void download(Boolean isHttps, OutputStream outputStream, String versionID, String partType)
			throws Exception {
		InputStream inputStream = null;
		versionID = Utils.isEmptyString(versionID) ? "last" : versionID;
		partType = Utils.isEmptyString(partType) ? "content" : partType;
		String url = getHost(isHttps) + "/repository/file/download/" + getsFileID() + "/" + versionID + "/" + partType
				+ "?" + DocUtils.getBizSecureParams();
		DocUtils.registHttps(url);
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		HttpMethod method = new GetMethod(url);
		try {
			client.executeMethod(method);
			inputStream = method.getResponseBodyAsStream();

			int bytesRead;
			byte[] buf = new byte[4 * 1024]; // 4K buffer
			while ((bytesRead = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, bytesRead);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();

		} catch (Exception e) {
			throw new DocRTException("Failed to send request to DocServer: " + method.getStatusLine(), new Exception());
		} finally {
			method.releaseConnection();
		}
	}

	public String getdownloadURL(Boolean isHttps, String versionID, String partType) throws Exception {
		versionID = Utils.isEmptyString(versionID) ? "last" : versionID;
		partType = Utils.isEmptyString(partType) ? "content" : partType;
		String url = getHost(isHttps) + "/repository/file/download/" + getsFileID() + "/" + versionID + "/" + partType
				+ "?" + DocUtils.getBizSecureParams();
		return url;
	}

	public InputStream download(Boolean isHttps, String versionID, String partType) throws Exception {
		InputStream inputStream = null;
		versionID = Utils.isEmptyString(versionID) ? "last" : versionID;
		partType = Utils.isEmptyString(partType) ? "content" : partType;
		String url = getHost(isHttps) + "/repository/file/download/" + getsFileID() + "/" + versionID + "/" + partType
				+ "?" + DocUtils.getBizSecureParams();
		DocUtils.registHttps(url);
		HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
		HttpMethod method = new GetMethod(url);
		try {
			client.executeMethod(method);
			inputStream = method.getResponseBodyAsStream();
		} catch (Exception e) {
			throw new DocRTException("Failed to send request to DocServer: " + method.getStatusLine(), new Exception());
		}
		return inputStream;
	}

	private void upload(String host, Part[] parts) throws Exception {
		String url = host + "/repository/file/cache/upload" + "?" + DocUtils.getBizSecureParams();
		DocUtils.registHttps(url);
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(DocUtils.addBsessionId(DocUtils.getUrlAssign(url)));
		MultipartRequestEntity multipartRequestEntity = new MultipartRequestEntity(parts, postMethod.getParams());
		postMethod.setRequestEntity(multipartRequestEntity);
		int statusCode = httpClient.executeMethod(postMethod);
		if (statusCode != HttpStatus.SC_OK) {
			throw new DocRTException("Failed to send request to DocServer: " + postMethod.getStatusLine());
		} else {
			InputStream is = postMethod.getResponseBodyAsStream();
			SAXReader reader = new SAXReader();
			Document responseDoc = null;
			try {
				responseDoc = reader.read(is);
				postMethod.releaseConnection();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			Element fe = responseDoc.getRootElement().element("file");
			setsKind(fe.attributeValue("mediatype"));
			setScacheName(fe.attributeValue("file-name"));
			if (fe.attributeValue("fileSize") == null)
				setsSize(0f);
			else
				setsSize(Float.valueOf(fe.attributeValue("fileSize")));
		}
	}

	private void upload(String host, InputStream inputStream) throws Exception {
		String sDocName = getsDocName();
		InputStreamPartSource bps = new InputStreamPartSource(inputStream, sDocName);
		Part[] parts = { new FilePart(sDocName, bps) };
		upload(host, parts);
	}

	private void upload(String host, File file) throws Exception {
		String sDocName = getsDocName();

		Part[] parts = { new FilePart(sDocName, file) };
		upload(host, parts);
	}

	public void upload(boolean isHttps, InputStream inputStream) throws Exception {
		upload(getHost(isHttps), inputStream);
	}

	public void upload(boolean isHttps, File file) throws Exception {
		upload(getHost(isHttps), file);
	}

	public String getsID() {
		String docID = info.getString("SA_DocNode");
		if (docID == null || "".equals(docID)) {
			docID = info.getString("SID");
		}
		return docID;
	}

	public void setsID(String sID) {
		info.setString("SA_DocNode", sID);
	}

	public String getBillID() {
		return info.getString("bill_id");
	}

	public void setBillID(String billID) {
		info.setString("bill_id", billID);
	}

	public String getsParentID() {
		return info.getString("sParentID");
	}

	public void setsParentID(String sParentID) {
		info.setString("sParentID", sParentID);
	}

	public String getsDocName() {
		return info.getString("sDocName");
	}

	public void setsDocName(String sDocName) {
		info.setString("sDocName", sDocName);
	}

	protected String getsSequence() {
		return info.getString("sSequence");
	}

	protected void setsSequence(String sSequence) {
		info.setString("sSequence", sSequence);
	}

	public Float getsSize() {
		return Utils.isNull(info.getFloatObject("sSize")) ? 0 : info.getFloat("sSize");
	}

	public void setsSize(Float sSize) {
		info.setFloatObject("sSize", sSize);
	}

	public String getsKind() {
		return info.getString("sKind");
	}

	public void setsKind(String sKind) {
		info.setString("sKind", sKind);
	}

	public String getsDocPath() {
		return info.getString("sDocPath");
	}

	public void setsDocPath(String sDocPath) {
		info.setString("sDocPath", sDocPath);
	}

	public String getsDocDisplayPath() {
		return info.getString("sDocDisplayPath");
	}

	public void setsDocDisplayPath(String sDocDisplayPath) {
		info.setString("sDocDisplayPath", sDocDisplayPath);
	}

	public String getsFileID() {
		return info.getString("sFileID");
	}

	public void setsFileID(String sFileID) {
		info.setString("sFileID", sFileID);
	}

	public Integer getsDocLiveVersionID() {
		return info.getInteger("sDocLiveVersionID");
	}

	public void setsDocLiveVersionID(int sDocLiveVersionID) {
		info.setInteger("sDocLiveVersionID", sDocLiveVersionID);
	}

	protected String getsNameSpace() {
		return info.getString("sNameSpace");
	}

	protected void setsNameSpace(String sNameSpace) {
		info.setString("sNameSpace", sNameSpace);
	}

	public int getVersion() {
		return info.getInt("version");
	}

	protected void setVersion(int version) {
		info.setInt("version", version);
	}

	protected int getsFlag() {
		return info.getInt("sFlag");
	}

	protected void setsFlag(int sFlag) {
		info.setInt("sFlag", sFlag);
	}

	public String getCacheName() {

		return info.getString("sCacheName");
	}

	public void setScacheName(String cacheName) {
		info.setString("sCacheName", cacheName);
	}

	public String getRevisionCacheName() {
		return info.getString("sRevisionCacheName");

	}

	public void setSrevisionCacheName(String revisionCacheName) {
		info.setString("sRevisionCacheName", revisionCacheName);
	}

	public String getCommentFileContent() {
		return commentFileContent;
	}

	public void setCommentFileContent(String commentFileContent) {
		this.commentFileContent = commentFileContent;
	}

	public void setSeditorFID(String value) {
		info.setString("sEditorFID", value);
	}

	public void setSeditorName(String value) {
		info.setString("sEditorName", value);
	}

	public void setSeditorDeptName(String value) {
		info.setString("sEditorDeptName", value);
	}

	public String getRelation(String relation) {
		return info.getString(relation);

	}

	public void setRelation(String relation, String value) {
		info.setString(relation, value);
	}

	public Docinfo getRow() {
		return info;
	}

	private class InputStreamPartSource implements PartSource {
		private InputStream in = null;
		private String fileName = null;

		public InputStreamPartSource(InputStream in, String fileName) {
			this.in = in;
			this.fileName = fileName;
		}

		public InputStream createInputStream() throws IOException {
			return in;
		}

		public String getFileName() {
			return fileName;
		}

		public long getLength() {
			try {
				return this.in.available();
			} catch (IOException e) {
				e.printStackTrace();
				return -1;
			}
		}
	}

	public String getScacheName() {
		return info.getString("sCacheName");
	}

	@SuppressWarnings({ "rawtypes" })
	public void commitFile() throws UnsupportedEncodingException, DocumentException, Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<data>");
		sb.append(createChangeLogItem());
		sb.append("</data>");
		String host = DocDBHelper.queryDocHost();
		String url = host + "/repository/file/cache/commit";
		Document result = DocUtils.excutePostAction(url, new ByteArrayInputStream(sb.toString().getBytes("UTF-8")));
		List itemList = result.selectNodes("//item");
		for (Iterator localIterator2 = itemList.iterator(); localIterator2.hasNext();) {
			Object litem = localIterator2.next();
			Element item = (Element) litem;
			String docID = item.selectSingleNode("doc-id").getText();
			String fileID = item.selectSingleNode("file-id").getText();
			String docVersionID = item.selectSingleNode("doc-version-id").getText();
			this.setsID(docID);
			this.setsFileID(fileID);
			this.setSrevisionCacheName(docVersionID);
		}
	}

	public boolean deleteFile() {
		StringBuffer sb = new StringBuffer();
		sb.append("<data>");
		sb.append(createDeleteLogItem());
		sb.append("</data>");
		String host = DocDBHelper.queryDocHost();
		String url = host + "/repository/file/cache/commit";
		try {
			DocUtils.excutePostAction(url, new ByteArrayInputStream(sb.toString().getBytes("UTF-8")));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public StringBuffer createChangeLogItem() {
		StringBuffer result = new StringBuffer();
		result.append("<item>");
		result.append("<operation-type>new</operation-type>");
		result.append("<process></process>");
		result.append("<activity></activity>");
		result.append("<person>" + this.getRelation("sLastWriterFID") + "</person>");
		result.append("<person-name>" + this.getRelation("sLastWriterName") + "</person-name>");
		result.append("<dept-name>" + DocUtils.getValue(this.getRelation("sLastWriterDeptName"), "") + "</dept-name>");
		result.append("<bill-id></bill-id>");
		result.append("<doc-id>" + this.getsID() + "</doc-id>");
		result.append("<version>" + String.valueOf(this.getVersion()) + "</version>");
		result.append("<file-id>" + DocUtils.getValue(this.getsFileID(), "") + "</file-id>");
		result.append("<doc-version-id>" + String.valueOf(this.getsDocLiveVersionID()) + "</doc-version-id>");
		result.append("<doc-name><![CDATA[" + this.getsDocName() + "]]></doc-name>");
		result.append("<kind>" + this.getsKind() + "</kind>");
		result.append("<size>" + String.valueOf(this.getsSize()) + "</size>");
		result.append("<parent-id>" + this.getsParentID() + "</parent-id>");
		result.append("<doc-path>/root</doc-path>");
		result.append("<doc-display-path>/文档中心</doc-display-path>");
		result.append("<description>" + DocUtils.getValue(this.getRelation("sDescription"), "") + "</description>");
		result.append(
				"<classification>" + DocUtils.getValue(this.getRelation("sClassification"), "") + "</classification>");
		result.append("<keywords><![CDATA[" + DocUtils.getValue(this.getRelation("sKeywords"), "") + "]]></keywords>");
		result.append("<finish-time></finish-time>");
		result.append("<serial-number></serial-number>");
		result.append("<doc-type>document</doc-type>");
		result.append("<cache-name>" + this.getCacheName() + "</cache-name>");
		result.append("<revision-cache-name>" + this.getRevisionCacheName() + "</revision-cache-name>");
		result.append("<comment-file-content><![CDATA[" + this.getCommentFileContent() + "]]></comment-file-content>");
		result.append("<link-id></link-id>");
		result.append("<access-record-id></access-record-id>");
		result.append("</item>");
		return result;
	}

	public StringBuffer createDeleteLogItem() {
		StringBuffer result = new StringBuffer();
		result.append("<item>");
		result.append("<operation-type>delete</operation-type>");
		result.append("<process></process>");
		result.append("<activity></activity>");
		result.append("<person>" + this.getRelation("sLastWriterFID") + "</person>");
		result.append("<person-name>" + this.getRelation("sLastWriterName") + "</person-name>");
		result.append("<dept-name>" + DocUtils.getValue(this.getRelation("sLastWriterDeptName"), "") + "</dept-name>");
		result.append("<bill-id></bill-id>");
		result.append("<doc-id>" + this.getsID() + "</doc-id>");
		result.append("<version>" + String.valueOf(this.getVersion()) + "</version>");
		result.append("<file-id>" + DocUtils.getValue(this.getsFileID(), "") + "</file-id>");
		result.append("<doc-version-id>" + String.valueOf(this.getsDocLiveVersionID()) + "</doc-version-id>");
		result.append("<doc-name><![CDATA[" + this.getsDocName() + "]]></doc-name>");
		result.append("<kind>" + this.getsKind() + "</kind>");
		result.append("<size>" + String.valueOf(this.getsSize()) + "</size>");
		result.append("<parent-id>" + this.getsParentID() + "</parent-id>");
		result.append("<doc-path>/root</doc-path>");
		result.append("<doc-display-path>/文档中心</doc-display-path>");
		result.append("<description>" + DocUtils.getValue(this.getRelation("sDescription"), "") + "</description>");
		result.append(
				"<classification>" + DocUtils.getValue(this.getRelation("sClassification"), "") + "</classification>");
		result.append("<keywords><![CDATA[" + DocUtils.getValue(this.getRelation("sKeywords"), "") + "]]></keywords>");
		result.append("<finish-time></finish-time>");
		result.append("<serial-number></serial-number>");
		result.append("<doc-type>document</doc-type>");
		result.append("<cache-name>" + this.getCacheName() + "</cache-name>");
		result.append("<revision-cache-name>" + this.getRevisionCacheName() + "</revision-cache-name>");
		result.append("<comment-file-content><![CDATA[" + this.getCommentFileContent() + "]]></comment-file-content>");
		result.append("<link-id></link-id>");
		result.append("<access-record-id></access-record-id>");
		result.append("</item>");
		return result;
	}

}
