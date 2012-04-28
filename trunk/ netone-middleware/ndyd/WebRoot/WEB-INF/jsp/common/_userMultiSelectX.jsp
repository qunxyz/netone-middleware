<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.jl.service.BaseService"%>
<%@page import="com.jl.entity.User"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="metaExt.jsp"></jsp:include>
		<jsp:include page="metaJQuery-min.jsp"></jsp:include>
		<script language="javascript" type="text/javascript" src="<%=path%>/script/jquery-plugin/easyui/easyloader.js" ></script>
		<title>用户选择</title>
	</head>
	<body>
		<input type="hidden" id="departmentId" name="departmentId" value="${node}" />
		
		<input type="hidden" id="ids" name="ids" />
		<input type="hidden" id="values" name="values" />
		<input type="hidden" id="texts" name="texts" />
		
		<div id="limitdiv"  >
			<span style="font-size: 12px;font-weight: normal;">办理期限:  </span>  <select id="limittime" name="limittime">
				<option value="24">紧急 24小时</option>
				<option value="72"  selected="selected">一般 72小时</option>
			</select>
		</div>
		<c:if test="${param.activityid=='trackActionSpecialType1'}">
		<div id="selectSpecial" style="color: red;"  >
			多人审批是否需要同步:<select id="issync" onchange="changeTodo();">
				<option value="0" selected="selected">否</option>
				<option value="1">是</option>
			</select>
		</div>
		</c:if>
		
	</body>
</html>
<script type="text/javascript">
$(function(){
		easyloader.base = '<%=path%>/script/jquery-plugin/easyui/';    // set the easyui base directory
		easyloader.load('combobox', function(){// load the specified module
		    $("#limittime").combobox({multiple:false,
		   	 	onSelect:function(record){
					var val = $('#limittime').combobox('getValue');
					$('#limittime').val(val);
				},
				onChange:function(record){
					changeTodoLimittime();
				}
		    });
		});		
});

function changeTodo(){
	if (parent){
		if (parent.document.getElementById('issync')){
			parent.document.getElementById('issync').value=document.getElementById('issync').value;
		}
	}
}

function changeTodoLimittime(){
	var v = $('#limittime').combobox('getValue');
	var reg = /^[0-9]{0,10}$/;
	if (v==0){
		alert('输入的值不能为0!');
		$('#limittime').combobox('setValue','');
		return;
	}
	if(!reg.test(v)){
		alert('输入的值不是整数!');
		$('#limittime').combobox('setValue','');
		return;
	}
	
	var pdata_usercode = parent.document.getElementById('usercode');
	if (pdata_usercode.value=='') {
		//not do
	} else {
		var pdata_activityid = parent.document.getElementById('activityid');  
		var pdata_limittime = parent.document.getElementById('limittime'); 
		var pdata_activityid_arr=pdata_activityid.value.split(',');
		var pdata_limittime_arr=pdata_limittime.value.split(',');
		var strs = '';
		for (var i=0;i<pdata_activityid_arr.length;i++){
			var tmp_activityid = pdata_activityid_arr[i];
			var isexist = false;
			if (tmp_activityid==param_activityid){
				var v =  $('#limittime').combobox('getValue');
				if (v=='') v=0;
				strs += v+',';
			} else {
				strs += pdata_limittime_arr[i]+',';
			}
		}
		strs = strs.substring(0,strs.length - 1);
		pdata_limittime.value=strs;
	}
}

