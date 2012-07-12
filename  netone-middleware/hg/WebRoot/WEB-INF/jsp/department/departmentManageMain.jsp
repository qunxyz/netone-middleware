<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%	String path = request.getContextPath();%>	
<%@ include file="../common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
        <jsp:include page="../common/metaJQuery-ui-dialog.jsp"/>
		<script type="text/javascript" src="<%=path%>/theme/ext/extend/gridToExcel.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<title>部门/公司管理</title>
	</head>
	<body>
	<div id="_c_conent">
	    <form id="deptform" action="" method="post">
	      <input type="hidden" id="departmentId" name="departmentId">
	      <input type="hidden" id="originalParentDepartmentId" name="originalParentDepartmentId"><!-- 原始值 -->
	      <input type="hidden" id="level" name="level">
	      <table>
	        <tr>
	          <td nowrap="nowrap">部门/公司编号:</td>
	          <td>
	          	<input type="text" class="text" id="departmentCode" name="departmentCode" style="width: 160px" />
			 </td>
	          <td nowrap="nowrap">&nbsp;&nbsp;部门/公司名称:</td>
	          <td><input type="text" class="text" id="departmentName" name="departmentName"  />
	          </td>
	          <td nowrap="nowrap">&nbsp;&nbsp;排序:</td>
	          <td><input type="text" class="text" id="orders" name="orders"  />
	          </td>
	        </tr>
	        <tr>
	         <td width="80px" height="30">上级部门/公司:<input type="hidden" name="parentDepartmentId" id="parentDepartmentId" /> </td>
	          <td colspan="3">
	          	<div id="comboBoxTree"></div>
	          </td>
	         </tr>
	         <!-- 
			<tr>
				<td colspan="4">
					<input type="hidden" id="bussType" name="bussType">
					部门/公司类型:<span id="bussTypeName">区域</span>
					&nbsp;&nbsp;&nbsp;<span id="statusName"></span>
				</td>
			</tr>
			 -->	         
	      </table>
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
									onclick="_exportSelltar('html');"/>
				<input id="queryBtn" type="button" value=" 下 载 " class="btn"
					onclick="_exportSelltar('excel');"/>
			</td>
			</tr>
		</table>
	</div>
	</body>
