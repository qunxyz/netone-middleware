<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
    <title>页框纵型预览2</title>
    <link rel="stylesheet" type="text/css" href="include/ext/resources/css/ext-all.css"/>
    <script type="text/javascript" src="include/ext/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="include/ext/ext-all.js"></script>
    <script type="text/javascript" src="include/ext/ux/TabCloseMenu.js"></script>

    <style type="text/css">
        html, body {
            font: normal 12px verdana;
            margin: 0;
            padding: 0;
            border: 0 none;
            overflow: hidden;
            height: 100%;
        }
        .empty .x-panel-body {
            padding-top:20px;
            text-align:center;
            font-style:italic;
            color: gray;
            font-size:11px;
        }
    </style>
    <script type="text/javascript">
    
    /**
     * 打开新tab
     * @param title tab显示的标题
     * @param task 中间件资源的 Naturalname
     * 
     */
    function addTab(title,task)
    {
    	Ext.getCmp('mytab').add({
            title: title,
            iconCls: 'tabs',
            html: '<iframe src="frames.do?task='+task+'" width="100%" height="100%"></iframe>',
            closable:true
        }).show();
    }
    
    Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = 'include/ext/resources/images/default/s.gif'; // 防止s.gif图片出错
	
	var root = new Ext.tree.AsyncTreeNode({id:'1',text:'菜单'});
	var myTreeLoader = new Ext.tree.TreeLoader
	({    
        url: 'extframes.do?ajax=1&listPath=${param.listPath}'
    });  
		
	new Ext.Viewport
	({
		layout: 'border',
		items: 
		[{
			region: 'west',
			collapsible: true,
			title: 'Navigation',
			xtype: 'treepanel',
			width: 210,
			margins:'0 0 0 4',
			autoScroll: true,
			split: true,
        		
        	root: root,
        	loader: myTreeLoader,
        	
        	rootVisible: false, // 隐藏根
        	listeners: {
            	click: function(n) {
                	//Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
                	addTab(n.attributes.text,n.attributes.id);
            	}
        	}
    	}, 
    	{
        	region: 'center',
        	id:'mytab',
        	xtype: 'tabpanel',
        		
        	resizeTabs:true,
        	minTabWidth: 115,
        	tabWidth:135,
        	enableTabScroll:true,
        	//width:600,
        	//height:250,
        	defaults: {autoScroll:true},
        	plugins: new Ext.ux.TabCloseMenu()
    	}]
    }); 
	
	//Ext.getCmp('myhide').remove() ;
    });
    </script>
</head>
<body>
</body>
</html>
