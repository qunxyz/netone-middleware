
<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>指标过滤条件选择框</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <link rel="stylesheet" type="text/css" href="<%=path%>/include/css/css.css">
	<style type="text/css">
	 BODY {
	        BORDER-TOP-WIDTH: 4px; 
			SCROLLBAR-FACE-COLOR: #EDF0F6; 
			BORDER-LEFT-WIDTH: 4px; 
			FONT-SIZE: 9pt; 
			BORDER-BOTTOM-WIDTH: 4px; 
			SCROLLBAR-HIGHLIGHT-COLOR: #ffffff; 
			SCROLLBAR-SHADOW-COLOR: #B3CDEA; 
			SCROLLBAR-3DLIGHT-COLOR: #B3CDEA; 
			SCROLLBAR-ARROW-COLOR: #B0C7E1; 
			SCROLLBAR-TRACK-COLOR: #F7FBFF; 
			SCROLLBAR-DARKSHADOW-COLOR: #ffffff; 
			SCROLLBAR-BASE-COLOR: #EEF7FF; 
			BORDER-RIGHT-WIDTH: 4px;
	}
	</style>
    <script type="text/javascript">
    
        function el(i) {
  			return document.getElementById(i);
		}
    	
    	
    	function isDigit(s)
		{
			var patrn=/^[0-9]{1,20}$/;
			
			if (!patrn.exec(s)) return false
			return true
		}
		
		function isFloat(s)
		{
			var patrn=/^(-\+)?([0-9]+)\.?([0-9]*)$/;
			if (!patrn.exec(s)) return false
			return true
		}
    	
    
    	function setdiv(){
    		var par = self.opener.parent;
    		var pardoc = par.document;
    		var divhtml = pardoc.getElementById("filtertgdiv").innerHTML;
    		var div = el("filtertgdiv");
    		div.innerHTML = divhtml;
    		
    		//获取选择的指标
    		var cbtgs = pardoc.getElementsByName("cbtg");
			var hasch = false;
			
			var tgsel = document.all.fil_tgid;
			
    		for(i=0 ;i<cbtgs.length ; i++){
    			var tgid = cbtgs[i].id;
    			
    			for(j=0 ; j<tgsel.length ; j++){
    				if(tgsel.options[j].value==tgid)
    				{
    					if(cbtgs[i].checked){
		    				hasch = true ;
		    			}
		    			else{
		    				tgsel.remove(j);
		    			}
		    			break ;
    				}	
    			}
    		}
    		
			if(hasch){
				el("fil_set").style.display = "block";
    			el("fil_none").style.display = "none";
    		}
    		else{
    			el("fil_set").style.display = "none";
    			el("fil_none").style.display = "block";
    		}
    	}
    	
    	
    	function check(){
    		
    		var tgid = document.all.fil_tgid.value;
    		if(tgid == -1){
    			alert("请选择一个指标！")
    			return false ;
    		}
    		
    		var topv = document.all.topvalue;
    		if(document.all.filtcbxtop.checked){
    			if(!isDigit(topv.value)){
    				alert("最值必须为数字！");
    				return false;
    			}
    		}
    		
    		var alarmv = document.all.txtalarm;
    		if(document.all.filtcbxalarm.checked){
    			if(!isFloat(alarmv.value)){
    				alert("门限值必须为数字！");
    				return false;
    			}
    		}
    		
    		return true ;
    	}
    	    	
    	//指标过滤的事件
    	function filterTg(){
    		
    		if(!check()){
    			return ;
    		}
			var sqlStr="";//构造出SQL查询条件语句
			var tgid_sql =document.all.fil_tgid.value.split(":")[0];
			var tgid_sql_Group =document.all.fil_tgid.value.split(":")[1];
			var top_sql=document.all.top.value;
			
			
			
    		var tgid = document.all.fil_tgid.value+"," ;
    		var top =  document.all.top.value+"=,";
    		if(document.all.filtcbxtop.checked){  //最值过滤
	    		top = document.all.top.value+"="+document.all.topvalue.value+"," ;
	    		
	    		//self.opener.dotgfilter(tgid_sql+":"+document.all.topvalue.value);//最值过滤的top值和字段,用冒号隔开
	    		
	    		
	    		if("topn"==top_sql)
	    		sqlStr=" order by "+tgid_sql+" DESC:"+tgid_sql_Group;
	    		else
	    		 sqlStr=" order by "+tgid_sql+" ASC:"+tgid_sql_Group;
    		}
    		var order = "order=,";
    		if(document.all.filtcbxorder.checked){  //排序
    			order = "order="+document.all.order.value + "," ;
    			
    			sqlStr=" order by "+tgid_sql+" "+document.all.order.value+":"+tgid_sql_Group;
    		}
    		var alarm = document.all.alarm.value + "=,";
    		if(document.all.filtcbxalarm.checked){  //门限
    			alarm = document.all.alarm.value + "=" +document.all.txtalarm.value +",";
    			if("alarmup"==document.all.alarm.value)
    			sqlStr=" and "+tgid_sql+">="+document.all.txtalarm.value+":"+tgid_sql_Group;
    			else
    			sqlStr=" and "+tgid_sql+"<="+document.all.txtalarm.value+":"+tgid_sql_Group;
    		}
    		
    		
    		var restr = tgid+top+alarm+order;
    		
    		
    		 
    		

    		if(restr){
    			//self.opener.dotgfilter(restr);
    			self.opener.dotgfilter(document.all.topvalue.value);
				self.opener.document.all.tgfiltSqlvalue.value = sqlStr;
    			window.close();
    		}
    		
    	}
    	
    	function clickcbxtop(cbxtop){
    		if(cbxtop.checked){
    			document.all.filtcbxalarm.checked=false;
    			document.all.filtcbxorder.checked=false;
    			cbxtop.nextSibling.nextSibling.disabled=false;
    		}
    		else{
    			cbxtop.nextSibling.nextSibling.disabled=true;
    		}
    		changetop(cbxtop.nextSibling.nextSibling);


    	}
    	
    	
    	function clickcbxalarm(cbxalarm){
    		if(cbxalarm.checked){
    			document.all.filtcbxtop.checked=false;
    			document.all.filtcbxorder.checked=false;
    			cbxalarm.nextSibling.nextSibling.disabled=false;
    		}
    		else{
    			cbxalarm.nextSibling.nextSibling.disabled=true;
    		}
    		
    		changealarm(cbxalarm.nextSibling.nextSibling);
    		

    	}
    	
    	
    	function clickcbxorder(cbxorder){
    		if(cbxorder.checked){
    			document.all.filtcbxtop.checked=false;
    			document.all.filtcbxalarm.checked=false;
    			cbxorder.nextSibling.nextSibling.disabled=false;
    		}
    		else{
    			cbxorder.nextSibling.nextSibling.disabled=true;
    		}
    		
    	}
    	
    	
    	function changetop(seltop){
    		if(seltop.value==-1 || seltop.disabled){
    			seltop.nextSibling.nextSibling.disabled=true;
    			seltop.nextSibling.nextSibling.nextSibling.firstChild.nextSibling.disabled=true;
    		}
    		else{
    			seltop.nextSibling.nextSibling.disabled=false;
    			seltop.nextSibling.nextSibling.nextSibling.firstChild.nextSibling.disabled=false;
    		}
    	}
    	
    	
    	function changealarm(selalarm){    		
    		if(selalarm.value==-1 || selalarm.disabled){
    			selalarm.nextSibling.nextSibling.disabled=true;
    		}
    		else{
    			selalarm.nextSibling.nextSibling.disabled=false;
    		}
    	}
    	
    	
    	
    	function changetgid(seltgid){
    		if(seltgid.value==-1){
    			document.all.filtcbxtop.disabled=true;
    			document.all.filtcbxalarm.disabled=true;
    			document.all.filtcbxorder.disabled=true;
    		}
    		else{
    			document.all.filtcbxtop.disabled=false;
    			document.all.filtcbxalarm.disabled=false;
    			document.all.filtcbxorder.disabled=false;
    		}
    		
    		setcbxinit();
    	}
    	
    	
    	
    	
    	
    	function setcbxinit(){
    		clickcbxtop(document.all.filtcbxtop);
    		clickcbxalarm(document.all.filtcbxalarm);
    		clickcbxorder(document.all.filtcbxorder);
    	}
    	
    	
    	function setinit(){
    		changetgid(document.all.fil_tgid);
    	}
    	
    	
    	
    </script>
    
    
    
  </head>
  
  <body BGCOLOR=#EDF0F6>
    请选择过滤条件：
    <DIV id="filtertgdiv" align="center" >
		<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#A9C0E5" align="center">
			
			<tr id="fil_set" class="td-02">
				<td bgcolor="#FFFFFF" valign="top">
					<select name="fil_tgid" onchange="changetgid(this);">
						<option value="-1" >请选择</option>
						<c:forEach items="${tgidlist}" var="tg" varStatus="vs" >
							<option value="${tg}">${tgnamelist[vs.index]}</option>
						</c:forEach>
					</select>
				</td> 
				<td bgcolor="#FFFFFF" align="left">
					<input type="checkbox" name="filtcbxtop"  onclick="clickcbxtop(this);" >
					最值过滤：
					<select name="top" onchange="changetop(this)">
						
						<c:forEach items="${_CHOICE_TOP}" var="ct">
							<option value="${ct[0]}">${ct[1]}</option>
						</c:forEach>
					</select>
					
					<input type="text" name="topvalue" style="width:100px;height:21px;font-size:10pt;"><span style="width:18px;border:0px solid red;">
					   <select name="r00" style="margin-left:-100px;width:118px; " onChange="this.parentNode.previousSibling.value=this.value;"> 
		                	<c:forEach items="${_CHOICE_TOP_VALUE}" var="ctv">
		                		<option value="${ctv[0]}">${ctv[1]}</option>
		                	</c:forEach>
		               </select> 
		            </span><br><br>
					
					 
					<input type="checkbox" name="filtcbxalarm" onclick="clickcbxalarm(this);">

					门限值：
					<select name="alarm" onchange="changealarm(this)">
						
						<c:forEach items="${_CHOICE_ALARM}" var="ca">
							<option value="${ca[0]}">${ca[1]}</option>
						</c:forEach>
					</select>
					
					<input type="text" name="txtalarm" style="width: 70px"><br><br>

					
					<input type="checkbox" name="filtcbxorder" onclick="clickcbxorder(this);">
					排序：
					<SELECT name="order">
						<c:forEach items="${_CHOICE_ORDER}" var="co">
							<option value="${co[0]}">${co[1]}</option>
						</c:forEach>
					</SELECT>

					
				</td>
			</tr>
		
			
			
			<tr id="fil_none" style="display:none" class="td-02">
				<td colspan="2" align="center" bgcolor="#FFFFFF">
					没有选择指标！
				</td>
			</tr>
			
			<tr class="td-02">
				<td colspan="2" align="right" bgcolor="#FFFFFF">
					<input type="button" value="确定" onclick="filterTg();" class="butt">
					&nbsp;
					<input type="button" value="关闭" onclick="window.close();" class="butt">
				</td>
			</tr>
		</TABLE>
	</DIV>
	
	<script type="text/javascript">
		//setdiv();
		setinit();
	</script>
    
  </body>
</html>
