<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp" />
	<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery.highlight-3.js"></script>
	<script type="text/javascript" src="<%=path%>/tiny_mce/jquery.tinymce.js"></script>	
	<style>
	#top {
		width:1000px;
		height:52px;
		background-image:url(<%=cssURL%>/top_nd.gif);
		background-repeat:no-repeat;
		margin:0 auto;
	}
	</style>
  </head>
  
  <body onload="$load();">
	<input type="hidden" id="runtimeid" name="runtimeid" value="${param.runtimeid}" />
	<input type="hidden" id="workcode" name="workcode" value="${param.workcode}" />
	<div id="app-header" style="z-index:9999;" align="left">
		<center>
		<div id="top_nd_bg_1" style="height:35px;background-image: url('<%=cssURL%>/top_nd_bg.gif');z-index:9999;">
			<div id="top_nd_bg" style="background-image: url('<%=cssURL%>/top_nd_bg.gif');text-align: left;width:1000px;z-index:9999;">
				<c:choose>
					<c:when test="${param.query=='look'}">
					<div id="look_1" style="">
						<div id="btn_nd">
							<input id="cannelBtn_look" type="button" value=" 关 闭 " class="btn"
								onclick="javascript:window.close();" />
							<c:if test="${param.cuibang=='true'}">	
							<input id="cannelBtn_cuibang" type="button" value=" 催 办 " class="btn"
								onclick="cuiban();" />
							</c:if>		
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div id="new_1" style="display: none;">
						<div id="btn_nd">
							<input id="deleteBtn_1" type="button" value=" 作 废 " class="btn"
								onclick="javascript:_delete();" />
							<input id="saveBtn_1" type="button" value=" 保 存 " class="btn"
								onclick="javascript:_save();" />
							<input id="nextBtn_1" type="button" value=" 下一步 " class="btn"
								onclick="javascript:_auditNext_1();" />
							<input id="cannelBtn_1" type="button" value=" 取 消 " class="btn"
								onclick="javascript:window.close();" />
						</div>
					</div>
				</c:otherwise>
				</c:choose>
				<div id="new_2" style="display: none;">
					<div id="btn_nd">
						<input id="upBtn_2" type="button" value=" 上一步 " class="btn"
							onclick="javascript:up_Next_1();" />
						<input id="nextBtn_2" type="button" value=" 提 交 " class="btn"
							onclick="javascript:end();" />

						<input id="cannelBtn_2" type="button" value=" 取 消 " class="btn"
							onclick="javascript:window.close();" />
					</div>
				</div>
				<div id="audit_1" style="display: none;">
					<div id="btn_nd">
						<c:if test="${isFirstAct==true}">
						
						<input id="deleteBtn_1" type="button" value=" 作 废 " class="btn"
								onclick="javascript:_delete();" />
						
						<input id="deleteBtn_2" type="button" value=" 归 档 " class="btn"
								onclick="javascript:_delete(0);" />
						</c:if>
						<c:if test="${isEditAct==true}">
						<input id="saveBtn_2" type="button" value=" 保 存 " class="btn"
									onclick="javascript:_save();" />		
						</c:if>
						<input id="nextBtn_3" type="button" value=" 下一步 " class="btn"
							onclick="javascript:onAuditNext(0);" />
							
						<c:choose>
							<c:when test="${param.operatemode=='03' || param.operatemode=='04'}">
							</c:when>
							<c:otherwise>
							<input id="nextBtn_3_2" type="button" value=" 退办/特送 " class="btn"
							onclick="javascript:onAuditNext(1);" />
								<c:if test="${isFobitzb==true}">
								<input id="nextBtn_3_3" type="button" value=" 转 办 " class="btn"
									onclick="javascript:onAuditNext(3);" />
								</c:if>
							</c:otherwise>
						</c:choose>	
						
						<input id="printBtn_3" type="button" value=" 打 印 " class="btn"
							onclick="javascript:$$print();" />
						
						<input id="cannelBtn_3" type="button" value=" 取 消 " class="btn"
							onclick="javascript:window.close();" />
					</div>
				</div>
				<div id="audit_2" style="display: none;">
					<div id="btn_nd">
						<input id="upBtn_4" type="button" value=" 上一步 " class="btn"
							onclick="javascript:up_4();" />
						
						
						<c:choose>
							<c:when test="${param.operatemode=='03' || param.operatemode=='04'}">
							<input id="nextBtn_4" type="button" value=" 提 交 " class="btn"
							onclick="javascript:next_4();" />
							</c:when>
							<c:otherwise>
							<input id="nextBtn_4" type="button" value=" 下一步 " class="btn"
							onclick="javascript:next_4();" />
							</c:otherwise>
						</c:choose>	
						<!-- 
						<c:if test="${creater=='true'}">
						<input id="nextBtn_4_end" type="button" value=" 归 档 " class="btn"
							onclick="javascript:next_4_end();" />
						</c:if>
						 -->
						<input id="cannelBtn_4" type="button" value=" 取 消 " class="btn"
							onclick="javascript:window.close();" />
					</div>
				</div>
				<div id="audit_3_3" style="display: none;">
					<div id="btn_nd">
						<input id="upBtn_5_3" type="button" value=" 上一步 " class="btn"
							onclick="javascript:up_5();" />
						<input id="nextBtn_5_3" type="button" value=" 提 交 " class="btn"
							onclick="javascript:end();" />
						<input id="cannelBtn_5_3" type="button" value=" 取 消 "
							class="btn" onclick="javascript:window.close();" />
					</div>
				</div>
			</div>
		</div>
		</center>
	</div>
	<br>
	<br>
  </body>
