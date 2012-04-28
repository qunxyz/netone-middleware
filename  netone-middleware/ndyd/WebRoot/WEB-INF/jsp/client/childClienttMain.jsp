<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>分销商基本信息管理</title>
	</head>
	<body>
		<div id='container' style="width: 100%; height: 100%;">
			<div id="queryDiv"
				style="display: none; margin-top: 5px; margin-left: 5px;">
				<form id="queryForm" action="">
					<table class="mian">
						<tr>
							<td class="label">
								用户名称:
							</td>
							<td>
								<input type="text" name="clientLoginName" id="clientLoginName"
									style="width: 150px" value="" />
								<input type="text" name="qh" id="qh" style="width: 150px"
									value="" />

							</td>
							<td class="label">
								营销管理公司:
							</td>
							<td>
								<input type="text" name="sellDepartment" id="sellDepartment"
									style="width: 150px" value="" />
							</td>
							<td class="label">
								联系人:
							</td>
							<td>
								<input type="text" name="servicestation" style="width: 150px"
									id="servicestation" value="" />
							</td>
						</tr>
						<tr>
							<td class="label">
								销售部门:
							</td>
							<td>
								<input type="text" name="belongTo" style="width: 150px"
									id="belongTo" value="" />
							</td>
							<td class="label">
								公司地址:
							</td>
							<td>

							</td>
							<td class="label">
								送达站
							</td>
							<td>

							</td>
							<td></td>
							<td rowspan="2">
								<input id="queryBtn" type="button" value=" 查 询 " class="btn"
									onmouseover="this.className='btn_mouseover'"
									onmouseout="this.className='btn'"
									onmousedown="this.className='btn_mousedown'"
									onmouseup="this.className='btn'" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<!-- 数据字典表格 -->
			<div id="listDiv">
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
  Ext.ns('Client.Data');
  Client.Data.ClientGrid  =  Ext.extend(Ext.grid.GridPanel,{
		 initComponent: function() {
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	});
		    
			var config = {
			    sm : cb,
			    frame:true,
			    border:false,
				store: new Ext.data.Store({
					url: "<c:url value='/client/client.do?method=onList' />"+'&flag=child', //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [ 
						{name: 'clientId'},
						{name: 'clientName'},
						{name: 'telphone'},
						{name: 'mobile'},
						{name: 'email'},
						{name: 'yxManagerCompany'},
						{naem: 'sellDepartment'},
						{name: 'operationDirector'},
						{name: 'linkman'},
						{name: 'companyAddress'},
						{name: 'servicestation'},
						{name: 'deliverGoodsAddress'},
						{name: 'remitAccounts'},
						{name: 'openAccountTitle'},
						{name: 'ratepayingType'},
						{name: 'lyBailMonery'},
						{name: 'remitPersion'},
						{name: 'remitTime'},
						{name: 'clientLoginName'}
					   ])
				}),
				columns:[
				        index,
						cb,
						{header: "客户编号", width: 120, dataIndex: 'clientLoginName', sortable: true},
						{header: "用户编号", width: 90, dataIndex: 'clientId', sortable: true,hidden:true},
						{header: "客户名称", width: 100, dataIndex: 'clientName', sortable: true},
						{header: "联系电话", width: 100, dataIndex: 'telphone', sortable: true},
						{header: "手机号码", width: 100, dataIndex: 'mobile', sortable: true},
						{header: "电子邮箱", width: 100, dataIndex: 'email', sortable: true},
						{header: "营销管理公司", width: 80, dataIndex: 'yxManagerCompany', sortable: true},
						{header: "销售部", width: 80, dataIndex: 'sellDepartment', sortable: true,hidden: true},
						{header: "业务主任", width: 80, dataIndex: 'operationDirector', sortable: true},
						{header: "联系人", width: 80, dataIndex: 'linkman', sortable: true,hidden: true},
						{header: "公司地址", width: 100, dataIndex: 'companyAddress', sortable: true},
						{header: "送达站", width: 100, dataIndex: 'servicestation', sortable: true},
						{header: "送货地址", width: 100, dataIndex: 'deliverGoodsAddress', sortable: true},
						{header: "汇款账号", width: 100, dataIndex: 'remitAccounts', sortable: true},
						{header: "开户行", width: 100, dataIndex: 'openAccountTitle', sortable: true},
						{header: "纳税类型", width: 100, dataIndex: 'ratepayingType', sortable: true},
						{header: "履约保证金金额", width: 120, dataIndex: 'lyBailMonery',type: 'double',sortable: true},
						{header: "汇款人", width: 100, dataIndex: 'remitPersion', sortable: true},
						{header: "汇款时间", width: 120, dataIndex: 'remitTime', sortable: true}
						],
				viewConfig:{forceFit:true},
				loadMask:true	
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
	 
		  this.bbar = new Ext.PagingToolbar({
			 store:this.store,
		     displayInfo:true,
		     pageSize:15,
		     displayMsg : '第 {0} 条到 {1} 条，一共 {2} 条',
			 emptyMsg : "没有记录"
		  });
	 
		  // call parent
		  Client.Data.ClientGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var condition = {
				        //取得HTML页面的查询条件
						clientLoginName: Ext.get('clientLoginName').getValue(),
						qh:Ext.get('qh').getValue()
						//sellDepartment: Ext.get('sellDepartment').getValue(),
						//servicestation: Ext.get('servicestation').getValue(),
						//belongTo: Ext.get('belongTo').getValue()
				  }; 
				  
				  options=options||{}; 
				  options.params = options.params||{}; 
				  options.params.condition = Ext.util.JSON.encode(condition);
				  
				  return true;
		  });
		  
		   // load the store at the latest possible moment
		  this.on({
			 beforeshow : {scope:this, single:true, fn:function() {
				   this.store.load({params:{start:0, limit:15}});
			 }}
		  });
	  }
  });
  Ext.reg('ClientGrid',  Client.Data.ClientGrid);//注册一个组件,注册成xtype以便能够延迟加载
  
  Ext.ns('Client.Layout');
  
  Client.Layout.Viewport =  Ext.extend(Ext.Viewport, {
  	 
       initComponent: function(){
       	   var clientHeight = 0;
		   if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
			    clientHeight = document.documentElement.clientHeight;
		   } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
			    clientHeight = document.body.clientHeight;
	       }
       
	       var config = {
				 collapsible:true,
				 renderTo :'container',
				 autoWidth:true,
				 border:false,
				 items:[{
					 xtype:'toolbar',
					 items:[
					  new Ext.Toolbar.Button({
						  text:'新增',
						  id:'ext_b_add',
						  iconCls:"addIcon"
					  }),	
					  new Ext.Toolbar.Button({
						   text:'修改',
						   id:'ext_b_change',
						   iconCls:"editIcon"
					  }),
					  new Ext.Toolbar.Button({
						  text:'删除',
						  id: 'ext_b_delete',
						  iconCls: "deleteIcon"
					  }),
					  new Ext.Toolbar.Button({
						  text:'查询',
						  id: 'ext_b_Query',
						  iconCls: "viewIcon"
					  }),new Ext.Toolbar.Button({
						   text:'查看客户指标',
						   id:'ext_b_Look',
						   iconCls:"viewIcon"
					  })
                     ]
                	},{
					    id:"ClientGrid",
					    xtype:"ClientGrid",
			            border:false,
			            hideBorders:true,
			            height : clientHeight-20,
					 	autoScroll:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			Client.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
   Ext.onReady(function(){//页面加载时候触发事件
	var viewport =  new Client.Layout.Viewport();
	var ClientGrid2 = viewport.findById('ClientGrid');//Grid

	refresh = function(){//刷新
		var start = 0 ;//当前分页记录开始位置
		var ClientGrid3  = viewport.findById('ClientGrid');//Grid
		if (ClientGrid3.store.lastOptions!=null){
			start = ClientGrid3.store.lastOptions.params.start;
		} else {
			start = 0;
		}
    	ClientGrid3.store.load({params:{start:start, limit:15}});//刷新当前页面
	};
	
	
	//--------------------------初始化加载--------------------------
	refresh();//初始化加载数据
	//Ext.get('queryDiv').dom.style.display='block';//显示自定义页面数据
	
	
	//------------------------------------初始化按钮事件开始--------------------------------
	Ext.EventManager.addListener(Ext.get('ext_b_add'), 'click', function(){//新增
		openWinCenter('新增','<c:url value='/client/client.do?method=onEditChildView' />', 600,400,true);
	});
	
	Ext.EventManager.addListener(Ext.get('ext_b_Look'), 'click', function(){//查看客户销售指标
			 var selectionSet = ClientGrid2.getSelectionModel().getSelections(); 
			 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
				Ext.MessageBox.alert('系统提示','请选择一条记录进行修改!');
				return ;
			 }	
			
             var guid = selectionSet[0].data.clientLoginName;
             var url = '<c:url value='/client/sellTargetNew.do?method=onMainView' />' +  '&clientLoginName=' + guid;
             openWinCenter('修改',url, 1000,800,true);
	});
	
	Ext.EventManager.addListener(Ext.get('ext_b_change'), 'click', function(){//修改记录
			 var selectionSet = ClientGrid2.getSelectionModel().getSelections(); 
			 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
				Ext.MessageBox.alert('系统提示','请选择一条记录进行修改!');
				return ;
			 }	
			
             var guid = selectionSet[0].data.clientId;
             var url = '<c:url value="/client/client.do?method=onEditChildView"/>' +  '&clientId=' + guid;
             openWinCenter('修改',url, 600,400,true);
	});

	
	Ext.EventManager.addListener(Ext.get('ext_b_delete'), 'click', function(){//删除数据
	      var clientIdStr = getClientId();
	      var clientNameStr=getClientLoginName();
	      var num = clientIdStr.length;
	      var numName=clientNameStr.length;
	      if(num >= 1){
	      	  Ext.MessageBox.confirm("系统提示",'您确定要删除所分销商吗？',function(btnId){
					if(btnId == 'yes'){
					var str = "";
					var strName="";
				    for(var i= 0 ; i < num ; i ++){
				        str = str + clientIdStr[i]+",";
				    }
				    for(var j=0;j<numName;j++){
				    strName=strName+clientNameStr[j]+","
				    }
				    var str = Ext.util.Format.substr(str,0,str.length - 1);
				    var str1 = Ext.util.Format.substr(strName,0,strName.length - 1);
						Ext.Ajax.request({//AJAX删除
								url : '<c:url value='/client/client.do?method=onDelete' />',
								params : {clientIdStr : str,clientLoginName:str1},
								method : 'POST',
								success : function(response,options){
									var result = Ext.util.JSON.decode(response.responseText);
									if(result.success){
										Ext.Msg.alert('系统提示','删除分销商信息成功');
										refresh();
								    }else{
										Ext.Msg.alert('系统提示','分销商信息被关联不能删除！');
									
								    }
								},
								failure : function(response,options){
									checkAjaxStatus(response);
									Ext.Msg.alert('系统提示','删除经分销商数据信息失败');
								}
						});
					}
			 });
		 }
    });
	
    Ext.EventManager.addListener(Ext.get('queryBtn'), 'click', function(){//查询事件      
	    refresh();
    });
    
   	Ext.EventManager.addListener(Ext.get('ext_b_Query'), 'click', function(){//新增
		openWinCenter('新增','<c:url value='/client/client.do?method=onQueryMainView' />', 600,400,true);
	});
    
    //----------公共方法定义(按钮,私有事件)-----------------
	function getClientId(){//取得所选数Id
			var recs = ClientGrid2.getSelectionModel().getSelections();
			var list = [];
			if(recs.length == 0){
				Ext.Msg.alert('系统提示','请选择要进行操作的经销商信息！');
			}else{
				for(var i = 0 ; i < recs.length ; i++){
					var rec = recs[i];					
					list.push(rec.get('clientId'));
				}
			}
			return list;
	}
	
	function getClientLoginName(){//取得所选数Id
			var recs = ClientGrid2.getSelectionModel().getSelections();
			var list = [];
			if(recs.length == 0){
				Ext.Msg.alert('系统提示','请选择要进行操作的客户信息！');
			}else{
				for(var i = 0 ; i < recs.length ; i++){
					var rec = recs[i];					
					list.push(rec.get('clientLoginName'));
				}
			}
			return list;
	}
	
  });
  

	

</script>
