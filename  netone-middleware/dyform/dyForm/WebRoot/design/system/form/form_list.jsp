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
		<title>表单设计</title>
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
							alert('请选择一个要修改的记录!');
							return;
						}
					}
			}
			if(values==''){
				alert('请选择一个要修改的记录!');
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
							alert('请选择一个要修改的记录!');
							return;
						}
					}
			}
			if(values==''){
				alert('请选择一个要修改的记录!');
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
							alert('请选择一个要修改的记录!');
							return;
						}
					}
			}
			if(values==''){
				alert('请选择一个要修改的记录!');
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
							alert('请选择一个要修改的记录!');
							return;
						}
					}
			}
			if(values==''){
				alert('请选择一个要修改的记录!');
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
						选择
					</td>
					<td class="tdheadline">
						表单名
					</td>
					<td class="tdheadline">
						日期
					</td>
					<td class="tdheadline">
						对应的表名
					</td>
					<td class="tdheadline">
						表单资源
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
						<input type="button" value="创建表单" onclick="javascript:create()"
							class="button" />
					</td>
					<td>
						<input type="button" value="修改表单" onclick="javascript:modify()"
							class="button" />
					</td>
					<td>
						<input type="button" value="删除表单" onclick="javascript:dele()"
							class="button" />
					</td>
					<td>
						<input type="button" value="设计/修改表单字段"
							onclick="javascript:modifycolumn()" class="button" />

					</td>
					<td>

						<input type="button" value="创建模板"
							onclick="javascript:createTemplate()" class="button" />
					</td>
					<td>

						<input type="button" value="系统管理"
							onclick="javascript:applist()" class="button" />
					</td>
				</tr>
			</table>


		</form>
	</body>
</html>
