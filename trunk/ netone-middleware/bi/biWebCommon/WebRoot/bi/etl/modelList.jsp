
<%@ page language="java" pageEncoding="GBK"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    
    <title>modelList.jsp</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="/biWeb/include/css/style.css" rel="stylesheet" type="text/css">
  </head>
  
  <body BGCOLOR=#EDF0F6 LEFTMARGIN=0 TOPMARGIN=10 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">
    <table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#A9C0E5" align="center">
          <tr>
            <td height="25" class="td-bg01">业务模型列表</td>
          </tr>
        </table>
        
         <CENTER>
    <table width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#A9C0E5" align="center">
      <tr class="td-bg01" align="center">
         <td height="25" bgcolor="#FFFFFF">模型选择</td>
         <td height="25" bgcolor="#FFFFFF">模型ID</td>
         <td height="25" bgcolor="#FFFFFF">模型名称</td>
         <td height="25" bgcolor="#FFFFFF">模型描述</td>
      </tr>
      <tr class="td-01" align="center">
         <td height="25" bgcolor="#FFFFFF"><input type="checkbox"/></td>
         <td height="25" bgcolor="#FFFFFF">1</td>
         <td height="25" bgcolor="#FFFFFF">模型1</td>
         <td height="25" bgcolor="#FFFFFF">模型描述1</td>
      </tr>
       <tr class="td-01" align="center">
         <td height="25" bgcolor="#FFFFFF"><input type="checkbox"/></td>
         <td height="25" bgcolor="#FFFFFF">1</td>
         <td height="25" bgcolor="#FFFFFF">模型1</td>
         <td height="25" bgcolor="#FFFFFF">模型描述1</td>
      </tr>
       <tr class="td-01" align="center">
         <td height="25" bgcolor="#FFFFFF"><input type="checkbox"/></td>
         <td height="25" bgcolor="#FFFFFF">2</td>
         <td height="25" bgcolor="#FFFFFF">模型2</td>
         <td height="25" bgcolor="#FFFFFF">模型描述2</td>
      </tr>
       <tr class="td-01" align="center">
         <td height="25" bgcolor="#FFFFFF"><input type="checkbox"/></td>
         <td height="25" bgcolor="#FFFFFF">3</td>
         <td height="25" bgcolor="#FFFFFF">模型3</td>
         <td height="25" bgcolor="#FFFFFF">模型描述3</td>
      </tr>
       <tr class="td-01" align="center">
         <td height="25" bgcolor="#FFFFFF"><input type="checkbox"/></td>
         <td height="25" bgcolor="#FFFFFF">4</td>
         <td height="25" bgcolor="#FFFFFF">模型4</td>
         <td height="25" bgcolor="#FFFFFF">模型描述4</td>
      </tr>
      <tr class="td-01" align="center">
         <td height="25" bgcolor="#FFFFFF"><input type="checkbox"/></td>
         <td height="25" bgcolor="#FFFFFF">5</td>
         <td height="25" bgcolor="#FFFFFF">模型5</td>
         <td height="25" bgcolor="#FFFFFF">模型描述5</td>
      </tr>
      <tr class="td-bg01" align="center">
         <td height="25" bgcolor="#FFFFFF">创建</td>
         <td height="25" bgcolor="#FFFFFF">修改</td>
         <td height="25" bgcolor="#FFFFFF">删除</td>
         <td height="25" bgcolor="#FFFFFF"><A href="/biWeb/etlmain.do" target="mainFrame">分析</A></td>
      </tr>
  </body>
</html:html>
