<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%=request.getAttribute("subflow") == null ? ""
					: request.getAttribute("subflow")%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>��������ʵ��ѡ��</title>
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/workflow/design.js"></SCRIPT>
	</head>
	<body>
		<form method="post" action="">
			<input name="pathinfo" type="hidden" value=<%=path%>>
			<br>
			<font class='Text1'>��ǰ����:${processname}</font>&nbsp;
			<input type="button" value="��������"
				onclick="useprocess('${param.processid}','new','');" class="butt" />

			<hr>
			<table width='100%' cellspacing="0" border="1" cellpadding="2"
				bordercolordark="#999999" bordercolorlight="#FFFFFF">
				<tr class='tdheadline'>

					<td nowrap>
						����ID
					</td>

					<td nowrap>
						״̬
					</td>
					<td nowrap>
						����
					</td>
					<td nowrap>
						����ʱ��
					</td>
					<td nowrap>
						����ʱ��
					</td>
					<td nowrap>
						����
					</td>
				</tr>
				<c:forEach items="${listinfo}" var="getCol">
					<tr class="td-02" align="left">

						<td height="21">
							${getCol.runtimeid}
						</td>

						<td height="21" bgcolor="#FFFFFF">
							<c:if test="${getCol.statusnow=='00'}">
								<font color='blue'>
							</c:if>
							<c:if test="${getCol.statusnow=='01'}">
								<font color='green'>
							</c:if>
							<c:if test="${getCol.statusnow=='04'}">
								<font color='yellow'>
							</c:if>
							<c:if test="${getCol.statusnow=='03'}">
								<font color='red'></c:if>
							${getCol.statusExt}
						</td>
						<td height="21">
							${getCol.typeExt}
						</td>
						<td height="21" bgcolor="#FFFFFF">
							${getCol.starttime}
						</td>
						<td height="21">
							${getCol.endtime}-
						</td>
						<td height="21">

							<a href="javascript:useprocess('','run','${getCol.runtimeid}')">[����]</a>
							<a href="javascript:useprocess('','init','${getCol.runtimeid}')">[��ʼ]</a>
							<a href="javascript:useprocessview('','${getCol.runtimeid}')">[���]</a>
							<a
								href="javascript:useprocessview('subflow','${getCol.runtimeid}')">[������]</a>
							<a href="javascript:checkthis('${getCol.runtimeid}')">[ѡ��]</a>
							<a href="javascript:worklistinfo('${getCol.runtimeid}')">[�����ʵ���б�]</a>
							<a href="javascript:Relevantvarinfo('${getCol.runtimeid}')">[��������б�]</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<script type="text/javascript">
					//��ѡ�� ��Ҫ����Ӧ�õ���Ҫ������
				function checkthis(id) {
				if(opener.opener.selecthis==undefined){
					alert('��ѡ��״̬');
					return;
				}
					opener.opener.selecthis(id);
					window.close();
					opener.close();
				}
			</script>

		</form>
	</body>
</html>