var nodeid='${node0}';
var nodename='${nodeName}';
nodename = nodename==''?'组织机构':nodename;
var nodecode='${nodeCode}';
var parentnodeid='${parentNode}';
 Ext.QuickTips.init();

 Ext.ns('Buss.Data');
  Buss.Data.BussGrid  =  Ext.extend(Ext.grid.GridPanel,{
		 initComponent: function() {
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
		    	
				singleSelect: <c:choose><c:when test="${param.singleselect==1}">true</c:when><c:otherwise>false</c:otherwise></c:choose>
		   	});
			var config = {
			    sm : cb,
			    frame:true,
			    border:false,
				store : new Ext.data.Store({
							url : "<c:url value='/department/user.do?method=onListUserX' />", // JSON数据
							reader : new Ext.data.JsonReader({
										totalProperty : 'total',
										root : 'rows'
									}, [
										{name: 'userId'},
										{name: 'userCode'},
										{name: 'userName'},
										{name: 'types'}
									])
						}),
				columns : [index,
					cb, 
					{header: "名称", align:'center',dataIndex: 'userName',sortable : true,
						renderer : function (value, metadata, record, rowIndex, columnIndex, store) {   
						    //build the qtip:   
						    var title = '&nbsp;' + record.get('userCode') +' ' + record.get('userName');   
						    
						    var tip = '类型:人员';
						    metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';   
						    
						    //return the display text:   
						    var displayText = value;   
						    return displayText;
						}
					}
				],
				viewConfig:{forceFit:true},
				loadMask:true	
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
		  this.bbar = new Ext.PagingToolbar({
			 store:this.store,
		     displayInfo:false,
		     pageSize:15,
		     displayMsg : '第 {0} 条到 {1} 条，一共 {2} 条',
			 emptyMsg : "没有记录"
		  });
		  // call parent
		  Buss.Data.BussGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
	      		  var userCode = '';
	      		  if (Ext.get('userCode')) userCode = Ext.get('userCode').getValue();
	      		  var list = '';
	      		  if (userCode!=''){
	      		  	list = 'all';
	      		  }	      
				  var condition = {
				   departmentId: Ext.get('departmentId').getValue(),
				   userCode:userCode,
				   list:list
				  }; 
				  options=options||{}; 
				  options.params = options.params||{}; 
				  options.params.condition = Ext.util.JSON.encode(condition);
				  options.params.includedept = '${param.includedept}';
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
  Ext.reg('BussGrid',  Buss.Data.BussGrid);//注册一个组件,注册成xtype以便能够延迟加载


  Ext.ns('Buss.Data2');
  Buss.Data2.BussGrid  =  Ext.extend(Ext.grid.GridPanel,{
		 initComponent: function() {
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: <c:choose><c:when test="${param.singleselect==1}">true</c:when><c:otherwise>false</c:otherwise></c:choose>
		   	});
			var config = {
			    sm : cb,
			    frame:true,
			    border:false,
				store : new Ext.data.Store({
							reader : new Ext.data.JsonReader({
										totalProperty : 'total',
										root : 'rows'
									}, [
										{name: 'userId'},
										{name: 'userCode'},
										{name: 'userName'}
									])
						}),
				columns : [index,
					cb, 
					{header: "名称", align:'center',dataIndex: 'userName',sortable : true}
				],
				viewConfig:{forceFit:true},
				loadMask:true	
		   }
		   
		   // apply config
		  Ext.apply(this, Ext.apply(this.initialConfig, config));
		  // call parent
		  Buss.Data2.BussGrid.superclass.initComponent.apply(this, arguments);
		  
	  }
  });
  Ext.reg('BussGrid2',  Buss.Data2.BussGrid);//注册一个组件,注册成xtype以便能够延迟加载

Ext.ns('Buss.Layout');

