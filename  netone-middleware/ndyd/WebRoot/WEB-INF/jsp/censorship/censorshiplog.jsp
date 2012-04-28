<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>:窗口::</title>
		<link rel="stylesheet" type="text/css" href="default.css">
		<style>
		.ui-widget-header {
		    background: repeat-x scroll 50% 50% #D5E1F2;
		    border: 1px solid #D5E1F2;
		    color: #FFFFFF;
		    font-weight: bold;
		}
		</style>	
			
	</head>
	<body >
	<c:if test="${fn:length(packlog)>0}">
	
	<table class="yijianw" width="98%" cellSpacing='0' height="100%"
								cellPadding='0'>
								
								<tr>
									<td>
									<c:if test="${fn:length(packlog1)>0}">
										<div id='yijian'
											style="background-image: url('<%=path%>/FDL/Dubang/Image/index_2323.gif'); width: 100%; text-align: left; color: #a52526; padding-top: 5px; font-weight: bold; height: 25px;">
											督办处理结果
										</div>
										<table class='tableyijian' cellSpacing='0' cellPadding='0'
											width='100%'>
											<tr style="height: 25px;">
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif');">
													<div class='fieldcaption1'>
														所在部门
													</div>
												</td>
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif');">
													<div class='fieldcaption1'>
														填写人
													</div>
												</td>
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif');">
													<div class='fieldcaption1'>
														填写时间
													</div>
												</td>
											</tr>
											<c:forEach items="${packlog1}" var="packlog1">
											<tr>
												<td style="font-weight: bold; font-size: 12px;">
													${packlog1.sdept}
												</td>
												<td style="font-weight: bold; font-size: 12px;">
													${packlog1.tname}
												</td>
												<td style="font-weight: bold; font-size: 12px;">
													<fmt:formatDate value='${packlog1.addtime}' pattern='yyyy-MM-dd HH:mm:ss' />
												</td>
											</tr>
											<tr>
												<td style="font-weight: bold; font-size: 12px;">
													落实反馈内容：
												</td>
												<td colspan="2"
													style="color: #a52526; text-align: left; font-size: 13px;">
													${packlog1.yijian}
												</td>
											</tr>
											</c:forEach>
										</table>
										<br />
										</c:if>
										<c:if test="${fn:length(packlog2)>0}">
										<div id='yijian'
											style="background-image: url('<%=path%>/FDL/Dubang/Image/index_2323.gif');  width: 100%; text-align: left; color: #a52526; padding-top: 5px; font-weight: bold; height: 25px;">
											批办意见
										</div>
										<table class='tableyijian' cellSpacing='0' cellPadding='0'
											width='100%'>
											<tr style="height: 25px;">
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif');">
													<div class='fieldcaption1'>
														所在部门
													</div>
												</td>
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif');">
													<div class='fieldcaption1'>
														填写人
													</div>
												</td>
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif')">
													<div class='fieldcaption1'>
														填写时间
													</div>
												</td>
											</tr>
											<c:forEach items="${packlog2}" var="packlog2">
											<tr>
												<td style="font-weight: bold; font-size: 12px;">
													${packlog2.sdept}
												</td>
												<td style="font-weight: bold; font-size: 12px;">
													${packlog2.sname}
												</td>
												<td style="font-weight: bold; font-size: 12px;">
													<fmt:formatDate value='${packlog2.addtime}' pattern='yyyy-MM-dd HH:mm:ss' />
												</td>
											</tr>
											<tr>
												<td style="font-weight: bold; font-size: 12px;">
													落实反馈内容：
												</td>
												<td colspan="2"
													style="color: #a52526; text-align: left; font-size: 13px;">
													${packlog2.yijian}
												</td>
											</tr>
											</c:forEach>
										</table>
										<br />
										</c:if>
										<c:if test="${fn:length(packlog3)>0}">
										<div id='yijian'
											style="background-image: url('<%=path%>/FDL/Dubang/Image/index_2323.gif'); padding-top: 5px;  marin-top: 5px; width: 100%; text-align: left; font-weight: bold; height: 25px;">
											<font color="#a52526">处理意见</font>
										</div>
										<table class='tableyijian' cellSpacing='0' cellPadding='0'
											width='100%'>
											<tr style="height: 25px;">
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif');">
													<div class='fieldcaption1'>
														所在部门
													</div>
												</td>
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif');">
													<div class='fieldcaption1'>
														填写人
													</div>
												</td>
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif');">
													<div class='fieldcaption1'>
														填写时间
													</div>
												</td>
											</tr>
											<c:forEach items="${packlog3}" var="packlog3">
											<tr>
												<td style="font-weight: bold; font-size: 12px;">
													${packlog3.sdept}
												</td>
												<td style="font-weight: bold; font-size: 12px;">
													${packlog3.sname}
												</td>
												<td style="font-weight: bold; font-size: 12px;">
													<fmt:formatDate value='${packlog3.addtime}' pattern='yyyy-MM-dd HH:mm:ss' />
												</td>
											</tr>
											<tr>
												<td style="font-weight: bold; font-size: 12px;">
													落实反馈内容：
												</td>
												<td colspan="2"
													style="color: #a52526; text-align: left; font-size: 13px;">
													${packlog3.yijian}
												</td>
											</tr>
											</c:forEach>
										</table>
										</c:if>
									</td>
								</tr>
							</table>
							<br>
							<c:if test="${fn:length(packlog)>0}">
							<table class="yijianw" width="98%" cellSpacing='0'
								cellPadding='0'>
								<tr>
									<td>
										<div id="dblog"
											style="padding-top: 3px; background-image: url('<%=path%>/FDL/Dubang/Image/index_2323.gif'); width: 100%; text-align: left; font-weight: bold;color:#a52526; height: 25px;">
											督办日志
										</div>
										<table class='tablelog' cellSpacing='0' cellPadding='0'
											width='100%'>
											<tr style="height: 25px;">
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif')">
													<div class='fieldcaption1'>
														操作人
													</div>
												</td>
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif')">
													<div class='fieldcaption1'>
														动作
													</div>
												</td>
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif')">
													<div class='fieldcaption1'>
														对象
													</div>
												</td>
												<td style="background-image: url('<%=path%>/FDL/Dubang/Image/td_title_bg1.gif')">
													<div class='fieldcaption1'>
														时间
													</div>
												</td>
											</tr>
											<c:forEach items="${packlog}" var="list">
											<tr>
												<td nowrap="nowrap" class="label_2">
													${list.sname}
												</td>
												<td nowrap="nowrap" class="label_2">
													${list.actionname}
												</td>
												<td nowrap="nowrap" class="label_2">
													${list.tname}
												</td>
												<td nowrap="nowrap" class="label_2">
													<fmt:formatDate value='${list.addtime}' pattern='yyyy-MM-dd HH:mm:ss' />
												</td>
											</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</table>
							</c:if>
		</c:if>					
	</body>
</html>
