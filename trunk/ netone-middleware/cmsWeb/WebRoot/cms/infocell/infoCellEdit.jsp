<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%String path = request.getContextPath();
			String login = (String) request.getAttribute("nologin");
			String done = (String) request.getAttribute("done");
%>
<html>
	<head>
		<title>
			±à¼­
		</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="<%=path%>/include/js/cell/cell.js"></script>
		<script src="<%=path%>/include/js/prototype.js"></script>
		<script src="<%=path%>/include/js/util.js"></script>
		<script src="<%=path%>/include/js/floatdiv/cut2.js"></script>
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet" type="text/css">
		<script>
   function todo(){
   	 if('<%=done%>'!=''){
   	 	alert('<%=done%>');
   	 }
   }
   
   function test(){
        var id=document.getElementById('cellid').value;
   		var url="<%=path%>/cms/detailinfo.jsp?cellid="+id;
		 window.open(url,'²âÊÔ','width=800,height=600,left=100,top=100,resizable=yes');
   }
   
   function reflashtree(){
		 window.open('/cmsWeb/cms/tree/tree.jsp','leftFrame','');
   }
   </script>
	</head>

	<body onLoad="todo();" BGCOLOR=#E2E2E2 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">
		<html:form action="infocelledit.do?editFlag=update">
			<html:hidden property="extendattribute" />
			<logic:notEqual name="seeFlag" value="true">
				<html:hidden property="cellid" />

				<table>

					<tr>
						<td>
							ËùÊô×é&nbsp;
							<html:select property="belongto">
								<OPTION value=""></OPTION>
								<logic:present name="groupList">
									<html:options collection="groupList" property="value" labelProperty="label" />
								</logic:present>
							</html:select>
							J++½Å±¾Ãû³Æ&nbsp;
							<html:text property="cellname"></html:text>
							&nbsp;&nbsp;
							<html:checkbox property="intime"></html:checkbox>
							»º´æ &nbsp;

						</td>
					</tr>

					<tr>
						<td>
							<html:textarea property="body" cols="100" rows="30"></html:textarea>
							<INPUT type="hidden" name="bodyTemp" />
						</td>

					</tr>
					<tr height="23px" valign="bottom" align="center">

						<td>

							<input type="button" class="butt" value="     ¸ü ÐÂ     " onclick="commit('<%=path%>/infocelledit.do?editFlag=update');" />
							<input type="button" class="butt" value="     ²â ÊÔ     " onclick="test();" />
						</td>
					</tr>
				</table>

			</logic:notEqual>
		</html:form>
	</body>
</html>
