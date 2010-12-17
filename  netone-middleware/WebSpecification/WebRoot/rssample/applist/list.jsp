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

		<title>�������</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/oe.css">
		<script type="text/javascript" src="rsinclude/page.js"></script>
		<script type="text/javascript" src="rsinclude/applist/applist.js"></script>
		<script type="text/javascript">
		//ִ�е�����ڵ����

		//��ѡ�� ��Ҫ����Ӧ�õ���Ҫ������
		function check(chkid){
			if(chkid){
				window.open("ApplistModifySvl?task=edit&chkid="+chkid+"&pagename=publiccheck","usermod","width=700,height=350,resizable=yes,left=150,status=yes");
			} else {
				var k = 0;
				var value;
				for(var i=0 ; i<form1.elements.length ; i++) {
					if (form1.elements[i].name=="chkid") {
						if(form1.elements[i].checked==true){
							value = form1.elements[i].value;
							k++;
						}
					}
				}
				if(k==0){
					alert("����ѡ����Ҫ�޸ĵ�ѡ��");
					return;
				}
				if(k>1){
					alert("ֻ��ѡ��������޸�");
					return;
				}
				window.open("ApplistModifySvl?task=edit&chkid="+value+"&pagename=publiccheck","usermod","width=700,height=350,resizable=yes,left=150,status=yes");
			}
		}
		
		//��ѯ�߼������Զ�����չ����,SQL��������,�������� created ��Ч���� active 1/0 ��չ���� extendattribute
		function search(){
			//Ĭ�ϵ�һ����չ����,ֻ����ʾactive!='0'�ļ�¼
			document.getElementById('ext').value=" and apptype='publicinfo'";
			form1.action="ApplistRightSvl";
			form1.submit();
		}
		
		function preview(){
			window.open("ApplistRightSvl?pagename=publicview&ext=and apptype='publicinfo' and active='1'",'_blank');
		}
		</script>

	</head>
	<body style="font-size: 12px;margin: 22px">
		<div style="width: 100%;height: 100%">

			<form action="" method="post" name="form1">
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="ext" value="" />

				<table width="90%" border="0">
					<tr>
						<td nowrap>
							&nbsp;&nbsp;����
						</td>
						<td width='100'>
							<input type="text" name="naturalname" value="${naturalname}"
								class="textinput_td">
						</td>
						<td nowrap>
							����
						</td>
						<td width='100'>
							<input type="text" name="name" value="${name}"
								class="textinput_td">
						</td>
						<td>
							<input type="button" name="searchbtn" value="�� ѯ"
								onclick="search();" class="butt">
						</td>
						<td>
							<input type="button" name="searchbtn" value="Ԥ ��"
								onclick="preview();" class="butt">
						</td>
						<td align='right' nowrap>
							<input type="button" name="btncreate" value="�� ��"
								onclick="newElemnt();" class="butt">
							&nbsp;
							<input type="button" name="btnmodify" value="�� ��"
								onclick="edit();" class="butt">
							&nbsp;
							<input type="button" name="btnmodify" value="�� ��"
								onclick="check();" class="butt">
							&nbsp;
							<input type="button" name="btndelete" value="ɾ ��"
								onclick="del();" class="butt">
						</td>
					</tr>
				</table>

				<br>
				<table id="hang_table" width="96%" border="0" align="center"
					cellpadding="0" cellspacing="1">
					<tr>
						<td width="70" class="td_titt_bg" nowrap>
							<input type="checkbox" name="checkall" onclick="allcheck();">
							ѡ��
						</td>
						<td class="td_titt_bg" nowrap>
							����
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							����
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							��������
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							����
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							����ͨ��
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							����
						</td>
					</tr>
					<c:forEach items="${list}" var="list">
						<tr>
							<td nowrap>
								<input type="checkbox" name="chkid" value="${list.id}">
							</td>
							<td nowrap>
								${list.naturalname}
							</td>
							<td nowrap>
								${list.name}
							</td>
							<td nowrap>
								${list.created}
							</td>
							<td nowrap>
								${list.apptype}
							</td>
							<td nowrap>
								<c:if test="${list.active=='1'}">
									YES
								</c:if>
								<c:if test="${list.active=='0'}">
									NO
								</c:if>
							</td>
							<td nowrap>
								<a href="javascript:edit('${list.id}');">�޸�</a>
								<a href="javascript:del('${list.id}');">ɾ��</a>
								<a href="javascript:check('${list.id}');">����</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<script type="text/javascript">
					var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
					pginfo.write();
				</script>

			</form>
		</div>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("�޸�ʧ�ܣ�");
				form1.action="ApplistRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DeleteSuccess == 'n'}">
			<script type="text/javascript">
				alert("ɾ��ʧ��");
				form1.action="ApplistRightSvl";
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
