<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<title>�޸��ӽ��</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript">
				//����ѡ��ҳ��
		function searchtree() {
			window.open("SelectSvl?appname=BUSSENV&pagename="+document.all.pagename.value);
		}
				//ִ�е�����ڵ����
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
			document.all.objecttype.value = name+"["+naturalname+"]";
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
							��&nbsp;&nbsp;&nbsp;&nbsp;ȫ
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
							����
						</td>
						<td width='300'>
							<input type="text" name="objecttype" value="${upo.objecttype}"
								class="textinput_td" readonly="readonly">
						</td>
					</tr>
					<tr>
						<td width="15%">
							����
						</td>
						<td>
							<input type="text" name="actionurl" value="${upo.actionurl}"
								class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td colspan='2'>
							�༭ҳ�ű�:
						<a href='rsinfo/jspapp/Template.txt' target='_blank'><font color='#999999'>[�ο�ģ��1]</font></a>
						<a href='rsinfo/jspapp/Template1.txt' target='_blank'><font color='#999999'>[�ο�ģ��2]</font></a>
						<a href='rsinfo/jspapp/Template2.txt' target='_blank'><font color='#999999'>[�ο�ģ��3]</font></a>
						<a href='rsinfo/jspapp/resourceweb.zip' target='_blank'><font color='green'>[��Դҳģ���]</font></a>
<a href='rsinfo/jspapp/flowpage.zip' target='_blank'><font color='green'>[����ҳģ���]</font></a>
						<a href='rsinfo/jspapp/AppFrame1.txt' target='_blank'><font color='green'>[ϵͳҳ��1]</font></a>
						<a href='rsinfo/jspapp/AppFrame2.txt' target='_blank'><font color='green'>[ϵͳҳ��2]</font></a>
						
						<a href='rsinfo/jspapp/netone_tag_all.doc' target='_blank'><font color='red'>[ҳ��ǩ���Ӵ�ȫ]</font></a>
						<a href='rsinfo/jspapp/OecWeb.zip' target='_blank'><font color='red'>[ҳ��ǩ������]</font></a>
							<textarea rows="30" cols="120" name="extendattribute">${upo.extendattribute}</textarea>
							<br>
							<input type="hidden" name="needSerilaizer" value="1" />
						</td>
					</tr>
					<tr style='display:none'>
						<td width="15%">
							����
						</td>
						<td>
							<textarea rows="4" cols="60" name="description">${upo.description}</textarea>
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
