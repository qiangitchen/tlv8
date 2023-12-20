package com.tlv8.oa.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

@Controller
@Scope("prototype")
public class insertNoticePerson extends ActionSupport {
	private String fids;
	private String names;
	private String sdata1;
	private Data data = new Data();

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getFids() {
		return fids;
	}

	public void setFids(String fids) {
		try {
			this.fids = URLDecoder.decode(fids, "UTF-8").trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		try {
			this.names = URLDecoder.decode(names, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSdata1() {
		return sdata1;
	}

	public void setSdata1(String sdata1) {
		this.sdata1 = sdata1;
	}

	@ResponseBody
	@RequestMapping("/insertNoticePersonAction")
	@Override
	public Object execute() throws Exception {
		ContextBean context = ContextBean.getContext(request);
		try {
			String sqlTxt = "";
			String[] idlist = fids.split(",");
			String[] namellist = names.split(",");
			for (int i = 0; i < idlist.length; i++) {
				String pid = idlist[i];
				String pname = namellist[i];
				// 如果没有 插入
				sqlTxt = "SELECT 1 FROM OA_NOTICE_PERSON WHERE FMAINID='" + sdata1 + "' and FPERSONID = '" + pid + "'";
				List<Map<String, String>> list = DBUtils.execQueryforList("oa", sqlTxt);
				if (list.size() < 1)
					sqlTxt = "insert into OA_NOTICE_PERSON"
							+ "(fid,version,FOGNID,FOGNNAME,FORGID,FORGNAME,FDEPTID,FDEPTNAME,FCREATEID,"
							+ "FCREATENAME,FCREATEDATETIME,FMAINID,FPERSONID,FPERSONNAME,FBROWSE)"
							+ "values(NEWID(),0,'" + context.getCurrentOgnID() + "','" + context.getCurrentOgnName()
							+ "','" + context.getCurrentOrgID() + "','" + context.getCurrentOrgName() + "','"
							+ context.getCurrentDeptID() + "','" + context.getCurrentDeptName() + "','"
							+ context.getCurrentPersonID() + "','" + context.getCurrentPersonName() + "',getdate(),'"
							+ sdata1 + "','" + pid + "','" + pname + "','否')";
				if (DBUtils.IsOracleDB("oa") || DBUtils.IsDMDB("oa")) {
					sqlTxt = "insert into OA_NOTICE_PERSON"
							+ "(fid,version,FOGNID,FOGNNAME,FORGID,FORGNAME,FDEPTID,FDEPTNAME,FCREATEID,"
							+ "FCREATENAME,FCREATEDATETIME,FMAINID,FPERSONID,FPERSONNAME,FBROWSE)"
							+ "values(sys_guid(),0,'" + context.getCurrentOgnID() + "','" + context.getCurrentOgnName()
							+ "','" + context.getCurrentOrgID() + "','" + context.getCurrentOrgName() + "','"
							+ context.getCurrentDeptID() + "','" + context.getCurrentDeptName() + "','"
							+ context.getCurrentPersonID() + "','" + context.getCurrentPersonName() + "',sysdate,'"
							+ sdata1 + "','" + pid + "','" + pname + "','否')";
				} else if (DBUtils.IsMySQLDB("oa")) {
					sqlTxt = "insert into OA_NOTICE_PERSON"
							+ "(fid,version,FOGNID,FOGNNAME,FORGID,FORGNAME,FDEPTID,FDEPTNAME,FCREATEID,"
							+ "FCREATENAME,FCREATEDATETIME,FMAINID,FPERSONID,FPERSONNAME,FBROWSE)" + "values('"
							+ UUID.randomUUID().toString().toUpperCase().replaceAll("-", "") + "',0,'"
							+ context.getCurrentOgnID() + "','" + context.getCurrentOgnName() + "','"
							+ context.getCurrentOrgID() + "','" + context.getCurrentOrgName() + "','"
							+ context.getCurrentDeptID() + "','" + context.getCurrentDeptName() + "','"
							+ context.getCurrentPersonID() + "','" + context.getCurrentPersonName() + "',now(),'"
							+ sdata1 + "','" + pid + "','" + pname + "','否')";
				}
				DBUtils.execUpdateQuery("oa", sqlTxt);
			}
			data.setFlag("true");
		} catch (Exception e) {
			e.printStackTrace();
			data.setFlag("false");
		}
		return AjaxResult.success(data);
	}
}