Buss.Layout.Viewport =  Ext.extend(Ext.Viewport, {

   initComponent: function(){
     var clientHeight = 0;
	 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
	    clientHeight = document.documentElement.clientHeight;
	 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
	    clientHeight = document.body.clientHeight;
   }
   
   var treeDataUrl = "<c:url value='/department/department.do?method=onFindDeptTreeX' />";
     
   var config = {
		 collapsible:true,
		 autoWidth:true,
		 border:false,
		 layout:"border", 
		 items:[
		 <c:if test="${hiddendept!=1}">
		 		{
		 			id:'treepanel',
		 			xtype:'treepanel',
		 			region:"west",
		 			width:180,
		 			style:"padding:2px",
		 			title:nodename,
		 			iconCls:"flow_chartIcon",
		 			//rootVisible:false,     //隐藏根节点
		 			split:true,
		 			collapsible: true,//伸缩
		 			collapsed :true,
		 			animCollapse :false,
		 			animate :false,//去除动画
　　　				autoScroll:true,
					border : true, // 边框
					useArrows :true,
				    loader: new Ext.tree.TreeLoader({dataUrl: treeDataUrl}),
		       	 	root : new Ext.tree.AsyncTreeNode({id:nodeid,text:nodename}),
		       	 	listeners : {
						click : function(node,e){
						   if(node.attributes.id != nodeid){
						   	   Ext.get('departmentId').dom.value = node.attributes.departmentId;
						       refresh();
						   } else {
						   	   Ext.get('departmentId').dom.value = nodeid;
						   	   refresh();
						   }
						}
					}
              	},
         </c:if>     	
              	{
              		id:'_grid',
		            region:"center",
		            xtype:'panel',
					border:false,
					hideBorders:true,
	            	autoScroll:true,
	            	buttonAlign :'center',
	            	layout:'column',
					 tbar:new Ext.Toolbar([
					 	{
							xtype:'panel',
							baseCls : 'x-plain',
							contentEl:'limitdiv'
						}
						<c:if test="${param.activityid=='trackActionSpecialType1'}">
						,{
							xtype:'panel',
							baseCls : 'x-plain',
							contentEl:'selectSpecial'
						}
						</c:if>
					 ]),
	            	/***
	            	bbar:new Ext.Toolbar([
						{text : '确认',iconCls:'viewIcon',id:'ext_b_search',handler:function(){
							var l = "";
						     var j = "";
						     var k = "";
						     var f = Ext.getCmp("BussGrid2").getStore();
						     var len =  f.getCount();
						     if (len<=0) {return;}
						      for ( var g = 0; g < len; g++) {
						       if (g > 0) {
						        l += ",";
						        j += ",";
						        k += ",";
						       }
						       l += f.getAt(g).data.userId;
						       j += f.getAt(g).data.userCode;
						       k += f.getAt(g).data.userName;
						      }
						     _select(l,j,k);
						}},
						{text : '关闭',iconCls:'closeIcon',id:'ext_b_search',handler:function(){
							window.close();
						}}
					]),
					***/
				    items: [{
				    	id:'BussGrid',
				        xtype:'BussGrid',
				        height:clientHeight-30,
				        <c:if test="${param.needsearch==true}">
				        tbar:new Ext.Toolbar([
							{
								xtype:'panel',
								baseCls : 'x-plain',
								html:'<input id=\'userCode\' type=\'text\' onkeyup=\'onKeyUpEvent(event);\'>'
							},
							'-',
							{text : '查询',iconCls:'viewIcon',id:'ext_b_search',handler:refresh}
						]),
						</c:if>
				        columnWidth: .5
				    },{
				        columnWidth: .5,
				        xtype:'panel',
				        layout:'column',
				        buttonAlign :'center',
				        items:[{
				        		region:"west",
				        		xtype : "panel",
				        		border:false,
				        		frame:false,
				        		width:58,
				        		items : [{xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},
				        		     {xtype : 'panel',border:false,frame:false},{
					            	 xtype : "button",
								     //iconCls : "add-allIcon",
								     buttonAlign :'center',
								     minWidth :58,
								     text :'选择',
								     handler : add_all
				            	},
				            	 {xtype : 'panel',border:false,frame:false},
				            	{
				            		 xtype : "button",
					            	 //iconCls : "rem-allIcon",
								     buttonAlign :'center',
								     minWidth :58,
								     text :'取消',
								     handler : rem_all
				            	}]
				        	},{
				        		region:"center",
				        		columnWidth: 1,
				        		id:'BussGrid2',
				       			xtype:'BussGrid2',
				       			height:clientHeight-30
				    		}
				        ]
				    }]
			  }
		 	]
	}
	         
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	Buss.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});

