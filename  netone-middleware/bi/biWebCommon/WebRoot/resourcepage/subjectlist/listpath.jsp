<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

		<title>��������</title>

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
		function checkthis(id,name) {
		if(opener.selecthis==undefined){
			alert('��ѡ��״̬');
			return;
		}
			opener.selecthis(id,name);
			window.close();
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
		
		function coreview(id){
			window.open('/biWeb/servlet/BiViewsvl?lsh='+id,'_blank');
		}
		
		</script>
	</head>
	<body style="font-size: 12px;margin: 22px">
		<div style="width: 100%;height: 100%">
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

				<br>
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
						<td  colspan='6'>
							${link}
							<!-- ������·��naturalname -->
							<!-- ${pathreal} -->
						</td>
						<td colspan='1'>
							<a href='javascript:' onclick="newInclusion();"><font
								color='blue'>�½�Ŀ¼</font>
							</a> &nbsp;&nbsp;

						</td>
					</tr>
					<tr>
						<td colspan='9' align='right' nowrap>
							<script type="text/javascript">
							function newds(){
								window.open('resourcepage/subjectlist/First.jsp?pagepath=${pathreal}','_blank');
							}
							function edits(){
								var k = 0;
								var value;
								for(var i=0 ; i<form1.elements.length ; i++) {
									if (form1.elements[i].name=="chkid") {
										if(form1.elements[i].checked==true){
											value = form1.elements[i].value;
											k++;
										}
									}
								}
								if(k==0){
									alert("����ѡ����Ҫ�޸ĵ�ѡ��");
									return;
								}
								if(k>1){
									alert("ֻ��ѡ��������޸�");
									return;
								}
								window.open('<%=path%>/ModifyAnalyse.do?task=Modify&chkid='+value,'_blank');
							}
							function done(value){

								window.open("<%=basePath%>flowpage.do?chkid="+value);
							}
							</script>
							&nbsp;&nbsp;
							<input type="button" value="�½���������" onclick="newds()" class="butt">
							&nbsp;&nbsp;

							<input type="button" name="btnmodify" value="�� ��"
								onclick="edits();" class="butt">
							&nbsp;&nbsp;

							<input type="button" name="btndelete" value="ɾ ��"
								onclick="del();" class="butt">

						</td>
					</tr>
				</table>
				<HR>
				<table id="hang_table" width="96%" border="0" align="center"
					cellpadding="0" cellspacing="1">
					<tr>
						<td width="70" class="td_titt_bg" nowrap>
							<input type="checkbox" name="checkall" onclick="allcheck();">
							ѡ��
						</td>
						<td class="td_titt_bg" nowrap>
							��������
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							����
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
								${list.objecttype}
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
							    <c:if test="${list.inclusion != '1'}">
								<a href="javascript:done('${list.id}');"><font color='red'>[�������]</font></a>
								<a href="javascript:coreview('${list.extendattribute}');"><font color='green'>[�����ļ�]</a>
								<a href="javascript:edit('${list.id}');">[�޸�]</a>
								<a href="javascript:del('${list.id}');">[ɾ��]</a>
								<a href="javascript:checkthis('${list.id}','${list.naturalname}');">ѡ��</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>

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