</html>
<script type="text/javascript">
var nodeid='${node}';
var nodename='${nodeName}';
nodename = nodename==''?'组织机构':nodename;
var nodecode='${nodeCode}';
var parentnodeid='${parentNode}';
var categoriesId,categoriesName;
Ext.QuickTips.init();

 Ext.ns('Buss.Data');
  Buss.Data.BussGrid  =  Ext.extend(Ext.grid.GridPanel,{
		 initComponent: function() {
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	});
			var config = {
			    sm : cb,
			   // frame:true,
			    border:false,
				store : new Ext.data.Store({
							url : "<c:url value='/department/user.do?method=onListUser' />", // JSON数据
							reader : new Ext.data.JsonReader(
										{totalProperty : 'total',root : 'rows'}, [
										{name: 'userId'},
										{name: 'userCode'},
										{name: 'userName'},
										{name: 'departmentName'},
										{name: 'types'},
										{name: 'status'}
									])
						}),
				columns : [index,
					cb, 
					{header: "用户编码", align:'center',dataIndex: 'userCode'},
					{header: "用户名称", align:'center',dataIndex: 'userName'},
					{header: "隶属部门", align:'center', dataIndex: 'departmentName'},
					{header: "业务类型", align:'center', dataIndex: 'types'},
					{header: "状态", align:'center', dataIndex: 'status',
						renderer : function todo(value, cellmeta, record,
									rowIndex, columnIndex, store) {
								var status = record.get('status');
								if (status == 1) {
									return '启用';
								} else if (status == 0) {
									return '禁用';
								} else {
									return '启用';
								}
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
			     displayInfo:false,
			     pageSize:15,
			     displayMsg : '第 {0} 条到 {1} 条，一共 {2} 条',
				 emptyMsg : "没有记录"
			  });
		  // call parent
		  Buss.Data.BussGrid.superclass.initComponent.apply(this, arguments);
	      this.store.on('beforeload', function(store,options){
				  var condition = {
				   departmentId: Ext.get('departmentId').getValue(),
				   userCode:''
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
  
Ext.ns('BussGrid.Layout');

BussGrid.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
   var fobidFunctionMenu = new Ext.menu.Menu({
		id : 'fobidFunctionMenu',
		text : '帐号',
		items : [
		{text : '启用',iconCls:'',handler:_fobidFunction1},
		{text : '禁用',iconCls:'',handler:_fobidFunction0}
		]
	});	
   var fobidFunctionMenu2 = new Ext.menu.Menu({
		id : 'fobidFunctionMenu2',
		text : '帐号',
		items : [
		{text : '启用',iconCls:'',handler:_fobidUserFunction1},
		{text : '禁用',iconCls:'',handler:_fobidUserFunction0}
		]
	});		
   var treeDataUrl = "<c:url value='/department/department.do?method=onFindDeptTree&show=1' />";
     
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
		 			animate :false,//去除动画
　　　				autoScroll:true,
					border : true, // 边框
					useArrows :true,
				    loader: new Ext.tree.TreeLoader({dataUrl: treeDataUrl}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:nodeid,text:nodename}),
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != nodeid){
						     Ext.get('departmentId').dom.value = node.attributes.departmentId;
						     Ext.get('orders').dom.value = node.attributes.orders;
						     Ext.get('parentDepartmentId').dom.value = node.attributes.parentDepartment.departmentId==0?"":node.attributes.parentDepartment.departmentId;
						     Ext.get('originalParentDepartmentId').dom.value = Ext.get('parentDepartmentId').getValue();
						     Ext.get('departmentCode').dom.value = node.attributes.departmentCode;
						     Ext.get('departmentName').dom.value = node.attributes.departmentName;
						     Ext.get('level').dom.value = node.attributes.level;
						     checkedRadio(node.attributes.level);
						     deptTree.comboBoxTree.setValue({id: node.attributes.parentDepartment.departmentId, text: node.attributes.parentDepartment.departmentName});
						     _isvisibleBtn(true);
						     _showStatus(node.attributes.status);
						     refresh();
						   } else {//选择根结点
						   	 Ext.get('orders').dom.value = '';
						     Ext.get('departmentId').dom.value = nodeid;
						     Ext.get('parentDepartmentId').dom.value = parentnodeid;
						     Ext.get('originalParentDepartmentId').dom.value = parentnodeid;
						     Ext.get('departmentCode').dom.value = nodecode;
						     Ext.get('departmentName').dom.value = nodename;
						     Ext.get('level').dom.value = '';
						     checkedRadio('-1');
						     deptTree.comboBoxTree.setValue({id: nodeid, text: nodename});
						     _isvisibleBtn(false);
						     _showStatus(node.attributes.status);
						     if (nodeid!='' || nodeid!='0') refresh();
						   }
						}
					}
              	},{
		            region:"center",
					autoScroll:true,
					layout:"border", 
					items: [{
						xtype : "panel",
						region : "north",
						id : "north",
						title: "组织机构信息管理",
						frame: false,
						contentEl : '_c_conent',// 加载本地资源
						style : "padding:10px",
						tbar:new Ext.Toolbar([
							 {text : '刷新',iconCls:'refreshIcon',handler:_refreshLoad},
							 {text : '新建',id:'_addDept',iconCls:'addIcon',handler:_addDept},
							 {text : '保存',id:'_saveDept',iconCls:'saveIcon',handler:_saveDept},
							 {text : '删除',id:'_deleteDept',iconCls:'deleteIcon',handler:_deleteDept},
							 {text : '帐号',iconCls:'manageIcon',menu :fobidFunctionMenu}
							 //{text : '客户信息',id:'_editClient',iconCls:'addUserIcon',handler:_editClient}
							 <c:if test="${adminx == 'adminx'}">
								,{text : '更新扩展',id:'_initExtData',iconCls:'saveIcon',handler:_initExtData}
							 </c:if>
						])
					  },{
						id:'BussGrid',
		            	xtype:'BussGrid',
		            	title: "组织机构下属人员管理",
		            	region:"center",
		            	autoScroll:true
		            	,tbar:new Ext.Toolbar([
							 {text : '新建',iconCls:'addIcon',handler:_addUser},
							 {text : '修改',iconCls:'editIcon',handler:_editUser},
							 {text : '删除',iconCls:'deleteIcon',handler:_deleteUser},
							 {text : '帐号',iconCls:'manageIcon',menu :fobidFunctionMenu2}
						])
					}]
				}
	  		]
	}
	
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	BussGrid.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});

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

