<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%@ include file="../common/metaJQueryTable.jsp"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript" src="<%=basePath%>/script/window-op.js"></script>
<script type="text/javascript">
$(document).ready(function(){
    var oTable = $('#workList').dataTable({
    				'iDisplayLength' : 10,
    				'_iDisplayStart' : 0,
    				'iDisplayEnd' : 10,
    				"aaSorting": [[0,'asc'], [2,'asc']],
					'oLanguage': {
							"sProcessing": "加载中...",
							/*"sLengthMenu": ' 显示_MENU_条记录',*/
							"sZeroRecords": "没有找到记录!",
							"sInfo": "从_START_至_END_条记录 共_TOTAL_条记录",
							"sInfoEmpty": "从0条至0条记录 共0条记录",
							"sInfoFiltered": "(从_MAX_条记录过滤)",
							"sInfoPostFix": "",
							"sSearch": "查找:",
							"sUrl": "",
							"oPaginate": {
								"sFirst":    "首页",
								"sPrevious": "上一页",
								"sNext":   "下一页",
								"sLast":   "末页"
							}
					},
					/*'sPaginationType': "full_numbers",*/
					'sDom':'<"dataTables_length">frtip'
				});
				
	var iHtml = '${processselect}'
	
	var pHtml = '<font style=\"font-size: 14px;\">显示</font><select name=\"wlDisplayLength\" id=\"wlDisplayLength\">';

		pHtml += '<option value=\"10\" selected=\"selected\">10</option>';
		pHtml += '<option value=\"20\">20</option>';
		pHtml += '<option value=\"50\">50</option>';
		pHtml += '<option value=\"100\">100</option>';
		pHtml += '</select><font style=\"font-size: 14px;\">条记录</font>'
	
	$('div.dataTables_length').html(iHtml+'\t\t&nbsp;&nbsp;&nbsp;'+pHtml);
	
    $('#filterOption').change(function() {
        oTable.fnFilter( this.value , 0 , false); // 正则
    });
    
    $('#wlDisplayLength').change(function() {
    	oSettings = oTable.fnSettings();
    	oSettings._iDisplayLength = parseInt(this.value);
        oTable.fnDraw();
    });
    
    $("#workList").scrollable({tableHeight:254});
    
});


</script>
<c:if test="${param.reloadtime>0}">
	<script>setTimeout("location.reload();",${reloadtime*60000})</script>
</c:if>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>workList</title>
		<jsp:include page="../common/metaExt.jsp"></jsp:include>

	</head>
	<body>


		<c:choose>
			<c:when
				test="${fn:length(workLists)==0}">
				当前无待办任务
			</c:when>
			<c:otherwise>
				<table id="workList" width="100%" class="display">

					<thead>
						<tr class="titleList">
							<th>
								任务名
							<br></th>
							<th>
								留言人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<br></th>
							<th>
								留言时间
							<br></th>
						</tr>
					</thead>
					<tbody>   

						<c:forEach items="${workLists }" var="workList">
							<tr>
								<td> <a href="<portal:envget envkey='WEBSER_WFWEB'/>/listRuntimeprocess.do?runtimeid=${workList.runtimeid}" target="_blank" class="none"> 流程:${workList.processName}</a>/
									&nbsp;<a
										href="<portal:envget envkey='WEBSER_WFWEB'/>/useAccess.do?runtimeid=${workList.runtimeid}&activityid=${workList.activeid}"
										target="_blankX" class="none">节点:${workList.activeName}</a>/${workList.busstype}&nbsp;${workList.busstip}
								<br></td>
								<td>
									${workList.customer}
								</td>
								<td>
									${fn:substring(workList.starttime,0,19) }
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
		<hr class="hrList">
	</body>
</html>
