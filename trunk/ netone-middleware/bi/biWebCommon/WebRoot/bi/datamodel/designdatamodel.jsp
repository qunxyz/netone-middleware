
<%@ page contentType="text/html; charset=GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<%String path = request.getContextPath();

			%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>
		数据仓库设计
	</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<SCRIPT type="text/javascript">
	
	function datamodelSubmit(opr){
	  var modelname=document.getElementById("modelname").value;
	  var datasets=document.getElementById("datasets").value;
	   var linkers=document.getElementById("linkers").value;
	   var targetColumns=document.getElementById("targetColumns").value;
	   var dimColumns=document.getElementById("dimColumns").value;
	   var optimizes=document.getElementById("optimizes").value;
	   if(modelname.trim()==""||datasets.trim()==""||targetColumns.trim()==""||dimColumns.trim()==""){
	     alert("信息填写不完整");
	     return;
	   }
 var modelid=document.getElementById("modelid").value;
	 var datamodelXmlStr='<?xml version="1.0" encoding="GB2312"?>\n\n';
	 datamodelXmlStr+='<DataModels>\n\n<DataModel modelid="'+modelid+'" modelname="'+modelname+'" extendattribute="" description="">\n\n';
	  datamodelXmlStr+='<DataSets>\n\n'+datasets+'</DataSets>\n\n<Linkers>'
						  +linkers+'</Linkers>\n\n<TargetColumns>\n\n'
						  +targetColumns+'</TargetColumns>\n\n<DimColumns>\n\n'
						  +dimColumns+'</DimColumns>\n\n<Optimizes>\n\n'
						  +optimizes+'</Optimizes>\n\n</DataModel>\n\n</DataModels>';

	document.getElementById("datamodelXmlStr").value=datamodelXmlStr;
	if(opr=="new"){
	document.forms[0].action="<%=path%>/design_Newdatamodel.do?flag=new"; //新增
	}else{
	document.forms[0].action="<%=path%>/design_Updatedatamodel.do?flag=update"; //更新
	}
	
	document.forms[0].submit();
	}
	/*-正则表达式-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}

function initDataCopy(){
document.getElementById("dataCopy").value=document.getElementById("tablename").value+"."+document.getElementById("columnfield").value;

}

function changeTable(opr){
  if(opr=="update"){
    window.document.forms[0].action="<%=path%>/design_Updatedatamodel.do?flag=changeTable";
     window.document.forms[0].submit();
  }else{
   window.document.forms[0].action="<%=path%>/design_Newdatamodel.do?flag=changeTable";
     window.document.forms[0].submit();  
  }


}
	</SCRIPT>
</head>
<%String booleanNewDataModel = String.valueOf(request
					.getAttribute("booleanNewDataModel"));
			if ("true".equals(booleanNewDataModel)) {
%>
<SCRIPT type="text/javascript">
 alert("保存成功");
 window.close();
</SCRIPT>
<%} else if ("false".equals(booleanNewDataModel)) {%>
<SCRIPT type="text/javascript">
 alert("保存失败");
</SCRIPT>
<%}%>
<body onload="initDataCopy();">
	<html:form action="design_Newdatamodel.do">
		<TABLE width="800">
			<tr>
				<td>
					<font>
						<strong>
							[ 模型名称 ]
						</strong>
					</font>
				</td>
			</tr>
			<tr>
				<td>
					<html:text property="modelname" size="50" />
				</td>
			</tr>

			<tr>
				<td>
					<font>
						<strong>
							[ 创建基础数据源 ]
						</strong>
					</font>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<html:textarea property="datasets" cols="300" rows="3" />
				</td>
			</tr>
			<tr>
				<td>
					<font>
						<strong>
							[ 创建基础数据源关联 ]
						</strong>
					</font>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<html:textarea property="linkers" cols="300" rows="2" />
				</td>
			</tr>
			<tr>
				<td>
					<font>
						<strong>
							[ 创建指标 ]
						</strong>
					</font>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<html:textarea property="targetColumns" cols="300" rows="3" />
				</td>
			</tr>
			<tr>

				<td>
					<font>
						<strong>
							[ 创建维度 ]
						</strong>
					</font>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<html:textarea property="dimColumns" cols="300" rows="6" />
				</td>
			</tr>
			<tr>
				<td>
					<font>
						<strong>
							[ 优化设计 ]
						</strong>
					</font>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<html:textarea property="optimizes" cols="300" rows="6" />
				</td>
			</tr>
			<tr>
				<td colspan="3" >
					<html:hidden property="datamodelXmlStr" />
					<html:hidden property="modelId" />
					<%if ("update".equals(request.getAttribute("editDatamodelFlag"))) {%>
					<input type="button" onclick="datamodelSubmit('update');" value="更新" />
					<%} else {%>
					<input type="button" onclick="datamodelSubmit('new');" value="新增" />
					<%}%>
					<INPUT type="button" value="取消" onclick="javascript:window.close();">
					&nbsp;&nbsp;&nbsp;
					<font size=2>辅助编辑工具</font>
					<%if ("update".equals(request.getAttribute("editDatamodelFlag"))) {%>
					<html:select property="tablename" onchange="changeTable('update')">
						<html:options collection="tablenameList" labelProperty="label" property="value" />
					</html:select>
					<%} else {%>
					<html:select property="tablename" onchange="changeTable('')">
						<html:options collection="tablenameList" labelProperty="label" property="value" />
					</html:select>
					<%}%> 
					<font size=2>选择字段</font>
					<html:select property="columnfield" onchange="initDataCopy();">
						<html:options collection="fieldList" labelProperty="label" property="value" />
					</html:select>
					<font size=2>拷贝信息</font>
					<input type="text" name="dataCopy" id="dataCopy" size="30"/>
				</td>

			</tr>
		</TABLE>
	</html:form>
</body>
</html:html>

