package com.tlv8.doc.clt.doc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.core.utils.UserFullInfo;

@Controller
@Scope("prototype")
@SuppressWarnings({ "rawtypes" })
public class saveFolderAction extends ActionSupport {
	private Data data = new Data();
	private String table;
	private String cells;
	private String rowid = null;
	private String where = null;
	private int page = 1;
	private int allpage = 1;
	private int rows = 20;

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
	@RequestMapping("/saveFolderAction")
	@Override
	public Object execute() throws Exception {
		// System.out.println("chenqian-test:" + getTable());
		data = new Data();
		String r = "";
		String m = "success";
		String f = "";
		try {
			r = saveData();
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.toString();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		data.setPage(page);
		data.setAllpage(allpage);
		return data;
	}

	@SuppressWarnings("deprecation")
	public String saveData()
			throws SQLException, NamingException, SAXException, IOException, ParserConfigurationException {
		// System.out.println("保存操作！...");
		String result = "success";
		Connection conn = null;
		Statement stm = null;
		String columns = "";
		String sql = "";
		String fID = "";
		String oSID = "";
		HashMap<String, String> cell = Data.getCell(cells);
		if (cell.containsKey("rowid") && !"newrowid".equals(cell.get("rowid"))
				&& !cell.get("rowid").endsWith("newrowid")) {
			fID = cell.get("rowid");
			setRowid(fID);
			Set<String> set = cell.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (!key.equalsIgnoreCase("rowid")) {
					DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						dataFormat.parse(cell.get(key));
						if (DBUtils.IsOracleDB("system")) {
							columns += "," + key + "=to_date('" + dataFormat.format(dataFormat.parse(cell.get(key)))
									+ "','yyyy-MM-dd')";
						} else {
							columns += "," + key + "=cast('" + dataFormat.format(dataFormat.parse(cell.get(key)))
									+ "' as datetime)";
						}
					} catch (Exception e) {
						// System.out.println("非日期格式!");
						columns += "," + key + "='" + cell.get(key) + "'";
					}

				}
			}
			columns = columns.replaceFirst(",", "");
			sql = "update " + table + " set " + columns + ",version=version+1 where sID='" + fID + "'";
			oSID = fID;
		} else {
			fID = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
			setRowid(fID);
			Set<String> set = cell.keySet();
			Iterator<String> it = set.iterator();
			String relation = "";
			String prelation = "";
			String values = "";
//			String pvalues = "";
			while (it.hasNext()) {
				String key = (String) it.next();
				if (!"rowid".equals(key)) {
					relation += "," + key;
					if ("SCODE".equals(key.toUpperCase()) || "SNAME".equals(key.toUpperCase()))
						prelation += "," + key;
					DateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
					try {
						dataFormat.parse(cell.get(key));
						if (DBUtils.IsOracleDB("system")) {
							values += ",to_date('" + dataFormat.format(dataFormat.parse(cell.get(key)))
									+ "','yyyy-MM-dd')";
						} else {
							values += ",cast('" + dataFormat.format(dataFormat.parse(cell.get(key))) + "'as datetime)";
						}
					} catch (Exception e) {
						// System.out.println("非日期格式!");
						values += ",'" + cell.get(key) + "'";
//						if ("SCODE".equals(key.toUpperCase())
//								|| "SNAME".equals(key.toUpperCase()))
//							pvalues += ",'" + cell.get(key) + "'";
					}
				}
			}
			prelation = "sID" + prelation + ",version";
			values = "'" + fID + "'" + values;
			oSID = fID;
			relation = "sID" + relation + ",version";
			sql = "insert into " + table + "(" + relation + ")values(" + values + ",0)";
		}
		String upSQL = null;
		HashMap<String, String> user = UserFullInfo.get(cell.get("SCREATORID"));
		String PersonName = user.get("currentPersonName") == null ? "" : user.get("currentPersonName");
		String PersonFID = user.get("currentPersonFullID") == null ? "" : user.get("currentPersonFullID");
		String CREATORDEPTNAME = user.get("currentDeptName") == null ? "" : user.get("currentDeptName");
		if (sql.indexOf("insert") > -1) {
			if (cell.get("SPARENTID") != null && !"".equals(cell.get("SPARENTID"))) {
				if (DBUtils.IsOracleDB("system")) {
					upSQL = "update " + table
							+ " set (SDOCPATH,SDOCDISPLAYPATH,SCREATORFID,SCREATORNAME,SCREATORDEPTNAME,SCREATETIME,SFLAG)=( select t1.SDOCPATH||t2.SID||'"
							+ "/',t1.SDOCDISPLAYPATH||t2.SDOCNAME||'" + "/','" + PersonFID + "','" + PersonName + "','"
							+ CREATORDEPTNAME + "',sysdate,1 from " + table + " t1," + table + " t2 where t1.sID = '"
							+ cell.get("SPARENTID") + "' and t2.sID = '" + oSID + "') where sID = '" + oSID + "'";
				} else {
					upSQL = "update " + table
							+ " set (SDOCPATH,SDOCDISPLAYPATH,SCREATORFID,SCREATORNAME,SCREATORDEPTNAME,SCREATETIME,SFLAG)=( select t1.SDOCPATH+t2.SID+'"
							+ "/',t1.SDOCDISPLAYPATH+t2.SDOCNAME+'" + "/','" + PersonFID + "','" + PersonName + "','"
							+ CREATORDEPTNAME + "',getdate(),1 from " + table + " t1," + table + " t2 where t1.sID = '"
							+ cell.get("SPARENTID") + "' and t2.sID = '" + oSID + "') where sID = '" + oSID + "'";
				}
			} else {
				if (DBUtils.IsOracleDB("system")) {
					upSQL = "update " + table
							+ " set (SDOCPATH,SDOCDISPLAYPATH,SKIND)=(select '/','/','dir' from dual) where sID = '"
							+ oSID + "'";
				} else {
					upSQL = "update " + table + " set SDOCPATH = '/',SDOCDISPLAYPATH= '/',SKIND = 'dir' where sID = '"
							+ oSID + "'";
				}
			}
		}
		String nsSQL = "update " + table
				+ " set (SNAMESPACE,SFLAG)=(select sID,'1' from sa_docnamespace where sFlag = 1) where sID = '" + oSID
				+ "'";
		if (DBUtils.IsMySQLDB("system")) {
			nsSQL = "update " + table
					+ " set SNAMESPACE =(select sID from sa_docnamespace where sFlag = 1) where sID = '" + oSID + "'";
		}
		// System.out.println(sql);
		// System.out.println(upSQL);
		try {
			conn = DBUtils.getAppConn("system");
			stm = conn.createStatement();
			stm.executeUpdate(sql);
			if (sql.indexOf("insert") > -1) {
				if (cell.get("SPARENTID") != null && !"".equals(cell.get("SPARENTID"))) {
					if (DBUtils.IsMySQLDB("system")) {
						Map m = getOperParent(oSID, cell.get("SPARENTID"));
						upSQL = "update " + table + " set SDOCPATH = '" + m.get("SDOCPATH") + "'"
								+ ",SDOCDISPLAYPATH = '" + m.get("SDOCDISPLAYPATH") + "'" + ",SCREATORFID = '"
								+ PersonFID + "'" + ",SCREATORNAME = '" + PersonName + "'" + ",SCREATORDEPTNAME='"
								+ CREATORDEPTNAME + "'" + ",SCREATETIME = '"
								+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "' "
								+ ",SFLAG=1 where sID = '" + oSID + "'";
					}
				}
			}
			if (!"".equals(upSQL) && upSQL != null)
				stm.execute(upSQL);
			if (!"".equals(nsSQL) && nsSQL != null)
				stm.execute(nsSQL);
		} catch (SQLException e) {
			result = "false";
			throw new SQLException(e + ":" + sql + ":" + upSQL);
		} finally {
			try {
				DBUtils.CloseConn(conn, stm, null);
			} catch (SQLException e) {
				throw new SQLException(e);
			}
		}
		return result;
	}

	private Map getOperParent(String oSID, String SPARENTID) throws SQLException {
		String sql = "select concat(t1.SDOCPATH,t2.SID,'"
				+ "/')SDOCPATH,concat(t1.SDOCDISPLAYPATH,t2.SDOCNAME,'/')SDOCDISPLAYPATH from " + table + " t1," + table
				+ " t2 where t1.sID = '" + SPARENTID + "' and t2.sID = '" + oSID + "'";
		List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
		return list.get(0);
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
