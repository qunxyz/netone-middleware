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
		<TITLE>�༭ͼƬ����</TITLE>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">

		<SCRIPT TYPE="text/javascript">
		//��һ���ű�
		function forward(){
			//history.go(-1);
			window.location.href='First.jsp?picid=${param.picid}&parentid=${param.parentid}&pagepath=${param.pagepath}&cellid=${param.cellid}';
		}
		//��һ���ű�
		function nextsubmit(){
			document.forms[0].submit();
		}
		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		//ѡ��ҳ�� 
		function selectpage(){
		  window.open('<%=basePath%>/PagelistpathRightSvl?pagename=jspapp&appname=JSPAPP');
		}
		
		function selecthis(id){
		  	document.getElementById("linkvaluepage").value='<%=basePath%>/servlet/PageService?cellid='+id;
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF" onLoad='javascript:select()'>
		<FORM ACTION="<%=basePath%>servlet/AjaxPageItemSvl" METHOD="POST"
			name="additem">
			<input type='hidden' name='parentid' value='${param.parentid}'>
			<input type='hidden' name='pagepath' value='${param.pagepath }'>
			<input type='hidden' name='cellid' value='${param.cellid}'>
			<input type='hidden' name='picid' value='${param.picid}'>
			<input type='hidden' name='picinfo' value=''>
			<input type='hidden' name="objtype" value="pic">
			<input type='hidden' name='cname' value='${param.cname}'>
			<input type='hidden' name='naturalname' value='${param.naturalname}'>

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
										<FONT CLASS="OecGlobalPageTitle">ͼƬ��ʾ��ʽ</FONT>
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
													<INPUT TYPE="button" NAME="p_request" VALUE="���&nbsp;&gt;"
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
										<FONT CLASS="OecInlineInfo">�� 2 ��, �� 3 ��&nbsp;&nbsp;</FONT>

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
						ͼƬ��ʽ����
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ: Ȼ�󵥻�����һ������</FONT>
				<BR>
				<DIV
					style="margin-left: 5%; margin-right: 5%; margin-top: 20px; margin-bottom: 20px;">
					<TABLE BORDER="0">
						<TR>
							<TD>
								&nbsp;�߶�
							</TD>
							<TD>
								<input type='text' name='height' value='${param.height}'>
							</TD>
						</TR>
						<TR>
							<TD>
								&nbsp;���
							</TD>
							<TD>
								<input type='text' name='width' value='${param.width}'>
							</TD>
						</TR>
						<TR>
							<TD>
								&nbsp;����
							</TD>
							<TD>
								<script type="text/javascript">
									function select(){
										var link=document.getElementById('linkType').value;
										if(link=='oecpage'){
										 	document.getElementById('choicepage').style.display='';
										 	document.getElementById('linkvaluepage').style.display='';
										 	document.getElementById('linkvalue').style.display='none';
										 	
										}else{
										    document.getElementById('choicepage').style.display='none';
										    document.getElementById('linkvaluepage').style.display='none';
										    document.getElementById('linkvalue').style.display='';
										}
									}
								</script>
								<select name='linkType' onChange='javascript:select()'
									value='${param.linkType}'>
									<option value='normal'>
										��ͨ
									</option>
									<option value='oecpage'
										<c:if test="${param.linkType=='oecpage'}">selected</c:if>>
										ҳ
									</option>
								</select>
								<input type='text' name='linkvalue' value='${param.linkvalue}'>
								<input type='text' name='linkvaluepage' style='display: none'
									value='${param.linkvaluepage}'>
								<input type='button' name='choicepage' value='ѡ��ҳ'
									onClick='selectpage()' style='display: none'>
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
										<INPUT TYPE="button" NAME="p_request" VALUE="���&nbsp;&gt;"
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
