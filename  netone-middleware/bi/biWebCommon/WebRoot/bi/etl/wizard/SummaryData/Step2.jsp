<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//��ҳ�ĸ��ڵ�ID
	String parentid = request.getParameter("parentid");
	//��ҳ������Ӧ��·��(���и��ڵ�ID���·���Ƕ�Ӧ��,����ͨ��envService����parentid�����)
	String pagepath = request.getParameter("pagepath");
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE>�ֶα༭</TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">

		<SCRIPT type="text/javascript" src="<%=path%>/include/js/util.js"></SCRIPT>
		<SCRIPT TYPE="text/javascript">
		//��һ���ű�
		function forward(){
			document.additem.action="<%=path%>/NextSummary.do?task=Step";
			document.additem.submit();
		}
		//��һ���ű�
		function nextsubmit(){
		
		    var level=document.getElementById('level').value;
		    var dimdata=document.getElementById('dimdata').value;
		    if(level==''){
		        alert('��ѡ��ά������');
		        return;
		    }
		   if(dimdata==''){
		        alert('��ѡ��ά��������Դ');
		        return;
		    }
			//����һ��ҳ�洫�ݲ���(��������parentid��pagepath�Ǳ����)
			var param="?parentid=<%=parentid%>&pagepath=<%=pagepath%>";
			var list = new List();
			var cname = document.all.cname.value;
			cname = cname.split(",");
			var chinesename;
			for(var i=0;i<cname.length;i++){
				if(document.getElementsByName(cname[i])[0].value==""){
					alert("����������Ϊ��!");
					return false;
				}
				list.add(document.getElementsByName(cname[i])[0].value);
			}
			document.all.chinesename.value=list;
			document.additem.submit();
		}
		
var curselect='';

function addselect(id) {
	document.getElementById(curselect).value = id;
}

function selectDimType() {
curselect='level';
	window.open("/biWeb/PagelistpathRightSvl?pagename=dimlist&appname=BUSSENV.BUSSENV.DYSER.BUSSLEVEL", "_blank");
}

function selectDimData() {
	curselect='dimdata';
	window.open("/biWeb/ApplistRightSvl?pagename=applist", "_blank");
}

		</SCRIPT>


	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<FORM ACTION="<%=path%>/NextSummary.do?task=Done" METHOD="POST"
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
			<!-- Step.jsp -->
			<input type="hidden" name="col" value="${so.col}">
			<input type="hidden" name="tablename" value="${so.tablename}">
			<input type="hidden" name="sqlvalue" value="${so.sqlvalue}">
			<input type="hidden" name="sqlview" value="${so.sqlview}">
			<!-- Step2.jsp -->
			<input type="hidden" name="chinesename" value="${so.chinesename}">
			<input type="hidden" name="cname" value="${cname}">
			<input type="hidden" name="ctype" value="${ctype}">

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
										<FONT CLASS="OecGlobalPageTitle">������-SQLģʽ</FONT>
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
										<FONT CLASS="OecInlineInfo2">·��:&nbsp;<%=pagepath%> </FONT>
										&nbsp;&nbsp;&nbsp;
										<FONT CLASS="OecInlineInfo">�� 2 ��, �� 3 ��&nbsp;&nbsp;</FONT>

									</TD>
								</TR>

							</TABLE>
						</TD>
					</TR>
				</TABLE>
			</CENTER>
			<DIV
				style="margin-left:8%;margin-right:8%;margin-top:20px;margin-bottom:20px;">
				<!-- Start--Title----���ܱ�������ʾ---->
				<FONT CLASS="OecHeaderSub"><NOBR>
						�༭�ֶε�����
					</NOBR> </FONT>
				<hr>
				<br>
				<FONT CLASS="OecInstructionText">������ʾ:
					��Ҫ�ֹ�ʶ��BELONGX�����ͺ�CREATED������</FONT>
				<!-- End --Title----���ܱ�������ʾ---->
				<BR>
				<BR>
				<!-- Start--Body---- �������ݶ���  --->
				<table>
					<tr>
						<td>
							�ֶα���
						</td>
						<td>
							�ֶ�����
						</td>
						<td>
							������
						</td>
						<td>
							����
						</td>
					</tr>
					<c:forEach items="${list}" var="map">
						<tr>
							<td>
								${map.name}
							</td>
							<td>
								${map.type}
							</td>
							<td>
								<input type="text" name="${map.name}" value="">
							</td>
							<td>
								<c:if test="${map.name=='BELONGX'}">

									<input type='text' name='level' value="${so.level}" readonly>
									<input type='button' value='ѡ������' onClick="selectDimType()">


									<input type='text' name='dimdata' value="${so.dimdata}" readonly>
									<input type='button' value='ѡ����Դ' onClick="selectDimData()">


								</c:if>
								<c:if test="${map.name=='TIMEX'}">
									<select name="selpoint">
										<option value="1h" ${so.selpoint=='1h'?"selected":""}>
											ʱ
										</option>
										<option value="1m" ${so.selpoint=='1m'?"selected":""}>
											��
										</option>
										<option value="1d" ${so.selpoint=='1d'?"selected":""}>
											��
										</option>

										<option value="1y" ${so.selpoint=='1y'?"selected":""}>
											��
										</option>
									</select>
								</c:if>
							</td>
						</tr>
					</c:forEach>
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
		<script type="text/javascript">
			if(document.all.chinesename.value!=""){
				var chinesename = document.all.chinesename.value.split(",");
				var cname = document.all.cname.value.split(",");
				if(chinesename.length>0){
					for(var i=0;i<chinesename.length;i++){
						document.getElementsByName(cname[i])[0].value=chinesename[i];
					}
				}
			}
		</script>
	</BODY>
</HTML>
