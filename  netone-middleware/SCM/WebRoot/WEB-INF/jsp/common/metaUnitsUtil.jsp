<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String _path0 = request.getContextPath();
String _basePath0 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+_path0+"/";
%>
<!--
<script type="text/javascript" src="<%=_basePath0%>dwr/engine.js"></script>
<script type="text/javascript" src="<%=_basePath0%>dwr/interface/UnitsUtil.js"></script>
 DWR���߰� 
<script type="text/javascript" src="<%=_basePath0%>dwr/util.js"></script>
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
-->
<script type="text/javascript">
    	
   	//��λת��JS������ Author:DON 2010-12-7
   	var UnitConvertTools = window.UnitConvertTools || {};
   	UnitConvertTools = {
   		/*
   		* ƿת�����
   		* @param bottleCount ƿ�� (��ֵ����)
   		* @param boxToBottleUnits ���㵥λ
   		* @Return ����
   		*/
   		BottleToBox : function(bottleCount,boxToBottleUnits){
   			var _count = '0';
	   		if (bottleCount=='' || bottleCount == null) {
				return _count;
			}    
	 		var box = 0;
			var bottle = 0;
			if (parseInt(bottleCount) > 0) {// ����
				box = parseInt(bottleCount / boxToBottleUnits);
				bottle = parseInt(bottleCount % boxToBottleUnits);
				_count = box + "." + bottle;
				return _count;
			} else {// ����
				bottleCount = bottleCount * -1;
				box =  parseInt(bottleCount / boxToBottleUnits);
				bottle = parseInt(bottleCount % boxToBottleUnits);
				if ((("-" + box + "." + bottle) == '-0.0') || (("-" + box + "." + bottle) == "-0.00")) {
					return _count;
				}
				_count = "-" + box + "." + bottle;
				return _count;
			}
	
			return _count;
	    },
	    /*
	    * ��תƿ����
   		* @param consignmentCount ���� (�ַ�������)
   		* @param boxToBottleUnits ���㵥λ
   		* @Return ƿ��
	    */
	    BoxToBottle : function(consignmentCount,boxToBottleUnits){ 
	    	var _count = '0'; 
			var cBox;// ��
			var cBottle;// ƿ
			if (consignmentCount=='' || consignmentCount == null) {
				return _count;
			}
			var consignmentStr = consignmentCount.toString().trim();
			if (consignmentStr.indexOf('.') > 0){
				var consignmentCountStr = consignmentStr.split(".");
				cBox = parseInt(consignmentCountStr[0]);
				if (cBox < 0) {
					cBottle = -1 * parseInt(consignmentCountStr[1]);
				} else {
					cBottle = parseInt(consignmentCountStr[1]);
				}
				if (consignmentCountStr[0]=='-0') {
					_count = (cBox * boxToBottleUnits + cBottle) * -1;
				} else {
				    _count = cBox * boxToBottleUnits + cBottle;
				}
	
			} else {
				cBox = parseInt(consignmentStr);
				cBottle = 0;
				_count = cBox * boxToBottleUnits;
			}
			return _count;
	    }
   	}
   	
   	//����
   	//alert('ƿת�����:'+UnitConvertTools.BoxToBottle('1.20',12));
   	//alert('��תƿ����:'+UnitConvertTools.BottleToBox(-1200,12));
</script>
