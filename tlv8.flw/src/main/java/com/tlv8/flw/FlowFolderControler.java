package com.tlv8.flw;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.tlv8.flw.base.FlowFile;
import com.tlv8.flw.base.FlowFolderTree;
import com.tlv8.flw.bean.FlowFolderTreeBean;
import com.tlv8.flw.helper.FlowFolderEditHelper;

/**
 * @author ChenQain
 */
@Controller
@Scope("prototype")
@SuppressWarnings({ "rawtypes" })
public class FlowFolderControler extends FlowFolderTreeBean {
	private Data data = new Data();

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	/*
	 * @获取目录树
	 */
	@ResponseBody
	@RequestMapping("/getflwFolderAction")
	public Object loadflwFolder() {
		data = new Data();
		String r = "true";
		String m = "success";
		String f = "";
		try {
			r = FlowFolderTree.getFolderJsonStr();
		} catch (Exception e) {
			m = "操作失败：" + e.toString();
			f = "false";
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return AjaxResult.success(data);
	}

	/*
	 * @编辑目录
	 */
	@ResponseBody
	@RequestMapping("/editflwFolderAction")
	public Object editflwFolder() {
		data = new Data();
		String r = "true";
		String m = "success";
		String f = "";
		try {
			String sql = FlowFolderEditHelper.createQuerySql(this);
			List li = DBUtils.execQueryforList("system", sql);
			if (li.size() > 0) {
				sql = FlowFolderEditHelper.createEditSql(this);
				r = DBUtils.execUpdateQuery("system", sql);
			} else {
				sql = FlowFolderEditHelper.createInsertSql(this);
				r = DBUtils.execInsertQuery("system", sql);
			}
		} catch (Exception e) {
			m = "操作失败：" + e.toString();
			f = "false";
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return AjaxResult.success(data);
	}

	/*
	 * @添加目录
	 */
	@ResponseBody
	@RequestMapping("/insertflwFolderAction")
	public Object insertflwFolder() {
		data = new Data();
		String r = "true";
		String m = "success";
		String f = "";
		try {
			String sql = FlowFolderEditHelper.createInsertSql(this);
			r = DBUtils.execInsertQuery("system", sql);
		} catch (Exception e) {
			m = "操作失败：" + e.toString();
			f = "false";
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return AjaxResult.success(data);
	}

	/*
	 * @删除目录
	 */
	@ResponseBody
	@RequestMapping("/deleteflwFolderAction")
	public Object deleteflwFolder() {
		data = new Data();
		String r = "true";
		String m = "success";
		String f = "";
		try {
			String sql = FlowFolderEditHelper.createDeleteSql(this);
			r = DBUtils.execdeleteQuery("system", sql);
			FlowFile.deleteFlowDraw(getSprocessid());
		} catch (Exception e) {
			m = "操作失败：" + e.toString();
			f = "false";
		}
		data.setData(r);
		data.setFlag(f);
		data.setMessage(m);
		return AjaxResult.success(data);
	}
	
}
