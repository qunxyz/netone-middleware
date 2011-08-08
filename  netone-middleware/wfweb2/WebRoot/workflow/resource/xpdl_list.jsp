<%@ page contentType="text/html; charset=GB2312"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<%=request.getAttribute("tip") == null ? "" : request
					.getAttribute("tip")%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>
			流程文件列表
		</title>
		<link href="<%=path%>/include/css/wf.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/workflowtrack/workflowSubflow.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/workflow/design.js"></SCRIPT>

	</head>
	<body>

		<form action="" name="form1" method="post">
			<input type="hidden" name="systemid" />
			<input name="pathinfo" type="hidden" value=<%=path%>>
			<table align="center" style="width: 100%;" border="1" cellpadding="2" bordercolordark="#F7F7F7" bordercolorlight="#F7F7F7">
				<tr align="center">
					<td class="tdheadline" width='50'>
						选择
					</td>
					<td class="tdheadline">
						流程名
					</td>
					<td class="tdheadline">
						流程中文名
					</td>
					<td class="tdheadline">
						创建时间
					</td>
				</tr>
				<c:forEach items="${listinfo}" var="getCol">
					<tr class="td-02" align="left">
						<td>
							<input type="radio" name="radioid" id='radioid' value="${getCol.id}" size="10">
						</td>
						<td height="21">
							${getCol.naturalname}
						</td>
						<td height="21">
							${getCol.name}
						</td>
						<td height="21">
							${getCol.created}
						</td>
					</tr>
				</c:forEach>
			</table>
			<table align="center" style="width:100%;">
				<tr height="23px" valign="bottom" align="center">
					<td align="center" style="width:100%">
						<input type="button" value="察看流程" onclick="choiceprocess2();" class="butt" />
						<input type="button" value="启动流程" onclick="choiceprocess3();" class="butt" />
						<input type="button" value="流程运行实例" onclick="choiceprocess4();" class="butt" />
						&nbsp;&nbsp;&nbsp;
						
						<input type="button" value="新建流程模型" onclick="newprocess();" class="butt" />
						<input type="button" value="修改流程模型" onclick="choiceprocess1();" class="butt" />
						<input type="button" class="butt" value="下载流程文件" onclick="downprocess();" />
						<input type="button" class="butt" value="删除流程文件" onclick="dropprocess();" />

					</td>
				</tr>
			</TABLE>
		</form>
	</body>
</html>
