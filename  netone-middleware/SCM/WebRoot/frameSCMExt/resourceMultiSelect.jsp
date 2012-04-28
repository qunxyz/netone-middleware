<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<title>选择</title>
	</head>
	<body>
		<input type="hidden" id="node" name="node" value="${node_}">
	</body>
</html>
<script type="text/javascript">
var nodeid='${nodeid}';
var nodecode='${nodecode}';
var nodename='${nodename}';
var parentnodeid='${parentnode}';
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
							url : "<c:url value='/frame.do?method=onListResourceNode' />", // JSON数据
							reader : new Ext.data.JsonReader({
										totalProperty : 'total',
										root : 'rows'
									}, [
										{name: 'resourceid'},
										{name: 'resourcecode'},
										{name: 'resourcename'},
										{name: 'types'}
									])
						}),
				columns : [index,
					cb, 
					/**
					{header: "编码", align:'center',dataIndex: 'resourcecode',sortable : true,
						renderer : function (value, metadata, record, rowIndex, columnIndex, store) {   
						    //build the qtip:   
						    var title = '&nbsp;' + record.get('resourcecode') +' ' + record.get('resourcename');   
						    var tip = '类型:'+record.get('types');
						    
						    metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';   
						    
						    //return the display text:   
						    var displayText = value;   
						    return displayText;   
						}
					},
					**/
					{header: "名称", align:'center',dataIndex: 'resourcename',sortable : true}
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
	      		  var condition = {
				   node: Ext.get('node').getValue(),
				   query:Ext.get('query').getValue(),
				   type:'${param.type}'
				  }; 
				  options=options||{}; 
				  options.params = options.params||{}; 
				  options.params.condition = Ext.util.JSON.encode(condition);
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
							reader : new Ext.data.JsonReader({
										totalProperty : 'total',
										root : 'rows'
									}, [
										{name: 'resourceid'},
										{name: 'resourcecode'},
										{name: 'resourcename'},
										{name: 'types'}
									])
						}),
				columns : [index,
					cb, 
					//{header: "编码", align:'center',dataIndex: 'resourcecode',sortable : true},
					{header: "名称", align:'center',dataIndex: 'resourcename',sortable : true}
				],
				viewConfig:{forceFit:true},
				loadMask:true	
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
		  // call parent
		  Buss.Data2.BussGrid.superclass.initComponent.apply(this, arguments);
		  
	  }
  });
  Ext.reg('BussGrid2',  Buss.Data2.BussGrid);//注册一个组件,注册成xtype以便能够延迟加载

Ext.ns('Buss.Layout');

Buss.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
   
   var treeDataUrl = "<c:url value='/frame.do?method=onFindResourceTree' />";
     
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
					   	   Ext.get('node').dom.value = node.attributes.resourceid;
				       	   refresh();
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
	            	bbar:new Ext.Toolbar([
						{text : '确认',iconCls:'viewIcon',id:'ext_b_search',handler:function(){
							var l = "";
						     var j = "";
						     var k = "";
						     var f = Ext.getCmp("BussGrid2").getStore();
						     var len =  f.getCount();
						     if (len<=0) {return;}
						      for ( var g = 0; g < len; g++) {
						       if (g > 0) {
						        l += ",";
						        j += ",";
						        k += ",";
						       }
						       l += f.getAt(g).data.resourceid;
						       j += f.getAt(g).data.resourcecode;
						       k += f.getAt(g).data.resourcename;
						      }
						     _select(l,j,k);
						}},
						{text : '关闭',iconCls:'closeIcon',id:'ext_b_search',handler:function(){
							window.close();
						}}
					]),
				    items: [{
				    	id:'BussGrid',
				        xtype:'BussGrid',
				        height:clientHeight-30,
				        tbar:new Ext.Toolbar([
							{
								xtype:'panel',
								baseCls : 'x-plain',
								html:'<input id=\'query\' type=\'text\' onkeyup=\'onKeyUpEvent(event);\'>'
							},
							'-',
							{text : '查询',iconCls:'viewIcon',id:'ext_b_search',handler:refresh}
						]),
				        columnWidth: .5
				    },{
				        columnWidth: .5,
				        xtype:'panel',
				        layout:'column',
				        items:[{
				        		region:"west",
				        		xtype : "panel",
				        		border:false,
				        		width:50,
				        		items : [{xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},{
					            	 xtype : "button",
								     //iconCls : "add-allIcon",
								     text :'选择',
								     buttonAlign :'center',
								     handler : add_all
				            	},
				            	 {xtype : 'panel',border:false,frame:false},
				            	{
				            		 xtype : "button",
					            	 //iconCls : "rem-allIcon",
								     buttonAlign :'center',
								     text :'取消',
								     handler : rem_all
				            	}]
				        	},{
				        		region:"center",
				        		columnWidth: 1,
				        		id:'BussGrid2',
				       			xtype:'BussGrid2',
				       			height:clientHeight-30
				    		}
				        ]
				    }]
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

function add_all(){//添加
	var t = Ext.getCmp("BussGrid");
	var n = Ext.getCmp("BussGrid2");
	var u = n.getStore();
	var x = t.getSelectionModel().getSelections();
	for ( var p = 0; p < x.length; p++) {
	 var q = x[p].data.resourceid;
	 var v = x[p].data.resourcecode;
	 var k = x[p].data.resourcename;
	 var s = false;
	 for ( var o = 0; o < u.getCount(); o++) {
	  if (u.getAt(o).data.resourceid == q) {
	   s = true;
	   break;
	  }
	 }
	 if (!s) {
	  var w = {
	   resourceid : q,
	   resourcecode : v,
	   resourcename : k
	  };
	  var r = new u.recordType(w);
	  n.stopEditing();
	  u.add(r);
	 }
	}
}

function rem_all(){//移除
 var p = Ext.getCmp("BussGrid2");
 var q = p.getSelectionModel().getSelections();
 var n = p.getStore();
 for ( var o = 0; o < q.length; o++) {
  p.stopEditing();
  n.remove(q[o]);
 }
}

/**
* 父窗口方法 _multSelectUser(ids,values,texts) 
*/
function _select(ids,values,texts){
	var columnid = '${param.columnid}';
	if(window.opener && window.opener.location) {
		opener.$('#'+opener.selectObjVar.id).siblings('#'+columnid).val(texts);
		//opener.selectObjVar.val(texts);
		//opener._multSelectUser(ids,values,texts);
		window.close();
	} else {
		alert('找不到父窗口!')
	}
}

var refresh = function (){
 	Ext.getCmp('BussGrid').store.load({params : {
						start : 0,
						limit : 15
					}});
}

Ext.onReady(function(){
	
	var viewport =  new Buss.Layout.Viewport();
	
    //Ext.getCmp('treepanel').expandAll();//树默认全部展开
    Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
    Ext.getCmp('treepanel').getRootNode().select();
    Ext.getCmp('_grid').bbar.dom.align = 'center';//按钮居中
	Ext.getCmp('BussGrid').addListener('celldblclick', function(grid, rowIndex,
	columnIndex, e) {// 列表双击事件
		add_all();
	});
	
	Ext.getCmp('BussGrid2').addListener('celldblclick', function(grid, rowIndex,
	columnIndex, e) {// 列表双击事件
		rem_all();
	});
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
