
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


/*--  �ύ--*/
event.prototype.submit =function(){
 var datasetsel = window.document.getElementById("datasetsel").value;
   var optimizename = window.document.getElementById("optimizename").value;
    var optimizeType = window.document.getElementById("optimizeType").value;
     var desc = window.document.getElementById("desc").value;
  if(datasetsel.trim()==""||optimizename.trim()==""||optimizeType.trim()==""||desc.trim()==""){
    alert("����д������Ϣ");
	return ;
  }else{
     newExtendObj(optimizename,datasetsel,optimizeType,desc);
	 self.close();
	 return ;
  }
}
/*----�½�һ����չ���Զ���----*/
function newExtendObj(optimizename,datasetsel,optimizeType,desc){
var windowObj = opener.document.frames("elistFrame");
 
var divObj =windowObj.document.createElement("div");
var trObj=  "<div id='extendLineList'><tr><td><input name='checkExtend' type='checkbox'/></td>"
            +"<td align='center'><input   type='text' size=6 name='optimizename' value='"+optimizename+"' readOnly/></td>"
            +"<td align='center'><input   type='text' size=6  name='optimizeid' value='"+datasetsel+"' readOnly/></td>"      
             +"<td align='center'><input   type='text' size=6  name='optimizeType' value='"+optimizeType+"' readOnly/></td>"      
            +"<td align='center'><input  type='text'  size=6 name='desc' value='"+desc+"' readOnly/></td>"
             +"<td align='center'><input  type='hidden'  name='targetMap' /></td>"
              +"<td align='center'><input  type='hidden' name='dimensionMap' /></td>"
            +"</tr></div>";
	divObj.innerHTML = trObj;
windowObj.document.body.appendChild(divObj);
}
/*-������ʽ-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}
    

  
    	</script>
	</head>

	<body>
	<html:form action="optimize.do">
		<CENTER>
			<H4>
				ά���Ż�����
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
			<input type="button" value="ȷ��" onclick="(new event()).submit();" />
			<input type="button" value="�ر�" onclick="window.close();" />

		</CENTER>
		</html:form>
	</body>
</html>
