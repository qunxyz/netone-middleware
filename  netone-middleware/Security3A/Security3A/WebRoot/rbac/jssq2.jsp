<%@ page language="java" pageEncoding="GBK" %>
<jsp:directive.page import="oe.security4a.web.rbac.AjaxTreeCheckItem"/>
<jsp:directive.page import="oe.security4a.web.rsinfo.resource.ResourceSelectAction"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String className = AjaxTreeCheckItem.class.getName();
new ResourceSelectAction().setApplications(request);
%>


	<script type="text/javascript">
		//当前状态
		var nowappid ;
		var label = "write";
		
		// appid=Map ; Map中为dn=权限信息，包含该app中所有的节点信息。
		var purviewMap = new Map(); 
		
		function changePurview(){
			saveChange();
			var radio = document.all.radio ;
			if(radio[0].checked){
				setWriteable();
				label = "write";
			}
			if(radio[1].checked){
				setHidden();
				label = "hidden";
			}
		}
		
		
		//获取当前app的purview
		function getpurview(){
			var appid = nowappid ;
			if(purviewMap.get(appid)){
				//alert("cache");
				return purviewMap.get(appid);
			}else{
				//alert("nocache");
				var resp = xmlhttp("servlet/AjaxServiceSvl?class=<%=className%>&appid="+appid+"&roleid=${paramMap.chkid}");
				var restr = resp.responseText;
				apppurview = new Map();
				apppurview.load(restr);
				purviewMap.put(appid,apppurview);
				return apppurview;
			}
		}
		
		//每个节点的权限信息都保存下来
		function saveChange(){
			var action ;
			var apppurview = getpurview();
			//alert(getpurview());
			if(label == "write"){
				var itemstr = tree.getAllSubItems(0);
				var items = itemstr.split(",");
				for(var i=0 ; i<items.length ; i++){
					if(tree.isItemChecked(items[i])==1){
						apppurview.put(items[i],"7");
					}else if(apppurview.get(items[i])!=1){
						apppurview.put(items[i],"3");
					}
				}
			}else if(label == "hidden"){
				var b = true ;
				while(b){
					var keyset = apppurview.keyarr;
					for(var i=0 ; i<keyset.length ; i++){
						if(i == (keyset.length-1)){
							b = false ;
						}
						if(apppurview.get(keyset[i])==1){
							apppurview.remove(keyset[i]);
							break ;
						}
					}
				}
				
				var itemstr = tree.getAllChecked();
				var items = itemstr.split(",");
				for(var i=0 ; i<items.length ; i++){
					if(tree.isItemChecked(items[i])){
						apppurview.put(items[i],"1");
					}
				}
			}
			//alert(getpurview());
			
		}
		
		
		//清除树图上选定的。
		function clearChecked(){
			var ckstr = tree.getAllChecked();
			if(ckstr){
				var cks = ckstr.split(",");
				for(var i=0 ; i<cks.length ; i++){
					tree.setCheck(cks[i],0);
				}
			}
		}
		
		
		//显示可操作的
		function setWriteable(){
			clearChecked();
			var apppurview = getpurview();
			//alert(nowappid +"#####"+apppurview);
			if(apppurview){
				var itemstr = tree.getAllSubItems(0);
				var items = itemstr.split(",");
				for(var i=0 ; i<items.length ; i++){
					tree.disableCheckbox(items[i],false);
					//alert(items[i]+"&&&&&&&&&"+apppurview.get(items[i]))
					if(apppurview.get(items[i])==7){
						//alert("setWriteable----"+items[i]);
						tree.setCheck(items[i],1);
					}
				}
			}
		}
		
		
		//显示隐藏的
		function setHidden(){	
			clearChecked();
			var apppurview = getpurview();
			var itemstr = tree.getAllSubItems(0);
			var items = itemstr.split(",");
			for(var i=0 ; i<items.length ; i++){
				var itempur = apppurview.get(items[i]);
				if(itempur==1){
					tree.setCheck(items[i],1);
				}else if(itempur == 7){
					tree.setCheck(items[i],0);
					tree.disableCheckbox(items[i],true);
				}
			}
		}
		
		
		//切换应用
		function changeapp(){
			saveChange();
			//alert(nowappid +"*****"+getpurview());
			nowappid = document.all.application.value ;
			tree.deleteChildItems(0);
			var url = "servlet/PurviewTreeXmlSvl?appid="+nowappid;
			tree.loadXML(url);	
			setTimeout("afterchangeapp()",100);
		}
		
		function afterchangeapp(){
			document.all.radio[0].checked = true ;
			label = "write";
			setWriteable();
		}
		
		
		
		
		//设置权限到hidden字段。
		function setPermissions(){
			saveChange();
			var ps = purviewMap.keyarr;
			var permissions = "";
			for(var i=0 ; i<ps.length ; i++){
				permissions += purviewMap.get(ps[i]).toString()+"@";
			}
			//alert(permissions);
			document.all.permissions.value = permissions ;
		}
		
		
	</script>	
	
	
    <input type="hidden" name="func" value="${paramMap.function}"/>
  	<input type="hidden" name="source" value="${paramMap.source}"/>
  	<input type="hidden" name="selectedNode" value=""/>
  	

  		<input type="hidden" name="permissions" value=''>
	  	<table style="width:100%;" border="0" >
	  		<tr>
	  			<td valign="top">
	  				<div>
	  					<input type="radio" name="radio" checked="checked" onclick="changePurview();">可操作
	  					<input type="radio" name="radio" onclick="changePurview();">隐藏
	  				</div>
	  				<br />
	  				<div>
	  					<select name="application" style="width: 130px;" onchange="changeapp();"  class="table_select_page">
	  						<c:forEach items="${applist}" var="app">
	  							<option value="${app.id}" >
	  							   ${app.name }  							
	  							</option>
	  						</c:forEach>
	  					</select>
	  				</div>
	  				<div id="treeboxbox_tree2" style="height:250"></div>
	  				<script type="text/javascript">		
	  					//document.all.application.value = 2;				
						var tree=new dhtmlXTreeObject("treeboxbox_tree2","100%","100%",0);
						tree.setImagePath("imgs/");
						tree.enableCheckBoxes(1);
						tree.enableThreeStateCheckboxes(true);
						var url = "servlet/PurviewTreeXmlSvl?appid="+document.all.application.value;
						tree.loadXML(url);	
						nowappid = document.all.application.value ;
						setWriteable();
						
					</script>
	  			</td>
	  		</tr>
	  	</table>
	  	

	  
	  		
	  	
	  	