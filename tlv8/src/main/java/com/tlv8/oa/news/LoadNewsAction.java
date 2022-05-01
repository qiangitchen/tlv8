package com.tlv8.oa.news;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSON;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

@Controller
@Scope("prototype")
public class LoadNewsAction extends ActionSupport {
	Data data = new Data();

	public Data getData() {
		return this.data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	@ResponseBody
	@RequestMapping("/loadNewsAction")
	@Override
	public Object execute() throws Exception {
		ContextBean context = ContextBean.getContext(request);
		String sql = "select TOP 5 sID FID, FNEWSTITLE FTITLE, FPEOPLE, FTIME FPEOPLEDATE,FOPENSCOPEID from cyea_news_release where "
				+ "  FSTATE='已发布' and (" + "EXISTS(select 1 from SA_OPORG o where FOPENSCOPEID like '%'+o.SID+'%' and '"
				+ context.getCurrentPersonFullID() + "' like o.SFID+'%' )" + ") order by FSETTOPTIME desc";
		if (DBUtils.IsOracleDB("system")) {
			sql = "select * from(select sID FID, FNEWSTITLE FTITLE, FPEOPLE, FTIME FPEOPLEDATE,FOPENSCOPEID from cyea_news_release where "
					+ "  FSTATE='已发布' and ("
					+ "EXISTS(select 1 from SA_OPORG o where FOPENSCOPEID like '%'||o.SID||'%' and '"
					+ context.getCurrentPersonFullID() + "' like o.SFID||'%' )"
					+ ") order by FSETTOPTIME desc)where rownum<=5";
		} else if (DBUtils.IsMySQLDB("system")) {
			sql = "select sID FID, CONCAT(FNEWSTITLE,'') as FTITLE, FPEOPLE, CONCAT(FTIME,'') as FPEOPLEDATE,FOPENSCOPEID from cyea_news_release where "
					+ "  FSTATE='已发布' and ("
					+ "EXISTS(select 1 from SA_OPORG o where FOPENSCOPEID like concat('%',o.SID,'%') and '"
					+ context.getCurrentPersonFullID() + "' like concat(o.SFID,'%') )"
					+ ") order by FSETTOPTIME desc limit 0,5";
		}
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			data.setData(JSON.toJSONString(list));
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.getMessage());
			e.printStackTrace();
		}
		return this;
	}

}
