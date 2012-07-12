<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%@ include file="../common/metaJQueryTable.jsp"%>
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
		<base href="<%=basePath%>">
		<title>workList</title>
		<link href="<c:url value='/portal/oe.css' />" rel="stylesheet" type="text/css">
		
	</head>
	<body> 
	<br />
		&nbsp;&nbsp;
		<font class='fonthead'><strong>公告： </strong></font>
		<hr style="color: #DFC68E">
		<br />
		&nbsp;&nbsp;
		<font class='fonthead'><strong>我的产销计划：</strong></font><a href="#"><font size='2' color='red'>${n}个待办事宜</font></a><br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=1"/>' target='_blank'> <font class='fonttext'>[正常计划]</font></a> &nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=2"/>' target='_blank'> <font class='fonttext' >[追加计划]</font></a> &nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=3"/>' target='_blank'> <font class='fonttext' >[取消计划]</font></a> &nbsp;&nbsp;
			<br /><br />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<%=basePath%>sell/plan.do?method=onPlanCollectMainView' target='_blank'> <font class='fonttext' >[产销计划分析]</font></a> &nbsp;&nbsp;
<br/><br/><br/>
		&nbsp;&nbsp;&nbsp;&nbsp;<font class='fonthead'><strong>我的订单：</strong></font><a href="#"><font size='2' color='red'>${n}个待办事宜</font></a> &nbsp;&nbsp;&nbsp;<a href="#"><font size='2' color='red'><font size='2' color='red'>${n}条收货提示</font></a>&nbsp;&nbsp;&nbsp;<a href="#"> <font size='2' color='red'>${n}条发票确认</font></a><br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=1"/>' target='_blank'><font class='fonttext' >[正常订单]</font></a> &nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=2"/>' target='_blank'><font class='fonttext'>[追加订单]</font></a> &nbsp;&nbsp;
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=3"/>' target='_blank'><font class='fonttext'>[取消订单]</font></a> &nbsp;&nbsp;
		    <br/><br />
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<%=basePath%>sell/indent.do?method=onShoearRangFrankMainView' target='_blank'><font class='fonttext'>[履约率分析]</font></a> &nbsp;&nbsp;
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<c:url value='/client/sellTargetNew.do?method=onTargetAnalysisMainView' />' target='_blank'><font class='fonttext'>[销售指标分析]</font></a> &nbsp;&nbsp;
		   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='<%=basePath%>finance/report2.do?method=onQueryBackPaymentDetailView' target='_blank'><font class='fonttext'>[货款明细分析]</font></a> &nbsp;&nbsp;
<br/><br/><br/>
		 &nbsp;&nbsp;&nbsp;&nbsp;<font class='fonthead'><strong>我的库存与销售：</strong></font><br/><br/>
		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<%=basePath%>storage/outStorageManage.do?method=onMainView' target='_blank'><font class='fonttext'>[分销商配送出货]</font></a> &nbsp;&nbsp;
		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<%=basePath%>finance/report3.do?method=onClientOutStorageQueryMain' target='_blank'><font class='fonttext'>[分销商销售查询]</font></a> &nbsp;&nbsp;
		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<%=basePath%>storage/inStorage.do?method=onMainView' target='_blank'><font class='fonttext'>[入库查询]</font></a> &nbsp;&nbsp;
		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<%=basePath%>storage/outStorage.do?method=onQueryOutStorage' target='_blank'><font class='fonttext'>[出库查询]</font></a> &nbsp;&nbsp;
		    <br /><br />
		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<%=basePath%>/servlet/TreeMarkRelation' target='_blank'><font class='fonttext'>[追朔查询]</font></a> &nbsp;&nbsp;
		     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='<%=basePath%>storage/queryStorage.do?method=onQueryView' target='_blank'><font class='fonttext'>[库存明细]</font></a> &nbsp;&nbsp;
		    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href='<%=basePath%>storage/queryStorage.do?method=onQueryIOStorage' target='_blank'><font class='fonttext'>[出入库汇总]</font></a> &nbsp;&nbsp;
			<br><br>

	</body>
</html>
