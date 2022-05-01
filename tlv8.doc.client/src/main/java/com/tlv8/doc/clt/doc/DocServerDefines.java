package com.tlv8.doc.clt.doc;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.tlv8.doc.clt.doc.transform.TransformConfig;

@SuppressWarnings({ "rawtypes" })
public class DocServerDefines {

	private List nameSpaces;
	private Map<String, DocServerDefine> docServers = new ConcurrentHashMap<String, DocServerDefine>();
	@SuppressWarnings("unused")
	private final static String DOCNameSPACE_CONCEPT = "SA_DocNameSpace";
	public static Logger logger = Logger.getLogger(DocServerDefines.class);
	private Boolean isLogicDelete = true;

	private DocServerDefines() {
		loadNameSpace();
	}

	public static DocServerDefines getInstance() {
		return new DocServerDefines();
	}

	public DocServerDefines(List nameSpaces) {
		for (int i = 0; i < nameSpaces.size(); i++) {
			Docinfo r = new Docinfo((Map) nameSpaces.get(i));
			DocServerDefine docServer = new DocServerDefine((Docinfo) r, this);
			docServers.put(r.getString("SID"), docServer);
		}
	}

	/*
	 * 获取分布式文档服务器的名空间
	 */
	private void loadNameSpace() {
		nameSpaces = DocDBHelper.queryNameSpace();
		for (int i = 0; i < nameSpaces.size(); i++) {
			Docinfo r = new Docinfo((Map) nameSpaces.get(i));
			DocServerDefine docServer = new DocServerDefine(r, this);
			// System.out.println(docServer);
			docServers.put(r.getString("SID"), docServer);
		}
	}

	public void refresh() {
		nameSpaces = null;
		docServers = null;
		loadNameSpace();
	}

	public DocServerDefine add(String sID, String sDisplayName, String sUrl,
			String sAccessMode) {
		if (Utils.isEmptyString(sID))
			sID = CommonUtils.createGUID();
		Docinfo r = new Docinfo((Map) nameSpaces.get(0));
		r.setString("SID", sID);
		r.setString("sDisplayName", sDisplayName);
		r.setString("sUrl", sUrl);
		r.setString("sAccessMode", sAccessMode);
		r.setInt("version", 0);
		r.setInt("sFlag", 1);
		DocServerDefine dsd = new DocServerDefine(r, this);
		docServers.put(sID, dsd);
		return dsd;
	}

	public DocServerDefine getDocServerByPath(String docPath) {
		// String rootDocId = DocUtils.getRootId(docPath);
		Docinfo inf = DocDBHelper.queryNameSpaceByPath(docPath);
		DocServerDefine result = new DocServerDefine(inf, this);
		return result;
	}

	public DocServerDefines remove(String sID) {
		if (isLogicDelete) {
			DocServerDefine dsd = get(sID);
			if (Utils.isNotNull(dsd))
				dsd.setsFlag(0);
		}
		docServers.remove(sID);
		return this;
	}

	public DocServerDefines remove(DocServerDefine docServerDefine) {
		String id = docServerDefine.getsID();
		remove(id);
		return this;
	}

	public DocServerDefine get(String sID) {
		return docServers.get(sID);
	}

	public int size() {
		return docServers.size();
	}

	public Iterator<DocServerDefine> getIterator() {
		return docServers.values().iterator();
	}

	public List toTable() {
		return nameSpaces;
	}

	public void readerFromJson(Object arg0, TransformConfig arg1) {

	}

	public void reader(Element arg0, TransformConfig arg1) {

	}

}
