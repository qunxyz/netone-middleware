<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>

<html>
	<head>
		<title>JSTL Support for XML</title>
	</head>
	<body bgcolor="#FFFFCC">
		<h3>
			Books Info:
		</h3>

		<c:set var="xmltext">
			<books>
			<book>
			<title>Book Title A</title>
			<author>A. B. C.</author>
			<price>17.95</price>
			</book>
			<book>
			<title>Book Title B</title>
			<author>X. Y. Z.</author>
			<price>24.99</price>
			</book>
			</books>
		</c:set>
		
		<x:parse xml="${xmltext}" var="output" />
		<b>The title of the first book is</b> :
		<x:out select="$output/books/book[1]/title" />
		<br>
		<b>The price of the second book</b> :
		<x:out select="$output/books/book[2]/price" />
	</body>
</html>
