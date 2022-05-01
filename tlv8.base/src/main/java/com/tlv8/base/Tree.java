package com.tlv8.base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.naming.NamingException;

import com.tlv8.base.db.DBUtils;

public class Tree {
	/*
	 * @chenqian 生成树形结构
	 * 
	 * @{
	 * 
	 * @可以通过sql生成树形：
	 * 
	 * @String tree = createTreeBysql(db,sql,parentCell,ValueCell,isMaster)
	 * 
	 * @参数：数据库连接，sql，父节点字段，显示值字段，是否多选 (所有参数均为字符型)
	 * 
	 * @}
	 */
	private static StringBuilder childresult;
	private static String pValueCell = "";
	private static String fparentCell = "";
	private static String fparentID = "";
	private static String tempRowid = "";
	private static String isMaster = "false";
	private static ArrayList<String> checkRowid = new ArrayList<String>();
	private static ArrayList<HashMap<String, String>> Valuetables;
	private static HashMap<String, ArrayList<HashMap<String, String>>> allValuetable;

	public static String createTree(ResultSet rs, String parentCell, String ValueCell, String master)
			throws SQLException {
		pValueCell = ValueCell.toUpperCase();
		fparentCell = parentCell.toUpperCase();
		if (master != null)
			isMaster = master;
		StringBuilder result = new StringBuilder();
		// System.out.println(viewID);
		result.append("<ul id='tree'>");
		ResultSetMetaData rsmd = rs.getMetaData();
		int size = rsmd.getColumnCount();
		Valuetables = new ArrayList<HashMap<String, String>>();
		allValuetable = new HashMap<String, ArrayList<HashMap<String, String>>>();
		HashMap<String, ArrayList<HashMap<String, String>>> parentNode = new HashMap<String, ArrayList<HashMap<String, String>>>();
		HashMap<String, ArrayList<HashMap<String, String>>> childNode = new HashMap<String, ArrayList<HashMap<String, String>>>();
		while (rs.next()) {
			// 第一个字段必须为主键
			String FID = rs.getString(1);
			HashMap<String, String> temMap = new HashMap<String, String>();
			for (int i = 1; i <= size; i++) {
				String key = rsmd.getColumnLabel(i);
				String value = rs.getString(i);
				temMap.put(key.toUpperCase(), value);
			}
			Valuetables.add(temMap);
			ArrayList<HashMap<String, String>> Valuetable = new ArrayList<HashMap<String, String>>();
			Valuetable.add(temMap);
			allValuetable.put(FID, Valuetable);
			String parentID = rs.getString(parentCell);
			if (parentID == null)
				parentNode.put(FID, Valuetable);
			else {
				childNode.put(FID, Valuetable);
			}
		}
		// System.out.println(Valuetables);
		// System.out.println(parentNode);
		// 创建根节点
		Set<String> pst = parentNode.keySet();
		Iterator<String> pit = pst.iterator();
		childresult = new StringBuilder();
		checkRowid = new ArrayList<String>();
		while (pit.hasNext()) {
			String key = pit.next();
			fparentID = null;
			// System.out.println(key);
			getAllChildNode(parentNode, key, ValueCell);
		}
		result.append(childresult.toString());
		result.append("</ul>");
		return result.toString();
	}

