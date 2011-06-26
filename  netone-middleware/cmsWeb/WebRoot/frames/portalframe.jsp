<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<link href="frames/css/css.css" rel="stylesheet" type="text/css">
	</head>
	<script type="text/javascript">
		var pClickTd = "";
		var pClicksubMenu = "";
		function turnit(ss){
			if (document.getElementById(ss).style.display=="none"){
				document.getElementById(ss).style.display="";
			} else{
				document.getElementById(ss).style.display="none"; 
			}
			
			if(pClicksubMenu == ""){
				pClicksubMenu = document.getElementById(ss);
			}else if(pClicksubMenu != document.getElementById(ss)){
				pClicksubMenu.style.display = "none";
				pClicksubMenu = document.getElementById(ss);
			}
		}
		function link(values){
			parent.document.frames["proletright"].location="frames.do?task="+values ;
		}
		
		function bgChange(clickedtd){

			if(pClickTd == ""){
				clickedtd.style.backgroundImage = "url(image/c_menu.gif)";
				pClickTd = clickedtd;
			}else{
				pClickTd.style.backgroundImage = "url(image/menu.gif)";
				clickedtd.style.backgroundImage = "url(image/c_menu.gif)";
				pClickTd = clickedtd;
			}
		}
	</script>
	<body style="margin: 22px">
		<form name="form1" action="" method="post">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" width=220>
				<c:forEach items="${childrenlist}" var="getCol">
					<tr width=220>
						<td onmouseup="turnit('${getCol.naturalname}')"
							style="background-image: url(image/menu.gif);background-repeat: no-repeat;CURSOR: hand; width:220px; height: 33px;" onclick=";bgChange(this)"
							onmouseover="if(this.style.backgroundImage != 'url(image/c_menu.gif)')this.style.backgroundImage = 'url(image/m_menu.gif)'" onmouseout="if(this.style.backgroundImage != 'url(image/c_menu.gif)')this.style.backgroundImage = 'url(image/menu.gif)'">&nbsp;
							<IMG  src="image/allen004.gif" border=0>
							<strong>${getCol.name}</strong>
						</td>
					</tr>
					<tr>
						<td id=${getCol.naturalname } style="DISPLAY:none ">
							<table width="100%" border="0" align="center" cellpadding="0">
								<c:forEach items="${map}" var="map">
									<c:if test="${map.key == getCol.naturalname}">
										<c:forEach items="${map.value}" var="list">
											<tr>
											<a href=javascript:link("${list.naturalname}")>
												<td style="cursor: pointer; background-image: url(image/sub_menu.gif); background-repeat: no-repeat; width: 220px; height: 28px;">
													<div style="float: left;margin-top: 3px; padding-left: 18px;"><img src="image/sub_menu_title_img.png"/></div>
													<div>${list.name}</div>
												</td>
											</a>
											</tr>
										</c:forEach>
									</c:if>
								</c:forEach>
							</table>
						</td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</body>
</html>
