<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>客户销售指标管理</title>
	</head>
	<body>
		<form id="form1" method="post">
		<input type="hidden" id="clientLoginName" name="clientLoginName" value="<%=new String(request.getParameter("clientLoginName").getBytes("ISO-8859-1"), "GBK") %>">
		</form>
	</body>
</html>
<script type="text/javascript">
  Ext.ns('SellTarget.Data');
  SellTarget.Data.SellTargetGrid  =  Ext.extend(Ext.grid.GridPanel,{
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
					url: "<c:url value='/client/sellTargetNew.do?method=onList' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   },[
					    <c:forEach items="${productCategoriesInfo}" var="list">
						{name: '${list.categoriesId}'},
						</c:forEach>
						{name: 'sellYear'},
						{name: 'clientLoginName'}
					   ])
				}),
				columns:[
				        index,
						cb,
						<c:forEach items="${productCategoriesInfo}" var="list1">
						{header: "${list1.categoriesName}",  dataIndex: '${list1.categoriesId}', sortable: true},
						</c:forEach>
						{header: "客户编号", width: 100, dataIndex: 'clientLoginName', sortable: true},
						{header: "销售指标年限", width: 100, dataIndex: 'sellYear', sortable: true}
						],
				viewConfig:{forceFit:true},
				loadMask:true	
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
	 
		  this.bbar = new Ext.PagingToolbar({
			 store:this.store,
		     displayInfo:false,
		     pageSize:999,
		     displayMsg : '第 {0} 条到 {1} 条，一共 {2} 条',
			 emptyMsg : "没有记录"
		  });
	 
		  // call parent
		  SellTarget.Data.SellTargetGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var condition = {
				   clientLoginName: Ext.get('clientLoginName').getValue()
				  }; 
				  options=options||{}; 
				  options.params = options.params||{}; 
				  options.params.condition = Ext.util.JSON.encode(condition);
				  return true;
		  });
		   // load the store at the latest possible moment
		  this.on({
			 beforeshow : {scope:this, single:true, fn:function() {
				   this.store.load({params:{start:0, limit:999}});
			 }}
		  });
	  }
  });
  Ext.reg('SellTargetGrid',  SellTarget.Data.SellTargetGrid);//注册一个组件,注册成xtype以便能够延迟加载
  
  Ext.ns('SellTarget.Layout');
  
  SellTarget.Layout.Viewport =  Ext.extend(Ext.Viewport, {
  	 
       initComponent: function(){
       	   var clientHeight = 0;
		   if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
			    clientHeight = document.documentElement.clientHeight;
		   } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
			    clientHeight = document.body.clientHeight;
	       }
       
	       var config = {
				 collapsible:true,
				 //autoWidth:true,
				 border:false,
				 layout:'border',
				 items:[{
			            region:"north",
			            xtype:'toolbar',
						 items:[
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
						  })
	                     ]
					  },{
					    id:"SellTargetGrid",
					    xtype:"SellTargetGrid",
					    region:"center",
			            border:false,
			            hideBorders:true,
			            height : clientHeight-40,
					 	autoScroll:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			SellTarget.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
   Ext.onReady(function(){//页面加载时候触发事件
	var viewport =  new SellTarget.Layout.Viewport();
	var SellTargetGrid2 = viewport.findById('SellTargetGrid');//Grid

	refresh = function(){//刷新
		var start = 0 ;//当前分页记录开始位置
		var SellTargetGrid3  = viewport.findById('SellTargetGrid');//Grid
		if (SellTargetGrid3.store.lastOptions!=null){
			start = SellTargetGrid3.store.lastOptions.params.start;
		} else {
			start = 0;
		}
    	SellTargetGrid3.store.load({params:{start:start, limit:15}});//刷新当前页面
	};
	
	//--------------------------初始化加载--------------------------
	refresh();//初始化加载数据
	
	//------------------------------------初始化按钮事件开始--------------------------------
	Ext.EventManager.addListener(Ext.get('ext_b_add'), 'click', function(){//新增
	    var name=document.getElementById("clientLoginName").value;
	    openWinCenter('新增','<c:url value='/client/sellTargetNew.do?method=onAddView' />'+'&clientLoginName='+name+"&flag=add&sellYear=", 700,600,true)
	    			  /*   Ext.Ajax.request({
											url :"<c:url value='/client/sellTarget.do?method=onChectDo' />",//请求的服务器地址
											method : 'POST',
											params : {clientLoginName : name},
											success : function(response,options){
														var result = Ext.util.JSON.decode(response.responseText);
														//Ext.MessageBox.alert('提示',result.tip);
														if(result.tip !='already'){
																openWinCenter('新增','<c:url value='/client/sellTarget.do?method=onEditView' />'+'&clientLoginName='+name, 700,500,true);
														}else{
																Ext.MessageBox.alert('提示','该客户今年的销售指标已存在');
														}
													},
											failure : function(response,options){
													checkAjaxStatus(response);
														var result = Ext.util.JSON.decode(response.responseText);
														Ext.MessageBox.alert('提示',"新增请求失败");
													}
											　});*/
});
	
	SellTargetGrid2.addListener('celldblclick', function(grid, rowIndex,
		columnIndex, e) {// 列表双击事件--查看明细
			var selectionSet = grid.getSelectionModel().getSelections();
            var guid = selectionSet[0].data.sellTargetId;
            var name1=document.getElementById("clientLoginName").value;
            var sellYear=selectionSet[0].data.sellYear;
            var url = '<c:url value="/client/sellTargetNew.do?method=onEditView"/>' +  '&sellTargetId=' + guid+'&clientLoginName='+name1+'&sellYear='+sellYear+'&flag=edit';
            openWinCenter('修改数据',url, 700,600,true);
		});
	
	Ext.EventManager.addListener(Ext.get('ext_b_change'), 'click', function(){//修改
			 var selectionSet = SellTargetGrid2.getSelectionModel().getSelections(); 
			 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
				Ext.MessageBox.alert('系统提示','请选择一条记录进行修改!');
				return ;
			 }	
			
              var guid = selectionSet[0].data.sellTargetId;
              var name1=document.getElementById("clientLoginName").value;
              var sellYear=selectionSet[0].data.sellYear;
              var url = '<c:url value="/client/sellTargetNew.do?method=onEditView"/>' +  '&sellTargetId=' + guid+'&clientLoginName='+name1+'&sellYear='+sellYear+'&flag=edit';
              openWinCenter('修改数据',url, 700,600,true);
	});
	Ext.EventManager.addListener(Ext.get('ext_b_delete'), 'click', function(){//删除数据
	      var  sellYearStr = getSellYear();
	      var num = sellYearStr.length;
	      
	      if(num >= 1){
	      	  Ext.MessageBox.confirm("系统提示",'您确定要删除所客户销售指标数据吗？',function(btnId){
					if(btnId == 'yes'){
					var str1="";
				    for(var i= 0 ; i < num ; i ++){
				        str1 = str1 + sellYearStr[i]+",";
				    }
				    var str1 = Ext.util.Format.substr(str1,0,str1.length - 1);
				
						Ext.Ajax.request({//AJAX删除
								url : '<c:url value='/client/sellTargetNew.do?method=onDelete' />',
								form:'form1',
								params : {sellYearStr : str1},
								method : 'POST',
								success : function(response,options){
									var result = Ext.util.JSON.decode(response.responseText);
									if(result.success){
										window.location.reload();
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
    });
    //----------公共方法定义(按钮,私有事件)-----------------
	 function getSellYear(){//取得所选数Id
			var recs = SellTargetGrid2.getSelectionModel().getSelections();
			var list = [];
			if(recs.length == 0){
				Ext.Msg.alert('系统提示','请选择要进行操作的客户销售指标信息！');
			}else{
				for(var i = 0 ; i < recs.length ; i++){
					var rec = recs[i];					
					list.push(rec.get('sellYear'));
				}
			}
			return list;
	}
  });

</script>
