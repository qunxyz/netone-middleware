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
		<TITLE>详细定义过滤条件</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript">
			//取消脚本
			function cancelsubmit(){
				window.close();
			}
			function check(){
				var tgothername;
				var targetop = document.all.target.options;
				if(targetop.length >0 ){
					for(var i=0 ; i<targetop.length ; i++){
						if(i==0){
							tgothername = targetop[i].value + "=" +targetop[i].text;
						} else {
							tgothername = tgothername + "#" + targetop[i].value + "=" +targetop[i].text;
						}
					}
				}
				document.all.tgothername.value = tgothername;
				
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
				return true;
			}
			function doact(){
				var values=null;
				var k = 0;
				for(var i=0 ; i<additem.elements.length ; i++) {
					if (additem.elements[i].name=="columnchkid") {
						if(additem.elements[i].checked==true){
							if(k==0){
								value=additem.elements[i].value+"#"+additem.elements[i+1].value+"#"+additem.elements[i+2].value+"#";
							} else {
								value+=";"+additem.elements[i].value+"#"+additem.elements[i+1].value+"#"+additem.elements[i+2].value+"#";
							}
							k++;
						}
					}
				}
				if(k>0){
					value = value.replace(/{/g,"#");
					value = value.replace(/}/g,"");
					document.all.actcondition.value=value;
				}
			}
			function done(){
				if(check()){
					doact();
					document.all.additem.submit();
				}
			}
			
			function showname(){
				if(document.all.target.selectedIndex>=0){
					document.all.temp.value = document.all.target.options[document.all.target.selectedIndex].text;
				}
			}
			function modify(){
				var targetop = document.all.target.options;
				if(targetop.length >0 ){
					var map = new Map();
					var k = document.all.target.selectedIndex;
					for(var i=0 ; i<targetop.length ; i++){
						if(i==k){
							map.put(targetop[document.all.target.selectedIndex].value,document.all.temp.value);
						} else {
							map.put(targetop[i].value,targetop[i].text);
						}
					}
				  	for(var i=targetop.length-1 ; i>=0 ; i--){
						document.all.target.remove(i);
				  	}
				  	var keyarr = map.keyarr;
				  	for(var i=0 ; i<keyarr.length ; i++){
						targetop.add(new Option(map.get(keyarr[i]),keyarr[i]));
					}
				}
			}
			function up(){
				var targetop = document.all.target.options;
				if(targetop.length >0 ){
					var map = new Map();
					var k = document.all.target.selectedIndex;
					if(k==0){
						alert("已经是最上层,不能上移!");
						return;
					} else {
						for(var i=0 ; i<targetop.length ; i++){
							map.put(targetop[i].value,targetop[i].text);
						}
					  	for(var i=targetop.length-1 ; i>=0 ; i--){
							document.all.target.remove(i);
					  	}
					  	var keyarr = map.keyarr;
					  	for(var i=0 ; i<keyarr.length ; i++){
					  		if(i==k-1){
					  			targetop.add(new Option(map.get(keyarr[k]),keyarr[k]));
					  			targetop.add(new Option(map.get(keyarr[i]),keyarr[i]));
					  			i++;
					  		} else {
					  			targetop.add(new Option(map.get(keyarr[i]),keyarr[i]));
					  		}
						}
					}
				}
			}
			function down(){
				var targetop = document.all.target.options;
				if(targetop.length >0 ){
					var map = new Map();
					var k = document.all.target.selectedIndex;
					if(k==targetop.length-1){
						alert("已经是最底层,不能下移!");
						return;
					} else {
						for(var i=0 ; i<targetop.length ; i++){
							map.put(targetop[i].value,targetop[i].text);
						}
					  	for(var i=targetop.length-1 ; i>=0 ; i--){
							document.all.target.remove(i);
					  	}
					  	var keyarr = map.keyarr;
					  	for(var i=0 ; i<keyarr.length ; i++){
					  		if(i==k){
					  			targetop.add(new Option(map.get(keyarr[i+1]),keyarr[i+1]));
					  			targetop.add(new Option(map.get(keyarr[i]),keyarr[i]));
					  			i++;
					  		} else {
					  			targetop.add(new Option(map.get(keyarr[i]),keyarr[i]));
					  		}
						}
					}
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
			
			function success_do_changeTgList(xmlhttp){
				var restr = xmlhttp.responseText;
				var map = new Map();
				map.load(restr);
				var keyarr = map.keyarr;
				var columnname = $("columnname");
				var sql = " and 1=1";
				for(var i=0 ; i<keyarr.length ; i++){
					var option = new Option(map.get(keyarr[i]).substr(0,map.get(keyarr[i]).indexOf("{")),keyarr[i]);
					columnname.add(option);
				}
				
				var tglistdiv = $("tglistdiv");
				var tgGroup = document.all.tgGroup;
				tglistdiv.innerHTML = "";
				var html = "";
				html += "<input type='checkbox' name='columnchkid' value='belongx{belongx}#隶属{string}'>隶属{string}&nbsp;&nbsp;&nbsp;&nbsp;";
				html += "选择类型<select name='columnsel' onchange='change(this);'>"
							+"<option value='text'>输入</option>"
							+"<option value='time'>时间</option>"
							+"<option value='select'>下拉</option>"
							+"<option value='resource'>资源</option>"
							+"</select>"
				html += "<input type='text' name='columntxt' style='display:none' value=''>"
				html += "<input type='button' name='columnbtn' style='display:none' value='选择' onclick='openRS(this)'><br>"
				
				html += "<input type='checkbox' name='columnchkid' value='timex{timex}#时间{date}'>时间{date}&nbsp;&nbsp;&nbsp;&nbsp;";
				html += "选择类型<select name='columnsel' onchange='change(this);'>"
							+"<option value='time'>时间</option>"
							+"</select>"
				html += "<input type='text' name='columntxt' style='display:none' value=''>"
				html += "<input type='button' name='columnbtn' style='display:none' value='选择' onclick='openRS(this)'><br>"
				for(var i=0 ; i<keyarr.length ; i++){
					html += "<input type='checkbox' name='columnchkid' value='"+keyarr[i]+"#"+map.get(keyarr[i])+"'>"+map.get(keyarr[i])+"&nbsp;&nbsp;&nbsp;&nbsp;";
					if(map.get(keyarr[i]).indexOf("number")!=-1){
						html += "选择类型<select name='columnsel' onchange='change(this);'>"
									+"<option value='text'>输入</option>"
									+"<option value='select'>下拉</option>"
									+"</select>"
					} else {
						html += "选择类型<select name='columnsel' onchange='change(this);'>"
									+"<option value='text'>输入</option>"
									+"<option value='time'>时间</option>"
									+"<option value='select'>下拉</option>"
									+"<option value='resource'>资源</option>"
									+"</select>"
					}
					html += "<input type='text' name='columntxt' style='display:none' value=''>"
					html += "<input type='button' name='columnbtn' style='display:none' value='选择' onclick='openRS(this)'><br>"
				}
				tglistdiv.innerHTML=html;
			}
		
			function failure_do_changeTgList(xmlhttp){
				alert("读取指标组中的指标出错！");
			}
			function analyse(){
				if(check()){
					doact();
					document.all.additem.action="<%=basePath%>flowpage.do?chkid="+document.all.chkid.value+"&act=table&task2=analyse1";
					document.all.additem.submit();
				}
			}
			
			var ele = null;
			function openRS(that){
				window.open('<%=basePath%>ApplistRightSvl?pagename=applist');
				ele=that;
			}
			function addselect(id) {
				ele.previousSibling.value=id;
			}
			
			function change(that){
				if(that.value=='select'){
					that.nextSibling.style.display="";
					that.nextSibling.nextSibling.style.display="none";
				} else if(that.value=='resource'){
					that.readonly="readonly";
					that.nextSibling.style.display="";
					that.nextSibling.nextSibling.style.display="";
				} else {
					that.nextSibling.style.display="none";
					that.nextSibling.nextSibling.style.display="none";
				}
			}	
		</SCRIPT>
	</HEAD>
	<c:if test="${do=='do'}">
		<script>
			alert("操作完毕!");
			window.close();
		</script>
	</c:if>
	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="<%=path%>/ModifyAnalyse.do?task=doFinish" METHOD="POST"
			name="additem">
			<input type="hidden" name="parentid" value="${fo.parentid}">
			<input type="hidden" name="pagepath" value="${fo.pagepath}">
			<input type="hidden" name="chkid" value="${chkid}">
			<!-- Zero.jsp -->
			<input type="hidden" name="name" value="${fo.name}">
			<input type="hidden" name="naturalname" value="${fo.naturalname}">
			<!-- BeforeFrist.jsp -->
			<input type="hidden" name="timetype" value="${fo.timetype}">
			<input type="hidden" name="selpoint1" value="${fo.selpoint1}">
			<input type="hidden" name="timelevelinfo" value="${fo.timelevelinfo}">
			<input type="hidden" name="selpoint1Text" value="${fo.selpoint1Text}">
			<!-- Frist.jsp -->
			<input type="hidden" name="dynamicDim" value="${fo.dynamicDim}">
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
			<!-- Step.jsp -->
			<input type="hidden" name="tgids" value="${fo.tgids}">
			<input type="hidden" name="tgnames" value="${fo.tgnames}">
			<input type="hidden" name="seltgvalue" value="${fo.seltgvalue}">
			<input type="hidden" name="tgGroup" value="${fo.tgGroup}">
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
			<input type="hidden" name="unit" value="${fo.unit}">
			<input type="hidden" name="timeResult" value="${fo.timeResult}">
			<!-- Step5.jsp -->
			<input type="hidden" name="str" value="${fo.str}">
			<input type="hidden" name="tgstr" value="${fo.tgstr}">
			<!-- Done.jsp -->
			<input type="hidden" name="act" value="${fo.act}">
			<input type="hidden" name="timespecial" value="${fo.timespecial}">
			<input type="hidden" name="allchkid2" value="${fo.allchkid2}">
			<input type="hidden" name="allorder" value="${fo.allorder}">
			<!-- Done2.jsp -->
			<input type="hidden" name="actcondition" value="${fo.actcondition}">
			<!-- Done3.jsp -->
			<input type="hidden" name="acturl" value="${fo.acturl}">
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
										<FONT CLASS="OecGlobalPageTitle">分析主题定义</FONT>
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
													<INPUT type='button' value='完成' onClick='javascript:done()'>
												</TD>
												<TD>
													<INPUT type='button' value='测试' onClick='analyse();'>
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
										<FONT CLASS="OecInlineInfo">第 3 步, 共 3 步&nbsp;&nbsp;</FONT>

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
						分析条件细致定义
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">帮助提示: 单击“下一步”。</FONT>
				<!-- End--Title----功能标题与提示---->
				<BR>
				<!-- Start--Body---- 功能内容定义  --->
				<table border="1">
					<tr>
						<td>
							维度修改:
						</td>
						<td>
							<textarea rows="4" cols="100" name="dimResult">${fo.dimResult}</textarea>
						</td>
					</tr>
					<tr>
						<td>
							指标修改:
						</td>
						<td>
							<input type="text" name="temp" value="">
							<input type="button" name="btn" value="修改" onclick="modify();">
							<input type="button" name="btn" value="上移" onclick="up();">
							<input type="button" name="btn" value="下移" onclick="down();">
							<br>
							<select name="target" size="3" onclick="showname();">
								<c:forEach items="${tglist}" var="tg">
									<option value="${tg.value}">
										${tg.label}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							时间修改:
						</td>
						<td>
							<textarea rows="4" cols="100" name="timeResults">${fo.timeResults}</textarea>
						</td>
					</tr>
					<tr style="display: none">
						<td>
							是否使用图表定制条件：
						</td>
						<td>
							<select name="active">
								<option value="true" ${fo.active==true? "selected":fo.active==""?"selected":"" }>
									是
								</option>
								<option value="false" ${fo.active==false?"selected":"" }>
									否
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							过滤条件:
						</td>
						<td>
							<table border="1" width="100%" height="100%">
								<tr>
									<td>
										<select name="columnname">

										</select>
										<a href="javascript:selectTarget();" style="color: blue">[选择指标]</a>
										<a href="javascript:delSelTarget();" style="color: blue">[删除指标]</a>
										<br>
										<select size="8" name="targets" style="width: 200px">
										</select>
									</td>
									<td>
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
									<td>&nbsp;&nbsp;
										<a href="javascript:appendBtnText('and');" style="color: red"><strong>[AND]</strong></a>
										&nbsp;
										<a href="javascript:appendBtnText('or');" style="color: red"><strong>[OR]</strong></a>
										&nbsp;
										<a href="javascript:appendBtnText('(');" style="color: red"><strong>[(]</strong></a>
										&nbsp;
										<a href="javascript:appendBtnText(')');" style="color: red"><strong>[)]</strong></a>
										&nbsp;&nbsp;

										门限规则
										<input type="checkbox" name="cbxedit"
											onclick="changeEditable();">
										可编辑
										<br>
										<textarea rows="5" cols="30" readonly="readonly"
											name="sqlview">${fo.sqlview}</textarea>
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
							<c:forEach items="${tglist2}" var="tg">
								<input type="checkbox" name="chkid2" value="${tg.value}">
									${tg.label}&nbsp;&nbsp;&nbsp;
									排序：
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
							<input type="text" name="tgfiltvalue" value="${fo.tgfiltvalue}"
								style="width: 100px; height: 21px; font-size: 10pt;">
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
					<tr>
						<td>
							过滤:
						</td>
						<td>
							<div id="tglistdiv">
								<script type="text/javascript">
									var tgGroup = $("tgGroup");
									var url = "<%=basePath%>servlet/EtlTargetSelectSvl2";
									var pars = "method=targetElementList&groupid="+encodeURI(tgGroup.value) ; 
									var tgajax = new Ajax.Request(url, {method: 'get', parameters: pars, onSuccess: success_do_changeTgList, onFailure: failure_do_changeTgList});
								</script>
							</div>
						</td>
					</tr>
				</table>
				<script type="text/javascript">
					var chkid = document.getElementsByName("chkid2");
					var order = document.getElementsByName("order");
					var allchkid = document.all.allchkid2.value;
					var allorder = document.all.allorder.value;
					if(allchkid!=""){
						allchkid = allchkid.split(",");
						allorder = allorder.split(",");
						for(var i=0;i<allchkid.length-1;i++){
							for(var j=0;j<chkid.length;j++){
								if(allchkid[i]==chkid[j].value){
									chkid[j].checked=true;
									order[j].value=allorder[i];
								}
							}
						}
					}
				</script>
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
										<INPUT type='button' value='测试' onClick='analyse();'>
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
