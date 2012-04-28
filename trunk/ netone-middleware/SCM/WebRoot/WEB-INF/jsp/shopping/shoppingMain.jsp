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
		<title>财物物购管理</title>
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
					url: "<c:url value='/shopping/shopping.do?method=onList' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [
						{name: 'shoppingId'},
						{name: 'purchaseIllustrate'},
						{name: 'cname'},
						{name: 'operateTime'},
						{name: 'type'},
						{name: 'status'}
					   ])
				}),
				columns:[
				        index,
						cb,
						{header: "请购说明", width: 120, dataIndex: 'purchaseIllustrate', sortable: true},
						{header: "请购人", width: 100, dataIndex: 'cname', sortable: true},
						{header: "请购时间", width: 100, dataIndex: 'operateTime', sortable: true},
						{header: "类型", width: 100, dataIndex: 'type', sortable: true,hidden:true},
						{header: "状态", width: 50, dataIndex: 'status', sortable: true,hidden:true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var status = record.get('status');
								 if (status == -1){
								 	return '<font color="red">退回</font>';
								 } else if (status ==0) {
								 	return '<font color="red">未提交</font>';
								 } else if (status == 3) {
								 	return '<font color="green">完成</font>';
								 } else {
								 	return '待审核';
								 }
							}
						}/*,
						{header: "操作", align:'center',dataIndex: '', sortable: false, renderer:
						     function operateRend(value, cellmeta, record, rowIndex, columnIndex, store) {
						     	   var shoppingId = store.getAt(rowIndex).get('shoppingId') ;
						     	   var value = "&nbsp;<a href=\"javascript:void(0)\" onclick=\"_edit('"+shoppingId+"');\" >查看</a>&nbsp;";
								   return  value;
							 }
							}*/
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
       	   var menu =  new Ext.menu.Menu({
						id: 'mainMenu',
						text:'新建',
						items: [
							{
								text: '低值易耗品',
								iconCls: '',
								handler: function (){
									openWinCenter('低值易耗品','<c:url value="/shopping/shopping.do?method=onEditView"/>'+'&typeO=1', 820,600,true);
								}  
							},{
								text: '备品备件',
								iconCls: '',
								handler: function (){
									//openWinCenter('备品备件','<c:url value="/shopping/shopping.do?method=onEditView"/>'+'&typeO=2', 820,400,true);
								}  
							},{
								text: '固定资产',
								iconCls: '',
								handler: function (){
									//openWinCenter('固定资产','<c:url value="/shopping/shopping.do?method=onEditView"/>'+'&typeO=3', 820,400,true);
								}   
							},{
								text: '宣传促销品',
								iconCls: '',
								handler: function (){
									//openWinCenter('宣传促销品','<c:url value="/shopping/shopping.do?method=onEditView"/>'+'&typeO=4', 820,400,true);
								}   
							},{
								text: '业务单册',
								iconCls: '',
								handler: function (){
									//openWinCenter('业务单册','<c:url value="/shopping/shopping.do?method=onEditView"/>'+'&typeO=5', 820,400,true);
								}   
							},{
								text: '工程物资',
								iconCls: '',
								handler: function (){
									//openWinCenter('工程物资','<c:url value="/shopping/shopping.do?method=onEditView"/>'+'&typeO=6', 820,400,true);
								}   
							}
			            ]    			
		   });
       	   
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
						  menu: menu
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
function _edit(shoppingId){
 openWinCenter('查看', '<c:url value="/shopping/shopping.do?method=onShoppingDetailMainView"/>&shoppingId=' + shoppingId, 820, 600, true);
}
function _change() { //修改
    var selectionSet = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
    var record = selectionSet[0];
    if (selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <= 0) {
        Ext.MessageBox.alert('提示', '请选择一条请购单进行修改!');
        return;
    }
    var status = record.get('status');
    if (status == 0) {
        guid = selectionSet[0].get('shoppingId');
        openWinCenter('修改', '<c:url value="/shopping/shopping.do?method=onEditView"/>&shoppingId=' + guid, 820, 600, true);
    } else {
        Ext.MessageBox.alert('提示', '该单子状态不能修改!');
    }

}


