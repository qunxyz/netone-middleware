<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="yc.erp.zy.common.CheckPlanTime"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String cssURL = request.getContextPath() + "/theme/styles/default";
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
       ResourceBundle configx = ResourceBundle.getBundle("config",Locale.CHINESE);
		/* 产销计划 隔月 控制 */
		Date date = new Date();
		String planLimitStr = configx.getString("methodLimit");
		Integer planLimit = Integer.parseInt(planLimitStr);
		int currDay = CheckPlanTime.getDay(date);
		if (planLimit < currDay) {
			request.setAttribute("planAllowType", "normal");// 只能提交隔月计划
		}
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
	
	<link rel="stylesheet" href="<%=cssURL%>/styles.css" type="text/css" />
	
	<script type="text/javascript">
	function openW(){ 
		window.open('<c:url value="/finance/payment.do?method=onQueryPaymentBalances"/>', '查账', 'height=400, width=300, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')
	}
	
	</script>
	<style type="text/css">
		body{font-size:12px;}

	</style>
  </head>
  
  <body>
  	<!-- <iframe src='<portal:envget envkey="WEBSER_ISS"/>/workList.do?method=onMainView' frameborder="0" width="850" height="420"></iframe>

 <br><br> -->
 					<rs:permission action="3" resource="BUSSENV.BUSSENV.SECURITY.ROLE.QUICK.JXS">
					
					<c:if test="${planAllowType != 'normal'}">
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=1"/>' target='_blank' >[正常计划]</a> &nbsp;
					</c:if>
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=2"/>' target='_blank' >[追加计划]</a> &nbsp;
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=3"/>' target='_blank' >[取消计划]</a> &nbsp;&nbsp;
						<a href='<%=basePath%>sellReport/sell.do?method=onQAnalysisShoearRangFrank' target='_blank' >[履约率分析]</a>&nbsp; 
						<a href='<%=basePath%>financeReport/finance.do?method=onQDetailsPayment' target='_blank' class="titles">货款明细分析</a> &nbsp;
						<a href='<%=basePath%>sellReport/sell.do?method=onQDetailsPlanCollect' target='_blank' class="titles">产销计划明细</a> &nbsp;
						<a href='<%=basePath%>sellReport/sell.do?method=onQDetailsIndent' target='_blank' class="titles">销售订单明细</a> &nbsp;
						<a href='<%=basePath%>financeReport/finance.do?method=onQSummaryPaymentCollect' target='_blank' class="titles">货款余额</a> &nbsp;

					<br><br>

						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=1"/>' target='_blank' >[正常订单]</a> &nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=2"/>' target='_blank' > [广告用酒]</a> &nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=3"/>' target='_blank' > [正常订单退货]</a> &nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=4"/>' target='_blank' > [广告用酒退货]</a> &nbsp;
						<a href='javascript:void(0)' onclick="openW()" > <font color="green" style="font-weight: bold;">[查账]</font></a> &nbsp;
					 <br><br>
					

			
						<!-- <a href='<%=basePath%>storage/outStorageManage.do?method=onMainView' target='_blank' > [我的分销商出库]</a> &nbsp;
						&nbsp;
					   
					    <a href='<%=basePath%>storage/queryStorage.do?method=onQueryView' target='_blank' class="titles" >库存明细查询</a> &nbsp;
					    <a href='<%=basePath%>storage/queryStorage.do?method=onQueryIOStorage' target='_blank' class="titles" >出入库汇总</a> &nbsp;
  -->
		<br><br><br>
		</rs:permission>
		<rs:permission action="3" resource="BUSSENV.BUSSENV.SECURITY.ROLE.QUICK.DQ">
			<a href='<%=basePath%>sell/indent.do?method=onMainView' target='_blank' > [订单管理]</a>:管理客户订单
			&nbsp;
			<a href='<%=basePath%>sell/plan.do?method=onMainView' target='_blank' > [产销计划管理]</a>:管理客户产销计划
			&nbsp;
			<a href='<%=basePath%>finance/payment.do?method=onMainView' target='_blank' class="titlemid"> [货款记账]</a>:订单审批前需先记帐
			&nbsp;
			<a href='<%=basePath%>storage/transferGoodsVirtual.do?method=onMainView' target='_blank' class="titlemid"> [产销计划调剂]</a><br><br>

		</rs:permission>	
		<rs:permission action="3" resource="BUSSENV.BUSSENV.SECURITY.ROLE.QUICK.CK">		
	业务流程:&nbsp;<a href='<%=basePath%>storage/inStorage.do?method=onMainViewx' target='_blank'>[入库单]</a>&nbsp;
	<a href='<%=basePath%>storage/allocateCargo.do?method=onMainView' target='_blank'>[配送单]</a>&nbsp;
	<a href='<%=basePath%>storage/produceLine.do?method=onMainView' target='_blank'>[生产单]</a>&nbsp;
	<a href='<%=basePath%>storage/adjustmentFrom.do?method=onMainView' target='_blank' class="titlemid"> [调帐单]</a>	

	<br><br>	
	业务综合:
		    <a href="<%=basePath%>/portal/reportStorageDetail.jsp" target='_blank'  title="提供入库/配送的制单、跟踪、明细查询、汇总报表和可视化的全景库位图">[入库&配送管理]</a>&nbsp;
		    <!--:提供入库/配送的制单、跟踪、明细查询、汇总报表和可视化的全景库位图-->
		    <a href='<portal:envget envkey="WEBSER_FACTORY"/>' target='_blank' >[生产&库位管理]</a> &nbsp;
		    
		     <a href='<portal:envget envkey="WEBSER_FACTORY"/>/produce/produceXB.do?method=onProduceXBMainView' target='_blank' >[二次生产1]</a> &nbsp;
		     <a href='<portal:envget envkey="WEBSER_FACTORY"/>/produce/produceXB2.do?method=onProduceXB2MainView' target='_blank' >[二次生产2]</a> &nbsp;
		    
