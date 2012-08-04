<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="oe.frame.web.WebCache"%>
<%@page import="oe.frame.web.util.WebTip"%>
<%
String formcode=request.getParameter("formcode");
WebCache.removeCache("fetchColumnList"+formcode);
WebCache.removeCache("queryColumnQ"+formcode);
WebCache.removeCache("queryColumnX0"+formcode);
WebCache.removeCache("queryColumnX1"+formcode);
WebCache.removeCache("queryColumnX2"+formcode);
WebCache.removeCache("queryColumnX3"+formcode);

WebTip.htmlInfo("Ok init!",true,false,response);
%>
