<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>

		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">

	${var1}的长度为:${fn:length(var1)}
	
	${fn:substring(xxxx,0,10)}
	<br>
	${var2+10}
	</BODY>
</HTML>
