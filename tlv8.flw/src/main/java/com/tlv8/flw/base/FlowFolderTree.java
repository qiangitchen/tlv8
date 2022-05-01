package com.tlv8.flw.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;

@SuppressWarnings({ "rawtypes" })
public class FlowFolderTree {
	public static final String get_folder_sql = "select SID,SPARENT,SCODE,SNAME,SIDPATH,SNAMEPATH,SCODEPATH,SPROCESSID,SPROCESSNAME from SA_FLOWFOLDER where 1=1 %s";

	/*
	 * @获取根节点列表
	 */
	private static List getRootFlowFolder() throws Exception {
		List list = new ArrayList();
		String sql = String.format(get_folder_sql, " and (SPARENT is null or SPARENT = '')");
		try {
			list = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			throw new Exception(e.toString()+":" +sql);
		}
		return list;
	}

	/*
	 * @获取当前节点子节点列表
	 */
	private static List getChildFlowFolder(String parent) {
		List list = new ArrayList();
		String sql = String.format(get_folder_sql, " and SPARENT = '" + parent
				+ "'");
		try {
			list = DBUtils.execQueryforList("system", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/*
	 * @构建指点节点的树{包含所有子节点}
	 */
	private static String buldtree(Map m) {
		StringBuffer tempStr = new StringBuffer();
		String parent = (String) m.get("SID");
		tempStr.append("{");
		tempStr.append("'name':'");
		tempStr.append(m.get("SNAME"));
		tempStr.append("','id':'");
		tempStr.append(m.get("SID"));
		tempStr.append("','pId':'");
		tempStr.append(m.get("SPARENT"));
		tempStr.append("','SCODE':'");
		tempStr.append(m.get("SCODE"));
		tempStr.append("','SIDPATH':'");
		tempStr.append(m.get("SIDPATH"));
		tempStr.append("','SCODEPATH':'");
		tempStr.append(m.get("SCODEPATH"));
		tempStr.append("','SNAMEPATH':'");
		tempStr.append(m.get("SNAMEPATH"));
		tempStr.append("','SPROCESSID':'");
		tempStr.append(m.get("SPROCESSID"));
		tempStr.append("','SPROCESSNAME':'");
		tempStr.append(m.get("SPROCESSNAME"));
		tempStr.append("', 'children':[");
		List eles = getChildFlowFolder(parent);
		if (!eles.isEmpty()) {
			int elesz = eles.size();
			for (int i = 0; i < elesz; i++) {
				//Sys.printMsg((Map) eles.get(i));
				if (i > 0)
					tempStr.append(", ");
				tempStr.append(buldtree((Map) eles.get(i)));
			}
		}
		tempStr.append("]}");
		return tempStr.toString();
	}

	/*
	 * @获取指定节点的一级子节点
	 */
	public static String getMyChildArrayJSON(String myid) {
		StringBuffer tempStr = new StringBuffer();
		List eles = getChildFlowFolder(myid);
		tempStr.append("[");
		for (int i = 0; i < eles.size(); i++) {
			Map m = (Map) eles.get(i);
			tempStr.append("{");
			tempStr.append("'name':'");
			tempStr.append(m.get("SNAME"));
			tempStr.append("','id':'");
			tempStr.append(m.get("SID"));
			tempStr.append("','pId':'");
			tempStr.append(m.get("SPARENT"));
			tempStr.append("','SCODE':'");
			tempStr.append(m.get("SCODE"));
			tempStr.append("','SIDPATH':'");
			tempStr.append(m.get("SIDPATH"));
			tempStr.append("','SCODEPATH':'");
			tempStr.append(m.get("SCODEPATH"));
			tempStr.append("','SNAMEPATH':'");
			tempStr.append(m.get("SNAMEPATH"));
			tempStr.append("','SPROCESSID':'");
			tempStr.append(m.get("SPROCESSID"));
			tempStr.append("','SPROCESSNAME':'");
			tempStr.append(m.get("SPROCESSNAME"));
			tempStr.append("', 'children':[]}");
			if (i < eles.size() - 1) {
				tempStr.append(",");
			}
		}
		tempStr.append("]");
		return tempStr.toString();
	}

	/*
	 * @ 获取整个目录树
	 */
	public static String getFolderJsonStr() throws Exception {
		StringBuffer str = new StringBuffer();
		str.append("[");
		List root = getRootFlowFolder();
		for (int i = 0; i < root.size(); i++) {
			str.append(buldtree((Map) root.get(i)));
			if (i < root.size() - 1) {
				str.append(",");
			} else {
				str.append("]");
			}
		}
		return str.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getFolderJsonStr());
		System.out.println(getMyChildArrayJSON("root"));
	}
}
