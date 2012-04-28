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
		/* �����ƻ� ���� ���� */
		Date date = new Date();
		String planLimitStr = configx.getString("methodLimit");
		Integer planLimit = Integer.parseInt(planLimitStr);
		int currDay = CheckPlanTime.getDay(date);
		if (planLimit < currDay) {
			request.setAttribute("planAllowType", "normal");// ֻ���ύ���¼ƻ�
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
		window.open('<c:url value="/finance/payment.do?method=onQueryPaymentBalances"/>', '����', 'height=400, width=300, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no')
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
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=1"/>' target='_blank' >[�����ƻ�]</a> &nbsp;
					</c:if>
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=2"/>' target='_blank' >[׷�Ӽƻ�]</a> &nbsp;
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=3"/>' target='_blank' >[ȡ���ƻ�]</a> &nbsp;&nbsp;
						<a href='<%=basePath%>sellReport/sell.do?method=onQAnalysisShoearRangFrank' target='_blank' >[��Լ�ʷ���]</a>&nbsp; 
						<a href='<%=basePath%>financeReport/finance.do?method=onQDetailsPayment' target='_blank' class="titles">������ϸ����</a> &nbsp;
						<a href='<%=basePath%>sellReport/sell.do?method=onQDetailsPlanCollect' target='_blank' class="titles">�����ƻ���ϸ</a> &nbsp;
						<a href='<%=basePath%>sellReport/sell.do?method=onQDetailsIndent' target='_blank' class="titles">���۶�����ϸ</a> &nbsp;
						<a href='<%=basePath%>financeReport/finance.do?method=onQSummaryPaymentCollect' target='_blank' class="titles">�������</a> &nbsp;

					<br><br>

						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=1"/>' target='_blank' >[��������]</a> &nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=2"/>' target='_blank' > [����þ�]</a> &nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=3"/>' target='_blank' > [���������˻�]</a> &nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=4"/>' target='_blank' > [����þ��˻�]</a> &nbsp;
						<a href='javascript:void(0)' onclick="openW()" > <font color="green" style="font-weight: bold;">[����]</font></a> &nbsp;
					 <br><br>
					

			
						<!-- <a href='<%=basePath%>storage/outStorageManage.do?method=onMainView' target='_blank' > [�ҵķ����̳���]</a> &nbsp;
						&nbsp;
					   
					    <a href='<%=basePath%>storage/queryStorage.do?method=onQueryView' target='_blank' class="titles" >�����ϸ��ѯ</a> &nbsp;
					    <a href='<%=basePath%>storage/queryStorage.do?method=onQueryIOStorage' target='_blank' class="titles" >��������</a> &nbsp;
  -->
		<br><br><br>
		</rs:permission>
		<rs:permission action="3" resource="BUSSENV.BUSSENV.SECURITY.ROLE.QUICK.DQ">
			<a href='<%=basePath%>sell/indent.do?method=onMainView' target='_blank' > [��������]</a>:����ͻ�����
			&nbsp;
			<a href='<%=basePath%>sell/plan.do?method=onMainView' target='_blank' > [�����ƻ�����]</a>:����ͻ������ƻ�
			&nbsp;
			<a href='<%=basePath%>finance/payment.do?method=onMainView' target='_blank' class="titlemid"> [�������]</a>:��������ǰ���ȼ���
			&nbsp;
			<a href='<%=basePath%>storage/transferGoodsVirtual.do?method=onMainView' target='_blank' class="titlemid"> [�����ƻ�����]</a><br><br>

		</rs:permission>	
		<rs:permission action="3" resource="BUSSENV.BUSSENV.SECURITY.ROLE.QUICK.CK">		
	ҵ������:&nbsp;<a href='<%=basePath%>storage/inStorage.do?method=onMainViewx' target='_blank'>[��ⵥ]</a>&nbsp;
	<a href='<%=basePath%>storage/allocateCargo.do?method=onMainView' target='_blank'>[���͵�]</a>&nbsp;
	<a href='<%=basePath%>storage/produceLine.do?method=onMainView' target='_blank'>[������]</a>&nbsp;
	<a href='<%=basePath%>storage/adjustmentFrom.do?method=onMainView' target='_blank' class="titlemid"> [���ʵ�]</a>	

	<br><br>	
	ҵ���ۺ�:
		    <a href="<%=basePath%>/portal/reportStorageDetail.jsp" target='_blank'  title="�ṩ���/���͵��Ƶ������١���ϸ��ѯ�����ܱ���Ϳ��ӻ���ȫ����λͼ">[���&���͹���]</a>&nbsp;
		    <!--:�ṩ���/���͵��Ƶ������١���ϸ��ѯ�����ܱ���Ϳ��ӻ���ȫ����λͼ-->
		    <a href='<portal:envget envkey="WEBSER_FACTORY"/>' target='_blank' >[����&��λ����]</a> &nbsp;
		    
		     <a href='<portal:envget envkey="WEBSER_FACTORY"/>/produce/produceXB.do?method=onProduceXBMainView' target='_blank' >[��������1]</a> &nbsp;
		     <a href='<portal:envget envkey="WEBSER_FACTORY"/>/produce/produceXB2.do?method=onProduceXB2MainView' target='_blank' >[��������2]</a> &nbsp;
		    
<br><br>
����ҵ��:
			<a href="<%=basePath%>/storage/storageDamaged.do?method=onMainView" target='_blank' >[�����������]</a> &nbsp;
			<a href='<%=basePath%>/storage/tagPriceInStorage.do?method=onMainView' target='_blank' >[��ʶ�������]</a> 

			
&nbsp;&nbsp;<a href='<%=basePath%>/storage/tagPriceDamage.do?method=onMainView' target='_blank' >[��ʶ��������Ǽ�]</a> &nbsp;
			<a href='<%=basePath %>/storage/transferGoods.do?method=onMainView' target='_blank' class="titlemid">[��������]</a> &nbsp;
			<a href='<%=basePath %>/extfunc/MergeInventoryAndProductAge.jsp' target='_blank' class="titlemid">[�ֿ��̵�]</a>  &nbsp;

			&nbsp;<a href='<%=basePath %>/products/product.do?method=onProductManageMain' target='_blank' class="titlemid">[��Ʒ������Ϣ����]</a> &nbsp;
			<!--  <a href='<portal:envget envkey="WEBSER_ZYSYNC"/>/dataProcess/exportData.do?method=onMainView' target='_blank' >[�ֿ����ݵ���]</a> &nbsp;&nbsp;
			<a href='<portal:envget envkey="WEBSER_SECURITY3A"/>/rsinfo/dept/frameIndex.jsp' target='_blank' class="titlemid">[�ʻ�Ȩ�޹���]</a> &nbsp;&nbsp;
			
			-->
			&nbsp;<a href='<%=basePath %>/storageReport/storage.do?method=onQueryProduceBalances&t=excel&type=book' class="titlemid">[����-�������(����)]</a> &nbsp;
			&nbsp;<a href='<%=basePath %>/storageReport/storage.do?method=onQueryProduceBalances&t=excel&type=fact'  class="titlemid">[����-�������(ʵ��)]</a> &nbsp;

		<br><br>
</rs:permission>
		
		<rs:permission action="3" resource="BUSSENV.BUSSENV.SECURITY.ROLE.QUICK.SGS">
			<a href='<%=basePath%>sell/indent.do?method=onMainView' target='_blank' > [��������]</a>&nbsp;&nbsp; 
			
			<a href='<%=basePath%>sell/plan.do?method=onMainView' target='_blank' > [�����ƻ�����]</a>
			
			<a href='<%=basePath%>finance/sellinvoice.do?method=onMainView' target='_blank' > [��Ʊ�Ǽ�]</a> &nbsp;&nbsp; 
			
			<a href='<%=basePath%>products/product.do?method=onProductList' target='_blank' class="titlemid">[��Ʒ�۸�ָ�����]</a> &nbsp; &nbsp; 
		
			<a href='<%=basePath%>/storage/transferGoodsVirtual.do?method=onMainView' target='_blank' class="titlemid"> [�����ƻ�����]</a>&nbsp;&nbsp; 	
			
			<br><br>
			<a href='<%=basePath%>finance/incentivePaymentCash.do?method=onMainView' target='_blank' >[���Ͷ��Ҹ�����]</a> &nbsp;&nbsp;
		    <a href='<%=basePath%>finance/zpayment.do?method=onMainView' target='_blank'>[ת����Ͷ�����]</a> &nbsp;&nbsp;

			<a href='<%=basePath%>finance/managePayment.do?method=onMainView' target='_blank' >[��ʶ�����շѼ���]</a> &nbsp;&nbsp;
		    <a href='<%=basePath%>finance/otherPayment.do?method=onMainView' target='_blank'>[��ʶ��������ʹ�÷Ѽ���]</a> &nbsp;&nbsp;
		    <br><br>
			 <a href='<%=basePath%>dataCheck.do?method=onMainView&ps=ps' target='_blank'>
			<strong><font class='fonttext'>[���ݱȶ�]</font></strong> </a>
			 ͨ�������ܲ�DRP��ص����ݣ��뱾ϵͳ���ݽ��бȶԣ���ʱ������������
		  
	</rs:permission>	
		
	
				

  </body>
</html>
