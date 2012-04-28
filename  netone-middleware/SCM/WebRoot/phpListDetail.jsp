<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/common/metaJQueryTable.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript" src="/script/window-op.js"></script>
<script src="<%=path%>/script/jquery-plugin/json2.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
<!-- 通过样式 按钮 -->
<link rel="stylesheet" type="text/css" href="/script/theme/main/common.css">
<style>
<!--
A{color: #006699; text-decoration: none;font-size:12px;font-weight:normal;} 
A:hover { color: #D95F40; text-decoration: none }
A:active {color: #928BA4; text-decoration: none}
A:visited {color: #928BA4;}
-->
</style>
<script type="text/javascript"> 
		var oTable = null;
		
		$(function() {
			$("#_datatables").hide();
			var $height = '${param.height==null?200:param.height}';
			if ($height==null || $height=='undefined' || $height=='') $height=460;
			
			if (oTable == null) { //仅第一次检索时初始化Datatable
				$("#_datatables").show();
				oTable = $('#_datatables').dataTable( {
    				'_iDisplayStart' : 0,
    				'iDisplayEnd' : 7,
    				//"aaSorting": ${aaSorting},
    				//"sScrollX": "100%",   //表格的宽度   
    				//"sScrollY": $height+"px",
    				
    				"sScrollX": "100%",
					"sScrollXInner": "100%",
					"bScrollCollapse": true,
					
					<c:if test="${aoColumns!=null}">
					//"aoColumns": ${aoColumns},//设定各列宽度
					</c:if>
    				
    				"bStateSave": true, //保存状态到cookie
    				"bJQueryUI": true,
					"bAutoWidth": false,					//不自动计算列宽度
					"bProcessing": true,					//加载数据时显示正在加载信息
					"bServerSide": true,					//指定从服务器端获取数据
					"bFilter": false,						//不使用过滤功能
					"bLengthChange": false,					//用户不可改变每页显示数量
					"iDisplayLength": ${empty param.psize?20:param.psize},					//每页显示20条数据
					"sAjaxSource": "<c:url value='/workList.do?method=phpListDetail'/>",//获取数据的url
					"fnServerData": retrieveData,			//获取数据的处理函数
					"sPaginationType": "full_numbers",		//翻页界面类型
					//"sDom": 'R<"H"lfr>t<"F"ip>',
					//"aLengthMenu": [[10, 25, 50, -1, 0], ["每页10条", "每页25条", "每页50条", "显示所有数据", "不显示数据"]]  //设置每页显示记录的下拉菜单 
					"oLanguage": {							//汉化
						"sLengthMenu": "每页显示 _MENU_ 条记录",
						"sZeroRecords": "没有检索到数据",
						"sInfo": "从第_START_到_END_条数据 共_TOTAL_条记录",
						"sInfoEmpty": "从第0到0条数据 共0条记录",
						"sProcessing": "正在加载数据...",
						"oPaginate": {
							"sFirst": "首页",
							"sPrevious": "前页",
							"sNext": "后页",
							"sLast": "尾页"
						}
					}
				});
				//new FixedColumns(oTable);
				/**
				$(window).bind('resize', function () { 
					oTable.fnAdjustColumnSizing(); 
				});
				**/
			}
			
			//$("#_datatables").scrollable({tableHeight:$height});
			
			if ($('#istime').val()=='true'){
				$changeinput(0);
			}
			
		});

		//“检索”按钮的处理函数
		function search() {
			if ($('#istime').val()=='true'){
				$inputCondition();
			}
			//刷新Datatable，会自动激发retrieveData
			oTable.fnDraw();
		}

		//自定义数据获取函数
		function retrieveData( sSource, aoData, fnCallback ) {
			//aoData.push( { "name": "condition", "value": $("#condition").val() } );
			//aoData.push( { "name": "querycolumn", "value": $("input[type=radio][id=querycolumn][checked]").val() } );
			//aoData.push( { "name": "querycolumnx", "value": $("#querycolumnx").val() } );
			//aoData.push( { "name": "appname", "value": '${param.appname}' } );
			//aoData.push( { "name": "sortfield", "value": '${param.sortfield}' } );
			//aoData.push( { "name": "sort", "value": '${param.sort}' } );
			//aoData.push( { "name": "mode", "value": '${param.mode}' } );
			//aoData.push( { "name": "listtype", "value": '${param.listtype}' } );
			
			
			aoData.push( { "name": "naturalname", "value": '${param.naturalname}' } );
			aoData.push( { "name": "did", "value": '${param.did}' } );
			aoData.push( { "name": "total", "value": '${param.total}' } );
			aoData.push( { "name": "type", "value": '${param.type}' } );
			
			//alert(JSON.stringify(aoData))
			$.post(sSource,{conditions:JSON.stringify(aoData)},function(data){fnCallback(data)});
			/**
			$.ajax( {
				"type": "POST", 
				"contentType": "application/json",
				"url": sSource, 
				"dataType": "json",
				"data": JSON.stringify(aoData), 
				"success": fnCallback
			});
			**/
		}
		
		function exportExcel(){//导出excel
			var aoData = new Array();
			
			//aoData.push( { "name": "condition", "value": $("#condition").val() } );
			//aoData.push( { "name": "querycolumn", "value": $("input[type=radio][id=querycolumn][checked]").val() } );
			//aoData.push( { "name": "querycolumnx", "value": $("#querycolumnx").val() } );
			//aoData.push( { "name": "appname", "value": '${param.appname}' } );
			//aoData.push( { "name": "sortfield", "value": '${param.sortfield}' } );
			//aoData.push( { "name": "sort", "value": '${param.sort}' } );
			//aoData.push( { "name": "mode", "value": '${param.mode}' } );
			//aoData.push( { "name": "listtype", "value": '${param.listtype}' } );
			
			aoData.push( { "name": "iDisplayStart", "value": '0' } );
			aoData.push( { "name": "iDisplayLength", "value": '65536' } );
			
			//alert(JSON.stringify(aoData))
			document.getElementById('conditions').value=JSON.stringify(aoData);
			var sSource = "<c:url value='/workList.do?method=exportWorklist&format=excel' />";
			//$.post(sSource,{conditions:JSON.stringify(aoData)},function(data){});
			document.getElementById('report').action=sSource;
			document.getElementById('report').submit();
		}
		
		function $changeinput(t){
			if (t==1){//非时间
				$('#condition').val('');
				$('#condition').show();
				$('#datecondition').hide();
				$('#istime').val('false');
			} else{//时间
				$('#condition').hide();
				$('#datecondition').show();	
				$('#istime').val('true');
			}
		}
		
		function $inputCondition(){
			var starttime = $('#starttime').val();
			var endtime = $('#endtime').val();
			$('#condition').val(starttime+"_"+endtime);
		}
		
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
		
	</script> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>详细工单信息</title>
	</head>
	<body>	
			<form id="report" target="_blank" method="post">
				<input type="hidden" id="conditions" name="conditions" />
				<input type="hidden" id="format" name="format" value="excel"/>
			</form>
			<input type="hidden" id="openerWinIdx" name="openerWinIdx" value="worklist">

			<table class="display" id="_datatables" width="100%">
				<thead>
					<tr>
						<th>工单主题</th>
						<th>流程名称</th>
						<th>创建时间</th>
						<th>当前节点</th>
						<th>当前处理人</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
	</body>
</html>