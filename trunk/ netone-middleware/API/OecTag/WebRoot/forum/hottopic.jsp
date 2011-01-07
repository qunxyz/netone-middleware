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

	String sqlCount = "SELECT count(*) FROM jforum_topics t, jforum_posts p WHERE p.post_id = t.topic_last_post_id   ";

	String sql = "SELECT t.*, u.`username` ,p.`user_id` AS last_user_id, p.`post_time`, p.`attach` AS attach ";
    sql +="FROM `jforum_topics` t, `jforum_posts` p ,`jforum_users` u ";
    sql +="WHERE p.`post_id` = t.`topic_last_post_id` AND u.`user_id` = p.`user_id` ";
    sql +="AND p.`need_moderate` = 0 ";
    sql +="ORDER BY `topic_views` DESC ";
    sql+=" limit #startIndex, #pageSize";

	request.setAttribute("sqlCount", sqlCount);
	request.setAttribute("sql", sql);


%>

<STYLE TYPE="text/css">
body,table{
	font-size:12px;
}
/*
table{
	table-layout:fixed;
	empty-cells:show; 
	border-collapse: collapse;
	margin:0 auto;
}
*/
td{
	height:20px;
}
h1,h2,h3{
	font-size:12px;
	margin:0;
	padding:0;
}



/*����ǽ��һ����̳����ʽ*/
table.t1{
	border:1px solid #cad9ea;
	color:#666;
}
table.t1 th {
	background-repeat::repeat-x;
	/** height:30px; */
}
table.t1 td,table.t1 th{
	border:1px solid #cad9ea;
	padding:0 1em 0;
}
table.t1 tr.a1{
	background-color:#f5fafe;
}

table.t2{
	border:1px solid #9db3c5;
	color:#666;
}
table.t2 th {
	background-image: url(th_bg2.gif);
	background-repeat::repeat-x;
	height:30px;
	color:#fff;
}
table.t2 td{
	border:1px dotted #cad9ea;
	padding:0 2px 0;
}
table.t2 th{
	border:1px solid #a7d1fd;
	padding:0 2px 0;
}
table.t2 tr.a1{
	background-color:#e8f3fd;
}



table.t3{
	border:1px solid #fc58a6;
	color:#720337;
}
table.t3 th {
	background-image: url(th_bg3.gif);
	background-repeat::repeat-x;
	height:30px;
	color:#35031b;
}
table.t3 td{
	border:1px dashed #feb8d9;
	padding:0 1.5em 0;
}
table.t3 th{
	border:1px solid #b9f9dc;
	padding:0 2px 0;
}
table.t3 tr.a1{
	background-color:#fbd8e8;
}
</STYLE>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path%>/include/js/page.js"></script>
	</HEAD>

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
		<B>���ܣ���ʾ��������</B>
		<br>
		<hr>


	<BODY bgcolor="#FFFFFF">
		<form action="" name="form1" method="post">
		
			<rs:sql ds="DATASOURCE.DATASOURCE.FORUM" sqlcount="${sqlCount}"
				sql="${sql}" dataname="jforum_teste" prepage="10"></rs:sql>

			<table  border="0" class="t1">
				<c:forEach items="${jforum_teste}" var="hottopic">
					<tr>
						<td>
							<a href="http://192.168.2.104:8081/forum/posts/list/${hottopic.topic_id}.page" target="_blank">${hottopic.topic_title}</a>
						</td>
<!--
						<td >
							${hottopic.username}
						</td>
-->
						<td align="right">
							${fn:substring(hottopic.post_time,0,10)}
						</td>
					</tr>

				</c:forEach>
				<tr>
<td></td>

				<td align="right" style="border:0px">
				<a href="http://192.168.2.104:8081/forum/" target="_blank">����</a>
				</td>
				</tr>
			</table>
		</form>
	</BODY>
</HTML>
