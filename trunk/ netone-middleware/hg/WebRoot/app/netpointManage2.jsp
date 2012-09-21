<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.oesee.com/netone" prefix="rs"%>
<%@ taglib uri="http://www.oesee.com/netone/portal" prefix="portal"%>	
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
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
	</head>
	<body >
		<div id="conditiondiv">
		<input type="text" id="conditions" name="conditions" />
		<input type="button" value="搜索" onclick="reloadTree()" />
		</div>
	</body>
</html>
<script type="text/javascript">

Ext.ns('Buss.Layout');
var treeDataUrl = "<c:url value='/app.do?method=queryNetPoint' />";
var nodeid='0';
var nodename='网点';
var nodecode='0';
var parentnodeid='0';
Buss.Layout.Viewport =  Ext.extend(Ext.Viewport, {

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
		 layout:"border", 
		 
		 items:[{
		 			id:'treepanel',
		 			xtype:'treepanel',
		 			region:"west",
		 			width:180,
		 			style:"padding:2px",
		 			title:nodename,
		 			iconCls:"flow_chartIcon",
		 			split:true,
		 			collapsible: true,//伸缩
		 			//rootVisible:false,     //隐藏根节点
		 			split:true,
		 			animCollapse :false,
		 			animate :false,//去除动画
　　　				autoScroll:true,
					border : true, // 边框
					useArrows :true,
					plugins: new Ext.ux.tree.TreeNodeMouseoverPlugin(), //must use the plugin
					/**
					tbar:new Ext.Toolbar([
		       			    {xtype:'panel',contentEl:'conditiondiv'}
					]),
					**/
				    loader: new Ext.ux.tree.PagingTreeLoader({
				    	clearOnLoad :true,
				    	preloadChildren:false,
				     	//baseParams :{condition:document.getElementById('conditions').value},
				    	pageSize:25,enableTextPaging:true,dataUrl: treeDataUrl
				    }),
		       	 	root : new Ext.tree.AsyncTreeNode({
		       	 		id:nodeid,text:nodename
		       	 	}),
		       	 	listeners : {
						click : function(node,e){
							linkUrl(node.attributes.text,node.attributes.linkurl);
						},
						beforeload : function(loader, node) {
					       this.body.mask('加载中...');//tree为TreePanel对象 
					    },
					    load : function() {   
					       this.body.unmask();//tree为TreePanel对象   
					    } 
						
					}
              	},{
              		id:'_grid',
		            region:"center",
		            xtype:'panel',
					border:false,
					hideBorders:true,
	            	autoScroll:true,
	            	buttonAlign :'center',
	        		//contentEl:'content'
	        		html:'<iframe id="contentFrame" src="" style="width: 100%; height: 98%;" frameborder="0" ></iframe>'
			  }
		 	]
	}
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	Buss.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});

function linkUrl(title,url){
	if (url=='' || url=='undefined' || url==null){
		//not do
	} else {
		if (isie6()){
			var load = function(){document.getElementById("contentFrame").src=url;}
			setTimeout(load,10);
		} else {
			document.getElementById("contentFrame").src=url;
		}
	}
}
function isie6() {//ie6
    if ($.browser.msie) {
        if ($.browser.version == "6.0") return true;
    }
    return false;
}

function reloadTree(){//重新加载树
	//Ext.getCmp('treepanel').getLoader().dataUrl =treeDataUrl;
	//Ext.getCmp('treepanel').getLoader().baseParams ={condition:document.getElementById('conditions').value};
	//Ext.getCmp('treepanel').root.reload();
	
	 Ext.Ajax.request({
            url: treeDataUrl,
            params:{condition:document.getElementById('conditions').value},
            success: function(request) {
                var data = Ext.util.JSON.decode(request.responseText);
                
                 Ext.getCmp('treepanel').getRootNode().eachChild(function(a,b){
					
					Ext.getCmp('treepanel').getRootNode().removeChild(a);
				});
				
                Ext.getCmp('treepanel').getRootNode().appendChild(data);
               
				
            },
            failure: function() {
            }
        });
}

Ext.onReady(function(){
	
	var viewport =  new Buss.Layout.Viewport();
	
    //Ext.getCmp('treepanel').expandAll();//树默认全部展开
    Ext.getCmp('treepanel').getRootNode().expand();//树展开第一级
    Ext.getCmp('treepanel').getRootNode().select();
   
    
    
    
});
</script>