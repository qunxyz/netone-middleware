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
		<title>����ҳ��</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<!-- ԭʼ���뿪ʼλ��:
		   <SCRIPT type="text/javascript"
		   src="<%=path%>\include\js\modelshow\personmodel.js"></SCRIPT>
		   <SCRIPT type="text/javascript" src="<%=path%>\include\js\checkrs.js"></SCRIPT>
		   <script type="text/javascript">
		   �޸�ʱ��:2009-2-06
		   ԭʼ�������λ��:-->

		<!-- �ĺ���뿪ʼλ�� -->
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/modelshow/personmodel.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/checkrs.js"></SCRIPT>
		<script type="text/javascript">
		<!-- �ĺ�������λ�� -->
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
				alert('ģ��ID:'+id);
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
				alert('ҳ����ʾ���� ������Ϊ��');
				return;
			}

			this.document.forms[0].submit();	
		}
		</script>
	</head>
	<body LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0
		style="font: 14px">

		<!--
		 ԭ���뿪ʼλ��:
		<html:form action="infomodelnew.do?newFlag=new" >
		�޸�����:2009-2-6
		ԭ�������λ��:-->
		<!-- �ĺ���뿪ʼλ�� -->
		<form action="infomodelnew.do?loginName=<%=loginname%>" id="from" name="from">
			<input type='hidden' name='newFlag' value='new' />
			<!-- �ĺ�������λ�� -->
			<input type='hidden' name='path' value='${param.path}' />
			<h4>
				����ҳ��
			</h4>
			<table style="width: 100%;">
				<tr>
					<td>
						ҳ������:
					</td>
					<td>
						<!--  �ĺ���뿪ʼλ�� -->
						<input name="naturalname" id="naturalname" size="40" value=""></input>
						<!--  �ĺ�������λ�� -->
						<!-- 
						ԭ���뿪ʼλ��:
						<html:text property="naturalname" size="40" value=""></html:text>>
						�޸�����:2009-2-6
						ԭ�������λ��:-->
						[��ʽ����ĸ��ͷ�����ĸ�����ֻ��»���]
					</td>
				</tr>
				<tr>
					<td>
						ҳ����ʾ����:
					</td>
					<td>
						<!--  �ĺ���뿪ʼλ�� -->
						<input name="modelname" id="modelname" size="40" value=""></input>
						<!--  �ĺ�������λ�� -->
						<!--
						ԭ���뿪ʼλ��:
						<html:text property="modelname" size="40" value=""></html:text> 
						�޸�����:2009-2-6
						ԭ�������λ��:-->

					</td>
				</tr>
				<tr>
					<td>
						ҳ����ʽ
					</td>
					<td>

						<input type='text' name='userid' id='userid'
							value='��׼��ʽ[CSSFILE.CSSFILE.DEFAULT]' readonly>
						<input type='button' value='ѡ����ʽ' onClick='listStyle()'>
					</td>
				</tr>
				<tr>
					<td>
						ѡ��ģ��
					</td>
					<td>
						<input type='text' name='template' id='template' value='' readonly>
						<input type='button' value='ѡ��ģ��' onClick='listTemplate()'>
					</td>
				</tr>
				<tr>
					<td>
						ҳ������
					</td>
					<td>
						<html:textarea property="description" cols="90" rows="10"></html:textarea>
					</td>
				</tr>


				<tr style="display: none">
					<td>
						��ȡģʽ
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
						WEB�����ڲ���ʽ
					</td>
					<td>
						<html:textarea property="infoxml" cols="90" rows="10"></html:textarea>
					</td>
				</tr>

			</table>
			<table align="center" style="width: 100%;">
				<tr height="23px" valign="bottom" align="center">
					<td align="center" style="width: 100%">

						<input type="button" class="butt" value="����"
							onClick="javascript:done();" />
						<input type="button" class="butt" value="ȡ��"
							onclick="window.close();" />
					</td>
				</tr>
			</table>
			<!--  �ĺ���뿪ʼλ�� -->
		</form>
		<!--  �ĺ�������λ�� -->
		<!--
		ԭ���뿪ʼλ��:
		</html:form>
		�޸�����:2009-2-6
          ԭ�������λ��:-->
	</body>
</html>
