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
       	  alert('��û�б༭��Ȩ�ޣ�');
      	}
      	if(tip=='0'){
       		alert('��δ��½ ��');
      	}
      }
      window.close();
   }
</script>
	</BODY>
</HTML>