	// 获取当前节点的所有子节点
	private static HashMap<String, ArrayList<HashMap<String, String>>> getMyChildren(
			HashMap<String, ArrayList<HashMap<String, String>>> m, String FID) {
		HashMap<String, ArrayList<HashMap<String, String>>> result = new HashMap<String, ArrayList<HashMap<String, String>>>();
		ArrayList<HashMap<String, String>> reList = new ArrayList<HashMap<String, String>>();
		Set<String> set = m.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			ArrayList<HashMap<String, String>> childList = m.get(key);
			for (int l = 0; l < childList.size(); l++) {
				HashMap<String, String> childMap = childList.get(l);
				if (childMap.containsValue(FID) && childMap.containsKey(fparentCell)) {
					if (FID.equals(childMap.get(fparentCell))) {
						reList.add(childMap);
						String Rowid = childMap.get("FID") == null ? childMap.get("FID") : childMap.get("SID");
						result.put(Rowid, reList);
					}
				}
			}
		}
		return result;
	}

	// 判断是否有子节点
	private static boolean isHaveChild(HashMap<String, ArrayList<HashMap<String, String>>> m, String FID) {
		Set<String> set = m.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			// System.out.println(key);
			ArrayList<HashMap<String, String>> childList = m.get(key);
			for (int l = 0; l < childList.size(); l++) {
				HashMap<String, String> childMap = childList.get(l);
				if (childMap.containsValue(FID)) {
					return true;
				}
			}
		}
		return false;
	}

	// 用递归法构建树
	private static void getAllChildNode(HashMap<String, ArrayList<HashMap<String, String>>> m, String FID,
			String ValueCell) {
		if (tempRowid.equals(FID))
			return;
		tempRowid = FID;
		Set<String> set = m.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (!incheckRowid(FID) && FID.equals(key)) {
				checkRowid.add(FID);
				String value = getValueByKey(key);
				// System.out.println(getIsMaster());
				boolean master = ("true".equals(isMaster.toLowerCase()) && !"".equals(isMaster) && isMaster != null)
						? true
						: false;
				// System.out.println(key + ":" + value);
				if (master) {
					childresult.append("<li><input id='" + key
							+ "_check' type='checkbox'/><a href='javascript:void(0);' onclick='return false;' id='"
							+ key + "' parentID='" + fparentID + "' title='" + value + "'>" + value + "</a>");
				} else {
					childresult.append("<li><a href='javascript:void(0);' onclick='return false;' id='" + key
							+ "' parentID='" + fparentID + "' title='" + value + "'>" + value + "</a>");
				}
				HashMap<String, ArrayList<HashMap<String, String>>> ChilMap = getMyChildren(allValuetable, key);
				Set<String> chilset = ChilMap.keySet();
				Iterator<String> chilit = chilset.iterator();
				while (chilit.hasNext()) {
					childresult.append("<ul>");
					String childkay = chilit.next();
					ArrayList<HashMap<String, String>> childList = ChilMap.get(childkay);
					for (int i = 0; i < childList.size(); i++) {
						HashMap<String, String> childMap = childList.get(i);
						String ID = childMap.containsKey("FID") ? childMap.get("FID") : childMap.get("SID");
						String ThValue = childMap.get(ValueCell);
						if (isHaveChild(allValuetable, ID) && !ID.equals(FID)) {
							fparentID = FID;
							getAllChildNode(allValuetable, ID, ValueCell);// 递归
						} else {
							if (master) {
								childresult.append("<li><input id='" + ID
										+ "_check' type='checkbox'/><a href='javascript:void(0);' onclick='return false;' id='"
										+ ID + "' parentID='" + fparentID + "' title='" + ThValue + "'>" + ThValue
										+ "</a></li>");
							} else {
								childresult.append("<li><a href='javascript:void(0);' onclick='return false;' id='" + ID
										+ "' parentID='" + fparentID + "' title='" + ThValue + "'>" + ThValue
										+ "</a></li>");
							}
						}
					}
					childresult.append("</ul>");
				}
				childresult.append("</li>");
			}
		}
	}

	private static String getValueByKey(String key) {
		String result = null;
		for (int i = 0; i < Valuetables.size(); i++) {
			HashMap<String, String> M = Valuetables.get(i);
			Set<String> set = M.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String TmpKey = it.next();
				String MID = M.containsKey("FID") ? M.get("FID") : M.get("SID");
				if (M.containsKey(TmpKey) && key.equals(MID)) {
					result = M.get(pValueCell);
					return result;
				}
			}
		}
		return result;
	}

	private static boolean incheckRowid(String key) {
		for (int i = 0; i < checkRowid.size(); i++) {
			if (checkRowid.get(i) == key || key.equalsIgnoreCase(checkRowid.get(i)) || key.equals(checkRowid.get(i))) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static String createTreeBysql(String db, String sql, String parentCell, String ValueCell, String master)
			throws SQLException, NamingException {
		String result = "";
		if (master != null)
			setIsMaster(master);
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getAppConn(db);
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			result = createTree(rs, parentCell, ValueCell, isMaster);
			// System.out.println(result);
		} catch (SQLException e) {
			throw new SQLException(e);
		} finally {
			try {
				DBUtils.CloseConn(conn, stm, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void setIsMaster(String isMaster) {
		Tree.isMaster = isMaster;
	}

	public static String getIsMaster() {
		return isMaster;
	}
}
