package com.tlv8.flw.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.tlv8.base.ActionSupport;

/**
 * @author ChenQian
 */
public class FlowFolderTreeBean extends ActionSupport {
	private String id;// SID
	private String name;// SNAME
	private String pid;// SPARENT
	private String scode;// SCODE
	private String sidpath;// SIDPATH
	private String scodepath;// SCODEPATH
	private String snamepath;// SNAMEPATH
	private String sprocessid;// SPROCESSID
	private String sprocessname;// SPROCESSNAME
	private String sdrawlg;//SDRAWLG

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setName(String name) {
		try {
			this.name = URLDecoder.decode(name, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPid() {
		return pid;
	}

	public void setScode(String scode) {
		this.scode = scode;
	}

	public String getScode() {
		return scode;
	}

	public void setSidpath(String sidpath) {
		try {
			this.sidpath = URLDecoder.decode(sidpath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSidpath() {
		return sidpath;
	}

	public void setScodepath(String scodepath) {
		try {
			this.scodepath = URLDecoder.decode(scodepath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getScodepath() {
		return scodepath;
	}

	public void setSnamepath(String snamepath) {
		try {
			this.snamepath = URLDecoder.decode(snamepath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSnamepath() {
		return snamepath;
	}

	public void setSprocessid(String sprocessid) {
		this.sprocessid = sprocessid;
	}

	public String getSprocessid() {
		return sprocessid;
	}

	public void setSprocessname(String sprocessname) {
		try {
			this.sprocessname = URLDecoder.decode(sprocessname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSprocessname() {
		return sprocessname;

	}

	public void setSdrawlg(String sdrawlg) {
		try {
			this.sdrawlg = URLDecoder.decode(sdrawlg, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSdrawlg() {
		return sdrawlg;
	}

}
