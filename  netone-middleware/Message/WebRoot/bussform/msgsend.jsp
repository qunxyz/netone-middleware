<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
		<title>���˷�����Ϣ��ѯ</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<script type="text/javascript" src="rsinclude/calendar.js"></script>
		<script type="text/javascript" src="rsinclude/page.js"></script>
		<script type="text/javascript">
			function show(workid){
				window.open("bussMessage.do?task=look&workid="+workid);
			}
			function look(workid){
			    window.open("bussMessage.do?task=look&workid="+workid);
			}
			function del(workid,extattr1){
			    if(confirm("ȷ��ɾ��"+extattr1+"������Ϣ��")){
			        window.open("bussMessage.do?task=del&workid="+workid,target="_self");
			        window.open("<%=path%>/bussform/MsgSend.do",target="_self");
			    }
			}
			function multidel(){
	    	var k = 0;
		    for(var i=0 ; i<form1.elements.length ; i++) {
			    if (form1.elements[i].name=="multiworkid") {
				    if(form1.elements[i].checked==true){
					    k = 1;
					    break;
				    }
			    }
		    }
		    if(k==0){
			    alert("����ѡ����Ҫɾ����ѡ��");
			    return;
		    } else {
			    if(confirm("��ȷ��ִ��ɾ������ô��")){
				    document.form1.multideltask.value="multidel";
			        document.form1.submit();
			    }
			}
		}
		//��ѡ�� ��Ҫ����Ӧ�õ���Ҫ������
		function allcheck(){
			if(document.all.checkall.checked){
				var checkids = document.getElementsByTagName("input");
				for(var i=0 ; i<checkids.length ; i++){
					if(checkids[i].type=="checkbox"){
						if(checkids[i].checked){
							
						}else{
							checkids[i].checked=true;
						}
					}
				}
			}
			if(document.all.checkall.checked==false){
				var checkids = document.getElementsByTagName("input");
				for(var i=0 ; i<checkids.length ; i++){
					if(checkids[i].type=="checkbox"){
						if(checkids[i].checked){
							checkids[i].checked=false;
						}
					}
				}
			}
		}
		
			function reply(workid){
				window.open("bussMessage.do?task=reply&workid="+workid);
			}
			function returnsend(workid){
				window.open("bussMessage.do?task=returnsend&workid="+workid);
			}
		</script>
	</head>
	<body>

		<form action="bussform/MsgSend.do" method="post" name="form1">
			<table width="100%"  cellspacing="0" border="1" cellpadding="2" bordercolordark="#999999" bordercolorlight="#FFFFFF">
				<input type="hidden" name="multideltask" value="">
				<tr class='tdheadline'>
					<td width="100">
						��ʼʱ��
					</td>
					<td width="200">
						<input type="text" name="created_begin"
							value="${paramMap.created_begin}" class="textinput_td"
							onfocus="calendar()">
					</td>
					<td width="100">
						����ʱ��
					</td>
					<td width="200">
						<input type="text" name="created_end"
							value="${paramMap.created_end}" class="textinput_td"
							onfocus="calendar()">
					</td>
				</tr>
				<tr>
					<td>
						������
					</td>
					<td>
						<input type="text" name="sender" value="${paramMap.sender}"
							class="textinput_td">
					</td>
					<td>
						������
					</td>
					<td>
						<input type="text" name="participant"
							value="${paramMap.participant}" class="textinput_td">
					</td>
				</tr>
				<tr>
					<td>
						״̬
					</td>
					<td>
						<select name="statusinfo">
							<option value="" ${paramMap.statusinfo==''?"selected":""}>
								��ѡ��
							</option>
							<option value="0" ${paramMap.statusinfo=='0'?"selected":""}>
								δ����
							</option>
							<option value="1" ${paramMap.statusinfo=='1'?"selected":""}>
								�Ѵ���
							</option>
						</select>
					</td>
					<td>
						����
					</td>
					<td>
						<input type="text" name="extattr2" value="${paramMap.extattr2}"
							class="textinput_td">
					</td>
				</tr>
			</table>
			<br>
			<div align="center">
				<input type="submit" value="��ѯ" class="butt">
			</div>
			<br>
			<table width="100%"  cellspacing="0" border="1" cellpadding="2" bordercolordark="#999999" bordercolorlight="#FFFFFF">
				<tr class='tdheadline'>
					<td nowrap>
						<input type="checkbox" name="checkall" onclick="allcheck();">
					</td>
					<td nowrap="nowrap">
						����
					</td>
					<td nowrap="nowrap">
						��Ϣ����
					</td>
					<td nowrap="nowrap">
						����ʱ��
					</td>
					<td nowrap="nowrap">
						������
					</td>
					<td nowrap="nowrap">
						������
					</td>
					<td nowrap="nowrap">
						����
					</td>
				</tr>
				<c:forEach items="${formlist}" var="form">
					<tr>
						<td nowrap>
							<input type="checkbox" name="multiworkid" value="${form.workid}">
						</td>
						<td>
							<a href="javascript:show('${form.workid}');">${form.extattr1}</a>&nbsp;
						</td>
						<td>
							${form.extattr2}&nbsp;
						</td>
						<td>
							<fmt:formatDate value="${form.created}"
								pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;
						</td>
						<td>
							${form.participant}&nbsp;
						</td>
						<td>
							${form.sender}&nbsp;
						</td>
						<td>
							<a href="javascript:look('${form.workid}');">�鿴</a>
							<a href="javascript:reply('${form.workid}');">�ظ�</a>
							<a href="javascript:returnsend('${form.workid}');">ת��</a>
							<a href="javascript:del('${form.workid}','${form.extattr1 }');">ɾ��</a>

						</td>
					</tr>
				</c:forEach>
			</table>
			<br>
			<table width="100%"  cellspacing="0" border="0" cellpadding="0" bordercolordark="#999999" bordercolorlight="#FFFFFF">
			<tr><td><input type="button" value="ɾ ��" class="butt" onclick="multidel()"></td></tr>
			<tr><td>
            <script type="text/javascript">
				var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
				pginfo.write();
			</script></td></tr>
			</table>

			

		</form>
	</body>
</html>
