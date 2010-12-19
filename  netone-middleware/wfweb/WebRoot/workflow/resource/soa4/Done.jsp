<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML>
	<HEAD>
		<TITLE>������</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<script type="text/javascript" src="include/js/workflow/soa4.js"></script>
		<SCRIPT TYPE="text/javascript">
		//��һ���ű�
		function forward(){
			document.forms[0].action="Show4.do?task=next";
			document.forms[0].submit();
		}

		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		//�����ű�
		function done(){
			if(document.all.show.value=='1'){
				if(!document.all.title.value){
					alert("��������⣡");
					return ;
				}
				if(!document.all.content.value){
					alert("����������!");
					return ;
				}
				if(!document.all.recevier.value){
					alert("��ѡ�������!");
					return ;
				}
				if(document.all.ext.value!=""){
					var ext = document.all.ext;
					var ids = "";
					for(var i=0;i<ext.options.length;i++){
						if(i==0){
							ids = ext.options[i].value+"="+ext.options[i].text;
						} else {
							ids = ids+","+ext.options[i].value+"="+ext.options[i].text;
						}
					}
					document.all.extattr3.value = ids;
				}
			}
			//���ս�����ʱ����صĴ����߼���Ҫ�ύ��Servlet����һ��
			this.document.forms[0].action="Show4.do?task=final";
			//����صı�����ȫ�����ݸ������Servlet
			this.document.forms[0].submit();
		}
		
		function addSelectedFile(options){
			var ext = document.all.ext;
			if(options){
				for(var i=0;i<options.length;i++){
					var op = new Option(options[i].text,options[i].value);
					ext.add(op);
				}
			}
		}
		function delfile(){
			var ext = document.all.ext;
			var i = ext.selectedIndex;
			if(i != -1){
				ext.remove(i);
			}
		}
		var selectmode='human';
		function openuserselect(){
			window.open('<%=basePath%>MSelectSvl?appname=DEPT&pagename=human','��Աѡ��')
		    selectmode='human';
		}
		function openfileselect(){
			window.open('<%=basePath%>MSelectSvl?appname=PIC,FILE&pagename=file','����ѡ��')
		    selectmode='file';
		}
		function mselected(options){
			if(options){
				var users = "";
				var userids = "";
				for(var i=0;i<options.length;i++){
					userids += options[i].value + "[" +options[i].text+ "],";
				}
				if(selectmode=='human'){
				  document.all.recevier.value = userids  ;
				}else{
				  addSelectedFile(options);
				}
			}
		}
		function manageresourse(){
		    window.open('<%=basePath%>ApplistRightSvl?pagename=applist','��Դ����');
		}
		function change(){
			if(document.all.show.checked){
				document.all.show.value="1";
				document.all.div1.style.display="";
			} else {
				document.all.show.value="0";
				document.all.div1.style.display="none";
			}
		}
		</SCRIPT>
	</HEAD>
	
	<c:if test="${success=='success'}">
		<script type="text/javascript">
			alert("���̽���!");
			window.close();
		</script>
	</c:if>
	<BODY bgcolor="#FFFFFF">
		<form action="" method="post" name="form1">
			<input type='hidden' name='parentid' value='${param.parentid}'>
			<input type='hidden' name='pagepath' value='${param.pagepath}'>
			
			<input type="hidden" name="chkid" value="${sf.chkid}">
			<input type="hidden" name="cdata" value="${sf.cdata}">
			<input type="hidden" name="syn" value="${sf.syn}">
			<input type="hidden" name="actionurl" value="${sf.actionurl}">
			<input type="hidden" name="show2" value="${sf.show2}">

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
										<FONT CLASS="OecGlobalPageTitle">SOA����</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF=""><IMG SRC="<%=basePath%>/image/helpsb.gif"
												ALT="����" BORDER="0"> </A>
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
										<FONT CLASS="OecInlineInfo2">·��:&nbsp;${param.pagepath}
										</FONT> &nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">�� 3 ��, �� 3 ��&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<DIV
				style="margin-left: 8%; margin-right: 8%; margin-top: 20px; margin-bottom: 20px;">
				<!-- Start--Title----���ܱ�������ʾ---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						��Ϣ֪ͨ
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ: ��������ɡ���</FONT>
				<!-- End--Title----���ܱ�������ʾ---->
				<BR>
				<!-- Start--Body---- �������ݶ���  --->
				<table width="98%" border="0" cellpadding="0" cellspacing="0"
					bgcolor="#E0E0E0">
					<tr>
						<td class="title_bg">
							<input type="checkbox" name="show" onclick="change();" value="${sf.show}" ${sf.show=='1'?"checked":""}>
							��ʾ/���� ˵��������Ϊ��ʾ��û����Ϊ����
						</td>
					</tr>
				</table>
				<DIV ${sf.show=='1'?"":"style='display: none'"} id="div1">
					<table width="96%" border="0" align="center" cellpadding="3"
						cellspacing="1">
						<tr>
							<td width="50" nowrap>
								����
							</td>
							<td align="left">
								<input type="text" name="title" value="${sf.title}"
									class="textinput_td">
							</td>
						</tr>
						<tr>
							<td width="50" nowrap>
								������
							</td>
							<td align="left">
								<input type="text" name="recevier" value="${sf.recevier}"
									class="textinput_td">
								&nbsp;
								<a href='javascript:openuserselect()'>ѡ ��</a>
							</td>
						</tr>
						<tr>
							<td width="50" nowrap>
								����
							</td>
							<td align="left">
								<select name="ext">

								</select>
								<a href='javascript:openfileselect()'>ѡ ��</a> &nbsp;
								<a href='javascript:delfile()'>ɾ ��</a> &nbsp;&nbsp;
								<a href='javascript:manageresourse()'>��Դ����</a>
								<input type="hidden" name="extattr3" value="${sf.extattr3}">
							</td>
						</tr>
						<tr>
							<td width="50" nowrap>
								����
							</td>
							<td align="left">
								<textarea rows="5" cols="60" name="content">${sf.content}</textarea>
							</td>
						</tr>
					</table>
					<script type="text/javascript">
						var extattr3 = document.all.extattr3.value;
						var ext = document.all.ext;
						if(extattr3!=""){
							extattr3 = extattr3.split(",");
							for(var i=0;i<extattr3.length;i++){
								var files = extattr3[i].split("=");
								if(files[1]!=""){
									var option = new Option(files[1],files[0]);
									ext.add(option);
								}
							}
						}
					</script>
				</DIV>
				<!-- End --Body---- �������ݶ���  --->
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
