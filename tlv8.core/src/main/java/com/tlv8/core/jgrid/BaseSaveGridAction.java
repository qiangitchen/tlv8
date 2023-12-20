package com.tlv8.core.jgrid;

import java.io.IOException;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.tlv8.base.Data;
import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.db.RegexUtil;
import com.tlv8.base.helper.DataTypeHelper;
import com.tlv8.base.utils.StringArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.CodeUtils;

/**
 * 
 */
public class BaseSaveGridAction extends ActionSupport {
	private static Logger log = LoggerFactory.getLogger(BaseSaveGridAction.class);
	protected String dbkay = "";
	protected String table = "";
	protected String cells = "";
	protected String billid = "";
	protected Data data = new Data();

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String saveData()
			throws SQLException, NamingException, SAXException, IOException, ParserConfigurationException, Exception {
		log.info("grid保存数据...");
		String result = "success";
		String db = ("".equals(getDbkay()) || getDbkay() == null) ? "system" : getDbkay();
		String skey = ("system".equals(db)) ? "SID" : "FID";
		JSONObject json = null;
		if (cells != null && !"".equals(cells)) {
			cells = cells.replace("\\", "#JXX;");
			Pattern p = Pattern.compile("\r|\n");
			Matcher m = p.matcher(cells);
			cells = m.replaceAll("#ENTER;");
			try {
				json = JSON.parseObject(cells);
			} catch (Exception er) {
				Sys.packErrMsg("保存数据失败! 内容:" + cells);
				log.error("保存数据失败! 内容:" + cells);
			}
		}
		SqlSession session = DBUtils.getSession(dbkay);
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "";
		try {
			conn = session.getConnection();
			Set<String> it = json.keySet();
			for (String key : it) {
				String datas = json.getString(key);
				Map<String, String> cell = null;
				try {
					cell = Data.getCell(datas);
				} catch (Exception e) {
					continue;
				}
				boolean isNew = true;
				String SreachSql = "select VERSION from " + getTable() + " where " + skey + " = '" + key + "'";
				List<Map<String, String>> list = DBUtils.selectStringList(session, SreachSql);
				if (list.size() > 0) {
					isNew = false;
					String version = list.get(0).get("VERSION");
					if (version == null) {
						version = "0";
					}
					int ovi = Integer.parseInt(version);
					int nvi = Integer.parseInt(cell.get("VERSION"));
					if (nvi <= ovi) {
						extracted();
					}
				}
				StringArray cellvaluess = new StringArray();
				StringArray celllist = new StringArray();
				StringArray pvallist = new StringArray();
				List<String> values = new ArrayList<String>();
				if (isNew == false) {
					Set<String> set = cell.keySet();
					Iterator<String> its = set.iterator();
					while (its.hasNext()) {
						String keys = (String) its.next();
						String vals = cell.get(keys);
						vals = vals.replace("#JXX;", "\\");
						vals = vals.replace("#ENTER;", "\n");
						vals = vals.replace("#quot;", "\"");
						vals = CodeUtils.decodeSpechars(vals);
						if (!keys.equalsIgnoreCase("ROWID")) {
							celllist.push(keys);
							cellvaluess.push(keys + "=?");
							values.add(vals);
						}
					}
					sql = "update " + getTable() + " set " + cellvaluess.join(",") + " where " + skey + "=?";
				} else {
					Set<String> ist = cell.keySet();
					Iterator<String> its = ist.iterator();
					while (its.hasNext()) {
						String keys = (String) its.next();
						String vals = cell.get(keys);
						vals = vals.replace("#JXX;", "\\");
						vals = vals.replace("#ENTER;", "\n");
						vals = CodeUtils.decodeSpechars(vals);
						if (!"ROWID".equals(keys)) {
							celllist.push(keys);
							pvallist.push("?");
							values.add(vals);
						}
					}
					sql = "insert into " + getTable() + "(" + celllist.join(",") + "," + skey + ")values("
							+ pvallist.join(",") + ",?)";
				}
				ps = conn.prepareStatement(sql);
				for (int i = 0; i < values.size(); i++) {
					String dataType = DataTypeHelper.getColumnDataType(conn, getTable(), celllist.get(i));
					String addval = values.get(i);
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
				ps.setString(values.size() + 1, key);
				ps.executeUpdate();
			}
			session.commit(true);
		} catch (Exception e) {
			session.rollback(true);
			result = "false";
			log.error("grid保存数据失败! sql:" + sql + " " + e.getMessage());
			session.close();
			throw new SQLException(RegexUtil.getSubOraex(e.getMessage()));
		} finally {
			try {
				DBUtils.closeConn(session, conn, ps, null);
			} catch (Exception e) {
			}
		}
		log.info("grid保存数据完成.");
		return result;
	}

	private void extracted() throws Exception {
		throw new Exception("数据已被修改，请刷新数据再操作!");
	}

	public void setDbkay(String dbkay) {
		try {
			this.dbkay = URLDecoder.decode(dbkay, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getDbkay() {
		return dbkay;
	}

	public void setTable(String table) {
		try {
			this.table = URLDecoder.decode(table, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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

	public void setBillid(String billid) {
		try {
			this.billid = URLDecoder.decode(billid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getBillid() {
		return billid;
	}

}
