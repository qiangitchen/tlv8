package com.tlv8.doc.clt.doc;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.doc.clt.doc.transform.TransformConfig;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.utils.ContextUtils;

@SuppressWarnings("rawtypes")
public class Docs {
	private List<Map<String, String>> container;
	private Map<String, Doc> docs = new ConcurrentHashMap<String, Doc>();
	private Map<String, Doc> removeDocs = new HashMap<String, Doc>();
	private DocPermissions permissions;

	private final static String commitUrlPattern = "/repository/file/cache/commit";
	private Map<String, String> cacheNames = new HashMap<String, String>();
	public static Logger logger = Logger.getLogger(Docs.class);
	private Boolean isHttps = false;
	private Boolean isLogicDelete = true;
	private Boolean isEditInfo = false;

	public Boolean getIsEditInfo() {
		return isEditInfo;
	}

	public void setIsEditInfo(Boolean isEditInfo) {
		this.isEditInfo = isEditInfo;
	}

	public List getContainer() {
		return container;
	}

	public Docs(DocPermissions permissions, Boolean isHttps) {
		this.isHttps = Utils.isNull(isHttps) ? false : isHttps;
		this.permissions = Utils.isNull(permissions) ? null : permissions;

	}

	public Docs(List<Map<String, String>> t, DocPermissions permissions, Boolean isHttps) {
		this(permissions, isHttps);
		this.container = t;
		for (int i = 0; i < t.size(); i++) {
			Docinfo r = new Docinfo((Map) t.get(i));
			docs.put(r.getString("SID"), new Doc(r, this));
		}
	}

	public Docs queryDocs(String docId, String docPath, String pattern, String orderBy, String custom) {
		return this.query(docId, docPath, pattern, orderBy, custom);
	}

	public Docs query(String docId, String docPath, String pattern, String orderBy, String custom) {
		container = DocDBHelper.queryDoc(docId, docPath, pattern, orderBy, custom);
		for (int i = 0; i < container.size(); i++) {
			Docinfo r = new Docinfo((Map) container.get(i));
			docs.put(r.getString("SID"), new Doc(r, this));
		}
		return this;
	}

	public Docs query(String concept, String idColumn, String select, String from, String condition,
			List<DataPermission> range, String filter, Boolean distinct, int offset, int limit, String columns,
			String orderBy, String aggregate, String aggregateColumns, Map<String, Object> variables, String dataModel,
			String fnModel) {
		container = BizData.query(concept, idColumn, select, from, condition, range, filter, distinct, offset, limit,
				columns, orderBy, aggregate, aggregateColumns, variables, dataModel, fnModel);
		for (int i = 0; i < container.size(); i++) {
			Docinfo r = new Docinfo((Map) container.get(i));
			docs.put(r.getString("SID"), new Doc(r, this));
		}
		return this;
	}

	public Doc addDoc(String sID, String sDocName, String sKind, Doc dirDoc) {
		if (Utils.isNull(container)) {
			container = DocDBHelper.initDocNode();
		}
		Docinfo r = new Docinfo((Map) container.get(0));
		if (Utils.isEmptyString(sID))
			sID = CommonUtils.createGUID();
		r.setString("SA_DocNode", sID);
		r.setString("sParentID", dirDoc.getsID());
		r.setString("sDocName", sDocName);
		r.setString("sKind", sKind);
		r.setFloat("sSize", 0);
		r.setString("sDocPath", createPath(dirDoc.getsDocPath(), dirDoc.getsID()));
		r.setString("sDocDisplayPath", createPath(dirDoc.getsDocDisplayPath(), dirDoc.getsDocName()));
		String currentPersionFID = ContextHelper.getPersonMember().getCurrentPersonFullID();
		String currentPersionFName = ContextHelper.getPersonMember().getCurrentPersonName();
		Timestamp currentTime = new Timestamp(new Date().getTime());

		r.setString("sCreatorFID", currentPersionFID);
		r.setString("sCreatorName", currentPersionFName);
		r.setDateTime("sCreateTime", currentTime);
		r.setString("sLastWriterFID", currentPersionFID);
		r.setString("sLastWriterName", currentPersionFName);
		r.setDateTime("sLastWriteTime", currentTime);
		r.setInt("version", 0);
		r.setInt("sFlag", 1);
		Doc doc = new Doc(r, this);
		docs.put(r.getString("SA_DocNode"), doc);

		return doc;
	}

