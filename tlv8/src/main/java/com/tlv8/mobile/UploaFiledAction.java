package com.tlv8.mobile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tlv8.base.Data;
import com.tlv8.base.ActionSupport;

/**
 * @author ChenQian
 * @category 文件上传
 */
@Controller
@Scope("prototype")
public class UploaFiledAction extends ActionSupport {
	private File upload;
	private String filname;
	private String billID;
	private String fileid;
	private String PersonID;

	private Data data = new Data();

	public Data getdata() {
		return this.data;
	}

	public void setdata(Data data) {
		this.data = data;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public File getUpload() {
		return upload;
	}

	public void setBillID(String billID) {
		try {
			this.billID = URLDecoder.decode(billID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getBillID() {
		return billID;
	}

	public void setFilname(String filname) {
		try {
			this.filname = URLDecoder.decode(filname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getFilname() {
		return filname;
	}

	public void setFileid(String fileid) {
		try {
			this.fileid = URLDecoder.decode(fileid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getFileid() {
		return fileid;
	}

	public String getPersonID() {
		return PersonID;
	}

	public void setPersonID(String personID) {
		PersonID = personID;
	}

	@ResponseBody
	@RequestMapping(value = "/uploaFileAction", method = RequestMethod.POST)
	public String execute(@RequestParam("upload") MultipartFile file) throws Exception {
		try {
			saveData();
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
		}
		return SUCCESS;
	}

	private void saveData() {
//		try {
//			String docid = ComUtils.searchMainDocid(billID, "2");
//			String oldDocID = docid;
//			OrgUtils context = new OrgUtils(PersonID);
//			String fcreator = context.getPersonID();
//			String forgcode = context.getOgnID();
//			String creatorName = context.getPersonName();
//			String orgName = context.getOgnName();
//			String fileTile = filname.substring(0, filname.indexOf("."));
//			ComUtils.repsUpLoadAttach("2", filname, forgcode, fcreator, creatorName, billID, orgName, upload, fileTile);
//			if (oldDocID != null && oldDocID.trim().length() > 0) {
//				// 删除文档
//				ComUtils.delDoc(oldDocID, false);
//				// 删除附件
//				ComUtils.delAttach(oldDocID);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (fileid != null && !"".equals(fileid)) {
//				ComUtils.deletDoc(fileid);
//			}
//		}
	}
}
