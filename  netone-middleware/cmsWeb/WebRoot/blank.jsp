<%@ page contentType="text/html; charset=GBK"%>
<%String login = (String) request.getAttribute("nologin");
%>
<HTML>
	<HEAD>

	</HEAD>

	<body>
		<script>

   function init()
   {
   	  alert("dsfdsfd");
      var tip='<%=login%>';
      if(tip!='2'){
       if(tip=='1'){
       	  alert('您没有编辑的权限！');
      	}
      	if(tip=='0'){
       		alert('您未登陆 ！');
      	}
      }
      window.close();
   }
</script>
	</BODY>
</HTML>
