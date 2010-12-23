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

		<title>数据源管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript"
			src="rsinclude/pagelistpath/pagelistpath.js"></script>
		<script type="text/javascript" src="rsinclude/calendar.js"></script>

		<script type="text/javascript">
		//单选择 需要根据应用的需要来订制
		function checkthis(naturalname,extendattribute,id) {
			if(window.opener){
				window.opener.todo(naturalname,extendattribute,id);
				window.close();
			} else {
				alert(extendattribute)
			}
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
						<td>
							${link}
							<!-- 真正的路径naturalname -->
							<!-- ${pathreal} -->
						</td>
						<td colspan='7'>
							<input type="button" value="新建目录" onclick="newInclusion();"
								class="butt">
							&nbsp;&nbsp;

						</td>
					</tr>
					<tr>
						<td colspan='9' align='right' nowrap>
														<input type="button" value="新建Db数据源" onclick="newElemnt()"
								class="butt">

							<input type="button" name="btnmodify" value="修 改"
								onclick="edit();" class="butt">
							&nbsp;&nbsp;
							<input type="button" name="btndelete" value="删 除"
								onclick="del();" class="butt">

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
						<c:if test="${list.objecttype!='二进制流[BINARY]'}">
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
										<a href="javascript:view('${list.id}');">${list.name}</a>
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
									<a href="javascript:edit('${list.id}');">修改</a>
									<a href="javascript:del('${list.id}');">删除</a>
									<a
										href="javascript:checkthis('${list.naturalname}','${list.extendattribute}','${list.id}');">选择</a>
								</td>
							</tr>
						</c:if>
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
