<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
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
			Ӧ��Oesee Netone��ǩ���JSTL������Netone������ע������ݿ�
		</h2>

		<!-- ������Դ�� SAMPLE -->
		<rs:sql ds="DATASOURCE.DATASOURCE.INIS"
			sql="SELECT * FROM inis_runner" dataname="bbb"></rs:sql>
		<table border='1'>
			<c:forEach items="${bbb}" var="pre">
				<tr>
					<td>
						${pre.name}
					</td>
					<td>
						${pre.guid}
					</td>
				</tr>
			</c:forEach>
		</table>
	</BODY>
</HTML>
