
<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'targetedit.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">

		<!--
   		 <link rel="stylesheet" type="text/css" href="styles.css">
   		-->

		<script type="text/javascript" src="<%=path%>/include/js/datamodelobj.js"></script>

         
		<script type="text/javascript">

/*--  提交--*/
function   submitTarget(){
 var datasetsel = window.document.getElementById("datasetsel").value;
 var target = window.document.getElementById("target").value;
   var tgname = window.document.getElementById("tgname").value;
    var alarm = window.document.getElementById("alarm").value;
     var desc = window.document.getElementById("desc").value;
  if(datasetsel.trim()==""||target.trim()==""||tgname.trim()==""){
    alert("名字：值必须被定义，定义它或按取消！");
	return ;
  }else{
     updateExtendObj(datasetsel,target,tgname,alarm,desc);
	 self.close();
	 return ;
  }
}
/*----更新属性对象----*/
function updateExtendObj(datasetsel,target,tgname,alarm,desc){

 var windowObj = opener.document.frames("elistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  
   var datasetselOpr = windowObj.document.getElementsByName("datasetsel");
 var targetOpr = windowObj.document.getElementsByName("target");
   var tgnameOpr = windowObj.document.getElementsByName("tgname");
    var alarmOpr = windowObj.document.getElementsByName("alarm");
     var descOpr = windowObj.document.getElementsByName("desc");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
	 datasetselOpr[i].value=datasetsel;
  targetOpr[i].value=target;
   tgnameOpr[i].value=tgname;
  alarmOpr[i].value = alarm;
    descOpr[i].value=desc;
	}
   }
}
	
    		
    		

/*--初始化--*/
 function  initTarget(opr){
if(opr!="change"){
  var windowObj = opener.document.frames("elistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  
   var datasetsel = windowObj.document.getElementsByName("datasetsel");
 var target = windowObj.document.getElementsByName("target");
   var tgname = windowObj.document.getElementsByName("tgname");
    var alarm = windowObj.document.getElementsByName("alarm");
     var desc = windowObj.document.getElementsByName("desc");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
	 window.document.getElementById("datasetsel").value=datasetsel[i].value;
  window.document.getElementById("target").value=target[i].value;
   window.document.getElementById("tgname").value=tgname[i].value;
    window.document.getElementById("alarm").value=alarm[i].value;
    window.document.getElementById("desc").value=desc[i].value;
	}
	}
	}
  window.document.forms[0].targetCopy.value=window.document.forms[0].targetsel.value; 
}

/*-正则表达式-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}

// 数据集下拉事件
  function  datasetChange(){
   var  str=window.opener.document.getElementById("dataSet").value;
    document.forms[0].action='targetedit.do?changeflag=change&&flag=edit&&datasetAll='+str;
  document.forms[0].submit();
  }
  
   function targetChange(){
    window.document.forms[0].targetCopy.value=window.document.forms[0].datasetsel.value+"."+window.document.forms[0].targetsel.value; 
  
  }
    	</script>
	</head>

	<body onLoad="initTarget('<%=(String)request.getAttribute("change")%>');">
	<html:form action="targetedit.do">
		<CENTER>
			<H4>
				指标编辑
			</H4>
		</CENTER>
		<CENTER>
			<input type="hidden" name="modtype" value="<%=request.getParameter("modtype")%>">
			<table width="625" border="1" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						选择数据集(<font color="red">*</font>)
					</td>
					<td>					
						<html:select property="datasetsel" onchange="datasetChange();">
						<logic:present name="dataSetList">
						<html:options collection="dataSetList" labelProperty="label" property="value"/>
						 </logic:present>
						</html:select>
					</td>
					<td>
						选择指标
					</td>
					<td>
					<html:select property="targetsel" onchange="targetChange();">
					<logic:present name="targetList">
					<html:options collection="targetList" labelProperty="label" property="value"/>					
					 </logic:present>
					</html:select>
					</td>
					<td>
					
						<input type="text" id="targetCopy" size="25"  readonly="readonly">
					</td>
				</tr>
				<tr>
					<td>
						编辑指标(<font color="red">*</font>)
					</td>
					<td colspan="4">
					<html:text property="target" size="50"></html:text>	
						
					</td>
				</tr>
				<tr>
					<td>
						指标名称(<font color="red">*</font>)
					</td>
					<td colspan="4">
					<html:text property="tgname"></html:text>					
					</td>
				</tr>
				<tr>
					<td>
						告警
					</td>
					<td colspan="4">
					<html:textarea property="alarm" ></html:textarea>
					</td>
				</tr>
				<tr>
					<td>
						指标说明(<font color="red">*</font>)
					</td>
					<td colspan="4">
							<html:textarea property="desc" rows="5" cols="30"></html:textarea>
					</td>
				</tr>
			</table>
			<br>
			<input type="button" value="确定" onclick="submitTarget();" />
			<input type="button" value="关闭" onclick="window.close();" />

		</CENTER>
		</html:form>
	</body>
</html>
