<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<TITLE></TITLE>
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</head>
	<body bgcolor="#FFFFFF">
		<h2>
			应用JSTL标签操作XML, 更多细节参考 JSTL_SPECIFICATION.pdf
		</h2>

		<h3>
			Books Info:
		</h3>
		XML信息：
		<textarea rows="12" cols="60">
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
		</textarea>
		<br><br>
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
		
		
		<b>查找第一本书,Xpath语法($output/books/book[1]/title):</b> :
		<x:out select="$output/books/book[1]/title" />
		<br>
		<b>查找第二本书,Xpath语法($output/books/book[2]/price):</b> :
		<x:out select="$output/books/book[2]/price" />
	</body>
</html>
