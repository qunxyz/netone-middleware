
<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUserMgr"%>
<%@page import="oe.security3a.sso.onlineuser.DefaultOnlineUserMgr"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String id = request.getParameter("id");
	
			OnlineUserMgr oum = new DefaultOnlineUserMgr();
			String loginname = oum.getOnlineUser(request).getLoginname();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>Portal</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript">
			var contextpath = "<%=path%>";
		</script>
		<SCRIPT type="text/javascript" src="<%=path%>/include/js/prototype.js"></SCRIPT>
		<SCRIPT type="text/javascript"
			src="<%=path%>/include/js/modelshow/personmodel.js"></SCRIPT>
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">
		<link href="<%=path%>/cms/include/css/portal.css" rel="stylesheet"
			type="text/css">
		<script type="text/javascript">
					function view(mode){
						var value=document.getElementById('rootpage').value;
						if(mode==1){
						
							window.open('/cmsWeb/frames/AppFrame1.jsp?rs='+value+'&layout=left&needhidden=y&height=0&fckid=&initurl=');
						}
						if(mode==2){
						   window.open('/cmsWeb/frames/AppFrame2.jsp?rs='+value+'&height=0&fckid=');
						}
					}
					
					function sselected(text,id){
					   document.getElementById('rootpage').value=id;
					}
		</script>
	</head>

	<body style="margin: 0px;">

		<table width="750" height="500" border="0">

			<tr>
				<td height="30" colspan="2">
					
						<img src='Netone.PNG'/>
					 <font color='red'><rs:logininfo/></font>  &nbsp;  <font color='blue'><a href='<rs:loginout/>'>��ע����</a></font><font color='blue'><a  
href='<rs:changepassword/>' target='_blank'>���޸����롿</a></font>
<font color='blue'><a href='<rs:userinfo/>' target='_blank'>��������Ϣ��</a></font> 
					

				</td>
			</tr>
			<tr bgcolor="#999999">
				<td height="35" colspan="2">
					<p>
						<strong><font color="#FFFFFF">&nbsp;OESEE NETONE-EM(��ҵ���м��ƽ̨) ���߿�����̨</font> </strong>
<font color="#FFFFFF">&nbsp;&nbsp;&nbsp;&nbsp;contact us  mail:oesee@139.com  tel:86+15860836998 </font>

					</p>
				</td>
			</tr>
			<tr>
				<td height="77" colspan="2">
					<p>
						<FONT class="OecText">&nbsp;ѡ��Ҫʹ�õ�ҳ�顣�����Բ鿴��ҳ��, �༭��������,
							�򵥻������ĳ�������ڸ�ҳ���д���ҳ, ��ʽ��ģ�塣</FONT>
					</p>
					&nbsp;
					<A HREF="javascript:"
						onclick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=pagegroup&appname=PORTALPG','_blank')"><FONT
						class="OecLink">PORTAL����/����</FONT> </A> &nbsp;&nbsp;&nbsp;&nbsp;
					<A HREF="PagelistpathRightSvl?pagename=pageframe&appname=FRAMEPG"
						target='_blank'><FONT class="OecLink">����ҳ��</FONT> </A>
					
					<input type='text' id='rootpage' value='FRAMEPG.FRAMEPG' readonly>
					<input type='button' value='ѡ���ҳ��'
						onClick="window.open('/cmsWeb/SSelectSvl?pagename=pagegroup3&appname=FRAMEPG','_blank');" />

					
					<A HREF="javascript:view(1)" ><FONT class="OecLink">[����Ԥ��]</FONT> </A>
					<A HREF="javascript:view(2)"><FONT class="OecLink">[����Ԥ��]</FONT> </A>
					
					&nbsp;&nbsp;
					<A HREF='/fck/PagelistpathRightSvl?appname=FCK&pagename=fcklist' target='_blank'>
							<FONT class="OecLink">[�����ĵ�]</FONT></A>
					<hr>
				</td>
			</tr>
			<tr>
				<td width="691" height="91" colspan='2'>

					Ӧ������:
					<select id='apptype'>
						<OPTION
							VALUE="/biWeb/PagelistpathRightSvl?pagename=dyformlist&appname=BUSSFORM">
							��̬��
						<OPTION
							VALUE="/wfweb/PagelistpathRightSvl?pagename=pagelist&appname=BUSSWF">
							������
						<option
							value="/wfweb/PagelistpathRightSvl?pagename=quartzlist&appname=PLAN">
							�ƻ�����
						<OPTION
							VALUE="/biWeb/PagelistpathRightSvl?pagename=etl&appname=ETL">
							�������
						<OPTION
							VALUE="/biWeb/PagelistpathRightSvl?pagename=chart&appname=CHART">
							ͼ�����
						<OPTION VALUE="/biWeb/bi/etl/wizard/ExcelToDb/First.jsp?pagepath=X">
							���ݲɼ�
						<OPTION
							VALUE="/cmsWeb/PagelistpathRightSvl?pagename=jspapp&appname=JSPAPP">
							POTRLETӦ��
						<OPTION
							VALUE="/cmsWeb/PagelistpathRightSvl?pagename=bean&appname=BUSSBEAN">
							BEAN����
					</select>
					<A HREF="javascript:createpage()" ><FONT
						class="OecLink">����/����Ӧ��</FONT> </A>
					&nbsp;
					<A HREF="/Security3A" target='_blank'><FONT class="OecLink">4A��ȫ</FONT>
					</A>
					&nbsp;
					<A HREF="/biWeb/PagelistpathRightSvl?pagename=datasourceExcellist&appname=DATASOURCE" target='_blank'><FONT class="OecLink">����Դ����

