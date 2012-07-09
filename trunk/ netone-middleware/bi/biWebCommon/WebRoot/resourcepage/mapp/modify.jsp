<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/netone/resource"  prefix="resource"%>
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
		<title>�ֻ������޸�</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript">
		//����ѡ��ҳ��
		function searchtree() {
			window.open("SelectSvl?appname=BUSSENV.BUSSENV.MOBILEAPP&pagename="+document.all.pagename.value);
		}
		
		function sselected(text,value){
		   	document.getElementById("objecttype").value=text+"["+value+"]";
		}
		</script>
	</head>
	<body style="font-size: 12px;margin: 22px">
		<c:if test="${ModifySuccess == 'y'}">
			<script type="text/javascript">
				alert("�޸ĳɹ���")
				opener.search();
				window.close();
			</script>
		</c:if>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("�޸�ʧ�ܣ�")
			</script>
		</c:if>
		<div style="width: 100%;height: 100%">
			<form action="PagelistModifySvl?task=editsave" method="post"
				name="form1">
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="id" value="${upo.id}" />
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;Ч
						</td>
						<td>

							<input type="checkbox" name="active" value="1"
								<c:if test="${upo.active=='1'}">checked</c:if> />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��&nbsp;&nbsp;&nbsp;&nbsp;��
						</td>
						<td>
							<input type="text" name="naturalname" value="${upo.naturalname}"
								class="textinput_td" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							��������
						</td>
						<td>
							<input type="text" name="name" value="${upo.name}"
								class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							ѡ�����
						</td>
						<td width='300'>

							<select name='objecttype' id='objecttype'>
							   <c:set var="condition" value="and naturalname like 'BUSSENV.BUSSENV.MOBILEAPP.%' order by extendattribute"></c:set>						
							   <resource:fetchResource dataname="listz" condition="${condition}"></resource:fetchResource>
							   <c:forEach items="${listz}" var="upoz">
									<option value='${upoz.extendattribute}' <c:if test="${upo.objecttype==upoz.objecttype}">selected</c:if>>${upoz.name}[${upoz.extendattribute}]</option>
							   </c:forEach>
							</select>
						</td>
					</tr>
					<tr style='display:none'>
						<td width="15%">
							����
						</td>
						<td>
							<input type="text" name="actionurl" value="${upo.actionurl}"
								class="textinput_td" />
						</td>
					</tr>
					<tr <c:if test="${inclusion=='1'}">style='display:none'</c:if>>
						<td width="15%">
							����
						</td>
						<td>
							<textarea rows="20" cols="80" name="extendattribute">${upo.extendattribute}</textarea>
						</td>
					</tr>
					<tr>
						<td width="15%">
							����
						</td>
						<td>
							<textarea rows="2" cols="80" name="description">${upo.description}</textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input type="submit" name="btnnew" value="�� ��" class="butt">
					<input type="button" name="btncancel" value="ȡ ��"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
