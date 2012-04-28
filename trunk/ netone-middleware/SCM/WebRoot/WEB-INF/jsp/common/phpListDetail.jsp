<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%@ include file="../common/metaJQueryTable.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	String scriptsURL = request.getContextPath() + "/script";
			
%>
<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
<script type="text/javascript"> 
		 Ext.onReady(function(){   
		    var total = '${param.total}';
		    var naturalname ='${param.naturalname}';
		    var type='${param.type}';
		    var did='${param.did}';
            var store = new Ext.data.Store({   
                proxy: new Ext.data.HttpProxy({   
                    url:"workList.do?method=phpListDetail&total="+total+"&naturalname="+naturalname+"&type="+type+"&did="+did,
                    method:"post"   
                }),   
                reader: new Ext.data.JsonReader({//读取json数据   
                    root:'newsList',                        
                    totalProperty:'totalProperty',  //总记录数   
                    id:'id'                    
                    },[{name:'naturalname1'},   
                        {name:'naturalname2'},   
                        {name:'workcode'},   
                        {name:'formtitle'},   
                        {name:'actname'},
                        {name:'commitername'},
                        {name:'commiter'} 
                    ])                     
            });            
            //创建列   
            var column = new Ext.grid.ColumnModel([                                                                   //复选框   
                {header:'流程名称',dataIndex:'naturalname1'},   
                {header:'工单主题',dataIndex:'naturalname2'},   
                {header:'创建时间',dataIndex:'workcode'}, 
                {header:'当前节点',dataIndex:'formtitle'},   
                {header:'当前处理人',dataIndex:'actname'},   
                {header:'总历时（小时/天数）',dataIndex:'commitername'},  
                {header:'待办历时（小时/天数）',dataIndex:'commiter'}               
            ]);   
            column.defaultSortable = true;//默认可排序   
            //创建一个工具条   
            var tba = new Ext.Toolbar();   
                       
            //面板   
            var grid = new Ext.grid.GridPanel({   
                el:'showNews',   
                width:800,   
                height:600, 
                title:'工单详细信息',   
                store:store,
                cm:column,                          //创建的列   
                trackMouseOver:false,   
                autoScroll: true,  
                autoWidth:true,
                loadMask: {msg:'正在加载数据，请稍侯……'},                 
                //下边   
                bbar:new Ext.PagingToolbar({   
                    pageSize:2,   
                    store:store,   
                    displayInfo:true,   
                    displayMsg:'显示第 {0} 条到 {1} 条记录，一共 {2} 条',   
                    emptyMsg:'没有记录'   
                })   
            });                
            grid.render();             
            store.load({params:{start:0,limit:20}});                       
        });   
     </script>  
		
	</script> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>工单详细信息</title>
	</head>
	<body>	
		<div id="showNews" style="margin-left: 240px"></div>
	</body>
</html>