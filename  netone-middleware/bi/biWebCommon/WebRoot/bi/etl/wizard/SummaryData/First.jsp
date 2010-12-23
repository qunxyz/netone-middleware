<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//本页的父节点ID
	String parentid = request.getParameter("parentid");
	//本页面所对应的路径(其中父节点ID与该路径是对应的,可以通过envService根据parentid来获得)
	String pagepath = request.getParameter("pagepath");
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>创建SQL表单</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT TYPE="text/javascript">
		//--Modify-START---可自定义修改结合您的业务需求来修订------------
		//下一步脚本
		function nextsubmit(){
			if(document.all.formname.value==""){
				alert("中文名称不能为空!");
				return false;
			}
			if(document.all.formnaturalname.value==""){
				alert("名称不能为空!");
				return false;
			}
			if(document.all.url.value==""){
				alert("url不能为空!");
				return false;
			}
			if(document.all.driver.value==""){
				alert("驱动不能为空!");
				return false;
			}
			if(document.all.loginname.value==""){
				alert("帐户不能为空!");
				return false;
			}
			if(document.all.password.value==""){
				alert("密码不能为空!");
				return false;
			}

			document.additem.submit();
		}
		//--Modify-END---可自定义修改结合您的业务需求来修订------------
		
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		
		function checkds(){
			window.open("<%=basePath%>PagelistpathRightSvl?pagename=datasourceDblist&appname=DATASOURCE");
		}
		
		function todo(naturalname,extendattribute,id){
			var s = extendattribute.split("#");
			if(s.length=4){
				document.all.driver.value=s[0];
				document.all.url.value=s[1];
				document.all.loginname.value=s[2];
				document.all.password.value=s[3];
			}
			document.all.datasource.value = naturalname;
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="<%=path%>/NextSummary.do?task=Step" METHOD="POST"
			name="additem">
			<input type='hidden' name='parentid' value='<%=parentid%>'>
			<input type='hidden' name='pagepath' value='<%=pagepath%>'>
			<!-- Step.jsp -->
			<input type="hidden" name="col" value="${so.col}">
			<input type="hidden" name="tablename" value="${so.tablename}">
			<input type="hidden" name="sqlvalue" value="${so.sqlvalue}">
			<input type="hidden" name="sqlview" value="${so.sqlview}">
			<!-- Step2.jsp -->
			<input type="hidden" name="level" value="${so.level}">
			<input type="hidden" name="dimdata" value="${so.dimdata}">
			<input type="hidden" name="selpoint" value="${so.selpoint}">
			<input type="hidden" name="chinesename" value="${so.chinesename}">
			<input type="hidden" name="cname" value="${so.cname}">
			<input type="hidden" name="ctype" value="${so.ctype}">



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
										<FONT CLASS="OecGlobalPageTitle">表单创建-SQL模式</FONT>
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
				<br>
				<FONT CLASS="OecInstructionText">帮助提示:点[选择数据源]按钮来选择或者管理数据源,
					然后单击“下一步”。</FONT>
				<br>
				<!-- End --Title----功能标题与提示---->
				<BR>

				<!-- Start--Body---- 功能内容定义  --->
				<table>
					<tr>
						<td>
							名称
						</td>
						<td>
							<input type="text" name="formnaturalname"
								value="${so.formnaturalname}" size='30'> [格式：字母开头后接字母或数字或下划线]
						</td>
					</tr>
					<tr>
						<td>
							中文名称
						</td>
						<td>
							<input type="text" name="formname" value="${so.formname}"
								size='30'> 
						</td>
					</tr>
					<tr>
						<td>
							选择数据源
						</td>
						<td>
							<input type="text" name="datasource" value="${so.datasource}"
								size='60'>
						</td>
						<td>
							<input type="button" value="选择" onclick="checkds();">
						</td>
					</tr>

					<tr>
						<td>
							URL
						</td>
						<td>
							<input type="text" name="url" value="${so.url}" size='60'>
						</td>
					</tr>
					<tr>
						<td>
							驱动
						</td>
						<td>
							<input type="text" name="driver" value="${so.driver}" size='60'>
						</td>
					</tr>
					<tr>
						<td>
							帐户
						</td>
						<td>
							<input type="text" name="loginname" value="${so.loginname}" size='60'>
						</td>
					</tr>
					<tr>
						<td>
							密码
						</td>
						<td>
							<input type="text" name="password" value="${so.password}" size='60'>
						</td>
					</tr>
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
