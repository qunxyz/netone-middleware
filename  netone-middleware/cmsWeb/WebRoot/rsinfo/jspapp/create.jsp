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
		<title>新建页程序</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript">
		
		//打开树选择页面
		function searchtree() {
			window.open("SelectSvl?appname=BUSSENV&pagename="+document.all.pagename.value);
		}
		//执行点击树节点操作
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
			document.all.objecttype.value = name+"["+naturalname+"]";
		}
		</script>
	</head>
	<body style="font-size: 12px;margin: 22px">
		<c:if test="${CreateSuccess == 'y'}">
			<script type="text/javascript">
				alert("新建成功！")
				opener.search();
			</script>
		</c:if>
		<c:if test="${CreateSuccess == 'n'}">
			<script type="text/javascript">
				alert("新建目录失败！")
			</script>
		</c:if>
		<div style="width: 100%;height: 100%">
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
							安&nbsp;&nbsp;&nbsp;&nbsp;全
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
					<tr >
						<td width="15%">
							分类
						</td>
						<td width='300'>
							<input type="text" name="objecttype" value="BUSSENV.BUSSENV.PORTAL.JSP "
								class="textinput_td" readonly="readonly">
						</td>
					</tr>
					<tr style='display:none'>
						<td width="15%">
							引用
						</td>
						<td>
							<input type="text" name="actionurl" value="" class="textinput_td" />
						</td>
					</tr>
					<tr>
						
						<td colspan='2'>
						编辑页脚本: 
						<a href='rsinfo/jspapp/Template.txt' target='_blank'><font color='#999999'>[参考模板1]</font></a>
						<a href='rsinfo/jspapp/Template1.txt' target='_blank'><font color='#999999'>[参考模板2]</font></a>
						<a href='rsinfo/jspapp/Template2.txt' target='_blank'><font color='#999999'>[参考模板3]</font></a>
						<a href='rsinfo/jspapp/resourceweb.zip' target='_blank'><font color='green'>[资源页模板包]</font></a>
						<a href='rsinfo/jspapp/flowpage.zip' target='_blank'><font color='green'>[流程页模板包]</font></a>
						<a href='rsinfo/jspapp/AppFrame1.txt' target='_blank'><font color='green'>[系统页框1]</font></a>
						<a href='rsinfo/jspapp/AppFrame2.txt' target='_blank'><font color='green'>[系统页框2]</font></a>
						
	<a href='rsinfo/jspapp/netone_tag_all.doc' target='_blank'><font color='red'>[页标签例子大全]</font></a>
						<a href='rsinfo/jspapp/OecWeb.zip' target='_blank'><font color='red'>[页标签开发包]</font></a>
					
						<textarea rows="30" cols="120" name="extendattribute"></textarea>
							<br>
							<input type="hidden" name="needSerilaizer" value="1" />
						</td>
					</tr>



					<tr style='display:none'>
						<td width="15%">
							描述
						</td>
						<td>
							<textarea rows="4" cols="60" name="description"></textarea>
						</td>
					</tr>
				</table>
				<br>
				<div align="center">
					<input type="button" name="btnnew" value="新 建" onclick="create();"
						class="butt">
					<input type="button" name="btncancel" value="取 消"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
