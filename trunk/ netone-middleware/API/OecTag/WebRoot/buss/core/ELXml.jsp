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
			Ӧ��JSTL��ǩ����XML, ����ϸ�ڲο� JSTL_SPECIFICATION.pdf
		</h2>

		<h3>
			Books Info:
		</h3>
		XML��Ϣ��
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
		
		
		<b>���ҵ�һ����,Xpath�﷨($output/books/book[1]/title):</b> :
		<x:out select="$output/books/book[1]/title" />
		<br>
		<b>���ҵڶ�����,Xpath�﷨($output/books/book[2]/price):</b> :
		<x:out select="$output/books/book[2]/price" />
	</body>
</html>
