<%@ page language="java" pageEncoding="GBK"%>
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="java.util.HashMap" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	//�������� д�뷽ʽ1
	Map map = new HashMap();
	map.put("a", "1");
	map.put("b", "2");

	Map map2 = new HashMap();
	map2.put("c", "3");
	//��map��Ƕ��map2
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
			Ӧ��JSTL��ǩ��д����, ����ϸ�ڲο� JSTL_SPECIFICATION.pdf
		</h2>

		<!-- ҳ���������д�뷽ʽ2 -->
		<c:set var="aaaa" value='true'></c:set>
		<br>
		<br>
		��ȡ���� aaaa��ֵ: ${aaaa}

		<br>
		<br>
		��ȡ����Map map��key;a��Ӧ��ֵ: ${map.a}
		<br>
		<br>
		��ȡ����Map map��key;c��Ӧ��map2��Key;c��ֵ: ${map.c.c}
		<br>
		<br>
		��ȡ��������� bbbb+10��ֵ:${param.bbbb+10}
		<br>
		<form>
			bbbb��������:
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
