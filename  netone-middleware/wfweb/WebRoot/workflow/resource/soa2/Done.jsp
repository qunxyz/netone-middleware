<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML>
	<HEAD>
		<TITLE>������</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<script type="text/javascript" src="include/js/workflow/soa2.js"></script>
		<script type="text/javascript" src="include/js/workflow/prototype.js"></script>
		<SCRIPT TYPE="text/javascript">
		//��һ���ű�
		function forward(){
			document.forms[0].action="Show2.do?task=next";
			document.forms[0].submit();
		}

		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		//�����ű�
		function done(){
			//���ս�����ʱ����صĴ����߼���Ҫ�ύ��Servlet�����
			this.document.forms[0].action="Show2.do?task=final";
			//����صı�����ȫ�����ݸ������Servlet
			this.document.forms[0].submit();
		}
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid,extendattribute){
			document.getElementById('text0').value=name+'['+extendattribute+']';
			var paramer = "extend="+extendattribute+"&tmpcheck="+document.all.tmpcheck.value;
			var url = "<%=path%>/Show2.do";
			var parser = new Ajax.Request(url, {method:"get", parameters:"" + paramer, asynchronous:false});
			var restr = parser.transport.responseText;
			document.all.tmpselect.innerHTML = restr;
		}
		function sselected(text,value){
			document.getElementById('text0').value=text+'['+value+']';
			var paramer = "extend="+value+"&tmpcheck="+document.all.tmpcheck.value;
			var url = "<%=path%>/Show2.do";
			var parser = new Ajax.Request(url, {method:"get", parameters:"" + paramer, asynchronous:false});
			var restr = parser.transport.responseText;
			document.all.tmpselect.innerHTML = restr;
		}
		function todo(){
			var select=document.getElementById('tmpcheck').value;
			if(select=='dy'){
				window.open('SSelectSvl?appname=BUSSFORM&pagename=applist');
			}
			if(select=='wf'){
				window.open('SSelectSvl?appname=BUSSWF&pagename=applist');
			}
			if(select=='be'){
				window.open('SSelectSvl?appname=BUSSBEAN&pagename=applist');
			}
		}
		</SCRIPT>
		<script type="text/javascript"
			src="<%=basePath%>/include/js/calendar.js"></script>


	</HEAD>
	<c:if test="${result=='y'}">
		<script type="text/javascript">
			alert("���̽���!");
			window.close();
		</script>
	</c:if>
	<BODY bgcolor="#FFFFFF">
		<form action="" method="post" name="form1">
			<input type='hidden' name='parentid' value='${param.parentid}'>
			<input type='hidden' name='pagepath' value='${param.pagepath}'>

			<input type="hidden" name="formcode" value="${sf.formcode}">
			<input type="hidden" name="processid" value="${sf.processid}">
			<input type="hidden" name="chkid" value="${sf.chkid}">
			<input type="hidden" name="checks" value="${sf.checks}">
			<input type="hidden" name="syn" value="${sf.syn}">
			<input type="hidden" name="txt" value="${sf.txt}">
			<input type="hidden" name="outdytext" value="${sf.outdytext}">
			<input type="hidden" name="outwftext" value="${sf.outwftext}">
			<input type="hidden" name="outbetext" value="${sf.outbetext}">
			<input type="hidden" name="innewtext" value="${sf.innewtext}">

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
				style="margin-left:8%;margin-right:8%;margin-top:20px;margin-bottom:20px;">
				<!-- Start--Title----���ܱ�������ʾ---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						��Ŀ����ѡ��
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ: ������Ҫ���ҵ�񸽼ӽű��ı�д��</FONT>
				<!-- End--Title----���ܱ�������ʾ---->
				<BR>
				<!-- Start--Body---- �������ݶ���  --->
				<DIV
					style="margin-left:5%;margin-right:5%;margin-top:20px;margin-bottom:20px;">
					<table>
					<tr>
							<td colspan="3">
								<textarea rows="12" cols="90" name="cdata">${sf.cdata}</textarea>
							</td>
						</tr>
						<tr>
							<td nowrap>
								���ز���:
								<select name="result1"
									onchange="document.all.text1.value=this.value">
									<c:forEach items="${df}" var="df">
										<option value="${df.id}">
											${df.name}
										</option>
									</c:forEach>
								</select>
								<input type="text" name="text1" value="">
								<script type="text/javascript">
									document.all.text1.value = document.all.result1.value;
								</script>
								&nbsp;&nbsp; �������:
								<c:if test="${sf.checks=='dy'}">
									<select name="result2"
										onchange="document.all.text2.value=this.value">
										<c:forEach items="${list}" var="item">
											<option value="${item.columnid}">
												${item.columname}
											</option>
										</c:forEach>
									</select>
								</c:if>
								<c:if test="${sf.checks=='wf'}">
									<select name="result2"
										onchange="document.all.text2.value=this.value">
										<c:forEach items="${dfX}" var="df">
											<option value="${df.id}">
												${df.name}
											</option>
										</c:forEach>
									</select>
								</c:if>
								<c:if test="${sf.checks=='be'}">
									<select name="result2"
										onchange="document.all.text2.value=this.value">
										<c:forEach items="${list}" var="map">
											<option value="${map.id}">
												${map.name}
											</option>
										</c:forEach>
									</select>
								</c:if>
								<input type="text" name="text2" value="">
								<script type="text/javascript">
									document.all.text2.value = document.all.result2.value;
								</script>
								<br><br>
								����Ӧ�ò���:
								<select name="tmpcheck" onchange="document.all.text0.value='';">
									<option value="dy">
										��̬��
									</option>
									<option value="wf">
										������
									</option>
									<option value="be">
										ҵ��Bean
									</option>
								</select>
								<input type="text" name="text0" value="">
								<input type='button' value='ѡ��' onClick='todo()' class="butt">

								<div id="tmpselect">
								</div>
							</td>
						</tr>

					</table>
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
