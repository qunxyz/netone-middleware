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
    <h4><a href="<%=basePath%>sample/workflow/load_relevantvar.jsp" target="main">����ID������ݶ���ʵ��</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/load-process.jsp" target="main">װ������</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/load-runtime.jsp" target="main">װ������ʵ��</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/load-worklist.jsp" target="main">װ�ػ</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/process-dataFiled-list.jsp" target="main">������̵�����</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/process-instance-list.jsp" target="main">��ʾ���е�����ʵ��</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/process-instance-one-rel-list.jsp" target="main">���һ����������ʵ����������ݶ���1</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/process-instance-rel-list.jsp" target="main">���һ����������ʵ����������ݶ���2</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/runtime-act-url.jsp" target="main">����ָ�����̵Ľ�����ַ</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/runtime-sub-act-url.jsp" target="main">����ָ�����̵��������б�ҳ���ַ</a></h4>
    <h4><a href="<%=basePath%>sample/workflow/work-list.jsp" target="main">��ʾ���еĻ�ڵ�</a></h4>
  </body>
</html>
