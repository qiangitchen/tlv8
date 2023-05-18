package com.tlv8.doc.clt.doc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.ibatis.session.SqlSession;
import org.dom4j.Document;
import org.dom4j.Element;

import com.tlv8.base.db.DBUtils;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.controller.UserController;
import com.tlv8.system.utils.ContextUtils;

/**
 * 文档数据相关操作
 * 
 * @author chenqian
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DocDBHelper {

	private static int parserInt(Object v) {
		int result = 0;
		if (v instanceof BigDecimal) {
			result = ((BigDecimal) v).intValue();
		} else if (v instanceof Integer) {
			result = ((Integer) v).intValue();
		} else if (v instanceof Long) {
			result = ((Long) v).intValue();
		} else {
			result = Integer.parseInt(v.toString());
		}
		return result;
	}

	private static String createQueryString(String docID, String docPath, String[] selectArr, String orderBy,
			String custom) {
		String whereStr = "";
		String selectStr = "";
		String oderByStr = "";
		if (docID != null && docID.length() > 0) {
			whereStr = whereStr + " and SA_DocNode.sID  ='" + docID + "' ";
		}
		if (docPath != null && docPath.length() > 0) {
			whereStr = whereStr + " and SA_DocNode.sDocPath  ='" + docPath + "'";
		}

		if (custom != null && custom.length() > 0) {
			if (custom.indexOf("SA_DocNode") > -1)
				whereStr = " and " + custom;
			else {
				String[] whereArr = custom.split(",");
				for (int i = 0; i < whereArr.length; i++) {
					if (i == whereArr.length - 1) {
						whereStr = whereStr + " and SA_DocNode." + whereArr[i];
						break;
					}
					whereStr = whereStr + " and SA_DocNode." + whereArr[i];
				}
			}
		}

		for (int i = 0; i < selectArr.length; i++) {
			if (i == selectArr.length - 1) {
				selectStr = selectStr + " SA_DocNode." + selectArr[i];
				break;
			}
			selectStr = selectStr + " SA_DocNode." + selectArr[i] + ",";
		}

		if (orderBy != null && orderBy.length() > 0 && !orderBy.equals("undefined")) {
			oderByStr = " order by SA_DocNode." + orderBy;
		}

		String sql = " select sID, " + selectStr + "  from SA_DocNode SA_DocNode where 1=1 and SA_DocNode.sFlag = 1 "
				+ whereStr + oderByStr;

		return sql;
	}

	/**
	 * 获取文档信息
	 * 
	 * @param billIDs
	 * @param process
	 * @param activity
	 * @return List
	 */
	public static List<?> queryDocLink(String billIDs, String process, String activity) {
		String docIDsQuery = "select SA_DocLink.sDocID,SA_DocLink.sOwnerID,SA_DocNode.sParentID,SA_DocNode.sDocName,SA_DocNode.sSequence,SA_DocNode.sSize,SA_DocNode.sKind,SA_DocNode.sDocPath,SA_DocNode.sDocDisplayPath,SA_DocNode.sCreatorFID,"
				+ "SA_DocNode.sCreatorName,SA_DocNode.sCreatorDeptName,SA_DocNode.sCreateTime,SA_DocNode.sEditorFID,SA_DocNode.sEditorName,SA_DocNode.sEditorDeptName,"
				+ "SA_DocNode.sLastWriterFID,SA_DocNode.sLastWriterName,SA_DocNode.sLastWriterDeptName,SA_DocNode.sLastWriteTime,SA_DocNode.sFileID,SA_DocNode.sDescription,"
				+ "SA_DocNode.sDocLiveVersionID,SA_DocNode.version,SA_DocNode.sFinishTime,SA_DocNode.sClassification,SA_DocNode.sKeywords,SA_DocNode.sDocSerialNumber,SA_DocNode.sNameSpace,SA_DocNode.sFlag,SA_DocNode.sCacheName,SA_DocNode.sRevisionCacheName from SA_DocLink SA_DocLink join SA_DocNode SA_DocNode on  SA_DocLink.sDocID = SA_DocNode and SA_DocLink.sProcess = '"
				+ process + "' and SA_DocLink.sActivity = '" + activity + "' and SA_DocLink.sOwnerID in ('" + billIDs
				+ "')";
		List<?> table = null;
		try {
			table = DBUtils.execQueryforList("system", docIDsQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 查询文档信息
	 * 
	 * @param docId
	 * @param docPath
	 * @param pattern
	 * @param orderBy
	 * @param custom
	 * @return List
	 */
	public static List<Map<String, String>> queryDoc(String docId, String docPath, String pattern, String orderBy,
			String custom) {
		if (Utils.isEmptyString(pattern)) {
			pattern = "SID,SPARENTID,SDOCNAME,SSEQUENCE,SSIZE,SKIND,SDOCPATH,SDOCDISPLAYPATH,SCREATORFID,"
					+ "SCREATORNAME,SCREATORDEPTNAME,SCREATETIME,SEDITORFID,SEDITORNAME,SEDITORDEPTNAME,"
					+ "SLASTWRITERFID,SLASTWRITERNAME,SLASTWRITERDEPTNAME,SLASTWRITETIME,SFILEID,SDESCRIPTION,"
					+ "SDOCLIVEVERSIONID,VERSION,SFINISHTIME,SCLASSIFICATION,SKEYWORDS,SDOCSERIALNUMBER,SNAMESPACE,SFLAG,SCACHENAME,SREVISIONCACHENAME";
		}
		String[] selectArr = pattern.split(",");
		String sql = createQueryString(docId, docPath, selectArr, orderBy, custom);

		List m = new ArrayList();
		try {
			m = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return m;
	}

	/**
	 * 根据文件ID查询文档信息
	 * 
	 * @param fileID
	 * @param docPath
	 * @return List
	 */
	public static List queryDocID(String fileID, String docPath) {
		String sql = "	select SA_DocNode.sID from SA_DocNode SA_DocNode where SA_DocNode.sFileID ='" + fileID + "'";
		List table = null;
		try {
			table = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}

	// Map格式
	protected static String addAccessRecord(Map<String, String> item) {
		String ksql = "insert into SA_DocLog(sID,sDocID,sPersonFID,sPersonName,sDeptName,sTime,sAccessType,sDocName,sDocVersionID,sSize,version) "
				+ " values ('" + item.get("sID") + "','" + item.get("sDocID") + "','" + item.get("sPersonFID") + "','"
				+ item.get("sPersonName") + "','" + item.get("sDeptName") + "',:curtime,'" + item.get("sAccessType")
				+ "','" + item.get("sDocName") + "'," + Integer.valueOf(item.get("sDocVersionID")) + ","
				+ CommonUtils.toFloat(item.get("sSize")) + ",0)";
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			ksql = ksql.replace(":curtime", "sysdate");
		} else if (DBUtils.IsMySQLDB("system")) {
			ksql = ksql.replace(":curtime", "now()");
		} else {
			ksql = ksql.replace(":curtime", "getdate()");
		}
		try {
			return DBUtils.execUpdateQuery("system", ksql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据文档ID获取版本号
	 * 
	 * @param docID
	 * @return String
	 */
	public static String getLogVersionByDocID(String docID) {
		int versionID = 1;
		String sql = "select nvl(SDOCLIVEVERSIONID,1) SDOCVERSIONID from sa_docnode where sID='" + docID + "'";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				versionID = Integer.valueOf((String) m.get("SDOCVERSIONID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return String.valueOf(versionID + 1);
	}

	/**
	 * 添加文档历史
	 * 
	 * @param sID
	 * @param sAccessType
	 * @param sDocID
	 * @param sDocName
	 * @param sDocVersionID
	 * @param sSize
	 * @param sPersonFID
	 * @param sPersonName
	 * @return boolean
	 */
	// String格式
	public static boolean addAccessRecord(String sID, String sAccessType, String sDocID, String sDocName,
			int sDocVersionID, float sSize, String sPersonFID, String sPersonName) {
		String sql = "select SID from SA_DocLog where sDocID = '" + sDocID + "' and sDocVersionID = " + sDocVersionID
				+ " and sAccessType ='" + sAccessType + "'";
		try {
			List li = DBUtils.execQueryforList("system", sql);
			if (li.size() > 0) {
				String uSql = "update SA_DocLog set sPersonFID='" + sPersonFID + "',sPersonName='" + sPersonName
						+ "',sTime=:curtime,sDocName='" + sDocName + "',sSize=" + sSize
						+ ",version=version+1 where sDocID='" + sDocID + "' and sDocVersionID='" + sDocVersionID + "'";
				if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
					uSql = uSql.replace(":curtime", "sysdate");
				} else if (DBUtils.IsMySQLDB("system")) {
					uSql = uSql.replace(":curtime", "now()");
				} else {
					uSql = uSql.replace(":curtime", "getdate()");
				}
				DBUtils.execUpdateQuery("system", uSql);
				return true;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String ksql = "insert into SA_DocLog(sID,sDocID,sPersonFID,sPersonName,sTime,sAccessType,sDocName,sDocVersionID,sSize,version) "
				+ " values ('" + sID + "','" + sDocID + "','" + sPersonFID + "','" + sPersonName + "',:curtime,'"
				+ sAccessType + "','" + sDocName + "'," + sDocVersionID + "," + sSize + ",0)";
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
			ksql = ksql.replace(":curtime", "sysdate");
		} else if (DBUtils.IsMySQLDB("system")) {
			ksql = ksql.replace(":curtime", "now()");
		} else {
			ksql = ksql.replace(":curtime", "getdate()");
		}
		try {
			DBUtils.execInsertQuery("system", ksql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 查询操作记录
	 * 
	 * @param docID
	 * @param hasNew
	 * @param hasDownload
	 * @param hasEdit
	 * @param docVersionID
	 * @return
	 */
	// 获取操作记录
	public static List queryAccessRecord(String docID, Boolean hasNew, Boolean hasDownload, Boolean hasEdit,
			Integer docVersionID) {
		String ksql = " select SA_DocLog.SID,SA_DocLog.SDOCVERSIONID,SA_DocLog.SDOCNAME,SA_DocLog.SSIZE,SA_DocLog.SPERSONNAME,"
				+ "SA_DocLog.SDEPTNAME,SA_DocLog.STIME,SA_DocLog.SDOCID,SA_DocLog.SPERSONFID,"
				+ "SA_DocLog.SACCESSTYPE,SA_DocLog.VERSION from SA_DocLog SA_DocLog where SA_DocLog.sDocID='" + docID
				+ "'";
		String whereStr = "";
		if (hasNew || hasDownload || hasEdit) {
			if (hasNew) {
				whereStr += " 'new'";
			}
			if (hasDownload) {
				whereStr += whereStr.length() == 0 ? "'download'" : " ,'download'";
			}
			if (hasEdit) {
				whereStr += whereStr.length() == 0 ? "'edit'" : ",'edit'";
			}
			ksql += " and SA_DocLog.sAccessType in (" + whereStr + ")";
		}

		if (Utils.isNotNull(docVersionID))
			ksql += " and SA_DocLog.sDocVersionID = " + docVersionID;

		ksql += " order by SA_DocLog.sDocVersionID";

		List table = null;
		try {
			table = DBUtils.execQueryforList("system", ksql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return table;
	}

	/**
	 * 根据文件ID获取文档信息
	 * 
	 * @param isHttps
	 * @param docPath
	 * @param fileId
	 * @param docVersion
	 * @return Map&lt;String, Object&gt;
	 * @throws Exception
	 */
	public static Map<String, Object> queryDocInfoById(boolean isHttps, String docPath, String fileId,
			String docVersion) throws Exception {
		String host = DocDBHelper.queryDocHost();
		String url = host + "/repository/fileinfo/" + fileId + "/" + docVersion;
		Document responseDoc = DocUtils.excutePostAction(url, null);

		Element rootElement = responseDoc.getRootElement();
		Map<String, Object> map = new HashMap<String, Object>();
		if ("last".equals(docVersion.toLowerCase())) {
			Element ns_document = (Element) rootElement.selectSingleNode("//*[local-name()= 'document']");
			map.put("fileId", fileId);
			map.put("docName", ns_document.attributeValue("name"));
		} else {
			Element ns_document = (Element) rootElement.selectSingleNode("//*[local-name()= 'version']");
			map.put("fileId", fileId);
			map.put("docName", ns_document.attributeValue("documentName"));
		}
		List<?> partsEle = rootElement.selectNodes("//*[local-name()= 'parts']/*[local-name()= 'part']");
		Map<String, Object> partList = new HashMap<String, Object>();
		int i = 0;
		for (Iterator<?> it = partsEle.iterator(); it.hasNext();) {
			Element part = (Element) it.next();
			Map<String, String> item = new HashMap<String, String>();
			String type = part.attributeValue("typeId");
			item.put("typeId", type);
			item.put("size", part.attributeValue("size"));
			item.put("mimeType", part.attributeValue("mimeType"));
			item.put("dataChangedInVersion", part.attributeValue("dataChangedInVersion"));
			partList.put("part_" + type, item);
			i++;
		}
		partList.put("length", i);
		map.put("parts", partList);
		map.put("queryFlag", "true");

		return map;
	}

	/**
	 * 删除文档链接
	 * 
	 * @param sOwnerID
	 * @param sDocID
	 * @return String
	 * @throws Exception
	 */
	public static String deleteDocLink(String sOwnerID, String sDocID) throws Exception {
		String affactRow;
		String delDocLinkKsql = "delete from SA_DocLink SA_DocLink where " + "  SA_DocLink.sOwnerID='" + sOwnerID
				+ "' and SA_DocLink.sDocID='" + sDocID + "' ";

		affactRow = DBUtils.execUpdateQuery("system", delDocLinkKsql);

		if (!"success".equals(affactRow))
			throw new Exception("删除DocLink失败");
		return affactRow;
	}

	/**
	 * 添加文档链接
	 * 
	 * @param sOwnerID
	 * @param sProcess
	 * @param sActivity
	 * @param sDocID
	 * @return int
	 * @throws Exception
	 */
	public static int insertDocLink(String sOwnerID, String sProcess, String sActivity, String sDocID)
			throws Exception {
		String newDocLinkKsql = "insert into SA_DocLink SA_DocLink (SA_DocLink.sID,SA_DocLink.version,SA_DocLink.sOwnerID,SA_DocLink.sProcess,SA_DocLink.sActivity,SA_DocLink.sDocID) values ("
				+ "'" + CommonUtils.createGUID() + "',0,'" + sOwnerID + "','" + sProcess + "', '" + sActivity + "','"
				+ sDocID + "' )";
		DBUtils.execUpdateQuery("system", newDocLinkKsql);
		return 1;
	}

	/**
	 * 文档数据保存
	 * 
	 * @param table
	 * @param concept
	 * @param dataModel
	 * @param fnModel
	 * @param insertRange
	 * @param deleteRange
	 * @param updateRange
	 * @param readOnly
	 * @param notNull
	 * @return int
	 * @throws Exception
	 */
	public static int docNodeSave(List<?> table, String concept, String dataModel, String fnModel,
			List<DataPermission> insertRange, List<DataPermission> deleteRange, List<DataPermission> updateRange,
			String readOnly, String notNull) throws Exception {
		return BizData.save(table, concept, insertRange, deleteRange, updateRange, readOnly, notNull, dataModel,
				fnModel);
	}

	/**
	 * 创建目录
	 * 
	 * @param docId
	 * @param fields
	 * @return boolean
	 * @throws Exception
	 * @throws ModelException
	 */
	public static boolean createDir(String docId, Map<String, Object> fields) throws Exception, ModelException {
		Utils.check(Utils.isNotEmptyString(docId), "createDir的DocId参数不能为空！");
		String ksql = "select SA_DocNode.* from SA_DocNode SA_DocNode where 1 = 2";
		String currentPersionFID = ContextHelper.getPersonMember().getCurrentPersonFullID();
		String currentPersionFName = ContextHelper.getPersonMember().getCurrentPersonName();
		List docNodeTable = DBUtils.execQueryforList("system", ksql);
		Docinfo row = new Docinfo((Map) docNodeTable.get(0));
		row.setString("SA_DocNode", docId);
		row.setString("sKind", "dir");
		row.setString("sCreatorFID", currentPersionFID);
		row.setString("sCreatorName", currentPersionFName);
		row.setDateTime("sCreateTime", CommonUtils.getCurrentDateTime());
		row.setString("sLastWriterFID", currentPersionFID);
		row.setString("sLastWriterName", currentPersionFName);
		row.setDateTime("sLastWriteTime", CommonUtils.getCurrentDateTime());
		for (Map.Entry<String, Object> field : fields.entrySet()) {
			String fieldName = field.getKey();
			Object fieldValue = field.getValue();
			String fieldType = fieldValue.getClass().getName();
			if (fieldType.equals("java.lang.String")) {
				row.setString(fieldName, (String) fieldValue);
			} else if (fieldType.equals("java.lang.Integer")) {
				row.setInteger(fieldName, (Integer) fieldValue);
			} else if (fieldType.equals("java.sql.Timestamp")) {
				row.setDateTime(fieldName, (Timestamp) fieldValue);
			} else {
				throw new Exception("createDir:fields参数中数据类型不匹配!");
			}
		}
		row.setString("sFlag", "1");
		row.setInteger("version", 0);
		docNodeTable = new ArrayList();
		docNodeTable.add(row);
		int affactRow = docNodeSave(docNodeTable, DocUtils.DOCNODE_CONCEPT, DocUtils.DATA_MODEL, null, null, null, null,
				null, null);
		return affactRow > 0 ? true : false;
	}

	/**
	 * 查询文档空间配置
	 * 
	 * @return List
	 */
	public static List queryNameSpace() {
		String sql = "select SA_DocNameSpace.sID SID,SA_DocNode.sID DOCNODE, SA_DocNameSpace.sDisplayName ,SA_DocNameSpace.sUrl,SA_DocNameSpace.sAccessMode,SA_DocNameSpace.sFlag,SA_DocNameSpace.version "
				+ " from SA_DocNameSpace SA_DocNameSpace left JOIN SA_DocNode SA_DocNode on SA_DocNameSpace.sid = SA_DocNode.sNameSpace where SA_DocNameSpace.sFlag = '1' and SA_DocNode.sFlag = '1'";

		List nameSpaces = new ArrayList();
		try {
			nameSpaces = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nameSpaces;
	}

	/**
	 * 获取文档服务地址
	 * 
	 * @return String
	 */
	public static String queryDocHost() {
		String sql = "select SURL from SA_DocNameSpace where SFLAG = '1'";
		String host = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getSession("system").getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if (rs.next()) {
				host = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return host;
	}

	/**
	 * 根据文档路径获取文档服务地址
	 * 
	 * @param docPath
	 * @return Docinfo
	 * @see com.tlv8.doc.clt.doc.Docinfo
	 */
	public static Docinfo queryNameSpaceByPath(String docPath) {
		String sql = "select SA_DOCNAMESPACE.SID SA_DOCNAMESPACE,SA_DOCNODE.SID SA_DOCNODE, SA_DOCNAMESPACE.SDISPLAYNAME ,SA_DOCNAMESPACE.SURL,SA_DOCNAMESPACE.SACCESSMODE,SA_DOCNAMESPACE.SFLAG,SA_DOCNAMESPACE.VERSION "
				+ " from SA_DocNameSpace SA_DocNameSpace, SA_DocNode SA_DocNode where SA_DocNameSpace.sFlag = '1'  and (SA_DocNode.sDocDisplayPath like '"
				+ docPath + "' or SA_DocNode.sDocPath like '" + docPath + "%')";
		List nameSpaces = new ArrayList();
		try {
			nameSpaces = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (nameSpaces.size() > 0) {
			return new Docinfo((Map) nameSpaces.get(0));
		}
		return new Docinfo();
	}

	public static List queryLinkDoc(String sOwnerID) {
		String sql = "select SA_DocNode.sEditorFID,SA_DocNode,SA_DocNode.sDocName,SA_DocNode.sSize,"
				+ " SA_DocNode.sCreatorName,SA_DocNode.sCreateTime,SA_DocNode.sLastWriterName,SA_DocNode.sLastWriteTime,"
				+ " SA_DocNode.sCreatorDeptName,SA_DocNode.sLastWriterDeptName,SA_DocNode.sEditorName,SA_DocNode.sEditorDeptName,"
				+ " SA_DocNode.sParentID,SA_DocNode.sKind,SA_DocNode.sDocPath,SA_DocNode.sDocDisplayPath,SA_DocNode.sFileID,"
				+ " SA_DocNode.sDocSerialNumber,SA_DocNode.sKeywords,SA_DocNode.sDescription,SA_DocNode.sClassification,"
				+ " SA_DocNode.sFinishTime,SA_DocNode.sDocLiveVersionID,SA_DocNode.version,SA_DocLink.sProcess,"
				+ " SA_DocLink.sActivity,SA_DocLink.sDocID "
				+ " from SA_DocLink SA_DocLink join SA_DocNode SA_DocNode on SA_DocLink.sDocID=SA_DocNode.sID "
				+ " where SA_DocNode.sKind <> 'dir' and SA_DocLink.sOwnerID='" + sOwnerID + "'";

		List linkedTable = new ArrayList();
		try {
			linkedTable = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return linkedTable;
	}

	public static List queryLinkDir(String sOwnerID) throws SQLException, NamingException {
		// 获取关联文档列表
		String relationKsql = "select SA_DocLink.sID,SA_DocLink.sProcess,SA_DocLink.sActivity,SA_DocLink.sDocID"
				+ " from SA_DocLink SA_DocLink join SA_DocNode SA_DocNode  "
				+ " on SA_DocLink.sDocID=SA_DocNode where SA_DocLink.sOwnerID='" + sOwnerID
				+ "' and SA_DocNode.sKind ='dir' ";
		return DBUtils.execQueryforList("system", relationKsql);
	}

	public static List queryTempDocDir(String rootDocID, String rootDisplayName, String[] subPathArray,
			List linkedTable) {
		String docDirSql = "select SA_DocNode,SA_DocNode.sDocName,SA_DocNode.sParentID,SA_DocNode.sDocPath,SA_DocNode.sDocDisplayPath,"
				+ "SA_DocNode.sKind from SA_DocNode SA_DocNode where SA_DocNode='" + rootDocID + "' ";
		// 获取关联文档信息

		for (int j = 0; j < subPathArray.length; j++) {
			String subPath = subPathArray[j];
			String subPathName = "";
			String docDisplayPath = rootDisplayName;
			String[] subPathList = subPath.split("/");
			if (!"".equals(subPath)) {
				for (int i = 0; i < subPathList.length; i++) {
					subPathName = subPathList[i];
					docDirSql += "or (SA_DocNode.sDocName like '%" + subPathName
							+ "%' and SA_DocNode.sDocDisplayPath like '%" + docDisplayPath + "%') ";
					docDisplayPath += "/" + subPathName;
				}
			}
		}
		for (int i = 0; i < linkedTable.size(); i++) {
			Docinfo r = new Docinfo((Map) linkedTable.get(i));
			docDirSql += " or SA_DocNode='" + r.getString("sDocID") + "' ";
		}
		docDirSql += " order by SA_DocNode.sDocPath ";

		try {
			return DBUtils.execQueryforList("system", docDirSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return linkedTable;
	}

	/* 初始化table */
	public static List<Map<String, String>> initDocNode() {
		String sql = "select SA_DocNode.* from SA_DocNode SA_DocNode where 1 = 2";
		List t = null;
		try {
			t = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static List<?> queryLink(String billID) {
		String relationKsql = "select SA_DocLink.* " + " from SA_DocLink SA_DocLink " + "where SA_DocLink.sOwnerID='"
				+ billID + "'";
		List<?> t = null;
		try {
			t = DBUtils.execQueryforList("system", relationKsql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static List intiDocLinkDir() {
		String sql = "select SA_DocNode.sID,SA_DocNode.sDocName,SA_DocNode.sParentID,SA_DocNode.sDocPath,SA_DocNode.sDocDisplayPath,SA_DocNode.sKind,SA_DocNode.version from SA_DocNode SA_DocNode where 1 = 2";
		List t = null;
		try {
			t = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static List initDocLog() {
		String sql = "select SA_DocLog.* from SA_DocLog SA_DocLog where 1 = 1";
		if (DBUtils.IsOracleDB("system")) {
			sql += " and rownum=1";
		} else if (DBUtils.IsMySQLDB("system")) {
			sql += " limit 0,1";
		} else if (DBUtils.IsPostgreSQL("system")) {
			sql += " limit 1";
		} else {
			sql = "select top 1 SA_DocLog.* from SA_DocLog SA_DocLog where 1 = 1";
		}
		List t = null;
		try {
			t = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static List initDocAuth() {
		String sql = "select SA_DocAuth.* from SA_DocLog SA_DocAuth where 1 = 2";
		List t = null;
		try {
			t = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static int checkLocker(String sDocID) {
		String sEditorFID = ContextHelper.getPersonMember().getCurrentPersonFullID();

		String checkLockerSql = "SELECT COUNT(*) as COUNTNUM from SA_DocNode SA_DocNode where SA_DocNode.SID='" + sDocID
				+ "' and SA_DocNode.sEditorFID ='" + sEditorFID + "'";
		List table;
		try {
			table = DBUtils.execQueryforList("system", checkLockerSql);
			if (table.size() > 0) {
				Map row = (Map) table.get(0);
				return parserInt(row.get("COUNTNUM"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void deleteRows(String docId) {
		String sql = "delete from SA_DocNode where SID = '" + docId + "'";
		try {
			DBUtils.execdeleteQuery("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新文档状态
	 * 
	 * @param isLockDoc （boolean）锁定状态
	 * @param sDocID
	 * @return int
	 */
	public static int updateDocState(boolean isLockDoc, String sDocID) {
		int affactRow;

		String updateDocStateKsql = "";
		String sEditorFID = ContextHelper.getPersonMember().getCurrentPersonFullID();
		String sEditorName = ContextHelper.getPersonMember().getCurrentPersonName();
		String sEditorDeptName = ContextHelper.getPersonMember().getCurrentDeptName();
		if (isLockDoc) {
			updateDocStateKsql = "update SA_DocNode SA_DocNode set SA_DocNode.sEditorFID ='" + sEditorFID
					+ "',SA_DocNode.sEditorName='" + sEditorName + "',SA_DocNode.sEditorDeptName='" + sEditorDeptName
					+ "' where (SA_DocNode.sEditorFID='' or SA_DocNode.sEditorFID is null) and SA_DocNode.sID='"
					+ sDocID + "'";

		} else {
			updateDocStateKsql = "update SA_DocNode SA_DocNode set SA_DocNode.sEditorFID='',SA_DocNode.sEditorName='',SA_DocNode.sEditorDeptName='',SA_DocNode.sCacheName='',SA_DocNode.sRevisionCacheName='' where  SA_DocNode.sID='"
					+ sDocID + "' and SA_DocNode.sEditorFID ='" + sEditorFID + "'  and SA_DocNode.sEditorName ='"
					+ sEditorName;

			if (Utils.isEmptyString(sEditorDeptName)) {
				updateDocStateKsql += "'";
			} else {
				updateDocStateKsql += "' and  SA_DocNode.sEditorDeptName='" + sEditorDeptName + "'";
			}
		}
		try {
			DBUtils.execUpdateQuery("system", updateDocStateKsql);
			affactRow = 1;
		} catch (Exception e) {
			affactRow = 0;
		}
		if (affactRow > 1) {
			throw new DocRTException("影响DocNode表的记录数大于一");
		}
		return affactRow;
	}

	public static Map<String, ?> getDocInfoByFileID(String fileID, String docPath) {
		String sql = "select * from SA_DocNode SA_DocNode where SA_DocNode.sFileID ='" + fileID + "'";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				return list.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加文档
	 * 
	 * @param docID
	 * @param dirID
	 * @param docName
	 */
	public static void addDoc(String docID, String dirID, String docName) {
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
				+ "SEDITORFID,SEDITORNAME,SEDITORDEPTNAME,SDOCLIVEVERSIONID,SFLAG,VERSION)values(?,?,?,"
				+ "?,?,?,?,?,?,?,?,?,1,1,1)";
		ContextBean context = ContextUtils.getContext();
		SqlSession session = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			session = DBUtils.getSession("system");
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, docID);
			ps.setString(2, dirID);
			ps.setString(3, docName);
			ps.setString(4, context.getCurrentPersonID());
			ps.setString(5, context.getCurrentPersonName());
			ps.setString(6, context.getCurrentDeptName());
			ps.setTimestamp(7, new Timestamp(new Date().getTime()));
			ps.setString(8, SDOCPATH + "/" + docID);
			ps.setString(9, SDOCDISPLAYPATH + "/" + docName);
			ps.setString(10, context.getCurrentPersonFullID());
			ps.setString(11, context.getCurrentPersonName());
			ps.setString(12, context.getCurrentDeptName());
			ps.executeUpdate();
			session.commit(true);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, null);
		}
	}

	/**
	 * 添加文档数据
	 * 
	 * @param docPath
	 * @param docName
	 * @param kind
	 * @param size
	 * @param cacheName
	 * @return String
	 * @throws Exception
	 */
	public static String addDocData(String docPath, String docName, String kind, String size, String cacheName)
			throws Exception {
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		Statement stm = null;
		ResultSet di = null;
		String docID = Utils.getID();
		try {
			conn = session.getConnection();
			if ("/".equals(docPath)) {
				docPath = "/root";
			}
			String dirsql = "select SID,SDOCPATH,SDOCDISPLAYPATH from SA_DOCNODE where SDOCPATH ='" + docPath
					+ "' or SDOCDISPLAYPATH='" + docPath + "'";
			if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
				dirsql = "select SID,SDOCPATH,SDOCDISPLAYPATH from SA_DOCNODE where SDOCPATH ='" + docPath
						+ "' or to_char(SDOCDISPLAYPATH) = '" + docPath + "'";
			}
			stm = conn.createStatement();
			di = stm.executeQuery(dirsql);
			String pdirpath = "";
			String pdirpathID = "";
			String dirpathID = "/root";
			String[] docDirs = docPath.split("/");
			String dirID = "root";
			if (di.next()) {
				dirID = di.getString("SID");
				dirpathID = di.getString("SDOCPATH");
				pdirpath = di.getString("SDOCDISPLAYPATH");
			} else if (docDirs.length > 2) {
				for (int n = 2; n < docDirs.length; n++) {
					String dir = docDirs[n];
					pdirpath += "/" + dir;
					String queryDirSql = "select SID,SDOCPATH from SA_DOCNODE where SDOCDISPLAYPATH='/文档中心" + pdirpath
							+ "' or SDOCDISPLAYPATH='/root" + pdirpath + "'";
					if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system")) {
						queryDirSql = "select SID,SDOCPATH from SA_DOCNODE where to_char(SDOCDISPLAYPATH)='/文档中心"
								+ pdirpath + "' or to_char(SDOCDISPLAYPATH)='/root" + pdirpath + "'";
					}
					Statement stms = conn.createStatement();
					ResultSet pli = stms.executeQuery(queryDirSql);
					if (pli.next()) {
						dirID = pli.getString("SID");
						pdirpathID = pli.getString("SDOCPATH");
					} else {
						dirID = Utils.getID();
						pdirpathID += "/" + dirID;
						String sql = "insert into SA_DOCNODE(SID,SPARENTID,SDOCNAME,SKIND,SDOCPATH,SDOCDISPLAYPATH,VERSION)select '"
								+ dirID + "',SID,'" + dir + "','dir','/root" + pdirpathID + "','/文档中心" + pdirpath
								+ "',0 from SA_DOCNODE where SDOCPATH = '" + dirpathID + "'";
						conn.createStatement().executeUpdate(sql);
					}
					DBUtils.closeConn(null, null, stms, pli);
					dirpathID += "/" + dirID;
				}
			}
			String docSql = "insert into SA_DOCNODE(SID,SPARENTID,SDOCNAME,"
					+ "SCREATORFID,SCREATORNAME,SCREATORDEPTNAME,SCREATETIME,SDOCPATH,SDOCDISPLAYPATH,"
					+ "SEDITORFID,SEDITORNAME,SEDITORDEPTNAME,SKIND,SSIZE,SCACHENAME,SDOCLIVEVERSIONID,SFLAG,VERSION)values('"
					+ docID + "','" + dirID + "','" + docName + "'," + "?,?,?,?,?,?,?,?,?,?,?,?,1,1,1)";
			ContextBean context = ContextUtils.getContext();
			PreparedStatement ps = conn.prepareStatement(docSql);
			ps.setString(1, context.getCurrentPersonFullID());
			ps.setString(2, context.getCurrentPersonName());
			ps.setString(3, context.getCurrentDeptName());
			ps.setTimestamp(4, new Timestamp(new Date().getTime()));
			ps.setString(5, dirpathID + "/" + docID);
			ps.setString(6, pdirpath + "/" + docName);
			ps.setString(7, context.getCurrentPersonFullID());
			ps.setString(8, context.getCurrentPersonName());
			ps.setString(9, context.getCurrentDeptName());
			ps.setString(10, kind);
			ps.setFloat(11, Float.parseFloat(size));
			ps.setString(12, cacheName);
			ps.executeUpdate();
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, stm, di);
		}
		return docID;
	}
}
