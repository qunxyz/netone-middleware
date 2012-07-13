<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="oe.frame.web.WebCache"%>
<%@page import="oe.frame.web.util.WebTip"%>
<%
String formcode=request.getParameter("formcode");
WebCache.removeCache("fetchColumnList"+formcode);
WebCache.removeCache("queryColumnQ"+formcode);
WebCache.removeCache("queryColumnX"+formcode+"0");
WebCache.removeCache("queryColumnX"+formcode+"1");
WebCache.removeCache("queryColumnX"+formcode+"2");
WebCache.removeCache("queryColumnX"+formcode+"3");

WebTip.htmlInfo("Ok init!",true,false,response);
%>
