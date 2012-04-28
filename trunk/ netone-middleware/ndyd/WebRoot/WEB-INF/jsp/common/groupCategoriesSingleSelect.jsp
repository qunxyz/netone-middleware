<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="metaExt.jsp"></jsp:include>
		<script type="text/javascript" src="<%=basePath%>/theme/ext/extend/TreeCheckNode.js"></script>
		<title>产品类别分组选择管理</title>
	</head>
	<body>
	<div id="clientLevelRelation" class="panel" style="text-align:left;display: none;">
		<table>
			<tr>
				<td align="center">产品分类列表</td>
			</tr>
			<tr>
				<td>
					<fieldset>
						<select id="select" name="select" multiple="multiple" size="25"
							style="width: 200px;" disabled="disabled">
						</select>
					</fieldset>
				</td>
			</tr>
		</table>
	</div>
	</body>
</html>
<script type="text/javascript">
Ext.QuickTips.init();

Ext.ns('GroupManage.Layout');

GroupManage.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
   
   var treeDataUrl = "<c:url value='/products/group.do?method=onFindGroupTree' />";
     
   var config = {
		 collapsible:true,
		 autoWidth:true,
		 border:false,
		 layout:"border", 
		 
		 items:[{
		 			id:'treepanel',
		 			xtype:'treepanel',
		 			region:"center",
		 			width:200,
		 			animCollapse :false,
		 			animate :false,//去除动画
　　　				autoScroll:true,
					border : true, // 边框
					useArrows :true,
				    loader: new Ext.tree.TreeLoader({dataUrl: treeDataUrl}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:'root',text:'分组树'}),
		       	 	rootVisible:true,//是否显示分组树这个标头
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != 'root'){
						       document.getElementById('select').options.length=0;//清空
						       //loadGroupOfRelation('1','select',node.attributes.id);//加载已关联
						   }
						 },
						 dblclick : function(node,e){
						   if(node.attributes.id != 'root'){
						     opener._select(node.attributes.text,node.attributes.text);
						     window.close();
						   }
						 }
					}
              	}/**,{
              		id:'leverConfig',
		            region:"center",
		            xtype:'panel',
					border:false,
					hideBorders:true,
					//height:clientHeight,
	            	autoScroll:true,
	            	contentEl:'clientLevelRelation'
			  }*/
		 	]
	}
	         
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	GroupManage.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});

function checkTreeIsSelected(){//检查树是否被选择
	var selNode = Ext.getCmp('treepanel').getSelectionModel().getSelectedNode();
	if (selNode!=null){
		if (selNode.id!='root'){
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}

/**
* 加载组别的类别关联信息
* @param related 1.已关联 0.未关联
* @param o 加载类别信息的SELECT对象ID
* @param id 当前选择树的ID
*/
function loadGroupOfRelation(related,o,id){//组别的类别关联信息
	Ext.Ajax.request({
		url : "<c:url value='/products/group.do?method=onLoadGroupOfRelation' />",// 请求的服务器地址
		params : {
			related : related,
			id : id
		},
		success : function(response, options) {
			var json = Ext.util.JSON.decode(response.responseText);

			var reader = new Ext.data.JsonReader({
						root : "productCategories"
					}, [{name : 'categoriesId'},{name : 'categoriesCode'}, {name : 'categoriesName'}]);
			var data = reader.readRecords(json);
			var records = data.records;
			var count = records.length;

			for (var i = 0; i < count; i++) {
				var rec = records[i];
				var categoriesId = rec.get('categoriesId');
				var categoriesCode = rec.get('categoriesCode');
				var categoriesName = rec.get('categoriesName');
				text = categoriesCode+' - '+categoriesName;
				value = categoriesId;
				document.getElementById(o).options.add(new Option(text, value));
			}
		},
		failure: function (response, options) {
		    checkAjaxStatus(response);
		}
	});
}

function refreshTree(){//刷新树
	Ext.getCmp('treepanel').root.reload();
	Ext.getCmp('treepanel').expandAll();//树默认全部展开
}

function getSelectNodesAttributes(attributesName){// 获取所有选中的节点的指定的属性的值
        var tArray = [];  
        var selectNodes = Ext.getCmp('treepanel').getChecked();  
        var ln =selectNodes.length;  
        for(var i=0;i<ln;i++)  
        {             
            eval("tArray[tArray.length]=selectNodes[i].attributes."+attributesName);  
        }  
        return tArray;  
}

Ext.onReady(function(){
	
	var viewport =  new GroupManage.Layout.Viewport();
	
    Ext.getCmp('treepanel').expandAll();//树默认全部展开
    Ext.getCmp('treepanel').getRootNode().select();
    
});

</script>
