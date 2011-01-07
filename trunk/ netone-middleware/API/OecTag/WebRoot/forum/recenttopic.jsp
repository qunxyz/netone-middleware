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
/**
	String sqlCount = "SELECT count(*) FROM jforum_topics t, jforum_posts p WHERE p.post_id = t.topic_last_post_id  ";
*/
         String sqlCount = "SELECT count(*)";
	sqlCount +="FROM jforum_topics t, jforum_posts p,`jforum_users` u  ";
	sqlCount +="WHERE p.post_id = t.topic_last_post_id  AND u.`user_id` = p.`user_id` ";
	sqlCount +="AND p.need_moderate = 0 ";

         String sql = "SELECT t.*,  u.`username` , p.`user_id` AS last_user_id, p.`post_time`, p.attach AS attach ";
	sql +="FROM jforum_topics t, jforum_posts p,`jforum_users` u  ";
	sql +="WHERE p.post_id = t.topic_last_post_id  AND u.`user_id` = p.`user_id` ";
	sql +="AND p.need_moderate = 0 ";
	sql +="ORDER BY topic_last_post_id DESC ";
	sql+=" limit #startIndex, #pageSize";
/**
    sql +="FROM `jforum_topics` t, `jforum_posts` p ,`jforum_users` u ";
    sql +="WHERE p.`post_id` = t.`topic_last_post_id` AND u.`user_id` = p.`user_id` ";
*/
	request.setAttribute("sqlCount", sqlCount);
	request.setAttribute("sql", sql);


%>
<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<!-- ����CSS���ӵ�ַ  -->
		<link href="<%=basePath %>/forum/forum-table.css" rel="stylesheet" type="text/css">
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
						<td >
							${hottopic.username}
						</td>
						<td align="right">
							${fn:substring(hottopic.post_time,0,19)}
						</td>
					</tr>
				</c:forEach>
				<tr>
				<td>
				 
				</td>
				<td>
				 
				</td>
				<td align="right" style="border:0px">
				<a href="http://192.168.2.104:8081/forum/" target="_blank">����</a>
				</td>
				</tr>
			</table>
<!-- ��ҳ���� -->
<table>
                            <td>
			<script type="text/javascript">
				var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
				pginfo.write();
	                  </script>
                            </td>
                            </tr>
</table>
		</form>
	</BODY>
</HTML>
