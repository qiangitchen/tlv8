package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

/**
 * @author ChenQain
 * @S:保存功能分配
 */
@Controller
@Scope("prototype")
public class savePermitionAction extends ActionSupport {
	private Data data = new Data();
	private String table;
	private String cells;
	private String rowid = null;
	private String where = null;
	private int page = 1;
	private int allpage = 1;
	private int rows = 20;// 默认20行数据

	public Data getdata() {
		return this.data;
	}

	public void setdata(Data data) {
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

	@ResponseBody
	@RequestMapping("/savePermitionAction")
	public Object execute() throws Exception {
		data = new Data();
		String r = "";
		String m = "success";
		String f = "";
		try {
			r = setPermi();
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.getMessage();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		data.setPage(page);
		data.setAllpage(allpage);
		return this;
	}

	public String setPermi() throws Exception {
		String result = "success";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		String columns = "";
		String sql = "";
		String fID = "";
		String chSQL = "";
		HashMap<String, String> cell = Data.getCell(cells);
		boolean isNew = true;
		if (cell.containsKey("ROWID")) {
			String nfID = cell.get("ROWID");
			String SreachSql = "select * from " + getTable() + " where SID  = '" + nfID + "'";
			List<Map<String, String>> list = DBUtils.execQueryforList("system", SreachSql);
			if (list.size() > 0) {
				isNew = false;
			}
		}
		if (cell.containsKey("ROWID") && !"newrowid".equals(cell.get("ROWID"))
				&& !cell.get("ROWID").endsWith("newrowid") && isNew == false) {
			fID = cell.get("ROWID");
			setRowid(fID);
			Set<String> set = cell.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (!key.equalsIgnoreCase("ROWID")) {
					DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						dataFormat.parse(cell.get(key));
						if (DBUtils.IsOracleDB("system") || DBUtils.IsPostgreSQL("system")) {
							columns += "," + key + "=to_date('" + dataFormat.format(dataFormat.parse(cell.get(key)))
									+ "','yyyy-MM-dd')";
						} else {
							columns += "," + key + "=cast('" + dataFormat.format(dataFormat.parse(cell.get(key)))
									+ "' as datetime)";
						}
					} catch (Exception e) {
						columns += "," + key + "='" + cell.get(key) + "'";
					}

				}
			}
			columns = columns.replaceFirst(",", "");
			sql = "update " + table + " set " + columns + " where sID='" + fID + "'";
		} else {
			fID = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
			if (cell.get("ROWID") != null && !"".equals(cell.get("ROWID")) && !"undefined".equals(cell.get("ROWID"))
					&& !"null".equals(cell.get("ROWID"))) {
				fID = cell.get("ROWID");
			}
			setRowid(fID);
			Set<String> set = cell.keySet();
			Iterator<String> it = set.iterator();
			String relation = "";
			String prelation = "";
			String values = "";
			while (it.hasNext()) {
				String key = (String) it.next();
				if (!"ROWID".equals(key)) {
					relation += "," + key;
					DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						dataFormat.parse(cell.get(key));
						if (DBUtils.IsOracleDB("system") || DBUtils.IsPostgreSQL("system")) {
							values += ",to_date('" + dataFormat.format(dataFormat.parse(cell.get(key)))
									+ "','yyyy-MM-dd')";
						} else {
							values += ",cast('" + dataFormat.format(dataFormat.parse(cell.get(key))) + "'as datetime)";
						}
					} catch (Exception e) {
						values += ",'" + cell.get(key) + "'";
					}
				}
			}
			prelation = "sID" + prelation;
			relation = "sID" + relation;
			sql = "insert into " + table + "(" + relation + ")values('" + fID + "'" + values + ")";
			chSQL = "select SID from " + table + " where SPERMISSIONROLEID = '" + cell.get("SPERMISSIONROLEID")
					+ "' and SPROCESS = '" + cell.get("SPROCESS") + "' and SACTIVITY = '" + cell.get("SACTIVITY") + "'";
		}
		SqlSession session = DBUtils.getSession("system");
		try {
			conn = session.getConnection();
			stm = conn.createStatement();
			if (!"".equals(chSQL.trim()) && chSQL != null) {
				rs = stm.executeQuery(chSQL);
				if (!rs.next())
					stm.executeUpdate(sql);
			} else
				stm.executeUpdate(sql);
			session.commit(true);
		} catch (SQLException e) {
			session.rollback(true);
			result = "false";
			throw new SQLException(e + ":" + chSQL + ":" + sql);
		} finally {
			DBUtils.closeConn(session, conn, stm, rs);
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
		this.where = where;
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
		this.rowid = rowid;
	}

	public String getRowid() {
		return rowid;
	}
}
