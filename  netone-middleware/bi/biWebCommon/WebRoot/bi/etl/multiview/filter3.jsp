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

<HTML>
	<HEAD>
		<TITLE>过滤3</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<script type="text/javascript"
			src="<%=basePath%>/include/js/calendar.js"></script>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript">
			//取消脚本
			function cancelsubmit(){
				window.close();
			}
			//结束脚本
			function done(){
				var sqlview = " and 1=1";
				for(var i=0 ; i<additem.elements.length ; i++) {
					if (additem.elements[i].name=="chkid") {
						if(additem.elements[i].value!=""){
							var tmpid = additem.elements[i].id;
							tmpid = tmpid.substring(tmpid.indexOf("{")+1,tmpid.indexOf("}"));
							if(additem.elements[i].title=="number"){
								sqlview+=" and "+tmpid+"="+additem.elements[i].value;
							} else {
								if(tmpid=='timex'){
									var level = additem.elements[i+1].value;
									var values = additem.elements[i].value;
									if(level=='1s'){
										sqlview+=" and "+tmpid+" like '$"+values+"$'";
									}
									if(level=='1m'){
										values = values.split(":");
										sqlview+=" and "+tmpid+" like '$"+values[0]+":"+values[1]+"$'";
									}
									if(level=='1h'){
										values = values.split(":");
										sqlview+=" and "+tmpid+" like '$"+values[0]+"$'";
									}
									if(level=='1d'){
										values = values.split(" ");
										sqlview+=" and "+tmpid+" like '$"+values[0]+"$'";
									}
									if(level=='1mo'){
										values = values.split("-");
										sqlview+=" and "+tmpid+" like '$"+values[0]+"-"+values[1]+"$'";
									}
									if(level=='1y'){
										values = values.split("-");
										sqlview+=" and "+tmpid+" like '$"+values[0]+"$'";
									}
								} else {
									sqlview+=" and "+tmpid+" like '$"+additem.elements[i].value+"$'";
								}
							}
						}
					}
				}
				if(window.opener && !window.opener.closed){
	   				opener.parent.window.location.href="<%=basePath%>flowpage.do?act=table&chkid="+document.all.openid.value+"&sqlview="+sqlview;
	   				window.close();
	   			}
			}
			var ele = null;
			function sselected(text,value){
				ele.previousSibling.value=text;
			}
			function openRS(that,naturalname){
				window.open('<%=basePath%>SSelectSvl?pagename=etl&appname='+naturalname);
				ele=that;
			}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
			<DIV
				style="margin-left: 8%; margin-right: 8%; margin-top: 20px; margin-bottom: 20px;">
				<!-- Start--Title----功能标题与提示---->
				<!-- End--Title----功能标题与提示---->
				<!-- Start--Body---- 功能内容定义  --->
				<table>
					<tr>
						<td>
							过滤条件:<input type="hidden" name="sb" value="${sb}">
							<input type="hidden" name="openid" value="${openid}">
							<input type="hidden" name="tmp" value="">
						</td>
						<td>
							<div id="div1">
							</div>
							<script type="text/javascript">
								var div1 = document.all.div1;
								var html = document.all.sb.value;
								div1.innerHTML = html;
							</script>
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
										<INPUT type='button' value='完成' onClick='javascript:done()'>
									</TD>
									<TD>
										<INPUT TYPE="button" NAME="p_request" VALUE="取消"
											onClick="javascript:cancelsubmit()">
									</TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
		</FORM>
	</BODY>
</HTML>
