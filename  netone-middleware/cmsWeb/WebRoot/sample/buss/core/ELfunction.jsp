<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>

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
			应用JSTL标签处理字符串, 更多细节参考 JSTL_SPECIFICATION.pdf 中的function章节
		</h2>
		<h5>
			计算参数字符的长度
		</h5>
		${fn:length(param.xxx)}
		<h5>
			遍历解析字符串a:b:c:d:e
		</h5>
		<c:forTokens items="a:b:c:d:e" delims=":" var="tok">
			${tok}<br>
		</c:forTokens>
	</BODY>
</HTML>
