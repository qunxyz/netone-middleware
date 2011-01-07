<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="java.util.List" />
<jsp:directive.page import="java.util.ArrayList" />
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.HashMap" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	//构造参数 1
	List list = new ArrayList();
	list.add(new String[] { "a", "b" });
	list.add(new String[] { "a1", "b1" });
	list.add(new String[] { "a2", "b2" });
	request.setAttribute("list", list);
	
	
	//构造参数 2
	List list1 = new ArrayList();
	Map map1 = new HashMap();
	map1.put("name", "Mike");
	list1.add(map1);

	Map map2 = new HashMap();
	map2.put("name", "Jim");
	list1.add(map2);
	request.setAttribute("list1", list1);
%>
<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<h2>
			JSTL中的循环逻辑的应用 , 更多细节参考 JSTL_SPECIFICATION.pdf
		</h2>

		<h5>
			遍历参数1的List中的数组的数据
		</h5>
		<c:forEach items="${list}" var="each">
		${each[0]},${each[1]} <br>
			<br>
		</c:forEach>
		<h5>
			遍历参数2的List中的map的数据
		</h5>
		<c:forEach items="${list1}" var="each">
			${each.name} <br>
		</c:forEach>
	</BODY>
</HTML>
