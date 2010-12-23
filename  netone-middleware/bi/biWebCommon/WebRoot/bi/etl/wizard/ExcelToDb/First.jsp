<%@ page language="java" pageEncoding="GBK"%>
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
		<TITLE>添加项0</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">


		<SCRIPT TYPE="text/javascript">
		//--Modify-START---可自定义修改结合您的业务需求来修订------------
		//下一步脚本
		function nextsubmit(){
			document.additem.submit();
		}
		//--Modify-END---可自定义修改结合您的业务需求来修订------------
		
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		
		function checkds(){
			window.open("<%=basePath%>PagelistpathRightSvl?pagename=datasourceExcellist&appname=DATASOURCE");
		}
		
		function todo(naturalname,extendattribute,id){
			var s = extendattribute.split("#");
			if(s.length=4){
				document.all.driver.value=s[0];
				document.all.url.value=s[1];
				document.all.loginname.value=s[2];
				document.all.password.value=s[3];
			} else {
				alert("没有找到数据源!")
				return false;
			}
			document.all.datasource.value = naturalname;
			var filename = "<%=basePath%>DownloadSvl?fileid="+id;
			document.all.filename.value = filename;
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="<%=path%>/NextExcel.do?task=Step" METHOD="POST"
			name="additem">
			<input type='hidden' name='parentid' value='<%=parentid%>'>
			<input type='hidden' name='pagepath' value='<%=pagepath%>'>
			<!-- Frist.jsp -->
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
										<FONT CLASS="OecGlobalPageTitle">数据导入向导</FONT>
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
						数据源选择
					</NOBR> </FONT>
				<hr>
				<br><br>
				<FONT CLASS="OecInstructionText">帮助提示:数据源统一来自系统管理的数据块,我们可以通过点选择打开数据源选择,同时我们在选择打开的页面中还可以创建数据来源</FONT>
				<!-- End --Title----功能标题与提示---->
				<BR>

				<!-- Start--Body---- 功能内容定义  --->
				<table>
					<tr>
						<td>
							选择数据源
						</td>
						<td>
							<input type="text" name="datasource" value="${eo.datasource}" readonly>
						</td>
						<td>
							<input type="button"  value="选择" onclick="checkds();">
						</td>
				</table>
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
