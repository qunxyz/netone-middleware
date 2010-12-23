
<%@ page contentType="text/html; charset=GBK" import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>


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
    		function event(){
}


/*--  提交--*/
event.prototype.submit =function(){
 var datasetsel = window.document.getElementById("datasetsel").value;
 var target = window.document.getElementById("targetsel").value;
   var dimname = window.document.getElementById("dimname").value;
    var sqltype = window.document.getElementById("sqltype").value;
     var desc = window.document.getElementById("desc").value;
  if(datasetsel.trim()==""||target.trim()==""||dimname.trim()==""||desc.trim()==""){
    alert("请填写完整信息");
	return ;
  }else{
     newExtendObj(dimname,datasetsel+"."+target,sqltype,desc);
	 self.close();
	 return ;
  }
}
/*----新建一个扩展属性对象----*/
function newExtendObj(dimname,dimid,sqltype,desc){
var windowObj = opener.document.frames("elistFrame");
 
var divObj =windowObj.document.createElement("div");
var trObj=  "<div id='extendLineList'><tr><td><input name='checkExtend' type='checkbox'/></td>"
            +"<td align='center'><input   type='text' size=6 name='dimname' value='"+dimname+"' readOnly/></td>"
            +"<td align='center'><input   type='text' size=6  name='dimid' value='"+dimid+"' readOnly/></td>"
            +"<td align='center'><input type='text' size=6  name='sqltype' value='"+sqltype+"' readOnly/></td>"   
            +"<td align='center'><input  type='text'  size=6 name='desc' value='"+desc+"' readOnly/></td>"
            +"<td align='center'><input  type='hidden'  name='treeModel'/></td>"
            +"</tr></div>";
	divObj.innerHTML = trObj;
windowObj.document.body.appendChild(divObj);
}
/*-正则表达式-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}
    	// 数据集下拉事件
  function  datasetChange(){
  var  str=window.opener.document.getElementById("dataSet").value;
    document.forms[0].action='dimedit.do?changeflag=change&&flag=new&&datasetAll='+str;
  document.forms[0].submit();
  }	

  
    	</script>
	</head>

	<body onload="targetChange();">
	<html:form action="dimedit.do">
		<CENTER>
			<H4>
				维度创建
			</H4>
		</CENTER>
		<CENTER>

			<table width="625" border="1" cellpadding="0"  cellspacing="0">
				<tr>
					<td>
						选择数据集(<font color="red">*</font>)
					</td>
					<td>					
						<html:select property="datasetsel" onchange="datasetChange();">
						<html:options collection="dataSetList" labelProperty="label" property="value"/>
						 
						</html:select>
					</td>
					<td>
						选择指标
					</td>
					<td>
					<html:select property="targetsel">
					<html:options collection="targetList" labelProperty="label" property="value"/>					
					</html:select>
					</td>
				</tr>

				<tr>
					<td>
						维度名称(<font color="red">*</font>)
					</td>
					<td colspan="4">
					<html:text property="dimname"></html:text>					
					</td>
				</tr>
				<tr>
					<td>
						维度类型(<font color="red">*</font>)
					</td>
					<td>					
						<html:select property="sqltype" >
						<html:options collection="sqlTypeList" labelProperty="label" property="value"/>						 
						</html:select>
					</td>
				</tr>
				<tr>
					<td>
						维度说明(<font color="red">*</font>)
					</td>
					<td colspan="4">
							<html:textarea property="desc" rows="5" cols="30"></html:textarea>
					</td>
				</tr>
			</table>
			<br>
			<input type="button" value="确定" onclick="(new event()).submit();" />
			<input type="button" value="关闭" onclick="window.close();" />

		</CENTER>
		</html:form>
	</body>
</html>
