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
   <h4><a href="<%=basePath%>sample/resource/checkExist.jsp" target="main">判断资源是否存在</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchClerk.jsp" target="main">查询人员</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchExtendedRole.jsp" target="main">查询父角色</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchPermission.jsp" target="main">查询角色的所有授权</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchResource.jsp" target="main">查询资源</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchRole.jsp" target="main">查询角色</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchUser2role.jsp" target="main">查询用户角色关系</a></h4>
   <h4><a href="<%=basePath%>sample/resource/fetchUserByroleId.jsp" target="main">查询拥有该角色的人员</a></h4>
   <h4><a href="<%=basePath%>sample/resource/getUserRoles.jsp" target="main">获得用户具有的角色</a></h4>
   <h4><a href="<%=basePath%>sample/resource/loadClerk.jsp" target="main">装载人员</a></h4>
   <h4><a href="<%=basePath%>sample/resource/loadResourceById.jsp" target="main">根据id装载资源</a></h4>
   <h4><a href="<%=basePath%>sample/resource/loadResourceByNatural.jsp" target="main">根据naturalname装载资源</a></h4>
   <h4><a href="<%=basePath%>sample/resource/loadRole.jsp" target="main">装载角色</a></h4>
   <h4><a href="<%=basePath%>sample/resource/subResource.jsp" target="main">获得所有的子节点</a></h4>
   <h4><a href="<%=basePath%>sample/resource/validationUser.jsp" target="main">登陆验证</a></h4>
  </body>
</html>
