<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="metaExt.jsp"></jsp:include>
		<title>产品选择</title>
	</head>
	<body>
	<input type="hidden" id="id" name="id" value="0">
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
							url : "<c:url value='/products/product.do?method=onFindProductSetByPC' />", // JSON数据
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
						    var title = '&nbsp;' + record.get('productCode') + ' ' + record.get('productName');   
						    var tip = '';
						    
						    metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';   
						    
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
				  options=options||{}; 
				  options.params = options.params||{}; 
				  options.params.id = Ext.get('id').getValue();
				  var condi = Ext.get('condition').getValue();
				  if (condi!=''){
				  	options.params.id = '';
				  }
				  options.params.condition = condi;
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
   
   var treeDataUrl = "<c:url value='/products/products.do?method=onFindDeptTree' />";
     
   var config = {
		 collapsible:true,
		 autoWidth:true,
		 border:false,
		 layout:"border", 
		 
		 items:[{
		 			id:'treepanel',
		 			xtype:'treepanel',
		 			region:"west",
		 			width:200,
		 			animCollapse :false,
		 			animate :false,//去除动画
　　　				autoScroll:true,
					border : true, // 边框
					useArrows :true,
				    loader: new Ext.tree.TreeLoader({dataUrl: treeDataUrl}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:'0',text:'产品分类树'}),
		       	 	listeners : {
						click : function(node,e){
					   	   Ext.get('id').dom.value = node.attributes.id;
					       refresh();
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
								html:'<input id=\'condition\' type=\'text\' onkeyup=\'onKeyUpEvent(event);\'>'
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

var refresh = function (){
 	Ext.getCmp('BussGrid').store.load();
}

/**
* 父窗口方法 _multSelectProduct(ids,values,texts) 
*/
function _select(ids,values,texts){
	//alert(ids+':'+texts);
	if (window.opener && window.opener.location){
		opener._singleSelectProduct(ids,texts);window.close();
	} else {
		alert('找不到父窗口!')
	}
}

Ext.onReady(function(){
	
	var viewport =  new Buss.Layout.Viewport();
	
    //Ext.getCmp('treepanel').expandAll();//树默认全部展开
    Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
    Ext.getCmp('treepanel').getRootNode().select();
	Ext.getCmp('BussGrid').addListener('celldblclick', function(grid, rowIndex,
	columnIndex, e) {// 列表双击事件
		 var l = "";
	     var j = "";
	     var k = "";
	     var f = Ext.getCmp("BussGrid").getSelectionModel().getSelections();
	     l = f[0].data.productId;
	     j = f[0].data.productCode;
	     k = f[0].data.productName;
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
