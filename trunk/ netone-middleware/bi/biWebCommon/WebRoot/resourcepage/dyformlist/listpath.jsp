<%@ page contentType="text/html; charset=GBK"%>
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
		var activeMap = new Map();
		function mapinfo(){
			<c:forEach items="${list}" var="list">
			totalMap.put('${list.id}','${list.extendattribute}');
			activeMap.put('${list.id}','${list.active}');
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
								function newds1(){
								   
									window.open('<portal:envget envkey="WEBSER_SpeedyForm"/>NewDoForm.html?parentdir=${upo.naturalname}','_blank');
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
									var active=activeMap.get(value);
									if(type=='formcolumn')
									window.open('/dyForm/design/system/column/list.do?formcode='+formcode,'_blank');
									if(type=='forminfo')
									window.open('/dyForm/design/system/form/modifyview.do?formcode='+formcode,'_blank');
									if(type=='template')
									window.open('/dyForm/design/system/form/createviewTemplate.do?formcode='+formcode,'_blank');
									if(type=='security')
									window.open('/biWeb/servlet/DySecuritySvl?rsid='+value,'_blank');
									if(type=='dyrs')
									window.open('/dyForm/DyDataToSelectSvl?formcode='+formcode,'_blank');
									if(type=='removesecurity')
									window.open('/biWeb/servlet/DySecuritySvl?rsid='+value+'&remove=yes','_blank');
									if(type=='forminfox')
									window.open('<portal:envget envkey="WEBSER_SpeedyForm"/>UpdateDoForm.html?naturalname=${upo.naturalname}&formid='+formcode,'_blank');
							        if(type=='copy')
									window.open('/dyForm/DyFormCopySvl?pagepath=${upo.naturalname}&formcode='+formcode,'_blank');
									if(type=='istake')
									window.open('/netone/dyistake?pagepath=${upo.naturalname}&formcode='+formcode,'_blank');
								}								

							</script>
							<input type="button" value="新建表单" onclick="newds1()" class="butt">
							<input type="button" value="修改表单" onclick="editsdy('forminfox')" class="butt">&nbsp;&nbsp;

							<input type="button" name="btnmodify2" value="修改字段"
								onclick="editsdy('formcolumn');" class="butt">
						<!--  

							<input type="button" value="新建表单(旧)" onclick="newds()" class="butt">
							
							<input type="button" name="btnmodify1" value="修改表单(旧)"
								onclick="editsdy('forminfo');" class="butt">


							-->
							<input type="button" name="btnmodify4" value="复制表单"
								onclick="editsdy('copy');" class="butt">
						    <input type="button" name="btnmodify5" value="是否启用"
								onclick="editsdy('istake');" class="butt">
							<!-- 
							
							<input type="button" name="btnmodify3" value="创建表单资源" onclick="editsdy('dyrs');" class="butt"> 
							
							-->
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

									
									<a
										href="javascript:window.open('<portal:envget envkey="WEBSER_APPFRAME"/>frame.do?method=onPreviewMain&formcode=${list.extendattribute}&isedit=1','_parent');"
										target="_blank"><font color='red'>[预览]</font></a>									
									<a   
										href="javascript:window.open('<portal:envget envkey="WEBSER_APPFRAME"/>/frame.do?method=onMainView&naturalname=${list.naturalname}','_parent');"
										target="_blank">[管理]</a>
									<a
										href="javascript:window.open('<%=path%>/Export.do?task=show&chkid=${list.id}','_parent');"
										target="_blank">[导出]</a>
									<a
										href="javascript:window.open('<portal:envget envkey="WEBSER_SpeedyForm"/>Diagram.html?formcode=${list.extendattribute}&isedit=1','_parent');"
										target="_blank"><font color='red'>[查看图表]</font></a>									
									<a
										href="resourcepage/dyformlist2/report.jsp?formcode=${list.extendattribute}"
										target="_blank"><font color='red'>[查看报表]</font></a>
									<a
										href="resourcepage/dyformlist2/reportExcel.jsp?formcode=${list.extendattribute}"
										target="_blank"><font color='red'>[导出EXCEL]</font></a>
										
									<a
										href="javascript:window.open('<portal:envget envkey="WEBSER_BIWEB"/>/PagelistRightSvl?pagename=dyformlist&appname=${list.naturalname}','_parent');"
										target="_top">[功能配置]</a>
										<!--  
									<a
										href="javascript:window.open('<%=path%>/SynSummary.do?task=Synbefore&chkid=${list.id}','_parent');"
										target="_blank">[导入]</a>-->
									<a
										href="javascript:window.open('/biWeb/servlet/FormViewsvl?formcode=${list.extendattribute}','_parent');"
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
