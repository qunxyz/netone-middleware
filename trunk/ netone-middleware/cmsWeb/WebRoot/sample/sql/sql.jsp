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

	<h3>���ñ�ǩ��ҳ�����sql������ʹ������Դ</h3>
		<b>��ǩ����</b><br>
			<c:out value="<rs:sql/>" escapeXml="true"></c:out><br>
		<b>��ǩ����</b><br>
			ds������Դ����Netoneƽ̨�϶��������Դ��������д����Դ������<br>
			sqlcount:���ݼ�¼����������ʹ��sql��������ݼ�¼��������<br>
			sql:���β���ִ�е�sql���<br>
			prepage:ÿҳ��ʾ��������������Ĭ��Ϊ10��
			dataname:���ؽ��<br>
			ע�⣺������û��ʹ�÷�ҳ
		<b>��������</b><br>
		ʹ�õ�����Դ����ΪDATASOURCE.DATASOURCE.INIS
		<br>
		<hr>
		
		<!-- ������Դ�� SAMPLE -->
		<rs:sql ds="DATASOURCE.DATASOURCE.INIS" sqlcount="SELECT count(*) from inis_runner"
			sql="SELECT * FROM inis_runner " dataname="bbb"></rs:sql>
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
