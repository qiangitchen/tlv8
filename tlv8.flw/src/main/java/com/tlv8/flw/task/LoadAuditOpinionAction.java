package com.tlv8.flw.task;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;

/**
 * 加载审批意见
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
public class LoadAuditOpinionAction extends ActionSupport {
	private String fbillID;
	private String fopviewID;

	private Data data = new Data();

	@ResponseBody
	@RequestMapping("/LoadAuditOpinionAction")
	public Object execute() throws Exception {
		String sql = "select t.FAGREETEXT,FCREATETIME,FCREATEPERID,FCREATEPERNAME,FSIGN from OA_FLOWRECORD t where  FBILLID=? and t.FOPVIEWID = ? order by FCREATETIME asc";
		try {
			List<Object> params = new ArrayList<Object>();
			params.add(fbillID);
			params.add(fopviewID);
			List<Map<String, String>> res = DBUtils.selectStringList("oa", sql, params);
			for (Map<String, String> map : res) {
				map.put("SID", getSignID(map.get("FCREATEPERID")));
			}
			data.setData(JSON.toJSONString(res));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		}
		return this;
	}

	private String getSignID(String personid) {
		SqlSession session = DBUtils.getSession("system");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sid = "";
		try {
			conn = session.getConnection();
			ps = conn.prepareStatement("select SHSPIC from SA_HANDWR_SIGNATURE where sID=?");
			ps.setString(1, personid);
			rs = ps.executeQuery();
			if (rs.next()) {
				Blob blob = (Blob) rs.getBlob(1);
				long size = blob.length();
				if (size > 10) {
					sid = personid;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConn(session, conn, ps, rs);
		}
		return sid;
	}

	public void setFbillID(String fbillID) {
		this.fbillID = fbillID;
	}

	public String getFbillID() {
		return fbillID;
	}

	public void setFopviewID(String fopviewID) {
		this.fopviewID = fopviewID;
	}

	public String getFopviewID() {
		return fopviewID;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

}
