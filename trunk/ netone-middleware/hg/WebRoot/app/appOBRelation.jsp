<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%	String path = request.getContextPath();%>	
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
        <jsp:include page="/WEB-INF/jsp/common/metaJQuery-ui-dialog.jsp"/>
		<script type="text/javascript" src="<%=path%>/theme/ext/extend/gridToExcel.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<title>理货频率配置</title>
	</head>
	<body>
	<div id="_c_conent">
	    <form id="pcform" action="" method="post">
	    <input type="hidden" id="lshId" name="lshId">
	    <input type="hidden" id="proName" name="proName">
	    
	    </form>	
	</div>
	<div id="divQuery" class="divQuery" align="center" style="display: none;">
		<table class="main" style="margin-top: 15px;">
			<tr>
				<td nowrap="nowrap" class="label">查询日期:</td>
				<td>
					<input id='beginTime' name='beginTime' type='text' class='Wdate' style='width:50px' onFocus="WdatePicker({dateFmt:'yyyy'});" value="<fmt:formatDate value='<%=new java.util.Date(System.currentTimeMillis())%>' pattern='yyyy' />">
					--
					<input id='endTime' name='endTime' type='text' class='Wdate' style='width:50px' onFocus="WdatePicker({dateFmt:'yyyy'});" value="<fmt:formatDate value='<%=new java.util.Date(System.currentTimeMillis())%>' pattern='yyyy' />">
				</td>
			</tr>
			<tr>
			<td colspan="2" align="center">
				<input id="queryBtn" type="button" value=" 查 询 " class="btn"
									onclick="_exportPCTarget('html');"/>
				<input id="queryBtn" type="button" value=" 下 载 " class="btn"
					onclick="_exportPCTarget('excel');"/>
			</td>
			</tr>
		</table>
	</div>
	<div id="dd" style="padding:0px;display: none;">
		<div class="loading-indicator" id="_loading">loading...</div>
		<iframe id="uiMainContent" src="" width="100%" frameborder="0" height="100%">
		loading...
		</iframe>
	</div>
	</body>
</html>
<script type="text/javascript">
var nodeid = '0';
var lshId,proName;
Ext.QuickTips.init();

Ext.ns('Buss.Data');//GridPanel
Buss.Data.BussGrid  =  Ext.extend(Ext.grid.EditorGridPanel,{

 initComponent: function() {
 
    var index= new Ext.grid.RowNumberer();//
    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
		singleSelect: false
   	});
	var config = {
	    sm : cb,
	    clicksToEdit:1,
	    border:false,
		enableHdMenu:false,
		store: new Ext.data.Store({
			url: "<c:url value='/app.do?method=onFindOutletSet' />", //JSON数据
			reader: new Ext.data.JsonReader(
			   {root: 'info'}, [
			    {name: 'tallId'},
				{name: 'outletsId'},
				{name: 'outletsName'},
				{name: 'brandId'},
				{name: 'brandName'},
				{name: 'userCode'},
				{name: 'userName'},
				{name: 'times'},
				{name: 'statusx'},
				{name: 'creadDate'},
				{name: 'note'},
				{name: 'operate'}
			   ])
		}),
		columns:[
		        index,
				cb,
				{header: "流水号", width: 100, dataIndex: 'tallId', sortable: true,hidden:true},
				{header: "网点名称ID", width: 50, dataIndex: 'outletsId', sortable: true,hidden:true},
				{header: "网点名称", width: 100, dataIndex: 'outletsName', sortable: true},
				{header: "理货员编码", width: 100, dataIndex: 'userCode', sortable: true,hidden:true},
				{header: "理货员", width: 100, dataIndex: 'userName', sortable: true,hidden:true},
				{header: "每周几次", width: 60, dataIndex: 'times', sortable: true
							,editor : new Ext.form.TextField({
							   allowBlank : false,
							   regex:/^[+]?[0-9]*\.{0,1}[0-9]{0,2}$/
							})
				},
				{header: "备注", width: 80, dataIndex: 'note', sortable: true
						,editor : new Ext.form.TextField({
							   allowBlank : false
							})
				},
				{header: "品牌ID", width: 80, dataIndex: 'brandId', sortable: true,hidden:true},
				{header: "品牌", width: 60, dataIndex: 'brandName', sortable: true},
				{header: "状态", width: 50, dataIndex: 'statusx', sortable: true,renderer:
					function todo(value, cellmeta, record, rowIndex, columnIndex, store){
						 if(record.get('statusx') == '1'){
						  		return '启用';
						 }else{
						  		return '禁用';
						 }
					}
					},
				{header: "创建时间", width: 80, dataIndex: 'creadDate', sortable: true},
				{header: "操作者", width: 80, dataIndex: 'operate', sortable: true}
				],
		viewConfig:{forceFit:true},
		loadMask:true	
   }
   
   // apply config
  Ext.apply(this, Ext.apply(this.initialConfig, config));

  // call parent
  Buss.Data.BussGrid.superclass.initComponent.apply(this, arguments);
     this.store.on('beforeload', function(store,options){
		  options=options||{};
		  options.params = options.params||{};
		  options.params.id = Ext.get('lshId').getValue();
		  options.params.condition = Ext.get('condition').getValue();
		  return true;
  });
  
  // load the store at the latest possible moment
  this.on({
	 beforeshow : {scope:this, single:true, fn:function() {
		   this.store.load();
	 }}
  });
 }
});
Ext.reg('BussGrid',  Buss.Data.BussGrid);//注册一个组件,注册成xtype以便能够延迟加载

