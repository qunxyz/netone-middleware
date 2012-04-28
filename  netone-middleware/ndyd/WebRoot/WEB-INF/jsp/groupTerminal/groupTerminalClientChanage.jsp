<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-ui-tab.jsp" />
		<script language="javascript" type="text/javascript" src="<%=path%>/script/jquery-plugin/easyui/easyloader.js" ></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script>
			$(function() {
				$( "#tabs" ).tabs();
			});
			
			function newEnd(arr){
				document.getElementById('runtimeid').value=parent.document.getElementById('runtimeid').value;
				document.getElementById('usercode').value=arr[1];
				document.getElementById('username').value=arr[2];
				
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        msg: '正在执行操作请稍后......'
			    });
				Ext.Ajax.request({
				        url: "<c:url value='/groupTerminal/groupTerminal.do?method=onNewEnd' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        form:'form1',
				        success: function (response, options) {
				            msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				            if (result.error != null) {
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	parent.parent.window.close();
				                Ext.ux.Toast.msg("", result.tip);
				            }
				        },
				        failure: function (response, options) {
				            msgTip.hide();
				            checkAjaxStatus(response);
				            var result = Ext.util.JSON.decode(response.responseText);
				            Ext.MessageBox.alert('提示', result.tip);
				        }
				    });
			}
			
		</script>
		
		<title>集团终端</title>
	</head>
	<body>
	<form id="form1" >
		<input type="hidden" id="usercode" name="usercode" />
		<input type="hidden" id="username" name="username" />
		<input type="hidden" id="runtimeid" name="runtimeid" />
	</form>
		<div>

			<div>
			<table width="100%">
				<tr>
					<td width="28px">
						<div id="top_nd-title">
						</div>
					</td>
					<td  id="title_nd">
						提交<span id="steptext"><img src="<%=path%>/script/theme/main/blue/images/first_top.gif"/>步骤:&nbsp;第&nbsp;<span id="stepNum">2</span>&nbsp;步,共 2 步。</span>
					</td>
				</tr>
			</table>
			<hr id="hr_nd">
			<div id="helpInfo_nd">
				帮助提示: 这里主要是选择审批人员并提交表单,确认无误后请点击完成,如需修改点击上一步,否取消。
			</div>
		</div>
		<div id="tabs" style="height: 100%;text-align: center;">
		<input type="hidden" id="groupTerminalId" name="groupTerminalId" value="${param.groupTerminalId}">
		<input type="hidden" id="node" name="node" value="${param.node}">
			<iframe id="clientMainFrame" name="clientMainFrame"
				src="<c:url value='/department/user.do?method=onMultiSelectUserX&hiddendept=1&includedept=1&node=${param.node}' />"
				scrolling="auto" frameborder="0">
			</iframe>
		</div>
	</body>
</html>
<script>
	function up_Next_1(){
		parent.$disabledall();
		var groupTerminalId = document.getElementById('groupTerminalId').value;
		var url = "<%=path%>/groupTerminal/groupTerminal.do?method=onEditView&groupTerminalId="+groupTerminalId;
		parent.document.getElementById('flowMainFrame').src=url;
		parent.$hideall();
		parent.$show('new_1');
	}
</script>