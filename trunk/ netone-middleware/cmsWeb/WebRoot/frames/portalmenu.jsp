<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

		<title></title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<style>
ul {
	padding: 0;
	margin: 0;
	list-style: none;
}

li {
	float: left;
	width: 100px;
}

ul li a {
	display: block;
	font-size: 12px;
	border: 1px solid #ccc;
	margin-top: 2px;
	margin-left: 3px;
	padding: 3px;
	text-decoration: none;
	color: #777;
}

ul li a:hover {
	background-color: #ddd;
}

li ul {
	display: none;
	top: 20px;
}

li:hover ul,li.over ul {
	display: block;
}
</style>
	</head>
	<script type="text/javascript">
		function link(values){
			document.frames["proletright"].location="frames.do?task="+values ;
		}
		startList = function() {
	        if (document.all&&document.getElementById){
	            navRoot = document.getElementById("nav");
	            for (i=0; i<navRoot.childNodes.length;i++){
	                node =navRoot.childNodes[i];
	                if(node.nodeName=="LI"){
	                  node.onmouseover=function() {
	                        this.className+="over";
	                        }
	                    node.onmouseout=function() {
	                        this.className=this.className.replace("over","");
	                        }
	                    }
	                }
	            }
	        }
	        window.onload=startList;    
	</script>
	<body BGCOLOR=#FFFFFF LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0
		MARGINHEIGHT=0>
		<br>
		<div style="position: absolute; z-index: 9999;">
			<ul id="nav" style="height: 30">
				<c:forEach items="${childrenlist}" var="getCol">
					<li>
						<a href="javascript:undefined;">${getCol.name}</a>
						<ul>
							<c:forEach items="${map}" var="map">
								<c:if test="${map.key == getCol.naturalname}">
									<c:forEach items="${map.value}" var="list">
										<li>
											<a href=javascript:link("${list.naturalname}")>${list.name}</a>
										</li>
									</c:forEach>
								</c:if>
							</c:forEach>
						</ul>
					</li>
				</c:forEach>
			</ul>

		</div>
		<div align='left'>
			
			<iframe id="proletright" src="${fn:replace(initurl,'$@','&')}" scrolling="no" resize="no"
				height="100%" width="100%"></iframe>
		</div>
	</body>
</html>