var deptTree={//加载树
   url: '<c:url value="/department/department.do?method=onFindDeptTree" />',
   
     init: function(){
     this.comboBoxTree = new Ext.ux.ComboBoxTree({//上级分类选项
				renderTo : 'comboBoxTree',
				width : 430,
				tree : {
					xtype:'treepanel',
				    loader: new Ext.tree.TreeLoader({dataUrl: this.url}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:nodeid,text:nodename}),
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != nodeid){
						       Ext.get('parentDepartmentId').dom.value = node.attributes.id;	
						   }else{
						       Ext.get('parentDepartmentId').dom.value = nodeid;
						       deptTree.comboBoxTree.setValue({id: nodeid, text: nodename});
						   }
						 }
					 }
		       	 
		    	},
		    	//all:所有结点都可选中
				//exceptRoot：除根结点，其它结点都可选(默认)
				//folder:只有目录（非叶子和非根结点）可选
				//leaf：只有叶子结点可选
				selectNodeModel:'folder'
      });
  	},		  
    clear: function(){
	     var comboBoxTree = document.getElementById('comboBoxTree');
		 comboBoxTree.innerHTML = '';  
	},
	  
	setValue: function(value){
	     this.comboBoxTree.setValue(value); 
	}
 }

Ext.onReady(function(){
	
	var viewport =  new BussGrid.Layout.Viewport();
	var _BussGrid  = viewport.findById('BussGrid');//Grid
    refresh = function(){//刷新
    	Ext.getCmp('BussGrid').store.load({params:{start:0, limit:15}});//刷新当前页面
    }
	 deptTree.init();
    _refreshTree();
    _isvisibleBtn();
    <c:if test="${sgs =='sgs'}">
	_BussGrid.addListener('celldblclick', function(grid, rowIndex,
		columnIndex, e) {// 列表双击事件
		_editUser();
	});
	</c:if>
});

function _refreshLoad(){//刷新分类树-重新加载树
	Ext.getCmp('treepanel').root.reload();//树根节目重新加载
	Ext.getCmp('treepanel').root.select();
    Ext.get('departmentId').dom.value = '';
    Ext.get('parentDepartmentId').dom.value = '';
    Ext.get('departmentCode').dom.value = '';
    Ext.get('departmentName').dom.value = '';
    Ext.get('level').dom.value = '';
    checkedRadio('-1');
    deptTree.comboBoxTree.setValue({id: nodeid, text: nodename});
    _isvisibleBtn(false);
    Ext.getCmp('BussGrid').store.removeAll();//移除
}

function _reloadTree(pid){
	if (pid==null || pid=='') pid=nodeid;
	var node=Ext.getCmp('treepanel').getNodeById(pid);//id  是被刷新的结点编号
	node.reload();
	node.select();

}

function _refreshTree(){
	//Ext.getCmp('treepanel').expandAll();//树默认全部展开
    Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
    //Ext.getCmp('treepanel').getRootNode().select();
}

function _isvisibleBtn(t){//功能禁用或开启
	 if (t==null) t=false;
     if (Ext.getCmp('_saveDept')) Ext.getCmp('_saveDept').setVisible(t);
	 if (Ext.getCmp('_deleteDept')) Ext.getCmp('_deleteDept').setVisible(t); 
}

function checkedRadio(value){//选中
   /**
   document.getElementById("bussType").value=value;
   var o = $('#bussTypeName');
   var d = $('#departmentCode');
   if (value==-1){
   	 o.html('区域');
   	 d.attr('readonly','');
   } else if (value==1){
     o.html('省公司');
     d.attr('readonly','');
   } else if (value==2){
   	 o.html('大营销部');
   	 d.attr('readonly','');
   } else if (value==3){
   	 o.html('营销部');
   	 d.attr('readonly','');
   } else if (value==4){
     o.html('经销商');
     d.attr('readonly','readonly');
   } else if (value==5){
     o.html('分销商');
     d.attr('readonly','readonly');
   } else if (value=='s'){
     o.html('仓库');
     d.attr('readonly','');
   } else {
   	 o.html('未知');
   	 d.attr('readonly','');
   }
   **/
}

