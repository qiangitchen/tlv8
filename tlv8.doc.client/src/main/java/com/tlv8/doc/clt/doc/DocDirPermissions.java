package com.tlv8.doc.clt.doc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tlv8.base.db.DBUtils;

@SuppressWarnings("rawtypes")
public class DocDirPermissions {
	private List container;
	private Map<String, DocPermission> pers = new ConcurrentHashMap<String, DocPermission>();

	public static Logger logger = LoggerFactory.getLogger(Docs.class);

	public DocDirPermissions() {
	}

	public DocDirPermissions(List table) {
		this.container = table;
	}

	public DocDirPermissions query(String concept, String idColumn, String select, String from, String condition,
			List<DataPermission> range, String filter, Boolean distinct, int offset, int limit, String columns,
			String orderBy, String aggregate, String aggregateColumns, Map<String, Object> variables, String dataModel,
			String fnModel) {
		container = BizData.query(concept, idColumn, select, from, condition, range, filter, distinct, offset, limit,
				columns, orderBy, aggregate, aggregateColumns, variables, dataModel, fnModel);
		for (int i = 0; i < container.size(); i++) {
			Docinfo r = new Docinfo((Map) container.get(i));
			pers.put(r.getString("SA_DocAuth"), new DocPermission(r, this));
		}
		return this;
	}

	public DocDirPermissions queryByPath(String docPath) {
		return query("SA_DocAuth", "SA_DocAuth", "SA_DocAuth.*", "SA_DocAuth SA_DocAuth", null, null,
				"(SA_DocAuth.sDocPath='" + docPath + "')", false, 0, -1,
				"sDocPath,sAuthorizerFID,sAuthorizerName,sAuthorizerDeptName,sAuthorizeeFID,sAuthorizeeName,sAuthorizeeDeptName,sGrantTime,sAccess,sScope,version,SA_DocAuth",
				null, null, null, null, "/SA/doc/data", null);
	}

	public DocPermission add(String sID, String sDocPath, String sAuthorizeeFID, String sAuthorizeeName, int sAccess) {
		if (Utils.isNull(container)) {
			container = DocDBHelper.initDocAuth();
		}
		if (Utils.isNotEmptyString(sID))
			sID = CommonUtils.createGUID();

		Docinfo r = new Docinfo((Map) container.get(0));
		r.setString("SA_DocAuth", sID);
		r.setString("sDocPath", sDocPath);
		r.setInt("sAccess", sAccess);
		r.setString("sAuthorizeeFID", sAuthorizeeFID);
		r.setString("sAuthorizeeName", sAuthorizeeName);

		DocPermission p = new DocPermission(r, this);
		pers.put(sID, p);
		return p;
	}

	public int save() {
		for (int i = 0; i < container.size(); i++) {
			Docinfo row = new Docinfo((Map) container.get(i));
			String str = row.getString("sDocPath");
			if (str.equals("/")) {
				if (Integer.parseInt(row.getValue("sAccess").toString()) % 32768 < 16384
						&& Integer.parseInt(row.getOldValue("sAccess").toString()) % 32768 > 16384) {
					checkRootPermission();
				}
			}
		}
		return 1;
	}

	private void checkRootPermission() {
		String ksql = "select SA_DocAuth,SA_DocAuth.sAccess,SA_DocAuth.sAuthorizeeFID from SA_DocAuth SA_DocAuth where mod(SA_DocAuth.sAccess,32768) >16384 and SA_DocAuth.sDocPath ='/'";
		List<?> table = new ArrayList();
		try {
			table = DBUtils.execQueryforList("system", ksql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (table.size() == 0) {
			throw new DocRTException("根节点必须保留至少一个管理权限");
		}
	}

	public DocDirPermissions remove(String sID) {
		pers.remove(sID);
		return this;
	}

	public DocDirPermissions remove(Doc doc) {
		String id = doc.getsID();
		remove(id);
		return this;
	}

	public List toTable() {
		return container;
	}

	public Map<String, DocPermission> toMap() {
		return pers;
	}

	public int size() {
		return pers.entrySet().size();
	}

	public DocPermission get(String sID) {
		return pers.get(sID);
	}

	public Iterator<DocPermission> getIterator() {
		return pers.values().iterator();
	}

}
