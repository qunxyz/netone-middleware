<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>客户关联选择页面</title>
	</head>
	<body>
	<input type="hidden" name="departmentCode" id="departmentCode" /><!-- 部门编号 -->
	<div id="mainDiv" style="text-align: center;">
		<table>
			<tr>
				<td>
					<fieldset>
						<select id="select" name="select" multiple="multiple" size="25"
							style="width: 250px;" ondblclick="moveSelected1();">
						</select>
					</fieldset>
				</td>
				<td>
					<div>
						<table>
							<tr>
								<td>
									<input type="button" name="add" id="add" value="←"
										style="width: 60px" onClick="moveSelected();" />
								</td>
							</tr>
							<tr>
								<td>
									<input style="width: 60px" name="delete" id="delete"
										type="button" value="→" onClick="moveSelected1();" />
								</td>
							</tr>
							<tr>
								<td>
									<input type="button" name="all" id="all" value="全部选择"
										style="width: 60px;"  onClick="loadAndClearAll('loadAll');">
								</td>
							</tr>
							<tr>
								<td>
									<input type="button" name="clearAll" id="clearAll" value="全部清空"
										style="width: 60px;"  onclick="loadAndClearAll('clearAll');">
								</td>
							</tr>
						</table>
					</div>
				</td>
				<td>
					<fieldset>
						<select id="unselect" name="unselect" multiple="multiple"
							size="25" style="width: 250px;" ondblclick="moveSelected();">
						</select>
					</fieldset>
				</td>
			</tr>
		</table>
		<BR>
		<input type="button" id="confirmBtn" value="确定"/>
		&nbsp;&nbsp;&nbsp;
		<input type="button" id="closeBtn" value="关闭"/>
	</div>
	</body>
</html>
<script type="text/javascript">
Ext.QuickTips.init();

Ext.ns('Client.Layout');

Client.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
   
   var treeDataUrl = "<c:url value='/client/client.do?method=onFindDepartmentTree' />";
     
   var config = {
		 collapsible:true,
		 autoWidth:true,
		 layout:"border", 
		 
		 items:[{
		 			id:'treepanel',
		 			xtype:'treepanel',
		 			region:"west",
		 			width:150,
		 			animCollapse :false,
		 			animate :false,//去除动画
　　　					autoScroll:true,
					border : true, // 边框
					useArrows :true,
				    loader: new Ext.tree.TreeLoader({dataUrl: treeDataUrl}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:'root',text:'福建张裕'}),
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != 'root'){
						       document.getElementById('select').options.length=0;//清空
						       loadClientLevelOfRelation('0','select',node.attributes.id);//加载未关联
						   }
						 }
					}
              	},{
		            region:"center",
					autoScroll:true,
					xtype : "panel",
					contentEl : 'mainDiv'
			    }
		 	]
	}
	         
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	Client.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});

function moveSelected(){//添加
    var oSourceSel=document.getElementById('unselect');
    var oTargetSel=document.getElementById('select');
    var arrSelValue = new Array();//建立存储value和text的缓存数组
    var arrSelText = new Array();
    var arrValueTextRelation = new Array();//此数组存贮选中的options，以value来对应
    var index = 0;//用来辅助建立缓存数组
    for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
        if(oSourceSel.options[i].selected){
            arrSelValue[index] = oSourceSel.options[i].value;//存储
            arrSelText[index] = oSourceSel.options[i].text;
            arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i];//建立value和选中option的对应关系
            index++;
        }
    }
    for(var i=0; i<arrSelText.length; i++){  //增加缓存的数据到目的列表框中，并删除源列表框中的对应项
        oTargetSel.options.add(new Option(arrSelText[i],arrSelValue[i]));
        oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);//删除源列表框中的对应项
    }
}

