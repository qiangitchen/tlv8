package com.tlv8.oa.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

/**
 * 文档归档
 */
@Controller
@Scope("prototype")
public class DocpigeonholeAction extends ActionSupport {
	private String folder;
	private String title;
	private String table;
	private String billid;
	private String surl;

	@ResponseBody
	@RequestMapping("/docpigeonholeAction")
	public Object execute() throws Exception {
		String[] names = folder.split("/");
		String folderid = "";
		for (int i = 0; i < names.length; i++) {
			String parentfname = getparentPath(names, i);
			String fname = parentfname + "/" + names[i];
			String parant = getFolderID(parentfname);
			if (!haveFolder(fname)) {
				folderid = addFolder(names[i], parant);
			} else {
				folderid = getFolderID(fname);
			}
		}
		addpige(folderid);
		return this;
	}

	public void addpige(String folderid) {
		ContextBean context = ContextBean.getContext(request);
		String sql = "insert into OA_PUB_DOCPIGEONHOLE(FID,FNAME,FPARENTID,FCREATEDEPT,FCREATEDEPTID,FCREATOR,FCREATORID,FFID,FFNAME,FTABLENAME,FBILLID,FURL)"
				+ " select sys_guid(),'" + title + "','" + folderid + "','" + context.getCurrentDeptName() + "','"
				+ context.getCurrentDeptID() + "','" + context.getCurrentPersonName() + "','"
				+ context.getCurrentPersonID() + "',t.FFID||'/" + folderid + "',t.FFNAME||'/" + title + "','" + table
				+ "','" + billid + "','" + surl + "' from OA_PUB_DOCPIGEONHOLE t where t.FID = '" + folderid + "'";
		if (folderid == null || "".equals(folderid)) {
			sql = "insert into OA_PUB_DOCPIGEONHOLE(FID,FNAME,FPARENTID,FCREATEDEPT,FCREATEDEPTID,FCREATOR,FCREATORID,FFID,FFNAME,FTABLENAME,FBILLID,FURL)"
					+ " select '" + folderid + "','" + title + "','" + folderid + "','" + context.getCurrentDeptName()
					+ "','" + context.getCurrentDeptID() + "','" + context.getCurrentPersonName() + "','"
					+ context.getCurrentPersonID() + "','/" + folderid + "','/" + title + "','" + table + "','" + billid
					+ "','" + surl + "' from dual";
		}
		try {
			DBUtils.execInsertQuery("oa", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getparentPath(String[] array, int d) {
		String path = "";
		for (int i = 0; i < array.length; i++) {
			if (i < d) {
				path += "/" + array[i];
			}
		}
		return path;
	}

	public boolean haveFolder(String fname) {
		String sql = "select FID from OA_PUB_DOCPIGEONHOLE where FFNAME = '" + fname + "'";
		try {
			List<Map<String, Object>> li = DBUtils.selectForList("oa", sql);
			return (li.size() > 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getFolderID(String fname) {
		String result = "";
		String sql = "select FID from OA_PUB_DOCPIGEONHOLE where FFNAME = '" + fname + "'";
		try {
			List<Map<String, String>> li = DBUtils.execQueryforList("oa", sql);
			if (li.size() > 0) {
				result = li.get(0).get("FID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String addFolder(String name, String parentid) {
		String folderid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		ContextBean context = ContextBean.getContext(request);
		String sql = "insert into OA_PUB_DOCPIGEONHOLE(FID,FNAME,FPARENTID,FCREATEDEPT,FCREATEDEPTID,FCREATOR,FCREATORID,FFID,FFNAME)"
				+ " select '" + folderid + "','" + name + "','" + parentid + "','" + context.getCurrentDeptName()
				+ "','" + context.getCurrentDeptID() + "','" + context.getCurrentPersonName() + "','"
				+ context.getCurrentPersonID() + "',t.FFID||'/" + folderid + "',t.FFNAME||'/" + name
				+ "' from OA_PUB_DOCPIGEONHOLE t where t.FID = '" + parentid + "'";
		if (parentid == null || "".equals(parentid)) {
			sql = "insert into OA_PUB_DOCPIGEONHOLE(FID,FNAME,FPARENTID,FCREATEDEPT,FCREATEDEPTID,FCREATOR,FCREATORID,FFID,FFNAME)"
					+ " select '" + folderid + "','" + name + "','" + parentid + "','" + context.getCurrentDeptName()
					+ "','" + context.getCurrentDeptID() + "','" + context.getCurrentPersonName() + "','"
					+ context.getCurrentPersonID() + "','/" + folderid + "','/" + name + "' from dual";
		}
		try {
			DBUtils.execInsertQuery("oa", sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return folderid;
	}

	public void setFolder(String folder) {
		try {
			this.folder = URLDecoder.decode(folder, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getFolder() {
		return folder;
	}

	public void setTitle(String title) {
		try {
			this.title = URLDecoder.decode(title, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTable(String table) {
		try {
			this.table = URLDecoder.decode(table, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getTable() {
		return table;
	}

	public void setBillid(String billid) {
		try {
			this.billid = URLDecoder.decode(billid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getBillid() {
		return billid;
	}

	public void setSurl(String surl) {
		try {
			this.surl = URLDecoder.decode(surl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSurl() {
		return surl;
	}

}
