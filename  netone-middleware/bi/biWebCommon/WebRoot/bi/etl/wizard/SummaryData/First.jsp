<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//��ҳ�ĸ��ڵ�ID
	String parentid = request.getParameter("parentid");
	//��ҳ������Ӧ��·��(���и��ڵ�ID���·���Ƕ�Ӧ��,����ͨ��envService����parentid�����)
	String pagepath = request.getParameter("pagepath");
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>����SQL��</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT TYPE="text/javascript">
		//--Modify-START---���Զ����޸Ľ������ҵ���������޶�------------
		//��һ���ű�
		function nextsubmit(){
			if(document.all.formname.value==""){
				alert("�������Ʋ���Ϊ��!");
				return false;
			}
			if(document.all.formnaturalname.value==""){
				alert("���Ʋ���Ϊ��!");
				return false;
			}
			if(document.all.url.value==""){
				alert("url����Ϊ��!");
				return false;
			}
			if(document.all.driver.value==""){
				alert("��������Ϊ��!");
				return false;
			}
			if(document.all.loginname.value==""){
				alert("�ʻ�����Ϊ��!");
				return false;
			}
			if(document.all.password.value==""){
				alert("���벻��Ϊ��!");
				return false;
			}

			document.additem.submit();
		}
		//--Modify-END---���Զ����޸Ľ������ҵ���������޶�------------
		
		//ȡ���ű�
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
										<FONT CLASS="OecGlobalPageTitle">������-SQLģʽ</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF=""><IMG
												SRC="<%=basePath%>/include/image/helpsb.gif" ALT="����"
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
													<INPUT TYPE="button" NAME="p_request" VALUE="��һ��&nbsp;&gt;"
														onClick="javascript:nextsubmit()">
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="ȡ��"
														onClick="javascript:cancelsubmit()">
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD ALIGN="LEFT">
										<FONT CLASS="OecInlineInfo2">·��:&nbsp;<%=pagepath%> </FONT>
										&nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">�� 1 ��, �� 3 ��&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>



			<DIV
				style="margin-left:8%;margin-right:8%;margin-top:20px;margin-bottom:20px;">

				<!-- Start--Title----���ܱ�������ʾ---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						����Դѡ��
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ:��[ѡ������Դ]��ť��ѡ����߹�������Դ,
					Ȼ�󵥻�����һ������</FONT>
				<br>
				<!-- End --Title----���ܱ�������ʾ---->
				<BR>

				<!-- Start--Body---- �������ݶ���  --->
				<table>
					<tr>
						<td>
							����
						</td>
						<td>
							<input type="text" name="formnaturalname"
								value="${so.formnaturalname}" size='30'> [��ʽ����ĸ��ͷ�����ĸ�����ֻ��»���]
						</td>
					</tr>
					<tr>
						<td>
							��������
						</td>
						<td>
							<input type="text" name="formname" value="${so.formname}"
								size='30'> 
						</td>
					</tr>
					<tr>
						<td>
							ѡ������Դ
						</td>
						<td>
							<input type="text" name="datasource" value="${so.datasource}"
								size='60'>
						</td>
						<td>
							<input type="button" value="ѡ��" onclick="checkds();">
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
							����
						</td>
						<td>
							<input type="text" name="driver" value="${so.driver}" size='60'>
						</td>
					</tr>
					<tr>
						<td>
							�ʻ�
						</td>
						<td>
							<input type="text" name="loginname" value="${so.loginname}" size='60'>
						</td>
					</tr>
					<tr>
						<td>
							����
						</td>
						<td>
							<input type="text" name="password" value="${so.password}" size='60'>
						</td>
					</tr>
				</table>
				<!-- End--Body---- �������ݶ���  --->

			</DIV>

			<CENTER>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD ALIGN="RIGHT" valign="top" width="100%">
							<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
								<TR>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="��һ��&nbsp;&gt;"
											onClick="javascript:nextsubmit()">
									</TD>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="ȡ��"
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