function refreshTree(){//刷新树
	Ext.getCmp('treepanel').root.reload();
	//Ext.getCmp('treepanel').expandAll();//树默认全部展开
	Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
}

function add_all(){//添加
	var t = Ext.getCmp("BussGrid");
	var n = Ext.getCmp("BussGrid2");
	var u = n.getStore();
	var x = t.getSelectionModel().getSelections();
	for ( var p = 0; p < x.length; p++) {
	 var q = x[p].data.userId;
	 var v = x[p].data.userCode;
	 var k = x[p].data.userName;
	 var s = false;
	 for ( var o = 0; o < u.getCount(); o++) {
	  if (u.getAt(o).data.userCode == v) {
	   s = true;
	   break;
	  }
	 }
	 if (!s) {
	  var w = {
	   userId : q,
	   userCode : v,
	   userName : k
	  };
	  <c:if test="${param.singleselect==1}">
	   u.removeAll();
	   rem_all_parent_single(q,v,k,true);
	  </c:if>
	  
	  var r = new u.recordType(w);
	  n.stopEditing();
	  u.add(r);
	  add_all_parent(q,v,k);
	 }
	}
}

function rem_all(){//移除
 var p = Ext.getCmp("BussGrid2");
 var q = p.getSelectionModel().getSelections();
 var n = p.getStore();
 for ( var o = 0; o < q.length; o++) {
 	var l = q[o].data.userId;
	var j = q[o].data.userCode;
	var k = q[o].data.userName;
    p.stopEditing();
    n.remove(q[o]);
	
	rem_all_parent(l,j,k);
  
 }
}

var param_activityid = '${param.activityid}';

function add_all_parent(userid,usercode,username){
	if (parent){
		var pdata_userid = parent.document.getElementById('userid');
		var pdata_usercode = parent.document.getElementById('usercode');
		var pdata_username = parent.document.getElementById('username');
		var pdata_activityid = parent.document.getElementById('activityid');
		var pdata_limittime = parent.document.getElementById('limittime');
		var tmp_userid = pdata_userid.value;
		var tmp_usercode = pdata_usercode.value;
		var tmp_username = pdata_username.value;
		var tmp_activityid = '';
		var tmp_limittime = '';;
		if (pdata_activityid) tmp_activityid = pdata_activityid.value;
		if (pdata_limittime) tmp_limittime = pdata_limittime.value;
		
		//判断是否存在
		var isexist = false;
		var p_activityidStrs = tmp_activityid.split(',');
		var p_usercodeStrs = tmp_usercode.split(',');
		for (var i=0;i<p_activityidStrs.length;i++){
			var tmp_activityid_str = p_activityidStrs[i];
			var tmp_usercode_str = p_usercodeStrs[i];
			if (tmp_activityid_str==param_activityid && tmp_usercode_str==usercode){
				isexist = true;
			}
		}
		
		if (isexist==false){
			if (tmp_userid!='') tmp_userid+=',';
			pdata_userid.value=tmp_userid+userid;
			if (tmp_usercode!='') tmp_usercode+=',';
			pdata_usercode.value=tmp_usercode+usercode;
			if (tmp_username!='') tmp_username+=',';
			pdata_username.value=tmp_username+username;	
			
			if (pdata_activityid){
			if (tmp_activityid!='') tmp_activityid+=',';
			pdata_activityid.value=tmp_activityid+param_activityid;
			}
			
			if (pdata_limittime){
			if (tmp_limittime!='') tmp_limittime+=',';
			pdata_limittime.value=tmp_limittime+$('#limittime').combobox('getValue');
			}
		}
	}
}

function rem_all_parent(userid,usercode,username){
	rem_all_parent_single(userid,usercode,username,false);
}

function add_all_parent_single(userid,usercode,username){
	add_all_parent(userid,usercode,username);
}

