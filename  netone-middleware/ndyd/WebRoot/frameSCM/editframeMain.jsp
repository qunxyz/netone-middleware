<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String cssURL = request.getContextPath()
			+ "/script/theme/main/blue/images";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp" />
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-easyui.jsp"></jsp:include>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
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
					$('#nextBtn_3').attr("disabled",true);
					$('#cannelBtn_3').attr("disabled",true);
					$('#nextBtn_3_2').attr("disabled",true);
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
					$('#nextBtn_3').attr("disabled",false);
					$('#cannelBtn_3').attr("disabled",false);
					$('#nextBtn_3_2').attr("disabled",false);
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
			
			$(function() {
				$enableall();
				$disabledall();
				$hideall();
				<c:if test="${empty param.workcode}">
				$show('new_1');
				</c:if>
				<c:if test="${!empty param.workcode}">
				$show('audit_1');
				</c:if>
				$("#flowMainFrame").load(function(){
					$enableall();
					Ext.MessageBox.hide();
	    		});
			});
		</script>

		<title>集团终端</title>
	</head>
	<style>
	body {
		text-align: center;
	}
	#top {
		width:1000px;
		height:52px;
		background-image:url(<%=cssURL%>/top_nd.gif);
		background-repeat:no-repeat;
		margin:0 auto;
	}
	</style>
	<input type="hidden" id="runtimeid" name="runtimeid" value="${param.runtimeid}"/>
	<input type="hidden" id="workcode" name="workcode" value="${param.workcode}"/>
	<body class="easyui-layout">
		<div region="north"
			style="height: 87px; overflow: hidden; width: 1000px;">
			<div id="app-header">
				<div id="top" sytle="height:52px;width:1000px;background-image: url('<%=cssURL%>/top_nd.gif');">
					<div id="topFontInfo">${htmltitleinfo}</div>
				</div>
				<div id="top_nd_bg_1" style="height:35px;background-image: url('<%=cssURL%>/top_nd_bg.gif');">
					<div id="top_nd_bg" style="background-image: url('<%=cssURL%>/top_nd_bg.gif');text-align: left;width:1000px">
						<c:choose>
							<c:when test="${param.query=='look'}">
							<div id="look_1" style="">
								<div id="btn_nd">
									<input id="cannelBtn_look" type="button" value=" 关 闭 " class="btn"
										onclick="javascript:window.close();" />
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div id="new_1" style="display: none;">
								<div id="btn_nd">
									<input id="deleteBtn_1" type="button" value=" 作 废 " class="btn"
										onclick="javascript:document.getElementById('flowMainFrame').contentWindow._delete();" />
									<input id="saveBtn_1" type="button" value=" 保 存 " class="btn"
										onclick="javascript:document.getElementById('flowMainFrame').contentWindow._save();" />
									<input id="nextBtn_1" type="button" value=" 下一步 " class="btn"
										onclick="javascript:document.getElementById('flowMainFrame').contentWindow._auditNext();" />
									<input id="cannelBtn_1" type="button" value=" 取 消 " class="btn"
										onclick="javascript:window.close();" />
								</div>
							</div>
						</c:otherwise>
						</c:choose>
						<div id="new_2" style="display: none;">
							<div id="btn_nd">
								<input id="upBtn_2" type="button" value=" 上一步 " class="btn"
									onclick="javascript:document.getElementById('flowMainFrame').contentWindow.up_Next_1();" />
								<input id="nextBtn_2" type="button" value=" 提 交 " class="btn"
									onclick="javascript:document.getElementById('flowMainFrame').contentWindow.end();" />
								<!--
					onclick="javascript:document.getElementById('flowMainFrame').contentWindow.newEnd(document.getElementById('flowMainFrame').contentWindow.document.getElementById('clientMainFrame').contentWindow.getResult());"
					-->

								<input id="cannelBtn_2" type="button" value=" 取 消 " class="btn"
									onclick="javascript:window.close();" />
							</div>
						</div>
						<div id="audit_1" style="display: none;">
							<div id="btn_nd">
								<input id="nextBtn_3" type="button" value=" 下一步 " class="btn"
									onclick="javascript:document.getElementById('flowMainFrame').contentWindow.onAuditNext(0);" />
								<c:if test="${param.operatemode!='03'}">	
								<input id="nextBtn_3_2" type="button" value=" 退办/特送 " class="btn"
									onclick="javascript:document.getElementById('flowMainFrame').contentWindow.onAuditNext(1);" />
								</c:if>
								<input id="cannelBtn_3" type="button" value=" 取 消 " class="btn"
									onclick="javascript:window.close();" />
							</div>
						</div>
						<div id="audit_2" style="display: none;">
							<div id="btn_nd">
								<input id="upBtn_4" type="button" value=" 上一步 " class="btn"
									onclick="javascript:document.getElementById('flowMainFrame').contentWindow.up_4();" />
									
								<c:if test="${param.operatemode!='03'}">
								<input id="nextBtn_4" type="button" value=" 下一步 " class="btn"
									onclick="javascript:document.getElementById('flowMainFrame').contentWindow.next_4();" />
								</c:if>
								<c:if test="${param.operatemode=='03'}"><!-- 抄阅 -->
								<input id="nextBtn_4" type="button" value=" 提 交 " class="btn"
									onclick="javascript:document.getElementById('flowMainFrame').contentWindow.next_4();" />
								</c:if>
								<c:if test="${creater=='true'}">
								<input id="nextBtn_4_end" type="button" value=" 归 档 " class="btn"
									onclick="javascript:document.getElementById('flowMainFrame').contentWindow.next_4_end();" />
								</c:if>
								<input id="cannelBtn_4" type="button" value=" 取 消 " class="btn"
									onclick="javascript:window.close();" />
							</div>
						</div>
						<div id="audit_3_3" style="display: none;">
							<div id="btn_nd">
								<input id="upBtn_5_3" type="button" value=" 上一步 " class="btn"
									onclick="javascript:document.getElementById('flowMainFrame').contentWindow.up_5();" />
								<input id="nextBtn_5_3" type="button" value=" 提 交 " class="btn"
									onclick="javascript:document.getElementById('flowMainFrame').contentWindow.end();" />
								<input id="cannelBtn_5_3" type="button" value=" 取 消 "
									class="btn" onclick="javascript:window.close();" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="index" region="center">
		
		<c:choose>
			<c:when test="${param.query=='look'}">
				<iframe id="flowMainFrame" name="flowMainFrame" 
					src="<c:url value='/frame.do?method=onQuery&naturalname=${param.naturalname}&lsh=${param.lsh}&runtimeid=${param.runtimeid}&operatemode=${param.operatemode}&readonly=${param.readonly}' />"
					scrolling="auto" frameborder="0" 
					style="width: 1000px; height: 100%; margin: 0 auto;">
				</iframe>
			</c:when>
			<c:otherwise>
				<iframe id="flowMainFrame" name="flowMainFrame" 
					src="<c:url value='/frame.do?method=onEditView&naturalname=${param.naturalname}&lsh=${param.lsh}&workcode=${param.workcode}&runtimeid=${param.runtimeid}&operatemode=${param.operatemode}' />"
					scrolling="auto" frameborder="0" 
					style="width: 1000px; height: 100%; margin: 0 auto;">
				</iframe>
			</c:otherwise>
		</c:choose>
		</div>
		<div id="bottom" region="south" style="height: 40px; padding: 2px;background-image: url('<%=cssURL%>/bottom_nd.gif');">
			<span>
				<center>
					版权所有 @ 宁德移动
				</center> </span>
		</div>
	</body>
</html>