<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String parentid = request.getParameter("parentid");
	String pagepath = request.getParameter("pagepath");
	String cellid = request.getParameter("id");
%>

<HTML>
	<HEAD>
		<TITLE>����ļ�</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">

		<SCRIPT TYPE="text/javascript">
		//��һ���ű�
		function forward(){
			history.go(-1);
		}
		//��һ���ű�
		function nextsubmit(){
			window.location.href='Done.jsp';
		}
		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		//�����ű�
		function done(){
			//���ս�����ʱ����صĴ����߼���Ҫ�ύ��Servlet�����
			this.document.forms[0].action='<%=basePath%>servlet/AjaxPageItemSvl';
			//����صı�����ȫ�����ݸ������Servlet
			this.document.forms[0].submit();
		}
		
		//����ļ���Ϣ
		function fetchFile(id,name,naturalname){
			document.getElementById("fileid").value=id;
			document.getElementById("name").value=name;
			//document.getElementById("naturalname").value=naturalname;
		}
		//���ļ�
		function openFile(){
			var url="<%=basePath%>"+"PagelistpathRightSvl?pagename=file&appname=FILE";
			window.open(url,'_blank','width=700,height=600,resizable=yes,left=200,top=180,status=yes');
		}
		</SCRIPT>
		<script type="text/javascript"
			src="<%=basePath%>/include/js/calendar.js"></script>


	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
			<input type='hidden' name='parentid' value='<%=parentid%>'>
			<input type='hidden' name='pagepath' value='<%=pagepath%>'>
			<input type='hidden' name='cellid' value='<%=cellid%>'>
			<input type='hidden' name='fileid' value=''>
			<input type='hidden' name='objtype' value='file'>
			<input type='hidden' name='naturalname' value='${param.naturalname }'>
			<input type='hidden' name='cname' value='${param.cname }'>
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
										<FONT CLASS="OecGlobalPageTitle">�������� STEP</FONT>
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
													<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;��һ��"
														onClick="javascript:forward()">
												</TD>
												<TD>
													<INPUT type='button' value='���' onClick='javascript:done()'>
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
										<FONT CLASS="OecInlineInfo">�� 1 ��, �� 1 ��&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<DIV
				style="margin-left: 8%; margin-right: 8%; margin-top: 20px; margin-bottom: 20px;">
				<FONT CLASS="OecHeaderSub"><NOBR>
						��Ŀ����ѡ��
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ: ��������ɡ���</FONT>
				<BR>
				<DIV
					style="margin-left: 5%; margin-right: 5%; margin-top: 20px; margin-bottom: 20px;">
					<TABLE BORDER="0">
						<TR>
							<TD>
								<input type='button' value=' ѡ �� �� �� '
									onClick="javascript:openFile()">
								��ʾ����:
								<INPUT type='text' name="name" value="" width='200'>
							</TD>

						</TR>
					</TABLE>
				</DIV>
			</DIV>
			<CENTER>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD ALIGN="RIGHT" valign="top" width="100%">
							<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
								<TR>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;��һ��"
											onClick="javascript:forward()">
									</TD>
									<TD>
										<INPUT type='button' value='���' onClick='javascript:done()'>
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
