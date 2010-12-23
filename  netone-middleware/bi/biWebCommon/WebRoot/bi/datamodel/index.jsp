
<%@ page contentType="text/html; charset=GB2312"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>
			����ģ���б�
		</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=path%>/include/css/css.css">
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
		<SCRIPT language="javascript">
			function newModel(){
			 window.open("<%=path%>/design_Newdatamodel.do",'����ģ��');
			}
			function newModelSrc(){
			 window.open("/biWeb/bi/datamodel/editdatamodel.jsp",'����ģ��Src','width=800,height=600,left=150,top=190,resizable=yes');
			}
			
			function editModel(opr){
			  var flag=false;
			   var id;
			  var dataModelValues=document.getElementsByName("modelId");
			  if(dataModelValues!=null){
			    for(var i=0;i<dataModelValues.length;i++){
			      if(dataModelValues[i].checked){
			        flag=true;
			        id=dataModelValues[i].value;
			        break;
			      }
			    }
			  }
 			if(flag==false){
			    alert("��ѡ��Ҫ������ģ��");
			    return;
			  } else{
			  if(opr=="edit"){
			     var url="<%=path%>/design_Updatedatamodel.do?id="+id;
			   window.open(url,'�༭ģ��');
			   }else if(opr=="delete"){
		      			     if (confirm("��ȷ��Ҫִ�д˲���!!!")) {
		     	 window.location="datamodellist.do?flag=delete&&id="+id;
		     	 }
			   }else if(opr=="etl"){
			     var url="etlmain.do?datamodelid="+id;
			    window.open(url,'ҵ�����');
			   }else if(opr=="show"){
			     var url="design_Showdatamodel.do?datamodelid="+id;
			    	window.open(url,'ҵ��ģ��ͼ');
			   }else if(opr=="editsrc"){
			   		var url="editDataModel.do?flag=init&&id="+id;
			   		window.open(url,'�༭ģ��src','width=800,height=600,left=150,top=190,resizable=yes');
			   }
			   
			  }
			}
			
		</SCRIPT>
	</head>

	<body>
		<form action="datamodellist.do" method="post">
			<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#A9C0E5" align="center">
				<tr class="td-bg01" align="center">
					<td height="30" bgcolor="#FFFFFF"  width="10%">
						ѡ��
					</td>
					<td height="30" bgcolor="#FFFFFF">
						ģ������
					</td>
				</tr>
				<%String[][] modelList = (String[][]) request
					.getAttribute("modelList");
			if (modelList != null) {
				for (int i = 0; i < modelList.length; i++) {

					%>
				<tr align="center">
					<td height="25" bgcolor="#FFFFFF" width="10%">
						<INPUT id="modelId" name="modelId" type="radio" value="<%=modelList[i][0]%>" />
					</td>
					<td height="25" bgcolor="#FFFFFF">
						<%=modelList[i][1]%>
					</td>
				</TR>
				<%}
			}

		%>
				<tr align="center" height="25" bgcolor="#FFFFFF">
					<td colspan="2" align=right>
						<!-- <input type="button" value="���" onclick="editModel('design')" class="butt" />-->
						<input type="button" value=" ҵ �� �� �� " onclick="editModel('etl')" class="butt" />
						<!-- <input type="button" value="ҵ��ģ��ͼ" onclick="editModel('show')" class="butt" /> -->
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="����ģ��" onclick="newModel()" class="butt" />
						<input type="button" value="�޸�ģ��" onclick="editModel('edit')" class="butt" />
						<input type="button" value="����ģ��Դ�ļ�" onclick="newModelSrc()" class="butt" />
						<input type="button" value="�޸�ģ��Դ�ļ�" onclick="editModel('editsrc')" class="butt" />
						<input type="button" value="ɾ��ģ��" onclick="editModel('delete')" class="butt" />



					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
