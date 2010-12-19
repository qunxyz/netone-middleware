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
			组列表
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
			function editInfoGroup(opr){
			 if(opr=="new"){
		     	  var url="<%=path%>/cms/infogroup/infoGroupNew.jsp";
			   	window.open(url,'新增组','width=800,height=600,left=100,top=100,resizable=yes');
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
			    alert("请选择要操作的资讯元");
			    return;
			  } else{
			  if(opr=="edit"){
			     var url="<%=path%>/infogroupedit.do?id="+id;
			   window.open(url,'编辑组','width=800,height=600,left=100,top=100,resizable=yes');
			   }else if(opr=="see"){
			     var url="<%=path%>/infogroupedit.do?seeFlag=true&id="+id;
			   window.open(url,'查看组','width=800,height=600,left=100,top=100,resizable=yes');
			   }else if(opr=="delete"){
			     if (confirm("您确定要执行此操作?")) {
		     	 //window.location="<%=path%>/infomodellist.do?flag=delete&&id="+id;
		     	  document.form1.action="<%=path%>/infogroupdelete.do?id="+id;
                  document.form1.submit();	
		     	 }
			   }			   
			}
		}
		</SCRIPT>
	</head>

	<body BGCOLOR=#E2E2E2 LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">
		<form action="infogrouplist.do" name="form1" method="post">

			<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" style="width: 100%;table-layout: fixed;" id="box_table">
				<thead>
				<tr >
					<td width="5%"  class="td-tittl01">
						选择
					</td>
					<td  class="td-tittl01">
						组名称
					</td>
					<td  class="td-tittl01">
						组ID
					</td>
					<td  class="td-tittl01">
						组描述
					</td>
				</tr>
					</thead>
				<tbody>
				<logic:present name="cmsinfogrouplist">
					<logic:iterate id="row" name="cmsinfogrouplist" >
						<tr>
							<td  align="center">
								<input type="radio" name="radioId" value="<bean:write name="row"  property="groupid"/>" />
							</td>
							<td>
								<bean:write name="row" property="groupname" />
							</td>
							<td >
								<bean:write name="row" property="groupid" />
							</td>
							<td>
								<bean:write name="row" property="description" />
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
			<table align="center" style="width:100%;">
				<tr height="23px" valign="bottom" align="center">
					<td align="center" style="width:100%">			
						<div align="right">
						<input type="button" value="查看" onclick="editInfoGroup('see')" class="butt" />
						<input type="button" value="新增" onclick="editInfoGroup('new')" class="butt" />
						<input type="button" value="修改" onclick="editInfoGroup('edit')" class="butt" />
						<input type="button" value="删除" onclick="editInfoGroup('delete')" class="butt" />
						</div>
					</td>
				</tr>
			</TABLE>
		</form>
	</body>
</html>
