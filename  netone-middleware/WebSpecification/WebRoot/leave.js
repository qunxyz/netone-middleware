Ext.BLANK_IMAGE_URL = 'ext/images/default/s.gif' ;
Ext.Updater.defaults.disableCaching = true ;
Ext.Updater.defaults.loadScripts = true ;
Ext.namespace("wf");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'side';
Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
/** 请假申请表 */
wf.addWindow= function(dict, ds) {
	this.addPanel=new Ext.form.FormPanel({
			bodyStyle: 'background-color: rgb(204,220, 242); background: top center;margin: 10pt;',
			labelAlign:'right', bodyStyle:'padding:5px;border:0px', method:'POST', labelWidth:150,
			width:300,
			height:200,
			lauout:"column",
			items:[{
				xtype:"panel",
				layout:"form",
				hideBorders : true,
				items:[{
					xtype:'datefield',
              		fieldLabel:'请假开始日期', 
              		width:300,
              		name:'sdate' ,
              		emptyText:'请选择',
              		format:'Y-m-d'
						},{
					xtype:'timefield',
              		fieldLabel:'请假开始时间', 
              		width:300,
              		emptyText:'请选择',
              		name:'stime' ,
              		minValue:'8:00 AM',
              		maxValue:'17:30 PM',
              		increment:30
						},{
					xtype:'datefield',
              		fieldLabel:'请假结束时间', 
              		width:300,
              		name:'endtime' 
						},{
					xtype:'textfield',
              		fieldLabel:'请假天数', 
              		width:300,
              		name:'days'
						},{   
        			xtype:'combo', 
					allowBlank: false,
					blankText:'请假类别不能为空!',
					name:'type', 
					fieldLabel:'请假类别',
					width:300,
					displayField:'dictValue',
					lazyInit:false,
				    store:dict, 
				  	hiddenName:'type',
				    editable :false,
				    valueField :'dictValue',
				    triggerAction:'all'								
    					},{
					xtype:'textarea',
              		fieldLabel:'请假理由', 
              		width:300,
              		name:'reason'
						}
    					]
						}]				
    					});			
    					//提交
    					
    var s_form = this.addPanel.getForm();
	this.sub = function() {
		
			s_form.submit({
				waitMsg:'正在提交信息...',
				url:'workflow/leave.do?action=newLeave',
				method:'POST',
				success:function(form, action){
					var fn = function() {
						//addWindow.close();
						ds.reload();
					};
					//alert(action.result.obj_id);
					Ext.Msg.show({
		    			title: '成功',
		    			msg: action.result ? action.result.msg : '提交信息成功',
		    			icon: Ext.MessageBox.INFO,
		    			fn: fn,
		    			scope: this
		    		});
		    	},
		    	failure:function(form,action){
		    		var fn = function() {
		    			//addWindow.close();
		    		};
		    		Ext.Msg.show({
		    			title: '错误信息',
		    			msg: action.result ? action.result.msg : '请输入正确的信息',
		    			icon: Ext.MessageBox.ERROR,
		    			fn: fn,
		    			scope: this
		    		});
		    	}
		    });
		};
	//新增窗口
	var sub = this.sub;
	
	this.addWindow = new Ext.Window({
			title:"添加请假单",
			layout:'fit',
			closable :true,			
			modal:true,
			//closeAction:"hide",
			width:600,
			height:280,
			buttonAlign: 'center',
			/** 加载新增面板 */
			items:[this.addPanel],
			buttons: [
      			{text:'确定', handler: sub},
      			{text:'取消', handler:function(){addWindow.close();}}
    		]
		});
		var addWindow = this.addWindow;
		this.show = function() {this.addWindow.show();}
}
wf.listWindow = function() {
	 //设置请假类型数据字典数据源   
	 this.dictRecord= Ext.data.Record.create([
		{name:'dictValue'}
	]);
	var dictRecord=this.dictRecord;
	var dict=this.dict = new Ext.data.JsonStore({
		url: 'bm/dict.do',
	    totalProperty: 'totalCount',
	    root: 'data',
	    fields:this.dictRecord,
	    remoteSort: true,
	    baseParams: {"action":"queryLeaveType"}
	});	
	dict.load();	
	//设置审批角色数据字典数据源   
//	 this.checkrolerRecord= Ext.data.Record.create(
//		[
//		{name:'rolename'}, {name: 'roleid'}
//	]);
//	var checkrolerRecord=this.checkrolerRecord;
//	var checkrolerDs=this.checkrolerDs = new Ext.data.JsonStore({
//		url: 'workflow/leave.do',
//	    totalProperty: 'totalCount',
//	    root: 'data',
//	    fields:this.checkrolerRecord,
//	    remoteSort: true,
//	    baseParams: {"action":"getCheckRoler"}
//	});	
//	checkrolerDs.load();
//	var ds;
//新增请假
	this.add= function() {
		
		var win = new wf.addWindow(dict, ds);
		win.show();
		
	}
	
/**修改*/
	this.editflow=function(){
		//提示选择一条记录
	if(this.gridPanel.getSelectionModel().getSelected() == null) {
			Ext.Msg.show({
		    	title: '提示信息',
		    	msg: '请选择一项进行修改',
		    	icon: Ext.MessageBox.INFO,
		    	scope: this
		    });
			return;
		} ; // end if
		var flow_id = this.gridPanel.getSelectionModel().getSelected().data.flow_id;
		
		
		Ext.Ajax.request({
			url: 'workflow/leave.do',
			method: 'post',
			params: {'action': 'getEditAccess','flow_id':flow_id},
 			success: function(response, options){ 	
				var reobj = Ext.util.JSON.decode(response.responseText);
						//alert(reobj.edit_access);
						if(reobj.success) {
						if(reobj.edit_access) {
							//获取查看数据源
							var detail_form;
							Ext.Ajax.request({
							url: 'workflow/leave.do',
							method: 'post',
							params: {"action":"leaveDetail","flow_id":flow_id},
							success: function(response, options){
							var reobj2 = Ext.util.JSON.decode(response.responseText);	
								if(reobj.success) {
									detail_form = reobj2;						
							//alert(detail_form.data.starttime);
									var win = new wf.edit(flow_id,detail_form,dict);
									win.show();
									}
								else{									
									Ext.Msg.show({
			    					title: '获取错误',
			    					msg: reobj2.msg,
			    					icon: Ext.MessageBox.ERROR,
			    					scope: this
		    								})
								}
							},
		    				failure: function(response, options){
							Ext.Msg.show({
		    				title: '获取错误',
		    				msg: '获取错误',
		    				icon: Ext.MessageBox.ERROR,
		    				scope: this
		    					});
							}			
								});								
								}
								else{																		
									Ext.Msg.show({
			    					title: '获取错误',
			    					msg: '对不起，你无权使用当前操作！',
			    					icon: Ext.MessageBox.ERROR,
			    					scope: this
		    								})								
								};
					} else {
						Ext.Msg.show({
			    		title: '错误信息',
			    		msg: '对不起，你无权使用当前操作',
			    		icon: Ext.MessageBox.ERROR,
			    		scope: this
		    		});
					}				
  			},
			failure: function(response, options){
				Ext.Msg.show({
		    		title: '错误信息',
		    		msg: '网络中断，请联系管理员!',
		    		icon: Ext.MessageBox.ERROR,
		    		scope: this
		    	});
			}
		});
		
  	  	
	};
	/**查看	*/	
	
	this.detail=function(){
	
	//提示必须选择一条记录
	if(this.gridPanel.getSelectionModel().getSelected() == null) {
			Ext.Msg.show({
		    	title: '提示信息',
		    	msg: '请选择一项查看',
		    	icon: Ext.MessageBox.INFO,
		    	scope: this
		    });
			return;
		} ;
	//获取flow_id	
		var flow_id = this.gridPanel.getSelectionModel().getSelected().data.flow_id;
	//	alert(flow_id);
	//获取该请假的流程表单数据	
		var flowdetail_form=new Ext.data.JsonStore({
     	 url: 'workflow/leave.do',
    	  totalProperty: 'totalCount',
    	  root: 'data',
     	 fields: this.flowDetailRecord,
     	 remoteSort: true,
    	  baseParams: {"action":"flowDetail","flow_id":flow_id}
   	   //autoLoad: false
  	  });
  	  flowdetail_form.load({"params":{"start":0, "limit":30}});
  	  
	//定义审批流程的各属性变量
	var bbar2 = new Ext.PagingToolbar({
      pageSize: 40,
      store: flowdetail_form,
      displayInfo: true
    });
   	var cm2 = new Ext.grid.ColumnModel([
		{header:"审批角色",dataIndex:"username", type:'string'},
		{header:"审批时间",dataIndex:"time", type:'date', renderer: Ext.util.Format.dateRenderer('Y-m-d H:i')},
		{header:"结果",dataIndex:"result",type:'string'},
		{header:"备注",dataIndex:"remark",type:'string'}
					]);
	//审批流程列表panel 
		var flowDetailPanel=new Ext.grid.GridPanel({	
		loadMask: {msg:'请稍后'},
   		bodyStyle: 'background-color: rgb(204,220, 242); background: top center;',
		labelAlign:'right', bodyStyle:'padding:5px;border:0px', method:'POST', labelWidth:150,
		//height:300,
		//lauout:"column",
    	region: 'center',
    	bbar: bbar2,
    	store: flowdetail_form,
    	cm: cm2    		
		}); 	  

  	  //请求查看的信息查看表单数据 	 
  	 var detail_form;
		Ext.Ajax.request({
			url: 'workflow/leave.do',
			method: 'post',
			params: {"action":"leaveDetail","flow_id":flow_id},
			success: function(response, options){
				var reobj = Ext.util.JSON.decode(response.responseText);	
				if(reobj.success) {
					detail_form = reobj;
					//定义查看的panel
				var detailPanel = new Ext.form.FormPanel({
			bodyStyle: 'background-color: rgb(204,220, 242); background: top center',
			labelAlign:'right', bodyStyle:'padding:5px;border:0px', method:'POST', labelWidth:150,
			height:200,
			autoScroll:true,
			region:"north",
			lauout:"column",
			items:[{
				xtype:"panel",
				layout:"form",
				hideBorders : true,
				items:[{
					xtype:'textfield',
              		fieldLabel:'请假开始时间',
              		readOnly:true,
              		width:300,
              		name:'starttime' ,              		
              		value: detail_form.data.starttime,
              		format:'Y-m-d'
						},{
					xtype:'textfield',
              		fieldLabel:'请假结束时间', 
              		readOnly:true,
              		width:300,
              		name:'endtime' ,
              		value: detail_form.data.endtime,
              		format:'Y-m-d H:i'
						},{
					xtype:'textfield',
              		fieldLabel:'请假天数',
              		readOnly:true,
              		width:300,
              		name:'days',
              		value: detail_form.data.days
						},{   
        			xtype:'textfield', 
					allowBlank: false,
					readOnly:true,
					name:'type', 
					fieldLabel:'请假类别',
					value: detail_form.data.type,
					width:300,
					displayField:'dictValue',
					lazyInit:false,
				    store:dict, 
				  	hiddenName:'type',
				    editable :false,
				    valueField :'dictValue',
				    triggerAction:'all'								
    					},{
					xtype:'textarea',
              		fieldLabel:'请假理由', 
              		readOnly:true,
              		value: detail_form.data.reason,
              		width:300,
              		name:'reason'
						}
    					]
						}]				
    					});
		//关闭查看窗口
		var closeWin = function() {
			detailWindow.close();
		};
		//定义查看窗口
		detailWindow = new Ext.Window({
			title:"查看请假申请",
			layout:'border',
			closable :true,
			modal:true,
			width:600,
			height:500,
			buttonAlign: 'center',
			items:[detailPanel,flowDetailPanel],
			buttons: [
      			//{text:'确定并继续', handler: subc},
      			//{text:'确定', handler: sub},
      			{text:'关闭', handler: closeWin}
    		]
		});
		detailWindow.show();
					
				} else {
					Ext.Msg.show({
			    		title: '获取',
			    		msg: reobj.msg,
			    		icon: Ext.MessageBox.ERROR,
			    		scope: this
		    		});
				}
			},
			failure: function(response, options){
				Ext.Msg.show({
		    		title: '获取错误',
		    		msg: '获取错误',
		    		icon: Ext.MessageBox.ERROR,
		    		scope: this
		    	});
			}
		});
	};
	/**关闭*/
	this.delflow=function(){	
		if(this.gridPanel.getSelectionModel().getSelected() == null) {
			Ext.Msg.show({
		    	title: '提示信息',
		    	msg: '请选择一项进行关闭',
		    	icon: Ext.MessageBox.INFO,
		    	scope: this
		    });
			return;
		} // end if
		var flow_id = this.gridPanel.getSelectionModel().getSelected().data.flow_id;
		Ext.Ajax.request({
			url: 'workflow/leave.do',
			method: 'post',
			params: {'action': 'getEditAccess', 'flow_id':flow_id},
			success: function(response, options){
				var reobj = Ext.util.JSON.decode(response.responseText);
				//alert(reobj.edit_access);
				if(reobj.edit_access) {
					var win = new wf.closeWindow(flow_id, ds);
					win.show();
				} else {
					Ext.Msg.show({
			    		title: '错误信息',
			    		msg: '对不起，你没有权限进行关闭！',
			    		icon: Ext.MessageBox.ERROR,
			    		scope: this
		    		});
				}
			},
			failure: function(response, options){
				Ext.Msg.show({
		    		title: '错误信息',
		    		msg: '网络中断，请联系管理员!',
		    		icon: Ext.MessageBox.ERROR,
		    		scope: this
		    	});
			}
		});		
	};
	/**历史流程*/
	this.flowhistory=function(){
		
	var listpanel = new wf.flowHistory(dict);
	var historyWindow = new Ext.Window({		
			title:"修改请假申请",
			layout:'fit',
			closable :true,
			modal:true,
			width:400,
			height:300,
			buttonAlign: 'center',
			items:[listpanel]			
	});
	historyWindow.show();		
	};
	/** 审批按钮 */
	this.checkflow = function() {
		var add_access = false;
		if(this.gridPanel.getSelectionModel().getSelected() == null) {
			Ext.Msg.show({
		    	title: '提示信息',
		    	msg: '请选择一项进行审批',
		    	icon: Ext.MessageBox.INFO,
		    	scope: this
		    });
			return;
		} // end if
		var flow_id = this.gridPanel.getSelectionModel().getSelected().data.flow_id;
		var step_id = this.gridPanel.getSelectionModel().getSelected().data.stepid;
		var id = this.gridPanel.getSelectionModel().getSelected().data.id;
		
//获取审批权限
		Ext.Ajax.request({
			url: 'workflow/leave.do',
			method: 'post',
			params: {'action': 'getCheckAccess', 'flow_id':flow_id, 'step_id':step_id, 'id':id},

			success: function(response, options){
				var reobj = Ext.util.JSON.decode(response.responseText);
				
				if(reobj.success) {
				if(reobj.check_access) {
					var win = new wf.checkWindow(flow_id,step_id,id);
					win.show();
				} else {
					Ext.Msg.show({
			    		title: '错误信息',
			    		msg: '对不起，你没有权限进行审批！',
			    		icon: Ext.MessageBox.ERROR,
			    		scope: this
		    		});
				}
								} else {
					Ext.Msg.show({
			    		title: '错误信息',
			    		msg: reobj.msg,
			    		icon: Ext.MessageBox.ERROR,
			    		scope: this
		    		});
				}
			},
			failure: function(response, options){
				Ext.Msg.show({
		    		title: '错误信息',
		    		msg: '网络中断，请联系管理员!',
		    		icon: Ext.MessageBox.ERROR,
		    		scope: this
		    	});
			}
		});
	};
	/**打印
	this.print = function() {		
		if(this.gridPanel.getSelectionModel().getSelected() == null) {
			Ext.Msg.show({
		    	title: '提示信息',
		    	msg: '请选择一项进行打印',
		    	icon: Ext.MessageBox.INFO,
		    	scope: this
		    });
			return;
		} // end if
		var flow_id = this.gridPanel.getSelectionModel().getSelected().data.flow_id;
		window.open("../../dev/fw/dev_use/print.jsp?flow_id=" + flow_id);		
	};	
	this.history = function() {
		var win = new dev.fw.dev_use_flow.historyWindow();
		win.show();
	};	*/
	/*工具栏*/
	this.tbar = [     
      {text:'新增', iconCls:'addIcon', handler:this.add, scope:this},'-',
      {text:'查看', iconCls:'detailIcon', handler:this.detail, scope:this},'-',
      {text:'修改', iconCls:'editIcon', handler:this.editflow, scope:this},'-',
      {text:'关闭', iconCls:'deleteIcon', handler:this.delflow, scope:this}, '-',
      {text:'审批', iconCls:'iconAccept', handler:this.checkflow, scope:this},'-',
      {text:'打印', iconCls:'printIcon', handler:this.print, scope:this},'-',
      {text:'历史流程', iconCls:'printIcon', handler:this.flowhistory, scope:this}
          ];
          /** 新增列表字段 */
	this.leaveRecord = Ext.data.Record.create([
	{name:'starttime', type:'date', dateFormat:'Y-m-d H:i:s.u'}, 
	  {name: 'endtime',  type:'date', dateFormat:'Y-m-d H:i:s.u'},
	  {name: 'username', type: 'string'}, 
	  {name: 'leavetime', type:'date', dateFormat:'Y-m-d H:i:s.u'},
	  {name: 'rolename', type:'string'},
	  	{name: 'status', type:'string'},
	  	{name: 'flow_id', type:'int'},
	  	{name: 'stepid', type:'int'},
	  	{name: 'id', type:'int'}
	  ]);
	  
	      /** 查看字段 */
	this.detailRecord = Ext.data.Record.create([
	{name:'data'}
	  ]);
	  	    /** 查看审批列表字段 */
	this.flowDetailRecord = Ext.data.Record.create([
	  {name:'username', type:'string'}, 
	  {name: 'time',  type:'date', dateFormat:'Y-m-d H:i'},
	  {name: 'result', type: 'string'}, 
	  {name: 'remark', type:'string'}
	  		  ]);
	  /** 列表字段数据源设置 */
	this.ds = new Ext.data.JsonStore({
      url: 'workflow/leave.do',
      totalProperty: 'totalCount',
      root: 'data',
      fields: this.leaveRecord,
      remoteSort: true,
      baseParams: {"action":"queryCurrentFlow"}
      //autoLoad: false
    });
    ds = this.ds;

	this.bbar = new Ext.PagingToolbar({
      pageSize: 40,
      store: this.ds,
      displayInfo: true
    });
	this.sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	this.cm = new Ext.grid.ColumnModel([
		this.sm,
		{header:"请假人员",dataIndex:"username", type:'string'},
		{header:"申请时间",dataIndex:"leavetime", renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
		{header:"当前审批人",dataIndex:"rolename",type:'string'},
		{header:"开始时间",dataIndex:"starttime", renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
		{header:"结束时间",dataIndex:"endtime", renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')},
		{header:"状态",dataIndex:"status",type:'string'}
			]);
	//列表		
	this.gridPanel = new Ext.grid.GridPanel({
		region: 'center',
		loadMask: {msg:'请稍后'},
    	region: 'center',
    	bbar: this.bbar,
    	store: this.ds,
    	
    	cm: this.cm,
    	sm: this.sm,
    	tbar: this.tbar,
    	height: 100
	});
	this.ds.load({"params":{"start":0, "limit":40}});		
	return this.gridPanel;
};	
/** 列表页面激活 */
activePanel = function(){
	var panel = new Ext.Panel({
		region:'center',
		layout:'fit',
		items:[wf.listWindow()]
	});
	return panel;
}
Ext.onReady(
			function(){
			  new Ext.Viewport(
			  {
			  	layout:'border',
			  	items:[activePanel()]
			  }
			  )
});

