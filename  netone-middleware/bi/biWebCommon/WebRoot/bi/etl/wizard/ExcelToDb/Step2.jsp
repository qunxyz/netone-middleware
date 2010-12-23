<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//本页的父节点ID
	String parentid = request.getParameter("parentid");
	//本页面所对应的路径(其中父节点ID与该路径是对应的,可以通过envService根据parentid来获得)
	String pagepath = request.getParameter("pagepath");
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>添加项2</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript">
			var list = new List();
			
			//上一步脚本
			function forward(){
				document.additem.action="<%=path%>/NextExcel.do?task=Step";
				document.additem.submit();
			}
			//下一步脚本
			function nextsubmit(){
				//往下一个页面传递参数(基础参数parentid和pagepath是必须的)
				var param="?parentid=<%=parentid%>&pagepath=<%=pagepath%>";
				if(list.size()==0){
					alert("选择字段不能为空!");
					return false;
				}
				document.all.checkedkey.value=list;
				document.additem.submit();
			}
			
			function docheck(){
				if(document.all.checkall.checked){
					var checkids = document.getElementsByTagName("input");
					for(var i=0 ; i<checkids.length ; i++){
						if(checkids[i].type=="checkbox"){
							if(checkids[i].checked){
								
							}else{
								checkids[i].checked=true;
							}
							if(list.indexOf(checkids[i].name)==-1 && checkids[i].name!="checkall"){
								list.add(checkids[i].name)
							}
						}
					}
					document.all.checkall.value="1";
				}
				if(document.all.checkall.checked==false){
					var checkids = document.getElementsByTagName("input");
					for(var i=0 ; i<checkids.length ; i++){
						if(checkids[i].type=="checkbox"){
							if(checkids[i].checked){
								checkids[i].checked=false;
							}
							if(list.indexOf(checkids[i].name)!=-1 && checkids[i].name!="checkall"){
								list.remove(list.indexOf(checkids[i].name));
							}
						}
					}
					document.all.checkall.value="0";
				}
			}
			function check(it){
				if(it.checked){
					if(list.indexOf(it.name)==-1){
						list.add(it.name);
					}
					var checkids = document.getElementsByTagName("input");
					var b = true;
					for(var i=0 ; i<checkids.length ; i++){
						if(checkids[i].type=="checkbox" && checkids[i].name!="checkall"){
							if(checkids[i].checked==false){
								b = false;
							}
						}
					}
					if(b==true){
						document.all.checkall.checked=true;
						document.all.checkall.value="1";
					}
				} else {
					if(list.indexOf(it.name)!=-1){
						list.remove(list.indexOf(it.name));
					}
					document.all.checkall.checked=false;
					document.all.checkall.value="0";
				}
			}
		</SCRIPT>


	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="<%=path%>/NextExcel.do?task=Done" METHOD="POST"
			name="additem">
			<input type='hidden' name='parentid' value='<%=parentid%>'>
			<input type='hidden' name='pagepath' value='<%=pagepath%>'>
			<!-- Frist.jsp -->
			<input type="hidden" name="datasource" value="${eo.datasource}">
			<input type="hidden" name="url" value="${eo.url}">
			<input type="hidden" name="driver" value="${eo.driver}">
			<input type="hidden" name="loginname" value="${eo.loginname}">
			<input type="hidden" name="password" value="${eo.password}">
			<input type="hidden" name="filename" value="${eo.filename}">
			<!-- Step.jsp -->
			<input type="hidden" name="sheetname" value="${eo.sheetname}">
			<!-- Step2.jsp -->
			<input type="hidden" name="tablename" value="${eo.tablename}">
			<input type="hidden" name="checkedkey" value="${eo.checkedkey}">
			
			
			
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
						项目类型选择
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 然后单击“下一步”。</FONT>
				<!-- End --Title----功能标题与提示---->
				<BR>
				<!-- Start--Body---- 功能内容定义  --->
				<table>
					<tr>
						<td>
							表名
						</td>
						<td>
							${eo.tablename}
						</td>
					</tr>
					<tr>
						<td>
							<input type="checkbox" name="checkall" value="${eo.checkall}" onclick="docheck();">
						</td>
						<td>
							全选
						</td>
					</tr>
					<c:forEach items="${map}" var="map">
						<tr>
							<td>
								<input type="checkbox" name="${map.key}" value="" onclick="check(this);">
							</td>
							<td>
								${map.key}
							</td>
						</tr>
					</c:forEach>
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
		</FORM>
		<script type="text/javascript">
			if(document.all.checkall.value=="1"){
				document.all.checkall.checked=true;
				docheck();
			} else {
				var checkedkey = document.all.checkedkey.value;
				checkedkey = checkedkey.split(",");
				for(var i=0;i<checkedkey.length;i++){
					document.getElementsByName(checkedkey[i])[0].checked=true;
					check(document.getElementsByName(checkedkey[i])[0]);
				}
			}
		</script>
	</BODY>
</HTML>
