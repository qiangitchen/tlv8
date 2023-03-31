<%@page import="com.tlv8.system.bean.ContextBean"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
ContextBean contextbean = ContextBean.getContext(request);
if(contextbean!=null && contextbean.isLogin()){
	response.sendRedirect("index.html");
}else{
	response.sendRedirect("login.html");
}
%>