<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<HTML dir="LTR" lang="zh-CN">
	<HEAD>
		<TITLE></TITLE>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oec.css">
		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
	
		<!-- ������û����������Ĺ���,������Ҫ�ֹ����������<p:login/>��Ҫ���ҳ�������ʿ��� -->
		����
		<rs:logininfo />

		,���� ��Դ <strong><font color='red'>BUSSWF.BUSSWF.MYFLOW</font></strong>
		<br><br>
		�����ʸ���?
		<rs:permission action="1" resource="BUSSWF.BUSSWF.MYFLOW">YES</rs:permission>
		<rs:noPermission action="1" resource="BUSSWF.BUSSWF.MYFLOW">NO</rs:noPermission>
		<br><br>
		��Ȩ����?
		<rs:permission action="3" resource="BUSSWF.BUSSWF.MYFLOW">YES</rs:permission>
		<rs:noPermission action="3" resource="BUSSWF.BUSSWF.MYFLOW">NO</rs:noPermission>
		<br><br>
		�����Ȩ����?

		<rs:permission action="7" resource="BUSSWF.BUSSWF.MYFLOW">YES</rs:permission>
		<rs:noPermission action="7" resource="BUSSWF.BUSSWF.MYFLOW">NO</rs:noPermission>
		<br>
	
	
	</BODY>
</HTML>
