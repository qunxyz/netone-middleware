<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
		<title>新建子节点</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript" src="include/js/prototype.js"></script>
		<script type="text/javascript">
		
		//打开树选择页面
		function searchtree() {
			window.open("SelectSvl?appname=BUSSENV&pagename="+document.all.pagename.value,'_blank', 'height=380, width=600, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
		}
		//执行点击树节点操作
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
			document.all.objecttype.value = name+"["+naturalname+"]";
		}
		
		function todo(){
				var beanurl=$('beanurl').value;
				var beanname=$('beanname').value;
				window.open('/cmsWeb/RmiBeanTestSvl?beanname='+beanname+'&beanurl='+beanurl,'_blank');
		}
		
		function predo(){
				var beanurl=$('beanurl').value;
				var beanname=$('beanname').value;
				$('extendattribute').value=beanurl+"#"+beanname;
		}		
		</script>

	</head>
	<body style="font-size: 12px; margin: 22px">
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
				<input type="hidden" name="pagename" id="pagename"
					value="${pagename}" />
				<input type="hidden" name="id" id="id" value="${id}" />
				<input type="hidden" name="appid" id="appid" value="${appid}" />
				<input type="hidden" name="ou" id="ou" value="${ou}" />
				<input type="hidden" name="inclusion" id="inclusion"
					value="${inclusion}" />
				<input type="hidden" name="extendattribute" id="extendattribute"
					value="${extendattribute}" />
				<table width="96%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr>
						<td width="15%">
							有&nbsp;&nbsp;&nbsp;&nbsp;效
						</td>
						<td>
							<input type="checkbox" name="active" id="actove" value="1"
								checked />
						</td>
					</tr>
					<tr>
						<td width="15%">
							名&nbsp;&nbsp;&nbsp;&nbsp;称
						</td>
						<td>
							<input type="text" name="naturalname" id="naturalname" value=""
								class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							中文名称
						</td>
						<td>
							<input type="text" name="name" id="name" value=""
								class="textinput_td" />
						</td>
					</tr>
					<tr style='display: none'>
						<td width="15%">
							<a href="javascript:searchtree();"><font color='blue'>选择分类</font>
							</a>
						</td>
						<td width='300'>
							<input type="text" name="objecttype" id="objecttype" value=""
								class="textinput_td" readonly="readonly">
						</td>
					</tr>
					<tr style='display: none'>
						<td width="15%">
							引用
							<br>
						</td>
						<td>
							<input type="text" name="actionurl" id="actionurl" value=""
								class="textinput_td" />
							<br>
						</td>
					</tr>
					<tr>
						<td width="15%">
							服务地址
						</td>
						<td>
							<input type='text' name="beanurl" id="beanurl"
								class="textinput_td" />

							<input type="hidden" name="needSerilaizer" id="needSerilaizer"
								value="1" />

						</td>
					</tr>
					<tr>
						<td width="15%">
							服务名
						</td>
						<td>
							<input type='text' name="beanname" id="beanname"
								class="textinput_td" />
							<input type='button' value='测试' onClick='todo()'>

						</td>
					</tr>
					<tr>
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
					<input type="button" name="btnnew" value="新 建"
						onclick="predo();create();" class="butt">
					<input type="button" name="btncancel" value="取 消"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
