<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>JSTL: SQL action examples</title>
	</head>
	<body bgcolor="#FFFFFF">

		<h1>
			SQL Query Execution using an iterator
		</h1>

		<sql:setDataSource var="example" driver="com.mysql.jdbc.Driver"
			url="jdbc:mysql://localhost:3306/teach" user="root" password="123" />
		<sql:query var="deejays" dataSource="${example}">
    SELECT * FROM mytable
  </sql:query>
		<hr>
		<h2>
			Iterating over each Row of the result
		</h2>

		<table border="1">
			<c:forEach var="row" items="${deejays.rowsByIndex}">
				<tr>
					<c:forEach var="column" items="${row}">
						<td>
							${column}
						</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
		<hr>
		<h2>
			Iterating over Columns without knowing the index
		</h2>

		<table border="1">
			<c:forEach var="row" items="${deejays.rows}">
				<tr>
					<td>
						Name:${row.NAMEID}
					</td>
					<td>
						Value: ${row.NAME}
					</td>
				</tr>
			</c:forEach>
		</table>
		<hr>

		<h2>
			Putting it all together
		</h2>

		<%-- Easiest example showing how to populate a table --%>
		<table border="1">
			<tr>
				<%-- Get the column names for the header of the table --%>
				<c:forEach var="columnName" items="${deejays.columnNames}">
					<th>
						${columnName}
					</th>
				</c:forEach>

				<%-- Get the value of each column while iterating over rows --%>

				<c:forEach var="row" items="${deejays.rowsByIndex}">
					<tr>
						<c:forEach var="column" items="${row}">
							<td>
								${column}
							</td>
						</c:forEach>
				</c:forEach>
		</table>

	</body>
</html>

