<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%String path = request.getContextPath();
%>
<html>
	<head>
		<title>
			新增组
		</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet" type="text/css">
	</head>

	<body BGCOLOR=#E2E2E2 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px" onload="changeColumn();">
		<logic:present name="createFlag">
			<logic:equal name="createFlag" value="true">
				<script type="text/javascript">
				
				      alert("新增成功");
				      window.open('/cmsWeb/cms/tree/tree.jsp','leftFrame','');
				</script>
			</logic:equal>
			<logic:equal name="createFlag" value="false">
				<script type="text/javascript">
				      alert("新增失败");
				</script>
			</logic:equal>
		</logic:present>
		<html:form action="infogroupnew.do">
			<br>
			<table align="center" style="width:100%;">
				<tr>
					<td>
						组名称
					</td>
					<td>
						<html:text property="groupName" size="30">
						</html:text>
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
						<input type="submit" class="butt" value="    新 增    " />

					</td>
				</tr>
			</table>

		</html:form>
	</body>
</html>
