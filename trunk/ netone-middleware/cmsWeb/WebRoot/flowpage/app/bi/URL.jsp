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
		<TITLE>��Ӫ����ҳӦ��</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
<SCRIPT type="text/javascript"
			src="<%=path%>\include\js\checkrs.js"></SCRIPT>
		<script type="text/javascript">
		//ѡ��ҳ��
		function selectpage(){
		  
		  	var funcvalue=document.getElementById("funcinfo").value;
			if(funcvalue=='table'||funcvalue=='table1'){ 
		  		window.open('/biWeb/PagelistpathRightSvl?pagename=etl&appname=ETL');
			}

			if(funcvalue=='ana'||funcvalue=='ana1'){ 
				window.open('/biWeb/PagelistpathRightSvl?pagename=chart&appname=CHART');
			}

		}
		
		function selecthis(id,name){
			var funcvalue=document.getElementById("funcinfo").value;

			if(funcvalue=='table1'){ 
		  		document.getElementById("linkvaluepage").value='/biWeb/flowpage.do?act=table&chkid='+id;
		  
			}
			if(funcvalue=='table'){                            
		  		document.getElementById("linkvaluepage").value='/biWeb/tableView.jsp?name='+name+'&pagestyle=${param.modelname}';
			}	
			if(funcvalue=='ana'){
		  		document.getElementById("linkvaluepage").value='/biWeb/graphView.jsp?chkid='+id;
			}	
			if(funcvalue=='ana1'){
		  		document.getElementById("linkvaluepage").value='/biWeb/flowpage.do?act=table&task=chart0&chkid='+id;
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

		   if(funcvalue=='table1'){ 
		    	var funcappx1=document.getElementById('funcappx1');
		  		var funcappx2=document.getElementById('funcappx2');
		  		var funcappx3=document.getElementById('funcappx3');
		  		var funcappx4=document.getElementById('funcappx4');
		  		var funcappx5=document.getElementById('funcappx5');
		  		

		  		var addparam='';
		  		if(funcappx1.checked){
		  		  addparam+='&'+funcappx1.value+'=yes';
		  		}
		  		if(funcappx2.checked){
		  		  addparam+='&'+funcappx2.value+'=yes';
		  		}
		  		if(funcappx3.checked){
		  		  addparam+='&'+funcappx3.value+'=yes';
		  		}
		  		if(funcappx4.checked){
		  		  addparam+='&'+funcappx4.value+'=yes';
		  		}
		  		if(funcappx5.checked){
		  		  addparam+='&'+funcappx5.value+'=yes';
		  		}
		  		$('linkvaluepage').value=$('linkvaluepage').value+addparam;
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
										<FONT CLASS="OecGlobalPageTitle">��Ӿ�Ӫ����Ӧ��</FONT>
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
								function todo(){
									var selecfunc=document.getElementById('funcinfo').value;
									if(selecfunc=='table1'){
										act.style.display='';
									}else{
									    act.style.display='none';
									}
								}
								</script>
								<select name='funcinfo' onChange='todo()'>
									<option value='table'>
										��ͨ������
									</option>
									<option value='table1'>
										�߼�������
									</option>
									<option value='ana'>
										��ͨͼ�����
									</option>
									<option value='ana1'>
										�߼�ͼ�����
									</option>
								</select>
							</td>
						</tr>
						<TR id='act' style='display:none'>
							<TD>
								���õĹ���ѡ��
							</TD>
							<TD>
								<input type='checkbox' name='funcappx1' value='forcast'/>Ԥ�����
								<input type='checkbox' name='funcappx2' value='oneface'/>��Ƭ����
								<input type='checkbox' name='funcappx3' value='normalact'/>��ͨ����
								<input type='checkbox' name='funcappx4' value='superact'/>�߼�����
								<input type='checkbox' name='funcappx5' value='excek'/>Excel����
							</TD>
						</TR>				
						<TR>
							<TD>
								Ӧ&nbsp;&nbsp;&nbsp;&nbsp;��
							</TD>
							<TD>
								<input type='text' name='linkvaluepage' value='' size='40'>
								<input type='button' name='choicepage' value='ѡ�����ģʽ'
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


						<tr id='disp' style='display: none'>
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
						<TD><%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