<br><br>
其他业务:
			<a href="<%=basePath%>/storage/storageDamaged.do?method=onMainView" target='_blank' >[库内破损管理]</a> &nbsp;
			<a href='<%=basePath%>/storage/tagPriceInStorage.do?method=onMainView' target='_blank' >[标识物料入库]</a> 

			
&nbsp;&nbsp;<a href='<%=basePath%>/storage/tagPriceDamage.do?method=onMainView' target='_blank' >[标识物料破损登记]</a> &nbsp;
			<a href='<%=basePath %>/storage/transferGoods.do?method=onMainView' target='_blank' class="titlemid">[调货管理]</a> &nbsp;
			<a href='<%=basePath %>/extfunc/MergeInventoryAndProductAge.jsp' target='_blank' class="titlemid">[仓库盘点]</a>  &nbsp;

			&nbsp;<a href='<%=basePath %>/products/product.do?method=onProductManageMain' target='_blank' class="titlemid">[产品基础信息管理]</a> &nbsp;
			<!--  <a href='<portal:envget envkey="WEBSER_ZYSYNC"/>/dataProcess/exportData.do?method=onMainView' target='_blank' >[仓库数据导出]</a> &nbsp;&nbsp;
			<a href='<portal:envget envkey="WEBSER_SECURITY3A"/>/rsinfo/dept/frameIndex.jsp' target='_blank' class="titlemid">[帐户权限管理]</a> &nbsp;&nbsp;
			
			-->
			&nbsp;<a href='<%=basePath %>/storageReport/storage.do?method=onQueryProduceBalances&t=excel&type=book' class="titlemid">[查账-生产余额(账面)]</a> &nbsp;
			&nbsp;<a href='<%=basePath %>/storageReport/storage.do?method=onQueryProduceBalances&t=excel&type=fact'  class="titlemid">[查账-生产余额(实际)]</a> &nbsp;

		<br><br>
</rs:permission>
		
		<rs:permission action="3" resource="BUSSENV.BUSSENV.SECURITY.ROLE.QUICK.SGS">
			<a href='<%=basePath%>sell/indent.do?method=onMainView' target='_blank' > [订单管理]</a>&nbsp;&nbsp; 
			
			<a href='<%=basePath%>sell/plan.do?method=onMainView' target='_blank' > [产销计划管理]</a>
			
			<a href='<%=basePath%>finance/sellinvoice.do?method=onMainView' target='_blank' > [发票登记]</a> &nbsp;&nbsp; 
			
			<a href='<%=basePath%>products/product.do?method=onProductList' target='_blank' class="titlemid">[产品价格指标管理]</a> &nbsp; &nbsp; 
		
			<a href='<%=basePath%>/storage/transferGoodsVirtual.do?method=onMainView' target='_blank' class="titlemid"> [产销计划调剂]</a>&nbsp;&nbsp; 	
			
			<br><br>
			<a href='<%=basePath%>finance/incentivePaymentCash.do?method=onMainView' target='_blank' >[差价投入兑付记账]</a> &nbsp;&nbsp;
		    <a href='<%=basePath%>finance/zpayment.do?method=onMainView' target='_blank'>[转下线投入记账]</a> &nbsp;&nbsp;

			<a href='<%=basePath%>finance/managePayment.do?method=onMainView' target='_blank' >[标识管理收费记账]</a> &nbsp;&nbsp;
		    <a href='<%=basePath%>finance/otherPayment.do?method=onMainView' target='_blank'>[标识管理其他使用费记账]</a> &nbsp;&nbsp;
		    <br><br>
			 <a href='<%=basePath%>dataCheck.do?method=onMainView&ps=ps' target='_blank'>
			<strong><font class='fonttext'>[数据比对]</font></strong> </a>
			 通过导入总部DRP相关的数据，与本系统数据进行比对，即时处理问题数据
		  
	</rs:permission>	
		
	
				

  </body>
</html>
