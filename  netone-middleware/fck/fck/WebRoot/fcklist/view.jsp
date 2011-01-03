<%@ page contentType="text/html; charset=GBK"%>
<%@page import="oe.product.fck.common.REGUtil"%>
<%@page import="oe.security3a.seucore.obj.db.UmsProtectedobject"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="FCK" %> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	UmsProtectedobject upo = (UmsProtectedobject)request.getAttribute("upo");
			String split = "<div style=\"page-break-after: always\"><span style=\"display: none\">&nbsp;</span></div>";
			
		String[] p = upo.getExtendattribute().split(split);

		List list = new ArrayList();
		for (int i = 0; i < p.length; i++) {
			list.add(p[i]);
		}
		request.setAttribute("list", list);
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>显示</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
	</head>
	<body style="font-size: 12px;margin: 22px">

		<div style="width: 100%;height: 100%">
			
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="id" value="${upo.id}" />
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr>
						<td width="15%">
							有&nbsp;&nbsp;&nbsp;&nbsp;效
						</td>
						<td>

							<input type="checkbox" name="active" value="1"
								<c:if test="${upo.active=='1'}">checked</c:if> />
						</td>
					</tr>
					<tr>
						<td width="15%">
							名&nbsp;&nbsp;&nbsp;&nbsp;称
						</td>
						<td>
							<input type="text" name="naturalname" value="${upo.naturalname}"
								class="textinput_td" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							中文名称
						</td>
						<td>
							<input type="text" name="name" value="${upo.name}"
								class="textinput_td" readonly="readonly"/>
						</td>
					</tr>
					<tr style="display: none">
						<td width="15%">
							分类
						</td>
						<td width='300'>
							<input type="text" name="objecttype" value="${upo.objecttype}"
								class="textinput_td" readonly="readonly">
						</td>
					</tr>
					<tr>
						<td width="15%">
							摘要
						</td>
						<td>
							<c:out value="${zhaiyao}"></c:out>
						</td>
					</tr>
					<tr>
						<td width="15%">
							内容
						</td>
						<td>
						<c:forEach items="${list}" var="upopage" varStatus="status">
						${upopage }<br><hr>${status.index }
						</c:forEach>
						</td>
					</tr>
					
				</table>
				<br>
				

			
		</div>
	</body>
</html>
