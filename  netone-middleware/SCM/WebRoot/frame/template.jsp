<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%
	String path = request.getContextPath();
	String cssURL = request.getContextPath()+ "/script/theme/main/blue/images";	
	//String cssURL = request.getContextPath()+ "/script/theme/main/red/images";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style>
	#top {
		height:52px;
		background-image:url(<%=cssURL%>/top_nd.gif);
		background-repeat:no-repeat;
		margin:0 auto;
	}
	#app-header-div{
		height: 66px;
		width: 100%;
		margin:0 auto;
	}
	
	</style>
  </head>
  
  <body onload="$load();">
	<input type="hidden" id="runtimeid" name="runtimeid" value="${param.runtimeid}" />
	<input type="hidden" id="workcode" name="workcode" value="${param.workcode}" />
	<div id="app-header-div" style="z-index:9999;" align="left">
	<center>
		<div id="top_nd_bg_1" style="height:35px;background-image: url('<%=cssURL%>/top_nd_bg.gif');z-index:9999;">
		<center>
			<div id="top_nd_bg" style="padding-top:5px;background-image: url('<%=cssURL%>/top_nd_bg.gif');text-align: left;width:900px;margin:0 auto; z-index:9999;">
				<rs:permission action="3" resource="${naturalname_dyform}.MODI">
					<input id="Btn_MODI" type="button" class="btn" value=" 保 存 " onclick="_save()" />
				</rs:permission>
				<rs:permission action="3" resource="${naturalname_dyform}.MODI">
					<input id="Btn_ADD" type="button" class="btn" value=" 继续添加 " onclick="_continueAdd()" />
				</rs:permission>
				<rs:permission action="3" resource="${naturalname_dyform}.MODI">
					<input id="Btn_DELE" type="button" class="btn" value=" 删 除 " onclick="_delete()" />
				</rs:permission>
				<input id="Btn_PRINT" type="button" class="btn" value=" 打 印 " onclick="_print()" />
				<input id="Btn_CLOSE" type="button" class="btn" value=" 关 闭 " onclick="javascript:window.close();" />
				
			</div>
			</center>	
		</div>
	</center>	
	</div>
	<br>
	<br>
  </body>
</html>
<script type="text/javascript"> 
	function $show(id) {
		if ($('#'+id)) $('#'+id).show();
	}
	
	/**
	 * @author 愚人码头
	 * 类似于新浪微博新消息提示的定位框 DON修改
	 * 更多
	 */
	(function($){
	    $.fn.capacityFixed = function(options) {
	        var opts = $.extend({},$.fn.capacityFixed.deflunt,options);
	        var FixedFun = function(element) {
	            var top = opts.top;
	            element.css({
	                "top":top
	            });
	            $(window).resize(function(){
	                element.css('left',0)
	            });
	            $(window).scroll(function() {
	                var scrolls = $(this).scrollTop();
	                if (scrolls > top) {
						
	                    if (window.XMLHttpRequest) {
	                        element.css({
	                            position: "fixed",
	                            top: 0							
	                        });
	                    } else {
	                        element.css({
	                            top: scrolls
	                        });
	                    }
	                }else {
	                    element.css({
	                        position: "absolute",
	                        top: top
	                    });
	                }
	            });
	        };
	        return $(this).each(function() {
	            FixedFun($(this));
	        });
	    };
	    
	    $.fn.capacityFixed.deflunt={
	        top:0
		};
	})(jQuery);
	
	function $load(){
		
		$("#app-header-div").capacityFixed();
		$("#app-header-div").css({
	       position: "absolute",
	       top: 0,
	       left: 0
	    });
	    
	    <c:if test="${!empty permission && permission=='false'}">
		</c:if>
	}
	
</script>
