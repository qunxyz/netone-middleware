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
		<!-- 过滤器没有配置这里的过滤,所以需要手工在这里加入<p:login/>来要求该页面做访问控制 -->
		我是
		<rs:logininfo /> 当前进入业务表单
		<hr>
		<table border ='1'>

			<tr>
				<td width='100'>
					审批
				</td>
				<td>
					<rs:permission action="3" resource="TESTDEMO.TESTDEMO.ADUITING">
						<select name='check'>
							<option>
								同意
							</option>
							<option>
								不同意
							</option>
						</select>
					</rs:permission>
					&nbsp;
				</td>
			</tr>

			<tr>
				<td>
					正文
				</td>
				<td>   
					 
					   
						<input type='text' name='info' value='编辑正文' <rs:noPermission action="7" resource="TESTDEMO.TESTDEMO.BODY">readonly</rs:noPermission>>
					
				</td>
			</tr>
						<tr>
				<td>
					&nbsp;
				</td>
				<td>
					
					<input type='button' value='提交'>
					
				</td>
			</tr>
		</table>


	</BODY>
</HTML>
