<%@ page language="java" pageEncoding="GBk"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>list.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=path%>/include/css/wf.css" rel="stylesheet"
		type="text/css">
</head>
<script type="text/javascript">
	function chked(){
		for(var i=0;i<form1.chkid.length;i++){
			if(document.getElementsByName("chkid")[i].checked == false){
				document.getElementsByName("chkid")[i].checked = true;
			}	
		}
	}
	function unchk(){
		for(var i=0;i<form1.chkid.length;i++){
			if(document.getElementsByName("chkid")[i].checked == false){
				document.getElementsByName("chkid")[i].checked = true;
			}
			else if(document.getElementsByName("chkid")[i].checked == true){
				document.getElementsByName("chkid")[i].checked = false;
			}	
		}
	}
	function del(){
		form1.action="";
		form1.submit();
	}
</script>

<body>
	<html:form action="/dic" method="post" styleId="form1">
		<table align="center" style="width: 100%;" border="1" cellpadding="2"
			bordercolordark="#F7F7F7" bordercolorlight="#F7F7F7">
			<logic:present name="databaseList" scope="request">
				<logic:notEmpty name="databaseList" scope="request">
					<logic:iterate id="list1" name="databaseList" scope="request">
						<tr>
							<td height="21">
								数据库
							</td>
							<td height="21">
								<bean:write name="list1" property="databaseName" />
							</td>
						</tr>
						<logic:present name="tableList" scope="request">
							<logic:notEmpty name="tableList" scope="request">
								<logic:iterate id="list2" name="tableList" scope="request">
									<logic:equal name="list2" property="databaseName"
										value="${list1.databaseName}">
										<tr>
											<td height="21">
												表名
											</td>
											<td height="21">
												<bean:write name="list2" property="tableName" />
											</td>
										</tr>
									</logic:equal>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
					</logic:iterate>
				</logic:notEmpty>
			</logic:present>
			<tr height="23px" valign="bottom" align="center">
				<td>
					<input type="button" value="全选" onclick="chked();" class="butt" />
					<input type="button" value="反选" onclick="unchk();" class="butt" />
				</td>
				<td align="center" style="width:100%">
					<input type="button" value="添加" onclick="" class="butt" />
					<input type="button" value="修改" onclick="" class="butt" />
					<input type="button" value="删除" onclick="" class="butt" />
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
