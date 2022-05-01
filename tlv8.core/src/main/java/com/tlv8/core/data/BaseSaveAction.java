package com.tlv8.core.data;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.tlv8.base.Data;
import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.db.RegexUtil;
import com.tlv8.base.utils.StringArray;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.CodeUtils;
import com.tlv8.core.helper.DataTypeHelper;
import com.tlv8.system.bean.ContextBean;

/**
 * 公用保存
 */
public class BaseSaveAction extends ActionSupport {
	private static Logger log = Logger.getLogger(BaseSaveAction.class);
	protected String dbkay = "";
	protected String table = "";
	protected String cells = "";
	protected Data data = new Data();
	protected String rowid = null;
	protected String where = null;
	protected int page = 1;
	protected int allpage = 1;
	protected int rows = 20;// 默认20行数据

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTable() {
		return table;
	}

	public void setCells(String cells) {
		try {
			this.cells = URLDecoder.decode(cells, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getCells() {
		return cells;
	}

	public String saveData() throws Exception {
		log.info("保存数据...");
		String userid = ContextBean.getContext(request).getCurrentPersonID();
		if (userid == null || "".equals(userid)) {
			throw new Exception("未登录或登录已超时，不允许操作!");
		}
		String result = "success";
		boolean isnewData = false;
		String db = ("".equals(getDbkay()) || getDbkay() == null) ? "system" : getDbkay();
		HashMap<String, String> cell = null;
		try {
			cell = Data.getCell(cells);
		} catch (Exception e) {
			// 丢弃错误的数据
			return result;
		}
		boolean isNew = true;
		String perkey = ("system".equals(db)) ? "SID" : "FID";
		SqlSession session = DBUtils.getSession(db);
		if (cell.containsKey("ROWID")) {
			String fID = cell.get("ROWID");
			String SreachSql = "select VERSION from " + getTable() + " where " + perkey + " = '" + fID + "'";
			List<Map<String, String>> list = DBUtils.selectStringList(session, SreachSql);
			if (list.size() > 0) {
				isNew = false;
				String version = list.get(0).get("VERSION");
				if (version == null) {
					version = "0";
				}
				int dcv = Integer.parseInt(version);
				int ncv = Integer.parseInt(cell.get("VERSION"));
				if (ncv <= dcv) {
					session.close();
					throw new Exception("数据已被修改，请刷新数据再操作!");
				}
			}
		}
		String fID = "";
		if (cell.containsKey("ROWID") && !"newrowid".equals(cell.get("ROWID"))
				&& !cell.get("ROWID").endsWith("newrowid") && isNew == false) {
			fID = cell.get("ROWID");
			List<Map<String, Object>> chl = DBUtils.selectList(session,
					"select " + perkey + " from " + getTable() + " where " + perkey + "='" + fID + "'");
			if (chl.size() > 0) {
				isnewData = false;
			} else {
				isnewData = true;
			}
		} else {
			fID = Sys.getUUID();
			if (cell.get("ROWID") != null && !"".equals(cell.get("ROWID")) && !"undefined".equals(cell.get("ROWID"))
					&& !"null".equals(cell.get("ROWID"))) {
				fID = cell.get("ROWID");
			}
			isnewData = true;
		}
		setRowid(fID);
		Set<String> set = cell.keySet();
		Iterator<String> it = set.iterator();
		StringArray cl = new StringArray();
		StringArray acl = new StringArray();
		StringArray setacl = new StringArray();
		Map<String, String> vlm = new HashMap<String, String>();
		List<String> celllist = new ArrayList<String>();
		String kayperm = "FID";
		if ("system".equals(db)) {
			kayperm = "SID";
		}
		while (it.hasNext()) {
			String key = (String) it.next();
			String vals = cell.get(key);
			vals = CodeUtils.decodeSpechars(vals);
			if (!"ROWID".equals(key)) {
				celllist.add(key);
				cl.push(key);
				acl.push("?");
				vlm.put(key, vals);
				setacl.push(key + "=?");
			}
		}
		cl.push(kayperm);
		acl.push("?");
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "";
		try {
			if (isnewData) {
				sql = "insert into " + getTable() + "(" + cl.join(",") + ")values(" + acl.join(",") + ")";
			} else {
				sql = "update " + getTable() + " set " + setacl.join(",") + " where " + kayperm + " = ?";
			}
			conn = session.getConnection();
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < celllist.size(); i++) {
				String dataType = DataTypeHelper.getColumnDataType(conn, getTable(), celllist.get(i));
				String addval = vlm.get(celllist.get(i));
				if (("DATE".equals(dataType.toUpperCase()) || "DATETIME".equals(dataType.toUpperCase())
						|| "TIMESTAMP".equals(dataType.toUpperCase())) && !"".equals(addval)) {
					try {
						DateFormat dataTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date vl1 = dataTimeFormat.parse(addval);
						ps.setTimestamp(i + 1, new java.sql.Timestamp(vl1.getTime()));
					} catch (Exception e) {
						try {
							DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
							Date vl2 = dataFormat.parse(addval);
							ps.setDate(i + 1, new java.sql.Date(vl2.getTime()));
						} catch (Exception e2) {
							ps.setNull(i + 1, Types.NULL);
						}
					}
				} else {
					if (addval != null && !"".equals(addval.trim()) && !"null".equals(addval)) {
						if (dataType.contains("int")) {
							ps.setInt(i + 1, Integer.parseInt(addval));
						} else if (dataType.contains("float")) {
							ps.setFloat(i + 1, Float.parseFloat(addval));
						} else if (dataType.contains("numeric")) {
							ps.setDouble(i + 1, Double.valueOf(addval));
						} else if (dataType.contains("decimal")) {
							ps.setDouble(i + 1, Double.valueOf(addval));
						} else {
							ps.setString(i + 1, addval);
						}
					} else {
						ps.setNull(i + 1, Types.NULL);
					}
				}
			}
			ps.setString(celllist.size() + 1, fID);
			ps.executeUpdate();
			session.commit(true);
		} catch (SQLException e) {
			session.rollback(true);
			result = "false";
			log.error("保存数据失败!sql:" + sql + " " + e.getMessage());
			System.err.println("SQL:" + sql);
			session.close();
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		} finally {
			try {
				DBUtils.CloseConn(session, conn, ps, null);
			} catch (Exception e) {
			}
		}
		return result;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getRows() {
		return rows;
	}

	public void setWhere(String where) {
		try {
			this.where = URLDecoder.decode(where, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getWhere() {
		return where;
	}

	public void setAllpage(int allpage) {
		this.allpage = allpage;
	}

	public int getAllpage() {
		return allpage;
	}

	public void setRowid(String rowid) {
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getRowid() {
		return rowid;
	}

	public void setDbkay(String dbkay) {
		this.dbkay = dbkay;
	}

	public String getDbkay() {
		return dbkay;
	}

	public String ArrayJoin(List<String> lis, String spl) {
		StringArray result = new StringArray();
		for (int i = 0; i < lis.size(); i++) {
			if (!"".equals(lis.get(i).trim())) {
				result.push(lis.get(i));
			}
		}
		return result.join(spl);
	}

	public String ArrayJoin(String[] lis, String spl) {
		StringArray result = new StringArray();
		for (int i = 0; i < lis.length; i++) {
			if (!"".equals(lis[i].trim())) {
				result.push(lis[i]);
			}
		}
		return result.join(spl);
	}

	public String[] ArrayReMove(String[] lis, String v) {
		String[] result = new String[lis.length];
		for (int i = 0; i < lis.length; i++) {
			if (!v.equals(lis[i])) {
				result[i] = lis[i];
			} else {
				result[i] = "";
			}
		}
		return result;
	}

	public String[] ArrayReMove(String[] lis, int j) {
		String[] result = new String[lis.length];
		for (int i = 0; i < lis.length; i++) {
			if (i != j) {
				result[i] = lis[i];
			} else {
				result[i] = "";
			}
		}
		return result;
	}

	public String[] ArrayToString(List<String> li) {
		String[] result = new String[li.size()];
		for (int i = 0; i < li.size(); i++) {
			result[i] = li.get(i);
		}
		return result;
	}
}
