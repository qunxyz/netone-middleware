<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>步骤一</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<script type="text/javascript" src="include/js/workflow/soa2.js"></script>
		<SCRIPT TYPE="text/javascript">
		//--Modify-START---可自定义结合您的业务需求来修订------------
		//下一步脚本
		function nextsubmit(){
			if(checksubmit()){
				var select=document.getElementById('checks').value;
				if(select=='dy'){
					document.all.outwftext.value="";
					document.all.outbetext.value="";
				}
				if(select=='wf'){
					document.all.outdytext.value="";
					document.all.outbetext.value="";
				}
				if(select=='be'){
					document.all.outdytext.value="";
					document.all.outwftext.value="";
				}
				document.forms[0].action="Show2.do?task=step";
				document.forms[0].submit();
			}
		}
		//--Modify-END---可自定义结合您的业务需求来修订------------
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		function sselected(text,value){
			document.getElementById('txt').value=text+'['+value+']';
		}
		function todo(){
			var select=document.getElementById('checks').value;
			if(select=='dy'){
				window.open('SSelectSvl?appname=BUSSFORM&pagename=applist');
			}
			if(select=='wf'){
				window.open('SSelectSvl?appname=BUSSWF&pagename=applist');
			}
			if(select=='be'){
				window.open('SSelectSvl?appname=BUSSBEAN&pagename=applist');
			}
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<form action="" method="post" name="form1">
			<input type='hidden' name='parentid' value='${param.parentid}'>
			<input type='hidden' name='pagepath' value='${param.pagepath}'>

			<input type="hidden" name="chkid" value="${sf.chkid}">
			<input type="hidden" name="syn" value="${sf.syn}">
			<input type="hidden" name="formcode" value="${sf.formcode}">
			<input type="hidden" name="processid" value="${sf.processid}">
			<input type="hidden" name="outdytext" value="${sf.outdytext}">
			<input type="hidden" name="outwftext" value="${sf.outwftext}">
			<input type="hidden" name="outbetext" value="${sf.outbetext}">
			<input type="hidden" name="innewtext" value="${sf.innewtext}">
			<input type="hidden" name="cdata" value="${sf.cdata}">

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
										<FONT CLASS="OecGlobalPageTitle">内容主题</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF="javascript:"><IMG
												SRC="<%=basePath%>/image/helpsb.gif" ALT="帮助" BORDER="0">
										</A>
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
													<INPUT TYPE="button" NAME="p_request" VALUE="下一步&nbsp;&gt;"
														onClick="javascript:nextsubmit()">
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
										<FONT CLASS="OecInlineInfo">第 1 步, 共 3 步&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>



			<DIV
				style="margin-left:8%;margin-right:8%;margin-top:20px;margin-bottom:20px;">

				<!-- Start--Title----功能标题与提示---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						项目类型选择
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示:从以下两个组中选择要添加到此页的项目类型,
					然后单击“下一步”。</FONT>
				<!-- End --Title----功能标题与提示---->
				<BR>

				<!-- Start--Body---- 功能内容定义  --->
				<DIV
					style="margin-left:5%;margin-right:5%;margin-top:20px;margin-bottom:20px;">
					<table>
						<tr>
							<td>
								<select name="checks" onchange="document.all.txt.value='';">
									<option value="dy" ${sf.checks=='dy'?"selected":""}>
										动态表单
									</option>
									<option value="wf" ${sf.checks=='wf'?"selected":""}>
										工作流
									</option>
									<option value="be" ${sf.checks=='be'?"selected":""}>
										业务Bean
									</option>
								</select>
							</td>
							<td>
								<input type="text" name="txt" value="${sf.txt}"
									readonly="readonly">
								<input type='button' value='选择' onClick='todo()' class="butt">
							</td>
						</tr>
					</table>
				</DIV>
				<!-- End--Body---- 功能内容定义  --->

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
											onClick="javascript:cancelsubmit()">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD>
							<%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>

		</FORM>
	</BODY>
</HTML>
