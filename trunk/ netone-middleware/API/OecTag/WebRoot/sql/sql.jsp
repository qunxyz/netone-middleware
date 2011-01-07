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

	<h3>利用标签在页面进行sql操作，使用数据源</h3>
		<b>标签名称</b><br>
			<c:out value="<rs:sql/>" escapeXml="true"></c:out><br>
		<b>标签参数</b><br>
			ds：数据源，在Netone平台上定义的数据源，这里填写数据源的名称<br>
			sqlcount:数据记录的总条数，使用sql语句获得数据记录的总条数<br>
			sql:本次操作执行的sql语句<br>
			prepage:每页显示的条数，不设置默认为10条
			dataname:返回结果<br>
			注意：本例中没有使用分页
		<b>测试例子</b><br>
		使用的数据源名称为DATASOURCE.DATASOURCE.INIS
		<br>
		<hr>
		
		<!-- 带数据源的 SAMPLE -->
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
