package com.tlv8.doc.clt.doc;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;

@Controller
@Scope("prototype")
public class QueryPermissionAction extends ActionSupport {
	private String deptPath;
	private String personId;
	private Data data = new Data();

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/queryPermissionAction")
	@SuppressWarnings("unchecked")
	@Override
	public Object execute() throws Exception {
		data = new Data();
		try {
			Map<String, Object> m = (Map<String, Object>) DocAdapter.queryPermission(deptPath, personId);
			data.setData(JSON.toJSONString(m));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
		}
		return data;

	}

	public void setDeptPath(String deptPath) {
		try {
			this.deptPath = URLDecoder.decode(deptPath, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDeptPath() {
		return deptPath;
	}

	public void setPersonId(String personId) {
		try {
			this.personId = URLDecoder.decode(personId, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getPersonId() {
		return personId;
	}

}
