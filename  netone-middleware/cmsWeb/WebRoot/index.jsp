
<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUserMgr"%>
<%@page import="oe.security3a.sso.onlineuser.DefaultOnlineUserMgr"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
	
			OnlineUserMgr oum = new DefaultOnlineUserMgr();
			String loginname = oum.getOnlineUser(request).getLoginname();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>Portal</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript">
			var contextpath = "<%=path%>";
		</script>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/modelshow/personmodel.js"></SCRIPT>
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/cms/include/css/portal.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript">
					function view(mode){
						var value=document.getElementById('rootpage').value;
						if(mode==1){
						
							window.open('/cmsWeb/frames/AppFrame1.jsp?rs='+value+'&layout=left&needhidden=y&height=0&fckid=&initurl=');
						}
						if(mode==2){
						   window.open('/cmsWeb/frames/AppFrame2.jsp?rs='+value+'&height=0&fckid=');
						}
					}
					
					function sselected(text,id){
					   document.getElementById('rootpage').value=id;
					}
		</script>
	</head>

	<body style="margin: 0px;">

		<table width="750" height="500" border="0">

			<tr>
				<td height="30" colspan="2">
					
						<img src='Netone.PNG'/>
					 <font color='red'><rs:logininfo/></font>  &nbsp;  <font color='blue'><a href='<rs:loginout/>'>【注销】</a></font><font color='blue'><a  
href='<rs:changepassword/>' target='_blank'>【修改密码】</a></font>
<font color='blue'><a href='<rs:userinfo/>' target='_blank'>【个人信息】</a></font> 
					

				</td>
			</tr>
			<tr bgcolor="#999999">
				<td height="35" colspan="2">
					<p>
						<strong><font color="#FFFFFF">&nbsp;OESEE NETONE-EM(企业级中间件平台) 在线开发后台</font> </strong>
<font color="#FFFFFF">&nbsp;&nbsp;&nbsp;&nbsp;contact us  mail:oesee@139.com  tel:86+15860836998 </font>

					</p>
				</td>
			</tr>
			<tr>
				<td height="77" colspan="2">
					<p>
						<FONT class="OecText">&nbsp;选择要使用的页组。您可以查看该页组, 编辑它的属性,
							或单击下面的某个链接在该页组中创建页, 样式或模板。</FONT>
					</p>
					&nbsp;
					<A HREF="javascript:"
						onclick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=pagegroup&appname=PORTALPG','_blank')"><FONT
						class="OecLink">PORTAL创建/管理</FONT> </A> &nbsp;&nbsp;&nbsp;&nbsp;
					<A HREF="PagelistpathRightSvl?pagename=pageframe&appname=FRAMEPG"
						target='_blank'><FONT class="OecLink">管理页框</FONT> </A>
					
					<input type='text' id='rootpage' value='FRAMEPG.FRAMEPG' readonly>
					<input type='button' value='选择根页框'
						onClick="window.open('/cmsWeb/SSelectSvl?pagename=pagegroup3&appname=FRAMEPG','_blank');" />

					
					<A HREF="javascript:view(1)" ><FONT class="OecLink">[纵型预览]</FONT> </A>
					<A HREF="javascript:view(2)"><FONT class="OecLink">[横型预览]</FONT> </A>
					
					&nbsp;&nbsp;
					<A HREF='/fck/PagelistpathRightSvl?appname=FCK&pagename=fcklist' target='_blank'>
							<FONT class="OecLink">[在线文档]</FONT></A>
					<hr>
				</td>
			</tr>
			<tr>
				<td width="691" height="91" colspan='2'>

					应用类型:
					<select id='apptype'>
						<OPTION
							VALUE="/biWeb/PagelistpathRightSvl?pagename=dyformlist&appname=BUSSFORM">
							动态表单
						<OPTION
							VALUE="/wfweb/PagelistpathRightSvl?pagename=pagelist&appname=BUSSWF">
							工作流
						<option
							value="/wfweb/PagelistpathRightSvl?pagename=quartzlist&appname=PLAN">
							计划任务
						<OPTION
							VALUE="/biWeb/PagelistpathRightSvl?pagename=etl&appname=ETL">
							报表管理
						<OPTION
							VALUE="/biWeb/PagelistpathRightSvl?pagename=chart&appname=CHART">
							图表管理
						<OPTION VALUE="/biWeb/bi/etl/wizard/ExcelToDb/First.jsp?pagepath=X">
							数据采集
						<OPTION
							VALUE="/cmsWeb/PagelistpathRightSvl?pagename=jspapp&appname=JSPAPP">
							POTRLET应用
						<OPTION
							VALUE="/cmsWeb/PagelistpathRightSvl?pagename=bean&appname=BUSSBEAN">
							BEAN服务
					</select>
					<A HREF="javascript:createpage()" ><FONT
						class="OecLink">创建/管理应用</FONT> </A>
					&nbsp;
					<A HREF="/Security3A" target='_blank'><FONT class="OecLink">4A安全</FONT>
					</A>
					&nbsp;
					<A HREF="/biWeb/PagelistpathRightSvl?pagename=datasourceExcellist&appname=DATASOURCE" target='_blank'><FONT class="OecLink">数据源配置

