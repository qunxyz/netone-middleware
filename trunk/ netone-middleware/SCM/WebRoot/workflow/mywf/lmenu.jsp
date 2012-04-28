<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'lmenu.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>workflow/styles/default.css" type="text/css" rel="stylesheet" />
	
	<style>
	</style>
	
	<script src="<%=basePath%>workflow/js/jquery-1.3.2.min.js" language="javascript" type="text/javascript"></script>
    <script src="<%=basePath%>workflow/js/jquery.collapsor.js" language="javascript" type="text/javascript"></script>
    <script src="<%=basePath%>workflow/js/jquery.slider.js" language="javascript" type="text/javascript"></script>
    <script language="javascript" type="text/javascript">
        $(function() {
            $('#sliding-navigation li.sliding-element a').slidinate().collapsor().filter('.selectedSlider').next('ul').show();
        });
    </script>

  </head>
  
  <body>
  
  <div id="navigation-block">
        <ul id="sliding-navigation">
            <li class="sliding-element">
                <a href="/default.aspx">所有我代办处理的工单</a>
            </li>
            <li class="sliding-element">
                <a href="/AspDotNet.aspx">所有我已处理的工单</a>
            </li>
            <li class="sliding-element">
                <a href="/Php.aspx">所有我已办结的工单</a>
            </li>
            <li class="sliding-element">
                <a href="/Java.aspx">所有我已阅的工单</a>
            </li>
            <li class="sliding-element">
                <a href="#">全业务建设流程</a>
                <ul>
                    <li class="sliding-element">
                        <a href="/Javascript.aspx">待处理全业务建设工单</a>
                        <ul>
                   			<li class="sliding-element">
                        		<a href="/Javascript.aspx">Javascript/jQuery Main</a>
                    		</li>
                    		<li class="sliding-element">
                     		   <a href="/dlb/DLBDocumentation.aspx">Dual Listbox Plug-in</a>
                    		</li>
                    		<li class="sliding-element">
                        		<a href="/MyMenu.aspx">My Menu</a>
                    		</li>
                		</ul>
                    </li>
                    <li class="sliding-element">
                        <a href="/dlb/DLBDocumentation.aspx">已处理全业务建设工单</a>
                    </li>
                    <li class="sliding-element">
                        <a href="/MyMenu.aspx">已办结全业务建设工单</a>
                    </li>
                    <li class="sliding-element">
                        <a href="/MyMenu.aspx">在建全业务建设工单</a>
                    </li>
                    <li class="sliding-element">
                        <a href="/MyMenu.aspx">所有全业务建设工单</a>
                    </li>
                </ul>
            </li>
            <li class="sliding-element">
                <a href="/AboutMe.aspx">全业务维护流程</a>
            </li>
            <li class="sliding-element">
                <a href="/AboutMe.aspx">IT支持流程</a>
            </li>
            <li class="sliding-element">
                <a href="/AboutMe.aspx">取数/优惠流程</a>
            </li>
        </ul>
    </div>
    
  </body>
</html>
