<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<title>门店分类与产品关联</title>
	</head>
	<body>
		<input type="hidden" id="parentCategoriesId" name="parentCategoriesId"/>
		<input type="hidden" id="node" name="node"/>
		<input type="hidden" id="parentProductId" name="parentProductId" value="0"/>
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
							url : "<c:url value='/app.do?method=onFindPartAProductRelation' />", // JSON数据
							reader : new Ext.data.JsonReader({
										root : 'rows'
									}, [
										{name: 'partId'},
										{name: 'productId'},
										{name: 'productCode'},
										{name: 'productName'},
										{name: 'type'}
									])
						}),
				columns : [index,
					cb,
					{header: "编码", align:'center',dataIndex: 'productCode',
						renderer : function (value, metadata, record, rowIndex, columnIndex, store) {   
						    //build the qtip:   
						    var title = '&nbsp;' + record.get('productCode') +   
						        ' ' + record.get('productName');   
						    var tip = '';
						    
						    metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';   
						    
						    //return the display text:   
						    var displayText = value;   
						    return displayText;   
						}
					},
					{header: "名称", align:'center',dataIndex: 'productName'},
					{header: "类别", align:'center',dataIndex: 'type', renderer:
						     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {
								   return $("#cates option[value='"+value+"']").text();
							 }
					},
					{header: "操作", align:'center',dataIndex: '', sortable: false, renderer:
						     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {
								   value = "&nbsp;<a href=\"javascript:void(0)\" onclick=relatePartAProduct(\"remove\",\""+record.get('productId')+"\")  title=\"移除关联\" >-</a>&nbsp;"+
								   		   "&nbsp;";
								   return value;
							 }
					 }
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
				  options.params.node = Ext.get('node').getValue();
				  options.params.productCode = Ext.get('productCode').getValue();
				  options.params.type = Ext.get('cates').getValue();
				  return true;
		  });
		   // load the store at the latest possible moment
		  
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
							url : "<c:url value='/app.do?method=onFindProductSetByPC' />", // JSON数据
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
					{header: "编码", align:'center',dataIndex: 'productCode',
						renderer : function (value, metadata, record, rowIndex, columnIndex, store) {   
						    //build the qtip:   
						    var title = '&nbsp;' + record.get('productCode') +   
						        ' ' + record.get('productName');   
						    var tip = '';
						    
						    metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';   
						    
						    //return the display text:   
						    var displayText = value;   
						    return displayText;
						}
					},
					{header: "名称", align:'center',dataIndex: 'productName'},
					{header: "操作", align:'center',dataIndex: '', sortable: false, renderer:
						     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {
								   value = "&nbsp;<a href=\"javascript:void(0)\" onclick=relatePartAProductx(\"add\","+rowIndex+")  title=\"添加关联\" >+</a>&nbsp;"+
								   		   "&nbsp;<a href=\"javascript:void(0)\" onclick=relatePartAProduct(\"remove\",\""+record.get('productId')+"\")  title=\"移除关联\" >-</a>&nbsp;";
								   return  value;
							 }
					 }
				],
				viewConfig:{forceFit:true},
				loadMask:true
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
		  // call parent
		  Buss.Data2.BussGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  options=options||{}; 
				  options.params = options.params||{}; 
				  if (Ext.get('parentCategoriesId').getValue()==0){
				  options.params.id = '';
				  } else {
				  options.params.id = Ext.get('parentCategoriesId').getValue();
				  }
		  		  options.params.condition = Ext.get('condition').getValue();
				  return true;
		  });
		  
	  }
  });
  Ext.reg('BussGrid2',  Buss.Data2.BussGrid);//注册一个组件,注册成xtype以便能够延迟加载

Ext.ns('Buss.Layout');

