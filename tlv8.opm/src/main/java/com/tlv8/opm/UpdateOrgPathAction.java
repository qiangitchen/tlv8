package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.utils.StringUtils;
import com.tlv8.system.pojo.SaOporg;
import com.tlv8.system.service.SaOporgService;

/**
 * 更新机构路径信息
 * 
 * @author chenqian
 * @update 2021/4/21
 */
@Controller
@Scope("prototype")
public class UpdateOrgPathAction extends ActionSupport {
	private String rowid;
	private String sparent;
	private String scode;
	private String sname;

	@Autowired
	SaOporgService saOpOrgService;

	@ResponseBody
	@PostMapping(value = "/updateOrgPathAction", produces = "application/json;charset=UTF-8")
	public Object execute() throws Exception {
		try {
			String fid = "", fcode = "", fname = "";
			if (StringUtils.isNotEmpty(sparent)) {
				SaOporg parentOrg = saOpOrgService.selectByPrimaryKey(sparent);
				if (parentOrg != null) {
					fid = parentOrg.getSfid();
					fcode = parentOrg.getSfcode();
					fname = parentOrg.getSname();
				}
			}
			SaOporg saOrg = saOpOrgService.selectByPrimaryKey(rowid);
			if (saOrg != null) {
				saOrg.setSfid(fid + "/" + saOrg.getSid() + "." + saOrg.getSorgkindid());
				saOrg.setSfcode(fcode + "/" + saOrg.getScode());
				saOrg.setSfname(fname + "/" + saOrg.getSname());
				saOpOrgService.updateData(saOrg);
				updateChildOrgPath(saOrg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	private void updateChildOrgPath(SaOporg porg) {
		List<SaOporg> orgList = saOpOrgService.selectListByParentID(porg.getSid());
		for (SaOporg org : orgList) {
			org.setSfid(porg.getSfid() + "/" + org.getSid() + "." + org.getSorgkindid());
			org.setSfcode(porg.getSfcode() + "/" + org.getScode());
			org.setSfname(porg.getSfname() + "/" + org.getSname());
			saOpOrgService.updateData(org);
			updateChildOrgPath(org);
		}
	}

	public void setRowid(String rowid) {
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getRowid() {
		return rowid;
	}

	public void setSparent(String sparent) {
		try {
			this.sparent = URLDecoder.decode(sparent, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSparent() {
		return sparent;
	}

	public void setScode(String scode) {
		try {
			this.scode = URLDecoder.decode(scode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getScode() {
		return scode;
	}

	public void setSname(String sname) {
		try {
			this.sname = URLDecoder.decode(sname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getSname() {
		return sname;
	}

}
