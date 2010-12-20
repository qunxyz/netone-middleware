<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html lang="true">
<head>
	<html:base />

	<title>Dic.jsp</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<SCRIPT type="text/javascript" src='<%=path%>/include/js/prototype.js'></SCRIPT>
	<SCRIPT type="text/javascript" src='<%=path%>/include/js/bussFileManage.js'></SCRIPT>
</head>

<body>
<script language="javascript">

	//判断路径
	function checkAction(){
		/*获取结果生成XML文档,有2种方式，第1种是默认生成所有，第2种是选择表和字段生成，这是第1种 */
		if(document.form1.chooseList.options.length==0){
			form1.action="<%=path%>/dic.do?task=getResult";
			
		/*获取结果生成XML文档,有2种方式，第1种是默认生成所有，第2种是选择表和字段生成，这是第2种 */
		} else {
			form1.action="<%=path%>/dic.do?task=getResult2&result="+aa();
		}
	}
	
	//把chooseList里的内容放在数组里当作参数传给Action
	function aa(){
		var s = document.form1.chooseList;
		var length = s.options.length;
		var choose = new Array();
		for(var  i=0;i<length;i++){ 
			choose[i] = s.options[i].value;;
		}
		return choose;
	}
</script>
	<html:form action="/dic?task=getResult" method="post" styleId="form1">
		<table border="0">
			<tr>
				<td>
					数据库名称
				</td>
				<td>
					<input type="text" name="databaseName" value="">
					<input type="button" name="button" value="提交" " onclick="tableListview();">
				</td>
				<td>
					<div id="tableList"></div>
				</td>
				<td>
					<div id="columnList"></div>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" name="button" value="删除字段" " onclick="deleteListview();">
				</td>
				<td>
					<select multiple=true size=20 id="chooseList">
					</select>
				</td>
			</tr>
			<tr>
				<td height="28" colspan="2">
					<input type="submit" name="Submit" value="确认提交" onclick="checkAction();">
					&nbsp;
					<input type='hidden' value='<%=path%>' name='pathinfo'>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
