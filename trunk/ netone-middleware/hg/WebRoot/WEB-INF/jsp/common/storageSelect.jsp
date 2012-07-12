<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/metaExt.jsp"></jsp:include>
<title>仓库选择</title>
</head>
<body onkeyup="onKeyUpEvent(event);">
<div id='container' style="width: 100%; height: 100%;">
  <div id="queryDiv" style="display: block; margin-top: 15px;margin-left: 25px;">
    <form id="queryForm" action="">
     <input type="hidden" name="storageId" id="storageId" style="width:100%"/>
     <input type="hidden" name="address" id="address" style="width:100%"/>
      <table  cellPadding="0"  border="3" borderColor="#DFC68E"  style="border-collapse:collapse;">
        <tr>
          <td  width="150px"><input type="text" name="storageCode" id="storageCode" style="width: 150px;font:12px/14px arial;color:#999;" value="请输入仓库编号" onfocus="if(this.value=='请输入仓库编号') {this.value=''}" onblur="if(this.value=='') this.value='请输入仓库编号'"/>
          </td>
          <td  width="150px"><input type="text" name="storageName" id="storageName" style="width: 150px;font:12px/14px arial;color:#999;" value="请输入仓库名称" onfocus="if(this.value=='请输入仓库名称') {this.value=''}" onblur="if(this.value=='') this.value='请输入仓库名称'"/>
          </td>
          <td colspan="2" align="right"><input id="queryBtn" type="button" value=" 查 询 " class="btn" />
          </td>
        </tr>
      </table>
    </form>
  </div>
</body>
</html>
<script type="text/javascript">
  Ext.ns('Storage.Data');
  Storage.Data.StorageGrid  =  Ext.extend(Ext.grid.GridPanel,{
  
		 initComponent: function() {
		 
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	});
		    
			var config = {
			    //sm : cb,
			    frame:true,
			    border:false,
				store: new Ext.data.Store({
					url: "<c:url value='/storage/storage.do?method=onStorageSelectList' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [
						{name: 'storageId'},
						{name: 'storageCode'},
						{name: 'storageName'},
						{name: 'address'},
						{name: 'maxLimits'},
						{name: 'note'}
					   ])
				}),
				columns:[
				        index,
						//cb,
						{header: "编号", width: 100, dataIndex: 'storageId', sortable: true,hidden:true},
						{header: "仓库编号", width: 80, dataIndex: 'storageCode', sortable: true},
						{header: "仓库名称", width: 100, dataIndex: 'storageName', sortable: true},
						{header: "仓库地址", width: 100, dataIndex: 'address', sortable: true,hidden:true},
						{header: "仓储最大容量", width: 100, dataIndex: 'maxLimits', sortable: true,hidden:true},
						{header: "摘要", width: 120, dataIndex: 'note', sortable: true,hidden:true}
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
		  Storage.Data.StorageGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
	      		　var code = Ext.get('storageCode').getValue();//取产品编号的值，将其清空；方便查找；
	      		  var name = Ext.get('storageName').getValue();
	      		  if(code =='请输入仓库编号'){
	      		  	code = '';
	      		  }
	      		  if(name =='请输入仓库名称'){
	      		  	name = '';
	      		  }
				  var condition = {//取得HTML页面的查询条件
				      storageId: Ext.get('storageId').getValue(),
				      storageCode: code,
				      storageName: name,
				      address: Ext.get('address').getValue()
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
  Ext.reg('StorageGrid', Storage.Data.StorageGrid);//注册一个组件,注册成xtype以便能够延迟加载
  
  Ext.ns('Storage.Layout');
  
  Storage.Layout.Viewport =  Ext.extend(Ext.Viewport, {
  	 
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
					  }),"-",
					  new Ext.Toolbar.Button({
						  text:'查看明细',
						  id:'ext_b_viewDetail',
			              iconCls:"viewIcon"
					  })
                	]},*/
                	{
			            xtype:'panel',
			            height:60,
			            border:false,
			            contentEl :'queryDiv',
				        bodyStyle:'background-color:#FFFFFF',
				        style:"padding:5px"
					  },{
					    id:"storageGrid",
					    xtype:"StorageGrid",
			            border:false,
			            hideBorders:true,
			            height : clientHeight-60,
					 	autoScroll:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			Storage.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
   Ext.onReady(function(){//页面加载时候触发事件
	var viewport =  new Storage.Layout.Viewport();
	var storageGrid2 = viewport.findById('storageGrid');//Grid

	//--------------------------页面数据加载方法定义-------------------------
	refresh = function(){//刷新
		var start = 0 ;//当前分页记录开始位置
		var grid  = viewport.findById('storageGrid');//Grid
		//if (grid.store.lastOptions!=null){
		//	start = grid.store.lastOptions.params.start;
		//} else {
			//start = 0;
		//}
    	grid.store.load({params:{start:start, limit:15}});//刷新当前页面
	};
	
	//--------------------------初始化加载--------------------------
	refresh();//初始化加载数据
	Ext.get('queryDiv').dom.style.display='block';//显示自定义页面数据
	
	storageGrid2.addListener('celldblclick',function(grid, rowIndex, columnIndex, e){//列表双击事件
		
	  	  	var recs = storageGrid2.getSelectionModel().getSelections();
			
			var storageId = recs[0].get('storageId');
			var storageCode = recs[0].get('storageCode');
			var storageName = recs[0].get('storageName');
			var storageIdOpener = opener.document.getElementById('storageId');
			var storageCodeOpener = opener.document.getElementById('storageCode');
			var storageNameOpener = opener.document.getElementById('storageName');
			//确认参数回父窗口
			if(storageIdOpener != null){
				storageIdOpener.value = storageId;
			}
			if(storageCodeOpener != null){
				storageCodeOpener.value = storageCode;
			}
			if(storageNameOpener != null){
				storageNameOpener.value = storageName;
			}
			window.close();//关闭窗口
		});
	
	//------------------------------------初始化按钮事件开始--------------------------------

    Ext.EventManager.addListener(Ext.get('queryBtn'), 'click', function(){//查询事件
	     refresh();//-->此方法只是显示当前页，所以如果要查找的东西，不在当前页的话，那么查询的结果就会有误
		 //var grid  = viewport.findById('StorageGrid');//Grid
		 //grid.store.load({params:{start:0, limit:15}});//刷新当前页面
    });
  
  //------公共方法定义(按钮,私有事件)-----------------
	getStorageId  = function(){//取得所取仓库信息
	    var recs = storageGrid2.getSelectionModel().getSelections()
		var list = [];
		if(recs.length == 0){
			Ext.MessageBox.alert('系统提示','请选择要进行操作的分类信息！');
		}else{
			for(var i = 0 ; i < recs.length ; i++){
				var rec = recs[i];	
				list.push(rec.get('storageId'))
			}
		}
		return list;
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
</script>
