<%@ page language="java" pageEncoding="GBK"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//��ҳ�ĸ��ڵ�ID
	String parentid = request.getParameter("parentid");
	//��ҳ������Ӧ��·��(���и��ڵ�ID���·���Ƕ�Ӧ��,����ͨ��envService����parentid�����)
	String pagepath = request.getParameter("pagepath");
	String cellid = request.getParameter("id");
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>�����</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
     <!-- /���޸� 2009-2-8 ����» -->
     <SCRIPT type="text/javascript"
			src="<%=path%>/include/js/checkrs.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript">
		//--Modify-START---���Զ����޸Ľ������ҵ���������޶�------------
		//��һ���ű�
		function nextsubmit(){

		    var naturalname=document.getElementById('naturalname').value;
		    var rs=checkNaturalname(naturalname);
			if(rs!=''){
				alert(rs);
				return;
			}
		    	
		    if(document.getElementById('cname').value==''){
		    	alert('����д������');
		    	return;
		    }
		    var type=document.getElementById("p_itemgroup").value;
		    var url='';
		    if(type=='s1'){
		       url=document.getElementById("itemtype1").value;
		    }else if(type=='s2'){
		      var naturalnamefunc=document.getElementById("naturalnamefunc").value;
		      if(naturalnamefunc==''){
		      	alert('��ѡ��һ�����õ�����');
		      	return;
		      }
		      url='/cmsWeb/TemplateExeInItemSvl';
		    }else if(type=='s3'){
		       document.getElementById('objtype').value='OUTITEM';
		       url='/cmsWeb/servlet/AjaxPageItemSvl';
		    }
		   	
			this.document.forms[0].action=url;
			this.document.forms[0].submit();
		}
		//--Modify-END---���Զ����޸Ľ������ҵ���������޶�------------
		
		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		
		function sselected(id,value){
			document.getElementById('naturalnamefunc').value=value;
		}
		
		function sselecteditem(id,value){
		   document.getElementById('naturalnameitem').value=value;
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
			<input type='hidden' name='parentid' value='<%=parentid%>'>
			<input type='hidden' name='pagepath' value='<%=pagepath%>'>
			<input type='hidden' name='id' value='<%=cellid%>'>
			<input type='hidden' name='objtype' value=''>
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
										<FONT CLASS="OecInlineInfo">�� 0 ��, �� n ��&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>



			<DIV
				style="margin-left: 8%; margin-right: 8%; margin-top: 20px; margin-bottom: 20px;">

				<!-- Modify-START---���Զ����޸Ľ������ҵ���������޶�---------- -->
				<FONT CLASS="OecHeaderSub"><NOBR>
						��Ŀ����ѡ��
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ:��������������ѡ��Ҫ��ӵ���ҳ����Ŀ����,
					Ȼ�󵥻�����һ������</FONT>
				<BR>
				<DIV
					style="margin-left: 5%; margin-right: 5%; margin-top: 20px; margin-bottom: 20px;">
					<TABLE BORDER="0">
						<tr>
							<td>
								����
							</td>
							<td>
							    <!-- ����id 2009-2-8 ����» -->
								<input type='text' name='naturalname' id="naturalname"
									value='${param.naturalname}' />[��ʽ����ĸ��ͷ�����ĸ�����ֻ��»���]
							</td>
						</tr>
						<tr>
							<td>
								��������
							</td>
							<td>
							 <!-- ����id 2009-2-8 ����» -->
								<input type='text' name='cname' id="cname" value='${param.cname}' />
							</td>
						</tr>
						<TR>

							<TD>
								������ѡ��
							</TD>
							<TD>
								<table>
									<tr>
										<td>
											<script type="text/javascript">
								function view(){
									var selectvalue=document.getElementById('p_itemgroup').value;
									if(selectvalue=='s1'){
									  s1.style.display='';
									  s2.style.display='none';
									  s3.style.display='none';
									}else if(selectvalue=='s2'){
									  s1.style.display='none';
									  s2.style.display='';
									  s3.style.display='none';
									}else if(selectvalue=='s3'){
									  s1.style.display='none';
									  s2.style.display='none';
									  s3.style.display='';
									}
								}
								</script>
								       <!-- ����id 2009-2-8 ����» -->
											<select name="p_itemgroup" id="p_itemgroup" onChange='view()'>
												<option value='s1'>
													������
												</option>
												<option value='s2'>
													������
												</option>
												<option value='s3'>
													������
												</option>
											</select>
										</td>
										<td>

											<div id='s1'>
												<SELECT NAME="p_itemtype" id="itemtype1">
													<OPTION
														VALUE="article/Article.jsp?parentid=pageitem&pagepath=pageitem.article">
														�ı�
													<OPTION
													VALUE="<%=basePath%>flowpage/multi/URL.jsp?parentid=pageitem&pagepath=pageitem.multi">
													����ı�
													<OPTION
														VALUE="<%=basePath%>flowpage/url/URL.jsp?parentid=pageitem&pagepath=pageitem.url">
														URL
													<OPTION
														VALUE="<%=basePath%>flowpage/cut/Cut.jsp?parentid=pageitem&pagepath=pageitem.cut">
														WEBץȡ
													<OPTION
														VALUE="<%=basePath%>flowpage/pic/First.jsp?parentid=pageitem&pagepath=pageitem.pic">
														ͼ��
													<OPTION
														VALUE="<%=basePath%>flowpage/file/File.jsp?parentid=pageitem&pagepath=pageitem.file">
														�ļ�
													
												</SELECT>
											</div>
											<div id='s2' style='display: none'>
												<input type='text' value='' name='naturalnamefunc' />
												<input type='button' value='ѡ��'
													onClick="window.open('/cmsWeb/SSelectSvl?pagename=template&appname=TEMPLATE','_blank','height=600, width=800, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')">
											</div>
											<div id='s3' style='display: none'>
												<input type='text' value='' name='naturalnameitem' />
												<input type='button' value='ѡ��'
													onClick="window.open('/cmsWeb/SSelectSvl?pagename=pageitem&appname=PORTALPG','_blank','height=600, width=800, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')">

											</div>
										</td>
									</tr>
								</table>
							</TD>
						</TR>
						<tr>
							<td>
								��������
							</td>
							<td>
							<!-- ����id 2009-2-8 ����» -->
								<input name='cachecycle' id="cachecycle" type='text' /> (Сʱ)
							</td>
						</tr>

					</TABLE>
				</DIV>
				<br>
				<FONT CLASS="OecFieldText">��Ϊҳ�����Ա, ������<A
					HREF="/cmsWeb/PagelistpathRightSvl?pagename=template&appname=TEMPLATE"
					target='_blank'>�������ù�����</A>��</FONT>


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
