package com.tlv8.opm.inter.impl;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import com.tlv8.opm.inter.OrgAdmAuthority;
import com.tlv8.opm.utils.FunTree;
import com.tlv8.opm.utils.OpmAgent;
import com.tlv8.system.action.FunctreeControl;
import com.tlv8.system.bean.ContextBean;

/*
 * 判断当前登录人是否有功能权限
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrganizationAdministrativeAuthority implements OrgAdmAuthority {
	protected static Map havePerMap = new HashMap();
	private ContextBean context;
	protected Map haveAutherMap = new HashMap();
	protected Map haveAgentAutherMap = new HashMap();

	public OrganizationAdministrativeAuthority(String sessionid) {
		initData(ContextBean.getContext(sessionid));
	}

	public OrganizationAdministrativeAuthority(ContextBean scontext, ServletContext sevContex) {
		if (havePerMap.isEmpty()) {
			havePerMap = new FunTree(sevContex).getFunmap();
		}
		initData(scontext);
	}

	public OrganizationAdministrativeAuthority(ContextBean scontext) {
		initData(scontext);
	}

	public void initData(ContextBean scontext) {
		context = scontext;
		if (havePerMap.isEmpty()) {
			havePerMap = new FunTree().getFunmap();
		}
		haveAutherMap = FunctreeControl.gethaveAuther(context.getCurrentPersonFullID(), context.getCurrentPersonID());
		haveAgentAutherMap = OpmAgent.getAgentFuncAuthorMap(context);
	}

	/*
	 * 是否有“机构管理”权限
	 */
	public boolean getAdminOrgAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/organization/mainActivity.html");
	}

	/*
	 * 是否有“机构管理-分级”权限
	 */
	public boolean getGradeOrgAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/organization/gradeActivity.html");
	}

	/*
	 * 是否有“人员管理”权限
	 */
	public boolean getAdminPsmAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/personmanage/mainActivity.html");
	}

	/*
	 * 是否有“人员管理-分级”权限
	 */
	public boolean getGradePsmAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/personmanage/gradeActivity.html");
	}

	/*
	 * 是否有“角色管理”权限
	 */
	public boolean getAdminRoleAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/role/mainActivity.html");
	}

	/*
	 * 是否有“授权管理”权限
	 */
	public boolean getAdminAuthmAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/authorization/mainActivity.html");
	}

	/*
	 * 是否有“授权管理-分级”权限
	 */
	public boolean getGradeAuthmAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/authorization/gradeActivity.html");
	}

	/*
	 * 是否有“权限管理”权限
	 */
	public boolean getAdminManageAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/management/mainActivity.html");
	}

	/*
	 * 是否有“权限管理-分级”权限
	 */
	public boolean getGradeManageAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/management/gradeActivity.html");
	}

	/*
	 * 是否有“回收站”权限
	 */
	public boolean getAdminRecycledAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/recycled/mainActivity.html");
	}

	/*
	 * 是否有“权限管理-分级”权限
	 */
	public boolean getGradeRecycledAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/recycled/gradeActivity.html");
	}

	/*
	 * 是否有“分级管理”权限
	 */
	public boolean getAdminGradeAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/grade/mainActivity.html");
	}

	/*
	 * 是否有“分级管理-分级”权限
	 */
	public boolean getGradeGradeAuthor() {
		return haveAutherMap.containsKey("/SA/OPM/grade/gradeActivity.html");
	}

	/*
	 * 判断访问的页面是否有权限(只针对在功能树上有配置的页面)
	 */
	public boolean isHaveAutority(String url) {
		return haveAutherMap.containsKey(url) || (!havePerMap.containsKey(url) && !havePerMap.containsValue(url))
				|| isContains(haveAutherMap, url);
	}

	/*
	 * 判断访问的页面是否有权限(只针对在功能树上有配置的页面)委托代理
	 */
	public boolean isHaveAgentAuthor(String url) {
		return haveAgentAutherMap.containsKey(url) || (!havePerMap.containsKey(url) && !havePerMap.containsValue(url))
				|| isContains(haveAgentAutherMap, url);
	}

	protected Set getKeySet(Map map, Object value) {
		if (!map.containsValue(value)) { // Value 是否存在
			return null;
		}
		Set<Object> keySet = new LinkedHashSet<Object>();
		for (Object k : map.keySet()) {
			if (map.get(k).equals(value)) {
				keySet.add(k);
			}
		}
		return keySet;
	}

	protected boolean isContains(Map map, String value) {
		boolean res = false;
		Set<String> keySet = getKeySet(havePerMap, value);
		if (keySet == null) {
			return res;
		}
		for (Object k : keySet) {
			if (map.containsKey(k)) {
				res = true;
				break;
			}
		}
		return res;
	}
}
