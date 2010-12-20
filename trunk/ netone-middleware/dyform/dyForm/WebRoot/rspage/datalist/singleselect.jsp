<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String className = "oe.mid.web.rspage.pagelist.util.MultiSelectDsCommon";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>单选择</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/xtree2.css">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/xtree2.js"></script>
		<script type="text/javascript" src="rsinclude/xloadtree2.js"></script>
		<script type="text/javascript" src="rsinclude/util.js"></script>
		<script type="text/javascript">
		function nodeAction(id,ou,naturalname,parentdir,url){
			var usersel = document.all.users;
			usersel.options.length=0;
			var ajax = xmlhttp("servlet/AjaxServiceSvl?class=<%=className%>&id="+id);
			var resp = ajax.responseText;
			if(resp){
				if(resp.indexOf("<SCRIPT") != -1){
					resp = resp.substring(resp.indexOf("\nname")+1,resp.length); 
				}
				var users = resp.split(";");
				for(var i=0 ; i<users.length ; i++){
					if(users[i]){
						var user = new Map();
						user.load(users[i]);
						usersel.add(new Option(user.get("name"),user.get("naturalname")));
					}
				}
			}
		}
		 
		function checkthis(){
			var usersel = document.all.users;
			if(usersel.selectedIndex == -1){
				alert("请选择一个");
			}else{
				var option = usersel.options[usersel.selectedIndex];
				opener.addselect(option.text+"["+option.value+"]");
				window.close();
			}
		}
	</script>
	</head>

	<body>
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="6" id="table" style="table-layout: fixed">
			<tr>
				<td align="center" valign="top" bgcolor="#fafdff">
					<br>
					<table border="0" width="90%" style="table-layout: fixed">
						<tr>
							<td width="70%" valign="top" align="left">
								<c:if test="${root != null}">
									<div style="width:90%;height:200px;overflow: auto">
										<script type="text/javascript">
						  					var functree = new WebFXLoadTree("${root.name}","XMLFuncTreeSvl?parentid=${root.id}","javascript:nodeAction('${root.id}','${root.ou}','${root.naturalname}','${root.parentdir}','${root.actionurl}')");
						  					functree.write();
						  					functree.expand();
						  					functree._setSelected(true);
				  						</script>
									</div>
								</c:if>
							</td>
							<td align="center" valign="top">
								<select size="13" name="users" style="width: 150px">
								</select>
							</td>
						</tr>
					</table>
					<br>
					<div style="margin: 10px" align="center">
						<input type="button" value="确 定" class="butt"
							onclick="checkthis();">

						<input type="button" value="关 闭" class="butt"
							onclick="window.close();">
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
