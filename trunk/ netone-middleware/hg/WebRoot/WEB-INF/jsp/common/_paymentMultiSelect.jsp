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
			<script language="javascript" type="text/javascript"
			src="<%=path%>/My97DatePicker/WdatePicker.js" charset="gb2312"></script>
		<jsp:include page="../common/metaExt.jsp"></jsp:include>
		<title>货款单选择</title>
	</head>
	<body>
		<div id='container' style="width: 100%; height: 100%;">
			<div id="queryDiv">
				<form id="queryForm" action="">
					<table class="main">
						<tr>
							<td width="80" class="label">
								日期范围:
							</td>
							<td width="150px">
								  <input type="text" name="beginDate" id="beginDate"
											value="${beginTime}" class="Wdate" style="width:150px"
											onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
							</td>
							<td width="150px" class="label">
							 <input type="text" name="endDate" id="endDate" value="${endTime}"
											style="width: 150px" class="Wdate"
											onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" />
							</td>
							<td  align="left">
								<input id="queryBtn" type="button" value=" 查 询 " class="btn"
								onclick="refresh();"
									onmouseover="this.className='btn_mouseover'"
									onMouseOut="this.className='btn'"
									onmousedown="this.className='btn_mousedown'"
									onMouseUp="this.className='btn'" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div id="listDiv">
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
   Ext.ns('Payment.Data');
   Payment.Data.PaymentGrid  =  Ext.extend(Ext.grid.GridPanel,{
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
    					url: "<c:url value='/finance/payment.do?method=onList&filterpayment=${filterpayment}'/>", //JSON数据
    					reader: new Ext.data.JsonReader(
    					   {totalProperty: 'total',
    					    root: 'rows'
    					   }, [ 
    					   		{name: 'pid'},
    					   		{name: 'payCode'},
    					   		{name: 'normalSum'},
    					   		{name: 'awardSum'},
    					   		{name: 'produceWineSum'},
    					   		{name: 'paySum'},
    					   		{name: 'remitDate'}
    					   ])
    				}),
    				columns:[
    				        index,
    						cb,
    						{
                                    header: "记账单号",
                                    width: 100,
                                    dataIndex: 'payCode',
                                    sortable: true
                                }, {
                                    header: "正常货款金额",
                                    width: 100,
                                    dataIndex: 'normalSum',
                                    sortable: true,
                                    renderer:
									function todo(value, cellmeta, record, rowIndex, columnIndex, store){
									 var normalSum = record.get('normalSum');
									 return (Math.round(normalSum*100)/100);//四舍五入
									}
                                }, {
                                    header: "差价投入金额",
                                    width: 100,
                                    dataIndex: 'awardSum',
                                    sortable: true,
                                    renderer:
									function todo(value, cellmeta, record, rowIndex, columnIndex, store){
									 var awardSum = record.get('awardSum');
									 return (Math.round(awardSum*100)/100);//四舍五入
									}
                                },{
                                    header: "广告用酒金额",
                                    width: 100,
                                    dataIndex: 'produceWineSum',
                                    sortable: true,
                                    renderer:
									function todo(value, cellmeta, record, rowIndex, columnIndex, store){
										 var produceWineSum = record.get('produceWineSum');
										 return (Math.round(produceWineSum*100)/100);//四舍五入
									}
                                },{
                                    header: "汇款总额",
                                    width: 100,
                                    dataIndex: 'paySum',
                                    sortable: true,
                                    renderer:
									function todo(value, cellmeta, record, rowIndex, columnIndex, store){
										var normalSum = record.get('normalSum');
										var awardSum = record.get('awardSum');
										var produceWineSum = record.get('produceWineSum');
								 		var paySum = normalSum+awardSum+produceWineSum;
									 	return (Math.round(paySum*100)/100);//四舍五入
									}
                                }, {
                                    header: "汇款时间",
                                    width: 100,
                                    dataIndex: 'remitDate',
                                    sortable: true
                                }
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
    	Payment.Data.PaymentGrid.superclass.initComponent.apply(this, arguments);
		  
	      this.store.on('beforeload', function(store,options){
				  var condition = {//取得HTML页面的查询条件
				     	clientId: '${param.clientId}',
				     	payCode:'',
				     	rpNumber:'',
				     	beginTime: document.getElementById('beginDate').value,
					  	endTime: document.getElementById('endDate').value,
					  	clientName: ''
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
   Ext.reg('PaymentGrid',  Payment.Data.PaymentGrid);
  
   Ext.ns('Payment.Layout');
  
  Payment.Layout.Viewport =  Ext.extend(Ext.Viewport, {
       initComponent: function(){
       	   var clientHeight = 0;
		   if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
			    clientHeight = document.documentElement.clientHeight;
		   } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
			    clientHeight = document.body.clientHeight;
	       }
       
	       var config = {
				 collapsible:true,
				 autoWidth:true,
				 border:false,
				 baseCls : 'x-plain',
				 layout:'border',
				 items:[{
			            xtype:'panel',
			            region:'north',
			            border:false,
			            contentEl :'queryDiv',
				        bodyStyle:'background-color:#FFFFFF',
				        tbar:[
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
					  },{
					    id:"PaymentGrid",
					    xtype:"PaymentGrid",
					    region:'center',
					 	autoScroll:true,
	                	border:false,
			            hideBorders:true
				 	  }
				 	]
			}
  	         
			Ext.apply(this, Ext.apply(this.initialConfig, config));
    			Payment.Layout.Viewport.superclass.initComponent.apply(this, arguments);
	    }
  });
  
   Ext.onReady(function(){//页面加载时候触发事件
	var viewport =  new Payment.Layout.Viewport();
	var PaymentGrid2 = viewport.findById('PaymentGrid');//Grid

	//--------------------------页面数据加载方法定义-------------------------
	
	 
	refresh = function(){//刷新
		var PaymentGrid3  = viewport.findById('PaymentGrid');//Grid
    	PaymentGrid3.store.load({params:{start:0, limit:15}});//刷新当前页面
	};
	
	//--------------------------初始化加载--------------------------
	refresh();//初始化加载数据

	PaymentGrid2.addListener('celldblclick',function(grid, rowIndex, columnIndex, e){//列表双击事件
		  	var recs = PaymentGrid2.getSelectionModel().getSelections();
			if(recs.length == 0){
				window.close();
			}else{
				var oDom = opener.document;
				innerHTMLPayMent(oDom,recs);
		    }
	});
		
    	Ext.EventManager.addListener(Ext.get('ext_b_add'), 'click', function(){//确认
	  	  	var recs = PaymentGrid2.getSelectionModel().getSelections();
			if(recs.length == 0){
				window.close();
			}else{
				var oDom = opener.document;
				innerHTMLPayMent(oDom,recs);
		    }
	    });
	   function innerHTMLPayMent(oDom,recs){
	   		var pamentInfoObject = oDom.getElementById('paymentInfo');
	   		//订单打印中要识别 选择了多少个货款单，把选中的货款ID列表记录起来
	   		var paymentCodeObj= oDom.getElementById('selectPaymentId');
	   	    var paymentCodeArr=null;
	   	    if(paymentCodeObj!=null){
	   	       paymentCodeArr=paymentCodeObj.value;
	   	    }
   	    
	   		var textStrHead='<table border="1" cellPadding="0" borderColor="#000000" style="border-collapse: collapse;text-align:center;width:100%">';
	   		var textStrTitle='<tr><td style="width: 100px; height: 20px" align="center">汇款单编号</td><td>汇款时间</td><td style="width: 100px; height: 20px" align="center">正常货款金额</td><td style="width: 100px; height: 20px" align="center">差价投入金额</td><td style="width: 100px; height: 20px" align="center">广告用酒金额</td><td style="width: 100px; height: 20px" align="center">汇款总额</td></tr>';
			var textStrEnd='</table>';
				//将选中的货款单加入到目标页面中去
	   		     var textStr='';
	   		     for(var i=0;i<recs.length;i++){
							pid = recs[i].get('pid');
							paymentCode = recs[i].get('payCode');
							paymentDate = recs[i].get('remitDate');
							normalSum = recs[i].get('normalSum');
							awardSum = recs[i].get('awardSum');
							produceWineSum = recs[i].get('produceWineSum');
						    payedSum = normalSum+awardSum+produceWineSum;
						    textStr+='<TR><TD style="width: 100px;" align="center">'+paymentCode+'</TD>';
						    textStr+='<TD style="width: 100px ;" align="center">'+paymentDate+'</TD>';
						    textStr+='<TD style="width: 100px ;" align="center">'+normalSum+'</TD>';
						    textStr+='<TD style="width: 100px ;" align="center">'+awardSum+'</TD>';
						    textStr+='<TD style="width: 100px ;" align="center">'+produceWineSum+'</TD>';
						    textStr+='<TD style="width: 100px ;" align="center">'+payedSum+'</TD></TR>';
						    if(paymentCodeArr!=null){
						    	paymentCodeArr=paymentCodeArr+pid+",";
						    }
					}  
				 var checkIfPaymentEmpty='${filterpayment}'.split(',').length;
				
	   		     if(checkIfPaymentEmpty==1){//表明是第一次添加货款
                        textStr=textStrHead+textStrTitle+textStr+textStrEnd;
						pamentInfoObject.innerHTML=textStr;
	   		     }else if(checkIfPaymentEmpty>1){//表明是第二次追加货款
	   		    	 textStr=textStrHead+textStr+textStrEnd;
	   		     }
	   		      
	   		     if(pamentInfoObject!=null){
	   		        	if(checkIfPaymentEmpty==1){//表明是第一次添加货款
							pamentInfoObject.innerHTML=textStr;
	   		    		 }else if(checkIfPaymentEmpty>1){//表明是第二次追加货款，在原先的货款基础上加入新的信息
	   		    	  		pamentInfoObject.innerHTML=pamentInfoObject.innerHTML+textStr;
	   		     		}
	   		     }
				 // oDom.getElementById('payment').style.display='none';
				if(paymentCodeObj!=null){
	   	       		paymentCodeObj.value=paymentCodeArr;
	   	    	}
	   	    	opener.savePaymentCode();//自动保存选择的货款内容
	   	    	
				window.close();
	   }
  });
</script>
