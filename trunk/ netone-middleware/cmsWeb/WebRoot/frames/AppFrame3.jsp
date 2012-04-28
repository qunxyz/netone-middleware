<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<html>
<head>
    <title>ҳ������Ԥ��2</title>
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
     * ����tab
     * @param title tab��ʾ�ı���
     * @param task �м����Դ�� Naturalname
     * 
     */
    //��ȡcookies�еĵ�css��ʽ
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
    
    //��center�д������ 
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
	Ext.BLANK_IMAGE_URL = 'include/ext/resources/images/default/s.gif'; // ��ֹs.gifͼƬ����
	//�������-��߲˵�
    var leftPanel = new Ext.Panel(
    {
   		title : '��߲˵�',
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
			        rootVisible: false,// ���ظ� 
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
                        title : '������ʾ',  
                        msg : "���ӷ�����ʧ��",  
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
             data: [['ext-all.css','Ĭ��ɫ'],['xtheme-green.css','��ɫ'],['xtheme-calista.css','ǳ��VS��ɫ'],['xtheme-midnight.css','ҹɫ'],['xtheme-gray.css','�Ұ�ɫ'],['xtheme-indigo.css','����ɫ'],['xtheme-pink.css','�ۺ�ɫ'],['xtheme-slickness.css','��ɫ']]
         }),
         width : 160,  
         emptyText: '��ѡ��ϵͳ��ʽ',
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
       				document.cookie="css="+name+";expires="+date.toGMTString();//���� cookies
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
	        title: '��ҳ',
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
      	//��������е�����
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
      	//��������е�����
   		var myDption = jsons[0].Dption;
   		var myActionurl = jsons[0].Actionurl;
   		if(myDption=="1"){
   			alert("����ȷ��ѡ�����ø�Ŀ¼");
   			myDption = "����ȷ��ѡ�����ø�Ŀ¼";
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
