package com.tlv8.layim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;

@Controller
@RequestMapping("/layim")
public class GetMembers {

	@ResponseBody
	@RequestMapping("/getMembers")
	public Object execute(HttpServletRequest request, String id) throws Exception {
		String cpath = request.getContextPath();
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("code", 0);
		json.put("msg", "");
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("list", getPersonlist(id, cpath));
		json.put("data", data);
		return json;
	}

	public static List<Map<String, String>> getPersonlist(String gpid, String cpath) {
		List<Map<String, String>> json = new ArrayList<Map<String, String>>();
		try {
			List<Map<String, String>> psms = DBUtils.execQueryforList("oa",
					"select FPERSONID,FPERSONNAME from OA_ADM_MYGROUPFROM where FOUTKEY = '" + gpid + "'");
			for (int i = 0; i < psms.size(); i++) {
				Map<String, String> pmap = psms.get(i);
				Map<String, String> person = new HashMap<String, String>();
				person.put("username", pmap.get("FPERSONNAME"));
				person.put("id", pmap.get("FPERSONID"));
				person.put("avatar", cpath + "/comon/picCompant/personhead.jsp?id=" + pmap.get("FPERSONID"));
				person.put("sign", "");
				json.add(person);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
}
