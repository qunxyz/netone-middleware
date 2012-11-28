<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>数据分析应用</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=path%>/script/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/script/jquery.maphilight.js"></script>
	<style type="text/css">
	.ajax-loading{
		z-index:99999;
		background:url("<%=path%>/images/ajax-loader.gif") no-repeat scroll center center transparent;
	}
	
	._mapFlag{
		background: url(<%=path%>/images/point.gif);
		background-repeat: no-repeat;
		width:20px;
		height:20px;
		text-align: center;
		vertical-align: middle;
	}
	._mapFlagOver{
		background: url(<%=path%>/images/_point.gif);
		background-repeat: no-repeat;
		width:16px;
		height:18px;
		text-align: center;
		vertical-align: middle;
		z-index:99999;
	}
	/*超链接的样式*/
	A:link.map{
		FONT-SIZE:12px;
		COLOR:red;
		font-weight:bold;
		text-decoration: none;
		background-color: yellow;
		white-space: nowrap;
	}
	A:visited.map{
		FONT-SIZE:12px;
		COLOR:red;
		font-weight:bold;
		text-decoration: none;
		background-color: yellow;
		white-space: nowrap;
	}
	A:hover.map{
		FONT-SIZE:18px;
		color:red;
		font-weight:bold;
		text-decoration: none;
		background-color: yellow;
		white-space: nowrap;
	}
	span.right_ A:link{
		FONT-SIZE:14px;
		COLOR:blue;
		text-decoration: none;
		white-space: nowrap;
	}
	span.right_ A:visited{
		FONT-SIZE:14px;
		COLOR:blue;
		text-decoration: none;
		white-space: nowrap;
	}
	span.right_ A:hover{
		FONT-SIZE:14px;
		color:blue;
		font-weight:bold;
		text-decoration: none;
		white-space: nowrap;
	}
	
	._mapGD {
	        scrollbar-face-color: #fff;
	        scrollbar-shadow-color: #808080;
	        scrollbar-3dlight-color: #fff;
	        scrollbar-arrow-color: #006C90;
	        scrollbar-darkshadow-color: #ffffff;
	        scrollbar-base-color: #ffffff;
	}
	.map_font_right{
		FONT-SIZE: 14px;
	}
	
	
	
	#maptable { 
	    padding: 0; 
	    margin: 0; 
	} 
	</style>
	<script type="text/javascript">
		$(function() {
			$.fn.maphilight.defaults = {
				fill: true,
				fillColor: '000000',
				fillOpacity: 0.2,
				stroke: true,
				strokeColor: 'ff0000',
				strokeOpacity: 1,
				strokeWidth: 1,
				fade: true,
				alwaysOn: true,
				neverOn: false,
				groupBy: false,
				wrapClass: true,
				// plenty of shadow:
				shadow: false,
				shadowX: 0,
				shadowY: 0,
				shadowRadius: 6,
				shadowColor: '000000',
				shadowOpacity: 0.8,
				shadowPosition: 'outside',
				shadowFrom: false
			};
		});	
		function analyze(){
			var params =  {picurl:$('#picurl').val(),coords:$('#coords').val(),mappoint:$('#mappoint').val()};
			var $dbody = $('#dbody');
			
			$.ajax({
					   type: "POST",
					   url: '<%=path%>/servlet/map',
					   data: params,
					   dataType:"html",
					   success: function(result){
					   	//alert(result);
					   	$dbody.html(result);
					   },
					   beforeSend:function(){
					   	$dbody.addClass("ajax-loading");
					   },
					   complete:function(){
					   	$dbody.removeClass("ajax-loading");
					   	$('#dform').hide();
					   	$('#_mapx').maphilight({alwaysOn:true});
					   }
					}); 
			/**
			Ext.Ajax.request({
					url :'<%=path%>/servlet/map',
					params: params,
					method : 'POST',
					success : function(response,options){
						$dbody.html(response.responseText);
					},
					failure : function(response,options){
					}
		　	 });	
		**/		
		}
		
		 function _focus(obj){
			obj.style.zIndex =99999;
	    }
	    function _focusOut(obj){
			obj.style.zIndex =0;
	    }
	    function _classOver(obj,obj1){
	    	if(obj1=='_mapMark'){
	    		obj.className="_mapMarkOver";
	    	}else if(obj1=='_mapFlag'){
	    		obj.className="_mapFlagOver";
	    	}
	    }
	    function _classOut(obj,obj1){
	    	if(obj1=='_mapMark'){
	    		obj.className="_mapMark";
	    	}else if(obj1=='_mapFlag'){
	    		obj.className="_mapFlag";
	    	}
	    	
	    }
	</script>
  </head>
  
  <body>
  <div id="dform" style="border: 1px solid gray;width: 450px;">
  	<form id="form1">
  		区域:<textarea rows="5" cols="50" id="coords" name="coords"></textarea>
  	　　<BR>注：以换行代表一行数据 格式如下：<BR>
  		281,28,185,161,332,162<BR>
		149,249,61,354,191,362<BR>
		350,237,286,351,413,359
		<hr>
  		节点:<textarea rows="5" cols="50" id="mappoint" name="mappoint"></textarea>
  	　　<BR>注：以换行代表一行数据 格式如下：<BR>
  		162,240,泉州,<BR>354,240,福州,http://127.0.0.1:8080/mapSimpleApp/images/mark.gif
  		<BR>
  		图片地址:<input id="picurl" width="100%" name="picurl" value="http://pic1.sc.chinaz.com/files/pic/pic9/201210/xpic8159.jpg" />
  		<hr>
	    <input type="button" value="分析" onclick="analyze()" />
    </form>  
  </div> 
  
  <div id="dbody" style="">
	
  </div>  
  </body>
</html>
