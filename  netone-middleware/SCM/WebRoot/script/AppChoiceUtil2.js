/**
* 应用程序选择JS公共类
* 
**/
/* 部门/公司对象*/
var department = Ext.data.Record.create([
    {name : 'departmentId'},//编号
	{name : 'departmentCode'},//编码
	{name : 'departmentName'},//名称
	{name : 'level'},//级别
	{name : 'levelName'},//级别名称
	{name : 'parentDepartmentCode'},//隶属编码
	{name : 'parentDepartmentName'},//隶属名称
	{name : 'departmentCode1'},//省公司编码
	{name : 'departmentName1'},//省公司名称
	{name : 'departmentCode2'},//隶属大营销部编码
	{name : 'departmentName2'},//隶属大营销部名称
	{name : 'departmentCode3'},//隶属营销部编码
	{name : 'departmentName3'},//隶属营销部名称
	{name : 'departmentCode4'},//隶属经销商编码
	{name : 'departmentName4'},//隶属经销商名称
	{name : 'departmentCode5'},//隶属分销商编码
	{name : 'departmentName5'},//隶属分销商名称
	{name : 'departmentCodex'},//隶属业务主任编码
	{name : 'departmentNamex'},//隶属业务主任名称
	{name : 'linkman'},
	{name : 'telphone'},
	{name : 'mobile'},
	{name : 'email'},
	{name : 'companyAddress'},
	{name : 'servicestation'},
	{name : 'deliverGoodsAddress'},
	{name : 'remitAccounts'},
	{name : 'openAccountTitle'},
	{name : 'ratepayingType'},
	{name : 'lyBailMonery'},
	{name : 'remitPersion'},
	{name : 'priceLevelCode'},
	{name : 'clientTag'},
	{name : 'tons'}
]);

var AppChoiceUtils2 = window.AppChoiceUtils2 || {};//回车事件选择工具类
AppChoiceUtils2 = {
	/**
	* 回车获取对象
	* 1.存在多个相同编码的情况下 必须选择一条记录
	* 2.只存一个编码的情况 返回store
	* @return 返回一条记录record
	           直接通过record.get('departmentId') 来获取对象的参数值
	   页面需要方法 
	   function _SingleChoiceClientInfo2(record){
	   		if (record!=null){
		   		var departmentId = record.get('departmentId');
		   		alert(departmentId)
	   		} else {
	   			//null
	   		}
	   }
	*/
	getSingleClientInfo:function(url,code){
		if (code=='') return;
		Ext.Ajax.request({
				url : url,
				params : {code:code},
				method : 'POST',
				sync:true,
				success : function(response,options){
	  				var json = Ext.util.JSON.decode(response.responseText);
					var reader = new Ext.data.JsonReader({
								root : 'rows',
								totalProperty : 'total'
							}, department);
					var data = reader.readRecords(json);
					var records = data.records;
					
					var count = records.length;
					if (count>1){
						Ext.ux.Toast.msg("", "系统中存在重复编码的部门/客户信息,请选择!");
					    new AppChoiceUtilView2(url,code,'single');
					} else if (count==1){
						_SingleChoiceClientInfo2(records[0]);
					} else {
						Ext.ux.Toast.msg("", "系统中找不到部门/客户信息或没有权限查看!");
						_SingleChoiceClientInfo2(null);
					}
				},
				failure : function(response,options){
				}
		});
	},
	/**
	* 回车获取对象
	* @return 返回record[]对象
	   页面需要方法 
	   function _MultiChoiceClientInfo2(records){
	   	    if (records!==null){
		   		var str = '';
		   		var split = '';
				for (var i = 0; i < records.length; i++) {
					var rec = records[i];
					var departmentName = rec.get('departmentName');
					str += split + departmentName;
					split=',';
				}
				//alert('name:'+str)
			} else {
				//null
			}
	   }
	*/
	getMultiClientInfo:function(url,code){
		if (code=='') return;
		Ext.Ajax.request({
				url : url,
				params : {code:code},
				method : 'POST',
				sync:true,
				success : function(response,options){
	  				var json = Ext.util.JSON.decode(response.responseText);
					var reader = new Ext.data.JsonReader({
								root : 'rows',
								totalProperty : 'total'
							}, department);
					var data = reader.readRecords(json);
					var records = data.records;
					
					var count = records.length;
					if (count>1){
						Ext.ux.Toast.msg("", "系统中存在重复编码的部门/客户信息,请选择!");
					    new AppChoiceUtilView2(url,code,'multi');
					} else if (count==1){
						_MultiChoiceClientInfo2(records);
					} else {
						Ext.ux.Toast.msg("", "系统中找不到部门/客户信息或没有权限查看!");
						_MultiChoiceClientInfo2(null);
					}
				}
		});
	}
}
 