function _delete() { //删除
    var selectionSet = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
    var record = selectionSet[0];
    if (selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <= 0) {
        Ext.MessageBox.alert('提示', '请选择一条请购单进行删除!');
        return;
    }
    var shoppingId = record.get('shoppingId');
    var status = record.get('status');
    if (status <= 0) {
           Ext.MessageBox.confirm("提示", "您确定要删除所选的信息吗？", function (btnId) {
           	   if (btnId == 'yes') {
           	   	deletePlanById(shoppingId);
           	   }
           });
    } else {
        Ext.MessageBox.alert('提示', '该请购单处于待审核或完成状态,不允许删除!');
    }
}



function _viewDetail() { //查看明细
    var recs = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
    if (recs.length <= 0 || recs.length > 1) {
        Ext.MessageBox.alert('提示', '请选择一条请购单!');
        return;
    }
    var record = recs[0];
    var shoppingId = record.get('shoppingId');
    openWinCenter('查看明细', '<c:url value="/shopping/shopping.do?method=onShoppingDetailPreview" />' + '&shoppingId=' + shoppingId, 800, 400, true);
}


function _auditing() { //提交审核事件
    var msgTip = Ext.MessageBox.show({
        title: '提示',
        width: 250,
        msg: '正在提交审核......'
    });

    var recs = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
    if (recs.length <= 0 || recs.length > 1) {
        Ext.MessageBox.alert('提示', '请选择一条请购单!');
        return;
    }
    var record = recs[0];
    var shoppingId = record.get('shoppingId');
    var status = record.get('status');
    if (status <= 0) {
        Ext.Ajax.request({
            url: "<c:url value='/shopping/shopping.do?method=onAudting' />",
            params: {
                shoppingId: shoppingId
            },
            method: 'POST',
            success: function (response, options) {
                msgTip.hide();
                var result = Ext.util.JSON.decode(response.responseText);
                if (result.error != null) {
                    Ext.MessageBox.alert('提示', result.tip);
                } else {
                    Ext.ux.Toast.msg("", result.tip);
                    refresh();
                }
                if (result.limitTip!=null){
	            	alert(result.limitTip);
	            }
            },
            failure: function (response, options) {
                msgTip.hide();
                checkAjaxStatus(response);
                var result = Ext.util.JSON.decode(response.responseText);
                Ext.MessageBox.alert('提示', result.tip);
            }
        });
    } else {
        Ext.MessageBox.alert('提示', '该请购单不能提交审核，请查看请购单状态!');
    }

}

function getShoppingId() { //取得所取产销计划订单信息
    var recs = Ext.getCmp('PlanGrid').getSelectionModel().getSelections()
    var list = [];
    if (recs.length == 0) {
        Ext.MessageBox.alert('提示', '请选择要进行操作的分类信息！');
    } else {
        for (var i = 0; i < recs.length; i++) {
            var rec = recs[i];
            var status = rec.get('status');
            if (status != 0 || status != 1) {
                list.push(rec.get('shoppingId'))
            } else {
                //do nothing
            }
        }
    }
    return list;
}

function deletePlanById(shoppingId) {
    var msgTip = Ext.MessageBox.show({
        title: '提示',
        width: 250,
        msg: '正在删除产销计划单信息请稍后......'
    });
    Ext.Ajax.request({
        url: '<c:url value="/shopping/shopping.do?method=onDelete" />',
        params: {
            shoppingId: shoppingId
        },
        method: 'POST',
        sync: true,
        success: function (response, options) {
            msgTip.hide();
            var result = Ext.util.JSON.decode(response.responseText);
            if (result.error != null) {
                Ext.Msg.alert('提示', result.tip);
            } else {
                refresh();
                Ext.ux.Toast.msg("", result.tip);
            }
        },
        failure: function (response, options) {
            msgTip.hide();
            checkAjaxStatus(response);
            Ext.Msg.alert('提示', '删除请购单信息请求失败！');
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
                limit: 15
            }
        }); //刷新当前页面
    };

    //--------------------------初始化加载--------------------------
    refresh(); //初始化加载数据
});
</script>
