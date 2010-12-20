<%@ page contentType="text/html; charset=GBK"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>�����</title>
		<script type="text/javascript" src="<%=path%>/include/js/page.js"></script>

		<script type="text/javascript">
		var curselect='dimlevel';
		function create() {
			window.open('<%=path%>/design/system/form/createview.do','_blank');
		}

		function modify() {
			var values = '';
			var checkids = document.getElementsByName("checkboxx");
			
			for(var i=0 ; i<checkids.length ; i++){
					if(checkids[i].checked){
						if(values == ''){
							values = checkids[i].value;
						} else {
							alert('��ѡ��һ��Ҫ�޸ĵļ�¼!');
							return;
						}
					}
			}
			if(values==''){
				alert('��ѡ��һ��Ҫ�޸ĵļ�¼!');
				return;
			}
			
			window.open('<%=path%>/design/system/form/modifyview.do?formcode='+values,'_blank');
		}
		
		
	   function dele() {
			var values = '';
			var checkids = document.getElementsByName("checkboxx");
			
			for(var i=0 ; i<checkids.length ; i++){
					if(checkids[i].checked){
						if(values == ''){
							values = checkids[i].value;
						} else {
							alert('��ѡ��һ��Ҫ�޸ĵļ�¼!');
							return;
						}
					}
			}
			if(values==''){
				alert('��ѡ��һ��Ҫ�޸ĵļ�¼!');
				return;
			}
		
			window.open('<%=path%>/design/system/form/dropope.do?formcode='+values,'_blank');
		}
		
		
		function modifycolumn() {
			var values = '';
			var checkids = document.getElementsByName("checkboxx");
			
			for(var i=0 ; i<checkids.length ; i++){
					if(checkids[i].checked){
						if(values == ''){
							values = checkids[i].value;
						} else {
							alert('��ѡ��һ��Ҫ�޸ĵļ�¼!');
							return;
						}
					}
			}
			if(values==''){
				alert('��ѡ��һ��Ҫ�޸ĵļ�¼!');
				return;
			}
			
			window.open('<%=path%>/design/system/column/list.do?formcode='+values,'_blank');
		}
		
		function createTemplate() {
			var values = '';
			var checkids = document.getElementsByName("checkboxx");
			
			for(var i=0 ; i<checkids.length ; i++){
					if(checkids[i].checked){
						if(values == ''){
							values = checkids[i].value;
						} else {
							alert('��ѡ��һ��Ҫ�޸ĵļ�¼!');
							return;
						}
					}
			}
			if(values==''){
				alert('��ѡ��һ��Ҫ�޸ĵļ�¼!');
				return;
			}
			
			window.open('<%=path%>/design/system/form/createviewTemplate.do?formcode='+values,'_blank');
		}
		
		function applist(){
			window.open('<%=basePath%>ApplistRightSvl?pagename=dyapplist');
		}
		
		function search(){
			window.location.href='<%=basePath%>form/listSvl';
		}
		</script>
		<LINK REL=Stylesheet TYPE="text/css"
			HREF="<%=basePath%>/include/css/oe.css">
	</head>
	<body>

		<form action="" name="form1" method="post">

			<input name="pathinfo" type="hidden" value="<%=path%>">
			<table align="center" style="width: 100%;" border="1" cellpadding="2"
				bordercolordark="#FFFFFF" bordercolorlight="#000000">
				<tr align="center" bgcolor='#cccccc'>
					<td class="tdheadline" width='50'>
						ѡ��
					</td>
					<td class="tdheadline">
						����
					</td>
					<td class="tdheadline">
						����
					</td>
					<td class="tdheadline">
						��Ӧ�ı���
					</td>
					<td class="tdheadline">
						����Դ
					</td>
				</tr>
				<c:forEach items="${listinfo}" var="getCol">
					<tr class="td-02" align="left">
						<td>
							<input type="checkbox" name="checkboxx"
								value="${getCol.formcode}" size="10">
						</td>
						<td height="21">
							${getCol.formname}
						</td>
						<td height="21">
							${getCol.created}
						</td>
						<td height="21">
							${getCol.description}
						</td>
						<td height="21">
							${getCol.formcode}
						</td>

					</tr>
				</c:forEach>
			</table>
			<script type="text/javascript">
				var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
				pginfo.write();
			</script>
			<table width="100" border="0" cellspacing="0" cellpadding="1"
				align="left">
				<tr>

					<td>
						<input type="button" value="������" onclick="javascript:create()"
							class="button" />
					</td>
					<td>
						<input type="button" value="�޸ı�" onclick="javascript:modify()"
							class="button" />
					</td>
					<td>
						<input type="button" value="ɾ����" onclick="javascript:dele()"
							class="button" />
					</td>
					<td>
						<input type="button" value="���/�޸ı��ֶ�"
							onclick="javascript:modifycolumn()" class="button" />

					</td>
					<td>

						<input type="button" value="����ģ��"
							onclick="javascript:createTemplate()" class="button" />
					</td>
					<td>

						<input type="button" value="ϵͳ����"
							onclick="javascript:applist()" class="button" />
					</td>
				</tr>
			</table>


		</form>
	</body>
</html>
