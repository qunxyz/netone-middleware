<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>">

		<title>维度数据</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/oe.css">
		<script type="text/javascript" src="rsinclude/page.js"></script>
		<script type="text/javascript" src="rsinclude/applist/applist.js"></script>
		<script type="text/javascript">
		//执行点击树节点操作
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
		
		}
		//单选
		function checkthis(name,natural) {
			//alert(id+'_'+name+'_'+chinesename);
			opener.addselect(name+'['+natural+']');
			window.close();
		}
		
		//显示
		function viewthis(id) {
			window.open("SelectSvl?appid="+id+"&pagename="+document.all.pagename.value);
		}
		
		//查询逻辑可以自定义扩展条件,SQL的子条件,日期属性 created 有效属性 active 1/0 扩展属性 extendattribute
		function search(){
			//默认的一个扩展条件,只能显示active!='0'的记录
			document.getElementById('ext').value="and active!='0' and naturalname='PORTALPG'";
			form1.action="ApplistRightSvl";
			form1.submit();
		}
		</script>

	</head>
	<body style="font-size: 12px;margin: 22px">
		<div style="width: 100%;height: 100%">

			<form action="" method="post" name="form1">
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="ext" value="" />

				<table width="90%" border="0">
					<tr>
						<td nowrap>
							&nbsp;&nbsp;名称
						</td>
						<td width='100'>
							<input type="text" name="naturalname" value="${naturalname}"
								class="textinput_td">
						</td>
						<td nowrap>
							中文名称
						</td>
						<td width='100'>
							<input type="text" name="name" value="${name}"
								class="textinput_td">
						</td>
						<td>
							<input type="button" name="searchbtn" value="查 询"
								onclick="search();" class="butt">
						</td>
						<td align='right' nowrap>
							<input type="button" name="btncreate" value="新 建"
								onclick="newElemnt();" class="butt">
							&nbsp;
							<input type="button" name="btnmodify" value="修 改"
								onclick="edit();" class="butt">
							&nbsp;
							<input type="button" name="btndelete" value="删 除"
								onclick="del();" class="butt">
						</td>
					</tr>
				</table>

				<br>
				<table id="hang_table" width="96%" border="0" align="center"
					cellpadding="0" cellspacing="1">
					<tr>
						<td width="70" class="td_titt_bg" nowrap>
							<input type="checkbox" name="checkall" onclick="allcheck();">
							选择
						</td>
						<td class="td_titt_bg" nowrap>
							名称
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							中文名称
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							创建日期
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							有效
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							操作
						</td>
					</tr>
					<c:forEach items="${list}" var="list">
						<c:if test="${list.naturalname=='PORTALPG'}">
							<tr>
								<td nowrap>
									<input type="checkbox" name="chkid" value="${list.id}">
								</td>
								<td nowrap>
									${list.naturalname}
								</td>
								<td nowrap>
									${list.name}
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
									<a href="javascript:edit('${list.id}');">[修改]</a>
									<a href="javascript:del('${list.id}');">[删除]</a>
									<a href="javascript:viewthis('${list.id}');">[查看]</a>
									<a
										href="javascript:checkthis('${list.name}','${list.naturalname}','${list.name}');"><font
										color='red'>[选择]</font> </a>

									<a
										href="javascript:managerthis('${list.naturalname}','path','datalist');"><font
										color='blue'>[管理]</font>
									</a>
								</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
				<br>
				<script type="text/javascript">
					var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
					pginfo.write();
				</script>

			</form>
		</div>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("修改失败！");
				form1.action="ApplistRightSvl";
				form1.submit();
			</script>
		</c:if>
		<c:if test="${DeleteSuccess == 'n'}">
			<script type="text/javascript">
				alert("删除失败");
				form1.action="ApplistRightSvl";
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
