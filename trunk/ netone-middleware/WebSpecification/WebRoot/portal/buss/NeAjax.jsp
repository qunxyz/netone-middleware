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

		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
		<script type="text/javascript">
    function todo(){
    	var param=$('sel1').value;
    	var parser = new Ajax.Request(
						"NeAjaxData.jsp",
						{method:"get",parameters:"need="+param,asynchronous:false}
						);
		 var restr = parser.transport.responseText;
		 DivBlock.innerHTML = restr ;
    
    }
    </script>
	</HEAD>

	<BODY bgcolor="#FFFFFF">
		<table border='1'>
			<tr>
				<td>
					1级菜单
				</td>
				<td>
					2级菜单
				</td>
			</tr>
			<tr>
				<td>
					<select name='sel1' onChange='todo()'>
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
				</td>
				<td>
					<div id='DivBlock'></div>
				</td>
			</tr>
		</table>



	</BODY>
</HTML>
