<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String basePathRoot = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/";
	String parentid = request.getParameter("parentid");
	String pagepath = request.getParameter("pagepath");
	String id = request.getParameter("id");
%>

<HTML>
	<HEAD>
		<TITLE>�ⲿӦ�ü���</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">

		<!--  ԭʼ���� 2009-2-8	
		   <SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>-->

		<!-- �ĺ���� 2009-2-8 ����» -->
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		
		<!-- ԭʼ����  2009-2-8
			<SCRIPT type="text/javascript"
			src="<%=path%>\include\js\checkrs.js"></SCRIPT>	-->

		<!-- ����» 2009-2-8 /���޸� -->
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/checkrs.js"></SCRIPT>
		
		<script type="text/javascript">
        function insermode(){
        	var selectvalue=document.getElementById('mode').value;
        	if(selectvalue=='disp'){
        		document.getElementById("disp").style.display='';
        	}else{
        		document.getElementById("disp").style.display='none';
        	}
        }

		//��һ���ű�
		function forward(){
			history.go(-1);
		}

		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		//�����ű�
		function done(){
			var naturalname=document.getElementById("naturalname").value;	
			var rs=checkNaturalname(naturalname);
			if(rs!=''){
				alert(rs);
				return;
			}
			var name=document.getElementById("name").value;
			if(name==''){
				alert('ȱ������');
				return;
			}	
 			$('url').value=$('linkvaluepage').value;
			//���ս�����ʱ����صĴ����߼���Ҫ�ύ��Servlet�����
			this.document.forms[0].action='<%=basePath%>servlet/AjaxPageItemSvl?objtype=url';
		//����صı�����ȫ�����ݸ������Servlet
		this.document.forms[0].submit();
	}
</SCRIPT>

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
										<FONT CLASS="OecGlobalPageTitle">�ⲿӦ�ü���</FONT>
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
														onClick="javascript:forward();">
												</TD>
												<TD>
													<INPUT type='button' value='���'
														onClick="javascript:done();">
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="ȡ��"
														onClick="javascript:cancelsubmit();">
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
					<!-- ����id 2009-2-8 dxl -->
					<input type='hidden' name='id' id="id" value='<%=id%>' />
					<!-- ����id 2009-2-8 dxl -->
					<INPUT type='hidden' name="url" id="url" value="" width='300'>
					<table CELLSPACING="4">
						<tr>
							<td>
								��&nbsp;&nbsp;&nbsp;&nbsp;��
							</td>
							<td>
								<!-- ����id 2009-2-8 dxl -->
								<INPUT type='text' name="naturalname" id="naturalname" value=""
									width='200'>
								[��ʽ����ĸ��ͷ�����ĸ�����ֻ��»���]
							</td>
						</tr>
						<tr>
							<td>
								��ʾ����
							</td>
							<td>
								<!-- ����id 2009-2-8 dxl -->
								<INPUT type='text' name="name" id="name" value="" width='200'>
							</td>
						</tr>
						<TR id='use1'>
							<TD>
								Ӧ&nbsp;&nbsp;&nbsp;&nbsp;��URL
							</TD>
							<TD>
								<!-- ����id 2009-2-8 dxl -->
								<input type='text' name='linkvaluepage' id="linkvaluepage"
									value='' size='60'>
							</TD>
						</TR>
						<tr>
							<td>
								����ģʽ
							</td>
							<td>
								<!-- ����id 2009-2-8 dxl -->
								<select name='mode' id="mode" onChange='insermode()'>
									<option value='link'>
										����
									</option>
									<option value='disp'>
										Ƕ����ʾ
									</option>
								</select>
							</td>
						</tr>


						<tr id='disp' style='display: none'>
							<td colspan='2'>
								&nbsp;&nbsp;&nbsp;
								<input type='checkbox' name='appbar' value='yes' />
								���ƴ��� &nbsp;&nbsp;���:

								<!-- ����id 2009-2-8 dxl -->
								<INPUT type='text' name="width" id="width" value="200" size='8'>

								&nbsp;&nbsp;�߶�:

								<!-- ����id 2009-2-8 dxl -->
								<INPUT type='text' name="height" id="height" value="200"
									size='8'>

								<br>
								<br>
								<br>
							</td>
						</tr>
						<tr>
							<td>
								��&nbsp;&nbsp;&nbsp;&nbsp;��
							</td>
							<td>
								<!-- ����id 2009-2-8 dxl -->
								<textarea rows="5" cols="60" name='description' id="description"></textarea>
							</td>
						</tr>

					</table>
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
											onClick=javascript:forward();>
									</TD>
									<TD>
										<INPUT type='button' value='���' onClick="javascript:done();">
									</TD>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="ȡ��"
											onClick="javascript:cancelsubmit();">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
					<TR>
						<TD><%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
