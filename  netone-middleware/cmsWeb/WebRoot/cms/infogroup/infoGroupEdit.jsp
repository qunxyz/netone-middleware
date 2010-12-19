<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String done=(String)request.getAttribute("done");
			%>
<html>
	<head>
		<title>
			组
		</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<SCRIPT type="text/javascript">
		  function update(){
		    document.forms[0].action="<%=path%>/infogroupedit.do?editFlag=update";
		    document.forms[0].submit();
		  
		  }
   function todo(){
   	 if('<%=done%>'!=''){
   	 	alert('<%=done%>');
   	 	window.open('/cmsWeb/cms/tree/tree.jsp','leftFrame','');
   	 }
   }
		</SCRIPT>
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet" type="text/css">
	</head>

	<body  onLoad="todo();" BGCOLOR=#E2E2E2 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">

		<html:form action="infogroupedit.do">
			<logic:notEqual name="seeFlag" value="true">
				<html:hidden property="groupId" />

				<table align="center" style="width:100%;">


					<tr>
						<td>
							组名称
						</td>
						<td>
							<html:text property="groupName" size="20"></html:text>
						</td>
					</tr>

					<tr>
						<td>
							描述
						</td>
						<td>
							<html:textarea property="description" cols="60" rows="4"></html:textarea>
						</td>
					</tr>
					<tr>
						<td>
							扩展属性
						</td>
						<td>
							<html:textarea property="extendattribute" cols="60" rows="4"></html:textarea>
						</td>
					</tr>
					<tr height="23px" valign="bottom" align="center">
						<td align="center" style="width:100%" colspan='2'>
							<input type="button" class="butt" value="    更 新    " onclick="update();" />

						</td>
					</tr>
				</table>

			</logic:notEqual>
			<logic:equal name="seeFlag" value="true">
				<br>
				<table align="center" style="width:100%;">
					<tr>
						<td align="center">
							<h4>
								查看资讯模型
							</h4>
						</td>
					</tr>
				</table>
				<table align="center" style="width:100%;">


					<tr>
						<td>
							资讯组名称
						</td>
						<td>
							<html:text property="groupName" size="20" disabled="true"></html:text>
						</td>
					</tr>

					<tr>
						<td>
							描述
						</td>
						<td>
							<html:textarea property="description" cols="90" rows="12" disabled="true"></html:textarea>
						</td>
					</tr>
					<tr>
						<td>
							扩展属性
						</td>
						<td>
							<html:textarea property="extendattribute" cols="90" rows="12" disabled="true"></html:textarea>
						</td>
					</tr>
				
				</table>
			</logic:equal>
		</html:form>
	</body>
</html>
