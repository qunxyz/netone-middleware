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

		<title>页管理</title>

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
							名称:
						</td>
						<td width="100">
							<input type="text" name="snaturalname" value="${naturalname}"
								class="textinput_td">
						</td>
						<td nowrap>
							中文名称:
						</td>
						<td width="100">
							<input type="text" name="sname" value="${name}"
								class="textinput_td">
						</td>
						<td nowrap>
							开始时间:
						</td>
						<td width="100">
							<input type="text" name="begintime" value="${begintime}"
								class="textinput_td" onfocus="calendar();">
						</td>
					</tr>
					<tr>
						<td nowrap>
							<a href='javascript:'><font color='blue'>路径</font> </a>
						</td>
						<td width='100'>
							<input type="text" name="spathname" value="${upo.name}"
								class="textinput_td" readonly="readonly">
							<input type="hidden" name="spath" value="${upo.id}"
								class="textinput_td">
						</td>

						<td>
							<input type="button" name="searchbtn" value="查 询"
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
							<input type="button" value="新建" onclick="newElemnt();"
								class="butt">
							&nbsp;
							<input type="button" name="btnmodify" value="修 改"
								onclick="edit();" class="butt">
							&nbsp;
							<input type="button" name="btndelete" value="删 除"
								onclick="del();" class="butt">
							&nbsp;

							<input type="button" name="btncheck" value="多 选"
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
								<a href="javascript:edit('${list.id}');">修改</a>
								<a href="javascript:del('${list.id}');">删除</a>
								<a href="javascript:checkthis('${list.id}');">选择</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<br>
				<div align="left">

					&nbsp;
					<input type="button" name="btnup" value="  上   移  " onclick="up();"
						class="butt">
					&nbsp;
					<input type="button" name="btndown" value="  下   移  "
						onclick="down();" class="butt">
					&nbsp;
					<input type="button" name="btndown" value="  关闭并且更新页  "
						onclick="closedo();" class="butt">
				</div>
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
