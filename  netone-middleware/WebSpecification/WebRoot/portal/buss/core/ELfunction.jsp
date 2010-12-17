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
			Ӧ��JSTL��ǩ�����ַ���, ����ϸ�ڲο� JSTL_SPECIFICATION.pdf �е�function�½�
		</h2>
		<h5>
			��������ַ��ĳ���
		</h5>
		${fn:length(param.xxx)}
		<h5>
			���������ַ���a:b:c:d:e
		</h5>
		<c:forTokens items="a:b:c:d:e" delims=":" var="tok">
			${tok}<br>
		</c:forTokens>
	</BODY>
</HTML>