	private String createPath(String origin, String destination) {
		return "/".equals(origin) ? origin + destination : origin + "/" + destination;
	}

	public Doc addDoc(String sID, String sParentID, String sDocName, String sKind, String sDocPath,
			String sDocDisplayPath) {
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
		String currentPersionFID = ContextHelper.getPersonMember().getCurrentPersonFullID();
		String currentPersionFName = ContextHelper.getPersonMember().getCurrentPersonName();
		Timestamp currentTime = new Timestamp(new Date().getTime());
		r.setString("sCreatorFID", currentPersionFID);
		r.setString("sCreatorName", currentPersionFName);
		r.setDateTime("sCreateTime", currentTime);
		r.setString("sLastWriterFID", currentPersionFID);
		r.setString("sLastWriterName", currentPersionFName);
		r.setDateTime("sLastWriteTime", currentTime);
		r.setInt("version", 0);
		r.setInt("sFlag", 1);
		Doc doc = new Doc(r, this);
		docs.put(r.getString("SA_DocNode"), doc);
		return doc;
	}

	public int createVersion() throws UnsupportedEncodingException, DocumentException, Exception {
		for (Entry<String, Doc> entry : docs.entrySet()) {
			Doc doc = entry.getValue();
			if (DocDBHelper.checkLocker(doc.getsID()) != 1 && (doc.getRow().getState().equals(ModifyState.EDIT)
					|| doc.getRow().getState().equals(ModifyState.NONE))) {
				deleteDoc(entry.getValue());
			} else if (Utils.isEmptyString(doc.getCacheName())) {
				deleteDoc(entry.getValue());
			}
		}
		Sys.printMsg("CreateVersionAction before commitFile.");
		commitFile();
		int result = commitData();
		commitFlag();
		return result;
	}

	public void commitFlag() throws Exception {
		Sys.printMsg("commitFlag");
		for (Entry<String, Doc> entry : docs.entrySet()) {
			Docinfo row = entry.getValue().getRow();
			if (row.getState().equals(ModifyState.DELETE)) {
				continue;
			}
			String cacheName = cacheNames.get(row.getString("sFileID"));
			try {
				Utils.check(Utils.isNotEmptyString(cacheName), "提交flag的时候,cachName不能为空");
			} catch (ModelException e) {
				e.printStackTrace();
			}
			DocUtils.saveDocFlag(row.getString("sDocPath"), row.getString("sKind"), row.getString("sFileID"), cacheName,
					isHttps);
		}
	}

	public void commitFile() throws UnsupportedEncodingException, DocumentException, Exception {
		Sys.printMsg("commitFile...");
		Map<String, List<StringBuffer>> logs = createChangeLogs();
		for (Iterator<Entry<String, List<StringBuffer>>> it = logs.entrySet().iterator(); it.hasNext();) {
			Entry<String, List<StringBuffer>> entry = (Entry<String, List<StringBuffer>>) it.next();
			StringBuffer sb = new StringBuffer();
			sb.append("<data>");
			for (StringBuffer log : entry.getValue()) {
				sb.append(log);
			}
			sb.append("</data>");
			String host = DocDBHelper.queryDocHost();
			String url = host + "/repository/file/cache/commit";
			Document result = DocUtils.excutePostAction(url, new ByteArrayInputStream(sb.toString().getBytes("UTF-8")));
			List<?> itemList = result.selectNodes("//item");
			for (Object litem : itemList) {
				Element item = (Element) litem;
				String docID = item.selectSingleNode("doc-id").getText();
				String fileID = item.selectSingleNode("file-id").getText();
				String docVersionID = item.selectSingleNode("doc-version-id").getText();
				Sys.printMsg(docID + ">>" + fileID + ">>" + docVersionID);
				setAfterCommit(docID, fileID, docVersionID);
			}
			if (!"true".equals(result.selectSingleNode("//flag").getText()))
				throw new DocRTException("DocServer commit error ", new Exception());
		}
	}

