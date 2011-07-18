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

		<title>应用开发框架资源管理</title>

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
			if(opener.selected==undefined){
				alert('非选择状态,父页面缺少回调函数');
				return;
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
		
		function cfg_p(id){
			window.open("CreateSubSvl?&pagename=${pagename}&chkid="+id);
		}
		function cfg_dy(id){
			window.open("CreateDySubSvl?&pagename=${pagename}&chkid="+id);
		}
		
		function man(name){
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>frame.do?method=onMainView&naturalname='+name;
			window.open(url);
		}
		
		function worklist(name){    
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>/WorkListSvl?ope=y&limit=0&status=01&appname='+name;
			window.open(url);
		}
		
		function worklistR(name){    
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>/WorkListSvl?ope=n&limit=0&status=01&appname='+name;
			window.open(url);
		}
		function worklistdone(name){
			var url='<portal:envget envkey="WEBSER_APPFRAME"/>/WorkListSvl?ope=y&limit=0&status=02&appname='+name;
			window.open(url);
		}
		function init(id){
			var url='InitCfgSvl?id='+id;
			window.open(url);
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
				<input type="hidden" name="entryvar" value="<%=entryvar%>" />
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
							<input type="button" value="新建应用" onclick="newElemnt();"
								class="butt">
							&nbsp;&nbsp; &nbsp;&nbsp;
							<input type="button" name="btndelete" value="删 除"
								onclick="del();" class="butt">
							&nbsp;&nbsp;
							<input type="button" name="btncheck" value="全 选"
								onclick="checkthese();" class="butt">
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
							名称中文名称
						</td>
						<td class="td_titt_bg" width="70" nowrap>
							名称
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
									<a href="javascript:view('${list.id}');">${list.name}</a>
								</c:if>
							</td>
							<td nowrap>
								${list.naturalname}
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
								<c:if test="${list.inclusion!='1'}">
									 <a
										href="javascript:edit('${list.id}');"><font color='red'>[框架配置]</font></a> <a
										href="javascript:cfg_p('${list.id}');"><font color='red'>[参与者配置]</font></a> <a
										href="javascript:cfg_dy('${list.id}');"><font color='red'>[流程表单配置]</font></a> 
										
									<a href="javascript:man('${list.naturalname}');"><font color='green'>过程:管理</font></a>
									<a href="javascript:worklist('${list.naturalname}');"><font color='green'>待办应用</font></a>
									<a href="javascript:worklistR('${list.naturalname}');"><font color='green'>待阅应用</font></a>
									<a href="javascript:worklistdone('${list.naturalname}');"><font color='green'>已办应用</font></a>
									<a href="javascript:init('${list.id}');">初始化</a>
									<a href="javascript:del('${list.id}');">删除</a>
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
