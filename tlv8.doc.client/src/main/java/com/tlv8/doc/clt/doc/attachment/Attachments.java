package com.tlv8.doc.clt.doc.attachment;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.doc.clt.doc.CommonUtils;
import com.tlv8.doc.clt.doc.Doc;
import com.tlv8.doc.clt.doc.DocDBHelper;
import com.tlv8.doc.clt.doc.DocLogs;
import com.tlv8.doc.clt.doc.DocRTException;
import com.tlv8.doc.clt.doc.DocServerDefine;
import com.tlv8.doc.clt.doc.DocServerDefines;
import com.tlv8.doc.clt.doc.DocUtils;
import com.tlv8.doc.clt.doc.Docinfo;
import com.tlv8.doc.clt.doc.ModifyState;
import com.tlv8.doc.clt.doc.Utils;
import com.tlv8.doc.clt.doc.transform.TransformConfig;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.utils.ContextUtils;

@SuppressWarnings("rawtypes")
public class Attachments {
	public static Logger logger = LoggerFactory.getLogger(Attachments.class);

	private String sfield;
	private Boolean isHttps = false;
	private List container;
	private Map<String, Attachment> atts = new ConcurrentHashMap<String, Attachment>();
	private DefineItems defineItems;
	private DocServerDefines dns;
	private Map<String, String> cacheNames = new HashMap<String, String>();

	public Map<String, String> getCacheNames() {
		return cacheNames;
	}

	public Attachments(String process, String activity, String jsonStr, Boolean isLoad, DefineItems defineItems,
			Boolean isHttps) {
		if (Utils.isNotNull(isHttps))
			this.isHttps = isHttps;

		this.sfield = jsonStr;
		this.dns = DocServerDefines.getInstance();
		if (Utils.isNotNull(defineItems))
			this.defineItems = defineItems;
		if (isLoad)
			loadItems();
	}

	private JSONArray fieldToJson() {
		JSONArray json = null;
		try {
			json = JSON.parseArray(sfield);
		} catch (Exception e) {
			logger.error("json字符串转换失败", e);
		}
		return json;
	}

	private String getFieldsql() {
		JSONArray json = fieldToJson();
		String sqlIn = "";
		int l = json.size() - 1;
		for (int i = 0; i <= l; i++) {
			try {
				JSONObject item = json.getJSONObject(i);
				sqlIn += i == l ? "'" + item.getString("docID") + "'" : "'" + item.getString("docID") + "',";
			} catch (Exception e) {
				logger.error("json转换失败", e);
			}
		}
		return "SA_DocNode in (" + sqlIn + ")";
	}

	private void queryItems() {
		container = DocDBHelper.queryDoc(null, null, null, null, getFieldsql());
		for (int i = 0; i < container.size(); i++) {
			Docinfo r = new Docinfo((Map) container.get(i));
			atts.put(r.getString("SA_DocNode"), new Attachment(r, this));
		}
	}

	public Attachments loadItems() {
		// if (!isLoad) {
		queryItems();
		// isLoad = true;
		// }
		return this;
	}

	public void refresh() {
		queryItems();
	}

	public Attachment addAttachment(Attachment att, String billID) {
		if (Utils.isNull(container)) {
			container = DocDBHelper.initDocNode();
		}
		Docinfo r = new Docinfo((Map) container.get(0));
		r.setString("sParentID", att.getsParentID());
		r.setString("sDocName", att.getsDocName());
		r.setString("sKind", att.getsKind());
		r.setFloat("sSize", att.getsSize());
		r.setString("sDocPath", att.getsDocPath());
		r.setString("sDocDisplayPath", att.getsDocDisplayPath());

		ContextBean ContextHelper = ContextUtils.getContext();
		String currentPersionFID = ContextHelper.getCurrentPersonFullID();
		String currentPersionFName = ContextHelper.getCurrentPersonFullName();
		Timestamp currentTime = new Timestamp(new Date().getTime());

		r.setString("sCreatorFID", currentPersionFID);
		r.setString("sCreatorName", currentPersionFName);
		r.setDateTime("sCreateTime", currentTime);
		r.setString("sLastWriterFID", currentPersionFID);
		r.setString("sLastWriterName", currentPersionFName);
		r.setDateTime("sLastWriteTime", currentTime);
		r.setInt("version", att.getVersion());
		r.setInt("sFlag", 1);
		r.setString("sRevisionCacheName", att.getRevisionCacheName());
		r.setString("sCacheName", att.getCacheName());
		r.setString("sFileID", att.getsFileID());
		r.setString("bill_id", billID);
		Attachment attment = new Attachment(r, this);
		atts.put(att.getsID(), attment);
		return att;

	}

