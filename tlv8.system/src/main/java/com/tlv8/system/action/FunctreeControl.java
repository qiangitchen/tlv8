package com.tlv8.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tlv8.base.spring.SpringUtils;
import com.tlv8.system.pojo.SaOppermission;
import com.tlv8.system.service.SaOppermissionService;

/**
 * 系统菜单权限控制
 * 
 * @author chenqian
 * @update 2023-12-20
 *
 */
public class FunctreeControl {
	private static Map<String, Map<String, String>> haveAutherMaps = new HashMap<String, Map<String, String>>();

	public static void initData(String personfID, String psnid) {
		Map<String, String> have = new HashMap<String, String>();
		SaOppermissionService saOppermissionService = SpringUtils.getBean(SaOppermissionService.class);
		List<SaOppermission> perlist = saOppermissionService.selectPermissionByPerson(personfID, psnid);
		for (SaOppermission per : perlist) {
			String process = per.getSprocess();
			String activity = per.getSactivity();
			activity = (activity == null || "null".equals(activity)) ? "" : activity;
			have.put(process + activity, activity);
		}
		haveAutherMaps.put(personfID, have);
	}

	public static void remove(String personfID) {
		haveAutherMaps.remove(personfID);
	}

	public static boolean haveAuther(String url, String personfID, String personid) {
		initData(personfID, personid);
		Map<String, String> havemap = haveAutherMaps.get(personfID);
		if (havemap != null) {
			return havemap.containsKey(url);
		}
		return false;
	}

	public static Map<String, String> gethaveAuther(String personfID, String psnid) {
		initData(personfID, psnid);
		return haveAutherMaps.get(personfID);
	}

}
