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
		<title>手机功能修改</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript">
		//打开树选择页面
		function searchtree() {
			window.open("SelectSvl?appname=BUSSENV.BUSSENV.MOBILEAPP&pagename="+document.all.pagename.value);
		}
		
		function sselected(text,value){
		   	document.getElementById("objecttype").value=text+"["+value+"]";
		}
		</script>
	</head>
	<body style="font-size: 12px;margin: 22px">
		<c:if test="${ModifySuccess == 'y'}">
			<script type="text/javascript">
				alert("修改成功！")
				opener.search();
				window.close();
			</script>
		</c:if>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("修改失败！")
			</script>
		</c:if>
		<div style="width: 100%;height: 100%">
			<form action="PagelistModifySvl?task=editsave" method="post"
				name="form1">
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="id" value="${upo.id}" />
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr>
						<td width="15%">
							有&nbsp;&nbsp;&nbsp;&nbsp;效
						</td>
						<td>

							<input type="checkbox" name="active" value="1"
								<c:if test="${upo.active=='1'}">checked</c:if> />
						</td>
					</tr>
					<tr>
						<td width="15%">
							名&nbsp;&nbsp;&nbsp;&nbsp;称
						</td>
						<td>
							<input type="text" name="naturalname" value="${upo.naturalname}"
								class="textinput_td" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							中文名称
						</td>
						<td>
							<input type="text" name="name" value="${upo.name}"
								class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							选择分类
						</td>
						<td width='300'>

							<select name='objecttype' id='objecttype'>
								<option value='1' <c:if test="${upo.objecttype=='1'}">selected</c:if>>流程应用</option>
								<option value='2' <c:if test="${upo.objecttype=='2'}">selected</c:if>>内容频道</option>
								<option value='3' <c:if test="${upo.objecttype=='3'}">selected</c:if>>表单应用</option>
								<option value='4' <c:if test="${upo.objecttype=='4'}">selected</c:if>>统一查询</option>
								<option value='5' <c:if test="${upo.objecttype=='5'}">selected</c:if>>定位管理</option>
								<option value='6' <c:if test="${upo.objecttype=='6'}">selected</c:if>>消息应用</option>
							</select>
						</td>
					</tr>
					<tr sytle='display:none'>
						<td width="15%">
							引用
						</td>
						<td>
							<input type="text" name="actionurl" value="${upo.actionurl}"
								class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							扩展属性
						</td>
						<td>
							<textarea rows="20" cols="80" name="extendattribute">${upo.extendattribute}</textarea>
						</td>
					</tr>
					<tr>
						<td width="15%">
							描述
						</td>
						<td>
							<textarea rows="2" cols="80" name="description">${upo.description}</textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input type="submit" name="btnnew" value="修 改" class="butt">
					<input type="button" name="btncancel" value="取 消"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
