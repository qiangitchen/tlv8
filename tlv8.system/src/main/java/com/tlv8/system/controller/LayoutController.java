package com.tlv8.system.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@RequestMapping("/system/Layout")
public class LayoutController extends BaseController {
	final String profiles_select = "SELECT SVALUE FROM SA_PORTALPROFILES WHERE SNAME = ? AND SPERSONID = ? ";
	final String profiles_insert = "INSERT INTO SA_PORTALPROFILES (SID, SVALUE, SNAME, SPERSONID) VALUES ('"
			+ UUID.randomUUID().toString().toUpperCase().replace("-", "") + "',?, ?, ?)";
	final String profiles_update = "UPDATE SA_PORTALPROFILES SET SVALUE = ? WHERE SNAME = ? AND SPERSONID = ? ";
	final String profiles_delete = "DELETE FROM SA_PORTALPROFILES WHERE SNAME = ? AND SPERSONID = ? ";
	final String profiles_key_tabs = "TABS";
	final String profiles_key_theme = "THEME";
	final String profiles_key_shortcuts = "SHORTCUTS";
	final String profiles_key_customFuncTree = "CUSTOMFUNCTREE";
	
	private void save(String key, String value) {
		String personID = this.getContext().getPersonID();
		SqlSession session = DBUtils.getSession("system");
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			cn = session.getConnection();
			Boolean autoCommit = cn.getAutoCommit();
			try {
				cn.setAutoCommit(false);
				ps = cn.prepareStatement(profiles_select);
				ps.setString(1, key);
				ps.setString(2, personID);
				rs = ps.executeQuery();
				PreparedStatement ps1 = cn.prepareStatement(rs.next() ? profiles_update : profiles_insert);
				ps1.setString(1, value);
				ps1.setString(2, key);
				ps1.setString(3, personID);
				ps1.executeUpdate();
				cn.commit();
			} catch (Exception e) {
				cn.rollback();
				e.printStackTrace();
			} finally {
				cn.setAutoCommit(autoCommit);
			}

			this.renderData(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, cn, ps, null);
		}
	}

	private void load(String key) {
		String personID = this.getContext().getPersonID();
		SqlSession session = DBUtils.getSession("system");
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = session.getConnection();
			ps = cn.prepareStatement(profiles_select);
			ps.setString(1, key);
			ps.setString(2, personID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				this.renderData(true, rs.getString(1));
			} else {
				this.renderData(false);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, cn, ps, null);
		}

	}

	private void remove(String key) {
		String personID = this.getContext().getPersonID();
		SqlSession session = DBUtils.getSession("system");
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = session.getConnection();
			ps = cn.prepareStatement(profiles_delete);
			ps.setString(1, key);
			ps.setString(2, personID);
			ps.executeUpdate();
			this.renderData(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, cn, ps, null);
		}
	}

	@RequestMapping("/loadLayout")
	@ResponseBody
	public void loadLayout() {
		String personID = this.getContext().getPersonID();
		SqlSession session = DBUtils.getSession("system");
		Connection cn = null;
		Statement st = null;
		try {
			String sql = "select t.sthemelayoutinfo from sa_themedefine t where t.screatorid = '" + personID
					+ "' and t.sThemeActivity = 'activity'";
			cn = session.getConnection();
			st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				this.renderData(true, rs.getString(1));
			} else {
				this.renderData(false);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.CloseConn(session, cn, st, null);
		}
	}

	@RequestMapping("/saveTabs")
	@ResponseBody
	public void saveTabs() {
		save(profiles_key_tabs, this.p("layout"));
	}

	@RequestMapping("/loadTabs")
	@ResponseBody
	public void loadTabs() {
		load(profiles_key_tabs);
	}

	@RequestMapping("/removeTabs")
	@ResponseBody
	public void removeTabs() {
		remove(profiles_key_tabs);
	}

	@RequestMapping("/saveTheme")
	@ResponseBody
	public void saveTheme() {
		save(profiles_key_theme, this.p("theme"));
	}

	@RequestMapping("/loadTheme")
	@ResponseBody
	public void loadTheme() {
		load(profiles_key_theme);
	}

	@RequestMapping("/removeTheme")
	@ResponseBody
	public void removeTheme() {
		remove(profiles_key_theme);
	}

	@RequestMapping("/saveShortcuts")
	@ResponseBody
	public void saveShortcuts() {
		save(profiles_key_shortcuts, this.p("shortcuts"));
	}

	@RequestMapping("/loadShortcuts")
	@ResponseBody
	public void loadShortcuts() {
		load(profiles_key_shortcuts);
	}

	@RequestMapping("/removeShortcuts")
	@ResponseBody
	public void removeShortcuts() {
		remove(profiles_key_shortcuts);
	}

	@RequestMapping("/saveCustomFuncTree")
	@ResponseBody
	public void saveCustomFuncTree() {
		save(profiles_key_customFuncTree, this.p("customFuncTree"));
	}

	@RequestMapping("/loadCustomFuncTree")
	@ResponseBody
	public void loadCustomFuncTree() {
		load(profiles_key_customFuncTree);
	}

	@RequestMapping("/removeCustomFuncTree")
	@ResponseBody
	public void removeCustomFuncTree() {
		remove(profiles_key_customFuncTree);
	}
}
