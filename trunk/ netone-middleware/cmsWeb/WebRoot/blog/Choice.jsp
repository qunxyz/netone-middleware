<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String cellid = request.getParameter("id");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>添加元素</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script type="text/javascript">
		function nextsubmit(){
		    var type=document.getElementById("p_itemgroup").value;
		    var element='';
		    if(type==1){
		       element=document.getElementById("itemtype1").value;
		    }else{
		       element=document.getElementById("itemtype2").value;
		    }
		   	
			window.location.href=element+'id=<%=cellid%>';
		}
		</script>
	</head>

	<body>

		<DIV
			style="margin-left:8%;margin-right:8%;margin-top:20px;margin-bottom:20px;">
			<FONT CLASS="OraHeaderSub"><NOBR>
					项目类型选择
				</NOBR> </FONT>
			<img src="/images/beigepx.gif" border="0" height="1" width="100%"
				ALT="">
			<br>

			<DIV
				style="margin-left:5%;margin-right:5%;margin-top:20px;margin-bottom:20px;">
				<TABLE BORDER="0">
					<TR>
						<TD>
							<INPUT TYPE="radio" NAME="p_itemgroup" VALUE="1" CHECKED
								id=content>
						</TD>
						<TD>
							<label for="content">
								<FONT CLASS="OraFieldText">内容项目类型</FONT>
							</label>
						</TD>
						<TD>
							<LABEL FOR="itemtype1" style="display:none">
								项目类型
							</LABEL>
							<SELECT NAME="p_itemtype" id="itemtype1"
								onFocus="javascript:check_radio_button(0)">
								<OPTION VALUE="Article.jsp?">
									文本
								<OPTION VALUE="URL.jsp?">
									URL
								<OPTION VALUE="Page.jsp?">
									页链接
								<OPTION VALUE="Cut.jsp?">
									WEB抓取
								<OPTION VALUE="/cmsWeb/flowpage/pic/First.jsp?parentid=choice&pagepath=choice.pic&">
									图像
								<OPTION VALUE="Zip.jsp?">
									Zip 文件
								<OPTION VALUE="File.jsp?">
									文件
							</SELECT>
						</TD>
					</TR>
					<TR>
						<TD>
							&nbsp;&nbsp;
						</TD>
					</TR>
					<TR>
						<TD>
							<INPUT TYPE="radio" NAME="p_itemgroup" VALUE="2" id=nav>
						</TD>
						<TD>
							<label for="nav">
								<FONT CLASS="OraFieldText">内置导航项目类型</FONT>
							</label>
						</TD>
						<TD>
							<LABEL FOR="itemtype2" style="display:none">
								项目类型
							</LABEL>
							<SELECT NAME="p_itemtype_list" id="itemtype2"
								onFocus="javascript:check_radio_button(1)">
								<OPTION VALUE="appinfo/DyFormInfo.jsp">
									表单内容
								<OPTION VALUE="appinfo/MessageInfo.jsp">
									消息内容
								<OPTION VALUE="appinfo/AnalysisInfo.jsp">
									报表内容
								<OPTION VALUE="appinfo/AnalysisInfo.jsp">
									图表内容
								<OPTION VALUE="appinfo/BlankScript.jsp">
									脚本内容
							</SELECT>
						</TD>
					</TR>
				</TABLE>
			</DIV>
			<FONT CLASS="OraFieldText">作为页组管理员, 您可以<A
				HREF=http://trssvr02.fj.cmcc/pls/portal/PORTAL.wwpob_PageGroup_ContentType.draw_select_content_type?p_siteid=53&p_language=zhs&p_backurl=PORTAL.wwv_additem.selectitemtype%3Fp_cornerid%3D1%26p_siteid%3D53%26p_containerpageid%3D1%26p_cornertype%3D%26p_subtype%3D%26p_itemtype%3D%26p_parentid%3D0%26p_topicid%3D%26p_topicsiteid%3D%26p_perspectiveid%3D%26p_nextpage%3D%26p_currcornerid%3D1%26p_regionid%3D2087%26p_language%3Dzhs%26p_looplink%3Dhttp%253A%252F%252Ftrssvr02.fj.cmcc%252Fportal%252Fpage%253F_pageid%253D53%252C1%2526_dad%253Dportal%2526_schema%253DPORTAL%2526_mode%253D16%26p_subtypeselected%3D%26p_seqcolumn%3D%26p_groupbyvalue%3D>配置可用项目类型的列表</A>。</FONT>
		</DIV>
		<CENTER>
			<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
				<TR>
					<TD ALIGN="RIGHT" valign="top" width="100%">
						<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
							<TR>
								<TD>
									<INPUT TYPE="button" NAME="p_request" VALUE="下一步&nbsp;&gt;"
										onClick="javascript:nextsubmit()">
								</TD>
								<TD>
									<INPUT TYPE="button" NAME="p_request" VALUE="取消"
										onClick="javascript:window.close();">
								</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
				<TR>
					<TD>
						<TABLE WIDTH="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0">
							<TR>
								<TD ALIGN="RIGHT">
									<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
										<TR>
											<TD ALIGN="RIGHT" CLASS="OraBgColorDark">
												<IMG SRC="/images/FFFFFFbr.gif" ALT="">
											</TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							<TR>
								<TD HEIGHT="14" CLASS="OraBgColorDark">
									&nbsp;
								</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
		</CENTER>
	</body>
</html>
