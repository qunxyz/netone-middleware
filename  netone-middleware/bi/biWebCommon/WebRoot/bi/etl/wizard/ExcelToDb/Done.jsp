<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String parentid = request.getParameter("parentid");
	String pagepath = request.getParameter("pagepath");
%>

<HTML>
	<HEAD>
		<TITLE>添加项3</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<script type="text/javascript" src="include/js/ExcelToDb/page.js"></script>
		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){
			document.additem.action="<%=path%>/NextExcel.do?task=Step2";
			document.additem.submit();
		}
		//下一步脚本
		function nextsubmit(){
			document.additem.submit();
		}
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		//结束脚本
		function done(){
			document.additem.action="<%=path%>/NextExcel.do?task=Finish";
			document.additem.submit();
		}
		
		</SCRIPT>

	</HEAD>
	<c:if test="${num!=null}">
		<script type="text/javascript">
			alert("变更了"+${num}+"条记录");
		</script>
	</c:if>
	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
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
			<input type="hidden" name="sheetname" value="${eo.sheetname}">
			<!-- Step2.jsp -->
			<input type="hidden" name="checkall" value="${eo.checkall}">
			<input type="hidden" name="tablename" value="${eo.tablename}">
			<input type="hidden" name="checkedkey" value="${eo.checkedkey}">




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
													<INPUT type='button' value='完成'
														onClick='javascript:window.close();'>
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
										<FONT CLASS="OecInlineInfo">第 3 步, 共 3 步&nbsp;&nbsp;</FONT>

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
				<FONT CLASS="OecInstructionText">帮助提示: 单击“完成”。</FONT>
				<!-- End--Title----功能标题与提示---->
				<BR>
				<!-- Start--Body---- 功能内容定义  --->
				<table>
					<tr>
						<td>
							编辑SQL视图
						</td>
					</tr>
					<tr>
						<td>
							<textarea rows="10" cols="100" name="sqlview">${eo.sqlview}</textarea>
							<INPUT type='button' value='查看数据' onClick='javascript:done()'>
						</td>
					</tr>
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
										<INPUT type='button' value='完成'
											onClick='javascript:window.close();'>
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
			<table align="center">
				<tr>
					<td align="center" colspan="3">
						数据列表
					</td>
				</tr>
				<tr>
					<c:forEach items="${columnlist}" var="lists">
						<td width="100" align="center">
							${lists}
						</td>
					</c:forEach>
				</tr>
				<c:forEach items="${list}" var="map">
					<tr>
						<c:forEach items="${map}" var="pre">
							<td align="center">
								${pre.value.value}
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
			<br>
			<script type="text/javascript">
					var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.additem);
					pginfo.write();
				</script>
			<br>
		</FORM>
	</BODY>
</HTML>
