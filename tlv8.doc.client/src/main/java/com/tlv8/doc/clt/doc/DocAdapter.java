package com.tlv8.doc.clt.doc;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.db.DBUtils;
import com.tlv8.doc.clt.doc.attachment.Attachment;
import com.tlv8.doc.clt.doc.attachment.Attachments;
import com.tlv8.doc.clt.doc.attachment.DocLinkAttachments;
import com.tlv8.doc.clt.doc.attachment.DocLinkDirs;

@SuppressWarnings("rawtypes")
public class DocAdapter {
	/*
	 * 文档中心调用
	 */
	public static Logger logger = Logger.getLogger(DocAdapter.class);

	public static List queryDocAuth(String concept, String idColumn, String select, String from, String condition,
			List<DataPermission> range, String filter, Boolean distinct, int offset, int limit, String columns,
			String orderBy, String aggregate, String aggregateColumns, Map<String, Object> variables, String dataModel,
			String fnModel) {
		DocDirPermissions ddp = new DocDirPermissions();
		return ddp.query(concept, idColumn, select, from, condition, range, filter, distinct, offset, limit, columns,
				orderBy, aggregate, aggregateColumns, variables, dataModel, fnModel).toTable();
	}

	public static int saveDocAuth(List table, String concept, String dataModel, String fnModel,
			List<DataPermission> insertRange, List<DataPermission> deleteRange, List<DataPermission> updateRange,
			String readOnly, String notNull) throws Exception {
		DocDirPermissions ddp = new DocDirPermissions(table);
		return ddp.save();
	}

	@SuppressWarnings("unchecked")
	public static int saveDocNode(List table, String concept, String dataModel, String fnModel,
			List<DataPermission> insertRange, List<DataPermission> deleteRange, List<DataPermission> updateRange,
			String readOnly, String notNull) throws Exception {
		Docs ds = new Docs(table, new DocPermissions(), null);
		return ds.saveTable();
	}

	public static Docs queryDocNode(String concept, String idColumn, String select, String from, String condition,
			List<DataPermission> range, String filter, Boolean distinct, int offset, int limit, String columns,
			String orderBy, String aggregate, String aggregateColumns, Map<String, Object> variables, String dataModel,
			String fnModel) {
		Docs ds = new Docs(new DocPermissions(), null);
		return ds.query(concept, idColumn, select, from, condition, range, filter, distinct, offset, limit, columns,
				orderBy, aggregate, aggregateColumns, variables, dataModel, fnModel);
	}

	public static Docs queryDoc(String docID, String docPath, String pattern, String orderBy, String custom) {
		Docs ds = new Docs(null, null);
		return ds.queryDocs(docID, docPath, pattern, orderBy, custom);
	}

	public static void syncCustomFileds(String sDocID, Boolean isHttps)
			throws UnsupportedEncodingException, DocumentException, Exception {
		Docs docs = new Docs(null, isHttps);
		docs.query(sDocID, null, null, null, null);
		for (Iterator<Doc> iterator = docs.getIterator(); iterator.hasNext();) {
			Doc doc = iterator.next();
			doc.setState(ModifyState.EDIT);
		}
		docs.setIsEditInfo(true);
		docs.commit();
	}

	public static int addAccessRecord(String param) throws Exception {
		JSONObject jsonObject = JSON.parseObject(param);
		JSONArray items = jsonObject.getJSONArray("items");
		DocLogs dl = new DocLogs();
		for (int i = 0; i < items.size(); i++) {
			JSONObject item = items.getJSONObject(i);
			dl.addLog("", item.getString("operation_type"), item.getString("doc_id"), item.getString("doc_name"),
					Integer.valueOf(item.getString("doc_version_id")), Float.valueOf(item.getString("size")));
		}
		// dl.save();
		return 1;
	}

