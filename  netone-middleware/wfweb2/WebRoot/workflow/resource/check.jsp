<%@ page contentType="text/html; charset=GB2312"%>
<jsp:directive.page import="oe.frame.web.util.WebStr"/>
<html>
<%
 // request.setCharacterEncoding("gb2312");
 // WorkFlowClick  workFlowClick = new WorkFlowClick();
 String newProcessAttribute =  WebStr.iso8859ToGB2312(request.getParameter("newProcessAttribute"));
 String createprocess=request.getParameter("createprocess");
 System.out.println(newProcessAttribute);

%>
<body>
<form name="checkForm" method="post" action="workStrack.jsp?isCreate=yes">
<input id="newProcess" name="newProcessAttribute" type="hidden" value=<%="\""+newProcessAttribute+"\""%> >
<input id="createprocess" name="createprocess" type="hidden" value=<%=createprocess%> >
</form>
<%
	   out.println("<script>");
	   out.println("document.forms[\"checkForm\"].submit();");
	   out.println("</script>");
  %>
</body>
</html>
