<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>ע��</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="css/css.css">
		<script type="text/javascript" src="include/js/human/regedit.js"></script>
		<script type="text/javascript">
		function changepic(){
		var myDate=new Date();
		
		   document.getElementById('jcaptcha').src='<%=basePath%>human/jcaptcha?px='+myDate.getSeconds();
		}
		function showcode(){
			var code = document.getElementById('code').value;
			document.getElementById('tip').innerText = code;
		}
		</script>

	</head>

	<body >
		<c:if test="${regSuccess == 'y'}">
			<script type="text/javascript">
				alert("ע��ɹ�");
				//alert('${regSuccessMsg}');
				window.close();
			</script>
		</c:if>
		<c:if test="${regSuccess == 'n'}">
			<script type="text/javascript">
				alert('${regErrorMsg }');
			</script>
		</c:if>
		<form action="<%=path%>/human/HumanRegeditSvl" method="post">
			<TABLE width="100%" align="left" cellpadding="10" cellspacing="0"
				border='1'>
				<tr>
					<td class="btextTitle">
						&nbsp;
					</td>
					<td class="btext">
						<font color="red">${regErrorMsg }</font>
					</td>
				</tr>
				<tr>
					<td class="btextTitle">
						&nbsp; E-mail
					</td>
					<td class="btext">
						<input type="text" name="email" class="textinput_td" size="20"
							value="${requestScope.email }" />
						<br>
						������һ����Ч�������ַ
					</td>
				</tr>
				<tr>
					<td class="btextTitle">
						&nbsp;�ʺ�(��¼����)
					</td>
					<td class="btext">
						<input type="text" name="name" onKeyup="nocn()" onblur="nocn();"
							class="textinput_td" size="20" maxlength="16"
							value="${requestScope.name }" />
					</td>
				</tr>
				<tr>
					<td class="btextTitle">
						&nbsp;����(��ʹ������)
					</td>
					<td class="btext">
						<input type="text" name="name_zh" class="textinput_td" size="20"
							maxlength="16" value="${requestScope.name_zh }" />
					</td>
				</tr>
				<tr>
					<td class="btextTitle">
						&nbsp;�ƶ��绰
					</td>
					<td class="btext">
						<input type="text" name="phone" class="textinput_td" size="40"
							value="${requestScope.phone }" />
					</td>
				</tr>
				<tr>
					<td class="btextTitle">
						&nbsp;��ϸ��Ϣ
					</td>
					<td class="btext">
					<textarea rows="3" cols="60" name="remark" value="${requestScope.remark }"></textarea>

					</td>
				</tr>
				<tr>
					<td class="btextTitle">
						&nbsp;��֤��
					</td>
					<td>
						<img id='jcaptcha' src="<%=basePath%>human/jcaptcha" height='60' />
					</td>
				</tr>
				<tr>
					<td class="btextTitle">
						&nbsp;������֤��
					</td>
					<td class="btext">
						<input type="text" name="checknum" require="true"
							styleClass="btext" />
						<a href='javascript:changepic()'>[�����壬��һ��]</a>
					</td>
				</tr>

				<tr style="display: none">
					<td class="btextTitle">
						&nbsp;��������
					</td>
					<td class="btextTitle">
						<select name="code" onchange="javascript:showcode()">
							<option value="0000">
								ѧУ
							</option>
							<option value="0001">
								��ҵ
							</option>
						</select>
						����½ʱʹ�õ���λ����Ϊ��<span id="tip">0000</span>
					</td>
				</tr>

				<tr>
					<td class="btextTitle">
						&nbsp;Ŀ¼ѡ��
					</td>
					<td class="btextTitle">
						<input type="text" name="deptName" readonly="readonly"
							value="${requestScope.deptName }">
						<a href='javascript:dept()'> [ѡ��]</a>
						<input type="hidden" name="deptId" value="${requestScope.deptId }">
					</td>
				</tr>

				<tr>
					<td class="btextTitle">
						&nbsp;��ʼ����
					</td>
					<td class="btextTitle">
						&nbsp;admin@1234
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">

						<input type="button" value="ע��" onclick="regedit()"
							target="_blank" class="butt" />
					</td>
				</tr>


			</table>

		</form>
	</body>
</html>
