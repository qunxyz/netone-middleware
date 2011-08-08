<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>步骤二</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<script type="text/javascript" src="include/js/workflow/soa2.js"></script>
		<script type="text/javascript" src="include/js/workflow/util.js"></script>
		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){	
			if(document.all.checks.value=="dy"){
				var alldytext = "";
				for(var i=0 ; i<form1.elements.length ; i++) {
					if (form1.elements[i].name=="dytext") {
						alldytext = alldytext+form1.elements[i].value+",";
					}
				}
				document.all.outdytext.value = alldytext;
			}
			if(document.all.checks.value=="wf"){
				var allwftext = "";
				for(var i=0 ; i<form1.elements.length ; i++) {
					if (form1.elements[i].name=="wftext") {
						allwftext = allwftext+form1.elements[i].value+",";
					}
				}
				document.all.outwftext.value = allwftext;
			}
			if(document.all.checks.value=="be"){
				var allbetext = "";
				for(var i=0 ; i<form1.elements.length ; i++) {
					if (form1.elements[i].name=="betext") {
						allbetext = allbetext+form1.elements[i].value+",";
					}
				}
				document.all.outbetext.value = allbetext;
			}
			document.forms[0].action="Show2.do?task=first";
			document.forms[0].submit();
		}
		//下一步脚本
		function nextsubmit(){
			if(document.all.checks.value=="dy"){
				var alldytext = "";
				for(var i=0 ; i<form1.elements.length ; i++) {
					if (form1.elements[i].name=="dytext") {
						alldytext = alldytext+form1.elements[i].value+",";
					}
				}
				document.all.outdytext.value = alldytext;
			}
			if(document.all.checks.value=="wf"){
				var allwftext = "";
				for(var i=0 ; i<form1.elements.length ; i++) {
					if (form1.elements[i].name=="wftext") {
						allwftext = allwftext+form1.elements[i].value+",";
					}
				}
				document.all.outwftext.value = allwftext;
			}
			if(document.all.checks.value=="be"){
				var allbetext = "";
				for(var i=0 ; i<form1.elements.length ; i++) {
					if (form1.elements[i].name=="betext") {
						allbetext = allbetext+form1.elements[i].value+",";
					}
				}
				document.all.outbetext.value = allbetext;
			}
			document.forms[0].action="Show2.do?task=next";
			document.forms[0].submit();
		}
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<form action="" method="post" name="form1">
			<input type='hidden' name='parentid' value='${param.parentid}'>
			<input type='hidden' name='pagepath' value='${param.pagepath}'>

			<input type="hidden" name="formcode" value="${sf.formcode}">
			<input type="hidden" name="processid" value="${sf.processid}">
			<input type="hidden" name="txt" value="${sf.txt}">
			<input type="hidden" name="chkid" value="${sf.chkid}">
			<input type="hidden" name="checks" value="${sf.checks}">
			<input type="hidden" name="outdytext" value="${sf.outdytext}">
			<input type="hidden" name="outwftext" value="${sf.outwftext}">
			<input type="hidden" name="outbetext" value="${sf.outbetext}">
			<input type="hidden" name="innewtext" value="${sf.innewtext}">
			<input type="hidden" name="cdata" value="${sf.cdata}">

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
										<FONT CLASS="OecGlobalPageTitle">内容主题 STEP</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF=""><IMG SRC="<%=basePath%>/image/helpsb.gif"
												ALT="帮助" BORDER="0"> </A>
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
													<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;上一步"
														onClick="javascript:forward()">
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="下一步&nbsp;&gt;"
														onClick="javascript:nextsubmit()">
												</TD>
												<TD>
													<INPUT TYPE="button" NAME="p_request" VALUE="取消"
														onClick="javascript:cancelsubmit()">
												</TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
									<TD ALIGN="LEFT">
										<FONT CLASS="OecInlineInfo2">路径:&nbsp;${param.pagepath}
										</FONT> &nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">第 2 步, 共 3 步&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<DIV
				style="margin-left:8%;margin-right:8%;margin-top:20px;margin-bottom:20px;">
				<!-- Start--Title----功能标题与提示---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						项目类型选择
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 然后单击“下一步”。</FONT>
				<!-- End --Title----功能标题与提示---->
				<BR>
				<!-- Start--Body---- 功能内容定义  --->
				<DIV
					style="margin-left:5%;margin-right:5%;margin-top:20px;margin-bottom:20px;">
					<table>
						<tr>
							<td>
								<select disabled="disabled">
									<option value="dy" ${sf.checks=='dy'?"selected":""}>
										动态表单
									</option>
									<option value="wf" ${sf.checks=='wf'?"selected":""}>
										工作流
									</option>
									<option value="be" ${sf.checks=='be'?"selected":""}>
										业务模块
									</option>
								</select>
							</td>
							<td>
								<input type="text" name="txt" value="${sf.txt}"
									readonly="readonly">
							</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" name="syn" value="${sf.syn}"
									onclick="checksyn();" ${sf.syn=='true'?"checked":""}>
								sync
							</td>
						</tr>
						<c:if test="${sf.checks=='dy'}">
							<c:forEach items="${list}" var="item">
								<tr>
									<td>
										<input type="text" name="dytext" value="">
									</td>
									<td>
										&nbsp;&nbsp;->&nbsp;&nbsp;${item.columname}[${item.columnid}]
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${sf.checks=='wf'}">
							<c:forEach items="${dfX}" var="df">
								<tr>
									<td>
										<input type="text" name="wftext" value="">
									</td>
									<td>
										&nbsp;&nbsp;->&nbsp;&nbsp;${df.name}[${df.id}]
									</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${sf.checks=='be'}">
							<c:forEach items="${list2}" var="map">
								<tr>
									<td>
										<input type="text" name="betext" value="">
									</td>
									<td>
										&nbsp;&nbsp;->&nbsp;&nbsp;${map.name}[${map.id}]
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
					<!-- End --Body---- 功能内容定义  --->
				</DIV>
				<CENTER>
					<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
						<TR>
							<TD ALIGN="RIGHT" valign="top" width="100%">
								<TABLE BORDER="0" CELLPADDING="1" CELLSPACING="4">
									<TR>
										<TD>
											<INPUT TYPE="button" NAME="p_request" VALUE="&lt;&nbsp;上一步"
												onClick="javascript:forward()">
										</TD>
										<TD>
											<INPUT TYPE="button" NAME="p_request" VALUE="下一步&nbsp;&gt;"
												onClick="javascript:nextsubmit()">
										</TD>
										<TD>
											<INPUT TYPE="button" NAME="p_request" VALUE="取消"
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
					if(document.all.checks.value=="dy"){
						var alldytext = document.all.outdytext.value;
						alldytext = alldytext.split(",");
						var dytext = document.getElementsByName("dytext");
						for(var i=0;i<alldytext.length;i++){
							dytext[i].value = alldytext[i];
						}
					}
					if(document.all.checks.value=="wf"){
						var allwftext = document.all.outwftext.value;
						allwftext = allwftext.split(",");
						var wftext = document.getElementsByName("wftext");
						for(var i=0;i<allwftext.length;i++){
							wftext[i].value = allwftext[i];
						}
					}
					if(document.all.checks.value=="be"){
						var allbetext = document.all.outbetext.value;
						allbetext = allbetext.split(",");
						var betext = document.getElementsByName("betext");
						for(var i=0;i<allbetext.length;i++){
							betext[i].value = allbetext[i];
						}
					}
				</script>
		</FORM>
	</BODY>
</HTML>
