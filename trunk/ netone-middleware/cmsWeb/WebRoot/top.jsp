<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%String path = request.getContextPath();
%>
<html lang="true">
	<head>

		<title>
			信息元列表
		</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<meta http-equiv="title" content="">

		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/calendar.js"></SCRIPT>
		<SCRIPT type="text/javascript">
            function confirmClick(){             
               	window.open("<%=path%>/showModelAction.do?modelid="+modelid,"content");        
            }
            
            function history(){
            	document.frames.item('content').location='/cmsWeb/showModelAction.do?date='+ document.getElementById('timeselect').value;
            }
         
         </SCRIPT>
	</head>

	<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px" bgcolor="#EEF6FE">
		<form action="">
			<table align="left" style="width: 100%;">
				<tr>
					<td align="left">
						<INPUT type="button" name="comfirmButt" value="首页" class="butt" onclick="javascript:window.open('<%=path%>/showModelAction.do','content');">
						<INPUT type="button" name="button1" value="自定义界面" class="butt" onclick="javascript:window.open('<%=path%>/showFloatDiv.do','content');">
						<INPUT type="button" name="button1" value="内容管理" class="butt" onclick="javascript:window.open('<%=path%>/configureIndex.jsp');">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
