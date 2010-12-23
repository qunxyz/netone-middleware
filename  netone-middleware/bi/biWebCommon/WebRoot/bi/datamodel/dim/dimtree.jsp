
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>dimtree.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<SCRIPT language="javascript">
	//div变化
	  
	  function  divChange(opr){
	  if(opr=="treetimeid"){
	    window.document.getElementById("treetimeid").style.display="block";
	    window.document.getElementById("treeid").style.display="none";
	    }else{
	     window.document.getElementById("treetimeid").style.display="none";
	    window.document.getElementById("treeid").style.display="block";
	    }
	  }
	  
	  
	  //时间树图提交	
	 function timetreeSubmit(){
	    var windowObj = opener.document.frames("elistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");  
  
     var treeModelObj = windowObj.document.getElementsByName("treeModel");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
    treeModelObj[i].value=window.document.forms[0].timetreedataset.value;
    window.close();
    return;
	}
   }
	  
	  }
	  
	    
	  //自定义树图提交	
 function treeSubmit(){
   var dataSet=window.document.getElementById("treedataset").value;
   var treeModel="["+dataSet+"]";
   var targetColumnId="";var treetargetColName="";var flag=0;
    for(var i=1;i<=10;i++){
     targetColumnId=window.document.getElementById("treetargetColId"+i).value;
     treetargetColName=window.document.getElementById("treetargetColName"+i).value;
     if(targetColumnId!=""&&treetargetColName!=""){
   
     flag++;
     treeModel+="["+dataSet+"."+targetColumnId+","+dataSet+"."+treetargetColName+"]";
     }
    }
    if(flag==0){
      alert("请选择一个指标");
      return;
    }
    var windowObj = opener.document.frames("elistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend"); 
  var treeModelObj = windowObj.document.getElementsByName("treeModel");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
    
    treeModelObj[i].value=treeModel;
    window.close();
    return;
	}
   }
   }
	</SCRIPT>
</head>

<body BGCOLOR=#EDF0F6 LEFTMARGIN=0 TOPMARGIN=10 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">
	<html:form action="dimtree.do">
		<table align="center">
			<tr>
				<td align="center">
				<SPAN style="cursor: hand " onclick="divChange('tree');">自定义钻取树</SPAN>
					<SPAN style="cursor: hand " onclick="divChange('treetimeid');">使用时间钻取树</SPAN> 
				</td>
			</tr>
			<tr>
				<td align="center">
					<div id="treetimeid" style="display:none">
					<TABLE>
					<tr>
					<td align="center">
						请选择时间树:
						<html:select property="timetreedataset">
							<html:options collection="timetreeList" labelProperty="label" property="value" />
						</html:select>
					</td></tr>
						<tr><td>
						<INPUT type="button" value="确定" onclick="timetreeSubmit();">&nbsp;&nbsp;&nbsp;
					<INPUT type="button" value="取消" onclick="javascript:window.close();">  
					</td></tr>
					</table>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center">
					<div id="treeid" style="display:block">
					<TABLE>
					<tr>
					<td align="center">	钻取表选择:</td>
					<td colspan="2">
						<html:select property="treedataset" onchange="javascript:document.forms[0].submit();">
							<html:options collection="treeDatasetList" labelProperty="label" property="value" />
						</html:select>
					<td>
					</tr>
					<% 
					String[] target=(String[])request.getAttribute("target");
					if(target!=null){
					%>
					  <tr>
					  <td align="center" > 1级 </td>  
					  <td  align="center">  
					  <select name="treetargetColId1"  id="treetargetColId1">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  <td  align="center">  
					  <select name="treetargetColName1"  id="treetargetColName1">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>					  
					   </tr>
					   <tr>
					   <td align="center" >2级</td>  
					 <td  align="center">  
					  <select name="treetargetColId2"  id="treetargetColId2">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  <td  align="center">  
					  <select name="treetargetColName2"  id="treetargetColName2">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					   </tr>
					   <tr>
					   <td align="center" >3级</td>  
					 <td  align="center">  
					  <select name="treetargetColId3"  id="treetargetColId3">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  <td  align="center">  
					  <select name="treetargetColName3"  id="treetargetColName3">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					   </tr>
					   <tr>
					   <td align="center" >4级</td>  
					 <td  align="center">  
					  <select name="treetargetColId4"  id="treetargetColId4">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  <td  align="center">  
					  <select name="treetargetColName4"  id="treetargetColName4">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					   </tr>
					   <tr>
					   <td align="center" >5级</td>  
					 <td  align="center">  
					  <select name="treetargetColId5"  id="treetargetColId5">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  <td  align="center">  
					  <select name="treetargetColName5"  id="treetargetColName5">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					   </tr>
					   <tr>
					   <td align="center" >6级</td>  
					   <td  align="center">  
					  <select name="treetargetColId6"  id="treetargetColId6">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  <td  align="center">  
					  <select name="treetargetColName6"  id="treetargetColName6">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					   </tr>
					  <tr>
					  <td align="center">7级 </td>  
					  <td  align="center">  
					  <select name="treetargetColId7"  id="treetargetColId7">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  <td  align="center">  
					  <select name="treetargetColName7"  id="treetargetColName7">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  </tr>
					  <tr align="center"> <td> 8级</td>  
					 <td  align="center">  
					  <select name="treetargetColId8"  id="treetargetColId8">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  <td  align="center">  
					  <select name="treetargetColName8"  id="treetargetColName8">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  </tr>
					  <tr>
					   <td align="center">  9级  </td>   
					 <td  align="center">  
					  <select name="treetargetColId9"  id="treetargetColId9">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  <td  align="center">  
					  <select name="treetargetColName9"  id="treetargetColName9">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  </tr>
					  <tr>
					   <td align="center"> 10级  </td>  
					 <td  align="center">  
					  <select name="treetargetColId10"  id="treetargetColId10">
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>	
					  <td  align="center">  
					  <select name="treetargetColName10" id="treetargetColName10" >
					  <OPTION value=""></OPTION>
							<%for(int i=0;i<target.length;i++){%>
							 <OPTION value="<%=target[i]%>"><%=target[i]%></OPTION>
							<%}%>
						</select>
					  </td>					 
					  </tr>
					  <tr>
					  <td align="center" colSpan="3">
					  <INPUT type="button" value="确定" onclick="treeSubmit();">&nbsp;&nbsp;&nbsp;
					<INPUT type="button" value="取消" onclick="javascript:window.close();">  </td>
					  </tr>
					  <%}%>
						</TABLE>
					</div>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
