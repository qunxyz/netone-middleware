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
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<SCRIPT TYPE="text/javascript">
		</SCRIPT>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<h2>
			Ӧ��Oesee Netone��ǩ���JSTL������ҳ�����Ȩ��
		</h2>
	
		<!-- ������û����������Ĺ���,������Ҫ�ֹ����������<p:login/>��Ҫ���ҳ�������ʿ��� -->
		����
		<rs:logininfo />

		,���� ��ԴRSSAMPLE.RSSAMPLE.DIRA
		<br><br>
		���и߼�Ȩ����?
		<br>
		<rs:permission action="7" resource="RSSAMPLE.RSSAMPLE.DIRA">YES</rs:permission>
		<rs:noPermission action="7" resource="RSSAMPLE.RSSAMPLE.DIRA">NO</rs:noPermission>
		<br>
		�м�Ȩ����?
		<br>
		<rs:permission action="3" resource="RSSAMPLE.RSSAMPLE.DIRA">YES</rs:permission>
		<rs:noPermission action="3" resource="RSSAMPLE.RSSAMPLE.DIRA">NO</rs:noPermission>
		<br>
		�����ʸ���?
		<br>
		<rs:permission action="1" resource="RSSAMPLE.RSSAMPLE.DIRA">YES</rs:permission>
		<rs:noPermission action="1" resource="RSSAMPLE.RSSAMPLE.DIRA">NO</rs:noPermission>
	</BODY>
</HTML>
