<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'dwrSample.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- DWR框架 -->
	<script type="text/javascript" src="dwr/engine.js"></script>
	<script type="text/javascript" src="dwr/interface/UnitsUtil.js"></script>
	<!-- DWR工具包 
	<script type="text/javascript" src="dwr/util.js"></script>
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
    	alert('瓶转箱计算:'+UnitConvertTools.BoxToBottle('1000.10',6));
    	alert('箱转瓶计算:'+UnitConvertTools.BottleToBox(1205,6));
    </script>
  </head>
  
  <body>
  </body>
</html>
