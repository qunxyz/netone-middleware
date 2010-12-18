<%@ page contentType="text/html; charset=GBK" %>
<html>
<head>
<title>
跳转到主页面...
</title> 
</head>
<%
  Object errormsg = request.getAttribute("errormsg");
  String strmsg = errormsg==null?"":errormsg.toString().trim();
  String path = request.getContextPath();
  String url = path+"/sso/impl/index.jsp";
%>
<body>
<script type="text/javascript">
	var par = window;
	while(par.parent != par){
		par = par.parent ;
	}
	par.location.href = "<%=url%>";
</script>
</body>
</html>
