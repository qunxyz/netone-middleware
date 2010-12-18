<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../human/BaseRoot.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>��������</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript" src="include/js/rsinfo/dept/right.js"></script>
		<script type="text/javascript"
			src="include/js/rsinfo/dept/humanindex.js"></script>
		<script type="text/javascript" src="include/js/page.js"></script>
		<script type="text/javascript" src="include/js/prototype.js"></script>
	</head>
	<body style="font-size: 12px; margin: 22px">
		${paramMap.alertMsg}
		<div style="width: 100%; height: 100%">
			<form action="" method="post" name="form1">
				<input type="hidden" name="jsppath" value="<%=path%>">
				<input type="hidden" name="parentdir" value="${upo.parentdir}" />
				<input type="hidden" name="appid" value="${upo.appid}" />
				<input type="hidden" name="ou" value="${upo.ou}" />
				<input type="hidden" name="id" value="${upo.id}" />
				<input type="hidden" name="inclusion" value="${upo.inclusion}" />
				<input type="hidden" name="extendattribute"
					value="${upo.extendattribute}" />
				<input type="hidden" name="aggregation" value="${upo.aggregation}" />
				<input type="hidden" name="created" value="${upo.created}" />
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr>
						<td colspan='2'>
						   <font color='red' size=3> <strong>�ṹĿ¼����:</strong></font>
							<input type="button" name="btndelete" value="ɾ ��" class="butt"
								onclick="doDelete1();">
							
							<input type="button" name="btncreate" value="�� ��" class="butt"
								onclick="changeToCreateModel();">

							
						<input type="button" name="btncreate" value="�� ��" class="butt"
									onclick="window.open('<%=path%>/rsinfo/dept/DepartmentModify.do?task=editn&naturalname=${upo.naturalname}')">
							

							&nbsp;
							<!--<input type="button" name="btnsyn" value="ͬ��"
								onclick="window.open('<%=path%>/rsinfo/syn.jsp?flag=byDept&id='+document.all.id.value)" class="butt">-->
						</td>
					</tr>
					<tr style='display:none'>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;��
						</td>
						<td>
							<input type="text" name="naturalname" id="naturalname"
								style="width: 350" value="${upo.naturalname}"
								class="textinput_td" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��������
						</td>
						<td>
							<input type="text" name="name" id="name" style="width: 350"
								value="${upo.name}" class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							����
						</td>
						<td>
							<input type="text" name="naturalname" id="naturalname" style="width: 350"
								value="${upo.naturalname}" class="textinput_td" />
						</td>
					</tr>
								<tr>
						<td width="15%">
							���ű���
						</td>
						<td>
							<input type="text" name="actionurl" id='actionurl' style="width: 350"
								value="${upo.actionurl}" class="textinput_td" />
						</td>
					</tr>
					<input type="hidden" name='objecttype' value='${upo.objecttype}'>
					<!-- �ͻ����� -->
				</table>
				<br>
			
				
				&nbsp;
				<input type="button" value="�����˺�" onclick="humanadd();" class="butt">
				
				<input type="button" value="�޸�" onclick="humanmodify();" class="butt">
				
				<input type="button" value="ɾ�� " onclick="humandel();" class="butt">




				

				<input type="button" value="���� " onclick="humanforbitx();" class="butt">
				


				
					<input type="button" value="����" onclick="humancopy();"
						class="butt">
					
					<input type="button" value="����" onclick="humanfileinput();"
						class="butt">
					
					<input type="button" value="����" onclick="humanfileoutput();"
						class="butt">
					
					
					<input type="button" name="btnsyn" value="ͬ��"
						onclick="syn();" class="butt">

				<input type="button" value="��������" onclick="humanpass();" class="butt">

				<br>
				<br>
				<table id="hang_table" width="96%" border="0" align="center"
					cellpadding="0" cellspacing="1">
					<tr>
						<td class="td_titt_bg" nowrap="nowrap" width='30'>
							<input type="checkbox" name="checkall" onclick="docheck();">
							<input type="hidden" name="task" value="">

						</td>
						<td class="td_titt_bg" nowrap="nowrap">
							����
						</td>
						<td class="td_titt_bg" nowrap="nowrap">
							�ʺ�
						</td>

						<td class="td_titt_bg" nowrap="nowrap">
							״̬
						</td>
						<td class="td_titt_bg" nowrap="nowrap">
							ϸĿ
						</td>
					</tr>
					<c:forEach items="${listinfo}" var="getCol">
						<tr>
							<td>
								<input type="checkbox" name="chkid"
									value="${getCol.description}">
							</td>
							<td height="21">
								${getCol.name}
							</td>
							<td height="21">
								${getCol.description}
							</td>
							<td height="21">
								<c:if test="${getCol.password == '9$9$'}">
                                                                 ����
                                                                </c:if>
								<c:if test="${getCol.password!= '9$9$'}">
                                                                 ����
                                                                </c:if>
                                                              
                                <c:if test="${getCol.active}"><a href='<%=path%>/servlet/ForceOutSvl?username=${getCol.naturalname}' target='_blank'> <font color='green'>[�˳���½]</font></a></c:if>
                                
							</td>
							<td height="21">
								<a href=javascript:link("${getCol.description}")>�鿴</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<script type="text/javascript">
				var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
				pginfo.write();
			</script>
				<br>



				<script type="text/javascript">
						function syn(){
							var k = 0;
							var chkid = "";
							for(var i=0 ; i<form1.elements.length ; i++) {
								if (form1.elements[i].name=="chkid") {
									if(form1.elements[i].checked==true){
										k++;
										chkid = chkid+form1.elements[i].value+",";
									}
								}
							}
							if(k==0){
								alert("����ѡ����Ҫͬ�����û�");
								return;
							} else {
								window.open("<%=path%>/rsinfo/syn.jsp?flag=byHuman&chkid="+chkid);
							}
						}


		function humanforbitx(){
	var k = 0;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				k = 1;
				break;
			}
		}
	}
	if(k==0){
		alert("����ѡ����Ҫ��ֹ��ѡ��");
		return;
	} else {
		if(confirm("�Ƿ�Ҫ��ֹ�ʻ�")){
			document.all.task.value="passreset";
			form1.action="humanDel.do?task2=dept&passConfus=yes";
			form1.submit();
			document.all.task.value="";
		}
	}
}
		
					</script>

			</form>

			<c:if test="${ModifySuccess == 'y'}">
				<script type="text/javascript">
				afterModify();
			</script>
			</c:if>
			<c:if test="${ModifySuccess == 'n'}">
				<script type="text/javascript">
				alert("�޸�ʧ�ܣ�");
			</script>
			</c:if>
			<c:if test="${DeleteSuccess == 'y'}">
				<script type="text/javascript">
				afterDelete();
			</script>
			</c:if>
			<c:if test="${DeleteSuccess == 'n'}">
				<script type="text/javascript">
				alert("����ɾ��ʧ��,����ò������Ƿ����û������Ƿ����ӽڵ�!");
			</script>
			</c:if>
			<c:if test="${DelSuccess == 'y'}">
				<script type="text/javascript">
				afterDel();
			</script>
			</c:if>
			<c:if test="${MoveSuccess == 'y'}">
				<script type="text/javascript">
				afterMove();
			</script>
			</c:if>
	</body>
</html>
