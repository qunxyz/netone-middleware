<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
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
		<title>修改应用资源</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>rsinclude/css/css.css">
		<script type="text/javascript" src="<%=basePath%>rsinclude/pagelist/pagelist.js"></script>
			<script type="text/javascript" src="<%=basePath%>rsinclude/pagelist/prototype.js"></script>
		<script type="text/javascript">
		function searchtree() {
			window.open("SSelectSvl?appname=BUSSENV&pagename="+document.all.pagename.value,'_blank', 'height=380, width=600, top=0, left=0,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
		}
				//执行点击树节点操作
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
			document.all.objecttype.value = name+"["+naturalname+"]";
		}
		
		function sselected(text,naturalname){
			document.all.objecttype.value = text+"["+naturalname+"]";
		}	
		
						function selectpagedyform(){
		  window.open('<%=basePath%>/SSelectSvl?pagename=dyformlist&appname=BUSSFORM');
		}
		
				//选择页面
		function selectpageflow(){
		  window.open('<%=basePath%>/SSelectSvl?pagename=workflow&appname=BUSSWF');
		}
		
		function sselectdy(text,value){
			document.getElementById("dy").value=text+"["+value+"]";
		}
		
		function sselectwf(text,value){
			document.getElementById("wf").value=text+"["+value+"]";
		}
		
		function modifyAppX(){
		    var wf=document.getElementById("wf").value;
		    var dy=document.getElementById("dy").value;
			document.getElementById("actionurl").value=wf+";"+dy;
			document.forms[0].submit(); 
		}
		
		function loadx(){
		  var dyAndWf= '${upo.actionurl}'.split(';');
		  document.getElementById("wf").value=dyAndWf[0];
		  document.getElementById("dy").value=dyAndWf[1];
		}
		
		
		function mandy(){
			var url='<portal:envget envkey="WEBSER_BIWEB"/>/PagelistpathRightSvl?pagename=dyformlist&appname=BUSSFORM';		
			window.open(url);
		}
		function manwf(){
			var url='<portal:envget envkey="WEBSER_WFWEB"/>/PagelistpathRightSvl?pagename=pagelist&appname=BUSSWF';
			window.open(url);
		}
		
		function mantemplate(){
			var url='<portal:envget envkey="WEBSER_FCK"/>/PagelistpathRightSvl?appname=FCK&pagename=fcklist';
			window.open(url);
		}
		
		function selecthis(id,name,inclusion,parent,path){
		 	var valuex=name+'['+id+']';
		 	document.getElementById("description_").value=valuex;
		 	document.getElementById("description").value=valuex;
		}
		</script>
	</head>
	<body style="font-size: 12px;margin: 22px" onload="loadx()">
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
		<c:if test="${ModifySuccess == 'noPermission'}">
			<script type="text/javascript">
				alert("没有权限！")
				window.close();
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
							<a href="javascript:searchtree();"><font color='blue'>选择分类</font>
							</a>
						</td>
						<td width='300'>
							<select name='objecttype' id='objecttype'>
								<option value='default' <c:if test="${upo.objecttype=='default'}">selected</c:if>>
									默认
								</option>
								<option value='ndyd' <c:if test="${upo.objecttype=='ndyd'}">selected</c:if>>
									宁德移动
								</option>

							</select>
						</td>
					</tr>
					<tr>
						<td width="15%">
							引用
						</td>
						<td>
						 <input type='text' name='dy' id="dy" value='' size='30'>
							<input type='button' name='choicepage' id="choicepage"
								value='选择表单' onClick='selectpagedyform()'>
							<input type='button' name='choicepagex' id="choicepagex"
								value='管理表单' onClick='mandy()'>	
							<br>
		
							<input type='text' name='wf' id="wf" value='' size='30'>
							<input type='button' name='choicepage' value='选择流程'
								onClick='selectpageflow()'>
														<input type='button' name='choicepagex' id="choicepagex"
								value='管理流程' onClick='manwf()'>		
															
								
								
						<input type="text" name="actionurl" value="" style="display:none"/>
						</td>
					</tr>
					<tr>
						<td width="15%">
							扩展属性
						</td>
						<td>
							<textarea rows="8" cols="60" name="extendattribute">${upo.extendattribute}</textarea>
							<br>
							<input type="checkbox" name="needSerilaizer" value="1" />
							持久化

						</td>
					</tr>
					<tr>
						<td width="15%">
							工单模板
						</td>
						<td>
							<input type="hidden" name="description" value="${upo.description}" />
							<input type="text" name="description_" value="${upo.description}" readonly/>							
							<input type='button' name='choicepagex' id="choicepagex"
								value='模板选择' onClick='mantemplate()'>		
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input type="button" name="btnnew" value="修 改" class="butt" onclick="modifyAppX()">
					<input type="button" name="btncancel" value="取 消"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
