<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
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

	String picid = request.getParameter("picid");
	String width = request.getParameter("width");
	width = width == null ? "" : width;
	String height = request.getParameter("height");
	height = height == null ? "" : height;
	String linkType = request.getParameter("linkType");
	String linkvalue = request.getParameter("linkvalue");
	linkvalue = linkvalue == null ? "" : linkvalue;
	String linkvaluepage = request.getParameter("linkvaluepage");
	linkvaluepage = linkvaluepage == null ? "" : linkvaluepage;
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
			window.location.href='First.jsp?picid=<%=picid%>&parentid=<%=parentid%>&pagepath=<%=pagepath%>';
		}
		//��һ���ű�
		function nextsubmit(){
			//����һ��ҳ�洫�ݲ���(��������parentid��pagepath�Ǳ����)
			var width=document.getElementById('width').value;
			var height=document.getElementById('height').value;
			var linkType=document.getElementById('linkType').value;
			var linkvalue=document.getElementById('linkvalue').value;
			var linkvaluepage=document.getElementById('linkvaluepage').value;
			
			var paramExt='&picid=<%=picid%>&width='+width+'&height='+height+'&linkType='+linkType+'&linkvalue='+linkvalue+'&linkvaluepage='+linkvaluepage;
			
			var param='?parentid=<%=parentid%>&pagepath=<%=pagepath%>'+paramExt;
			window.location.href='Done.jsp'+param;
		}
		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		//ѡ��ҳ��
		function selectpage(){
		  window.open('<%=basePath%>/PagelistpathRightSvl?pagename=page&appname=PPAGE');
		}
		
		function selecthis(id){
		  	document.getElementById("linkvaluepage").value='<%=basePath%>/servlet/PageService?cellid='+id;
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF" onLoad='javascript:select()'>
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
										<FONT CLASS="OecInlineInfo">�� 2 ��, �� 3 ��&nbsp;&nbsp;</FONT>

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
						ͼƬ��ʽ����
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ: Ȼ�󵥻�����һ������</FONT>
				<BR>
				<DIV
					style="margin-left:5%;margin-right:5%;margin-top:20px;margin-bottom:20px;">
					<TABLE BORDER="0">
						<TR>
							<TD>
								&nbsp;�߶�
							</TD>
							<TD>
								<input type='text' name='height' value='<%=height%>'>
							</TD>
						</TR>
						<TR>
							<TD>
								&nbsp;���
							</TD>
							<TD>
								<input type='text' name='width' value='<%=width%>'>
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
									value='<%=linkType%>'>
									<option value='normal'>
										��ͨ
									</option>
									<option value='oecpage'
										<%if("oecpage".equals(linkType)){out.print("selected");}%>>
										ҳ
									</option>
								</select>
								<input type='text' name='linkvalue' value='<%=linkvalue%>'>
								<input type='text' name='linkvaluepage' style='display:none'
									value='<%=linkvaluepage%>'>
								<input type='button' name='choicepage' value='ѡ��ҳ'
									onClick='selectpage()' style='display:none'>
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
