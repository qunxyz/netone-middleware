<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>����һ</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">

		<SCRIPT TYPE="text/javascript">
		//--Modify-START---���Զ���������ҵ���������޶�------------
		//��һ���ű�
		function nextsubmit(){
			if(document.getElementsByName("selectt")[0].checked){
			   document.additem.action="<%=path%>/SynSummary.do?task=Syn";
			   document.all.isall.value="yes";
			}else{
			   document.additem.action="<%=path%>/SynSummary.do?task=Synbefore2";
			   document.all.isall.value="no";
			}
			additem.submit();
		}
		//--Modify-END---���Զ���������ҵ���������޶�------------
		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="post" name="additem">
			<input type='hidden' name='parentid' value='${param.parentid}'>
			<input type='hidden' name='pagepath' value='${param.pagepath}'>
			<input type='hidden' name='donetime' value='${param.donetime}'>

			<input type="hidden" name="datasource" value="${so.datasource}">
			<input type="hidden" name="formname" value="${so.formname}">
			<input type="hidden" name="formnaturalname"
				value="${so.formnaturalname}">
			<input type="hidden" name="url" value="${so.url}">
			<input type="hidden" name="driver" value="${so.driver}">
			<input type="hidden" name="loginname" value="${so.loginname}">
			<input type="hidden" name="password" value="${so.password}">
			<input type="hidden" name="tablename" value="${so.tablename}">
			<input type="hidden" name="sqlvalue" value="${so.sqlvalue}">
			<input type="hidden" name="sqlview" value="${so.sqlview}">
			<input type="hidden" name="chinesename" value="${so.chinesename}">
			<input type="hidden" name="cname" value="${so.cname}">
			<input type="hidden" name="ctype" value="${so.ctype}">
			<input type="hidden" name="isall" value="${so.isall}">
			<input type="hidden" name="stepmode" value="${so.stepmode}">
			<input type="hidden" name="chkid" value="${so.chkid}">
			<input type="hidden" name="syntable" value="${syntable}">
			<input type="hidden" name="formcode" value="${formcode}">
			<input type="hidden" name="limitcount" value="${so.limitcount}">
			<input type="hidden" name="limittime" value="${so.limittime}">
			<input type="hidden" name="limitnumber" value="${so.limitnumber}">

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
										<FONT CLASS="OecGlobalPageTitle">��������</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF="javascript:"><IMG
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
										<FONT CLASS="OecInlineInfo2">·��:&nbsp;${param.pagepath}
										</FONT> &nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">�� 1 ��, �� 3 ��&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>



			<DIV
				style="margin-left: 8%; margin-right: 8%; margin-top: 20px; margin-bottom: 20px;">

				<!-- Start--Title----���ܱ�������ʾ---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						������ѡ��
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ: Ȼ�󵥻�����һ������</FONT>
				<!-- End --Title----���ܱ�������ʾ---->
				<BR>

				<!-- Start--Body---- �������ݶ���  --->
				<DIV
					style="margin-left: 5%; margin-right: 5%; margin-top: 20px; margin-bottom: 20px;">


					<input type='radio' name='selectt' value='yes'
						<c:if test="${so.isall==null || so.isall=='yes'}">
						checked="checked"
					</c:if>>
					�������
					<br>
					<br>
					<input type='radio' name='selectt' value='no'
						<c:if test="${so.isall=='no'}">
						checked="checked"
					</c:if>>
					�������
				</DIV>
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
