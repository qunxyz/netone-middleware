<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<h2>
			应用JSTL标签做IF判断, 更多细节参考 JSTL_SPECIFICATION.pdf
		</h2>
		<h5>
			判断(1==1)的结果写入到theTruth中的为:
		</h5>
		<c:if test="${1==1}" var="theTruth"></c:if>
		${theTruth}
		<h5>
			根据条件决定是否现在包含的内容
		</h5>

		<c:if test="${2>0}">
				It's true that (2>0)!
    	</c:if>
	</BODY>
</HTML>
