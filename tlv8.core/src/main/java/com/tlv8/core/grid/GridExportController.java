package com.tlv8.core.grid;

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

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.CodeUtils;
import com.tlv8.base.db.DBUtils;
import com.tlv8.core.report.ExportExcel;

/**
 * grid数据导出操作
 * 
 * @author chenqian
 *
 */
@Controller
@RequestMapping("/core")
@Scope("prototype")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GridExportController extends ActionSupport {

	@RequestMapping(value = "exportGridData", method = RequestMethod.POST)
	public Object export(String dbkey, String table, String relation, String labels, String where, String orderby,
			@RequestHeader("User-Agent") String userAgent) {
		relation = deCode(relation);
		labels = deCode(labels);
		where = deCode(where);
		orderby = deCode(orderby);
		Map objOut = new HashMap();
		String expStatus = "";
		String inputpage = "/common/gridReport/export-input";
		if (relation == null || "".equals(relation.trim())) {
			expStatus = "字段不能为空！";
			request.setAttribute("expStatus", expStatus);
			return inputpage;
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
			return inputpage;
		}
		objOut.put("excelName", labels);
		String[] excelKeyArray = relation.split(",");
		objOut.put("excelKey", excelKeyArray);
		try {
			SQL sql = new SQL();
			sql.SELECT(relation);
			sql.FROM(table);
			if (where != null && !"undefined".equals(where.trim()) && !"".equals(where.trim())) {
				sql.WHERE(where);
			}
			if (orderby != null && !"undefined".equals(orderby.trim()) && !"".equals(orderby.trim())) {
				sql.ORDER_BY(orderby);
			}
			getData(objOut, excelKeyArray, dbkey, sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			expStatus = e.getMessage();
			request.setAttribute("expStatus", expStatus);
			return inputpage;
		}
		InputStream excelStream;
		try {
			excelStream = getExcelInputStream(objOut);
		} catch (Exception e) {
			e.printStackTrace();
			expStatus = e.getMessage();
			request.setAttribute("expStatus", expStatus);
			return inputpage;
		}
		response.setContentType("application/vnd.ms-excel");
		return getByteResponseEntity(excelStream, MediaType.APPLICATION_OCTET_STREAM, userAgent, "导出的Excel文件.xls");
	}

	protected String deCode(String ss) {
		try {
			ss = URLDecoder.decode(ss, "UTF-8");
		} catch (Exception e) {
		}
		return ss;
	}

	public InputStream getExcelInputStream(Map objOut) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ExportExcel.exportExcel(out, objOut);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return new ByteArrayInputStream(out.toByteArray());
	}

	private void getData(Map objOut, String[] excelKeyArray, String dbkey, String sql) throws Exception {
		SqlSession session = DBUtils.getSession(dbkey);
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = session.getConnection();
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
					if ("DATETIME".equals(type.toUpperCase()) || "TIMESTAMP".equals(type.toUpperCase())) {
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
			DBUtils.closeConn(session, conn, stm, rs);
		}
	}
}
