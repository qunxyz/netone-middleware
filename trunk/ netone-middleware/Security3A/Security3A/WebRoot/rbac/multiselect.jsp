<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String className = "oe.security4a.web.rbac.MultiSelectDsRole";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>角色选择</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/xtree2.css">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript" src="include/js/xtree2.js"></script>
		<script type="text/javascript" src="include/js/xloadtree2.js"></script>
		<script type="text/javascript" src="include/js/util.js"></script>
		<script type="text/javascript">
		function nodeAction(id,ou,naturalname,parentdir,url){
			var rolesel = document.all.roles;
			rolesel.options.length=0;
			var ajax = xmlhttp("servlet/AjaxServiceSvl?class=<%=className%>&belongingness="+id);
			var resp = ajax.responseText;
			if(resp){
				if(resp.indexOf("<SCRIPT") != -1){
					resp = resp.substring(resp.indexOf("\nname")+1,resp.length); 
				}
				var roles = resp.split(";");
				for(var i=0 ; i<roles.length ; i++){
					if(roles[i]){
						var role = new Map();
						role.load(roles[i]);
						rolesel.add(new Option(role.get("name"),role.get("id")));
					}
				}
			}
		}
		 
		function add(){
			var roles = document.all.roles;
			for(var i=0 ; i<roles.length ; i++){
				if(roles.options[i].selected){
					var length = document.all.result.length;
					var b = true;
					for(var j=0;j<length;j++){
						if(roles.options[i].value==document.all.result.options[j].value){
							b=false;
							alert(roles.options[i].text+"已经被选过了!");
							break;
						}
					}
					if(b){
						document.all.result.add(new Option(roles.options[i].text,roles.options[i].value));
					}
				}
			}
		}
		
		function drop(){
			var s = document.all.result;
			var i = s.selectedIndex;
			while(i != -1){
				s.remove(i);
				i = s.selectedIndex;
			}
		}
		 
		function doSelect(){
			var result = document.all.result;
			if(result.length == 0){
				alert("请至少选择一个角色");
			}else{
				opener.addSelectedRole2(result.options);
				window.close();
			}			
		}
		
		function doclose(){
			if(opener.userSelectClose){
				opener.userSelectClose();
			}
			window.close();
		}
	</script>
	</head>

	<body>
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="6" id="table" style="table-layout: fixed">
			<tr>
				<td align="center" valign="top" bgcolor="#fafdff">
					<br>
					<table border="0" width="90%" height="80%"
						style="table-layout: fixed">
						<tr>
							<td width="30%" valign="top" align="left">
								<c:if test="${root != null}">
									<div style="width:98%;height:98%;overflow: auto">
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
								<select size="13" name="roles" style="width: 150px"
									multiple="multiple">
								</select>
							</td>
							<td align="center" bgcolor="#FFFFFF" width="80px" valign="middle">
								<p>
									<input type="button" value=" &gt;&gt; " class="butt"
										onclick="add();">
								</p>
								<p>
									<input type="button" value=" &lt;&lt; " class="butt"
										onclick="drop();">
								</p>
							</td>
							<td align="center" valign="top">
								<select size="13" name="result" style="width: 150px"
									multiple="multiple">
								</select>
							</td>
						</tr>
					</table>
					<br>
					<div style="margin: 10px" align="center">
						<input type="button" value="确 定" class="butt"
							onclick="doSelect();">

						<input type="button" value="关 闭" class="butt" onclick="doclose();">
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
