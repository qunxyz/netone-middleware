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
		<TITLE>JSPҳӦ��</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
<SCRIPT type="text/javascript"
			src="<%=path%>\include\js\checkrs.js"></SCRIPT>
		<script type="text/javascript">
		
		//ѡ��ҳ��
		function selectpage(){
		  window.open('<%=basePath%>/PagelistpathRightSvl?pagename=jspapp&appname=JSPAPP');
		}
		
		function selecthis(id,name,inclusion,parentdir,path){
		
			var seltype=document.getElementById('pagetype').value;
			if(seltype=='0'){
			   if(inclusion=='1'){
			   	  alert('��ͨҳ����ѡ��Ŀ¼');
			   	  return;
			   }
			   document.getElementById("linkvaluepage").value='/cmsWeb/'+id+'.jsp?pagestyle=${param.modelname}';
			}else if(seltype=='1'){
			   if(inclusion=='0'){
			   	  alert('��Դҳ����ҳĿ¼');
			   	  return;
			   }
			  document.getElementById("linkvaluepage").value='/cmsWeb/PagelistpathRightSvl?appname=DEPT&pagename='+name+'&pagestyle=${param.modelname}';
			}else if(seltype=='2'){
			   if(inclusion=='0'){
			   	  alert('��Դҳ����ҳĿ¼');
			   	  return;
			   }
			  document.getElementById("linkvaluepage").value='/cmsWeb/PagelistRightSvl?appname=DEPT&pagename='+name+'&pagestyle=${param.modelname}';
			}else if(seltype=='3'){
			   if(inclusion=='1'){
			   	  alert('�򵼲���ѡ��Ŀ¼');
			   	  return;
			   }
			   document.getElementById("linkvaluepage").value='/cmsWeb/'+id+'.jsp?parentid='+parentdir+'&pagepath='+path+'&pagestyle=${param.modelname}';
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
										<FONT CLASS="OecGlobalPageTitle">���JSPӦ��</FONT>
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
								ҳ����
							</td>
							<td>
								<select name='pagetype'>
									<option value='0'>��ͨ</option>
									<option value='3'>��ҳ</option>
									<option value='1'>��Դ·���б�ҳ</option>
									<option value='2'>��Դ�б�ҳ</option>
									
								</select> 
								<font size='2' color='#999999'>��Դ��ص�ҳ�Ĳ���appnameΪĬ��DEPT����Ҫ���ʵ�ʵ����޶�����ҳ�в���css�ɸ�����Ҫѡ��ͬ����ʽ</font>
							</td>
						</tr>
						<TR>
							<TD>
								Ӧ&nbsp;&nbsp;&nbsp;&nbsp;��
							</TD>
							<TD>
								<input type='text' name='linkvaluepage' value='' size='40'>
								<input type='button' name='choicepage' value='ѡ��ҳ'
									onClick='selectpage()'>
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
							<td colspan='2'>

								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type='checkbox' name='appbar' value='yes'  />
								���ƴ��� &nbsp;&nbsp;���:
								<INPUT type='text' name="width" value="200" size='8'>

								&nbsp;&nbsp;�߶�:
								<INPUT type='text' name="height" value="300" size='8'>

							</td>
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
						<TD>
							<%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
