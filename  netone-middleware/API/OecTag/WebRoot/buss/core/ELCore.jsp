<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.HashMap" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	//参数属性 写入方式1
	Map map = new HashMap();
	map.put("a", "1");
	map.put("b", "2");

	Map map2 = new HashMap();
	map2.put("c", "3");
	//在map中嵌套map2
	map.put("c", map2);

	request.setAttribute("map", map);
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
			应用JSTL标签读写参数, 更多细节参考 JSTL_SPECIFICATION.pdf
		</h2>

		<!-- 页面参数属性写入方式2 -->
		<c:set var="aaaa" value='true'></c:set>
		<br>
		<br>
		读取参数 aaaa的值: ${aaaa}

		<br>
		<br>
		读取参数Map map的key;a对应的值: ${map.a}
		<br>
		<br>
		读取参数Map map的key;c对应的map2的Key;c的值: ${map.c.c}
		<br>
		<br>
		读取表单输入参数 bbbb+10的值:${param.bbbb+10}
		<br>
		<form>
			bbbb参数输入:
			<select name='bbbb'>
				<option value='1'>
					1
				<option value='2'>
					2
				<option value='3'>
					3
				<option value='4'>
					4
				<option value='5'>
					5
			</select>
			<input type='submit' value='todo' />
		</form>
	</BODY>
</HTML>
