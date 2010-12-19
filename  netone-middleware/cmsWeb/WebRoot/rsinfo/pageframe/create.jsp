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
		<title>新建页框</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<SCRIPT type="text/javascript" src="<%=path%>\include\js\prototype.js"></SCRIPT>
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<SCRIPT type="text/javascript"
			src="<%=path%>\include\js\modelshow\personmodel.js"></SCRIPT>
		<script type="text/javascript">
		
		//打开树选择页面
		function searchtree() {
			window.open("SelectSvl?appname=BUSSENV&pagename="+document.all.pagename.value);
		}
		//执行点击树节点操作
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
			document.all.objecttype.value = name+"["+naturalname+"]";
		}
		
		function sselected(id){
				   var url=$('pagetypeinfo').value;
				   $('actionurl').value=url+id;
		}
		</script>
	</head>
	<body style="font-size: 12px; margin: 22px" >
		<c:if test="${CreateSuccess == 'y'}">
			<script type="text/javascript">
				alert("新建成功！")
				opener.search();
			</script>
		</c:if>
		<c:if test="${CreateSuccess == 'n'}">
			<script type="text/javascript">
				alert("${errorinfo}");
			</script>
		</c:if>
		<div style="width: 100%; height: 100%">
			<form action="PagelistAddSvl?task=addsave" method="post">
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="id" value="${id}" />
				<input type="hidden" name="appid" value="${appid}" />
				<input type="hidden" name="ou" value="${ou}" />
				<input type="hidden" name="inclusion" value="${inclusion}" />
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr>
						<td width="15%">
							有&nbsp;&nbsp;&nbsp;&nbsp;效
						</td>
						<td>
							<input type="checkbox" name="active" value="1" checked />
						</td>
					</tr>
					<tr>
						<td width="15%">
							名&nbsp;&nbsp;&nbsp;&nbsp;称
						</td>
						<td>
							<input type="text" name="naturalname" value=""
								class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							中文名称
						</td>
						<td>
							<input type="text" name="name" value="" class="textinput_td" />
						</td>
					</tr>
					<tr style='display:none'>
						<td width="15%">
							<font color='blue'>分类</font>
						</td>
						<td width='300'>
							<input type="text" name="objecttype"
								value="BUSSENV.BUSSENV.BUSSFRAME" class="textinput_td"
								readonly="readonly">
						</td>
					</tr>
					<tr style='display:none' id='pagegroup'>
						<td width="15%">
							<font color='blue'>页组</font>
						</td>
						<td>
							<select name='pagetypeinfo'>
								<option
									value='/cmsWeb/portal.do?toolbar=true&border=true&portalmode=1&id='>
									带控制条
								</option>
								<option value='/cmsWeb/portal.do?border=true&portalmode=2&id='>
									无控制条带边框
								</option>
								<option value='/cmsWeb/portal.do?portalmode=3&id='>
									无控制条无边框
								</option>
							</select>
							<a href="/cmsWeb/SSelectSvl?pagename=pagegroup&appname=PORTALPG" target='_blank'>[选择页组]</a><A HREF="javascript:" onclick="window.open('/cmsWeb/PagelistpathRightSvl?pagename=pagegroup&appname=PORTALPG','_blank')"><FONT
						class="OecLink">创建/管理页组</FONT> </A>
						</td>
					</tr>
					<tr>
						<td>引用地址</td>
						<td>
						<input type="text" name="actionurl" class="textinput_td"/> &nbsp;<input type='checkbox' name='usepagegroup' onClick="if(this.checked)pagegroup.style.display='';else pagegroup.style.display='none'; "/>使用页组
						</td>
					</tr>
					<tr>
						<td width="15%">
							层次类型
						</td>
						<td>
							<select name='extendattribute'>
								<option value='normal'>普通页</option>
								<option value='final'>终了页</option>
							</select>
							<input type="hidden" name="needSerilaizer" value="1" />
						
						</td>
					</tr>
					<tr>
						<td width="15%">
							描述
						<br><br></td>
						<td><textarea rows="4" cols="60" name="description"></textarea>
						<br><br></td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input type="button" name="btnnew" value="新 建" onclick="create()"
						class="butt">
					<input type="button" name="btncancel" value="取 消"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
