<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>步骤二</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">

		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){	
			document.forms[0].action="First.jsp";
			document.forms[0].submit();
		}
		//下一步脚本
		function nextsubmit(){
			document.forms[0].action="Done.jsp";
			document.forms[0].submit();
		}
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="GET" name="additem">
			<input type='hidden' name='parentid' value='${param.parentid}'>
			<input type='hidden' name='pagepath' value='${param.pagepath}'>
			<input type='hidden' name='p_itemtype' value='${param.p_itemtype}'>
			<input type='hidden' name='donetime' value='${param.donetime}'>
			
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
										<A HREF=""><IMG SRC="<%=basePath%>/include/image/helpsb.gif"
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
										<FONT CLASS="OecInlineInfo2">路径:&nbsp;${param.pagepath} </FONT>
										&nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">第 2 步, 共 3 步&nbsp;&nbsp;</FONT>

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
				<FONT CLASS="OecInstructionText">帮助提示: 然后单击“下一步”。</FONT>
				<!-- End --Title----功能标题与提示---->
				<BR>
				<!-- Start--Body---- 功能内容定义  --->
				<DIV
					style="margin-left:5%;margin-right:5%;margin-top:20px;margin-bottom:20px;">
					<TABLE BORDER="0">
						<TR>
							<TD>
								<h3>[内容项目类型]:${param.p_itemtype}
								<img src='<%=basePath%>/include/image/oesee.gif'>
							</TD>

						</TR>
					</TABLE>
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
