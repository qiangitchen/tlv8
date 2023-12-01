<%@page import="cn.dev33.satoken.stp.StpUtil"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String context = request.getContextPath();
    String gopage = "login.html";
    if (StpUtil.isLogin()) {
		gopage = "index.html";
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>TLv8</title>
<script type="text/javascript">
	/*
	 * 智能机浏览器版本信息:
	 *
	 */
	var browser = {
		versions : function() {
			var u = navigator.userAgent, app = navigator.appVersion;
			return {//移动终端浏览器版本信息 
				trident : u.indexOf('Trident') > -1, //IE内核
				presto : u.indexOf('Presto') > -1, //opera内核
				webKit : u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
				gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
				mobile : !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
				ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
				android : u.indexOf('Android') > -1, //android终端或者uc浏览器 || u.indexOf('Linux') > -1
				iPhone : u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
				iPad : u.indexOf('iPad') > -1, //是否iPad
				webApp : u.indexOf('Safari') == -1
			//是否web应该程序，没有头部与底部
			};
		}(),
		language : (navigator.browserLanguage || navigator.language)
				.toLowerCase()
	};
	if (browser.versions.mobile || browser.versions.ios
			|| browser.versions.android || browser.versions.iPhone
			|| browser.versions.iPad) {
		window.location.href = "<%=context%>/mobileUI/portal/<%=gopage%>";
	} else {
		window.location.href = "<%=context%>/portal/<%=gopage%>";
	}
</script>
</head>

<body>
	<p>Loading ...</p>
</body>
</html>