package com.tlv8.flw.other;

import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;

@SuppressWarnings({ "rawtypes" })
public class locateCustomerOperatorname {
	public static String locateCustomerOperator(String customerid) {
		// System.out.println(supplyercode);
		String result = "";
		String sql = "select sid from dysys.sa_opperson a where a.sdescription =(select FOPERATORID from dycem_bdncustom where fid='"
				+ customerid + "')";
		// System.out.println(sql);
		try {
			List list = DBUtils.execQueryforList("dycem", sql);
			if (list.size() > 0) {
				Map rs = (Map) list.get(0);
				result += rs.get("SID") + ",";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
}