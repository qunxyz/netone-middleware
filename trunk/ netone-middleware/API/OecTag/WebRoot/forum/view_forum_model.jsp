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

	String sqlCount = "SELECT count(*) FROM jforum_forums ";

	String sql = "SELECT forum_id, forum_name, categories_id, forum_desc, forum_order, forum_topics,      forum_last_post_id, moderated ";
    sql +="FROM jforum_forums  ORDER BY forum_order ASC ";

	request.setAttribute("sqlCount", sqlCount);
	request.setAttribute("sql", sql);


%>
<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<!-- 本地CSS链接地址  -->
		<link href="<%=basePath %>/forum/forum-table.css" rel="stylesheet" type="text/css">
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path%>/include/js/page.js"></script>
	</HEAD>

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
		<B>功能:查看所有版块</B>
		<br>
		<hr>


	<BODY bgcolor="#FFFFFF">
		<form action="" name="form1" method="post">
		
			<rs:sql ds="DATASOURCE.DATASOURCE.FORUM" sqlcount="${sqlCount}"
				sql="${sql}" dataname="jforum_teste" prepage="10"></rs:sql>

			<table  border="0" class="t1">
				<c:forEach items="${jforum_teste}" var="forum">
					<tr>
						<td>
							${forum.forum_id}
						</td>
						<td>
							${forum.forum_name}
						</td>
					</tr>
				</c:forEach>

			</table>
		</form>
	</BODY>
</HTML>
