<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="yc.erp.zy.common.CheckPlanTime"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
       ResourceBundle configx = ResourceBundle.getBundle("config",Locale.CHINESE);
		/* �����ƻ� ���� ���� */
		Date date = new Date();
		String planLimitStr = configx.getString("planLimit");
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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function openW(){
		openWinCenter('����','<c:url value="/finance/payment.do?method=onQueryPaymentBalancesView&v=1"/>',400,330);
	}
	
	</script>

  </head>
  
  <body>
		<p class="titlebig">&nbsp;&nbsp;����ҵ����ڣ�</p>

		    <c:if test="${depttype=='jxs'}"><!-- ������ҵ����� -->
		    <c:if test="${planTaskCount>0}">
            	<a href='<c:url value="/sell/plan.do?method=onMainView&stype=backAndUnSubmit"/>' target='_blank' class="titlemid">${planTaskCount}��δ����ļƻ�</a>
			</c:if>
			&nbsp;
			<c:if test="${indentTaskCount>0}">
				<a href='<c:url value="/sell/indent.do?method=onMainView&stype=backAndUnSubmit"/>' target='_blank' class="titlemid">${indentTaskCount}��δ����Ķ���</a>
			</c:if>
			 <br>
			<table>
				<tr><td class="title" >
					<strong>&nbsp;&nbsp;&nbsp;&nbsp;�ҵĲ����ƻ�:</strong>
					<br><br>
					</td><td>
					<c:if test="${planAllowType != 'normal'}">
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=1"/>' target='_blank' class="titlesmall">[�����ƻ�]</a> &nbsp;&nbsp;
					</c:if>
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=2"/>' target='_blank' class="titlesmall">[׷�Ӽƻ�]</a> &nbsp;&nbsp;
						<a href='<c:url value="/sell/plan.do?method=onEditView&typeO=3"/>' target='_blank' class="titlesmall">[ȡ���ƻ�]</a> &nbsp;&nbsp;&nbsp;
						<a href='<%=basePath%>sellReport/sell.do?method=onQAnalysisShoearRangFrank' target='_blank' class="titles">[��Լ�ʷ���]</a>
					<br><br>
					</td>
				</tr>
				<tr><td class="title" >
				    <strong>&nbsp;&nbsp;&nbsp;&nbsp;��Ҫ�¶���:</strong>
				    <br><br>
					</td><td>
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=1"/>' target='_blank' class="titlesmall">[��������]</a> &nbsp;&nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=2"/>' target='_blank' class="titlesmall"> [����þ�]</a> &nbsp;&nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=3"/>' target='_blank' class="titlesmall"> [���������˻�]</a> &nbsp;&nbsp;
						<a href='<c:url value="/sell/indent.do?method=onEditView&typeO=4"/>' target='_blank' class="titlesmall"> [����þ��˻�]</a> &nbsp;&nbsp;
						<a href='javascript:void(0)' onclick="openW()" class="titlesmall"> <font color="green" style="font-weight: bold;">[����]</font></a> &nbsp;&nbsp;
					 <br><br>
					</td>
				  </tr>
				  <tr><td class="title" >
					<strong>&nbsp;&nbsp;&nbsp;&nbsp;�ҵ���������:</strong>
					</td><td>
						<a href='<%=basePath%>storage/outStorageManage.do?method=onMainView' target='_blank' class="titlesmall"> [�ҵķ����̳���]</a> &nbsp;&nbsp;
					    <a href='<%=basePath%>financeReport/finance.do?method=onQDetailsPayment' target='_blank' class="titles">������ϸ����</a> &nbsp;&nbsp;
					   <!--  <a href='<%=basePath%>storage/queryStorage.do?method=onQueryView' target='_blank' class="titles" >�����ϸ��ѯ</a> &nbsp;&nbsp;
					    <a href='<%=basePath%>storage/queryStorage.do?method=onQueryIOStorage' target='_blank' class="titles" >��������</a> &nbsp;&nbsp;
					   -->
			   		</td>
			   	</tr>
		   </table>
		</c:if>
		<c:if test="${depttype=='yxbm'&&sysmode!=1}"><!-- ��Ӫ������� -->
		<br>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href='<%=basePath%>finance/payment.do?method=onMainView' target='_blank' class="titlemid"> [�������]</a> &nbsp;&nbsp;
			<a href='<%=basePath%>sell/indent.do?method=onMainView' target='_blank' class="titlesmall"> [��������]</a> &nbsp;&nbsp;
			
		</c:if>		

		<c:if test="${storem==1}"><!-- �ְ� --> <br>&nbsp;&nbsp;&nbsp;&nbsp;
		
		    <a href="<%=basePath%>/portal/reportStorageDetail.jsp" target='_blank' class="titlesmall" title="�ṩ���/���͵��Ƶ������١���ϸ��ѯ�����ܱ���Ϳ��ӻ���ȫ����λͼ">[���&���͹���]</a>
		    <!--:�ṩ���/���͵��Ƶ������١���ϸ��ѯ�����ܱ���Ϳ��ӻ���ȫ����λͼ-->
		    &nbsp;&nbsp;<a href='<portal:envget envkey="WEBSER_FACTORY"/>' target='_blank' class="titlesmall">[����&��λ�ۺϹ���]</a> &nbsp;&nbsp;
		     &nbsp;&nbsp;<a href='<%=basePath%>/storage/produceLine.do?method=onMainView' target='_blank' class="titlesmall">[�������ݹ��������]</a> &nbsp;&nbsp;
		     &nbsp;&nbsp;<a href='<%=basePath %>/storage/produceLine.do?method=onProduceGjView&status=2' target='_blank' class="titlesmall">[�����������]</a> &nbsp;&nbsp;
		     &nbsp;&nbsp;<a href='<%=basePath %>/storage/produceLine.do?method=onProduceGjView&status=1' target='_blank' class="titlesmall">[δ���������]</a> &nbsp;&nbsp;
		   
		    <br><br>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="<%=basePath%>/storage/storageDamaged.do?method=onMainView" target='_blank' class="titlesmall">[�����������]</a> &nbsp;&nbsp;
			<a href='<%=basePath%>/storage/tagPriceInStorage.do?method=onMainView' target='_blank' class="titlesmall">[��ʶ�������]</a> &nbsp;&nbsp;
			<a href='<%=basePath%>/storage/tagPriceDamage.do?method=onMainView' target='_blank' class="titlesmall">[��ʶ��������Ǽ�]</a> &nbsp;&nbsp;
			<a href='<portal:envget envkey="WEBSER_FACTORY"/>/storage/trace.do?method=onMainView' target='_blank'class="titlesmall"> [�����������ѯ]</a> &nbsp;&nbsp;
			<a href="<%=basePath %>/storage/allocateCargo.do?method=onGrossWeightQueryMain" target='_blank' class="titlesmall">[����ë��ͳ��]</a> &nbsp;&nbsp;
			<br><br>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href='<portal:envget envkey="WEBSER_ZYSYNC"/>/clientSync.jsp' target='_blank' class="titlesmall">[ͬ����������]</a> &nbsp;&nbsp;
			<a href="<portal:envget envkey="WEBSER_ZYSYNC"/>/dataProcess/exportData.do?method=onMainView" target='_blank' class="titlesmall">[�ֿ����ݵ���]</a> &nbsp;&nbsp;
			<a href='<portal:envget envkey="WEBSER_SECURITY3A"/>/rsinfo/dept/frameIndex.jsp' target='_blank' class="titlemid">[�ʻ�Ȩ�޹���]</a> &nbsp;&nbsp;
			<a href='<%=basePath %>/products/product.do?method=onProductList' target='_blank' class="titlemid">[��Ʒ������Ϣ����]</a> &nbsp;&nbsp;
			<a href='<%=basePath %>/extfunc/MergeInventoryAndProductAge.jsp' target='_blank' class="titlemid">[�ֿ��̵�]</a> &nbsp;&nbsp;
			<a href='<%=basePath %>/storage/transferGoods.do?method=onMainView' target='_blank' class="titlemid">[��������]</a> &nbsp;&nbsp;
						<a href='<%=basePath %>/storage/adjustmentFrom.do?method=onMainView'
				target='_blank' class="titlemid">[���˵�]</a> &nbsp;&nbsp;
		    <a href='<portal:envget envkey="WEBSER_FACTORY"/>/storage/wl.do?method=onEditWlInfoMainView' target='_blank'><strong><font class='fonttext'>[��λ����ƽ��]</font></strong></a><font class='fonttext'></font>&nbsp;&nbsp;
		    <a href='<portal:envget envkey="WEBSER_FACTORY"/>/storage/wl.do?method=onWlMoveView' target='_blank'><strong><font class='fonttext'>[�ƿ�]</font></strong></a><font class='fonttext'></font>
		    </br></br>&nbsp;&nbsp;&nbsp;&nbsp;<a href='<%=basePath %>/storage/produceLine.do?method=onMainView' target='_blank' class="titlemid">[�����Ƶ�]</a><font class='fonttext'>&nbsp;&nbsp;&nbsp;�����Ƶ�ͨ������Դ����̵ı������Ƶ�ʱϵͳ����ʾĳ�����̵ĵ�ǰ���еı�����,�Ƶ��ύ��ϵͳ�����Ƶ���</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�����������֪ͨ������</font> &nbsp;&nbsp;
		</c:if>					
		
		
		<c:if test="${depttype==null}"><!--ʡ��˾ -->
		<br>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href='<%=basePath%>products/product.do?method=onProductList' target='_blank' class="titlemid">[��Ʒ�۸�ָ�����]</a> &nbsp;&nbsp;
			<a href='<%=basePath%>finance/sellinvoice.do?method=onMainView' target='_blank' class="titlesmall"> [��Ʊ�Ǽ�]</a> &nbsp;&nbsp; <br><br>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<a href='<%=basePath%>finance/incentivePaymentCash.do?method=onMainView' target='_blank' class="titlesmall">[���Ͷ��Ҹ�����]</a> &nbsp;&nbsp;
		    <a href='<%=basePath%>finance/zpayment.do?method=onMainView' target='_blank'class="titlesmall">[ת����Ͷ�����]</a> &nbsp;&nbsp;

			<a href='<%=basePath%>finance/managePayment.do?method=onMainView' target='_blank' class="titlesmall">[��ʶ�����շѼ���]</a> &nbsp;&nbsp;
		    <a href='<%=basePath%>finance/otherPayment.do?method=onMainView' target='_blank'class="titlesmall">[��ʶ��������ʹ�÷Ѽ���]</a> &nbsp;&nbsp;
		    <br><br>&nbsp;&nbsp;&nbsp;&nbsp;
		    <a href='<%=basePath%>dataCheck.do?method=onMainView' target='_blank'>
			<strong><font class='fonttext'>[���ݱȶ�]</font></strong> </a> ͨ�������ܲ�DRP��ص����ݣ��뱾ϵͳ���ݽ��бȶԣ���ʱ������������
		  
		</c:if>
		
		<c:if test="${storem==2}">
			<!-- �ֹ� -->
			<br><br>&nbsp;&nbsp;&nbsp;&nbsp;
		    <a
				href='<portal:envget envkey="WEBSER_FACTORY"/>/storage/wl.do?method=onWlMoveView'
				target='_blank'><strong><font class='fonttext'>[�ƿ�]</font>
			</strong>
			</a>&nbsp;&nbsp;
		    <a
				href='<portal:envget envkey="WEBSER_FACTORY"/>/produce/produceXB.do?method=onProduceXBMainView'
				target='_blank'><strong><font class='fonttext'>[��������]</font>
			</strong>
			</a>
			<font class='fonttext'></font>
		</c:if>
		<c:if test="${storem==3}">
			<!-- ���� -->
			<br><br>&nbsp;&nbsp;&nbsp;&nbsp;
			<a  href='<portal:envget envkey="WEBSER_FACTORY"/>/produce/produceX.do?method=onProduceTaskMainView'
				target='_blank'><strong><font class='fonttext'>[�������������б�]</font>
			</strong>
			</a> &nbsp;
		</c:if>
		<c:if test="${admin=='yes'}"><!-- ����Ա --> <br>&nbsp;&nbsp;&nbsp;&nbsp;
		
   
			<a href='<portal:envget envkey="WEBSER_FACTORY"/>/storage/trace.do?method=onMainView' target='_blank'class="titlesmall"> [�����������ѯ]</a> &nbsp;&nbsp;

			<a href='<portal:envget envkey="WEBSER_SECURITY3A"/>/rsinfo/dept/frameIndex.jsp' target='_blank' class="titlemid">[�ʻ�Ȩ�޹���]</a> &nbsp;&nbsp;
			<a href='<%=basePath %>/products/product.do?method=onProductList' target='_blank' class="titlemid">[��Ʒ������Ϣ����]</a> &nbsp;&nbsp;
		</c:if>					

  </body>
</html>
