package com.tlv8.core.report;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.base.CodeUtils;

/**
 * @author ChenQain
 * @Excel导出主程序
 * @C 2011-11-27
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ExpportAction extends ActionSupport {
	private String dbkey;// 数据连接
	private String table;// 表名
	private String relation;// 字段
	private String labels;// 字段名（中文列名）
	private String where;// 条件
	private String orderby;// 排序
	private InputStream excelStream;
	private String expStatus;

	public void setRelation(String relation) {
		try {
			this.relation = URLDecoder.decode(relation, "UTF-8");
		} catch (Exception e) {
			this.relation = relation;
		}
	}

	public String getRelation() {
		return relation;
	}

	public void setTable(String table) {
		try {
			this.table = URLDecoder.decode(table, "UTF-8");
		} catch (Exception e) {
			this.table = table;
		}
	}

	public String getTable() {
		return table;
	}

	public void setWhere(String where) {
		try {
			this.where = URLDecoder.decode(where, "UTF-8");
		} catch (Exception e) {
			this.where = where;
		}
	}

	public String getWhere() {
		return where;
	}

	@RequestMapping(value = "/expportAction", method = RequestMethod.POST)
	public Object execute(@RequestHeader("User-Agent") String userAgent) throws Exception {
		// 创建ModelAndView视图
		Map objOut = new HashMap();
		if (relation == null || "".equals(relation.trim())) {
			expStatus = "字段不能为空！";
			request.setAttribute("expStatus", expStatus);
			return "/comon/report/export-input";
		}
		String[] relations = relation.split(",");
		String relationArray = "";
		String[] label = labels.split(",");
		String labelArray = "";
		int count = 0;
		for (int i = 0; i < relations.length; i++) {
			// 去除序号列 去除多选列
			if (!"No".equals(relations[i]) && !"master_check".equals(relations[i])) {
				if (count > 0) {
					relationArray += ",";
					labelArray += ",";
				}
				relationArray += relations[i];
				labelArray += label[i];
				count++;
			}
		}
		relation = relationArray.trim();
		labels = labelArray.trim();
		if ("".equals(relation.trim())) {
			expStatus = "字段不能为空！";
			request.setAttribute("expStatus", expStatus);
			return "/comon/report/export-input";
		}
		objOut.put("excelName", labels);
		String[] excelKeyArray = relation.split(",");
		objOut.put("excelKey", excelKeyArray);
		try {
			getData(objOut, excelKeyArray);
		} catch (Exception e) {
			e.printStackTrace();
			expStatus = e.getMessage();
			request.setAttribute("expStatus", expStatus);
			return "/comon/report/export-input";
		}
		// System.out.println(objOut);
		try {
			setExcelStream(getExcelInputStream(objOut));
		} catch (Exception e) {
			e.printStackTrace();
			expStatus = e.getMessage();
			request.setAttribute("expStatus", expStatus);
			return "/comon/report/export-input";
		}
		response.setContentType("application/vnd.ms-excel");
		return getByteResponseEntity(getExcelStream(), MediaType.APPLICATION_OCTET_STREAM, userAgent, "导出的Excel文件.xls");
	}

	public InputStream getExcelInputStream(Map objOut) throws Exception {
		// 将OutputStream转化为InputStream
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ExportExcel.exportExcel(out, objOut);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	@SuppressWarnings("deprecation")
	private void getData(Map objOut, String[] excelKeyArray) throws Exception {
		if (table == null || "".equals(table.trim())) {
			throw new Exception("表名不能为空！");
		}
		if (dbkey == null || "".equals(dbkey.trim())) {
			dbkey = "system";
		}
		if (where == null || "undefined".equals(where.trim()))
			where = "";
		if (where != null && !"".equals(where.trim())) {
			if (where.toLowerCase().indexOf("and") != 0)
				where = "and " + where;
		}
		if (orderby == null || "undefined".equals(orderby.trim()) || "".equals(orderby.trim())) {
			orderby = "";
		} else {
			orderby = "order by " + orderby;
		}
		String sql = "select " + relation + " from " + table + " where 1=1 " + where + " " + orderby;
		sql = sql.trim();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = DBUtils.getAppConn(dbkey);
			stm = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stm.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			rs.last();
			int size = rs.getRow();
			String[][] dataLi = new String[excelKeyArray.length][size];
			rs.beforeFirst();
			int j = 0;
			while (rs.next()) {
				for (int i = 0; i < excelKeyArray.length; i++) {
					String type = rsmd.getColumnTypeName(i + 1);
					String dval = "";
					if ("DATETIME".equals(type.toUpperCase())) {
						SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = rs.getDate(i + 1);
						try {
							dval = fmt.format(date);
						} catch (Exception er1) {
						}
					} else if ("DATE".equals(type.toUpperCase())) {
						SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = rs.getDate(i + 1);
						try {
							dval = fmt.format(date);
							if ("00:00:00".equals(dval.substring(11))) {
								dval = dval.substring(0, 11).trim();
							}
						} catch (Exception er1) {
							fmt = new SimpleDateFormat("yyyy-MM-dd");
							try {
								dval = fmt.format(date);
							} catch (Exception er2) {
							}
						}
					} else if ("CLOB".equals(type.toUpperCase())) {
						Clob clob = rs.getClob(i + 1);
						String content = "";
						if (clob != null) {
							BufferedReader in = new BufferedReader(clob.getCharacterStream());
							StringWriter out = new StringWriter();
							int c;
							try {
								while ((c = in.read()) != -1) {
									out.write(c);
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							content = out.toString();
						}
						if (!"".equals(content)) {
							dval = CodeUtils.decodeSpechars(content);
						}
					} else {
						dval = rs.getString(i + 1);
					}
					dataLi[i][j] = dval;
				}
				j++;
			}
			for (int i = 0; i < excelKeyArray.length; i++) {
				String[] value = dataLi[i];
				objOut.put(excelKeyArray[i], value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException(e + ":" + sql);
		} finally {
			try {
				DBUtils.CloseConn(conn, stm, rs);
			} catch (SQLException e) {
				throw new SQLException(e);
			}
		}
	}

	public void setDbkey(String dbkey) {
		try {
			this.dbkey = URLDecoder.decode(dbkey, "UTF-8");
		} catch (Exception e) {
			this.dbkey = dbkey;
		}
	}

	public String getDbkey() {
		return dbkey;
	}

	public void setLabels(String labels) {
		try {
			this.labels = URLDecoder.decode(labels, "UTF-8");
		} catch (Exception e) {
			this.labels = labels;
		}
	}

	public String getLabels() {
		return labels;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExpStatus(String expStatus) {
		this.expStatus = expStatus;
	}

	public String getExpStatus() {
		return expStatus;
	}

	public void setOrderby(String orderby) {
		try {
			this.orderby = URLDecoder.decode(orderby, "UTF-8");
		} catch (Exception e) {
			this.orderby = orderby;
		}
	}

	public String getOrderby() {
		return orderby;
	}

}
