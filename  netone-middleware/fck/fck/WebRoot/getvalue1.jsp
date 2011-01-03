<%@ page language="java" contentType="text/html; charset=GB18030"
    pageEncoding="GB18030"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="FCK" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>使用fck进行修改1</title>
</head>
<body>
   <form action="result.jsp" method="post">
    <table width="100%">
    <tr>
    <td>
    <div align="center">
      	  <FCK:editor id="infoContent" basePath="/fck/FCKeditor/"
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
              flashUploadURL="/fck/FCKeditor/editor/filemanager/upload/simpleuploader?Type=Flash"
              	
              >
              <c:import url="${param.path }"></c:import>
      </FCK:editor>	
    </div>
    </td>
    </tr>
    </table>
    <table>
    <tr>
    <td>
    <input type="submit" value="提交">
    </td>
    </tr>
    </table>
    </form>
</body>
</html>