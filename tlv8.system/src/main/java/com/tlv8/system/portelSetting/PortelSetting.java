package com.tlv8.system.portelSetting;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tlv8.base.db.DBUtils;
import com.tlv8.system.BaseController;

@Controller
@Scope("prototype")
public class PortelSetting extends BaseController {
	private String portelIDs = null;

	public String getPortelIDs() {
		return portelIDs;
	}

	public void setPortelIDs(String portelIDs) {
		this.portelIDs = portelIDs;
	}

	@RequestMapping("/saveOpenPageWhenLogining")
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public void saveOpenPageWhenLogining() {
		String personID = this.getContext().getPersonID();
		SqlSession session = DBUtils.getSession("system");
		try {
			Map mapRe = session.selectMap("selectPortelSettingByPersonID", personID, "SPORTALTYPE");
			String[] type = null;
			if (portelIDs != null && !"".equals(portelIDs)) {
				type = portelIDs.split(",");
			}
			PortelsettingEntity pe = null;
			PortelsettingEntity peNew = new PortelsettingEntity();
			peNew.setSPERSONID(personID);
			peNew.setSISOPENWHENLONGING((short) 1);
			if (type != null) {
				for (int i = 0; i < type.length; i++) {
					if (mapRe.containsKey(type[i])) {
						// 更新配置文件。SISOPENWHENLONGING 变为1；更新顺序
						pe = (PortelsettingEntity) mapRe.get(type[i]);
						pe.setSISOPENWHENLONGING((short) 1);
						pe.setSORDER(i);
						session.update("updatePSByEntity", pe);
						mapRe.remove(type[i]);
					} else {
						// 插入配置文件
						peNew.setSID(UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""));
						peNew.setSORDER(i);
						peNew.setSPORTALTYPE(type[i]);
						session.insert("insertPSByEntity", peNew);
					}
				}
			}
			// 更新不存在的记录.更新显示SISOPENWHENLONGING为0；
			String reType = null;
			for (Iterator it = mapRe.keySet().iterator(); it.hasNext();) {
				reType = (String) it.next();
				pe = (PortelsettingEntity) mapRe.get(reType);
				pe.setSISOPENWHENLONGING((short) 0);
				session.update("updatePSByEntity", pe);
			}
			session.commit();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		} finally {
			session.close();
		}
	}
}
