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

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.tlv8.doc.clt.doc.CommonUtils;
import com.tlv8.doc.clt.doc.DocDBHelper;
import com.tlv8.doc.clt.doc.DocLogs;
import com.tlv8.doc.clt.doc.DocRTException;
import com.tlv8.doc.clt.doc.DocUtils;
import com.tlv8.doc.clt.doc.Docinfo;
import com.tlv8.doc.clt.doc.Docs;
import com.tlv8.doc.clt.doc.ModelException;
import com.tlv8.doc.clt.doc.ModifyState;
import com.tlv8.doc.clt.doc.Utils;
import com.tlv8.doc.clt.doc.transform.TransformConfig;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.controller.UserController;

@SuppressWarnings("rawtypes")
public class DocLinkAttachments {
	private String billIDs;
	private String process;
	private String activity;
	private Boolean isLoad;
	private Boolean isHttps = false;
	private List container;
	private Map<String, Attachment> atts = new ConcurrentHashMap<String, Attachment>();
	public static Logger logger = Logger.getLogger(Docs.class);
	private Map<String, String> cacheNames = new HashMap<String, String>();

	public DocLinkAttachments(String billIDs, String process, String activity,
			Boolean isLoad, Boolean isHttps) {
		if (Utils.isNotNull(isHttps))
			this.isHttps = isHttps;
		this.billIDs = billIDs;
		this.process = process;
		this.activity = activity;
		if (Utils.isNotNull(isLoad))
			this.isLoad = isLoad;
		if (isLoad) {
			queryItems();
		}
	}

	public void setBillIDs(String billIDs) {
		this.billIDs = billIDs;
	}

	public Attachment get(String sID) {
		return atts.get(sID);
	}

	private void queryItems() {
		container = DocDBHelper.queryLinkDoc(billIDs);
		for (int i = 0; i < container.size(); i++) {
			Docinfo r = new Docinfo((Map) container.get(i));
			atts.put(r.getString("SA_DocNode"), new Attachment(r, this));
		}
	}

	public DocLinkAttachments loadItems() {
		if (!isLoad) {
			queryItems();
			isLoad = true;
		}
		return this;
	}

	public void refresh() {
		queryItems();
	}

