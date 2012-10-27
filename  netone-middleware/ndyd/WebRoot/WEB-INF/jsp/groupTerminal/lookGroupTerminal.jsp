<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-min.jsp"></jsp:include>
		<jsp:include page="../common/metaJQuery-ui-tab.jsp" />
		<script language="javascript" type="text/javascript" src="<%=path%>/script/jquery-plugin/easyui/easyloader.js" ></script>
		<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<script>
			$(function() {
				$("#tabs").tabs();
				$('#tabs').tabs('select', "tabs-3");
			});
			
		</script>
		
		<title>集团终端</title>
	</head>
	<body>
		<div style="margin: 8px;">
			<table width="100%">
				<tr>
					<td width="28px">
						<div id="top_nd-title">
						</div>
					</td>
					<td  id="title_nd">
						查看
					</td>
				</tr>
			</table>
			<hr id="hr_nd">
		 </div>
		<div id="tabs" style="height: 100%;">
			<ul>
				<li>
					<a href="#tabs-1" class="">流程跟踪</a>
				</li>
				<li>
					<a href="#tabs-2">审批意见</a>
				</li>
				<li>
					<a href="#tabs-3" class="ui-tabs-selected">流程办理</a>
				</li>
			</ul>
			<div id="tabs-1">
				<table class="main_nd" width="100%">
					<tr>
						<td nowrap="nowrap" class="label_nd_1" width="20%">
							日期
						</td>
						<td nowrap="nowrap" class="label_nd_1" width="20%">
							执行人
						</td>
						<td nowrap="nowrap" class="label_nd_1" width="40%">
							执行行动
						</td>
						<td nowrap="nowrap" class="label_nd_1" width="20%">
							目标
						</td>
					</tr>
					<tr>
						<td id="active" colspan="4">无</td>
					</tr>
				</table>
				<table class="main_nd" width="100%">
					<tr>
						<td nowrap="nowrap" class="label_nd_1" width="100%">
							当前文档流程状态图
						</td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="label_nd_2" width="100%" height="300px">
							<c:choose>
								<c:when test="${runtimeid!='' && !empty runtimeid}">
									<iframe name="myframe" id="myframe"
										src="<portal:envget envkey='WEBSER_WFWEB' />/listRuntimeprocess.do?runtimeid=${runtimeid}"
										width="100%" height="450px" frameborder="0" scrolling="auto"></iframe>
								</c:when>
								<c:otherwise>
									<iframe name="myframe" id="myframe"
											src="<portal:envget envkey='WEBSER_WFWEB' />/viewreadonlyprocess.do?processid=${processid}"
											width="100%" height="450px" frameborder="0" scrolling="auto"></iframe>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
			</div>
			<div id="tabs-2" style="height: 100%">
				<table class="main_nd" width="100%">
					<tr>
						<td nowrap="nowrap" class="label_nd_1" width="100%">
							审批意见
						</td>
					</tr>
					<tr>
						<td class='label_nd_2' align="center" id="mytip">
							无
						</td>
					</tr>
				</table>
			</div>

			<div id="tabs-3">
			<form id="groupTerminalFrom" action="" method="post">
				<div id="panel" style='width: 940px; height: 100%;'>
					<div id="div1"
						style='width: 900px; height: 370px; text-align: left'>
							<input type="hidden" id="unid" name="unid"
								value="${param.groupTerminalId}">
							<input type="hidden" id="groupTerminalId" name="groupTerminalId"
								value="${param.groupTerminalId}">
							<table class="mainPI">
								<tr>
									<td nowrap="nowrap" class="labelPI">
										需求说明
										<font color="red">*</font>:
									</td>
									<td colspan="5">
										<select name="subjectId" id="subjectId" style="width: 100%;" disabled="disabled">
										</select>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" class="labelPI">
										客户姓名
										<font color="red">*</font>:
									</td>
									<td colspan="2">
										<input class="text" type="text" id="customerName"
											name="customerName" style="width: 100%;" value="${groupTerminal.customerName}"  readonly="readonly"  />
									</td>
									<td nowrap="nowrap" class="labelPI">
										手机号码:
									</td>
									<td colspan="2">
										<input class="text" type="text" id="customerPhone"
											name="customerPhone" style="width: 100%;" value="${groupTerminal.customerPhone}" readonly="readonly" />
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" class="labelPI">
										客户经理:
									</td>
									<td colspan="2">
										<c:choose>
											<c:when test="${!empty groupTerminal.cid}">
												<input class="text" type="hidden" id="clientId" name="clientId" value="${groupTerminal.cid}"/>
												<input class="text" type="text" id="clientName" name="clientName" value="${groupTerminal.cname}"style="width: 100%;" disabled="disabled"/>
											</c:when>
											<c:otherwise>
												<input class="text" type="hidden" id="clientId" name="clientId" value="${cid}"/>
												<input class="text" type="text" id="clientName" name="clientName" value="${cname}"style="width: 100%;" disabled="disabled"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td nowrap="nowrap" class="labelPI">
										客户联系号码:
									</td>
									<td colspan="2">
										<input class="text" type="text" id="phone" name="phone" style="width: 100%;" value="${groupTerminal.phone}"  readonly="readonly" />
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" class="labelPI">
										集团名称
										<font color="red">*</font>:
									</td>
									<td colspan="2">
										<input class="text" type="text" id="groupName"
											name="groupName" style="width: 100%;" value="${groupTerminal.groupName}" readonly="readonly"/>
									</td>
									<td nowrap="nowrap" class="labelPI">
										集团编号:
									</td>
									<td colspan="2">
										<input class="text" type="text" id="groupId" name="groupId" value="${groupTerminal.groupId}" style="width: 100%;"  readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" class="labelPI">
										集团价值等级
										<font color="red">*</font>:
									</td>
									<td>
										<select name="groupValueLevel" id="groupValueLevel" style="width: 160px;" value="${groupTerminal.groupValueLevel}"  disabled="disabled">
										</select>
									</td>
									<td nowrap="nowrap" class="labelPI">
										职务
										<font color="red">*</font>:
									</td>
									<td>
										<input class="text" type="text" id="post" name="post" style="width: 100%;" value="${groupTerminal.post}" readonly="readonly" />
									</td>
									<td nowrap="nowrap" class="labelPI">
										职级
										<font color="red">*</font>:
									</td>
									<td>
										<input class="text" type="text" id="postLevel"
											name="postLevel" style="width: 100%;" value="${groupTerminal.postLevel}" readonly="readonly"/>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" class="labelPI">
										手机厂家
										<font color="red">*</font>:
									</td>
									<td>
										<input class="text" type="text" id="phoneManufacturers"
											name="phoneManufacturers" style="width: 100%;" value="${groupTerminal.phoneManufacturers}" readonly="readonly"/>
									</td>
									<td nowrap="nowrap" class="labelPI">
										手机型号
										<font color="red">*</font>:
									</td>
									<td>
										<input class="text" type="text" id="phoneModels"
											name="phoneModels" style="width: 100%;" value="${groupTerminal.phoneModels}"  readonly="readonly" />
									</td>
									<td nowrap="nowrap" class="labelPI">
										入网时间
										<font color="red">*</font>:
									</td>
									<td>
										<c:choose>
											<c:when test="${!empty groupTerminal.networkTime}">
												<input type="text" id="networkTime" name="networkTime"
													value="<fmt:formatDate value='${groupTerminal.networkTime}' pattern='yyyy-MM-dd'/>"
													style="width: 120px"
													onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" class="Wdate"  disabled="disabled"/>
											</c:when>
											<c:otherwise>
												<input type="text" id="networkTime" name="networkTime"
													value="<fmt:formatDate value='<%=new java.util.Date(System.currentTimeMillis())%>' pattern='yyyy-MM-dd'/>"
													style="width: 120px"
													onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" class="Wdate"   disabled="disabled"/>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" class="labelPI">
										手机单价:
									</td>
									<td>
										<input class="text" type="text" id="phonePrice"
											name="phonePrice" style="width: 100%;" value="${groupTerminal.phonePrice}" readonly="readonly" />
									</td>
									<td nowrap="nowrap" class="labelPI">
										手机数量
										<font color="red">*</font>:
									</td>
									<td>
										<input class="text" type="text" id="phoneAmount"
											name="phoneAmount" style="width: 100%;" value="1" value="${groupTerminal.phoneAmount}"  readonly="readonly" />
									</td>
									<td nowrap="nowrap" class="labelPI">
										总价格
										<font color="red">*</font>:
									</td>
									<td>
										<input class="text" type="text" id="priceCount"
											name="priceCount" style="width: 100%;" readonly="readonly" value="${groupTerminal.priceCount}"/>
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" class="labelPI">
										申请项目
										<font color="red">*</font>:
									</td>
									<td colspan="2">
										<select name="applicationProject" id="applicationProject" value="${groupTerminal.applicationProject}" style="width:100%"  disabled="disabled" >
										</select>
									</td>
									<td nowrap="nowrap" class="labelPI">
										预存或赠送话费(元):
									</td>
									<td colspan="2">
										<input class="text" type="text" id="storedOrGive"
											name="storedOrGive" style="width: 100%;" value="${groupTerminal.storedOrGive}"  readonly="readonly" />
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" class="labelPI">
										月保底消费金额(元):
									</td>
									<td colspan="2">
										<input class="text" type="text" id="monthConsumption"
											name="monthConsumption" style="width: 100%;" value="${groupTerminal.monthConsumption}" readonly="readonly" />
									</td>
									<td nowrap="nowrap" class="labelPI">
										保底月份数:
									</td>
									<td colspan="2">
										<input class="text" type="text" id="monthAmount"
											name="monthAmount" value="12" style="width: 100%;" value="${groupTerminal.monthAmount}"  readonly="readonly" />
									</td>
								</tr>
								<tr>
									<td nowrap="nowrap" class="labelPI">
										已办优惠方案
										<font color="red">*</font>:
									</td>
									<td colspan="2">
										<select name="preferentialSchemes" id="preferentialSchemes" style="width:100%"  disabled="disabled" >
											<option  <c:if test="${groupTerminal.preferentialSchemes==0}"> selected </c:if> value=0>否</option>
											<option <c:if test="${groupTerminal.preferentialSchemes==1}"> selected </c:if> value=1>是</option>
										</select>
									</td>
									<td nowrap="nowrap" class="labelPI">
										活动名称及到期时间:
									</td>
									<td colspan="2">
										<input class="text" type="text" id="activityTitleAndDueTime"
											name="activityTitleAndDueTime"  style="width: 100%;" value="${groupTerminal.activityTitleAndDueTime}"  readonly="readonly" />
									</td>
								</tr>
							</table>
						
					</div>
				</div>
				<iframe id="fileMainFrame" name="fileMainFrame" 
					src="<c:url value='/file.do?method=onMainView&d_unid=${param.groupTerminalId}&readonly=${param.readonly}' />"
					scrolling="auto" frameborder="0"
					style="width: 100%;">
				</iframe>
				<table style="width: 100%" class="main_nd">
					<tr>
						<td class="label_nd_1" colspan="5">
							申请简述
						</td>
					</tr>
					<tr>
						<td colspan="5">
							<textarea rows="5" style="width: 99%;" id="applyNote" name="applyNote"  disabled="disabled" >${groupTerminal.applyNote}</textarea>
						</td>
					</tr>
				</table>
				</form>
				<table class="main_nd" width="100%">
					<tr>
						<td nowrap="nowrap" class="label_nd_1" width="100%">
							审批意见
						</td>
					</tr>
					<tr>
						<td class='label_nd_2' align="center" id="mytip_">
							无
						</td>
					</tr>
				</table>
				
			</div>
		</div>
	</body>
