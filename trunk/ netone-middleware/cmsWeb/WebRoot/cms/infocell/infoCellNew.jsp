<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%String path = request.getContextPath();
			String id = (String) request.getAttribute("cellid");
%>
<html>
	<head>
		<title>
			portalet
		</title>
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path%>/include/js/cell/cell.js"></script>
	</head>

	<body BGCOLOR=#E2E2E2 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">
		<logic:present name="createFlag">
			<logic:equal name="createFlag" value="true">
				<script type="text/javascript">
				      alert("新增成功");
				      var url="<%=path%>/infocelledit.do?id="+<%=id%>;
			          window.open(url,'mainFrame');
				</script>
			</logic:equal>
			<logic:equal name="createFlag" value="false">
				<script type="text/javascript">
				      alert("新增失败");
				</script>
			</logic:equal>
		</logic:present>
		<html:form action="infocellnew.do?newFlag=new">
			<table align="center" style="width:100%;">
				<tr>
					<td>
						所属组&nbsp;
						<html:select property="belongto">
							<OPTION value=""></OPTION>
							<logic:present name="groupList">
								<html:options collection="groupList" property="value" labelProperty="label" />
							</logic:present>
						</html:select>

						J++脚本名称
						<html:text property="cellname"></html:text>
						&nbsp;
						<html:checkbox property="intime"></html:checkbox>
						缓存&nbsp;

					</td>
				</tr>

				<tr>
					<td>

						<html:textarea property="body" cols="100" rows="30"></html:textarea>
						<INPUT type="hidden" name="bodyTemp" />
					</td>
				</tr>
				<tr height="23px" align="center">
					<td>
						<input type="button" class="butt" value="     新 增   " onclick="commit('<%=path%>/infocellnew.do?newFlag=new');" />
					</td>
				</tr>
			</table>

		</html:form>
	</body>
</html>
