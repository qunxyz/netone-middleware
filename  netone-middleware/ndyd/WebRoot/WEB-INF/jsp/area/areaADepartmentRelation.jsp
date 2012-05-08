<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>行政区划与组织机构关联</title>
	</head>
	<body>
		<input type="hidden" id="node" name="node"/>
		<input type="hidden" id="parentDepartmentId" name="parentDepartmentId" value="0"/>
		<input type="hidden" id="list" name="list" value="all"/>
	</body>
</html>
<script type="text/javascript">
 Ext.QuickTips.init();

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
							url : "<c:url value='/department/area.do?method=onFindAreaADepartmentRelation' />", // JSON数据
							reader : new Ext.data.JsonReader({
										totalProperty : 'total',
										root : 'rows'
									}, [
										{name: 'areaId'},
										{name: 'departmentId'},
										{name: 'departmentCode'},
										{name: 'departmentName'},
										{name: 'nLevel'},
										{name: 'level'},
										{name: 'nLevelName'},
										{name: 'levelName'}
									])
						}),
				columns : [index,
					{header: "编码", align:'center',dataIndex: 'departmentCode',
						renderer : function (value, metadata, record, rowIndex, columnIndex, store) {   
						    //build the qtip:   
						    var title = '&nbsp;' + record.get('departmentCode') +   
						        ' ' + record.get('departmentName');   
						    var tip = '目录上下关系:'+record.get('nLevelName')+'<BR>';
						    	tip += '树级别:'+record.get('level')+'<BR>';
						    	tip += '业务类型:'+record.get('levelName');
						    
						    metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';   
						    
						    //return the display text:   
						    var displayText = value;   
						    return displayText;   
						}
					},
					{header: "名称", align:'center',dataIndex: 'departmentName'},
					{header: "操作", align:'center',dataIndex: '', sortable: false, renderer:
						     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {
						     	   var stackCode = store.getAt(rowIndex).get('stackCode') ;
								   value = "&nbsp;<a href=\"javascript:void(0)\" onclick=relateAreaADepartment(\"remove\",\""+record.get('departmentId')+"\")  title=\"移除关联\" >-</a>&nbsp;"+
								   		   "&nbsp;<a href=\"javascript:void(0)\" onclick=relateAreaADepartment(\"multRemove\",\""+record.get('departmentId')+"\")  title=\"移除关联(包含所有下级)\" >-*</a>&nbsp;";
								   return value;
							 }
					 }
				],
				viewConfig:{forceFit:true},
				loadMask:true
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
		  this.bbar = new Ext.PagingToolbar({
			 store:this.store,
		     displayInfo:false,
		     pageSize:50,
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
				  return true;
		  });
		   // load the store at the latest possible moment
		  this.on({
			 beforeshow : {scope:this, single:true, fn:function() {
				   this.store.load({params:{start:0, limit:50}});
			 }}
		  });
		  
	  }
  });
  Ext.reg('BussGrid',  Buss.Data.BussGrid);//注册一个组件,注册成xtype以便能够延迟加载


 Ext.ns('Buss.Data2');
  Buss.Data2.BussGrid  =  Ext.extend(Ext.grid.GridPanel,{
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
										{name: 'nLevel'},
										{name: 'level'},
										{name: 'levelName'},
										{name: 'nLevelName'}
									])
						}),
				columns : [index,
					{header: "编码", align:'center',dataIndex: 'departmentCode',
						renderer : function (value, metadata, record, rowIndex, columnIndex, store) {   
						    //build the qtip:   
						    var title = '&nbsp;' + record.get('departmentCode') +   
						        ' ' + record.get('departmentName');   
						    var tip = '目录上下关系:'+record.get('nLevelName')+'<BR>';
						    	tip += '树级别:'+record.get('level')+'<BR>';
						    	tip += '业务类型:'+record.get('levelName');
						    
						    metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';   
						    
						    //return the display text:   
						    var displayText = value;   
						    return displayText;   
						}
					},
					{header: "名称", align:'center',dataIndex: 'departmentName'},
					{header: "操作", align:'center',dataIndex: '', sortable: false, renderer:
						     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {
						     	   var stackCode = store.getAt(rowIndex).get('stackCode') ;
								   value = "&nbsp;<a href=\"javascript:void(0)\" onclick=relateAreaADepartment(\"add\",\""+record.get('departmentId')+"\")  title=\"添加关联\" >+</a>&nbsp;"+
								   		   "&nbsp;<a href=\"javascript:void(0)\" onclick=relateAreaADepartment(\"multAdd\",\""+record.get('departmentId')+"\")  title=\"添加关联(包含所有下级)\">+*</a>&nbsp;"+
								   		   "&nbsp;<a href=\"javascript:void(0)\" onclick=relateAreaADepartment(\"remove\",\""+record.get('departmentId')+"\")  title=\"移除关联\" >-</a>&nbsp;"+
								   		   "&nbsp;<a href=\"javascript:void(0)\" onclick=relateAreaADepartment(\"multRemove\",\""+record.get('departmentId')+"\")  title=\"移除关联(包含所有下级)\" >-*</a>&nbsp;";
								   return  value;
							 }
					 }
				],
				viewConfig:{forceFit:true},
				loadMask:true
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
		  this.bbar = new Ext.PagingToolbar({
			 store:this.store,
		     displayInfo:false,
		     pageSize:50,
		     displayMsg : '第 {0} 条到 {1} 条，一共 {2} 条',
			 emptyMsg : "没有记录"
		  });
		  // call parent
		  Buss.Data2.BussGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  options=options||{}; 
				  options.params = options.params||{}; 
				  options.params.l = '';
				  options.params.pid = Ext.get('parentDepartmentId').getValue();
				  options.params.list = Ext.get('list').getValue();
				  return true;
		  });
		   // load the store at the latest possible moment
		  this.on({
			 beforeshow : {scope:this, single:true, fn:function() {
				   this.store.load({params:{start:0, limit:50}});
			 }}
		  });
		  
	  }
  });
  Ext.reg('BussGrid2',  Buss.Data2.BussGrid);//注册一个组件,注册成xtype以便能够延迟加载

