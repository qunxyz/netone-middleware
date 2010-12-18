
<%@page import="oe.rmi.client.RmiEntry"%>
<%@page import="oe.env.client.EnvService"%>

<%
EnvService env=(EnvService)RmiEntry.iv("envinfo");
String str=env.fetchEnvValue("WEBSER_ISS");
request.setAttribute("rootpath",str);
%>