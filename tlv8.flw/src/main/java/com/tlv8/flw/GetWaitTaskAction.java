package com.tlv8.flw;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.tlv8.common.domain.AjaxResult;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

@Controller
@Scope("prototype")
public class GetWaitTaskAction extends ActionSupport {
	private Data data;
	private String count;
	private String offerset;
	private String limit;
	private String filter;

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
	}

	@ResponseBody
	@RequestMapping("/getWaitTaskAction")
	@SuppressWarnings({ "rawtypes" })
	public Object execute() throws Exception {
		data = new Data();
		ContextBean context = ContextBean.getContext(request);
		String personfid = context.getCurrentPersonFullID();
		String where = "";
		if (filter != null && !"".equals(filter)) {
			where = " and (" + filter + ") ";
		} else {
			where = "";
		}
		String sql = "select T.SID ID,T.SNAME NAME, "
				+ "substr(t.SNAME,instr(t.SNAME,':',-1,1)+1) TITLE, T.SCURL,T.SCPERSONNAME PSNNAME,"
				+ "T.SFLOWID, T.SACTIVITY, T.SCREATETIME TIME, T.SEFNAME, T.SEXECUTORNAMES, "
				+ "T.SEURL, T.SCFNAME, T.SSHORTCUT, T.SHINTS, T.SCFID, T.SEFID, T.SKINDID, T.SDATA1,T.SWARNINGTIME,T.SLIMITTIME  "
				+ "from SA_TASK T  where ('" + personfid + "' like SEFID||'%' and T.SEFID LIKE '/%') "
				+ " and (T.sKindID='tkTask' or T.sKindID='tkExecutor' or T.sKindID='tkNotice' or T.sKindID IS NULL)"
				+ " and (T.sStatusID='tesReady' or T.sStatusID='tesExecuting')"
				+ " and (T.sTypeID IS NULL or T.sTypeID <> 'WORKREMIND')" + " and (T.SACTIVITY is not null)" + where
				+ " order by  t.sCreateTime desc";
		if (DBUtils.IsMySQLDB("system")) {
			sql = "select T.SID ID,T.SNAME NAME, "
					+ "substring(t.SNAME,LOCATE(t.SNAME,':')+1) TITLE, T.SCURL,T.SCPERSONNAME PSNNAME,"
					+ "T.SFLOWID, T.SACTIVITY, T.SCREATETIME TIME, T.SEFNAME, T.SEXECUTORNAMES, "
					+ "T.SEURL, T.SCFNAME, T.SSHORTCUT, T.SHINTS, T.SCFID, T.SEFID, T.SKINDID, T.SDATA1,T.SWARNINGTIME,T.SLIMITTIME  "
					+ "from SA_TASK T  where ('" + personfid + "' like SEFID||'%' and T.SEFID LIKE '/%') "
					+ " and (T.sKindID='tkTask' or T.sKindID='tkExecutor' or T.sKindID='tkNotice' or T.sKindID IS NULL)"
					+ " and (T.sStatusID='tesReady' or T.sStatusID='tesExecuting')"
					+ " and (T.sTypeID IS NULL or T.sTypeID <> 'WORKREMIND')" + " and (T.SACTIVITY is not null)" + where
					+ " order by  t.sCreateTime desc";
		} else if (DBUtils.IsMSSQLDB("system")) {
			sql = "select T.SID ID,T.SNAME NAME, "
					+ "substring(t.SNAME,0,charindex(t.SNAME,':')+1) TITLE, T.SCURL,T.SCPERSONNAME PSNNAME,"
					+ "T.SFLOWID, T.SACTIVITY, T.SCREATETIME TIME, T.SEFNAME, T.SEXECUTORNAMES, "
					+ "T.SEURL, T.SCFNAME, T.SSHORTCUT, T.SHINTS, T.SCFID, T.SEFID, T.SKINDID, T.SDATA1,T.SWARNINGTIME,T.SLIMITTIME  "
					+ "from SA_TASK T  where ('" + personfid + "' like SEFID+'%' and T.SEFID LIKE '/%') "
					+ " and (T.sKindID='tkTask' or T.sKindID='tkExecutor' or T.sKindID='tkNotice' or T.sKindID IS NULL)"
					+ " and (T.sStatusID='tesReady' or T.sStatusID='tesExecuting')"
					+ " and (T.sTypeID IS NULL or T.sTypeID <> 'WORKREMIND')" + " and (T.SACTIVITY is not null)"
					+ where;
		}
		try {
			List cl = DBUtils.execQueryforList("system", "select count(*) as COUNT from (" + sql + ") a");
			if (cl.size() > 0) {
				Map m = (Map) cl.get(0);
				count = String.valueOf(m.get("COUNT"));
			}
			if (limit != null && !"".equals(limit)) {
				if (DBUtils.IsMySQLDB("system")) {
					sql = "select r.* from (" + sql + ")r limit " + offerset + "," + limit;
				} else if (DBUtils.IsMSSQLDB("system")) {
					sql = "select * from (" + sql + " order by  t.sCreateTime desc)a where ID in (select top " + limit
							+ " ID from (" + sql + " order by  t.sCreateTime desc)b) and ID not in (select top "
							+ offerset + " ID from (" + sql + ")c)";
				} else {
					sql = "select * from (select rownum srownu,r.* from (" + sql + ")r where rownum<=" + limit
							+ ")a where a.srownu >" + offerset;
				}
			} else {
				if (DBUtils.IsMySQLDB("system")) {
					sql = "select r.* from (" + sql + ")r limit 0,10";
				} else if (DBUtils.IsMSSQLDB("system")) {
					sql = "select TOP 10 a.* from(" + sql + ")a";
				} else {
					sql = "select a.* from(" + sql + ")a where rownum <=10";
				}
			}
			List list = DBUtils.execQueryforList("system", sql);
			data.setData(JSON.toJSONString(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return AjaxResult.success(data);
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCount() {
		return count;
	}

	public void setOfferset(String offerset) {
		this.offerset = offerset;
	}

	public String getOfferset() {
		return offerset;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getLimit() {
		return limit;
	}

	public void setFilter(String filter) {
		try {
			this.filter = URLDecoder.decode(filter, "UTF-8");
		} catch (Exception e) {
			this.filter = filter;
		}
	}

	public String getFilter() {
		return filter;
	}
}
