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
			Ӧ��JSTL��ǩ��IF�ж�, ����ϸ�ڲο� JSTL_SPECIFICATION.pdf
		</h2>
		<h5>
			�ж�(1==1)�Ľ��д�뵽theTruth�е�Ϊ:
		</h5>
		<c:if test="${1==1}" var="theTruth"></c:if>
		${theTruth}
		<h5>
			�������������Ƿ����ڰ���������
		</h5>

		<c:if test="${2>0}">
				It's true that (2>0)!
    	</c:if>
	</BODY>
</HTML>
