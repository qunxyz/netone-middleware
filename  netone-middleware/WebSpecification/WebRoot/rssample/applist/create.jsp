<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>创建公告</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/applist/applist.js"></script>
	</head>
	<body style="font-size: 12px;margin: 22px">
		<c:if test="${CreateSuccess == 'y'}">
			<script type="text/javascript">
				alert("新建成功！")
				opener.search();
			</script>
		</c:if>
		<c:if test="${CreateSuccess == 'n'}">
			<script type="text/javascript">
				alert("新建目录失败！")
			</script>
		</c:if>
		<div style="width: 100%;height: 100%">
			<form action="ApplistAddSvl?task=addsave" method="post">
				<input type="hidden" name="pagename" value="${pagename}" />
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr style="display:none">
						<td width="15%">
							有&nbsp;&nbsp;&nbsp;&nbsp;效
						</td>
						<td>
							<input type="checkbox" name="active" value="0" checked />
						</td>
					</tr>
					<tr>
						<td width="15%">
							名&nbsp;&nbsp;&nbsp;&nbsp;称
						</td>
						<td>
							<input type="text" name="naturalname" value=""
								class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							中文名称
						</td>
						<td>
							<input type="text" name="name" value="" class="textinput_td" />
						</td>
					</tr>
					<tr style="display:none">
						<td width="15%">
							类&nbsp;&nbsp;&nbsp;&nbsp;型
						</td>
						<td>
							<input name="apptype" value='publicinfo' />
						</td>
					</tr>

					<tr>
						<td width="15%">
							描述
						</td>
						<td>
							<textarea rows="4" cols="60" name="description"></textarea>
						</td>
					</tr>

					<tr>
						<td width="15%">
							发布内容颜色
						</td>
						<td>
						<select name='color'
								onChange="document.getElementById('extendattribute').value=this.value">
								<option value='black'>
									黑色
								</option>
								<option value='red'>
									红色
								</option>
								<option value='blue'>
									蓝色
								</option>
								<option value='green'>
									绿色
								</option>
								<option value='white'>
									白色
								</option>
							</select>
							<input type='hidden' name='extendattribute' value="" />
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input type="button" name="btnnew" value="新 建" onclick="create();"
						class="butt">
					<input type="button" name="btncancel" value="取 消"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
