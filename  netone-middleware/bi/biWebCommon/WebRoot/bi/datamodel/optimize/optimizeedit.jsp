
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
   var optimizename = window.document.getElementById("optimizename").value;
    var optimizeType = window.document.getElementById("optimizeType").value;
     var desc = window.document.getElementById("desc").value;
  if(datasetsel.trim()==""||optimizename.trim()==""||optimizeType.trim()==""||desc.trim()==""){
    alert("����д������Ϣ");
	return ;
  }else{
     updateExtendObj(optimizename,datasetsel,optimizeType,desc);
	 self.close();
	 return ;
  }
}
/*----�������Զ���----*/
function updateExtendObj(optimizename,datasetsel,optimizeType,desc){

 var windowObj = opener.document.frames("elistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  
   var optimizenameOpr = windowObj.document.getElementsByName("optimizename");
 var optimizeidOpr = windowObj.document.getElementsByName("optimizeid"); 
    var optimizetypeOpr = windowObj.document.getElementsByName("optimizetype");
     var descOpr = windowObj.document.getElementsByName("desc");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
    optimizenameOpr[i].value=optimizename;
  optimizeidOpr[i].value=datasetsel;   
  optimizetypeOpr[i].value = optimizeType;
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
  
  
 var optimizeid = windowObj.document.getElementsByName("optimizeid");
   var optimizename = windowObj.document.getElementsByName("optimizename");
    var optimizetype = windowObj.document.getElementsByName("optimizetype");
     var desc = windowObj.document.getElementsByName("desc");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
	 window.document.getElementById("datasetsel").value=optimizeid[i].value;
  window.document.getElementById("optimizename").value=optimizename[i].value;
   window.document.getElementById("optimizetype").value=optimizetype[i].value;
    window.document.getElementById("desc").value=desc[i].value;
	}
	}
	}
}

/*-������ʽ-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}


  
 
    	</script>
	</head>

	<body onLoad="initTarget();">
		<html:form action="dimedit.do">
			<CENTER>
				<H4>
					ָ��༭
				</H4>
			</CENTER>
			<CENTER>				
			
			<table width="625" border="1" cellpadding="0"  cellspacing="0">
				<tr>
					<td>
						ѡ�����ݼ�(<font color="red">*</font>)
					</td>
					<td>					
						<html:select property="datasetsel">
						<html:options collection="dataSetList" labelProperty="label" property="value"/>
						 
						</html:select>
					</td>					
				</tr>

				<tr>
					<td>
						�鲢����(<font color="red">*</font>)
					</td>
					<td colspan="4">
					<input type="text" name="optimizename"/>					
					</td>
				</tr>
				<tr>
					<td>
						�鲢����(<font color="red">*</font>)
					</td>
					<td>					
						<input type="text" name="optimizeType"/>
					</td>
				</tr>
				<tr>
					<td>
						�鲢˵��(<font color="red">*</font>)
					</td>
					<td colspan="4">
							<input type="text" name="desc"/>
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
