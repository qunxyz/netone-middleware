<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String _path0 = request.getContextPath();
String _basePath0 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+_path0+"/";
%>
<script type="text/javascript" src="<%=_basePath0%>dwr/engine.js"></script>
<script type="text/javascript" src="<%=_basePath0%>dwr/interface/UnitsUtil.js"></script>
<!-- DWR工具包 
<script type="text/javascript" src="<%=_basePath0%>dwr/util.js"></script>
-->
<script type="text/javascript">
    	
   	//单位转换JS工具类 Author:DON 2010-3-2
   	var UnitConvertTools = window.UnitConvertTools || {};
   	UnitConvertTools = {
   		_count : '0',//箱/瓶数
   		/*
   		* 瓶转箱计算
   		* @param bottleCount 瓶数 (数值类型)
   		* @param boxToBottleUnits 换算单位
   		* @Return 箱数
   		*/
   		BottleToBox : function(bottleCount,boxToBottleUnits){        
	        //调用Java类UnitsUtil的BottleToBox方法,callBackFun为回调函数
	        UnitsUtil.BottleToBox(bottleCount,boxToBottleUnits,{   
	                                     callback:function(value){_count = value;},
	                                     async:false
	                                 }
	        );
	     	return _count;
	    },
	    /*
	    * 箱转瓶计算
   		* @param consignmentCount 箱数 (字符串类型)
   		* @param boxToBottleUnits 换算单位
   		* @Return 瓶数
	    */
	    BoxToBottle : function(consignmentCount,boxToBottleUnits){        
	        //调用Java类UnitsUtil的BoxToBottle方法,callBackFun为回调函数   
	        UnitsUtil.BoxToBottle(consignmentCount,boxToBottleUnits,{   
	                                     callback:function(value){_count = value;},
	                                     async:false
	                                 }
	        );
	     	return _count;
	    }
   	}
   	
   	//测试
   	//alert('瓶转箱计算:'+UnitConvertTools.BoxToBottle('100.10',6));
   	//alert('箱转瓶计算:'+UnitConvertTools.BottleToBox(1000,6));
</script>
