﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
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
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-dialog.jsp"/>
		<script language="javascript" type="text/javascript" src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<title>应用</title>
	</head>
	<body>
		<input type="hidden" id="openerWinId" name="openerWinId" value="app" />
	</body>
</html>
<script type="text/javascript">
var selectObjVar = null;//全局变量 存放需要选择资源返回值的对象
function $select(o,url){
	selectObjVar=o;
	openWinCenter("选择",encodeURI(encodeURI(url)),800,600,true);
}

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
					url: "<c:url value='/frame.do?method=onListExt' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [
						${fields}
					   ])
				}),
				columns:[
				        index,
						cb,
						${columns}
				],
				viewConfig:{forceFit:true},
				loadMask:true	
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
	 
		  this.bbar = new Ext.PagingToolbar({
			 store:this.store,
		     displayInfo:true,
		     pageSize:${limit},
		     displayMsg : '第 {0} 条到 {1} 条，一共 {2} 条',
			 emptyMsg : "没有记录"
		  });
	 
		  // call parent
		  Plan.Data.PlanGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var conditions = {
				   ${queryConditionHtml}
				  };
				  options=options||{}; 
				  options.params = options.params||{}; 
				  options.params.formcode = '${param.formcode}';
				  options.params.fatherlsh = '${param.fatherlsh}';
				  options.params.conditions = Ext.util.JSON.encode(conditions);
				  return true;
		  });
		  
		   // load the store at the latest possible moment
		  this.on({
			 beforeshow : {scope:this, single:true, fn:function() {
				   this.store.load({params:{start:0, limit:${limit}}});
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
				 items:[{
				     id:'toolbar',
					 xtype:'toolbar',
					 region:"north",
					 cls:'',
					 items:[
					 	<c:if test="${param.query!='look'}">
					  	{
						  text:'新增',
						  id:'ext_b_add',
						  iconCls:"addIcon",
						  handler: function (){
								window.open('<c:url value="/frame.do?method=onEditFrameExt"/>&formcode=${param.formcode}&fatherlsh=${param.fatherlsh}&workcode=${param.workcode}&naturalname=${param.naturalname}');
						  }
						},
						</c:if>
						{
						  text:'关闭',
						  id:'ext_b_close',
						  iconCls:"exitIcon",
						  handler: function(){
							window.close();
						  }
						}  
                	]},{
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
function $edit(lsh,runtimeid){
	window.open('<c:url value="/frame.do?method=onEditFrameExt"/>&formcode=${param.formcode}&fatherlsh=${param.fatherlsh}&workcode=${param.workcode}&naturalname=${param.naturalname}&lsh='+lsh);
}
function $query(lsh,runtimeid,readonly){
	window.open('<c:url value="/frame.do?method=onEditFrameExt"/>&formcode=${param.formcode}&fatherlsh=${param.fatherlsh}&workcode=${param.workcode}&naturalname=${param.naturalname}&query=look&lsh='+lsh);
}

function $delete(lsh){
	var url = "<c:url value='/frame.do?method=onDeleteExt' />";
	if (!confirm('确认要作废?')){return;};
	var msgTip = Ext.MessageBox.show({
        title: '提示',
        width: 250,
        closable:false,
        msg: '正在执行操作请稍候......'
    });
	 Ext.Ajax.request({
        url: url,
        // 请求的服务器地址
        // 指定要提交的表单id
        method: 'POST',
        params:{lsh:lsh,formcode:'${param.formcode}'},
        success: function (response, options) {
            msgTip.hide();
            var result = Ext.util.JSON.decode(response.responseText);
            if (result.error != null) {
                Ext.MessageBox.alert('提示', result.tip);
            } else {
            	refresh();
            }
        },
        failure: function (response, options) {
            msgTip.hide();
            checkAjaxStatus(response);
            var result = Ext.util.JSON.decode(response.responseText);
            Ext.MessageBox.alert('提示', result.tip);
        }
    });
}

Ext.onReady(function () { //页面加载时候触发事件
    var viewport = new Plan.Layout.Viewport();
    var PlanGrid2 = viewport.findById('PlanGrid'); //Grid
    var toolbar = viewport.findById('toolbar');
    toolbar.addSpacer();
    toolbar.addSeparator();
    toolbar.addFill();

    //--------------------------页面数据加载方法定义-------------------------
/* 
	* @param _isFirst 值 为 true or false 
	*			 若为true 跳转到第一页
	*/
    refresh = function () { //刷新
        Ext.getCmp('PlanGrid').store.load({
            params: {
                start: 0,
                limit: ${limit}
            }
        }); //刷新当前页面
    };

    //--------------------------初始化加载--------------------------
    refresh(); //初始化加载数据
});
</script>