Ext.ns("AppChoiceUtilView2");
var AppChoiceUtilView2 = function (url,code,type) {
	this.url=url;
	this.code=code;
	if (type=='single'){
		this.AppSingleChoiceUtilView2();
	} else if(type=='multi'){
		this.AppMultiChoiceUtilView2();
	}
};
AppChoiceUtilView2.prototype.AppSingleChoiceUtilView2 = function () {
		var _window = Ext.getCmp("ext_window_choice_1_2"); 
		if (_window){
		
		} else {
			var flag = false;
			var w=new Ext.Window({
			   id:'ext_window_choice_1_2',
			   title:'选择',
	           width:400,
	           height:120,
	           layout:'fit',
	           //closable :false,
	           autoScroll:true,
	           items:[this.singlegrid2()],
	           bbar:new Ext.Toolbar.Button({
				  text:'选择',
				  iconCls:"viewIcon",
				  handler:function(){
			    	var records = [];
			    	var recs = Ext.getCmp("ext_window_choice_1_2").findById('ext_window_choice_departmentGridx2').getSelectionModel().getSelections(); 
					if( recs == undefined || recs.length > 1 || recs.length <=0){
						Ext.Msg.alert('系统提示','请选择一条记录!');
						return;
					} else {
						_SingleChoiceClientInfo2(recs[0]);
						flag = true;
						Ext.getCmp("ext_window_choice_1_2").close();
					}
				  }
			   })
	        });
	        w.addListener({"close":function () {
				if (flag==false) _SingleChoiceClientInfo2(null);
			}});
	        w.show();
	        w.bbar.dom.align = 'center';// 按钮居中
 			Ext.getCmp('ext_window_choice_departmentGridx2').addListener('celldblclick', function(grid, rowIndex,
				columnIndex, e) {// 列表双击事件
			    	var records = [];
			    	var recs = Ext.getCmp('ext_window_choice_departmentGridx2').getSelectionModel().getSelections(); 
					if( recs == undefined || recs.length > 1 || recs.length <=0){
						Ext.Msg.alert('提示','请选择一条记录!');
						return;
					} else {
						_SingleChoiceClientInfo2(recs[0]);
						flag = true;
						Ext.getCmp("ext_window_choice_1_2").close();
					}
			});	        
		}
	    
};
AppChoiceUtilView2.prototype.AppMultiChoiceUtilView2 = function () {
		var _window = Ext.getCmp("ext_window_choice_1_2"); 
		if (_window){
		
		} else {
			var flag = false;
			var w=new Ext.Window({
			   id:'ext_window_choice_1',
			   title:'选择',
	           width:400,
	           height:120,
	           layout:'fit',
	           //closable :false,
	           autoScroll:true,
	           items:[this.multigrid2()],
	           bbar:new Ext.Toolbar.Button({
				  text:'选择',
				  iconCls:"viewIcon",
				  handler:function(){
			    	var records = [];
			    	var recs = Ext.getCmp("ext_window_choice_1_2").findById('ext_window_choice_departmentGridx2').getSelectionModel().getSelections(); 
					if (recs.length == 0){
						Ext.Msg.alert('系统提示','请选择要进行操作的信息！');
						return;
					} else {
						for(var i = 0 ; i < recs.length ; i++){
							var rec = recs[i];
							records.push(rec);
						}
						_MultiChoiceClientInfo2(records);
						flag = true;
						Ext.getCmp("ext_window_choice_1_2").close();
					}
				  }
			   })
	        });
	        w.addListener({"close":function () {
				if (flag==false) _MultiChoiceClientInfo2(null);
			}});
	        w.show();
	        w.bbar.dom.align = 'center';// 按钮居中
 			Ext.getCmp('ext_window_choice_departmentGridx2').addListener('celldblclick', function(grid, rowIndex,
				columnIndex, e) {// 列表双击事件
			    	var records = [];
			    	var recs = Ext.getCmp('ext_window_choice_departmentGridx2').getSelectionModel().getSelections(); 
					if (recs.length == 0){
						Ext.Msg.alert('系统提示','请选择要进行操作的信息！');
						return;
					} else {
						for(var i = 0 ; i < recs.length ; i++){
							var rec = recs[i];
							records.push(rec);
						}
						_MultiChoiceClientInfo2(records);
						flag = true;
						Ext.getCmp("ext_window_choice_1_2").close();
					}
			});	        
		}
	    
};