Ext.ns('Buss.Layout');

var nodeid='0';
var nodename='行政区划';
var nodecode='0';
var parentnodeid='0';
Buss.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
   
   var treeDataUrl = "<c:url value='/department/area.do?method=onFindAreaTree' />";
     
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
		 			split:true,
		 			collapsible: true,//伸缩
		 			//rootVisible:false,     //隐藏根节点
		 			split:true,
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
						   	   Ext.get('node').dom.value = node.attributes.areaId;
						       refresh();
						   } else {
						   	   Ext.get('node').dom.value='0';
						   	   Ext.getCmp('BussGrid').store.removeAll();
						   }
						}
					}
              	},{
              		id:'_grid',
		            region:"center",
		            xtype:'panel',
					border:false,
					hideBorders:true,
	            	autoScroll:true,
	            	buttonAlign :'center',
	            	layout:'column',
				    items: [{
				    	columnWidth: .5,
						title:'已关联',
				        frame:true,				    	
				    	id:'BussGrid',
				        xtype:'BussGrid',
				        height:clientHeight,
				        tbar:new Ext.Toolbar([
							{
								xtype:'panel',
								baseCls : 'x-plain',
								html:'<input id=\'departmentCode\' type=\'text\' onkeyup=\'onKeyUpEvent(event);\'>'
							},
							'-',
							{text : '查询',iconCls:'viewIcon',id:'ext_b_search',handler:refresh}
						])
				        
				    },{
				        columnWidth: .5,
				        title:'组织机构',
				        frame:true,
				        height:clientHeight+40,
				        id:'BussGrid2',
		       			xtype:'BussGrid2',
		       			tbar:new Ext.Toolbar([
							{
								xtype:'panel',
								baseCls : 'x-plain',
								html:'<div id=\"comboBoxTree\"></div>'
							},
							'-',
							{text : '显示所有',iconCls:'viewIcon',id:'ext_b_search2',handler:refresh2All}
						])
				    }]
			  }
		 	]
	}
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	Buss.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});