function moveSelected1(){//移除
  	var oSourceSel=document.getElementById('select');
    var oTargetSel=document.getElementById('unselect');
    var arrSelValue = new Array();//建立存储value和text的缓存数组
    var arrSelText = new Array();
    var arrValueTextRelation = new Array();//此数组存贮选中的options，以value来对应
    var index = 0;//用来辅助建立缓存数组
    for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
        if(oSourceSel.options[i].selected){
            arrSelValue[index] = oSourceSel.options[i].value; //存储
            arrSelText[index] = oSourceSel.options[i].text;
            arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i];//建立value和选中option的对应关系
            index++;
        }
    }
    for(var i=0; i<arrSelText.length; i++){//增加缓存的数据到目的列表框中，并删除源列表框中的对应项
        oTargetSel.options.add(new Option(arrSelText[i],arrSelValue[i]));
        oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);//删除源列表框中的对应项
    }
}

function loadAndClearAll(obj){//全选
  	var oSourceSel = '';
	var oTargetSel = '';
	if(obj.trim()=='loadAll'){//<-
	  oSourceSel=document.getElementById('unselect');
	  oTargetSel=document.getElementById('select');
	}
	if(obj.trim()=='clearAll'){//->
	  oSourceSel=document.getElementById('select');
      oTargetSel=document.getElementById('unselect');
	}
     var arrSelValue = new Array();//建立存储value和text的缓存数组
     var arrSelText = new Array();
     var arrValueTextRelation = new Array();//此数组存贮选中的options，以value来对应
     var index = 0;//用来辅助建立缓存数组
     for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
             arrSelValue[index] = oSourceSel.options[i].value;
             arrSelText[index] = oSourceSel.options[i].text;
             arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i]; //建立value和选中option的对应关系
             index++;
     }
     for(var i=0; i<arrSelText.length; i++){//增加缓存的数据到目的列表框中，并删除源列表框中的对应项
         oTargetSel.options.add(new Option(arrSelText[i],arrSelValue[i]));
         oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);
     }
}

/**
* 加载客户级别的客户关联信息
* @param related 1.已关联 0.未关联
* @param o 加载客户关联信息的SELECT对象ID
* @param id 当前选择树的ID
*/
function loadClientLevelOfRelation(related,o,id){//查找客户级别的客户关联信息
	var msgTip = Ext.MessageBox.show({
				title:'系统提示',
				width : 250,
				msg:'正在加载未关联级别客户信息......'
 	});
	
	Ext.Ajax.request({
		url : "<c:url value='/client/client.do?method=onLoadClientLevelOfRelation' />",// 请求的服务器地址
		params : {
			related : related,
			id : id
		},
		success : function(response, options) {
			msgTip.hide();
			var json = Ext.util.JSON.decode(response.responseText);

			var reader = new Ext.data.JsonReader({
						root : "clientInfo"
					}, [{name : 'clientLoginName'}, {name : 'clientName'}]);
			var data = reader.readRecords(json);
			var records = data.records;
			var count = records.length;

			for (var i = 0; i < count; i++) {
				var rec = records[i];
				var clientCode = rec.get('clientLoginName');
				var clientName = rec.get('clientName');
				text = clientCode+'-'+clientName;
				value = clientCode;
				document.getElementById(o).options.add(new Option(text, value));
			}
		},
		failure: function (response, options) {
		    checkAjaxStatus(response);
		}
	});
}

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

function refreshTree(){//刷新树
	Ext.getCmp('treepanel').root.reload();
	Ext.getCmp('treepanel').expandAll();//树默认全部展开
}

function getSelectedValue(obj){//将options中的对象以逗号分隔
		 var arrValue =  '';
	     var deliter='';
	     
	     var options = obj.options;
	     
		 for(var i=0; i < options.length; i++){
		      arrValue += deliter + options[i].value;
		      deliter=',';
		 }

		 return arrValue;
 }

Ext.onReady(function(){
	
	var viewport =  new Client.Layout.Viewport();
    Ext.getCmp('treepanel').expandAll();//树默认全部展开
    Ext.getCmp('treepanel').getRootNode().select();
    
   	Ext.EventManager.addListener(Ext.get('confirmBtn'), 'click', function(){
		var o = document.getElementById('unselect');
		var selected = '${param.selected}';
		if (selected == '1'){
			opener.setSelectValue(getSelectedValue(o));
		} else if (selected == '0'){
			opener.setUnSelectValue(getSelectedValue(o));
		}
		
		window.close();
	});

   	Ext.EventManager.addListener(Ext.get('closeBtn'), 'click', function(){
		 window.close();
	});
    
});

</script>
