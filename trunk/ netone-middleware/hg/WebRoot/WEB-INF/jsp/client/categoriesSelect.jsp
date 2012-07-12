<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>产品管理</title>
	</head>
	<body>
		<div id='container' style="width: 100%; height: 100%;">
			<div id="queryDiv"
				style="display: none; margin-top: 5px; margin-left: 5px;">
				<form id="queryForm" action="">
					<table class="main">
						<tr>
							<td>
								<input type="text" name="categirues" id="categirues"
									style="width: 250px" />
							</td>
							<td class="label">
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
  Ext.ns('Categories.Data');
  Categories.Data.CategoriesGrid  =  Ext.extend(Ext.grid.GridPanel,{
  
		 initComponent: function() {
		 
		    var index= new Ext.grid.RowNumberer();//
		   /* var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	});*/
		    
			var config = {
			    //sm : cb,
			    frame:true,
			    border:false,
				store: new Ext.data.Store({
					url: "<c:url value='/client/sellTarget.do?method=onListCategories' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [
						{name: 'categoriesCode'},
						{name: 'categoriesName'}
					   ])
				}),
				columns:[
				        index,
						//cb,
						{header: "分类编号", width: 90, dataIndex: 'categoriesCode', sortable: true},
						{header: "分类名称", width: 100, dataIndex: 'categoriesName', sortable: true}
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
		  Categories.Data.CategoriesGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var condition = {//取得HTML页面的查询条件
						categirues: Ext.get('categirues').getValue()
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
  Ext.reg('CategoriesGrid',  Categories.Data.CategoriesGrid);//注册一个组件,注册成xtype以便能够延迟加载
  
  Ext.ns('Categories.Layout');
  
  Categories.Layout.Viewport =  Ext.extend(Ext.Viewport, {
  	 
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
			            xtype:'panel',
			            height:40,
			            border:false,
			            contentEl :'queryDiv',
				        bodyStyle:'background-color:#FFFFFF',
				        style:"padding:5px"
					  },{
					    id:"CategoriesGrid",
					    xtype:"CategoriesGrid",
			            border:false,
			            hideBorders:true,
			            height : clientHeight-45,
					 	autoScroll:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			Categories.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
   Ext.onReady(function(){//页面加载时候触发事件
	var viewport =  new Categories.Layout.Viewport();
	var CategoriesGrid2 = viewport.findById('CategoriesGrid');//Grid

	//--------------------------页面数据加载方法定义-------------------------
	
	refresh = function(){//刷新
		var start = 0 ;//当前分页记录开始位置
		var CategoriesGrid3  = viewport.findById('CategoriesGrid');//Grid
		if (CategoriesGrid3.store.lastOptions!=null){
			start = CategoriesGrid3.store.lastOptions.params.start;
		} else {
			start = 0;
		}
    	CategoriesGrid3.store.load({params:{start:start, limit:15}});//刷新当前页面
	};
	
	//--------------------------初始化加载--------------------------
	refresh();//初始化加载数据
	Ext.get('queryDiv').dom.style.display='block';//显示自定义页面数据
	
	
	//------------------------------------初始化按钮事件开始--------------------------------
	CategoriesGrid2.addListener('celldblclick',function(grid, rowIndex, columnIndex, e){//列表双击事件
			var categoriesCode = '';//产品分类编号
	   		var categoriesName = '';//产品分类名称
	  	  	var recs = CategoriesGrid2.getSelectionModel().getSelections();
			if( recs == undefined || recs.length > 1 || recs.length <0){
					window.alert('请选择一条记录进行确认!');
					return ;
			}else if(recs.length == 0){
				//window.alert('未选择！');
			}else{
				categoriesCode = recs[0].get('categoriesCode');
				categoriesName = recs[0].get('categoriesName');
			}
			//确认参数回父窗口
			opener.document.getElementById('categoriesCode').value=categoriesCode;
			opener.document.getElementById('categoriesName').value=categoriesName;
			window.close();//关闭窗口
		});
	
    Ext.EventManager.addListener(Ext.get('queryBtn'), 'click', function(){//查询事件      
	    refresh();
    });
 });
</script>
