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
		<TITLE>过滤2</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<script type="text/javascript"
			src="<%=basePath%>/include/js/calendar.js"></script>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){
			//history.go(-1);
			document.additem.action="<%=path%>/NextFilter.do?task=filter1";
			document.additem.submit();
		}
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		//结束脚本
		function done(){
			var allchkid="";
			var allorder="";
			var order_sql="";
			if(document.all.tgfiltvalue.value!=""){
				if(!isDigit(document.all.tgfiltvalue.value)){
	   				alert("最值必须为数字！");
	   				return false;
	   			}
			}
			
			var chkid = document.getElementsByName("chkid2");
			var order = document.getElementsByName("order");
			for(var i=0;i<chkid.length;i++){
				if(chkid[i].checked){
	    			allchkid += chkid[i].value+",";
	    			order_sql=order[i].value;
	    			allorder += order_sql+",";
				}
			}
			document.all.allchkid2.value=allchkid;
			document.all.allorder.value=allorder;
			if(window.opener && !window.opener.closed){
				var kk = "&sqlview="+document.all.sqlview.value+"&allchkid="+document.all.allchkid.value+"&allchkid2="+allchkid+"&alltop="+document.all.alltop.value+"&alltopvalue="+document.all.alltopvalue.value;
				kk += "&allalarm="+document.all.allalarm.value+"&alltxtalarm="+document.all.alltxtalarm.value+"&allorder="+allorder+"&tgfiltvalue="+document.all.tgfiltvalue.value;
   				opener.parent.window.location.href="<%=basePath%>flowpage.do?act=table&chkid="+document.all.openid.value+kk;
   				window.close();
   			} else {
   				document.additem.action="<%=basePath%>flowpage.do?act=table&chkid="+document.all.openid.value;
   				document.additem.submit();
   			}
		}
		function whenSelected(row) {
			row.style.backgroundColor = "#9ABFFA";
		}
		
		function whenUnSelect(row) {
			row.style.backgroundColor = "#FFFFCC";
		}
		
		function closediv1(){
			document.getElementById("div1").style.display="none";
			document.all.iframe1.style.display="none";
		}
		
		function showdiv1(){
			document.all.iframe1.style.display="";
			document.getElementById("div1").style.display="";
		}
		
		function selectTarget(){
			var columnname = document.all.columnname;
			var targetList = document.all.targets;
			var texts = columnname.options[columnname.selectedIndex].text;
			var values = columnname.value;
			var newoption = new Option(texts," and "+values.substring(values.indexOf("{")+1,values.indexOf("}")));
			newoption.selected = true ;
			targetList.options.add(newoption);
		}
		
		function delSelTarget(){
			var targetList = document.all.targets;
			if(targetList.selectedIndex == -1){
				alert("请先选择一个指标！");
			}else{
				var option = targetList.options[targetList.selectedIndex];
				//delSqlItem(option.value);
				targetList.remove(option);
			}
		}
		
		function delSqlItem(target){
			var sql = document.all.sql.innerText;
			if(sql.indexOf(target)!= -1){
				var i = sql.indexOf(target);
				var j = sql.indexOf("  " , i);
				if( j != -1){
					sql = sql.substr(0,i) + sql.substr(j);
				}else{
					sql = sql.substr(0,i) ;
				}
				document.all.sqlview.innerText = sql ;
			}
		}
		
		function createSqlItem(){
			var targetList = document.all.targets;
			if(targetList.selectedIndex == -1){
				alert("请先选择一个指标！");
			}else{
				if(document.all.tcavalue.value==""){
					alert("门限值不能为空！");
    				return;
				}
				if(!isDigit(document.all.tcavalue.value)){
    				alert("门限值必须为数字！");
    				return;
    			}
				var dbfield = targetList.options[targetList.selectedIndex].value;
				var sqlitem = dbfield ;
				sqlitem = sqlitem + document.all.countoper.value ;
				sqlitem = sqlitem + document.all.tcavalue.value ;
				appendsql(sqlitem);
			}
		}
		
		function appendsql(text){
			document.all.sqlview.innerText = document.all.sqlview.innerText + " "+text ;
		}
		
		function appendBtnText(text){
			appendsql(text);
		}	
		
		function changeEditable(){
			if(document.all.cbxedit.checked){
				document.all.sqlview.readOnly = false ;
			}else{
				document.all.sqlview.readOnly = true ;
			}
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
			<input type="hidden" name="parentid" value="${fo.parentid}">
			<input type="hidden" name="pagepath" value="${fo.pagepath}">
			<input type="hidden" name="openid" value="${openid}">
			<!-- Zero.jsp -->
			<input type="hidden" name="name" value="${fo.name}">
			<input type="hidden" name="naturalname" value="${fo.naturalname}">
			<!-- BeforeFrist.jsp -->
			<input type="hidden" name="timetype" value="${fo.timetype}">
			<input type="hidden" name="selpoint1" value="${fo.selpoint1}">
			<input type="hidden" name="timelevelinfo" value="${fo.timelevelinfo}">
			<input type="hidden" name="selpoint1Text" value="${fo.selpoint1Text}">
			<!-- Frist.jsp -->
			<input type="hidden" name="preps" value="${fo.preps}">
			<input type="hidden" name="selectedDimValue"
				value="${fo.selectedDimValue}">
			<input type="hidden" name="levelname" value="${fo.levelname}">
			<input type="hidden" name="dimResultValue"
				value="${fo.dimResultValue}">
			<input type="hidden" name="type" value="${fo.type}">
			<input type="hidden" name="dimTreeList" value="${fo.dimTreeList}">
			<input type="hidden" name="choice" value="${fo.choice}">
			<input type="hidden" name="dimTreeDiv" value="${fo.dimTreeDiv}">
			<input type="hidden" name="dimResult" value="${fo.dimResult}">
			<!-- Step.jsp -->
			<input type="hidden" name="tgids" value="${fo.tgids}">
			<input type="hidden" name="tgnames" value="${fo.tgnames}">
			<input type="hidden" name="seltgvalue" value="${fo.seltgvalue}">
			<input type="hidden" name="tgGroup" value="${fo.tgGroup}">
			<input type="hidden" name="tglistdiv" value="${fo.tglistdiv}">
			<input type="hidden" name="targetResult" value="${fo.targetResult}">
			<!-- Step1.jsp -->
			<input type="hidden" name="filtertype" value="${fo.filtertype}">
			<!-- Step2.jsp -->
			<input type="hidden" name="allchkid" value="${fo.allchkid}">
			<input type="hidden" name="alltop" value="${fo.alltop}">
			<input type="hidden" name="alltopvalue" value="${fo.alltopvalue}">
			<input type="hidden" name="allalarm" value="${fo.allalarm}">
			<input type="hidden" name="alltxtalarm" value="${fo.alltxtalarm}">
			<input type="hidden" name="tgfiltSqlvalue"
				value="${fo.tgfiltSqlvalue}">
			<!-- Step3.jsp -->
			<input type="hidden" name="exterValue" value="${fo.exterValue}">
			<input type="hidden" name="seldimvalue" value="${fo.seldimvalue}">
			<input type="hidden" name="timelevel" value="${fo.timelevel}">
			<input type="hidden" name="selpoint2" value="${fo.selpoint2}">
			<input type="hidden" name="hour" value="${fo.hour}">
			<input type="hidden" name="grownum" value="${fo.grownum}">
			<input type="hidden" name="selecttime1" value="${fo.selecttime1}">
			<input type="hidden" name="selecttime2" value="${fo.selecttime2}">
			<input type="hidden" name="timeResult" value="${fo.timeResult}">
			<input type="hidden" name="timeResults" value="${fo.timeResults}">
			<!-- Step4.jsp -->
			<input type="hidden" name="active" value="${fo.active}">
			<!-- Step5.jsp -->
			<input type="hidden" name="showactive" value="${fo.showactive}">
			<input type="hidden" name="selcharttype" value="${fo.selcharttype}">
			<input type="hidden" name="seldatatype" value="${fo.seldatatype}">
			<input type="hidden" name="seldim" value="${fo.seldim}">
			<input type="hidden" name="seltg" value="${fo.seltg}">
			<input type="hidden" name="str" value="${fo.str}">
			<input type="hidden" name="tgstr" value="${fo.tgstr}">
			<input type="hidden" name="multichart" value="${fo.multichart}">
			<input type="hidden" name="maxvalue" value="${fo.maxvalue}">
			<input type="hidden" name="pngwidth" value="${fo.pngwidth}">
			<input type="hidden" name="showvalue" value="${fo.showvalue}">
			<input type="hidden" name="pictitle" value="${fo.pictitle}">
			<input type="hidden" name="piccolor" value="${fo.piccolor}">
			<input type="hidden" name="xqingxie" value="${fo.xqingxie}">
			<!-- Done.jsp -->
			<input type="hidden" name="allchkid2" value="${fo.allchkid2}">
			<input type="hidden" name="allorder" value="${fo.allorder}">
			<!-- Finish.jsp -->
			<input type="hidden" name="lsh" value="${fo.lsh}">
			<input type="hidden" name="tgothername" value="${fo.tgothername}">


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
								<TR>
									<TD ALIGN="LEFT">
										<FONT CLASS="OecInlineInfo2">路径:&nbsp;<%=pagepath%> </FONT>
										&nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">第 2 步, 共 2 步&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<DIV
				style="margin-left: 8%; margin-right: 8%; margin-top: 20px; margin-bottom: 20px;">
				<!-- Start--Title----功能标题与提示---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						项目类型选择
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 单击“完成”。</FONT>
				<!-- End--Title----功能标题与提示---->
				<INPUT type="hidden" name="act" value="tableView">
				<input type='hidden' name='timespecial' value="" />
				<table border="1">
					<tr>
						<td>
							过滤条件:
						</td>
						<td>
							<table border="1" width="100%" height="50%">
								<tr>
									<td>
										<select name="columnname">
											<c:forEach items="${tgidlist}" var="tg" varStatus="vs">
												<option value="${tg}">
													${tgnamelist[vs.index]}
												</option>
											</c:forEach>
										</select>
										<a href="javascript:selectTarget();" style="color: blue">[选择指标]</a>
										<a href="javascript:delSelTarget();" style="color: blue">[删除指标]</a>
										<br>
										<select size="8" name="targets" style="width: 200px">
										</select>
									</td>
									<td valign="top">
										<div>
											运算符
											<br>
											<select name="countoper" style="width: 50px">
												<option value="=">
													=
												</option>
												<option value="&gt;">
													&gt;
												</option>
												<option value="&lt;">
													&lt;
												</option>
												<option value="&gt;=">
													&gt;=
												</option>
												<option value="&lt;=">
													&lt;=
												</option>
												<option value="!=">
													!=
												</option>
											</select>
											<br>
											<br>
											门限值
											<br>
											<input type="text" name="tcavalue" style="width: 50px">
											<br>
											<br>
											<a href="javascript:createSqlItem();" style="color: blue">[添加]</a>
										</div>
									</td>
									<td>
										<a href="javascript:appendBtnText('and');" style="color: red">and</a>
										&nbsp;
										<a href="javascript:appendBtnText('or');" style="color: red">or</a>
										&nbsp;
										<a href="javascript:appendBtnText('(');" style="color: red">(</a>
										&nbsp;
										<a href="javascript:appendBtnText(')');" style="color: red">)</a>
										&nbsp;
										<br>
										门限规则
										<span style="width: 190px"></span>
										<input type="checkbox" name="cbxedit"
											onclick="changeEditable();">
										可编辑
										<br>
										<textarea rows="8" cols="40" readonly="readonly"
											name="sqlview">${fo.sqlview}</textarea>
										&nbsp;&nbsp;
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							其他：
						</td>
						<td>
							<c:forEach items="${tgidlist}" var="tg" varStatus="vs">
								<input type="checkbox" name="chkid2" value="${tg}">
									${tgnamelist[vs.index]}&nbsp;&nbsp;&nbsp; 排序：
									<SELECT name="order">
									<c:forEach items="${_CHOICE_ORDER}" var="co">
										<option value="${co[0]}">
											${co[1]}
										</option>
									</c:forEach>
								</SELECT>
								<br>
							</c:forEach>
							记录数:
							<input type="text" name="tgfiltvalue" value="${fo.tgfiltvalue}">
							<span style="width: 18px; border: 0px solid red;"> <select
									name="r00" style="margin-left: -100px; width: 118px;"
									onChange="document.all.tgfiltvalue.value=this.value">
									<c:forEach items="${_CHOICE_TOP_VALUE}" var="ctv">
										<option value="${ctv[0]}">
											${ctv[1]}
										</option>
									</c:forEach>
								</select> </span>


						</td>
					</tr>
				</table>
				<!-- Start--Body---- 功能内容定义  --->
				<DIV
					style="margin-left: 5%; margin-right: 5%; margin-top: 20px; margin-bottom: 20px;">
					<TABLE BORDER="0">
						<TR>
							<TD>
								<INPUT type="hidden" name="act" value="tableView">
								<input type='hidden' name='timespecial' value="" />
							</TD>

						</TR>
					</TABLE>
				</DIV>
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
