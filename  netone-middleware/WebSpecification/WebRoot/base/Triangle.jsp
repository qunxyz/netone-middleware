<%@page import="oe.teach.web.base.Triangle"%>
<html>
	<body>
			<div align='center'>
			<%!int cycle = 9;%>
			<%
					Triangle triangle = new Triangle(cycle);
					for (int i = 0; i <= cycle; i++) {
						for (int j = 0; j <= i; j++) {
			%>
			<%=(int) triangle.recursion(i, j)%>
			&nbsp;
			<%}%>
			<br>
			<%}%>
			</div>
	</body>
</html>
