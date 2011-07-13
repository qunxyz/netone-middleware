<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	String className = "oe.mid.web.rspage.pagelist.util.MultiSelectDsOnlyDir";
	//选择资源属性，不填写\默认的属性为naturalname，同时我们可以根据需要选择一下的其他资源属性
	//objecttype,name,naturalname,active,description,appid,parentdir,actionurl,ou,extendattribute,inclusion,reference,aggregation,created
	//选择人员属性，不填写\默认的属性为naturalname，同时我们可以根据需要选择一下的其他人员属性
	//email,phoneNO,description
	String selectvalue="";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>资源多选</title>
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
			
			var ajax = xmlhttp("servlet/AjaxServiceSvl?class=<%=className%>&id="+id+"&selectvalue=<%=selectvalue%>");
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
		 
		function add(){
			var users = document.all.users;
			for(var i=0 ; i<users.length ; i++){
				if(users.options[i].selected){
					var length = document.all.result.length;
					var b = true;
					for(var j=0;j<length;j++){
						if(users.options[i].value==document.all.result.options[j].value){
							b=false;
							alert(users.options[i].text+"已经被选过了!");
							break;
						}
					}
					if(b){
						document.all.result.add(new Option(users.options[i].text,users.options[i].value));
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
		 
		function checkthis(){
			if(opener.mselected==undefined){
				alert('非选择状态,父页面缺少回调函数');
				return;
			}
			var result = document.all.result;
			if(result.length == 0){
				alert("请至少选择一个用户");
			}else{
				opener.mselected(result.options);
				window.close();
			}			
		}
		
		function doclose(){
			if(opener.userSelectClose){
				opener.userSelectClose();
			}
		}
	</script>
	</head>

	<body>
		<form action="" method="post">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="6" id="table" style="table-layout: fixed">
				<tr>
					<td align="center" valign="top" bgcolor="#fafdff">
						<br>
						<table border="0" width="90%" height="80%"
							style="table-layout: fixed">
							<tr>
								<td width="30%" valign="top" align="left">
									<div>
										<select name="application"
											onchange="document.forms[0].submit()">
											<c:forEach items="${applist}" var="app">
												<option value="${app.naturalname}" ${app.naturalname==appnaturalname?"selected":"" }>
													${app.name }
												</option>
											</c:forEach>
										</select>
									</div>
									<c:if test="${root != null}">
										<div style="width: 98%; height: 98%; overflow: auto">
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
									<select size="18" name="users" style="width: 150px"
										multiple="multiple">
									</select>
								</td>
								<td align="center" bgcolor="#FFFFFF" width="80px"
									valign="middle">
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
									<select size="18" name="result" style="width: 150px"
										multiple="multiple">
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
		</form>
	</body>
</html>
