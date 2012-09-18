﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>		
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
		${datecompFunc}
		<title>应用</title>
	</head>
	<body>
		<input type="hidden" id="openerWinId" name="openerWinId" value="app" />
		<!--<input type="hidden" id="beginTime" name="beginTime">
		<input type="hidden" id="endTime" name="endTime">
		<input type="hidden" id="status" name="status">
		<input type="hidden" id="clientId" name="clientId">-->
		<div id="conditionDiv">
			${queryColumnsHtml}
		</div>
		
	</body>
</html>
<script type="text/javascript">

function $$print(lsh){
		var url = "<c:url value='/xreporthg.do?method=report1' />"+"&lsh="+lsh;
		if (lsh!='') window.open(url);
	}
	
function $$hx(lsh){
	if (!confirm('确认要核销?')){return;};
	window.open('<c:url value="/frame.do?method=onEditViewMain&naturalname=APPFRAME.APPFRAME.HGMY.HXTASK"/>'+'&lsh='+lsh);
}

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
					url: "<c:url value='/frame.do?method=onList' />", //JSON数据
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
						,{header: "核销状态",dataIndex: "statusinfo", width:80,sortable: false, renderer: 
						function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) { 
								var statusinfo = store.getAt(rowIndex).get('statusinfo') ; 
								if (statusinfo=='01'){
									return '<font color="green">待核销</font>';
								} else if (statusinfo=='02'){
									return '<font color="blue">已核销</font>';
								} else {
									return '<font color="red">未核销</font>';
								}
							}
						}
						,{header: "操作",dataIndex: "", width:200,sortable: false, renderer: 
						function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) { 
							var lsh = store.getAt(rowIndex).get('lsh') ; 
							var runtimeid = store.getAt(rowIndex).get('runtimeid') ; 
							var statusinfo = store.getAt(rowIndex).get('statusinfo') ; 
							var RUN = store.getAt(rowIndex).get('run') ; 
							var value = "";
							value += "&nbsp;<a href='javascript:void(0)' onclick=$query('"+lsh+"','"+runtimeid+"','true'); >查看</a>&nbsp;";
							<rs:permission action="7" resource="${naturalname_dyform}.PRINT">
							value += "&nbsp;<a href='javascript:void(0)' onclick=$$print('"+lsh+"'); >打印</a>&nbsp;";
							</rs:permission>
							<rs:permission action="7" resource="${naturalname_dyform}.HX">
							if (statusinfo=='01'){
							value += "&nbsp;<a href='javascript:void(0)' onclick=$$hx('"+lsh+"'); >核销</a>&nbsp;";
							}
							</rs:permission>
							<rs:permission action="7" resource="${naturalname_dyform}.MODI">
							value += "&nbsp;<a href='javascript:void(0)' onclick=$edit('"+lsh+"','"+runtimeid+"'); >编辑</a>&nbsp;";
							</rs:permission>
							<rs:permission action="7" resource="${naturalname_dyform}.DEL">
							value += "&nbsp;<a href='javascript:void(0)' onclick=$delete('"+lsh+"'); >作废</a>&nbsp;";
							</rs:permission>
							return value;
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
				  options.params.naturalname = '${param.naturalname}';
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
					  	<rs:permission action="7" resource="${naturalname_dyform}.ADD">
					  	{
						  text:'新增',
						  id:'ext_b_add',
						  iconCls:"addIcon",
						  handler: function (){
								window.open('<c:url value="/frame.do?method=onEditViewMain&naturalname=${param.naturalname}"/>');
						  }
						},'-',
						</rs:permission>
						{xtype:'panel',
							baseCls : 'x-plain',
							contentEl : 'conditionDiv'
						}
                	]},{
					    id:"PlanGrid",
					    xtype:"PlanGrid",
					    region:"center",
					    minColumnWidth:20,
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
	window.open('<c:url value="/frame.do?method=onEditViewMain"/>&naturalname=${param.naturalname}&lsh='+lsh+'&runtimeid='+runtimeid);
}
function $query(lsh,runtimeid,readonly){
	window.open('<c:url value="/frame.do?method=onEditViewMain"/>&naturalname=${param.naturalname}&query=look&lsh='+lsh+'&runtimeid='+runtimeid+"&readonly="+readonly);
}

function $delete(lsh){
	var url = "<c:url value='/frame.do?method=onDelete' />";
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
        params:{lsh:lsh,naturalname:'${param.naturalname}'},
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