	public int commitData() throws Exception {
		for (Entry<String, Doc> entry : docs.entrySet()) {
			Doc doc = entry.getValue();
			if (doc.getRow().getState().equals(ModifyState.NONE)) {
				doc.getRow().setState(ModifyState.EDIT);
			}
			if (!doc.getState().equals(ModifyState.DELETE)) {
				cacheNames.put(doc.getsFileID(), doc.getCacheName());
			}
			doc.setScacheName("");
			doc.setSrevisionCacheName("");
			doc.setSeditorDeptName("");
			doc.setSeditorFID("");
			doc.setSeditorName("");
		}
		return saveTable();

	}

	public int save() throws Exception {
		return saveTable();
	}

	public Docs remove(String sID) {
		if (isLogicDelete) {
			Doc doc = get(sID);
			if (Utils.isNotNull(doc))
				doc.setsFlag(0);
		} else {
			//container.remove(sID);
		}
		removeDocs.put(sID, docs.get(sID));
		docs.remove(sID);

		return this;
	}

	public Docs deleteDoc(Doc doc) {
		// container.setRecordState(false);
		// container.deleteRows(doc.getsID());
		// container.setRecordState(true);
		try {
			docs.remove(doc.getsID());
		} catch (Exception e) {

		}
		return this;
	}

	public Docs remove(Doc doc) {
		String id = doc.getsID();
		remove(id);
		return this;
	}

	protected int saveTable() throws Exception {
		checkAceess();
		DocLogs dl = new DocLogs();
		for (int i = 0; i < container.size(); i++) {
			Docinfo row = new Docinfo((Map) container.get(i));
			Doc doc = new Doc((Docinfo) row, this);
			String accessType = getOperationType(doc);
			Sys.printMsg("accessType:" + accessType);
			if (Utils.isNotEmptyString(accessType))
				dl.addLog("", accessType, doc.getsID(), doc.getsDocName(), doc.getsDocLiveVersionID(), doc.getsSize());
		}
		if (dl.size() > 0)
			dl.save();

		return 1;
	}

