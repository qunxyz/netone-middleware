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
			
		<style type="text/css">
			#queryprocess{
				border-bottom-color: #cccccc;
				border-bottom-width: 2px;
				border-top-color: #cccccc;
				border-top-width: 2px;
				height: 50px;
				width: 100%;
			}
		</style>
		
		<script type="text/javascript">
		
			function onready(){

			}
		
			function queryProcess(){

				var pname = document.getElementById('pname').value;
				var url = "";

				if(pname != "" && pname != null){
					url = "/wfweb/listUseprocess.do?processid="+pname+"&operate=t";
					document.forms[0].action = "/wfweb/listUseprocess.do?processid="+pname+"&operate=t";
				}else{
					url = "/wfweb/listUseprocess.do?processid="+'param.processid';
					document.forms[0].action = "/wfweb/listUseprocess.do?processid="+'${param.processid}'+pname+"&operate=t";
				}
				document.forms[0].submit();
			}
		</script>
	</head>
	<body onload="onready()">
		<form method="post" action="/wfweb/listUseprocess.do">
			<input name="pathinfo" type="hidden" value=<%=path%>>
			<br>
			<font class='Text1'>��ǰ����:${processname}</font>&nbsp;
			<input type="button" value="��������"
				onclick="useprocess('${param.processid}','new','');" class="butt" />
			<div id="queryprocess"></br>
				<div>��ѯ</div>
				<label for="pname">�������ƣ�</label><input id="pname" name="pname" type="text"/>
				&nbsp;&nbsp;
					<label for="pstate">״̬��</label>
					<select name="pstate">
						<option selected="selected" value="10">��ѡ��</option>
						<option value="00">׼����</option>
						<option value="01">ִ����</option>
						<option value="02">���</option>
						<option value="03">�쳣</option>
						<option value="04">����</option>
					</select>&nbsp;&nbsp;
				<label for="starttime">��ʼʱ�䣺</label><input type="text" name="starttime" />&nbsp;&nbsp;
				<label for="endtime">����ʱ�䣺</label><input type="text" name="endtime" />&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;
			<input type="button" value="��ѯ" onclick="queryProcess()"/>

			</div>
			<table width='100%' cellspacing="0" border="1" cellpadding="2"
				bordercolordark="#999999" bordercolorlight="#FFFFFF">
				<tr class='tdheadline'>

					<td nowrap>
						������
					</td>
					<td nowrap>
						��ʾ��
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
							${getCol.PROCESSID}
						</td>
						
						<td height="21">
							${pname }
						</td>

						<td height="21" bgcolor="#FFFFFF">
							<c:if test="${getCol.STATUSNOW=='00'}">
								<font color='blue'>׼����
							</c:if>
							<c:if test="${getCol.STATUSNOW=='01'}">
								<font color='green'>ִ����
							</c:if>
							<c:if test="${getCol.STATUSNOW=='04'}">
								<font color='black'>����
							</c:if>
							<c:if test="${getCol.STATUSNOW=='03'}">
								<font color='yellow'>�쳣
							</c:if>
							<c:if test="${getCol.STATUSNOW=='02'}">
								<font color='red'>���</c:if>
							
						</td>
						<td height="21">
							<c:if test="${getCol.KIND=='01'}">
								������
							</c:if>
							<c:if test="${getCol.KIND=='02'}">
								��֧����
							</c:if>
						</td>
						<td height="21" bgcolor="#FFFFFF">
							${getCol.STARTTIME}
						</td>
						<td height="21">
							${getCol.ENDTIME}-
						</td>
						<td height="21">

							<a href="javascript:useprocess('','run','${getCol.RUNTIMEID}')">[����]</a>
							<a href="javascript:useprocess('','init','${getCol.RUNTIMEID}')">[��ʼ]</a>
							<a href="javascript:useprocessview('','${getCol.RUNTIMEID}')">[���]</a>
							<a
								href="javascript:useprocessview('subflow','${getCol.RUNTIMEID}')">[������]</a>
							<a href="javascript:checkthis('${getCol.RUNTIMEID}')">[ѡ��]</a>
							<a href="javascript:worklistinfo('${getCol.RUNTIMEID}')">[�����ʵ���б�]</a>
							<a href="javascript:Relevantvarinfo('${getCol.RUNTIMEID}')">[��������б�]</a>
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
