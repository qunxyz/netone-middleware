<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			String login = (String) request.getAttribute("nologin");
			String cellid = (String) request.getAttribute("cellid");
%>
<HTML>
	<HEAD>
		<TITLE>
			����/�޸�
		</TITLE>
		<META NAME="Generator" CONTENT="EditPlus">
		<META NAME="Author" CONTENT="">
		<META NAME="Keywords" CONTENT="">
		<META NAME="Description" CONTENT="">
		<link href="<%=path%>/cms/include/css.css" rel="stylesheet" type="text/css">

		<script>
   function AddSql()
   {
      form1.submit();
      alert('����ʾ��');
      window.opener.refreshdiv('<%=cellid%>');
      windows.close();
   }
   
   function init()
   {
      var tip='<%=login%>';
      if(tip!='2'){
       if(tip=='1'){
       	  alert('��û�б༭��Ȩ�ޣ�');
      	}
      	if(tip=='0'){
       		alert('��δ��½ ��');
      	}
     	window.close();
      }

   }
   
   
</script>
	</HEAD>


	<body onload="init();" LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">
		<form action="<%=path%>/recordtipmodi.do" name="form1" method="post">

			<input type="hidden" name="tipid" value="${tipid}">
			<table align="center" style="width: 90%;">
				<tr height='31'>
					<td>
						<font size='2'>
							<strong>���������ע</strong>
						</font>
					</td>
				</tr>
				<tr>
					<td>
						<textarea cols=80 rows=20 name="tipinfo">${tipinfo}</textarea>
					</td>
				</tr>
			</table>
			<table align="center" style="width:100%;">
				<tr height="23px" valign="bottom" align="center">
					<td align="center" style="width:100%">
						<input name="buttontgfilter" type="button" value="�� ��" onclick="AddSql();" class="butt">
						<input name="buttonshowchart" type="reset" value="�� ��" class="butt">
					</td>
				</tr>
			</table>
		</form>
	</BODY>
</HTML>
