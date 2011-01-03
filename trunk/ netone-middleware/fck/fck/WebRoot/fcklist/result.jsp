<%@ page contentType="text/html; charset=gbk"%>
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

		<title>�������</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript" src="rsinclude/calendar.js"></script>
		<script type="text/javascript">
		//��ѡ�� ��Ҫ����Ӧ�õ���Ҫ������
		function checkthis(id) {
			alert(id)
		}
		
		function searchall1(){
			document.forms[0].action="SearchSvl";
			document.forms[0].submit();
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
				<input type="hidden" name="pagename1" value="simplefcklist" />

				<input type="hidden" name="ext" value="${ext}" />
				<input type="hidden" name="entryvar" value="<%=entryvar%>" />
				<table width="96%" border="0" align="center" cellpadding="5"
					cellspacing="5">
					<tr>
						<td width="100">
							<input type="text"  class="textinput_td" name="searchText" id="searchText">
						</td>
						<td> 
							<input type="button"  value="ȫ�ļ���" onclick="searchall1();" class="butt">
						</td>
					</tr>
				</table>
				<br>


				<br>
				<table id="hang_table" width="96%" border="0"
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
					<c:forEach items="${upolist}" var="list">
						<tr>
							<td nowrap>
								<input type="checkbox" name="chkid" value="${list.id}">
							</td>
							<td align='left' nowrap>
								<a href="javascript:view('${list.id}');">${list.name}</a>
							</td>
							<td align='left' nowrap>
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
								<c:if test="${list.inclusion == '0'}">
								<a href="javascript:edit('${list.id}');">�޸�</a>
								<a href="javascript:view('${list.id}');">���1</a>
								<a href="javascript:view1('${list.id}');">���2</a>
								</c:if>
								<a href="javascript:del('${list.id}');">ɾ��</a>
							</td>
							
						
						</tr>
					</c:forEach>
				</table>
				<br>
			</form>
		</div>
	</body>
</html>
