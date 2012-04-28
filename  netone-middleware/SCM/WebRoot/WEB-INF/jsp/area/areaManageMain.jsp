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
		<title>行政区划管理</title>
	</head>
	<body>
	<div id="_c_conent">
	    <form id="areaform" action="" method="post">
	      <input type="hidden" id="areaId" name="areaId">
	      <input type="hidden" id="level" name="level">
	      <table>
	        <tr>
	          <td nowrap="nowrap">行政区划编号:</td>
	          <td>
	          	<input type="text" class="text" id="areaCode" name="areaCode" style="width: 160px" />
			 </td>
	          <td nowrap="nowrap">&nbsp;&nbsp;行政区划名称:</td>
	          <td><input type="text" class="text" id="areaName" name="areaName"  />
	          </td>
	        </tr>
	        <tr>
	         <td width="80px" height="30">上级行政区划:<input type="hidden" name="parentAreaId" id="parentAreaId" /> </td>
	          <td colspan="3">
	          	<div id="comboBoxTree"></div>
	          </td>
	         </tr>
	         <!-- 
			<tr>
				<td colspan="4">
					<input type="hidden" id="bussType" name="bussType">
					行政区划类型:<span id="bussTypeName">区域</span>
				</td>
			</tr>
			 -->	         
	      </table>
	    </form>	
	</div>
	</body>
</html>
<script type="text/javascript">
var nodeid='0';
var nodename='行政区划';
var nodecode='0';
var parentnodeid='0';
Ext.QuickTips.init();

Ext.ns('BussGrid.Layout');

BussGrid.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
   var areaMenu = new Ext.menu.Menu({
		id : 'areaMenu',
		text : '区域信息操作',
		items : [
		{text : '导出区域信息',id:'exportArea',iconCls:'excelIcon',handler : _exportExcel},
		{text : '导入区域信息',id:'importArea',iconCls:'import',handler : _importExcel}
		]
	});
   var treeDataUrl = "<c:url value='/department/area.do?method=onFindAreaTree' />";
     
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
						     Ext.get('areaId').dom.value = node.attributes.areaId;
						     Ext.get('parentAreaId').dom.value = node.attributes.parentArea.areaId==0?"":node.attributes.parentArea.areaId;
						     Ext.get('areaCode').dom.value = node.attributes.areaCode;
						     Ext.get('areaName').dom.value = node.attributes.areaName;
						     Ext.get('level').dom.value = node.attributes.level;
						     areaTree.comboBoxTree.setValue({id: node.attributes.parentArea.areaId, text: node.attributes.parentArea.areaName});
						     _isvisibleBtn(true);
						   } else {//选择根结点
						     Ext.get('areaId').dom.value = nodeid;
						     Ext.get('parentAreaId').dom.value = parentnodeid;
						     Ext.get('areaCode').dom.value = nodecode;
						     Ext.get('areaName').dom.value = nodename;
						     Ext.get('level').dom.value = '';
						     areaTree.comboBoxTree.setValue({id: nodeid, text: nodename});
						     _isvisibleBtn(false);
						   }
						}
					}
              	},{
		            region:"center",
					autoScroll:true,
					layout:"border", 
					items: [{
						xtype : "panel",
						region : "center",
						id : "north",
						title: "行政区划信息管理",
						frame: false,
						contentEl : '_c_conent',// 加载本地资源
						style : "padding:10px",
						tbar:new Ext.Toolbar([
							 {text : '刷新',iconCls:'refreshIcon',handler:_refreshLoad},
							 {text : '新建',id:'_addArea',iconCls:'addIcon',handler:_addArea},
							 {text : '保存',id:'_saveArea',iconCls:'saveIcon',handler:_saveArea},
							 {text : '删除',id:'_deleteArea',iconCls:'deleteIcon',handler:_deleteArea},
							 {text : '关联公司',iconCls:'manageIcon',handler:_openAreaADepartmentMain},
							 {text : '其它操作',iconCls:'manageIcon',menu : areaMenu}
						])
					  }]
				}
	  		]
	}
	
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	BussGrid.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});

var areaTree={//加载树
   url: '<c:url value="/department/area.do?method=onFindAreaTree" />',
   
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
						       Ext.get('parentAreaId').dom.value = node.attributes.id;	
						   }else{
						       Ext.get('parentAreaId').dom.value = nodeid;
						       areaTree.comboBoxTree.setValue({id: nodeid, text: nodename});
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
	 areaTree.init();
    _refreshTree();
    _isvisibleBtn();
});

