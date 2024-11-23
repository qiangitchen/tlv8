package com.tlv8.opm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.ActionSupport;
import com.tlv8.base.Data;
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.system.pojo.SaOporg;
import com.tlv8.system.service.SaOporgService;
import com.tlv8.system.service.SaOppersonService;

/**
 * @d 组织机构及人员移动
 * @author 陈乾
 */
@Controller
@Scope("prototype")
public class MoveOrgAction extends ActionSupport {
	@Autowired
	SaOporgService saOpOrgService;
	@Autowired
	SaOppersonService saOpPersonService;

	private Data data;
	private String rowid;
	private String orgID;

	public Data getdata() {
		return this.data;
	}

	public void setdata(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping(value = "/moveOrgAction", produces = "application/json;charset=UTF-8")
	public Object execute() throws Exception {
		data = new Data();
		String r = "true";
		String m = "success";
		String f = "";
		try {
			String orgid = rowid;
			String toid = orgID;
			SaOporg porg = saOpOrgService.selectByPrimaryKey(toid);
			if (porg != null) {
				if (!"psm".equalsIgnoreCase(porg.getSorgkindid())) {
					SaOporg org = saOpOrgService.selectByPrimaryKey(orgid);
					if (org != null) {
						org.setSfid(porg.getSfid() + "/" + org.getSid() + "." + org.getSorgkindid());
						org.setSfcode(porg.getSfcode() + "/" + org.getScode());
						org.setSfname(porg.getSfname() + "/" + org.getSname());
						org.setSlevel(porg.getSlevel() + 1);
						org.setSparent(porg.getSid());
						saOpOrgService.updateData(org);
						updateChildOrgPath(org);
						f = "true";
						m = "移动成功";
					} else {
						f = "false";
						m = "指定组织无效";
					}
				} else {
					SaOporg org = saOpOrgService.selectByPrimaryKey(orgid);
					if (org != null) {
						org.setSfid("/" + org.getSid() + "." + org.getSorgkindid());
						org.setSfcode("/" + org.getScode());
						org.setSfname("/" + org.getSname());
						org.setSlevel(1);
						org.setSparent(null);
						saOpOrgService.updateData(org);
						updateChildOrgPath(org);
						f = "true";
						m = "移动成功";
					} else {
						f = "false";
						m = "指定组织无效";
					}
				}
			} else {
				f = "false";
				m = "指定移动位置无效";
			}
		} catch (Exception e) {
			m = "操作失败：" + e.getMessage();
			f = "false";
			e.printStackTrace();
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return AjaxResult.success(data);
	}

	private void updateChildOrgPath(SaOporg porg) {
		List<SaOporg> orgList = saOpOrgService.selectListByParentID(porg.getSid());
		for (SaOporg org : orgList) {
			org.setSfid(porg.getSfid() + "/" + org.getSid() + "." + org.getSorgkindid());
			org.setSfcode(porg.getSfcode() + "/" + org.getScode());
			org.setSfname(porg.getSfname() + "/" + org.getSname());
			org.setSlevel(porg.getSlevel() + 1);
			saOpOrgService.updateData(org);
			updateChildOrgPath(org);
		}
	}

	public void setRowid(String rowid) {
		try {
			this.rowid = URLDecoder.decode(rowid, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.rowid = rowid;
		}
	}

	public String getRowid() {
		return rowid;
	}

	public void setOrgID(String orgID) {
		try {
			this.orgID = URLDecoder.decode(orgID, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			this.orgID = orgID;
		}
	}

	public String getOrgID() {
		return orgID;
	}
}
