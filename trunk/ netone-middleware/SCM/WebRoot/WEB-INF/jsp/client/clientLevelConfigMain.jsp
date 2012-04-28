<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>级别配置管理</title>
	</head>
	<body>
	<div id="clientLevelRelation" class="panel" style="text-align: center;">
		<table>
			<tr>
				<td>已设置级别的客户</td>
				<td></td>
				<td>未设置级别的客户</td>
			</tr>
			<tr>
				<td>
					<fieldset>
						<select id="select" name="select" multiple="multiple" size="30"
							style="width: 300px;" ondblclick="moveSelected1();">
						</select>
					</fieldset>
					<input type="text" id="findClientCode" name="findClientCode"
									class="titleInfo" 
									title="请输入客户编号(可输入多个客户编号以逗号隔开)" 
									value="请输入客户编号" 
									onfocus="if(this.value=='请输入客户编号') {this.value=''}" 
									onblur="if(this.value=='') this.value='请输入客户编号'"/>
					<input type="button" name="querySelectCode" id="querySelectCode" value="查询" onclick="findClientByCode(this.id)" class="btn"
							onmouseover="this.className='btn_mouseover'" onMouseOut="this.className='btn'" 
        							onmousedown="this.className='btn_mousedown'" onMouseUp="this.className='btn'" />
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
							size="30" style="width: 300px;" ondblclick="moveSelected();">
						</select>
					</fieldset>
					<input type="text" id="findClientCodeU" name="findClientCodeU"
									class="titleInfo"
									value="请输入客户编号" 
									title="请输入客户编号(可输入多个客户编号以逗号隔开)"
									onfocus="if(this.value=='请输入客户编号') {this.value=''}" 
									onblur="if(this.value=='') this.value='请输入客户编号'"/>
					<input type="button" id="clientSelect" value="选择" onclick="openClientRelationSelectView('0')" class="btn"
							onmouseover="this.className='btn_mouseover'" onMouseOut="this.className='btn'" 
        					onmousedown="this.className='btn_mousedown'" onMouseUp="this.className='btn'" />					
					<input type="button" name="queryUnSelectCode" id="queryUnSelectCode" value="查询" onclick="findClientByCode(this.id)" class="btn"
							onmouseover="this.className='btn_mouseover'" onMouseOut="this.className='btn'" 
        					onmousedown="this.className='btn_mousedown'" onMouseUp="this.className='btn'" />
				</td>
			</tr>
		</table>
	</div>
	</body>
</html>
<script type="text/javascript">
Ext.QuickTips.init();

Ext.ns('LevelManage.Layout');

LevelManage.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
   
   var treeDataUrl = "<c:url value='/client/client.do?method=onFindClientLevelTree' />";
     
   var config = {
		 collapsible:true,
		 autoWidth:true,
		 border:false,
		 layout:"border", 
		 
		 items:[{
		 			id:'treepanel',
		 			xtype:'treepanel',
		 			region:"west",
		 			width:200,
		 			animCollapse :false,
		 			animate :false,//去除动画
　　　				autoScroll:true,
					border : true, // 边框
					useArrows :true,
				    loader: new Ext.tree.TreeLoader({dataUrl: treeDataUrl}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:'${param.id==''?0:param.id}',text:'客户级别树'}),
		       	 	//rootVisible:false,
		       	 	tbar : new Ext.Toolbar([
								 {text : '新增',iconCls:'addIcon',id:'ext_b_addLevel',tooltip :'新增级别'},
								 {text : '修改',iconCls:'editIcon',id:'ext_b_modifyLevel',tooltip :'修改级别'},
								 {text : '删除',iconCls:'deleteIcon',id:'ext_b_deleteLevel',tooltip :'删除级别(请确认该级别下没有人员)'}
					]),
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != '${param.id==''?0:param.id}'){
						       document.getElementById('select').options.length=0;//清空
						       loadClientLevelOfRelation('1','select',node.attributes.id);//加载已关联
						   }
						 }
					}
              	},{
              		id:'leverConfig',
		            region:"center",
		            xtype:'panel',
					border:false,
					hideBorders:true,
					//height:clientHeight,
	            	autoScroll:true,
	            	contentEl:'clientLevelRelation'
			  }
		 	]
	}
	         
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	LevelManage.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});

