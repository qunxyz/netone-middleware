<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<title>JSTL: SQL action examples</title>
	</head>
	<body bgcolor="#FFFFFF">

		<h1>
			DROP Table
		</h1>

		<sql:setDataSource var="example" driver="com.mysql.jdbc.Driver"
			url="jdbc:mysql://localhost:3306/teach" user="root" password="123" />

		<sql:update var="newTable" dataSource="${example}">
  drop table mytable
</sql:update>

	</body>
</html>

