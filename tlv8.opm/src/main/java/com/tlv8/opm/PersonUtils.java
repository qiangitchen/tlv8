package com.tlv8.opm;

import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;

public class PersonUtils {

	@SuppressWarnings("rawtypes")
	public static String getPersonMemerFID(String personid) {
		String result = "";
		String sql = "select t.SFID from sa_oporg t where t.SPERSONID = '" + personid + "'";
		try {
			List list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				result = (String) ((Map) list.get(0)).get("SFID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
