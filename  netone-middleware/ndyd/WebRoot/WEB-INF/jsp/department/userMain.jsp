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
		<title>用户管理</title>
	</head>
	<body>
	     <input type="hidden" id="departmentId" name="departmentId">
	     <input type="hidden" id="departmentName" name="departmentName">
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
							url : "<c:url value='/department/user.do?method=onListUser' />", // JSON数据
							reader : new Ext.data.JsonReader({
										totalProperty : 'total',
										root : 'rows'
									}, [
										{name: 'userId'},
										{name: 'userCode'},
										{name: 'userName'},
										{name: 'departmentName'},
										{name: 'status'}
									])
						}),
				columns : [index,
					cb, 
					{header: "用户编码", align:'center',dataIndex: 'userCode'},
					{header: "用户名称", align:'center',dataIndex: 'userName'},
					{header: "隶属部门", align:'center', dataIndex: 'departmentName'},
					{header: "状态", align:'center', dataIndex: 'status'}
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
				   departmentId: Ext.get('departmentId').getValue(),
				   userCode:''
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
		       	 	root : new Ext.tree.AsyncTreeNode({id:nodeid,text:nodeid}),
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != nodeid){
						     Ext.get('departmentId').dom.value = node.attributes.departmentId;
						     Ext.get('departmentName').dom.value = node.attributes.departmentName;
						     refresh();
						   } else {//选择根结点
						     Ext.get('departmentId').dom.value = nodeid;
						     refresh();
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
								  new Ext.Toolbar.Button({
									  text:'新增',
									  id:'ext_b_add',
									  iconCls:"addIcon",
									  handler:_add
								  }),	
								  new Ext.Toolbar.Button({
									  text:'修改',
									  id:'ext_b_change',
									  iconCls:"editIcon",
									  handler:_change
								  }),
								  new Ext.Toolbar.Button({
									  text:'删除',
									  id: 'ext_b_delete',
									  iconCls: "deleteIcon",
									  handler:_delete
								  })
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
		var start = 0 ;//当前分页记录开始位置
		var BussGrid2  = viewport.findById('BussGrid');//Grid
		if (BussGrid2.store.lastOptions!=null){
			start = BussGrid2.store.lastOptions.params.start;
		} else {
			start = 0;
		}
    	BussGrid2.store.load({params:{start:start, limit:15}});//刷新当前页面
	};
	
	BussGrid.addListener('celldblclick', function(grid, rowIndex,
		columnIndex, e) {// 列表双击事件
		_change();
	});
	
});

function _add(){//新增
    var url = "<c:url value='/department/user.do?method=onEditUserView' />";
    openModalDialogWindow('新增',url,1200,600);
};

function _change(){//修改
	 var selectionSet = Ext.getCmp('BussGrid').getSelectionModel().getSelections(); 
	 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
		Ext.MessageBox.alert('系统提示','请选择一条记录进行修改!');
		return ;
	 }
	    var notice;
       if(Ext.get('mail').dom.checked&&Ext.get('msg').dom.checked){
    	   notice=3;
       }else if(Ext.get('mail').dom.checked){
		   notice=0;
	   }else if(Ext.get('msg').dom.checked){
    	   notice=1;
       }
     var userId=selectionSet[0].data.userId;
     var url = '<c:url value="/department/user.do?method=onEditUserView"/>' +'&userId='+userId+'notice='+notice;
     openModalDialogWindow('修改',url,600,300);
};
function _getId(){//取得所选数Id
	var recs = Ext.getCmp('BussGrid').getSelectionModel().getSelections();
	var list = [];
	if(recs.length == 0){
		Ext.Msg.alert('系统提示','请选择要进行操作的信息！');
	}else{
		for(var i = 0 ; i < recs.length ; i++){
			var rec = recs[i];
			list.push(rec.get('userId'));
		}
	}
	return list;
}

function _getCode(){//取得所选数Id
	var recs = Ext.getCmp('BussGrid').getSelectionModel().getSelections();
	var list = [];
	if(recs.length == 0){
		Ext.Msg.alert('系统提示','请选择要进行操作的信息！');
	}else{
		for(var i = 0 ; i < recs.length ; i++){
			var rec = recs[i];
			list.push(rec.get('userCode'));
		}
	}
	return list;
}

function _delete(){//删除数据
     var arr = _getId();
     var arr2 = _getCode();
     var num = arr.length;
     if(num >= 1){
     	  Ext.MessageBox.confirm("系统提示",'您确定要删除所选择数据吗？',function(btnId){
			if(btnId == 'yes'){
				var str1="";
			    for(var i= 0 ; i < num ; i ++){
			        str1 = str1 + arr[i]+",";
			    }
			    str1 = Ext.util.Format.substr(str1,0,str1.length - 1);
			    var str2="";
			    for(var i= 0 ; i < num ; i ++){
			        str2 = str2 + arr2[i]+",";
			    }
			    str2 = Ext.util.Format.substr(str2,0,str2.length - 1);
				Ext.Ajax.request({//AJAX删除
						url : '<c:url value='/department/user.do?method=onDeleteUser' />',
						params : {userId : str1,userCode : str2},
						method : 'POST',
						success : function(response,options){
							var result = Ext.util.JSON.decode(response.responseText);
							if(result.error==null){
								Ext.ux.Toast.msg("", result.tip);
							}else{
								Ext.MessageBox.alert('提示',result.tip);
							}
						},
						failure : function(response,options){
							checkAjaxStatus(response);
							Ext.Msg.alert('系统提示','删除数据信息失败');
						}
				});
			}
	 });
 	}
}
    
</script>
