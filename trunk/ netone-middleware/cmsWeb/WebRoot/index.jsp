<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUserMgr"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
						if(mode==3){
						
							window.open('/cmsWeb/extframes.do?listPath='+value);
						}
						if(mode==4){
						   window.open('/cmsWeb/extframes.do?mode=4&listPath='+value);
						}
					}
					
					function sselected(text,id){
					   document.getElementById('rootpage').value=id;
					}
		</script>
	</head>

	<body style="margin: 0px;">

		<table width="750" height="500" border="0" align="left">
			<tr>
				<td>
					
				</td>
				<td>
					<table>
						<tr>
							<td height="30" colspan="2">

								<img src='image/Netone.PNG' />



								<font color='red'><rs:logininfo /> </font><font color='blue'><a
									href='<rs:loginout/>'>【注销】</a> </font><font color='blue'><a
									href='<rs:changepassword/>' target='_blank'>【修改密码】</a> </font>
								<font color='blue'><a href='<rs:userinfo/>'
									target='_blank'>【个人信息】</a> </font>

							</td>
						</tr>
						<tr bgcolor="#999999">
							<td height="35" colspan="2">
								<p>
									<strong><font color="#FFFFFF">OESEE
											NETONE-EM(企业级中间件平台) 在线开发后台</font> </strong>
									<font color="#FFFFFF">&nbsp;&nbsp;&nbsp;&nbsp;contact us
										mail:oesee@139.com tel:86+15860836998 </font>

								</p>
							</td>
						</tr>
						<tr>
							<td height="77" colspan="2">
								<font size=2 color="red"><strong>在线门户定制:</strong> </font> Portal可用于构建丰富的功能门户，再通过页框管理可以轻松的整合出结构清晰的WEB应用首页
								<br>
								<br>

								<A HREF="javascript:"
									onclick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=pagegroup&appname=PORTALPG','_blank')"><FONT
									class="OecLink">PORTAL创建/管理</FONT> </A> &nbsp;&nbsp;&nbsp;
								<A
									HREF="PagelistpathRightSvl?pagename=pageframe&appname=FRAMEPG"
									target='_blank'><FONT class="OecLink">管理页框</FONT> </A>

								<input type='text' id='rootpage' value='FRAMEPG.FRAMEPG'
									readonly>
								<input type='button' value='选择根页框'
									onClick="window.open('/cmsWeb/SSelectSvl?pagename=pagegroup3&appname=FRAMEPG','_blank');" />


								<A HREF="javascript:view(1)"><FONT class="OecLink">[纵型预览]</FONT>
								</A>
								<A HREF="javascript:view(3)"><FONT class="OecLink">[纵型预览2]</FONT>
								</A>
								<A HREF="javascript:view(2)"><FONT class="OecLink">[横型预览]</FONT>
								</A>
								<A HREF="javascript:view(4)"><FONT class="OecLink">[横型预览2]</FONT>
								</A> &nbsp;&nbsp;
								<A HREF='/fck/PagelistpathRightSvl?appname=FCK&pagename=fcklist'
									target='_blank'> <FONT class="OecLink">[在线文档]</FONT> </A>
								<hr>
							</td>
						</tr>
						<tr>
							<td width="691" height="91" colspan='2'>

								
								<font size=2 color="red"><strong>在线功能定制开发:</strong> </font>支持用户在线快速定制相关的业务功能。包括:表单、流程、BI图表、安全应用、资源等
								<br>
								<br>
								请选择应用:
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
								</select>
								<A HREF="javascript:createpage()"><FONT class="OecLink">创建/管理应用</FONT>
								</A> &nbsp;
								
								
								<A HREF="<portal:envget envkey="WEBSER_APPFRAME"/>/department/department.do?method=onDepartmentManageView" target='_blank'><FONT class="OecLink">帐户</FONT>&nbsp;
								<A HREF="/Security3A" target='_blank'><FONT class="OecLink">4A安全</FONT>
								</A> &nbsp;
								<A
									HREF="/biWeb/PagelistpathRightSvl?pagename=datasourceDblist&appname=DATASOURCE"
									target='_blank'><FONT class="OecLink">数据源配置</FONT> </A> &nbsp;
								<A HREF="javascript:" onClick='createResource()'><FONT
									class="OecLink">资源管理</FONT> </A> &nbsp;
								<A HREF="/biWeb/bi/etl/wizard/ExcelToDb/First.jsp?pagepath=X"
									target='_blank'><FONT class="OecLink">数据采集</FONT> </A>
								<HR>
								
								<font size=2 color="red"><strong>高级业务构件的二次开发:</strong> </font>
								<FONT class="OecText">本部分是针对在线定制开发的补充，它能更进一步深入中间件内部，实现更加灵活更加强大的二次开发。这些二次开发的模块是很容易可以被中间件集成的。</FONT>
								
							</td>

						</tr>
						<tr>

						<tr>
							<td height="60">
								<A HREF="javascript:" onClick='listStyle()'><FONT
									class="OecLink">创建/管理样式</FONT></A>:
								<FONT class="OecText">使用分步向导来创建新样式。样式在页组中被使用到,并且自动生效到所有隶属于该组的页中。</FONT>
							</td>
							<td>
								<A HREF="javascript:"
									onclick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=template&appname=TEMPLATE','_blank')"><FONT
									class="OecLink">创建/管理业务元</FONT></A>:
								<FONT class="OecText">业务元用于封装业务元素，在这里您可以定义功能业务脚本和展现脚本，然后被集成到流程/任务中调度使用。</FONT>
							</td>
						</tr>
						<tr>
							<td height="60">
								<A HREF="javascript:"
									onClick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=jspapp&appname=JSPAPP')"><FONT
									class="OecLink">创建PORTLET</FONT> </A>:在这里您可以创建基于JSP规范的PORTLET页程序,通过使用NETONE中间件提供的标签结合JSP&JSTL的相关技术来实现更加有效的页程序。

							</td>
							<td >

								<A HREF="javascript:"
									onClick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=bean&appname=BUSSBEAN')"><FONT
									class="OecLink">注册BEAN服务</FONT> </A>:BEAN服务是NETONE中间件提供的分布式规范协议，用于定制开发业务中间件在这里您可以在线集成Bean服务
								实现在线监控和管理。
							</td>
						</tr>

						<tr>

							<td height="60">
							    所有活动流程的实例列表，可在该页面上可视化的监控业务过程，执行业务操作逻辑
							   
								<A HREF="<portal:envget envkey="WEBSER_APPFRAME"/>/workList.do?method=onMainView2&mode=1&height=260&listtype=01&sortfield=&sort=desc&psize=20&appname=" target='_blank'><FONT
									class="OecLink">[待办]</FONT> </A>
								<A HREF="<portal:envget envkey="WEBSER_APPFRAME"/>workList.do?method=moreWorklist&mode=1&height=430&listtype=02&sortfield=&sort=&psize=20&appname=" target='_blank'><FONT
									class="OecLink">[已办]</FONT> </A>:
                               <A HREF="<portal:envget envkey="WEBSER_APPFRAME"/>workList.do?method=moreWorklist&mode=1&height=430&listtype=03&sortfield=&sort=&psize=20&appname=" target='_blank'><FONT
									class="OecLink">[已归档]</FONT> </A>:								
								<A HREF="<portal:envget envkey="WEBSER_APPFRAME"/>workList.do?method=moreWorklist&mode=1&height=260&listtype=00&sortfield=&sort=desc&psize=20" target='_blank'><FONT
									class="OecLink">[全部]</FONT> </A>:			

							</td>
							<td>
							<A HREF="/cmsWeb/PagelistpathRightSvl?pagename=appframelist&appname=APPFRAME" target='_blank'><FONT
									class="OecLink">应用框架定制</FONT> </A>:
								整合表单和流程提供全应用在线定制开发
								
							<a href='/Security3A/extinfo.jsp' target='_blank'> <font
							color='red'>[相关资料下载]</font> </a>关于更多Netone中间件应用开发材料下载
							</td>
						</tr><tr>
<td colspan="2">
<img src="image/syncx.jpg" height='150'>
</td>
</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