</FONT>
					</A>
					<BR>
<BR>
					<FONT class="OecText">Ӧ���ǹ���Portal�е�Ӧ�ó����ܡ����Ƕ����Ա����ɵ�Portal�л���ʹ����صı�ǩ�����ο���ʵ��Portlet��</FONT>
					<BR>
				</td>

			</tr>
			<tr>
				<td height="83">
					<A HREF="javascript:" onClick='listStyle()'><FONT
						class="OecLink">����/������ʽ</FONT> </A>
					<BR>
					<BR>
					<FONT class="OecText">ʹ�÷ֲ�������������ʽ����ʽ��ҳ���б�ʹ�õ�,�����Զ���Ч�����������ڸ����ҳ�С�</FONT>
				</td>
				<td>
					<A HREF="javascript:"
						onclick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=template&appname=TEMPLATE','_blank')"><FONT
						class="OecLink">����/����ҵ��Ԫ</FONT> </A>
					<BR>
					<BR>
					<FONT class="OecText">ҵ��Ԫ�������ķ�װҵ��Ԫ�أ������������Զ��幦��ҵ��ű���չ�ֽű������ɺ�����ڹ�������ҳ����������/��������Ϊ���ȵ�Ԫ��ʹ�á�</FONT>
				</td>
			</tr>
			<tr>

				<td height="70">
					<A HREF="javascript:"
						onClick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=jspapp&appname=JSPAPP')"><FONT
						class="OecLink">����/����ҳ����</FONT> </A>
					<BR>
					<BR>
					�����������Դ�������OESEE�淶��ҳ�����.......��

				</td>
				<td height="70">
					<A HREF="javascript:" onClick='createResource()'><FONT
						class="OecLink">��Դ����</FONT> </A>
					<BR>
					<BR>
					��ҳ����Ҫ���ô�����ʵ����Դ�����磺ͼƬ���ļ�������.......��

				</td>
			</tr>
			<tr>
				<td height="128" colspan="2">
					<hr>
					<Strong><font color='red'>��ҵ�� Ӧ�ÿ���ģʽ</font></Strong> &nbsp;
					<br>

					<br>
					����ϵͳ:
					
					<a
						href='<portal:envget envkey="WEBSER_FORUM"/>'
						target='_blank'><FONT class="OecLink">��̳</font> </a>
					<a
						href='<portal:envget envkey="WEBSER_WEBMAIL"/>'
						target='_blank'><FONT class="OecLink">�ʼ�</font> </a>
					<a
						href='<portal:envget envkey="WEBSER_INIS"/>'
						target='_blank'><FONT class="OecLink">Ѳ��</font> </a>
					<a
						href='<portal:envget envkey="WEBSER_ITKNOW"/>'
						target='_blank'><FONT class="OecLink">֪ʶ��</font> </a>
					
					<a	href='<portal:envget envkey="WEBSER_PMS"/>/PagelistpathRightSvl?pagename=pagelist&appname=PMS.PMS.INSTANCES'
						target='_blank'><FONT class="OecLink">����ϵͳƽ̨վ</font> 
					<a
						href='<portal:envget envkey="WEBSER_PMS"/>/PagelistpathRightSvl?pagename=pojlist&appname=PMS.PMS.FRAMEWORK'
						target='_blank'><FONT class="OecLink">����ϵͳƽ̨վ��ܹ���</font> </a>
						
						 <br>
						<br> ����������Ԥ�����Ϻ��з���һЩӦ��ϵͳ���м��ƽ̨��ͨ��ѧϰ��ʹ����Щϵͳ��ƽ̨������ҵ��ϵͳ������ӵ�����

						��Ŀ��Ч����ϵͳ������ϵͳ��ҵ�񿼺�ϵͳ�� CRM�ͻ���ϵ����ϵͳ����ϵͳ����Ӫ��������Ա���ֹ�����ҵ��̳�� 
				</td>
			</tr>
		</table>
	</body>
</html>
