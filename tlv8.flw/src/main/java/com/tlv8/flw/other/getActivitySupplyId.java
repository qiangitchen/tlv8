package com.tlv8.flw.other;

import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;
import com.tlv8.system.BaseController;

@SuppressWarnings({ "rawtypes" })
public class getActivitySupplyId {
	public static String getSupplyerActivity(String supplyercode) {
		String fsuppliercode = "";
		String result = "";
		String forgid = new BaseController().getContext().getCurrentOrgID();
		String sqlsupplycode = "select fid,fsuppliercode,fsuppliername from dyscmapp.DYSCM_SUPPLIER where forgid='"
				+ forgid + "' ";
		try {
			List list = DBUtils.execQueryforList("system", sqlsupplycode);
			if (list.size() > 0) {
				Map rs = (Map) list.get(0);
				fsuppliercode = (String) rs.get("FSUPPLIERCODE");
				String sql = "select fpersonid,fpersoncode,fpersonname from dyscmapp.DYSCM_PERSON_SUPPLIER_RIGHTS where fsuppliercode='"
						+ fsuppliercode + "'";
				List<Map<String, String>> list2 = DBUtils.execQueryforList("system", sql);
				for (int i = 0; i < list2.size(); i++) {
					Map m = list2.get(i);
					result += m.get("FPERSONID") + ",";

				}
			} else {
				// String sql = "select distinct FSUPPLIERCODE from DYSCM_PERSON_SUPPLIER_RIGHTS
				// where fPERSONCODE=upper('"+ PersonCode + "')";
				// System.out.println(sql);
				try {
					// List list1 = DBUtils.execQueryforList("dyscm", sql);
					// for (int i = 0; i < list1.size(); i++) {
					// Map r = (Map) list1.get(i);
					// String supplycode = (String) r.get("FSUPPLIERCODE");
					String sql1 = "select sid from dysys.sa_opperson where smainorgid=(select sid from dysys.sa_oporg where scode='"
							+ supplyercode + "')";
					// System.out.println(sql1);
					List list2 = DBUtils.execQueryforList("dyscm", sql1);
					for (int i = 0; i < list2.size(); i++) {
						Map m = (Map) list2.get(i);
						result += m.get("SID") + ",";
					}

					// }

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;

	}

	public static void main(String[] args) {
		// String ss="F46460A510BF404B99B97A810F5738C0";
		// getActivitySupplyId.getOrgUnitHasActivity(ss);
	}
}
