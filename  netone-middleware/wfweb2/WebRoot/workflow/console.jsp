<%@ page contentType="text/html; charset=GB2312"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%String path = request.getContextPath();%>
<html>
	<head>
		<link href="<%=path%>/include/css/oe.css" rel="stylesheet" type="text/css">

		<script>
	function choice(opekey){
		var but1=window.top.frames.mainFrame.document.getElementById(opekey);
		if(but1==null){
			if(opekey=='Submit12'){
				alert("��ǰû�пɱ��������");
			}
			if(opekey=='Submit15'){
				alert("��ǰû�пɲ��Ե�����");
			}
			return;
		}
		but1.onclick();
	}
	function newprocess(){
		  var pathinfo =  document.getElementById("pathinfo").value;
		  var url= pathinfo+'/newprocessfile.do';
		  window.open(url,"mainFrame","width=800,height=600");
	}
	
	function managerprocess(){
		  var pathinfo =  document.getElementById("pathinfo").value;
		  var url= pathinfo+'/selectprocess.do';
		  window.open(url,"_blank","width=500,height=400");
	}
	</script>
	</head>

	<body>
		<form method="post" action="/workflow/resource">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="left">
			<input name="pathinfo" type="hidden" value=<%=path%>>
			<br>
				<tr>
					<td align='right'>
						<input class=butt onclick='newprocess()' type="button" value="�½�����">
						<input class=butt onclick="choice('Submit12')" type="button" value="��������">
						<input class=butt onclick="choice('Submit15')" type="button" value="���̵��Է���">
						<input class=butt onclick='managerprocess()' type="button" value="�����ļ�����">
					</td>
				</tr>
			</table>

			</form>
	</body>
</html>
