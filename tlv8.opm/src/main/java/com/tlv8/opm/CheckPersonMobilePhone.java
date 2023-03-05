package com.tlv8.opm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.db.DBUtils;

@Controller
@Scope("prototype")
public class CheckPersonMobilePhone extends ActionSupport{
	private int count;
	private String value;
	private String id;

	@ResponseBody
	@RequestMapping("/checkPersonMobilePhone")
	public Object execute() throws Exception {
		String sql = "SELECT p.SCODE FROM SA_OPPerson p WHERE p.SMOBILEPHONE = ? and p.SID != ?";
		List<Object> params = new ArrayList<Object>();
		params.add(value);
		params.add(id);
		List<Map<String, String>> li = DBUtils.selectStringList("system", sql, params);
		count = li.size();
		return this;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
