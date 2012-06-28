<%@ page language="java" contentType="text/html; charset=UTF-8"
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
		<script type="text/javascript" src="<%=path%>/script/ext/extend/pPageSize.js"></script>
		<script type="text/javascript" src="<%=path%>/script/ext/extend/gridToExcel.js"></script>
		<script type="text/javascript" src="<%=path%>/script/jquery-plugin/jquery.blockUI.js"></script>
		<script>
		/** getdata1,getdata2方法必须添加 */
		function getdata1(){
			/**
			return ['<input id="c1" value="1" />','<input id="c2" value="2" />','<input id="c3" value="3" />',
			'<input id="c4" value="4" />','<input id="c5" value="5" />','<input id="c6" value="6" />'];
			**/
			return [${ExtQueryColumn}];
		}
		function getdata2(){
			/** return [['11','11'],['22','22'],['33','33']]; **/
			return [${ExtQueryColumn2}];
		}
		</script>
		<script language="javascript" type="text/javascript" src="<%=path%>/frameSCMExt/query.js" ></script>
		<!-- 样式文件 -->	
		${linkcss}
		<!-- 时间控件脚本 -->
		${datecompFunc}
		<title>应用</title>
	</head>
	<body>
		<input type="hidden" id="extquery" name="extquery" value="0" />
		<input type="hidden" id="fatherlsh" name="fatherlsh" value="" />
		<input type="hidden" id="addlsh" name="addlsh" value="" />
		<input type="hidden" id="extconditions" name="extconditions" value="" /><!-- 缓存条件 -->
		<input type="hidden" id="openerWinId" name="openerWinId" value="app" />
		
		<!--<input type="hidden" id="beginTime" name="beginTime">
		<input type="hidden" id="endTime" name="endTime">
		<input type="hidden" id="status" name="status">
		<input type="hidden" id="clientId" name="clientId">-->
		

		<!-- 普通查询 -->
		<div id="queryform1" style="display: none">${queryform1}</div>
		<!-- 高级查询 -->
		<div id="queryform2"></div>
		
	</body>
</html>
<script type="text/javascript">

