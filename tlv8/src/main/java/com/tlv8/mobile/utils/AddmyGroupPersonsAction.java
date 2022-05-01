package com.tlv8.mobile.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

/**
 * 添加我的群组成员
 * 
 * @author 陈乾
 *
 */
@Controller
@Scope("prototype")
public class AddmyGroupPersonsAction extends ActionSupport {
	private String groupid;
	private String personids;
	private String personnames;

	@ResponseBody
	@RequestMapping("/addmyGroupPersonsAction")
	@Override
	public Object execute() throws Exception {
		try {
			ContextBean context = ContextBean.getContext(request);
			String[] psmids = personids.split(",");
			String[] psmnms = personnames.split(",");
			for (int i = 0; i < psmids.length; i++) {
				String chSQL = "select FID from OA_ADM_MYGROUPFROM where FOUTKEY = '" + groupid + "' and FPERSONID = '"
						+ psmids[i] + "'";
				List<Map<String, String>> list = DBUtils.execQueryforList("oa", chSQL);
				if (list.size() < 1) {
					String addSQL = "insert into OA_ADM_MYGROUPFROM(FID,FOUTKEY,FPERSONID,FPERSONNAME,FCREATORID,FCREATOR,FCREATEDEPTID,FCREATEDEPT,FCREATEDATE,VERSION)"
							+ "values('" + IDUtils.getGUID() + "','" + groupid + "','" + psmids[i] + "','" + psmnms[i]
							+ "','" + context.getCurrentPersonID() + "','" + context.getCurrentPersonName() + "','"
							+ context.getCurrentDeptID() + "','" + context.getCurrentDeptName() + "','"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "',0)";
					DBUtils.execInsertQuery("oa", addSQL);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		try {
			this.groupid = URLDecoder.decode(groupid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getPersonids() {
		return personids;
	}

	public void setPersonids(String personids) {
		try {
			this.personids = URLDecoder.decode(personids, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getPersonnames() {
		return personnames;
	}

	public void setPersonnames(String personnames) {
		try {
			this.personnames = URLDecoder.decode(personnames, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
