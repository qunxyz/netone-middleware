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
		<title>产品销售指标管理</title>
	</head>
	<body>
		<div id='container' style="width: 100%; height: 100%;">
			<div id="queryDiv"
				style="display: none; margin-top: 5px; margin-left: 5px; height: 0px">
				<form id="queryForm" action="">
					<input type="hidden" id="categoriesId"
						name="categoriesId" value="${param.categoriesId}">
				</form>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
  Ext.ns('ProductCategroiesSellTarget.Data');
  ProductCategroiesSellTarget.Data.ProductCategroiesSellTargetGrid  =  Ext.extend(Ext.grid.GridPanel,{
		 initComponent: function() {
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	});
			var config = {
			    sm : cb,
			    //frame:true,
			    border:false,
				store: new Ext.data.Store({
					url: "<c:url value='/client/sellTarget.do?method=onListCategoriesSellTarget' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   },[
						{name: 'id'},
						{name: 'targetScale1'},
						{name: 'targetScale2'},
						{name: 'targetScale3'},
						{name: 'targetScale4'},
						{name: 'targetScale5'},
						{name: 'targetScale6'},
						{name: 'targetScale7'},
						{name: 'targetScale8'},
						{name: 'targetScale9'},
						{name: 'targetScale10'},
						{name: 'targetScale11'},
						{name: 'targetScale12'},
						{name: 'year'},
						{name: 'categoriesId'},
						{name: 'operate'},
						{name: 'operateTime'}
					   ])
				}),
				columns:[
				        index,
						cb,
						{header: "一月", width: 100, dataIndex: 'targetScale1', sortable: true ,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var january = record.get('targetScale1');
									return january+'%';
							}
						},
						{header: "二月", width: 100, dataIndex: 'targetScale2', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var february = record.get('targetScale2');
									return february+'%';
							}
						},
						{header: "三月", width: 90, dataIndex: 'targetScale3', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var march = record.get('targetScale3');
									return march+'%';
							}
						},
						{header: "四月", width: 90, dataIndex: 'targetScale4', sortable: true
						,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var april = record.get('targetScale4');
									return april+'%';
							}
						},
						{header: "五月", width: 100, dataIndex: 'targetScale5', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var may = record.get('targetScale5');
									return may+'%';
							}
						},
						{header: "六月", width: 100, dataIndex: 'targetScale6', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var june = record.get('targetScale6');
									return june+'%';
							}},
						{header: "七月", width: 100, dataIndex: 'targetScale7', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var july = record.get('targetScale7');
									return july+'%';
							}},
						{header: "八月", width: 100, dataIndex: 'targetScale8', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var august = record.get('targetScale8');
									return august+'%';
							}},
						{header: "九月", width: 100, dataIndex: 'targetScale9', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var september = record.get('targetScale9');
									return september+'%';
							}},
						{header: "十月", width: 100, dataIndex: 'targetScale10', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var october = record.get('targetScale10');
									return october+'%';
							}},
						{header: "十一月", width: 100, dataIndex: 'targetScale11', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var november = record.get('targetScale11');
									return november+'%';
							}},
						{header: "十二月", width: 100, dataIndex: 'targetScale12', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var december = record.get('targetScale12');
									return december+'%';
							}},
						{header: "年限", width: 100, dataIndex: 'year', sortable: true},
						{header: "产品分类编号", width: 100, dataIndex: 'categoriesId', sortable: true,hidden:true},
						{header: "操作者", width: 100, dataIndex: 'operate', sortable: true,hidden:true},
						{header: "操作时间", width: 100, dataIndex: 'operateTime', sortable: true}
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
		  ProductCategroiesSellTarget.Data.ProductCategroiesSellTargetGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var condition = {
				   categoriesId: Ext.get('categoriesId').getValue()
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
  Ext.reg('ProductCategroiesSellTargetGrid',  ProductCategroiesSellTarget.Data.ProductCategroiesSellTargetGrid);//注册一个组件,注册成xtype以便能够延迟加载
  
  Ext.ns('ProductCategroiesSellTarget.Layout');
  
  ProductCategroiesSellTarget.Layout.Viewport =  Ext.extend(Ext.Viewport, {
  	 
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
				 layout : "border",
				 items:[{
					 xtype:'toolbar',
					 region : "north",
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
                	},/*{
			            xtype:'panel',
			            height:10,
			            border:false,
			            contentEl :'queryDiv',
				        bodyStyle:'background-color:#FFFFFF',
				        style:"padding:5px"
					  },*/{
					    id:"ProductCategroiesSellTargetGrid",
					    xtype:"ProductCategroiesSellTargetGrid",
					    region : "center",
			            border:false,
			            hideBorders:true,
			            height : clientHeight,
					 	autoScroll:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			ProductCategroiesSellTarget.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
   Ext.onReady(function(){//页面加载时候触发事件
	var viewport =  new ProductCategroiesSellTarget.Layout.Viewport();
	var ProductCategroiesSellTargetGrid2 = viewport.findById('ProductCategroiesSellTargetGrid');//Grid

	refresh = function(){//刷新
		var start = 0 ;//当前分页记录开始位置
		var ProductCategroiesSellTargetGrid3  = viewport.findById('ProductCategroiesSellTargetGrid');//Grid
		if (ProductCategroiesSellTargetGrid3.store.lastOptions!=null){
			start = ProductCategroiesSellTargetGrid3.store.lastOptions.params.start;
		} else {
			start = 0;
		}
    	ProductCategroiesSellTargetGrid3.store.load({params:{start:start, limit:15}});//刷新当前页面
	};
	
	
	//--------------------------初始化加载--------------------------
	refresh();//初始化加载数据
	Ext.get('queryDiv').dom.style.display='block';//显示自定义页面数据

  });
	//------------------------------------初始化按钮事件开始--------------------------------
	function _add(){
	   openModalDialogWindow('新增','<c:url value='/client/sellTarget.do?method=onEditCategoriesSellTargetView' />&categoriesId=${param.categoriesId}', 500,200);	    			
	}
	
	function _edit(){//修改
			 var selectionSet = Ext.getCmp('ProductCategroiesSellTargetGrid').getSelectionModel().getSelections(); 
			 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
				Ext.MessageBox.alert('系统提示','请选择一条记录进行修改!');
				return ;
			 }	
			
               var id = selectionSet[0].data.id;
               var year = selectionSet[0].data.year;
               var url = '<c:url value='/client/sellTarget.do?method=onEditCategoriesSellTargetView' />' +  '&id=' + id+'&categoriesId=${param.categoriesId}'+'&year='+year;
             openModalDialogWindow('修改数据',url, 500,200);
	}
	function _delete(){//删除数据
			var recs = Ext.getCmp('ProductCategroiesSellTargetGrid').getSelectionModel().getSelections();
			var list = [];
			if(recs.length == 0){
				Ext.Msg.alert('系统提示','请选择要进行操作的产品分类的指标信息！');
			} else if(recs.length >= 1){
				Ext.MessageBox.confirm("系统提示",'您确定要删除所选产品分类指标吗？',function(btnId){
					if(btnId == 'yes'){
						var jsonStr = '';
						var split = '';
						for(var i = 0 ; i < recs.length ; i++){
							var rec = recs[i];					
							var jsonRecord = {
								categoriesId : rec.get('categoriesId'),
								year : rec.get('year')
							}
					        jsonStr += split + Ext.util.JSON.encode(jsonRecord);
							split = ',';
						}
						jsonStr = '['+jsonStr+']';
						Ext.Ajax.request({//AJAX删除
								url : '<c:url value='/client/sellTarget.do?method=onDeleteCategoriesSellTarget' />',
								params : {deleteList : jsonStr},
								method : 'POST',
								success : function(response,options){
									var result = Ext.util.JSON.decode(response.responseText);
									if(result.success){
										Ext.ux.Toast.msg('', '删除该产品分类的指标成功!');
										refresh();
								    }else{
										Ext.Msg.alert('系统提示','删除失败!');
								    }
								},
								failure : function(response,options){
									checkAjaxStatus(response);
									Ext.Msg.alert('系统提示','删除数据信息失败!');
								}
						});
					}
			 });
		 }
    }
    //----------公共方法定义(按钮,私有事件)-----------------
	function getproductCategoriesSellTargetId(){//取得所选数Id
			var recs = Ext.getCmp('ProductCategroiesSellTargetGrid').getSelectionModel().getSelections();
			var list = [];
			if(recs.length == 0){
				Ext.Msg.alert('系统提示','请选择要进行操作的产品分类的指标信息！');
			}else{
				for(var i = 0 ; i < recs.length ; i++){
					var rec = recs[i];					
					list.push(rec.get('productCategoriesSellTargetId'));
				}
			}
			return list;
	}
</script>
