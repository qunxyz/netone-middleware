<%@ page contentType="text/html; charset=GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />
	<SCRIPT language="javascript">
    function  newModel(){
     document.forms[0].action="../../editDataModel.do?submitFlag=newModel";
     document.forms[0].submit();
        alert("新增成功");
     window.opener.location.reload();
     window.close();
    }
    
    function editModel(){
      document.forms[0].action="../../editDataModel.do?submitFlag=editModel";
      document.forms[0].submit();
      alert("保存成功");
        window.opener.location.reload();
        window.close();
    //  window.setTimeout(refreshOpener,200);
            
    }
    
    
    function refreshOpener(){        
    	window.opener.location.reload();      
        window.close();
    }
    
    </SCRIPT>
	<title>
		编辑模型
	</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>

<body>
	<html:form action="editDataModel.do">

		<html:hidden property="dataModelId" />
		<table>
			<tr>
				<td align="left">
					<logic:present name="editFlag">
						<input type="button" value="更新" onclick="editModel();" />
					</logic:present>
					<logic:notPresent name="editFlag">
						<input type="button" value="新增" onclick="newModel();" />
					</logic:notPresent>
					&nbsp;&nbsp;&nbsp;
					<input type="button" value="取消" onclick="javascript:window.close();" />
				</td>
			</tr>
			<tr>
				<td align="center">
					<html:textarea property="dataModelText" rows="30" cols="300">
					</html:textarea>
					<br>
				</td>
			</tr>

		</table>
	</html:form>
</body>
</html:html>
