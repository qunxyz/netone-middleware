
<%@ page language="java" pageEncoding="GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
  <head>
    <html:base />
    <title>filterCondition.jsp</title>
    <SCRIPT src="/biWeb/include/js/datamodel/design/biModel.js" type="text/javascript"></script>
<link href="/biWeb/include/css/style.css" rel="stylesheet" type="text/css">
  </head>
  
  <%
    String datasetid = request.getParameter("datasetid").toString();
    System.out.println("datasetid:" + datasetid);
  %>
  <body>
    
    <FORM name="filterForm">
    <TABLE align="center">
    <TR>
         <TD align="center">
          <H4>�༭���ݼ�</H4>
         </TD>
       </TR>
        <TR>
         <TD align="center">
            ���ݼ���������<INPUT type="text" name="dataSetName">
         </TD>
       </TR>
        <TR>
         <TD align="left">
            ���ݼ���������:
         </TD>
       </TR>
       <TR>
         <TD align="center">
            <TEXTAREA name="filtCondition" rows="3" cols="30"></TEXTAREA>
         </TD>
       </TR>
       <TR>
         <TD align="center">
            <INPUT type="button" value="����" onclick="saveDataSetFilter('<%=datasetid%>')">
         </TD>
       </TR>
    </TABLE>
    </FORM>
  </body>
</html:html>
