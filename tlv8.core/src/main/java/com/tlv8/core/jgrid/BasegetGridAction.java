package com.tlv8.core.jgrid;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.NamingException;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.CodeUtils;
import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.AesEncryptUtil;
import com.tlv8.base.utils.StringArray;

/**
 * @author 陈乾
 * @category grid基础动作
 */
public class BasegetGridAction extends ActionSupport {
	protected Data data = new Data();
	protected String gridid = "";// 列表ID（必须）
	protected String dbkay = "system";// 数据连接
	protected String insql = "";// 查询的SQL
	protected String table = "";// 查询的表名
	protected String columns = null;// 列名（逗号分隔）
	protected String columnstype = null;// 数据类型（逗号分隔）类型的顺序必须和列名顺序一致
	protected String master = null;// 是否多选
	protected String showindex = null;// 是否有序号
	protected String where = null;// 插选条件
	protected String orderby = null;// 排序字段（多个用逗号分隔 如：sCode,sName）
	protected String billid = null;// 外键
	protected int page = 1;// 开始页
	protected int allpage = 1;// 总页数
	protected int allrows = 1;// 总行数
	protected int rows = 20;// 但也行数 默认20行数据

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
		this.where = CodeUtils.getDoubleDecode(where);
		this.where = AesEncryptUtil.desEncrypt(this.where);
		this.where = CodeUtils.getDoubleDecode(this.where);
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

