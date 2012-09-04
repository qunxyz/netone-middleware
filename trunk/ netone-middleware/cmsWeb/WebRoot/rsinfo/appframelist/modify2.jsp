<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<script type="text/javascript"
			src="<%=basePath%>rsinclude/prototype.js"></script>
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
		
		function modifythis(){
			var description=document.getElementById("description").value;
			var treeid=document.getElementById("needtree");
			var neededit=document.getElementById("needcheck");
			var forbidzb=document.getElementById("forbidzb");
			var columnedit=document.getElementById("columnedit").value;
			if(description==null||description==''){
				description='0';
			}
			if(treeid.checked){
				description=description+"-1";
			}else{
				description=description+"-0";
			}
			
			if(neededit.checked){
				description=description+"$1#";
			}else{
				description=description+"$0#";
			}
			
			if(forbidzb.checked){
				description=description+"@1%";
			}else{
				description=description+"@0%";
			}
			description=description+columnedit;
			document.getElementById("description").value=description;
			
			document.forms[0].submit();


		}
		
		function loadx(){
	    	var parser = new Ajax.Request(
							"<%=basePath%>ListDyColumnSelectSvl",
							{method:"get",parameters:"appid=${upo.parentdir}&thisid=${upo.id}",asynchronous:false}
							);
			 var restr = parser.transport.responseText;
			 DivBlock.innerHTML = restr ;
		}
		
		function addselect(){
			var  str= ""; 
			var   sel   =   document.getElementById('selecx').options; 
			for(   i=0;   i <sel.length;   i++   ) 
			{ 
			        if(   sel[i].selected   ==   true   ) 
			                str   +=   sel[i].value   +   ",\n\r"; 
			} 
			
			document.getElementById('columnedit').value   =   str; 

		
		
		}
		</script>
	</head>
	<body style="font-size: 12px; margin: 22px" onload='loadx()'>
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
								class="textinput_td"/>
						</td>
					</tr>
					<tr>
						<td width="15%">
							参与者模式
						</td>
						<td>
							<select name='objecttype' id='objecttype' onchange="choicex()">
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
								<option value='flowrolecreater'
									<c:if test="${upo.objecttype=='flowrolecreater'}">selected</c:if>>
									流程创建者角色
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
							<input type='hidden' name='description' id='description'
								value="${fn:substringBefore(upo.description,'-')}" />
							<input type="radio" name="opex" value="1"
								onclick="document.getElementById('description').value=this.value;"
								<c:if test="${fn:substringBefore(upo.description,'-')==1||upo.description==null||upo.description==''}">checked</c:if>>
							无协办
							<input type="radio" name="opex" value="2"
								onclick="document.getElementById('description').value=this.value;"
								<c:if test="${fn:substringBefore(upo.description,'-')==2}">checked</c:if>>
							抄送
							<input type="radio" name="opex" value="3"
								onclick="document.getElementById('description').value=this.value;"
								<c:if test="${fn:substringBefore(upo.description,'-')==3}">checked</c:if>>
							抄阅
							<input type="radio" name="opex" value="4"
								onclick="document.getElementById('description').value=this.value;"
								<c:if test="${fn:substringBefore(upo.description,'-')==4}">checked</c:if>>
							抄送且抄阅
						</td>
					</tr>
					<tr>
						<td width="15%">
							目录树
						</td>
						<td>
							<input type="checkbox" name="needtree" id="needtree" value="1"
								<c:if test="${fn:substring(upo.description,2,3)==1}">checked</c:if> />
							需要人员目录树
						</td>
					</tr>
					<tr>
						<td width="15%">
							转办控制
						</td>
						<td>
							<input type="checkbox" name="forbidzb" id="forbidzb" value="1"
								<c:if test="${fn:substring(upo.description,7,8)==1}">checked</c:if> />
							禁用转办
						</td>
					</tr>
					<tr>
						<td width="15%">
							编辑控制
						</td>
						<td>
							<input type='checkbox' value='1' id='needcheck' name='needcheck' <c:if test="${fn:substring(upo.description,4,5)==1}">checked</c:if>/>
							是否需要编辑
							<div id='DivBlock'></div>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input type="button" name="btnnew" value="修 改" class="butt"
						onclick="modifythis();" />
					<input type="button" name="btncancel" value="取 消"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
