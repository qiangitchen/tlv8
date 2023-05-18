package com.tlv8.system.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tlv8.base.spring.SpringUtils;
import com.tlv8.base.utils.IDUtils;
import com.tlv8.system.action.FunctreeControl;
import com.tlv8.system.help.Configuration;
import com.tlv8.system.help.SessionHelper;
import com.tlv8.system.service.TokenService;
import com.tlv8.system.validator.ValidationError;

/**
 * 当前登录人相关信息
 * 
 * @author chenqian
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ContextBean implements Serializable{
	private static final long serialVersionUID = -1829210822129794715L;
	private Boolean isLogin = Boolean.valueOf(false);
	private String uiServerURL;
	private String businessServerURL;
	private String uiServerRemoteURL;
	private String businessServerRemoteURL;
	private String businessID;
	private String sessionID;
	private String onceFunc;
	private String username;
	private String password;
	private String agent;
	private boolean isAgent;
	private boolean isNTLogin;
	private String loginDate;
	private String ip;
	private String personID;
	private String personName;
	private String personCode;
	private String orgID;
	private String orgName;
	private String orgPath;
	private String agentPersonID;
	private String agentPersonName;
	private String agentPersonCode;
	private String agentOrgID;
	private String agentOrgName;
	private String agentOrgPath;
	private String allMemberOfOrgFullID;
	private String currentActivityLabel;
	private String currentProcessLabel;
	private String currentAgentFullName;
	private String currentAgentName;
	private String currentPositionFullCode;
	private String currentPositionCode;
	private String currentPositionName;
	private String currentPositionFullName;
	private String currentPositionFullID;
	private String currentPositionID;
	private String currentDeptFullCode;
	private String currentDeptCode;
	private String currentDeptName;
	private String currentDeptFullName;
	private String currentDeptFullID;
	private String currentDeptID;
	private String currentPersonFullCode;
	private String currentPersonFullName;
	private String currentPersonFullID;
	private String currentPersonName;
	private String currentPersonCode;
	private String currentPersonID;
	private String currentUserFullCode;
	private String currentUserFullName;
	private String currentUserFullID;
	private String currentUserCode;
	private String currentUserName;
	private String currentUserID;
	private String currentOgnFullCode;
	private String currentOgnFullID;
	private String currentOgnFullName;
	private String currentOgnCode;
	private String currentOgnName;
	private String currentOgnID;
	private String currentOrgFullCode;
	private String currentOrgFullName;
	private String currentOrgFullID;
	private String currentOrgCode;
	private String currentOrgName;
	private String currentOrgID;
	private String loginID;
	
	/**
	 * 用户唯一标识
	 */
	private String token;

	private long loginTime;

	private long expireTime;

	/**
	 * 根据request获取
	 * 
	 * @param request
	 * @return
	 */
	public static ContextBean getContext(HttpServletRequest request) {
		ContextBean contextBean = null;
		try {
			TokenService tokenService = SpringUtils.getBean(TokenService.class);
			contextBean = tokenService.getContextBean(request);
		} catch (Exception e) {
			contextBean = new ContextBean();
		}
		if (contextBean == null) {
			contextBean = new ContextBean();
		}
		return contextBean;
	}

	private String getLocalFix(HttpServletRequest request, String path) {
		String[] s = request.getRequestURL().toString().split("/");
		if (s.length >= 3) {
			return s[0] + "/" + s[1] + "/" + s[2] + "/" + path;
		}
		return "http://" + request.getLocalAddr() + ":" + String.valueOf(request.getLocalPort()) + "/" + path;
	}

	private String getUIURLPath(String pathKey, String pathDefault) {
		String path = (pathKey != null) && (!pathKey.equals("")) ? Configuration.getUIURLPath(pathKey) : "";
		if ((path == null) || (path.equals("")))
			path = pathDefault != null ? pathDefault : "";
		return path;
	}

	private String getBusinessURLPath(String pathKey, String pathDefault) {
		String path = (pathKey != null) && (!pathKey.equals("")) ? Configuration.getBusinessURLPath(pathKey) : "";
		if ((path == null) || (path.equals("")))
			path = pathDefault != null ? pathDefault : "";
		return path;
	}

	public void validate(HttpServletRequest request, List<ValidationError> validationErrors) {
		String version = request.getParameter("jpolite_key_req_version");
		if ((version == null) || (!version.equals("0.1")))
			validationErrors.add(new ValidationError("system.ContextBean.validate.1", null,
					"jpolite.res.system.ContextBean.validate.1"));
	}

	public void initLoginContext(HttpServletRequest request, HashMap<String, String> params) {
		SessionHelper.setUsername(request, (String) params.get("personID"));
		this.sessionID = request.getSession().getId();
		SessionHelper.setSessionID(request, this.sessionID);
		this.isLogin = Boolean.valueOf(true);
		this.username = ((String) params.get("username"));
		this.personID = ((String) params.get("personID"));
		this.personName = ((String) params.get("personName"));
		this.personCode = ((String) params.get("personCode"));
		this.orgID = ((String) params.get("orgID"));
		this.orgName = ((String) params.get("orgName"));
		this.orgPath = ((String) params.get("orgPath"));
		this.agentPersonID = ((String) params.get("agentPersonID"));
		this.agentPersonName = ((String) params.get("agentPersonName"));
		this.agentPersonCode = ((String) params.get("agentPersonCode"));
		this.agentOrgID = ((String) params.get("agentOrgID"));
		this.agentOrgName = ((String) params.get("agentOrgName"));
		this.agentOrgPath = ((String) params.get("agentOrgPath"));

		this.allMemberOfOrgFullID = ((String) params.get("allMemberOfOrgFullID"));
		this.currentActivityLabel = ((String) params.get("currentActivityLabel"));
		this.currentProcessLabel = ((String) params.get("currentProcessLabel"));
		this.currentAgentFullName = ((String) params.get("currentAgentFullName"));
		this.currentAgentName = ((String) params.get("currentAgentName"));
		this.currentPositionFullCode = ((String) params.get("currentPositionFullCode"));
		this.currentPositionCode = ((String) params.get("currentPositionCode"));
		this.currentPositionName = ((String) params.get("currentPositionName"));
		this.currentPositionFullName = ((String) params.get("currentPositionFullName"));
		this.currentPositionFullID = ((String) params.get("currentPositionFullID"));
		this.currentPositionID = ((String) params.get("currentPositionID"));
		this.currentDeptFullCode = ((String) params.get("currentDeptFullCode"));
		this.currentDeptCode = ((String) params.get("currentDeptCode"));
		this.currentDeptName = ((String) params.get("currentDeptName"));
		this.currentDeptFullName = ((String) params.get("currentDeptFullName"));
		this.currentDeptFullID = ((String) params.get("currentDeptFullID"));
		this.currentDeptID = ((String) params.get("currentDeptID"));
		this.currentPersonFullCode = ((String) params.get("currentPersonFullCode"));
		this.currentPersonFullName = ((String) params.get("currentPersonFullName"));
		this.currentPersonFullID = ((String) params.get("currentPersonFullID"));
		this.currentPersonName = ((String) params.get("currentPersonName"));
		this.currentPersonCode = ((String) params.get("currentPersonCode"));
		this.currentPersonID = ((String) params.get("currentPersonID"));
		this.currentUserFullCode = ((String) params.get("currentUserFullCode"));
		this.currentUserFullName = ((String) params.get("currentUserFullName"));
		this.currentUserFullID = ((String) params.get("currentUserFullID"));
		this.currentUserCode = ((String) params.get("currentUserCode"));
		this.currentUserName = ((String) params.get("currentUserName"));
		this.currentUserID = ((String) params.get("currentUserID"));
		this.currentOgnFullCode = ((String) params.get("currentOgnFullCode"));
		this.currentOgnFullID = ((String) params.get("currentOgnFullID"));
		this.currentOgnFullName = ((String) params.get("currentOgnFullName"));
		this.currentOgnCode = ((String) params.get("currentOgnCode"));
		this.currentOgnName = ((String) params.get("currentOgnName"));
		this.currentOgnID = ((String) params.get("currentOgnID"));
		this.currentOrgFullCode = ((String) params.get("currentOrgFullCode"));
		this.currentOrgFullName = ((String) params.get("currentOrgFullName"));
		this.currentOrgFullID = ((String) params.get("currentOrgFullID"));
		this.currentOrgCode = ((String) params.get("currentOrgCode"));
		this.currentOrgName = ((String) params.get("currentOrgName"));
		this.currentOrgID = ((String) params.get("currentOrgID"));
		this.loginDate = ((String) params.get("loginDate"));

		if (this.currentPersonFullID != null) {
			String[] keys = this.currentPersonFullID.split("/");
			for (int i = keys.length - 1; i >= 0; i--) {
				String locale = Configuration.getLocale(keys[i].replaceAll("\\..*", ""));
				if ((locale != null) && (!locale.equals(""))) {
					setLocale(request, locale);
					break;
				}
			}
		}
		this.loginID = IDUtils.getGUID();
		SessionHelper.setContext(request, this);
		FunctreeControl.initData(currentPersonFullID, personID);
	}

	public void initLogoutContext(HttpServletRequest request) {
		this.isLogin = Boolean.valueOf(false);
		SessionHelper.invalidate(request);
	}

	public Boolean hasBusinessSession() {
		return Boolean.valueOf((this.businessID != null) && (!this.businessID.trim().equals("")));
	}

	public Boolean isAgent() {
		return Boolean.valueOf((this.agentPersonID != null) && (!this.agentPersonID.trim().equals("")));
	}

	public Boolean isLogin() {
		// try {
		// LoginSetting.checkLog(OnlineHelper.getOnlineCount());
		// } catch (Exception e) {
		// return false;
		// }
		return this.isLogin;
	}

	public String getBusinessServerURL(HttpServletRequest request, String pathKey, String pathDefault) {
		return ((this.businessServerURL == null) || (this.businessServerURL.equals(""))
				? Configuration.getBusinessServerURL(getLocalFix(request, "BusinessServer"))
				: this.businessServerURL) + getBusinessURLPath(pathKey, pathDefault);
	}

	public String getBusinessServerRemoteURL(HttpServletRequest request, String pathKey, String pathDefault) {
		return ((this.businessServerRemoteURL == null) || (this.businessServerRemoteURL.equals(""))
				? Configuration.getBusinessServerRemoteURL(getLocalFix(request, "BusinessServer"))
				: this.businessServerRemoteURL) + getBusinessURLPath(pathKey, pathDefault);
	}

	public void setBusinessServerURL(String url) {
		this.businessServerURL = url;
	}

	public void setBusinessServerRemoteURL(String url) {
		this.businessServerRemoteURL = url;
	}

	public String getUIServerURL(HttpServletRequest request, String pathKey, String pathDefault) {
		return ((this.uiServerURL == null) || (this.uiServerURL.equals(""))
				? Configuration.getUIServerURL(getLocalFix(request, "UIServer"))
				: this.uiServerURL) + getUIURLPath(pathKey, pathDefault);
	}

	public String getUIServerRemoteURL(HttpServletRequest request, String pathKey, String pathDefault) {
		return ((this.uiServerRemoteURL == null) || (this.uiServerRemoteURL.equals(""))
				? Configuration.getUIServerRemoteURL(getLocalFix(request, "UIServer"))
				: this.uiServerRemoteURL) + getUIURLPath(pathKey, pathDefault);
	}

	public void setUIServerURL(String url) {
		this.uiServerURL = url;
	}

	public void setUIServerRemoteURL(String url) {
		this.uiServerRemoteURL = url;
	}

	public String getLocale(HttpServletRequest request) {
		return SessionHelper.getLocale(request);
	}

	public void setLocale(HttpServletRequest request, String language) {
		SessionHelper.setLocale(request, language);
	}

	public String getBusinessID() {
		return this.businessID;
	}

	public void setBusinessID(String businessID) {
		this.businessID = businessID;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPersonID() {
		return this.personID;
	}

	public String getPersonName() {
		return this.personName;
	}

	public String getPersonCode() {
		return this.personCode;
	}

	public String getOrgID() {
		return this.orgID;
	}

	public String getOrgName() {
		return this.orgName;
	}

	public String getOrgPath() {
		return this.orgPath;
	}

	public String getAgentPersonID() {
		return this.agentPersonID;
	}

	public String getAgentPersonName() {
		return this.agentPersonName;
	}

	public String getAgentPersonCode() {
		return this.agentPersonCode;
	}

	public String getAgentOrgID() {
		return this.agentOrgID;
	}

	public String getAgentOrgName() {
		return this.agentOrgName;
	}

	public String getAgentOrgPath() {
		return this.agentOrgPath;
	}

	public String getOnceFunc() {
		String s = this.onceFunc;
		this.onceFunc = null;
		return s;
	}

	public void setOnceFunc(String onceFunc) {
		this.onceFunc = onceFunc;
	}

	public String getAllMemberOfOrgFullID() {
		return this.allMemberOfOrgFullID;
	}

	public String getCurrentActivityLabel() {
		return this.currentActivityLabel;
	}

	public String getCurrentProcessLabel() {
		return this.currentProcessLabel;
	}

	public String getCurrentAgentFullName() {
		return this.currentAgentFullName;
	}

	public String getCurrentAgentName() {
		return this.currentAgentName;
	}

	public String getCurrentPositionFullCode() {
		return this.currentPositionFullCode;
	}

	public String getCurrentPositionCode() {
		return this.currentPositionCode;
	}

	public String getCurrentPositionName() {
		return this.currentPositionName;
	}

	public String getCurrentPositionFullName() {
		return this.currentPositionFullName;
	}

	public String getCurrentPositionFullID() {
		return this.currentPositionFullID;
	}

	public String getCurrentPositionID() {
		return this.currentPositionID;
	}

	public String getCurrentDeptFullCode() {
		return this.currentDeptFullCode;
	}

	public String getCurrentDeptCode() {
		return this.currentDeptCode;
	}

	public String getCurrentDeptName() {
		return this.currentDeptName;
	}

	public String getCurrentDeptFullName() {
		return this.currentDeptFullName;
	}

	public String getCurrentDeptFullID() {
		return this.currentDeptFullID;
	}

	public String getCurrentDeptID() {
		return this.currentDeptID;
	}

	public String getCurrentPersonFullCode() {
		return this.currentPersonFullCode;
	}

	public String getCurrentPersonFullName() {
		return this.currentPersonFullName;
	}

	public String getCurrentPersonFullID() {
		return this.currentPersonFullID;
	}

	public String getCurrentPersonName() {
		return this.currentPersonName;
	}

	public String getCurrentPersonCode() {
		return this.currentPersonCode;
	}

	public String getCurrentPersonID() {
		return this.currentPersonID;
	}

	public String getCurrentUserFullCode() {
		return this.currentUserFullCode;
	}

	public String getCurrentUserFullName() {
		return this.currentUserFullName;
	}

	public String getCurrentUserFullID() {
		return this.currentUserFullID;
	}

	public String getCurrentUserCode() {
		return this.currentUserCode;
	}

	public String getCurrentUserName() {
		return this.currentUserName;
	}

	public String getCurrentUserID() {
		return this.currentUserID;
	}

	public String getCurrentOgnFullCode() {
		return this.currentOgnFullCode;
	}

	public String getCurrentOgnFullID() {
		return this.currentOgnFullID;
	}

	public String getCurrentOgnFullName() {
		return this.currentOgnFullName;
	}

	public String getCurrentOgnCode() {
		return this.currentOgnCode;
	}

	public String getCurrentOgnName() {
		return this.currentOgnName;
	}

	public String getCurrentOgnID() {
		return this.currentOgnID;
	}

	public String getCurrentOrgFullCode() {
		return this.currentOrgFullCode;
	}

	public String getCurrentOrgFullName() {
		return this.currentOrgFullName;
	}

	public String getCurrentOrgFullID() {
		return this.currentOrgFullID;
	}

	public String getCurrentOrgCode() {
		return this.currentOrgCode;
	}

	public String getCurrentOrgName() {
		return this.currentOrgName;
	}

	public String getCurrentOrgID() {
		return this.currentOrgID;
	}

	public String getLoginDate() {
		return this.loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getAgent() {
		return agent;
	}

	public Boolean getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}

	public boolean getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(boolean isAgent2) {
		this.isAgent = isAgent2;
	}

	public boolean getIsNTLogin() {
		return isNTLogin;
	}

	public void setIsNTLogin(boolean isNTLogin) {
		this.isNTLogin = isNTLogin;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Map toMap() throws Exception {
		Map json = new HashMap();
		json.put("username", this.username);
		json.put("personID", this.personID);
		json.put("personName", this.personName);
		json.put("personCode", this.personCode);
		json.put("orgID", this.orgID);
		json.put("orgName", this.orgName);
		json.put("orgPath", this.orgPath);
		json.put("agentPersonID", this.agentPersonID);
		json.put("agentPersonName", this.agentPersonName);
		json.put("agentPersonCode", this.agentPersonCode);
		json.put("agentOrgID", this.agentOrgID);
		json.put("agentOrgName", this.agentOrgName);
		json.put("agentOrgPath", this.agentOrgPath);

		json.put("allMemberOfOrgFullID", this.allMemberOfOrgFullID);
		json.put("currentActivityLabel", this.currentActivityLabel);
		json.put("currentProcessLabel", this.currentProcessLabel);
		json.put("currentAgentFullName", this.currentAgentFullName);
		json.put("currentAgentName", this.currentAgentName);
		json.put("currentPositionFullCode", this.currentPositionFullCode);
		json.put("currentPositionCode", this.currentPositionCode);
		json.put("currentPositionName", this.currentPositionName);
		json.put("currentPositionFullName", this.currentPositionFullName);
		json.put("currentPositionFullID", this.currentPositionFullID);
		json.put("currentPositionID", this.currentPositionID);
		json.put("currentDeptFullCode", this.currentDeptFullCode);
		json.put("currentDeptCode", this.currentDeptCode);
		json.put("currentDeptName", this.currentDeptName);
		json.put("currentDeptFullName", this.currentDeptFullName);
		json.put("currentDeptFullID", this.currentDeptFullID);
		json.put("currentDeptID", this.currentDeptID);
		json.put("currentPersonFullCode", this.currentPersonFullCode);
		json.put("currentPersonFullName", this.currentPersonFullName);
		json.put("currentPersonFullID", this.currentPersonFullID);
		json.put("currentPersonName", this.currentPersonName);
		json.put("currentPersonCode", this.currentPersonCode);
		json.put("currentPersonID", this.currentPersonID);
		json.put("currentUserFullCode", this.currentUserFullCode);
		json.put("currentUserFullName", this.currentUserFullName);
		json.put("currentUserFullID", this.currentUserFullID);
		json.put("currentUserCode", this.currentUserCode);
		json.put("currentUserName", this.currentUserName);
		json.put("currentUserID", this.currentUserID);
		json.put("currentOgnFullCode", this.currentOgnFullCode);
		json.put("currentOgnFullID", this.currentOgnFullID);
		json.put("currentOgnFullName", this.currentOgnFullName);
		json.put("currentOgnCode", this.currentOgnCode);
		json.put("currentOgnName", this.currentOgnName);
		json.put("currentOgnID", this.currentOgnID);
		json.put("currentOrgFullCode", this.currentOrgFullCode);
		json.put("currentOrgFullName", this.currentOrgFullName);
		json.put("currentOrgFullID", this.currentOrgFullID);
		json.put("currentOrgCode", this.currentOrgCode);
		json.put("currentOrgName", this.currentOrgName);
		json.put("currentOrgID", this.currentOrgID);
		json.put("loginDate", this.loginDate);

		json.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		json.put("token", token);

		return json;
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

}