function moveSelected(){//添加
	if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择客户级别树下的一个级别!');return;};

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
    updateClientLevel('1',arrSelValue.join(','));
}

function moveSelected1(){//移除 
	if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择客户级别树下的一个级别!');return;};

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
    
    updateClientLevel('0',arrSelValue.join(','));
}

function loadAndClearAll(obj){//全选
	if(!checkTreeIsSelected()) {Ext.MessageBox.alert('提示','请选择客户级别树下的一个级别!');return;};

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
     if(obj.trim()=='loadAll'){//<-
     	updateClientLevel('1',arrSelValue.join(','));
     }
     if(obj.trim()=='clearAll'){//->
     	updateClientLevel('0',arrSelValue.join(','));
     }
     
}

function checkTreeIsSelected(){//检查树是否被选择
	var selNode = Ext.getCmp('treepanel').getSelectionModel().getSelectedNode();
	if (selNode!=null){
		if (selNode.id!='0'){
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}

function updateClientLevel(type,list){//设置客户级别
	var selNode = Ext.getCmp('treepanel').getSelectionModel().getSelectedNode();
	var levelCode;
	if (type == 0){
		levelCode = null;
	} else if (type == 1){
		levelCode = selNode.id;
	}
	var msgTip = Ext.MessageBox.show({
				title:'系统提示',
				width : 250,
				msg:'正在设置客户级别请稍后......'
 	});
 	
	Ext.Ajax.request({
		url : "<c:url value='/client/client.do?method=onUpdateClientLevel' />",// 请求的服务器地址
		params : {
			levelCode : levelCode,
			clientIdList : list
		},
		success : function(response, options) {
				msgTip.hide();		
		},
		failure: function (response, options) {
			msgTip.hide();		
		    checkAjaxStatus(response);
		}
	});
}

/**
* 加载客户级别的客户关联信息
* @param related 1.已关联 0.未关联
* @param o 加载客户关联信息的SELECT对象ID
* @param id 当前选择树的ID
*/
function loadClientLevelOfRelation(related,o,id){//查找客户级别的客户关联信息
	Ext.Ajax.request({
		url : "<c:url value='/client/client.do?method=onLoadClientLevelOfRelation' />",// 请求的服务器地址
		params : {
			related : related,
			id : id
		},
		success : function(response, options) {
			var json = Ext.util.JSON.decode(response.responseText);

			var reader = new Ext.data.JsonReader({
						root : "clientInfo"
					}, [{name : 'clientId'},{name : 'clientCode'}, {name : 'clientName'}]);
			var data = reader.readRecords(json);
			var records = data.records;
			var count = records.length;

			for (var i = 0; i < count; i++) {
				var rec = records[i];
				var clientId = rec.get('clientId');
				var clientCode = rec.get('clientCode');
				var clientName = rec.get('clientName');
				text = clientCode+'-'+clientName;
				value = clientId;
				document.getElementById(o).options.add(new Option(text, value));
			}
		},
		failure: function (response, options) {
		    checkAjaxStatus(response);
		}
	});
}

function findClientByCode(obj){//根据关键字查询
    var clientSetObj = '';
    var code = '';
    if(obj=='queryUnSelectCode'){//未选择
    	code = Ext.get('findClientCodeU').getValue();
    	clientSetObj = document.getElementById('unselect');
    	if(code=='请输入客户编号'){
    		code='';
    	}
    }
    if(obj=='querySelectCode'){//已选择
    	code = Ext.get('findClientCode').getValue();
    	clientSetObj = document.getElementById('select');
    	if(code=='请输入客户编号'){
    		code='';
    	}
    }
    
    if (code.indexOf(',')>0){//插入多个客户编号
    	var codeArr = code.split(',');
    	for(var i=0;i<clientSetObj.options.length;i++){
	    	var str = clientSetObj.options[i].text;
	    	var selectObject = str.substring(0,str.indexOf("-")).trim();
	    	clientSetObj.options[i].selected = false;
	    	
	    	for(var j=0;j<codeArr.length;j++)
	    	if(selectObject == codeArr[j].trim()){
	    		clientSetObj.options[i].selected = true;
	    	}
    	}
    } else {//优化
        for(var i=0;i<clientSetObj.options.length;i++){
	    	var str = clientSetObj.options[i].text;
	    	var selectObject = str.substring(0,str.indexOf("-")).trim();
	    	clientSetObj.options[i].selected = false;
	    	if(selectObject == code.trim()){
	    		clientSetObj.options[i].selected = true;
	    	}
    	}
    }
}

function refreshTree(){//刷新树
	Ext.getCmp('treepanel').root.reload();
	//Ext.getCmp('treepanel').expandAll();//树默认全部展开
}

/**
* 客户级别管理操作
* @param title 标题
* @param type 操作 append|modify|remove
*/
function onManangeLevel(title,type){//弹出操作界面事件
	 var selNode = Ext.getCmp('treepanel').getSelectionModel().getSelectedNode();
	 var levelCode='',levelName='',parentLevelCode=null;
	 if (selNode!=null){
	 	levelCode = selNode.id;
	 	if (type != 'append') {levelName = selNode.text};
	 	
	 	if (type == 'append'){
	 		parentLevelCode = selNode.id;
	 	} else if (type == 'modify'){
	 		if (selNode!='${param.id==''?0:param.id}') parentLevelCode = selNode.parentNode.id;
	 	}
	 }
	 
	 if (type == 'remove'){
	 		var selectObj = document.getElementById('select');
			if (selNode.hasChildNodes()){//存在子节点
				Ext.MessageBox.alert('提示','该级别下存在子级别,不能删除该级别!');
				return;
			}
			
			if (selectObj.options.length>0){
				Ext.MessageBox.alert('提示','请确认该级别下没有人员,若有,请移除与该级别关联的客户!');
				return;
			}
	 		Ext.MessageBox.confirm('系统提示','你确定要删除所选客户级别吗？',function(btn){
					if(btn=='yes') {
						 var msgTip = Ext.MessageBox.show({
									title:'系统提示',
									width : 250,
									msg:'正在执行操作请稍后......'
					 	 });
					     Ext.Ajax.request({
								url :"<c:url value='/client/client.do?method=onManageClientLevelNode'/>",//请求的服务器地址
								params : {type:type,levelCode:levelCode,parentLevelCode:parentLevelCode},
								success : function(response,options){
									msgTip.hide();
									refreshTree();
								},
								failure : function(response,options){
									msgTip.hide();
									checkAjaxStatus(response);
								}
					　    });
					}
			});		
	 } else {
	 		Ext.MessageBox.prompt(title, '客户级别名称:', function(btn, text){
	 	 			if (btn == 'ok') {
	 	 				 var msgTip = Ext.MessageBox.show({
									title:'系统提示',
									width : 250,
									msg:'正在执行操作请稍后......'
					 	 });
					     Ext.Ajax.request({
								url :"<c:url value='/client/client.do?method=onManageClientLevelNode'/>",//请求的服务器地址
								params : {type:type,levelCode:levelCode,levelName:text,parentLevelCode:parentLevelCode},
								success : function(response,options){
									msgTip.hide();
									refreshTree();
								},
								failure : function(response,options){
									msgTip.hide();
									checkAjaxStatus(response);
								}
					　    });
	 	 			}
	 	 	},this,false,levelName);
	 }
}

function openClientRelationSelectView(selected){//打开选择页面
	openWinCenter('设置级别的客户选择页面','<c:url value='/client/client.do?method=onClientRelationSelectView' />'+'&selected='+selected, 800,500,true);
}

function setUnSelectValue(text){
	document.getElementById('findClientCodeU').value=text;
	findClientByCode('queryUnSelectCode');
}

function setSelectValue(text){
	document.getElementById('findClientCode').value=text;
}

Ext.onReady(function(){
	
	var viewport =  new LevelManage.Layout.Viewport();
	
    //Ext.getCmp('treepanel').expandAll();//树默认全部展开
    Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
    Ext.getCmp('treepanel').getRootNode().select();
    loadClientLevelOfRelation('0','unselect');//加载未关联

    Ext.EventManager.addListener(Ext.get('ext_b_addLevel'), 'click', function(){//新增级别
			onManangeLevel('新增级别','append');
	});

    Ext.EventManager.addListener(Ext.get('ext_b_modifyLevel'), 'click', function(){//修改级别
			onManangeLevel('修改级别','modify');
	});

    Ext.EventManager.addListener(Ext.get('ext_b_deleteLevel'), 'click', function(){//删除级别
			onManangeLevel('删除级别','remove');
	});

});

</script>
