<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<title>产品选择</title>
	</head>
	<body>
		<input type="hidden" id="node" name="node" value="${nodeid}" />
		<input type="hidden" id="nodecode" name="nodecode"  />
		<input type="hidden" id="nodename" name="nodename"  />
		
	</body>
</html>
<script type="text/javascript">
var nodeid='0';
var nodename='产品列表';
nodename = nodename==''?'产品列表':nodename;
var nodecode='';
var parentnodeid='0';
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
							url : "<c:url value='/app.do?method=findProductSetByParentId' />", // JSON数据
							reader : new Ext.data.JsonReader({
										root : 'info'
									}, [
										{name: 'productId'},
										{name: 'productCode'},
										{name: 'productName'}
									])
						}),
				columns : [index,
					cb, 
					{header: "编码", align:'center',dataIndex: 'productCode',sortable : true,
						renderer : function (value, metadata, record, rowIndex, columnIndex, store) {   
						    //build the qtip:   
						    var title = '&nbsp;' +record.get('productCode')+' ' + record.get('productName');   
						    
						    metadata.attr = 'ext:qtitle="' + title + '"';   
						    
						    //return the display text:   
						    var displayText = value;   
						    return displayText;   
						}
					},
					{header: "名称", align:'center',dataIndex: 'productName',sortable : true}
				],
				viewConfig:{forceFit:true},
				loadMask:true	
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
		  // call parent
		  Buss.Data.BussGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var condition = {
				  }; 
				  options=options||{}; 
				  options.params = options.params||{}; 
				  options.params.id = Ext.get('node').dom.value;
				  //options.params.code = Ext.get('nodecode').dom.value;
				  options.params.condition = Ext.get('condition').dom.value;
				  return true;
		  });
		   // load the store at the latest possible moment
		  this.on({
			 beforeshow : {scope:this, single:true, fn:function() {
				   this.store.load();
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
										{name: 'productId'},
										{name: 'productCode'},
										{name: 'productName'}
									])
						}),
				columns : [index,
					cb, 
					{header: "编码", align:'center',dataIndex: 'productCode',sortable : true},
					{header: "名称", align:'center',dataIndex: 'productName',sortable : true}
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
   
   var treeDataUrl = "<c:url value='/app.do?method=onFindCategoriesTree' />";
     
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
						   	   Ext.get('node').dom.value = node.attributes.id;
						   	   Ext.get('nodecode').dom.value = node.attributes.code;
						   	   Ext.get('nodename').dom.value = node.attributes.text;
						       refresh();
						   } else {
						   	   Ext.get('node').dom.value='0';
						   	   Ext.get('nodecode').dom.value = '';
						   	   Ext.get('nodename').dom.value='';
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
	            	bbar:new Ext.Toolbar([
						{text : '提交',iconCls:'viewIcon',id:'ext_b_search',handler:function(){
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
						       l += f.getAt(g).data.productId;
						       j += f.getAt(g).data.productCode;
						       k += f.getAt(g).data.productName;
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
								html:'<input id=\'condition\' type=\'text\' onkeyup=\'onKeyUpEvent(event);\'>'
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
				        		buttonAlign :'center',
				        		width:26,
				        		items : [
				        		     {baseCls : 'x-plain'},
				        		     {baseCls : 'x-plain'},
				        		     {baseCls : 'x-plain'},
				        		     {baseCls : 'x-plain'},
				        		     {baseCls : 'x-plain'},
				        		     {baseCls : 'x-plain'},{
					            	 xtype : "button",
								     iconCls : "add-allIcon",
								     buttonAlign :'center',
								     handler : add_all
				            	},{
				            		 xtype : "button",
					            	 iconCls : "rem-allIcon",
								     buttonAlign :'center',
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
	 var q = x[p].data.productId;
	 var v = x[p].data.productCode;
	 var k = x[p].data.productName;
	 var s = false;
	 for ( var o = 0; o < u.getCount(); o++) {
	  if (u.getAt(o).data.productId == q) {
	   s = true;
	   break;
	  }
	 }
	 if (!s) {
	  var w = {
	   productId : q,
	   productCode : v,
	   productName : k
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
* 父窗口方法 _audit(ids,values,texts) 
*/
function _select(ids,values,texts){
	if (window.opener && window.opener.location){
		opener.$selectProduct(ids,values,texts);window.close();
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
