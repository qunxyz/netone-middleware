<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
    <title>“≥øÚ∫·–Õ‘§¿¿2</title>
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
    
    Ext.onReady(function() {
	
	var menu = new Ext.menu.Menu({
        id: 'mainMenu',
        style: {
            overflow: 'visible'     // For the Combo popup
        },
        items: 
        [
            {
                text: 'Radio Options',
                menu: {        // <-- submenu by nested config object
                    items: [
                        // stick any markup in a menu
                        '<b class="menu-title">Choose a Theme</b>',
                        {
                            text: 'Aero Glass',
                            checked: true,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: 'Vista Black',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: 'Gray Theme',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }, {
                            text: 'Default Theme',
                            checked: false,
                            group: 'theme',
                            checkHandler: onItemCheck
                        }
                    ]
                }
            },
            <c:forEach items="${childrenlist}" var="getCol" varStatus="status">
            {
                text: '${getCol.name}'
                //iconCls: 'calendar'
                //menu: dateMenu // <-- submenu by reference
            },
            </c:forEach>
            ''
        ]
    });
		
	new Ext.Viewport
	({
		layout: 'border',
		items: 
		[{
			id:'toolbar',
			region: 'north',
			// collapsible: true,
			//title: 'Navigation',
			xtype: 'toolbar',
			height: 30,
			margins:'0 0 0 0',
			border:true,
			//autoScroll: true,
			items:
			[
				<c:forEach items="${childrenlist}" var="getCol" varStatus="status">
				{
            		text:'${getCol.name}'
            		//iconCls: 'bmenu',  // <-- icon
            		//menu: menu${status.index+1}  // assign menu by instance
            		<c:forEach items="${map}" var="map">
						<c:if test="${map.key == getCol.naturalname}">
            			,menu: 
            			{
                    		items: 
                    		[
                    			<c:forEach items="${map.value}" var="list">
                        		{
                            		text: '${list.name}'
                            		//,checked: true,
                            		//group: 'theme',
                            		//,checkHandler: onItemCheck
                            		,handler:myclick
                        		}, 
                        		</c:forEach>
                        		''
                    		]
                		}
            			</c:if>
					</c:forEach>
    			}, 
    			'-', 
    			</c:forEach>
    			''
			]
    	}, 
    	{
        	region: 'center',
        	id:'mytab',
        	xtype: 'panel',
        		
        	//resizeTabs:true,
        	//minTabWidth: 115,
        	//tabWidth:135,
        	//enableTabScroll:true,
        	//width:600,
        	//height:250,
        	//defaults: {autoScroll:true},
        	//plugins: new Ext.ux.TabCloseMenu()
    	}]
    }); 
	
	var tb = new Ext.Toolbar();
    tb.render('toolbar');
	tb.add({
            text:'Button w/ Menu',
            iconCls: 'bmenu',  // <-- icon
            menu: menu  // assign menu by instance
    });
	
    function onItemCheck(item, checked){
    	alert(33);
        //Ext.example.msg('Item Check', 'You {1} the "{0}" menu item.', item.text, checked ? 'checked' : 'unchecked');
        Ext.Msg.alert('Navigation Click', 'You clicked: 123');
    }
    
    function myclick( item , e ) 
    {
    	Ext.getCmp('myhide');
    	//alert(123);
    }
    
	//Ext.getCmp('myhide').remove() ;
    });
    </script>
</head>
<body>
</body>
</html>
