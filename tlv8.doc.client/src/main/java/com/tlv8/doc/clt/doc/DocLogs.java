package com.tlv8.doc.clt.doc;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;

@SuppressWarnings("rawtypes")
public class DocLogs {
	private String sDocId;
	private List<?> container;
	private boolean isInit = false;
	private Map<String, DocLog> logs = new HashMap<String, DocLog>();

	public DocLogs() {

	}

	public DocLogs(String docID) {
		sDocId = docID;
		loadLogs(docID);
	}

	public String getsDocId() {
		return sDocId;
	}

	private DocLogs loadLogs(String docID) {
		container = DocDBHelper.queryAccessRecord(docID, false, false, false,
				null);
		for (int i = 0; i < container.size(); i++) {
			Docinfo r = new Docinfo((Map) container.get(i));
			logs.put(r.getString("SID"), new DocLog(r, this));
		}
		isInit = true;
		return this;
	}

	public DocLogs refresh() {
		if (Utils.isNotEmptyString(this.sDocId))
			loadLogs(sDocId);
		return this;
	}

	public List<DocLog> getHistorys() {
		List<DocLog> historyLog = new ArrayList<DocLog>();

		for (Entry<String, DocLog> entry : logs.entrySet()) {
			DocLog log = entry.getValue();
			if ("edit".equals(log.getsAccessType())
					|| "new".equals(log.getsAccessType())) {
				historyLog.add(log);
			}
		}

		return historyLog;
	}

	@SuppressWarnings("unchecked")
	public List getHistorysTable() {
		List t = container;
		for (Entry<String, DocLog> entry : logs.entrySet()) {
			DocLog log = entry.getValue();
			if (!("edit".equals(log.getsAccessType()) || "new".equals(log
					.getsAccessType()))) {
				int index = getArrayMapIndex(t, log.getsID());
				if (index != -1)
					t.remove(index);
			}
		}
		return t;
	}

