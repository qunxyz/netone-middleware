<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//����Զ����ҵ����ʱ����
	String entryvar = (String) request.getAttribute("entryvar");
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>Ӧ�û�������</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript" src="rsinclude/calendar.js"></script>
		<script type="text/javascript" src="include/js/workflow/soa.js"></script>
		<script type="text/javascript" src="include/js/workflow/soa2.js"></script>
		<script type="text/javascript" src="include/js/workflow/soa4.js"></script>
		<script type="text/javascript" src="include/js/workflow/soax.js"></script>
		<script type="text/javascript">
			//��ѡ�� ��Ҫ����Ӧ�õ���Ҫ������
			function checkthis(id) {
				alert(id)
			}
			//��ѡ�� ��Ҫ����Ӧ�õ���Ҫ������
			function checkthese(){
				var values = null;
				var checkids = document.getElementsByTagName("input");
				for(var i=0 ; i<checkids.length ; i++){
					if(checkids[i].type=="checkbox"){
						if(checkids[i].checked && checkids[i].name!='checkall'){
							if(values == null){
								values = checkids[i].value;
							} else {
								values = values + "," + checkids[i].value;
							}
						}
					}
				}
				if(values==null){
					alert('û��ѡ���Ԫ��');
				}else{
					alert(values);
				}
				
			}
			
			//ִ�е�����ڵ����
			function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
				document.all.spathname.value = name;
				document.all.spath.value = id;
			}
			
			
			//����ѡ��ҳ��
			function searchtree() {
				window.open("SelectSvl?appid="+document.all.appid.value+"&pagename="+document.all.pagename.value);
			}
		
			
			
		</script>

	</head>
	<body style="font-size: 12px; margin: 22px">

		<div style="width: 100%; height: 100%">
			<form action="" method="post" name="form1">
				<input type="hidden" name="pagename" value="participant" />

				<table id="hang_table" width="80%" border="0" cellpadding="0"
					cellspacing="1">
					<tr>
						<td width="70" style='display:none'>
							<input type="checkbox" name="checkall" onclick="allcheck();">
							ѡ��
						</td>
						<td class="td_titt_bg" nowrap>
							��������
						</td>
						<td class="td_titt_bg" nowrap>
							����
						</td>
						<td class="td_titt_bg" nowrap>
							����
						</td>

						<td class="td_titt_bg" nowrap>
							�ֶ�
						</td>
						<td class="td_titt_bg" nowrap>
							����
						</td>
					</tr>

					<c:forEach items="${listxz}" var="list">
						<tr>
							<td style='display:none'>
								<input type="checkbox" name="chkid" value="${list.id}">
							</td>
							<td align='left' nowrap>
								<a href="javascript:view('${list.id}');">${list.name}</a>
							</td>
							<td align='left' nowrap>
								${list.naturalname}
							</td>
							<td align='left' nowrap>

								<c:if test="${list.objecttype=='human'}">��Ա</c:if>
													
								<c:if test="${list.objecttype=='team'}">��</c:if>
									
								<c:if test="${list.objecttype=='dept'}">��֯</c:if>
	
								<c:if test="${list.objecttype=='role'}">��ɫ</c:if>

							    <c:if test="${list.objecttype=='flowrole'}">���̽�ɫ</c:if>

								<c:if test="${list.objecttype=='creater'}">������</c:if>
								<c:if test="${list.objecttype=='flowrolecreater'}">���̴����߽�ɫ</c:if>							
								
							</td>
							<td nowrap>
								<c:if test="${list.active=='1'}">
									�ֶ�
								</c:if>
								<c:if test="${list.active=='0'}">
									�Զ�
								</c:if>
							</td>

							<td nowrap>
								<a href="javascript:edit('${list.id}');"><font color='blue'>[����]</font>
								</a>
								<a href="javascript:del('${list.id}');"><font color='blue'>[ɾ��]</font>
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>

				<iframe src='<portal:envget envkey="WEBSER_WFWEB"/>/viewreadonlyprocess.do?processid=${processid }' frameborder="0" width="1000" height="400"></iframe>

			</form>
		</div>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("�޸�ʧ�ܣ�");
				form1.action="PagelistRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DeleteSuccess == 'n'}">
			<script type="text/javascript">
				alert("�ýڵ��´����ӽڵ�,������ɾ����");
				form1.action="PagelistRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DelSuccess == 'y'}">
			<script type="text/javascript">
				alert("ɾ���ɹ���");
			</script>
		</c:if>
	</body>
</html>
