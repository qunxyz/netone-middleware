<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>����һ</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<script type="text/javascript" src="<%=path%>/include/js/calendar.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript">
		//--Modify-START---���Զ���������ҵ���������޶�------------
		//��һ���ű�
		function forward(){
			document.additem.action="<%=path%>/SynSummary.do?task=Synbefore";
			document.additem.submit();
		}
		//��һ���ű�
		function nextsubmit(){
			if(document.getElementsByName("selectt")[0].checked){
			   document.all.stepmode.value="limittime";
			   document.all.limitcount.value="";
			   document.all.limitnumber.value="";
			   if(document.all.limittime.value==""){
			    	alert("ʱ�䲻��Ϊ��!");
			    	return;
			    }
			} else if(document.getElementsByName("selectt")[1].checked){
			   document.all.stepmode.value="limitcount";
			   if(document.all.limitcount.value==""){
			    	alert("��N������Ϊ��!");
			    	return;
			    }
			   if(isNaN(document.all.limitcount.value)){
			   		alert("��N������Ϊ����!");
			   		return;
			   }
			   document.all.limittime.value="";
			   document.all.limitnumber.value="";
			} else if(document.getElementsByName("selectt")[2].checked){
			   document.all.stepmode.value="limitnumber";
			    document.all.limittime.value="";
			    document.all.limitcount.value="";
			    if(document.all.limitnumber.value==""){
			    	alert("ѡ����Ϊ��!");
			    	return;
			    }
			}else if(document.getElementsByName("selectt")[3].checked){
			   document.all.stepmode.value="append";
			}
			 document.additem.action="<%=path%>/SynSummary.do?task=Syn";
			additem.submit();
		}
		function openwindow(){
			window.open("<%=path%>/SumSynSvl?task=check&chkid="+document.all.chkid.value);
		}
		//--Modify-END---���Զ���������ҵ���������޶�------------
		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="post" name="additem">
			<input type='hidden' name='parentid' value='${param.parentid}'>
			<input type='hidden' name='pagepath' value='${param.pagepath}'>
			<input type='hidden' name='donetime' value='${param.donetime}'>

			<input type="hidden" name="datasource" value="${so.datasource}">
			<input type="hidden" name="formname" value="${so.formname}">
			<input type="hidden" name="formnaturalname"
				value="${so.formnaturalname}">
			<input type="hidden" name="url" value="${so.url}">
			<input type="hidden" name="driver" value="${so.driver}">
			<input type="hidden" name="loginname" value="${so.loginname}">
			<input type="hidden" name="password" value="${so.password}">
			<input type="hidden" name="tablename" value="${so.tablename}">
			<input type="hidden" name="sqlvalue" value="${so.sqlvalue}">
			<input type="hidden" name="sqlview" value="${so.sqlview}">
			<input type="hidden" name="chinesename" value="${so.chinesename}">
			<input type="hidden" name="cname" value="${so.cname}">
			<input type="hidden" name="ctype" value="${so.ctype}">
			<input type="hidden" name="isall" value="${so.isall}">
			<input type="hidden" name="stepmode" value="${so.stepmode}">
			<input type="hidden" name="chkid" value="${so.chkid}">
			<input type="hidden" name="syntable" value="${syntable}">
			<input type="hidden" name="formcode" value="${formcode}">

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
										<A HREF="javascript:"><IMG
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
										<FONT CLASS="OecInlineInfo2">·��:&nbsp;${param.pagepath}
										</FONT> &nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">�� 1 ��, �� 3 ��&nbsp;&nbsp;</FONT>

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
						������ѡ��
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ: Ȼ�󵥻�����һ������</FONT>
				<!-- End --Title----���ܱ�������ʾ---->
				<BR>

				<!-- Start--Body---- �������ݶ���  --->
				<DIV
					style="margin-left: 5%; margin-right: 5%; margin-top: 20px; margin-bottom: 20px;">


					<input type='radio' name='selectt' value='limittime'
						<c:if test="${so.stepmode=='limittime'}">
						checked="checked"
					</c:if>>
					����ʱ�䣨ͨ��ʶ��ʱ�� ���Զ���������ݣ�
					<input type="text" name="limittime" value="${so.limittime}" readonly="readonly" onfocus="calendar();">
					<br>
					<br>
					<input type='radio' name='selectt' value='limitcount'
						<c:if test="${so.stepmode=='limitcount'}">
						checked="checked"
					</c:if>>
					�Զ����N��
					<input type="text" name="limitcount" value="${so.limitcount}">
					<br>
					<br>
					<input type='radio' name='selectt' value='limitnumber'
						<c:if test="${so.stepmode=='limitnumber'}">
						checked="checked"
					</c:if>>
					�ֹ�ѡ��
					<input type="text" name="limitnumber" value="${so.limitnumber}">
					<input type="button" value="ѡ��" onclick="openwindow();"><br><br>
					
										<input type='radio' name='selectt' value='append'
						<c:if test="${ so.stepmode=='append'}">
						checked="checked"
					</c:if>>
					����
				</DIV>
				<!-- End--Body---- �������ݶ���  --->

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
