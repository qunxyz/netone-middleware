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

		<title>ά������</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/oe.css">
		<script type="text/javascript" src="rsinclude/page.js"></script>
		<script type="text/javascript" src="rsinclude/applist/applist.js"></script>
		<script type="text/javascript">
		//ִ�е�����ڵ����
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
		
		}
		//��ѡ
		function checkthis(name,natural) {
			//alert(id+'_'+name+'_'+chinesename);
			opener.addselect(name+'['+natural+']');
			window.close();
		}
		
		//��ʾ
		function viewthis(id) {
			window.open("SelectSvl?appid="+id+"&pagename="+document.all.pagename.value);
		}
		
		//��ѯ�߼������Զ�����չ����,SQL��������,�������� created ��Ч���� active 1/0 ��չ���� extendattribute
		function search(){
			//Ĭ�ϵ�һ����չ����,ֻ����ʾactive!='0'�ļ�¼
			document.getElementById('ext').value="and active!='0' and naturalname='PORTALPG'";
			form1.action="ApplistRightSvl";
			form1.submit();
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
							��������
						</td>
						<td width='100'>
							<input type="text" name="name" value="${name}"
								class="textinput_td">
						</td>
						<td>
							<input type="button" name="searchbtn" value="�� ѯ"
								onclick="search();" class="butt">
						</td>
						<td align='right' nowrap>
							<input type="button" name="btncreate" value="�� ��"
								onclick="newElemnt();" class="butt">
							&nbsp;
							<input type="button" name="btnmodify" value="�� ��"
								onclick="edit();" class="butt">
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
							��������
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							��������
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							��Ч
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							����
						</td>
					</tr>
					<c:forEach items="${list}" var="list">
						<c:if test="${list.naturalname=='PORTALPG'}">
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
									<c:if test="${list.active=='1'}">
									YES
								</c:if>
									<c:if test="${list.active=='0'}">
									NO
								</c:if>
								</td>
								<td nowrap>
									<a href="javascript:edit('${list.id}');">[�޸�]</a>
									<a href="javascript:del('${list.id}');">[ɾ��]</a>
									<a href="javascript:viewthis('${list.id}');">[�鿴]</a>
									<a
										href="javascript:checkthis('${list.name}','${list.naturalname}','${list.name}');"><font
										color='red'>[ѡ��]</font> </a>

									<a
										href="javascript:managerthis('${list.naturalname}','path','datalist');"><font
										color='blue'>[����]</font>
									</a>
								</td>
							</tr>
						</c:if>
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
