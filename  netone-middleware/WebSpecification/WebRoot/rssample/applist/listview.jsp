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

		<title>����</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/oe.css">
		<script type="text/javascript" src="rsinclude/page.js"></script>
		<script type="text/javascript" src="rsinclude/applist/applist.js"></script>
		<script type="text/javascript">
		//��ѯ�߼������Զ�����չ����,SQL��������,�������� created ��Ч���� active 1/0 ��չ���� extendattribute
		function search(){
			//Ĭ�ϵ�һ����չ����,ֻ����ʾactive!='0'�ļ�¼
			document.getElementById('ext').value="and active='1' and apptype='publicinfo'";
			form1.action="ApplistRightSvl";
			form1.submit();
		}
		
		function view(id){
			window.open("ApplistModifySvl?task=edit&chkid="+id+"&pagename=publicview","usermod","width=700,height=350,resizable=yes,left=150,status=yes");
		}
	
		</script>
	</head>
	<body style="font-size: 12px;margin: 22px">
		<div style="width: 100%;height: 100%">

			<form action="" method="post" name="form1">
				<marquee behavior='scroll' direction='up' width='100%' height='100%'
					scrollamount='1' scrolldelay='60'>
					<table width="50%" border="0" align="left">
						<c:forEach items="${list}" var="list">
							<tr>
								<td nowrap>
									[<a href="javascript:view('${list.id}');">${list.name}</a>] &nbsp;${list.created}<br>
									<font color='${list.extendattribute}'>${list.description}</font>
								</td>
							</tr>
						</c:forEach>
					</table>
				</marquee>
				<table>
					<tr>
						<td></td>
						<td align='right'>
							<a href="ApplistRightSvl?pagename=publicviewall&ext= and apptype='publicinfo'  and active='1'" target='_blank'>>>����</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