function _refreshLoad(){//刷新分类树-重新加载树
	Ext.getCmp('treepanel').root.reload();//树根节目重新加载
	Ext.getCmp('treepanel').root.select();
    Ext.get('areaId').dom.value = '';
    Ext.get('parentAreaId').dom.value = '';
    Ext.get('areaCode').dom.value = '';
    Ext.get('areaName').dom.value = '';
    Ext.get('level').dom.value = '';
    areaTree.comboBoxTree.setValue({id: nodeid, text: nodename});
    _isvisibleBtn(false);
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
    Ext.getCmp('treepanel').getRootNode().select();
}

function _isvisibleBtn(t){//功能禁用或开启
	 if (t==null) t=false;
     Ext.getCmp('_saveArea').setVisible(t);
	 Ext.getCmp('_deleteArea').setVisible(t); 
}

function _addArea(){
 	 openModalDialogWindow('新增',"<c:url value='/department/area.do?method=onEditView' />",550,180);
}

function _saveArea(){
    var areaId = Ext.get('areaId').getValue();
    var parentAreaId = Ext.get('parentAreaId').getValue();
	if (areaId=='') return;
	var str = '保存行政区划失败!提示如下:\n';
	var i=1;
	var blank = '';
	if(Ext.get('areaCode').dom.value == ''){
	   str+= blank+ i+ '、此行政区划编号为空。\n';
	   i++;
	}
	if(Ext.get('areaName').dom.value == ''){
	   str+= blank+ i+ '、此行政区划名称为空。';
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
			url :"<c:url value='/department/area.do?method=onSaveOrUpdate' />",//请求的服务器地址
			form : 'areaform',//指定要提交的表单id
			method : 'POST',
			success : function(response,options){
				msgTip.hide();
				var result = Ext.util.JSON.decode(response.responseText);
				if(result.error==null){
					Ext.ux.Toast.msg("", result.tip);
					_reloadTree(parentAreaId);
					refreshComboBoxTree(parentAreaId);
				}else{
					Ext.MessageBox.alert('提示',result.tip);
				}
			},
			failure : function(response,options){
				checkAjaxStatus(response);
				var result = Ext.util.JSON.decode(response.responseText);
				Ext.MessageBox.alert('系统提示',result.tip);
			}
　   });

}

function _deleteArea(){
	var areaId = Ext.get('areaId').getValue();
	if (areaId=='') return;
  	var msgTip = Ext.MessageBox.show({
			title:'系统提示',
			width : 250,
			msg:'正在执行操作请稍后......'
		});
		Ext.Ajax.request({
					url : '<c:url value="/department/area.do?method=onDelete" />',
					params : {areaId:areaId},
					method : 'POST',
					success : function(response,options){
							msgTip.hide();
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.error==null){
						Ext.ux.Toast.msg("", result.tip);
						var parentAreaId = Ext.get('parentAreaId').getValue();
						if (parentAreaId=='') parentAreaId=nodeid;
						_reloadTree(parentAreaId);
						refreshComboBoxTree(parentAreaId);
						Ext.getCmp('treepanel').root.select();
					    Ext.get('areaId').dom.value = '';
					    Ext.get('parentAreaId').dom.value = '';
					    Ext.get('areaCode').dom.value = '';
					    Ext.get('areaName').dom.value = '';
					    Ext.get('level').dom.value = '';
					    areaTree.comboBoxTree.setValue({id: '', text: ''});
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

function refreshComboBoxTree(pid){
	if (pid==null || pid=='') pid=nodeid;
	areaTree.comboBoxTree.reloadNode(pid);
}
function _exportExcel(){//导出(excel)
	var url = '<c:url value='/department/area.do?method=onExportAreaInfo' />&format=excel';
	openWinCenter('导出行政区划地图信息',url, 300,250);
}
function _importExcel(){
	var url = "<c:url value='/department/area.do?method=onImportAreaFile'/>";
	window.open(url);
}

function _openAreaADepartmentMain(){
	var url = '<c:url value='/department/area.do?method=onAreaADepartmentMain' />';
	openWinCenter('关联行政区划与公司',url, 800,600);
}
</script>