function rem_all_parent_single(userid,usercode,username,clear){
	if (parent){
			if (parent.document.getElementById('activityid')){
				var p_activityid = parent.document.getElementById('activityid').value;
				var p_limittime = parent.document.getElementById('limittime').value;
				var p_userid = parent.document.getElementById('userid').value;
				var p_usercode = parent.document.getElementById('usercode').value;
				var p_username = parent.document.getElementById('username').value;
				
				var p_useridStrs = p_userid.split(',');
				var p_usercodeStrs = p_usercode.split(',');
				var p_usernameStrs = p_username.split(',');
				var p_limittimeStrs = p_limittime.split(',');
				
				var useridstrs = '';
				var usercodestrs = '';
				var usernamestrs = '';
				var activityidstrs = '';
				var limittimestrs = '';
				
				var p_activityidStrs = p_activityid.split(',');
				for (var i=0;i<p_activityidStrs.length;i++){
					var tmp_activityid = p_activityidStrs[i];
					var tmp_usercode = p_usercodeStrs[i];
					if (tmp_activityid==param_activityid && tmp_usercode==usercode){
						//remove
					} else {
						if (clear==true && tmp_activityid==param_activityid){
						
						} else {
						useridstrs+=p_useridStrs[i]+',';
						usercodestrs+=p_usercodeStrs[i]+',';
						usernamestrs+=p_usernameStrs[i]+',';
						activityidstrs+=p_activityidStrs[i]+',';
						limittimestrs+=p_limittimeStrs[i]+',';
						}
					}
				}
				useridstrs = useridstrs.substring(0,useridstrs.length - 1);
				usercodestrs = usercodestrs.substring(0,usercodestrs.length - 1);
				usernamestrs = usernamestrs.substring(0,usernamestrs.length - 1);
				activityidstrs = activityidstrs.substring(0,activityidstrs.length - 1);
				limittimestrs = limittimestrs.substring(0,limittimestrs.length - 1);

				parent.document.getElementById('userid').value=useridstrs;
				parent.document.getElementById('usercode').value=usercodestrs;
				parent.document.getElementById('username').value=usernamestrs;
				parent.document.getElementById('activityid').value=activityidstrs;
				parent.document.getElementById('limittime').value=limittimestrs;
			}
	}
}

function init_data(){//初始化数据 读取退办人员数据列表
	if (parent){
		var tmp_usercode = parent.document.getElementById(param_activityid+"_usercode");
		var tmp_username = parent.document.getElementById(param_activityid+"_username");
		if (tmp_usercode==null) return;
		if (tmp_usercode.value!='' && tmp_usercode.value!=null && tmp_usercode.value!='null') {
			var tmp_usercode_arr=tmp_usercode.value.split(',');
			var tmp_username_arr=tmp_username.value.split(',');
			var n = Ext.getCmp("BussGrid");
			var u = n.getStore();
		    if(tmp_usercode_arr.length>0){
		      for(var i=0;i< tmp_usercode_arr.length;i++){
				 var q = tmp_usercode_arr[i];
				 var v = tmp_username_arr[i];
				 
				 var s = false;
				 for ( var o = 0; o < u.getCount(); o++) {
				  if (u.getAt(o).data.userCode == q) {
				   s = true;
				   break;
				  }
				 }
				 if (!s) {
				  var w = {
				   userId : '',
				   userCode : q,
				   userName : v
				  };
				  var r = new u.recordType(w);
				  n.stopEditing();
				  u.add(r);
				 }
		      }
		    }
		}
	}
}

function init_add(){//初始化
	if (parent){
		var pdata_usercode = parent.document.getElementById(param_activityid+"_usercode");
		var pdata_username = parent.document.getElementById(param_activityid+"_username");
		if (pdata_usercode){
			
			if (pdata_usercode.value=='')  return;
			
			var pdata_usercode_arr=pdata_usercode.value.split(',');
			var pdata_username_arr=pdata_username.value.split(',');
			var n = Ext.getCmp("BussGrid2");
			var u = n.getStore();
		    if(pdata_usercode_arr.length>0){
		      for(var i=0;i< pdata_usercode_arr.length;i++){
				 var q = '';
				 var v = pdata_usercode_arr[i];
				 var k = pdata_username_arr[i];
				 var s = false;
				 for ( var o = 0; o < u.getCount(); o++) {
				  if (u.getAt(o).data.userCode == v) {
				   s = true;
				   break;
				  }
				 }
				 if (!s) {
				  var w = {
				   userId : q,
				   userCode : v,
				   userName : k
				  };
				  var r = new u.recordType(w);
				  n.stopEditing();
				  u.add(r);
				 }
		      }
		    }
		}
	}
}

