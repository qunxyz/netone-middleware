<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript" src="/script/window-op.js"></script>
<script src="<%=path%>/script/jquery-plugin/json2.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
<!-- 通过样式 按钮 -->
<link rel="stylesheet" type="text/css" href="/script/theme/main/common.css">

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>工单详细信息</TITLE>
<META content="text/html; charset=utf-8" http-equiv=Content-Type>
<LINK rel=stylesheet type=text/css href="<%=path%>/styles/wf_detail.css">
<STYLE type=text/css>
TABLE {
	FONT-SIZE: 12px
}

.divstyle {
	TEXT-ALIGN: left;
	WIDTH: 100%;
	DISPLAY: block;
	FONT-SIZE: 12px
}

.divstyle A {
	LINE-HEIGHT: 35px;
	WIDTH: 70px;
	MARGIN-LEFT: 10px;
	MARGIN-RIGHT: 10px;
	TEXT-DECORATION: none
}

.divstyle A:hover {
	COLOR: orange;
	FONT-SIZE: 14px
}

.closebtn {
	BORDER-BOTTOM: medium none;
	BORDER-LEFT: medium none;
	WIDTH: 55px;
	BACKGROUND: url(dubang/image/close2.gif) no-repeat 0px 0px;
	HEIGHT: 25px;
	BORDER-TOP: medium none;
	CURSOR: pointer;
	BORDER-RIGHT: medium none
}

.td {
	BORDER-BOTTOM: #ece9d8 1px solid;
	BORDER-LEFT: #ece9d8 1px solid;
	LINE-HEIGHT: 25px;
	BACKGROUND-COLOR: #ffffff;
	BORDER-TOP: #ece9d8 1px solid;
	BORDER-RIGHT: #ece9d8 1px solid
}

.tbbox {
	BORDER-TOP: #000000 1px solid
}

.tdlbox {
	BORDER-BOTTOM: #000000 1px solid;
	BORDER-LEFT: #000000 1px solid;
	LINE-HEIGHT: 25px
}

.tdbox {
	BORDER-BOTTOM: #000000 1px solid;
	BORDER-LEFT: #000000 1px solid;
	LINE-HEIGHT: 25px;
	WIDTH: 30%;
	BORDER-RIGHT: #000000 1px solid
}

.tdrbox {
	BORDER-BOTTOM: #000000 1px solid;
	LINE-HEIGHT: 25px;
	BORDER-RIGHT: #000000 1px solid
}

.tdrrbox {
	BORDER-BOTTOM: #000000 1px solid;
	LINE-HEIGHT: 25px;
	PADDING-LEFT: 10px;
	WIDTH: 60%;
	BORDER-RIGHT: #000000 1px solid
}
</STYLE>

</HEAD>
<BODY ms_positioning="GridLayout">

<DIV class=bgblue><FONT face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><FONT
	style="BACKGROUND-COLOR: #ffffff" face=宋体></FONT><BR>
	<BR>
<TABLE
	style="PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; TABLE-LAYOUT: fixed; PADDING-TOP: 0px"
	border=1 cellSpacing=0 borderColorLight=#58b4f0 borderColorDark=#ffffff
	cellPadding=0 width=885 bgColor=white align=center >
	<TBODY>
		<TR height=40>
			<td>工单主题</td>
			<td>流程名称</td>
			<td>创建时间</td>
			<td>处理人</td>
			<td>处理节点</td>
		</TR>
		<c:forEach var="ltdata" items="${ltdata}">
		<TR height=30>
		<td>
		<c:if test="${flag==1}">
		<a href="<%=path%>/frame.do?method=onEditViewMain&naturalname=<c:out value="${ltdata.appname}"/>&lsh=<c:out value="${ltdata.lsh}"/>&workcode=<c:out value="${ltdata.workcode}"/>&operatemode=01&query=look&cuibang=true&commiter=<c:out value="${ltdata.commiter}"/>" target="_blank">
		<c:out value="${ltdata.formtitle}"/></a>
		</c:if>
		<c:if test="${flag==0}">
		<c:out value="${ltdata.formtitle}"/>
		</c:if>
		</td>
		<td><c:out value="${ltdata.appnameext}"/></td>
		<td><c:out value="${ltdata.starttime}"/></td>
		<td><c:out value="${ltdata.username}"/></td>
		<td><c:out value="${ltdata.actname}"/></td>
		</TR>
		</c:forEach>
	</TBODY>
</TABLE>
<BR>
</DIV>
</BODY>
</HTML>
