<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Oesee NetOne Ext Portal</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="">
	<meta http-equiv="description" content="This is dds page">
	
	<link rel="stylesheet" type="text/css" href="include/ext/resources/css/ext-all.css" />
	
	<!-- ExtJs Themes -->
	<link rel="stylesheet" type="text/css" href="include/ext/resources/css/xtheme-calista.css" disabled="disabled" />
	<link rel="stylesheet" type="text/css" href="include/ext/resources/css/xtheme-gray.css" disabled="disabled" />
	<link rel="stylesheet" type="text/css" href="include/ext/resources/css/xtheme-green.css" disabled="disabled" />
	
	<link rel="stylesheet" type="text/css" href="include/ext/ux/css/Portal.css" disabled="disabled" />
	
	
	<!-- ExtJs required -->
	<script type="text/javascript" src="include/ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="include/ext/ext-all.js"></script>
	
	
	<!-- ExtJs widgets
    <script type="text/javascript" src="include/ext/ux/Portal.js"></script>
    <script type="text/javascript" src="include/ext/ux/PortalColumn.js"></script>
    <script type="text/javascript" src="include/ext/ux/Portlet.js"></script> -->
    
    <!-- DDS required
	<script type="text/javascript" src="jsfactory.js?op=extportal"></script> -->
	
  </head>
  
  <body>
  <script type="text/javascript">
  Ext.onReady(function(){
	
	<c:if test="${not empty theme}">
	Ext.util.CSS.swapStyleSheet("theme", "include/ext/resources/css/${theme}.css");
	</c:if>
    // NOTE: This is an example showing simple state management. During development,
    // it is generally best to disable state management as dynamically-generated ids
    // can change across page loads, leading to unpredictable results.  The developer
    // should ensure that stable state ids are set for stateful components in real apps.
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    // create some portlet tools using built in Ext tool ids
    var tools = 
    [
    	/*
    	{
        	id:'gear',
        	qtip:'设置',
        	handler: function(){ Ext.Msg.alert('消息', '设置工具已被点击。'); }
    	},
    	{
	    	id: "refresh", 
			handler: function(event, toolEl, p)
			{ 
				//p.getUpdater().update({url: p.get(url), scripts: true}); 
				//var iFrame = Ext.getCmp(p.getId()).getEl().dom;
				//alert(iFrame);
				p.getUpdater().refresh(); 
				//p.doLayout();
				//p.load();
			} 
    	},*/
    	
    	{
			id : 'maximize',
			handler : myMaximize
		},
    	{
        	id:'close',
       		handler: function(e, target, panel){ panel.ownerCt.remove(panel, true); }
		}
    ];

    var viewport = new Ext.Viewport
    ({
    	id:'myViewport',
        layout:'anchor',
        autoScroll: true,
        //width : 1024,
        //height : 1000,
        //anchorSize:{width:1024,height:1000},
        items:
        [{
        	id:'outBox',
        	width:904,
        	//height:100,
        	border:false,
        	items:
        	[{
				id:'inBox',
		        //xtype:'portal',
		        //width:'${widthlist}',
		        border:false,
		        items:
		        [ 
				{
					id:'title1',
					title:'集团终端审批流程',
					html:"<iframe src='<portal:envget envkey='WEBSER_APPFRAME'/>/WorkListSvl?appname=APPFRAME.APPFRAME.NDYD&ope=y&limit=5&status=01' width='100%' height='100%' frameborder='0' scrolling='no'/>",
					//autoLoad:'',
					tools:tools,
					autoScroll: true,
					collapsible : true,
					script:true,
					width:450,
					height:220,
					style:'padding-top:4px;float:left' 
				}, 
				{
					id:'title2',
					title:'T2',
					html:'',
					tools:tools,
					autoScroll: true,
					collapsible : true,
					script:true,
					width:450,
					height:220,
					style:'padding-left:4px;padding-top:4px;float:left' 
				},
				{
					id:'title3',
					title:'T3',
					html:'',
					tools:tools,
					autoScroll: true,
					collapsible : true,
					script:true,
					width:450,
					height:220,
					style:'padding-top:4px;float:left' 
				},
				{
					id:'title4',
					title:'T4',
					html:'',
					tools:tools,
					autoScroll: true,
					collapsible : true,
					script:true,
					width:450,
					height:220,
					style:'padding-left:4px;padding-top:4px;float:left' 
				}
				]
			}]
		}]
    });
    
    function myMaximize(event, toolEl, panel) 
    {   
    	// originalOwnerCt 组件自身的父Container，类型Ext类
		panel.originalOwnerCt = panel.ownerCt;   
		panel.originalPosition = panel.ownerCt.items.indexOf(panel);   
		panel.originalSize = panel.getSize();   
		
		// 如果toolEl没有window这个自定义属性（里面存一个window面板）
		if (!toolEl.window) 
		{   
			var defaultConfig = 
			{  
				//title:panel.title,
				//x:0,
				//y:0,
				id : (panel.getId() + '-MAX'),   
				width : (Ext.getBody().getSize().width),   
				height : (Ext.getBody().getSize().height),
				maximizable :false,   
				resizable : true,   
				draggable : true,   
				closable : true,   
				closeAction : 'hide',   
				hideBorders : true,   
				plain : true,   
				layout : 'fit',   
				autoScroll : true,   
				border : false,   
				bodyBorder : false,   
				frame : true,   
				pinned : true,   
				bodyStyle : 'background-color: #ffffff;'  
			};   
			
			toolEl.window = new Ext.Window(defaultConfig);
			//当被隐藏时执行 
			toolEl.window.on('hide', myMinimize, panel);
		} 
		
		// 如果没有dummyComponent
		if (!panel.dummyComponent) 
		{   
			var dummyCompConfig = 
			{
				width : panel.getSize().width,   
				height : panel.getSize().height,   
				html : '&nbsp;'  
			};   
			panel.dummyComponent = new Ext.Panel(dummyCompConfig);   
		}   
		
		// 把复制的panel加入到临时的window面板
		//panel.setTitle('');
		toolEl.window.add(panel);
		
		// 去掉原panel的标题栏工具
		if (panel.tools['toggle'])   
			panel.tools['toggle'].setVisible(false);   
		if (panel.tools['close'])   
			panel.tools['close'].setVisible(false);   
		panel.tools['maximize'].setVisible(false);   

		panel.originalOwnerCt.insert(panel.originalPosition,panel.dummyComponent);   
		panel.originalOwnerCt.doLayout();  // 重新计算容器的布局尺寸  
		panel.dummyComponent.setSize(panel.originalSize);   
		panel.dummyComponent.setVisible(true);
		panel.dummyComponent.getEl().mask('it is maximized');   
		toolEl.window.show(this);   
	};
	
	function myMinimize(window) 
	{
        this.dummyComponent.getEl().unmask();
        this.dummyComponent.setVisible(false);
        this.originalOwnerCt.insert(this.originalPosition, this);
        this.originalOwnerCt.doLayout();
        //this.originalOwnerCt.getUpdater().refresh();
        this.setSize(this.originalSize);
        this.tools['maximize'].setVisible(true);
        if (this.tools['toggle'])
            this.tools['toggle'].setVisible(true);
        if (this.tools['close'])
            this.tools['close'].setVisible(true);
    }
	
	//alert(Ext.getCmp('myViewport').getWidth( ));
	//Ext.getCmp('myViewport').setWidth(300);
	//alert(Ext.getCmp('myViewport').getWidth( ));

	//var DD${cell.cellid} = new Ext.dd.DDProxy('${cell.cellid}');
	//DD${cell.cellid}.setXConstraint(1000,1000,50); // 限定按格子移动
	//new Ext.dd.DragSource('${cell.cellid}',{group:'dd'});
	//new Ext.dd.DDTarget('${cell.cellid}','dd');
});
  </script>
  </body>
</html>
