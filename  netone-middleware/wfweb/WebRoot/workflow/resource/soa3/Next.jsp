<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML>
	<HEAD>
		<TITLE>步骤三</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<script type="text/javascript" src="include/js/quartzlist.js"></script>
		<script type="text/javascript" src="include/js/calendar.js"></script>
		<script type="text/javascript" src="include/js/workflow/soa3.js"></script>
		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){
		    if(document.getElementById("cronYear").value==""){
		        alert("年信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronWeek").value==""){
		        alert("星期信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronMonth").value==""){
		        alert("月信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronDay").value==""){
		        alert("日信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronHour").value==""){
		        alert("时信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronMinute").value==""){
		        alert("分信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronSecond").value==""){
		        alert("秒信息不能为空");
		        return false;
		    }
			createExtendAttribute();
			document.forms[0].action="Show3.do?task=step";
			document.forms[0].submit();
		}

		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		//下一步脚本
		function done(){
		    if(document.getElementById("cronYear").value==""){
		        alert("年信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronWeek").value==""){
		        alert("星期信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronMonth").value==""){
		        alert("月信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronDay").value==""){
		        alert("日信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronHour").value==""){
		        alert("时信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronMinute").value==""){
		        alert("分信息不能为空");
		        return false;
		    }
		    if(document.getElementById("cronSecond").value==""){
		        alert("秒信息不能为空");
		        return false;
		    }
			createExtendAttribute();
			//最终结束的时候相关的处理逻辑需要提交给Servlet来完成
			this.document.forms[0].action="Show3.do?task=done";
			//将相关的表单参数全部传递给上面的Servlet
			this.document.forms[0].submit();
		}
		
		</SCRIPT>
	</HEAD>
	<BODY bgcolor="#FFFFFF">
		<form action="" method="post" name="form1">
			<input type='hidden' name='parentid' value='${param.parentid}'>
			<input type='hidden' name='pagepath' value='${param.pagepath}'>

			<input type="hidden" name="id" value="${sf.id}">
			<input type="hidden" name="appid" value="${sf.appid}">
			<input type="hidden" name="pagename" value="${sf.pagename}">
			<input type="hidden" name="naturalname" value="${sf.naturalname}">
			<input type="hidden" name="name" value="${sf.name}">
			<input type="hidden" name="description" value="${sf.description}">
			<input type="hidden" name="chkid" value="${sf.chkid}">
			<input type="hidden" name="syn" value="${sf.syn}">
			<input type="hidden" size="100" name="actionurl" id="actionurl"
				value="${sf.actionurl}">
			<input type="hidden" name="show" value="${sf.show}">
			<input type="hidden" name="cdata" value="${sf.cdata}">
			<input type="hidden" name="title" value="${sf.title}">
			<input type="hidden" name="recevier" value="${sf.recevier}">
			<input type="hidden" name="extattr3" value="${sf.extattr3}">
			<input type="hidden" name="content" value="${sf.content}">

			<%@ include file="/include/page/PageHeadInfo.jsp"%>


			<CENTER>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD>
							<TABLE WIDTH="100%" BORDER="0" CELLPADDING="1" CELLSPACING="0">
								<TR CLASS="OecBgColorDark">
									<TD>
										&nbsp;
									</TD>
									<TD WIDTH="100%" valign="center">
										<FONT CLASS="OecGlobalPageTitle">内容主题 STEP</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF=""><IMG SRC="<%=basePath%>/image/helpsb.gif"
												ALT="帮助" BORDER="0"> </A>
									</TD>
									<TD>
										&nbsp;
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="100%">
								<TR>
									<TD ROWSPAN="2" VALIGN="TOP">
										<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0">
											<TR>
												<TD VALIGN="TOP" CLASS="OecBgColorDark">

												</TD>
											</TR>
										</TABLE>
									</TD>
									<TD ROWSPAN="2" VALIGN="TOP">
										&nbsp;&nbsp;&nbsp;
									</TD>
									<TD ALIGN="RIGHT" valign="top" width="100%">
										<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
											<TR>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;上一步"
														onClick="javascript:forward()">
												</TD>
												<TD>
													<INPUT type='button' value='下一步>'
														onClick='javascript:done()'>
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="取消"
														onClick="javascript:cancelsubmit()">
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD ALIGN="LEFT">
										<FONT CLASS="OecInlineInfo2">路径:&nbsp;${param.pagepath}
										</FONT> &nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">第 3 步, 共 3 步&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<DIV
				style="margin-left: 8%; margin-right: 8%; margin-top: 20px; margin-bottom: 20px;">
				<!-- Start--Title----功能标题与提示---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						项目类型选择
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 单击“下一步”。</FONT>
				<!-- End--Title----功能标题与提示---->
				<BR>
				<!-- Start--Body---- 功能内容定义  --->
				<DIV
					style="margin-left: 5%; margin-right: 5%; margin-top: 20px; margin-bottom: 20px;">
					<table border='1'>
						<tr>
							<td width="15%">
								<textarea rows="8" cols="60" id="extendattribute"
									name="extendattribute" style="display: none">${upo.extendattribute}</textarea>
								启动时间
							</td>
							<td>
								<input type="text" name="startTime" id="startTime" size="25"
									onfocus="calendar()">
								<script type="text/javascript">
						        var now=new Date();
						        document.getElementById("startTime").value=now.getYear()+"-"+now.getMonth()+"-"+now.getDay()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
						    </script>
							</td>
						</tr>
						<tr style="line-height: 200%">
							<td width="15%">
								定时规则
							</td>
							<td>
								<table>
									<tr>
										<td colspan='4'>
											<input type="button" value="," onclick="addmark(',')">
											&nbsp;
											<input type="button" value="-" onclick="addmark('-')">
											&nbsp;
											<input type="button" value="*" onclick="addmark('*')">
											&nbsp;
											<input type="button" value="/" onclick="addmark('/')">
											&nbsp;
											<input type="button" value="?" onclick="addmark('?')">
											&nbsp;
											<input type="button" value="#" onclick="addmark('#')">
											&nbsp;
											<input type="button" value="L" onclick="addmark('L')">
											&nbsp;
											<input type="button" value="W" onclick="addmark('W')">
											&nbsp;
											<input type="button" value="C" onclick="addmark('C')">
											&nbsp;&nbsp;
											<a href="workflow/resource/soa3/grammar.html" target="_blank"><font color='blue'>[语法]</font></a>
										</td>
									</tr>
									<tr>
										<td>年
											<input type="text" name="cronYear" id="cronYear" value="*"
												onkeyup="value=value.replace(/[^\d\,\-\*\/\?\#]/g,'') "
												onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d\,\-\*\/\?\#]/g,''))"
												onfocus="javascript:document.getElementById('recent').value='cronYear';" style='width:80'>
											

										</td>
										<td>星期
											<input type="text" name="cronWeek" id="cronWeek" value="?"
												onfocus="javascript:document.getElementById('recent').value='cronWeek';" style='width:80'>
											
										</td>
										<td>月
											<input type="text" name="cronMonth" id="cronMonth" value="*"
												onfocus="javascript:document.getElementById('recent').value='cronMonth';" style='width:80'>
											
										</td>
										<td>日
											<input type="text" name="cronDay" id="cronDay" value="*"
												onfocus="javascript:document.getElementById('recent').value='cronDay';" style='width:80'>
											
										</td>
									</tr>
									<tr>
										<td>时
											<input type="text" name="cronHour" id="cronHour" value="*"
												onfocus="javascript:document.getElementById('recent').value='cronHour';" style='width:80'>
											
										</td>
										<td>分&nbsp;&nbsp;
											<input type="text" name="cronMinute" id="cronMinute"
												value="*"
												onfocus="javascript:document.getElementById('recent').value='cronMinute';" style='width:80'>
											
										</td>
										<td colspan='2'>秒
											<input type="text" name="cronSecond" id="cronSecond"
												value="*"
												onfocus="javascript:document.getElementById('recent').value='cronSecond';" style='width:80'>
											
										</td>

										<input type="hidden" id="recent">
										<input type="hidden" id="cron">

									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="15%">
								结束时间
							</td>
							<td>
								<div style="display: " id="stopByTimeDiv">

									<input type="text" name="stopTime" id="stopTime" size="25"
										onfocus="calendar()">
									<script type="text/javascript">
						            document.getElementById("stopTime").value=now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" "+now.getHours()+":"+(now.getMinutes()+1)+":"+"00";
						        </script>
									<br>
								</div>
							</td>
						</tr>
					</table>
				</DIV>
				<!-- End --Body---- 功能内容定义  --->
			</DIV>
			<CENTER>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD ALIGN="RIGHT" valign="top" width="100%">
							<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
								<TR>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;上一步"
											onClick="javascript:forward()">
									</TD>
									<TD>
										<INPUT type='button' value='下一步>' onClick='javascript:done()'>
									</TD>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="取消"
											onClick="javascript:cancelsubmit()">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD><%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<script type="text/javascript">
				if(document.all.actionurl.value!=""){
					initPage();
				}
	        </script>
		</FORM>
	</BODY>
</HTML>
