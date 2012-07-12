<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../common/metaExt.jsp"></jsp:include>
<title>产品选择</title>
</head>
<body onkeyup="onKeyUpEvent(event);">
<div id='container' style="width: 100%; height: 100%;">
  <div id="queryDiv" style="display: none;margin-top: 15px;margin-left: 25px;">
    <form id="queryForm" action="">
    <input type="hidden" name="productId" id="productId" style="width: 150px" />
  
           <select id="specifications" name="specifications" style="width: 150px;display: none;">
              <option value=""> --所有-- </option>
              <c:forEach var="specificationsMap" items="${specificationsMap}">
                <option value="${specificationsMap.key}"> ${specificationsMap.key} </option>
              </c:forEach>
            </select>
          <select id="units" name="units" style="width: 150px;display: none;" >
              <option value=""> --所有-- </option>
              <c:forEach var="unitsMap" items="${unitsMap}">
                <option value="${unitsMap.key}"> ${unitsMap.value} </option>
              </c:forEach>
            </select>
            <select name="categoriesId" id="categoriesId" style="width: 150px;display: none;">
				<option value="">
					----所有----
				</option>
		  </select>
    
         <input type="hidden" name="invoicePrice" style="width: 150px" id="invoicePrice" />
         <input type="hidden" name="awardPrice" style="width: 150px" id="awardPrice" />
         <input type="hidden" name="minOrder" style="width: 150px" id="minOrder" />
         <input type="hidden" name="ton" style="width: 150px" id="ton" />
         <input type="hidden" name="bottle" style="width: 150px" id="bottle" />
         <input type="hidden" name="box" style="width: 150px" id="box" />
         <input type="hidden" name="grossWeight" style="width: 150px" id="grossWeight" />
         <input type="hidden" name="storageTag" style="width: 150px" id="storageTag" />
      	 <input type="hidden" name="isScan" style="width: 150px" id="isScan" />
      	 <input type="hidden" name="bottle2" style="width: 150px" id="bottle2" />
      <table class="main" >
        <tr>
          <td><input type="text" name="productCode" id="productCode" class="titleInfo" value="请输入产品编号" onfocus="if(this.value=='请输入产品编号') {this.value=''}" onblur="if(this.value=='') this.value='请输入产品编号'"/>
          </td>
          <td><input type="text" name="productName" class="titleInfo" id="productName" value="请输入产品名称" onfocus="if(this.value=='请输入产品名称') {this.value=''}" onblur="if(this.value=='') this.value='请输入产品名称'"/>
          <input type="hidden" name="tagPrice" style="width: 150px" id="tagPrice" />
          </td>
       <td>
          <input id="queryBtn" type="button" value=" 查 询 " class="btn"
          		onmouseover="this.className='btn_mouseover'" onmouseout="this.className='btn'" 
          		onmousedown="this.className='btn_mousedown'" onmouseup="this.className='btn'"/>
          	</td>
        </tr>
        
        
      </table>     
    </form>
  </div>
  <!-- 数据字典表格 -->
  <div id="listDiv"> </div>
