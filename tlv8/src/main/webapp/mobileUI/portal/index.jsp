<%@page import="com.tlv8.system.controller.UserController"%>
<%@page import="com.tlv8.system.bean.ContextBean"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
ContextBean contextbean = new UserController().getContext(request.getSession().getId());
if(contextbean!=null && contextbean.isLogin()){
	response.sendRedirect("index.html");
}else{
	response.sendRedirect("login.html");
}
%>