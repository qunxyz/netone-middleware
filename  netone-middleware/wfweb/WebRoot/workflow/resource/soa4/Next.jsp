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
		<script type="text/javascript" src="include/js/calendar.js"></script>
		<script type="text/javascript" src="include/js/quartzlist.js"></script>
		<script type="text/javascript" src="include/js/workflow/soa4.js"></script>

		<SCRIPT TYPE="text/javascript">
		//��һ���ű�
		function forward(){
			if(document.all.show2.checked){
				createExtendAttribute();
			}
			document.forms[0].action="Show4.do?task=step";
			document.forms[0].submit();
		}

		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		//�����ű�
		function done(){
			//createExtendAttribute();
			//���ս�����ʱ����صĴ����߼���Ҫ�ύ��Servlet�����
			this.document.forms[0].action="Show4.do?task=done";
			//����صı�����ȫ�����ݸ������Servlet
			this.document.forms[0].submit();
		}
		function change(){
			if(document.all.show2.checked){
				document.all.show2.value="1";
				document.all.div1.style.display="";
			} else {
				document.all.show2.value="0";
				document.all.div1.style.display="none";
			}
		}
		</SCRIPT>

	</HEAD>
	<BODY bgcolor="#FFFFFF">
		<form action="" method="post" name="form1">
			<input type='hidden' name='parentid' value='${param.parentid}'>
			<input type='hidden' name='pagepath' value='${param.pagepath}'>

			<input type="hidden" name="chkid" value="${sf.chkid}">
			<input type="hidden" name="syn" value="${sf.syn}">
			<input type="hidden" name="actionurl" value="${sf.actionurl}">
			<input type="hidden" name="cdata" value="${sf.cdata}">
			<input type="hidden" name="title" value="${sf.title}">
			<input type="hidden" name="recevier" value="${sf.recevier}">
			<input type="hidden" name="extattr3" value="${sf.extattr3}">
			<input type="hidden" name="content" value="${sf.content}">
			<input type="hidden" name="show" value="${sf.show}">

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
													<INPUT type='button' value='��һ��>'
														onClick='javascript:done()'>
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
										<FONT CLASS="OecInlineInfo">�� 3 ��, �� 4 ��&nbsp;&nbsp;</FONT>

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
						���ȼƻ�����
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ: ��������һ������</FONT>
				<!-- End--Title----���ܱ�������ʾ---->
				<BR>
				<!-- Start--Body---- �������ݶ���  --->
				<table width="98%" border="0" cellpadding="0" cellspacing="0"
					bgcolor="#E0E0E0">
					<tr>
						<td class="title_bg">
							<input type="checkbox" name="show2" onclick="change();" value="${sf.show2}" ${sf.show2=='1' || sf.actionurl!=''?"checked":""}>
							��ʾ/���� ˵��������Ϊ��ʾ��û����Ϊ����
						</td>
					</tr>
				</table>
				<DIV ${sf.show2=='1' || sf.actionurl!=''?"":"style='display: none'"} id="div1">
					<table>
						<tr>
							<td width="15%">
								<textarea rows="8" cols="60" id="extendattribute"
									name="extendattribute" style="display: none">${upo.extendattribute}</textarea>
								����ʱ��
							</td>
							<td>
								&nbsp;&nbsp;&nbsp;����ʱ�䣺
								<input type="text" name="startTime" id="startTime" size="25"
									onfocus="calendar()">
								<script type="text/javascript">
						        var now=new Date();
						        document.getElementById("startTime").value=now.getYear()+"-"+now.getMonth()+"-"+now.getDay()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds();
						    </script>
							</td>
						</tr>
						<tr style="line-height: 200%">
							<td width="15%">
								������ʽ
							</td>
							<td>
								<br>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="chooseByRadio" value="chooseByDay"
									onclick="chooseByDay()" checked="checked">
								����ѡ��&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="chooseByRadio" value="chooseByWeek"
									onclick="chooseByWeek()">
								������ѡ��&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="hidden" name="chooseMode" id="chooseMode"
									value="chooseByDay">
								<br>
								<br>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="text" name="startYear" id="startYear" value="0"
									size="5"
									onkeyup="value=value.replace(/[^\d]/g,'');changeMonth();"
									onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"
									maxlength="4" onchange="changeMonth()">
								<script type="text/javascript">
					           //Ĭ����Ϊ��ǰ��
					           document.getElementById("startYear").value=now.getFullYear();
					       </script>
								&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;

								<select name="startMonth" id="startMonth"
									onchange="changeMonth()">
									<option value="*" checked="checked">
										ÿ&nbsp;&nbsp;
									</option>
									<c:forEach begin="1" end="12" step="1" var="totalMonth">
										<option value="${totalMonth}">
											${totalMonth}
										</option>
									</c:forEach>
								</select>
								��&nbsp;

								<span style="display: " id="selectDaySpan"> &nbsp;&nbsp;
									<select name="startDay" id="startDay">
										<option value="*" checked="checked">
											ÿ&nbsp;&nbsp;
										</option>
										<c:forEach begin="1" end="28" step="1" var="totalDay">
											<option value="${totalDay}">
												${totalDay}
											</option>
										</c:forEach>
									</select>��</span>

								<span style="display: none" id="selectWeekSpan"> ��<select
										name="startNumWeek" id="startNumWeek">
										<option value="1">
											1
										</option>
										<option value="2">
											2
										</option>
										<option value="3">
											3
										</option>
										<option value="4">
											4
										</option>
										<option value="5">
											5
										</option>
									</select> ������<select name="startWeek" id="startWeek">
										<option value="1">
											��
										</option>
										<option value="2">
											һ
										</option>
										<option value="3">
											��
										</option>
										<option value="4">
											��
										</option>
										<option value="5">
											��
										</option>
										<option value="6">
											��
										</option>
										<option value="7">
											��
										</option>
									</select> </span>
								<br>
								<br>
								&nbsp;&nbsp;&nbsp;
								<select name="startHour" id="startHour">
									<option value="*">
										ÿ&nbsp;&nbsp;
									</option>
									<c:forEach begin="00" end="23" step="1" var="hourOption">
										<option value="${hourOption}">
											${hourOption}
										</option>
									</c:forEach>
								</select>
								ʱ&nbsp;&nbsp;&nbsp;&nbsp;

								<select name="startMinute" id="startMinute">
									<option value="*">
										ÿ&nbsp;&nbsp;
									</option>
									<c:forEach begin="00" end="59" step="1" var="minuteOption">
										<option value="${minuteOption}">
											${minuteOption}
										</option>
									</c:forEach>
								</select>
								��&nbsp;&nbsp;&nbsp;&nbsp;

								<select name="startSecond" id="startSecond">
									<option value="*">
										ÿ&nbsp;&nbsp;
									</option>
									<c:forEach begin="00" end="59" step="1" var="secondOption">
										<option value="${secondOption}">
											${secondOption}
										</option>
									</c:forEach>
								</select>
								��&nbsp;&nbsp;

								<script type="text/javascript">
					           document.getElementById("startHour").value="8";
					           document.getElementById("startMinute").value="0";
					           document.getElementById("startSecond").value="0";
					       </script>
								<br>
								<br>
							</td>
						</tr>
						<tr>
							<td width="15%">
								����ʱ��
							</td>
							<td>
								<input type="hidden" id="stopMode" value="stopByCount">
								<div style="display: " id="stopByTimeDiv">
									<input type="text" name="stopTime" id="stopTime" size="25"
										onfocus="calendar()">
									<script type="text/javascript">
						            document.getElementById("stopTime").value=now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" "+now.getHours()+":"+(now.getMinutes()+1)+":"+"00";
						        </script>
									<br>
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
										<INPUT type='button' value='��һ��>' onClick='javascript:done()'>
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
			<script type="text/javascript">
				if(document.all.actionurl.value!=""){
					initPage();
				}
	        </script>
		</FORM>
	</BODY>
</HTML>
