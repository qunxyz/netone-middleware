<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% String cssURL = request.getContextPath() + "/styles";
   String scriptsURL = request.getContextPath() + "/script";
   String imgURL = request.getContextPath() + "/images/default";
   System.out.println("----------------------------------");
   System.out.println(request.getAttribute("message"));
%>

<html>
<head>
<title></title>
<style type="text/css">
<!--
.error{
	color:#FF0000;
}
//-->
</style>
</head>
<body bgcolor="#ffffff">

    <br />
    <br />
    <table width="90%" align="center" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="40%">&nbsp;</td>
        <td width="60%">&nbsp;</td>
      </tr>
      <tr>
        <td rowspan="3" align="center">&nbsp;<img src="<%=imgURL%>/err.gif" width="168" height="148" border="0"></td>
          <td>&nbsp;很抱歉，系统出现错误，详细信息：</td>
      </tr>
      <tr>
        <td>&nbsp;<font color="red"><c:out value='${message}'/></font></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>
          <table  border="0" cellpadding="0" cellspacing="0" class="buttonOnTable" onClick="history.back()" >
            <tbody>
                <tr>
                  <td><img src="<%=imgURL%>/but1-left.gif" width="5" height="20" border="0"></td>
                    <td background="<%=imgURL%>/but1-mid.gif" >返回</td>
                    <td><img src="<%=imgURL%>/but1-right.gif" width="5" height="20" border="0"></td>
                </tr>
            </tbody>
          </table>
                    </td>
      </tr>
    </table>


</body>
</html>
