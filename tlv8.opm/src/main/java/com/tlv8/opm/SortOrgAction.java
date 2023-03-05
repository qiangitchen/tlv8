package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.Statement;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;

/**
 * @d 组织排序
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class SortOrgAction extends ActionSupport {
	private String idlist;
	private String orgkind;

	@ResponseBody
	@RequestMapping(value = "/sortOrgAction", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public Object execute() throws Exception {
		String[] array = idlist.split(",");
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		Statement stm = null;
		try {
			conn = session.getConnection();
			stm = conn.createStatement();
			for (int i = 0; i < array.length; i++) {
				if (i < 9) {
					String sql = "update " + orgkind + " set SSEQUENCE = '0" + (i + 1) + "' where SID = '" + array[i]
							+ "'";
					stm.executeUpdate(sql);
				} else {
					String sql = "update " + orgkind + " set SSEQUENCE = '" + (i + 1) + "' where SID = '" + array[i]
							+ "'";
					stm.executeUpdate(sql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, stm, null);
		}
		return this;
	}

	public void setIdlist(String idlist) {
		try {
			this.idlist = URLDecoder.decode(idlist, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getIdlist() {
		return idlist;
	}

	public void setOrgkind(String orgkind) {
		try {
			this.orgkind = URLDecoder.decode(orgkind, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getOrgkind() {
		return orgkind;
	}

}
