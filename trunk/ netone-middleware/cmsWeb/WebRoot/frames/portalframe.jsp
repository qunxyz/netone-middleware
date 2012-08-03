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
		<script src="<%=basePath%>js/jquery/jquery-1.5.1.min.js"></script>
		<style>
		* {
			font-size: 12px;
			padding: 0;
			margin: 0 auto;
			font-family: "arial", "微软雅黑", "宋体";
		}
		
		img {
			border: 0;
		}
		
		ol,ul {
			list-style: none;
			margin: 0;
			padding: 0;
		}
		
		table {
			border-collapse: collapse;
			border-spacing: 0;
		}
		
		body {
			background: #FFF;
		}
		</style>
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
				document.getElementById('first').style.display="none";
			}else if(pClicksubMenu != document.getElementById(ss)){
				pClicksubMenu.style.display = "none";
				pClicksubMenu = document.getElementById(ss);
			}
		}
		function link(values){

			parent.document.frames["proletright"].location="frames.do?task="+values ;
			
			
		}
		
	   function link(values,type){
			if(type=="1"){
				window.open("<%=basePath%>frames.do?task="+values);
			}else{
			
			parent.document.frames["proletright"].location="frames.do?task="+values ;
			
			}
			
			
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
	<style type="text/css">
	a:link,a:visited {
		color: #444;
		text-decoration: none;
	}
	#menutitle
	{
		width:222px;
		height:32px;
		background:#eee url('<%=basePath%>frames/images/menutitle.gif') no-repeat left top;
	}
	#menufooter
	{
		width:222px;
		height:32px;
		background:#eee url('<%=basePath%>frames/images/menufooter.gif') no-repeat left top;
	}
	#mymenux
	{
		border:1px solid #686B70;
		width:220px;
	}
	.has_c
	{
		background:#888 url('<%=basePath%>frames/images/myspanx1.gif') no-repeat left top;
		color:#FFFFFF;
		cursor:pointer;
		width:220px;
	}
	.hlx
	{
		color:#fff;
		background:#347BB9 url('<%=basePath%>frames/images/myspanx2.gif') no-repeat left top;
	}
	a.myax
	{
		text-indent:40px;
		-text-indent:0;
		-padding-left:40px;
		display:none;
		float:left;
		clear:both;
		background:#ddd url('<%=basePath%>frames/images/myax.gif') no-repeat left top;
		width:220px;
		height:30px;
		line-height:30px;
	}
	a.myax:link,a.myax:visited {
		color: #0C408B;
		text-decoration: none;
		border-bottom:1px solid #CED0CF;
	}
	.myspanx
	{
		text-indent:23px;
		-text-indent:23px;
		height:32px;
		line-height:32px;
		border-bottom:1px solid #686B70;
		display:block;
		width:220px;
	}
	</style>
	
	
	
	
	
	<script type="text/javascript">
	
	
	$(
	function()
	{
		// 默认第一个打开
		$(".has_c:first").addClass("hlx").children("a").show();
		$(".has_c:first").children("a:last").css('border-bottom','1px solid #686B70');
		// 最后一个的下边框去掉
		$(".myspanx:last").css('border-bottom','');
		
		$(".has_c").click( 
				function()
				{
					//$(this).next().children("span").css('border-top','1px solid #686B70');
					//alert($(this).children("a:last").html());
					$(this).children("a:last").css('border-bottom','1px solid #686B70');
					
					$(this).
					addClass("hlx")
					.children("a")
					.show(500)
					.end()
					.siblings()
					.removeClass("hlx") // 其他span高亮去掉
					.children("a")
					.hide();
					
					$(".myspanx:last").css('border-bottom','');
				} 
		 );
		$(".has_c:last").click( 
				function()
				{
					$(this).
					addClass("hlx")
					.children("a")
					.show()
					.end()
					.siblings()
					.removeClass("hlx") // 其他span高亮去掉
					.children("a")
					.hide();
					$(".myspanx:last").css('border-bottom','1px solid #686B70');
					$(this).children("a:last").css('border-bottom','');
				} 
		 );
		
		$(".myax").mouseover( 
				function()
				{
					$(this).css('font-weight','bold');
				}
		).mouseout( 
				function()
				{
					$(this).css('font-weight','normal');
				}
		);
	}
	);
	
	</script>

	<body>
	
	<div  style="margin-top: 22px;margin-left:5px">
	<div id="menutitle"></div>
	<div id="mymenux">
		<c:forEach items="${childrenlist}" var="getCol" varStatus="status">
		<div class="has_c">
			<span class="myspanx">${getCol.name}</span>
			<c:forEach items="${map}" var="map">
				<c:if test="${map.key == getCol.naturalname}">
					<c:forEach items="${map.value}" var="list">
					
					<a class="myax" href='javascript:link("${list.naturalname}" <c:if test="${list.name=='新建'}">,"1"</c:if>)'>${list.name}</a>
					</c:forEach>
				</c:if>
			</c:forEach>
		</div>
		</c:forEach>
	</div>
	<div id="menufooter"></div>	
	</div>
		
		
		<form name="form1" action="" method="post">
			<table style="display:none" width="100%" border="0" cellspacing="0" cellpadding="0" width=220>
				<c:forEach items="${childrenlist}" var="getCol" varStatus="status">
					<tr width=220>
						<c:choose>
							<c:when test="${status.index==0}">
								<td onmouseup="turnit('first')"
							style="background-image: url(image/menu.gif);background-repeat: no-repeat;CURSOR: hand; width:220px; height: 33px;" onclick=";bgChange(this)"
							onmouseover="if(this.style.backgroundImage != 'url(image/c_menu.gif)')this.style.backgroundImage = 'url(image/m_menu.gif)'" onmouseout="if(this.style.backgroundImage != 'url(image/c_menu.gif)')this.style.backgroundImage = 'url(image/menu.gif)'">
							</c:when>
							<c:otherwise>
								<td onmouseup="turnit('${getCol.naturalname}')"
							style="background-image: url(image/menu.gif);background-repeat: no-repeat;CURSOR: hand; width:220px; height: 33px;" onclick=";bgChange(this)"
							onmouseover="if(this.style.backgroundImage != 'url(image/c_menu.gif)')this.style.backgroundImage = 'url(image/m_menu.gif)'" onmouseout="if(this.style.backgroundImage != 'url(image/c_menu.gif)')this.style.backgroundImage = 'url(image/menu.gif)'">
							</c:otherwise>
						</c:choose>
						&nbsp;
							<IMG  src="image/allen004.gif" border=0>
							<strong>${getCol.name}</strong>
						</td>
					</tr>
					<tr>
						<c:choose>
							<c:when test="${status.index==0}">
								<td id="first">
							</c:when>
							<c:otherwise>
								<td id=${getCol.naturalname } style="DISPLAY:none ">
							</c:otherwise>
						</c:choose>

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
