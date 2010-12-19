<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'workflow_index.jsp' starting page</title>
    
  </head>
  
  <body>
    <h4><a href="<%=basePath%>sample/workflow/load_relevantvar.jsp" target="main">根据ID获得数据对象实例</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/load-process.jsp" target="main">装载流程</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/load-runtime.jsp" target="main">装载流程实例</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/load-worklist.jsp" target="main">装载活动</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/process-dataFiled-list.jsp" target="main">获得流程的数据</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/process-instance-list.jsp" target="main">显示所有的流程实例</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/process-instance-one-rel-list.jsp" target="main">获得一个流程运行实例的相关数据对象1</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/process-instance-rel-list.jsp" target="main">获得一个流程运行实例的相关数据对象2</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/runtime-act-url.jsp" target="main">返回指定流程的交互地址</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/runtime-sub-act-url.jsp" target="main">返回指定流程的子流程列表页面地址</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/work-list.jsp" target="main">显示所有的活动节点</a></h4>
  </body>
</html>
