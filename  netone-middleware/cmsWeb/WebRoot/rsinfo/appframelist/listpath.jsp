<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//����Զ����ҵ����ʱ����
	String entryvar = (String) request.getAttribute("entryvar");
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>Ӧ�ÿ��������Դ����</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript"
			src="rsinclude/pagelistpath/pagelistpath.js"></script>
		<script type="text/javascript" src="rsinclude/calendar.js"></script>

		<script type="text/javascript">
		//��ѡ�� ��Ҫ����Ӧ�õ���Ҫ������
		function checkthis(id) {
			if(opener.selected==undefined){
				alert('��ѡ��״̬,��ҳ��ȱ�ٻص�����');
				return;
			}
		}
		//��ѡ�� ��Ҫ����Ӧ�õ���Ҫ������
		function checkthese(){
			var values = null;
			var checkids = document.getElementsByTagName("input");
			for(var i=0 ; i<checkids.length ; i++){
				if(checkids[i].type=="checkbox"){
					if(checkids[i].checked && checkids[i].name!='checkall'){
						if(values == null){
							values = checkids[i].value;
						} else {
							values = values + "," + checkids[i].value;
						}
					}
				}
			}
			alert(values)
		}
		
		//��ѯ�߼������Զ�����չ����,SQL��������,��������created ��Ч����active 1/0 ��չ����extendattribute
		function search(){
			form1.action="PagelistpathRightSvl";
			form1.submit();
		}
		
		function cfg_p(id){
			window.open("CreateSubSvl?&pagename=${pagename}&chkid="+id);
		}
		function cfg_dy(id){
			window.open("CreateDySubSvl?&pagename=${pagename}&chkid="+id);
		}
		
		function man(name){
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>frame.do?method=onMainView&naturalname='+name;
			window.open(url);
		}
		
		function worklist(name){    
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>/WorkListSvl?ope=y&limit=0&status=01&appname='+name;
			window.open(url);
		}
		
		function worklistR(name){    
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>/WorkListSvl?ope=n&limit=0&status=01&appname='+name;
			window.open(url);
		}
		function worklistdone(name){
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>/WorkListSvl?ope=y&limit=0&status=02&appname='+name;
			window.open(url);
		}
		function init(id){
			var url='InitCfgSvl?id='+id;
			window.open(url);
		}		
		</script>
	</head>
	<body style="font-size: 12px; margin: 22px">
		<div style="width: 100%; height: 100%">
			<form action="" method="post" name="form1">
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="parentdir" value="${upo.parentdir}" />
				<input type="hidden" name="appid" value="${upo.appid}" />
				<input type="hidden" name="ou" value="${upo.ou}" />
				<input type="hidden" name="id" value="${upo.id}" />
				<input type="hidden" name="inclusion" value="${upo.inclusion}" />
				<input type="hidden" name="extendattribute"
					value="${upo.extendattribute}" />
				<input type="hidden" name="ext" value="${ext}" />
				<input type="hidden" name="aggregation" value="${upo.aggregation}" />
				<input type="hidden" name="created" value="${upo.created}" />
				<input type="hidden" name="entryvar" value="<%=entryvar%>" />
				<table width="96%" border="0" align="center" cellpadding="5"
					cellspacing="0">
					<tr>
						<td nowrap>
							����:
						</td>
						<td width="100">
							<input type="text" name="snaturalname" value="${snaturalname}"
								class="textinput_td">
						</td>
						<td nowrap>
							��������:
						</td>
						<td width="100">
							<input type="text" name="sname" value="${sname}"
								class="textinput_td">
						</td>
						<td nowrap>
							��ʼʱ��:
						</td>
						<td width="100">
							<input type="text" name="begintime" value="${begintime}"
								class="textinput_td" onfocus="calendar()">
						</td>
						<td nowrap>
							����ʱ��:
						</td>
						<td width="100">
							<input type="text" name="endtime" value="${endtime}"
								class="textinput_td" onfocus="calendar()">
						</td>
						<td>
							<input type="button" name="searchbtn" value="�� ѯ"
								onclick="search();" class="butt">
						</td>
					</tr>
					<tr>
						<td nowrap>
							·��:
						</td>
						<td colspan='6'>
							${link}
							<!-- ������·��naturalname -->
							<!-- ${pathreal} -->
						</td>
						<td colspan='1'>
							<a href='javascript:newInclusion();'><font color='blue'>�½�Ŀ¼</font>
							</a> &nbsp;&nbsp;

						</td>
					</tr>
					<tr>
						<td colspan='9' align='right' nowrap>
							&nbsp;&nbsp;
							<input type="button" value="�½�Ӧ��" onclick="newElemnt();"
								class="butt">
							&nbsp;&nbsp; &nbsp;&nbsp;
							<input type="button" name="btndelete" value="ɾ ��"
								onclick="del();" class="butt">
							&nbsp;&nbsp;
							<input type="button" name="btncheck" value="ȫ ѡ"
								onclick="checkthese();" class="butt">
						</td>
					</tr>
				</table>
				<br>
				<table id="hang_table" width="96%" border="0" align="center"
					cellpadding="0" cellspacing="1">
					<tr>
						<td width="70" class="td_titt_bg" nowrap>
							<input type="checkbox" name="checkall" onclick="allcheck();">
							ѡ��
						</td>
						<td class="td_titt_bg" nowrap>
							������������
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							����
						</td>

						<td class="td_titt_bg" width="70" nowrap>
							����
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							��Ч
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							����
						</td>
					</tr>
					<c:forEach items="${list}" var="list">
						<tr>
							<td>
								<input type="checkbox" name="chkid" value="${list.id}">
							</td>

							<td align='left' nowrap>
								<c:if test="${list.inclusion == '1'}">
									<a href="javascript:inclusionlink('${list.id}','${list.ou}');"><img
											BORDER='0' src='<%=basePath%>rsinclude/images/folder.png'>
									</a>
									<a href="javascript:view('${list.id}');">${list.name}</a>
								</c:if>
								<c:if test="${list.inclusion != '1'}">
									<a href="javascript:view('${list.id}');">${list.name}</a>
								</c:if>
							</td>
							<td nowrap>
								${list.naturalname}
							</td>

							<td nowrap>
								${list.created}
							</td>
							<td nowrap>
								<c:if test="${list.active=='1'}">
									YES
								</c:if>
								<c:if test="${list.active=='0'}">
									NO
								</c:if>
							</td>

							<td nowrap>
								<c:if test="${list.inclusion!='1'}">
									 <a
										href="javascript:edit('${list.id}');"><font color='red'>[�������]</font></a> <a
										href="javascript:cfg_p('${list.id}');"><font color='red'>[����������]</font></a> <a
										href="javascript:cfg_dy('${list.id}');"><font color='red'>[���̱�����]</font></a> 
										
									<a href="javascript:man('${list.naturalname}');"><font color='green'>����:����</font></a>
									<a href="javascript:worklist('${list.naturalname}');"><font color='green'>����Ӧ��</font></a>
									<a href="javascript:worklistR('${list.naturalname}');"><font color='green'>����Ӧ��</font></a>
									<a href="javascript:worklistdone('${list.naturalname}');"><font color='green'>�Ѱ�Ӧ��</font></a>
									<a href="javascript:init('${list.id}');">��ʼ��</a>
									<a href="javascript:del('${list.id}');">ɾ��</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<HR>
			</form>
		</div>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("�޸�ʧ�ܣ�");
				form1.action="PagelistpathRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DeleteSuccess == 'n'}">
			<script type="text/javascript">
				alert("�ýڵ��´����ӽڵ�,������ɾ����");
				form1.action="PagelistpathRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DelSuccess == 'y'}">
			<script type="text/javascript">
				alert("ɾ���ɹ���");
			</script>
		</c:if>
	</body>
</html>
