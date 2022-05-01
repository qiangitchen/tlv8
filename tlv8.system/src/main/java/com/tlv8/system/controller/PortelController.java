package com.tlv8.system.controller;

import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tlv8.base.db.DBUtils;
import com.tlv8.system.BaseController;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.help.ResponseProcessor;
import com.tlv8.system.portelSetting.PortelsettingEntity;

/**
 * portal操作相关
 * 
 * @author chenqian
 */
@Controller
@Scope("prototype")
@RequestMapping("/system/Portal")
public class PortelController extends BaseController {
	private String themeType = null;
	private String deskJson = null;
	private String portalId = null;

	final String profiles_select = "SELECT SLAYOUTSET,SPANLES FROM SA_PORTALLETS WHERE SPERSONID = ? ";
	final String profiles_insert = "INSERT INTO SA_PORTALLETS (SLAYOUTSET,SPANLES,SCREATORID,SCREATORNAME,SCREATEDATE,SPERSONID) VALUES (?, ?, ?, ?, ?, ?)";
	final String profiles_update = "UPDATE SA_PORTALLETS SET SLAYOUTSET = ?,SPANLES=?, SCREATORID=?, SCREATORNAME=?, SCREATEDATE=? WHERE SPERSONID = ? ";
	final String profiles_delete = "DELETE FROM SA_PORTALLETS WHERE SPERSONID = ? ";

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getDeskJson() {
		return deskJson;
	}

	public void setDeskJson(String deskJson) {
		this.deskJson = deskJson;
	}

	public void setThemeType(String themeType) {
		this.themeType = themeType;
	}

	public String getThemeType() {
		return themeType;
	}

	@ResponseBody
	@RequestMapping("/themeType")
	@SuppressWarnings({ "resource", "deprecation" })
	public void obtainThemeType() {
		String personID = this.getContext().getPersonID();
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = "";
		try {
			cn = DBUtils.getAppConn("system");
			String selectSQL = "select STHEMETYPE from SA_PORTELINFO WHERE SPERSONID=?";
			ps = cn.prepareStatement(selectSQL);
			ps.setString(1, personID);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				result = "{\"themeType\":\"" + rs.getString("STHEMETYPE") + "\"}";
			} else {
				result = "{\"themeType\":\"old\"}";
				String insertSQL = "insert into SA_PORTELINFO(SID,STHEMETYPE,SPERSONID) VALUES(?,'old',?)";
				ps = cn.prepareStatement(insertSQL);
				String id = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
				ps.setString(1, id);
				ps.setString(2, personID);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			this.renderData(false, "{\"msg\":\"" + e.getMessage() + "\"}");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		this.renderData(true, result);
	}

	@ResponseBody
	@RequestMapping("/obtainPsnDeskInfo")
	@SuppressWarnings("rawtypes")
	public void obtainPsnDeskInfo() {
		String personID = this.getContext().getPersonID();
		Map mapRe = null;
		PortelsettingEntity pe = null;
		StringBuffer sb = new StringBuffer();
		SqlSession session = DBUtils.getSession("system");
		try {
			Map<String, String> conditonMap = new HashMap<String, String>();
			conditonMap.put("personId", personID);
			conditonMap.put("isOpen", "1");
			mapRe = session.selectMap("selectPortelSettingFromIndex", conditonMap, "SPORTALTYPE");
			String reType = null, infor = null;
			if (mapRe.size() == 0) {
				// 打开默认的个人桌面.这里个人桌面id为p7。只能固定了。
				conditonMap.remove("isOpen");
				conditonMap.put("type", "p7");
				mapRe = session.selectMap("selectPortelSettingFromIndex", conditonMap, "SPORTALTYPE");
				if (mapRe.size() == 0) {// 插入一条数据
					pe = new PortelsettingEntity();
					pe.setSID(UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""));
					pe.setSPERSONID(personID);
					pe.setSORDER(0);
					infor = "\"order\":[\"screen_1\"],\"manIndex\":1,\"psnScreen\":{\"screen_1\":[]}";
					pe.setSPORTALTYPE("p7");
					pe.setSPSNDESKINFO(infor);
					session.insert("insertPSByEntity", pe);
					sb.append("p7:{" + infor + "},");
				} else {// 更新数据
					pe = (PortelsettingEntity) mapRe.get("p7");
					infor = pe.getSPSNDESKINFO();
					if (infor == null) {
						infor = "";
					}
					sb.append("p7:{" + infor + "},");
					pe.setSORDER(0);
					pe.setSISOPENWHENLONGING((short) 0);
					session.update("updatePSByEntity", pe);
				}
			} else {
				// 把查询结果传到前台；
				for (Iterator it = mapRe.keySet().iterator(); it.hasNext();) {
					reType = (String) it.next();
					pe = (PortelsettingEntity) mapRe.get(reType);
					infor = pe.getSPSNDESKINFO();
					if (infor == null) {
						infor = "";
					}
					sb.append(reType + ":{" + infor + "},");
				}
			}

		} catch (Exception e) {
			this.renderData(false, "{\"msg\":\"" + e.getMessage() + "\"}");
		} finally {
			session.close();
		}
		// System.out.println(sb.substring(0, sb.length() - 1));
		this.renderData(true, sb.substring(0, sb.length() - 1));
	}

