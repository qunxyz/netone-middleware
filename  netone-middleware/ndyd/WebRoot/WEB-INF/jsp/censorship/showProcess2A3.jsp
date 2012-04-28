<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="../common/metaExt.jsp"></jsp:include>
	<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
	<jsp:include page="../common/metaJQuery-ui-tab.jsp" />
	</script>

	<script type="text/javascript">
	
		function up_Next_1(){
			parent.$disabledall();
			var unid = document.getElementById('unid').value;
			var url = "<%=path%>/censorship/censorship.do?method=onEditX&unid="+unid;
			parent.document.getElementById('flowMainFrame').src=url;
			parent.$hideall();
			parent.$show('new_1');
		}
	
		function init(){
		
		}
		
		function up_5(){
			var unid = document.getElementById('unid').value;
			var chooseresult = document.getElementById('chooseresult').value;
			var url ="<%=path%>/censorship/censorship.do?method=onAuditView&chooseresult="+ chooseresult+"&unid="+unid;
			parent.document.getElementById('flowMainFrame').src=url;
			parent.$hideall();
			parent.$show('audit_2');
			parent.$disabledall();
		}
		
		function end(){
			<c:choose>
				<c:when test="${param.chooseresult==0}">
					auditEnd0();
				</c:when>
				<c:when test="${param.chooseresult==1}">
					auditEnd1();
				</c:when>
				<c:when test="${param.chooseresult==2}">
					auditEnd2();
				</c:when>
				<c:when test="${param.chooseresult==3}">
					auditEnd3();
				</c:when>
				<c:otherwise>
					auditEnd0();
				</c:otherwise>
			</c:choose>
		}
		
		function auditEnd0(){//
				var userid = document.getElementById('usercode').value;
				if (userid==''){
					return '未选择,请选择人员或部门提交!';
				}
				
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        msg: '正在执行操作请稍后......'
			    });
				Ext.Ajax.request({
				        url: "<c:url value='/censorship/censorship.do?method=onAuditNext' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        params: {
			            	perunid: document.getElementById('usercode').value,
			            	tname : document.getElementById('username').value,
			            	unid : document.getElementById('unid').value,
			            	parentunid: document.getElementById('unid').value
			        	},
				        success: function (response, options) {
				            msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				            if (result.error != null) {
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	parent.$disabledall();
				            	parent.parent.window.opener=null;
				            	parent.parent.window.close();
				            	//parent.window.close();
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
		
		function auditEnd1(){//交办
				var userid = document.getElementById('usercode').value;
				if (userid==''){
					return '未选择,请选择人员或部门提交!';
				}
				
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        msg: '正在执行操作请稍后......'
			    });
				Ext.Ajax.request({
				        url: "<c:url value='/censorship/censorship.do?method=onAssign' />",
				        // 请求的服务器地址
				        // 指定要提交的表单id
				        method: 'POST',
				        form:'form1',
				        params: {
			            	perunid: document.getElementById('usercode').value,
			            	tname : document.getElementById('username').value,
			            	unid : document.getElementById('unid').value,
			            	parentunid: document.getElementById('unid').value
			        	},
				        success: function (response, options) {
				            msgTip.hide();
				            var result = Ext.util.JSON.decode(response.responseText);
				            if (result.error != null) {
				                Ext.MessageBox.alert('提示', result.tip);
				            } else {
				            	parent.$disabledall();
				            	parent.parent.window.opener=null;
				            	parent.parent.window.close();
				            	//parent.window.close();
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
			
			function auditEnd2(){//办理完毕
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        msg: '正在执行操作请稍后......'
			    });
				Ext.Ajax.request({
				        url: "<c:url value='/censorship/censorship.do?method=onAudit' />",
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
				            	parent.$disabledall();
				            	parent.parent.window.opener=null;
				            	parent.parent.window.close();
				            	//parent.window.close();
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
			
			function auditEnd3(){//归档
				var msgTip = Ext.MessageBox.show({
			        title: '提示',
			        width: 250,
			        msg: '正在执行操作请稍后......'
			    });
				Ext.Ajax.request({
				        url: "<c:url value='/censorship/censorship.do?method=onPack' />",
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
				            	parent.$disabledall();
				            	parent.parent.window.opener=null;
				            	parent.parent.window.close();
				            	//parent.window.close();
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


  </head>
  
  <body>
  <form id="form1" >
		<input type="hidden" id="userid" name="userid"/>
		<input type="hidden" id="usercode" name="usercode" />
		<input type="hidden" id="username" name="username" />
		
		<input type="hidden" id="perunid" name="perunid" />
		<input type="hidden" id="tname" name="tname" />
		<input type="hidden" id="unid" name="unid" value="${param.unid}" />
		<input type="hidden" id="chooseresult" name="chooseresult" value="${param.chooseresult}" />
		
	</form>
  
    <div id="box">
    	<table width="100%">
				<tr>
					<td width="12px">
						<div id="top_nd-header">
						</div>
					</td>
					<td id="steps_nd">
						<c:choose>
							<c:when test="${param.flowppage==2}">
							步骤:第 2 步,共 2 步。
							</c:when>
							<c:otherwise>步骤:第 3 步,共 3 步。</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
			<div>
				<table width="100%">
					<tr>
						<td width="28px">
							<div id="top_nd-title">
							</div>
						</td>
						<td id="title_nd">
							${processTitle}
							<span id="helpInfo_nd">${helpTip}</span>
						</td>
					</tr>
				</table>
				<hr id="hr_nd">
			</div>
    </div>
  </body>
</html>
