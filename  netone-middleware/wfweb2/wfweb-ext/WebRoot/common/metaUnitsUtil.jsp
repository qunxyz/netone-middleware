<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String _path0 = request.getContextPath();
String _basePath0 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+_path0+"/";
%>
<script type="text/javascript" src="<%=_basePath0%>dwr/engine.js"></script>
<script type="text/javascript" src="<%=_basePath0%>dwr/interface/UnitsUtil.js"></script>
<!-- DWR���߰� 
<script type="text/javascript" src="<%=_basePath0%>dwr/util.js"></script>
-->
<script type="text/javascript">
    	
   	//��λת��JS������ Author:DON 2010-3-2
   	var UnitConvertTools = window.UnitConvertTools || {};
   	UnitConvertTools = {
   		_count : '0',//��/ƿ��
   		/*
   		* ƿת�����
   		* @param bottleCount ƿ�� (��ֵ����)
   		* @param boxToBottleUnits ���㵥λ
   		* @Return ����
   		*/
   		BottleToBox : function(bottleCount,boxToBottleUnits){        
	        //����Java��UnitsUtil��BottleToBox����,callBackFunΪ�ص�����
	        UnitsUtil.BottleToBox(bottleCount,boxToBottleUnits,{   
	                                     callback:function(value){_count = value;},
	                                     async:false
	                                 }
	        );
	     	return _count;
	    },
	    /*
	    * ��תƿ����
   		* @param consignmentCount ���� (�ַ�������)
   		* @param boxToBottleUnits ���㵥λ
   		* @Return ƿ��
	    */
	    BoxToBottle : function(consignmentCount,boxToBottleUnits){        
	        //����Java��UnitsUtil��BoxToBottle����,callBackFunΪ�ص�����   
	        UnitsUtil.BoxToBottle(consignmentCount,boxToBottleUnits,{   
	                                     callback:function(value){_count = value;},
	                                     async:false
	                                 }
	        );
	     	return _count;
	    }
   	}
   	
   	//����
   	//alert('ƿת�����:'+UnitConvertTools.BoxToBottle('100.10',6));
   	//alert('��תƿ����:'+UnitConvertTools.BottleToBox(1000,6));
</script>
