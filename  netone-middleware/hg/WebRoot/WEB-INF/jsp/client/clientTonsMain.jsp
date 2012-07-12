<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-easyui.jsp"></jsp:include>
		<title>客户吨数配置</title>
	</head>
	<body>
	<input type="hidden" name="levelCode" id="levelCode" /><!-- 级别流水号 -->
	<input type="hidden" name="levelName" id="levelName" /><!-- 级别名 -->
	<div id="queryDiv">
		客户编号:   
		<input type="text" id="clientCode" name="clientCode" value="" onkeyup="onKeyUpEvent(event)"/>
		
		<input type="button" class="btn" value="查询" onclick="refresh();"  />
	</div>
	<div id="dd" style="padding:0px;display: none;">
		<iframe id="uiMainContent" src="" width="100%" frameborder="0" height="100%">
		loading...
		</iframe>
	</div>
	</body>
</html>
<script type="text/javascript">
Ext.QuickTips.init();

Ext.ns('ClientTons.Data');
ClientTons.Data.ClientTonsGrid  =  Ext.extend(Ext.grid.EditorGridPanel,{

 initComponent: function() {
 
    var index= new Ext.grid.RowNumberer();//
    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
		singleSelect: false
   	});
	var config = {
	    sm : cb,
	    border:false,
	    clicksToEdit:1,
		enableHdMenu:false,
		store: new Ext.data.Store({
			url: "<c:url value='/client/client.do?method=onFindClientOfTons' />",//JSON数据
			reader: new Ext.data.JsonReader(
			   {totalProperty: 'total',
			    root: 'rows'
			   }, [ 
				{name: 'clientLoginName'},
				{name: 'clientName'},
				{name: 'tons'}
			   ])
		}),
		columns:[
		        index,
				//cb,
				{header: "客户编号", width: 60, dataIndex: 'clientLoginName', sortable: true},
				{header: "客户名称", width: 80, dataIndex: 'clientName', sortable: true},
				{header: "吨数", width: 80, dataIndex: 'tons', sortable: true,
							editor : new Ext.form.TextField({
					   		   allowNegative:true,
							   name: 'text',
							   allowBlank:false,
							   maxLengthText:'输入数字,小数点后只能保留三位小数!',
							   regexText: '输入数字,小数点后只能保留三位小数!',
							   regex:/^[+]?[0-9]*\.{0,1}[0-9]{0,3}$/,
							   blankText:'输入数字,小数点后只能保留三位小数!'
							})
				}],
		viewConfig:{forceFit:true},
		loadMask:true	
   }
   
   // apply config
  Ext.apply(this, Ext.apply(this.initialConfig, config));

  /*this.bbar = new Ext.PagingToolbar({
	 store:this.store,
     displayInfo:true,
     pageSize:25,
     displayMsg : '第 {0} 条到 {1} 条，一共 {2} 条',
	 emptyMsg : "没有记录"
  });*/

  // call parent
  ClientTons.Data.ClientTonsGrid.superclass.initComponent.apply(this, arguments);

     this.store.on('beforeload', function(store,options){
		  var condition = {//取得HTML页面的查询条件
		  		levelCode : Ext.get('levelCode').dom.value,
		  		clientCode : Ext.get('clientCode').dom.value
		  };
		
		  options=options||{};
		  options.params = options.params||{}; 
		  options.params.condition = Ext.util.JSON.encode(condition);
		  
		  return true;
  });
  
   // load the store at the latest possible moment
  this.on({
	 beforeshow : {scope:this, single:true, fn:function() {
		   this.store.load({params:{start:0, limit:25}});
	 }}
  });
 }
});
Ext.reg('ClientTonsGrid',  ClientTons.Data.ClientTonsGrid);//注册一个组件,注册成xtype以便能够延迟加载

Ext.ns('ClientTonsGrid.Layout');

ClientTonsGrid.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
   
   var treeDataUrl = "<c:url value='/client/client.do?method=onClientTonsLevelMain' />";
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
		 			animate :false,//去除动画
