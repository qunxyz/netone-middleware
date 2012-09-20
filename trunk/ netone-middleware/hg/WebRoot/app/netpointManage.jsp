<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String path = request.getContextPath();

	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script src="<%=path%>/script/jquery-1.3.2.min.js" type="text/javascript"></script>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-easyui.jsp"></jsp:include>
		<script type="text/javascript" src="<%=path%>/script/AppUtil.js"></script>
		<!--[if lte IE 6]>
		<script type="text/javascript" src="<%=path%>/script/belatedPNG.js"></script>
		<![endif]-->
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery.blockUI.js"></script>
		<title></title>
		<style>
		.close {  display:block; width:16px; height:16px; background:url(images/appImage/btn/cancel.png) no-repeat;outline:none; }
		</style>
		<!--[if lte IE 6]>
		<script type="text/javascript">
		if(!window.XMLHttpRequest) {
			// 解决IE6透明PNG图片BUG
			DD_belatedPNG.fix(".png");
			// 解决IE6不缓存背景图片问题
			document.execCommand("BackgroundImageCache", false, true);
		}		
		</script>
		<![endif]-->
	</head>
	<body class="easyui-layout">
		<div region="west" style="width: 200px; padding1: 1px; overflow: hidden;" iconCls="icon-company_menu"><!-- 第一层 -->
			<!-- 第二层 -->
			${accordhtml}
			<!-- 第二层 -->  
		</div><!-- 第一层 -->
		<div region="center" title="&nbsp;" iconCls="icon-destop">
			<iframe id="contentFrame" src="" style="width: 100%; height: 98%;"
				frameborder="0" ></iframe>
		</div>
	</body>
</html>
<script type="text/javascript">
	function linkUrl(title,url){
		if (url=='' || url=='undefined' || url==null){
			//not do
		} else {
			if (isie6()){
				var load = function(){document.getElementById("contentFrame").src=url;}
				setTimeout(load,10);
			} else {
				document.getElementById("contentFrame").src=url;
			}
			$('.easyui-layout').layout('panel', 'center').panel({ title: title});
		}
	}
	
	function isie6() {//ie6
	    if ($.browser.msie) {
	        if ($.browser.version == "6.0") return true;
	    }
	    return false;
	}


if($.browser.msie){//IE
    var height = 0;
    var width = 0;
	if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    height = document.documentElement.clientHeight;
	    width = document.documentElement.clientWidth;
	} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    height = document.body.clientHeight;
	    width = document.body.clientWidth;
    }
	var leftW = width/2-50;   
	var top = height/2;
	
	var _html = "<div id='___loading' style='position:absolute;left:0;width:100%;height:"+height+"px;top:0;background:#E0ECFF;opacity:0.8;filter:alpha(opacity=80);'><div style='position:absolute;  cursor1:wait;left:"+leftW+"px;top:"+top+"px;width:auto;height:16px;padding:12px 5px 10px 30px;border:2px solid #ccc;color:#000;'>正在加载，请稍候...</div></div>";   
	    
	window.onload = function(){
	 	var _mask = document.getElementById('___loading');   
   		_mask.parentNode.removeChild(_mask);   
	}
	document.write(_html);  
}
  	$(function(){
  	   
	  	 var mainTheme=getCookie('mainTheme');
	  	    var appTheme=getCookie('appTheme');
	  	    if(mainTheme==null || mainTheme==''){
		    // mainTheme='black';
		    mainTheme='blue';
	     }
	     if(appTheme==null || appTheme==''){
		      //appTheme='red';
		    appTheme='blue';
	      } 
		AppStyleUtils.swapStyleSheet('mainTheme', '<%=path%>/script/theme/jtheme/'+ mainTheme+'/style.css');
		AppStyleUtils.swapStyleSheet('appTheme', '<%=path%>/script/theme/main/'+appTheme+'/style.css');
  	   
   	   if(!$.browser.msie){ //not IE
   	    // 在FF/Linux下启用遮罩层透明
   	    $.blockUI.defaults.applyPlatformOpacityRules = false;
  	    $.blockUI({message: '<div style=\"padding:12px 5px 10px 30px;height:16px\">正在加载，请稍候...</div>',css:{border:'2px solid #ccc'},overlayCSS:{backgroundColor: '#E0ECFF',opacity:.8}});		   	
   	   }
  		
	if(!$.browser.msie){ //not IE
		$.unblockUI();
	}

})

</script>