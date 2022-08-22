package com.tlv8.core.action;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;

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
import com.tlv8.base.utils.IPUtils;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.utils.LogUtils;

/**
 * @author ChenQian 用于公共的sql跟新 @参数{dbkey：数据库连接标识(String)；
 *         sql：需要执行的sql语句(String)}
 */
@Controller
@Scope("prototype")
public class SqlUpdateAction extends ActionSupport {
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
	@RequestMapping(value = "/sqlUpdateAction", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
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
			r = exeUpdateAction();
			String sIP = IPUtils.getRemoteAddr(request);
			LogUtils.WriteActionLogs("sqlUpdateAction", "公共Action", "数据操作", sIP, "执行SQL：" + querys, "WEB", null);
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

	@SuppressWarnings("deprecation")
	private String exeUpdateAction() throws SQLException, NamingException {
		String result = "";
		Connection conn = null;
		Statement stm = null;
		String db = this.dbkey;
		String sql = this.querys;
		try {
			conn = DBUtils.getAppConn(db);
			stm = conn.createStatement();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new SQLException(e + "dblink:" + db + " :" + sql);
		} finally {
			try {
				DBUtils.CloseConn(conn, stm, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = "执行:" + sql + ";" + "数据库连接:" + db;
		return result;
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
