<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String parentid = request.getParameter("parentid");
	String pagepath = request.getParameter("pagepath");

	String width = request.getParameter("width");
	String height = request.getParameter("height");

	String linkvalue = request.getParameter("linkvalue");

	String picid = request.getParameter("picid");

	String linkInfo = "";

	String script = "<a href='" + linkvalue
			+ "' target='_blank'><img src='" + basePath
			+ "DownloadSvl?fileid=" + picid + "' width=" + width
			+ " height=" + height + "/></a>";
%>

<HTML>
	<HEAD>
		<TITLE>�����3</TITLE>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">

		<SCRIPT TYPE="text/javascript">
		//��һ���ű�
		function forward(){
			window.location.href='Step.jsp?width=<%=width%>&height=<%=height%>&linkvalue=<%=linkvalue%>&parentid=<%=parentid%>&pagepath=<%=pagepath%>&picid=<%=picid%>';

		}
		//��һ���ű�
		function nextsubmit(){
			alert('ͼƬ�������');
			window.close();
		}
		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		//�����ű�
		function done(){
			opener.location.href='';
			window.close();
			//���ս�����ʱ����صĴ����߼���Ҫ�ύ��Servlet�����
			this.document.forms[0].action='<%=basePath%>servlet/FlowpageSvl';
			//����صı�����ȫ�����ݸ������Servlet
			this.document.forms[0].submit();
		}
		</SCRIPT>
		<script type="text/javascript"
			src="<%=basePath%>/include/js/calendar.js"></script>


	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
			<input type='hidden' name='parentid' value='<%=parentid%>'>
			<input type='hidden' name='pagepath' value='<%=pagepath%>'>

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
										<FONT CLASS="OecInlineInfo">�� 3 ��, �� 3 ��&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<DIV
				style="margin-left:8%;margin-right:8%;margin-top:20px;margin-bottom:20px;">
				<FONT CLASS="OecHeaderSub"><NOBR>
						ϸ�ڴ���
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ: ��������ɡ���</FONT>
				<BR>
				<DIV
					style="margin-left:5%;margin-right:5%;margin-top:20px;margin-bottom:20px;">
					<TABLE BORDER="0">

						<TR>
							<TD>
								����:
								<form action='/cmsWeb/servlet/JppService' target='preivewpic'>
									<textarea name='bodyinfo' rows="8" cols="100">
										<%=script%>
									</textarea>
									<script type="text/javascript">
									function preview(){
										var valaue=document.getElementById("bodyinfo").value;
										document.getElementById("preview").innerHTML=valaue;
									}
									</script>
									<input type='button' value='Ԥ��' onClick='preview()'>
								</form>
							</TD>
						</TR>
						<tr>
							<td>
								<div id='preview'><%=script%></div>

							</td>
						</tr>
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
