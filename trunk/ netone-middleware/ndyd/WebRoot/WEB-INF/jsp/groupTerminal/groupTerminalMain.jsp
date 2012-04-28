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
		<title>公司应用</title>
	</head>
	<body>
		<input type="hidden" id="openerWinId" name="openerWinId" value="plan">
		<!--<input type="hidden" id="beginTime" name="beginTime">
		<input type="hidden" id="endTime" name="endTime">
		<input type="hidden" id="status" name="status">
		<input type="hidden" id="clientId" name="clientId">-->

		
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
					url: "<c:url value='/groupTerminal/groupTerminal.do?method=onList' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [
						{name: 'groupTerminalId'},
						{name: 'subjectId'},
						{name: 'subjectName'},/** 需求名称 */
						{name: 'customerName'},/** 客户姓名 */
						{name: 'groupName'},/** 集团名称 */
						{name: 'groupValueLevel'},/** 集团价值等级 */
						{name: 'post'},/** 职务 */
						{name: 'postLevel'}, /** 职级 */
						{name: 'phoneManufacturers'},/** 手机厂家*/
						{name: 'phoneModels'},/** 手机型号 */
						{name: 'networkTime',type : 'date', mapping : 'networkTime.time', dateFormat : 'time' },/** 入网时间 */
						{name: 'phonePrice'},/**手机单价(元) */
						{name: 'phoneAmount'},/**手机数量 */
						{name: 'priceCount'},/**总价格 */
						{name: 'applicationProject'},/**申请项目 */
						{name: 'preferentialSchemes'},/**已办优惠方案 */
						{name: 'RUN'},
						{name: 'status'},
						{name:'runtimeid'}
					   ])
				}),
				columns:[
				        index,
						cb,
						{header: "需求名称", width: 120, dataIndex: 'subjectName', sortable: true},
						{header: "客户姓名", width: 100, dataIndex: 'customerName', sortable: true},
						{header: "集团名称", width: 100, dataIndex: 'groupName', sortable: true},
						{header: "集团价值等级", width: 100, dataIndex: 'groupValueLevel', sortable: true},
						{header: "入网时间", width: 100, dataIndex: 'networkTime', sortable: true,renderer : Ext.util.Format.dateRenderer('Y-m-d')},
						{header: "手机单价(元)", width: 100, dataIndex: 'phonePrice', sortable: true},
						{header: "手机数量", width: 100, dataIndex: 'phoneAmount', sortable: true},
						{header: "总价格", width: 100, dataIndex: 'priceCount', sortable: true},
						{header: "申请项目", width: 100, dataIndex: 'applicationProject', sortable: true},
						{header: "已办优惠方案", width: 50, dataIndex: 'preferentialSchemes', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								var  pre = store.getAt(rowIndex).get('preferentialSchemes') ;
								if(pre==1){
									return '是';
								}
								if(pre==0){
									return '否';
								}
							}
						},
						{header: "状态", width: 60, dataIndex: 'status', sortable: true},
						{header: "操作", align:'center',dataIndex: '', sortable: false, renderer:
						     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {
						     	   var groupTerminalId = store.getAt(rowIndex).get('groupTerminalId') ;
						     	   var runtimeid = store.getAt(rowIndex).get('runtimeid') ;
						     	   var RUN = store.getAt(rowIndex).get('RUN') ;
						     	   var value = null;
						     	   if(RUN==true){
						     	   		value = "&nbsp;<a href=\"javascript:void(0)\" onclick=\"_query('"+groupTerminalId+"','"+runtimeid+"','true');\" >查看</a>&nbsp;";
						     	   }else{
						     	   		value = "&nbsp;<a href=\"javascript:void(0)\" onclick=\"_edit('"+groupTerminalId+"','"+runtimeid+"');\" >编辑</a>&nbsp;";
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
				 items:[{
				     id:'toolbar',
					 xtype:'toolbar',
					 region:"north",
					 cls:'',
					 items:[
					  		{
						  text:'新增',
						  id:'ext_b_add',
						  iconCls:"addIcon",
						  handler: function (){
								openWinCenter('集团终端','<c:url value="/groupTerminal/groupTerminal.do?method=onEditViewMain"/>', 1000,1000,true);
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
function _edit(groupTerminalId,runtimeid){
	openWinCenter('集团终端','<c:url value="/groupTerminal/groupTerminal.do?method=onEditViewMain"/>&groupTerminalId='+groupTerminalId+'&runtimeid='+runtimeid, 1000,1000,true);
}
function _query(groupTerminalId,runtimeid,readonly){
	openWinCenter('集团终端','<c:url value="/groupTerminal/groupTerminal.do?method=onEditViewMain"/>&query=look&groupTerminalId='+groupTerminalId+'&runtimeid='+runtimeid+"&readonly="+readonly, 1000,1000,true);
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
                limit: 15
            }
        }); //刷新当前页面
    };

    //--------------------------初始化加载--------------------------
    refresh(); //初始化加载数据
});
</script>