function init_add_single(){//初始化
	if (parent){
		var pdata_usercode = parent.document.getElementById(param_activityid+"_usercode");
		var pdata_username = parent.document.getElementById(param_activityid+"_username");
		if (pdata_usercode){
			
			if (pdata_usercode.value=='')  return;
			
			var pdata_usercode_arr=pdata_usercode.value.split(',');
			var pdata_username_arr=pdata_username.value.split(',');
			var n = Ext.getCmp("BussGrid2");
			var u = n.getStore();
		    if(pdata_usercode_arr.length==1){
		      for(var i=0;i< pdata_usercode_arr.length;i++){
				 var q = '';
				 var v = pdata_usercode_arr[i];
				 var k = pdata_username_arr[i];
				 var s = false;
				 for ( var o = 0; o < u.getCount(); o++) {
				  if (u.getAt(o).data.userCode == v) {
				   s = true;
				   break;
				  }
				 }
				 if (!s) {
				  var w = {
				   userId : q,
				   userCode : v,
				   userName : k
				  };
				  var r = new u.recordType(w);
				  n.stopEditing();
				  u.add(r);
				 }
		      }
		    }
		}
	}
}

/**
	取得结果
	返回结果:array
		   arr[0] = ids    ID集合
		   arr[1] = values 编码集合
		   arr[2] = texts  名称集合
*/
function getResult(){
	var l = "";
	var j = "";
	var k = "";
	var f = Ext.getCmp("BussGrid2").getStore();
	var len =  f.getCount();
	if (len<=0) {return;}
	 for ( var g = 0; g < len; g++) {
	  if (g > 0) {
	   l += ",";
	   j += ",";
	   k += ",";
	  }
	  l += f.getAt(g).data.userId;
	  j += f.getAt(g).data.userCode;
	  k += f.getAt(g).data.userName;
	 }
	document.getElementById('ids').value=l;
	document.getElementById('values').value=j;
	document.getElementById('texts').value=k;
	
	var arr = new Array();
	arr[0]=l;
	arr[1]=j;
	arr[2]=k;
	return arr; 
}

/**
* 父窗口方法 _multSelectUser(ids,values,texts) 
*/
function _select(ids,values,texts){
	//alert(ids+','+values+','+texts)
	//if (opener) {opener._multSelectUser(ids,values,texts);window.close();};
	//else alert('找不到父窗口!');
	if (window.opener && window.opener.location){
		opener._auditnext2(ids,values,texts);window.close();
	} else {
		alert('找不到父窗口!')
	}
	if (parent) {
		parent._auditnext2(ids,values,texts);window.close();
	}
}

var refresh = function (){
 	Ext.getCmp('BussGrid').store.load({params : {
						start : 0,
						limit : 15
					}});
}

