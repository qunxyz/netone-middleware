<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String done = (String) request.getAttribute("done");
%>
<html lang="true">
	<head>

		<title>portal列表</title>
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
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
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
			function editInfoModel(opr){
			 if(opr=="new"){
		     	  var url="<%=path%>/infomodelnew.do";
			   	window.open(url,'新增WEB布局','width=800,height=450,left=300,top=100,resizable=yes');
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
			    alert("请选择要操作的WEB布局");
			    return;
			  } else{
			   if(opr=="tomodel"){
			     var url="<%=path%>/infomodeledit.do?tomodel=ok&id="+id;
			   	 window.open(url,'_blank','width=400,height=400,left=300,top=100,resizable=yes');
			   }else  if(opr=="edit"){
			     var url="<%=path%>/infomodeledit.do?id="+id;
			   window.open(url,'编辑WEB布局','width=800,height=400,left=300,top=100,resizable=yes');
			   }else if(opr=="see"){
			     var url="<%=path%>/infomodeledit.do?seeFlag=true&id="+id;
			   window.open(url,'查看WEB布局','width=800,height=400,left=300,top=100,resizable=yes');
			   }else if(opr=="design"||opr=="designInCol"){
			     var url="<%=path%>/showFloatDiv.do?modelid="+id+"&opr="+opr;
			     window.open(url,'WEB布局设计');
			   }else if(opr=="designSimple"){
			     var url="<%=path%>/showFloatDiv2.do?modelid="+id;
			     window.open(url,'_Des');
			   } else if(opr=="delete"){
			     if (confirm("您确定要执行此操作?")) {
		     	 //window.location="<%=path%>/infomodellist.do?flag=delete&&id="+id;
		     	  document.form1.action="<%=path%>/infomodellist.do?flag=delete&id="+id;
                  document.form1.submit();	
		     	 }
			   }else if(opr=='view'){
			   			     var url="<%=path%>/showFloatDiv.do?view=1&modelid="+id;
			   window.open(url,'WEB布局预览');
			   }else if(opr=='view2'){
			   			     var url="<%=path%>/showFloatDiv2.do?view=1&modelid="+id;
			   window.open(url,'WEB布局预览');
			   }
			}
		}
		
		
   function todo(){
   		if('<%=done%>'!=''){
   	 alert('<%=done%>');
   	 }
   }

		</SCRIPT>
	</head>

	<body onLoad="todo();" BGCOLOR=#E2E2E2 LEFTMARGIN=0 TOPMARGIN=0
		MARGINWIDTH=0 MARGINHEIGHT=0 style="font:14px">
		<form action="infomodellist.do" name="form1" method="post">


			<table width="98%" border="0" align="center" cellpadding="2"
				cellspacing="1" style="width: 100%;table-layout: fixed;"
				id="box_table">
				<thead>
					<tr class="td-tittl01">
						<td width="5%" class="td-tittl">
							选择
						</td>
						<td class="td-tittl">
							WEB布局名
						</td>
						<td class="td-tittl">
							设计者
						</td>

						<td class="td-tittl">
							描述
						</td>
					</tr>
				</thead>
				<tbody>
					<logic:present name="cmsinfomodellist">
						<logic:iterate id="row" name="cmsinfomodellist">
							<tr>
								<td>
									<input type="radio" name="radioId"
										value="<bean:write name="row"  property="modelid"/>" />
								</td>
								<td>
									<bean:write name="row" property="modelname" />
								</td>
								<td>
									<bean:write name="row" property="participant" filter="false" />
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
					<td align="left" style="width:100%">
						WEB布局名:
						<input type="text" name="searchName"
							value="${paramMap.searchName}">
						<INPUT type="submit" class="butt" value="查询">
						&nbsp;&nbsp;

						<input type="button" class="butt" value="新增"
							onclick="editInfoModel('new')" />
						<input type="button" class="butt" value="修改"
							onclick="editInfoModel('edit')" />
						<input type="button" class="butt" value="删除"
							onclick="editInfoModel('delete')" />
						<input type="button" class="butt" value="制作模板"
							onclick="editInfoModel('tomodel')" />
						&nbsp;
						<input type="button" class="butt" value="设计版面"
							onclick="editInfoModel('design')" />
						<input type="button" class="butt" value="预览结果界面"
							onclick="editInfoModel('view')" />
						<hr>
					</td>
				</tr>
				<tr>
					<td>
						<INPUt type="button" class="butt" value="保存页面缓存"
							onclick="window.open('/cmsWeb/infomodellist.do?init=hist','_blank','height=200, width=300, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">

						<INPUt type="button" class="butt" value="初始化页面缓存 "
							onclick="window.open('/cmsWeb/infomodellist.do?init=all','_blank','height=200, width=300, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">

						<INPUt type="button" class="butt" value="初始化静态页面 "
							onclick="window.open('/cmsWeb/infomodellist.do?init=static','_blank','height=200, width=300, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">

						<INPUt type="button" class="butt" value="初始化排名 "
							onclick="window.open('/cmsWeb/infomodellist.do?init=order','_blank','height=200, width=300, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">

						<INPUt type="button" class="butt" value="初始化设计模板 "
							onclick="window.open('/cmsWeb/infomodellist.do?init=model','_blank','height=200, width=300, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">
						<INPUt type="button" class="butt" value="查看设计模板 "
							onclick="window.open('/cmsWeb/templateModel.do?onlyview=yes','_blank','height=400, width=600, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');">

					</td>
				</tr>
			</TABLE>
		</form>
	</body>
</html>
