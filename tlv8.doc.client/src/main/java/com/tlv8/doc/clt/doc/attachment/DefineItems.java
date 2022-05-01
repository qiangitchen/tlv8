package com.tlv8.doc.clt.doc.attachment;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;
import com.tlv8.doc.clt.doc.CommonUtils;
import com.tlv8.doc.clt.doc.Doc;
import com.tlv8.doc.clt.doc.DocDBHelper;
import com.tlv8.doc.clt.doc.DocRTException;
import com.tlv8.doc.clt.doc.DocUtils;
import com.tlv8.doc.clt.doc.Docinfo;
import com.tlv8.doc.clt.doc.Utils;

@SuppressWarnings("rawtypes")
public class DefineItems {
	private String rootName;
	private String rootPath;
	private String subPath;
	private Integer access;
	private Float limitSize;
	private List dirs;
	private Doc uploadDir;

	public DefineItems(String rootName, String rootPath, String subPath,
			Integer access, Float limitSize) {
		this.rootName = rootName;
		this.rootPath = rootPath;
		this.subPath = subPath;
		this.access = access;
		this.limitSize = limitSize;
		loadFullPath();
	}

	private void loadFullPath() {
		// 对subpath进行转换
		subPath = DocUtils.convertExpression(subPath);

		String[] pathList = rootPath.split("/");
		String docId = "";
		for (int i = 0; i < pathList.length; i++) {
			docId = pathList[i];
			if ("".equals(docId)) {
				if (i == 0) {
					continue;
				} else {
					throw new DocRTException(String.format("“%s”路径中不能包含空文件夹名",
							pathList[i]));
				}
			}
		}

		List docNodeTable = DocDBHelper.queryDoc(docId, "",
				"sDocName,sParentID,sDocPath,sDocDisplayPath,sKind",
				"sDocPath", "");

		String rootDisplayName = "/";
		String rootDisplayPath = "/";
		for (int i = 0; i < docNodeTable.size(); i++) {
			Docinfo r = new Docinfo((Map) docNodeTable.get(i));
			// 根文件夹显示名称

			if (Utils.isEmptyString(subPath)) {
				uploadDir = new Doc((Docinfo) r, null);
				return;
			}

			rootDisplayName = "/".equals(r.getString("sDocDisplayPath")) ? "/"
					+ r.getString("sDocName") : r.getString("sDocDisplayPath")
					+ "/" + r.getString("sDocName");
			rootDisplayPath = "/".equals(r.getString("sDocPath")) ? "/"
					+ r.getString("SA_DocNode") : r.getString("sDocPath") + "/"
					+ r.getString("SA_DocNode");
		}

		dirs = queryTempDocDir(docId, rootDisplayName, subPath);

		uploadDir = checkNewDirs(docId, rootDisplayPath, rootDisplayName,
				subPath, dirs);
	}

	public void refresh() {
		dirs = null;
		uploadDir = null;
		loadFullPath();
	}

	public int saveSubPath() {
		return 1;
	}

	private Doc checkNewDirs(String rootId, String rootDisplayPath,
			String rootDisplayName, String subPath, List dirs2) {
		String subPathName = "";
		String docDisplayPath = rootDisplayName;
		String[] subPathList = subPath.split("/");
		String currentId = rootId;
		String docPath = rootDisplayPath;

		dirs = DocDBHelper.initDocNode();

		Docinfo result = null;

		if (!"".equals(subPath)) {
			for (int i = 0; i < subPathList.length; i++) {
				subPathName = subPathList[i];
				boolean isExist = false;
				for (int n = 0; n < dirs2.size(); n++) {
					Docinfo r1 = new Docinfo((Map)dirs2.get(n));
					if (subPathName.equals(r1.getString("sDocName"))
							& docDisplayPath.equals(r1
									.getString("sDocDisplayPath"))) {
						currentId = r1.getString("SA_DocNode");
						isExist = true;
						result = (Docinfo) r1;
						break;
					}
				}
				if (!isExist) {
					Docinfo r = new Docinfo((Map)dirs.get(0));
					String sId = CommonUtils.createGUID();
					r.setString("SA_DocNode", sId);
					r.setString("sParentID", currentId);
					r.setString("sDocName", subPathName);
					r.setString("sKind", "dir");
					r.setString("sDocPath", docPath);
					r.setString("sDocDisplayPath", docDisplayPath);
					// String currentPersionFID =
					// ContextHelper.getPersonMember().getFID();
					// String currentPersionFName =
					// ContextHelper.getPersonMember().getName();
					// Timestamp currentTime =
					// com.justep.system.util.CommonUtils.getCurrentDateTime();

					// r.setString("sCreatorFID", currentPersionFID);
					// r.setString("sCreatorName", currentPersionFName);
					// r.setDateTime("sCreateTime", currentTime);
					// r.setString("sLastWriterFID", currentPersionFID);
					// r.setString("sLastWriterName", currentPersionFName);
					// r.setDateTime("sLastWriteTime", currentTime);
					r.setInt("version", 0);
					currentId = sId;
					result = (Docinfo) r;
				}

				docDisplayPath += "/" + subPathName;
				docPath += "/" + currentId;
			}
		}

		return new Doc(result, null);

	}

	protected List queryTempDocDir(String rootDocID, String rootDisplayName,
			String subPath) {
		String docDirSql = "select SA_DocNode,SA_DocNode.sDocName,SA_DocNode.sParentID,SA_DocNode.sDocPath,SA_DocNode.sDocDisplayPath,"
				+ "SA_DocNode.sKind from SA_DocNode SA_DocNode where SA_DocNode='"
				+ rootDocID + "' ";
		// 获取关联文档信息

		String subPathName = "";
		String docDisplayPath = rootDisplayName;
		String[] subPathList = subPath.split("/");
		if (!"".equals(subPath)) {
			for (int i = 0; i < subPathList.length; i++) {
				subPathName = subPathList[i];
				docDirSql += "or (SA_DocNode.sDocName like '%" + subPathName
						+ "%' and SA_DocNode.sDocDisplayPath like '%"
						+ docDisplayPath + "%') ";
				docDisplayPath += "/" + subPathName;
			}
		}

		docDirSql += " order by SA_DocNode.sDocPath ";

		try {
			return DBUtils.execQueryforList("system", docDirSql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Doc getUploadDir() {
		return uploadDir;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getSubPath() {
		return subPath;
	}

	public void setSubPath(String subPath) {
		this.subPath = subPath;
	}

	public Integer getAccess() {
		return access;
	}

	public void setAccess(Integer access) {
		this.access = access;
	}

	public Float getLimitSize() {
		return limitSize;
	}

	public void setLimitSize(Float limitSize) {
		this.limitSize = limitSize;
	}

}
