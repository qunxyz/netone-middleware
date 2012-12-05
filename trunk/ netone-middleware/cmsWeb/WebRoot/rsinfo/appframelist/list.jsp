<%@ page contentType="text/html; charset=gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//存放自定义的业务临时数据
	String entryvar = (String) request.getAttribute("entryvar");
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>应用环节配置</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript" src="rsinclude/calendar.js"></script>
		<script type="text/javascript" src="include/js/workflow/soa.js"></script>
		<script type="text/javascript" src="include/js/workflow/soa2.js"></script>
		<script type="text/javascript" src="include/js/workflow/soa4.js"></script>
		<script type="text/javascript" src="include/js/workflow/soax.js"></script>
		<script type="text/javascript">
			//单选择 需要根据应用的需要来订制
			function checkthis(id) {
				alert(id)
			}
			//多选择 需要根据应用的需要来订制
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
					alert('没有选择的元素');
				}else{
					alert(values);
				}
				
			}
			
			//执行点击树节点操作
			function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
				document.all.spathname.value = name;
				document.all.spath.value = id;
			}
			
			
			//打开树选择页面
			function searchtree() {
				window.open("SelectSvl?appid="+document.all.appid.value+"&pagename="+document.all.pagename.value);
			}
		
			
			
		</script>

	</head>
	<body style="font-size: 12px; margin: 22px">

		<div style="width: 100%; height: 100%">
			<form action="" method="post" name="form1">
				<input type="hidden" name="pagename" value="participant" />

				<table id="hang_table" width="80%" border="0" cellpadding="0"
					cellspacing="1">
					<tr>
						<td width="70" style='display:none'>
							<input type="checkbox" name="checkall" onclick="allcheck();">
							选择
						</td>
						<td class="td_titt_bg" nowrap>
							中文名称
						</td>
						<td class="td_titt_bg" nowrap>
							名称
						</td>
						<td class="td_titt_bg" nowrap>
							类型
						</td>

						<td class="td_titt_bg" nowrap>
							手动
						</td>
						<td class="td_titt_bg" nowrap>
							操作
						</td>
					</tr>

					<c:forEach items="${listxz}" var="list">
						<tr>
							<td style='display:none'>
								<input type="checkbox" name="chkid" value="${list.id}">
							</td>
							<td align='left' nowrap>
								<a href="javascript:view('${list.id}');">${list.name}</a>
							</td>
							<td align='left' nowrap>
								${list.naturalname}
							</td>
							<td align='left' nowrap>

								<c:if test="${list.objecttype=='human'}">人员</c:if>
													
								<c:if test="${list.objecttype=='team'}">组</c:if>
									
								<c:if test="${list.objecttype=='dept'}">组织</c:if>
	
								<c:if test="${list.objecttype=='role'}">角色</c:if>

							    <c:if test="${list.objecttype=='flowrole'}">流程角色</c:if>

								<c:if test="${list.objecttype=='creater'}">创建者</c:if>
								<c:if test="${list.objecttype=='flowrolecreater'}">流程创建者角色</c:if>							
								
							</td>
							<td nowrap>
								<c:if test="${list.active=='1'}">
									手动
								</c:if>
								<c:if test="${list.active=='0'}">
									自动
								</c:if>
							</td>

							<td nowrap>
								<a href="javascript:edit('${list.id}');"><font color='blue'>[配置]</font>
								</a>
								<a href="javascript:del('${list.id}');"><font color='blue'>[删除]</font>
								</a>
							</td>
						</tr>
					</c:forEach>
				</table>

				<iframe src='<portal:envget envkey="WEBSER_WFWEB"/>/viewreadonlyprocess.do?processid=${processid }' frameborder="0" width="1000" height="400"></iframe>

			</form>
		</div>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("修改失败！");
				form1.action="PagelistRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DeleteSuccess == 'n'}">
			<script type="text/javascript">
				alert("该节点下存在子节点,不允许删除！");
				form1.action="PagelistRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DelSuccess == 'y'}">
			<script type="text/javascript">
				alert("删除成功！");
			</script>
		</c:if>
	</body>
</html>
