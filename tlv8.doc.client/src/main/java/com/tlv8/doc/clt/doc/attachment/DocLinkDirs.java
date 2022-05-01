package com.tlv8.doc.clt.doc.attachment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Element;

import com.tlv8.doc.clt.doc.DocDBHelper;
import com.tlv8.doc.clt.doc.DocRTException;
import com.tlv8.doc.clt.doc.DocUtils;
import com.tlv8.doc.clt.doc.Docinfo;
import com.tlv8.doc.clt.doc.Utils;
import com.tlv8.doc.clt.doc.transform.TransformConfig;

@SuppressWarnings("rawtypes")
public class DocLinkDirs {
	private String billID;
	private String rootPath;
	private String subPath;
	private List container;
	protected Map<String, DocLinkDir> dirs = new HashMap<String, DocLinkDir>();

	public DocLinkDirs(String billID, String rootPath, String subPath)
			throws Exception {
		if (Utils.isEmptyString(billID) || Utils.isEmptyString(rootPath)) {
			throw new DocRTException("docLinks:传入参数为空，请检查");
		}
		this.billID = billID;
		this.rootPath = rootPath;
		this.subPath = subPath;
		loadLinkedDir();
	}

	public DocLinkDirs loadLinkedDir() throws Exception {
		// 对subpath进行转换
		subPath = DocUtils.convertExpression(subPath);
		String[] subPathArray = subPath.split(",");

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

		for (int i = 0; i < docNodeTable.size(); i++) {
			Docinfo r = new Docinfo((Map) docNodeTable.get(i));
			// 根文件夹显示名称
			rootDisplayName = "/".equals(r.getString("sDocDisplayPath")) ? "/"
					+ r.getString("sDocName") : r.getString("sDocDisplayPath")
					+ "/" + r.getString("sDocName");
		}

		List linkedTable = DocDBHelper.queryLinkDir(billID); // 获取Link-dir

		List docDirTable = DocDBHelper.queryTempDocDir(docId, rootDisplayName,
				subPathArray, linkedTable);

		container = convertLinkDir(rootDisplayName, rootPath, subPathArray,
				docNodeTable, linkedTable, docDirTable);

		for (int i = 0; i < container.size(); i++) {
			Docinfo r = new Docinfo((Map) container.get(i));
			dirs.put(r.getString("SA_DocNode"), new DocLinkDir(r));
		}

		return this;
	}

	@SuppressWarnings( { "unchecked", "null" })
	private List convertLinkDir(String rootDisplayName, String rootPath,
			String[] subPathArray, List docNodeTable, List linkedTable,
			List docDirTable) {

		String sDocId = "";
		String sDocName = "";
		String sParentID = "";
		String sDocPath = "";
		String sDocDisplayPath = "";

		List result = DocDBHelper.intiDocLinkDir();
		Docinfo row = null;

		// 插入主目录
		for (int i = 0; i < docNodeTable.size(); i++) {
			Docinfo r = new Docinfo((Map) docNodeTable.get(i));
			sDocId = r.getString("SA_DocNode");
			sDocDisplayPath = r.getString("sDocDisplayPath");
			row.setString("SA_DocNode", sDocId);
			row.setString("sDocName", r.getString("sDocName"));
			row.setString("sParentID", r.getString("sParentID"));
			row.setString("sDocPath", r.getString("sDocPath"));
			row.setString("sDocDisplayPath", sDocDisplayPath);
			row.setString("sKind", r.getString("sKind"));
			row.setString("is_linked", isLinked(linkedTable, sDocId) ? "true"
					: "false");
			row.setString("is_exist", "true");
			// 父节点id
			sParentID = r.getString("SA_DocNode");
			sDocPath = r.getString("sDocPath");

		}
		// 插入子目录
		String selectName = "";
		for (int j = 0; j < subPathArray.length; j++) {
			String subPath = subPathArray[j];
			if (subPath.equals(""))
				break;
			sDocPath = rootPath;
			sDocDisplayPath = rootDisplayName;
			String isExist = "";
			String isLinked = "";
			String[] subPathList = subPath.split("/");

			for (int i = 0; i < subPathList.length; i++) {
				boolean isPathExist = false;
				String subPathName = subPathList[i];
				for (int n = 0; n < docDirTable.size(); n++) {
					Docinfo r = new Docinfo((Map) docDirTable.get(n));
					/* 通过判断sDocName是否相等，说明改sDocName是否在docnode中存在 */
					if (subPathName.equals(r.getString("sDocName"))
							&& sDocDisplayPath.equals(r
									.getString("sDocDisplayPath"))) {
						isPathExist = true;
						sDocId = r.getString("SA_DocNode");
						sDocName = r.getString("sDocName");
						sParentID = r.getString("sParentID");
						sDocPath = r.getString("sDocPath");
						sDocDisplayPath = r.getString("sDocDisplayPath");
						isExist = "true";
						isLinked = isLinked(linkedTable, sDocId) ? "true"
								: "false";
					}
				}
				if (!isPathExist) {
					sDocId = UUID.randomUUID().toString();
					sDocName = subPathName;
					isExist = "false";
					isLinked = "false";
				}

				row.setString("SA_DocNode", sDocId);
				row.setString("sDocName", sDocName);
				row.setString("sParentID", sParentID);
				row.setString("sDocPath", sDocPath);
				row.setString("sDocDisplayPath", sDocDisplayPath);
				row.setString("sKind", "dir");

				row.setString("is_linked", isLinked);
				row.setString("is_exist", isExist);

				// 拼接docDisplayPath，sDocPath
				sParentID = sDocId;
				selectName = subPathName;
				sDocDisplayPath += "/" + subPathName;
				sDocPath += "/" + sDocId;

			}
		}

		Map<String, Object> selectRow = (Map<String, Object>) result.get(0);
		selectRow.put("sys.selectedID", sDocId);
		selectRow.put("sys.selectedName", selectName);
		selectRow.put("sys.selectedPath", sDocPath);
		selectRow.put("sys.selectedDisplayPath", sDocDisplayPath);

		return result;
	}

	private Boolean isLinked(List linkedTable, String docId) {
		boolean result = false;
		for (int i = 0; i < linkedTable.size(); i++) {
			Docinfo r = new Docinfo((Map) linkedTable.get(i));
			if (docId.equals(r.getString("sDocID"))) {
				result = true;
			}
		}
		return result;
	}

	public int size() {
		return dirs.entrySet().size();
	}

	public DocLinkDir getDirsIndex(String index) {
		return dirs.get(index);
	}

	public Iterator<DocLinkDir> getDocsIterator() {
		return dirs.values().iterator();
	}

	public List toTable() {
		return this.container;
	}

	public void readerFromJson(Object arg0, TransformConfig arg1) {

	}

	public void reader(Element arg0, TransformConfig arg1) {
		// TODO Auto-generated method stub

	}

	public Element writer(TransformConfig arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object writerToJson(TransformConfig arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