	public Attachment addDoc(String sID, String sParentID, String sDocName,
			String sKind, String sDocPath, String sDocDisplayPath, String billID)
			throws ModelException {
		if (Utils.isNull(container)) {
			container = DocDBHelper.initDocNode();
			if (Utils.isEmptyString(billIDs)) {
				billIDs = billID;
			} else {
				Utils.check(billIDs.equals(billID), billIDs + "和" + billID
						+ "不是同一批的附件，不能统一保存");
			}
		}
		Docinfo r = new Docinfo((Map<?, ?>) container.get(0));
		if (Utils.isEmptyString(sID))
			sID = CommonUtils.createGUID();
		r.setString("SA_DocNode", sID);
		r.setString("sParentID", sParentID);
		r.setString("sDocName", sDocName);
		r.setString("sKind", sKind);
		r.setFloat("sSize", 0);
		r.setString("sDocPath", sDocPath);
		r.setString("sDocDisplayPath", sDocDisplayPath);

		ContextBean ContextHelper = new UserController().getContext();
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

	public int createVersion() throws UnsupportedEncodingException,
			DocumentException, Exception {
		for (Entry<String, Attachment> entry : atts.entrySet()) {
			Attachment up = entry.getValue();
			if (DocDBHelper.checkLocker(up.getsID()) != 1
					&& (up.getRow().getState().equals(ModifyState.EDIT) || up
							.getRow().getState().equals(ModifyState.NONE))) {
				deleteAtts(entry.getValue());
			} else if (Utils.isEmptyString(up.getCacheName())
					&& (up.getRow().getState().equals(ModifyState.EDIT) || up
							.getRow().getState().equals(ModifyState.NONE))) {
				deleteAtts(entry.getValue());
			}
		}
		int result = save();
		for (Entry<String, Attachment> entry : atts.entrySet()) {
			Attachment up = entry.getValue();
			if (Utils.isEmptyString(up.getCacheName())
					&& up.getRow().getState().equals(ModifyState.NEW)) {
				deleteAtts(entry.getValue());
			}
		}
		commitFlag();
		return result;
	}

	public void commitFlag() throws Exception {
		for (Entry<String, Attachment> entry : atts.entrySet()) {
			Docinfo row = entry.getValue().getRow();
			String cacheName = cacheNames.get(row.getString("SA_DocNode"));
			if (row.getState().equals(ModifyState.DELETE)
					|| Utils.isNotEmptyString(cacheName)) {
				continue;
			}
			DocUtils.saveDocFlag(row.getString("sDocPath"),
					row.getString("sKind"), row.getString("sFileID"),
					cacheName, isHttps);
		}
	}

	public void commitFile() throws UnsupportedEncodingException,
			DocumentException, Exception {
		Map<String, List<StringBuffer>> logs = createChangeLogs();
		for (Iterator<Entry<String, List<StringBuffer>>> it = logs.entrySet()
				.iterator(); it.hasNext();) {
			Entry<String, List<StringBuffer>> entry = (Entry<String, List<StringBuffer>>) it
					.next();
			StringBuffer sb = new StringBuffer();
			sb.append("<data>");
			for (StringBuffer log : entry.getValue()) {
				sb.append(log);
			}
			sb.append("</data>");

			String host = DocDBHelper.queryDocHost();
			String url = host + "/repository/file/cache/commit";

			Document result = DocUtils.excutePostAction(url,
					new ByteArrayInputStream(sb.toString().getBytes("UTF-8")));
			List<?> itemList = result.selectNodes("//item");
			for (Object litem : itemList) {
				Element item = (Element) litem;
				String docID = item.selectSingleNode("doc-id").getText();
				String fileID = item.selectSingleNode("file-id").getText();
				String docVersionID = item.selectSingleNode("doc-version-id")
						.getText();
				setAfterCommit(docID, fileID, docVersionID);
			}
			if (!"true".equals(result.selectSingleNode("//flag").getText()))
				throw new DocRTException("DocServer commit error ",
						new Exception());
		}
	}

	public int commitData() throws Exception {
		for (Entry<String, Attachment> entry : atts.entrySet()) {
			Attachment att = entry.getValue();
			if (att.getRow().getState().equals(ModifyState.NONE)) {
				att.getRow().setState(ModifyState.EDIT);
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

	private int save() throws UnsupportedEncodingException, DocumentException,
			Exception {
		commitFile();
		int result = commitData();
		return result;
	}

	public int saveTable() throws Exception {
		StringBuffer result = new StringBuffer();
		result.append("<data>");

		DocLogs dl = new DocLogs();
		List<?> link = DocDBHelper.queryLink(billIDs);

		for (Attachment att : atts.values()) {
			if ("dir".equals(att.getsKind()))
				continue;
			String accessType = getOperationType(att);
			dl.addLog(null, accessType, att.getsID(), att.getsDocName(),
					att.getsDocLiveVersionID(), att.getsSize());
			if ("new".equals(accessType)) {
				Map m = new HashMap();
				Docinfo r = new Docinfo(m);
				r.setString("SA_DocLink", CommonUtils.createGUID());
				r.setString("sOwnerID", att.getBillID());
				r.setString("sProcess", process);
				r.setString("sActivity", activity);
				r.setString("sDocID", att.getsID());
				r.setInt("version", 0);
			}
			if ("delete".equals(accessType)) {
				for (int i = 0; i < link.size(); i++) {
					Docinfo r = new Docinfo((Map) link.get(i));
					if (att.getsID().equals(r.getString("sDocID")))
						r.setState(ModifyState.DELETE);
				}
				/* 不删除文档中心的数据 */
				att.setState(ModifyState.NONE.toString());
			}
		}

		if (dl.size() > 0) {
			dl.save();
		}

		return 0;
	}

	private Map<String, List<StringBuffer>> createChangeLogs()
			throws DocumentException {
		Map<String, List<StringBuffer>> serverLogs = new HashMap<String, List<StringBuffer>>();
		for (Attachment att : atts.values()) {
			/* 删除操作不提交文档服务器 */
			if (att.getState().equals(ModifyState.DELETE))
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
		result.append("<person>" + doc.getRelation("sLastWriterFID")
				+ "</person>");
		result.append("<person-name>" + doc.getRelation("sLastWriterName")
				+ "</person-name>");
		result.append("<dept-name>"
				+ DocUtils.getValue(doc.getRelation("sLastWriterDeptName"), "")
				+ "</dept-name>");
		result.append("<bill-id></bill-id>");
		result.append("<doc-id>" + doc.getsID() + "</doc-id>");
		result.append("<version>" + String.valueOf(doc.getVersion())
				+ "</version>");
		result.append("<file-id>" + DocUtils.getValue(doc.getsFileID(), "")
				+ "</file-id>");
		result.append("<doc-version-id>"
				+ String.valueOf(doc.getsDocLiveVersionID())
				+ "</doc-version-id>");
		result.append("<doc-name><![CDATA[" + doc.getsDocName() + "]]></doc-name>");
		result.append("<kind>" + doc.getsKind() + "</kind>");
		result.append("<size>" + String.valueOf(doc.getsSize()) + "</size>");
		result.append("<parent-id>" + doc.getsParentID() + "</parent-id>");
		result.append("<doc-path><![CDATA[" + doc.getsDocPath() + "]]></doc-path>");
		result.append("<doc-display-path><![CDATA[" + doc.getsDocDisplayPath() + "]]></doc-display-path>");
		result.append("<description>"
				+ DocUtils.getValue(doc.getRelation("sDescription"), "")
				+ "</description>");
		result.append("<classification>"
				+ DocUtils.getValue(doc.getRelation("sClassification"), "")
				+ "</classification>");
		result.append("<keywords><![CDATA[" + DocUtils.getValue(doc.getRelation("sKeywords"), "") + "]]></keywords>");
		result.append("<finish-time></finish-time>");
		result.append("<serial-number></serial-number>");
		result.append("<doc-type>document</doc-type>");
		result.append("<cache-name>" + doc.getCacheName() + "</cache-name>");
		result.append("<revision-cache-name>" + doc.getRevisionCacheName()
				+ "</revision-cache-name>");
		result.append("<comment-file-content></comment-file-content>");
		result.append("<link-id></link-id>");
		result.append("<access-record-id></access-record-id>");
		result.append("</item>");
		return result;
	}

	public DocLinkAttachments deleteAtts(String sID) {
		// container.setRecordState(false);
		// container.deleteRows(sID);
		// container.setRecordState(true);
		atts.remove(sID);
		return this;
	}

	public DocLinkAttachments deleteAtts(Attachment att) {
		String id = att.getsID();
		deleteAtts(id);
		return this;
	}

	private String getOperationType(Attachment uploadDoc) {
		String operationType = "";
		String state = uploadDoc.getState();
		if (state == ModifyState.NEW) {
			operationType = "dir".equals(uploadDoc.getsKind()) ? "newDir"
					: "new";
		} else if (state == ModifyState.EDIT) {
			operationType = "edit";
		} else if (state == ModifyState.DELETE) {
			operationType = "delete";
		}
		return operationType;
	}

	public List toTable() {
		return this.container;
	}

	public void readerFromJson(Object arg0, TransformConfig arg1) {

	}

	public void reader(Element arg0, TransformConfig arg1) {

	}

}