AppChoiceUtilView2.prototype.singlegrid2 = function () {
	var d = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var a = new Ext.grid.ColumnModel({columns:[ new Ext.grid.RowNumberer(), {
		       header : '部门/公司编号',
		       sortable : true,
		       dataIndex : 'departmentCode',
				renderer : function (value, metadata, record, rowIndex, columnIndex, store) {   
				    //build the qtip:   
				    var title = '&nbsp;' + record.get('departmentCode') +   
				        ' ' + record.get('departmentName');   
				    var tip = '业务类型:'+record.get('levelName');
				    
				    metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';   
				    
				    //return the display text:   
				    var displayText = value;   
				    return displayText;
				}
		    }, {
		       header : '部门/公司名称',
		       sortable : true,
		       dataIndex : 'departmentName'
		    }, {
		       header : '隶属名称',
		       sortable : true,
		       dataIndex : 'parentDepartmentName'
		    }]});
	var b = this.store();
	var c = new Ext.grid.GridPanel({id:"ext_window_choice_departmentGridx2", autoScroll :true,store:b, trackMouseOver:true, disableSelection:false, loadMask:true, cm:a, sm:d, viewConfig:{forceFit:true, enableRowBody:false, showPreview:false}});
	return c;
};
AppChoiceUtilView2.prototype.multigrid2 = function () {
	var d = new Ext.grid.CheckboxSelectionModel();
	var a = new Ext.grid.ColumnModel({columns:[d, new Ext.grid.RowNumberer(), {
		       header : '部门/公司编号',
		       sortable : true,
		       dataIndex : 'departmentCode',
				renderer : function (value, metadata, record, rowIndex, columnIndex, store) {   
				    //build the qtip:   
				    var title = '&nbsp;' + record.get('departmentCode') +   
				        ' ' + record.get('departmentName');   
				    var tip = '业务类型:'+record.get('levelName');
				    
				    metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';   
				    
				    //return the display text:   
				    var displayText = value;   
				    return displayText;   
				}
		    }, {
		       header : '部门/公司名称',
		       sortable : true,
		       dataIndex : 'departmentName'
		    }, {
		       header : '隶属名称',
		       sortable : true,
		       dataIndex : 'parentDepartmentName'
		    }]});
	var b = this.store();
	var c = new Ext.grid.GridPanel({id:"ext_window_choice_departmentGridx2", autoScroll :true,store:b, trackMouseOver:true, disableSelection:false, loadMask:true, cm:a, sm:d, viewConfig:{forceFit:true, enableRowBody:false, showPreview:false}});
	return c;
};
AppChoiceUtilView2.prototype.store = function () {
	//var a = this.storex;
	var a = new Ext.data.Store({proxy:new Ext.data.HttpProxy({url:this.url+'&code='+this.code}), 
			reader:new Ext.data.JsonReader({
				root:"rows", totalProperty:"total", 
				fields:department})});
	a.load();
	return a;
};
