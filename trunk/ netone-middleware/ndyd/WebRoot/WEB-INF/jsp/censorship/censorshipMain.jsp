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
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
        <jsp:include page="../common/metaJQuery-ui-dialog.jsp"/>
		<script language="javascript" type="text/javascript" src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<title></title>
	</head>
	<body>
	<input type="hidden" id="openerWinIdFDL"  name="openerWinIdFDL" value="FDL__1" />
	</body>
</html>
<script type="text/javascript">

  Ext.ns('Plan.Data');
  Plan.Data.PlanGrid  =  Ext.extend(Ext.grid.GridPanel,{
  
		 initComponent: function() {
		 
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	});
		    
			var config = {
			    sm : cb,
			    //frame:true,
			    border:false,
			    enableHdMenu:false,
				store: new Ext.data.Store({
					url: "<c:url value='/censorship/censorship.do?method=onSelectView' />"+"&status=${param.status}", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [
						{name: 'unid'},
						{name: 'subject'},
						{name: 'handler'},
						{name: 'iscreater'},
						{name: 'chargedept'},
						{name: 'duetime'},
						{name: 'donetime'},
						{name: 'transdept'},
						{name: 'memo'},
						{name: 'newtime'},
						{name: 'frome'}
					   ])
				}),
				columns:[
				        index,
						cb,
						/**
						{header: "unid",  dataIndex: 'unid', sortable: true},
						{header: "subject",  dataIndex: 'subject', sortable: true},
						{header: "handler",  dataIndex: 'handler', sortable: true},
						{header: "chargedept",  dataIndex: 'chargedept', sortable: true},
						{header: "duetime",  dataIndex: 'duetime', sortable: true},
						{header: "donetime",  dataIndex: 'donetime', sortable: true},
						{header: "transdept",  dataIndex: 'transdept', sortable: true},
						{header: "memo",  dataIndex: 'memo', sortable: true},
						{header: "newtime",  dataIndex: 'newtime', sortable: true},
						{header: "frome",  dataIndex: 'frome', sortable: true},
						**/
						{header: "督办标题",  dataIndex: 'subject', sortable: true},
						{header: "发起人",  dataIndex: 'handler', sortable: true},
						{header: "需要完成时间",  dataIndex: 'duetime', sortable: true},
						{header: "当前流程环节名", align:'center',dataIndex: '', sortable: false, renderer:
						     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {
								   return '督办处理';
							 }
						},
						{header: "操作", align:'center',dataIndex: '', sortable: false, renderer:
						     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {
						     	   var unid = store.getAt(rowIndex).get('unid') ;
						     	   var iscreater = store.getAt(rowIndex).get('iscreater') ;
						     	   if (iscreater==true){
						     	   value = "&nbsp;<a href=\"javascript:void(0)\" onclick=\"_edit('"+unid+"',0,'false');\" >处理</a>&nbsp;";
						     	   }else{
						     	   value = "&nbsp;<a href=\"javascript:void(0)\" onclick=\"_edit('"+unid+"',1,'false');\" >处理</a>&nbsp;";
						     	   }
						     	   var status = '${param.status}';
						     	   if (status=='' || status=='1' || status=='2'){
						     	   	value = "&nbsp;<a href=\"javascript:void(0)\" onclick=\"_edit('"+unid+"',-1,'true');\" >查看</a>&nbsp;";
						     	   }
								   return  value;
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
		  Plan.Data.PlanGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  options=options||{}; 
				  options.params = options.params||{}; 
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
  Ext.reg('PlanGrid', Plan.Data.PlanGrid);//注册一个组件,注册成xtype以便能够延迟加载
  
  Ext.ns('Plan.Layout');
  
  Plan.Layout.Viewport =  Ext.extend(Ext.Viewport, {
  	 
       initComponent: function(){
       	   var clientHeight = 0;
		   if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
			    clientHeight = document.documentElement.clientHeight;
		   } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
			    clientHeight = document.body.clientHeight;
	       }
       	   
	       var config = {
				 collapsible:true,
				 layout:"border", 
				 autoWidth:true,
				 border:false,
				 cls:'',
				 items:[
				 	{
				     id:'toolbar',
					 xtype:'toolbar',
					 region:"north",
					 cls:'',
					 items:[
					  		{
						  text:'新增',
						  id:'ext_b_add',
						  iconCls:"addIcon",
						  handler:_new
					  }
                	]},
                	{
					    id:"PlanGrid",
					    xtype:"PlanGrid",
					    region:"center",
			            border:false,
			            hideBorders:true,
			            height : clientHeight-20,
					 	autoScroll:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			Plan.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
  function _t1(){
  	document.getElementById('status').value="2";
  	refresh(); 
  }
  
  function _t2(){
  	document.getElementById('status').value="";
  	refresh(); 
  }
  
  function _new(){
    window.open('<c:url value="/censorship/censorship.do?method=onEditXmain"/>');
  }
  
  function _edit(unid,t,readonly){
    window.open('<c:url value="/censorship/censorship.do?method=onEditXmain"/>'+"&unid="+unid+"&t="+t+"&readonly="+readonly);
  }
  
Ext.onReady(function () { //页面加载时候触发事件
    var viewport = new Plan.Layout.Viewport();
    var PlanGrid2 = viewport.findById('PlanGrid'); //Grid

    //--------------------------页面数据加载方法定义-------------------------
    refresh = function () { //刷新
        Ext.getCmp('PlanGrid').store.load({
            params: {
                start: 0,
                limit: 15
            }
        }); //刷新当前页面
    };

    //--------------------------初始化加载--------------------------
    refresh(); //初始化加载数据

});
</script>