	public Attachment addDoc(String sID, String sDocName, String sKind, Doc dirDoc, String billID) {
		if (Utils.isNull(container)) {
			container = DocDBHelper.initDocNode();
		}

		Doc ParentDoc = dirDoc;// Utils.isNull(defineItems) ? dirDoc :
		// defineItems.getUploadDir();
		Docinfo r = new Docinfo((Map) container.get(0));
		if (Utils.isEmptyString(sID))
			sID = CommonUtils.createGUID();
		r.setString("SA_DocNode", sID);
		r.setString("sParentID", ParentDoc.getsID());
		r.setString("sDocName", sDocName);
		r.setString("sKind", sKind);
		r.setFloat("sSize", 0);
		r.setString("sDocPath", ParentDoc.getsDocPath() + "/" + ParentDoc.getsID());
		r.setString("sDocDisplayPath", ParentDoc.getsDocDisplayPath() + "/" + ParentDoc.getsDocName());

		ContextBean ContextHelper = ContextUtils.getContext();
		String currentPersionFID = ContextHelper.getCurrentPersonFullID();
		String currentPersionFName = ContextHelper.getCurrentPersonFullName();
		Timestamp currentTime = new Timestamp(new Date().getTime());

		r.setString("sCreatorFID", currentPersionFID);
		r.setString("sCreatorName", currentPersionFName);
		r.setDateTime("sCreateTime", currentTime);
		r.setString("sLastWriterFID", currentPersionFID);
		r.setString("sLastWriterName", currentPersionFName);
		r.setDateTime("sLastWriteTime", currentTime);
		r.setInt("version", 0);
		r.setInt("sFlag", 1);

		r.setString("bill_id", billID);
		Attachment att = new Attachment(r, this);
		atts.put(sID, att);
		return att;
	}

	public Attachment addDoc(String sID, String sParentID, String sDocName, String sKind, String sDocPath,
			String sDocDisplayPath, String billID) {
		if (Utils.isNull(container)) {
			container = DocDBHelper.initDocNode();
		}
		Docinfo r = new Docinfo((Map) container.get(0));

		if (Utils.isEmptyString(sID))
			sID = CommonUtils.createGUID();
		r.setString("SA_DocNode", sID);
		r.setString("sParentID", sParentID);
		r.setString("sDocName", sDocName);
		r.setString("sKind", sKind);
		r.setFloat("sSize", 0);
		r.setString("sDocPath", sDocPath);
		r.setString("sDocDisplayPath", sDocDisplayPath);

		ContextBean ContextHelper = ContextUtils.getContext();
		String currentPersionFID = ContextHelper.getCurrentPersonFullID();
		String currentPersionFName = ContextHelper.getCurrentPersonFullName();
		Timestamp currentTime = new Timestamp(new Date().getTime());

		r.setString("sCreatorFID", currentPersionFID);
		r.setString("sCreatorName", currentPersionFName);
		r.setDateTime("sCreateTime", currentTime);
		r.setString("sLastWriterFID", currentPersionFID);
		r.setString("sLastWriterName", currentPersionFName);
		r.setDateTime("sLastWriteTime", currentTime);
		r.setInt("version", 0);
		r.setInt("sFlag", 1);
		r.setString("sRevisionCacheName", "");
		r.setString("sFileID", "");
		r.setString("bill_id", billID);
		Attachment att = new Attachment(r, this);
		atts.put(sID, att);
		return att;
	}

	public int saveTable() throws Exception {
		checkAceess();
		if (atts.size() == 0) {
			return 0;
		}
		StringBuffer result = new StringBuffer();
		result.append("<data>");

		DocLogs dl = new DocLogs();
		for (Attachment att : atts.values()) {
			if ("dir".equals(att.getsKind()))
				continue;
			String accessType = getOperationType(att);
			dl.addLog(null, accessType, att.getsID(), att.getsDocName(), att.getsDocLiveVersionID(), att.getsSize());
		}
		dl.save();
		saveDeFineItems();
		return 1;
	}

	private void saveDeFineItems() {
		if (Utils.isNotNull(defineItems))
			defineItems.saveSubPath();
	}

