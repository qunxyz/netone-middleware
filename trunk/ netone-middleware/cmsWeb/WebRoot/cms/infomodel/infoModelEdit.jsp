<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
%>
<html>
	<head>
		<title>�޸�ҳ��</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
			<!-- ԭʼ����  2009-2-8
			<SCRIPT type="text/javascript"
			src="<%=path%>\include\js\modelshow\personmodel.js"></SCRIPT>	-->
			<!-- ����» 2009-2-8 /���޸� -->
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
							�޸�ҳ��
						</h4>
					</td>
				</tr>
			</table>
			<table style="width:100%;">
				<tr>
					<td width='100'>
						ҳ������:
					</td>
					<td>
						<input type='text' name="naturalname"  value='${infomodelForm.naturalname}' size='60' readonly>
					</td>
				</tr>
				<tr>
					<td>
						ҳ����ʾ����:
					</td>
					<td>
						<input type='text' name="modelname"  value='${infomodelForm.modelname}'>
					</td>
				</tr>
				<tr>
					<td>
						ҳ����ʽ
					</td>
					<td>
						<input type='text' name='userid' readonly value='${infomodelForm.userid}'>
						<input type='button' value='ѡ����ʽ' onClick='listStyle()'>
					</td>
				</tr>
				<tr>
					<td>
						ѡ��ģ��
					</td>
					<td>
						<input type='text' name='template' id='template'
							value='' readonly>
						<input type='button' value='ѡ��ģ��' onClick='listTemplate()'>
					</td>
				</tr>
				<tr>
					<td>
						ҳ������
					</td>
					<td> <em>�����ⲿչ�ֿɼ���:$[width:xxxx;height:xxxx;]<br>����ģ��ɼ���:$[template:naturalname;] ����naturalname�ο�WEB�����ڲ���ʽ�е�extendattribute�е�·����xxx.xxx.xxx</em>
						<textarea  name="description" cols="90" rows="5">${infomodelForm.description}</textarea>
					</td>
				</tr>
				<tr>
					<td>
						WEB�����ڲ���ʽ:
					</td>
					<td>
					<!-- Don ���ǻ����ģʽ��Ϣ ɾ������Ϣ �û��޴��ҳ�ԭ��ģ�� ���û��Ƕ���˵����Ҳ����Ҫ   ����PMS����  ���� readonly-->
						<textarea name="infoxml" cols="90" rows="15"  readonly>${infomodelForm.infoxml}</textarea>
					</td>
				</tr>


				<tr style="display:none">
					<td>
						�û�ID
					</td>
					<td>
						<html:text property="userid" size="20"></html:text>
					</td>
				</tr>
				<tr style="display:none">
					<td>
						��ȡģʽ
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
						WEB�����ڲ���ʽ
					</td>
					<td>
						<html:textarea property="infoxml" cols="90" rows="10"></html:textarea>
				
					</td>
				</tr>

			</table>
			<table align="center" style="width:100%;">
				<tr height="23px" valign="bottom" align="center">
					<td align="center" style="width:100%">
						<input type="button" class="butt" value="�޸�" onClick='update()' />
						<input type="button" class="butt" value="ȡ��"
							onclick="window.close();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
