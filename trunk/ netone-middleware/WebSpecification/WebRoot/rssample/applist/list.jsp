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

		<title>公告管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/oe.css">
		<script type="text/javascript" src="rsinclude/page.js"></script>
		<script type="text/javascript" src="rsinclude/applist/applist.js"></script>
		<script type="text/javascript">
		//执行点击树节点操作

		//单选择 需要根据应用的需要来订制
		function check(chkid){
			if(chkid){
				window.open("ApplistModifySvl?task=edit&chkid="+chkid+"&pagename=publiccheck","usermod","width=700,height=350,resizable=yes,left=150,status=yes");
			} else {
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
				window.open("ApplistModifySvl?task=edit&chkid="+value+"&pagename=publiccheck","usermod","width=700,height=350,resizable=yes,left=150,status=yes");
			}
		}
		
		//查询逻辑可以自定义扩展条件,SQL的子条件,日期属性 created 有效属性 active 1/0 扩展属性 extendattribute
		function search(){
			//默认的一个扩展条件,只能显示active!='0'的记录
			document.getElementById('ext').value=" and apptype='publicinfo'";
			form1.action="ApplistRightSvl";
			form1.submit();
		}
		
		function preview(){
			window.open("ApplistRightSvl?pagename=publicview&ext=and apptype='publicinfo' and active='1'",'_blank');
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
							标题
						</td>
						<td width='100'>
							<input type="text" name="name" value="${name}"
								class="textinput_td">
						</td>
						<td>
							<input type="button" name="searchbtn" value="查 询"
								onclick="search();" class="butt">
						</td>
						<td>
							<input type="button" name="searchbtn" value="预 览"
								onclick="preview();" class="butt">
						</td>
						<td align='right' nowrap>
							<input type="button" name="btncreate" value="新 建"
								onclick="newElemnt();" class="butt">
							&nbsp;
							<input type="button" name="btnmodify" value="修 改"
								onclick="edit();" class="butt">
							&nbsp;
							<input type="button" name="btnmodify" value="审 批"
								onclick="check();" class="butt">
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
							标题
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							创建日期
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							类型
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							审批通过
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							操作
						</td>
					</tr>
					<c:forEach items="${list}" var="list">
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
								${list.apptype}
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
								<a href="javascript:check('${list.id}');">审批</a>
							</td>
						</tr>
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