</html>
<script type="text/javascript"> 
	function $hideall() {
		if ($('#new_1')) $('#new_1').hide();
		if ($('#new_2')) $('#new_2').hide();
		if ($('#audit_1')) $('#audit_1').hide();
		if ($('#audit_2')) $('#audit_2').hide();
		if ($('#audit_3_3')) $('#audit_3_3').hide();
	}
	
	function $disabledall(){
		if ($('#new_1')) {
			//$('#new_1').attr("disabled",true);
			$('#deleteBtn_1').attr("disabled",true);
			$('#saveBtn_1').attr("disabled",true);
			$('#nextBtn_1').attr("disabled",true);
			$('#cannelBtn_1').attr("disabled",true);
		}
		if ($('#new_2')) {
			//$('#new_2').attr("disabled",true);
			$('#upBtn_2').attr("disabled",true);
			$('#nextBtn_2').attr("disabled",true);
			$('#cannelBtn_2').attr("disabled",true);
		}
		if ($('#audit_1')) {
			//$('#audit_1').attr("disabled",true);
			if($('#deleteBtn_1')) $('#deleteBtn_1').attr("disabled",true);
			if($('#deleteBtn_2')) $('#deleteBtn_2').attr("disabled",true);
			if($('#saveBtn_2')) $('#saveBtn_2').attr("disabled",true);
			$('#nextBtn_3').attr("disabled",true);
			if($('#nextBtn_3_2')) $('#nextBtn_3_2').attr("disabled",true);
			if($('#nextBtn_3_3')) $('#nextBtn_3_3').attr("disabled",true);
			$('#cannelBtn_3').attr("disabled",true);
		}
		if ($('#audit_2')) {
			//$('#audit_2').attr("disabled",true);
			$('#upBtn_4').attr("disabled",true);
			$('#nextBtn_4').attr("disabled",true);
			$('#nextBtn_4_end').attr("disabled",true);
			$('#cannelBtn_4').attr("disabled",true);
		}
		if ($('#audit_3_3')) {
			//$('#audit_3_3').attr("disabled",true);
			$('#upBtn_5_3').attr("disabled",true);
			$('#nextBtn_5_3').attr("disabled",true);
			$('#cannelBtn_5_3').attr("disabled",true);
		}
	}
	
	function $enableall(){
		if ($('#new_1')) {
			//$('#new_1').attr("disabled",false);
			$('#deleteBtn_1').attr("disabled",false);
			$('#saveBtn_1').attr("disabled",false);
			$('#nextBtn_1').attr("disabled",false);
			$('#cannelBtn_1').attr("disabled",false);
		}
		if ($('#new_2')) {
			//$('#new_2').attr("disabled",false);
			$('#upBtn_2').attr("disabled",false);
			$('#nextBtn_2').attr("disabled",false);
			$('#cannelBtn_2').attr("disabled",false);
		}
		if ($('#audit_1')) {
			//$('#audit_1').attr("disabled",false);
			if($('#deleteBtn_1')) $('#deleteBtn_1').attr("disabled",false);
			if($('#deleteBtn_2')) $('#deleteBtn_2').attr("disabled",false);
			if($('#saveBtn_2')) $('#saveBtn_2').attr("disabled",false);
			$('#nextBtn_3').attr("disabled",false);
			if($('#nextBtn_3_2')) $('#nextBtn_3_2').attr("disabled",false);
			if($('#nextBtn_3_3')) $('#nextBtn_3_3').attr("disabled",false);
			$('#cannelBtn_3').attr("disabled",false);
		}
		if ($('#audit_2')) {
			//$('#audit_2').attr("disabled",false);
			$('#upBtn_4').attr("disabled",false);
			$('#nextBtn_4').attr("disabled",false);
			$('#nextBtn_4_end').attr("disabled",false);
			$('#cannelBtn_4').attr("disabled",false);
		}
		if ($('#audit_3_3')) {
			//$('#audit_3_3').attr("disabled",false);
			$('#upBtn_5_3').attr("disabled",false);
			$('#nextBtn_5_3').attr("disabled",false);
			$('#cannelBtn_5_3').attr("disabled",false);
		}
	}
	
	function $show(id) {
		if ($('#'+id)) $('#'+id).show();
	}
	
	function cuiban(){
		openWinCenter('催办','<c:url value="/CuibangSvl?ope=new"/>&appname=${param.naturalname}&workcode=${param.workcode}', 480,180,true);
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
		$hideall();
		$disabledall();
		<c:choose>
		  	<c:when test="${empty param.workcode || param.page=='new_1' || param.page=='new_2'}">
			  <c:choose>
			  	<c:when test="${param.page=='new_1'}">
			  		$show('new_1');
			  	</c:when>
			  	<c:when test="${param.page=='new_2'}">
			  		$show('new_2');
			  	</c:when>
			  	<c:otherwise>
			  		<c:if test="${param.query!='look'}">
			  			$show('new_1');
			  		</c:if>
			  	</c:otherwise>
			  </c:choose>
			</c:when>
			<c:otherwise>
			  <c:choose>
			  	<c:when test="${param.page=='audit_1'}">
			  		$show('audit_1');
			  	</c:when>
			  	<c:when test="${param.page=='audit_2'}">
			  		$show('audit_2');
			  	</c:when>
			  	<c:when test="${param.page=='audit_3_3'}">
			  		$show('audit_3_3');
			  	</c:when>
			  	<c:otherwise>
			  		<c:if test="${param.query!='look'}">
			  			$show('audit_1');
			  		</c:if>
			  	</c:otherwise>
			  </c:choose>
			</c:otherwise>
		</c:choose>	
		$enableall();
		$enableall();
		
		$("#app-header").capacityFixed();
		$("#app-header").css({
	       position: "absolute",
	       top: 0,
	       left: 0
	    });
	    
	    <c:if test="${!empty permission && permission=='false'}">
			$disabledall();
			//$hideall();
		</c:if>
	}
	
	
	function $$print(){
		var lsh = '${param.lsh}';
		var url = "<c:url value='/xreporthg.do?method=report1' />"+"&lsh="+lsh;
		if (lsh!='') window.open(url);
	}	
</script>