var nodeid='0';
var nodename='公司';
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
   
   var treeDataUrl = "<c:url value='/app.do?method=onFindPartTree' />";
     
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
					plugins: new Ext.ux.tree.TreeNodeMouseoverPlugin(), //must use the plugin 
				    loader: new Ext.ux.tree.PagingTreeLoader({pageSize:25,enableTextPaging:true,dataUrl: treeDataUrl}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:nodeid,text:nodename}),
		       	 	listeners : {
						click : function(node,e){	
						   if(node.attributes.id != nodeid){
						   	   Ext.get('node').dom.value = node.attributes.partId;
						       refresh();
						   } else {
						   	   Ext.get('node').dom.value='0';
						   	   Ext.getCmp('BussGrid').store.removeAll();
						   }
						},
						beforeload : function() {   
					       this.body.mask('加载中...');//tree为TreePanel对象   
					    },   
					    load : function() {   
					       this.body.unmask();//tree为TreePanel对象   
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
				        	{xtype:'panel',baseCls : 'x-plain',
								html:'类别:<select id=\'cates\'   onchange=\'refresh()\'><option value=\'\'>所有</option></select>'
							 },
							 '-',
				        	{text : '移除',iconCls:'deleteIcon',handler:function(){multi('remove');}},
							{
								xtype:'panel',
								baseCls : 'x-plain',
								html:'<input id=\'productCode\' type=\'text\' onkeyup=\'onKeyUpEvent(event);\'>'
							},
							'-',
							{text : '查找',iconCls:'viewIcon',id:'ext_b_search',handler:refresh}
						])
				        
				    },{
				        columnWidth: .5,
				        title:'产品列表',
				        frame:true,
				        height:clientHeight+40,
				        id:'BussGrid2',
		       			xtype:'BussGrid2',
		       			tbar:new Ext.Toolbar([
		       			    {text : '添加',iconCls:'addIcon',handler:function(){multi('add');}},
							{
								xtype:'panel',
								baseCls : 'x-plain',
								html:'<div id=\"comboBoxTree\"></div>'
							},
							{xtype:'panel',baseCls : 'x-plain',
								html:'<input id=\'condition\' type=\'text\' onkeyup=\'onKeyUpEvent2(event);\' onchange=\'refresh2();\'>'
							 },
							 '-',
							 {text : '查找',iconCls:'viewIcon',handler:function(){refresh2();}}
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
var nodenamex='产品列表';
var nodecodex='0';
var parentnodeidx='0';
var deptTree={//加载树
   url: '<c:url value="/app.do?method=onFindCategoriesTree" />',
   
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
						       Ext.get('parentCategoriesId').dom.value = node.attributes.id;
						       refresh2();
						   }else{
						       Ext.get('parentCategoriesId').dom.value = nodeidx;
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
		opener._multSelectProduct(ids,values,texts);window.open("","_self");window.opener=null;window.close();
	} else {
		Ext.MessageBox.alert('提示','找不到父窗口!')
	}	
}

var refresh = function (){
 	Ext.getCmp('BussGrid').store.load();
}

var refresh2 = function (){
 	Ext.getCmp('BussGrid2').store.load();
}

var refresh2All = function (){
	Ext.get('parentProductId').dom.value=nodeid;
	Ext.get('list').dom.value='all';
 	Ext.getCmp('BussGrid2').store.load();
}

function relatePartAProduct(t,did){
var cates = document.getElementById('cates').value;
if (t=='add'){
	if (cates=='') {
		alert('不能添加所有类别，请选择一个类别！');
		return;
	}
}
			var partId = Ext.get('node').getValue();
			if (partId=='0' || partId=='') {
				Ext.MessageBox.alert('提示','请选择要关联的公司划分!');
				return;
			}
		    var msgTip = Ext.MessageBox.show({
				title:'系统提示',
				width : 200,
				msg:'正在保存信息请稍后......'
		  	});
		     Ext.Ajax.request({
					url :"<c:url value='/app.do?method=onRelatePartAProduct' />",//请求的服务器地址
					method : 'POST',
					params:{t:t,partId:partId,productId:did},
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

function relatePartAProductx(t,index){
var cates = document.getElementById('cates').value;
if (t=='add'){
	if (cates=='') {
		alert('不能添加所有类别，请选择一个类别！');
		return;
	}
}
			var rec = Ext.getCmp('BussGrid2').store.getAt(index);
			var partId = Ext.get('node').getValue();
			if (partId=='0' || partId=='') {
				Ext.MessageBox.alert('提示','请选择要关联的公司划分!');
				return;
			}
		    var msgTip = Ext.MessageBox.show({
				title:'系统提示',
				width : 200,
				msg:'正在保存信息请稍后......'
		  	});
		     Ext.Ajax.request({
					url :"<c:url value='/app.do?method=onRelatePartAProduct' />",//请求的服务器地址
					method : 'POST',
					params:{t:t,type:cates,partId:partId,productId:rec.get('productId'),productCode:rec.get('productCode'),productName:rec.get('productName')},
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


function multi(t){
var cates = document.getElementById('cates').value;
	var partId = Ext.get('node').getValue();
	if (partId=='0' || partId=='') {
		Ext.MessageBox.alert('提示','请选择要关联的公司划分!');
		return;
	}
	var recs = null;
	if (t=='add'){
		recs = Ext.getCmp('BussGrid2').getSelectionModel().getSelections();
		if (cates=='') {
			alert('不能添加所有类别，请选择一个类别！');
			return;
		}
	} else if (t='remove'){
		recs = Ext.getCmp('BussGrid').getSelectionModel().getSelections();
	}
	if(recs.length == 0){
		Ext.Msg.alert('系统提示','请选择要进行操作的信息！');
	}else{
		for(var i = 0 ; i < recs.length ; i++){
			var rec = recs[i];
			var productId = rec.get('productId');
			var productCode = rec.get('productCode');
			var productName = rec.get('productName');
			Ext.Ajax.request({
					url :"<c:url value='/app.do?method=onRelatePartAProduct' />",//请求的服务器地址
					method : 'POST',
					sync:true,
					params:{t:t,type:cates,partId:partId,productId:productId,productCode:productCode,productName:productName},
					success : function(response,options){
						var result = Ext.util.JSON.decode(response.responseText);
						if(result.error==null){
							
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
		refresh();
	}
	
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
	
	Ext.getCmp('BussGrid2').syncSize();
	
	refresh2();
	deptTree.init();
	loadCates();
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

	function loadCates(){//加载分类
	     var cates = '';
		 var vUrl = '<c:url value="/dic.do?method=onGetSystemConfig"/>'+'&type=productBussCate';
			Ext.Ajax.request({
			   url:  vUrl,
			   success: function(response, options){
					  var responseArray = Ext.util.JSON.decode(response.responseText);     
					  var catesSelect = document.getElementById('cates');
					  for(var i=0; i< responseArray.length; i++){
						 var text = responseArray[i].name ;
		                 var value = responseArray[i].sid;
						 catesSelect.options.add(new Option(text,value));
					  }   
	  			},
				failure: function (response, options) {
				    checkAjaxStatus(response);
				}
			});
	}
</script>
