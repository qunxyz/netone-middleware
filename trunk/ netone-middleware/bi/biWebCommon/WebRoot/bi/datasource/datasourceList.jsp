<%@ page contentType="text/html; charset=GB2312" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>

<%
	String path = request.getContextPath();
%>


<html xmlns:v>
<head>
<title>�ޱ����ĵ�</title>
<style>
v\:*{behavior:url(#default#VML);}
</style>
<link href="<%=path%>/include/css/style.css" rel="stylesheet" type="text/css">

<script src="<%=path%>/include/js/biModel.js" type="text/javascript"></script>
<SCRIPT src="<%=path%>/include/js/datamodelobj.js" type="text/javascript"></script>
<SCRIPT src="<%=path%>/include/js/targetmain.js" type="text/javascript"></script>
</head>


<body oncontextmenu="return false">
	
	<xml id="xmldslist" src="<%=path%>/servlet/XmlDataSourceSvl"></xml>
	
	
  <form action="" method="POST">
  <TABLE>
  <TR>
  <TD>
  
  <v:rect style="POSITION:absolute;Z-INDEX:0;LEFT:0px;TOP:10px;width:200;height:500;">
  <table>
  <tr><td>����Դ1</td></tr>  
     <%=request.getAttribute("str")%> 
  </table>
  </v:rect>
  </TD>
  <TD valign="top">
  <v:rect style="POSITION:absolute;Z-INDEX:0;LEFT:202px;TOP:10px;width:700;height:500;">
  <table>
    <tr>
    <td>
 
      <a href="#" onclick="new trackFactory('beenLine','');">ֱ��</a>&nbsp;&nbsp;
      <a href="#" onclick="new trackFactory('ZLine','');">����</a>&nbsp;&nbsp;<a href="#" onclick="window.open('targetcol.jsp','','width=500,height=400,resizable=yes,status=yes')">ָ������</a>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="saveBusModel()">����</a>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��άҵ������ģ��
    </td>
    </tr>
  </table>
  </v:rect>
  </TD>
  </TR>
  </TABLE>
  
<span id="workTrackMenu" name="menu" style="DISPLAY:none; Z-INDEX: 102; POSITION: absolute; BACKGROUND-COLOR: #CCCCCC">
<input id="delete"class=workMune onclick='workTrackMenu.style.display="none";new  deleteEvent("action");'type="button" value="ɾ��">
<hr style="WIDTH: 50px" color="white" SIZE="0">
<input class=workMune onclick='workTrackMenu.style.display="none"' type="button" value="ȡ��">
</span>

<span id="lineMenu" name="trackLineMenu" style="DISPLAY:none; Z-INDEX: 102; POSITION: absolute; BACKGROUND-COLOR: #CCCCCC">
<input id="setProperty" onclick='lineMenu.style.display="none";lineAttribute()' class=workMune type="button" value="����" ><br>
<input id="delete"class=workMune onclick='lineMenu.style.display="none";new  deleteEvent("line");'type="button" value="ɾ��">
<hr style="WIDTH: 50px" color="white" SIZE="0">
<input class=workMune onclick='lineMenu.style.display="none";' type="button" value="ȡ��">
</span>

<input id="defineActionObjName" type="hidden">
<input id="defineLineName" type="hidden">
<input id="defineLineTrueId" type="hidden">
<input id="defineActionTrueId" type="hidden">
<input id="relationData" type="hidden">
<input id="newProcess" name="newProcessAttribute" type="hidden" value="processId,processname,extendName&extendValue|extendName1&extendValue1|">

<script>
document.onmousedown = engage
document.onmousemove = dragIt
document.onmouseup = release
</script>
</form>
</body>
</html>
