package com.tlv8.flw;

import com.tlv8.system.BaseController;

public class flwOrgUtils {
	/*
	 * 获取当前登录人信息
	 */
	public static String getCurrentPersonID() {
		return new BaseController().getContext().getCurrentPersonID();
	}

	public static String getCurrentPersonCode() {
		return new BaseController().getContext().getCurrentPersonCode();
	}

	public static String getCurrentPersonName() {
		return new BaseController().getContext().getCurrentPersonName();
	}

	public static String getCurrentPersonFID() {
		return new BaseController().getContext().getCurrentPersonFullID();
	}

	public static String getCurrentPersonFCode() {
		return new BaseController().getContext().getCurrentPersonFullCode();
	}

	public static String getCurrentPersonFName() {
		return new BaseController().getContext().getCurrentPersonFullName();
	}

	/*
	 * 获取当前登录人岗位信息
	 */
	public static String getCurrentPosID() {
		return new BaseController().getContext().getCurrentPositionID();
	}

	public static String getCurrentPosCode() {
		return new BaseController().getContext().getCurrentPositionCode();
	}

	public static String getCurrentPosName() {
		return new BaseController().getContext().getCurrentPositionName();
	}

	public static String getCurrentPosFID() {
		return new BaseController().getContext().getCurrentPositionFullID();
	}

	public static String getCurrentPosFCode() {
		return new BaseController().getContext().getCurrentPositionFullCode();
	}

	public static String getCurrentPosFName() {
		return new BaseController().getContext().getCurrentPositionFullName();
	}

	/*
	 * 获取当前登录人部门信息
	 */
	public static String getCurrentDeptID() {
		return new BaseController().getContext().getCurrentDeptID();
	}

	public static String getCurrentDeptCode() {
		return new BaseController().getContext().getCurrentDeptCode();
	}

	public static String getCurrentDeptName() {
		return new BaseController().getContext().getCurrentDeptName();
	}

	public static String getCurrentDeptFID() {
		return new BaseController().getContext().getCurrentDeptFullID();
	}

	public static String getCurrentDeptFCode() {
		return new BaseController().getContext().getCurrentDeptFullCode();
	}

	public static String getCurrentDeptFName() {
		return new BaseController().getContext().getCurrentDeptFullName();
	}

	/*
	 * 获取当前登录人机构信息
	 */
	public static String getCurrentOgnID() {
		return new BaseController().getContext().getCurrentOgnID();
	}

	public static String getCurrentOgnCode() {
		return new BaseController().getContext().getCurrentOgnCode();
	}

	public static String getCurrentOgnName() {
		return new BaseController().getContext().getCurrentOgnName();
	}

	public static String getCurrentOgnFID() {
		return new BaseController().getContext().getCurrentOgnFullID();
	}

	public static String getCurrentOgnFCode() {
		return new BaseController().getContext().getCurrentOgnFullCode();
	}

	public static String getCurrentOgnFName() {
		return new BaseController().getContext().getCurrentOgnFullName();
	}

	/*
	 * 获取当前登录人组织信息
	 */
	public static String getCurrentOrgID() {
		return new BaseController().getContext().getCurrentOrgID();
	}

	public static String getCurrentOrgCode() {
		return new BaseController().getContext().getCurrentOrgCode();
	}

	public static String getCurrentOrgName() {
		return new BaseController().getContext().getCurrentOrgName();
	}

	public static String getCurrentOrgFID() {
		return new BaseController().getContext().getCurrentOrgFullID();
	}

	public static String getCurrentOrgFCode() {
		return new BaseController().getContext().getCurrentOrgFullCode();
	}

	public static String getCurrentOrgFName() {
		return new BaseController().getContext().getCurrentOrgFullName();
	}

}
