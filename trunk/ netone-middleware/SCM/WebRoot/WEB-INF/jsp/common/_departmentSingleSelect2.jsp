<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="metaExt.jsp"></jsp:include>
		<title>用户选择</title>
	</head>
	<body>
		<input type="hidden" id="node" name="node" value="${node}">
	</body>
</html>
<script type="text/javascript">
var nodeid='${node}';
var nodename='${nodeName}';
nodename = nodename==''?'组织机构':nodename;
var nodecode='${nodeCode}';
var parentnodeid='${parentNode}';
 Ext.QuickTips.init();

 Ext.ns('Buss.Data');
  Buss.Data.BussGrid  =  Ext.extend(Ext.grid.GridPanel,{
		 initComponent: function() {
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: true
		   	});
			var config = {
			    sm : cb,
			    frame:true,
			    border:false,
				store : new Ext.data.Store({
							url : "<c:url value='/department/department.do?method=onFindDeptTree&p=${param.p}' />", // JSON数据
							reader : new Ext.data.JsonReader({
										root : ''
									}, [
										{name: 'departmentId'},
										{name: 'departmentCode'},
										{name: 'departmentName'},
										{name: 'NLevelName'},
										{name: 'levelName'}
									])
						}),
				columns : [index,
					cb, 
					{header: "部门/公司编码",sortable : true, align:'center',dataIndex: 'departmentCode',
						renderer : function (value, metadata, record, rowIndex, columnIndex, store) {   
						    //build the qtip:   
						    var title = '&nbsp;' + record.get('departmentCode') +   
						        ' ' + record.get('departmentName');   
						    var tip = '业务类型:'+record.get('levelName');
						    tip += '<BR>隶属:'+record.get('NLevelName');
						    tip = tip.replace('[0]','');
						    
						    metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';   
						    
						    //return the display text:   
						    var displayText = value;   
						    return displayText;   
						}					
					},
					{header: "部门/公司名称", align:'center',dataIndex: 'departmentName',sortable : true}
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
				  options.params.node = Ext.get('node').getValue();
				  options.params.departmentCode = Ext.get('departmentCode').getValue();
				  var deptCode = Ext.get('departmentCode').getValue();
				  if (deptCode!=''){
				  	options.params.include = 0;
				  	options.params.list = 'all';
				  } else {
				  	options.params.include = 1;
				  }
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
   
   var treeDataUrl = "<c:url value='/department/department.do?method=onFindDeptTree' />";
     
   var config = {
		 collapsible:true,
		 autoWidth:true,
		 border:false,
		 layout:"border", 
		 
		 items:[{
		 			id:'treepanel',
		 			xtype:'treepanel',
		 			region:"west",
		 			width:180,
		 			style:"padding:2px",
		 			title:nodename,
		 			iconCls:"flow_chartIcon",
		 			//rootVisible:false,     //隐藏根节点  
		 			split:true,
		 			collapsible: true,//伸缩
		 			animCollapse :false,
		 			animate :false,//去除动画
　　　				autoScroll:true,
					border : true, // 边框
					useArrows :true,
				    loader: new Ext.tree.TreeLoader({dataUrl: treeDataUrl}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:nodeid,text:nodename}),
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != nodeid){
						   	   Ext.get('node').dom.value = node.attributes.departmentId;
						       refresh();
						   } else {
						   	   Ext.get('node').dom.value = nodeid;
						   	   refresh();
						   }
						}
					}
              	},{
              		id:'BussGrid',
		            region:"center",
		            xtype:'BussGrid',
					border:false,
					hideBorders:true,
	            	//autoScroll:true,
	            	buttonAlign : "center",
	            	tbar:new Ext.Toolbar([
							{
								xtype:'panel',
								baseCls : 'x-plain',
								html:'<input id=\'departmentCode\' type=\'text\' onkeyup=\'onKeyUpEvent(event);\'>'
							},
							'-',
							{text : '查询',iconCls:'viewIcon',id:'ext_b_search',handler:refresh}
					])
			  }
		 	]
	}
	         
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	Buss.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});

function refreshTree(){//刷新树
	Ext.getCmp('treepanel').root.reload();
	//Ext.getCmp('treepanel').expandAll();//树默认全部展开
	Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
}

/**
* 父窗口方法 _multSelectUser(ids,values,texts) 
*/
function _select(ids,values,texts){
	//alert(ids+','+values+','+texts)
	if (window.opener && window.opener.location){
		opener._singleSelectDepartment2(ids,values,texts);window.close();
	} else {
		alert('找不到父窗口!')
	}
}

var refresh = function (){
 	Ext.getCmp('BussGrid').store.load();
}

Ext.onReady(function(){
	
	var viewport =  new Buss.Layout.Viewport();
	
    //Ext.getCmp('treepanel').expandAll();//树默认全部展开
    Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
    Ext.getCmp('treepanel').getRootNode().select();
    Ext.getCmp('BussGrid').bbar.dom.align = 'center';//按钮居中
	Ext.getCmp('BussGrid').addListener('celldblclick', function(grid, rowIndex,
	columnIndex, e) {// 列表双击事件
		 var l = "";
	     var j = "";
	     var k = "";
	     var f = Ext.getCmp("BussGrid").getSelectionModel().getSelections();
	     l = f[0].data.departmentId;
	     j = f[0].data.departmentCode;
	     k = f[0].data.departmentName;
	     _select(l,j,k);
	});
	
});

function onKeyUpEvent(e){//监听键盘事件
	switch(e.keyCode)
	{
		case 13:
			refresh();
			break;//回车  
		default:
		break;
	}
}

</script>
