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
		<rs:logininfo /> ��ǰ����ҵ���
		<hr>
		<table border ='1'>

			<tr>
				<td width='100'>
					����
				</td>
				<td>
					<rs:permission action="3" resource="TESTDEMO.TESTDEMO.ADUITING">
						<select name='check'>
							<option>
								ͬ��
							</option>
							<option>
								��ͬ��
							</option>
						</select>
					</rs:permission>
					&nbsp;
				</td>
			</tr>

			<tr>
				<td>
					����
				</td>
				<td>   
					 
					   
						<input type='text' name='info' value='�༭����' <rs:noPermission action="7" resource="TESTDEMO.TESTDEMO.BODY">readonly</rs:noPermission>>
					
				</td>
			</tr>
						<tr>
				<td>
					&nbsp;
				</td>
				<td>
					
					<input type='button' value='�ύ'>
					
				</td>
			</tr>
		</table>


	</BODY>
</HTML>
