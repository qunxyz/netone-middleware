<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>

		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
	<c:forEach items="${list}" var="each" >
		${each.name},${each.age} <br><br>
	</c:forEach>
	</BODY>
</HTML>
