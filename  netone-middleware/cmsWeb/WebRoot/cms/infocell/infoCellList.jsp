<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%String path = request.getContextPath();%>
<html lang="true">
	<head>
		<title>
			portalet列表
		</title>
		<style type="text/css">
 BODY {
        BORDER-TOP-WIDTH: 4px; 
		SCROLLBAR-FACE-COLOR: #EDF0F6; 
		BORDER-LEFT-WIDTH: 4px; 
		FONT-SIZE: 9pt; 
		BORDER-BOTTOM-WIDTH: 4px; 
		SCROLLBAR-HIGHLIGHT-COLOR: #ffffff; 
		SCROLLBAR-SHADOW-COLOR: #B3CDEA; 
		SCROLLBAR-3DLIGHT-COLOR: #B3CDEA; 
		SCROLLBAR-ARROW-COLOR: #B0C7E1; 
		SCROLLBAR-TRACK-COLOR: #F7FBFF; 
		SCROLLBAR-DARKSHADOW-COLOR: #ffffff; 
		SCROLLBAR-BASE-COLOR: #EEF7FF; 
		BORDER-RIGHT-WIDTH: 4px;
}
</style>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="<%=path%>/include/js/page.js"></script>
		<style type="text/css">
<!--
.STYLE1 {color: #D61418}
-->
</style>


		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<SCRIPT type="text/javascript">
			function editInfoCell(opr){
			 if(opr=="new"){
		     	var url="<%=path%>/infocellnew.do" ;
			   	window.open(url,'mainFrame');
			   	window.close();
				return;
			   }
			  var flag=false;
			   var id;
			  var dataValues=document.getElementsByName("radioId");
			  if(dataValues!=null){
			    for(var i=0;i<dataValues.length;i++){
			      if(dataValues[i].checked){
			        flag=true;
			        id=dataValues[i].value;
			        break;
			      }
			    }
			  }
			  if(flag==false){
			    alert("请选择要操作的Portalet");
			    return;
			  } else{
			  if(opr=="edit"){

			    
			     
			     //将J++元素添加入布局设计界面

	var sel = window.opener.document.getElementById("valueid").value = id;
	window.opener.document.getElementById("valuedo").onclick();
	alert("已添到设计面板中,您可以通过对该页Portalet进行修改");

			     
			   }else if(opr=="delete"){
			     if (confirm("您确定要执行此操作?")) {
		     	  var url="<%=path%>/infocelldelete.do?id="+id;
                  window.open(url);

		     	 }
			   }else if(opr=='test'){
			   	 var url="<%=path%>/cms/detailinfo.jsp?cellid="+id;
			   	 window.open(url,'测试','width=800,height=600,left=100,top=100,resizable=yes');
			   }
			}
		}
		</script>

	</head>

	<BODY  LEFTMARGIN=0 TOPMARGIN=10 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">

		<form action="infocelllist.do" name="form1" method="post">
			<table style="font:14px">
				<tr>
					<td>
						&nbsp;页中文名：
						<input type="text" name="searchName" value="${paramMap.searchName}">
						&nbsp;归属组:
						<select name="group">
							<OPTION value=""></OPTION>
							<c:forEach items="${groupList}" var="groupinfo">
								<OPTION value="${groupinfo.value}">
									${groupinfo.label}
								</OPTION>
							</c:forEach>
						</select>
						&nbsp;缓存:
						<input type="checkbox" name="intime">
						<INPUT type="submit" class="butt" value="查询">

						<input type="button" class="butt" value="修改" onclick="editInfoCell('edit');" />
						<input type="button" class="butt" value="删除" onclick="editInfoCell('delete');" />
						<input type="button" class="butt" value="测试" onclick="editInfoCell('test');" />
					</td>
				</tr>
			</table>
			<table border="0" align="center" cellpadding="2" cellspacing="1" style="width: 100%;table-layout: fixed;" id="box_table">

				<tbody>
					<logic:present name="cmsInfocelllist">
						<logic:iterate id="row" name="cmsInfocelllist" >
							<tr>
								<td align="center" width="8%">
									<input type="radio" name="radioId" value="<bean:write name="row"  property="cellid"/>" />
								</td>
								<td>
									<bean:write name="row" property="cellid" />
								</td>
								<td>
									<bean:write name="row" property="cellname" />
								</td>
								<td>
									<bean:write name="row" property="belongto" />
								</td>
								<td>
									<bean:write name="row" property="participant" filter="false" />
								</td>
							</tr>
						</logic:iterate>
					</logic:present>
				</tbody>
			</table>
			<script type="text/javascript">
						var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
						pginfo.write();			
			</script>
		</form>
		<script type="text/javascript">
	 	var value = "${paramMap.intime}";
	 	if(value){
	 		document.all["intime"].checked = "true" ;
	 	}
	</script>
	</body>
</html>
