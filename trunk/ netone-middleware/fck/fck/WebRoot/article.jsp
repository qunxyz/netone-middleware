<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="FCK" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>use FCKeditor</title>
    
    <script type="text/javascript" src="FCKeditor/fckeditor.js"></script> 
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <form action="article.jsp" method="post" name="form2">
    <table width="100%">
    <tr>
    <td>
   
      	  <FCK:editor id="content" basePath="/fck/FCKeditor/"
              width="100%"
              height="400"              
              skinPath="/fck/FCKeditor/editor/skins/silver/"
              defaultLanguage="zh-cn"
              tabSpaces="8"
              toolbarSet="Default"
			  imageBrowserURL="/fck/FCKeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector"
              linkBrowserURL="/fck/FCKeditor/editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector"
              flashBrowserURL="/fck/FCKeditor/editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector"
              imageUploadURL="/fck/FCKeditor/editor/filemanager/upload/simpleuploader?Type=Image"
              linkUploadURL="/fck/FCKeditor/editor/filemanager/upload/simpleuploader?Type=File"
              flashUploadURL="/fck/FCKeditor/editor/filemanager/upload/simpleuploader?Type=Flash">
              ${param.content }
      </FCK:editor>	
   
    </td>
    </tr>
    <tr style="display: none">
    <td>
    <input type="submit" value="Íê³É±à¼­" id="submit" name="submit">
    </td>
    </tr>
    
    </table>
   
  
    </form>
  </body>
</html>
