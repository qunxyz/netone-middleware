<%@ page contentType="text/html; charset=GBK"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUserMgr"%>
<%@page import="oe.security3a.sso.onlineuser.DefaultOnlineUserMgr"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUser"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
	OnlineUser oluser = olmgr.getOnlineUser(request);

	String loginname = oluser.getLoginname();
%>
<html>
	<head>
		<title>新增页组</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<!-- 原始代码开始位置:
		   <SCRIPT type="text/javascript"
		   src="<%=path%>\include\js\modelshow\personmodel.js"></SCRIPT>
		   <SCRIPT type="text/javascript" src="<%=path%>\include\js\checkrs.js"></SCRIPT>
		   <script type="text/javascript">
		   修改时间:2009-2-06
		   原始代码结束位置:-->

		<!-- 改后代码开始位置 -->
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/modelshow/personmodel.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/checkrs.js"></SCRIPT>
		<script type="text/javascript">
		<!-- 改后代码结束位置 -->
		function selectthis(name,naturalname){
		//alert(id);
			document.getElementById('userid').value=name+"["+naturalname+"]";
		}
		function tiao()
		{
		window.alert("ok");
		}
		function sselected(text,id){
			var checkrs=checkNumber(id);
			if(!checkrs){
				alert('模型ID:'+id);
				return;
			}
		   	document.getElementById('template').value=text+"["+id+"]";
		}
		 
		
		function done(){
			var naturalname=document.getElementById('naturalname').value;
			var rs=checkNaturalname(naturalname);
			if(rs!=''){
				alert(rs);
				return;
			}
			var modelname=document.getElementById('modelname').value;
			if(modelname==''){
				alert('页组显示名称 不允许为空');
				return;
			}

			this.document.forms[0].submit();	
		}
		</script>
	</head>
	<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
		style="font: 14px">

		<!--
		 原代码开始位置:
		<html:form action="infomodelnew.do?newFlag=new" >
		修改日期:2009-2-6
		原代码结束位置:-->
		<!-- 改后代码开始位置 -->
		<form action="infomodelnew.do?loginName=<%=loginname%>" id="from" name="from">
			<input type='hidden' name='newFlag' value='new' />
			<!-- 改后代码结束位置 -->
			<input type='hidden' name='path' value='${param.path}' />
			<h4>
				新增页组
			</h4>
			<table style="width: 100%;">
				<tr>
					<td>
						页组名称:
					</td>
					<td>
						<!--  改后代码开始位置 -->
						<input name="naturalname" id="naturalname" size="40" value=""></input>
						<!--  改后代码结束位置 -->
						<!-- 
						原代码开始位置:
						<html:text property="naturalname" size="40" value=""></html:text>>
						修改日期:2009-2-6
						原代码结束位置:-->
						[格式：字母开头后接字母或数字或下划线]
					</td>
				</tr>
				<tr>
					<td>
						页组显示名称:
					</td>
					<td>
						<!--  改后代码开始位置 -->
						<input name="modelname" id="modelname" size="40" value=""></input>
						<!--  改后代码结束位置 -->
						<!--
						原代码开始位置:
						<html:text property="modelname" size="40" value=""></html:text> 
						修改日期:2009-2-6
						原代码结束位置:-->

					</td>
				</tr>
				<tr>
					<td>
						页组样式
					</td>
					<td>

						<input type='text' name='userid' id='userid'
							value='标准样式[CSSFILE.CSSFILE.DEFAULT]' readonly>
						<input type='button' value='选择样式' onClick='listStyle()'>
					</td>
				</tr>
				<tr>
					<td>
						选择模板
					</td>
					<td>
						<input type='text' name='template' id='template' value='' readonly>
						<input type='button' value='选择模板' onClick='listTemplate()'>
					</td>
				</tr>
				<tr>
					<td>
						页组描述
					</td>
					<td>
						<html:textarea property="description" cols="90" rows="10"></html:textarea>
					</td>
				</tr>


				<tr style="display: none">
					<td>
						存取模式
					</td>
					<td>
						<html:select property="accessmode" style="width:100">
							<logic:present name="accessmodeList">
								<html:options collection="accessmodeList" property="value"
									labelProperty="label" />
							</logic:present>
						</html:select>
					</td>
				</tr>
				<tr style="display: none">
					<td>
						WEB布局内部格式
					</td>
					<td>
						<html:textarea property="infoxml" cols="90" rows="10"></html:textarea>
					</td>
				</tr>

			</table>
			<table align="center" style="width: 100%;">
				<tr height="23px" valign="bottom" align="center">
					<td align="center" style="width: 100%">

						<input type="button" class="butt" value="新增"
							onClick="javascript:done();" />
						<input type="button" class="butt" value="取消"
							onclick="window.close();" />
					</td>
				</tr>
			</table>
			<!--  改后代码开始位置 -->
		</form>
		<!--  改后代码结束位置 -->
		<!--
		原代码开始位置:
		</html:form>
		修改日期:2009-2-6
          原代码结束位置:-->
	</body>
</html>
