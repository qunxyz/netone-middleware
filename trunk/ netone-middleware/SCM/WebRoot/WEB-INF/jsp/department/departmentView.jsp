<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%	String path = request.getContextPath();%>	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
        <jsp:include page="../common/metaJQuery-ui-dialog.jsp"/>
		<title>部门、公司信息管理</title>
	</head>
	<body>
	     <input type="hidden" id="departmentId" name="departmentId">
	     <input type="hidden" id="departmentName" name="departmentName">
	     <div id="bussTypeDiv">
	     	<input type="hidden" id="l" name="l" value="" />
	     </div>
	     <div id="listDiv">
	     	是否显示所有隶属:
	     	<select id="list" name="list" onchange="refresh()">
	     		<option value="all" selected="selected">是</option>
	     		<option value="this">否</option>
	     	</select>
	     </div>	     
	</body>
</html>
<script type="text/javascript">
var nodeid='${node}';
var nodename='${nodeName}';
nodename = nodename==''?'组织机构':nodename;
var nodecode='${nodeCode}';
var parentnodeid='${parentNode}';
  Ext.ns('Buss.Data');
  Buss.Data.BussGrid  =  Ext.extend(Ext.grid.GridPanel,{
		 initComponent: function() {
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	});
			var config = {
			    sm : cb,
			    frame:true,
			    border:false,
				store : new Ext.data.Store({
							url : "<c:url value='/department/department.do?method=onListDepartment' />", // JSON数据
							reader : new Ext.data.JsonReader({
										totalProperty : 'total',
										root : 'rows'
									}, [
										{name: 'departmentId'},
										{name: 'departmentCode'},
										{name: 'departmentName'},
										{name: 'parentDepartmentName'},
										{name: 'level'},
										{name: 'levelName'}
									])
						}),
				columns : [index,
					cb, 
					{header: "编码", align:'center',dataIndex: 'departmentCode'},
					{header: "名称", align:'center',dataIndex: 'departmentName'},
					{header: "隶属", align:'center', dataIndex: 'parentDepartmentName'}
					//,{header: "业务类型", align:'center', dataIndex: 'levelName'}
				],
				viewConfig:{forceFit:true},
				loadMask:true	
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
		  this.bbar = new Ext.PagingToolbar({
			 store:this.store,
		     displayInfo:false,
		     pageSize:15,
		     displayMsg : '第 {0} 条到 {1} 条，一共 {2} 条',
			 emptyMsg : "没有记录"
		  });
		  // call parent
		  Buss.Data.BussGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  options=options||{}; 
				  options.params = options.params||{}; 
				  options.params.l = Ext.get('l').getValue();
				  options.params.code = Ext.get('code').getValue();
				  options.params.pid = Ext.get('departmentId').getValue();
				  options.params.list = Ext.get('list').getValue();
				  
				  return true;
		  });
		   // load the store at the latest possible moment
		  this.on({
			 beforeshow : {scope:this, single:true, fn:function() {
				   this.store.load({params:{start:0, limit:15}});
			 }}
		  });
	  }
  });
  Ext.reg('BussGrid',  Buss.Data.BussGrid);//注册一个组件,注册成xtype以便能够延迟加载

Ext.ns('Buss.Layout');
Buss.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
   var mapMenu = new Ext.menu.Menu({
		id : 'areaMenu',
		text : '区域信息操作',
		items : [
		{text : '导出',iconCls:'excelIcon',handler:_export},
		{text : '导出区域坐标信息',id:'exportArea',iconCls:'excelIcon',handler : _exportExcel},
		{text : '导入区域坐标信息',id:'importArea',iconCls:'import',handler : _importExcel}
		]
	});
   var treeDataUrl = "<c:url value='/department/department.do?method=onFindDeptTree' />";
     
   var config = {
		 collapsible:true,
		 autoWidth:true,
		 layout:"border", 
		 frame:true,
		 items:[{
		 			id:'treepanel',
		 			xtype:'treepanel',
		 			region:"west",
		 			width:200,
		 			animCollapse :false,
		 			split:true,
		 			animate :false,//去除动画
　　　				autoScroll:true,
					border : true, // 边框
					useArrows :true,
				    loader: new Ext.tree.TreeLoader({dataUrl: treeDataUrl}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:nodeid,text:nodename}),
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != nodeid){
						     Ext.get('departmentId').dom.value = node.attributes.departmentId;
						     Ext.get('departmentName').dom.value = node.attributes.departmentName;
						     refresh();
						   } else {//选择根结点
						     Ext.get('departmentId').dom.value = nodeid;
						     if (nodeid!='' || nodeid!='0') refresh();
						   }
						}
					}
              	},{
		            region:"center",
					layout:"border",
					border : false, 
					items: [{
								xtype : 'toolbar',
								region:"north",
								items:[
								  {text : '修改',iconCls:'editIcon',id:'ext_b_change',handler:_change},
								  '-',
								  {
									xtype:'panel',
									baseCls : 'x-plain',
									html:"<input type='text' id='code' name='code' title='请输入编码或名称' onchange='refresh()' />"
								  },
								  '-',
								  {
									xtype:'panel',
									baseCls : 'x-plain',
									contentEl:'bussTypeDiv'
								  },
								  {
									xtype:'panel',
									baseCls : 'x-plain',
									contentEl:'listDiv'
								  },
								  {text : '其它操作',iconCls:'manageIcon',menu : mapMenu}
								  
			                     ]
							}, {
								id : "BussGrid",
								xtype : "BussGrid",
								region:"center",
								border : false,
								hideBorders : true,
								height : clientHeight,
								autoScroll : true
							}
					]
			  }
		 	]
	}
	         
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	Buss.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});


Ext.onReady(function(){
	
	var viewport =  new Buss.Layout.Viewport();
	var BussGrid = viewport.findById('BussGrid');//Grid

	refresh = function(){//刷新
    	Ext.getCmp('BussGrid').store.load({params:{start:0, limit:15}});//刷新当前页面
	};
	
	BussGrid.addListener('celldblclick', function(grid, rowIndex,
		columnIndex, e) {// 列表双击事件
		_change();
	});
	
	Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
});

function _change(){//修改
	 var selectionSet = Ext.getCmp('BussGrid').getSelectionModel().getSelections(); 
	 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
		Ext.MessageBox.alert('系统提示','请选择一条记录进行修改!');
		return ;
	 }
     var clientId=selectionSet[0].data.departmentId;
     var url = '<c:url value='/client/client.do?method=onEditClientInfoView'/>' +  '&clientId=' + clientId;
     openModalDialogWindow('修改',url,800,400); 	 
};

function _export(){
 	var level = Ext.get('l').getValue();
	var pid  = Ext.get('departmentId').getValue();
	var list = Ext.get('list').getValue();
	var url = '<c:url value='/department/department.do?method=onExportDepartmentInfo'/>&format=excel'+'&l='+level+'&pid='+pid+'&list='+list;
	openWinCenter('导出客户信息',url, 300,250);
}
function _exportExcel(){//导出(excel)
	var level = Ext.get('l').getValue();
	var pid  = Ext.get('departmentId').getValue();
	var list = Ext.get('list').getValue();
	var url = '<c:url value='/department/department.do?method=onExportDepartmentInfo' />&format=excel'+'&ex_type=dArea'+'&l='+level+'&pid='+pid+'&list='+list;
	openWinCenter('导出行政区域坐标信息',url, 300,250);
}
function _importExcel(){
	var url = "<c:url value='/department/department.do?method=onImportDAreaFile'/>";
	window.open(url);
}
</script>
