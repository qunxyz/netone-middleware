
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>���ݼ��й���</title>
  </head>
<link href="/biWeb/include/css/style.css" rel="stylesheet" type="text/css">
<SCRIPT src="/biWeb/include/js/datamodel/design/biModel.js" type="text/javascript"></script>
  <%  
     
     //�����ߵĽ������ݼ��ڵ�����  �ڵ���������Ϊ������ԴID + "." + ���ݼ�ID(������)
     String toDsName = String.valueOf(request.getAttribute("toDsName"));
     
     //�����ߵĿ�ʼ���ݼ��ڵ�����
     String fromDsName = String.valueOf(request.getAttribute("fromDsName"));

     String toDsId =toDsName.substring(toDsName.indexOf(".")+1,toDsName.length());
     
     String fromDsId =fromDsName.substring(fromDsName.indexOf(".")+1,fromDsName.length());
     
  %>
  <body>
  <BR> 
  <CENTER><H4>���ݼ�����</H4></CENTER>
   <form name="unitForm">
  <table width="301" border="1" cellpadding="0" cellspacing="0" bordercolor="#999999" bgcolor="#666666">
    <tr bgcolor="#FFFFFF">
      <td>�ֶ��б�</td>
      <td>���ݼ���</td>
      <td>�ֶ��б�</td>
    </tr>
    <tr bgcolor="#FFFFFF">
      <td><select name="formColumn">
      <%String[] fromDsIdField=(String[])request.getAttribute("fromDsIdField");
        for(int i=0;i<fromDsIdField.length;i++){
      %>
        <option value="<%=fromDsIdField[i]%>"><%=fromDsIdField[i].split(",")[0]%></option>
    <%}%>
      </select></td>
      <td><%=toDsId%></td>
      <td><select name="toColumn">
         <%String[] toDsIdField=(String[])request.getAttribute("toDsIdField");
        for(int i=0;i<toDsIdField.length;i++){
      %>
        <option value="<%=toDsIdField[i]%>"><%=toDsIdField[i].split(",")[0]%></option>
    <%}%>
      </select></td>
    </tr>
    <tr bgcolor="#FFFFFF">
      <td>&nbsp;</td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr bgcolor="#FFFFFF">
      <td colspan=3 align=center><input type="button" value="����" onClick="saveUnitColumn('<%=fromDsId%>','<%=fromDsName%>','<%=toDsId%>','<%=toDsName%>')"></td>
    </tr>
  </table>
 </form>
  </body>
</html>
