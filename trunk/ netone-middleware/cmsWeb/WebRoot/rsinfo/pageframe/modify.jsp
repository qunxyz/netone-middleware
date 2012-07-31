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
		<title>�޸�ҳ��</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<SCRIPT type="text/javascript"
			src="<%=path%>\include\js\modelshow\personmodel.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
		<script type="text/javascript">
				//����ѡ��ҳ��
		function searchtree() {
			window.open("SelectSvl?appname=BUSSENV&pagename="+document.all.pagename.value);
		}
				//ִ�е�����ڵ����
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
			document.all.objecttype.value = name+"["+naturalname+"]";
		}
		
		function sselected(name,value){
				   var url=$('pagetypeinfo').value;
				   $('actionurl').value=url+value;
		}
		</script>
	</head>
	<body style="font-size: 12px; margin: 22px">
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
		<div style="width: 100%; height: 100%">
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
					<tr style='display:none'>
						<td width="15%">
							<a href="javascript:searchtree();"><font color='blue'>ѡ�����</font>
							</a>
						</td>
						<td width='300'>
							<input type="text" name="objecttype" value="${upo.objecttype}"
								class="textinput_td" readonly="readonly">
						</td>
					</tr>
					<tr style='display:none' id='pagegroup'>
						<td width="15%">
							<font color='blue'>ҳ��</font>
						</td>
						<td>
							<select name='pagetypeinfo'>
								<option
									value='/cmsWeb/portal.do?toolbar=true&border=true&portalmode=1&id='>
									��������
								</option>
								<option value='/cmsWeb/portal.do?border=true&portalmode=2&id='>
									�޿��������߿�
								</option>
								<option value='/cmsWeb/portal.do?portalmode=3&id='>
									�޿������ޱ߿�
								</option>
							</select>
							<a href="/cmsWeb/SSelectSvl?pagename=pagegroup&appname=PORTALPG" target='_blank'>[ѡ��ҳ��]</a> <A HREF="javascript:" onclick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=pagegroup&appname=PORTALPG','_blank')"><FONT
						class="OecLink">����/����ҳ��</FONT> </A>
						</td>
					</tr>
					<tr>
						<td>���õ�ַ</td>
						<td>
						<input type="text" name="actionurl" class="textinput_td" value="${upo.actionurl}"/> &nbsp;<input type='checkbox' name='usepagegroup' onClick="if(this.checked)pagegroup.style.display='';else pagegroup.style.display='none'; "/>ʹ��ҳ��
						</td>
					</tr>

					<tr>
						<td width="15%">
							�������
						</td>
						<td>
						
							<select name='extendattribute'>
								<option value='normal'>��ͨҳ</option>
								<option value='normal-close' <c:if test="${upo.extendattribute=='normal-close'}">selected</c:if>>��ͨҳ-��չ��</option>
								<option value='final'  <c:if test="${upo.extendattribute=='final'}">selected</c:if>>����ҳ</option>
							</select>
							<input type="hidden" name="needSerilaizer" value="1" />
						
						</td>
					</tr>
					<tr>
						<td width="15%">
							����
						</td>
						<td>
							<input type="text" name="aggregation" value="${upo.aggregation}" />
						</td>
					</tr>
					<tr>
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
