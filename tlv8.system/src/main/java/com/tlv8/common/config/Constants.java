package com.tlv8.common.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统静态常量
 * 
 * @author fbf
 * 
 */
public class Constants {
	/* ~~~~~~~~~~~~~~~~~~~~~~~~以下内容不允许修改~~~~~~~~~~~~~~~~~~~~~~ */
	/**
	 * 菜单 按钮
	 */
	public static final String MENU = "1";
	public static final String BUTTON = "2";
	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	/**
	 * 对/错
	 */
	public static final String TRUE = "1";
	public static final String FALSE = "0";
	/**
	 * AJAX_SESSION_TIMEOUT主要用在ajax session timeout过滤器中 参考http的status的标准状态吗408代表超时
	 * ！！！注意在js页面中注册全局的ajax处理代码 配合使用
	 */
	public static final int AJAX_SESSION_TIMEOUT = 408;

	// 客户端发来的加密消息在request中的key value是Meaasge对象
	public static final String REQUEST_ENCRYPT_MESSAGE_KEY = "requestEncryptMessageKey";

	// 客户端发来的加密消息在解析过程中失败的原因在request域中的key值 value是String对象 说明失败原因
	public static final String REQUEST_ENCRYPT_MESSAGE_FAILURE_KEY = "requestEncryptMessageFailureKey";

	/**
	 * CURRENT_USER 代表session中代表当前用户的key值 session中得到的是SysUser对象
	 */
	public static final String CURRENT_USER = "sysUser";
	/**
	 * CURRENT_USER_NAME 代表session中代表当前用户POJO的key值 session中得到的是username String对象
	 */
	public static final String CURRENT_USER_NAME = "sysUserName";

	/**
	 * 登录验证失败存储的key值 同FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME一样
	 * 
	 */
	public static final String LOGIN_FAILURE_KEY = "shiroLoginFailure";

	/**
	 * SESSION_FORCE_LOGOUT_KEY 代表session中表示管理员强制某人退出的key值
	 * 管理员踢人时在要提的session中标记key为forceLogoutKey为true
	 * 当该session再次访问时如果session中该key为true则提示已被提出再销毁session
	 */
	public static final String SESSION_FORCE_LOGOUT_KEY = "forceLogoutKey";

	/**
	 * session中系统生成当前session用户的验证码在session域中的key名
	 */
	public static final String SESSION_CAPTCHA = "session.captcha";
	/**
	 * request中用户输入的当前请求用户的验证码在request域中的key名
	 */
	public static final String REQUEST_CAPTCHA = "validatecode";

	/* 加密模块常量 */
	public static String CLIENT_PUBLIC_KEY = "clientPublicKey";
	public static String CLIENT_PRIVATE_KEY = "clientPrivateKey";
	public static String SERVER_PUBLIC_KEY = "serverPublicKey";
	public static String SERVER_PRIVATE_KEY = "serverPrivateKey";

	/**
	 * 是否开启验证码校验 这里默认关闭 配置文件里的配置会覆盖此配置 请不要在这里修改 如需修改请改config.properties文件
	 */
	public static boolean CAPTCHA_ENABLED = true;
	/**
	 * 是否开启验证码校验的request域中attr的key值 前台页面可以根据该属性的值选择是否显示验证码 不允许修改
	 */
	public static final String CAPTCHA_ENABLED_KEY = "captchaEnabled";

	/* ~~~~~~~~~~~~~~~~~~~~~~以下请根据实际情况修改 建议默认即可~~~~~~~~~~~~~~~~~~~~~~~~~~ */

	/**
	 * shiro相关的错误页跳转url 请和springMVC中的requestmapping保持一致
	 * 如果修改请注意shiro配置文件中该url访问应该是anno任何人都能访问
	 */
	public static final String ERROR_PAGE_URL = "/errorpage";

	/**
	 * 把session放入到加密请求的request域中的key value是Session对象
	 */
	public static final String GET_SESSION_BY_TOKEN = "getSessionByToken";

	/**
	 * 存储后台管理界面的url 同时也是登陆成功后访问的url 请和shiro相关配置中的shiro.success.url保持一致 请不要在这里修改
	 * 配置文件中如果配置错误系统启动会停止
	 */
	public static String SUCCESS_URL = "/index";

	/**
	 * 存放在session中的菜单列表的key
	 */
	public static final String SESSION_MENUS = "menus";
	/**
	 * 存放在session中的角色名称的key
	 */
	public static final String CURRENT_ROLES_NAME = "currentRolesName";
	/**
	 * 存放在session中的组织机构名称的key
	 */
	public static final String CURRENT_ORGANIZATION_NAME = "currentOrganizationName";
	/**
	 * 系统版权信息 默认值 配置文件里如果有会覆盖此值
	 */
	public static String SYS_COPYRIGHT = "www.max256.com";
	/**
	 * 系统名称 默认值 配置文件里如果有会覆盖此值
	 */
	public static String SYS_NAME = "morpho system";

	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~以下内容根据需求自行增加~~~~~~~~~~~~~~~~~~~~~~ */
	/**
	 * 未知错误存储的value值
	 */
	public static final String UNKNOWN_ERROR = "0000";
	/**
	 * 验证码验证失败存储的value值
	 */
	public static final String CAPTCHA_FAILURE = "0001";
	/**
	 * 被踢出系统存储的value值
	 */
	public static final String KICK_OUT = "0002";
	/**
	 * 账号被锁定存储的value值
	 */
	public static final String ACCOUNT_LOCKED = "0003";
	// 未知账户错误
	public static final String UNKNOWN_ACCOUNT_EXCEPTION = "0004";
	// 凭证错误
	public static final String INCORRECT_CREDENTIALS_EXCEPTION = "0005";
	// 尝试次数过多
	public static final String EXCESSIVE_ATTEMPTS_EXCEPTION = "0006";
	// 系统超时
	public static final String TIME_OUT = "0007";
	// 错误代码表
	public static Map<String, String> ERROR_CODE_MAP = new HashMap<String, String>();
	static {
		ERROR_CODE_MAP.put(UNKNOWN_ERROR, "未知错误");
		ERROR_CODE_MAP.put(CAPTCHA_FAILURE, "验证码错误");
		ERROR_CODE_MAP.put(KICK_OUT, "您已被踢出系统");
		ERROR_CODE_MAP.put(ACCOUNT_LOCKED, "账号被锁定");
		ERROR_CODE_MAP.put(UNKNOWN_ACCOUNT_EXCEPTION, "未知的账号错误");
		ERROR_CODE_MAP.put(INCORRECT_CREDENTIALS_EXCEPTION, "密码错误");
		ERROR_CODE_MAP.put(EXCESSIVE_ATTEMPTS_EXCEPTION, "登录尝试次数过多");
		ERROR_CODE_MAP.put(TIME_OUT, "系统超时,请重新登陆");
	}

}
