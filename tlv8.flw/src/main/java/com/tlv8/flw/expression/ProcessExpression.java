package com.tlv8.flw.expression;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;

@SuppressWarnings({ "rawtypes" })
public class ProcessExpression {

	/**
	 * 获取指定环节处理人
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityExecutor(String flowID, String activity) {
		String result = "";
		String sql = "select SEPERSONNAME from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME desc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = (String) m.get("SEPERSONNAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节处理人FID
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityExecutorFID(String flowID, String activity) {
		String result = "";
		String sql = "select SEFID from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME desc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = (String) m.get("SEFID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节处理人所属的组织
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityExecutorOrg(String flowID, String activity) {
		String result = "";
		String sql = "select SEOGNNAME from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME desc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = (String) m.get("SEOGNNAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节处理人所属的组织
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityExecutorOrgID(String flowID, String activity) {
		String result = "";
		String sql = "select SEOGNID from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME desc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = (String) m.get("SEOGNID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节处理人所属的组织
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityExecutorOrgFID(String flowID, String activity) {
		String result = "";
		String sql = "select SEOGNID from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME desc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = "'" + m.get("SEOGNID") + "'";
				if (!"".equals(result)) {
					sql = "select SFID from SA_OPORG where SID in (" + result + ")";
					list = DBUtils.execQueryforList("system", sql);
					if (list.size() > 0) {
						Map ms = list.get(0);
						result = (String) ms.get("SFID");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节处理人所属的部门
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityExecutorDept(String flowID, String activity) {
		String result = "";
		String sql = "select SEDEPTNAME from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME desc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = (String) m.get("SEDEPTNAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节处理人所属的部门ID
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityExecutorDeptID(String flowID, String activity) {
		String result = "";
		String sql = "select SEDEPTID from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "'  order by SCREATETIME desc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = (String) m.get("SEDEPTID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节处理人所属的部门FID
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityExecutorDeptFID(String flowID, String activity) {
		String result = "";
		String sql = "select SEDEPTID from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "'  order by SCREATETIME desc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = "'" + m.get("SEDEPTID") + "'";
				if (!"".equals(result)) {
					sql = "select SFID from SA_OPORG where SID in (" + result + ")";
					list = DBUtils.execQueryforList("system", sql);
					if (list.size() > 0) {
						Map ms = list.get(0);
						result += ms.get("SFID");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节提交人
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityCreator(String flowID, String activity) {
		String result = "";
		String sql = "select SCPERSONNAME from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME asc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = (String) m.get("SCPERSONNAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节提交人FID
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityCreatorFID(String flowID, String activity) {
		String result = "";
		String sql = "select SCFID from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME asc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = (String) m.get("SCFID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节提交人所属的组织
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityCreatorOrg(String flowID, String activity) {
		String result = "";
		String sql = "select SCOGNNAME from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME asc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = (String) m.get("SCOGNNAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节提交人所属的组织
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityCreatorOrgFID(String flowID, String activity) {
		String result = "";
		String sql = "select SCOGNID from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME asc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = "'" + m.get("SCOGNID") + "'";
			}
			if (!"".equals(result)) {
				sql = "select SFID from SA_OPORG where SID in (" + result + ")";
				list = DBUtils.execQueryforList("system", sql);
				if (list.size() > 0) {
					Map m = list.get(0);
					result = (String) m.get("SFID");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节提交人所属的部门
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityCreatorDept(String flowID, String activity) {
		String result = "";
		String sql = "select SCDEPTNAME from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME asc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = (String) m.get("SCDEPTNAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 获取指定环节提交人所属的部门ID
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityCreatorDeptID(String flowID, String activity) {
		String result = "";
		String sql = "select SCDEPTID from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME asc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = (String) m.get("SCDEPTID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取指定环节提交人所属的部门FID
	 * 
	 * @activity: 可以直接传activity标识
	 */
	public static String getActivityCreatorDeptFID(String flowID, String activity) {
		String result = "";
		String sql = "select SCDEPTID from SA_TASK where SFLOWID = '" + flowID + "' and SACTIVITY = '" + activity
				+ "' order by SCREATETIME asc";
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList("system", sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = "'" + m.get("SCDEPTID") + "'";
			}
			if (!"".equals(result)) {
				sql = "select SFID from SA_OPORG where SID in (" + result + ")";
				list = DBUtils.execQueryforList("system", sql);
				if (list.size() > 0) {
					Map m = list.get(0);
					result = (String) m.get("SFID");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
