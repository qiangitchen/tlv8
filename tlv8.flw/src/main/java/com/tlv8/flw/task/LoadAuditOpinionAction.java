package com.tlv8.flw.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
			data.setData(JSON.toJSONString(res));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		}
		return this;
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