</FONT>
					</A>
					<BR>
<BR>
					<FONT class="OecText">应用是关于Portal中的应用程序功能。他们都可以被集成到Portal中或者使用相关的标签做二次开发实现Portlet。</FONT>
					<BR>
				</td>

			</tr>
			<tr>
				<td height="83">
					<A HREF="javascript:" onClick='listStyle()'><FONT
						class="OecLink">创建/管理样式</FONT> </A>
					<BR>
					<BR>
					<FONT class="OecText">使用分步向导来创建新样式。样式在页组中被使用到,并且自动生效到所有隶属于该组的页中。</FONT>
				</td>
				<td>
					<A HREF="javascript:"
						onclick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=template&appname=TEMPLATE','_blank')"><FONT
						class="OecLink">创建/管理业务元</FONT> </A>
					<BR>
					<BR>
					<FONT class="OecText">业务元用于灵活的封装业务元素，在这里您可以定义功能业务脚本和展现脚本，它可后可用于构建各种页或者在流程/任务中作为调度的元素使用。</FONT>
				</td>
			</tr>
			<tr>

				<td height="70">
					<A HREF="javascript:"
						onClick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=jspapp&appname=JSPAPP')"><FONT
						class="OecLink">创建/管理页程序</FONT> </A>
					<BR>
					<BR>
					在这里您可以创建基于OESEE规范的页面程序.......。

				</td>
				<td height="70">
					<A HREF="javascript:" onClick='createResource()'><FONT
						class="OecLink">资源管理</FONT> </A>
					<BR>
					<BR>
					在页中需要引用大量的实体资源，比如：图片，文件，附件.......。

				</td>
			</tr>
			<tr>
				<td height="128" colspan="2">
					<hr>
					<Strong><font color='red'>企业级 应用开发模式</font></Strong> &nbsp;
					<br>

					<br>
					案例系统:
					
					<a
						href='<portal:envget envkey="WEBSER_FORUM"/>'
						target='_blank'><FONT class="OecLink">论坛</font> </a>
					<a
						href='<portal:envget envkey="WEBSER_WEBMAIL"/>'
						target='_blank'><FONT class="OecLink">邮件</font> </a>
					<a
						href='<portal:envget envkey="WEBSER_INIS"/>'
						target='_blank'><FONT class="OecLink">巡检</font> </a>
					<a
						href='<portal:envget envkey="WEBSER_ITKNOW"/>'
						target='_blank'><FONT class="OecLink">知识库</font> </a>
					
					<a	href='<portal:envget envkey="WEBSER_PMS"/>/PagelistpathRightSvl?pagename=pagelist&appname=PMS.PMS.INSTANCES'
						target='_blank'><FONT class="OecLink">任务系统平台站</font> 
					<a
						href='<portal:envget envkey="WEBSER_PMS"/>/PagelistpathRightSvl?pagename=pojlist&appname=PMS.PMS.FRAMEWORK'
						target='_blank'><FONT class="OecLink">任务系统平台站框架管理</font> </a>
						
						 <br>
						<br> 在这里我们预先整合和研发了一些应用系统和中间件平台，通过学习和使用这些系统和平台，您的业务系统将会更加的完美

						项目绩效管理系统、工单系统、业务考核系统、 CRM客户关系管理系统、表单系统、经营分析、会员积分管理、企业论坛、 
				</td>
			</tr>
		</table>
	</body>
</html>