	public int getArrayMapIndex(List<Map<String, ?>> li, String sId) {
		try {
			for (int i = 0; i < li.size(); i++) {
				Map<String, ?> m = li.get(i);
				if (sId.equals(m.get("SID"))) {
					return i;
				}
			}
		} catch (Exception e) {
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	public List getDownloadsTable() {
		List t = container;
		for (Entry<String, DocLog> entry : logs.entrySet()) {
			DocLog log = entry.getValue();
			if (!"download".equals(log.getsAccessType())) {
				int index = getArrayMapIndex(t, log.getsID());
				if (index != -1)
					t.remove(index);
			}
		}
		return t;
	}

	public List<DocLog> getDownloads() {
		List<DocLog> downloadLog = new ArrayList<DocLog>();
		if (Utils.isNull(logs))
			loadLogs(sDocId);

		for (Entry<String, DocLog> entry : logs.entrySet()) {
			DocLog log = entry.getValue();
			if ("download".equals(log.getsAccessType())) {
				downloadLog.add(log);
			}
		}
		return downloadLog;
	}

	public Iterator<DocLog> getDocLogsIterator() {
		return logs.values().iterator();
	}

	public int size() {
		return logs.size();
	}

	@SuppressWarnings("unlikely-arg-type")
	public DocLog getLog(int i) {
		return logs.get(i);
	}

	public DocLogs addLog(String sID, String sAccessType, String sDocID,
			String sDocName, int sDocVersionID, float sSize) throws Exception {
		if (!isInit) {
			container = DocDBHelper.initDocLog();
			isInit = true;
		}
		Docinfo r = (container.size() > 0) ? new Docinfo((Map) container.get(0))
				: new Docinfo();
		sID = Utils.isEmptyString(sID) ? CommonUtils.createGUID() : sID;
		this.sDocId = sDocID;
		r.setString("SID", sID);
		r.setString("SACCESSTYPE", sAccessType);
		r.setString("SDOCID", sDocID);
		r.setString("SDOCNAME", sDocName);
		if (Utils.isNotNull(sDocVersionID))
			r.setInteger("SDOCVERSIONID", sDocVersionID);
		r.setFloat("SSIZE", sSize);
		r.setString("SPERSONFID", ContextHelper.getPersonMember()
				.getCurrentPersonFullID());
		r.setString("SPERSONNAME", ContextHelper.getPersonMember()
				.getCurrentPersonName());
		r.setDateTime("STIME", CommonUtils.getCurrentDateTime());
		r.setInt("VERSION", 0);
		logs.put(r.getString("SID"), new DocLog(r, this));
		DocDBHelper.addAccessRecord(sID, sAccessType, sDocID, sDocName,
				sDocVersionID, sSize, r.getString("SPERSONFID"),
				r.getString("SPERSONNAME"));
		return this;
	}

	public int save() {
		// container.save(model);
		return 1;
	}

	public static DocLog getHistoryLogByVersion(String docID,
			String docVersionID) {
		List table = DocDBHelper.queryAccessRecord(docID, true, false, true,
				null);
		if (!table.isEmpty()) {
			Docinfo r = new Docinfo((Map) table.get(0));
			return new DocLog(r, null);
		}
		return null;
	}

	public static String getFileComment(boolean isHttps, String docPath,
			String fileID, String docVersionID) throws Exception {
		String host = DocDBHelper.queryDocHost();
		String url = host + "/repository/file/download/" + fileID + "/"
				+ docVersionID + "/comment";
		String result = "";
		try {
			result = DocUtils.excutePostAction(url);
		} catch (Exception e) {
			return "{}";
		}
		return result;
	}

	public static void deleteVersion(Boolean isHttps, String docPath,
			String fileID, String sLogID, String docVersionID)
			throws UnsupportedEncodingException, Exception {
		try {
			Utils.check(Utils.isNotEmptyString(docVersionID), "非法的docVersion");
		} catch (ModelException e) {
			e.printStackTrace();
		}
		String host = DocDBHelper.queryDocHost();
		String url = host + "/repository/file/delete";
		StringBuffer sb = new StringBuffer();
		sb.append("<data>");
		sb.append("<file-id>");
		sb.append(fileID);
		sb.append("</file-id>");
		sb.append("<doc-version>");
		sb.append(docVersionID);
		sb.append("</doc-version>");
		sb.append("</data>");
		Document result = DocUtils.excutePostAction(url,
				new ByteArrayInputStream(sb.toString().getBytes("UTF-8")));
		List table = DocDBHelper.queryDocID(fileID, docPath);
		String docID = "";
		if (!table.isEmpty()) {
			Docinfo row = new Docinfo((Map) table.get(0));
			docID = row.getString("SID");
		}
		try {
			Utils.check(Utils.isNotEmptyString(docID), "fileID:" + fileID
					+ "在文档中心无对应的记录");
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DocLogs dls = new DocLogs(docID);
		if (Boolean.parseBoolean(result.selectSingleNode("//flag").getText())) {
			String lastVersionID = result.selectSingleNode("//doc-version-id")
					.getText();
			String liveVersionID = result.selectSingleNode(
					"//doc-live-version-id").getText();
			if ("-1".equals(docVersionID)) {
				for (Entry<String, DocLog> entry : dls.logs.entrySet()) {
					String docVersion = entry.getValue().getsDocVersionID()
							.toString();
					if (!(docVersion.equals(lastVersionID) || docVersion
							.equals(liveVersionID)))
						entry.getValue().getRow().setState(ModifyState.DELETE);

				}
				dls.save();
			} else if (!(docVersionID.equals(lastVersionID) || docVersionID
					.equals(liveVersionID))) {
				DocLog dl = dls.logs.get(sLogID);
				dl.getRow().setState(ModifyState.DELETE);
				dls.save();
			} else if (docVersionID.equals(lastVersionID)
					|| docVersionID.equals(liveVersionID)) {
				throw new DocRTException("不能删除最终版本");
			}
		} else {
			throw new DocRTException(result.asXML());
		}
	}

}
