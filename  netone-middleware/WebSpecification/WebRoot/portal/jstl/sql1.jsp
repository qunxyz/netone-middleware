<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>JSTL: SQL action examples</title>
</head>
<body bgcolor="#FFFFFF">

<h1>CREATE TABLE</h1>

<sql:setDataSource
  var="example"
  driver="com.mysql.jdbc.Driver"
  url="jdbc:mysql://localhost:3306/teach"
  user="root"
  password="123"
/>

<sql:transaction dataSource="${example}">
  <sql:update var="newTable">
    create table mytable (
      nameid int primary key,
      name varchar(80)
    )
  </sql:update>
  <sql:update var="updateCount">
    INSERT INTO mytable VALUES (1,'Paul Oakenfold')
  </sql:update>
  <sql:update var="updateCount">
    INSERT INTO mytable VALUES (2,'Timo Maas')
  </sql:update>
  <sql:update var="updateCount">
    INSERT INTO mytable VALUES (3,'Paul van Dyk')
  </sql:update>
</sql:transaction>

</body>
</html>

