<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%>
<%@page import="oe.security3a.sso.onlineuser.OnlineUserMgr"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
						if(mode==3){
						
							window.open('/cmsWeb/extframes.do?listPath='+value);
						}
						if(mode==4){
						   window.open('/cmsWeb/extframes.do?mode=4&listPath='+value);
						}
					}
					
					function sselected(text,id){
					   document.getElementById('rootpage').value=id;
					}
		</script>
	</head>

	<body style="margin: 0px;">

		<table width="750" height="500" border="0" align="left">
			<tr>
				<td>
					
				</td>
				<td>
					<table>
						<tr>
							<td height="30" colspan="2">

								<img src='image/Netone.PNG' />



								<font color='red'><rs:logininfo /> </font><font color='blue'><a
									href='<rs:loginout/>'>��ע����</a> </font><font color='blue'><a
									href='<rs:changepassword/>' target='_blank'>���޸����롿</a> </font>
								<font color='blue'><a href='<rs:userinfo/>'
									target='_blank'>��������Ϣ��</a> </font>

							</td>
						</tr>
						<tr bgcolor="#999999">
							<td height="35" colspan="2">
								<p>
									<strong><font color="#FFFFFF">OESEE
											NETONE-EM(��ҵ���м��ƽ̨) ���߿�����̨</font> </strong>
									<font color="#FFFFFF">&nbsp;&nbsp;&nbsp;&nbsp;contact us
										mail:oesee@139.com tel:86+15860836998 </font>

								</p>
							</td>
						</tr>
						<tr>
							<td height="77" colspan="2">
								<font size=2 color="red"><strong>�����Ż�����:</strong> </font> Portal�����ڹ����ḻ�Ĺ����Ż�����ͨ��ҳ�����������ɵ����ϳ��ṹ������WEBӦ����ҳ
								<br>
								<br>

								<A HREF="javascript:"
									onclick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=pagegroup&appname=PORTALPG','_blank')"><FONT
									class="OecLink">PORTAL����/����</FONT> </A> &nbsp;&nbsp;&nbsp;
								<A
									HREF="PagelistpathRightSvl?pagename=pageframe&appname=FRAMEPG"
									target='_blank'><FONT class="OecLink">����ҳ��</FONT> </A>

								<input type='text' id='rootpage' value='FRAMEPG.FRAMEPG'
									readonly>
								<input type='button' value='ѡ���ҳ��'
									onClick="window.open('/cmsWeb/SSelectSvl?pagename=pagegroup3&appname=FRAMEPG','_blank');" />


								<A HREF="javascript:view(1)"><FONT class="OecLink">[����Ԥ��]</FONT>
								</A>
								<A HREF="javascript:view(3)"><FONT class="OecLink">[����Ԥ��2]</FONT>
								</A>
								<A HREF="javascript:view(2)"><FONT class="OecLink">[����Ԥ��]</FONT>
								</A>
								<A HREF="javascript:view(4)"><FONT class="OecLink">[����Ԥ��2]</FONT>
								</A> &nbsp;&nbsp;
								<A HREF='/fck/PagelistpathRightSvl?appname=FCK&pagename=fcklist'
									target='_blank'> <FONT class="OecLink">[�����ĵ�]</FONT> </A>
								<hr>
							</td>
						</tr>
						<tr>
							<td width="691" height="91" colspan='2'>

								
								<font size=2 color="red"><strong>���߹��ܶ��ƿ���:</strong> </font>֧���û����߿��ٶ�����ص�ҵ���ܡ�����:�������̡�BIͼ����ȫӦ�á���Դ��
								<br>
								<br>
								��ѡ��Ӧ��:
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
								</select>
								<A HREF="javascript:createpage()"><FONT class="OecLink">����/����Ӧ��</FONT>
								</A> &nbsp;
								
								
								<A HREF="<portal:envget envkey="WEBSER_APPFRAME"/>/department/department.do?method=onDepartmentManageView" target='_blank'><FONT class="OecLink">�ʻ�</FONT>&nbsp;
								<A HREF="/Security3A" target='_blank'><FONT class="OecLink">4A��ȫ</FONT>
								</A> &nbsp;
								<A
									HREF="/biWeb/PagelistpathRightSvl?pagename=datasourceDblist&appname=DATASOURCE"
									target='_blank'><FONT class="OecLink">����Դ����</FONT> </A> &nbsp;
								<A HREF="javascript:" onClick='createResource()'><FONT
									class="OecLink">��Դ����</FONT> </A> &nbsp;
								<A HREF="/biWeb/bi/etl/wizard/ExcelToDb/First.jsp?pagepath=X"
									target='_blank'><FONT class="OecLink">���ݲɼ�</FONT> </A>
								<HR>
								
								<font size=2 color="red"><strong>�߼�ҵ�񹹼��Ķ��ο���:</strong> </font>
								<FONT class="OecText">��������������߶��ƿ����Ĳ��䣬���ܸ���һ�������м���ڲ���ʵ�ָ���������ǿ��Ķ��ο�������Щ���ο�����ģ���Ǻ����׿��Ա��м�����ɵġ�</FONT>
								
							</td>

						</tr>
						<tr>

						<tr>
							<td height="60">
								<A HREF="javascript:" onClick='listStyle()'><FONT
									class="OecLink">����/������ʽ</FONT></A>:
								<FONT class="OecText">ʹ�÷ֲ�������������ʽ����ʽ��ҳ���б�ʹ�õ�,�����Զ���Ч�����������ڸ����ҳ�С�</FONT>
							</td>
							<td>
								<A HREF="javascript:"
									onclick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=template&appname=TEMPLATE','_blank')"><FONT
									class="OecLink">����/����ҵ��Ԫ</FONT></A>:
								<FONT class="OecText">ҵ��Ԫ���ڷ�װҵ��Ԫ�أ������������Զ��幦��ҵ��ű���չ�ֽű���Ȼ�󱻼��ɵ�����/�����е���ʹ�á�</FONT>
							</td>
						</tr>
						<tr>
							<td height="60">
								<A HREF="javascript:"
									onClick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=jspapp&appname=JSPAPP')"><FONT
									class="OecLink">����PORTLET</FONT> </A>:�����������Դ�������JSP�淶��PORTLETҳ����,ͨ��ʹ��NETONE�м���ṩ�ı�ǩ���JSP&JSTL����ؼ�����ʵ�ָ�����Ч��ҳ����

							</td>
							<td >

								<A HREF="javascript:"
									onClick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=bean&appname=BUSSBEAN')"><FONT
									class="OecLink">ע��BEAN����</FONT> </A>:BEAN������NETONE�м���ṩ�ķֲ�ʽ�淶Э�飬���ڶ��ƿ���ҵ���м�����������������߼���Bean����
								ʵ�����߼�غ͹���
							</td>
						</tr>

						<tr>

							<td height="60">
							    ���л���̵�ʵ���б����ڸ�ҳ���Ͽ��ӻ��ļ��ҵ����̣�ִ��ҵ������߼�
							   
								<A HREF="<portal:envget envkey="WEBSER_APPFRAME"/>/workList.do?method=onMainView2&mode=1&height=260&listtype=01&sortfield=&sort=desc&psize=20&appname=" target='_blank'><FONT
									class="OecLink">[����]</FONT> </A>
								<A HREF="<portal:envget envkey="WEBSER_APPFRAME"/>workList.do?method=moreWorklist&mode=1&height=430&listtype=02&sortfield=&sort=&psize=20&appname=" target='_blank'><FONT
									class="OecLink">[�Ѱ�]</FONT> </A>:
                               <A HREF="<portal:envget envkey="WEBSER_APPFRAME"/>workList.do?method=moreWorklist&mode=1&height=430&listtype=03&sortfield=&sort=&psize=20&appname=" target='_blank'><FONT
									class="OecLink">[�ѹ鵵]</FONT> </A>:								
								<A HREF="<portal:envget envkey="WEBSER_APPFRAME"/>workList.do?method=moreWorklist&mode=1&height=260&listtype=00&sortfield=&sort=desc&psize=20" target='_blank'><FONT
									class="OecLink">[ȫ��]</FONT> </A>:			

							</td>
							<td>
							<A HREF="/cmsWeb/PagelistpathRightSvl?pagename=appframelist&appname=APPFRAME" target='_blank'><FONT
									class="OecLink">Ӧ�ÿ�ܶ���</FONT> </A>:
								���ϱ��������ṩȫӦ�����߶��ƿ���
								
							<a href='/Security3A/extinfo.jsp' target='_blank'> <font
							color='red'>[�����������]</font> </a>���ڸ���Netone�м��Ӧ�ÿ�����������
							</td>
						</tr><tr>
<td colspan="2">
<img src="image/syncx.jpg" height='150'>
</td>
</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
