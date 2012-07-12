﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@ include file="../common/taglibs.jsp"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-ui-dialog.jsp"></jsp:include>
		<jsp:include page="../common/metaUnitsUtil.jsp"></jsp:include>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<title>:制订货单窗口::</title>
	</head>
	<style>
		table.main {
			border-collapse: collapse;
			boder-spacing:1px;
		}
		td.label_1 {
			FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType =   0, StartColorStr =   #E9F7FF, EndColorStr =#69C3FD);
			background: -webkit-gradient(linear, left top, left bottom, from(#E9F7FF), to(#69C3FD)); /* for webkit browsers */
			background: -moz-linear-gradient(top,  #E9F7FF,  #69C3FD); /* for firefox 3.6+ */
			color: #000066;
			text-align: left;
			padding-left: 10px;
			padding-right: 10px;
			font-style: normal;
			border: 1px solid #86B4E5;
			line-height: 20px;
		}
		td.label_2 {
			background-color: #fff;
			color: #000;
			text-align: left;
			padding-left: 10px;
			padding-right: 10px;
			font-style: normal;
			border: 1px solid #8D8D8D;
			line-height: 20px;
		}
	</style>
	<script type="text/javascript">
	function loadInfo()
	{
		Ext.Ajax.request({ 
  			url: "shopping.do?method=bussTipListView&workcode=${param.workcode}", 
  			success: function(response, config){ 
    			//alert(config.url + "," + config.method); 
		    //Ext.MessageBox.alert("result", response.responseText);
		    document.getElementById('mytip').innerHTML = response.responseText;
			    	Ext.Ajax.request({ 
			  			url: "shopping.do?method=queryActive&workcode=${param.workcode}", 
			  			success: function(response, config){ 
			    			//alert(config.url + "," + config.method); 
					    //Ext.MessageBox.alert("result", response.responseText);
					    document.getElementById('active').innerHTML = response.responseText;
						    
			  			}, 
					  failure: function(){ 
					    Ext.MessageBox.alert("result", "请求失败"); 
					  }, 
					  method: "post"
					}); 
  			}, 
		  failure: function(){ 
		    Ext.MessageBox.alert("result", "请求失败"); 
		  }, 
		  method: "post"
		}); 
	}
 
	</script>
	<body>
	<div id="div_1"></div>
		<div id="_openDiv2">
			<div id="_innerHtml2"></div>
		</div>
		<div id="panel" style='width: 100%; height: 100%;'> 
			<div id="div1" style='width: 100%; height: 400px; text-align: left'>
					<input type="hidden" id="type1time" name="type1time"
					value="${type1time}">
				<input type="hidden" id="querType" name="querType">
				<form id="planform" action="" method="post">
					<!-- 当前登录客户编号 -->
					<input type="hidden" id="shoppingId" name="shoppingId" value="${shopping.shoppingId}">
					<table class="mainPI" >
						<tr>
							<td nowrap="nowrap" class="labelPI">
								请购说明<font color="red">*</font>:
							</td>
							<td colspan="3">
								<input class="text" type="text" style="width: 80%;" id="purchaseIllustrate" name="purchaseIllustrate" value="${shopping.purchaseIllustrate}" <c:if test="${shopping.status > 0}">  readonly="readonly"</c:if>/>
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" class="labelPI">
								请购人:
							</td>
							<td>
							<c:choose>
									<c:when test="${!empty shopping.cid}">
										<input type="hidden" id="clientId" name="clientId"
											value="${shopping.cid}">
										<input readonly="readonly" class="text" type="text"
											id="clientName" name="clientName" value="${shopping.cname}" />
									</c:when>
									<c:otherwise>
										<input type="hidden" id="clientId" name="clientId"
											value="${cid}">
										<input readonly="readonly" class="text" type="text"
											id="clientName" name="clientName" value="${cname}" />
									</c:otherwise>
								</c:choose>
							</td>
							<td nowrap="nowrap" class="labelPI">
								请购时间:
							</td>
							<td>
								
											
								<c:choose>
									<c:when test="${!empty shopping.operateTime}">
										<input type="text" id="operateTime" name="operateTime"
											value="<fmt:formatDate value='${shopping.operateTime}' pattern='yyyy-MM-dd HH:mm:ss'/>"
											style="width: 160px"
											onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" class="Wdate" <c:if test="${shopping.status > 0}">   disabled="disabled"</c:if>/>
									</c:when>
									<c:otherwise>
										<input type="text" id="operateTime" name="operateTime"
											value="<fmt:formatDate value='<%=new java.util.Date(System.currentTimeMillis())%>' pattern='yyyy-MM-dd HH:mm:ss'/>"
											style="width: 160px"
											onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" class="Wdate" <c:if test="${shopping.status > 0}">   disabled="disabled"</c:if>/>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td nowrap="nowrap" class="labelPI">
								金额:
							</td>
							<td nowrap="nowrap" colspan="3">
							<input  class="text" type="text"
									id="price" name="price"  readonly="readonly"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="div2" >
			<table class="main" width="100%">
				<tr>
					<td nowrap="nowrap" class="label_1" width="100%">审批意见</td>
				</tr>
				<tr>
					<td class='label_2'  align="center" id="mytip">无</td>
				</tr>
			</table>
			<table class="main" width="100%">
				<tr>
					<td nowrap="nowrap" class="label_1" width="20%">日期</td>
					<td nowrap="nowrap" class="label_1" width="20%">执行人</td>
					<td nowrap="nowrap" class="label_1" width="40%">执行行动</td>
					<td nowrap="nowrap" class="label_1" width="20%">目标</td>
				</tr>
				<tr><td id="active" colspan="4"></td></tr>
			</table> 
			<table class="main" width="100%">
				<tr>
					<td nowrap="nowrap" class="label_1" width="100%">当前文档流程状态图</td>
				</tr>
				<tr>
					<td nowrap="nowrap" class="label_2" width="100%" height="700"><iframe name="myframe" id="myframe" src="<portal:envget envkey='WEBSER_WFWEB'/>/listRuntimeprocess.do?runtimeid=${runtimeid}" width="100%" height="700" frameborder="0"></iframe></td>
				</tr>
			</table>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
   
Ext.ns('Plan.Data');
Plan.Data.PlanGrid = Ext.extend(Ext.grid.EditorGridPanel, {

	initComponent : function() {
		var index = new Ext.grid.RowNumberer();//
		var cb = new Ext.grid.CheckboxSelectionModel({ // 创建Grid表格组件
			singleSelect : false
		});
		var config = {
			sm : cb,
			frame : false,
			border : false,
			title: '采购项目明细',
			header : true,
			// footer:true,
			clicksToEdit : 1,
			enableHdMenu : false,
			//style : 'margin-left:10px;margin-right:10px',
			store : new Ext.data.Store({
				url : "<c:url value='/shopping/shopping.do?method=onShoppingDetailList' />", // JSON数据
				reader : new Ext.data.JsonReader({
							root : 'rows'
						}, [ {name : 'shoppingDetailId'},
							 {name : 'deviceName'},
							 {name : 'units'},
							 {name : 'consignmentCount'},
							 {name : 'unitPrice'},
							 {name : 'hopeDeliveryTime'},
							 {name : 'suggestBrand'},
							 {name : 'note'}
							 ])
			}),
			columns : [index, cb, {
						id : "deviceName",
						header : "设备名称(型号)<font color='red'>*</font>",
						align : 'center',
						width : 100,
						dataIndex : 'deviceName'
						<c:if test="${shopping.status==0}">
						,editor : new Ext.form.TextField({})
						</c:if>
					}, {
						id:'units',
						header : "单位<font color='red'>*</font>",
						align : 'center',
						width : 100,
						dataIndex : 'units'
						<c:if test="${shopping.status==0}">
						,editor : new Ext.form.TextField({})
						</c:if>
					}, {
						id:'consignmentCount',
						header : "数量<font color='red'>*</font>",
						width : 100,
						align : 'center',
						dataIndex : 'consignmentCount'
						<c:if test="${shopping.status==0}">
						,editor : new Ext.form.TextField({
							// allowDecimals:true,
							// decimalPrecision:2,
							allowNegative : true,
							name : 'text',
							allowBlank : false,
							//maxLengthText : '最多只能输入小于6位的数字(包括小数点),例如:10.10等于10箱+10瓶',
							//regexText : '最多只能输入小于6位的数字(包括小数点), 例如:10.10等于10箱+10瓶',
							regex:/^[+-]?[0-9]{1,12}\.{0,1}[0-9]{0,2}$/
						//	blankText : '最多只能输入小于6位的数字(包括小数点), 例如:10.10等于10箱+10瓶'
						})
						</c:if>
					}, {
						id: 'unitPrice',
						header : "单价<font color='red'>*</font>",
						align : 'center',
						width : 100,
						dataIndex : 'unitPrice'
						<c:if test="${shopping.status==0}">
						,editor : new Ext.form.TextField({
							allowNegative : true,
							name : 'text',
							allowBlank : false,
							regex:/^[+-]?[0-9]{1,12}\.{0,1}[0-9]{0,2}$/
						})
						</c:if>
					}, {
						id: 'price',
						header : "金额<font color='red'>*</font>",
						align : 'center',
						width : 100,
						dataIndex : 'price',
						renderer:
						function todo(value, cellmeta, record, rowIndex, columnIndex, store){
							var unitPrice = record.get('unitPrice');
							var consignmentCount = record.get('consignmentCount');
							var count = unitPrice*consignmentCount;
							/**统计列金额 */
							var currRowIndex = store.getCount();
							var totalPrices = 0;
						  	for(var i=0;i<currRowIndex;i++){
								var j = store.getAt(i);
								var _unitPrice = j.get('unitPrice');
								var _consignmentCount = j.get('consignmentCount');
								if (_unitPrice!='' && _unitPrice!='0'　&& _unitPrice!=null){
									if(_consignmentCount!=null){
										totalPrices+=_unitPrice*_consignmentCount;
									}
								}
							}
							document.getElementById('price').innerText=totalPrices;
								
							return count;
						}
					}, {
						id: 'hopeDeliveryTime',
						header : "希望到货时间<font color='red'>*</font>",
						align : 'center',
						width : 100,
						dataIndex : 'hopeDeliveryTime'
						//,editor : new Ext.form.TextField()
						<c:if test="${empty param.shoppingId}">
						,renderer: Ext.util.Format.dateRenderer('Y-m-d')
						</c:if>
						<c:if test="${shopping.status==0}">
						,editor : new Ext.form.DateField({format :'Y-m-d'})
						</c:if>
					}, {
						id: 'suggestBrand',
						header : "建议品牌或厂家<font color='red'>*</font>",
						align : 'center',
						width : 100,
						dataIndex : 'suggestBrand'
						<c:if test="${shopping.status==0}">
						,editor : new Ext.form.TextField()
						</c:if>
					}, {
						id: 'note',
						header : "备注",
						align : 'center',
						width : 100,
						dataIndex : 'note'
						<c:if test="${shopping.status==0}">
						,editor : new Ext.form.TextField()
						</c:if>
					}],
			viewConfig : {
				forceFit : true
			},
			loadMask : true
		}
		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config));
		
<c:if test="${shopping.status==0}">
		this.bbar = new Ext.Panel({
					collapsible : true,
					closable : true,
					height : 50,
					listeners:{
						'render':function(){
							this.bbar.dom.align='right';
						}
					},
					//contentEl : 'div2',// 加载本地资源
					bbar : new Ext.Toolbar([
							{
								text : '新增行 ',
								iconCls : 'addIcon',
								id : 'ext_b_addRow',
								handler:appendRow
							}, {
								text : '删除行 ',
								iconCls : 'deleteIcon',
								id : 'ext_b_deleteRow',
								handler:deleteRow
							}, {
								text : '附件 ',
								iconCls : 'excelIcon',
								id : 'ext_b_upLoad'
							}])
					
				});
			</c:if>	
		// call parent
		Plan.Data.PlanGrid.superclass.initComponent.apply(this, arguments);

		this.store.on('beforeload', function(store, options) {
					var condition = {// 取得HTML页面的查询条件
					};
					options = options || {};
					options.params = options.params || {};
					//options.params.condition = Ext.util.JSON.encode(condition);
					options.params.shoppingId = Ext.get('shoppingId').getValue();
					return true;
				});
		// load the store at the latest possible moment
		this.on({
					beforeshow : {
						scope : this,
						single : true,
						fn : function() {
							this.store.load();
						}
					}
				});
	}
});
Ext.reg('PlanGrid', Plan.Data.PlanGrid);// 注册一个组件,注册成xtype以便能够延迟加载
   
   
   
   function appendRow() { // 动态添加行
    var PlanGrid2 = Ext.getCmp('PlanGrid');
    var currRowIndex = PlanGrid2.store.getCount();
    for (var i = 0; i < currRowIndex; i++) {
        var obj = PlanGrid2.store.getAt(i);
        if (obj.get('productCode') == '合计') {
            PlanGrid2.store.remove(obj);
            break;
        }
    }

    var currRowIndex = PlanGrid2.store.getCount();
    var newRs = new Ext.data.Record({
        'shoppingDetailId': currRowIndex++,
        'deviceName': '',
        'units': '',
        'consignmentCount': '',
        'unitPrice': '',
        'price': '',
        'hopeDeliveryTime': '',
        'suggestBrand': '',
        'note': ''
    });
    PlanGrid2.store.add(newRs); // 复制
}
function deleteRow() { // 动态删除行'
    var PlanGrid2 = Ext.getCmp('PlanGrid');
    var recs = PlanGrid2.getSelectionModel().getSelections();
    var list = [];
    if (recs.length == 0) {
         Ext.MessageBox.alert('提示','请选择要进行操作的列表！');
    } else {
        for (var i = 0; i < recs.length; i++) {
            var rec = recs[i];
            PlanGrid2.store.remove(rec);
        }
    }
}
function _clearForm(isedit) {
    Ext.getCmp('PlanGrid').store.removeAll();
    for (var i = 0; i < 7; i++) { // 默认添加7行
        appendRow();
    }
    if (!isedit) document.getElementById('shoppingId').value = '';
}
function loadTips() { // 加载提示信息
    var ext_b_search = document.getElementById('ext_b_search');
    if (ext_b_search != null) {
        var eTip1 = new Ext.ToolTip({
            html: "小提示:请选择一行",
            target: "ext_b_search"
        });
    }
    var ext_b_deleteRow = document.getElementById('ext_b_deleteRow');
    if (ext_b_deleteRow != null) {
        var eTip2 = new Ext.ToolTip({
            html: "小提示:请选择要删除的行",
            target: "ext_b_deleteRow"
        });
    }
}
function _writetip()
{
	var add_winForm = new Ext.form.FormPanel
	({
		layout:'fit',
		labelWidth:75,
        //monitorValid:true,
        frame:true,
        width:400,
        height:280,
        items:
        [
        {
        	width:400,
            height:280,
            name:'content',
            //allowBlank:false,
            xtype:'textarea'
        }
        ]
    });
	
	var syswin = new Ext.Window
	({   
		title: "填写意见",   
		width: 420,   
		height: 330,   
		plain: true,   
		iconCls: "addicon",   
		resizable: false, //不可以随意改变大小     
		//draggable:false, //是否可以拖动     
		collapsible: true, //允许缩放条   
		closeAction: 'close',   
		closable: true,   
		//弹出模态窗体   
		modal: 'true',   
		buttonAlign: "center",    
		items: [add_winForm],   
		buttons: 
		[
		{   
			text: "保 存",   
			minWidth: 70,
			type:'submit',
			handler: function() 
			{   
				if (add_winForm.getForm().isValid()) {   
			    	add_winForm.getForm().submit
			    	({  
				    	method : 'POST',
				    	url: 'busstip.do?method=onSave&workcode=${param.workcode}',   
				    	waitTitle: '请稍等...',   
				    	waitMsg: '正在提交信息...',   
				        //params: { t: "add" },  
				        success: function(form, action) {  
				                         Ext.Msg.alert('提示', '保存成功!',function callback(){/* Ext.getCmp('msgWindow').getUpdater().refresh(); */});
				                         syswin.close();
				                         window.location.reload(); 
				                         
				        },   
				        failure: function(form,action) {   
				        	Ext.Msg.alert('提示', '保存失败' );
	                    }
			        });
			        //Ext.Msg.alert('提示', '保存成功')
			     }   
			}   
		}, 
		{   
			text: "关 闭",   
			minWidth: 70,   
			handler: function() {   
			                syswin.close();   
			            }   
		}
		]   
	});   
	syswin.show();  
}
function _save() {
    var msgTip = Ext.MessageBox.show({
        title: '提示',
        width: 250,
        msg: '正在保存信息请稍后......'
    });
	/**
     * - 验证
     */
    var clientId = document.getElementById('clientId').value;
 	var purchaseIllustrate = document.getElementById('purchaseIllustrate').value;
    if (clientId == '') {
        Ext.MessageBox.alert('提示', '客户不能为空!');
        return;
    }
    if(purchaseIllustrate==''){
     Ext.MessageBox.alert('提示', '请购说明不能为空!');
       return;
    }
    var store = Ext.getCmp('PlanGrid').store;
    var count = store.getCount();
    
    var jsonStr = '',
        deliter = '';
    for (var i = 0; i < count; i++) {
        var rs = store.getAt(i);
        var deviceName = rs.get('deviceName');
        var units = rs.get('units');
        var consignmentCount = rs.get('consignmentCount');

        var unitPrice = rs.get('unitPrice');
        var hopeDeliveryTime = rs.get('hopeDeliveryTime');
        var suggestBrand = rs.get('suggestBrand');
        var note = rs.get('note');

        var jsonRecord = {
            deviceName: deviceName,
            units: units,
            consignmentCount: consignmentCount,
            unitPrice: unitPrice,
            hopeDeliveryTime: hopeDeliveryTime,
            suggestBrand: suggestBrand,
            note: note
        }
        if (deviceName != '') { // 判断productId是否有空
            if (consignmentCount == '' || parseFloat(consignmentCount) == 0) {
                Ext.MessageBox.alert('提示', '明细列表中,【' + deviceName + '】的数量不能为空,也不能为0!');
                return;
            }

            jsonStr += deliter + Ext.util.JSON.encode(jsonRecord);
            deliter = ',';
        }
    }
    if (jsonStr == '') {
        Ext.MessageBox.alert('提示', '明细不能为空!');
        return;
    }
    var ok = '';

    /**
     * - 保存产销计划及明细
     */
    jsonStr = '[' + jsonStr + ']';
    Ext.Ajax.request({
        url: "<c:url value='/shopping/shopping.do?method=onSavaOrUpdate' />",
        // 请求的服务器地址
        form: 'planform',
        // 指定要提交的表单id
        method: 'POST',
        sync: true,
        params: {
            shoppingDetailInfo: jsonStr
        },
        success: function (response, options) {
            msgTip.hide();
            var result = Ext.util.JSON.decode(response.responseText);
            var auditing_ = true;

            if (result.error != null) {
                Ext.MessageBox.alert('提示', result.tip);
            } else {
            	
            	if (result.shoppingId!=null){
	            		//激活作废按钮
	            		document.getElementById('shoppingId').value=result.shoppingId;
	            		if('${shopping.status}'<=0){
	            		Ext.getCmp('_del_icon').setVisible(true);
	            		}
	            }
            	
            	var paramShoppingId = result.shoppingId;
            	if (paramShoppingId  == '') _clearForm(false); // 保存完之后继续创建
                Ext.ux.Toast.msg("", result.tip);
            }
            _refreshOpenerWin();
            _closeOpenerWin();
        },
        failure: function (response, options) {
            msgTip.hide();
            checkAjaxStatus(response);
            var result = Ext.util.JSON.decode(response.responseText);
            Ext.MessageBox.alert('提示', result.tip);
        }
    });
    
}

function _exit() {
    _refreshOpenerWin();
    window.close();
}

function _refreshOpenerWin(){
	if (window.opener && window.opener.location) {
		if (opener.document.getElementById('openerWinId')){
			opener.refresh();
		}
    }
}

function _closeOpenerWin(){
	if (window.opener && window.opener.location) {
		if (opener.document.getElementById('openerWinIdx')){
			opener.$delnode(document.getElementById('shoppingId').value);
		}
    }
}

	function _auditnext2(ids,values,texts){
		var msgTip = Ext.MessageBox.show({
	        title: '提示',
	        width: 250,
	        msg: '正在执行操作请稍后......'
	    });
		 Ext.Ajax.request({
	        url: "<c:url value='/shopping/shopping.do?method=onAudit&workcode=${param.workcode}' />",
	        // 请求的服务器地址
	        form: 'planform',
	        params:{
	        	values:values,
	        	texts:texts
	        },
	        // 指定要提交的表单id
	        method: 'POST',
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

function _auditNext(){
	_save();
	openWinCenter("人员选择", "<c:url value='/department/user.do?method=onMultiSelectUserFDL&special=1' />", 680, 400);
}

function _rollback(){
openWinCenter("退办", "<c:url value='/shopping/shopping.do?method=onRollBackView' />&workcode=${param.workcode}", 680, 400);
	
}

function _delete(){
	var msgTip = Ext.MessageBox.show({
	        title: '提示',
	        width: 250,
	        msg: '正在执行操作请稍后......'
	    });
		 Ext.Ajax.request({
	        url: "<c:url value='/shopping/shopping.do?method=onDelete' />",
	        // 请求的服务器地址
	        form: 'planform',
	        // 指定要提交的表单id
	        method: 'POST',
	        success: function (response, options) {
	            msgTip.hide();
	            var result = Ext.util.JSON.decode(response.responseText);
	            if (result.error != null) {
	                Ext.MessageBox.alert('提示', result.tip);
	            } else {
	            	document.getElementById('shoppingId').value='';
	            	Ext.getCmp('_del_icon').setVisible(false);
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

 var toolbar = new Ext.Toolbar([
    	 {text : '关闭 ',iconCls:'exitIcon',handler:_exit}
    	 <c:if test="${shopping.status==0}">
 		 ,{text : '保存 ',iconCls:'saveIcon',id:'save_',handler:_save}
 		 </c:if>
 		 <c:if test="${shopping.status>0}">
 		,{text : '填写意见',iconCls:'infoIcon',id:'_txyj',handler:_writetip}
 		 </c:if>
 		 ,{text : '提交下一处理',iconCls:'okIcon',handler:_auditNext}
 		 <c:if test="${shopping.status>0}">
 		 ,{text : '退办',iconCls:'okIcon',id:'_rollback',handler:_rollback}
 		 </c:if>
 		 ,{text : '删除',id:'_del_icon',hidden:true,iconCls:'deleteIcon',handler:_delete}
	]);
 	Ext.onReady(function(){
	    var panel = new Ext.Panel({
		      tbar:toolbar,
		      title:'<font color="red">物品请购\\低值易耗品</font>',
	          applyTo :'panel',
	          layout : "border",
	          border:false,
	          items: [{
				     id: 'tabpanel',
					 xtype: 'tabpanel',
					 region:'center',
					 autoDestroy:true,
					 activeTab : 2,//默认激活第一个tab页   
            		 animScroll : true,//使用动画滚动效果   
            		 border:false,
            		// height:1000,
                     enableTabScroll : true,//tab标签超宽时自动出现滚动按钮 
					 items: [   
				                {title: '流程跟踪',html : '流程跟踪'},   
				                {title: '审批意见',html : '审批意见'},   
				                {title: '流程办理',items : [
				                {
									xtype : "panel",
									region : "north",
									id : "north",
									height : 70,
									contentEl : 'div1',// 加载本地资源
									// border:false,
									collapsible : true
								}, {
									region : "center",
									id : "PlanGrid",
									xtype : "PlanGrid",
									border : false,
									height : 250,
									hideBorders : true,
									autoScroll : true
								},{
									region:'south',
									xtype:'panel',
									//height:700,
									contentEl : 'div2',// 加载本地资源
									border:true
								}]
								}
				            ] 
		    	  }]
		});
		
		refresh = function(){
			Ext.getCmp('PlanGrid').store.load();
		}
		
		var planId = Ext.get('shoppingId').getValue();
	    if (planId != '') {
	       // getClientInfo(Ext.get('clientId').getValue());
	       refresh(); // 初始化加载数据
	
	    } else {
			/*-
			 *初始加载当前登录者的客户编号
			 */
	        <c:if test="${sgs=='kh'}" > getClientInfo(Ext.get('clientId').getValue()); </c:if>
	
	        for (var i = 0; i < 7; i++) { // 默认添加7行
	            appendRow();
	        }
	    }
	    loadInfo();
 	});
 	

 
</script>
