package com.tlv8.core.grid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.helper.DataTypeHelper;
import com.tlv8.base.utils.StringArray;
import com.tlv8.system.BaseController;

/**
 * grid数据相关操作
 * 
 * @author chenqian
 *
 */
@Controller
@RequestMapping("/core")
public class GridDataController extends GridCoreController {

	/**
	 * 数据加载
	 * 
	 * @param dbkey       数据库
	 * @param table       表
	 * @param columns     列
	 * @param columnstype 类数据类型
	 * @param where       固定条件
	 * @param orderby     排序
	 * @param page        页码
	 * @param rows        每页行数
	 * @param filter      查询条件
	 * @param searchtext  搜索内容
	 * @return Object
	 */
	@ResponseBody
	@RequestMapping("loadGridData")
	public Object execute(String dbkey, String table, String columns, String columnstype, String where, String orderby,
			int page, int rows, String filter, String searchtext) {
		columns = deCode(columns);
		columnstype = deCode(columnstype);
		where = deCode(where);
		orderby = deCode(orderby);
		filter = deCode(filter);
		searchtext = deCode(searchtext);
		Map<String, Object> map = new HashMap<>();
		SqlSession session = DBUtils.getSession(dbkey);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			String[] relations = columns.split(",");
			String[] datatypes = columnstype.split(",");
			StringArray cells = new StringArray(relations);
			if (dbkey.equalsIgnoreCase("system")) {
				if (cells.indexOfIgnoreCase("sid") < 0) {
					cells.push("SID");
				}
			} else {
				if (cells.indexOfIgnoreCase("fid") < 0) {
					cells.push("FID");
				}
			}
			if (cells.indexOfIgnoreCase("version") < 0) {
				cells.push("VERSION");
			}
			List<String> sp = new ArrayList<String>();
			SQL sql = new SQL();
			sql.SELECT(cells.join(","));
			sql.FROM(table);
			if (filter != null && !"".equals(filter)) {
				where += "and (" + filter + ")";
			}
			if (searchtext != null && !"".equals(searchtext)) {
				StringArray sfilter = new StringArray();
				for (int i = 0; i < relations.length; i++) {
					if (!relations[i].equals("No") && !relations[i].equals("master_check")
							&& !datatypes[i].equals("date") && !datatypes[i].equals("datetime")) {
						sfilter.push(relations[i] + " like ? ");
						sp.add("%" + searchtext + "%");
					}
				}
				where += "and (" + sfilter.join(" or ") + ")";
			}
			if (where != null && !"".equals(where)) {
				sql.WHERE(where);
			}
			if (orderby != null && !"".equals(orderby)) {
				sql.ORDER_BY(orderby);
			}
			int startrow = (page == 0) ? 0 : (page - 1) * rows;
			String sql_count = "select count(*) from (" + sql.toString() + ")a";
			int total = 0;
			PreparedStatement ps1 = conn.prepareStatement(sql_count);
			for (int p = 0; p < sp.size(); p++) {
				ps1.setString(p + 1, sp.get(p));
			}
			ResultSet rs1 = ps1.executeQuery();
			if (rs1.next()) {
				total = rs1.getInt(1);
			}
			int allpage = getPages(total, rows);
			if (allpage == 1) {
				page = 1;
				startrow = 0;
			}
			sql.LIMIT(rows);
			sql.OFFSET(startrow);
			ps = conn.prepareStatement(sql.toString());
			for (int p = 0; p < sp.size(); p++) {
				ps.setString(p + 1, sp.get(p));
			}
			rs = ps.executeQuery();
			List<Map<String, Object>> datas = new ArrayList<>();
			while (rs.next()) {
				Map<String, Object> data = new HashMap<>();
				for (int i = 0; i < relations.length; i++) {
					String cellname = relations[i];
					String cellvalue = rs.getString(cellname);
					if (cellvalue == null) {
						cellvalue = "";
					} else {
						if ("date".equals(datatypes[i])) {
							cellvalue = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate(cellname));
						} else if ("datetime".equals(datatypes[i])) {
							cellvalue = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rs.getTime(cellname));
						}
					}
					data.put(cellname, cellvalue);
				}
				if (rs.getString("FID") != null) {
					data.put("rowid", rs.getString("FID"));
				} else {
					data.put("rowid", rs.getString("SID"));
				}
				data.put("version", rs.getInt("VERSION"));
				datas.add(data);
			}
			map.put("data", datas);
			map.put("allpage", allpage);
			map.put("page", page);
			map.put("total", total);
		} catch (Exception e) {
			Sys.printErr(e);
		} finally {
			DBUtils.CloseConn(session, conn, ps, rs);
		}
		return map;
	}

	/**
	 * 数据保存
	 * 
	 * @param dbkey    数据库
	 * @param table    表
	 * @param datas    数据
	 * @param billid   外键
	 * @param billcell 外键字段
	 * @return Object
	 */
	@SuppressWarnings({ "resource" })
	@ResponseBody
	@RequestMapping("saveGridData")
	public Object saveData(String dbkey, String table, String datas, String billid, String billcell) {
		Map<String, Object> map = new HashMap<>();
		String userid = new BaseController().getContext().getCurrentPersonID();
		if (userid == null || "".equals(userid)) {
			String msg = "未登录或登录已超时，不允许操作!";
			map.put("flag", "timeout");
			map.put("message", msg);
			Sys.printMsg(msg);
			return map;
		}
		datas = deCode(datas);
		billid = deCode(billid);
		billcell = deCode(billcell);
		SqlSession session = DBUtils.getSession(dbkey);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
			String key = "FID";
			if ("system".equals(dbkey)) {
				key = "SID";
			}
			SQL sql = new SQL();
			sql.SELECT("version");
			sql.FROM(table);
			sql.WHERE(key + "=?");
			JSONArray jsona = JSON.parseArray(datas);
			for (int i = 0; i < jsona.size(); i++) {
				JSONObject json = jsona.getJSONObject(i);
				ps = conn.prepareStatement(sql.toString());
				ps.setString(1, json.getString("rowid"));
				rs = ps.executeQuery();
				if (rs.next()) {
					int version = rs.getInt(1);
					if (version > json.getIntValue("version")) {
						throw new Exception("数据已被修改，请刷新之后在编辑!");
					} else {
						version++;
					}
					SQL upsql = new SQL();
					upsql.UPDATE(table);
					upsql.SET("version=" + version);
					Set<String> iter = json.keySet();
					StringArray refs = new StringArray();
					for (String ref : iter) {
						if (!"rowid".equals(ref) && !"version".equals(ref)) {
							upsql.SET(ref + "=?");
							refs.push(ref);
						}
					}
					if (billcell != null && billid != null) {
						upsql.SET(billcell + "='" + billid + "'");
					}
					upsql.WHERE(key + "=?");
					PreparedStatement ps1 = conn.prepareStatement(upsql.toString());
					for (int n = 0; n < refs.getLength(); n++) {
						String dataType = DataTypeHelper.getColumnDataType(conn, table, refs.get(n));
						String addval = json.getString(refs.get(n));
						praperTimeVal(ps1, n + 1, addval, dataType);
					}
					ps1.setString(refs.getLength() + 1, json.getString("rowid"));
					ps1.executeUpdate();
				} else {
					SQL addsql = new SQL();
					addsql.INSERT_INTO(table);
					Set<String> iter = json.keySet();
					StringArray refs = new StringArray();
					StringArray vals = new StringArray();
					for (String ref : iter) {
						if (!"rowid".equals(ref) && !"version".equals(ref)) {
							refs.push(ref);
							vals.push("?");
						}
					}
					String billpa = "";
					String billva = "";
					if (billcell != null && billid != null) {
						billpa = "," + billcell;
						billva = ",'" + billid + "'";
					}
					vals.push("?");
					addsql.INTO_COLUMNS(refs.join(",") + "," + key + billpa + ",version");
					addsql.INTO_VALUES(vals.join(",") + billva + ",0");
					PreparedStatement ps2 = conn.prepareStatement(addsql.toString());
					for (int m = 0; m < refs.getLength(); m++) {
						String dataType = DataTypeHelper.getColumnDataType(conn, table, refs.get(m));
						String addval = json.getString(refs.get(m));
						praperTimeVal(ps2, m + 1, addval, dataType);
					}
					ps2.setString(refs.getLength() + 1, json.getString("rowid"));
					ps2.executeUpdate();
				}
			}
			session.commit(true);
			map.put("flag", "true");
		} catch (Exception e) {
			map.put("flag", "false");
			map.put("message", e.getMessage());
			session.rollback(true);
			Sys.printErr(e);
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, rs);
		}
		return map;
	}

	/**
	 * 删除数据
	 * 
	 * @param dbkey  数据库
	 * @param table  表
	 * @param rowids 主键值（多行用逗号分隔）
	 * @return Object
	 */
	@ResponseBody
	@RequestMapping("removeGridData")
	public Object removeData(String dbkey, String table, String rowids) {
		Map<String, Object> map = new HashMap<>();
		String userid = new BaseController().getContext().getCurrentPersonID();
		if (userid == null || "".equals(userid)) {
			String msg = "未登录或登录已超时，不允许操作!";
			map.put("flag", "timeout");
			map.put("message", msg);
			Sys.printMsg("未登录或登录已超时，不允许操作!");
			return map;
		}
		rowids = deCode(rowids);
		SqlSession session = DBUtils.getSession(dbkey);
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = session.getConnection();
			String key = "FID";
			if ("system".equals(dbkey)) {
				key = "SID";
			}
			String[] rows = rowids.split(",");
			SQL sql = new SQL();
			sql.DELETE_FROM(table);
			sql.WHERE(key + "=?");
			for (int i = 0; i < rows.length; i++) {
				ps = conn.prepareStatement(sql.toString());
				ps.setString(1, rows[i]);
				ps.executeUpdate();
			}
			session.commit(true);
			map.put("flag", "true");
		} catch (Exception e) {
			map.put("flag", "false");
			map.put("message", e.getMessage());
			session.rollback(true);
			Sys.printErr(e);
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, conn, ps, null);
		}
		return map;
	}
}
