package com.tlv8.mobile;

import java.util.List;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

/**
 * @author zsx
 * @date 2014年3月11日11:11:23
 * @remark 用户移动版首页待处理数量获取
 */
public class getPortalCountAction extends ActionSupport {
	private Data data = new Data();
	private String type;
	private String task_count;
	private String mail_count;
	private String wp_count;// weekplan周计划
	private String mp_count;// monthplan月计划
	private String dr_count;// dayreport日报
	private String wr_count;// weekreport周报
	private String mr_count;// monthreport月报
	private String notice_count;// 通知

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getTask_count() {
		return task_count;
	}

	public void setTask_count(String task_count) {
		this.task_count = task_count;
	}

	public String getMail_count() {
		return mail_count;
	}

	public void setMail_count(String mail_count) {
		this.mail_count = mail_count;
	}

	public String getWp_count() {
		return wp_count;
	}

	public void setWp_count(String wp_count) {
		this.wp_count = wp_count;
	}

	public String getMp_count() {
		return mp_count;
	}

	public void setMp_count(String mp_count) {
		this.mp_count = mp_count;
	}

	public String getDr_count() {
		return dr_count;
	}

	public void setDr_count(String dr_count) {
		this.dr_count = dr_count;
	}

	public String getWr_count() {
		return wr_count;
	}

	public void setWr_count(String wr_count) {
		this.wr_count = wr_count;
	}

	public String getMr_count() {
		return mr_count;
	}

	public void setMr_count(String mr_count) {
		this.mr_count = mr_count;
	}

	@SuppressWarnings({ "rawtypes" })
	public String execute() throws Exception {
		ContextBean context = ContextBean.getContext(request);
		String personid = context.getPersonID();
		String personfid = context.getCurrentPersonFullID();
		String sql_task = "select 1 from SA_TASK T  where ('"
				+ personfid
				+ "' like SEFID||'%' and T.SEFID LIKE '/%') "
				+ " and (T.sKindID='tkTask' or T.sKindID='tkExecutor' or T.sKindID='tkNotice' or T.sKindID IS NULL)"
				+ " and (T.sStatusID='tesReady' or T.sStatusID='tesExecuting')"
				+ " and (T.sTypeID IS NULL or T.sTypeID <> 'WORKREMIND')"
				+ " and (T.SPARENTID is not null)";
		if(DBUtils.IsMSSQLDB("system")) {
			sql_task = "select 1 from SA_TASK T  where ('"
					+ personfid
					+ "' like SEFID+'%' and T.SEFID LIKE '/%') "
					+ " and (T.sKindID='tkTask' or T.sKindID='tkExecutor' or T.sKindID='tkNotice' or T.sKindID IS NULL)"
					+ " and (T.sStatusID='tesReady' or T.sStatusID='tesExecuting')"
					+ " and (T.sTypeID IS NULL or T.sTypeID != 'WORKREMIND')"
					+ " and (T.SPARENTID is not null)";
		}
		String sql_mail = "SELECT 1 FROM OA_EM_RECEIVEEMAIL WHERE FCONSIGNEEID = '"
				+ personid
				+ "' AND FQUREY = '未查看' ORDER BY FQUREY, FSENDTIME DESC";
		String sql_wp = "SELECT 1 FROM LOOK_WEEK_WORK_PLAN T WHERE T.FPERSONID= '"
				+ personid
				+ "' AND T.FBROWSE = '否' AND FPUSHDATETIME IS NOT NULL";
		// System.out.println(sql_wp);
		String sql_mp = "SELECT 1 FROM LOOK_MONTH_WORK_PLAN T WHERE T.FPERSONID= '"
				+ personid
				+ "' AND T.FBROWSE = '否' AND FPUSHDATETIME IS NOT NULL";
		String sql_dr = "SELECT 1 FROM SHOW_OA_RE_DAYREPORT T WHERE T.FPERSONID= '"
				+ personid
				+ "' AND T.FBROWSE = '否' AND FPUSHDATETIME IS NOT NULL";
		String sql_wr = "SELECT 1 FROM SHOW_OA_RE_WEEKREPORT T WHERE T.FPERSONID= '"
				+ personid
				+ "' AND T.FBROWSE = '否' AND FPUSHDATETIME IS NOT NULL";
		String sql_mr = "SELECT 1 FROM SHOW_OA_RE_MONTHREPORT T WHERE T.FPERSONID= '"
				+ personid
				+ "' AND T.FBROWSE = '否' AND FPUSHDATETIME IS NOT NULL";
		String sql_notice = "SELECT distinct(fid) FROM OA_NOTICE_PERSON_VIEW T WHERE FPUSHDATETIME is not null and (fid not in (select fid from OA_NOTICE_PERSON_VIEW where FTYPE = '集体发布' AND FPERSONID ='"
				+ personid
				+ "')  OR (FTYPE = '限制发布' AND FPERSONID='"
				+ personid + "' and FBROWSE != '是'))";
		// System.out.println(sql_notice);
		try {
			if (type == null || "".equals(type)) {
				List l_task = DBUtils.execQueryforList("system", sql_task);
				List l_mail = DBUtils.execQueryforList("oa", sql_mail);
				List l_wp = DBUtils.execQueryforList("oa", sql_wp);
				List l_mp = DBUtils.execQueryforList("oa", sql_mp);
				List l_dr = DBUtils.execQueryforList("oa", sql_dr);
				List l_wr = DBUtils.execQueryforList("oa", sql_wr);
				List l_mr = DBUtils.execQueryforList("oa", sql_mr);
				List l_notice = DBUtils.execQueryforList("oa", sql_notice);
				task_count = Integer.toString(l_task.size());
				mail_count = Integer.toString(l_mail.size());
				wp_count = Integer.toString(l_wp.size());
				mp_count = Integer.toString(l_mp.size());
				dr_count = Integer.toString(l_dr.size());
				wr_count = Integer.toString(l_wr.size());
				mr_count = Integer.toString(l_mr.size());
				notice_count = Integer.toString(l_notice.size());
			} else if ("weekPlan".equals(type)) {
				List l_wp = DBUtils.execQueryforList("oa", sql_wp);
				wp_count = Integer.toString(l_wp.size());
			} else if ("monthPlan".equals(type)) {
				List l_mp = DBUtils.execQueryforList("oa", sql_mp);
				mp_count = Integer.toString(l_mp.size());
			} else if ("dayReport".equals(type)) {
				List l_dr = DBUtils.execQueryforList("oa", sql_dr);
				dr_count = Integer.toString(l_dr.size());
			} else if ("weekReport".equals(type)) {
				List l_wr = DBUtils.execQueryforList("oa", sql_wr);
				wr_count = Integer.toString(l_wr.size());
			} else if ("monthReport".equals(type)) {
				List l_mr = DBUtils.execQueryforList("oa", sql_mr);
				mr_count = Integer.toString(l_mr.size());
			}
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public void setNotice_count(String notice_count) {
		this.notice_count = notice_count;
	}

	public String getNotice_count() {
		return notice_count;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