	public static int deleteDocAdapter(String changeLog)
			throws UnsupportedEncodingException, DocumentException, Exception {
		JSONObject jsonObject = null;
		int result = -1;
		jsonObject = JSON.parseObject(changeLog);
		boolean isHttps = jsonObject.getBoolean("isHttps");
		Docs ds = new Docs(null, isHttps);
		JSONArray jsonArray = jsonObject.getJSONArray("items");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject item = jsonArray.getJSONObject(i);
			String sID = item.getString("doc_id");
			if (Utils.isEmptyString(sID))
				sID = CommonUtils.createGUID();
			Doc up = ds.addDoc(sID, item.getString("parent_id"), item.getString("doc_name"), item.getString("kind"),
					item.getString("doc_path"), item.getString("doc_display_path"));
			String op = item.getString("operation_type");
			// ds.setIsLogicDelete(false);
			if (op.equals("delete")) {
				up.setsFileID(item.getString("file_id"));
				up.setVersion(Integer.valueOf(item.getString("version")));
				/* 通过标志位假删除 */
				up.setState(ModifyState.DELETE);
			}
		}
		result = ds.saveTable();
		if (result < 0)
			throw new DocRTException("文档保存失败");
		return result;
	}

	public static Docs commitDocAdapter(String changeLog) throws Exception, ModelException {
		JSONObject jsonObject = null;
		Docs result = null;
		try {
			jsonObject = JSON.parseObject(changeLog);
			boolean isHttps = jsonObject.getBoolean("isHttps");
			Docs ds = new Docs(null, isHttps);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
				Doc up = ds.addDoc("", item.getString("parent_id"), item.getString("doc_name"), item.getString("kind"),
						item.getString("doc_path"), item.getString("doc_display_path"));
				String op = item.getString("operation_type");
				Utils.check(Utils.isNotEmptyString(item.getString("cache_name")) && (!op.equals("delete")),
						"new或edit状态的cache_name不能为空");
				up.setScacheName(item.getString("cache_name"));

				String revisionCacheName = item.getString("revision_cache_name");
				if (Utils.isNotEmptyString(revisionCacheName))
					up.setSrevisionCacheName(revisionCacheName);

				String commentFileContent = item.getString("comment_file_content");
				if (Utils.isNotEmptyString(commentFileContent)) {
					up.setCommentFileContent(commentFileContent);
				}

				if (op.equals("new") || op.equals("newDir")) {
					up.setState(ModifyState.NEW);
				} else if (op.equals("edit")) {
					up.setsFileID(item.getString("file_id"));
					up.setVersion(Integer.valueOf(item.getString("version")));
					up.setState(ModifyState.EDIT);
				} else if (op.equals("delete")) {
					up.setsFileID(item.getString("file_id"));
					up.setState(ModifyState.DELETE);
				}
			}
			ds.commit();
			result = ds;
		} catch (Exception e) {
			logger.error(e);
			throw new DocRTException("提交文档中心文档失败", e);
		}
		return result;
	}

	private static void parseTocommitFile(Attachments atts, JSONArray createVersionArray, String process,
			String activity, Boolean isHttps) {
		for (int i = 0; i < createVersionArray.size(); i++) {
			JSONObject item = createVersionArray.getJSONObject(i);
			String attachmentValue = item.getString("attachmentValue");
			String billID = item.getString("billID");
			Attachments temp = new Attachments(process, activity, attachmentValue, true, null, isHttps);
			for (Iterator<Attachment> iterator = temp.getIterator(); iterator.hasNext();) {
				Attachment att = (Attachment) iterator.next();
				String sCacheName = att.getCacheName();
				att.setState(ModifyState.EDIT);
				if (Utils.isEmptyString(sCacheName)) {
					iterator.remove();
				} else if (atts.get(att.getsID()) != null) {
					iterator.remove();
				} else {
					int lockNum = DocDBHelper.checkLocker(att.getsID());
					if (lockNum != 1) {
						iterator.remove();
					} else {
						atts.addAttachment(att, billID);
					}
				}
			}
		}
	}

	private static void parseTocommitFile(Attachments atts, JSONArray jsonArray, Boolean autoCreateVersion) {
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject item = jsonArray.getJSONObject(i);
			String cacheName = "";
			String revisionCacheName = "";
			String commentFileContent = "";
			if (item.containsKey("cache_name")) {
				cacheName = item.getString("cache_name");
			}

			if (item.containsKey("revision_cache_name")) {
				revisionCacheName = item.getString("revision_cache_name");
			}
			if (item.containsKey("comment_file_content")) {
				commentFileContent = item.getString("comment_file_content");
			}

			String op = item.getString("operation_type");
			if (Utils.isEmptyString(cacheName) && (!op.equals("delete"))) {
				continue;
			}
			if (op.equals("edit")) {
				if (autoCreateVersion) {
					int lockNum = DocDBHelper.checkLocker(item.getString("doc_id"));
					if (lockNum != 1) {
						continue;
					}
				} else {
					continue;
				}
			}
			Attachment up = atts.addDoc(item.getString("doc_id"), item.getString("parent_id"),
					item.getString("doc_name"), item.getString("kind"), item.getString("doc_path"),
					item.getString("doc_display_path"), item.getString("bill_id"));
			up.setScacheName(cacheName);
			if (Utils.isNotEmptyString(revisionCacheName))
				up.setSrevisionCacheName(revisionCacheName);
			if (Utils.isNotEmptyString(commentFileContent)) {
				up.setCommentFileContent(commentFileContent);
			}

			if (Utils.isNotEmptyString(item.getString("size")))
				up.setsSize(Float.valueOf(item.getString("size")));
			if (op.equals("new") || op.equals("newDir")) {
				up.setState(ModifyState.NEW.toString());
			} else if (op.equals("edit")) {
				up.setsFileID(item.getString("file_id"));
				up.setVersion(Integer.valueOf(item.getString("version")));
				up.setState(ModifyState.EDIT.toString());
			} else if (op.equals("delete")) {
				up.setsFileID(item.getString("file_id"));
				up.setState(ModifyState.DELETE.toString());
			}
		}
	}

	private static void parseTocommitData(Attachments atts, JSONArray createVersionArray, String process,
			String activity, Boolean isHttps) {
		for (int i = 0; i < createVersionArray.size(); i++) {
			JSONObject item = createVersionArray.getJSONObject(i);
			String attachmentValue = item.getString("attachmentValue");
			String billID = item.getString("billID");
			Attachments temp = new Attachments(process, activity, attachmentValue, true, null, null);
			for (Iterator<Attachment> iterator = temp.getIterator(); iterator.hasNext();) {
				Attachment att = (Attachment) iterator.next();
				String sCacheName = att.getCacheName();
				if (Utils.isEmptyString(sCacheName)) {
					iterator.remove();
				} else if (atts.get(att.getsID()) != null) {
					iterator.remove();
				} else if (DocDBHelper.checkLocker(att.getsID()) == 1) {
					Attachment up = atts.addAttachment(att, billID);
					up.getRow().setState(ModifyState.EDIT);
					up.setScacheName("");
					up.setSrevisionCacheName("");
					up.setSeditorDeptName("");
					up.setSeditorFID("");
					up.setSeditorName("");
				}
			}
		}
	}

	private static void parseTocommitData(Attachments atts, JSONArray jsonArray, Boolean autoCreateVersion) {
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject item = jsonArray.getJSONObject(i);
			String op = item.getString("operation_type");
			if (op.equals("edit") && !autoCreateVersion) {
				continue;
			}
			Attachment up = atts.addDoc(item.getString("doc_id"), item.getString("parent_id"),
					item.getString("doc_name"), item.getString("kind"), item.getString("doc_path"),
					item.getString("doc_display_path"), item.getString("bill_id"));
			if (Utils.isNotEmptyString(item.getString("size")))
				up.setsSize(Float.valueOf(item.getString("size")));
			if ("new".equals(op) || "newDir".equals(op)) {
				up.setVersion(Integer.valueOf(item.getString("version")));
				up.setScacheName(item.getString("cache_name"));
				up.setSrevisionCacheName(item.getString("revision_cache_name"));
				up.setState(ModifyState.NEW.toString());
			} else if ("edit".equals("edit") || "editInfo".equals(op)) {
				up.setVersion(Integer.valueOf(item.getString("version")));
				up.setScacheName(item.getString("cache_name"));
				up.setSrevisionCacheName(item.getString("revision_cache_name"));
				up.setState(ModifyState.EDIT.toString());
			} else if ("delete".equals(op)) {
				up.setsFileID(item.getString("file_id"));
				up.setState(ModifyState.DELETE.toString());
			}
			if (Utils.isNotEmptyString(item.getString("file_id"))) {
				up.setsFileID(item.getString("file_id"));
				up.setsDocLiveVersionID(Integer.valueOf(item.getString("doc_version_id")));
			}
		}
	}

	public static List commitAttachAdapter(String changeLog) throws Exception {
		JSONObject jsonObject = JSON.parseObject(changeLog);
		String url = jsonObject.getString("url");
		Boolean autoCreateVersion = true;
		if (jsonObject.containsKey("autoCreateVersion")) {
			autoCreateVersion = jsonObject.getBoolean("autoCreateVersion");
		}
		String process = null;
		if (jsonObject.containsKey("process")) {
			process = jsonObject.getString("process");
		}
		String activity = null;
		if (jsonObject.containsKey("activity")) {
			activity = jsonObject.getString("activity");
		}
		Boolean isHttpsValue = jsonObject.getBoolean("isHttps");
		Boolean isHttps = (isHttpsValue == true) ? isHttpsValue : false;
		if (!"".equals(url)) {
			isHttps = url.startsWith("https") ? true : false;
		}
		Attachments atts = new Attachments(process, activity, "", false, null, isHttps);
		JSONArray jsonArray = jsonObject.getJSONArray("items");
		parseTocommitFile(atts, jsonArray, autoCreateVersion);
		if (jsonObject.containsKey("createVersionLogs") && autoCreateVersion) {
			JSONArray createVersionArray = jsonObject.getJSONArray("createVersionLogs");

			parseTocommitFile(atts, createVersionArray, process, activity, isHttps);
			for (Iterator<Attachment> iterator = atts.getIterator(); iterator.hasNext();) {
				Attachment att = iterator.next();
				System.out.println(att.getCacheName());
			}
		}
		atts.commitFile();
		return atts.toTable();
	}

	// 以下2个方法为文档中心保留
	public static void commitDocCacheAdapter(String changeLog) throws Exception {
		JSONObject item = JSON.parseObject(changeLog);
		String cacheName = item.getString("cache_name");
		String revisionCacheName = item.getString("revision_cache_name");
		String docID = item.getString("doc_id");
		String size = item.getString("size");
		if (!(Utils.isNotEmptyString(cacheName) || Utils.isNotEmptyString(revisionCacheName))) {
			throw new DocRTException("daisy的cache生成错误");
		}
		String sEditorFID = ContextHelper.getPersonMember().getCurrentPersonFullID();
		String sEDITORNAME = ContextHelper.getPersonMember().getCurrentPersonName();
		String sEDITORDEPTNAME = ContextHelper.getPersonMember().getCurrentDeptName();
		String updateKSql = "update SA_DocNode SA_DocNode set SA_DocNode.sCacheName='" + cacheName
				+ "', SA_DocNode.sRevisionCacheName ='" + revisionCacheName + "',SSIZE=" + size + ",sEditorFID = '"
				+ sEditorFID + "',SEDITORNAME='" + sEDITORNAME + "',SEDITORDEPTNAME='" + sEDITORDEPTNAME
				+ "',sLastWriterFID='" + sEditorFID + "',sLastWriterName='" + sEDITORNAME + "',sLastWriterDeptName='"
				+ sEDITORDEPTNAME + "',SLASTWRITETIME=getDate(),version=version+1"
				+ ",sDocLiveVersionID=sDocLiveVersionID+1 " + " where SA_DocNode.sID='" + docID + "'";
		if (DBUtils.IsOracleDB("system")) {
			updateKSql = updateKSql.replace("getDate()", "sysdate");
		} else if (DBUtils.IsMySQLDB("system")) {
			updateKSql = updateKSql.replace("getDate()", "now()");
		}
		System.out.println(updateKSql);
		@SuppressWarnings("unused")
		String effactNum = DBUtils.execUpdateQuery("system", updateKSql);
		// Utils.check(effactNum == 1, "保存docCache影响记录数不唯一");
	}

	public static void commitDocFlag(String changeLog) throws Exception {
		JSONObject jsonObject = JSON.parseObject(changeLog);
		JSONArray jsonArray = jsonObject.getJSONArray("items");
		if (jsonArray.size() == 0) {
			return;
		}
		boolean isHttps = jsonObject.getBoolean("isHttps");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject item = jsonArray.getJSONObject(i);
			String operationType = item.getString("operation_type");
			if (!("new".equals(operationType) || "edit".equals(operationType))) {
				continue;
			}
			DocUtils.saveDocFlag(item.getString("doc_path"), item.getString("kind"), item.getString("file_id"),
					item.getString("cache_name"), isHttps);
		}
	}

	public static int saveAttachAdapter(String changeLog, Boolean isSaveDocLink) throws Exception, ModelException {
		int i = -1;
		if (isSaveDocLink) {
			i = saveLinkAttach(changeLog);
		} else {
			i = saveCommAttach(changeLog);
		}

		return i;
	}

	/**
	 * office控件点击成文时候调用
	 */
	public static String createVersion(String sDocID, Boolean isSaveDocLink, Boolean isHttps) throws Exception {
		return DocUtils.createVersion(sDocID, isHttps);
	}

	@Deprecated
	public static void saveAttachFlag(String changeLog) throws Exception {
		JSONObject jsonObject = null;
		jsonObject = JSON.parseObject(changeLog);
		JSONArray jsonArray = jsonObject.getJSONArray("items");
		if (jsonArray.size() == 0) {
			return;
		}
		boolean isHttps = jsonObject.getBoolean("isHttps");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject item = jsonArray.getJSONObject(i);
			String operationType = item.getString("operation_type");
			if (!("new".equals(operationType) || "edit".equals(operationType))) {
				continue;
			}
			DocUtils.saveDocFlag(item.getString("doc_path"), item.getString("kind"), item.getString("file_id"), "",
					isHttps);
		}
	}

	private static int saveCommAttach(String changeLog) throws Exception {
		JSONObject jsonObject = JSON.parseObject(changeLog);
		String process = null;
		if (jsonObject.containsKey("process")) {
			process = jsonObject.getString("process");
		}
		String activity = null;
		if (jsonObject.containsKey("process")) {
			activity = jsonObject.getString("activity");
		}
		Boolean isHttps = jsonObject.getBoolean("isHttps");
		Boolean autoCreateVersion = true;
		if (jsonObject.containsKey("autoCreateVersion")) {
			autoCreateVersion = jsonObject.getBoolean("autoCreateVersion");
		}
		JSONArray jsonArray = jsonObject.getJSONArray("items");
		Attachments atts = new Attachments(process, activity, "", false, null, isHttps);
		parseTocommitData(atts, jsonArray, autoCreateVersion);
		if (jsonObject.containsKey("createVersionLogs") && autoCreateVersion) {
			JSONArray createVersionArray = jsonObject.getJSONArray("createVersionLogs");
			parseTocommitData(atts, createVersionArray, process, activity, isHttps);
		}
		int affectNum = -1;
		if (atts.size() > 0) {

			affectNum = atts.commitData();
			atts.commitFlag();
		}
		return affectNum;
	}

	private static int saveLinkAttach(String changeLog) throws Exception, ModelException {
		JSONObject jsonObject = JSON.parseObject(changeLog);
		JSONArray jsonArray = jsonObject.getJSONArray("items");
		Boolean isHttps = false;
		if (jsonObject.containsKey("isHttps")) {
			isHttps = jsonObject.getBoolean("isHttps");
		}
		Boolean autoCreateVersion = false;
		if (jsonObject.containsKey("autoCreateVersion")) {
			autoCreateVersion = jsonObject.getBoolean("autoCreateVersion");
		}
		String process = null;
		if (jsonObject.containsKey("process")) {
			process = jsonObject.getString("process");
		}
		String activity = null;
		if (jsonObject.containsKey("activity")) {
			activity = jsonObject.getString("activity");
		}
		DocLinkAttachments atts = new DocLinkAttachments("", process, activity, false, isHttps);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject item = jsonArray.getJSONObject(i);
			String op = item.getString("operation_type");
			String cacheName = "";
			String revisionCacheName = "";
			String commentFileContent = "";
			if (item.containsKey("cache_name")) {
				cacheName = item.getString("cache_name");
			}
			if (item.containsKey("revision_cache_name")) {
				revisionCacheName = item.getString("revision_cache_name");
			}
			if (item.containsKey("comment_file_content")) {
				commentFileContent = item.getString("comment_file_content");
			}
			Attachment up = atts.addDoc(item.getString("doc_id"), item.getString("parent_id"),
					item.getString("doc_name"), item.getString("kind"), item.getString("doc_path"),
					item.getString("doc_display_path"), item.getString("bill_id"));
			up.setScacheName(cacheName);
			up.setSrevisionCacheName(revisionCacheName);
			up.setCommentFileContent(commentFileContent);
			if (op.equals("new") || op.equals("newDir")) {
				up.setState(ModifyState.NEW);
				if (Utils.isNotEmptyString(item.getString("size")))
					up.setsSize(Float.valueOf(item.getString("size")));
				setLinkAttachRalation(up, "sDocSerialNumber", item.getString("serial_number"));
				setLinkAttachRalation(up, "sClassification", item.getString("classification"));
				setLinkAttachRalation(up, "sKeywords", item.getString("keywords"));
				setLinkAttachRalation(up, "sDescription", item.getString("description"));
			} else if (op.equals("edit") || "editInfo".equals(op)) {
				up.setVersion(Integer.valueOf(item.getString("version")));
				up.setState(ModifyState.EDIT);
				up.setsDocName(item.getString("doc_name"));
				editLinkAttachInfo(up, item);
				if (!autoCreateVersion && op.equals("edit")) {
					up.setState(ModifyState.NONE);
					atts.deleteAtts(up.getsID());
				} else if (op.equals("editInfo")) {
					syncCustomFileds(up.getsID(), isHttps);
					atts.saveTable();
					atts.deleteAtts(up.getsID());
				}
			} else if (op.equals("delete")) {
				up.setsFileID(item.getString("file_id"));
				up.setState(ModifyState.DELETE.toString());
			}
			if (Utils.isNotEmptyString(item.getString("file_id"))) {
				up.setsFileID(item.getString("file_id"));
				up.setsDocLiveVersionID(Integer.valueOf(item.getString("doc_version_id")));
			}
		}
		if (autoCreateVersion && jsonObject.containsKey("changedBillIDs")) {
			String billIDs = jsonObject.getString("changedBillIDs");
			atts.setBillIDs(billIDs);
			List table = DocDBHelper.queryDocLink(billIDs, process, activity);
			for (int i = 0; i < table.size(); i++) {
				Map row = (Map) table.get(i);
				String sID = (String) row.get("SDOCID");
				String sParentID = (String) row.get("SPARENTID");
				String sDocName = (String) row.get("SDOCNAME");
				String sKind = (String) row.get("SKIND");
				String sDocPath = (String) row.get("SDOCPATH");
				String sDocDisplayPath = (String) row.get("SDOCDISPLAYPATH");
				String sCacheName = (String) row.get("SCACHENAME");
				String sRevisionCacheName = (String) row.get("SREVISIONCACHENAME");
				String sEditorFID = (String) row.get("SEDITORFID");
				String sEditorName = (String) row.get("SEDITORNAME");
				String sEditorDeptName = (String) row.get("SEDITORDEPTNAME");
				String billID = (String) row.get("SOWNERID");
				int version = Integer.valueOf((String) row.get("VERSION"));
				if (Utils.isNotEmptyString(sCacheName) && Utils.isNotEmptyString(sRevisionCacheName)
						&& DocDBHelper.checkLocker(sID) == 1) {
					if (atts.get(sID) == null) {
						Attachment up = atts.addDoc(sID, sParentID, sDocName, sKind, sDocPath, sDocDisplayPath, billID);
						up.setScacheName(sCacheName);
						up.setSrevisionCacheName(sRevisionCacheName);
						up.setSeditorFID(sEditorFID);
						up.setSeditorName(sEditorName);
						up.setSeditorDeptName(sEditorDeptName);
						up.setSrevisionCacheName(sRevisionCacheName);
						up.setVersion(version);
						up.setState(ModifyState.EDIT);
					}
				}
			}
		}
		int i = atts.createVersion();
		return i;
	}

	private static void editLinkAttachInfo(Attachment att, JSONObject item) {
		if (Utils.isNotEmptyString(item.getString("size")))
			att.setsSize(Float.valueOf(item.getString("size")));
		att.setRelation("sDocSerialNumber", item.getString("serial_number"));
		att.setRelation("sClassification", item.getString("classification"));
		att.setRelation("sKeywords", item.getString("keywords"));
		att.setRelation("sDescription", item.getString("description"));
	}

	private static void setLinkAttachRalation(Attachment att, String field, String value) {
		if (Utils.isNotEmptyString(value))
			att.setRelation(field, value);
	}

	public static Map<String, Object> queryDocInfoById(boolean isHttps, String host, String fileId, String docVersion)
			throws Exception {
		return (new Docs(null, isHttps)).querFileInfoById(host, fileId, docVersion).toMap();
	}

	public static Map<String, String> queryDocCacheById(String docID) throws Exception {
		Map<String, String> cacheMap = new HashMap<String, String>();
		String result = DocUtils.lock(docID);
		if (result == "failure") {
			cacheMap.put("isLockSuccess", "failure");
			return cacheMap;
		} else {
			cacheMap.put("isLockSuccess", result);
		}
		String queryCacheSql = "select SFILEID,SCACHENAME,SREVISIONCACHENAME from SA_DocNode  where SID='" + docID
				+ "'";
		List<Map<String, String>> table = DBUtils.execQueryforList("system", queryCacheSql);
		if (table.size() > 0) {
			Map row = (Map) table.get(0);
			String cachename = (String) row.get("SCACHENAME");
			if (cachename == null || "".equals(cachename)) {
				cachename = (String) row.get("SFILEID");
			}
			cacheMap.put("sCacheName", cachename);
			cacheMap.put("sRevisionCacheName", (String) row.get("SREVISIONCACHENAME"));
		}
		return cacheMap;
	}

	// 查询文件权限
	public static Object queryPermission(String deptPath, String personId) throws Exception {
		DocPermissions p = new DocPermissions();
		return p.toMap();
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			Map<String, Object> m = (Map<String, Object>) queryPermission("", "system");
			JSONObject json = new JSONObject(m);
			System.out.println(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Document queryDocSearch(Document param) {
		Document result = null;
		/* 兼容原先ui实现的形式 */
		String optStr = getOptionType(param);
		if (optStr.equals("queryDocSearch")) {
			try {
				DocSearch ds = new DocSearch();
				result = ds.queryDocSearch(param);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} /* RDF格式xml */
		return result;
	}

	public static List queryLinkDirAdapter(String rootPath, String subPath, String billID, String process,
			String activity, Boolean isTree) throws Exception {
		DocLinkDirs dl = new DocLinkDirs(billID, rootPath, subPath);
		return dl.toTable();
	}

	public static List docDispatchOptT(Document param) throws Exception {
		List result = null;
		String optStr = getOptionType(param);
		if (optStr.equals("queryAccessRecord")) {
			List<?> list = param.selectNodes("data");
			Element elt = (Element) list.get(0);
			String docID = elt.elementText("doc-id");
			String hasNew = elt.elementText("has-new");
			String hasDownload = elt.elementText("has-download");
			String hasEdit = elt.elementText("has-edit");
			DocLogs dl = new DocLogs(docID);
			if ("true".equals(hasNew) || "true".equals(hasEdit))
				result = dl.getHistorysTable();
			if ("true".equals(hasDownload)) {
				result = dl.getDownloadsTable();
			}
		} else if (optStr.equals("queryNameSpace")) {
			DocServerDefines docNameSpace = DocServerDefines.getInstance();
			result = docNameSpace.toTable();
		} else if (optStr.equals("queryLinkedDoc")) {
			/* 查询与业务数据关联的文档信息 */
			List<?> list = param.selectNodes("data");
			Element elt = (Element) list.get(0);
			String billID = elt.elementText("bill-id");
			DocLinkAttachments dl = new DocLinkAttachments(billID, "", "", true, null);
			result = dl.toTable();
		} else {
			throw new DocRTException("传入操作参数不正确！");
		}
		return result;
	}

	public static String queryHost(Boolean isHttps, String docPath, String urlPattern, Boolean isFormAction)
			throws Exception {
		String docUrl = "#";
		if (!"".equals(docPath)) {
			checkPermission(urlPattern, docPath);
			docUrl = DocUtils.getUrlAssign(DocDBHelper.queryDocHost() + urlPattern);
		}
		return docUrl;
	}

	private static Boolean checkPermission(String urlPattern, String docPath) {
		String fileID = "";
		String docID = "";
		String operater = "";
		try {
			if (urlPattern.indexOf("/repository/file/cache/upload") != -1) {
				operater = "upload";
			} else if (urlPattern.indexOf("/repository/file/download") != -1) {
				fileID = urlPattern.split("/repository/file/download")[1].split("/")[1];
				operater = "download";
			} else if (urlPattern.indexOf("/repository/file/view") != -1) {
				fileID = urlPattern.split("/repository/file/view")[1].split("/")[1];
				operater = "view";
			} else if (urlPattern.indexOf("/repository/file/cache/office/") != -1) {
				fileID = urlPattern.split("/repository/file/cache/office")[1].split("/")[1];
				if (fileID.equals("new")) {
					fileID = "";
					operater = "upload";
				} else {
					operater = "view";
				}

			}
		} catch (NullPointerException e) {
			throw new DocRTException("非法的url请求" + urlPattern, e);
		}
		if (!fileID.equals("")) {
			List table = DocDBHelper.queryDocID(fileID, docPath);
			if (table.size() > 0) {
				Map row = (Map) table.get(0);// "不能对应多条记录");
				docID = (String) row.get("SID");
			}
			if ("download".equals(operater)) {
				Map docinf = DocDBHelper.getDocInfoByFileID(fileID, docPath);
				if (docinf != null) {
					String currentPersionFID = ContextHelper.getPersonMember().getCurrentPersonFullID();
					String currentPersionFName = ContextHelper.getPersonMember().getCurrentPersonName();
					DocDBHelper.addAccessRecord(Utils.getID(), "download", docID, (String) docinf.get("SDOCNAME"),
							Integer.valueOf((String) docinf.get("SDOCLIVEVERSIONID")),
							Float.valueOf((String) docinf.get("SSIZE")), currentPersionFID, currentPersionFName);
				}
			}
		}
		if (!"".equals(docID)) {
			DocPermission dp = new DocPermissions().queryPermissionById(docID, docPath);
			if (Utils.isNull(dp)) {
				return false;
			}
			int access = dp.getsAccess();
			if ("upload".equals(operater)) {
				return access % 512 >= 256;
			} else if ("download".equals(operater)) {
				return access % 8 >= 4;
			} else if ("view".equals(operater)) {
				return access % 4 >= 2;
			} else if ("edit".equals(operater)) {
				return access % 1024 >= 512;
			} else if ("".equals(operater)) {
				logger.info("扩展的url：" + urlPattern + "暂不判断权限");
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	private static String getOptionType(Document param) {
		String xpath = "//data/operate";
		List<?> list = param.selectNodes(xpath);
		Element elt = (Element) list.get(0);
		return elt.getText();
	}

	public static String checkSession(String key, String path) throws Exception {
		DesUtils des = new DesUtils();
		String deKey = des.decrypt(key);
		String[] a = deKey.split(",");
		if (!Utils.isEmptyString(a[0])) {
			a[0] = a[0].replace(":80/", "/");
		}
		if (Utils.isEmptyString(path) || !path.equals(a[0])) {
			throw new DocRTException("文档服务请求路径验证不安全！");
		}
		long ss = (new java.util.Date().getTime() - Long.valueOf(a[1])) / 1000;
		if (ss > 60) {
			throw new DocRTException("文档服务请求路径验证不安全！");
		}
		return "";
	}

	public static int deleteDocNameSpace(Boolean isHttps, String sID)
			throws UnsupportedEncodingException, DocumentException, Exception, ModelException {
		Utils.check(Utils.isNotEmptyString(sID), "deleteDocNameSpace的sID参数不能为空！");
		DocServerDefines ds = DocServerDefines.getInstance();
		ds.remove(sID);
		Docs docs = new Docs(new DocPermissions(), isHttps);
		docs.query("", "", "", "", "sNameSpace='" + sID + "'");
		for (Iterator<Doc> it = docs.getIterator(); it.hasNext();) {
			Doc doc = it.next();
			docs.remove(doc);
		}
		docs.commitFile();
		docs.commitData();
		return 1;// ds.save();
	}

	public static String queryCommentFileContent(Boolean isHttps, String docPath, String fileID, String docVersionID)
			throws Exception {
		return DocLogs.getFileComment(isHttps, docPath, fileID, docVersionID);
	}

	public static void deleteVersion(String sDocPath, String sFileID, String sLogID, String docVersion, Boolean isHttps)
			throws Exception {
		DocLogs.deleteVersion(isHttps, sDocPath, sFileID, sLogID, docVersion);
	}
}
