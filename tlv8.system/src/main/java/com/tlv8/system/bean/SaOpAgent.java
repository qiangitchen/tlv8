package com.tlv8.system.bean;

import java.util.Date;

public class SaOpAgent {
	private String sid;
	private String sorgfid;
	private String sorgfname;
	private String sagentid;
	private int svalidstate;
	private Date sstarttime;
	private Date sfinishtime;
	private String sprocess;
	private String screatorid;
	private String screatorname;
	private Date screatetime;
	private int scantranagent;
	private int version;
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSagentid() {
		return sagentid;
	}
	public void setSagentid(String sagentid) {
		this.sagentid = sagentid;
	}
	public Date getSstarttime() {
		return sstarttime;
	}
	public void setSstarttime(Date sstarttime) {
		this.sstarttime = sstarttime;
	}
	public Date getSfinishtime() {
		return sfinishtime;
	}
	public void setSfinishtime(Date sfinishtime) {
		this.sfinishtime = sfinishtime;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getSorgfid() {
		return sorgfid;
	}
	public void setSorgfid(String sorgfid) {
		this.sorgfid = sorgfid;
	}
	public String getSorgfname() {
		return sorgfname;
	}
	public void setSorgfname(String sorgfname) {
		this.sorgfname = sorgfname;
	}
	public int getSvalidstate() {
		return svalidstate;
	}
	public void setSvalidstate(int svalidstate) {
		this.svalidstate = svalidstate;
	}
	public String getSprocess() {
		return sprocess;
	}
	public void setSprocess(String sprocess) {
		this.sprocess = sprocess;
	}
	public String getScreatorid() {
		return screatorid;
	}
	public void setScreatorid(String screatorid) {
		this.screatorid = screatorid;
	}
	public String getScreatorname() {
		return screatorname;
	}
	public void setScreatorname(String screatorname) {
		this.screatorname = screatorname;
	}
	public Date getScreatetime() {
		return screatetime;
	}
	public void setScreatetime(Date screatetime) {
		this.screatetime = screatetime;
	}
	public int getScantranagent() {
		return scantranagent;
	}
	public void setScantranagent(int scantranagent) {
		this.scantranagent = scantranagent;
	}
	
}
