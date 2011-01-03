<%@ page contentType="text/html; charset=gbk"%>
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

		<title>检索结果</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript" src="rsinclude/calendar.js"></script>
		<script type="text/javascript">
		//单选择 需要根据应用的需要来订制
		function checkthis(id) {
			alert(id)
		}
		
		function searchall1(){
			document.forms[0].action="SearchSvl";
			document.forms[0].submit();
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
		
		//查询逻辑可以自定义扩展条件,SQL的子条件,日期属性created 有效属性active 1/0 扩展属性extendattribute
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
		
		
		//打开树选择页面
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
							<input type="button"  value="全文检索" onclick="searchall1();" class="butt">
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
							选择
						</td>
						<td class="td_titt_bg" nowrap>
							中文名称
						</td>
						<td class="td_titt_bg" nowrap>
							名称
						</td>

						<td class="td_titt_bg" nowrap>
							分类
						</td>
						<td class="td_titt_bg" nowrap>
							日期
						</td>
						<td class="td_titt_bg" nowrap>
							有效
						</td>
						<td class="td_titt_bg" nowrap>
							操作
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
								<a href="javascript:edit('${list.id}');">修改</a>
								<a href="javascript:view('${list.id}');">浏览1</a>
								<a href="javascript:view1('${list.id}');">浏览2</a>
								</c:if>
								<a href="javascript:del('${list.id}');">删除</a>
							</td>
							
						
						</tr>
					</c:forEach>
				</table>
				<br>
			</form>
		</div>
	</body>
</html>
