package com.tlv8.mobile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;


public class SendAppMessageAction extends ActionSupport {
	private String personid;
	private String msgtitle;
	private String msgcontent;

	private Data data = new Data();

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String execute() throws Exception {
		String[] persons = personid.trim().split(",");
		for (int i = 0; i < persons.length; i++) {
			String sql = "insert into APP_PUSHMESSAGE(SID,SPERSONID,STITLE,SMESSAGE,SSDATE,VERSION)select sys_guid(),'"
					+ persons[i]
					+ "','"
					+ msgtitle
					+ "','"
					+ msgcontent
					+ "',sysdate,0 from dual";
			DBUtils.execInsertQuery("system", sql);
		}
		return SUCCESS;
	}

	public void setPersonid(String personid) {
		try {
			this.personid = URLDecoder.decode(personid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getPersonid() {
		return personid;
	}

	public void setMsgtitle(String msgtitle) {
		try {
			this.msgtitle = URLDecoder.decode(msgtitle, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getMsgtitle() {
		return msgtitle;
	}

	public void setMsgcontent(String msgcontent) {
		try {
			this.msgcontent = URLDecoder.decode(msgcontent, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getMsgcontent() {
		return msgcontent;
	}

}
