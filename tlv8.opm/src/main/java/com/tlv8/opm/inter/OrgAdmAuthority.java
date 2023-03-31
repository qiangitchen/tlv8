package com.tlv8.opm.inter;

/*
 * 判断当前登录人是否有系统管理相关权限
 */
public interface OrgAdmAuthority {

	/*
	 * 是否有“机构管理”权限
	 */
	public boolean getAdminOrgAuthor();

	/*
	 * 是否有“机构管理-分级”权限
	 */
	public boolean getGradeOrgAuthor();

	/*
	 * 是否有“人员管理”权限
	 */
	public boolean getAdminPsmAuthor();

	/*
	 * 是否有“人员管理-分级”权限
	 */
	public boolean getGradePsmAuthor();

	/*
	 * 是否有“角色管理”权限
	 */
	public boolean getAdminRoleAuthor();

	/*
	 * 是否有“授权管理”权限
	 */
	public boolean getAdminAuthmAuthor();

	/*
	 * 是否有“授权管理-分级”权限
	 */
	public boolean getGradeAuthmAuthor();

	/*
	 * 是否有“权限管理”权限
	 */
	public boolean getAdminManageAuthor();

	/*
	 * 是否有“权限管理-分级”权限
	 */
	public boolean getGradeManageAuthor();

	/*
	 * 是否有“回收站”权限
	 */
	public boolean getAdminRecycledAuthor();

	/*
	 * 是否有“权限管理-分级”权限
	 */
	public boolean getGradeRecycledAuthor();

	/*
	 * 是否有“分级管理”权限
	 */
	public boolean getAdminGradeAuthor();

	/*
	 * 是否有“分级管理-分级”权限
	 */
	public boolean getGradeGradeAuthor();

	public boolean isHaveAutority(String url);
	
	public boolean isHaveAgentAuthor(String url);
}