Ext.ns('BussGrid.Layout');

BussGrid.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
  
   var treeDataUrl = "<c:url value='/app.do?method=onFindProTree' />";
     
   var config = {
		 collapsible:true,
		 autoWidth:true,
		 layout:"border", 
		 frame:true,
		 items:[{
		 			id:'treepanel',
		 			xtype:'treepanel',
		 			region:"west",
		 			width:200,
		 			animCollapse :false,
		 			split:true,
		 			animate :true,//去除动画
　　　				autoScroll:true,
					border : true, // 边框
					//useArrows :true,
				    loader: new Ext.tree.TreeLoader({dataUrl: treeDataUrl}),
		       		root : new Ext.tree.AsyncTreeNode({id:nodeid,text:'品牌'}),
		       	 	listeners : {
						click : function(node,e){
							if(node.attributes.id != nodeid){
								 Ext.get('lshId').dom.value = node.attributes.id;
								 Ext.get('proName').dom.value = node.attributes.text;
								 refresh();
							}else{
								Ext.get('lshId').dom.value = '';
								Ext.get('proName').dom.value = '';
								refresh();
							}
						}
					}
              	},{
						id:'BussGrid',
		            	xtype:'BussGrid',
		            	region:"center",
		            	autoScroll:true,
		            	tbar:new Ext.Toolbar([
							 {text : '新建',iconCls:'addIcon',handler:_addOutlets},
							 {text : '删除',iconCls:'deleteIcon',handler:_deleteOutletsTimes},'-',
							 {text : '导出Excel',iconCls:'excelIcon',handler:_exportExcel},'-',
							 {xtype:'panel',baseCls : 'x-plain',
								html:'<input id=\'condition\' type=\'text\' onkeyup=\'onKeyUpEvent(event);\' onchange=\'refresh();\'>'
							 },
							 {text : '查找',iconCls:'viewIcon',handler:function(){refresh();}}
						])
					}]
		}
	         
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	BussGrid.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});
/**-----------------------------基本操作事件------------------------------*/
/**---产品分类基本事件---*/
function _refreshLoad(){//刷新分类树-重新加载树
	Ext.getCmp('treepanel').root.reload();//树根节目重新加载
	//resetForm(Ext.get('pcform').dom);//重载产品分类表单
  Ext.getCmp('BussGrid').store.removeAll();//移除产品信息
}

