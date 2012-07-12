<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title>����</title>

		<script> 
		var number = /^[-]?([0-9]+)\.?([0-9]*)$/;
		function todo(){
			var oriprice=document.getElementById('oriprice').value;
			var curprice=document.getElementById('curprice').value;
			var name=document.getElementById('name').value;
			if(name==''){
				alert('name can not be empty');
				return;
			}
			if(!oriprice.match(number)){
				alert('oriprice must be double');
				return;
			}
			if(!curprice.match(number)){
				alert('curprice must be double');
				return;
			}
			

	this.document.forms[0].method = "post";
	this.document.forms[0].submit();
		}
		
		
		</script>
	</head>
	<body>
		<c:if test="${ope == 'done'}">
			<script type="text/javascript">
				alert("�½��ɹ���")
				opener.search();
			</script>
		</c:if>

		<form action="<%=basePath%>/turnpageori/create" name="form1"
			method="post">
			<input type='hidden' name='ope' value='done'>
			<br>
			<table border='1' align='left'>
				<tr>
					<td class="tdheadline">
						����
					</td>
					<td>
						<input type="text" name="name" id="name">
					</td>
				</tr>
				<tr>
					<td class="tdheadline">
						����
					</td>
					<td>
						<select name="types">
							<option id='Car'>
								����
							</option>
							<option id='House'>
								����
							</option>
							<option id='Antique'>
								�Ŷ�
							</option>
							<option id='Stock'>
								��Ʊ
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tdheadline">
						ԭ��
					</td>
					<td>
						<input type="text" name="oriprice" id="oriprice" value='0.0' />
					</td>
				</tr>
				<tr>
					<td class="tdheadline">
						�ּ�
					</td>
					<td>
						<input type="text" name="curprice" id="curprice"  value='0.0' />
					</td>
				</tr>
				<tr>
					<td class="tdheadline">
						����
					</td>
					<td>
						<input type="text" name="estprice" id="curprice"  value='0.0' />
					</td>
				</tr>
				<tr>
					<td class="tdheadline">
						����
					</td>
					<td>
						<input type="text" name="description" />
					</td>
				</tr>
				<tr>
					<td class="tdheadline">
						�Ǽ���
					</td>
					<td>
						<input type="text" name="participant" value="${loginname}" readonly/>
					</td>
				</tr>
				<tr>
					<td colspan='2'>
						<input type='button' value='  ��  ��  ' onClick='todo()'>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