function _addDept(){
 	 openModalDialogWindow('新增',"<c:url value='/department/department.do?method=onEditView' />",550,180);
}

function _saveDept(){
    var departmentId = Ext.get('departmentId').getValue();
    var parentDepartmentId = Ext.get('parentDepartmentId').getValue();
	if (departmentId=='') return;
	var str = '保存部门或公司失败!提示如下:\n';
	var i=1;
	var blank = '';
	if(Ext.get('departmentCode').dom.value == ''){
	   str+= blank+ i+ '、此部门或公司编号为空。\n';
	   i++;
	}
	if(Ext.get('departmentName').dom.value == ''){
	   str+= blank+ i+ '、此部门或公司名称为空。';
	   i++;
	}
	if(i>1){
	  	Ext.MessageBox.alert('系统提示',str);
	   return;
	}
	   	var msgTip = Ext.MessageBox.show({
		title:'系统提示',
		width : 200,
		msg:'正在保存信息请稍后......'
			});
    Ext.Ajax.request({
			url :"<c:url value='/department/department.do?method=onSaveOrUpdate' />",//请求的服务器地址
			form : 'deptform',//指定要提交的表单id
			method : 'POST',
			success : function(response,options){
				msgTip.hide();
				var result = Ext.util.JSON.decode(response.responseText);
				if(result.error==null){
					Ext.ux.Toast.msg("", result.tip);
					_reloadTree(parentDepartmentId);
					refreshComboBoxTree(parentDepartmentId);
				}else{
					Ext.MessageBox.alert('提示',result.tip);
				}
			},
			failure : function(response,options){
				msgTip.hide();
				checkAjaxStatus(response);
				var result = Ext.util.JSON.decode(response.responseText);
				Ext.MessageBox.alert('系统提示',result.tip);
			}
　   });

}

function _deleteDept(){
	var departmentId = Ext.get('departmentId').getValue();
	if (departmentId=='') return;
	 Ext.MessageBox.confirm("系统提示",'您确定要删除所选择数据吗？',function(btnId){
			if(btnId == 'yes'){
	
	  		var msgTip = Ext.MessageBox.show({
				title:'系统提示',
				width : 250,
				msg:'正在执行操作请稍后......'
			});
			Ext.Ajax.request({
						url : '<c:url value="/department/department.do?method=onDelete" />',
						params : {departmentId:departmentId},
						method : 'POST',
						success : function(response,options){
								msgTip.hide();
						var result = Ext.util.JSON.decode(response.responseText);
						if(result.error==null){
							Ext.ux.Toast.msg("", result.tip);
							var parentDepartmentId = Ext.get('parentDepartmentId').getValue();
							if (parentDepartmentId=='') parentDepartmentId=nodeid;
							_reloadTree(parentDepartmentId);
							refreshComboBoxTree(parentDepartmentId);
							Ext.getCmp('treepanel').root.select();
						    Ext.get('departmentId').dom.value = '';
						    Ext.get('parentDepartmentId').dom.value = '';
						    Ext.get('departmentCode').dom.value = '';
						    Ext.get('departmentName').dom.value = '';
						    Ext.get('level').dom.value = '';
						    checkedRadio('-1');
						    deptTree.comboBoxTree.setValue({id: '', text: ''});
						    _isvisibleBtn(false);
						}else{
							Ext.MessageBox.alert('提示',result.tip);
						}
				},
				failure : function(response,options){
						msgTip.hide();
						checkAjaxStatus(response);
						Ext.MessageBox.alert('提示','删除信息请求失败！');
				}
			});	
		}
	 });
		
}

function _editClient(){
	 if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择一个节点!');return;};
	 var clientId = Ext.get('departmentId').getValue();
	 var levelClient = Ext.get('level').getValue();
	 var url = null;
	 if(levelClient=='5'){
	 	url = '<c:url value='/client/client.do?method=onEditChildView'/>' +  '&clientId=' + clientId;
	 }else{
     	url = '<c:url value='/client/client.do?method=onEditClientInfoView'/>' +  '&clientId=' + clientId;
     }
     openModalDialogWindow('修改客户信息',url,620,360); 	 
}

function _editClientTarget(){
	 if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择一个节点!');return;};
	 var clientId = Ext.get('departmentId').getValue();
     var url = '<c:url value='/client/sellTarget.do?method=onMainSellTargetView'/>' +  '&id=' + clientId;
     openModalDialogWindow('修改客户指标',url,800,400);
}

