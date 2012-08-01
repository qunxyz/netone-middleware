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
		<script language="javascript" type="text/javascript" src="<%=path%>/frameSCMExt/query.js"></script>
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
		<input type="hidden" id="openerWinId" name="openerWinId" value="app" />
		<!--<input type="hidden" id="beginTime" name="beginTime">
		<input type="hidden" id="endTime" name="endTime">
		<input type="hidden" id="status" name="status">
		<input type="hidden" id="clientId" name="clientId">-->

		<!-- 新增入库 -->
		<div id="instorageform" style="display: none;">
		<table border=0 >
		<tr>
			<td><input type='radio' id="bigcategorie" name="bigcategorie" value="dl001" checked="checked" /><font style="font-size: 14px;">镶嵌类</font></td>
			<td><input type='radio' id="bigcategorie" name="bigcategorie" value="dl002" /><font style="font-size: 14px;">珍珠类(件)</font></td>
			<td><input type='radio' id="bigcategorie" name="bigcategorie" value="dl003" /><font style="font-size: 14px;">玉器类</font></td>
			<td><input type='radio' id="bigcategorie" name="bigcategorie" value="dl004" /><font style="font-size: 14px;">宝石类</font></td>
			<td><input type='radio' id="bigcategorie" name="bigcategorie" value="dl005" /><font style="font-size: 14px;">银饰(件)</font></td>
		</tr>
		<tr style="line-height: 25px;">
			<td><input type='radio' id="bigcategorie" name="bigcategorie" value="dl006" /><font color="red" style="font-size: 14px;font-weight: bold;">黄金(克)</font></td>
			<td><input type='radio' id="bigcategorie" value="dl007" name="bigcategorie" /><font color="red" style="font-size: 14px;font-weight: bold;">铂金(克)</font></td>
			<td><input type='radio' id="bigcategorie" value="dl008" name="bigcategorie" /><font color="red" style="font-size: 14px;font-weight: bold;">钯金(克)</font></td>
			<td><input type='radio' id="bigcategorie" value="dl009" name="bigcategorie" /><font color="red" style="font-size: 14px;font-weight: bold;">K金(克)</font></td>
			<td><input type='radio' id="bigcategorie" value="dl010" name="bigcategorie" /><font color="red" style="font-size: 14px;font-weight: bold;">银饰(克)</font></td>
		</tr>
		<tr>
			<td><input type='radio' id="bigcategorie" value="dl011" name="bigcategorie" /><font style="font-size: 14px;">半成品</font></td>
			<td><input type='radio' id="bigcategorie" value="dl012" name="bigcategorie" /><font style="font-size: 14px;">定单类</font></td>
			<td><input type='radio' id="bigcategorie" value="dl013" name="bigcategorie" /><font style="font-size: 14px;">一口价类</font></td>
			<td><input type='radio' id="bigcategorie" value="dl014" name="bigcategorie" /><font style="font-size: 14px;">赠品类</font></td>
			<td><input type='radio' id="bigcategorie" value="dl015" name="bigcategorie" /><font style="font-size: 14px;">其他类</font></td>
		</tr>
		</table>
		</div>
		
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
						} 
				
				},
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
				  if (extquery=="1"){
				  	options.params.conditions = Ext.util.JSON.encode({});
				  	options.params.extconditions = extconditions;
				  } else {
				  	options.params.conditions = Ext.util.JSON.encode(conditions);
				  	options.params.extconditions = "";
				  	//document.getElementById('extquery').value="0";
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

  Ext.ns('Plan2.Data');
  Plan2.Data.PlanGrid  =  Ext.extend(Ext.grid.GridPanel,{
  
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
					url: "<c:url value='/frame.do?method=onListdetail' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [
						${subfields}
					   ])
				}),
				columns:[
				        index,
						cb,
						${subcolumns}
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
				autoScroll:true,
				loadMask:true	
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
	 
		  // call parent
		  Plan2.Data.PlanGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var conditions = {
				  };
				  options=options||{}; 
				  options.params = options.params||{}; 
				  options.params.naturalname = '${param.naturalname}';
				  options.params.fatherlsh = document.getElementById('fatherlsh').value;
				  options.params.conditions = Ext.util.JSON.encode(conditions);
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
  Ext.reg('PlanGrid2', Plan2.Data.PlanGrid);//注册一个组件,注册成xtype以便能够延迟加载

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
					  	<rs:permission action="7" resource="${naturalname_dyform}.ADD">
					 	{
						  text:'新增',
						  id:'ext_b_add',
						  iconCls:"addIcon",
						  handler: function (){
								if ('${param.naturalname}'=='APPFRAME.APPFRAME.JEWELRY.JEWELRY.JEWELRYAPP25'){
						  		$instorageWindow();
						  		} else {
						  		window.open('<c:url value="/frame.do?method=onEditViewMain&naturalname=${param.naturalname}"/>');
						  		}
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
						
						<c:if test="${!empty excel_actionurl}">
						,{
						  text:'数据导入',
						  iconCls:"configIcon",
						  handler: function (){
								$importExcel();
						  }
						}
						</c:if>
						
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
			            split:true,
			            height : clientHeight/2-20,
					 	autoScroll:true
				 	  },{
					    id:"PlanGrid2",
					    xtype:"PlanGrid2",
					    region:"south",
					    split:true,
			            border:false,
			            hideBorders:true,
			            height : clientHeight/2-20,
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
    var PlanGrid = viewport.findById('PlanGrid'); //Grid
    var PlanGrid2 = viewport.findById('PlanGrid2'); //Grid
    var toolbar = viewport.findById('toolbar');
    toolbar.addSpacer();
    toolbar.addSeparator();
    toolbar.addFill();

	PlanGrid.addListener('rowclick', rowclickFn);      
     
	function rowclickFn(grid, rowindex, e){      
	    PlanGrid.getSelectionModel().each(function(rec){      
	    	//alert(rec.get("lsh")); //fieldName，记录中的字段名
	    	document.getElementById('fatherlsh').value=rec.get("lsh");
	    	Ext.getCmp('PlanGrid2').store.load(); //刷新当前页面
	    });
	}


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
        Ext.getCmp('PlanGrid2').store.removeAll();
    };
	
	refreshExtdata = function () { //刷新
		//no do
	}
	
    //--------------------------初始化加载--------------------------
    refresh(); //初始化加载数据
    
    Ext.getCmp('PlanGrid').addListener('celldblclick', function (grid, rowIndex, columnIndex, e) { // 列表双击事件--查看明细
        var selectionSet = grid.getSelectionModel().getSelections();
        var lsh = selectionSet[0].get('lsh');
        if (lsh!='')
        window.open('<c:url value="/frame.do?method=onEditViewMain"/>&naturalname=${param.naturalname}&lsh='+lsh);
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

function $importExcel(){
	window.open('${excel_actionurl}&parentid=1');
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

function $instorageWindow(){
$('#instorageform').css('display','block');
var _querywindow = new Ext.Window({  
            title:"新增入库",  
            width:420,
            height:180, 
            layout:"form",  
            allowDomMove: false,
            closeAction: 'hide',
            autoScroll:false,
            resizable :false,
            buttonAlign:'center',
            //modal:true,
            items:[
            new Ext.form.FieldSet({
                title:'请选择系统大类',
	        	width:420,height:180,
		        contentEl:'instorageform'
		       }) 
            //,{
			//html:"<font color='red'>  注:红色【素金类】默认按金价销售，其他类默认以标签价销售。</font>"
            //}
            ],
            buttons:[  
                {  
                    text:" 确 认 ",  
                    handler:function(){
                    	//alert($("input[name='bigcategorie']:checked").val());
                    	window.open('<c:url value="/frame.do?method=onEditViewMain&naturalname=${param.naturalname}"/>'+"&ext="+$("input[name='bigcategorie']:checked").val());
						_querywindow.hide();
                    }
                },{  
                    text:" 取 消 ",  
                    handler:function(){  
                       _querywindow.hide();
                    }
                }
            ]  
        });
	_querywindow.show();
	_querywindow.center();
}
function $colconfig(){
	window.open('<c:url value="/frame.do?method=colconfig"/>&formcode=47c36268b7c611e1ba92af790c025a41_&naturalname=${param.naturalname}');
}

$(function(){
	<rs:noPermission action="7" resource="${naturalname_dyform}.LIST">
	//alert('无操作权限，请联系管理员！');
	//window.opener=null;
	//window.close();
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