Ext.onReady(function(){
	
	var viewport =  new Buss.Layout.Viewport();
	
	<c:if test="${hiddendept!=1}">
    //Ext.getCmp('treepanel').expandAll();//树默认全部展开
    if (Ext.getCmp('treepanel')){
    Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
    Ext.getCmp('treepanel').getRootNode().select();
    }
    </c:if>
    //Ext.getCmp('_grid').bbar.dom.align = 'center';//按钮居中
	Ext.getCmp('BussGrid').addListener('celldblclick', function(grid, rowIndex,
	columnIndex, e) {// 列表双击事件
		add_all();
	});
	
	Ext.getCmp('BussGrid2').addListener('celldblclick', function(grid, rowIndex,
	columnIndex, e) {// 列表双击事件
		rem_all();
	});
	Ext.getCmp('BussGrid').addListener('bodyresize', function(p,width,height) {
	   var clientHeight = 0;
		 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
		    clientHeight = document.documentElement.clientHeight;
		 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
		    clientHeight = document.body.clientHeight;
	   }
	   p.setHeight(clientHeight-30);
	});
	Ext.getCmp('BussGrid2').addListener('bodyresize', function(p,width,height) {
	   var clientHeight = 0;
		 if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
		    clientHeight = document.documentElement.clientHeight;
		 } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
		    clientHeight = document.body.clientHeight;
	   }
	   p.setHeight(clientHeight-30);
	});
	
	init_data();
	<c:if test="${param.singleselect==0 && param.needsync=='true'}">
		//init_add();//加载已选择的人员
	</c:if>
	<c:if test="${param.singleselect==1}">
		//init_add_single();//加载已选择的人员
	</c:if>	
	<% User user=BaseService.getOnlineUser(request);String username = user.getUserName();%>
	if (param_activityid=='trackActionSpecialType3'){
		var defaultuserid="normaluser";
		var defaultusercode="normaluser";
		var defaultusername="<%=username%>";
		
			var n = Ext.getCmp("BussGrid2");
			var u = n.getStore();
			 var q = defaultuserid;
			 var v = defaultusercode;
			 var k = defaultusername;
			 var s = false;
			 for ( var o = 0; o < u.getCount(); o++) {
			  if (u.getAt(o).data.userCode == v) {
			   s = true;
			   break;
			  }
			 }
			 if (!s) {
			  var w = {
			   userId : q,
			   userCode : v,
			   userName : k
			  };
			  var r = new u.recordType(w);
			  n.stopEditing();
			  u.add(r);
			 }
		add_all_parent_TMP(defaultuserid,defaultusercode,defaultusername);
	}
	
});

function onKeyUpEvent(e){//监听键盘事件
	switch(e.keyCode)
	{
		case 13:
			refresh();
			break;//回车  
		default:
		break;
	}
}
/**
针对阶段性回复
*/
function add_all_parent_TMP(userid,usercode,username){
	if (parent){
		var pdata_userid = parent.document.getElementById('userid');
		var pdata_usercode = parent.document.getElementById('usercode');
		var pdata_username = parent.document.getElementById('username');
		var pdata_activityid = parent.document.getElementById('activityid');
		var pdata_limittime = parent.document.getElementById('limittime');
		var tmp_userid = pdata_userid.value;
		var tmp_usercode = pdata_usercode.value;
		var tmp_username = pdata_username.value;
		var tmp_activityid = '';
		var tmp_limittime = '';;
		if (pdata_activityid) tmp_activityid = pdata_activityid.value;
		if (pdata_limittime) tmp_limittime = pdata_limittime.value;
		
		//判断是否存在
		var isexist = false;
		var p_activityidStrs = tmp_activityid.split(',');
		var p_usercodeStrs = tmp_usercode.split(',');
		for (var i=0;i<p_activityidStrs.length;i++){
			var tmp_activityid_str = p_activityidStrs[i];
			var tmp_usercode_str = p_usercodeStrs[i];
			if (tmp_activityid_str==param_activityid && tmp_usercode_str==usercode){
				isexist = true;
			}
		}
		
		if (isexist==false){
			if (tmp_userid!='') tmp_userid+=',';
			pdata_userid.value=tmp_userid+userid;
			if (tmp_usercode!='') tmp_usercode+=',';
			pdata_usercode.value=tmp_usercode+usercode;
			if (tmp_username!='') tmp_username+=',';
			pdata_username.value=tmp_username+username;	
			
			if (pdata_activityid){
			if (tmp_activityid!='') tmp_activityid+=',';
			pdata_activityid.value=tmp_activityid+param_activityid;
			}
			
			if (pdata_limittime){
			if (tmp_limittime!='') tmp_limittime+=',';
			pdata_limittime.value=tmp_limittime+"72";
			}
		}
	}
}

</script>
