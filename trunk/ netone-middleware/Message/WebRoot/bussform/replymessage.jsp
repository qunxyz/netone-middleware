<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>回复消息</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rinclude/page.js"></script>
		<script type="text/javascript" src="include/js/msg.js"></script>
	</head>
	<body onload='init()'>
		<br>
		${paramMap.alertMsg }
		<form action="bussform/SendMessage.do" method="post" name="form1">
			<input type="hidden" name="task" value="">

			<br>
			<table width="96%" border="0" align="center" cellpadding="3"
				cellspacing="1" id="lie_table">
				<tr>
					<td width="50" nowrap>
						标题
					</td>
					<td align="left">
						<input type="text" name="title" value="${title}"
							class="textinput_td">
						<input type="hidden" name="workid" value="${workid}">
					</td>
				</tr>
				<tr>
					<td width="50" nowrap>
						内容
					</td>
					<td align="left">
						<textarea rows="5" cols="90" name="content">${content}</textarea>
					</td>
				</tr>
				<tr>
					<td width="50" nowrap>
						接收人
					</td>
					<td align="left">
						<input type="text" name="recevier" value="${recevier}"
							class="textinput_td">
						&nbsp;
						<input type="button" value="选 择" class="butt"
							onclick="openuserselect();">


					</td>
				</tr>
				<tr>
					<td width="50" nowrap>
						附件
					</td>
					<td align="left">
						<select name="ext">

						</select>
						<a href="javascript:download();">下载</a>
						<input type="button" value="选 择" class="butt"
							onclick="openfileselect();">
						<input type="button" value="删 除" class="butt" onclick="delfile();">
						<input type="hidden" name="extattr3" value="${extattr3}">
					</td>
				</tr>
				<tr>
					<td width="50" nowrap>
						其它
					</td>
					<td align="left">
						<select name="ext2">
							
						</select>	
						<input type="hidden" name="extattr4" value="${extattr4}">
						<a href='javascript:bussdo()'><font color='blue'>访问</font></a>
					</td>
				</tr>
			</table>
			<br>
			<div align="center">
				<input type="button" value="发 送" class="butt"
					onclick="sendmessage();">
			</div>
		</form>
	</body>
</html>