　　　				autoScroll:true,
					border : true, // 边框
					useArrows :true,
				    loader: new Ext.tree.TreeLoader({dataUrl: treeDataUrl}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:'root',text:'区域'}),
		       	 	tbar : new Ext.Toolbar([
						{text : '批量更新',iconCls:'editIcon',id:'ext_b_batchUpdate',tooltip :'选择客户，批量更新'},
						{text : '刷新',iconCls:'refreshIcon',id:'ext_b_refreshTree',tooltip :'刷新区域'}
						//,{text : '同步区域',iconCls:'copyIcon',id:'ext_b_sysArea',tooltip :'同步大区域'}
					]),
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != 'root'){//弹出下级菜单
						       Ext.get('levelCode').dom.value = node.attributes.id;
						       Ext.get('levelName').dom.value = node.attributes.text;
						       //refresh();
						   }else{
						       Ext.get('levelCode').dom.value = '';	 
						       Ext.get('levelName').dom.value = '区域';
						   }
						  // removeAll();
						  
						  if(node.attributes.text!='区域'){//点击文本显示下级
						  	Ext.get('levelCode').dom.value = node.attributes.id;
						  	refresh();
						  }
						 }
					}
              	},{
		            region:"center",
					//height:clientHeight,
					autoScroll:true,
					layout:"border", 
					items: [{
						xtype : "panel",
						region : "north",
						id : "north",
						frame: false,
						baseCls : 'x-plain',
						contentEl : 'queryDiv',// 加载本地资源
						style : "padding:10px"
					},{
						id:'ClientTonsGrid',
		            	xtype:'ClientTonsGrid',
		            	region : "center",
		            	autoScroll:true
					}]
			  }
		 	]
	}
	         
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	ClientTonsGrid.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});
//****** 触发事件
function refreshTree(){//刷新树
	Ext.getCmp('treepanel').root.reload();
	//Ext.getCmp('treepanel').expandAll();//树默认全部展开
}

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
function checkTreeIsSelected(){//检查树是否被选择
	var selNode = Ext.getCmp('treepanel').getSelectionModel().getSelectedNode();
	if (selNode!=null){
		if (selNode.id!='root'){
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}
function afterEdit(obj){//点击GRID的编辑事件
	var record=obj.record;
	var tons = record.get('tons');
	if (record.isModified('tons')){
		updateClientTons('tons',record);
	}
	record.cancelEdit(true);//取消编辑状态
}
function updateClientTons(type,record){//根据经销商编号更新相应的吨数
		var levelCode = document.getElementById('levelCode').value;
		var clientLoginName = record.get('clientLoginName');
		var tons = record.get('tons');
		/*
		var msgTip = Ext.MessageBox.show({
				title:'系统提示',
				width : 250,
				msg:'正在保存...'
 		});
		*/
    	var vUrl = '<c:url value="/client/client.do?method=onUpdateClientTons"/>';
		Ext.Ajax.request({
		   url:  vUrl,
		   params: {levelCode:levelCode,clientLoginName:clientLoginName,tons:tons,type:type},
		   success: function(response, options){
				 //msgTip.hide();
		   },
		   failure : function(response,options){
		   		//msgTip.hide();
		   		checkAjaxStatus(response);
		   		var result = Ext.util.JSON.decode(response.responseText);
		   		alert(result.tip);
		   }
		});
}

/**
* 批量更新吨数
* @param title 标题
*/
function onOpenWindow(title){//弹出操作界面事件
	var selNode = Ext.getCmp('treepanel').getSelectionModel().getSelectedNode();
	var validation = /^[+]?[0-9]*\.{0,1}[0-9]{0,3}$/;//验证格式【100###.001】
	var levelCode='',levelName='';
	if (selNode!=null){
		levelCode = selNode.id;
	}
	Ext.MessageBox.prompt(title, '吨数:', function(btn, text){
	 	 			if (btn == 'ok') {
	 	 				 var msgTip = Ext.MessageBox.show({
									title:'系统提示',
									width : 250,
									msg:'正在更新中，请稍后......'
					 	 });
					 	
					 	 if(!validation.exec(text)){
					 	 	Ext.MessageBox.alert("错误提示","输入格式有误！<br />格式：【1000.001】");
					 	 }else{
						     Ext.Ajax.request({
									url : '<c:url value="/client/client.do?method=onBatchUpdateClientTons"/>',//请求的服务器地址
									params: {levelCode:selNode.id,tons:text},
									success : function(response,options){
										msgTip.hide();
										var result = Ext.util.JSON.decode(response.responseText);
										//alert(result.tip);
										refresh();
									},
									failure : function(response,options){
										msgTip.hide();
										checkAjaxStatus(response);
								   		var result = Ext.util.JSON.decode(response.responseText);
								   		alert(result.tip);
									}
						　    });
						}
	 	 			}
	 	 	},this,false,levelName);
}

Ext.onReady(function(){
	
	var viewport =  new ClientTonsGrid.Layout.Viewport();
	var _clientTonsGrid  = viewport.findById('ClientTonsGrid');//Grid
	refresh = function(){//刷新
    	_clientTonsGrid.store.load({params:{start:0, limit:9999}});//刷新当前页面
    }
   
    //Ext.getCmp('treepanel').expandAll();//树默认全部展开
    Ext.getCmp('treepanel').getRootNode().select();
    Ext.EventManager.addListener(Ext.get('ext_b_refreshTree'), 'click', function(){//刷新客户级别树
    	refreshTree();
    });
    _clientTonsGrid.on("afteredit",afterEdit,this);//编辑完成事件
    
     Ext.EventManager.addListener(Ext.get('ext_b_batchUpdate'), 'click', function(){//批量更新
    	if(!checkTreeIsSelected()) {alert('请选择区域下的一个级别!');return;};
    	onOpenWindow('批量更新吨数');
    });

});



</script>
