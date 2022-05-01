package com.tlv8.flw.helper;

import com.tlv8.flw.FlowFolderControler;
import com.tlv8.flw.base.FlowFile;

public class FlowFolderEditHelper {

	public static String createEditSql(FlowFolderControler obj) {
		String sql = "update " + FlowFile.folder_table + " set SNAME = '" + obj.getName() + "' %s where SID = '"
				+ obj.getId() + "'";
		String set = "";
		if (obj.getScode() != null && !"".equals(obj.getScode())) {
			set += ",SCODE = '" + obj.getScode() + "'";
		}
		if (obj.getPid() != null && !"".equals(obj.getPid())) {
			set += ",SPARENT = '" + obj.getPid() + "'";
		}
		if (obj.getSidpath() != null && !"".equals(obj.getSidpath())) {
			set += ",SIDPATH = '" + obj.getSidpath() + "'";
		}
		if (obj.getScodepath() != null && !"".equals(obj.getScodepath())) {
			set += ",SCODEPATH = '" + obj.getScodepath() + "'";
		}
		if (obj.getSnamepath() != null && !"".equals(obj.getSnamepath())) {
			set += ",SNAMEPATH = '" + obj.getSnamepath() + "'";
		}
		if (obj.getSprocessid() != null && !"".equals(obj.getSprocessid())) {
			set += ",SPROCESSID = '" + obj.getSprocessid() + "'";
		}
		if (obj.getSprocessname() != null && !"".equals(obj.getSprocessname())) {
			set += ",SPROCESSNAME = '" + obj.getSprocessname() + "'";
		}
		sql = String.format(sql, set);
		return sql;
	}

	public static String createInsertSql(FlowFolderControler obj) {
		String sql = "insert into " + FlowFile.folder_table + "(SID,SPARENT,SCODE,SNAME,SIDPATH,SCODEPATH,SNAMEPATH)"
				+ "values('" + obj.getId() + "','" + obj.getPid() + "','" + obj.getScode() + "','" + obj.getName()
				+ "','" + obj.getSidpath() + "','" + obj.getScodepath() + "','" + obj.getSnamepath() + "')";
		return sql;
	}

	public static String createQuerySql(FlowFolderControler obj) {
		String sql = "select * from  " + FlowFile.folder_table + " where SID = '" + obj.getId() + "'";
		return sql;
	}

	public static String createDeleteSql(FlowFolderControler obj) {
		String sql = "delete from  " + FlowFile.folder_table + " where SIDPATH like '" + obj.getSidpath() + "%'";
		return sql;
	}

}
