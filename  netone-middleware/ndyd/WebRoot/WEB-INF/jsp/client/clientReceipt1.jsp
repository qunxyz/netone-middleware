<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<title>产品管理</title>
	</head>
	<body>
		<div id='container' style="width: 100%; height: 100%;">
			<div id="queryDiv"
				style="display: none; margin-top: 5px; margin-left: 5px;">
				<form id="queryForm" action="">
					<input type="hidden" id="allocateCargoCode"
						name="allocateCargoCode" value="">
					<input type="hidden" id="ckNumber" name="ckNumber" value="">
					<input type="hidden" id="allocateCargoCode" name="operate" value="">

					<input type="hidden" name="beginTime" id="beginTime"
						style="width: 148px" class="Wdate"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="" />
					-
					<input type="hidden" name="endTime" d="endTime"
						style="width: 148px" class="Wdate"
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" value="" />
				</form>
			</div>
			<!-- 数据字典表格 -->
			<div id="listDiv">
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
  Ext.ns('AllcoateCargo.Data');
  AllcoateCargo.Data.AllcoateCargoGrid  =  Ext.extend(Ext.grid.GridPanel,{
  
		 initComponent: function() {
		 
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	});
		    
			var config = {
			    sm : cb,
			    frame:true,
			    border:false,
				store: new Ext.data.Store({
					url: "<c:url value='/storage/allocateCargo.do?method=onList' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [ 
						{name: 'allocateCargoId'},
						{name: 'allocateCargoCode'},
						{name: 'operate'},
						{name: 'operateTime'},
						{name: 'outStorageInfo.ckNumber'},
						{naem: 'outStorageInfo.linkman'},
						{name: 'clientInfo.clientName'},
						{name: 'clientInfo.telphone'},
						{name: 'clientInfo.mobile'},
						{name: 'clientInfo.email'},
						{name: 'clientInfo.servicestation'},
						{name: 'clientInfo.deliverGoodsAddress'},
						{name: 'clientInfo.clientLoginName'},
						{name: 'status'}
					   ])
				}),
				columns:[
				        index,
						cb,
						{header: "编号", width: 90, dataIndex: 'allocateCargoId', sortable: true,hidden:true},
						{header: "配货单号", width: 100, dataIndex: 'allocateCargoCode', sortable: true},
						{header: "出库单号", width: 80, dataIndex: 'outStorageInfo.ckNumber', sortable: true},
						{header: "客户编码", width: 100, dataIndex: 'clientInfo.clientLoginName', sortable: true},
						{header: "客户名称", width: 80, dataIndex: 'clientInfo.clientName', sortable: true},
						{header: "联系人", width: 80, dataIndex: 'clientInfo.linkman', sortable: true,hidden: true},
						{header: "联系电话", width: 100, dataIndex: 'clientInfo.telphone', sortable: true},
						{header: "手机", width: 100, dataIndex: 'clientInfo.mobile', sortable: true},
						{header: "电子邮件", width: 100, dataIndex: 'clientInfo.email', sortable: true},
						{header: "送达站", width: 100, dataIndex: 'clientInfo.servicestation', sortable: true},
						{header: "送货地址", width: 100, dataIndex: 'clientInfo.deliverGoodsAddress', sortable: true},
						{header: "制单人", width: 100, dataIndex: 'operate', sortable: true},
						{header: "制单时间", width: 80, dataIndex: 'operateTime', sortable: true},
						{header: "状态", width: 120, dataIndex: 'status', sortable: true,hidden: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var status = record.get('status');
								 if( status == '0' ){
									return '已锁定';
								 }else if(  status == '1'){
									return '未提交';
								 }else if (  status == '2' ){
									return '已提交';
								 }else {
									return '';
								 }
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
		     displayInfo:true,
		     pageSize:15,
		     displayMsg : '第 {0} 条到 {1} 条，一共 {2} 条',
			 emptyMsg : "没有记录"
		  });
	 
		  // call parent
		  AllcoateCargo.Data.AllcoateCargoGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var condition = {//取得HTML页面的查询条件
						allocateCargoCode: Ext.get('allocateCargoCode').getValue(),
						ckNumber: Ext.get('ckNumber').getValue(),
						operate: Ext.get('operate').getValue(),
						beginTime: Ext.get('beginTime').getValue(),
						endTime: Ext.get('endTime').getValue()
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
  Ext.reg('AllcoateCargoGrid',  AllcoateCargo.Data.AllcoateCargoGrid);//注册一个组件,注册成xtype以便能够延迟加载
  
  Ext.ns('AllcoateCargo.Layout');
  
  AllcoateCargo.Layout.Viewport =  Ext.extend(Ext.Viewport, {
  	 
       initComponent: function(){
       	   var clientHeight = 0;
		   if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
			    clientHeight = document.documentElement.clientHeight;
		   } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
			    clientHeight = document.body.clientHeight;
	       }
       
	       var config = {
				 collapsible:true,
				 renderTo :'container',
				 autoWidth:true,
				 border:false,
				 items:[{
					 xtype:'toolbar',
					 items:[
					 /*
					  new Ext.Toolbar.Button({
						  text:'新增',
						  id:'ext_b_add',
						  iconCls:"addIcon"
					  }),	
					  new Ext.Toolbar.Button({
						   text:'修改',
						   id:'ext_b_change',
						   iconCls:"editIcon"
					  }),
					  new Ext.Toolbar.Button({
						  text:'删除',
						  id: 'ext_b_delete',
						  iconCls: "deleteIcon"
					  }),*/
					  	  new Ext.Toolbar.Button({
						  text:'收货确认',
						  id: 'ext_b_look',
						  iconCls: "editIcon"
					  })/*,
					  	  new Ext.Toolbar.Button({
						  text:'查询',
						  id: 'ext_b_query',
						  iconCls: "viewIcon"
					  })*/
                     ]
                	},{
					    id:"AllcoateCargoGrid",
					    xtype:"AllcoateCargoGrid",
			            border:false,
			            hideBorders:true,
			            height : clientHeight-25,
					 	autoScroll:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			AllcoateCargo.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
   Ext.onReady(function(){//页面加载时候触发事件
	var viewport =  new AllcoateCargo.Layout.Viewport();
	var AllcoateCargoGrid2 = viewport.findById('AllcoateCargoGrid');//Grid

	//--------------------------页面数据加载方法定义-------------------------
	
	refresh = function(){//刷新
		var start = 0 ;//当前分页记录开始位置
		var AllcoateCargoGrid3  = viewport.findById('AllcoateCargoGrid');//Grid
		if (AllcoateCargoGrid3.store.lastOptions!=null){
			start = AllcoateCargoGrid3.store.lastOptions.params.start;
		} else {
			start = 0;
		}
    	AllcoateCargoGrid3.store.load({params:{start:start, limit:15}});//刷新当前页面
	};
	
	//--------------------------初始化加载--------------------------
	refresh();//初始化加载数据
	Ext.get('queryDiv').dom.style.display='block';//显示自定义页面数据
	
	
	//------------------------------------初始化按钮事件开始--------------------------------
	/*Ext.EventManager.addListener(Ext.get('ext_b_add'), 'click', function(){//新增
		openWinCenter('新增','<c:url value='/storage/allocateCargo.do?method=onEditView' />', 600,400,true);
	});
	
	Ext.EventManager.addListener(Ext.get('ext_b_change'), 'click', function(){//修改
			 var selectionSet = AllcoateCargoGrid2.getSelectionModel().getSelections(); 
			 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
				Ext.MessageBox.alert('系统提示','请选择一条记录进行修改!');
				return ;
			 }	
             var guid = selectionSet[0].data.allocateCargoId;
             var url = '<c:url value="/storage/allocateCargo.do?method=onEditView"/>'+'&allocateCargoId=' + guid;
             openWinCenter('修改',url, 600,400,true);
	});*/
	
	 Ext.EventManager.addListener(Ext.get('ext_b_look'), 'click', function(){//查看配货单明细
			 var selectionSet = AllcoateCargoGrid2.getSelectionModel().getSelections(); 
			 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
				Ext.MessageBox.alert('系统提示','请选择一条记录进行回执确认!');
				return ;
			 }	
             var guid = selectionSet[0].data.allocateCargoId;
             var url = '<c:url value="/client/client.do?method=onClientReceiptForm"/>'+'&allocateCargoId=' + guid;
             openWinCenter('查看配货单明细',url, 910,600,true);
	});
	/*
	Ext.EventManager.addListener(Ext.get('ext_b_delete'), 'click', function(){//删除数据
	      var allcoateCargoIdStr =  getAllcoateCargoId();
	      var num = allcoateCargoIdStr.length;
	      if(num >= 1){
	      	  Ext.MessageBox.confirm("系统提示",'您确定要删除所选产品数据吗？',function(btnId){
					if(btnId == 'yes'){
					var str = "";
				    for(var i= 0 ; i < num ; i ++){
				        str = str + allcoateCargoIdStr[i]+",";
				    }
				    var str = Ext.util.Format.substr(str,0,str.length - 1);
						Ext.Ajax.request({//AJAX删除
								url : '<c:url value="/storage/allocateCargo.do?method=onDelete" />',
								params : {allocateCargoIdStr : str},
								method : 'POST',
								success : function(response,options){
									Ext.Msg.alert('系统提示','删除数据成功');
									refresh();
								},
								failure : function(response,options){
									checkAjaxStatus(response);
									Ext.Msg.alert('系统提示','删除数据失败');
								}
						});
					}
			 });
		 }
    });
    
    Ext.EventManager.addListener(Ext.get('ext_b_query'), 'click', function(){//新增
		openWinCenter('查询页面','<c:url value='/storage/allocateCargo.do?method=onQueryMainView' />', 600,400,true);
	});
	
 
    
    //----------公共方法定义(按钮,私有事件)-----------------
	function getAllcoateCargoId(){//取得所选数据字典Id
			var recs = AllcoateCargoGrid2.getSelectionModel().getSelections();
			var list = [];
			if(recs.length == 0){
				Ext.Msg.alert('系统提示','请选择要进行操作的产品信息！');
			}else{
				for(var i = 0 ; i < recs.length ; i++){
					var rec = recs[i];					
					list.push(rec.get('allocateCargoId'));
				}
			}
			return list;
	}
    */
  });


</script>
