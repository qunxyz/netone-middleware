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

	//�ж�·��
	function checkAction(){
		/*��ȡ�������XML�ĵ�,��2�ַ�ʽ����1����Ĭ���������У���2����ѡ�����ֶ����ɣ����ǵ�1�� */
		if(document.form1.chooseList.options.length==0){
			form1.action="<%=path%>/dic.do?task=getResult";
			
		/*��ȡ�������XML�ĵ�,��2�ַ�ʽ����1����Ĭ���������У���2����ѡ�����ֶ����ɣ����ǵ�2�� */
		} else {
			form1.action="<%=path%>/dic.do?task=getResult2&result="+aa();
		}
	}
	
	//��chooseList������ݷ��������ﵱ����������Action
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
					���ݿ�����
				</td>
				<td>
					<input type="text" name="databaseName" value="">
					<input type="button" name="button" value="�ύ" " onclick="tableListview();">
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
					<input type="button" name="button" value="ɾ���ֶ�" " onclick="deleteListview();">
				</td>
				<td>
					<select multiple=true size=20 id="chooseList">
					</select>
				</td>
			</tr>
			<tr>
				<td height="28" colspan="2">
					<input type="submit" name="Submit" value="ȷ���ύ" onclick="checkAction();">
					&nbsp;
					<input type='hidden' value='<%=path%>' name='pathinfo'>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>