	protected void commit() throws UnsupportedEncodingException, DocumentException, Exception {
		Map<String, List<StringBuffer>> logs = createChangeLogs();
		for (Iterator<Entry<String, List<StringBuffer>>> it = logs.entrySet().iterator(); it.hasNext();) {
			Entry<String, List<StringBuffer>> entry = it.next();
			StringBuffer sb = new StringBuffer();
			sb.append("<data>");
			for (StringBuffer log : entry.getValue()) {
				sb.append(log);
			}
			sb.append("</data>");
			String host = DocDBHelper.queryDocHost();
			String url = host + commitUrlPattern;
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

	private void setAfterCommit(String docID, String fileID, String docVersionID) {
		Doc doc = docs.get(docID);
		doc.setsFileID(fileID);
		doc.setsDocLiveVersionID(Integer.valueOf(docVersionID));
		docs.put(docID, doc);
		Sys.printMsg("setAfterCommit end");
	}

	private StringBuffer createChangeLogItem(Doc doc) {
		StringBuffer result = new StringBuffer();
		/* 判断是否是文件夹 */
		String operationType = getOperationType(doc);
		// operationType = "dir".equals(doc.getsKind()) &&
		// "new".equals(operationType)? "newDir" : operationType;

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

	private Map<String, List<StringBuffer>> createChangeLogs() throws DocumentException {
		Map<String, List<StringBuffer>> serverLogs = new HashMap<String, List<StringBuffer>>();
		try {
			setChangeLogs(serverLogs, docs);
			setChangeLogs(serverLogs, removeDocs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverLogs;
	}

	private void setChangeLogs(Map<String, List<StringBuffer>> serverLogs, Map<String, Doc> docs) {
		for (Doc doc : docs.values()) {
			if (ModifyState.NONE.equals(doc.getState()))
				continue;
			String docPath = doc.getsDocPath();
			String rootPath = "/".equals(docPath.trim()) ? doc.getsID() : DocUtils.getRootId(doc.getsDocPath());
			StringBuffer sb = new StringBuffer();
			sb.append(createChangeLogItem(doc));
			if (serverLogs.containsKey(rootPath)) {
				serverLogs.get(rootPath).add(sb);
			} else {
				List<StringBuffer> al = new ArrayList<StringBuffer>();
				al.add(sb);
				serverLogs.put(rootPath, al);
			}
		}
	}

	private String getOperationType(Doc doc) {
		String operationType = "";
		String state = doc.getState();
		if (state == ModifyState.NEW) {
			operationType = "new";
		} else if (state == ModifyState.EDIT) {
			operationType = isEditInfo ? "editInfo" : "edit";
		} else if (state == ModifyState.DELETE) {
			operationType = isLogicDelete ? "logicDelete" : "delete";
		}
		return operationType;
	}

	public int size() {
		return docs.entrySet().size();
	}

	public Doc get(String sID) {
		return docs.get(sID);
	}

	public Iterator<Doc> getIterator() {
		return docs.values().iterator();
	}

	public DocFileInfo querFileInfoById(String docPath, String fileId, String docVersion) throws Exception {
		Map<String, Object> m = DocDBHelper.queryDocInfoById(isHttps, docPath, fileId, docVersion);
		DocFileInfo fi = new DocFileInfo(m);
		return fi;
	}

	public DocPermissions getPermissions() {
		return permissions;
	}

	class DocFileInfo {
		private String fileId;
		private String docName;
		private List<DocFilePart> parts = new ArrayList<Docs.DocFilePart>();
		Map<String, Object> fileInfo = new HashMap<String, Object>();;

		public DocFileInfo(Map<String, Object> fileInfo) {
			this.fileInfo = fileInfo;
			init();
		}

		@SuppressWarnings("unchecked")
		private void init() {
			this.fileId = (String) fileInfo.get("fileId");
			this.docName = (String) fileInfo.get("docName");
			Map<String, Object> mapParts = (Map<String, Object>) fileInfo.get("parts");
			Iterator<?> it = mapParts.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry m = (Map.Entry) it.next();
				if ("length".equals((String) m.getKey()))
					continue;
				Map<String, Object> item = (Map<String, Object>) m.getValue();
				DocFilePart dfp = new DocFilePart((String) item.get("typeId"), (String) item.get("size"),
						(String) item.get("mimeType"), (String) item.get("dataChangedInVersion"));
				parts.add(dfp);

			}
		}

		public String getFileId() {
			return fileId;
		}

		public String getDocName() {
			return docName;
		}

		public List<DocFilePart> getParts() {
			return parts;
		}

		public Map<String, Object> toMap() {
			return fileInfo;
		}
	}

	class DocFilePart {
		private String typeId;

		public DocFilePart(String typeId, String size, String mimeType, String dataChangedInVersion) {
			this.typeId = typeId;
			this.size = size;
			this.mimeType = mimeType;
			this.dataChangedInVersion = dataChangedInVersion;
		}

		private String size;
		private String mimeType;
		private String dataChangedInVersion;

		public String getTypeId() {
			return typeId;
		}

		/*
		 * public void setTypeId(String typeId) { this.typeId = typeId; }
		 */
		public String getSize() {
			return size;
		}

		/*
		 * public void setSize(String size) { this.size = size; }
		 */
		public String getMimeType() {
			return mimeType;
		}

		public void setMimeType(String mimeType) {
			this.mimeType = mimeType;
		}

		public String getDataChangedInVersion() {
			return dataChangedInVersion;
		}
		/*
		 * public void setDataChangedInVersion(String dataChangedInVersion) {
		 * this.dataChangedInVersion = dataChangedInVersion; }
		 */
	}

	public void readerFromJson(Object arg0, TransformConfig arg1) {

	}

	public void reader(Element arg0, TransformConfig arg1) {

	}

	private void checkAceess() {
		if (Utils.isNull(permissions))
			return;
		for (Doc doc : docs.values()) {
			DocPermission p = null;
			try {
				p = permissions.queryPermissionById(doc.getsID(), doc.getsDocPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			int access = p.getsAccess();
			String state = doc.getState();
			if (state == ModifyState.NEW && !((access % 512) >= 256)) {
				logger.error("没有新建文件的权限");
				DocRTException e = new DocRTException("没有新建文件的权限");
				throw e;

			}
			if (state == ModifyState.EDIT && !((access % 1024) >= 512)) {
				logger.error("没有编辑文件的权限");
				DocRTException e = new DocRTException("没有编辑文件的权限");
				throw e;

			}
			if (state == ModifyState.DELETE && !((access % 2048) >= 1024)) {
				logger.error("没有删除文件的权限");
				DocRTException e = new DocRTException("没有删除文件的权限");
				throw e;
			}
		}
	}

	public Boolean getIsLogicDelete() {
		return isLogicDelete;
	}

	public void setIsLogicDelete(Boolean isLogicDelete) {
		this.isLogicDelete = isLogicDelete;
	}

	public static Doc queryDocById(String sID) {
		Docs ds = new Docs(null, null);
		return ds.queryDocs(sID, "", "", "", "").get(sID);
	}

	private static int deleteDocTable(boolean isLogicDelete, String docId, String docPath, boolean isDir, Docs docs) {
		try {
			Utils.check(Utils.isNotEmptyString(docId), "deleteDocTable的docId参数不能为空！");
		} catch (ModelException e1) {
			e1.printStackTrace();
		}
		String docFullPath = "/".equals(docPath.trim()) ? docPath + docId : docPath + "/" + docId;
		String ksql = isLogicDelete ? "update SA_DocNode SA_DocNode set SA_DocNode.sFlag = '0' "
				: "delete from SA_DocNode SA_DocNode ";
		ksql += " where SA_DocNode = '" + docId + "' ";
		if (isDir) {
			ksql += " or SA_DocNode.sDocPath = '" + docFullPath + " ' or SA_DocNode.sDocPath like '" + docFullPath
					+ "/%' ";
		}
		if (Utils.isNotNull(docs)) {
			for (Iterator<Doc> it = docs.getIterator(); it.hasNext();) {
				Doc doc = it.next();
				if ((docId.equals(doc.getsID()) || doc.getDocFullPath().indexOf(docFullPath) > 0)
						&& !(doc.getState() == ModifyState.NONE)) {
					doc.setState(ModifyState.NONE);
				}
			}
		}
		try {
			DBUtils.execUpdateQuery("system", ksql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	private static Document commitToServer(boolean isHttps, String docPath, List<Map<String, String>> items)
			throws Exception {
		Document result = null;
		String param = DocUtils.getDocServerParam(items);
		String host = DocDBHelper.queryDocHost();
		String url = host + commitUrlPattern;
		result = DocUtils.excutePostAction(url, new ByteArrayInputStream(param.getBytes("UTF-8")));
		if (!"true".equals(result.selectSingleNode("//flag").getText()))
			throw new DocRTException("DocServer commit error ", new Exception());
		return result;
	}

	private static List<Map<String, String>> getCommitItems(String operationType, String docId, String docPath,
			String fileId, boolean isDir) {
		Map<String, String> item = new HashMap<String, String>();
		item.put("operation-type", operationType);
		item.put("doc-id", docId);
		item.put("doc-path", docPath);
		item.put("kind", isDir ? "dir" : "");
		List<Map<String, String>> items = new ArrayList<Map<String, String>>();
		items.add(item);
		return items;
	}

	public static int deleteDoc(boolean isHttps, boolean isLogicDelete, String docId, String docPath, String fileId,
			boolean isDir) throws Exception {
		commitToServer(isHttps, docPath,
				getCommitItems(isLogicDelete ? "logicDelete" : "delete", docId, docPath, fileId, isDir));
		return deleteDocTable(isLogicDelete, docId, docPath, isDir, null);
	}

	public static int deleteDoc(boolean isHttps, boolean isLogicDelete, Doc doc) throws Exception {
		return deleteDoc(isHttps, isLogicDelete, doc.getsID(), doc.getsDocPath(), doc.getsFileID(),
				"dir".equals(doc.getsKind()));
	}

	private static int reverseDocTable(String docId, String docPath, boolean isDir) {
		try {
			Utils.check(Utils.isNotEmptyString(docId), "deleteDocTable的docId参数不能为空！");
		} catch (ModelException e1) {
			e1.printStackTrace();
		}
		String docFullPath = docPath + "/" + docId;
		String ksql = "update SA_DocNode SA_DocNode set SA_DocNode.sFlag = '1' where SA_DocNode.sID = '" + docId + "' ";
		if (isDir) {
			ksql += " or SA_DocNode.sDocPath = '" + docFullPath + " ' or SA_DocNode.sDocPath like '" + docFullPath
					+ "/%' ";
		}
		try {
			DBUtils.execUpdateQuery("system", ksql);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public Doc addDoc(String dirID, String docName) {
		String docID = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		return addDoc(docID, dirID, docName);
	}

	public Doc addDoc(String docID, String dirID, String docName) {
		String SDOCPATH = "";
		String SDOCDISPLAYPATH = "";
		try {
			List<Map<String, String>> docDir = DBUtils.execQueryforList("system",
					"select SDOCPATH,SDOCDISPLAYPATH from SA_DOCNODE where SID = '" + dirID + "'");
			if (docDir.size() > 0) {
				Map m = docDir.get(0);
				SDOCPATH = m.get("SDOCPATH").toString();
				SDOCDISPLAYPATH = m.get("SDOCDISPLAYPATH").toString();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String sql = "insert into SA_DOCNODE(SID,SPARENTID,SDOCNAME,"
				+ "SCREATORFID,SCREATORNAME,SCREATORDEPTNAME,SCREATETIME,SDOCPATH,SDOCDISPLAYPATH,"
				+ "SEDITORFID,SEDITORNAME,SEDITORDEPTNAME,SDOCLIVEVERSIONID,SFLAG,VERSION)values('" + docID + "','"
				+ dirID + "','" + docName + "'," + "?,?,?,sysdate,?,?,?,?,?,1,1,1)";
		ContextBean context = ContextUtils.getContext();
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, context.getCurrentPersonID());
			ps.setString(2, context.getCurrentPersonName());
			ps.setString(3, context.getCurrentDeptName());
			ps.setString(4, SDOCPATH + "/" + docID);
			ps.setString(5, SDOCDISPLAYPATH + "/" + docName);
			ps.setString(6, context.getCurrentPersonFullID());
			ps.setString(7, context.getCurrentPersonName());
			ps.setString(8, context.getCurrentDeptName());
			ps.executeUpdate();
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
		return Docs.queryDocById(docID);
	}

	public void saveDocData(String docID, Doc doc) {
		String sql = "update SA_DOCNODE set SKIND = '" + doc.getsKind() + "',SSIZE= '" + doc.getsSize() + "',SFILEID='"
				+ doc.getsFileID() + "' where SID = '" + docID + "'";
		try {
			DBUtils.execUpdateQuery("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int reverseDoc(boolean isHttps, String docId, String docPath, String fileId, boolean isDir)
			throws Exception {
		commitToServer(isHttps, docPath, getCommitItems("logicReverse", docId, docPath, fileId, isDir));
		return reverseDocTable(docId, docPath, isDir);
	}

	public static int reverseDoc(boolean isHttps, Doc doc) throws Exception {
		return reverseDoc(isHttps, doc.getsID(), doc.getsDocPath(), doc.getsFileID(), "dir".equals(doc.getsKind()));
	}

}
