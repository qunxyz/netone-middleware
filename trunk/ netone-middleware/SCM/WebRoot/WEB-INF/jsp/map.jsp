<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="common/metaExt.jsp"></jsp:include>
		<jsp:include page="common/metaJQuery-min.jsp"></jsp:include>
        <jsp:include page="common/metaJQuery-ui-dialog.jsp"></jsp:include>
		<title>
		</title>
		<style>
			.ext-ie ul.x-tree-node-ct{font-size:0;line-height:0;zoom:1;}
			.x-tree-node{color:black;font:normal 14px arial,tahoma,helvetica,sans-serif;white-space:nowrap;}
			.x-tree-node-el{line-height:20px;cursor:pointer;}
			.x-tree .x-panel-body{background-color:#fff;}
			.x-tree-node-expanded .x-tree-node-icon{background-image:url(<%=path%>/script/theme/main/folder-open.gif);}
			.x-tree-node a span,.x-dd-drag-ghost a span{text-decoration:none;color:black;font-weight: bold; }
			.x-tree-node a:hover,.x-dd-drag-ghost a:hover{text-decoration:none;background-color: red;}
		</style>
	</head>
	<body class="_mapGD">
	<map name="Map_Direction" id="Map_Direction">
	  <!-- 
	  <area shape="rect" coords="16,4,28,16" href="javascript:void(0)" title="上" id="up_d" onclick="_flexibleRate('up_d')"/>
	  <area shape="rect" coords="4,16,16,28" href="javascript:void(0)" title="左" id="left_d" onclick="_flexibleRate('left_d')"/>
	  <area shape="rect" coords="28,16,40,28" href="javascript:void(0)" title="右" id="right_d" onclick="_flexibleRate('right_d')"/>
	  <area shape="rect" coords="16,29,28,41" href="javascript:void(0)" title="下" id="down_d" onclick="_flexibleRate('down_d')"/>
	  -->
	  <area shape="rect" coords="14,67,32,83" href="javascript:void(0)" title="还原" id="reset_d" onclick="_flexibleRate('reset_d')" />
	  <area shape="rect" coords="16,54,28,66" href="javascript:void(0)" title="拉伸" id="add_d" onclick="_flexibleRate('add_d')"/>
	  <area shape="rect" coords="16,83,28,95" href="javascript:void(0)" title="缩放" id="remove_d" onclick="_flexibleRate('remove_d')"/>
	</map>
	
	<div id="__map" class="panel" style="width: 100%;height: 100%" >
		<div id="mapDIV" style="width: 100%;height: 100%">
		</div>
	</div>
	<div id="relation" class="panel" style="margin-left: 2px;width: 100%;height: 100%">
		<div id="mapInfo" name = "mapInfo" style="width: 100%;height: 100%" class="map_font_right">
		</div>
	</div>
	</body>
</html>
<script type="text/javascript">
var nodeid='0';
var flexibleRate = 100;//伸缩比例
var nodename='中国';
var arr = new Array(); //数据栈
var pos = 0;//指针
var rightArr = new Array();//存放右边数组

Ext.QuickTips.init();
Ext.ns('Buss.Layout');
Buss.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
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
		 			height:clientHeight,
		 			//style:"padding:6px",
		 			title:'组织结构',
		 			iconCls:"flow_chartIcon",
		 			split:true,
		 			collapsible: true,
		 			animCollapse :false,
		 			animate :false,//去除动画
　　　				autoScroll:true,
					border : true, // 边框
					useArrows :true,
				    loader: new Ext.tree.TreeLoader({dataUrl: '<c:url value="/department/department.do?method=onFindDeptTree&excludeNode=yes&p=map" />'}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:nodeid,text:nodename}),
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != nodeid){
					   		   	var path = node.getPath();
								Ext.getCmp('treepanel').collapseAll();
								Ext.getCmp('treepanel').expandPath(path);
								node.select();
						   		var level = node.attributes.NLevel;
						   		var busspointId = node.attributes.departmentId;
						   		var areapointId = node.attributes.areaId;
						   		onLoadNext(level-2,areapointId,busspointId);
						   } else {
						   		initMap();
							}
						}
					}    
				},{
		 			id:'_map',
		 			xtype:'panel',
		 			region:"center",
		 			title:'地图选择',
		 			iconCls:"flow_chartIcon",
		 			height:clientHeight,
　　　				autoScroll:true,
					border : true, // 边框
				    contentEl:'__map'
              	},{
              		id:'_grid',
		            region:"east",
		            xtype:'panel',
		            title:"信息展示",
		            minSize: 300,
                	maxSize: 545,
                	bodyStyle:"background: #C0C0C0",
		            iconCls:"job_checkIcon",
		            width:395,
		            height:clientHeight,
		            split:true,
					border:false,
					hideBorders:true,
	            	autoScroll:true,
	            	contentEl:'relation',
	            	tbar:new Ext.Toolbar([
							 {text : '初始',iconCls:'topIcon',handler:function (){ initMap();}},
							 {text : '上一级',iconCls:'upIcon',handler:goback}
						])
			  }
		 	]
	}
	         
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	Buss.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});
function checkTreeIsSelected(){//检查树是否被选择
	var selNode = Ext.getCmp('treepanel').getSelectionModel().getSelectedNode();
	if (selNode!=null){
		if (selNode.id!=nodeid){
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
	//Ext.getCmp('treepanel').expandAll();//树默认全部展开
	Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
}
Ext.onReady(function(){
	
	var viewport =  new Buss.Layout.Viewport();
	initMap();
   	Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
    Ext.getCmp('treepanel').getRootNode().select();
});

function onLoadNext(parentstep,areapointId,busspointId,picId){//保存
		if (busspointId!='0') _selectDeptTree(busspointId);
  		var msgTip = Ext.MessageBox.show({
				title : '系统提示',
				width : 250,
				msg : '正在加载信息请稍后......'
		 });
	     Ext.Ajax.request({
				url :"<c:url value='/map.do?method=onQMapXY' />",
				//form : 'form1',
				params: {parentstep:parentstep,areapointId:areapointId,busspointId:busspointId,picId:picId},
				method : 'POST',
				success : function(response,options){
					msgTip.hide();
					var postmp = pos++;
					arr[postmp] = document.getElementById("mapDIV").innerHTML;
					rightArr[postmp] = document.getElementById("mapInfo").innerHTML;
					var result = Ext.util.JSON.decode(response.responseText);
					document.getElementById("mapDIV").innerHTML = result.data;
					document.getElementById("mapInfo").innerHTML = result.rightInfo;
					draggableObj();
				},
				failure : function(response,options){
					msgTip.hide();
					checkAjaxStatus(response);
					var result = Ext.util.JSON.decode(response.responseText);
					Ext.MessageBox.alert('提示',"加载失败");
				}
	　	 });
    }
    function onLoadNext_(){//保存
  		var msgTip = Ext.MessageBox.show({
				title : '系统提示',
				width : 250,
				msg : '正在加载信息请稍后......'
		 });
	     Ext.Ajax.request({
				url :"<c:url value='/map.do?method=onQMapClientXY' />",
				//form : 'form1',
				success : function(response,options){
					msgTip.hide();
					var result = Ext.util.JSON.decode(response.responseText);
					document.getElementById("mapDIV").innerHTML = result.data_;
					document.getElementById("mapInfo").innerHTML = result.info_;
				},
				failure : function(response,options){
					msgTip.hide();
					checkAjaxStatus(response);
					var result = Ext.util.JSON.decode(response.responseText);
					Ext.MessageBox.alert('提示',"加载失败");
				}
	　	 });
    }
    
    function goback(){
    	var len = arr.length;
    	
    	var arr_ =  arr[len-1];
    	var rightArr_=rightArr[len-1];
    	if(arr_=='undefined' || arr_ =='unknown'||arr_=="" ||arr_==null){
    		return;
    	}else{
    		if(arr.length>1){
    			document.getElementById("mapDIV").innerHTML = arr_;
    			document.getElementById("mapInfo").innerHTML = rightArr_;
    			arr.pop();
    			rightArr.pop();
    		}
    	}
    }
    
    function _loadLineData(picid,busslevel,busspointId,extendbussinfo){
    	var msgTip = Ext.MessageBox.show({
				title : '系统提示',
				width : 250,
				msg : '正在加载信息请稍后......'
		 });
	     Ext.Ajax.request({
				url :"<c:url value='/map.do?method=onQMapClientXY' />",
				params: {picid:extendbussinfo,busslevel:busslevel,busspointId:busspointId,extendbussinfo:extendbussinfo},
				method : 'POST',
				success : function(response,options){
					msgTip.hide();
					var postmp = pos++;
					arr[postmp] = document.getElementById("mapDIV").innerHTML;
					rightArr[postmp] = document.getElementById("mapInfo").innerHTML;
					var result = Ext.util.JSON.decode(response.responseText);
					document.getElementById("mapDIV").innerHTML = result.data;
					document.getElementById("mapInfo").innerHTML = result.rightInfo;
				},
				failure : function(response,options){
					msgTip.hide();
					checkAjaxStatus(response);
					var result = Ext.util.JSON.decode(response.responseText);
					Ext.MessageBox.alert('提示',"加载失败!");
				}
	　	 });
    }
    
    function _loadTypeData(){
    	
    }
    
    function initMap(){
    	arr = new Array();
    	rightArr = new Array();
    	pos = 0;
    	onLoadNext(-1,'#$#','#$#');
    }

    function openQuery(str){
    
    	var url='';
    	var titleName='';
    	if(str=="1"){
    		url='<c:url value="/sellReport/sell.do?method=onQAnalysisBackPayment"/>';
    		titleName='详细';
    	}else if(str=="2"){
    		url='<c:url value="/chart.do?method=onQSellLineChart"/>';
    		titleName='销售趋势';
    	}else if(str=="3"){
    		url='<c:url value="/sellReport/sell.do?method=onQDetailsIndent"/>';
    		titleName='详细';
    	}else if(str=="4"){
    		url='<c:url value="/chart.do?method=onQSellLineChart"/>';
    		titleName='销售趋势';
    	}else if(str=="5"){
    		url='<c:url value="/chart.do?method=onQSellPieChart3L"/>';
    		titleName='各类酒种比例';
    	}else if(str=="6"){
    		url='<c:url value="/sellReport/sell.do?method=onQAnalysisBackPayment"/>';
    		titleName='详细';
    	}else if(str=="7"){
    		url='<c:url value="/sellReport/sell.do?method=onQDetailsIndent"/>';
    		titleName='详细';
    	}else if(str=="8"){
    		url='<c:url value="/chart.do?method=onQSellPieChart3L"/>';
    		titleName='各类酒种比例';
    	}else if(str=="9"){
    		url='<c:url value="/storageReport/storage.do?method=onQSummaryOutIn"/>';
    		titleName='详细';
    	}else if(str=="10"){
    		url='<c:url value="/chart.do?method=onQClientStoragePieChart"/>';
    		titleName='各类酒种比例';
    	}else if(str=="11"){
    		url='<c:url value="/chart.do?method=onStorageWlAgeTopN"/>';
    		titleName='不良资产';
    	}
    	openModalDialogWindow(titleName+"查询窗口",url,550,300);
    }    
    function _focus(obj){
		obj.style.zIndex =99999;
    }
    function _focusOut(obj){
		obj.style.zIndex =0;
    }
    function _classOver(obj,obj1){
    	if(obj1=='_mapMark'){
    		obj.className="_mapMarkOver";
    	}else if(obj1=='_mapFlag'){
    		obj.className="_mapFlagOver";
    	}
    }
    function _classOut(obj,obj1){
    	if(obj1=='_mapMark'){
    		obj.className="_mapMark";
    	}else if(obj1=='_mapFlag'){
    		obj.className="_mapFlag";
    	}
    	
    }
    
    function _selectDeptTree(nodeid){
   		var node=Ext.getCmp('treepanel').getNodeById(nodeid);//id  是被刷新的结点编号
   		if (node!=null){
   		   	var path = node.getPath();
			Ext.getCmp('treepanel').collapseAll();
			Ext.getCmp('treepanel').expandPath(path);
			node.select();	
   		}
    }
    
	function draggableObj(){//移动
		if($ ("#_direction").length>0){
		    $('#_direction').draggable({
				edge:0,
				disabled:false,
				cursor:'move'
			})	
		}
	}
	
	function _flexibleRate(type){
		var isFlexible = false;
		if (type=='add_d'){
			//flexibleRate++;
			flexibleRate = 150;
			isFlexible = true;
		} else if (type=='remove_d'){
			//flexibleRate--;
			flexibleRate = 50;
			isFlexible = true;
		} else if (type=='reset_d'){
			flexibleRate = 100;
			isFlexible = true;
		}
		if(isFlexible){
			$("._mapFlag").each(function() {
				var o = $(this).attr("v").split(',');
				var x1 = o[0].replace('px', '');
				var y1 = o[1].replace('px', '');
				
				$(this).css("left", x1*flexibleRate/100+'px');
				$(this).css("top", y1*flexibleRate/100+'px');
		    });
			$("._mapxNav").each(function() {
				var o = $(this).attr("v").split(',');
				var x1 = o[0].replace('px', '');
				var y1 = o[1].replace('px', '');
				
				$(this).css("left", x1*flexibleRate/100+'px');
				$(this).css("top", y1*flexibleRate/100+'px');
		    });
		    var o0 = $('#_mapx').attr("v").split(',');
			var x0 = o0[0].replace('px', '');
			var y0 = o0[1].replace('px', '');			 
			var w0 = o0[2].replace('px', '');
			var h0 = o0[3].replace('px', '');
							 
		   	$('#_mapx').css("left", x0*flexibleRate/100+'px');
		   	$('#_mapx').css("top", y0*flexibleRate/100+'px');
		   	$('#_mapx').css("width", w0*flexibleRate/100+'px');
		   	$('#_mapx').css("height", h0*flexibleRate/100+'px');
		}
	}
</script>