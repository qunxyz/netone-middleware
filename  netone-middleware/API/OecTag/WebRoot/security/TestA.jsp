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
		�ҽ�
		<rs:logininfo></rs:logininfo>
		,���Ѿ��ɹ�������
		<br>
		<br>
		<br>
		<INPUT type='button' value='���ʰ�ȫ��̨'
			onClick="window.open('<rs:accessSecurity/>')" class='butt' />
		<a href='<rs:accesslog/>' target='_blank'>������־</a>
		<a href='<rs:changepassword/>' target='_blank'>�޸ĸ�������</a>
		<a href='<rs:userinfo/>' target='_blank'>������Ϣ</a>

		<a href='<rs:rolemanage/>' target='_blank'>��ɫ����</a>
		<a href='<rs:rolemanageTree/>' target='_blank'>��ɫ������</a>
		<a href='<rs:usermanageTree/>' target='_blank'>�û�������</a>
		<a href='<rs:usermanage/>' target='_blank'>�û�����</a>
		<a href='<rs:loginout/>'>ע��</a>


	</BODY>
</HTML>