	private void checkAceess() {
		if (Utils.isNull(defineItems))
			return;
		for (Attachment att : atts.values()) {
			int access = defineItems.getAccess();
			String state = att.getState();
			if (state.equals(ModifyState.NEW) && !((access % 512) >= 256)) {
				logger.error("没有新建文件夹的权限");
				DocRTException e = new DocRTException("没有新建文件夹的权限");
				throw e;

			}
			if (state.equals(ModifyState.EDIT) && !((access % 1024) >= 512)) {
				logger.error("没有编辑文件夹的权限");
				DocRTException e = new DocRTException("没有编辑文件夹的权限");
				throw e;

			}
			if (state.equals(state == ModifyState.DELETE) && !((access % 2048) >= 1024)) {
				logger.error("没有删除文件夹的权限");
				DocRTException e = new DocRTException("没有删除文件夹的权限");
				throw e;
			}
		}
	}

	public int createVersion() throws UnsupportedEncodingException, DocumentException, Exception {
		for (Entry<String, Attachment> entry : atts.entrySet()) {
			Attachment up = entry.getValue();
			if (DocDBHelper.checkLocker(up.getsID()) != 1 && (up.getRow().getState().equals(ModifyState.EDIT)
					|| up.getRow().getState().equals(ModifyState.NONE))) {
				remove(entry.getValue());
			} else if (Utils.isEmptyString(up.getCacheName())) {
				remove(entry.getValue());
			}
		}
		commitFile();
		int result = commitData();
		commitFlag();
		return result;
	}

	public void commitFile() throws UnsupportedEncodingException, DocumentException, Exception {
		Map<String, List<StringBuffer>> logs = createChangeLogs();
		for (Iterator<Entry<String, List<StringBuffer>>> it = logs.entrySet().iterator(); it.hasNext();) {
			Entry<String, List<StringBuffer>> entry = (Entry<String, List<StringBuffer>>) it.next();
			StringBuffer sb = new StringBuffer();
			sb.append("<data>");
			for (StringBuffer log : entry.getValue()) {
				sb.append(log);
			}
			sb.append("</data>");

			DocServerDefine ds = dns.getDocServerByPath("/" + entry.getKey());
			String host = ds.getsUrl();
			String url = host + "/repository/file/cache/commit";

			Document result = DocUtils.excutePostAction(url, new ByteArrayInputStream(sb.toString().getBytes("UTF-8")));
			List<?> itemList = result.selectNodes("//item");
			for (Object litem : itemList) {
				Element item = (Element) litem;
				String docID = item.selectSingleNode("doc-id").getText();
				String fileID = item.selectSingleNode("file-id").getText();
				String docVersionID = item.selectSingleNode("doc-version-id").getText();
				setAfterCommit(docID, fileID, docVersionID);
			}
			if (!"true".equals(result.selectSingleNode("//flag").getText()))
				throw new DocRTException("DocServer commit error ", new Exception());
		}
	}

	public int commitData() throws UnsupportedEncodingException, DocumentException, Exception {
		for (Entry<String, Attachment> entry : atts.entrySet()) {
			Attachment att = entry.getValue();
			if (att.getState().equals(ModifyState.NONE)) {
				att.setState(ModifyState.EDIT.toString());
			}
			if (!att.getState().equals(ModifyState.DELETE)) {
				cacheNames.put(att.getsID(), att.getCacheName());
			}
			att.setScacheName("");
			att.setSrevisionCacheName("");
			att.setSeditorDeptName("");
			att.setSeditorFID("");
			att.setSeditorName("");
		}
		return saveTable();
	}

	public void commitFlag() throws Exception {
		for (Entry<String, Attachment> entry : atts.entrySet()) {
			Docinfo row = entry.getValue().getRow();
			if (row.getState().equals(ModifyState.DELETE)) {
				continue;
			}

			String cacheName = cacheNames.get(entry.getValue().getsID());
			if (Utils.isNotEmptyString(cacheName)) {
				DocUtils.saveDocFlag(row.getString("sDocPath"), row.getString("sKind"), row.getString("sFileID"),
						cacheName, isHttps);
			}
		}
	}

	public int save() throws Exception {
		return saveTable();
	}

	private Map<String, List<StringBuffer>> createChangeLogs() throws DocumentException {
		Map<String, List<StringBuffer>> serverLogs = new HashMap<String, List<StringBuffer>>();
		for (Attachment att : atts.values()) {
			/* 删除操作不提交文档服务器 */
			if (att.getState() == ModifyState.DELETE)
				continue;
			String rootPath = DocUtils.getRootId(att.getsDocPath());
			StringBuffer sb = new StringBuffer();
			sb.append(createChangeLogItem(att));
			if (serverLogs.containsKey(rootPath)) {
				serverLogs.get(rootPath).add(sb);
			} else {
				List<StringBuffer> al = new ArrayList<StringBuffer>();
				al.add(sb);
				serverLogs.put(rootPath, al);
			}

		}
		return serverLogs;
	}