</div>
</body>
</html>
<script type="text/javascript">
  Ext.ns('Product.Data');
  Product.Data.ProductGrid  =  Ext.extend(Ext.grid.GridPanel,{
  
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
					url: "<c:url value='/products/product.do?method=onList' />", //JSON数据
					reader: new Ext.data.JsonReader(
					   {totalProperty: 'total',
					    root: 'rows'
					   }, [ 
						{name: 'productId'},
						{name: 'productCode'},
						{name: 'productName'},
						{name: 'specifications'},
						{naem: 'specName'},
						{name: 'units'},
						{name: 'unitsName'},
						{name: 'categories.categoriesId'},
						{name: 'categories.categoriesName'},
						{name: 'invoicePrice'},
						{name: 'awardPrice'},
						{name: 'minOrder'},
						{name: 'ton'},
						{name: 'bottle'},
						{name: 'tagPrice'},
						{name: 'box'},
						{name: 'operate'},
						{name: 'operateTime'}
					   ])
				}),
				columns:[
				        index,
						//cb,
						{header: "编号", width: 90, dataIndex: 'productId', sortable: true,hidden: true},
						{header: "产品编号", width: 100, dataIndex: 'productCode', sortable: true},
						{header: "产品名称", width: 100, dataIndex: 'productName', sortable: true},
						{header: "规格", width: 80, dataIndex: 'specifications', sortable: true,hidden: true},
						{header: "规格value", width: 80, dataIndex: 'specName', sortable: true,hidden: true},
						{header: "单位", width: 80, dataIndex: 'units', sortable: true,hidden: true},
						{header: "单位value", width: 80, dataIndex: 'unitsName', sortable: true,hidden: true},
						{header: "分类编号", width: 100, dataIndex: 'categories.categoriesId', sortable: true,hidden: true},
						{header: "产品分类名称", width: 100, dataIndex: 'categories.categoriesName', sortable: true,hidden:true},
						{header: "发票单价", width: 100, dataIndex: 'invoicePrice', sortable: true,hidden:true},
						{header: "差价投入单价", width: 100, dataIndex: 'awardPrice', sortable: true,hidden: true},
						{header: "客户最低订货", width: 100, dataIndex: 'minOrder', sortable: true,hidden:true},
						{header: "标记费单价", width: 100, dataIndex: 'tagPrice', sortable: true},
						{header: "一箱折合", width: 100, dataIndex: 'ton', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var ton = record.get('ton');
								 if(ton==''){
								 	return;
								 }else{
								 	//return (Math.round(ton*100)/100) +'吨';
								 	return ton+'吨';
								 }
							}},
						{header: "一箱等于", width: 100, dataIndex: 'stack', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var bottle = record.get('bottle');
								 if(bottle==''){
								 	return;
								 }else{
								 	return (Math.round(bottle*100)/100) +'瓶';
								 }
							}},
						{header: "一托盘等于", width: 100, dataIndex: 'box', sortable: true,renderer:
							function todo(value, cellmeta, record, rowIndex, columnIndex, store){
								 var box = record.get('box');
								 if(box == ''){
								 	return;
								 }else{
								 	return (Math.round(box*100)/100) +'箱';
								 }
								 
							}},
						{header: "操作者", width: 100, dataIndex: 'operate', sortable: true,hidden:true},
						{header: "操作时间", width: 120, dataIndex: 'operateTime', sortable: true,hidden:true}
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
		  Product.Data.ProductGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
	      		　var code = Ext.get('productCode').getValue();//取产品编号的值，将其清空；方便查找；
	      		  var name = Ext.get('productName').getValue();
	      		  if(code =='请输入产品编号'){
	      		  	code = '';
	      		  }
	      		  if(name =='请输入产品名称'){
	      		  	name = '';
	      		  }
				  var condition = {//取得HTML页面的查询条件
						productId: Ext.get('productId').getValue(),
						productCode: code,
						productName: name,
						specifications: Ext.get('specifications').getValue(),
						units: Ext.get('units').getValue(),
						categoriesId: Ext.get('categoriesId').getValue(),
						invoicePrice: Ext.get('invoicePrice').getValue(),
						awardPrice: Ext.get('awardPrice').getValue(),
						minOrder: Ext.get('minOrder').getValue(),
						tagPrice: Ext.get('tagPrice').getValue(),
						ton: Ext.get('ton').getValue(),
						bottle: Ext.get('bottle').getValue(),
				     	box: Ext.get('box').getValue(),
				     	grossWeight: Ext.get('grossWeight').getValue(),
				     	storageTag: Ext.get('storageTag').getValue(),
				     	isScan: Ext.get('isScan').getValue(),
				     	bottle2: Ext.get('bottle2').getValue()
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
  Ext.reg('ProductGrid',  Product.Data.ProductGrid);//注册一个组件,注册成xtype以便能够延迟加载
  
  Ext.ns('Product.Layout');
  
  Product.Layout.Viewport =  Ext.extend(Ext.Viewport, {
  	 
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
				 layout:'border',
				 items:[/*{
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
                     ]
                	},*/{
			            xtype:'panel',
			            height:50,
			            border:false,
			            contentEl :'queryDiv',
			            region:'north',
				        bodyStyle:'background-color:#FFFFFF'
				       // style:"padding:5px"
					  },{
					    id:"ProductGrid",
					    xtype:"ProductGrid",
			            border:false,
			            region:'center',
			            hideBorders:true,
			            height : clientHeight-50,
					 	autoScroll:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
			Product.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
   Ext.onReady(function(){//页面加载时候触发事件
	var viewport =  new Product.Layout.Viewport();
	var productGrid2 = viewport.findById('ProductGrid');//Grid

	//--------------------------页面数据加载方法定义-------------------------
	
	function loadCategories(){//加载类别
			var vUrl = '<c:url value="/products/products.do?method=onFindPCByType"/>';
			Ext.Ajax.request({
			   url:  vUrl,
			   success: function(response, options){
							  var responseArray = Ext.util.JSON.decode(response.responseText);     
							  var categoriesSelect = document.getElementById('categoriesId');
							  for(var i=0; i< responseArray.length; i++){
							     var option = document.createElement('option');
								 option.text = responseArray[i].categoriesName ;
				                 option.value = responseArray[i].categoriesId;
								 categoriesSelect.options.add(option);
							  }         
			  			},
				failure: function (response, options) {
				    checkAjaxStatus(response);
				}
			});
  		}
	
	refresh = function(){//刷新
		var start = 0 ;//当前分页记录开始位置
		var ProductGrid3  = viewport.findById('ProductGrid');//Grid
		/*
		if (ProductGrid3.store.lastOptions!=null){
			start = ProductGrid3.store.lastOptions.params.start;
		} else {
			start = 0;
		}*/
    	ProductGrid3.store.load({params:{start:start, limit:15}});//刷新当前页面
	};
	
	//--------------------------初始化加载--------------------------
	refresh();//初始化加载数据
	Ext.get('queryDiv').dom.style.display='block';//显示自定义页面数据
	
	loadCategories();//加载产品分类
	
	//------------------------------------初始化按钮事件开始--------------------------------
	
	productGrid2.addListener('celldblclick',function(grid, rowIndex, columnIndex, e){//列表双击事件
			var productId = '';//产品编号
	   		var productName = '';//产品名称
	   		var specifications = '';//规格
	   		var units = '';//单位
	   		var categoriesId = '';
	   		var minOrder = '';//最低
	   		var tagPrice = '';//标记费
	   		var stack = '';
	   		var box = '';
	  	  	var recs = productGrid2.getSelectionModel().getSelections();
		
				productId = recs[0].get('productId');
				productCode = recs[0].get('productCode');
				productName = recs[0].get('productName');
			 	specifications = recs[0].get('specifications');
				units = recs[0].get('units');
				categoriesId = recs[0].get('categories.categoriesId');
				invoicePrice = recs[0].get('invoicePrice');
				awardPrice = recs[0].get('awardPrice');
				minOrder = recs[0].get('minOrder');
				tagPrice = recs[0].get('tagPrice');
				ton = recs[0].get('ton');
				bottle = recs[0].get('bottle');
				box = recs[0].get('box');
			var productIdObj = opener.document.getElementById('productId');
			var productCodeObj = opener.document.getElementById('productCode');
			var productNameObj = opener.document.getElementById('productName');
			var specificationsObj =opener.document.getElementById('specifications');
			var unitsObj = opener.document.getElementById('units');
			var categoriesIdObj = opener.document.getElementById('categoriesId');
			var invoicePriceObj = opener.document.getElementById('invoicePrice');
			var awardPriceObj = opener.document.getElementById('awardPrice');
			var tagPriceObj = opener.document.getElementById('tagPrice');
			var minOrderObj = opener.document.getElementById('minOrder');
			var tonObj = opener.document.getElementById('ton');
			var bottleObj = opener.document.getElementById('bottle');
			var boxObj = opener.document.getElementById('box');
			
			var openerType = '${param.type}';//父窗口对象,plan,indent界面调用
			if (openerType == 'extSelectCode' ){
				opener.setProductCode(productCode);
			}
			
			if(productIdObj != null){
				productIdObj.value=productId;
			}
			if(productCodeObj != null){
				productCodeObj.value=productCode;
			}
			if(productNameObj != null){
				productNameObj.value=productName;
			}
			if(specificationsObj != null){
				specificationsObj.value=specifications;
			}
			if(unitsObj != null){
				unitsObj.value=units;
			}
			if(categoriesIdObj != null){
				categoriesIdObj.value=categoriesId;
			}
			if(invoicePriceObj != null){
				invoicePriceObj.value=invoicePrice;
			}
			if(awardPriceObj != null){
				awardPriceObj.value=awardPrice;
			}
			if(minOrderObj != null){
				minOrderObj.value = minOrder;
			}
			if(tagPriceObj != null){
				tagPriceObj.value = tagPrice;
			}
			if(tonObj != null){
				tonObj.value = ton;
			}
			if(bottleObj != null){
				bottleObj.value = bottle;
			}
			if(boxObj != null){
				boxObj.value = box;
			}
			//确认参数回父窗口
			//if (openerType != 'extSelectCode'){
				window.close();//关闭窗口
			//}
		});
	
	
	/*Ext.EventManager.addListener(Ext.get('ext_b_add'), 'click', function(){//确认
		var productId = '';//产品编号
   		var productName = '';//产品名称
   		var specifications = '';//规格
   		var units = '';//单位
   		var minOrder = '';//最低
  	  	var recs = productGrid2.getSelectionModel().getSelections();
		if( recs == undefined || recs.length > 1 || recs.length <0){
				window.alert('请选择一条记录进行确认!');
				return ;
		}else if(recs.length == 0){
			//window.alert('未选择！');
		}else{
			productId = recs[0].get('productId');
			productName = recs[0].get('productName');
		 	specifications = recs[0].get('specifications');
			units = recs[0].get('units');
			minOrder = recs[0].get('minOrder');
			
		}
		//确认参数回父窗口
		opener.document.getElementById('productId').value=productId;
		opener.document.getElementById('productName').value=productName;
		opener.document.getElementById('specifications').value=specifications;
		opener.document.getElementById('units').value=units;
		//opener.document.getElementById('minOrder').value=minOrder;
		window.close();//关闭窗口
	});*/
	
    Ext.EventManager.addListener(Ext.get('queryBtn'), 'click', function(){//查询事件      
	    refresh();
    });
    
    //----------公共方法定义(按钮,私有事件)-----------------

    
	
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
