<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>

<%@ taglib uri="http://www.oesee.com/netone/dy" prefix="dy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>Insert title here</title>
	</head>
	<body>
	<h3>ʹ�ö�̬��+����ı���ʾҳ������</h3>
		ͨ����ȡ��̬���е����ݣ������ݽ��д��������ö���ı�����ԴID�����ö���ı�������ʾ<br>
		<b>��������</b><br>
		ʹ�ö�̬����ǩ��������ݣ���ʾҳ��
		<hr>
		<dy:dydata dataname="mydata"
			fatherlsh="956a2c2bb92311dd84e2db5c1bf50150"
			formcode="017806cfb92311dda932b9bba8510e29_" prepage="10"/>
		<table border='1' width='800' height='220'>
			<tr>

				<td>
					<iframe
						src='http://192.168.2.104:8080/fck/PagelistViewSvl?chkid=${fn:substring(mydata[0].extendattribute,6,38)}&pagename=simplefcklist'
						frameborder='0' width='560' height='210'></iframe>
				</td>

				<td>
					<table width='200'>
						<c:forEach items="${mydata}" var="data">
							<tr>
								<td>
									<strong><font size='2'>[${fn:substring(data.created,0,10)}]</font>
									</strong>
									<br>
									<strong>
									<a href='http://192.168.2.104:8080/fck/PagelistViewSvl?chkid=${fn:substring(data.extendattribute,47,79)}&pagename=simplefcklist' target='_blank'><font color='#0066CC' size='2'>${data.column3}</font></a>
									</strong>
								</td>
							</tr>

						</c:forEach>

					</table>
				</td>
			</tr>
		</table>

	</body>
</html>