package com.tlv8.oa.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

@Controller
@Scope("prototype")
public class updateNoticeBrowseState extends ActionSupport {
	private String rowid;
	private String isnew;
	private Data data = new Data();

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/updateNoticeBrowseAction")
	@Override
	public String execute() throws Exception {
		ContextBean context = ContextBean.getContext(request);
		String fpersonid = context.getCurrentPersonID();
		if (rowid == null || "".equals(rowid))
			return SUCCESS;
		try {
			if (isnew.equals("true")) {
				String sql_query = "SELECT 1 FROM OA_NOTICE_PERSON T WHERE t.FMAINID='" + rowid
						+ "' and T.FPERSONID = '" + fpersonid + "'";
				List<Map<String, String>> li = DBUtils.execQueryforList("oa", sql_query);
				if (li.size() > 0) {
					String sql = "UPDATE OA_NOTICE_PERSON SET FBROWSE = '是',FREADDATE = GETDATE() WHERE FMAINID='"
							+ rowid + "' AND FPERSONID='" + fpersonid + "' AND FBROWSE != '是'";
					if (DBUtils.IsOracleDB("oa") || DBUtils.IsDMDB("oa")) {
						sql = "UPDATE OA_NOTICE_PERSON SET FBROWSE = '是',FREADDATE = sysdate WHERE FMAINID='" + rowid
								+ "' AND FPERSONID='" + fpersonid + "' AND FBROWSE != '是'";
					} else if (DBUtils.IsMySQLDB("oa")) {
						sql = "UPDATE OA_NOTICE_PERSON SET FBROWSE = '是',FREADDATE = now() WHERE FMAINID='" + rowid
								+ "' AND FPERSONID='" + fpersonid + "' AND FBROWSE != '是'";
					}
					DBUtils.execUpdateQuery("oa", sql);
				} else {
					String nowdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
					String sql_insert = "INSERT INTO OA_NOTICE_PERSON"
							+ "(FID,VERSION,FOGNID,FOGNNAME,FORGID,FORGNAME,FDEPTID,FDEPTNAME,FCREATEID,FCREATENAME,FCREATEDATETIME,FPERSONID,FPERSONNAME,FMAINID,FREADDATE,FBROWSE) values "
							+ "('" + IDUtils.getGUID() + "',0,'" + context.getCurrentOgnID() + "','"
							+ context.getCurrentOgnName() + "','" + context.getCurrentOrgID() + "','"
							+ context.getCurrentOrgName() + "','" + context.getCurrentDeptID() + "','"
							+ context.getCurrentDeptName() + "','" + context.getCurrentPersonID() + "','"
							+ context.getCurrentPersonName() + "','" + nowdt + "','" + context.getCurrentPersonID()
							+ "','" + context.getCurrentPersonName() + "','" + rowid + "','" + nowdt + "','是')";
					DBUtils.execInsertQuery("oa", sql_insert);
				}
			} else {
				String sql = "";
				if (DBUtils.IsOracleDB("oa") || DBUtils.IsDMDB("oa")) {
					sql = "UPDATE OA_NOTICE_PERSON T SET T.FBROWSE = '是',T.FREADDATE = SYSDATE WHERE T.FMAINID='"
							+ rowid + "' AND T.FPERSONID='" + fpersonid + "' AND T.FBROWSE != '是'";
				} else if (DBUtils.IsMySQLDB("oa")) {
					sql = "UPDATE OA_NOTICE_PERSON T SET T.FBROWSE = '是',T.FREADDATE = now() WHERE T.FMAINID='" + rowid
							+ "' AND T.FPERSONID='" + fpersonid + "' AND T.FBROWSE != '是'";
				}
				DBUtils.execUpdateQuery("oa", sql);
			}
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	public String getIsnew() {
		return isnew;
	}
}
