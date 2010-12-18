<%@ page contentType="text/html; charset=GBK" %>
<jsp:directive.page import="java.net.URLEncoder"/>
<html>
<head>
<title>
跳转到登录页面...
</title>
</head>
<%
String path = request.getContextPath();
String url = path+"/sso/impl/login.jsp";
String para = "";
Object errormsg = request.getAttribute("errormsg");
String strmsg = errormsg==null?"":errormsg.toString();
String openurl = "";
if(strmsg.indexOf("已经过期")!=-1){
	int i = strmsg.indexOf("@");
	if(i != -1){
		openurl = strmsg.substring(i+1);
		strmsg = strmsg.substring(0,i);
	}
}
if(errormsg != null)
{
  try{
    para = para + "&errormsg="+java.net.URLEncoder.encode(strmsg,"UTF-8");
  }
  catch(Exception ex)
  {
    ex.printStackTrace();
  }
}
String gotourl = request.getParameter("gotourl");
if(gotourl != null)
{
  para = para + "&gotourl="+URLEncoder.encode(gotourl,"GBK");
}
String todo = request.getParameter("todo") ;
if(todo != null)
{
  para = para + "&todo="+todo;
}

if(para.length()!=0)
{
  para = para.substring(1,para.length());
  url = url + "?"+para ;
}

%>

<body>
<script type="text/javascript">
	var url = "<%=url%>";
	var errmsg = "<%=strmsg%>";
	if(errmsg){
		if(errmsg.indexOf("已经过期")!=-1){
			alert(errmsg);
			var openurl = '<%=openurl%>';
			if(openurl){
				url = openurl ;
			}
		}
	}

	var par = window;
	while(par.parent != par){
		par = par.parent ;
	}
	par.location.href = url;
</script>
</body>
</html>
