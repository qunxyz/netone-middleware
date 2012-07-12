<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%@ include file="../common/metaJQueryTable.jsp"%>
<jsp:include page="../common/metaExt.jsp"></jsp:include>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript" src="<%=basePath%>/script/window-op.js"></script>
<script type="text/javascript">
var oTable = null;
$(document).ready(function(){
    oTable = $('#workList').dataTable({
    				'iDisplayLength' : 20,
    				'_iDisplayStart' : 0,
    				'iDisplayEnd' : 5,
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
				
	var iHtml = "${requestScope.radioChoose}";
	
	//var pHtml = '<font style=\"font-size: 14px; margin-left: 15px;\">显示</font><select name=\"wlDisplayLength\" id=\"wlDisplayLength\">';
	//	pHtml += '<option value=\"8\" selected=\"selected\">8</option>';
	//	pHtml += '<option value=\"15\">15</option>';
	//	pHtml += '<option value=\"20\">20</option>';
	//	pHtml += '<option value=\"50\">50</option>';
	//	pHtml += '<option value=\"100\">100</option>';
	//	pHtml += '</select><font style=\"font-size: 14px;\">条记录</font>'
	$('div.dataTables_length').html('\t\t&nbsp;&nbsp;&nbsp;'+iHtml);

	oTable.fnFilter( this.value , 0 , false); // 正则
    $("input[name='fieldChoose']").click(function() {
        var chooseValue = $("input[name='fieldChoose'][checked]").val();
    	oTable.fnFilter( "" , chooseValue , false); // 正则
    });
    
    $('#wlDisplayLength').change(function() {
    	oSettings = oTable.fnSettings();
    	oSettings._iDisplayLength = parseInt(this.value);
        oTable.fnDraw();
    });
    
    $("#workList").scrollable({tableHeight:460});
    
});
// bussid 业务ID
function $delnode(bussid){//删除节点
	var anSelected = fnGetSelected(oTable,bussid);
	oTable.fnDeleteRow( anSelected[0] );
	//$('#'+bussid).remove();
}

/* Get the rows which are currently selected */
function fnGetSelected(oTableLocal,bussid)
{
	var aReturn = new Array();
	var aTrs = oTableLocal.fnGetNodes();
	for ( var i=0 ; i<aTrs.length ; i++ )
	{
		var id = $(aTrs[i]).attr('id');
		if ( id == bussid )
		{
			aReturn.push( aTrs[i] );
		}
	}
	return aReturn;
}

function cuiban(){
  window.open ('<%=basePath%>/workflow/cuiban.jsp', 'cuiban', 'height=200, width=400, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
}

</script>
<c:if test="${reloadworklist>0}">
	<script>setTimeout("location.reload();",${reloadworklist})</script>
</c:if>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>workList</title>
	</head>
	<body>
		<input type="hidden" id="openerWinIdx" name="openerWinIdx"
			value="worklist">
		<c:choose>
			<c:when test="${fn:length(workLists)==0}">
				当前无待办任务
			</c:when>
			<c:otherwise>
				<table id="workList" width="100%" class="display">

					<thead>
						<tr class="titleList">
							${titleExt}
							<th nowrap>
								流程环节
							</th>
							<th style="display: none;" nowrap>
								任务名
							</th>
							<th nowrap>
								提交者
							</th>
							<th nowrap>
								留言时间
							</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${workLists }" var="workList">
							<tr id="${workList.bussid}">
								${workList.extinfo}
								<td>
									<a
										href="<%=basePath%>${workList.bussurl }${workList.bussid }&workcode=${workList.workcode}&operatemode=${workList.operatemode}<c:if test="${mode=='1' || mode=='2'}">&query=look</c:if><c:if test="${cuiban=='1'}">&cuibang=true</c:if>"
										target="_blank" class="none">${workList.act.name}</a>

									<br>
								</td>
								<td style="display: none;">

								</td>
								<td>
									${workList.participant}
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
	</body>
</html>