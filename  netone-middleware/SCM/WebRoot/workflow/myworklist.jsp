<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>代办任务</title>
		<style>
* {
	font-size: 12px;
	padding: 0;
	margin: 0;
}

a:link,a:visited {
	color: #444;
	text-decoration: none;
}

#myulx {
	margin: 0;
	width: 430px;
	border-bottom: 1px solid #ddd;
	line-height: 23px;
}

#myulx li {
	float: left;
}

ul {
	list-style: none;
	margin: 0;
	padding: 0;
}
</style>

		<script type="text/javascript">
function newope(){
	window.open('<%=basePath%>frame.do?method=onEditViewMain&naturalname=${appname}','_blank');
}

</script>
	</head>
	<body>

		<c:if test="${simple!='y'}">
			<a href="javascript:newope();"
				style="float: right; margin-right: 8px;">[新增]</a>
		</c:if>
		<ul style="margin: 10px;">

			<c:forEach var="workList" items="${workLists}">

				<li>
						<c:if test="${tip!=null}">
							<ul id="myulx">
							${tip}
							</ul>
						</c:if>
					<ul id="myulx">
						<li style="width: 200px;" nowrap>
							<a
								href="<%=basePath%>${workList.bussurl }${workList.bussid }&workcode=${workList.workcode}&operatemode=${workList.operatemode}<c:if test="${mode=='1' || mode=='2'}">&query=look</c:if>"
								target="_blank" class="none">${workList.extinfo}</a>

						</li>
						<li style="width: 140px;" nowrap>
							<c:out value="${fn:substringBefore(workList.participant,'[')}" />
						</li>
						<li style="width: 60px;" >
							<c:out value="${fn:substring(workList.starttime,0,10)}" />
						</li>
					</ul>
				</li>

			</c:forEach>

		</ul>  
		<c:if test="${simple!='y'}">
			<a style="margin-left: 11px;"
				href="<%=basePath%>WorkListSvl?appname=${appname}&ope=y&limit=0&status=01"
				target="_blank">[更多]</a>&nbsp;&nbsp;&nbsp;
		<a
				href="<%=basePath%>WorkListSvl?appname=${appname}&ope=y&limit=0&status=02"
				target="_blank">[已处理]</a>
		</c:if>
	</body>
</html>