	private void setAfterCommit(String docID, String fileID, String docVersionID) {
		Attachment att = atts.get(docID);
		att.setsFileID(fileID);
		att.setsDocLiveVersionID(Integer.valueOf(docVersionID));
	}

	private StringBuffer createChangeLogItem(Attachment doc) {
		StringBuffer result = new StringBuffer();
		/* 判断是否是文件夹 */
		String operationType = getOperationType(doc);
		result.append("<item>");
		result.append("<operation-type>" + operationType + "</operation-type>");
		result.append("<process></process>");
		result.append("<activity></activity>");
		result.append("<person>" + doc.getRelation("sLastWriterFID") + "</person>");
		result.append("<person-name>" + doc.getRelation("sLastWriterName") + "</person-name>");
		result.append("<dept-name>" + DocUtils.getValue(doc.getRelation("sLastWriterDeptName"), "") + "</dept-name>");
		result.append("<bill-id></bill-id>");
		result.append("<doc-id>" + doc.getsID() + "</doc-id>");
		result.append("<version>" + String.valueOf(doc.getVersion()) + "</version>");
		result.append("<file-id>" + DocUtils.getValue(doc.getsFileID(), "") + "</file-id>");
		result.append("<doc-version-id>" + String.valueOf(doc.getsDocLiveVersionID()) + "</doc-version-id>");
		result.append("<doc-name><![CDATA[" + doc.getsDocName() + "]]></doc-name>");
		result.append("<kind>" + doc.getsKind() + "</kind>");
		result.append("<size>" + String.valueOf(doc.getsSize()) + "</size>");
		result.append("<parent-id>" + doc.getsParentID() + "</parent-id>");
		result.append("<doc-path><![CDATA[" + doc.getsDocPath() + "]]></doc-path>");
		result.append("<doc-display-path><![CDATA[" + doc.getsDocDisplayPath() + "]]></doc-display-path>");
		result.append("<description>" + DocUtils.getValue(doc.getRelation("sDescription"), "") + "</description>");
		result.append(
				"<classification>" + DocUtils.getValue(doc.getRelation("sClassification"), "") + "</classification>");
		result.append("<keywords><![CDATA[" + DocUtils.getValue(doc.getRelation("sKeywords"), "") + "]]></keywords>");
		result.append("<finish-time></finish-time>");
		result.append("<serial-number></serial-number>");
		result.append("<doc-type>document</doc-type>");
		result.append("<cache-name>" + doc.getCacheName() + "</cache-name>");
		result.append("<revision-cache-name>" + doc.getRevisionCacheName() + "</revision-cache-name>");
		result.append("<comment-file-content><![CDATA[" + doc.getCommentFileContent() + "]]></comment-file-content>");
		result.append("<link-id></link-id>");
		result.append("<access-record-id></access-record-id>");
		result.append("</item>");
		return result;
	}

	public DefineItems getDefineItems() {
		return defineItems;
	}

	public Attachments remove(String sID) {
		// container.setRecordState(false);
		DocDBHelper.deleteRows(sID);
		// container.setRecordState(true);
		atts.remove(sID);
		return this;
	}

	public Attachments remove(Attachment att) {
		String id = att.getsID();
		remove(id);
		return this;
	}

	private String getOperationType(Attachment uploadDoc) {
		String operationType = "";
		String state = uploadDoc.getState();
		if (state == ModifyState.NEW) {
			operationType = "dir".equals(uploadDoc.getsKind()) ? "newDir" : "new";
		} else if (state == ModifyState.EDIT) {
			operationType = "edit";
		} else if (state == ModifyState.DELETE) {
			operationType = "delete";
		} else {
			operationType = "edit";
		}
		return operationType;
	}

	public int size() {
		return atts.entrySet().size();
	}

	public Attachment get(String sID) {
		return atts.get(sID);
	}

	public Attachment get(int index) {
		int i = 0;
		for (Attachment att : atts.values()) {
			if (i == index) {
				return att;
			}
			i++;
		}
		return null;
	}

	public Iterator<Attachment> getIterator() {
		return atts.values().iterator();
	}

	public String tojson() {
		String result = "[";
		for (Attachment att : atts.values()) {
			result += att.toJsonString() + ",";
		}
		return result.substring(0, result.lastIndexOf(",")) + "]";
	}

	public List toTable() {
		return this.container;
	}

	public void readerFromJson(Object arg0, TransformConfig arg1) {
		// TODO Auto-generated method stub

	}

	public void reader(Element arg0, TransformConfig arg1) {
		// TODO Auto-generated method stub

	}

}
