<%@ page contentType="text/html; charset=GBK"%>



<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�����б�</title>

 

	</head>
	<body >
		<strong>������˶�̬</strong> <a href='<%=path%>/turnpageori/list' target='_blank'><font color='red'>[������Ŀ]</font></a>
		<form action="" name="form1"
			method="post">

				<c:forEach items="${listinfo}" var="getCol">
				
						<a href="<%=path%>/turnpageori/update?lsh=${getCol.lsh}"
								target='_blank'>${getCol.name}&nbsp;${getCol.curprice}&nbsp;${getCol.created}&nbsp;</a><br>
					
				</c:forEach>
		
		</form>
	</body>
</html>
