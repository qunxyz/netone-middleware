<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String cellid = request.getParameter("cellid");
	String entryvar = (String) request.getAttribute("entryvar");
	if (entryvar == null || entryvar.equals("")
			|| entryvar.equals("null")) {
		entryvar = cellid;
	}
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>ҳ����</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript" src="rsinclude/calendar.js"></script>
		<script type="text/javascript">

		function closedo(){
			opener.refreshdiv('<%=entryvar%>');
			window.close();
		}
		//��ѡ�� ��Ҫ����Ӧ�õ���Ҫ������
		function checkthis(id) {
			alert(id)
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
			if(values==null){
				alert('û��ѡ���Ԫ��');
			}else{
				alert(values);
			}
			
		}
		
		//ִ�е�����ڵ����
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
			document.all.spathname.value = name;
			document.all.spath.value = id;
		}
		
		//��ѯ�߼������Զ�����չ����,SQL��������,��������created ��Ч����active 1/0 ��չ����extendattribute
		function search(){
			if(document.all.spath.value!=''){
				document.all.id.value = document.all.spath.value;
			}
			var condition="and active!='0'  order by aggregation desc";
			
			var begintime=document.getElementById('begintime').value;
			if(begintime!=''){
				condition=" and created >=UNIX_TIMESTAMP('"+begintime+" 00:00:00.0')"+condition;
			}

			document.getElementById('ext').value=condition;
			form1.action="PagelistRightSvl";
			form1.submit();
		}
		
		
		//����ѡ��ҳ��
		function searchtree() {
			window.open("SelectSvl?appid="+document.all.appid.value+"&pagename="+document.all.pagename.value);
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
				<input type="hidden" name="aggregation" value="${upo.aggregation}" />
				<input type="hidden" name="created" value="${upo.created}" />
				<input type="hidden" name="entryvar" value="<%=entryvar%>" />
				<input type="hidden" name="ext" value="${ext}" />
				<table width="96%" border="0" align="center" cellpadding="5"
					cellspacing="5">
					<tr>
						<td nowrap>
							����:
						</td>
						<td width="100">
							<input type="text" name="snaturalname" value="${naturalname}"
								class="textinput_td">
						</td>
						<td nowrap>
							��������:
						</td>
						<td width="100">
							<input type="text" name="sname" value="${name}"
								class="textinput_td">
						</td>
						<td nowrap>
							��ʼʱ��:
						</td>
						<td width="100">
							<input type="text" name="begintime" value="${begintime}"
								class="textinput_td" onfocus="calendar();">
						</td>
					</tr>
					<tr>
						<td nowrap>
							<a href='javascript:'><font color='blue'>·��</font> </a>
						</td>
						<td width='100'>
							<input type="text" name="spathname" value="${upo.name}"
								class="textinput_td" readonly="readonly">
							<input type="hidden" name="spath" value="${upo.id}"
								class="textinput_td">
						</td>

						<td>
							<input type="button" name="searchbtn" value="�� ѯ"
								onclick="search();" class="butt">
						</td>
						<td>
							&nbsp;
						</td>
						<td>
							&nbsp;
						</td>

						<td align='right' nowrap>
							&nbsp;
							<input type="button" value="�½�" onclick="newElemnt();"
								class="butt">
							&nbsp;
							<input type="button" name="btnmodify" value="�� ��"
								onclick="edit();" class="butt">
							&nbsp;
							<input type="button" name="btndelete" value="ɾ ��"
								onclick="del();" class="butt">
							&nbsp;

							<input type="button" name="btncheck" value="�� ѡ"
								onclick="checkthese();" class="butt">

						</td>
					</tr>

				</table>
				<br>


				<br>
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
						<td class="td_titt_bg" nowrap>
							����
						</td>

						<td class="td_titt_bg" nowrap>
							����
						</td>
						<td class="td_titt_bg" nowrap>
							����
						</td>
						<td class="td_titt_bg" nowrap>
							��Ч
						</td>
						<td class="td_titt_bg" nowrap>
							����
						</td>
					</tr>
					<c:forEach items="${list}" var="list">
						<tr>
							<td nowrap>
								<input type="checkbox" name="chkid" value="${list.id}">
							</td>
							<td nowrap>
								<a href="javascript:view('${list.id}');">${list.name}</a>
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
								<a href="javascript:edit('${list.id}');">�޸�</a>
								<a href="javascript:del('${list.id}');">ɾ��</a>
								<a href="javascript:checkthis('${list.id}');">ѡ��</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<div align="left">

					&nbsp;
					<input type="button" name="btnup" value="  ��   ��  " onclick="up();"
						class="butt">
					&nbsp;
					<input type="button" name="btndown" value="  ��   ��  "
						onclick="down();" class="butt">
					&nbsp;
					<input type="button" name="btndown" value="  �رղ��Ҹ���ҳ  "
						onclick="closedo();" class="butt">
				</div>
			</form>
		</div>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("�޸�ʧ�ܣ�");
				form1.action="PagelistRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DeleteSuccess == 'n'}">
			<script type="text/javascript">
				alert("�ýڵ��´����ӽڵ�,������ɾ����");
				form1.action="PagelistRightSvl";
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
