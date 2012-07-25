<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>个人消息管理</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link href="/cmsWeb/AutoStyleSvl?name=${param.pagestyle}"
			rel="stylesheet" type="text/css">
		<script type="text/javascript" src="rsinclude/page.js"></script>
		<script type="text/javascript">	
			function look(workid){
				window.open("bussMessage.do?task=look&workid="+workid);
			}
			function reply(workid){
				window.open("bussMessage.do?task=reply&workid="+workid);
			}
			function returnsend(workid){
				window.open("bussMessage.do?task=returnsend&workid="+workid);
			}
			function del(workid,extattr1){
			    if(confirm("确认删除"+extattr1+"这条消息吗？")){
			        window.open("bussMessage.do?task=del&workid="+workid,target="_self");
			    }
			}
			function multidel(){
	    	var k = 0;
		    for(var i=0 ; i<form1.elements.length ; i++) {
			    if (form1.elements[i].name=="multiworkid") {
				    if(form1.elements[i].checked==true){
					    k = 1;
					    break;
				    }
			    }
		    }
		    if(k==0){
			    alert("请先选中需要删除的选项");
			    return;
		    } else {
			    if(confirm("您确认执行删除操作么？")){
				    document.form1.multideltask.value="multidel";
			        document.form1.submit();
			    }
			}
		}
		//多选择 需要根据应用的需要来订制
		function allcheck(){
			if(document.all.checkall.checked){
				var checkids = document.getElementsByTagName("input");
				for(var i=0 ; i<checkids.length ; i++){
					if(checkids[i].type=="checkbox"){
						if(checkids[i].checked){
							
						}else{
							checkids[i].checked=true;
						}
					}
				}
			}
			if(document.all.checkall.checked==false){
				var checkids = document.getElementsByTagName("input");
				for(var i=0 ; i<checkids.length ; i++){
					if(checkids[i].type=="checkbox"){
						if(checkids[i].checked){
							checkids[i].checked=false;
						}
					}
				}
			}
		}
		</script>
	</head>

	<body style="font-size: 12px">
		${paramMap.alertMsg }
		<form action="bussform/bussMessage.do" method="post" name="form1">
			<table width="96%"  cellspacing="0" border="1" cellpadding="2" bordercolordark="#FFFFFF" bordercolorlight="#999999"> 
				<input type="hidden" name="multideltask" value="">
				<tr class='tdheadline'>
					<td nowrap>
						<input type="checkbox" name="checkall" onclick="allcheck();">
					</td>
					<td nowrap>
						标题
					</td>

					<td nowrap>
						创建时间
					</td>
					<td nowrap>
						发送者
					</td>
					<td  nowrap>
						操作
					</td>
				</tr>
				<c:forEach items="${formlist}" var="form">
					<tr>
						<td nowrap>
							<input type="checkbox" name="multiworkid" value="${form.workid }">
						</td>
						<td nowrap>
							${form.extattr1}&nbsp;
						</td>
						<td nowrap>
							<fmt:formatDate value="${form.created}"
								pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;
						</td>
						<td nowrap>
							${form.sender}&nbsp;
						</td>
						<td>
							<a href="javascript:look('${form.workid}');">查看</a>
							<a href="javascript:reply('${form.workid}');">回复</a>
							<a href="javascript:returnsend('${form.workid}');">转发</a>
							<a href="javascript:del('${form.workid}','${form.extattr1 }');">删除</a>
						</td>
					</tr>
				</c:forEach>
			</table>


			<table width="100%"  cellspacing="0" border="0" cellpadding="0" bordercolordark="#999999" bordercolorlight="#FFFFFF">
			<tr><td><input type="button" value="删 除" class="butt" onclick="multidel()"></td></tr>
			<tr><td nowrap>		
			  <script type="text/javascript">
				var pginfo = new PageInfo("${page_pginfo.pginfostr}",document.all.form1);
				pginfo.write();
			</script></td></tr>
			</table>
		</form>

	</body>
</html>
