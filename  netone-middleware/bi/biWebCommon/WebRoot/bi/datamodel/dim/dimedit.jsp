
<%@ page contentType="text/html; charset=GBK"%>
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

/*--  �ύ--*/
function   submitDim(){
 var datasetsel = window.document.getElementById("datasetsel").value;
 var target = window.document.getElementById("targetsel").value;
   var dimname = window.document.getElementById("dimname").value;
    var sqltype = window.document.getElementById("sqltype").value;
     var desc = window.document.getElementById("desc").value;
  if(datasetsel.trim()==""||target.trim()==""||dimname.trim()==""||desc.trim()==""){
    alert("���֣�ֵ���뱻���壬��������ȡ����");
	return ;
  }else{
     updateExtendObj(dimname,datasetsel+"."+target,sqltype,desc);
	 self.close();
	 return ;
  }
}
/*----�������Զ���----*/
function updateExtendObj(dimname,dimid,sqltype,desc){

 var windowObj = opener.document.frames("elistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  
   var dimnameOpr = windowObj.document.getElementsByName("dimname");
 var dimidOpr = windowObj.document.getElementsByName("dimid"); 
    var sqltypeOpr = windowObj.document.getElementsByName("sqltype");
     var descOpr = windowObj.document.getElementsByName("desc");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
    dimnameOpr[i].value=dimname;
  dimidOpr[i].value=dimid;   
  sqltypeOpr[i].value = sqltype;
    descOpr[i].value=desc;
    return;
	}
   }
}
	
    		
    		

/*--��ʼ��--*/
 function  initTarget(opr){
if(opr!="change"){
  var windowObj = opener.document.frames("elistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  
   var sqltype = windowObj.document.getElementsByName("sqltype");
 var dimid = windowObj.document.getElementsByName("dimid");
   var dimname = windowObj.document.getElementsByName("dimname");
     var desc = windowObj.document.getElementsByName("desc");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
	 window.document.getElementById("datasetsel").value=dimid[i].value.split(".")[0];
  window.document.getElementById("targetsel").value=dimid[i].value.split(".")[1];
   window.document.getElementById("dimname").value=dimname[i].value;
    window.document.getElementById("desc").value=desc[i].value;
	}
	}
	}
}

/*-������ʽ-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}

// ���ݼ������¼�
  function  datasetChange(){
   var  str=window.opener.document.getElementById("dataSet").value;
    document.forms[0].action='dimedit.do?changeflag=change&&flag=edit&&datasetAll='+str;
  document.forms[0].submit();
  }
  
 
    	</script>
	</head>

	<body onLoad="initTarget('<%=(String)request.getAttribute("change")%>');">
		<html:form action="dimedit.do">
			<CENTER>
				<H4>
					ָ��༭
				</H4>
			</CENTER>
			<CENTER>
				<input type="hidden" name="modtype" value="<%=request.getParameter("modtype")%>">
				<table width="625" border="1" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							ѡ�����ݼ�(<font color="red">*</font>)
						</td>
						<td>
							<html:select property="datasetsel" onchange="datasetChange();">
								<html:options collection="dataSetList" labelProperty="label" property="value" />

							</html:select>
						</td>
						<td>
							ѡ��ָ��
						</td>
						<td>
							<html:select property="targetsel">
								<html:options collection="targetList" labelProperty="label" property="value" />
							</html:select>
						</td>
					</tr>

					<tr>
						<td>
							ά������(<font color="red">*</font>)
						</td>
						<td colspan="4">
							<html:text property="dimname"></html:text>
						</td>
					</tr>
					<tr>
						<td>
							ά������(<font color="red">*</font>)
						</td>
						<td>
							<html:select property="sqltype">
								<html:options collection="sqlTypeList" labelProperty="label" property="value" />
							</html:select>
						</td>
					</tr>
					<tr>
						<td>
							ά��˵��(<font color="red">*</font>)
						</td>
						<td colspan="4">
							<html:textarea property="desc" rows="5" cols="30"></html:textarea>
						</td>
					</tr>
				</table>
				<br>
				<input type="button" value="ȷ��" onclick="submitDim();" />
				<input type="button" value="�ر�" onclick="window.close();" />

			</CENTER>
		</html:form>
	</body>
</html>