function onKeyUpEvent(e){//监听键盘事件
	switch(e.keyCode)
	{
		case 13:
			refresh();
			break;//回车  
		default:
		break;
	}
}
/** ----  右边基本操作 ---- */
//插入网点信息
function _onSave(selectObj,lshId,times,note){
 	var selectedStr = getSelectedValueStr(selectObj);
 	var times = times;
 	var note = note;
 	var statusx = 1;
	var vUrl = '<c:url value="/app.do?method=onSaveOutlets"/>';
	Ext.Ajax.request({
	   url:  vUrl,
	   params : {outIdStr : selectedStr,lshId:lshId,times:times,note:note,statusx:statusx},
	   success: function(response, options){
	   		refresh();
	   },
	   failure : function(response,options){
	   		checkAjaxStatus(response);
	   		var result = Ext.util.JSON.decode(response.responseText);
	   		Ext.MessageBox.alert('提示',result.tip);
	   }
	});
}

//打开新增窗口
function _addOutlets(){//新增网点
   var lshId = Ext.get('lshId').dom.value;
   var proName = Ext.get('proName').dom.value;
	if(lshId!=""){
		//openModalDialogWindow('新增',"<c:url value='/app.do?method=onEditView' />&lshId="+lshId,600,400);

	      document.getElementById("uiMainContent").src='';
	      var url = '<c:url value="/app.do?method=onEditView" />&lshId='+lshId;
	 	  document.getElementById("_loading").style.display = "block";
	     	  $('#dd').dialog({
	     			title:'&nbsp;'+proName,
	     			//href :'',/** 加载静态页面且不支持跨域访问 */
	     			resizable:false,
	     			draggable:false,
	     			maximizable:false,
	     			minimizable:false,
	     			width:800,
	     			height:450,
	     			//shadow:true,
	     			modal:true,
				buttons:{
					'关闭':function(){
						$('#dd').dialog('close');
					},
					'保存':function(){
					    var iframeObj = document.getElementById('uiMainContent').contentWindow;
					    var selectObj = iframeObj.document.getElementById('select');
					    var times = iframeObj.document.getElementById('times').value;
					    if (times==''){
					    	alert('理货频率不能为空!');
					    	return;
					    } 
					    var note = iframeObj.document.getElementById('note').value;
					    //asyncProductPrice(selectObj,0);
					    //保存结果  未
					    _onSave(selectObj,lshId,times,note);
					    $('#dd').dialog('close');
					}
				},
				close: function(event, ui) {
					$('#dd').dialog("destroy");
				},
				open: function(event, ui) {
					document.getElementById("uiMainContent").src=url;
					$("#uiMainContent").load(function(){
							_hideLoading();
			    	});
				}
		});
	}else{
		Ext.MessageBox.alert('错误提示','请选择品牌!');
	}	
}	

//更新网点频率信息事件 编辑事件后调用
function updateTimes(type,record){
		//var lshId = document.getElementById('lshId').value;
		var tallId = record.get('tallId');
		var outletsId = record.get('outletsId');
		var brandId = record.get('brandId');
		var times = record.get('times');
		var note = record.get('note');
		//var note = encodeURI(encodeURI(note));//去除页面乱码问题
		/*
		var msgTip = Ext.MessageBox.show({
				title:'系统提示',
				width : 250,
				msg:'正在保存产品价格...'
 		});
		*/
		
    	var vUrl = '<c:url value="/app.do?method=onUpdateTimes"/>';
		Ext.Ajax.request({
		   url:  vUrl,
		   params: {tallId:tallId,outletsId:outletsId,brandId:brandId,times:times,note:note},
		   success: function(response, options){
				 //msgTip.hide();
		   },
		   failure : function(response,options){
		   		//msgTip.hide();
		   		checkAjaxStatus(response);
		   		var result = Ext.util.JSON.decode(response.responseText);
		   		Ext.MessageBox.alert('提示',result.tip);
		   }
		});
}

//编辑后调用更新事件
function afterEdit(obj){
	var record=obj.record;
	var times = record.get('times');
	var note = record.get('note');
	
	if (record.isModified('times')){
		updateTimes('times',record);
	}
	if (record.isModified('note')){
		updateTimes('note',record);
	}
	
	record.cancelEdit(true);//取消编辑状态
}	

