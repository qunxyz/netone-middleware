<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
%>
<html>
	<head>
		<title>修改页组</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
			<!-- 原始代码  2009-2-8
			<SCRIPT type="text/javascript"
			src="<%=path%>\include\js\modelshow\personmodel.js"></SCRIPT>	-->
			<!-- 戴新禄 2009-2-8 /的修改 -->
				<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/modelshow/personmodel.js"></SCRIPT>	
		<script type="text/javascript">
		 function update(){
		    document.forms[0].action="<%=path%>/infomodeledit.do?editFlag=update";
		    document.forms[0].submit();
		  }
		function selectthis(name,naturalname){
			document.getElementById('userid').value=name+"["+naturalname+"]";
		}		
		function sselected(text,id){
		   document.getElementById('template').value=text+"["+id+"]";
		}
	
		</script>

	</head>

	<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
		style="font:14px">
		<form action="" method="post">
			<input type='hidden' name='modelid' value='${infomodelForm.modelid}'>
			<input type='hidden' name='extendattribute' value='${infomodelForm.extendattribute}'>
			<input type='hidden' name='editFlag' value='update'>
			<table align="center" style="width:100%;">
				<tr>
					<td align="left">
						<h4>
							修改页组
						</h4>
					</td>
				</tr>
			</table>
			<table style="width:100%;">
				<tr>
					<td width='100'>
						页组名称:
					</td>
					<td>
						<input type='text' name="naturalname"  value='${infomodelForm.naturalname}' size='60' readonly>
					</td>
				</tr>
				<tr>
					<td>
						页组显示名称:
					</td>
					<td>
						<input type='text' name="modelname"  value='${infomodelForm.modelname}'>
					</td>
				</tr>
				<tr>
					<td>
						页组样式
					</td>
					<td>
						<input type='text' name='userid' readonly value='${infomodelForm.userid}'>
						<input type='button' value='选择样式' onClick='listStyle()'>
					</td>
				</tr>
				<tr>
					<td>
						选择模板
					</td>
					<td>
						<input type='text' name='template' id='template'
							value='' readonly>
						<input type='button' value='选择模板' onClick='listTemplate()'>
					</td>
				</tr>
				<tr>
					<td>
						页组描述
					</td>
					<td> <em>控制外部展现可加入:$[width:xxxx;height:xxxx;]<br>制作模板可加入:$[template:naturalname;] 其中naturalname参考WEB布局内部格式中的extendattribute中的路径名xxx.xxx.xxx</em>
						<textarea  name="description" cols="90" rows="5">${infomodelForm.description}</textarea>
					</td>
				</tr>
				<tr>
					<td>
						WEB布局内部格式:
					</td>
					<td>
					<!-- Don 考虑会出现模式信息 删除其信息 用户无从找出原有模版 从用户角度来说可能也不需要   基于PMS发布  加入 readonly-->
						<textarea name="infoxml" cols="90" rows="15"  readonly>${infomodelForm.infoxml}</textarea>
					</td>
				</tr>


				<tr style="display:none">
					<td>
						用户ID
					</td>
					<td>
						<html:text property="userid" size="20"></html:text>
					</td>
				</tr>
				<tr style="display:none">
					<td>
						存取模式
					</td>
					<td>
						<html:select property="accessmode" style="width:100">
							<logic:present name="accessmodeList">
								<html:options collection="accessmodeList" property="value"
									labelProperty="label" />
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr style="display:none">
					<td>
						WEB布局内部格式
					</td>
					<td>
						<html:textarea property="infoxml" cols="90" rows="10"></html:textarea>
				
					</td>
				</tr>

			</table>
			<table align="center" style="width:100%;">
				<tr height="23px" valign="bottom" align="center">
					<td align="center" style="width:100%">
						<input type="button" class="butt" value="修改" onClick='update()' />
						<input type="button" class="butt" value="取消"
							onclick="window.close();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