function _editProductPrice(){
	 if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择一个节点!');return;};
	 var clientId = Ext.get('parentDepartmentId').getValue();
	 if (clientId=='') clientId='0';
     var url = '<c:url value='/products/productPrice.do?method=onPriceGridMainView'/>' +  '&id=' + clientId;
     openModalDialogWindow('修改',url,820,420,'auto');
}
function _exportExcelByC(){//导出(excel)
	if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择一个节点!');return;};
	var url = '<c:url value='/client/client.do?method=onExport' />&format=excel'+'&id='+Ext.get('departmentId').getValue();
	openWinCenter('导出经销商基本信息',url, 300,250);
}
function _exportHtml(){//导出(html)
	if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择一个节点!');return;};
	var url = '<c:url value='/client/client.do?method=onExport' />&format=html'+'&id='+Ext.get('departmentId').getValue();
	window.open(url);
}
function _importClient(){//导入经销商信息
	var url = "<c:url value='/client/import.do?method=onImportClientInfoView'/>";
	window.open(url);
}
function __exportExcelByC(){//分销商导出(excel)
	if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择一个节点!');return;};
	var url = '<c:url value='/client/client.do?method=onExport' />&format=excel'+'&ex_type=client_'+'&id='+Ext.get('departmentId').getValue();
	openWinCenter('导出分销商基本信息',url, 300,250);
}
function __exportHtml(){//分销商导出(html)
	if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择一个节点!');return;};
	var url = '<c:url value='/client/client.do?method=onExport' />&format=html'+'&ex_type=client_'+'&id='+Ext.get('departmentId').getValue();
	window.open(url);
}
function __importExcelByC(){//导入分销商信息
	var url = "<c:url value='/client/import.do?method=onImportChildClientView'/>";
	window.open(url);
}
function _exportSelltar(t){//导出(excel)经销商销售指标
	if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择一个节点!');return;};
	var beginTime = document.getElementById('beginTime').value;
	var endTime = document.getElementById('endTime').value;
	
	var url = '<c:url value='/client/sellTarget.do?method=onExport' />&format='+t+'&beginTime='+beginTime+'&endTime='+endTime+'&id='+Ext.get('departmentId').getValue();
	if(t=='excel'){
		openWinCenter('导出经销商销售指标',url, 300,250);
	}else{
		window.open(url);
	}
}
function _importClientTatget(){//导入客户指标
	var url = "<c:url value='/client/import.do?method=onImportClientTarget'/>";
	window.open(url);
}

function refreshComboBoxTree(pid){
	if (pid==null || pid=='') pid=nodeid;
	deptTree.comboBoxTree.reloadNode(pid);
}

function _openTagPage(){
	$('#divQuery').dialog({
		title : '经销商销售指标',
		'bgiframe': true,
		'modal': true,
		resizable: false,
		draggable: true,
		width: 250,
		height: 150,
		position: 'center',
		beforeclose: function(event, ui) {
			//alert('close')
		},
		close: function(event, ui) {
			$('#divQuery').dialog("destroy");
		}
	});
}

function _addUser(){//新增
    var url = "<c:url value='/department/user.do?method=onEditUserView' />"+'&type=1';
    openWinCenter('新增',url,1000,400,true);
}

function _editUser(){//修改
	 var selectionSet = Ext.getCmp('BussGrid').getSelectionModel().getSelections(); 
	 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
		Ext.MessageBox.alert('系统提示','请选择一条记录进行修改!');
		return ;
	 }
     var userId=selectionSet[0].data.userId;
     var url = '<c:url value="/department/user.do?method=onEditUserView"/>' +'&type=1&userId='+userId;
     openWinCenter('修改',url,1000,400,false);
}
function _getId(){//取得所选数Id
	var recs = Ext.getCmp('BussGrid').getSelectionModel().getSelections();
	var list = [];
	if(recs.length == 0){
		Ext.Msg.alert('系统提示','请选择要进行操作的信息！');
	}else{
		for(var i = 0 ; i < recs.length ; i++){
			var rec = recs[i];
			list.push(rec.get('userId'));
		}
	}
	return list;
}

