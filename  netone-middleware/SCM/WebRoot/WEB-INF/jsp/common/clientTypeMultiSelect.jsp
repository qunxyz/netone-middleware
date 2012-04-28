<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>客户类型选择</title>
	</head>
	<body>
		<div id='container' style="width: 100%; height: 100%;">
			<div id="listDiv">
			</div>
		</div>
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
				store: new Ext.data.Store({
					url: '<c:url value="/products/systemConfig.do?method=onList"/>', //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [
						 {name: 'sid'},
    					 {name: 'name'},
    					 {name: 'operate'},
    					 {name: 'operateTime',
    					    type : 'date',
							mapping : 'operateTime.time',
							dateFormat : 'time'
    					  }
					   ])
				}),
				columns:[
				        index,
						cb,
						{header: "编号",width:100,dataIndex: 'sid',sortable: true,hidden:true},
    					{header: "名称",width:100,dataIndex: 'name',sortable: true},
    					{header: "操作者",width:100,dataIndex: 'operate',sortable: true},
    					{header: "操作时间",width:100,dataIndex: 'operateTime',sortable: true,renderer : Ext.util.Format.dateRenderer('Y-m-d')}
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
		  Buss.Data.BussGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var condition = {//取得HTML页面的查询条件
				      type: '${param.type}'
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
  Ext.reg('BussGrid', Buss.Data.BussGrid);//注册一个组件,注册成xtype以便能够延迟加载
  
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
				 renderTo :'container',
				 autoWidth:true,
				 border:false,
				 items:[{
					 xtype:'toolbar',
					 items:[
					 new Ext.Toolbar.Button({
						  text:'确认',
						  id:'ext_b_add',
						  iconCls:"addIcon"
					  }),
					  new Ext.Toolbar.Button({
									   text:'退出',
									   id:'ext_b_change',
									   iconCls:"exitIcon",
									   handler: function(){window.close();}
					  })
                	]},{
					    id:"BussGrid",
					    xtype:"BussGrid",
			            border:false,
			            hideBorders:true,
			            height : clientHeight-30,
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

	//--------------------------页面数据加载方法定义-------------------------
	
	 
	refresh = function(){//刷新
		var start = 0 ;//当前分页记录开始位置
		var BussGrid3  = viewport.findById('BussGrid');//Grid
		if (BussGrid3.store.lastOptions!=null){
			start = BussGrid3.store.lastOptions.params.start;
		} else {
			start = 0;
		}
    	BussGrid3.store.load({params:{start:start, limit:15}});//刷新当前页面
	};
	
	//--------------------------初始化加载--------------------------
	refresh();//初始化加载数据
	
	//------------------------------------初始化按钮事件开始--------------------------------
    	Ext.EventManager.addListener(Ext.get('ext_b_add'), 'click', function(){//确认
		var systemConfectCodeStr = '';//配制信息编号
   		var systemConfectNameStr = '';//配制信息名称
  	  	var recs = BussGrid2.getSelectionModel().getSelections();
		if(recs.length == 0){
			window.close();
		}else{
			var oDom = opener.document;
			var inputId="";
			var inputName="";
			var type='${param.type}';
			if(type=='clientType'){
				inputId = "clientType";
				inputName="clientTypeName";
			}else if(type='marketType'){
				inputId = "marketType";
				inputName="marketTypeName";
			}
			var systemConfectCode = oDom.getElementById(inputId);
			var systemConfectName = oDom.getElementById(inputName);
			if(systemConfectCode!=null){
				for(var i=0;i<recs.length;i++){
				   if(i==recs.length-1){
				   		systemConfectCodeStr =systemConfectCodeStr+recs[i].get('sid');
				   }else{
				   		systemConfectCodeStr =systemConfectCodeStr+recs[i].get('sid')+',';
				   }
			       }
			       systemConfectCode.value=systemConfectCodeStr
		     }
		    if(systemConfectName!=null){
				for(var i=0;i<recs.length;i++){
				   if(i==recs.length-1){
				   		systemConfectNameStr =systemConfectNameStr+ recs[i].get('name');
				   }else{
				  		systemConfectNameStr =systemConfectNameStr+ recs[i].get('name')+',';
				   }
					
			       }
			       systemConfectName.value=systemConfectNameStr;
		     }
		window.close();//关闭窗口
	}});
  });
</script>
