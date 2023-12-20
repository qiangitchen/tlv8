package com.tlv8.oa.mail;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class DeleteMailAction extends ActionSupport {
	private Data data = new Data();
	private String rowid;
	private String type;

	@ResponseBody
	@RequestMapping("/DeleteMailAction")
	@Override
	public Object execute() throws Exception {
		String sql = "";
		if (type.equals("收件箱")) {
			sql = "DELETE FROM OA_EM_RECEIVEEMAIL WHERE FID IN (" + rowid + ")";
		} else {
			sql = "DELETE FROM OA_EM_SENDEMAIL WHERE FID IN (" + rowid + ")";
		}
		try {
			DBUtils.execdeleteQuery("oa", sql);
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
		}
		return AjaxResult.success(data);
	}

	public void setRowid(String rowid) {
		try {
			String id = URLDecoder.decode(rowid, "UTF-8");
			if (!id.startsWith("'")) {
				id = "'" + id + "'";
			}
			this.rowid = id;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getRowid() {
		return rowid;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	public void setType(String type) {
		try {
			this.type = URLDecoder.decode(type, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getType() {
		return type;
	}
}