function _getCode(){//取得所选数Id
	var recs = Ext.getCmp('BussGrid').getSelectionModel().getSelections();
	var list = [];
	if(recs.length == 0){
		Ext.Msg.alert('系统提示','请选择要进行操作的信息！');
	}else{
		for(var i = 0 ; i < recs.length ; i++){
			var rec = recs[i];
			list.push(rec.get('userCode'));
		}
	}
	return list;
}

function _deleteUser(){//删除数据
     var arr = _getId();
     var arr2 = _getCode();
     var num = arr.length;
     if(num >= 1){
     	  Ext.MessageBox.confirm("系统提示",'您确定要删除所选择数据吗？',function(btnId){
			if(btnId == 'yes'){
				var str1="";
			    for(var i= 0 ; i < num ; i ++){
			        str1 = str1 + arr[i]+",";
			    }
			    str1 = Ext.util.Format.substr(str1,0,str1.length - 1);
			    var str2="";
			    for(var i= 0 ; i < num ; i ++){
			        str2 = str2 + arr2[i]+",";
			    }
			    str2 = Ext.util.Format.substr(str2,0,str2.length - 1);
				Ext.Ajax.request({//AJAX删除
						url : '<c:url value='/department/user.do?method=onDeleteUser' />',
						params : {userId : str1,userCode : str2},
						method : 'POST',
						success : function(response,options){
							var result = Ext.util.JSON.decode(response.responseText);
							if(result.error==null){
								Ext.ux.Toast.msg("", result.tip);
								refresh();
							}else{
								Ext.MessageBox.alert('提示',result.tip);
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
}

function _initExtData(){
	Ext.getCmp('_initExtData').disable(); 
	Ext.MessageBox.confirm("系统提示",'您确定要执行操作吗？',function(btnId){
		if(btnId == 'yes'){
			Ext.Ajax.request({
				sync:false,
				url : '<c:url value="/department/department.do?method=onInitExtData" />'
			});
		}
	});	
}
function _fobidFunction(did,uid,s){//禁用、开启功能
	Ext.MessageBox.confirm("系统提示",'您确定要执行操作吗？',function(btnId){
			if(btnId == 'yes'){
				Ext.Ajax.request({//AJAX删除
						url : '<c:url value='/department/department.do?method=onFobidFunction' />',
						params : {departmentId : did,userId:uid,s:s},
						method : 'POST',
						success : function(response,options){
							var result = Ext.util.JSON.decode(response.responseText);
							if(result.error==null){
								Ext.ux.Toast.msg("", result.tip);
								refresh();
							}else{
								Ext.MessageBox.alert('提示',result.tip);
							}
						},
						failure : function(response,options){
							checkAjaxStatus(response);
							Ext.Msg.alert('提示','操作失败');
						}
				});
			}
	});
}
function _fobidFunction1(){
	if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择一个节点!');return;};
	var did = Ext.get('departmentId').getValue();
	_fobidFunction(did,'',1);
	_showStatus(1);
}
function _fobidFunction0(){
	if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择一个节点!');return;};
	var did = Ext.get('departmentId').getValue();
	_fobidFunction(did,'',0);
	_showStatus(0);
}

function _fobidUserFunction1(){
	 var selectionSet = Ext.getCmp('BussGrid').getSelectionModel().getSelections(); 
	 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
		Ext.MessageBox.alert('系统提示','请选择一条记录进行操作!');
		return ;
	 }
     var userId=selectionSet[0].data.userId;
	 _fobidFunction('',userId,1);
}
function _fobidUserFunction0(){
	 var selectionSet = Ext.getCmp('BussGrid').getSelectionModel().getSelections(); 
	 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
		Ext.MessageBox.alert('系统提示','请选择一条记录进行操作!');
		return ;
	 }
     var userId=selectionSet[0].data.userId;
	 _fobidFunction('',userId,0);
}

function _showStatus(status){
	if (status=='0'){
		$('#statusName').html('<font color=\"red\">禁用</font>');
	} else {
		$('#statusName').html('');
	}
}

function checkTreeIsSelected(){//检查树是否被选择
	var pass = false;
	var selNode = Ext.getCmp('treepanel').getSelectionModel().getSelectedNode();
	if (selNode!=null){
		pass = true;
	} else {
		pass = false;
	}
	return pass;
	//if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择一个节点!');return;};	
}
</script>
