package com.tlv8.system.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

import javax.naming.NamingException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tlv8.base.Sys;
import com.tlv8.system.BaseController;
import com.tlv8.system.action.Agents;
import com.tlv8.system.action.Login;
import com.tlv8.system.action.Pwd;
import com.tlv8.system.action.Signm;
import com.tlv8.system.action.WriteLoginLog;
import com.tlv8.system.bean.ContextBean;
import com.tlv8.system.help.Configuration;
import com.tlv8.system.help.SessionHelper;
import com.tlv8.system.online.InitOnlineInfoAction;
import com.tlv8.system.online.OnlineUtils;

import cn.dev33.satoken.stp.StpUtil;

@Controller
@Scope("prototype")
@RequestMapping("/system/User")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserController extends BaseController {

	@ResponseBody
	@RequestMapping("/login")
	public void login() throws DocumentException, HttpException, IOException {
		String username = p("username");
		String password = p("password");
		String loginDate = p("loginDate");
		String language = p("language");
		String logintype = (p("logintype") != null ? p("logintype") : "mobile");

		String captcha = p("captcha");
		String agent = p("agent");
		String onceFunc = p("onceFunc");
		String mode = p("mode");
		String ip = p("ip");

		String msg = "";
		String token = "";

		boolean isAgent = ((agent != null) && (!agent.equals("")));
		boolean isNTLogin = ((mode != null) && (mode.equals("nt")));
		if ((isNTLogin) && ((ip == null) || (ip.equals("")))) {
			Object ipAttribute = a("ip");
			ip = (ipAttribute != null ? ipAttribute.toString() : getRemoteAddr(this.request));
		}
		if ((ip == null) || ("".equals(ip))) {
			ip = getRemoteAddr(this.request);
		}
		if (captcha == null) {
			captcha = "";
		}
		try {
			Configuration.processConfiguration(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ((!"no_captcha".equals(captcha)) && (Configuration.getCaptchaEnabled().booleanValue())) {
			if (captcha == "") {
				msg = "system.UserController.login.3:请输入验证码!";
				renderData(Boolean.valueOf(false), "{\"msg\":\"" + r(msg) + "\"}");
				return;
			}
			if (!captcha.equals(SessionHelper.getAttrString(this.request, "SESSION_SECURITY_CODE"))) {
				msg = "system.UserController.login.4:验证码错误！";
				renderData(Boolean.valueOf(false), "{\"msg\":\"" + r(msg) + "\"}");
				return;
			}
		}
		if ((loginDate == null) || (loginDate.equals(""))) {
			loginDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		ContextBean contextBean = getContext();
		contextBean.setLocale(this.request, language);
		String serverURL = Configuration.getUIServerURL(null);
		contextBean.setUIServerURL(serverURL);

		clearHttpClient();

		boolean success = false;
		try {
			HashMap<String, String> params = Login.doLogin(username, password, ip, logintype);
			getSysParams(this.request, params);
			contextBean.initLoginContext(this.request, params);
			contextBean.setOnceFunc(onceFunc);
			contextBean.setUsername(username);
			contextBean.setPassword(password);
			contextBean.setAgent(agent);
			contextBean.setIsAgent(isAgent);
			contextBean.setIsNTLogin(isNTLogin);
			contextBean.setLoginDate(loginDate);
			contextBean.setIp(ip);
			// 设置登录用户的ID
			StpUtil.login(contextBean.getPersonID());
			// 获取JWT Token
			token = StpUtil.getTokenValue();
			tokenService.refreshToken(contextBean);
			success = true;
			InitOnlineInfoAction.execute(contextBean);
			WriteLoginLog.write(contextBean.getCurrentUserID(), username, getRemoteAddr(this.request), password,
					this.request);
			String signm = p("signm");
			if ((signm != null) && (!"".equals(signm)))
				Signm.addSign(contextBean.getCurrentPersonID(), signm);
		} catch (Exception e) {
			success = false;
			msg = e.getMessage();
			e.printStackTrace();
		}
		if (!success) {
			clearHttpClient();
			contextBean.initLogoutContext(this.request);
		}
		System.out.println("token:" + token);
		renderData(Boolean.valueOf(success), "{\"msg\":\"" + r(msg) + "\",\"token\":\"" + token + "\"}");
	}

	@ResponseBody
	@RequestMapping("/MD5login")
	public void MD5login() throws DocumentException, HttpException, IOException {
		String username = p("username");
		String password = p("password");
		String loginDate = p("loginDate");
		String language = p("language");

		String captcha = p("captcha");
		String agent = p("agent");
		String onceFunc = p("onceFunc");
		String mode = p("mode");
		String ip = p("ip");
		String mobilestatus = "0";
		// String title = "jpolite.res.system.UserController.login.0";
		String msg = "";
		String token = "";
		String hxname = "";
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()) + " " + username + ":登录系统");
		boolean isAgent = ((agent != null) && (!agent.equals("")));
		boolean isNTLogin = ((mode != null) && (mode.equals("nt")));
		clearHttpClient();
		boolean success = false;
		try {
			Configuration.processConfiguration(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!"no_captcha".equals(captcha)) {
			if (captcha == "") {
				msg = "请输入验证码!";
				renderData(Boolean.valueOf(false), "{\"msg\":\"" + r(msg) + "\"}");
				return;
			}
			if (!captcha.equals(SessionHelper.getAttrString(this.request, "SESSION_SECURITY_CODE"))) {
				msg = "验证码错误！";
				renderData(Boolean.valueOf(false), "{\"msg\":\"" + r(msg) + "\"}");
				return;
			}
		}
		if ((isNTLogin) && ((ip == null) || (ip.equals("")))) {
			Object ipAttribute = a("ip");
			ip = (ipAttribute != null ? ipAttribute.toString() : getRemoteAddr(this.request));
		}
		if ((ip == null) || ("".equals(ip))) {
			ip = getRemoteAddr(this.request);
		}
		if ((loginDate == null) || (loginDate.equals(""))) {
			loginDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
		ContextBean contextBean = getContext();
		contextBean.setLocale(this.request, language);
		String serverURL = Configuration.getUIServerURL(null);
		contextBean.setUIServerURL(serverURL);

		try {
			HashMap<String, String> params = Login.MD5doLogin(username, password);
			getSysParams(this.request, params);
			contextBean.initLoginContext(this.request, params);
			contextBean.setOnceFunc(onceFunc);
			contextBean.setUsername(username);
			contextBean.setPassword(password);
			contextBean.setAgent(agent);
			contextBean.setIsAgent(isAgent);
			contextBean.setIsNTLogin(isNTLogin);
			contextBean.setLoginDate(loginDate);
			contextBean.setIp(ip);
			// 设置登录用户的ID
			StpUtil.login(contextBean.getPersonID());
			// 获取JWT Token
			token = StpUtil.getTokenValue();
			tokenService.refreshToken(contextBean);
			success = true;
			InitOnlineInfoAction.execute(contextBean);
			WriteLoginLog.write(contextBean.getCurrentUserID(), username, getRemoteAddr(this.request), password,
					this.request);
			String signm = p("signm");
			if ((signm != null) && (!"".equals(signm)))
				Signm.addSign(contextBean.getCurrentPersonID(), signm);
		} catch (Exception e) {
			success = false;
			msg = e.getMessage();
			System.out.println(e.getMessage());
		}
		if (!success) {
			clearHttpClient();
			contextBean.initLogoutContext(this.request);
		}
		System.out.println("token:" + token);
		renderData(Boolean.valueOf(success),
				"{\"msg\":\"" + r(msg) + "\",\"bsessionID\":\"" + contextBean.getToken() + "\",\"mobilestatus\":\""
						+ mobilestatus + "\",\"hxname\":\"" + hxname + "\",\"token\":\"" + token + "\"}");
	}

	@ResponseBody
	@RequestMapping({ "/sCALogin" })
	public void scalogin() {
		ContextBean contextBean = getContext();
		contextBean.setLocale(this.request, "zh_CN");
		String serverURL = Configuration.getUIServerURL(null);
		contextBean.setUIServerURL(serverURL);
		String msg = "";
		String token = "";
		clearHttpClient();
		String signm = p("signm");
		boolean success = false;
		try {
			HashMap params = Login.sCAdoLogin(signm);
			getSysParams(this.request, params);
			contextBean.initLoginContext(this.request, params);

			contextBean.setUsername((String) params.get("username"));

			contextBean.setIsAgent(false);
			contextBean.setIsNTLogin(false);
			contextBean.setLoginDate(p("loginDate"));
			contextBean.setIp(getRemoteAddr(this.request));
			// 设置登录用户的ID
			StpUtil.login(contextBean.getPersonID());
			// 获取JWT Token
			token = StpUtil.getTokenValue();
			tokenService.refreshToken(contextBean);
			success = true;
			InitOnlineInfoAction.execute(contextBean);
			WriteLoginLog.write(contextBean.getCurrentUserID(), (String) params.get("username"),
					getRemoteAddr(this.request), "", this.request);
		} catch (Exception e) {
			success = false;
			msg = e.getMessage();
			e.printStackTrace();
		}
		if (!success) {
			clearHttpClient();
			contextBean.initLogoutContext(this.request);
		}
		System.out.println("token:" + token);
		renderData(Boolean.valueOf(success), "{\"msg\":\"" + r(msg) + "\",\"token\":\"" + token + "\"}");
	}

	@ResponseBody
	@RequestMapping("/linkToX5")
	public void linkToX5() {
		Sys.printMsg("连接X5... ...");
		try {
			ContextBean contextBean = getContext();
			getBusinessServerSession(contextBean.getUsername(), contextBean.getAgent(), contextBean.getPassword(),
					contextBean.getIsAgent(), contextBean.getIsNTLogin(), contextBean.getLoginDate(),
					contextBean.getIp());
			Sys.printMsg("连接X5成功.");
		} catch (Exception e) {
			Sys.packErrMsg("连接X5异常");
		}
		renderData(Boolean.valueOf(true), "{\"msg\":\"success\"}");
	}

	@ResponseBody
	@RequestMapping("/setBusinessID")
	public void setBusinessID() {
		String bsessionID = p("bsessionid");
		Sys.printMsg("连接X5... ..." + bsessionID);
		this.request.getSession().setAttribute("jpolite_key_ses_bsessionid", bsessionID);
		renderData(Boolean.valueOf(true), "{\"msg\":\"success\"}");
	}

	@ResponseBody
	@RequestMapping("/logout")
	public void logout() throws HttpException, IOException, DocumentException {
		ContextBean contextBean = getContext();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()) + " 用户："
				+ contextBean.getPersonName() + " 退出系统.");
		OnlineUtils.deleteOnlie(contextBean.getToken());
		tokenService.deleteContextBean(contextBean.getToken());
		contextBean.initLogoutContext(this.request);
		renderData(Boolean.valueOf(true));
	}

	@ResponseBody
	@RequestMapping("/MD5logout")
	public void MD5logout() throws HttpException, IOException, DocumentException {
		ContextBean contextBean = getContext();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()) + " 用户："
				+ contextBean.getPersonName() + " 退出系统.");
		OnlineUtils.deleteOnlie(contextBean.getToken());
		tokenService.deleteContextBean(contextBean.getToken());
		contextBean.initLogoutContext(this.request);
		renderData(Boolean.valueOf(true));
	}

	@ResponseBody
	@RequestMapping("/check")
	public void check() throws Exception {
		ContextBean contextBean = getContext();
		renderData(contextBean.isLogin(), JSON.toJSONString(contextBean));
	}

	@ResponseBody
	@RequestMapping("/getAgents")
	public void getAgents() throws IOException, DocumentException {
		JSONObject json = new JSONObject();
		renderData(Boolean.valueOf(true), json.toString());
	}

	@ResponseBody
	@RequestMapping("/changePassword")
	public void changePassword() throws HttpException, IOException, DocumentException {
		String username = p("username");
		String password = p("password");
		String new_password = p("new_password");
		try {
			username = URLDecoder.decode(username, "UTF-8");
			password = URLDecoder.decode(password, "UTF-8");
			new_password = URLDecoder.decode(new_password, "UTF-8");
		} catch (Exception localException) {
		}
		Boolean success = Boolean.valueOf(false);
		String msg = "";
		ContextBean contextBean = getContext();
		if (((username == null) || (username.equals(""))) && (contextBean.isLogin().booleanValue())) {
			username = contextBean.getPersonCode();
		}
		if ((username != null) && (!username.equals(""))) {
			try {
				System.out.println(username + "修改密码，原密码:" + password + "新密码：" + new_password);
				Pwd.changePassword(username, password, new_password);
				msg = "修改成功！";
				success = Boolean.valueOf(true);
			} catch (NamingException e) {
				msg = r("系统异常，操作失败!");
				success = Boolean.valueOf(false);
			} catch (SQLException e) {
				success = Boolean.valueOf(false);
				msg = r("密码验证失败！");
			}
		}
		renderData(success, "{\"msg\":\"" + msg + "\"}");
	}

	@ResponseBody
	@RequestMapping("/initPortalInfo")
	public void initPortalInfo() {
		ContextBean context = getContext();
		JSONObject json = new JSONObject();
		try {
			json.put("username", context.getUsername());
			json.put("orgFullName", context.getCurrentOgnFullName());
			json.put("personName", context.getCurrentPersonName());
			json.put("personid", context.getCurrentPersonID());
			json.put("personcode", context.getCurrentPersonCode());
			json.put("userCode", context.getCurrentUserCode());
			json.put("positionid", context.getCurrentPositionID());
			json.put("positioncode", context.getCurrentPositionCode());
			json.put("positionname", context.getCurrentPositionName());
			json.put("deptid", context.getCurrentDeptID());
			json.put("deptcode", context.getCurrentDeptCode());
			json.put("deptname", context.getCurrentDeptName());
			json.put("deptfid", context.getCurrentDeptFullID());
			json.put("deptfcode", context.getCurrentDeptFullCode());
			json.put("deptfname", context.getCurrentDeptFullName());
			json.put("orgid", context.getCurrentOrgID());
			json.put("orgcode", context.getCurrentOrgCode());
			json.put("orgname", context.getCurrentOrgName());
			json.put("orgfid", context.getCurrentOrgFullID());
			json.put("orgfcode", context.getCurrentOrgFullCode());
			json.put("orgfname", context.getCurrentOrgFullName());
			json.put("ognid", context.getCurrentOgnID());
			json.put("ogncode", context.getCurrentOgnCode());
			json.put("ognname", context.getCurrentOgnName());
			json.put("ognfid", context.getCurrentOgnFullID());
			json.put("ognfcode", context.getCurrentOgnFullCode());
			json.put("ognfname", context.getCurrentOgnFullName());
			json.put("personfid", context.getCurrentPersonFullID());
			json.put("personfcode", context.getCurrentPersonFullCode());
			json.put("personfname", context.getCurrentPersonFullName());
			json.put("businessid", context.getBusinessID());
			json.put("locale", context.getLocale(request));
			json.put("uiserverremoteurl", context.getUIServerURL(this.request, null, null));
		} catch (Exception e) {
		}
		renderData(json.toString());
	}

	@ResponseBody
	@RequestMapping("/getPrincipalList")
	public void getPrincipalList() {
		renderData(Agents.getPrincipalList(getContext().getCurrentPersonID()));
	}

	@ResponseBody
	@RequestMapping("/getPrincipalInfo")
	public void getPrincipalInfo() {
		String principalID = p("principalid");
		if (principalID != null) {
			renderData(Agents.getPrincipalInfo(principalID, getContext()));
		} else {
			renderData(Boolean.valueOf(true), "{\"msg\":\"success\"}");
		}
	}

	@ResponseBody
	@RequestMapping("/PersonMembers")
	public void PersonMembers() {
		initPortalInfo();
	}

	@ResponseBody
	@RequestMapping("/getOnlineCount")
	public void getOnlineCount() {
		renderData(Boolean.valueOf(true), "{\"count\":" + OnlineUtils.getOnlineCount() + "}");
	}

	@ResponseBody
	@RequestMapping("/getOnlineUserInfo")
	public void getOnlineUserInfo() throws Exception {
		JSONObject json = new JSONObject();
		json.put("data", OnlineUtils.getOnlineList());
		renderData(Boolean.valueOf(true), json.toString());
	}

	@ResponseBody
	@RequestMapping("/getOnceFunc")
	public void getOnceFunc() throws Exception {
		ContextBean context = getContext();
		Boolean b = context.isLogin();
		String s = "";
		if (b.booleanValue()) {
			String onceFunc = context.getOnceFunc();
			if ((onceFunc != null) && (!onceFunc.equals(""))) {
				JSONObject jsonOnceFunc = JSON.parseObject(onceFunc);
				if (jsonOnceFunc.containsKey("url")) {
					JSONObject json = new JSONObject();
					json.put("onceFunc", jsonOnceFunc.toString());
					s = json.toString();
				}
			}
		}
		renderData(b, s);
	}

	@ResponseBody
	@RequestMapping("/setLanguage")
	public void setLanguage() {
		String language = p("language");
		getContext().setLocale(this.request, language);
		renderData(Boolean.valueOf(true), "{\"language\":\"" + language + "\"}");
	}

	@ResponseBody
	@RequestMapping("/getLanguage")
	public void getLanguage() {
		String language = getContext().getLocale(this.request);
		renderData(Boolean.valueOf(true), "{\"language\":\"" + language + "\"}");
	}

	public void getBusinessServerSession(String username, String agent, String password, boolean isAgent,
			boolean isNTLogin, String loginDate, String ip) throws HttpException, IOException, DocumentException {
		clearHttpClient();
		ContextBean context = getContext();
		if (context.hasBusinessSession().booleanValue()) {
			Hashtable<String, String> attributes = new Hashtable();
			Hashtable<String, String> parameters = new Hashtable();

			attributes.put("process", "/SA/OPM/system/systemProcess");
			if (isAgent) {
				attributes.put("name", "agentLoginAction");
				parameters.put("userName", username);
				parameters.put("personName", agent);
				parameters.put("pwd", password);
			} else if (isNTLogin) {
				attributes.put("name", "ntLoginAction");
				attributes.put("ip", ip);
				attributes.put("password", password);
				parameters.put("name", username);
			} else {
				attributes.put("name", "loginAction");
				parameters.put("name", username);
				parameters.put("pwd", password);
			}
			parameters.put("loginDate#date", loginDate);
			parameters.put("lang", context.getLocale(this.request));

			String act = genAction(attributes, parameters);
			String actionURL = isNTLogin ? context.getBusinessServerURL(this.request, "ntloginaction", "/ntLoginAction")
					: context.getBusinessServerURL(this.request, "action", "/business-action");

			PostMethod post = new PostMethod(actionURL + "?language=" + context.getLocale(this.request));
			try {
				post.setRequestEntity(new StringRequestEntity(act, "text/html", "UTF-8"));
				Document resultDoc = executeMethodAsDocument(post);
				if (resultDoc != null) {
					Node node_flag = resultDoc.getRootElement().selectSingleNode("data/items/item[1]");
					if ((node_flag != null) && (node_flag.getText().trim().equalsIgnoreCase("true"))) {
						Node node_personID = resultDoc.getRootElement().selectSingleNode("data/items/item[2]");
						Node node_personName = resultDoc.getRootElement().selectSingleNode("data/items/item[3]");
						Node node_orgID = resultDoc.getRootElement().selectSingleNode("data/items/item[4]");
						Node node_orgName = resultDoc.getRootElement().selectSingleNode("data/items/item[5]");
						Node node_orgPath = resultDoc.getRootElement().selectSingleNode("data/items/item[6]");
						Node node_personCode = isAgent ? null
								: resultDoc.getRootElement().selectSingleNode("data/items/item[7]");

						Node node_client_personID = isAgent
								? resultDoc.getRootElement().selectSingleNode("data/items/item[7]")
								: null;
						Node node_client_personName = isAgent
								? resultDoc.getRootElement().selectSingleNode("data/items/item[8]")
								: null;
						Node node_client_personCode = isAgent
								? resultDoc.getRootElement().selectSingleNode("data/items/item[9]")
								: null;

						String personID =

								node_personID != null ? node_personID.getText().trim()
										: isAgent ? ""
												: node_client_personID != null ? node_client_personID.getText().trim()
														: "";
						String personName =

								node_personName != null ? node_personName.getText().trim()
										: isAgent ? ""
												: node_client_personName != null
														? node_client_personName.getText().trim()
														: "";
						String personCode =

								node_personCode != null ? node_personCode.getText().trim()
										: isAgent ? ""
												: node_client_personCode != null
														? node_client_personCode.getText().trim()
														: "";
						String orgID = node_orgID != null ? node_orgID.getText().trim() : isAgent ? "" : "";
						String orgName = node_orgName != null ? node_orgName.getText().trim() : isAgent ? "" : "";
						String orgPath = node_orgPath != null ? node_orgPath.getText().trim() : isAgent ? "" : "";

						String agentPersonID = isAgent ? ""
								: node_personID != null ? node_personID.getText().trim() : "";
						String agentPersonName = isAgent ? ""
								: node_personName != null ? node_personName.getText().trim() : "";
						String agentPersonCode = "";
						String agentOrgID = isAgent ? "" : node_orgID != null ? node_orgID.getText().trim() : "";
						String agentOrgName = isAgent ? "" : node_orgName != null ? node_orgName.getText().trim() : "";
						String agentOrgPath = isAgent ? "" : node_orgPath != null ? node_orgPath.getText().trim() : "";

						HashMap<String, String> params = new HashMap();
						params.put("username", username);
						params.put("personID", personID);
						params.put("personName", personName);
						params.put("personCode", personCode);
						params.put("orgID", orgID);
						params.put("orgName", orgName);
						params.put("orgPath", orgPath);
						params.put("agentPersonID", agentPersonID);
						params.put("agentPersonName", agentPersonName);
						params.put("agentPersonCode", agentPersonCode);
						params.put("agentOrgID", agentOrgID);
						params.put("agentOrgName", agentOrgName);
						params.put("agentOrgPath", agentOrgPath);

						getSysParams(this.request, params);
					}
				}
			} catch (Exception localException) {
			} finally {
				post.releaseConnection();
			}
		}
	}
}