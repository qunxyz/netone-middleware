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
		<TITLE>添加项5</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript">
		//上一步脚本
		function forward(){
			//history.go(-1);
			var seltgs = document.getElementsByName("seltg");
			var tgstr = "";
			for(i=0 ;i<seltgs.length;i++){
				if(seltgs[i].checked){
					tgstr += seltgs[i].value+",";
				}
			}
      	  	document.all.tgstr.value=tgstr;
			document.additem.action="<%=path%>/NextAnalyse.do?task=Step4";
			document.additem.submit();
		}
		//下一步脚本
		function nextsubmit(){
			if(checkseldim()){
				var seltgs = document.getElementsByName("seltg");
				var tgstr = "";
				for(i=0 ;i<seltgs.length;i++){
					if(seltgs[i].checked){
						tgstr += seltgs[i].value+",";
					}
				}
	      	  	document.all.tgstr.value=tgstr;
	      	  	
	      	  	var seldims = document.getElementsByName("seldim");
	      	  	var str="";
				for(i=0 ;i<seldims.length ; i++){
					str += seldims[i].id+"="+seldims[i].value+",";
				}
				document.all.str.value = str;
				document.additem.submit();
			}
		}
		//取消脚本
		function cancelsubmit(){
			window.close();
		}
		function checkseldim(){
    		var seldatatype = document.all.seldatatype;
    		if(seldatatype.value=='ne-time' || seldatatype.value=='time-ne'){
    			return true ;
    		}
			
    		var sels = document.getElementsByName("seldim");
    		var nosel = 0
    		for(i=0 ;i<sels.length ; i++){
    			if(sels[i].value == "-1"){
    				nosel++;
    			}
    		}
    		if(nosel!=1){
    			alert("必须有且只有一个维度可以被设置成未选择！");
    			return false;
    		}
    		return true ;
    	}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="<%=path%>/NextAnalyse.do?task=Done" METHOD="POST"
			name="additem">
			<input type="hidden" name="parentid" value="${fo.parentid}">
			<input type="hidden" name="pagepath" value="${fo.pagepath}">
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
			<input type="hidden" name="unit" value="${fo.unit}">
			<input type="hidden" name="timeResult" value="${fo.timeResult}">
			<input type="hidden" name="timeResults" value="${fo.timeResults}">
			<!-- Step4.jsp -->
			<input type="hidden" name="active" value="${fo.active}">
			<!-- Step5.jsp -->
			<input type="hidden" name="str" value="${fo.str}">
			<input type="hidden" name="tgstr" value="${fo.tgstr}">
			<!-- Done.jsp -->
			<input type="hidden" name="act" value="${fo.act}">
			<input type="hidden" name="timespecial" value="${fo.timespecial}">
			<input type="hidden" name="sqlview" value="${fo.sqlview}">
			<input type="hidden" name="allchkid2" value="${fo.allchkid2}">
			<input type="hidden" name="allorder" value="${fo.allorder}">
			<input type="hidden" name="tgfiltvalue" value="${fo.tgfiltvalue}">
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
										<FONT CLASS="OecInlineInfo">第 2 步, 共 10 步&nbsp;&nbsp;</FONT>

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
				<FONT CLASS="OecInstructionText">帮助提示: 然后单击“下一步”。</FONT>
				<!-- End --Title----功能标题与提示---->
				<BR>
				<!-- Start--Body---- 功能内容定义  --->
				<div
					style="width: 99%; height: 39%; margin: 0px; margin-top: 0px; border: 0 solid #A9C0E5">
					<table width="100%" border="0" cellpadding="0" cellspacing="1"
						bgcolor="#A9C0E5" align="center">
						<tr class="td-02">
							<td bgcolor="#FFFFFF" align="left" width="110" nowrap>
								图表类型
							</td>
							<td bgcolor="#FFFFFF" align="left" nowrap>
								<select name="selcharttype">
									<c:forEach items="${graphTypeList}" var="gp">
										<option value="${gp[0]}" ${fo.selcharttype==gp[0]?"selected":""}>
											${gp[1]}
										</option>
									</c:forEach>
								</select>
								&nbsp;
								<select name="seldatatype">
									<option value="default" ${fo.seldatatype=="default"?"selected":""}>
										默认统计图
									</option>
									<option value="ne-time" ${fo.seldatatype=="ne-time"?"selected":""}>
										三维统计-维度X时间统计图
									</option>
									<option value="time-ne" ${fo.seldatatype=="time-ne"?"selected":""}>
										三维统计-时间X维度统计图
									</option>
								</select>
							</td>
						</tr>
						<tr class="td-02">
							<td bgcolor="#FFFFFF" align="left" width="110" nowrap>
								维度
							</td>
							<td bgcolor="#FFFFFF" align="left" nowrap>
								<c:forEach items="${dimvalues}" var="dm">
									<div style="float: left; margin-right: 5px">
										${dm[1]}
										<select id="${dm[0]}" name="seldim">
											<OPTION value="-1">
												未选择
											</OPTION>
											<c:forTokens items="${dm[2]}" delims="," var="dmv">
												<option value="${dmv}">
													${dmv}
												</option>
											</c:forTokens>
										</select>
										&nbsp;
									</div>
								</c:forEach>
							</td>
						</tr>
						<tr class="td-02">
							<td bgcolor="#FFFFFF" align="left" width="110">
								指标
							</td>
							<td bgcolor="#FFFFFF" align="left" nowrap>
								<c:forEach items="${tglist}" var="tg">
									<div style="float: left; margin-right: 5px">
										<input type="checkbox" name="seltg" value="${tg.value}">
										${tg.label} &nbsp;
									</div>
								</c:forEach>
							</td>
						</tr>
						<tr class="td-02">
							<td bgcolor="#FFFFFF" align="left" width="110">
								多图显示：
							</td>
							<td bgcolor="#FFFFFF" align="left" nowrap>
								<input type="radio" value="0" name="multichart"
									${fo.multichart== "0"?"checked":fo.multichart==""?"checked":""}>
								否
								<input type="radio" value="1" name="multichart"
									${fo.multichart=="1"?"checked":""}>
								是
							</td>
						</tr>
						<tr class="td-02">
							<td bgcolor="#FFFFFF" align="left" width="110" nowrap>
								需要参数切换
							</td>
							<td bgcolor="#FFFFFF" align="left" width="110" nowrap>

								<select name="showactive">
									<option value="1" ${fo.showactive==1?
										"selected":fo.showactive==""?"selected":""}>
										是
									</option>
									<option value="0" ${fo.showactive==0?"selected":"" }>
										否
									</option>
								</select>

							</td>
						</tr>
						<tr class="td-02">
							<td bgcolor="#FFFFFF" align="left" width="110" nowrap>
								外观
							</td>
							<td bgcolor="#FFFFFF" align="left" nowrap>
								指标最小值(Y轴):
								<input type="text" value="${fo.maxvalue }" name="maxvalue"
									style="width: 50">
								&nbsp; 图片宽度:
								<input type="text" value="${fo.pngwidth }" name="pngwidth"
									style="width: 50">
								&nbsp; 显示数值:
								<select name="showvalue">
									<option value="true" ${fo.showvalue==true?"selected":"" }>
										是
									</option>
									<option value="false" ${fo.showvalue==false?"selected":"" }>
										否
									</option>
								</select>
								&nbsp; 图片标题：
								<input type="text" value="${fo.pictitle }" name="pictitle"
									style="width: 100">
								&nbsp; 图片背景色:
								<input type="text" value="${fo.piccolor }" name="piccolor"
									style="width: 100">
								&nbsp; X轴文字倾斜值
								<input type="text" value="" name="xqingxie" style="width: 100">
							</td>
						</tr>
					</table>
				</div>
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
				var tgstr = document.all.tgstr.value;
				if(tgstr!=""){
					tgstr = tgstr.split(",");
					var seltgs = document.getElementsByName("seltg");
					for(var i=0 ;i<seltgs.length;i++){
						for(var j=0 ;j<tgstr.length;j++){
							if(tgstr[j]==seltgs[i].value){
								seltgs[i].checked = true;
							}
						}
					}
				}
				var str = document.all.str.value;
				if(str!=""){
					str = str.split(",");
					for(var i=0;i<str.length;i++){
						var s = str[i].split("=");
						if(s[0]!=""){
							if(s[1]==""){
								s[1] = "-1";
							}
							document.getElementById(s[0]).value=s[1];
						}
					}
					
				}
				
			</script>
		</FORM>
	</BODY>
</HTML>
