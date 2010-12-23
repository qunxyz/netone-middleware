<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//本页的父节点ID
	String parentid = request.getParameter("parentid");
	//本页面所对应的路径(其中父节点ID与该路径是对应的,可以通过envService根据parentid来获得)
	String pagepath = request.getParameter("pagepath");
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>添加项1</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<SCRIPT type="text/javascript" src='<%=path%>/include/js/prototype.js'></SCRIPT>
		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){
			document.additem.action="<%=path%>/NextExcel.do?task=First";
			document.additem.submit();
		}
		//下一步脚本
		function nextsubmit(){
			//往下一个页面传递参数(基础参数parentid和pagepath是必须的)
			var param="?parentid=<%=parentid%>&pagepath=<%=pagepath%>";
			document.additem.submit();
		}
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="<%=path%>/NextExcel.do?task=Step2" METHOD="POST"
			name="additem">
			<input type='hidden' name='parentid' value='<%=parentid%>'>
			<input type='hidden' name='pagepath' value='<%=pagepath%>'>
			<!-- Frist.jsp -->
			<input type="hidden" name="datasource" value="${eo.datasource}">
			<input type="hidden" name="url" value="${eo.url}">
			<input type="hidden" name="driver" value="${eo.driver}">
			<input type="hidden" name="loginname" value="${eo.loginname}">
			<input type="hidden" name="password" value="${eo.password}">
			<input type="hidden" name="filename" value="${eo.filename}">
			<!-- Step.jsp -->
			<!-- Step2.jsp -->
			<input type="hidden" name="checkall" value="${eo.checkall}">
			<input type="hidden" name="tablename" value="${eo.tablename}">
			<input type="hidden" name="checkedkey" value="${eo.checkedkey}">
			
			
			<%@ include file="/include/page/PageHeadInfo.jsp"%>

			<CENTER>
				<input type='hidden' value='<%=path%>' name='pathinfo'>
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
										<A HREF=""><IMG
												SRC="<%=basePath%>/include/image/helpsb.gif" ALT="帮助"
												BORDER="0"> </A>
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
										<FONT CLASS="OecInlineInfo2">路径:&nbsp;<%=pagepath%> </FONT>
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
				<table>
					<tr>
						<td>
							表资源
						</td>
						<td>
							<select name="sheetname">
								<c:forEach items="${sheetname}" var="sheet">
									<option value="${sheet}" ${eo.sheetname==sheet?"selected":""}>
										${sheet}
									</option>
								</c:forEach>
							</select>
						</td>
				</table>
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
			<script type="text/javascript">
				columnListview();
			</script>
		</FORM>
	</BODY>
</HTML>
