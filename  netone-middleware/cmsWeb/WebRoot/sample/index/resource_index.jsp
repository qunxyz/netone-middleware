<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'resource_index.jsp' starting page</title>
  
  </head>
  
  <body>
   <h4><a href="<%=basePath%>sample/resource/checkExist.jsp" target="main">�ж���Դ�Ƿ����</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchClerk.jsp" target="main">��ѯ��Ա</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchExtendedRole.jsp" target="main">��ѯ����ɫ</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchPermission.jsp" target="main">��ѯ��ɫ��������Ȩ</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchResource.jsp" target="main">��ѯ��Դ</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchRole.jsp" target="main">��ѯ��ɫ</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchUser2role.jsp" target="main">��ѯ�û���ɫ��ϵ</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchUserByroleId.jsp" target="main">��ѯӵ�иý�ɫ����Ա</a></h4>
   <h4><a href="<%=basePath%>sample/resource/getUserRoles.jsp" target="main">����û����еĽ�ɫ</a></h4>
   <h4><a href="<%=basePath%>sample/resource/loadClerk.jsp" target="main">װ����Ա</a></h4>
   <h4><a href="<%=basePath%>sample/resource/loadResourceById.jsp" target="main">����idװ����Դ</a></h4>
   <h4><a href="<%=basePath%>sample/resource/loadResourceByNatural.jsp" target="main">����naturalnameװ����Դ</a></h4>
   <h4><a href="<%=basePath%>sample/resource/loadRole.jsp" target="main">װ�ؽ�ɫ</a></h4>
   <h4><a href="<%=basePath%>sample/resource/subResource.jsp" target="main">������е��ӽڵ�</a></h4>
   <h4><a href="<%=basePath%>sample/resource/validationUser.jsp" target="main">��½��֤</a></h4>
  </body>
</html>
