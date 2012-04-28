<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
        <jsp:include page="../common/metaJQuery-ui-dialog.jsp"/>
		<title>客户销售指标管理</title>
	</head>
	<body>
		<input type="hidden" id="id" name="id" value="${param.id}">
	</body>
</html>
<script type="text/javascript">
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
			    //autoWidth:true,
				store: new Ext.data.Store({
					url: "<c:url value='/client/sellTarget.do?method=onListSellTarget' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   },[
					    <c:forEach items="${productCategoriesInfo}" var="list">
						{name: '${list.categoriesId}'},
						</c:forEach>
						{name: 'sellYear'},
						{name: 'departmentId'},
						{name: 'departmentCode'},
						{name: 'departmentName'}
					   ])
				}),
				columns:[
				        index,
						cb,
						{header: "部门/公司编号", dataIndex: 'departmentCode', sortable: true},
						{header: "部门/公司名称", dataIndex: 'departmentName', sortable: true},
						{header: "销售指标年限", dataIndex: 'sellYear', sortable: true},						
						<c:forEach items="${productCategoriesInfo}" var="list1">
						{header: "${list1.categoriesName}",  dataIndex: '${list1.categoriesId}', sortable: true},
						</c:forEach>
						{header: "", dataIndex: 'blank', sortable: true,hidden : true}
						],
				//viewConfig:{forceFit:true},
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
				   id: Ext.get('id').getValue(),
				   sellYear:''
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
       
	       var config = {
				 collapsible:true,
				 border:false,
				 layout:'border',
				 items:[{
			            region:"north",
			            xtype:'toolbar',
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
							   handler:_edit
						  }),
						  new Ext.Toolbar.Button({
							  text:'删除',
							  id: 'ext_b_delete',
							  iconCls: "deleteIcon",
							  handler:_delete
						  })
	                     ]
					  },{
					    id:"BussGrid",
					    xtype:"BussGrid",
					    region:"center",
			            border:false,
			            hideBorders:true,
			            height : clientHeight-40,
					 	autoScroll:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			Buss.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
   Ext.onReady(function(){//页面加载时候触发事件
		var viewport =  new Buss.Layout.Viewport();
		var BussGrid2 = viewport.findById('BussGrid');//Grid
	
		refresh = function(){//刷新
			var start = 0 ;//当前分页记录开始位置
			var BussGrid3  = viewport.findById('BussGrid');//Grid
			if (BussGrid3.store.lastOptions!=null){
				start = BussGrid3.store.lastOptions.params.start;
			} else {
				start = 0;
			}
	    	Ext.getCmp('BussGrid').store.load({params:{start:0, limit:15}});//刷新当前页面
		};
		
		//--------------------------初始化加载--------------------------
		refresh();//初始化加载数据
		
		//------------------------------------初始化按钮事件开始--------------------------------
	
		Ext.getCmp('BussGrid').addListener('celldblclick', function(grid, rowIndex,
			columnIndex, e) {// 列表双击事件--查看明细
				_edit();
		});
	
    });
    	//------------------------------------初始化按钮事件--------------------------------
      
     function _add(){
     	var id = Ext.get('id').getValue();
	    openModalDialogWindow('新增','<c:url value='/client/sellTarget.do?method=onEditSellTargetView&s=add' />'+'&departmentId='+id, 650,300,'yes')
     }
     function _edit(){
       	 var selectionSet = Ext.getCmp('BussGrid').getSelectionModel().getSelections(); 
		 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
			Ext.MessageBox.alert('系统提示','请选择一条记录进行修改!');
			return ;
		 }
         var sellYear=selectionSet[0].data.sellYear;
         var departmentId=selectionSet[0].data.departmentId;
         var url = '<c:url value="/client/sellTarget.do?method=onEditSellTargetView"/>' +'&departmentId='+departmentId+'&sellYear='+sellYear;
         openModalDialogWindow('修改',url,650,300,'yes');
       }
       function _delete(){
       	  var arr = getId();
	      var num = arr[0].length;
   	      if(num >= 1){
	      	  Ext.MessageBox.confirm("系统提示",'您确定要删除所客户销售指标数据吗？',function(btnId){
					if(btnId == 'yes'){
					var str1="",str2="";
				    for(var i= 0 ; i < num ; i ++){
				        str1 = str1 + arr[0][i]+",";
				        str2 = str2 + arr[1][i]+",";
				    }
				    str1 = Ext.util.Format.substr(str1,0,str1.length - 1);
				    str2 = Ext.util.Format.substr(str2,0,str2.length - 1);
						Ext.Ajax.request({//AJAX删除
								url : '<c:url value='/client/sellTarget.do?method=onDeleteSellTarget' />',
								params : {sellYear : str1,departmentId : str2},
								method : 'POST',
								success : function(response,options){
									var result = Ext.util.JSON.decode(response.responseText);
									refresh();
									Ext.ux.Toast.msg("", result.tip);
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
    //----------公共方法定义(按钮,私有事件)-----------------
	function getId(){//取得所选数Id
			var recs = Ext.getCmp('BussGrid').getSelectionModel().getSelections();
			var list = [],list2 = [];
			if(recs.length == 0){
				Ext.Msg.alert('系统提示','请选择要进行操作的信息！');
			}else{
				for(var i = 0 ; i < recs.length ; i++){
					var rec = recs[i];
					list.push(rec.get('sellYear'));
					list2.push(rec.get('departmentId'));
				}
			}
			var arr = new Array();
			arr[0]=list;
			arr[1]=list2;
			return arr;
	  }

</script>