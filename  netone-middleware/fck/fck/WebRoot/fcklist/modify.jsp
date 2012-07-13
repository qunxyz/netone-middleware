<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="FCK" %> 
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
		<title>修改子结点</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="rsinclude/css/css.css">
		<script type="text/javascript" src="rsinclude/pagelist/pagelist.js"></script>
		<script type="text/javascript">
				//打开树选择页面
		function searchtree() {
			window.open("SelectSvl?appname=BUSSENV&pagename="+document.all.pagename.value,'_blank', 'height=380, width=600, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
		}
				//执行点击树节点操作
		function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
			document.all.objecttype.value = name+"["+naturalname+"]";
		}
		</script>
	</head>
	<body style="font-size: 12px;margin: 22px">
		<c:if test="${ModifySuccess == 'y'}">
			<script type="text/javascript">
				alert("修改成功！")
				
			</script>
		</c:if>
		<c:if test="${ModifySuccess == 'n'}">
			<script type="text/javascript">
				alert("修改失败！")
			</script>
		</c:if>
		<div style="width: 100%;height: 100%">
			<form action="PagelistModifySvl?task=editsave" method="post"
				name="form1">
				<input type="hidden" name="pagename" value="${pagename}" />
				<input type="hidden" name="id" value="${upo.id}" />
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="1" id="lie_table">
					<tr style="display: none">
						<td width="15%">
							有&nbsp;&nbsp;&nbsp;&nbsp;效
						</td>
						<td>

							<input type="checkbox" name="active" value="1"
								<c:if test="${upo.active=='1'}">checked</c:if> />
						</td>
					</tr>
					<tr  style="display: none">
						<td width="15%">
							名&nbsp;&nbsp;&nbsp;&nbsp;称
						</td>
						<td>
							<input type="text" name="naturalname" value="${upo.naturalname}"
								class="textinput_td" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							中文名称
						</td>
						<td>
							<input type="text" name="name" value="${upo.name}"
								class="textinput_td" />
						</td>
					</tr>
					<tr style="display: none">
						<td width="15%" >
							<a href="javascript:searchtree();"><font color='blue'>选择分类</font>
							</a>
						</td>
						<td width='300'>
							<input type="text" name="objecttype" value="${upo.objecttype}"
								class="textinput_td" readonly="readonly">
						</td>
					</tr>
					<tr style="display: none">
						<td width="15%">
							引用
						</td>
						<td>
							<input type="text" name="actionurl" value="${upo.actionurl}"
								class="textinput_td" />
						</td>
					</tr>
					<tr style="display: none">
						<td width="0%">
							
							
						</td>
						<td>
							<input type="checkbox" name="needSerilaizer" value="1" />
							持久化
						</td>
					</tr>
					<tr>
						<td width="15%">
							关联资源
						</td>
						<td>
						<input type="text" name="description" value="${upo.description}">
						</td>
					</tr>
				</table>
				请手动单击<img src="save.jpg">来保存所编辑的内容。<br>
				<table width="100%">
				<div align="center">
					      	  <FCK:editor id="extendattribute" basePath="/fck/FCKeditor/"
					              width="100%"
					              height="550"              
					              skinPath="/fck/FCKeditor/editor/skins/silver/"
					              defaultLanguage="zh-cn"
					              tabSpaces="8"
					              toolbarSet="Default"
								  imageBrowserURL="/fck/FCKeditor/editor/filemanager/browser/default/browser.html?Type=Image&Connector=connectors/jsp/connector"
					              linkBrowserURL="/fck/FCKeditor/editor/filemanager/browser/default/browser.html?Connector=connectors/jsp/connector"
					              flashBrowserURL="/fck/FCKeditor/editor/filemanager/browser/default/browser.html?Type=Flash&Connector=connectors/jsp/connector"
					              imageUploadURL="/fck/FCKeditor/editor/filemanager/upload/simpleuploader?Type=Image"
					              linkUploadURL="/fck/FCKeditor/editor/filemanager/upload/simpleuploader?Type=File"
					              flashUploadURL="/fck/FCKeditor/editor/filemanager/upload/simpleuploader?Type=Flash">
					               ${upo.extendattribute}
					      </FCK:editor>	
					    </div>
				</table>
				<br>
				<div style="display:none">
					<input type="submit" name="btnnew" value="修 改" class="butt">
					<input type="button" name="btncancel" value="取 消"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
	</body>
</html>
