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

		<title>portal�б�</title>
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
			   	window.open(url,'����WEB����','width=800,height=450,left=300,top=100,resizable=yes');
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
			    alert("��ѡ��Ҫ������WEB����");
			    return;
			  } else{
			   if(opr=="tomodel"){
			     var url="<%=path%>/infomodeledit.do?tomodel=ok&id="+id;
			   	 window.open(url,'_blank','width=400,height=400,left=300,top=100,resizable=yes');
			   }else  if(opr=="edit"){
			     var url="<%=path%>/infomodeledit.do?id="+id;
			   window.open(url,'�༭WEB����','width=800,height=400,left=300,top=100,resizable=yes');
			   }else if(opr=="see"){
			     var url="<%=path%>/infomodeledit.do?seeFlag=true&id="+id;
			   window.open(url,'�鿴WEB����','width=800,height=400,left=300,top=100,resizable=yes');
			   }else if(opr=="design"||opr=="designInCol"){
			     var url="<%=path%>/showFloatDiv.do?modelid="+id+"&opr="+opr;
			     window.open(url,'WEB�������');
			   }else if(opr=="designSimple"){
			     var url="<%=path%>/showFloatDiv2.do?modelid="+id;
			     window.open(url,'_Des');
			   } else if(opr=="delete"){
			     if (confirm("��ȷ��Ҫִ�д˲���?")) {
		     	 //window.location="<%=path%>/infomodellist.do?flag=delete&&id="+id;
		     	  document.form1.action="<%=path%>/infomodellist.do?flag=delete&id="+id;
                  document.form1.submit();	
		     	 }
			   }else if(opr=='view'){
			   			     var url="<%=path%>/showFloatDiv.do?view=1&modelid="+id;
			   window.open(url,'WEB����Ԥ��');
			   }else if(opr=='view2'){
			   			     var url="<%=path%>/showFloatDiv2.do?view=1&modelid="+id;
			   window.open(url,'WEB����Ԥ��');
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
							ѡ��
						</td>
						<td class="td-tittl">
							WEB������
						</td>
						<td class="td-tittl">
							�����
						</td>

						<td class="td-tittl">
							����
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
						WEB������:
						<input type="text" name="searchName"
							value="${paramMap.searchName}">
						<INPUT type="submit" class="butt" value="��ѯ">
						&nbsp;&nbsp;

						<input type="button" class="butt" value="����"
							onclick="editInfoModel('new')" />
						<input type="button" class="butt" value="�޸�"
							onclick="editInfoModel('edit')" />
						<input type="button" class="butt" value="ɾ��"
							onclick="editInfoModel('delete')" />
						<input type="button" class="butt" value="����ģ��"
							onclick="editInfoModel('tomodel')" />
						&nbsp;
						<input type="button" class="butt" value="��ư���"
							onclick="editInfoModel('design')" />
						<input type="button" class="butt" value="Ԥ���������"
							onclick="editInfoModel('view')" />
						<hr>
					</td>
				</tr>
				<tr>
					<td>
						<INPUt type="button" class="butt" value="����ҳ�滺��"
							onclick="window.open('/cmsWeb/infomodellist.do?init=hist','_blank','height=200, width=300, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">

						<INPUt type="button" class="butt" value="��ʼ��ҳ�滺�� "
							onclick="window.open('/cmsWeb/infomodellist.do?init=all','_blank','height=200, width=300, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">

						<INPUt type="button" class="butt" value="��ʼ����̬ҳ�� "
							onclick="window.open('/cmsWeb/infomodellist.do?init=static','_blank','height=200, width=300, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">

						<INPUt type="button" class="butt" value="��ʼ������ "
							onclick="window.open('/cmsWeb/infomodellist.do?init=order','_blank','height=200, width=300, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">

						<INPUt type="button" class="butt" value="��ʼ�����ģ�� "
							onclick="window.open('/cmsWeb/infomodellist.do?init=model','_blank','height=200, width=300, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');">
						<INPUt type="button" class="butt" value="�鿴���ģ�� "
							onclick="window.open('/cmsWeb/templateModel.do?onlyview=yes','_blank','height=400, width=600, top=300, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');">

					</td>
				</tr>
			</TABLE>
		</form>
	</body>
</html>
