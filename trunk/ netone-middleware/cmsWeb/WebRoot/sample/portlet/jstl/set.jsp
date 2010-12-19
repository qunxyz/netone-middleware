<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Vector" %>
<html>
<head>
  <title>JSTL: Expression Language Support -- Set Example</title>
</head>
<body bgcolor="#FFFFFF">
<h3></h3>
<%  Vector v = new Vector();
	v.add("One"); v.add("Two"); v.add("Three"); v.add("Four");

	pageContext.setAttribute("vector", v);
%>
<h4>Setting application scope attribute "customerTable"</h4>

<c:set var="customerTable" scope="application">
	<table border="1">
    <c:forEach items="${vector}" var="item" >
    <tr>
	  <td>${item}</td>
	  <td><c:out value="${item}" default="###"/></td>
	</tr>
  </c:forEach>
</table>
</c:set>

${customerTable}
<hr>
<c:out value="${customerTable}"/> 



</body>
</html>

