package com.tlv8.code;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

/**
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class AutoSetCodeAction extends ActionSupport {
	private String Key;
	private String value;

	@ResponseBody
	@RequestMapping("/autoSetCodeAction")
	@SuppressWarnings("deprecation")
	public Object execute() throws Exception {
		// System.out.println(Key);
		Connection connection = DBUtils.getAppConn("system");
		String sql = "select V+1 as V from SA_KVSEQUENCE where K='" + Key + "'";
		// 构建树的json
		try {
			ResultSet reslut = DBUtils.execQuery(connection, sql);
			if (reslut.next()) {
				value = reslut.getString("V");
				String sql_update = "update SA_KVSEQUENCE set V=V+1 where K ='"
						+ Key + "'";
				DBUtils.execUpdateQuery("system", sql_update);
			} else {
				String sql_insert = "insert into SA_KVSEQUENCE(K,V) values('"
						+ Key + "',1)";
				DBUtils.execInsertQuery("system", sql_insert);
				value = "1";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.closeConn(connection, null, null);
		}
		return this;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getKey() {
		return Key;
	}
}
