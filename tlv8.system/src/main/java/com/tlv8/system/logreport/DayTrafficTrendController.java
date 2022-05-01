package com.tlv8.system.logreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;

@Controller
@RequestMapping("/system/logreport")
public class DayTrafficTrendController {

	@ResponseBody
	@RequestMapping(value = "/getDayTrafficTrend", produces = "application/json;charset=UTF-8")
	@SuppressWarnings({ "rawtypes" })
	public Object execute() throws Exception {
		List<String> xdata = new ArrayList<String>();
		List<Long> ydata = new ArrayList<Long>();
		if (DBUtils.IsOracleDB("system") || DBUtils.IsDMDB("system") || DBUtils.IsKingDB("system")) {
			for (int t = 8; t < 21; t++) {
				String sql = "select count(*) AMOUNT from SA_LOG where to_char(SCREATETIME,'hh24') >=" + t
						+ " and to_char(SCREATETIME,'hh24') <" + (t + 1);
				List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
				xdata.add(t + ":00");
				ydata.add(Long.parseLong(list.get(0).get("AMOUNT")));
			}
		} else if (DBUtils.IsMySQLDB("system")) {
			for (int t = 8; t < 21; t++) {
				String sql = "select count(*) AMOUNT from SA_LOG where hour(SCREATETIME) >=" + t
						+ " and hour(SCREATETIME) <" + (t + 1);
				List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
				xdata.add(t + ":00");
				ydata.add(Long.parseLong(list.get(0).get("AMOUNT")));
			}

		} else {
			for (int t = 8; t < 21; t++) {
				String sql = "select count(*) AMOUNT from SA_LOG where datename(HH,SCREATETIME) >=" + t
						+ " and datename(HH,SCREATETIME) <" + (t + 1);
				List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
				xdata.add(t + ":00");
				ydata.add(Long.parseLong(list.get(0).get("AMOUNT")));
			}
		}
		Map<String, List> json = new HashMap<String, List>();
		try {
			json.put("xdata", xdata);
			json.put("ydata", ydata);
		} catch (Exception e) {
		}
		return json;
	}

}