</html>
<script>



	function loadInfo()
	{
		Ext.Ajax.request({ 
  			url: "<c:url value='/shopping/shopping.do?method=bussTipListView&workcode=${param.workcode}&runtimeid=${param.runtimeid}' />", 
  			success: function(response, config){ 
    			//alert(config.url + "," + config.method); 
		    //Ext.MessageBox.alert("result", response.responseText);
		    document.getElementById('mytip').innerHTML = response.responseText;
		    document.getElementById('mytip_').innerHTML = response.responseText;
			    	Ext.Ajax.request({ 
			  			url: "<c:url value='/shopping/shopping.do?method=queryActive&workcode=${param.workcode}&runtimeid=${param.runtimeid}' />", 
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
	
$(function(){
	easyloader.base = '<%=path%>/script/jquery-plugin/easyui/';    // set the easyui base directory
	easyloader.locale = 'zh_CN';
	easyloader.load('validatebox', function(){// load the specified module
	    $("#customerName").validatebox({required:true});
	    $("#groupName").validatebox({required:true});
	    $("#post").validatebox({required:true});
	    $("#postLevel").validatebox({required:true});
	    $("#phoneManufacturers").validatebox({required:true});
	    $("#phoneModels").validatebox({required:true});
	    $("#phoneAmount").validatebox({required:true});
	    $("#applyNote").validatebox({required:true});
	});
	easyloader.load('numberbox', function(){// load the specified module
		$("#phonePrice").numberbox({max:999999999999.99,precision:2,required:false});
		$("#phoneAmount").numberbox({max:999999999999,precision:0,required:true});
		$("#storedOrGive").numberbox({max:999999999999.99,precision:2,required:false});
		$("#monthConsumption").numberbox({max:999999999999.99,precision:2,required:false});
		$("#monthAmount").numberbox({max:999999999999,precision:0,required:false});
	});
});

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
			title: '近期消费',
			header : true,
			// footer:true,
			clicksToEdit : 1,
			enableHdMenu : false,
			//style : 'margin-left:10px;margin-right:10px',
			store : new Ext.data.Store({
				url : "<c:url value='/groupTerminal/groupTerminal.do?method=onGroupTerminalDetailList' />", // JSON数据
				reader : new Ext.data.JsonReader({
							root : 'rows'
						}, [ {name : 'groupTerminalDetailId'},
							 {name : 'lastlastlastMonth'},
							 {name : 'monthBeforeLast'},
							 {name : 'lastMonth'},
							 {name : 'totalMonth'},
							 {name : 'averageConsumption'}
							 ])
			}),
			columns : [index, {
						id:'lastlastlastMonth',
						header : "上上上月",
						width : 80,
						align : 'center',
						dataIndex : 'lastlastlastMonth'
					}, {
						id:'monthBeforeLast',
						header : "上上月",
						width : 80,
						align : 'center',
						dataIndex : 'monthBeforeLast'
					},{
						id: 'lastMonth',
						header : "上月",
						align : 'center',
						width : 80,
						dataIndex : 'lastMonth'
					}, {
						id: 'totalMonth',
						header : "合计",
						align : 'center',
						width : 80,
						dataIndex : 'totalMonth',
						renderer:
						function todo(value, cellmeta, record, rowIndex, columnIndex, store){
						
							var count = 0;
							var count_ = 0;
							var step = 3;
							var lastlastlastMonth = parseFloat(record.get('lastlastlastMonth'));
							var monthBeforeLast = parseFloat(record.get('monthBeforeLast'));
							var lastMonth = parseFloat(record.get('lastMonth'));
							if (!isNaN(lastlastlastMonth)){
								count =  count+lastlastlastMonth;
							}
							if(!isNaN(monthBeforeLast)){
								count =  count+monthBeforeLast;
							}
							if(!isNaN(lastMonth)){
								count =  count+lastMonth;
							}
							return count;
						}
					}, {
						id: 'averageConsumption',
						header : "近三个月月均消费",
						align : 'center',
						width : 80,
						dataIndex : 'averageConsumption',
						renderer:
						function todo(value, cellmeta, record, rowIndex, columnIndex, store){
						
							var count = 0;
							var count_ = 0;
							var step = 3;
							var lastlastlastMonth = parseFloat(record.get('lastlastlastMonth'));
							var monthBeforeLast = parseFloat(record.get('monthBeforeLast'));
							var lastMonth = parseFloat(record.get('lastMonth'));
							if (!isNaN(lastlastlastMonth)){
								count =  count+lastlastlastMonth;
							}
							if(!isNaN(monthBeforeLast)){
								count =  count+monthBeforeLast;
							}
							if(!isNaN(lastMonth)){
								count =  count+lastMonth;
							}
							
							count_ = count/step;
							count_ = (Math.round(count_*100)/100); 
							return count_;
						}
					}],
			viewConfig : {
				forceFit : true
			},
			loadMask : true
		}
		// apply config
		Ext.apply(this, Ext.apply(this.initialConfig, config));
		
	
		// call parent
		Plan.Data.PlanGrid.superclass.initComponent.apply(this, arguments);

		this.store.on('beforeload', function(store, options) {
					var condition = {// 取得HTML页面的查询条件
					};
					options = options || {};
					options.params = options.params || {};
					//options.params.condition = Ext.util.JSON.encode(condition);
					options.params.groupTerminalId = Ext.get('groupTerminalId').getValue();
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
    var newRs = new Ext.data.Record({
        'groupTerminalDetailId': currRowIndex++,
        'lastlastlastMonth': '',
        'monthBeforeLast': '',
        'lastMonth': '',
        'totalMonth': '',
        'averageConsumption': ''
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
    for (var i = 0; i < 1; i++) { // 默认添加7行
        appendRow();
    }
    if (!isedit) document.getElementById('groupTerminalId').value = '';
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
function loadSubjectType(){//加载需求名称
     var balanceType = '${groupTerminal.subjectId}';
	 var vUrl = '<c:url value="/common/systemConfig.do?method=onGetSystemConfig"/>'+'&type=subject';
		Ext.Ajax.request({
		   url:  vUrl,
		   success: function(response, options){
						  var responseArray = Ext.util.JSON.decode(response.responseText);     
						  var subjectSelect = document.getElementById('subjectId');
						  for(var i=0; i< responseArray.length; i++){
						     var option = document.createElement('option');
							 option.text = responseArray[i].name ;
			                 option.value = responseArray[i].sid;
							 if((typeof(balanceType) != 'undefined') && (balanceType.trim() == option.value.trim())){
					     		option.setAttribute('selected', 'selected');
					 		 }
							 subjectSelect.options.add(option);
						  }         
		  			},
	        failure: function (response, options) {
	            checkAjaxStatus(response);
	        }
		});
}

function loadGroupLevelType(){//加载集团价值等级
     var balanceType = '${groupTerminal.groupValueLevel}';
	 var vUrl = '<c:url value="/common/systemConfig.do?method=onGetSystemConfig"/>'+'&type=groupLevel';
		Ext.Ajax.request({
		   url:  vUrl,
		   success: function(response, options){
						  var responseArray = Ext.util.JSON.decode(response.responseText);     
						  var groupLevelSelect = document.getElementById('groupValueLevel');
						  for(var i=0; i< responseArray.length; i++){
						     var option = document.createElement('option');
							 option.text = responseArray[i].name ;
			                 option.value = responseArray[i].sid;
							 if((typeof(balanceType) != 'undefined') && (balanceType.trim() == option.value.trim())){
					     		option.setAttribute('selected', 'selected');
					 		 }
							 groupLevelSelect.options.add(option);
						  }         
		  			},
	        failure: function (response, options) {
	            checkAjaxStatus(response);
	        }
		});
}
function loadApplyType(){//加载集团价值等级
     var balanceType = '${groupTerminal.applicationProject}';
	 var vUrl = '<c:url value="/common/systemConfig.do?method=onGetSystemConfig"/>'+'&type=apply';
		Ext.Ajax.request({
		   url:  vUrl,
		   success: function(response, options){
						  var responseArray = Ext.util.JSON.decode(response.responseText);     
						  var applySelect = document.getElementById('applicationProject');
						  for(var i=0; i< responseArray.length; i++){
						     var option = document.createElement('option');
							 option.text = responseArray[i].name ;
			                 option.value = responseArray[i].sid;
							 if((typeof(balanceType) != 'undefined') && (balanceType.trim() == option.value.trim())){
					     		option.setAttribute('selected', 'selected');
					 		 }
							 applySelect.options.add(option);
						  }         
		  			},
	        failure: function (response, options) {
	            checkAjaxStatus(response);
	        }
		});
}



Ext.onReady(function(){
	    var panel = new Ext.Panel({
	          applyTo :'panel',
	          border: true,
	         layout : "border",
			items : [{
						xtype : "panel",
						region : "north",
						id : "north",
						height : 260,
						contentEl : 'div1',// 加载本地资源
						// border:false,
						collapsible : true,
						style : "padding:5px"
					}, {
						region : "center",
						id : "PlanGrid",
						xtype : "PlanGrid",
						border : false,
						hideBorders : true,
						autoScroll : true
					}]
		});
		
		refresh = function(){
			Ext.getCmp('PlanGrid').store.load();
		}
		
		var planId = Ext.get('groupTerminalId').getValue();
	    if (planId != '') {
	       // getClientInfo(Ext.get('clientId').getValue());
	       refresh(); // 初始化加载数据
	
	    } else {
	
	      //for (var i = 0; i < 1; i++) { // 默认添加7行
	            appendRow();
	       // }
	    }
	    loadSubjectType();//加载需求名称
	    loadGroupLevelType();//加载集团价值等级
	    loadApplyType();//申请项目
	    bindBlur();
	    loadInfo();
 	});
 	//绑定Blur事件
   function bindBlur(){
		var phonePrice=document.getElementById("phonePrice");
   		var phoneAmount=document.getElementById("phoneAmount");
   		phonePrice.onblur=sumAll;
   		phoneAmount.onblur=sumAll; 
   }	
 	
   //自动生成汇总额
   function sumAll(){
   		var phonePrice=document.getElementById("phonePrice");
   		var phoneAmount=document.getElementById("phoneAmount");
   		var priceCount=document.getElementById("priceCount");
   		var sum=1;
   		if(phonePrice.value!="undefined"&&phonePrice.value!=null&&phonePrice.value.trim()!=""){
   			sum*=Number(phonePrice.value);
   		}
   		if(phoneAmount.value!="undefined"&&phoneAmount.value!=null&&phoneAmount.value.trim()!=""){
   			sum*=Number(phoneAmount.value);
   		}
   		priceCount.value=sum;
   }
</script>