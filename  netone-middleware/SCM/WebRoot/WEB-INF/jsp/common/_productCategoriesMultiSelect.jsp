<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>产品分类选择</title>
	</head>
	<body>
		<div id='container' style="width: 100%; height: 100%;">
			<div id="queryDiv"
				style="display: none; margin-top: 3px; margin-left: 5px;">
				<form id="queryForm" action="">
					<table class="main">
						<tr>
							<input type="hidden" name="parentCategoriesId" id="parentCategoriesId" />
							<input type="hidden" name="categoriesId" id="categoriesId"
								style="width: 150px" />

							<td width="80" class="label">
								产品分类编号:
							</td>
							<td width="150px">
								<input name="categoriesCode" id="categoriesCode"
									style="width: 150px" value="" />
							</td>
							<td width="80px" class="label">
								产品分类名称:
							</td>
							<td width="150px">
								<input name="categoriesName" id="categoriesName"
									style="width: 150px" value="" />
							</td>
							<td colspan="2" align="right">
								<input id="queryBtn" type="button" value=" 查 询 " class="btn"
									onmouseover="this.className='btn_mouseover'"
									onMouseOut="this.className='btn'"
									onmousedown="this.className='btn_mousedown'"
									onMouseUp="this.className='btn'" />
							</td>
						</tr>


						<input type="hidden" name="stack" id="stack" value="" />

						<input type="hidden" name="box" id="box" value="" />


						<input type="hidden" name="ton" id="ton" value="" />

					</table>
				</form>
			</div>
			<div id="listDiv">
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
  Ext.ns('ProductCategories.Data');
  ProductCategories.Data.ProductCategoriesGrid  =  Ext.extend(Ext.grid.GridPanel,{
  
		 initComponent: function() {
		 
		    var index= new Ext.grid.RowNumberer();//
		    var cb = new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
		    	<c:if test="${param.select eq 'single'}">singleSelect: true</c:if>
		    	<c:if test="${param.select != 'single'}">singleSelect: false</c:if>
				
		   	});
		    
			var config = {
			    sm : cb,
			    frame:true,
			    border:false,
				store: new Ext.data.Store({
					url: "<c:url value='/products/products.do?method=onList' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [
						{name: 'categoriesId'},
						{name: 'categoriesCode'},
						{name: 'categoriesName'}/*,
						{name: 'stack'},
						{name: 'box'},
						{name: 'ton'}*/
					   ])
				}),
				columns:[
				        index,
						cb,
						{header: "编号", width: 100, dataIndex: 'categoriesId', sortable: true,hidden: true},
						{header: "产品分类编号", width: 100, dataIndex: 'categoriesCode', sortable: true},
						{header: "产品分类名称", width: 100, dataIndex: 'categoriesName', sortable: true}/*,
						{header: "一箱等于", width: 100, dataIndex: 'stack', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var stack = record.get('stack');
								 if(stack==''){
								 	return;
								 }else{
								 	return stack +'瓶';
								 }
							}},
						{header: "一托盘等于", width: 100, dataIndex: 'box', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var box = record.get('box');
								 if(box == ''){
								 	return;
								 }else{
								 	return box +'箱';
								 }
								 
							}},
						{header: "一箱等于", width: 100, dataIndex: 'ton', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var ton = record.get('ton');
								 if(ton==''){
								 	return;
								 }else{
								 	return ton +'吨';
								 }
							}}*/
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
		  ProductCategories.Data.ProductCategoriesGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var condition = {//取得HTML页面的查询条件
				      categoriesId: Ext.get('categoriesId').getValue(),
				      categoriesName: Ext.get('categoriesName').getValue(),
				      categoriesCode: Ext.get('categoriesCode').getValue(),
				      parentCategoriesId: Ext.get('parentCategoriesId').getValue()/*,
				      stack: Ext.get('stack').getValue(),
				      box: Ext.get('box').getValue(),
				      ton: Ext.get('ton').getValue()*/
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
  Ext.reg('ProductCategoriesGrid', ProductCategories.Data.ProductCategoriesGrid);//注册一个组件,注册成xtype以便能够延迟加载
  
  Ext.ns('ProductCategories.Layout');
  
  ProductCategories.Layout.Viewport =  Ext.extend(Ext.Viewport, {
  	 
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
						  text:'确认',
						  id:'ext_b_add',
						  iconCls:"addIcon"
					  }),
					  new Ext.Toolbar.Button({
									   text:'退出',
									   id:'ext_b_change',
									   iconCls:"exitIcon",
									   handler: function(){window.close();}
					  })
                	]}/*,{
						  xtype:'panel',
						  //height:65,
						  height:0,
						  border:false,
						  contentEl :'queryDiv',//加载本地资源
						  bodyStyle:'background-color:#FFFFFF',//设置面板体的背景色
						  style:"padding:5px"
					  }*/,{
					    id:"ProductCategoriesGrid",
					    xtype:"ProductCategoriesGrid",
			            border:false,
			            hideBorders:true,
			            height : clientHeight-30,
					 	autoScroll:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			ProductCategories.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
   Ext.onReady(function(){//页面加载时候触发事件
	var viewport =  new ProductCategories.Layout.Viewport();
	var productCategoriesGrid2 = viewport.findById('ProductCategoriesGrid');//Grid

	//--------------------------页面数据加载方法定义-------------------------
	
	 
	refresh = function(){//刷新
    	productCategoriesGrid2.store.load({params:{start:0, limit:15}});//刷新当前页面
	};
	
	//--------------------------初始化加载--------------------------
	refresh();//初始化加载数据
	//Ext.get('queryDiv').dom.style.display='none';//显示自定义页面数据
	
	//------------------------------------初始化按钮事件开始--------------------------------
	
//     Ext.EventManager.addListener(Ext.get('ext_b_view'), 'click', function(){//查询事件弹出查询窗口 ---new20090730 yoki     
//	    openWinCenter('产品分类查询窗口','<c:url value="/products/products.do?method=onMainView"/>', 500,250,true);
//    });

		productCategoriesGrid2.addListener('celldblclick',function(grid, rowIndex, columnIndex, e){//列表双击事件
		
			var categoriesId = '';//产品leibie编号
	   		var categoriesName = '';//产品leibie名称
	   		var categoriesCode='';
	   		var categoriesNameStr="";
	   		var categoriesCodeStr="";
	  	  	var recs = productCategoriesGrid2.getSelectionModel().getSelections();
			if(recs.length == 0){
				window.close();
			}else{
			
				var oDom = opener.document;
				var categoriesIdArr = oDom.getElementById('categoriesIdArr');
				
				//产品分类的名称  2009-10-06 产品多选
			    var categoriesNameStrObject=oDom.getElementById('code');
				if(categoriesNameStrObject!=null){
					for(var i=0;i<recs.length;i++){
							categoriesId = recs[i].get('categoriesId');
							categoriesName = recs[i].get('categoriesName');
							categoriesCode=recs[i].get('categoriesCode');
							if(recs.length-1==i){
								categoriesNameStr=categoriesNameStr+categoriesName;
								categoriesCodeStr=categoriesCodeStr+categoriesCode;
							}else{
								categoriesNameStr=categoriesNameStr+categoriesName+",";
								categoriesCodeStr=categoriesCodeStr+categoriesCode+",";
							}
						}
						oDom.getElementById('code').value=categoriesNameStr;
						if(oDom.getElementById('categoriesCode')!=null){
					   oDom.getElementById('categoriesCode').value=categoriesCodeStr;
					}
				}
				
		 		var categoriesNameStrObject1=oDom.getElementById('categories');
				if(categoriesNameStrObject1!=null){
						for(var i=0;i<recs.length;i++){
							categoriesId = recs[i].get('categoriesId');
							categoriesName = recs[i].get('categoriesName');
							categoriesCode=recs[i].get('categoriesCode');
							if(recs.length-1==i){
								categoriesNameStr=categoriesNameStr+categoriesName;
								categoriesCodeStr=categoriesCodeStr+categoriesCode;
							}else{
								categoriesNameStr=categoriesNameStr+categoriesName+",";
								categoriesCodeStr=categoriesCodeStr+categoriesCode+",";
								
							}
						}
						oDom.getElementById('categories').value=categoriesNameStr;
						if(oDom.getElementById('categoriesCode')!=null){
					   oDom.getElementById('categoriesCode').value=categoriesCodeStr;
					      }
				}
				
				
				if(categoriesIdArr!=null){
					for(var i=0;i<recs.length;i++){
						categoriesId = recs[i].get('categoriesId');
						categoriesName = recs[i].get('categoriesName');
						categoriesCode=recs[i].get('categoriesCode');
						if(recs.length-1==i){
							categoriesNameStr=categoriesNameStr+categoriesName;
							categoriesCodeStr=categoriesCodeStr+categoriesCode;
						}else{
							categoriesNameStr=categoriesNameStr+categoriesName+",";
							categoriesCodeStr=categoriesCodeStr+categoriesCode+",";
						}
						
						var hasexist=0;
						for(var o=0;o<categoriesIdArr.length;o++){
							 var optionsvalue_sub=categoriesIdArr.options[o].value;
							 if(optionsvalue_sub==categoriesId)
							 	hasexist+=1;
						}
						if(hasexist==0){
							var oOption = oDom.createElement("OPTION");
							categoriesIdArr.options.add(oOption);
							oOption.text = categoriesName;
							oOption.value = categoriesId;
						}
					}
				
				}
			window.close();//关闭窗口
			}
		});
		
    	Ext.EventManager.addListener(Ext.get('ext_b_add'), 'click', function(){//确认
		var categoriesId = '';//产品leibie编号
   		var categoriesName = '';//产品leibie名称
   		var categoriesNameStr="";
   		var categoriesCodeStr="";
   		var categoriesCode='';
  	  	var recs = productCategoriesGrid2.getSelectionModel().getSelections();
		if(recs.length == 0){
			window.close();
		}else{
			var oDom = opener.document;
			var categoriesIdArr = oDom.getElementById('categoriesIdArr');
			
			//产品分类的名称  2009-10-06 产品多选
		    var categoriesNameStrObject=oDom.getElementById('code');
			if(categoriesNameStrObject!=null){
				for(var i=0;i<recs.length;i++){
						categoriesId = recs[i].get('categoriesId');
						categoriesName = recs[i].get('categoriesName');
						categoriesCode = recs[i].get('categoriesCode');
						if(recs.length-1==i){
							categoriesNameStr=categoriesNameStr+categoriesName;
							categoriesCodeStr=categoriesCodeStr+categoriesCode;
						}else{
							categoriesNameStr=categoriesNameStr+categoriesName+",";
							categoriesCodeStr=categoriesCodeStr+categoriesCodeStr+",";
						}
					}
					oDom.getElementById('code').value=categoriesNameStr;
					if(oDom.getElementById('categoriesCode')!=null){
					   oDom.getElementById('categoriesCode').value=categoriesCodeStr;
					}
			}
			
	 var categoriesNameStrObject1=oDom.getElementById('categories');
		if(categoriesNameStrObject1!=null){
				for(var i=0;i<recs.length;i++){
						categoriesId = recs[i].get('categoriesId');
						categoriesName = recs[i].get('categoriesName');
						categoriesCode = recs[i].get('categoriesCode');
						if(recs.length-1==i){
							categoriesNameStr=categoriesNameStr+categoriesName;
							categoriesCodeStr=categoriesCodeStr+categoriesCode;
						}else{
							categoriesNameStr=categoriesNameStr+categoriesName+",";
							categoriesCodeStr=categoriesCodeStr+categoriesCodeStr+",";
						}
					}
					oDom.getElementById('categories').value=categoriesNameStr;
					if(oDom.getElementById('categoriesCode')!=null){
					   oDom.getElementById('categoriesCode').value=categoriesCodeStr;
					}
			}
			
			
			if(categoriesIdArr!=null){
				for(var i=0;i<recs.length;i++){
					categoriesId = recs[i].get('categoriesId');
					categoriesName = recs[i].get('categoriesName');
					categoriesCode = recs[i].get('categoriesCode');
					if(recs.length-1==i){
						categoriesNameStr=categoriesNameStr+categoriesName;
						categoriesCodeStr=categoriesCodeStr+categoriesCode;
					}else{
						categoriesNameStr=categoriesNameStr+categoriesName+",";
						categoriesCodeStr=categoriesCodeStr+categoriesCodeStr+",";
					}
					
					var hasexist=0;
					for(var o=0;o<categoriesIdArr.length;o++){
						 var optionsvalue_sub=categoriesIdArr.options[o].value;
						 if(optionsvalue_sub==categoriesId)
						 	hasexist+=1;
					}
					if(hasexist==0){
						var oOption = oDom.createElement("OPTION");
						categoriesIdArr.options.add(oOption);
						oOption.text = categoriesName;
						oOption.value = categoriesId;
				}
			}
			
		}
		window.close();//关闭窗口
	}});
  
  //------公共方法定义(按钮,私有事件)-----------------
	getCategoriesId  = function(){//取得所取分类信息
	    var recs = productCategoriesGrid2.getSelectionModel().getSelections()
		var list = [];
		if(recs.length == 0){
			Ext.MessageBox.alert('系统提示','请选择要进行操作的分类信息！');
		}else{
			for(var i = 0 ; i < recs.length ; i++){
				var rec = recs[i];	
				list.push(rec.get('categoriesId'))
			}
		}
		return list;
	}
  });
</script>