//删除网点明细--右边
function _deleteOutletsTimes(){
     var tallIdStr =  getTallId();
     var num = tallIdStr.length;
     
     if(num >= 1){
     	  Ext.MessageBox.confirm("系统提示",'您确定要删除所选网点配置信息吗？',function(btnId){
			if(btnId == 'yes'){
			var msgTip = Ext.MessageBox.show({
				title:'系统提示',
				width : 250,
				msg:'正在删除网点配置信息请稍后......'
	 			});
			
			var str = "";
			var codeStr = "";
		    for(var i= 0 ; i < num ; i ++){
		        str = str + tallIdStr[i]+",";
		    }
		    var str = Ext.util.Format.substr(str,0,str.length - 1);
		    var codeStr = Ext.util.Format.substr(codeStr,0,codeStr.length - 1);
				Ext.Ajax.request({//AJAX删除
						url : '<c:url value='/app.do?method=onDeleteOT' />',
						params : {tallIdStr : str},
						method : 'POST',
						success : function(response,options){
							msgTip.hide();
							var result = Ext.util.JSON.decode(response.responseText);
							if(result.error==null){
								Ext.ux.Toast.msg("", result.tip);
								refresh();
							}else{
								Ext.MessageBox.alert('提示',result.tip);
							}
						},
						failure : function(response,options){
							msgTip.hide();
							checkAjaxStatus(response);
							var result = Ext.util.JSON.decode(response.responseText);
							Ext.MessageBox.alert('提示',result.tip);
						}
				});
			}
	 });
 }
}

//导出网点配置(excel)
function _exportExcel(){
	var lshId = Ext.get('lshId').dom.value;
	var tallIdStr =  getTallIdStr();
	
	var url = '<c:url value='/app.do?method=onExportAppOBR' />&format=excel&lshId='+lshId+'&tallIdStr='+tallIdStr;
	openWinCenter('导出',url, 200,200);

}	

//将options中的对象以逗号分隔
function getSelectedValueStr(obj){
		 var arrValueText = new Array();
		
	     var str1 = '';
	     var deliter='';
	     
	     var options = obj.options;
		 for(var i=0; i < options.length; i++){
		      str1 += deliter + options[i].value;
		      deliter=',';
		 }
		 arrValueText[0] = str1;
		 return arrValueText; 
}

//取得所选数据字典Id
function getTallId(){
		var recs = Ext.getCmp('BussGrid').getSelectionModel().getSelections();
		var list = [];
		if(recs.length == 0){
			Ext.Msg.alert('系统提示','请选择要操作的网点配置信息！');
		}else{
			for(var i = 0 ; i < recs.length ; i++){
				var rec = recs[i];					
				list.push(rec.get('tallId'));
			}
		}
		return list;
}	
//单纯取ID值
function getTallIdStr(){
		var recs = Ext.getCmp('BussGrid').getSelectionModel().getSelections();
		var list = [];
		if(recs.length == 0){
			//Ext.Msg.alert('系统提示','请选择要操作的网点配置信息！');
		}else{
			for(var i = 0 ; i < recs.length ; i++){
				var rec = recs[i];					
				list.push(rec.get('tallId'));
			}
		}
		return list;
} 
/**-----------------------------基本操作事件end------------------------------*/


Ext.onReady(function(){
	
	var viewport =  new BussGrid.Layout.Viewport();
	var _BussGrid  = viewport.findById('BussGrid');//Grid
	refresh = function(){//刷新
    	//_BussGrid.store.load();//刷新当前页面
    	_BussGrid.store.load({params:{start:0, limit:99999999},
				  callback : function(r, options, success) {  
				  if (success == false) {
					   Ext.Msg.alert("ERROR",   "加载数据失败，可能原因:连接超时或服务器中断，请尝试重新打开页面."); 
				  } else {//Store 加载成功后
					 /**
					 var count = _BussGrid.store.getCount();
					 if (count == 0){
							var msgTip = Ext.MessageBox.show({
									title:'系统提示',
									width : 250,
									msg:'该客户级别下没有同步产品价格,请按【同步产品价格】按钮,即可以该客户级别下的产品重新定价!'
					 		});
					 }
					 **/
				  }
				}});//刷新当前页面
    }

    _refreshTree();
    _BussGrid.on("afteredit",afterEdit,this);//编辑完成事件
});

function _refreshTree(){
	Ext.getCmp('treepanel').expandAll();//树默认全部展开
    //Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
   // Ext.getCmp('treepanel').getRootNode().select();
}



</script>