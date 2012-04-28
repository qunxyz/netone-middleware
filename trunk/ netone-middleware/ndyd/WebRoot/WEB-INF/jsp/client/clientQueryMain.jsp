<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>用户查询管理</title>
	</head>
	<body>
		<div id='container' style="width: 100%; height: 100%;">
			<div id="queryDiv"
				style="display: block; margin-top: 50px; margin-left: 5px;"
				align="center">
				<form id="queryForm" action="">
					<table class="main" width="300px">
						<tr>
							<td width="80px" class="label">
								销售部门：
							</td>
							<td width="200px" align="left">
							<!-- 
								<input type="text" name="deptnaturalName" id="deptnaturalName"
									style="width: 156px" /> -->
								<input type="text" name="departmentName" id="departmentName"
									style="width: 180px" onkeyup="onKeyUpEvent3(event)"
									onchange="getDepartmentInfo()" />
								<input type="button"
									onClick="
											javascript:openWinCenter('销售部门选择','../SelectSvl?appname=DEPT.DEPT.ZYERP.YXBM&pagename=deptlist',650,450,true)"
									value="..." class="btn" style="margin-left: -7px"
									onmouseover="this.className='btn_mouseover'"
									onMouseOut="this.className='btn'"
									onmousedown="this.className='btn_mousedown'"
									onMouseUp="this.className='btn'" />
							</td>
						</tr>
						<tr>
							<td width="80px" class="label">
								客户名称：
							</td>
							<td width="200px" align="left">
							<!-- 
								<input type="hidden" name="clientLoginName" id="clientLoginName"
									style="width: 100%" /> -->
								<input type="text" name="clientName" id="clientName"
									style="width: 180px" onkeyup="onKeyUpEvent(event)"
									onchange="getClientInfo()" />
								<input type="button" value="..." class="btn"
									style="margin-left: -7px;"
									onclick="javascript:openWinCenter('客户名称','../SSelectSvl?appname=DEPT.DEPT.ZYERPC&pagename=human', 650,450,true);tmp=document.all.clientName;"
									onmouseover="this.className='btn_mouseover'"
									MouseOut="this.className='btn'"
									onmousedown="this.className='btn_mousedown'"
									onMouseUp="this.className='btn'" />
							</td>
						</tr>
						<!-- 
						<tr>
							<td width="80px" class="label">
								销售部：
							</td>
							<td width="170px">
								<input type="text" name="sellDepartment" id="sellDepartment"
									style="width: 100%" />
							</td>
						</tr>
						<tr>
							<td width="80px" class="label">
								送达站：
							</td>
							<td width="170px">
								<input type="text" name="servicestation" id="servicestation"
									style="width: 100%" />
							</td>
						</tr>
						<tr>
							<td width="80px" class="label">
								隶属于:
							</td>
							<td width="170px">
								<input type="text" name="belongTo" id="belongTo"
									style="width: 100%" />
							</td>
						</tr>
						 -->
						<tr>
							<td colspan="2" class="labelbtn">
								<input id="queryBtn" type="button" value=" 查 询 " class="btn"
									onmouseover="this.className='btn_mouseover'"
									onMouseOut="this.className='btn'"
									onmousedown="this.className='btn_mousedown'"
									onMouseUp="this.className='btn'" />
							</td>
						</tr>
						<input type="hidden" name="operateTime" id="operateTime"
							style="width: 100%" />
					</table>
				</form>
				<!-- <div id="tip" style="text-align: center; color: red;"></div>-->
				<!-- 提示 -->
			</div>
			<div id="listDiv">
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
 Ext.ns('InStorage.Data');
  InStorage.Data.InStorageGrid  =  Ext.extend(Ext.grid.GridPanel,{
  
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
					url: "<c:url value='/storage/inStorage.do?method=onList' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [
						{name: 'inStorageId'},
						{name: 'rkNumber'},
						{name: 'operate'},
						{name: 'operateTime'},
						{name: 'status'},
						{name: 'note'}
						
					   ])
				}),
				columns:[
				        index,
						cb,
						{header: "入库号", width: 100, dataIndex: 'inStorageId', sortable: true,hidden: hidden},
						{header: "入库编码", width: 100, dataIndex: 'rkNumber', sortable: true},
						{header: "操作者", width: 100, dataIndex: 'operate', sortable: true},
						{header: "入库时间", width: 100, dataIndex: 'operateTime', sortable: true},
						{header: "状态", width: 100, dataIndex: 'status', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var status = record.get('status');
								 if( status == '0' ){
									return '在途';
								 }else if(  status == '1'){
									return '入库';
								 }else {
									return '';
								 }
							}
						},
						{header: "摘要", width: 100, dataIndex: 'note', sortable: true}
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
		  InStorage.Data.InStorageGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var condition = {//取得HTML页面的查询条件
				      //inStorageId: Ext.get('inStorageId').getValue(),
				      rkNumber: Ext.get('rkNumber').getValue(),
				      operate: Ext.get('operate').getValue(),
				      operateTime: Ext.get('operateTime').getValue(),
				      //clientId: Ext.get('clientId').getValue(),
				      //clientName: Ext.get('clientName').getValue(),
				      status: Ext.get('status').getValue()
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
  Ext.reg('InStorageGrid', InStorage.Data.InStorageGrid);//注册一个组件,注册成xtype以便能够延迟加载
  
  Ext.ns('InStorage.Layout');
  
  InStorage.Layout.Viewport =  Ext.extend(Ext.Viewport, {
  	 
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
				 items:[/*{
					 xtype:'toolbar',
					 items:[
					  new Ext.Toolbar.Button({
						  text:'在途入库',
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
					  })
                	]},*/{
						  xtype:'panel',
						  height:800,
						  border:false,
						  contentEl :'queryDiv',//加载本地资源
						  bodyStyle:'background-color:#FFFFFF'//设置面板体的背景色
						  //style:"padding:5px"
					  }/*,{
					    id:"InStorageGrid",
					    xtype:"InStorageGrid",
			            border:false,
			            hideBorders:true,
			            height : clientHeight-135,
					 	autoScroll:true
				 	  }*/
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			InStorage.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
   Ext.onReady(function(){//页面加载时候触发事件
	var viewport =  new InStorage.Layout.Viewport();
	//var inStorageGrid2 = viewport.findById('InStorageGrid');//Grid

	//--------------------------页面数据加载方法定义-------------------------
	
	 
	/*refresh = function(){//刷新
		var start = 0 ;//当前分页记录开始位置
		var inStorageGrid3  = viewport.findById('InStorageGrid');//Grid
		if (inStorageGrid3.store.lastOptions!=null){
			start = inStorageGrid3.store.lastOptions.params.start;
		} else {
			start = 0;
		}
    	inStorageGrid3.store.load({params:{start:start, limit:15}});//刷新当前页面
	};*/
	
	//--------------------------初始化加载--------------------------
	//refresh();//初始化加载数据
	Ext.get('queryDiv').dom.style.display='block';//显示自定义页面数据
	
	//------------------------------------初始化按钮事件开始--------------------------------
	/*Ext.EventManager.addListener(Ext.get('ext_b_add'), 'click', function(){//新增
		openWinCenter('在途入库','<c:url value="/storage/inStorage.do?method=onEditView"/>', 1024,800,true);
	});
	
	Ext.EventManager.addListener(Ext.get('ext_b_change'), 'click', function(){//修改
			 var selectionSet = inStorageGrid2.getSelectionModel().getSelections(); 
			 if( selectionSet == undefined || selectionSet.length > 1 || selectionSet.length <=0){
				Ext.MessageBox.alert('系统提示','请选择一条记录进行修改!');
				return ;
			 }
		     guid = selectionSet[0].data.inStorageId;
		     openWinCenter('修改','<c:url value="/storage/inStorage.do?method=onEditView"/>&inStorageId=' + guid, 800,800,true);
	});
	
	Ext.EventManager.addListener(Ext.get('ext_b_delete'), 'click', function(){//删除
	var inStorageIdStr=getInStorageId();
	var num=inStorageIdStr.length;
	if(num >= 1){
			Ext.MessageBox.confirm("系统提示","您确定要删除所选的信息吗？",function(btnId){
				if(btnId == 'yes'){
					var str = "";
					for(var i= 0 ; i < num ; i ++){
						str = str + inStorageIdStr[i]+",";         
					}		
					var str = Ext.util.Format.substr(str,0,str.length - 1);
					deleteInStorageById(str);
				}
		 });
	   }
    });*/
	
    Ext.EventManager.addListener(Ext.get('queryBtn'), 'click', function(){//查询事件   
   		 queryCommon();
	    //openWinCenter('用户查询','<c:url value="/client/client.do?method=onMainView"/>'+'&clientLoginName='+clientLoginName+'&sellDepartment='+sellDepartment+'&servicestation='+servicestation+'&belongTo='+belongTo, 1024,600,true);   
    });	
  });
  
  /*************该页面公共的查询方法***********************/
	function queryCommon()
	{
	  
	    var clientLoginName = encodeURI(document.getElementById("clientName").value);
	    var departmentName=encodeURI(document.getElementById("departmentName").value);
	    opener.document.getElementById('clientLoginName').value=clientLoginName;
		opener.document.getElementById('qh').value=departmentName;
		if(opener){//防止出现没有权限"的js错误对话框
						 if(typeof(window.opener.document)=='unknown'||typeof(window.opener.document) == 'undefined'){ //父窗口已关闭 
							//do nothing
						 }else{ //父窗口没有关闭
							opener.refresh();
						 }
					} else { //父窗口已关闭 
						 //do nothing
					}
					window.close();//关闭
	}
	
	/************************监听键盘事件***************************************/
      function onKeyUpEvent(e){//监听键盘事件---20090730 by yoki
		switch(e.keyCode)
			{
				case 13:
				getClientInfo();
				break;//回车  
				default:
				break;
			}
	}
	function getClientInfo(){//取得人员信息
			var clientCode = document.getElementById('clientName').value;
			if (clientCode!=''){
				Ext.Ajax.request({
	  					url : '<c:url value="/client/client.do?method=onGetClientInfo" />',
	  					params : {clientCode:clientCode,target:1},
	  					method : 'POST',
	  					sync:true,
	  					success : function(response,options){
								var result = Ext.util.JSON.decode(response.responseText);
									document.getElementById('clientName').value=result.clientName;
								 if (result.clientCode == '' && result.clientName==''){
									document.getElementById('clientName').value=clientCode;
								}
								
						},
						failure : function(response,options){
								checkAjaxStatus(response);
						}
	  			});
			}
	}

	var tmp = null;
		var tmp1=null;
 	 		function mselected(options){
			    var humans='';
			    for(var i=0;i<options.length;i++){
			       var human=options[i].text;
			       var code=options[i].value;
			       var reg=options[i].text+'\\['+options[i].value+'\\],';
			       var pat=new RegExp(reg);
			       humans+=human;
			    }
			    tmp.value=humans;
			    tmp1.value=code;
	}
	
	function sselected(text,value){
 		tmp.value=text;
 		//tmp1.value=value;
	}
	
	function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
		//document.all.deptnaturalName.value = url;
		document.all.departmentName.value = name;
	}
	
		function onKeyUpEvent3(e){//监听键盘事件----查看组织
			var clientIdObj = document.getElementById('departmentName');
				switch(e.keyCode)
				{
					case 13:
						getDepartmentInfo();
						break;//回车  
					default:
					break;
				}
	}
	
		function getDepartmentInfo(){//取得信息
			var deptnaturalName = document.getElementById('departmentName').value;
			if (deptnaturalName!=''){
				Ext.Ajax.request({
	  					url : '<c:url value="/storage/storage.do?method=onGetDepartmentInfo" />',
	  					params : {deptnaturalName:deptnaturalName},
	  					method : 'POST',
	  					sync:true,
	  					success : function(response,options){
								var result = Ext.util.JSON.decode(response.responseText);
									document.getElementById('departmentName').value=result.departmentName;
									 if (result.deptnaturalName == '' && result.departmentName==''){
									document.getElementById('departmentName').value=deptnaturalName;
								}
						},
						failure : function(response,options){
								checkAjaxStatus(response);
						}
	  			});
			}
	}
</script>
