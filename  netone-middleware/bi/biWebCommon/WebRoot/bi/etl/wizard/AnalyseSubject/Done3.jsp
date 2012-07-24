<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//��ҳ�ĸ��ڵ�ID
	String parentid = request.getParameter("parentid");
	//��ҳ������Ӧ��·��(���и��ڵ�ID���·���Ƕ�Ӧ��,����ͨ��envService����parentid�����)
	String pagepath = request.getParameter("pagepath");
%>

<HTML>
	<HEAD>
		<TITLE>������10</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<script type="text/javascript"
			src="<%=basePath%>/include/js/calendar.js"></script>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript">
		//��һ���ű�
		function forward(){
			//history.go(-1);
			document.additem.action="<%=path%>/NextAnalyse.do?task=Done2";
			document.additem.submit();
		}
		//ȡ���ű�
		function cancelsubmit(){
			window.close();
		}
		//�����ű�
		function done(){
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
				document.all.acturl.value=value;
			}
			//���ս�����ʱ����صĴ����߼���Ҫ�ύ��Servlet�����
			additem.action='<%=basePath%>servlet/FlowpageSvl';
			//����صı�������ȫ�����ݸ������Servlet
			additem.submit();
		}
		
		var selectedTgList = new List();
		var tgIdNameMap = new Map();
		//����ָ���б�
		function do_changeTgList(){
	 		var url = "<%=basePath%>servlet/EtlTargetSelectSvl2";
			var pars = "method=targetElementList&groupid="+document.all.tgGroup.value; 
			var tgajax = new Ajax.Request(url, {method: 'get', parameters: pars, onSuccess: success_do_changeTgList, onFailure: failure_do_changeTgList});
		}

		function success_do_changeTgList(xmlhttp){
			var tglistdiv = $("tglistdiv");
			var tgGroup = document.all.tgGroup;
			tglistdiv.innerHTML = "";
			var restr = xmlhttp.responseText;
			var map = new Map();
			map.load(restr);
			var keyarr = map.keyarr;
			var html = "";
			html += "<input type='checkbox' name='columnchkid' value='name_en'>����{string}&nbsp;&nbsp;&nbsp;&nbsp;";
			html += "����·��:<input type='text' name='columntxt' value=''>"
			html += "<select name='columnsel'>"
			html += "<option value=''></option>";
			html += "<option value='name_en'>����{string}</option>";
			html += "<option value='start_time'>ʱ��{date}</option>";
			for(var j=0 ; j<keyarr.length ; j++){
				html += "<option value="+keyarr[j].substring(0,keyarr[j].indexOf("{"))+">"+map.get(keyarr[j])+"</option>";
			}
			html += "</select><br>"
			
			html += "<input type='checkbox' name='columnchkid' value='start_time'>ʱ��{date}&nbsp;&nbsp;&nbsp;&nbsp;";
			html += "����·��:<input type='text' name='columntxt' value=''>"
			html += "<select name='columnsel'>"
			html += "<option value=''></option>";
			html += "<option value='name_en'>����{string}</option>";
			html += "<option value='start_time'>ʱ��{date}</option>";
			for(var j=0 ; j<keyarr.length ; j++){
				html += "<option value="+keyarr[j].substring(keyarr[j].indexOf("{")+1,keyarr[j].indexOf("}"))+">"+map.get(keyarr[j])+"</option>";
			}
			html += "</select><br>"
			for(var i=0 ; i<keyarr.length ; i++){
				html += "<input type='checkbox' name='columnchkid' value='"+keyarr[i].substring(0,keyarr[i].indexOf("{"))+"'>"+map.get(keyarr[i])+"&nbsp;&nbsp;&nbsp;&nbsp;";
				html += "����·��:<input type='text' name='columntxt' value=''>"
				html += "<select name='columnsel'>"
				html += "<option value=''></option>";
				html += "<option value='name_en'>����{string}</option>";
				html += "<option value='start_time'>ʱ��{date}</option>";
				for(var j=0 ; j<keyarr.length ; j++){
					html += "<option value="+keyarr[j].substring(0,keyarr[j].indexOf("{"))+">"+map.get(keyarr[j])+"</option>";
				}
				html += "</select><br>"
			}
			tglistdiv.innerHTML=html;
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
	
		function failure_do_changeTgList(xmlhttp){
			alert("��ȡָ�����е�ָ�������");
		}
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="" METHOD="POST" name="additem">
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
										<FONT CLASS="OecGlobalPageTitle">����������</FONT>
									</TD>
									<TD ALIGN="RIGHT" valign="center">
										<A HREF=""><IMG
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
										<FONT CLASS="OecInlineInfo2">·��:&nbsp;<%=pagepath%> </FONT>
										&nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">�� 10 ��, �� 10 ��&nbsp;&nbsp;</FONT>

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
					   ��������
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ: Ϊ�˷ḻ��������Ϣ������ͨ���ò�������ø���صı������ݼ������ӣ��Էḻ�ı�������չ�֡�</FONT>
				<!-- End--Title----���ܱ�������ʾ---->
				<!-- Start--Body---- �������ݶ���  --->
				<table>
					<tr>
						<td>
							<div id="tglistdiv">
								<script type="text/javascript">
									do_changeTgList();
								</script>
							</div>
						</td>
					</tr>
				</table>

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