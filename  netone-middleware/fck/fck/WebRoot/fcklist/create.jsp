<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/FCKeditor.tld" prefix="FCK" %> 
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %> 

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
		<script type="text/javascript">
		
		//打开树选择页面
		function searchtree() {
			window.open("SelectSvl?appname=BUSSENV&pagename="+document.all.pagename.value,'_blank', 'height=800, width=1000, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
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
				alert("${errorinfo}");
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
					<tr style="display: none">
						<td width="15%">
							<a href="javascript:searchtree();"><font color='blue'>选择分类</font>
							</a>
						</td>
						<td width='300'>
							<input type="text" name="objecttype" value=""
								class="textinput_td" readonly="readonly">
						</td>
					</tr>
					<tr style="display: none">
						<td width="15%">
							引用
						</td>
						<td>
							<input type="text" name="actionurl" value="" class="textinput_td" />
						</td>
					</tr>
					<tr>
						<td width="15%">
							扩展属性
						</td>
						<td>
							
							<br>
							<input type="checkbox" name="needSerilaizer" value="1" />持久化
							<div align="center">
					      	  <FCK:editor id="extendattribute" basePath="/fck/FCKeditor/"
					              width="100%"
					              height="400"              
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
					      </FCK:editor>	
					    </div>
						</td>
					</tr>
					<tr>
						<td width="15%">
							关联资源
						</td>
						<td>
						<input type="text" name="description">
						</td>
					</tr>
				</table>
				<br>
				<div style="display:none">
					<input type="button" name="btnnew" value="新 建" onClick="create();"
						class="butt">
					<input type="button" name="btncancel" value="取 消"
						onclick="javascript:window.close();" class="butt">
				</div>
			</form>
		</div>
		
		
	</body>
</html>
