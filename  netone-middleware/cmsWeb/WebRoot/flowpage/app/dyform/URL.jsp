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
		<TITLE>��ҳӦ��</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
			
			<!--�޸�/�ķ��� 2009-2-8 dxl -->
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		
			<!--�޸�/�ķ��� 2009-2-8 dxl -->
      <SCRIPT type="text/javascript"
			src="<%=path%>/include/js/checkrs.js"></SCRIPT>
			
		<script type="text/javascript">

		function selectFormColumn(){
			var formcode= document.getElementById("formcode").value;
			if(formcode==null||formcode==''){
				alert('����ѡ��һ����');
				return;
			}
			window.open('/dyForm/servlet/FormViewsvl?formcode='+formcode,'_blank');
		}
		//���ո��ڵ�
		function selectfatherlsh(lsh,formcode){
		
			if(document.getElementById('funcinfo').value=='view'){
				selecthis(lsh,formcode);
				
			}else{
				document.getElementById('fatherlsh').value=lsh;
			}
		}
		
		function selecthis(lsh,formcode){
			document.getElementById('viewlsh').value=lsh;
			document.getElementById('formcode').value=formcode;
			

		  	document.getElementById("linkvaluepage").value='/dyForm/data/showdata/display.do?lsh='+lsh+'&formcode='+formcode+'&pagestyle=${param.modelname}';
			
		}
		
		//ѡ��ҳ��
		function selectpoint(){
		  window.open('/biWeb/PagelistpathRightSvl?pagename=dyformlist&appname=BUSSFORM');
		}
		
		function selectpage(){
		  window.open('/biWeb/SSelectSvl?pagename=dyformlist&appname=BUSSFORM');
		}
		
		function sselected(name,id){
		    document.getElementById("formcode").value=id;
			var funcvalue=document.getElementById("funcinfo").value;
			var fatherlsh=document.getElementById('fatherlsh').value;
			if(fatherlsh==null||fatherlsh==''){
			  fatherlsh='1';//Ĭ��Ϊ1
			}
			if(funcvalue=='create'){
		  		document.getElementById("linkvaluepage").value='/dyForm/data/showdata/createview.do?fatherlsh='+fatherlsh+'&formcode='+id+'&pagestyle=${param.modelname}';
			}
			if(funcvalue=='list')
			{
				var mode=document.getElementById("modelist").value;
				if(mode=='useview'||mode=='onlyview'){
		  		document.getElementById("linkvaluepage").value='/dyForm/data/data/listShare.do?lsh='+fatherlsh+'&formcode='+id+'&pagestyle=${param.modelname}';
				
				}else if(mode=='viewlist'){
				document.getElementById("linkvaluepage").value='/dyForm/data/showdata/listviews.do?lsh='+fatherlsh+'&formcode='+id+'&pagestyle=${param.modelname}';
				}
				
				else{
		  		document.getElementById("linkvaluepage").value='/dyForm/data/data/list.do?lsh='+fatherlsh+'&formcode='+id+'&pagestyle=${param.modelname}';
				
				}
			}
			if(funcvalue=='listview')
			{
		  		document.getElementById("linkvaluepage").value='/dyForm/data/showdata/listviews.do?lsh='+fatherlsh+'&formcode='+id+'&pagestyle=${param.modelname}';
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
		
		    if(document.getElementById('funcinfo').value=='view'){
		    	var viewlsh=document.getElementById('viewlsh').value;
				if(viewlsh==null||viewlsh==''){
			  		alert('δѡ����ͼLSH');
			  		return;
				}
		    }
		
			if(document.getElementById('funcinfo').value=='list'){
			
			   var mode=document.getElementById("modelist").value;
			   $('url').value=$('linkvaluepage').value+"&mode="+mode;
			}else{
			   $('url').value=$('linkvaluepage').value;
			}
			var selectedcolumn=document.getElementById('columnselect').value;
		
			if(selectedcolumn!=''){
			   $('url').value+='&selectcolumn='+selectedcolumn;
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
			<input type='hidden' name='formcode' value=''>
			<!-- ����id��  ���portal��ӱ�Ӧ�� ��������  ԭ���Ҳ���id ��Ӧ��̨cellid -->
			<input type='hidden' name='id' id="id" value='${param.id}'>
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
										<FONT CLASS="OecGlobalPageTitle">>��ӱ�Ӧ�ó���</FONT>
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
					<!-- ����id 2009-2-8 dxl -->
					<input type='hidden' name='id' id="id" nvalue='<%=id%>' />
					<!-- ����id 2009-2-8 dxl -->
					<INPUT type='hidden' name="url" id="url" value="" width='300'>
					<table CELLSPACING="4">
						<tr>
							<td>
								��&nbsp;&nbsp;&nbsp;&nbsp;��
							</td>
							<td>
							<!-- ����id 2009-2-8 dxl -->
								<INPUT type='text' name="naturalname" id="naturalname" value="" width='200'>
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
						<tr>
							<td>
								����ѡ��
							</td>
							<td>
								<script type="text/javascript">
							function selectdo(){
								var funcinfo=document.getElementById('funcinfo').value;
								if(funcinfo=='view'){
									father.style.display='none'
									view.style.display='';
									formselect.style.display='none';
									
								}else{
									father.style.display=''
									view.style.display='none';	
									formselect.style.display='';					
								}
								
								if(funcinfo=='view'||funcinfo=='create'){
								   columnselectx.style.display='none';
								}else{
								   columnselectx.style.display='';
								}
							
								if(document.getElementById('funcinfo').value=='list'){
								    document.getElementById('modelist').style.display='';
								}else{
								    document.getElementById('modelist').style.display='none';
								}
							}
							</script>
							<!-- ����id 2009-2-8 dxl -->
								<select name='funcinfo' id="funcinfo"  onchange='selectdo()'>
									<option value='listview'>
										��ͼ�б�
									</option>
									<option value='list'>
										��¼�б�
									</option>
									<option value='create'>
										����
									</option>
									<option value='view'>
										��ͼ
									</option>


								</select>
								<!-- ����id 2009-2-8 dxl -->
								<select name='modelist' id="modelist" style='display: none'>
									<option value=''>
										����1
									</option>
									<option value='manage'>
										����2
									</option>
									<option value='useview'>
										Ӧ��
									</option>
									<option value='onlyview'>
										��ʾ
									</option>
									<option value='viewlist'>
										��ʾ��
									</option>
								</select>
							</td>
						</tr>
						<TR id='father'>
							<TD>
								��&nbsp;&nbsp;��&nbsp;��
							</TD>
							<TD>
							<!-- ����id 2009-2-8 dxl -->
								<input type='text' name='fatherlsh' id="fatherlsh" value='1' size='20'>

								<a href='javascript:selectpoint()'>ѡ�񸸽ڵ�</a>
								<em>Ĭ�ϸ��ڵ�Ϊ1,[ѡ�񸸽ڵ�]��ť����ѡ�񸸽ڵ㣬�ڴ򿪵ı�ѡ��ҳ�У���[Ӧ��1]��������ҳ��ſ�ѡ��</em>
							</TD>
						</TR>
						<TR id='view' style='display: none'>
							<TD>
								��ͼ�ڵ�
							</TD>
							<TD>
							<!-- ����id 2009-2-8 dxl -->
								<input type='text' name='viewlsh' id="viewlsh" value='' size='20'>

								<a href='javascript:selectpoint()'>ѡ����ͼ�ڵ�</a>
								<em>[ѡ����ͼ�ڵ�]��ť����ѡ��ڵ㣬�ڴ򿪵ı�ѡ��ҳ�У���[Ӧ��1]��������ҳ��ſ�ѡ��</em>
							</TD>
						</TR>
						<TR id='formselect'>
							<TD>
								Ӧ&nbsp;&nbsp;&nbsp;&nbsp;��
							</TD>
							<TD>
							<!-- ����id 2009-2-8 dxl -->
								<input type='text' name='linkvaluepage' id="linkvaluepage"  value='' size='40'>
								<!-- ����id 2009-2-8 dxl -->
								<input type='button' name='choicepage' id="choicepage" value='ѡ���'
									onClick='selectpage()'>
							</TD>
						</TR>
						<TR id='columnselectx' style='display:none'>
							<TD>
								�ֶ�ѡ��
							</TD>
							<TD>
								<!-- ����id 2009-2-8 dxl -->
								<input type='text' name='columnselect' id="columnselect" value='' size='40'>
								����ѡ��
								<a href='javascript:selectFormColumn()'><font color='blue'>[�ֶ�����]</font></a>
								�е�����ֶε�uuidֵ,��Ϊ��չ������Ҫ���ֶΣ�����ֶο��ö��ŷָ�
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
								&nbsp;&nbsp;&nbsp;&nbsp;
								<!-- ����id 2009-2-8 dxl -->
								<input type='checkbox' name='appbar' id="appbar" value='yes'  />
								���ƴ��� &nbsp;&nbsp;���:
								<!-- ����id 2009-2-8 dxl -->
								<INPUT type='text' name="width"  id="width" value="200" size='8'>

								&nbsp;&nbsp;�߶�:
								<!-- ����id 2009-2-8 dxl -->
								<INPUT type='text' name="height" id="height" value="300" size='8'>

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
							n<%@ include file="/include/page/PageEndInfo.html"%>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
