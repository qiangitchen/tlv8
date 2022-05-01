package com.tlv8.flw.expression;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.tlv8.base.db.DBUtils;

@SuppressWarnings({ "rawtypes" })
public class BusinessExpression {
	/**
	 * 获取表中字段的值
	 * 
	 * @concept【必填】： 表(视图)名称
	 * 
	 * @individual【必填】： 主键的值 fID/sID
	 * 
	 * @condition【可选】： 过虑条件， 例如：fName='system';
	 * 
	 * @orderRelation【可选】： 用来作排序的字段 例如：fCode,fName
	 * 
	 * @returnRelation【必填】： 要取哪个关系的值.
	 * 
	 * @dbkey【必填】：数据库连接标志。
	 */
	public static String getRelationValueString(String concept, String individual, String condition,
			String orderRelation, String returnRelation, String dbkey) {
		String result = "";
		String sql = "select " + returnRelation.toUpperCase() + " from " + concept + " where "
				+ ("system".equals(dbkey.toLowerCase()) ? "SID" : "FID") + " = '" + individual + "'";
		if (condition != null && !"".equals(condition.trim())) {
			sql += " and (" + condition + ")";
		}
		if (orderRelation != null && !"".equals(orderRelation.trim())) {
			sql += " orderby " + orderRelation;
		}
		try {
			List<Map<String, String>> list = DBUtils.execQueryforList(dbkey, sql);
			if (list.size() > 0) {
				Map m = list.get(0);
				result = String.valueOf(m.get(returnRelation.toUpperCase()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
