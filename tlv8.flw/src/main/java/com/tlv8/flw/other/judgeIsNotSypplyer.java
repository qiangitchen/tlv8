package com.tlv8.flw.other;

import java.util.List;

import com.tlv8.base.db.DBUtils;
import com.tlv8.system.utils.ContextUtils;

@SuppressWarnings({ "rawtypes" })
public class judgeIsNotSypplyer {
	public static String judgeIsNotSypplyerActivity() {
		String result = "";
		String forgid = ContextUtils.getContext().getCurrentOrgID();
		String sqlsupplycode = "select fid,fsuppliercode,fsuppliername from dyscmapp.DYSCM_SUPPLIER where forgid='"
			+ forgid + "' ";
		try {
			List list = DBUtils.execQueryforList("system", sqlsupplycode);
			if (list.size() > 0) {
				result="false";
			} else {
				result="true";
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}
}
