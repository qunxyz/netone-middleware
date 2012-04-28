<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<html>
<head>
    <title>页框纵型预览2</title>
    <link rel="stylesheet" type="text/css" href="include/ext/resources/css/ext-all.css"/>
    <link rel="stylesheet" type="text/css" href="include/ext/resources/css/xtheme-green.css"/>
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
    //读取cookies中的的css样式
    var cookiesArr=document.cookie.split(";"); 
    var css; 
    for(var i=0;i<cookiesArr.length;i++) 
    { 
         var arr=cookiesArr[i].split("="); 
         if(arr[0]=="css") 
         { 
            css=arr[1];
            break; 
         } 
    } 
    document.getElementsByTagName("link")[1].href="include/ext/resources/css/"+css;
    
    //从center中创建面板 
    function addTab(title,task)
    {
    	Ext.getCmp('mytab').add({
            title: title,
            iconCls: 'tabs',
            html: '<iframe src="frames.do?task='+task+'" width="100%" height=800"></iframe>',
            closable:true
        }).show();
    }
	
    Ext.onReady(function() {
	Ext.BLANK_IMAGE_URL = 'include/ext/resources/images/default/s.gif'; // 防止s.gif图片出错
	//导航面板-左边菜单
    var leftPanel = new Ext.Panel(
    {
   		title : '左边菜单',
        region: 'west',
		collapsible: true,
		xtype: 'treepanel',
		width: 230,
		margins:'0 0 0 4',
		autoScroll: true,
		split: true,
        layout:'accordion',
        minSize : 10, 
        maxSize : 300,
        plugins: new Ext.ux.TabCloseMenu(),
        listeners:{'beforerender':function(){
        Ext.Ajax.request({  
	        url : 'extframes.do?ajax=1&node=1&listPath=${param.listPath}',
	        success : function(response) {
        	var json  = eval(response.responseText)
        	for(var i=0;i<json.length;i++){
		    	var tmp = new Ext.tree.TreePanel({   
			        title:json[i].text,
			        region : 'tmpicon',
			        //id : 'tmpid',
					iconCls:"icon-accordion",
					collapsible: true,
					xtype: 'treepanel',
					width: 210,
					margins:'0 0 0 4',
					autoScroll: true,
					plugins: new Ext.ux.TabCloseMenu(),
					split: true,
			        root : new Ext.tree.AsyncTreeNode({id:json[i].id,text:json[i].text}),
			        loader : new Ext.tree.TreeLoader({
			        	dataUrl : 'extframes.do?ajax=1&listPath=${param.listPath}'
			        }),
			        rootVisible: false,// 隐藏根 
			        listeners: {
		           		click: function(n) {
			           		if(n.leaf){
	                 			addTab(n.attributes.text,n.attributes.id);
				            }else{}
		           		}
	       			}
	    		});
	    		this.add(tmp);
				this.doLayout(true);
		    }
        },  
        scope:this,
        failure : function(request) {
            Ext.MessageBox.show({  
                        title : '操作提示',  
                        msg : "连接服务器失败",  
                        buttons : Ext.MessageBox.OK,  
                        icon : Ext.MessageBox.ERROR  
                    });  
        },  
        method : 'post'  
    	});
       }},
         bbar : [{  
         xtype: 'combo',
         store: new Ext.data.SimpleStore({
             fields: ['value_id','text'],
             data: [['ext-all.css','默认色'],['xtheme-green.css','绿色'],['xtheme-calista.css','浅绿VS黄色'],['xtheme-midnight.css','夜色'],['xtheme-gray.css','灰白色'],['xtheme-indigo.css','银白色'],['xtheme-pink.css','粉红色'],['xtheme-slickness.css','黑色']]
         }),
         width : 160,  
         emptyText: '请选择系统样式',
         mode: 'local',
         valueField: 'value_id',
         displayField: 'text',
         triggerAction: 'all',
         id : 'com',
         listeners : {  
             "select":function(){
					var name = Ext.getCmp('com').getValue();
					var date=new Date(); 
       				date.setTime(date.getTime()+30*24*3066*1000); 
       				document.getElementsByTagName("link")[1].href="include/ext/resources/css/"+name;
       				document.cookie="css="+name+";expires="+date.toGMTString();//设置 cookies
				} 
              }  
         }]
    });
	
	var tabs = new Ext.TabPanel({
	    renderTo: Ext.getBody(),
	    activeTab: 0,
	    id:'mytab',
	    border: false,
		resizeTabs:true,
        minTabWidth: 115,
        tabWidth:135,
        tabHeight:250,
        enableTabScroll:true,
	    items: [{
	        title: '首页',
	        html:'<iframe id="Actionurltext" name="iframe" scrolling="auto" frameborder="0" width="100%" height=800" ></iframe>'
	    }]
	});
	new Ext.Viewport
	({		
		layout: 'border',
		items: 
		[{
			region:'north',
			autoScroll : true,
        	height:80,
        	html: '<span id="Dptiontext"></span>',
        	plugins: new Ext.ux.TabCloseMenu()
		},
		leftPanel
		,{
        	region: 'center',
        	plugins: new Ext.ux.TabCloseMenu(),
        	deferredRender:false,
        	items:[tabs]
    	}]
    }); 

	//Ext.getCmp('myhide').remove() ;
    });
	Ext.Ajax.request({  
       url : 'extframes.do?ajax=1&node=1&listPath=${param.listPath}',
       method : 'post',
       success : function(response) {
      	var jsons  = eval(response.responseText);
      	//存放描述中的内容
   		var myDption = jsons[0].Dption;
   		var myActionurl = jsons[0].Actionurl;
		var myDptiontext= Ext.get('Dptiontext').dom;
		myDptiontext.innerHTML = myDption;
		document.getElementById('Actionurltext').src="<c:url value='"+myActionurl+"'/>";
      	}
      });
      
     Ext.Ajax.request({  
       url : 'extframes.do?Dption=1&listPath=${param.listPath}',
       method : 'post',
       success : function(response) {
      	var jsons  = eval(response.responseText);
      	//存放描述中的内容
   		var myDption = jsons[0].Dption;
   		var myActionurl = jsons[0].Actionurl;
   		if(myDption=="1"){
   			alert("请正确的选择配置根目录");
   			myDption = "请正确的选择配置根目录";
   		}
		var myDptiontext= Ext.get('Dptiontext').dom;
		myDptiontext.innerHTML = myDption;
		document.getElementById('Actionurltext').src="<c:url value='"+myActionurl+"'/>";
      	}
      });
    </script>
</head>
<body>

</body>
</html>
