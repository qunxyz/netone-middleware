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

	String sqlCount = "SELECT count(*) FROM jforum_topics t, jforum_posts p WHERE p.post_id = t.topic_last_post_id ";

	String sql = "SELECT t.*, u.`username` ,p.`user_id` AS last_user_id, p.`post_time`, p.`attach` AS attach ";
    sql +="FROM `jforum_topics` t, `jforum_posts` p ,`jforum_users` u ";
    sql +="WHERE p.`post_id` = t.`topic_last_post_id` AND u.`user_id` = p.`user_id` ";
    sql +="AND p.`need_moderate` = 0 ";
    sql +="ORDER BY `topic_views` DESC ";
    sql+=" limit #startIndex, #pageSize";

	System.out.println(sql);

	request.setAttribute("sqlCount", sqlCount);
	request.setAttribute("sql", sql);


%>
<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path%>/include/js/page.js"></script>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<form action="" name="form1" method="post">
		
			<rs:sql ds="DATASOURCE.DATASOURCE.FORUM" sqlcount="${sqlCount}"
				sql="${sql}" dataname="jforum_teste" prepage="10"></rs:sql>

			<table width="774" border="1">
				<c:forEach items="${jforum_teste}" var="hottopic">
					<tr>
						<td>
							<a href="http://192.168.2.104:8081/forum/posts/list/${hottopic.topic_id}.page" target="_blank">${hottopic.topic_title}</a>
						</td>
						<td >
							${hottopic.username}
						</td>
						<td>
							${hottopic.post_time}
						</td>
					</tr>
				</c:forEach>
				<tr>
				<td>
				&nbsp;
				</td>
				<td>
				&nbsp;
				</td>
				<td align="right">
				<a href="http://192.168.2.104:8081/forum/" target="_blank">¸ü¶à</a>
				</td>
				</tr>
			</table>
		</form>
	</BODY>
</HTML>
