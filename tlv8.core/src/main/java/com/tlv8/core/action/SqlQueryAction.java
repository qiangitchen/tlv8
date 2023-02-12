package com.tlv8.core.action;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.base.Sys;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.AesEncryptUtil;
import com.tlv8.system.bean.ContextBean;

/**
 * @author ChenQian 用于公共的sql查询
 * @参数 {dbkey：数据库连接标识(String)；sql：需要执行的sql语句(String)}
 */
@Controller
@Scope("prototype")
public class SqlQueryAction extends ActionSupport {
	private Data data;
	private String dbkey;
	private String querys;

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping(value = "/sqlQueryAction", produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
	public Object execute() throws Exception {
		data = new Data();
		String userid = ContextBean.getContext(request).getCurrentPersonID();
		if (userid == null || "".equals(userid)) {
			data.setFlag("timeout");
			Sys.packErrMsg("未登录或登录已超时，不允许操作!");
			return this;
		}
		String r = "true";
		String m = "success";
		String f = "";
		try {
			r = exeQueryAction();
			f = "true";
		} catch (Exception e) {
			m = "操作失败：" + e.getMessage();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return this;
	}

	private String exeQueryAction() throws SQLException, NamingException {
		StringBuilder result = new StringBuilder();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd;
		String db = this.dbkey;
		String sql = this.querys;
		if (DBUtils.IsOracleDB(db)) {
			if (sql.indexOf("limit ") > 0) {
				String le = sql.substring(sql.indexOf("limit ") + 5).trim();
				sql = sql.substring(0, sql.indexOf("limit "));
				if (sql.indexOf(" where ") > 0) {
					try {
						sql = sql.replace(" where ", " where rownum <= " + le.split(",")[1] + " and ");
					} catch (Exception e) {

					}
				}
			}
		} else if (!DBUtils.IsMySQLDB(db)) {
			if (sql.indexOf("limit ") > 0) {
				String le = sql.substring(sql.indexOf("limit ") + 5).trim();
				sql = sql.substring(0, sql.indexOf("limit "));
				if (sql.indexOf(" where ") > 0) {
					try {
						sql = sql.replace("select", "select top " + le.split(",")[1] + " ");
					} catch (Exception e) {

					}
				}
			}
			// SQLServer将concat转为+号连接
			try {
				Pattern pat = Pattern.compile("concat\\((.*?)\\)");
				Matcher mat = pat.matcher(sql);
				while (mat.find()) {
					sql = sql.replace(mat.group(0), mat.group(1).replace(",", "+"));
				}
			} catch (Exception e) {
			}
		}
		Integer ii = 0;
		SqlSession session = DBUtils.getSession(db);
		try {
			conn = session.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			rsmd = rs.getMetaData();
			int size = rsmd.getColumnCount();
			result.append("<root><columns>");
			for (int i = 1; i <= size; i++) {
				result.append(rsmd.getColumnLabel(i));
				if (i < size)
					result.append(",");
			}
			result.append("</columns>\n");
			if (rs.next()) {
				result.append("<rows>\n");
				result.append("<row>");
				for (int i = 1; i <= size; i++) {
					try {
						String value = (rs.getString(i) == null) ? "" : rs.getString(i);
						value = value.replaceAll("<", "&lt;");
						value = value.replaceAll(">", "&gt;");
						result.append(value);
					} catch (Exception e) {
						result.append("");
					}
					if (i < size)
						result.append(";");
				}
				ii++;
				result.append("</row> \n");
				while (rs.next()) {
					result.append("<row>");
					for (int i = 1; i <= size; i++) {
						try {
							String value = (rs.getString(i) == null) ? "" : rs.getString(i);
							value = value.replaceAll("<", "&lt;");
							value = value.replaceAll(">", "&gt;");
							result.append(value);
						} catch (Exception e) {
							result.append("");
						}
						if (i < size)
							result.append(";");
					}
					ii++;
					result.append("</row> \n");
				}
				result.append("</rows>\n");
			}
			result.append("<count>" + ii + "</count>\n</root>");
		} catch (SQLException e) {
			throw new SQLException(e + "dblink:" + db + " :" + sql);
		} finally {
			DBUtils.closeConn(session, conn, stm, rs);
		}
		return result.toString();
	}

	public void setDbkey(String dbkey) {
		try {
			this.dbkey = URLDecoder.decode(dbkey, "UTF-8");
		} catch (Exception e) {
		}
		this.dbkey = AesEncryptUtil.desEncrypt(this.dbkey);
	}

//	public String getDbkey() {
//		return dbkey;
//	}
//
//	public String getQuerys() {
//		return querys;
//	}

	public void setQuerys(String querys) {
		try {
			this.querys = URLDecoder.decode(querys, "UTF-8");
		} catch (Exception e) {
		}
		this.querys = AesEncryptUtil.desEncrypt(this.querys);
	}

}
