<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:include page="common/taglibs.jsp"></jsp:include>
<jsp:include page="common/metaExt.jsp"></jsp:include>
<jsp:include page="common/metaJQuery-min.jsp"></jsp:include>
<jsp:include page="common/metaJQuery-ui-dialog.jsp"></jsp:include>
<script type="text/javascript" src="<%=path%>/script/ext/extend/pPageSize.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>日志</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css"	href="<%=basePath%>/styles/styles.css" />
		<script language="javascript" type="text/javascript" defer="defer"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script type="text/javascript">
			
		Ext.QuickTips.init();
 		Ext.form.Field.prototype.msgTarget = 'side';
		Ext.ns('Bus.Data');
		Bus.Data.BusGrid = Ext.extend(Ext.grid.GridPanel, {
		
			initComponent : function() {
		
				var index = new Ext.grid.RowNumberer();//
				var cb = new Ext.grid.CheckboxSelectionModel({ // 创建Grid表格组件
					singleSelect : false
				});
		
				var config = {
					//sm : cb,
					frame : false,
					border : false,
					//enableHdMenu : false,
					store : new Ext.data.Store({
								url : "log.do?method=onList", // JSON数据
								reader : new Ext.data.JsonReader({
											totalProperty : 'total',
											root : 'rows'
										}, [
											{name: 'logId'},
											{name: 'userId'},
											{name: 'userName'},
											{name: 'operateTime'},
											{name: 'operateId'},
											{name: 'resultInfo'},
											{name: 'logSeq'},
											{name: 'userIp'},
											{name: 'userHost'},
											{name: 'userAgent'},
											{name: 'remark'}
										])
							}),
					columns : [index,
						//cb, 
						{header: "操作者编号", align:'center',dataIndex: 'userId',css:'font-size:14;'},
						{header: "操作者名称", align:'center',dataIndex: 'userName',css:'font-size:14;'},
						{header: "操作时间", align:'center', dataIndex: 'operateTime',css:'font-size:14;'},
						{header: "操作项", align:'center', dataIndex: 'operateId',css:'font-size:14;'},
						{header: "详细信息", align:'center', dataIndex: 'logSeq',width:500,css:'font-size:14;'},
						{header: "备注", align:'center', dataIndex: 'remark',width:150,css:'font-size:14;'},
						{header: "登录IP", align:'center', dataIndex: 'userIp',css:'font-size:14;',hidden:true},
						{header: "登录Host", align:'center', dataIndex: 'userHost',css:'font-size:14;',hidden:true},
						{header: "登录Agent", align:'center', dataIndex: 'userAgent',css:'font-size:14;',hidden:true}
						/*{header: "操作", align:'center', dataIndex: 'resultInfo',css:'font-size:14;'
								,renderer : function todo(value, cellmeta, record,rowIndex, columnIndex, store) {
									var resultInfo = record.get('resultInfo');
									return '<font color=\"green\" >'+resultInfo+'</font>';
								}
						}*/
					],
					viewConfig : {
						forceFit : true
					},
					loadMask : true
				}
		
				// apply config
				Ext.apply(this, Ext.apply(this.initialConfig, config));
		
				this.bbar = new Ext.PagingToolbar({
						plugins:new Ext.ux.Andrie.pPageSize({
							variations: [ 50, 100, 200, 500],
							beforeText : '每页显示:',
							afterText :'条'
						}),
						store : this.store,
						displayInfo : true,
						pageSize : 50,
						displayMsg : '第 {0} 条到 {1} 条，一共 {2} 条',
						emptyMsg : "没有记录"
					});
		
				// call parent
				Bus.Data.BusGrid.superclass.initComponent.apply(this, arguments);
		
				this.store.on('beforeload', function(store, options) {
							var condition = {// 取得HTML页面的查询条件
									beginDate : document.getElementById('beginDate').value,
									endDate : document.getElementById('endDate').value,
									userId : document.getElementById('userId').value,
									operateId : document.getElementById('operateId').value,
									operateIdDetail : document.getElementById('operateIdDetail').value,
									logseq : document.getElementById('logseq').value,
									remark : document.getElementById('remark').value
							};
		
							options = options || {};
							options.params = options.params || {};
							options.params.condition = Ext.util.JSON.encode(condition);
							options.params.mode = '${param.mode}';
							return true;
						});
				// load the store at the latest possible moment
				this.on({
							beforeshow : {
								scope : this,
								single : true,
								fn : function() {
									this.store.load({
												params : {
													start : 0,
													limit : 50
												}
											});
								}
							}
						});
			}
		});
		Ext.reg('BusGrid', Bus.Data.BusGrid);// 注册一个组件,注册成xtype以便能够延迟加载
		
		Ext.ns('Bus.Layout');
		
		Bus.Layout.Viewport = Ext.extend(Ext.Viewport, {
		
			initComponent : function() {
				var clientHeight = 0;
				if (document.documentElement
						&& (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
					clientHeight = document.documentElement.clientHeight;
				} else if (document.body
						&& (document.body.clientWidth || document.body.clientHeight)) {
					clientHeight = document.body.clientHeight;
				}
		
				var config = {
					collapsible : true,
					layout:"border", 
					autoWidth : true,
					border : false,
					items : [{
								xtype : 'toolbar',
								region:"north",
								items : [
							  		 {text : '查询',iconCls:'viewIcon',id:'ext_b_search',handler:query},
							  		 {text : '查看明细',iconCls:'viewIcon',id:'ext_b_view',handler:view}
								]
							}, {
								id : "BusGrid",
								xtype : "BusGrid",
								region:"center",
								border : false,
								hideBorders : true,
								height : clientHeight - 20,
								autoScroll : true
							}]
				}
		
				Ext.apply(this, Ext.apply(this.initialConfig, config));
				Bus.Layout.Viewport.superclass.initComponent.apply(this, arguments);
			}
		});
		
		function view(){
			var recs = Ext.getCmp('BusGrid').getSelectionModel().getSelections();
			if (recs.length <= 0 || recs.length > 1) {
				Ext.MessageBox.alert('系统提示', '请选择一条记录!');
				return;
			} else {
				var rowIndex = Ext.getCmp('BusGrid').store.indexOf(recs[0]);
				queryDetail(rowIndex);
			}
		}
		
		Ext.onReady(function() {// 页面加载时候触发事件
					var viewport = new Bus.Layout.Viewport();
					var busGrid = viewport.findById('BusGrid');// Grid
		
					//refresh();
					
					Ext.getCmp('BusGrid').addListener('celldblclick', function(grid, rowIndex,
					columnIndex, e) {// 列表双击事件--查看明细
						view();
					});
					
					//query();
		
		});// End OnReady
		
		refresh = function() {// 刷新
			var pageSize = Ext.getCmp('_pPageSize_combo').getValue();
			Ext.getCmp('BusGrid').store.load({
						params : {
							start : 0,
							limit : pageSize
						}
					});
		};
		
		function query(){
				$("#_openDivx #_innerHtmlx").css("display","block"); 
				/**
				$('#_openDivx').dialog({
		     			title:'日志查询',
		     			resizable:true,
		     			draggable:true,
		     			maximizable:false,
		     			minimizable:false,
		     			width:400,
		     			height:250,
		     			shadow:true,
		     			open: function(event, ui) {
		     				$("#_openDivx").css("display","block"); 
						},
						close: function(event, ui) {
							$('#_openDivx').dialog("destroy");
						}
		     			
			   });
			   $("#_openDivx").dialog('open').show(); 
			   **/
			   var w=new Ext.Window({
		           contentEl:"_openDivx",//主体显示的html元素，也可以写为el:"win"
		           width:400,
		     	   height:250,
		     	   resizable:false,
		           title:"日志查询",
		           modal: true,
		           closeAction :'hide'
		        });
		       w.show();
		}
		
		function loadHtml(oid,rowIndex,total){
				if (rowIndex < 0){
					alert('已到第一条记录');
					return;
				} else if ( rowIndex+1 > 50 ) {
					alert('已到最后一条记录');
					return;
				}
				var store = Ext.getCmp('BusGrid').store;
				var record = store.getAt(rowIndex);
				if (record == null){
					alert('已到最后一条记录');
					return;
				}
				var id = record.get('logId');
				var userId = record.get('userId');
				var userName = record.get('userName');
				var operateTime = record.get('operateTime');
				var operateId = record.get('operateId');
				var resultInfo = record.get('resultInfo');
				var logSeq = record.get('logSeq');
				var userIp = record.get('userIp');
				var userHost = record.get('userHost');
				var userAgent = record.get('userAgent');
				var remark = record.get('remark');
				var total = store.getTotalCount();
				
				var prevIndex = rowIndex-1;
				var nextIndex = rowIndex+1;
				
				var html = '<div><div align=\"left\"><input type=\"button\" value=\"上一条\" onclick=\"prevFun('+(prevIndex)+','+total+');\" />'+
						   '<input type=\"button\" value=\"下一条\" onclick=\"nextFun('+(nextIndex)+','+total+');\" /></div>'+
				 		   '<table height=\"400px\">'+
						   '<tr><td nowrap=\"nowrap\" width=\"150px\" valign=\"top\">日志流水号:</td><td>'+id+'</td></tr>'+
						   '<tr><td nowrap=\"nowrap\" width=\"150px\" valign="\top\">操作者编号:</td><td>'+userId+'</td></tr>'+
						   '<tr><td nowrap=\"nowrap\" width=\"150px\" valign=\"top\">操作者名称:</td><td>'+userName+'</td></tr>'+
						   '<tr><td nowrap=\"nowrap\" width=\"150px\" valign=\"top\">操作时间:</td><td>'+operateTime+'</td></tr>'+
						   '<tr><td nowrap=\"nowrap\" width=\"150px\" valign=\"top\">操作项:</td><td>'+operateId+'</td></tr>'+
						   '<tr><td nowrap=\"nowrap\" width=\"150px\" valign=\"top\">操作结果:</td><td>'+resultInfo+'</td></tr>'+
						   '<tr><td nowrap=\"nowrap\" width=\"150px\" valign=\"top\">登录IP:</td><td>'+userIp+'</td></tr>'+
						   '<tr><td nowrap=\"nowrap\" width=\"150px\" valign=\"top\">登录Agent:</td><td><div style=\"overflow:auto;width: 550px;\">'+userAgent+'</div></td></tr>'+
						   '<tr><td nowrap=\"nowrap\" width=\"150px\" valign=\"top\">登录Host:</td><td>'+userHost+'</td></tr>'+
						   '<tr><td nowrap=\"nowrap\" width=\"150px\" valign=\"top\">详细信息:</td><td><div style=\"overflow:no;width: 550px;\">'+logSeq+'</div></td></tr>'+
						   '<tr><td nowrap=\"nowrap\" width=\"150px\" valign=\"top\">备注:</td><td><div style=\"overflow:no;width: 600px;\">'+remark+'</div></td></tr>'+
						   '</table></div>';
						   
				$(oid).html(html);
		}
		
		function queryDetail(rowIndex){
				var recs = Ext.getCmp('BusGrid').getSelectionModel().getSelections();
				if (recs.length <= 0 || recs.length > 1) {
					Ext.MessageBox.alert('系统提示', '请选择一条记录!');
					return;
				} 
				var store = Ext.getCmp('BusGrid').store;
				var total = store.getTotalCount();
				var index = rowIndex;
				
			   var w=new Ext.Window({
		           contentEl:"dd",//主体显示的html元素，也可以写为el:"win"
		           width:800,
		           height:400,
		           title:"明细",
		           autoScroll:true,
		           modal: true,
		           maximizable: true,
		           closeAction :'hide'
		        });
		       w.show();
			   loadHtml('#dd',rowIndex,total);
		}
		
		function commonEvent(){//触发事件---按钮触发事件
			Ext.getCmp('BusGrid').store.removeAll();
	    	refresh();
	    	Ext.WindowMgr.each(function(){
				this.hide();
			});
  		}
		function reset(){
			document.getElementById('userId').value='';
			document.getElementById('operateId').value='';
			document.getElementById('operateIdDetail').value='';
		}
		
		function prevFun(rowIndex,total){
			loadHtml('#dd',rowIndex,total);
		}
		function nextFun(rowIndex,total){
			loadHtml('#dd',rowIndex,total);
		}
		function closeFun(){
			$('#dd').dialog('close');
		}
		</script>
		</head>
	<body>
		<div id="_openDivx">
			<div id="_innerHtmlx" style="display: none;">
				<table border="0" cellPadding="0" borderColor="#9fd6ff"
							style="border-collapse: collapse; width: 100%;">
					<tr>
						 <td><font color="red">时间点:&nbsp;</font></td>
						 <td>
						 <input type="text" name="beginDate" id="beginDate"
											value="${beginDate}" class="Wdate" style="width:120px"
											onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
									--
						  <input type="text" name="endDate" id="endDate" value="${endDate}"
											style="width: 120px" class="Wdate"
											onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
						 </td> 
		           </tr>		
					<tr>
						<td>客户编号:&nbsp;</td>
						<td>
							<input type="text" id="userId" name="userId" style="width: 200px;" />
						</td>
					</tr>
					<tr>
						<td>操作项:&nbsp;</td>
						<td>
						<select id="operateId" name="operateId" style="width: 200px;">
							<option value="">--所有--</option>
						</select>
						</td>
					</tr>
					<tr>
						<td>操作项明细:&nbsp;</td>
						<td>
						<select id="operateIdDetail" name="operateIdDetail" style="width: 200px;">
							<option value="">--所有--</option>
						</select>
						</td>
					</tr>
					<tr>
						<td>操作结果:&nbsp;</td>
						<td>
						<input type="text" id="logseq" name="logseq"   style="width: 200px;"/>
						</td>
					</tr>	
					<tr>
						<td>备注:&nbsp;</td>
						<td>
						<input type="text" id="remark" name="remark"  style="width: 200px;" />
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2">
							<div align="center">
					        <input id="queryBtn" type="button" value=" 查 询 " class="btn"
														onclick="commonEvent();"
														onmouseover="this.className='btn_mouseover'"
														onmouseout="this.className='btn'"
														onmousedown="this.className='btn_mousedown'"
														onmouseup="this.className='btn'" />
														
							<input id="queryBtn" type="button" value=" 重 置 " class="btn"
														onclick="reset()"
														onmouseover="this.className='btn_mouseover'"
														onmouseout="this.className='btn'"
														onmousedown="this.className='btn_mousedown'"
														onmouseup="this.className='btn'" />							
					        </div>
						</td>
					</tr>
				</table>	
			</div>
		</div>
		<div id="dd" style="width:400px;height:200px;" >
		</div>
	</body>
</html>
