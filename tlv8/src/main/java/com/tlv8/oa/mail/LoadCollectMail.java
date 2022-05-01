package com.tlv8.oa.mail;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.Data;
import com.tlv8.base.db.DBUtils;
import com.alibaba.fastjson.JSONArray;
import com.tlv8.base.ActionSupport;
import com.tlv8.system.bean.ContextBean;

@Controller
@Scope("prototype")
public class LoadCollectMail extends ActionSupport {
	private Data data = new Data();
	private String value;
	private String count;
	private String limit;
	private String offerset;
	private String cost;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		try {
			this.value = URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping("/LoadCollectMailAction")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object execute() throws Exception {

		String result = "";

		int last_week_count = 0;
		int earlier_week_count = 0;
		int Monday_count = 0;
		int Tuesday_count = 0;
		int Wednesday_count = 0;
		int Thursday_count = 0;
		int Friday_count = 0;
		int Saturday_count = 0;
		int Today_count = 0;

		List last_week = new ArrayList();
		List earlier_week = new ArrayList();
		List Monday_info = new ArrayList();
		List Tuesday_info = new ArrayList();
		List Wednesday_info = new ArrayList();
		List Thursday_info = new ArrayList();
		List Friday_info = new ArrayList();
		List Saturday_info = new ArrayList();
		List Today_info = new ArrayList();

		Calendar ca = Calendar.getInstance();
		int c_day = ca.get(Calendar.DAY_OF_WEEK) - 1;

		ContextBean con = ContextBean.getContext(request);
		String sql = "SELECT FID,MAILIN,FQUREY,FSENDDEPT,FEMAILNAME,FSENDPERNAME,FSENDTIME,WEEK FROM "
				+ "((SELECT FID,'收件箱' MAILIN,FQUREY FQUREY,FSENDDEPT,FEMAILNAME,FSENDPERNAME,FSENDTIME,'this_week' WEEK FROM OA_EM_RECEIVEEMAIL  WHERE FCOLLECT='1' AND FCONSIGNEEID = '"
				+ con.getCurrentPersonID()
				+ "' AND TRUNC(FSENDTIME) > TRUNC(SYSDATE-"
				+ c_day
				+ ") UNION ALL "
				+ "SELECT FID,CASE(FSTATE) WHEN '已保存' THEN '草稿箱' WHEN '已发送' THEN '发件箱' ELSE '' END MAILIN,FSTATE FQUREY ,FSENDDEPT,FEMAILNAME,to_char(FCONSIGNEE) FSENDPERNAME,FSENDTIME,'this_week' WEEK FROM OA_EM_SENDEMAIL  WHERE FCOLLECT='1' AND FSENDPERID = '"
				+ con.getCurrentPersonID()
				+ "' AND TRUNC(FSENDTIME) > TRUNC(SYSDATE-" + c_day + "))";
		sql += " UNION ";
		sql += "(SELECT FID,'收件箱' MAILIN,FQUREY FQUREY,FSENDDEPT,FEMAILNAME,FSENDPERNAME,FSENDTIME,'last_week' WEEK FROM OA_EM_RECEIVEEMAIL  WHERE FCOLLECT='1' AND FCONSIGNEEID = '"
				+ con.getCurrentPersonID()
				+ "' AND TRUNC(FSENDTIME) <= TRUNC(SYSDATE-"
				+ c_day
				+ ")  AND TRUNC(FSENDTIME) > TRUNC(SYSDATE-"
				+ c_day
				+ "-7) UNION ALL "
				+ "SELECT FID,CASE(FSTATE) WHEN '已保存' THEN '草稿箱' WHEN '已发送' THEN '发件箱' ELSE '' END MAILIN,FSTATE FQUREY ,FSENDDEPT,FEMAILNAME,to_char(FCONSIGNEE) FSENDPERNAME,FSENDTIME,'last_week' WEEK FROM OA_EM_SENDEMAIL  WHERE FCOLLECT='1' AND FSENDPERID = '"
				+ con.getCurrentPersonID()
				+ "' AND TRUNC(FSENDTIME) <= TRUNC(SYSDATE-"
				+ c_day
				+ ")  AND TRUNC(FSENDTIME) > TRUNC(SYSDATE-" + c_day + "-7))";
		sql += " UNION ";
		sql += "(SELECT FID,'收件箱' MAILIN,FQUREY FQUREY,FSENDDEPT,FEMAILNAME,FSENDPERNAME,FSENDTIME,'earlier' WEEK FROM OA_EM_RECEIVEEMAIL  WHERE FCOLLECT='1' AND FCONSIGNEEID = '"
				+ con.getCurrentPersonID()
				+ "' AND TRUNC(FSENDTIME) <= TRUNC(SYSDATE-"
				+ c_day
				+ "-7) UNION ALL "
				+ "SELECT FID,CASE(FSTATE) WHEN '已保存' THEN '草稿箱' WHEN '已发送' THEN '发件箱' ELSE '' END MAILIN,FSTATE FQUREY ,FSENDDEPT,FEMAILNAME,to_char(FCONSIGNEE) FSENDPERNAME,FSENDTIME,'earlier' WEEK FROM OA_EM_SENDEMAIL  WHERE FCOLLECT='1' AND FSENDPERID = '"
				+ con.getCurrentPersonID()
				+ "' AND TRUNC(FSENDTIME) <= TRUNC(SYSDATE-"
				+ c_day
				+ "-7))) WHERE ROWNUM<=20 ORDER BY FSENDTIME DESC";
		if (DBUtils.IsMySQLDB("oa")) {
			sql = "SELECT FID,'收件箱' MAILIN,FQUREY FQUREY,FSENDDEPT,FEMAILNAME,FSENDPERNAME,FSENDTIME,'this_week' WEEK FROM OA_EM_RECEIVEEMAIL  WHERE FCOLLECT='1' AND FCONSIGNEEID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME > date_sub(curdate(), interval -"
					+ c_day
					+ " day) "
					+ "UNION ALL "
					+ "SELECT FID,CASE(FSTATE) WHEN '已保存' THEN '草稿箱' WHEN '已发送' THEN '发件箱' ELSE '' END MAILIN,FSTATE FQUREY ,FSENDDEPT,FEMAILNAME,FCONSIGNEE FSENDPERNAME,FSENDTIME,'this_week' WEEK FROM OA_EM_SENDEMAIL  WHERE FCOLLECT='1' AND FSENDPERID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME > date_sub(curdate(), interval -"
					+ c_day + " day)";
			sql += " UNION ";
			sql += "SELECT FID,'收件箱' MAILIN,FQUREY FQUREY,FSENDDEPT,FEMAILNAME,FSENDPERNAME,FSENDTIME,'last_week' WEEK FROM OA_EM_RECEIVEEMAIL  WHERE FCOLLECT='1' AND FCONSIGNEEID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME <= date_sub(curdate(), interval -"
					+ c_day
					+ " day)  AND FSENDTIME > date_sub(curdate(), interval -"
					+ (c_day + 7)
					+ " day) UNION ALL "
					+ "SELECT FID,CASE(FSTATE) WHEN '已保存' THEN '草稿箱' WHEN '已发送' THEN '发件箱' ELSE '' END MAILIN,FSTATE FQUREY ,FSENDDEPT,FEMAILNAME,FCONSIGNEE FSENDPERNAME,FSENDTIME,'last_week' WEEK FROM OA_EM_SENDEMAIL  WHERE FCOLLECT='1' AND FSENDPERID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME <= date_sub(curdate(),interval -"
					+ c_day
					+ " day)  AND FSENDTIME > date_sub(curdate(),interval -"
					+ (c_day + 7) + " day)";
			sql += " UNION ";
			sql += "SELECT FID,'收件箱' MAILIN,FQUREY FQUREY,FSENDDEPT,FEMAILNAME,FSENDPERNAME,FSENDTIME,'earlier' WEEK FROM OA_EM_RECEIVEEMAIL  WHERE FCOLLECT='1' AND FCONSIGNEEID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME <= date_sub(curdate(),interval -"
					+ (c_day + 7)
					+ " day) UNION ALL "
					+ "SELECT FID,CASE(FSTATE) WHEN '已保存' THEN '草稿箱' WHEN '已发送' THEN '发件箱' ELSE '' END MAILIN,FSTATE FQUREY ,FSENDDEPT,FEMAILNAME,FCONSIGNEE FSENDPERNAME,FSENDTIME,'earlier' WEEK FROM OA_EM_SENDEMAIL  WHERE FCOLLECT='1' AND FSENDPERID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME <= date_sub(curdate(),interval -"
					+ (c_day + 7) + " day) ORDER BY FSENDTIME DESC";
		} else if (DBUtils.IsMSSQLDB("oa")) {
			sql = "SELECT FID,'收件箱' MAILIN,FQUREY FQUREY,FSENDDEPT,FEMAILNAME,FSENDPERNAME,FSENDTIME,'this_week' WEEK FROM OA_EM_RECEIVEEMAIL  WHERE FCOLLECT='1' AND FCONSIGNEEID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME > DATEADD(DAY, -"
					+ c_day
					+ ",GETDATE()) "
					+ "UNION ALL "
					+ "SELECT FID,CASE(FSTATE) WHEN '已保存' THEN '草稿箱' WHEN '已发送' THEN '发件箱' ELSE '' END MAILIN,FSTATE FQUREY ,FSENDDEPT,FEMAILNAME,CONVERT(varchar(4000),FCONSIGNEE) FSENDPERNAME,FSENDTIME,'this_week' WEEK FROM OA_EM_SENDEMAIL  WHERE FCOLLECT='1' AND FSENDPERID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME > DATEADD(DAY, -"
					+ c_day
					+ ",GETDATE())";
			sql += " UNION ";
			sql += "SELECT FID,'收件箱' MAILIN,FQUREY FQUREY,FSENDDEPT,FEMAILNAME,FSENDPERNAME,FSENDTIME,'last_week' WEEK FROM OA_EM_RECEIVEEMAIL  WHERE FCOLLECT='1' AND FCONSIGNEEID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME <= DATEADD(DAY, -"
					+ c_day
					+ ", GETDATE())  AND FSENDTIME > DATEADD(DAY, -"
					+ (c_day + 7)
					+ ", GETDATE()) UNION ALL "
					+ "SELECT FID,CASE(FSTATE) WHEN '已保存' THEN '草稿箱' WHEN '已发送' THEN '发件箱' ELSE '' END MAILIN,FSTATE FQUREY ,FSENDDEPT,FEMAILNAME,CONVERT(varchar(4000),FCONSIGNEE) FSENDPERNAME,FSENDTIME,'last_week' WEEK FROM OA_EM_SENDEMAIL  WHERE FCOLLECT='1' AND FSENDPERID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME <= DATEADD(DAY, "
					+ c_day
					+ ", GETDATE())  AND FSENDTIME > DATEADD(DAY, -"
					+ (c_day + 7) + ", GETDATE())";
			sql += " UNION ";
			sql += "SELECT FID,'收件箱' MAILIN,FQUREY FQUREY,FSENDDEPT,FEMAILNAME,FSENDPERNAME,FSENDTIME,'earlier' WEEK FROM OA_EM_RECEIVEEMAIL  WHERE FCOLLECT='1' AND FCONSIGNEEID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME <= DATEADD(DAY, -"
					+ (c_day + 7)
					+ ", GETDATE()) "
					+ "UNION ALL "
					+ "SELECT FID,CASE(FSTATE) WHEN '已保存' THEN '草稿箱' WHEN '已发送' THEN '发件箱' ELSE '' END MAILIN,FSTATE FQUREY ,FSENDDEPT,FEMAILNAME,CONVERT(varchar(4000),FCONSIGNEE) FSENDPERNAME,FSENDTIME,'earlier' WEEK FROM OA_EM_SENDEMAIL  WHERE FCOLLECT='1' AND FSENDPERID = '"
					+ con.getCurrentPersonID()
					+ "' AND FSENDTIME <= DATEADD(DAY, -"
					+ (c_day + 7)
					+ ", GETDATE()) ORDER BY FSENDTIME DESC";
		}
		try {
			long bdate = new Date().getTime();
			count = Utils.getCount("oa", sql);
			List list = DBUtils.execQueryforList("oa",
					Utils.getLimitSql(limit, offerset, sql));
			// count = list.size() + "";
			long edate = new Date().getTime();
			double between = (double) (edate - bdate) / 1000;
			cost = Double.toString(between);
			for (int i = 0; i < list.size(); i++) {
				Map m = (Map) list.get(i);
				String week = (String) m.get("WEEK");
				if (week.equals("this_week")) {
					String date = (String) m.get("FSENDTIME");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date da = sdf.parse(date);
					Calendar c_date = Calendar.getInstance();
					c_date.setTime(da);
					int day = c_date.get(Calendar.DAY_OF_WEEK) - 1;
					if (day == c_day) {
						day = 0;
					}
					switch (day) {
					case 0:
						Today_info.add(m);
						Today_count++;
						break;
					case 1:
						Monday_info.add(m);
						Monday_count++;
						break;
					case 2:
						Tuesday_info.add(m);
						Tuesday_count++;
						break;
					case 3:
						Wednesday_info.add(m);
						Wednesday_count++;
						break;
					case 4:
						Thursday_info.add(m);
						Thursday_count++;
						break;
					case 5:
						Friday_info.add(m);
						Friday_count++;
						break;
					case 6:
						Saturday_info.add(m);
						Saturday_count++;
						break;
					case 7:
						Today_info.add(m);
						Today_count++;
						break;

					default:
						break;
					}
				} else if (week.equals("last_week")) {
					last_week.add(m);
					last_week_count++;
				} else {
					earlier_week.add(m);
					earlier_week_count++;
				}
			}
			if (Today_count > 0) {
				JSONArray json = new JSONArray(Today_info);
				result += ",{\"week\":\"今天\",\"count\":\"" + Today_count
						+ "\",\"data\":\""
						+ json.toString().replaceAll("\"", "'") + "\"}";

			}
			if (Monday_count > 0) {
				JSONArray json = new JSONArray(Monday_info);
				result += ",{\"week\":\"星期一\",\"count\":\"" + Monday_count
						+ "\",\"data\":\""
						+ json.toString().replaceAll("\"", "'") + "\"}";
			}
			if (Tuesday_count > 0) {
				JSONArray json = new JSONArray(Tuesday_info);
				result += ",{\"week\":\"星期二\",\"count\":\"" + Tuesday_count
						+ "\",\"data\":\""
						+ json.toString().replaceAll("\"", "'") + "\"}";
			}
			if (Wednesday_count > 0) {
				JSONArray json = new JSONArray(Wednesday_info);
				result += ",{\"week\":\"星期三\",\"count\":\"" + Wednesday_count
						+ "\",\"data\":\""
						+ json.toString().replaceAll("\"", "'") + "\"}";
			}
			if (Thursday_count > 0) {
				JSONArray json = new JSONArray(Thursday_info);
				result += ",{\"week\":\"星期四\",\"count\":\"" + Thursday_count
						+ "\",\"data\":\""
						+ json.toString().replaceAll("\"", "'") + "\"}";
			}
			if (Friday_count > 0) {
				JSONArray json = new JSONArray(Friday_info);
				result += ",{\"week\":\"星期五\",\"count\":\"" + Friday_count
						+ "\",\"data\":\""
						+ json.toString().replaceAll("\"", "'") + "\"}";
			}
			if (Saturday_count > 0) {
				JSONArray json = new JSONArray(Saturday_info);
				result += ",{\"week\":\"星期六\",\"count\":\"" + Saturday_count
						+ "\",\"data\":\""
						+ json.toString().replaceAll("\"", "'") + "\"}";
			}
			if (last_week_count > 0) {
				JSONArray json = new JSONArray(last_week);
				result += ",{\"week\":\"上周\",\"count\":\"" + last_week_count
						+ "\",\"data\":\""
						+ json.toString().replaceAll("\"", "'") + "\"}";
			}
			if (earlier_week_count > 0) {
				JSONArray json = new JSONArray(earlier_week);
				result += ",{\"week\":\"更早\",\"count\":\"" + earlier_week_count
						+ "\",\"data\":\""
						+ json.toString().replaceAll("\"", "'") + "\"}";
			}
			result = "[" + result.replaceFirst(",", "") + "]";
			// System.out.println(result);
			data.setData(result);
			data.setFlag("true");
		} catch (Exception e) {
			data.setFlag("false");
			data.setMessage(e.toString());
			e.printStackTrace();
		}
		return this;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Data getData() {
		return data;
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

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getCost() {
		return cost;
	}

}
