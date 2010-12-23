<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//本页的父节点ID
	String parentid = request.getParameter("parentid");
	//本页面所对应的路径(其中父节点ID与该路径是对应的,可以通过envService根据parentid来获得)
	String pagepath = request.getParameter("pagepath");
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>编辑SQL视图</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
						<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT type="text/javascript" src='<%=path%>/include/js/prototype.js'></SCRIPT>
		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){
			document.additem.action="<%=path%>/NextSummary.do?task=First";
			document.additem.submit();
		}
		//下一步脚本
		function nextsubmit(){
			//往下一个页面传递参数(基础参数parentid和pagepath是必须的)
			var param="?parentid=<%=parentid%>&pagepath=<%=pagepath%>";
			var sqlview = document.all.sqlview.value;
			if(sqlview==""){
				alert("SQL视图不能为空!");
				return false;
			}
			document.additem.submit();
		}
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		function columnListview() {
			var paramer = "task=showsql&tablename="+$("tablename").value+"&url="+$("url").value+"&driver="+$("driver").value+"&loginname="+$("loginname").value+"&password="+$("password").value;
			var url = $("pathinfo").value + "/NextSummary.do";
			var parser = new Ajax.Request(url, {method:"get", parameters:"" + paramer, asynchronous:false});
			var restr = parser.transport.responseText;
			var html = restr.substring(0,restr.indexOf("$"));
			var sql = restr.substring(restr.indexOf("$")+1,restr.length);
			document.getElementById("columnname").innerHTML = html;
			document.all.sqlvalue.value = sql;
			changecol();
		}
		function changecol(){
			document.all.col.value = document.all.columns.value;
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="<%=path%>/NextSummary.do?task=Step2" METHOD="POST"
			name="additem">
			<input type='hidden' name='parentid' value='<%=parentid%>'>
			<input type='hidden' name='pagepath' value='<%=pagepath%>'>
			<!-- Frist.jsp -->
			<input type="hidden" name="datasource" value="${so.datasource}">
			<input type="hidden" name="formname" value="${so.formname}">
			<input type="hidden" name="formnaturalname"
				value="${so.formnaturalname}">
			<input type="hidden" name="url" value="${so.url}">
			<input type="hidden" name="driver" value="${so.driver}">
			<input type="hidden" name="loginname" value="${so.loginname}">
			<input type="hidden" name="password" value="${so.password}">
			<!-- Step2.jsp -->
			<input type="hidden" name="level" value="${so.level}">
			<input type="hidden" name="dimdata" value="${so.dimdata}">
			<input type="hidden" name="selpoint" value="${so.selpoint}">
			<input type="hidden" name="chinesename" value="${so.chinesename}">
			<input type="hidden" name="cname" value="${so.cname}">
			<input type="hidden" name="ctype" value="${so.ctype}">

			<%@ include file="/include/page/PageHeadInfo.jsp"%>

			<CENTER>
				<input type='hidden' value='<%=path%>' name='pathinfo'>
				<TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0" WIDTH="90%">
					<TR>
						<TD>
							<TABLE WIDTH="100%" BORDER="0" CELLPADDING="1" CELLSPACING="0">
								<TR CLASS="OecBgColorDark">
									<TD>
										&nbsp;
									</TD>
									<TD WIDTH="100%" valign="center">
										<FONT CLASS="OecGlobalPageTitle">表单创建-SQL模式</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF=""><IMG
												SRC="<%=basePath%>/include/image/helpsb.gif" ALT="帮助"
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
										<FONT CLASS="OecInlineInfo2">路径:&nbsp;<%=pagepath%> </FONT>
										&nbsp;&nbsp;&nbsp;
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
						编辑SQL表单视图
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 在这里编辑SQL(根据您所需要的表单来编辑),您可以通过拷贝'当前选择的库表参考SQL视图'中的SQL到'编辑SQL视图'中继续改造来实现,SQL视图的编辑。需要特别注意:'编辑SQL视图'中的Form之前的字段中不允许出现以下别名的字段:<br>LSH,FORMCODE,PARTICIPANT,FATHERLSH,STATUSINFO,EXTENDATTRIBUTE,HIT 同时必须明确指明以下两个别名:BELONGX,TIMEX,其中BELONGX表示隶属信息,如部门,组织,分类.... TIMEX表示日期信息</FONT>
				<!-- End --Title----功能标题与提示---->
				<BR><BR>
				<!-- Start--Body---- 功能内容定义  --->
				<table>
					<tr>

						<td nowrap>
							库表资源
							<select name="tablename" onchange="columnListview();">
								<c:forEach items="${tablenamelist}" var="tab">
									${fn:split(tab,':')[1]}
									<option value="${fn:split(tab,':')[1]}" >
										${fn:split(tab,':')[0]}[${fn:split(tab,':')[1]}]
									</option>
								</c:forEach>
							</select>
						</td>
						<td nowrap>
						字段列表
						</td>
						<td nowrap>
							<div id="columnname">
							</div>
						</td>
						<td>
							<input type="text" name="col" value="${so.col}">
						</td>
					</tr>
					<tr>
						<td colspan="4">
						<br>
							当前选择的库表参考SQL视图
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<textarea rows="4" cols="100" name="sqlvalue">${so.sqlvalue }</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="4">
							编辑SQL视图
						</td>
					</tr>
					<tr>
						<td colspan="4">
							<textarea rows="5" cols="100" name="sqlview" title="双击打开编辑页面" ondblclick="window.open('<%=path%>/bi/etl/wizard/SummaryData/Stepedit.jsp?values='+document.all.sqlview.value)">${so.sqlview}</textarea>
						</td>
					</tr>
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
				columnListview();
			</script>
		</FORM>
	</BODY>
</HTML>
