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

		<title>页管理</title>

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
		function checkthis(id) {
		
			var sel=self.opener.document.getElementById('valueid');
			if(sel!=null){
			   var obj=opener.document.getElementById(id);
			   if(obj!=null){
			   		alert('该页已加入布局');
			   		return;
			   }else{
			       sel.value=id;
			       opener.document.getElementById('valuedo').onclick();
			       alert('已添加入布局页');

			   }
			}
		}
		
		function deleteDo(id,cell){
			alert(cell);
		    var obj=opener.document.getElementById(cell);
		    if(obj!=null){
		   		 opener.removeFDiv2(obj);
		   		 opener.savelayout();
		    }
		    del(id);
		}

		
		//查询逻辑可以自定义扩展条件,SQL的子条件,日期属性created 有效属性active 1/0 扩展属性extendattribute
		function search(){
			form1.action="PagelistpathRightSvl";
			form1.submit();
		}
		
		function newElemntX(pagepath){
			window.open('<%=basePath%>dspage.do?pagepath='+pagepath,'_blank');
		}
		</script>
	</head>
	<body style="font-size: 12px; margin: 22px">
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

						</td>
					</tr>
					<tr>
						<td nowrap>
							路径:
						</td>
						<td>
							${link} &nbsp;&nbsp;

						</td>
						<td colspan='7'>
							<input type="button" name="searchbtn" value="查 询"
								onclick="search();" class="butt">

						</td>
					</tr>
				</table>
				<br>

				<table id="hang_table" width="96%" border="0" align="center"
					cellpadding="0" cellspacing="1">
					<tr>
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

							<td align='left' nowrap>
								<c:choose>
								<c:when test="${(list.inclusion == '1' or list.objecttype =='pagegroup') and list.objecttype !='page'}">
									<a href="javascript:inclusionlink('${list.id}','${list.ou}');"><img BORDER='0' src='<%=basePath%>rsinclude/images/folder.png'></a>
									<a href="javascript:view('${list.id}');">${list.name}</a>
								</c:when>
								<c:otherwise>
								<a href="javascript:view('${list.id}');">${list.name}</a>
								</c:otherwise>		
								</c:choose>
							</td>
							<td align='left' nowrap>
								${list.naturalname}
							</td>
							<td align='left' nowrap>
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
							
								<c:if test="${ (list.inclusion != '1' or list.objecttype =='page') and list.objecttype !='pagegroup'}">
								<a
									href="javascript:deleteDo('${list.id}','${list.extendattribute}');">删除</a>
								<a href="javascript:checkthis('${list.extendattribute}');">加入布局</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<HR>
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
