
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>

		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		write value:20
		<c:set var="mike" value='20'></c:set>
		<br><br>

		output writed value is:${mike}
	</BODY>
</HTML>
