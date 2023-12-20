package com.tlv8.opm;

import java.net.URLDecoder;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.StringArray;
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.base.ActionSupport;

/**
 * 还原已删除的数据
 * 
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class ResetOrgDataAction extends ActionSupport {
	private Data data;
	private String rowid;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping(value = "/resetOrgDataAction", produces = "application/json;charset=UTF-8")
	public Object execute() throws Exception {
		data = new Data();
		StringArray IDS = new StringArray();
		String[] rowids = rowid.split(",");
		for (int i = 0; i < rowids.length; i++) {
			IDS.push("'" + rowids[i] + "'");
		}
		String sqlo = "update SA_OPorg o set o.SVALIDSTATE = 1 where sID in (" + IDS.join(",") + ")";
		String sqlp = "update SA_OPPerson o set o.SVALIDSTATE = 1 where sID in (select SPERSONID from SA_OPorg where SID in ("
				+ IDS.join(",") + "))";
		try {
			DBUtils.execUpdateQuery("system", sqlo);
			DBUtils.execUpdateQuery("system", sqlp);
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return AjaxResult.success(data);
	}

	public void setRowid(String rowid) {
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRowid() {
		return rowid;
	}

}
