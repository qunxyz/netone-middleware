<%@ page contentType="text/html; charset=GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath(); 

%>
<html>

<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>加载数据中</title>
</head>

<body>
<div style="height: 99%" align="center">
<table border="1" width="37%" cellspacing="0" cellpadding="4"  style="border-collapse: collapse;" bgcolor="#FFFFEC" height="87">
 <tr>
  <td bgcolor="#A0A0A4" style="font-size:12px;color:#ffffff" height=24>初始化中...</td>
 </tr>
 <tr>
  <td style="font-size:12px;line-height:200%" align=center>正在初始化.请耐心等待...

  <marquee style="border:1px solid #000000" behavior="alternate" direction="right" width="300" scrollamount="5" scrolldelay="2500"  bgcolor="#ECF2FF" onbounce="javascript:alert('初始化成功');window.close();">
  <table cellspacing="1" cellpadding="0">
  <tr height=8>
  <td bgcolor=#3399FF width=8></td>
  <td></td>
  <td bgcolor=#3399FF width=8></td>
  <td></td>
  <td bgcolor=#3399FF width=8></td>
  <td></td>
  <td bgcolor=#3399FF width=8></td>
  <td></td>
  </tr></table></marquee>

  </td>
 </tr>
</table>
</div>

</body>

</html>