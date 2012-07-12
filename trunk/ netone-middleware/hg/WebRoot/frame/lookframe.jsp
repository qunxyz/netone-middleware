<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-tab.jsp" />
		<script language="javascript" type="text/javascript" src="<%=path%>/script/jquery-plugin/easyui/easyloader.js" ></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script>
			$(function() {
				$("#tabs").tabs();
				$('#tabs').tabs('select', "tabs-3");
			});
			
		</script>
		
		<title><c:choose><c:when test="${!empty htmltitleinfo}">${htmltitleinfo} - ${activityName}</c:when><c:otherwise>${activityName}</c:otherwise></c:choose></title>
	</head>
	<body>
		<div style="margin: 8px;">
			<table width="100%">
				<tr>
					<td width="28px">
						<div id="top_nd-title">
						</div>
					</td>
					<td  id="title_nd">
						查看
					</td>
				</tr>
			</table>
			<hr id="hr_nd">
		 </div>
		<div id="tabs" style="height: 100%;">
			<ul>
				<li>
					<a href="#tabs-1" class="">流程跟踪</a>
				</li>
				<li>
					<a href="#tabs-2">审批意见</a>
				</li>
				<li>
					<a href="#tabs-3" class="ui-tabs-selected">流程办理</a>
				</li>
			</ul>
			<div id="tabs-1">
				<table class="main_nd" width="100%">
					<tr>
						<td nowrap="nowrap" class="label_nd_1" width="20%">
							日期
						</td>
						<td nowrap="nowrap" class="label_nd_1" width="20%">
							执行人
						</td>
						<td nowrap="nowrap" class="label_nd_1" width="40%">
							执行行动
						</td>
						<td nowrap="nowrap" class="label_nd_1" width="20%">
							目标
						</td>
					</tr>
					<tr>
						<td id="active" colspan="4">无</td>
					</tr>
				</table>
				<table class="main_nd" width="100%">
					<tr>
						<td nowrap="nowrap" class="label_nd_1" width="100%">
							当前文档流程状态图
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="label_nd_2" width="100%" height="300px">
							<c:choose>
								<c:when test="${runtimeid!='' && !empty runtimeid}">
									<iframe name="myframe" id="myframe"
										src="<portal:envget envkey='WEBSER_WFWEB' />/listRuntimeprocess.do?runtimeid=${runtimeid}"
										width="100%" height="450px" frameborder="0" scrolling="auto"></iframe>
								</c:when>
								<c:otherwise>
									<iframe name="myframe" id="myframe"
											src="<portal:envget envkey='WEBSER_WFWEB' />/viewreadonlyprocess.do?processid=${processid}"
											width="100%" height="450px" frameborder="0" scrolling="auto"></iframe>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
			</div>
			<div id="tabs-2" style="height: 100%">
				<table class="main_nd" width="100%">
					<tr>
						<td nowrap="nowrap" class="label_nd_1" width="100%">
							审批意见
						</td>
					</tr>
					<tr>
						<td class='label_nd_2' align="center" id="mytip">
							无
						</td>
					</tr>
				</table>
			</div>

			<div id="tabs-3">
			<form id="groupTerminalFrom" action="" method="post">
				<div id="panel" style='width: 940px; height: 100%;'>
					<div id="div1"
						style='width: 900px; height: 370px; text-align: left'>
						<!-- 动态表单 start-->
							${form}
							<!-- 动态表单 end-->
					</div>
				</div>
				<iframe id="fileMainFrame" name="fileMainFrame" 
					src="<c:url value='/file.do?method=onMainView&d_unid=${param.lsh}&readonly=${param.readonly}' />"
					scrolling="auto" frameborder="0"
					style="width: 100%;">
				</iframe>
				<table class="main_nd" width="100%">
					<tr>
						<td nowrap="nowrap" class="label_nd_1" width="100%">
							审批意见
						</td>
					</tr>
					<tr>
						<td class='label_nd_2' align="center" id="mytip_">
							无
						</td>
					</tr>
				</table>
				
			</div>
		</div>
		</div>
	</body>
</html>
<script>

function loadInfo()
	{
		Ext.Ajax.request({ 
  			url: "<c:url value='/frame.do?method=bussTipListView&workcode=${param.workcode}&runtimeid=${param.runtimeid}' />", 
  			success: function(response, config){ 
    			//alert(config.url + "," + config.method); 
		    //Ext.MessageBox.alert("result", response.responseText);
		    document.getElementById('mytip').innerHTML = response.responseText;
		    document.getElementById('mytip_').innerHTML = response.responseText;
			    	Ext.Ajax.request({ 
			  			url: "<c:url value='/frame.do?method=queryActive&workcode=${param.workcode}&runtimeid=${param.runtimeid}' />", 
			  			success: function(response, config){ 
			    			//alert(config.url + "," + config.method); 
					    //Ext.MessageBox.alert("result", response.responseText);
					    document.getElementById('active').innerHTML = response.responseText;
						    
			  			}, 
					  failure: function(){ 
					    Ext.MessageBox.alert("result", "请求失败"); 
					  }, 
					  method: "post"
					}); 
  			}, 
		  failure: function(){ 
		    Ext.MessageBox.alert("result", "请求失败"); 
		  }, 
		  method: "post"
		}); 
	}
	
</script>