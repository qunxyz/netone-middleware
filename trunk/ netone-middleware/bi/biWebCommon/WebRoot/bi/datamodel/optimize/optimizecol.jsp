<%@ page contentType="text/html; charset=GBK" import="java.util.*"%>

<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'targetcol.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<link href="<%=path%>/include/css/style.css" rel="stylesheet" type="text/css">

		<script type="text/javascript">
			
			function getDataSet(){//遍历父窗口的所有的节点对象 
			var actionObj  = window.opener.document.getElementsByName("dataNode");
              var dslength = actionObj.length;
              var str="";
                for(var i=0;i<dslength;i++){
						    
						    var dataSetNode = actionObj[i];
						    
						   str+=dataSetNode.nodeID+",";
							
							
						}
				window.document.getElementById("dataSet").value=str;
			}
			
           function openDimEdit(opr){
           
          var  str=window.document.getElementById("dataSet").value;  //得到所有的数据集对象
   
            if(opr=="new"){
		    window.open('<%=path%>/optimize.do?flag=new&&datasetAll='+str);
		    }else {
		    
		     var windowObj = document.frames("elistFrame");
             var checkObj = windowObj.document.getElementsByName("checkExtend");
             var optimizeidObj = windowObj.document.getElementsByName("optimizeid");
             var length = checkObj.length;
             var dataset="";
					var flag = 0;
				  for(var i=0;i<length;i++){
				    if(checkObj[i].checked){
					   flag++;
					   dataset=optimizeidObj[i].value;
					}
				  }
				  if(flag==1){
				  if(opr=="edit")
				    window.open('<%=path%>/optimize.do?flag=edit&&datasetAll='+str+'&&dataset='+dataset);				     
				    else if(opr=="targetReflect"){
				     var optimizeTarget=window.opener.document.getElementById("optimizeTarget").value;
				     window.open('<%=path%>/optimizeReflect.do?flag=targetReflect&&reflet='+optimizeTarget+'&&dataset='+dataset);				     
				     }
				   else if(opr=="dimReflect"){
				     var optimizeDim=windowObj.document.getElementById("optimizeDim");
				     window.open('<%=path%>/optimizeReflect.do?flag=dimReflect&&reflet='+optimizeDim+'&&dataset='+dataset);				     
				     }
				  return;
				  }else if(flag==0){
				     alert("请选择一个维度！");
					 return;
				  }else{
				      alert("你只能选种一个编辑对象！");
					  return;
				  }
				 }
           }		
           
           
  function submitTarget(){
      var isCheck=booleanCheck();
      if(isCheck=="false"){
        alert("请创建至少一个维度");
        return;
      }
      var  dimColumn="<DimColumns>\n\n";
   var windowObj = document.frames("elistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  var dimid = windowObj.document.getElementsByName("dimid");
   var dimname = windowObj.document.getElementsByName("dimname");
    var sqltype = windowObj.document.getElementsByName("sqltype");
    var treeModel=windowObj.document.getElementsByName("treeModel");
     var desc = windowObj.document.getElementsByName("desc");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
     dimColumn+='<DimColumn id="'+dimid[i].value+'" name="'+dimname[i].value+'" sqltype="'+sqltype[i].value+'" treeModel="'+treeModel[i].value+'" extendattribute="" description="'+desc[i].value+'" />\n\n';
   }  
   dimColumn+="</DimColumns>\n\n";
   alert(dimColumn);
   window.opener.document.getElementById("dimColumn").value=dimColumn;
   window.close();
                  
}
         
         function booleanCheck(){
          var windowObj = document.frames("elistFrame");
          var flag="false";
  var listObj = windowObj.document.getElementsByName("checkExtend");
  var listLength = listObj.length;
   if(listLength>0) flag="true"    ;
   return flag;     
    }
			
			
//删除			
 function  deleteTarget(){

  var  checkObj = document.frames("elistFrame").document.getElementsByName("checkExtend");
  var length = checkObj.length;
  var parentObj;
  var index=0;
  for(var i=0;i<length;i++){
   if(checkObj[i].checked){
     index++;
    }
  }
  if(length==0){
    alert("你的维度为空，无法删除！");
	return ;
  }else if (index==0)
  { 
     alert("你至少要选种一个删除对象！");
	 return ;
  }
  if (confirm("您确定要执行此操作!!!")) {
  for(var i=0;i<length;i++){
   if(checkObj[i].checked){
    parentObj = (checkObj[i]).parentElement.parentElement;
	parentObj.removeChild(checkObj[i].parentElement);
	checkObj = document.frames("elistFrame").document.getElementsByName("checkExtend");
	length--;
	i=-1;
	}
  }
 }
}

		</script>
	</head>

	<body onload="getDataSet();">
		
		<table width="280" border="0" align="center" cellpadding="0" cellspacing="0">
			<br>
			<BR>
		<div align="center"><H4>
			维度优化设计
		</H4>
		</div>
			<tr>
				<td height="135" colspan="2" align="center">
					<table width="280" border="0" align="center" cellpadding="0" cellspacing="0">
						<br>
						<tr>
							<td height="135" colspan="2" align="center">
								<table width="100%" height="86%" border="1" cellpadding=0 cellspacing=0>
									<tr>
										<td width="266" height="133" valign="top">
											<table width="401" border="1" cellpadding="0" cellspacing="0" bordercolor="#999999" bgcolor="#666666">
												<tr>
													<td>
														选择
													</td>
													<td>
														归并名称

													</td>
													<td>
														归并ID
													</td>
													<td>
														归并类型
													</td>
													<td>
														归并描述
													</td>
												</tr>
											</table>
											<IFRAME frameBorder=0 id=eAttributeList name=elistFrame src="bi/datamodel/target/targetList.jsp" style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1"></IFRAME>
										</td>
										<td width="58" align="center" valign="top">
											<input type="button" value="创建" onclick="openDimEdit('new');" />
											<input type="button" value="修改" onclick="openDimEdit('edit');" />
											<input type="button" value="删除" onclick="deleteTarget();" />
											<input type="button" value="定义指标映射" onclick="openDimEdit('targetReflect')" />
											<input type="button" value="定义维度映射" onclick="openDimEdit('dimReflect')" />
										</td>
									</tr>
								</table>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<div align="center">
					<INPUT type="hidden" name="dataSet" id="dataSet">
						<input type="button" name="Submit" value="提交" onClick="submitTarget();">
						&nbsp;
						<input type="button" name="Submit" value="关闭" onClick="self.close();">
					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
