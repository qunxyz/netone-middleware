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
	
	//取地址参数
	String forum_model = request.getParameter("model");
	//默认版块ID=1
	if("".equals(forum_model) | forum_model ==null){
		forum_model= "1";
	}

	String sqlCount = "SELECT count(*) ";
    sqlCount +="FROM jforum_topics t, jforum_posts p ,`jforum_users` u  ";

    /** t.forum_id  版块ID号*/
    sqlCount +="WHERE (t.forum_id = '"+ forum_model+"' AND t.`topic_moved_id` = 0)  AND u.`user_id` = p.`user_id` "; 
    
    sqlCount +="AND p.post_id = t.topic_last_post_id ";
    sqlCount +="AND p.`need_moderate` = 0 ";
    sqlCount +="ORDER BY t.topic_type DESC, t.topic_last_post_id DESC ";


	String sql = "SELECT t.*,u.`username` , p.user_id AS last_user_id, p.post_time, p.attach AS attach ";
    sql +="FROM jforum_topics t, jforum_posts p ,`jforum_users` u  ";

    /** t.forum_id  版块ID号*/
    sql +="WHERE (t.forum_id = '"+ forum_model+"' AND t.`topic_moved_id` = 0)  AND u.`user_id` = p.`user_id` "; 
    
    sql +="AND p.post_id = t.topic_last_post_id ";
    sql +="AND p.`need_moderate` = 0 ";
    sql +="ORDER BY t.topic_type DESC, t.topic_last_post_id DESC ";
    sql+=" limit #startIndex, #pageSize";

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
		<B>功能：显示版块ID=1下的帖子内容</B>  若要查看其他版块的帖子，<br>
		在地址/forum/view_forum_post.jsp加上参数 ?model= 版块ID
		<br>
		如果不知道版块ID，点 “查看版块” 查看版块ID
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
				<a href="http://192.168.2.104:8081/forum/" target="_blank">更多</a>
				</td>
				</tr>
                          </table>
                          <tr>
<!-- 分页处理 -->
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