var nodeidx='0';
var nodenamex='组织机构';
var nodecodex='0';
var parentnodeidx='0';
var deptTree={//加载树
   url: '<c:url value="/department/department.do?method=onFindDeptTree" />',
   
     init: function(){
     this.comboBoxTree = new Ext.ux.ComboBoxTree({//上级分类选项
				renderTo : 'comboBoxTree',
				width : 200,
				tree : {
					xtype:'treepanel',
				    loader: new Ext.tree.TreeLoader({dataUrl: this.url}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:nodeidx,text:nodenamex}),
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != nodeidx){
						       Ext.get('parentDepartmentId').dom.value = node.attributes.id;
						       Ext.get('list').dom.value='notall';
						       refresh2();
						   }else{
						       Ext.get('parentDepartmentId').dom.value = nodeidx;
							   Ext.get('list').dom.value='all';
						       deptTree.comboBoxTree.setValue({id: nodeidx, text: nodenamex});
						       refresh2();
						   }
						 }
					 }
		       	 
		    	},
		    	//all:所有结点都可选中
				//exceptRoot：除根结点，其它结点都可选(默认)
				//folder:只有目录（非叶子和非根结点）可选
				//leaf：只有叶子结点可选
				selectNodeModel:'folder'
      });
  	},		  
    clear: function(){
	     var comboBoxTree = document.getElementById('comboBoxTree');
		 comboBoxTree.innerHTML = '';  
	},
	  
	setValue: function(value){
	     this.comboBoxTree.setValue(value); 
	}
 }


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
		opener._multSelectDepartment(ids,values,texts);window.close();
	} else {
		Ext.MessageBox.alert('提示','找不到父窗口!')
	}	
}

var refresh = function (){
 	Ext.getCmp('BussGrid').store.load({params : {
						start : 0,
						limit : 50
					}});
}

var refresh2 = function (){
 	Ext.getCmp('BussGrid2').store.load({params : {
						start : 0,
						limit : 50
					}});
}

var refresh2All = function (){
	Ext.get('parentDepartmentId').dom.value=nodeid;
	Ext.get('list').dom.value='all';
 	Ext.getCmp('BussGrid2').store.load({params : {
						start : 0,
						limit : 50
					}});
}

function relateAreaADepartment(t,did){
			var areaId = Ext.get('node').getValue();
			if (areaId=='0' || areaId=='') {
				Ext.MessageBox.alert('提示','请选择要关联的区划!');
				return;
			}
		    var msgTip = Ext.MessageBox.show({
				title:'系统提示',
				width : 200,
				msg:'正在保存信息请稍后......'
		  	});
		     Ext.Ajax.request({
					url :"<c:url value='/department/area.do?method=onRelateAreaADepartment' />",//请求的服务器地址
					method : 'POST',
					params:{t:t,areaId:areaId,departmentId:did},
					success : function(response,options){
						msgTip.hide();
						var result = Ext.util.JSON.decode(response.responseText);
						if(result.error==null){
							refresh();
						}else{
							Ext.MessageBox.alert('提示',result.tip);
						}
					},
					failure : function(response,options){
						checkAjaxStatus(response);
						var result = Ext.util.JSON.decode(response.responseText);
						Ext.MessageBox.alert('提示',result.tip);
					}
		　   });
}

Ext.onReady(function(){
	
	var viewport =  new Buss.Layout.Viewport();
	
    //Ext.getCmp('treepanel').expandAll();//树默认全部展开
    Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
    Ext.getCmp('treepanel').getRootNode().select();
	
	Ext.getCmp('BussGrid').addListener('bodyresize', function(p,width,height) {
	   var clientHeight = 0;
		 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
		    clientHeight = document.documentElement.clientHeight;
		 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
		    clientHeight = document.body.clientHeight;
	   }
	   p.setHeight(clientHeight-30);
	});
	Ext.getCmp('BussGrid2').addListener('bodyresize', function(p,width,height) {
	   var clientHeight = 0;
		 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
		    clientHeight = document.documentElement.clientHeight;
		 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
		    clientHeight = document.body.clientHeight;
	   }
	   p.setHeight(clientHeight-30);
	});
	
	refresh2();
	deptTree.init();
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

function onKeyUpEvent2(e){//监听键盘事件
	switch(e.keyCode)
	{
		case 13:
			refresh2();
			break;//回车  
		default:
		break;
	}
}
</script>