	@ResponseBody
	@RequestMapping("/saveThemeType")
	@SuppressWarnings("deprecation")
	public void saveThemeType() {
		String personID = this.getContext().getPersonID();
		// System.out.println("themeType:" +
		// this.themeType+"personID:"+personID);
		Connection cn = null;
		PreparedStatement ps = null;
		@SuppressWarnings("unused")
		int rs = -1;
		String result = "";
		try {
			cn = DBUtils.getAppConn("system");
			String selectSQL = "update SA_PORTELINFO set STHEMETYPE=? WHERE SPERSONID=?";
			ps = cn.prepareStatement(selectSQL);
			ps.setString(1, this.themeType);
			ps.setString(2, personID);
			rs = ps.executeUpdate();
			// System.out.println(rs+"--"+"update SA_PORTELINFO set
			// STHEMETYPE='"+this.themeType+"' WHERE SPERSONID='"+personID+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.renderData(false, "{\"msg\":\"" + e.getMessage() + "\"}");
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		this.renderData(true, result);
	}

	@ResponseBody
	@RequestMapping("/saveDeskJson")
	@SuppressWarnings("deprecation")
	public void saveDeskJson() {
		String personID = this.getContext().getPersonID();
		Connection cn = null;
		PreparedStatement ps = null;
		@SuppressWarnings("unused")
		int rs = -1;
		String result = "";
		try {
			cn = DBUtils.getAppConn("system");
			String selectSQL = "update sa_portelsetting set SPSNDESKINFO=? WHERE SPERSONID=? and SPORTALTYPE=?";
			ps = cn.prepareStatement(selectSQL);
			ps.setString(1, this.deskJson);
			ps.setString(2, personID);
			ps.setString(3, this.portalId);
			rs = ps.executeUpdate();
			// System.out.println(rs+"--"+"update SA_PORTELINFO set
			// STHEMETYPE='"+this.themeType+"' WHERE SPERSONID='"+personID+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.renderData(false, "{\"msg\":\"" + e.getMessage() + "\"}");
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		this.renderData(true, result);
	}

	@ResponseBody
	@RequestMapping("/savePortal")
	@SuppressWarnings("resource")
	public void save(String layout, String panles) {
		try {
			panles = URLDecoder.decode(panles, "UTF-8");
		} catch (Exception e) {
		}
		String personID = this.getContext().getPersonID();
		ContextBean contexts = this.getContext();
		SqlSession session = DBUtils.getSession("system");
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = session.getConnection();
			try {
				cn.setAutoCommit(false);
				ps = cn.prepareStatement(profiles_select);
				ps.setString(1, personID);
				rs = ps.executeQuery();
				ps = cn.prepareStatement(rs.next() ? profiles_update : profiles_insert);
				ps.setString(1, layout);
				ps.setString(2, panles);
				ps.setString(3, contexts.getCurrentPersonID());
				ps.setString(4, contexts.getCurrentPersonName());
				ps.setDate(5, new java.sql.Date(new Date().getTime()));
				ps.setString(6, personID);
				ps.executeUpdate();
				cn.commit();
			} catch (Exception e) {
				cn.rollback();
				e.printStackTrace();
			} finally {
				cn.setAutoCommit(true);
			}
			this.renderData(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, cn, ps, null);
		}
	}

	@ResponseBody
	@RequestMapping("/loadPortal")
	public void load() {
		String personID = this.getContext().getPersonID();
		try {
			List<Map<String, String>> li = DBUtils.execQueryforList("system",
					profiles_select.replace("?", "'" + personID + "'"));
			ResponseProcessor.renderText(this.response, JSON.toJSONString(li));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping("/deletePortal")
	public void delete() {
		String personID = this.getContext().getPersonID();
		try {
			DBUtils.execdeleteQuery("system", profiles_delete.replace("?", "'" + personID + "'"));
			this.renderData(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
