<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

		<title>表单管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript"
			src="rsinclude/pagelistpath/pagelistpath.js"></script>
		<script type="text/javascript" src="rsinclude/calendar.js"></script>
		<script type="text/javascript" src="rsinclude/util.js"></script>
		<script type="text/javascript">
		
		//单选择 需要根据应用的需要来订制
		function checkthis(id) {
			if(opener.selecthis==undefined){
				alert('非选择状态,父页面缺少回调函数');
				return;
			}
			opener.selecthis(id);
			window.close();
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
			alert(values)
		}
		
		//查询逻辑可以自定义扩展条件,SQL的子条件,日期属性created 有效属性active 1/0 扩展属性extendattribute
		function search(){
			form1.action="PagelistpathRightSvl";
			form1.submit();
		}
		var totalMap = new Map();
		function mapinfo(){
			<c:forEach items="${list}" var="list">
			totalMap.put('${list.id}','${list.extendattribute}');
			</c:forEach>
		}
		</script>
	</head>
	<body style="font-size: 12px; margin: 22px" onload='mapinfo()'>
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

				<br>
				<table width="96%" border="0" align="center" cellpadding="5"
					cellspacing="0">
					<tr>
						<td nowrap>
							名称:
						</td>
						<td width="100">
							<input type="text" name="snaturalname" value="${snaturalname}"
								class="textinput_td">
						</td>
						<td nowrap>
							中文名称:
						</td>
						<td width="100">
							<input type="text" name="sname" value="${sname}"
								class="textinput_td">
						</td>
						<td nowrap>
							开始时间:
						</td>
						<td width="100">
							<input type="text" name="begintime" value="${begintime}"
								class="textinput_td" onfocus="calendar()">
						</td>
						<td nowrap>
							结束时间:
						</td>
						<td width="100">
							<input type="text" name="endtime" value="${endtime}"
								class="textinput_td" onfocus="calendar()">
						</td>
						<td>
							<input type="button" name="searchbtn" value="查 询"
								onclick="search();" class="butt">
						</td>
					</tr>
					<tr>
						<td nowrap>
							路径:
						</td>
						<td colspan='6'>
							${link}
							<!-- 真正的路径naturalname -->
							<!-- ${pathreal} -->
						</td>
						<td colspan='1'>
							<a href='javascript:newInclusion();'><font color='blue'>新建目录</font>
							</a> &nbsp;&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan='9' align='right' nowrap>
							&nbsp;&nbsp;
							<script type="text/javascript">
								function newds(){
								   
									window.open('resourcepage/dyformlist/First.jsp?pagepath=${pathreal}&id=${upo.id}','_blank');
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
										alert("请先选中需要修改的选项");
										return;
									}
									if(k>1){
										alert("只能选择单项进行修改");
										return;
									}
									window.open('<%=path%>/ModifySummary.do?task=Modify&chkid='+value,'_blank');
								}
								
								function syn(){
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
										alert("请先选中需要修改的选项");
										return;
									}
									if(k>1){
										alert("只能选择单项进行修改");
										return;
									}
									window.open('<%=path%>/SynSummary.do?task=Syn&chkid='+value,'_blank');
								}
								
								function exportexcel(){
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
										alert("请先选中需要修改的选项");
										return;
									}
									if(k>1){
										alert("只能选择单项进行修改");
										return;
									}
									window.open('<%=path%>/Export.do?task=show&chkid='+value,'_blank');
								}
								
								function editsdy(type){
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
										alert("请先选中需要修改的选项");
										return;
									}
									if(k>1){
										alert("只能选择单项进行修改");
										return;
									}
									var formcode=totalMap.get(value);
									
									if(type=='formcolumn')
									window.open('/dyForm/design/system/column/list.do?formcode='+formcode,'_blank');
									if(type=='forminfo')
									window.open('/dyForm/design/system/form/modifyview.do?formcode='+formcode,'_blank');
									if(type=='template')
									window.open('/dyForm/design/system/form/createviewTemplate.do?formcode='+formcode,'_blank');
									if(type=='security')
									window.open('/dyForm/design/system/form/addToPermission.do?rsid='+value,'_blank');
									if(type=='dyrs')
									window.open('/dyForm/DyDataToSelectSvl?formcode='+formcode,'_blank');
									if(type=='removesecurity')
									window.open('/dyForm/design/system/form/addToPermission.do?rsid='+value+'&remove=yes','_blank');
								}								

							</script>
							<input type="button" value="新建表单" onclick="newds()" class="butt">
							&nbsp;&nbsp;
							<input type="button" name="btnmodify1" value="修改表单"
								onclick="editsdy('forminfo');" class="butt">
							<input type="button" name="btnmodify2" value="修改字段"
								onclick="editsdy('formcolumn');" class="butt">
							<input type="button" name="btnmodify3" value="安全保护"
								onclick="editsdy('security');" class="butt">
							<input type="button" name="btnmodify3" value="取消保护"
								onclick="editsdy('removesecurity');" class="butt">
							<input type="button" name="btnmodify3" value="创建表单资源"
								onclick="editsdy('dyrs');" class="butt">
							&nbsp;&nbsp;
							<input type="button" name="btndelete" value="删除" onclick="del();"
								class="butt">
							<input type="button" name="btndelete" value="修改"
								onclick="edit();" class="butt">
							&nbsp;&nbsp;

							<input type="button" name="btncheck" value="全选"
								onclick="checkthese();" class="butt">




						</td>
					</tr>
				</table>
				<HR>
				<table id="hang_table" width="96%" border="0" align="center"
					cellpadding="0" cellspacing="1">
					<tr>
						<td width="70" class="td_titt_bg" nowrap>
							<input type="checkbox" name="checkall" onclick="allcheck();">
							选择
						</td>
						<td class="td_titt_bg" nowrap>
							中文名称
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							名称
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							分类
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							日期
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							有效
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							操作
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
									${list.name}

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
									<select
										onchange='if(this.value!=0){ window.open(this.value);}this.value=0'
										style="width: 80">
										<option value='0'>
											-访问-
										</option>
										<option
											value='/dyForm/data/data/list.do?lsh=1&formcode=${list.extendattribute}&mode=manage'>
											普通列表
										</option>
										<option
											value='/dyForm/data/data/listShare.do?lsh=1&formcode=${list.extendattribute}&mode=useview'>
											共享应用列表
										</option>
										<option
											value='/dyForm/data/data/listShare.do?lsh=1&formcode=${list.extendattribute}&mode=onlyview'>
											共享视图列表1
										</option>
										<option
											value='/dyForm/data/showdata/listviews.do?lsh=1&formcode=${list.extendattribute}'>
											共享视图列表2
										</option>
										<option
											value='/dyForm/data/showdata/createview.do?fatherlsh=1&formcode=${list.extendattribute}'>
											快速创建
										</option>
									</select>
									<!--  
									<select onchange='if(this.value!=0){ window.open(this.value);}'
										style="width: 80">
										<option value='0'>
											-管理-
										</option>
										<option
											value='/dyForm/data/data/list.do?lsh=1&formcode=${list.extendattribute}'>
											数据管理
										</option>
										<option value='<%=path%>/Export.do?task=show&chkid=${list.id}'>
											导出数据
										</option>
										<option
											value='<%=path%>/SynSummary.do?task=Synbefore&chkid=${list.id}'>
											导入数据
										</option>
										<option
											value='/dyForm/servlet/FormViewsvl?formcode=${list.extendattribute}'>
											定义描述
										</option>
									</select>
									-->
									<a
										href="javascript:window.open('/dyForm/data/data/list.do?lsh=1&formcode=${list.extendattribute}','_parent');"
										target="_blank">[管理]</a>
									<a
										href="javascript:window.open('<%=path%>/Export.do?task=show&chkid=${list.id}','_parent');"
										target="_blank">[导出]</a>
									<a
										href="javascript:window.open('<%=path%>/SynSummary.do?task=Synbefore&chkid=${list.id}','_parent');"
										target="_blank">[导入]</a>
									<a
										href="javascript:window.open('/dyForm/servlet/FormViewsvl?formcode=${list.extendattribute}','_parent');"
										target="_top">[描述]</a>
									<a href="javascript:checkthis('${list.extendattribute}');">选择</a>
								</c:if>



							</td>
						</tr>

					</c:forEach>
				</table>

			</form>
		</div>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("修改失败！");
				form1.action="PagelistpathRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DeleteSuccess == 'n'}">
			<script type="text/javascript">
				alert("该节点下存在子节点,不允许删除！");
				form1.action="PagelistpathRightSvl";
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