	public void setColumns(String columns) {
		try {
			this.columns = URLDecoder.decode(columns, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getColumns() {
		return columns;
	}

	public void setColumnstype(String columnstype) {
		try {
			this.columnstype = URLDecoder.decode(columnstype, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getColumnstype() {
		return columnstype;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getMaster() {
		return master;
	}

	public void setShowindex(String showindex) {
		this.showindex = showindex;
	}

	public String getShowindex() {
		return showindex;
	}

	public void setDbkay(String dbkay) {
		this.dbkay = dbkay;
	}

	public String getDbkay() {
		return dbkay;
	}

	public void setTable(String table) {
		try {
			this.table = URLDecoder.decode(table, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.table = table;
		}
	}

	public String getTable() {
		return table;
	}

	public void setGridid(String gridid) {
		try {
			this.gridid = URLDecoder.decode(gridid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getGridid() {
		return gridid;
	}

	public void setOrderby(String orderby) {
		try {
			this.orderby = URLDecoder.decode(orderby, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getOrderby() {
		return orderby;
	}

	public void setInsql(String insql) {
		try {
			this.insql = URLDecoder.decode(insql, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.insql = AesEncryptUtil.desEncrypt(this.insql);
	}

//	public String getInsql() {
//		return insql;
//	}

	public int getAllrows() {
		return allrows;
	}

	public void setAllrows(int allrows) {
		this.allrows = allrows;
	}

	@SuppressWarnings("rawtypes")
	public String createGrid(int row, int page) throws Exception {
		String result = "";
		String[] selectcolumn = (columns != null) ? columns.split(",") : null;
		String[] columndatatype = (columnstype != null) ? columnstype.split(",") : null;
		if (selectcolumn == null) {
			throw new Exception("列名未指定！");
		}
		String selectcolumns = "";
		columns = "";
		if (dbkay == null || "".equals(dbkay))
			dbkay = "system";
		for (int i = 0; i < selectcolumn.length; i++) {
			if (selectcolumn.length == columndatatype.length) {
				if (!"no".equals(selectcolumn[i].toLowerCase())
						&& !"master_check".equals(selectcolumn[i].toLowerCase())) {
					if ("DATE".equals(columndatatype[i].toUpperCase())) {
						if (DBUtils.IsOracleDB(dbkay) || DBUtils.IsDMDB(dbkay) || DBUtils.IsPostgreSQL(dbkay)) {
							selectcolumns += ",to_char(" + selectcolumn[i] + ",'yyyy-MM-dd')" + selectcolumn[i];
						} else if (DBUtils.IsMySQLDB(dbkay)) {
							selectcolumns += ",DATE_FORMAT(" + selectcolumn[i] + ",'%Y-%m-%d')" + selectcolumn[i];
						} else {
							selectcolumns += ",convert(date," + selectcolumn[i] + ")" + selectcolumn[i];

						}
					} else if ("DATETIME".equals(columndatatype[i].toUpperCase())) {
						if (DBUtils.IsOracleDB(dbkay) || DBUtils.IsDMDB(dbkay) || DBUtils.IsPostgreSQL(dbkay)) {
							selectcolumns += ",to_char(" + selectcolumn[i] + ",'yyyy-MM-dd hh24:mi:ss')"
									+ selectcolumn[i];
						} else if (DBUtils.IsMySQLDB(dbkay)) {
							selectcolumns += ",DATE_FORMAT(" + selectcolumn[i] + ",'%Y-%m-%d %H:%i:%s')"
									+ selectcolumn[i];
						} else {
							selectcolumns += ",convert(varchar(23)," + selectcolumn[i] + ",20)" + selectcolumn[i];
						}
					} else {
						selectcolumns += "," + selectcolumn[i];
					}
					columns += "," + selectcolumn[i];
				}
			}
		}
		columns = columns.replaceFirst(",", "");
		selectcolumns = selectcolumns.replaceFirst(",", "");
		String sql = "";

		if (DBUtils.IsOracleDB(dbkay) || DBUtils.IsDMDB(dbkay)) {
			sql = "select fID,Cucolumns from(select a.*,rownum my_num from(select fID,selectcolumns from " + table
					+ " where 1=1 " + " param)a where rownum <= endrow) t where t.my_num> startrow";
		} else if (DBUtils.IsMySQLDB(dbkay)) {
			sql = "select fID,Cucolumns from(select fID,selectcolumns from " + table
					+ " where fID is not null param limit startrow,endrow)a";
		} else if (DBUtils.IsPostgreSQL(dbkay)) {
			sql = "select fID,Cucolumns from(select fID,selectcolumns from " + table
					+ " where fID is not null param limit endrow OFFSET startrow)a";
		} else {
			sql = "select fID,Cucolumns from (select fID,selectcolumns from " + table
					+ " where fID in (select top endrow fID from " + table + " where 1=1 "
					+ " param) and fID not in (select top startrow fID from " + table + " where 1=1 " + " param)) a";
		}
		String sql_count = "select count(*) ALLPAGE from " + table + " where 1=1 param";
//		if (DBUtils.IsMySQLDB(dbkay)) {
//			sql_count = "select count(*) ALLPAGE from " + table + " t where 1=1 param";
//		}
		int startrow = (page == 0) ? 0 : (page - 1) * row;
		int endrow = (page == 0) ? (startrow + row) : (startrow + row);
		String filter = where;
		String param = (filter != null && !("").equals(filter)) ? " and (" + filter + ")" : "";
		param += (billid != null && !"".equals(billid)) ? "fbillid='" + billid + "'" : "";
		if (!DBUtils.IsOracleDB(dbkay) && !DBUtils.IsDMDB(dbkay) && !DBUtils.IsMySQLDB(dbkay)) {
			param = param.replace("||", "+");// SQLServer连接符用+号
		}
		String countparam = param;
		if (!"".equals(orderby) && orderby != null) {
			param += " order by " + orderby + ", fID asc";
		} else {
			param += " order by fID asc";
		}
		if ("".equals(columns.trim()) || columns == null) {
			return "";
		}
		if (columns.toUpperCase().indexOf(",VERSION") < 0) {
			columns += ",VERSION";
		}
		if (selectcolumns.toUpperCase().indexOf(",VERSION") < 0) {
			selectcolumns += ",VERSION";
		}
		sql = sql.replace("Cucolumns", columns);
		sql = sql.replace("selectcolumns", selectcolumns);
		sql = sql.replace("param", param);
		sql_count = sql_count.replace("param", countparam);
		sql_count = sql_count.replace("Cucolumns", columns);
		if (dbkay == null || "".equals(dbkay))
			dbkay = "system";
		if ("system".equals(dbkay)) {
			sql = sql.replaceAll("fID", "sID");
		}
		if (!DBUtils.IsMySQLDB(dbkay)) {
			String userlablorder = getColumnOrderby(columns);
			if (userlablorder != null && !"".equals(userlablorder)) {
				sql += " order by " + userlablorder;
			}
		}
		try {
			boolean ismaster = false;
			if (master != null && "TRUE".equals(master.toUpperCase()))
				ismaster = true;
			boolean isshowindex = false;
			if (showindex != null && "TRUE".equals(showindex.toUpperCase()))
				isshowindex = true;
			Map map = DBUtils.selectStringList(dbkay, sql_count).get(0);
			if (DBUtils.IsPostgreSQL(dbkay)) {
				allrows = Integer.parseInt(map.get("allpage").toString());
			} else {
				allrows = Integer.parseInt(map.get("ALLPAGE").toString());
			}
			data.setAllrows(allrows);
			allpage = Page.getCount(dbkay, sql_count, row);
			if (allpage == 1) {
				page = 1;
				startrow = 0;
			}
			try {
				sql = sql.replace("endrow",
						(DBUtils.IsMySQLDB(dbkay) || DBUtils.IsPostgreSQL(dbkay) ? rows : endrow) + "");
				sql = sql.replace("startrow", startrow + "");
			} catch (Exception e) {
			}
			result = Grid.createGridBysql(dbkay, sql, ismaster, isshowindex, startrow + 1, gridid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("数据查询失败~");
		} catch (NamingException ne) {
			throw new NamingException(ne.getMessage());
		}
		return result;
	}

	public String[] getOrdrByColumns() {
		String[] res = null;
		if (!"".equals(orderby) && orderby != null) {
			res = orderby.split(",");
		}
		return res;
	}

	public String removeOrderDas(String s_orderby) {
		s_orderby = s_orderby.replace(" asc", "");
		s_orderby = s_orderby.replace(" desc", "");
		s_orderby = s_orderby.replace(" ASC", "");
		s_orderby = s_orderby.replace(" DESC", "");
		return s_orderby;
	}

	public String getColumnOrderby(String columns1) {
		String res = null;
		String[] orderbys = getOrdrByColumns();
		StringArray useorder = new StringArray();
		if (orderbys != null) {
			for (int i = 0; i < orderbys.length; i++) {
				String s_orderby = removeOrderDas(orderbys[i]);
				if (columns1.toUpperCase().indexOf(s_orderby.trim().toUpperCase()) > -1) {
					useorder.push(orderbys[i]);
				}
			}
			res = useorder.join(",");
		}
		return res;
	}

	public String createGridBySQL(int page, int row) throws SQLException, NamingException {
		String result = "";
		String sql = "";
		if (dbkay == null || "".equals(dbkay))
			dbkay = "system";
		String inwhere = "where 1=1 ";
		columns = columns.replace("No,", "");
		columns = columns.replace("master_check,", "");
		if (columns.toUpperCase().indexOf(",VERSION") < 0) {
			columns += ",VERSION";
		}
		String lSQL = (insql.toLowerCase().indexOf("group") > 0)
				? insql.substring(0, insql.toLowerCase().indexOf("group"))
				: insql;
		String rSQL = (insql.toLowerCase().indexOf("group") > 0) ? insql.substring(insql.toLowerCase().indexOf("group"))
				: "";
		if ((insql.toLowerCase().indexOf("group") > 0) && (insql.toLowerCase().indexOf("where") > 0)) {
			inwhere = insql.substring(insql.toLowerCase().indexOf("where"), insql.toLowerCase().indexOf("group"));
			lSQL = lSQL.substring(0, lSQL.toLowerCase().indexOf("where"));
		} else if (insql.toLowerCase().indexOf("where") > 0) {
			inwhere = insql.substring(insql.toLowerCase().indexOf("where"));
			lSQL = lSQL.substring(0, lSQL.toLowerCase().indexOf("where"));
		}
		if (DBUtils.IsOracleDB(dbkay) || DBUtils.IsDMDB(dbkay)) {
			sql = "select fID," + columns + " from(select a.*,rownum my_num from(select *from (" + lSQL + inwhere + " "
					+ rSQL + ") where 1=1 param )a where rownum <= endrow ) t where t.my_num> startrow";
		} else if (DBUtils.IsMySQLDB(dbkay)) {
			sql = "select fID," + columns + " from(" + lSQL + inwhere + " " + rSQL
					+ ") where fID is not null param limit startrow,endrow";
		} else if (DBUtils.IsPostgreSQL(dbkay)) {
			sql = "select fID," + columns + " from(" + lSQL + inwhere + " " + rSQL
					+ ") where fID is not null param limit endrow OFFSET startrow";
		} else {
			sql = "select fID," + columns + " from (select * from (" + lSQL + inwhere + " " + rSQL
					+ ") where fID in (select top endrow fID from (" + lSQL + inwhere + " " + rSQL + ") where 1=1 "
					+ " param) and fID not in (select top startrow fID from (" + lSQL + inwhere + " " + rSQL
					+ ") where 1=1 " + " param)";
		}
		String sql_count = "select count(*) ALLPAGE from (" + insql + ") where 1 =1 param";
		int startrow = (page == 0) ? 0 : (page - 1) * row;
		int endrow = (page == 0) ? (startrow + row) : (startrow + row);
		if (DBUtils.IsMySQLDB(dbkay))
			endrow = row;
		String filter = where;
		// System.out.println(filter);
		String param = (filter != null && !("").equals(filter)) ? " and (" + filter + ")" : "";
		param += (billid != null && !"".equals(billid)) ? "fbillid='" + billid + "'" : "";
		if (!"".equals(orderby) && orderby != null) {
			param += " order by " + orderby + ", fID asc";
		} else {
			param += " order by fID asc";
		}
		// System.out.println(param);
		sql = sql.replace("param", param);
		// System.out.println(sql);
		sql_count = sql_count.replace("param", param);
		if ("system".equals(dbkay)) {
			sql = sql.replaceAll("fID", "sID");
			sql_count = sql_count.replaceAll("fID", "sID");
		}
		try {
			boolean ismaster = false;
			if (master != null && "TRUE".equals(master.toUpperCase()))
				ismaster = true;
			boolean isshowindex = false;
			if (showindex != null && "TRUE".equals(showindex.toUpperCase()))
				isshowindex = true;
			allpage = Page.getCount(dbkay, sql_count, rows);
			if (allpage == 1) {
				page = 1;
				startrow = 0;
			}
			try {
				// sql = String.format(sql, endrow, startrow);
				sql = sql.replace("endrow",
						(DBUtils.IsMySQLDB(dbkay) || DBUtils.IsPostgreSQL(dbkay) ? rows : endrow) + "");
				sql = sql.replace("startrow", startrow + "");
			} catch (Exception e) {
			}
			// System.out.println(sql);
			result = Grid.createGridBysql(dbkay, sql, ismaster, isshowindex, startrow + 1, gridid);// (dbkey,sql，是否多选，是否序号，开始行数)
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("数据查询失败~");
		} catch (NamingException ne) {
			throw new NamingException(ne.getMessage());
		}
		return result;
	}
}
