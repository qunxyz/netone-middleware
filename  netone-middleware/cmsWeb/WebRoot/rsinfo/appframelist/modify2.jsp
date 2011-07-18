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
		<title>修改应用配置</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript">
		function searchtree() {
			window.open("SSelectSvl?appname=BUSSENV&pagename="+document.all.pagename.value,'_blank', 'height=380, width=600, top=0, left=0,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
		}
				//执行点击树节点操作
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
			document.all.objecttype.value = name+"["+naturalname+"]";
		}
		
		function sselected(text,naturalname){
			document.all.extendattribute.value = text+"["+naturalname+"]";
		}	
		
		function selectpage(pagename,name){
		  window.open('<%=basePath%>/MSelectSvl?pagename='+pagename+'&appname='+name);
		}
		
		function mselected(options){
		
			var subform='';
			for(var i=0;i<options.length;i++){
				subform+=options[i].text+'['+options[i].value+'],';
			}
			document.getElementById('extendattribute').value=subform;
		}
		
		function selectpagex(){
			var typex=document.getElementById("objecttype").value;
			
			if(typex=='human'){
				selectpage('men','DEPT');
			}
			if(typex=='dept'){
				selectpage('department','DEPT');
			}
			if(typex=='team'){
				selectpage('department','SYSTEMTEAM');
			}
			if(typex=='role'||typex=='flowrole'){
				selectpage('role','SYSROLE');
			}
		}
		
		function choicex(){
			document.getElementById('extendattribute').value=''
			if(document.getElementById('objecttype').value=='creater'){
				document.getElementById('extendattribute').style.display='none';
				document.getElementById('choicepage').style.display='none';
			}else{
				document.getElementById('extendattribute').style.display='';
				document.getElementById('choicepage').style.display='';			
			}
		}
		</script>
	</head>
	<body style="font-size: 12px; margin: 22px">
		<c:if test="${ModifySuccess == 'y'}">
			<script type="text/javascript">
				alert("修改成功！")
				window.close();
			</script>
		</c:if>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("修改失败！")
			</script>
		</c:if>
		<c:if test="${ModifySuccess == 'noPermission'}">
			<script type="text/javascript">
				alert("没有权限！")
				window.close();
			</script>
		</c:if>
		<div style="width: 100%; height: 100%">
			<form action="PagelistModifySvl?task=editsave" method="post"
				name="form1">
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="id" value="${upo.id}" />
				<table width="96%" border="0" align="center" cellpadding="10"
					cellspacing="1" id="lie_table">
					<tr>
						<td width="15%">
							手&nbsp;&nbsp;&nbsp;&nbsp;动
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
								class="textinput_td" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							参与者模式
						</td>
						<td>
							<select name='objecttype' id='objecttype'
								onchange="choicex()">
								<option value='human'
									<c:if test="${upo.objecttype=='human'}">selected</c:if>>
									人员
								</option>
								<option value='team'
									<c:if test="${upo.objecttype=='team'}">selected</c:if>>
									组
								</option>
								<option value='dept'
									<c:if test="${upo.objecttype=='dept'}">selected</c:if>>
									组织
								</option>
								<option value='role'
									<c:if test="${upo.objecttype=='role'}">selected</c:if>>
									角色
								</option>
								<option value='flowrole'
									<c:if test="${upo.objecttype=='flowrole'}">selected</c:if>>
									流程角色
								</option>
								<option value='creater'
									<c:if test="${upo.objecttype=='creater'}">selected</c:if>>
									创建者
								</option>
							</select>

							<input type='text' name='extendattribute' id="extendattribute"
								value='${upo.extendattribute}' size='40'>
							<input type='button' name='choicepage' value='选择参与者'
								onClick='selectpagex()'>

							<input type="checkbox" name="needSerilaizer" value="0"
								style="display: none" />


						</td>
					</tr>
					<tr>
						<td>
							并发控制
						</td>
						<td>

							<input type="radio" name="actionurl" value="1"
								<c:if test="${upo.actionurl==1||upo.actionurl==null||upo.description==''}">checked</c:if>>
							单人审批
							<input type="radio" name="actionurl" value="2"
								<c:if test="${upo.actionurl==2}">checked</c:if>>
							多人审批-不控制同步
							<input type="radio" name="actionurl" value="3"
								<c:if test="${upo.actionurl==3}">checked</c:if>>
							多人审批-控制同步
						</td>
					</tr>
					<tr>
						<td width="15%">
							协办
						</td>
						<td>
							<input type="radio" name="description" value="1"
								<c:if test="${upo.description==1||upo.description==null||upo.description==''}">checked</c:if>>
							无协办
							<input type="radio" name="description" value="2"
								<c:if test="${upo.description==2}">checked</c:if>>
							抄送
							<input type="radio" name="description" value="3"
								<c:if test="${upo.description==3}">checked</c:if>>
							抄阅
							<input type="radio" name="description" value="4"
								<c:if test="${upo.description==4}">checked</c:if>>
							抄送且抄阅
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
