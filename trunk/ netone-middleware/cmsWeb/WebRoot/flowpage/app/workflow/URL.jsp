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
		<TITLE>����ҳӦ��</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
<SCRIPT type="text/javascript"
			src="<%=path%>\include\js\checkrs.js"></SCRIPT>
		<script type="text/javascript">
		
		//ѡ��ҳ��
		function selectpage(){
		  window.open('<%=basePathRoot%>wfweb/PagelistpathRightSvl?pagename=pagelist&appname=BUSSWF');
		}
		
		function selecthis(id){
			var funcvalue=document.getElementById("funcinfo").value;
			if(funcvalue=='list'){
		  		document.getElementById("linkvaluepage").value='/wfweb/listUseprocess.do?processid='+id;
			}else if(funcvalue=='disp'){
		  		document.getElementById("linkvaluepage").value='/wfweb/viewreadonlyprocess.do?processid='+id;
			}else if(funcvalue=='inst'){
		  		document.getElementById("linkvaluepage1").value='/wfweb/listRuntimeprocess.do?runtimeid='+id;
			}
		}
        
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
			var funcvalue=document.getElementById("funcinfo").value;
			if(funcvalue=='inst'){
			  $('url').value=$('linkvaluepage1').value;
			}else{
			  $('url').value=$('linkvaluepage').value;
			}
			
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
										<FONT CLASS="OecGlobalPageTitle">�������Ӧ��</FONT>
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
				style="margin-left:8%;margin-right:8%;margin-top:20px;margin-bottom:20px;">
				<FONT CLASS="OecHeaderSub"><NOBR>
						��Ŀ����ѡ��
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ: ��������ɡ���</FONT>
				<BR>
				<DIV
					style="margin-left:5%;margin-right:5%;margin-top:20px;margin-bottom:20px;">
					<input type='hidden' name='id' value='<%=id%>' />
					<INPUT type='hidden' name="url" value="" width='300'>
					<table CELLSPACING="4">
						<tr>
							<td>
								��&nbsp;&nbsp;&nbsp;&nbsp;��
							</td>
							<td>
								<INPUT type='text' name="naturalname" value="" width='200'>
							</td>
						</tr>
						<tr>
							<td>
								��ʾ����
							</td>
							<td>
								<INPUT type='text' name="name" value="" width='200'>
							</td>
						</tr>
						<tr>
							<td>
								����ѡ��
							</td>
							<td>
								<script type="text/javascript">
								function todox(){
								    document.getElementById('linkvaluepage').value='';
									var selet=document.getElementById('funcinfo').value;
									if(selet=='inst'){
										use2.style.display=''
										use1.style.display='none'
									}else{
										use2.style.display='none'
										use1.style.display=''									
									}
									if(selet=='worklist'){
									   document.getElementById('linkvaluepage').value='/wfweb-ext/worklist?reloadtime=10';
									}
								}
								</script>
								<select name='funcinfo' onChange='todox()'>
									<option value='disp'>
										ĳ����ʵ����ͼ
									</option>
									<option value='inst'>
										ĳ����ʵ������
									</option>
									<option value='list'>
										ĳ���̴�������
									</option>
									<option value='worklist'>
										��������
									</option>
								</select>
							</td>
						</tr>
						<TR id='use1'>
							<TD>
								Ӧ&nbsp;&nbsp;&nbsp;&nbsp;��
							</TD>
							<TD>
								<input type='text' name='linkvaluepage' value='' size='40'>
								<input type='button' name='choicepage' value='ѡ������'
									onClick='selectpage()'>
							</TD>
						</TR>
						<TR id='use2' style='display:none'>
						<TD>
								Ӧ&nbsp;&nbsp;&nbsp;&nbsp;��
							</TD>
							<TD>
								<input type='text' name='linkvaluepage1' value='' size='40'>
								<input type='button' name='choicepage' value='ѡ������ʵ��'
									onClick='selectpage()'> <em>�Ƚ��������б�ĵ�[����ʵ��],������ѡ��һ������ʵ��</em>
							</TD>
						</TR>
						<tr>
							<td>
								����ģʽ
							</td>
							<td>
								<select name='mode' onChange='insermode()'>
									<option value='link'>
										����
									</option>
									<option value='disp'>
										Ƕ����ʾ
									</option>
								</select>
							</td>
						</tr>


						<tr id='disp' style='display:none'>
							<td colspan='2'>non &nbsp;&nbsp;&nbsp; 
								<input type='checkbox' name='appbar' value='yes'  />
								���ƴ��� &nbsp;&nbsp;���:
								<INPUT type='text' name="width" value="200" size='8'>

								&nbsp;&nbsp;�߶�:
								<INPUT type='text' name="height" value="300" size='8'>

							<br><br><br></td>
						</tr>
						<tr>
							<td>
								��&nbsp;&nbsp;&nbsp;&nbsp;��
							</td>
							<td>
								<textarea rows="5" cols="60" name='description'></textarea>
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
						<TD><%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