if($.browser.msie){//IE
	    var height = 0;
	    var width = 0;
		if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
		    height = document.documentElement.clientHeight;
		    width = document.documentElement.clientWidth;
		} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
		    height = document.body.clientHeight;
		    width = document.body.clientWidth;
	    }
		var leftW = width/2-50;   
		var top = height/2;
		
		var _html = "<div id='___loading' style='position:absolute;left:0;width:100%;height:"+height+"px;top:0;background:#E0ECFF;opacity:0.8;filter:alpha(opacity=80);'><div style='position:absolute;  cursor1:wait;left:"+leftW+"px;top:"+top+"px;width:auto;height:16px;padding:12px 5px 10px 30px;border:2px solid #ccc;color:#000;'>正在加载，请稍候...</div></div>";   
		    
		window.onload = function(){
		 	var _mask = document.getElementById('___loading');   
	   		_mask.parentNode.removeChild(_mask);   
		}
		document.write(_html);  
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
			    frame:true,
			    border:false,
			    //enableHdMenu:false,
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
				],
				/**
				viewConfig:{forceFit:false,autoHeight:true,autoScroll:true,
					layout : function() { 
						if (!this.mainBody) { 
						return; 
						} 
						var g = this.grid; 
						var c = g.getGridEl(); 
						var csize = c.getSize(true); 
						var vw = csize.width; 
						if (!g.hideHeaders && (vw < 20 || csize.height < 20)) { 
						return; 
						} 
						if (g.autoHeight) { 
						if (this.innerHd) { 
						this.innerHd.style.width = '100%'; 
						} 
						} else { 
						this.el.setSize(csize.width, csize.height); 
						var hdHeight = this.mainHd.getHeight(); 
						var vh = csize.height - (hdHeight); 
						this.scroller.setSize(vw, vh); 
						if (this.innerHd) { 
						this.innerHd.style.width = '100%'; 
						} 
						} 
						if (this.forceFit) { 
						if (this.lastViewWidth != vw) { 
						this.fitColumns(false, false); 
						this.lastViewWidth = vw; 
						} 
						} else { 
						this.autoExpand(); 
						this.syncHeaderScroll(); 
						} 
						this.onLayout(vw, vh); 
						} },
				**/
				autoScroll:true,
				loadMask:true	
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
	 
		  this.bbar = new Ext.PagingToolbar({
		     plugins:new Ext.ux.Andrie.pPageSize({
				variations: [${limit},100,200,500,1000],
				beforeText : '每页显示:',
				afterText :'条'
			 }),
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
				  options.params.fatherlsh = document.getElementById('fatherlsh').value;
				  var extquery = document.getElementById('extquery').value;
				  var extconditions = document.getElementById('extconditions');
				  if (extquery=="1"){
				  	options.params.conditions = Ext.util.JSON.encode({});
				  	options.params.extconditions = "";
				  	extconditions.value='';
				  } else if (extquery=="2"){
				  	options.params.conditions = Ext.util.JSON.encode({});
				  	
				  	var selectionSet = Ext.getCmp('ExtData').getSelectionModel().getSelections();
			        if (lshs!='' && selectionSet.length>0) {
			        	var lshs = selectionSet[0].get('lshs');
			        	lshs = lshs.substr(0, lshs.length-1);
			        	var lshstrs = lshs.split(',');
			        	var strs = ' and lsh in ( ';
			        	var split = "";
			        	for(var i=0;i<lshstrs.length;i++){
			        		strs += split+"'"+lshstrs[i]+"'";
			        		split=",";
			        	}
			        	strs+=") ";
			        	options.params.extconditions = strs;
			        	extconditions.value=strs;
			        } else {
			        	options.params.extconditions = extconditions.value;
			        }
				  } else {
				  	options.params.conditions = Ext.util.JSON.encode(conditions);
				  	options.params.extconditions = "";
				  	document.getElementById('extquery').value="0";
				  	extconditions.value='';
				  }
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

  Ext.ns('ExtData.Data');
  ExtData.Data.ExtGrid  =  Ext.extend(Ext.grid.GridPanel,{
  
		 initComponent: function() {
		 
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	});
		    
			var config = {
			    sm : cb,
			    //frame:true,
			    border:false,
			    //enableHdMenu:false,
				store: new Ext.data.Store({
					url: "<c:url value='/frame.do?method=onListExtdata' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [
						${extfields}
					   ])
				}),
				columns:[
				        index,
						${extcolumns}
				],
				/**
				viewConfig:{forceFit:false,autoHeight:true,autoScroll:true,
					layout : function() { 
						if (!this.mainBody) { 
						return; 
						} 
						var g = this.grid; 
						var c = g.getGridEl(); 
						var csize = c.getSize(true); 
						var vw = csize.width; 
						if (!g.hideHeaders && (vw < 20 || csize.height < 20)) { 
						return; 
						} 
						if (g.autoHeight) { 
						if (this.innerHd) { 
						this.innerHd.style.width = '100%'; 
						} 
						} else { 
						this.el.setSize(csize.width, csize.height); 
						var hdHeight = this.mainHd.getHeight(); 
						var vh = csize.height - (hdHeight); 
						this.scroller.setSize(vw, vh); 
						if (this.innerHd) { 
						this.innerHd.style.width = '100%'; 
						} 
						} 
						if (this.forceFit) { 
						if (this.lastViewWidth != vw) { 
						this.fitColumns(false, false); 
						this.lastViewWidth = vw; 
						} 
						} else { 
						this.autoExpand(); 
						this.syncHeaderScroll(); 
						} 
						this.onLayout(vw, vh); 
						} 
				},
				**/
				loadMask:true,
				autoScroll:true
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
	 
		  // call parent
		  ExtData.Data.ExtGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var conditions = {
				  };
				  options=options||{}; 
				  options.params = options.params||{}; 
				  options.params.naturalname = '${param.naturalname}';
				  var extmode = '${param.extmode}'.trim();
				  var allstore = Ext.getCmp('ExtData').getStore();
				  if (extmode=="2"){
				  	allstore = Ext.getCmp('PlanGrid').getStore();
				  }
				  
				  var count = allstore.getCount();
				  var jsonStr = Ext.util.JSON.encode({lsh:document.getElementById('addlsh').value});
				  for (var i = 0; i < count; i++) {
				    var r = allstore.getAt(i);
				    if (extmode=="2"){
				    	Ext.getCmp('ExtData').store.add(r);
				    	Ext.getCmp('ExtData').store.commitChanges();
				    }
				    var jsonRecord = {
				      lsh: r.get('lsh')
				    }
				    jsonStr += "," + Ext.util.JSON.encode(jsonRecord);
				  }
				  jsonStr = '[' + jsonStr + ']';
				  options.params.fatherlsh = document.getElementById('fatherlsh').value;
				  options.params.extmode = extmode;
				  options.params.conditions = jsonStr;
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
  Ext.reg('ExtData', ExtData.Data.ExtGrid);//注册一个组件,注册成xtype以便能够延迟加载

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
				 autoScroll:true,
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
						},
						</rs:permission>
						
						<rs:permission action="7" resource="${naturalname_dyform}.MODI">
						{
						  text:'修改',
						  id:'ext_b_edit',
						  iconCls:"editIcon",
						  handler: function (){
								$edit();
						  }
						},
						</rs:permission>
						
						<rs:permission action="7" resource="${naturalname_dyform}.DELE">
						{
						  text:'删除',
						  id:'ext_b_delete',
						  iconCls:"deleteIcon",
						  handler: function (){
								$delete();
						  }
						},
						</rs:permission>
						
						<rs:permission action="7" resource="${naturalname_dyform}.CONF">
						{
						  text:'确认',
						  iconCls:"reIcon",
						  handler: function (){
								$confirmStatus();
						  }
						},
						</rs:permission>
						
						<rs:permission action="7" resource="${naturalname_dyform}.UCONF">
						{
						  text:'反确认',
						  iconCls:"resetIcon",
						  handler: function (){
								$unconfirmStatus();
						  }
						},
						</rs:permission>
						
						<rs:permission action="7" resource="${naturalname_dyform}.QUE">
						{
						  text:'查询',
						  iconCls:"viewIcon",
						  menu : querymenu
						},
						</rs:permission>
						
						{
						  text:'打印',
						  iconCls:"print",
						  handler: function (){
								$print();
						  }
						}
						
						,{text : '导出Excel',iconCls:'excelIcon',handler:function(){
									var selectionSet = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
								    if (selectionSet == undefined || selectionSet.length <= 0) {
								        Ext.MessageBox.alert('提示', '请选择一条或多条记录进行导出操作!');
								        return;
								    }
									var vExportContent = Ext.getCmp('PlanGrid').getSelectedExcelXml(false);
									vExportContent = vExportContent.replace(/<BR>/ig, " ");
							        if (Ext.isIE6 || Ext.isIE7 || Ext.isSafari || Ext.isSafari2 || Ext.isSafari3) {
							            var fd=Ext.get('frmDummy');
							            if (!fd) {
							                fd=Ext.DomHelper.append(Ext.getBody(),{tag:'form',method:'post',id:'frmDummy',action:"<c:url value='/exportexcel.jsp' />", target:'_blank',name:'frmDummy',cls:'x-hidden',cn:[
							                    {tag:'input',name:'exportContent',id:'exportContent',type:'hidden'}
							                ]},true);
							            }
							            fd.child('#exportContent').set({value:vExportContent});
							            fd.dom.submit();
							        } else {
							            document.location = 'data:application/vnd.ms-excel;base64,'+Base64.encode(vExportContent);
							        }
						        }
						}
						
						,{
						  text:'列配置',
						  iconCls:"configIcon",
						  handler: function (){
								$colconfig();
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
				 	  },{
					    id:"ExtData",
					    xtype:"ExtData",
					    title:"列表",
					    //frame:true,
					    split:true,
					    region:"west",
			            //border:false,
			            //hideBorders:true,
			            split:true,width:200,collapsible: true,frame:true,titleCollapse:true,animCollapse:true,collapseFirst:true,
			            height : clientHeight-20,
					 	autoScroll:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			Plan.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
/**
function $edit(lsh,runtimeid){
	window.open('<c:url value="/frame.do?method=onEditViewMain"/>&naturalname=${param.naturalname}&lsh='+lsh+'&runtimeid='+runtimeid);
}
function $query(lsh,runtimeid,readonly){
	window.open('<c:url value="/frame.do?method=onEditViewMain"/>&naturalname=${param.naturalname}&query=look&lsh='+lsh+'&runtimeid='+runtimeid+"&readonly="+readonly);
}

function $delete(lsh){
	var url = "<c:url value='/frame.do?method=onDelete' />";
	if (!confirm('确认要删除?')){return;};
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
**/

Ext.onReady(function () { //页面加载时候触发事件
    var viewport = new Plan.Layout.Viewport();
    var PlanGrid2 = viewport.findById('PlanGrid'); //Grid
    var ExtData = viewport.findById('ExtData'); //Grid
    
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
    	var pageSize = Ext.getCmp('_pPageSize_combo').getValue();
        Ext.getCmp('PlanGrid').store.load({
            params: {
                start: 0,
                limit: pageSize
            },callback : function(r, options, success) {  
				  if (success == false) {
				  	var extquery = document.getElementById('extquery').value;
				  	if (extquery=="1"){
				  		Ext.Msg.alert("提示","查询失败!");
				  	}
				  } else {
				  	Ext.getCmp('ExtData').store.load();
				  	if(Ext.getCmp('PlanGrid').store.getCount()<=0){
				  		var n = Ext.getCmp("PlanGrid");
						var u = n.getStore();
						  var w = {
						   lsh : ''
						  };
						  var r = new u.recordType(w);
						  n.stopEditing();
						  u.add(r);
				  	}
				  }
				  
			}
        }); //刷新当前页面
        
    };
	
	refreshExtdata = function () { //刷新
		// ExtData.store.reload();
	}
	
    //--------------------------初始化加载--------------------------
    refresh(); //初始化加载数据
    Ext.getCmp('PlanGrid').addListener('celldblclick', function (grid, rowIndex, columnIndex, e) { // 列表双击事件--查看明细
        var selectionSet = grid.getSelectionModel().getSelections();
        var lsh = selectionSet[0].get('lsh');
        if (lsh!='')
        window.open('<c:url value="/frame.do?method=onEditViewMain"/>&naturalname=${param.naturalname}&lsh='+lsh);
    });
    
    Ext.getCmp('ExtData').addListener('celldblclick', function (grid, rowIndex, columnIndex, e) { // 列表双击事件--查看明细
        var selectionSet = grid.getSelectionModel().getSelections();
        var lshs = selectionSet[0].get('lshs');
        var extmode = '${param.extmode}'.trim();
        if (lshs!='' && extmode=='2'){
        	document.getElementById('extquery').value='2';
        	refresh();
        }
    });    
});

function $print(){
	var selectionSet = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
    if (selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <= 0) {
        Ext.MessageBox.alert('提示', '请选择一条记录进行操作!');
        return;
    }
    var lsh = selectionSet[0].get('lsh');
	var url = "<c:url value='/frame.do?method=onPrintViewMain' />"+"&naturalname=${param.naturalname}&lsh="+lsh;
	if (lsh!='') window.open(url);
}

function $confirmStatus(){
	var selectionSet = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
    if (selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <= 0) {
        Ext.MessageBox.alert('提示', '请选择一条记录进行操作!');
        return;
    }
    var lsh = selectionSet[0].get('lsh');
	var url = "<c:url value='/frame.do?method=onConfirmStatus' />";
	if (!confirm('确认要确认?')){return;};
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
            	Ext.ux.Toast.msg("", result.tip);
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

function $unconfirmStatus(){
	var selectionSet = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
    if (selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <= 0) {
        Ext.MessageBox.alert('提示', '请选择一条记录进行操作!');
        return;
    }
    var lsh = selectionSet[0].get('lsh');

	var url = "<c:url value='/frame.do?method=onUnConfirmStatus' />";
	if (!confirm('确认要反确认?')){return;};
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
            	Ext.ux.Toast.msg("", result.tip);
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


function $edit(){
	var selectionSet = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
    if (selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <= 0) {
        Ext.MessageBox.alert('提示', '请选择一条记录进行操作!');
        return;
    }
    var lsh = selectionSet[0].get('lsh');
	window.open('<c:url value="/frame.do?method=onEditViewMain"/>&naturalname=${param.naturalname}&lsh='+lsh);
}

function $delete(){
	var selectionSet = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
    if (selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <= 0) {
        Ext.MessageBox.alert('提示', '请选择一条记录进行操作!');
        return;
    }
    var lsh = selectionSet[0].get('lsh');
    
	var url = "<c:url value='/frame.do?method=onDelete' />";
	if (!confirm('确认要删除?')){return;};
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

function $edit(){
	var selectionSet = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
    if (selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <= 0) {
        Ext.MessageBox.alert('提示', '请选择一条记录进行操作!');
        return;
    }
    var lsh = selectionSet[0].get('lsh');
	window.open('<c:url value="/frame.do?method=onEditViewMain"/>&naturalname=${param.naturalname}&lsh='+lsh);
}

function $delete(){
	var selectionSet = Ext.getCmp('PlanGrid').getSelectionModel().getSelections();
    if (selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <= 0) {
        Ext.MessageBox.alert('提示', '请选择一条记录进行操作!');
        return;
    }
    var lsh = selectionSet[0].get('lsh');
    
	var url = "<c:url value='/frame.do?method=onDelete' />";
	if (!confirm('确认要删除?')){return;};
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
function $colconfig(){
	window.open('<c:url value="/frame.do?method=colconfig"/>&formcode=47c36268b7c611e1ba92af790c025a41_&naturalname=${param.naturalname}');
}

$(function(){
	<rs:noPermission action="7" resource="${naturalname_dyform}.LIST">
	alert('无操作权限，请联系管理员！');
	window.opener=null;
	window.close();
	</rs:noPermission>

 	if(!$.browser.msie){ //not IE
 	    // 在FF/Linux下启用遮罩层透明
 	    $.blockUI.defaults.applyPlatformOpacityRules = false;
	    $.blockUI({message: '<div style=\"padding:12px 5px 10px 30px;height:16px\">正在加载，请稍候...</div>',css:{border:'2px solid #ccc'},overlayCSS:{backgroundColor: '#E0ECFF',opacity:.8}});		   	
 	}
  		    		    		
	if(!$.browser.msie){ //not IE
		$.unblockUI();
	}
})
</script>
