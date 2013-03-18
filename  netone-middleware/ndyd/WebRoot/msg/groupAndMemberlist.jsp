<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-dialog.jsp"/>
        <jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp"/>
		<script language="javascript" type="text/javascript" src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<link type="text/css" href="<%=path%>/msg/msg.css" rel="stylesheet" />
		<script type="text/javascript">
		var arrPerson=new Array();
		/**
		* 从对象数组中删除属性为objPropery，值为objValue元素的对象
		* @param Array arrPerson 数组对象
		* @param String objPropery 对象的属性
		* @param String objPropery 对象的值
		* @return Array 过滤后数组
		*/
		function remove(arrPerson,objPropery,objValue)
		{
		   return $.grep(arrPerson, function(cur,i){
		          return cur[objPropery]!=objValue;
		       });
		}
		/**
		* 从对象数组中获取属性为objPropery，值为objValue元素的对象
		* @param Array arrPerson 数组对象
		* @param String objPropery 对象的属性
		* @param String objPropery 对象的值
		* @return Array 过滤后的数组
		*/
		function get(arrPerson,objPropery,objValue)
		{
		   return $.grep(arrPerson, function(cur,i){
		          return cur[objPropery]==objValue;
		       });
		}
		
		$(function() {
		    $("#memebertabs").tabs();
		    $('#memebertabs').find('input').each(function(){
		    	$(this).toggle(
				  function(){
				  	//$(this).attr('disabled',true);
				  	//alert('已添加');
				  	var person=new Object();
					person.name=$(this).attr('v');
				  	arrPerson.push(person);
				  	$(this).attr('value','取消添加');
				  },
				  function(){
				  	//$(this).attr('disabled',false);
				  	//alert('取消添加');
				  	arrPerson=remove(arrPerson,'name',$(this).attr('v'));
				  	$(this).attr('value','添加');
				  }
				);
		    });
		});
		
		function getSelectedMember(){
			$.each(arrPerson,function(key,val){  
			    //回调函数有两个参数,第一个是元素索引,第二个为当前值
			    alert('_mozi数组中 ,索引 : '+key+' 对应的值为: '+val.name);  
			}); 
		}
		</script>
		<title>群组</title>
	</head>
	<body>
		<input type="button" value="获取选择人员" onclick="getSelectedMember();" />
		<div id="memebertabs">
		  <ul>
		  <c:forEach var="list" items="${msglist}" varStatus="s">
			<li>
		    <li id="${list.groupid}"><a href="#tabs-${s.index}">${list.groupname}</a></li>
		  </c:forEach>  
		  </ul>
		  <c:forEach var="list" items="${msglist}" varStatus="s">
		  <div id="tabs-${s.index}">
		    <ul>
			<c:forEach var="list2" items="${list.people}">
				<li>${list2.id},${list2.name}<input type="button" value="添加" v="${list2.id}" /> </li>
			</c:forEach>
			</ul>
		  </div>
		  </c:forEach>
		</div>
	</body>
</html>
