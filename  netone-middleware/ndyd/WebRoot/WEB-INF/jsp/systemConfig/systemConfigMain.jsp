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
		<title>系统配置管理</title>
	</head>
	<body>
            <input type="hidden" id="sid" name="sid">
        	<input type="hidden" id="name" name="name">
        	<input type="hidden" id="type" name="type" value="${param.type}">
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
    					url: "<c:url value='/common/systemConfig.do?method=onList' />", //JSON数据
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
    					   },
    					   {name: 'type'}
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
					  	//type: document.getElementById('type').value
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
    					 xtype:'toolbar',
    					 region:"north",
    					 items:[
    					  new Ext.Toolbar.Button({
    						  text:'新增',
    						  id:'ext_b_add',
    						  iconCls:"addIcon",
    						  handler: _add
    					  }),	
    					  new Ext.Toolbar.Button({
    						   text:'修改',
    						   id:'ext_b_change',
    						   iconCls:"editIcon",
    						   handler: _edit
    					  }),
    					  new Ext.Toolbar.Button({
    						  text:'删除',
    						  id:'ext_b_delete',
    						  iconCls:"deleteIcon",
    						  handler:_delete
    					  })
                         ]
                    	},{
    					    id:"BussGrid",
    					    xtype:"BussGrid",
    					    region:"center",
    			            border:false,
    			            hideBorders:true,
    			            height : clientHeight-25,
    					 	bodyStyle:'width:100%',
            				autoWidth:true
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
        	BussGrid2.store.load({params:{start:0, limit:15}});//刷新当前页面
    	};
    	
    	//--------------------------初始化加载--------------------------
    	refresh();//初始化加载数据
    	
        Ext.getCmp('BussGrid').addListener('celldblclick',function(grid, rowIndex, columnIndex, e){//列表双击事件--修改
      		_edit();
        });
        
      });
     
   	//------------------------------------初始化按钮事件--------------------------------
        
       function _add(){
       		openModalDialogWindow('新增','<c:url value="/common/systemConfig.do?method=onEditView" />'+'&type=${param.type}' ,300,160); 
       }
       function _edit(){
       	 var selectionSet = Ext.getCmp('BussGrid').getSelectionModel().getSelections(); 
  			 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
  				Ext.MessageBox.alert('系统提示','请选择一条记录进行修改!');
  				return ;
  			 }
            var guid = selectionSet[0].data.sid;
            var url = '<c:url value="/common/systemConfig.do?method=onEditView"/>'+ '&sid=' + guid +'&type=${param.type}' ;//*
            openModalDialogWindow('修改',url,300,160);
       }
       function _delete(){
       	  var sidStr =  getSid();
   	      var num = sidStr.length;
   	      if(num >= 1){
   	      	  Ext.MessageBox.confirm("系统提示",'您确定要删除所选数据吗？',function(btnId){
   					if(btnId == 'yes'){
   					var str = "";
   				    for(var i= 0 ; i < num ; i ++){
   				        str = str + sidStr[i]+",";
   				    }
   				    var str = Ext.util.Format.substr(str,0,str.length - 1);
   						Ext.Ajax.request({//AJAX删除
   								url : '<c:url value="/common/systemConfig.do?method=onDelete" />',
   								params : {sid:str},
   								method : 'POST',
   								success : function(response,options){
   									var result = Ext.util.JSON.decode(response.responseText);
   									if(result.error==null){
										Ext.ux.Toast.msg("", result.tip);
										refresh();
									} else {
										Ext.MessageBox.alert('提示',result.tip);
									}
   								},
   								failure : function(response,options){
   									checkAjaxStatus(response);
   									Ext.Msg.alert('系统提示',result.tip);
   								}
   						});
   					}
   			 });
   		 }
       }
       
    //----------公共方法定义(按钮,私有事件)-----------------
   	function getSid(){//取得所选数据字典Id
  			var recs = Ext.getCmp('BussGrid').getSelectionModel().getSelections();
  			var list = [];
  			if(recs.length == 0){
  				Ext.Msg.alert('系统提示','请选择要进行操作的信息！');
  			}else{
  				for(var i = 0 ; i < recs.length ; i++){
  					var rec = recs[i];					
  					list.push(rec.get('sid'));
  				}
  			}
  			return list;
   	}
</script>
