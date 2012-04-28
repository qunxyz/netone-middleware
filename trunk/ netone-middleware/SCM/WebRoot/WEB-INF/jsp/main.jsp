<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="common/metaExt.jsp"></jsp:include>
		<title>
		</title>
	</head>
	<body>
	
		<div id="topDiv" align="center" >
			<div id="app-header">
				<div id="header-left">
				</div>
				
				<div id="header-right">
					<div id="setting">
						<a href="<%=path %>/portal/help.jsp"  target="blank">帮助</a>
						|&nbsp;
						<a href="<%=basePath %>/logoutsvl?gotourl=<%=basePath%>" >注销</a>
					</div>
				</div>
			</div>	
		</div>
		<div id='aboutDiv' style='height: 100%; width: 100%; padding: 0px;'>
			<iframe id="contentFrame" src="<c:url value='/department/department.do?method=onDepartmentManageView' />"
				style="width: 100%; height: 100%" frameborder="0" scrolling="no"></iframe>
		</div>
	</body>
</html>
<script type="text/javascript">
//定义使用改变个性化定制的控件   
//该控制实际上为一个可供选择样式表值的下拉框   
//当改变下拉框的选择时则调用Ext.util.CSS.swapStyleSheet来替换其样式表路径   
 Ext.ux.ThemeChange = Ext.extend(Ext.form.ComboBox,{   
    editable : false,   
    displayField : 'theme',   
    valueField : 'theme_',   
    typeAhead : true,   
    mode : 'local',   
    value : '默认',   
    readonly : true,   
    triggerAction : 'all',   
    selectOnFocus : true,   
    initComponent : function(){   
        var themes = [   
                ['默认', 'default','xtheme-brown.css','brown'],
                ['蓝色', 'blue','ext-all.css','blue'],
                ['浅灰色_蓝','gray_blue', 'xtheme-gray.css','blue'],
                ['浅灰色_黄','gray_brown', 'xtheme-gray.css','brown'],
                ['浅灰色_红','gray_red', 'xtheme-gray.css','red'],
                ['黑-黄','black_brown', 'xtheme-black.css','brown'],
                ['黑-红','black_red', 'xtheme-black.css','red'],
                ['粉-黄','pink_brown', 'xtheme-pink.css','brown'],
                ['粉-红','pink_red_', 'xtheme-pink.css','red'],
                ['红-白-红','red_white_red', 'xtheme-silverCherry.css','red'],
                ['红-白-黄','red_white_brown', 'xtheme-silverCherry.css','brown'],
                ['咖啡-黄','coff_brown', 'xtheme-chocolate.css','brown']
        ];   
        this.store = new Ext.data.SimpleStore({   
            fields : ['theme', 'theme_','extTheme','appTheme'],   
            data : themes   
        });   
    },   
    initEvents : function(){   
        this.on('collapse', function(){   
            //实际改变风格样式的处理   
            var index = this.store.find("theme_",this.getValue()); 
            var extTheme = this.store.getAt(index).get('extTheme');
            var appTheme = this.store.getAt(index).get('appTheme');
             Ext.util.CSS.swapStyleSheet('appTheme', 'script/theme/'+appTheme+'/style.css');  
            Ext.util.CSS.swapStyleSheet('extTheme', 'script/ext/resources/css/'+ extTheme); 
           
            var a = new Date();
			a.setDate(a.getDate() + 300);
			var url = "<%=request.getContextPath() %>";
			setCookie("extTheme", extTheme, a, url);
			setCookie("appTheme", appTheme, a, url);
        });
    }   
});   
Ext.reg('xthemeChange', Ext.ux.ThemeChange);   
Ext.onReady(function(){
		//个人区域
		var person = new Ext.tree.TreeNode({
		   text:"个人区域",
		   iconCls :'Icon1',
		   url:"",
		   expanded : true//默认展开根节点
		});
		var person_task = new Ext.tree.TreeNode({
			text:"待办任务<a href='workList.do?method=onMainView&cache=no'>&nbsp;[刷新]</a>",
			iconCls : 'Icon11',
			url:"<c:url value='/workList.do?method=onMainView' />",
			expanded : true
		});
		person.appendChild(person_task);
		
		var menu_person = new Ext.tree.TreePanel({
			rootVisible:false,     //隐藏根节点
			border : false,
			root : person,
			hrefTarget : 'mainContent',
			listeners : {
				click : function(node,e){
				    if(node.attributes.url != ""){
				    	 var contentFrame = document.getElementById('contentFrame');
						 contentFrame.src = node.attributes.url;  
				  	     mainPanel.setTitle(node.text);
					}
				}
			}
			
		});
		
		//经营分析
		var report = new Ext.tree.TreeNode({
		   text:"经营分析",
		   iconCls :'Icon1',
		   url:"",
		   expanded : true//默认展开根节点
		});
		var report_graph = new Ext.tree.TreeNode({
			text:"综合图表分析",
			iconCls : 'Icon11',
			url:"<c:url value='/portal/ChartAll.jsp' />",
			expanded : true
		});
		var report_invent =  new Ext.tree.TreeNode({
		   text:"财务报表分析",
		   iconCls :'Icon22',
		   url:"<c:url value='/portal/reportPaymentDetail.jsp' />",
		   expanded : true//默认展开根节点 	
		});
		var report_sell = new Ext.tree.TreeNode({
		   text:"销售报表分析",
		   iconCls :'Icon11',
		   url:"<c:url value='/portal/reportSellDetail.jsp' />",
		   expanded : true//默认展开根节点
		});
		var report_storage =  new Ext.tree.TreeNode({
		   text:"库存报表分析",
		   iconCls :'Icon22',
		   url:"<c:url value='/portal/reportStorageDetail.jsp' />",
		   expanded : true//默认展开根节点 	
		});
		report.appendChild(report_graph);
		report.appendChild(report_invent);
		report.appendChild(report_sell);
		report.appendChild(report_storage);
		var menu_report = new Ext.tree.TreePanel({
			rootVisible:false,     //隐藏根节点
			border : false,
			root : report,
			hrefTarget : 'mainContent',
			listeners : {
				click : function(node,e){
				    if(node.attributes.url != ""){
				    	 var contentFrame = document.getElementById('contentFrame');
						 contentFrame.src = node.attributes.url;  
				  	     mainPanel.setTitle(node.text);
					}
				}
			}
		});
		
		//仓库管理
		var store = new Ext.tree.TreeNode({
		   text:"仓库管理",
		   iconCls :'Icon2',
		   url:"",
		   expanded : true//默认展开根节点
		});
		var store_storage =  new Ext.tree.TreeNode({
		   text:"库容管理",
		   iconCls :'Icon22',
		   url:"<c:url value='/storage/storage.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var store_delivery =  new Ext.tree.TreeNode({
		   text:"配送管理",
		   iconCls :'Icon2',
		   url:"",
		   expanded : true//默认展开根节点 	
		});
		var delivery1 =  new Ext.tree.TreeNode({
		   text:"配送单管理",
		   iconCls :'Icon22',
		   url:"<c:url value='/storage/allocateCargo.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var delivery2 =  new Ext.tree.TreeNode({
		   text:"收货确认",
		   iconCls :'Icon22',
		   url:"<c:url value='/client/client.do?method=onClienttReceipt' />",
		   expanded : true//默认展开根节点 	
		});
		store_delivery.appendChild(delivery1);
		store_delivery.appendChild(delivery2);
		
		var store_damaged =  new Ext.tree.TreeNode({
		   text:"库内破损管理",
		   iconCls :'Icon22',
		   url:"<c:url value='/storage/storageDamaged.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var store_productLine =  new Ext.tree.TreeNode({
		   text:"生产管理",
		   iconCls :'Icon22',
		   url:"<c:url value='/storage/produceLine.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var store_adjustment =  new Ext.tree.TreeNode({
		   text:"仓库调账",
		   iconCls :'Icon22',
		   url:"<c:url value='/storage/adjustmentFrom.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		
		store.appendChild(store_storage);
		store.appendChild(store_delivery);
		store.appendChild(store_damaged);
		store.appendChild(store_productLine);
		store.appendChild(store_adjustment);
		
		var menu_store = new Ext.tree.TreePanel({
			rootVisible:false,     //隐藏根节点    
			border : false,
			root : store,
			hrefTarget : 'mainContent',
			listeners : {
				click : function(node,e){
				    if(node.attributes.url != ""){
				    	 var contentFrame = document.getElementById('contentFrame');
						 contentFrame.src = node.attributes.url;  
				  	     mainPanel.setTitle(node.text);
					}
				}
			}
		});
		
		//财务管理
		var invent = new Ext.tree.TreeNode({
		   text:"财务管理",
		   iconCls :'Icon2',
		   url:"",
		   expanded : true//默认展开根节点
		});
		var invent_payment =  new Ext.tree.TreeNode({
		   text:"应收账款",
		   iconCls :'Icon22',
		   url:"<c:url value='/finance/payment.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var invent_incentive =  new Ext.tree.TreeNode({
		   text:"差价投入",
		   iconCls :'Icon2',
		   url:"",
		   expanded : true//默认展开根节点 	
		});
		var incentive1 =  new Ext.tree.TreeNode({
		   text:"差价投入兑付记账",
		   iconCls :'Icon22',
		   url:"<c:url value='/finance/incentivePaymentCash.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var incentive2 =  new Ext.tree.TreeNode({
		   text:"转线下投入记账",
		   iconCls :'Icon22',
		   url:"<c:url value='/finance/zpayment.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		invent_incentive.appendChild(incentive1);
		invent_incentive.appendChild(incentive2);
		
		var invent_sellInvoice =  new Ext.tree.TreeNode({
		   text:"销售发票记账",
		   iconCls :'Icon22',
		   url:"<c:url value='/finance/sellinvoice.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var invent_managePayment =  new Ext.tree.TreeNode({
		   text:"标识费往来账",
		   iconCls :'Icon2',
		   url:"",
		   expanded : true//默认展开根节点 	
		});
		var managePayment1 =  new Ext.tree.TreeNode({
		   text:"标识管理收费记账",
		   iconCls :'Icon22',
		   url:"<c:url value='/finance/managePayment.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var managePayment2 =  new Ext.tree.TreeNode({
		   text:"标识使用费其它记账",
		   iconCls :'Icon22',
		   url:"<c:url value='/finance/otherPayment.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		invent_managePayment.appendChild(managePayment1);
		invent_managePayment.appendChild(managePayment2);
		
		
		var invent_margin =  new Ext.tree.TreeNode({
		   text:"保证金往来账",
		   iconCls :'Icon2',
		   url:"",
		   expanded : true//默认展开根节点 	
		});
		var margin1 =  new Ext.tree.TreeNode({
		   text:"收款记账",
		   iconCls :'Icon22',
		   url:"<c:url value='/finance/collectionPayment.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var margin2 =  new Ext.tree.TreeNode({
		   text:"退款记账",
		   iconCls :'Icon22',
		   url:"<c:url value='/finance/reimbursePayment.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		invent_margin.appendChild(margin1);
		invent_margin.appendChild(margin2);
		
		invent.appendChild(invent_payment);
		invent.appendChild(invent_incentive);
		invent.appendChild(invent_sellInvoice);
		invent.appendChild(invent_managePayment);
		invent.appendChild(invent_margin);
		
		var menu_invent = new Ext.tree.TreePanel({
			rootVisible:false,     //隐藏根节点    
			border : false,
			root : invent,
			hrefTarget : 'mainContent',
			listeners : {
				click : function(node,e){
				    if(node.attributes.url != ""){
				    	 var contentFrame = document.getElementById('contentFrame');
						 contentFrame.src = node.attributes.url;  
				  	     mainPanel.setTitle(node.text);
					}
				}
			}
		});
		
		//销售管理
		var sell = new Ext.tree.TreeNode({
		   text:"销售管理",
		   iconCls :'Icon2',
		   url:"",
		   expanded : true//默认展开根节点
		});
		var sell_indent =  new Ext.tree.TreeNode({
		   text:"订单列表",
		   iconCls :'Icon22',
		   url:"<c:url value='/sell/indent.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var sell_plan =  new Ext.tree.TreeNode({
		   text:"产销列表",
		   iconCls :'Icon22',
		   url:"<c:url value='/sell/plan.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var sell_receipt =  new Ext.tree.TreeNode({
		   text:"配送列表",
		   iconCls :'Icon22',
		   url:"<c:url value='/client/client.do?method=onClienttReceipt'/>",
		   expanded : true//默认展开根节点 	
		});
		var sell_outStorage =  new Ext.tree.TreeNode({
		   text:"出库列表",
		   iconCls :'Icon22',
		   url:"<c:url value='/storage/outStorageManage.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		sell.appendChild(sell_indent);
		sell.appendChild(sell_plan);
		//sell.appendChild(sell_receipt);
		sell.appendChild(sell_outStorage);
		
		var menu_sell = new Ext.tree.TreePanel({
			rootVisible:false,     //隐藏根节点    
			border : false,
			root : sell,
			hrefTarget : 'mainContent',
			listeners : {
				click : function(node,e){
				    if(node.attributes.url != ""){
				    	 var contentFrame = document.getElementById('contentFrame');
						 contentFrame.src = node.attributes.url;  
				  	     mainPanel.setTitle(node.text);
					}
				}
			}
		});
		
		//基础信息
		var base = new Ext.tree.TreeNode({
		   text:"基础信息",
		   iconCls :'Icon2',
		   url:"",
		   expanded : true//默认展开根节点
		});
		var base_security =  new Ext.tree.TreeNode({
		   text:"组织机构",
		   iconCls :'Icon22',
		   url:"<c:url value='/department/department.do?method=onDepartmentManageView' />",
		   expanded : true//默认展开根节点 	
		});
		var base_product1 =  new Ext.tree.TreeNode({
		   text:"产品信息管理",
		   iconCls :'Icon22',
		   url:"<c:url value='/products/product.do?method=onProductManageMain' />",
		   expanded : true//默认展开根节点 	
		});		
		var base_product =  new Ext.tree.TreeNode({
		   text:"产品信息",
		   iconCls :'Icon22',
		   url:"<c:url value='/products/product.do?method=onProductList' />",
		   expanded : true//默认展开根节点 	
		});
		var base_group =  new Ext.tree.TreeNode({
		   text:"分组信息",
		   iconCls :'Icon22',
		   url:"<c:url value='/products/group.do?method=onGCList' />",
		   expanded : true//默认展开根节点 	
		});
		var base_productC =  new Ext.tree.TreeNode({
		   text:"分类信息",
		   iconCls :'Icon22',
		   url:"<c:url value='/products/products.do?method=onPCList' />",
		   expanded : true//默认展开根节点 	
		});
		var base_ton =  new Ext.tree.TreeNode({
		   text:"吨数配置",
		   iconCls :'Icon22',
		   url:"<c:url value='/client/client.do?method=onClientTonsMain' />",
		   expanded : true//默认展开根节点 	
		});
		var base_tagPrice =  new Ext.tree.TreeNode({
		   text:"标识物料管理",
		   iconCls :'Icon2',
		   url:"",
		   expanded : true//默认展开根节点 	
		});
		var tagPrice1 =  new Ext.tree.TreeNode({
		   text:"标识物料入库",
		   iconCls :'Icon22',
		   url:"<c:url value='/storage/tagPriceInStorage.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		var tagPrice2 =  new Ext.tree.TreeNode({
		   text:"破损登记",
		   iconCls :'Icon22',
		   url:"<c:url value='/storage/tagPriceDamage.do?method=onMainView' />",
		   expanded : true//默认展开根节点 	
		});
		base_tagPrice.appendChild(tagPrice1);
		base_tagPrice.appendChild(tagPrice2);
		base.appendChild(base_security);
		base.appendChild(base_product1);
		base.appendChild(base_product);
		base.appendChild(base_group);
		base.appendChild(base_productC);
		base.appendChild(base_ton);
		base.appendChild(base_tagPrice);
		
		var menu_base = new Ext.tree.TreePanel({
			rootVisible:false,     //隐藏根节点    
			border : false,
			root : base,
			hrefTarget : 'mainContent',
			listeners : {
				click : function(node,e){
				    if(node.attributes.url != ""){
				    	 var contentFrame = document.getElementById('contentFrame');
						 contentFrame.src = node.attributes.url;  
				  	     mainPanel.setTitle(node.text);
					}
				}
			}
		});
		
		
		//系统管理
		var manage = new Ext.tree.TreeNode({
		   text:"系统管理",
		   iconCls :'Icon2',
		   url:"",
		   expanded : true//默认展开根节点
		});
		var manage_security =  new Ext.tree.TreeNode({
		   text:"组织机构",
		   iconCls :'Icon22',
		   url:"<portal:envget envkey='WEBSER_SECURITY3A'/>/rsinfo/dept/frameIndex.jsp",
		   expanded : true//默认展开根节点 	
		});
		var manage_role =  new Ext.tree.TreeNode({
		   text:"角色权限",
		   iconCls :'Icon22',
		   url:"<portal:envget envkey='WEBSER_SECURITY3A'/>/rsinfo/dept2/frameIndex.jsp",
		   expanded : true//默认展开根节点 	
		});
		var manage_import =  new Ext.tree.TreeNode({
		   text:"数据导入",
		   iconCls :'Icon22',
		   url:"<c:url value='/portal/import.jsp' />",
		   expanded : true//默认展开根节点 	
		});
		var manage_other =  new Ext.tree.TreeNode({
		   text:"其它管理",
		   iconCls :'Icon22',
		   url:"<c:url value='/portal/special.jsp' />",
		   expanded : true//默认展开根节点 	
		});
		manage.appendChild(manage_security);
		manage.appendChild(manage_role);
		manage.appendChild(manage_import);
		manage.appendChild(manage_other);
		
		var menu_manage = new Ext.tree.TreePanel({
			rootVisible:false,     //隐藏根节点    
			border : false,
			root : manage,
			hrefTarget : 'mainContent',
			listeners : {
				click : function(node,e){
				    if(node.attributes.url != ""){
				    	 var contentFrame = document.getElementById('contentFrame');
						 contentFrame.src = node.attributes.url;  
				  	     mainPanel.setTitle(node.text);
					}
				}
			}
		});
		
		
		new Ext.Viewport({
			title : 'Ext.Viewport示例',
			layout:'border',//表格布局
			items: [{
				/*title : '<table width="95%" height="6px;" border="0" cellpadding="0" cellspacing="0"><tr>' +
				           '<td width="*">' +
						   '<font color="#FF0000">当前时间</font>: <span id="currentTime"></span>' + 
						   '</td>' + 
						  '</tr></table>', */
				collapsible: true,
				contentEl: 'topDiv',
				region: 'north'
			},{
				region:'south',
				xtype:'panel',
				height:28,
				border:false,
				bbar:[{
						xtype:'xthemeChange'
					  },"-",{text:"修改密码"},"-",
					  {text:"退出"},"-"/*,{
					  text:"11111111111", iconCls:"btn-onlineUser",handler:function(){
					  		Ext.ux.Toast.msg("提示", "content中文");
						}
					  }*/, "->",
					 {xtype:"tbfill"},
					 {text:"任务栏", iconCls:"btn-logout"}
				]
			},{
				split:true,
				collapsible: true,
				region:'west',//指定子面板所在区域为west
				width: 180,
                minSize: 175,
                maxSize: 200,
                //collapsible: true,//窗口伸缩
                margins:'0 0 0 5',
                layout:'accordion',//窗口折叠
                baseCls:'itemStyle',
                layoutConfig:{
                	animate:true,
                	fill : true,
                 	border:false,
                    hideCollapseTool: true
                   },
                   items: [{
                        title:'<div class="LTitle">个人区域</div>',
                        items:menu_person
                    },{
                        title:'<div class="LTitle">经营分析</div>',
                        items:menu_report
                    },{
                    	title:'<div class="LTitle">仓库管理</div>',
                    	items:menu_store
                    },{
                    	title:'<div class="LTitle">财务管理</div>',
                    	items:menu_invent
                    },{
                    	title:'<div class="LTitle">销售管理</div>',
                    	items:menu_sell
                    },{
                        title:'<div class="LTitle">基础信息</div>',
                        items:menu_base
                    },{
                        title:'<div class="LTitle">系统管理</div>',
                        items:menu_manage
                    }]
			},{
				title: '系统信息',
				contentEl: 'aboutDiv',
				collapsible: true,
				id : 'mainContent',
				region:'center'//指定子面板所在区域为center
			}]
		});
		var mainPanel = Ext.getCmp('mainContent');
		//showCurrentTime();
});

function showCurrentTime() {//显示当前时间
	var now,n,y,r,h,m,s;
	now=new Date();
	n = now.getYear();
	y = now.getMonth()+1;
	r = now.getDate();
	h = now.getHours();
	m =now.getMinutes();
	s = now.getSeconds();
	if(y<10) y="0"+y;
	if(r<10) r="0"+r;
	if(h<10) h="0"+h;
	if(m<10) m="0"+m;
	if(s<10) s="0"+s;
	var currentTimeObj = Ext.get('currentTime').dom;
	
	currentTimeObj.innerHTML = n + "-" + y + "-" + r + " "+ h + ":" + m + ":" + s;
	setTimeout("showCurrentTime();", 1000);
}
   //Ext.ux.Toast.msg("提示", "content中文");
    
</script>
