<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="yc.erp.zy.common.CheckPlanTime"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
       ResourceBundle configx = ResourceBundle.getBundle("config",Locale.CHINESE);
		/* 产销计划 隔月 控制 */
		Date date = new Date();
		String planLimitStr = configx.getString("planLimit");
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function openW(){
		openWinCenter('查账','<c:url value="/finance/payment.do?method=onQueryPaymentBalancesView&v=1"/>',400,330);
	}
	
	</script>

  </head>
  
  <body>
		<p class="titlebig">&nbsp;&nbsp;快速业务入口：</p>

		    <c:if test="${depttype=='jxs'}"><!-- 经销商业务入口 -->
		    <c:if test="${planTaskCount>0}">
            	<a href='<c:url value="/sell/plan.do?method=onMainView&stype=backAndUnSubmit"/>' target='_blank' class="titlemid">${planTaskCount}条未处理的计划</a>
			</c:if>
			&nbsp;
			<c:if test="${indentTaskCount>0}">
				<a href='<c:url value="/sell/indent.do?method=onMainView&stype=backAndUnSubmit"/>' target='_blank' class="titlemid">${indentTaskCount}条未处理的订单</a>
			</c:if>
			 <br>
			<table>
				<tr><td class="title" >
					<strong>&nbsp;&nbsp;&nbsp;&nbsp;我的产销计划:</strong>
					<br><br>
					</td><td>
					<c:if test="${planAllowType != 'normal'}">
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=1"/>' target='_blank' class="titlesmall">[正常计划]</a> &nbsp;&nbsp;
					</c:if>
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=2"/>' target='_blank' class="titlesmall">[追加计划]</a> &nbsp;&nbsp;
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=3"/>' target='_blank' class="titlesmall">[取消计划]</a> &nbsp;&nbsp;&nbsp;
						<a href='<%=basePath%>sellReport/sell.do?method=onQAnalysisShoearRangFrank' target='_blank' class="titles">[履约率分析]</a>
					<br><br>
					</td>
				</tr>
				<tr><td class="title" >
				    <strong>&nbsp;&nbsp;&nbsp;&nbsp;我要下订单:</strong>
				    <br><br>
					</td><td>
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=1"/>' target='_blank' class="titlesmall">[正常订单]</a> &nbsp;&nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=2"/>' target='_blank' class="titlesmall"> [广告用酒]</a> &nbsp;&nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=3"/>' target='_blank' class="titlesmall"> [正常订单退货]</a> &nbsp;&nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=4"/>' target='_blank' class="titlesmall"> [广告用酒退货]</a> &nbsp;&nbsp;
						<a href='javascript:void(0)' onclick="openW()" class="titlesmall"> <font color="green" style="font-weight: bold;">[查账]</font></a> &nbsp;&nbsp;
					 <br><br>
					</td>
				  </tr>
				  <tr><td class="title" >
					<strong>&nbsp;&nbsp;&nbsp;&nbsp;我的销售与库存:</strong>
					</td><td>
						<a href='<%=basePath%>storage/outStorageManage.do?method=onMainView' target='_blank' class="titlesmall"> [我的分销商出库]</a> &nbsp;&nbsp;
					    <a href='<%=basePath%>financeReport/finance.do?method=onQDetailsPayment' target='_blank' class="titles">货款明细分析</a> &nbsp;&nbsp;
					   <!--  <a href='<%=basePath%>storage/queryStorage.do?method=onQueryView' target='_blank' class="titles" >库存明细查询</a> &nbsp;&nbsp;
					    <a href='<%=basePath%>storage/queryStorage.do?method=onQueryIOStorage' target='_blank' class="titles" >出入库汇总</a> &nbsp;&nbsp;
					   -->
			   		</td>
			   	</tr>
		   </table>
		</c:if>
		<c:if test="${depttype=='yxbm'&&sysmode!=1}"><!-- 大营销部入口 -->
		<br>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href='<%=basePath%>finance/payment.do?method=onMainView' target='_blank' class="titlemid"> [货款记账]</a> &nbsp;&nbsp;
			<a href='<%=basePath%>sell/indent.do?method=onMainView' target='_blank' class="titlesmall"> [订单管理]</a> &nbsp;&nbsp;
			
		</c:if>		

		<c:if test="${storem==1}"><!-- 仓办 --> <br>&nbsp;&nbsp;&nbsp;&nbsp;
		
		    <a href="<%=basePath%>/portal/reportStorageDetail.jsp" target='_blank' class="titlesmall" title="提供入库/配送的制单、跟踪、明细查询、汇总报表和可视化的全景库位图">[入库&配送管理]</a>
		    <!--:提供入库/配送的制单、跟踪、明细查询、汇总报表和可视化的全景库位图-->
		    &nbsp;&nbsp;<a href='<portal:envget envkey="WEBSER_FACTORY"/>' target='_blank' class="titlesmall">[生产&库位综合管理]</a> &nbsp;&nbsp;
		     &nbsp;&nbsp;<a href='<%=basePath%>/storage/produceLine.do?method=onMainView' target='_blank' class="titlesmall">[生产单据管理与跟踪]</a> &nbsp;&nbsp;
		     &nbsp;&nbsp;<a href='<%=basePath %>/storage/produceLine.do?method=onProduceGjView&status=2' target='_blank' class="titlesmall">[已生产数检查]</a> &nbsp;&nbsp;
		     &nbsp;&nbsp;<a href='<%=basePath %>/storage/produceLine.do?method=onProduceGjView&status=1' target='_blank' class="titlesmall">[未生产数检查]</a> &nbsp;&nbsp;
		   
		    <br><br>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="<%=basePath%>/storage/storageDamaged.do?method=onMainView" target='_blank' class="titlesmall">[库内破损管理]</a> &nbsp;&nbsp;
			<a href='<%=basePath%>/storage/tagPriceInStorage.do?method=onMainView' target='_blank' class="titlesmall">[标识物料入库]</a> &nbsp;&nbsp;
			<a href='<%=basePath%>/storage/tagPriceDamage.do?method=onMainView' target='_blank' class="titlesmall">[标识物料破损登记]</a> &nbsp;&nbsp;
			<a href='<portal:envget envkey="WEBSER_FACTORY"/>/storage/trace.do?method=onMainView' target='_blank'class="titlesmall"> [三级关联码查询]</a> &nbsp;&nbsp;
			<a href="<%=basePath %>/storage/allocateCargo.do?method=onGrossWeightQueryMain" target='_blank' class="titlesmall">[配送毛重统计]</a> &nbsp;&nbsp;
			<br><br>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href='<portal:envget envkey="WEBSER_ZYSYNC"/>/clientSync.jsp' target='_blank' class="titlesmall">[同步基础数据]</a> &nbsp;&nbsp;
			<a href="<portal:envget envkey="WEBSER_ZYSYNC"/>/dataProcess/exportData.do?method=onMainView" target='_blank' class="titlesmall">[仓库数据导出]</a> &nbsp;&nbsp;
			<a href='<portal:envget envkey="WEBSER_SECURITY3A"/>/rsinfo/dept/frameIndex.jsp' target='_blank' class="titlemid">[帐户权限管理]</a> &nbsp;&nbsp;
			<a href='<%=basePath %>/products/product.do?method=onProductList' target='_blank' class="titlemid">[产品基础信息管理]</a> &nbsp;&nbsp;
			<a href='<%=basePath %>/extfunc/MergeInventoryAndProductAge.jsp' target='_blank' class="titlemid">[仓库盘点]</a> &nbsp;&nbsp;
			<a href='<%=basePath %>/storage/transferGoods.do?method=onMainView' target='_blank' class="titlemid">[调货管理]</a> &nbsp;&nbsp;
						<a href='<%=basePath %>/storage/adjustmentFrom.do?method=onMainView'
				target='_blank' class="titlemid">[调账单]</a> &nbsp;&nbsp;
		    <a href='<portal:envget envkey="WEBSER_FACTORY"/>/storage/wl.do?method=onEditWlInfoMainView' target='_blank'><strong><font class='fonttext'>[库位数据平滑]</font></strong></a><font class='fonttext'></font>&nbsp;&nbsp;
		    <a href='<portal:envget envkey="WEBSER_FACTORY"/>/storage/wl.do?method=onWlMoveView' target='_blank'><strong><font class='fonttext'>[移库]</font></strong></a><font class='fonttext'></font>
		    </br></br>&nbsp;&nbsp;&nbsp;&nbsp;<a href='<%=basePath %>/storage/produceLine.do?method=onMainView' target='_blank' class="titlemid">[生产制单]</a><font class='fonttext'>&nbsp;&nbsp;&nbsp;生产制单通常是针对大经销商的备货、制单时系统会提示某经销商的当前已有的备货数,制单提交后系统将按制单人</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;输入的生产数通知生产。</font> &nbsp;&nbsp;
		</c:if>					
		
		
		<c:if test="${depttype==null}"><!--省公司 -->
		<br>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href='<%=basePath%>products/product.do?method=onProductList' target='_blank' class="titlemid">[产品价格指标管理]</a> &nbsp;&nbsp;
			<a href='<%=basePath%>finance/sellinvoice.do?method=onMainView' target='_blank' class="titlesmall"> [发票登记]</a> &nbsp;&nbsp; <br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href='<%=basePath%>finance/incentivePaymentCash.do?method=onMainView' target='_blank' class="titlesmall">[差价投入兑付记账]</a> &nbsp;&nbsp;
		    <a href='<%=basePath%>finance/zpayment.do?method=onMainView' target='_blank'class="titlesmall">[转下线投入记账]</a> &nbsp;&nbsp;

			<a href='<%=basePath%>finance/managePayment.do?method=onMainView' target='_blank' class="titlesmall">[标识管理收费记账]</a> &nbsp;&nbsp;
		    <a href='<%=basePath%>finance/otherPayment.do?method=onMainView' target='_blank'class="titlesmall">[标识管理其他使用费记账]</a> &nbsp;&nbsp;
		    <br><br>&nbsp;&nbsp;&nbsp;&nbsp;
		    <a href='<%=basePath%>dataCheck.do?method=onMainView' target='_blank'>
			<strong><font class='fonttext'>[数据比对]</font></strong> </a> 通过导入总部DRP相关的数据，与本系统数据进行比对，即时处理问题数据
		  
		</c:if>
		
		<c:if test="${storem==2}">
			<!-- 仓管 -->
			<br><br>&nbsp;&nbsp;&nbsp;&nbsp;
		    <a
				href='<portal:envget envkey="WEBSER_FACTORY"/>/storage/wl.do?method=onWlMoveView'
				target='_blank'><strong><font class='fonttext'>[移库]</font>
			</strong>
			</a>&nbsp;&nbsp;
		    <a
				href='<portal:envget envkey="WEBSER_FACTORY"/>/produce/produceXB.do?method=onProduceXBMainView'
				target='_blank'><strong><font class='fonttext'>[二次生产]</font>
			</strong>
			</a>
			<font class='fonttext'></font>
		</c:if>
		<c:if test="${storem==3}">
			<!-- 生产 -->
			<br><br>&nbsp;&nbsp;&nbsp;&nbsp;
			<a  href='<portal:envget envkey="WEBSER_FACTORY"/>/produce/produceX.do?method=onProduceTaskMainView'
				target='_blank'><strong><font class='fonttext'>[进入生产任务列表]</font>
			</strong>
			</a> &nbsp;
		</c:if>
		<c:if test="${admin=='yes'}"><!-- 管理员 --> <br>&nbsp;&nbsp;&nbsp;&nbsp;
		
   
			<a href='<portal:envget envkey="WEBSER_FACTORY"/>/storage/trace.do?method=onMainView' target='_blank'class="titlesmall"> [三级关联码查询]</a> &nbsp;&nbsp;

			<a href='<portal:envget envkey="WEBSER_SECURITY3A"/>/rsinfo/dept/frameIndex.jsp' target='_blank' class="titlemid">[帐户权限管理]</a> &nbsp;&nbsp;
			<a href='<%=basePath %>/products/product.do?method=onProductList' target='_blank' class="titlemid">[产品基础信息管理]</a> &nbsp;&nbsp;
		</c:if>					

  </body>
</html>
