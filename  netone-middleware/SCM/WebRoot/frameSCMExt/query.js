var extconditions = "";
var querymenu = new Ext.menu.Menu({
					id : 'queryMenu',
					text : '查询',
					items : [{
						text : '普通查询',
						handler : function() {
							_query();
						}
					}, {
						text : '高级查询',
						handler : function() {
							_query2();
						}
					}
					]
				});

var $extqueryGrid = new Ext.grid.GridPanel({ 
viewConfig:{forceFit:false,onLayout : function(vw, vh){
	    var g = this.grid;
	    if(g.autoHeight){
	        /**原本Ext设置的是visible，不会出现滚动条*/
	        this.scroller.dom.style.overflow = 'auto';
	        /**另外还要更新表头宽度，以便滚动位置同步*/
	        if(this.innerHd){
	            this.innerHd.style.width = (vw)+'px';
	        }
	    }
	}
},
autoHeight:true,enableColumnMove:false,enableColumnHide:false,enableHdMenu:false,
sm:new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
columns:[
//new Ext.grid.RowNumberer(),
{header: '左括号', dataIndex: 'c1', sortable: false},
{header: '项目', dataIndex: 'c2', sortable: false},
{header: '条件', dataIndex: 'c3', sortable: false},
{header: '结果', dataIndex: 'c4', sortable: false},
{header: '右括号', dataIndex: 'c5', sortable: false},
{header: '连接条件', dataIndex: 'c6', sortable: false}
],
id:'extqueryGrid',
store:new Ext.data.SimpleStore({data:[getdata1()],fields:['c1','c2','c3','c4','c5','c6']})
});

var $extqueryGrid2 = new Ext.grid.GridPanel({ 
viewConfig:{forceFit:false,onLayout : function(vw, vh){
	    var g = this.grid;
	    if(g.autoHeight){
	        /**原本Ext设置的是visible，不会出现滚动条*/
	        this.scroller.dom.style.overflow = 'auto';
	        /**另外还要更新表头宽度，以便滚动位置同步*/
	        if(this.innerHd){
	            this.innerHd.style.width = (vw)+'px';
	        }
	    }
	}
},
autoHeight:true,enableColumnMove:false,enableColumnHide:true,enableHdMenu:false,
sm:new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
columns:[
//new Ext.grid.RowNumberer(),
new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	}),
{header: '左括号', dataIndex: 'c1', sortable: false},
{header: '项目', dataIndex: 'c2_show', sortable: false},
{header: '条件', dataIndex: 'c3', sortable: false},
{header: '结果', dataIndex: 'c4', sortable: false},
{header: '右括号', dataIndex: 'c5', sortable: false},
{header: '连接条件', dataIndex: 'c6', sortable: false}
],
id:'extqueryGrid2',
store:new Ext.data.SimpleStore({data:[],fields:['c1','c2','c2_show','c3','c4','c5','c6']})
});

var $extqueryGrid3 = new Ext.grid.GridPanel({ 
viewConfig:{forceFit:true,onLayout : function(vw, vh){
	    var g = this.grid;
	    if(g.autoHeight){
	        /**原本Ext设置的是visible，不会出现滚动条*/
	        this.scroller.dom.style.overflow = 'auto';
	        /**另外还要更新表头宽度，以便滚动位置同步*/
	        if(this.innerHd){
	            this.innerHd.style.width = (vw)+'px';
	        }
	    }
	}},
autoHeight:true,
sm:new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
columns:[
//new Ext.grid.RowNumberer(),
new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	}),
{header: '项目(←从左边选择)', dataIndex: 'column', sortable: true}
],
id:'extqueryGrid3',
store:new Ext.data.SimpleStore({data:getdata2(),fields:['column','columnid']})
});
$extqueryGrid3.on("celldblclick",function(grid,rowIndex,columnIndex,e)    {         
	$add_all();
});

var $extqueryGrid4 = new Ext.grid.GridPanel({ 
viewConfig:{forceFit:true,onLayout : function(vw, vh){
	    var g = this.grid;
	    if(g.autoHeight){
	        /**原本Ext设置的是visible，不会出现滚动条*/
	        this.scroller.dom.style.overflow = 'auto';
	        /**另外还要更新表头宽度，以便滚动位置同步*/
	        if(this.innerHd){
	            this.innerHd.style.width = (vw)+'px';
	        }
	    }
	}},
autoHeight:true,
sm:new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
columns:[
//new Ext.grid.RowNumberer(),
new Ext.grid.CheckboxSelectionModel({ //创建Grid表格组件
				singleSelect: false
		   	}),
{header: '项目(排序条件→)', dataIndex: 'column', sortable: true}
],
id:'extqueryGrid4',
store:new Ext.data.SimpleStore({data:[],fields:['column','columnid']})
});
$extqueryGrid4.on("celldblclick",function(grid,rowIndex,columnIndex,e)    {         
	$rem_all();
});

function _query(){
$('#queryform1').css('display','block');
var _querywindow = new Ext.Window({  
            title:"普通查询",  
            width:950,  
            height:300, 
            layout:"form",  
            allowDomMove: false,
            closeAction: 'hide',
            autoScroll:true,
            contentEl:'queryform1',
            buttonAlign:'center',
            modal:true,
            buttons:[  
                {  
                    text:"查询",  
                    handler:function(){  
                        refresh();
						_querywindow.hide();
                    }
                },{  
                    text:"关闭",  
                    handler:function(){  
                       _querywindow.hide();
                    }
                }
            ]  
        });
	_querywindow.show();
	_querywindow.center();
}

function _query2(){
var _querywindow = new Ext.Window({  
            title:"高级查询",  
            width:831,  
            height:450, 
            layout:'table',
            allowDomMove: false,
            resizable :false,
            closeAction: 'hide',
            layoutConfig: {
		        // The total column count must be specified here
		        columns: 1
		    },
            autoScroll:true,
            items:[
            {
            xtype:'panel',
            layout:'border',
            frame:true,
            width:800,
            height:80,
            items:[
             {
		        id:"ExtQueryX",
			    xtype:"panel",
			    items:[$extqueryGrid],
			    region:"center",
	            border:false,
	            hideBorders:true,
	            height : 80,
			 	autoScroll:true,
		        width:700
		    },new Ext.form.FieldSet({
		        	width:100,height : 80,
		        	region:"east",
		        	items:[{
			        xtype:'button',
			        text:' 添 加 ',
			        buttonAlign:'center',
			        handler:$addtogrid
		        }]
		      })
            ]
            
            },{
            xtype:'panel',
            layout:'border',
            width:800,
            frame:true,
            height:120,
            items:[
            {
		        id:"ExtQueryX2",
			    xtype:"panel",
			    items:[$extqueryGrid2],
			    region:"center",
	            border:false,
	            hideBorders:true,
	            height : 110,
			 	autoScroll:true,
		        width:700
		    },new Ext.form.FieldSet({
                title:'选择操作',
                region:"east",
	        	width:110,height : 150,
		        items:[{
		        	xtype:'button',
			        text:' 删 除 ',
			        buttonAlign:'center',
			        handler:$deleteFromgrid
		        }
		        ,
		        {
		        	xtype:'button',
			        text:' 确 定 ',
			        buttonAlign:'center',
			        handler:$enterCondition
		        }
		        ,
		        {
		        	xtype:'button',
			        text:' 退 出 ',
			        buttonAlign:'center',handler:function(){
			        	_querywindow.hide();
			        }
		        }]
               })
            ]
            }
            
		    ,
            {
            xtype:'panel',
            layout:'border',
            width:800,
            height:120,
            items:[
            
	             {
			        id:"ExtQueryX3",
				    xtype:"panel",
				    items:[$extqueryGrid3],
				    region:"west",
		            border:false,
		            hideBorders:true,
		            height : 100,
				 	autoScroll:true,
			        width:380
			    }
	            ,{
	            region:"center",
        		xtype : "panel",
        		border:false,
        		height : 100,
        		frame:true,
        		//width:40,
        		items : [{
	            	 xtype : "button",
				     iconCls : "add-allIcon",
				     buttonAlign :'center',
				     handler : $add_all
            	},{
            		 xtype : "button",
	            	 iconCls : "rem-allIcon",
				     buttonAlign :'center',
				     handler : $rem_all
            	}]
	            
	            }
			      ,{
			        id:"ExtQueryX4",
				    xtype:"panel",
				    items:[$extqueryGrid4],
				    region:"east",
		            border:false,
		            hideBorders:true,
		            height : 100,
				 	autoScroll:true,
			        width:380
			    }
            
            
            ]
            },new Ext.form.FieldSet({
		        	height : 80,
		        	title:'日期查询说明',
		        	html:'高级查询中的日期查询,都精确到几时几分几秒的,所以按日期条件查询时,需正确表达.<BR>比如:查询\'2005/06/01\'的数据时,必须表达成:\"大于等于2005/06/01 并且小于 2005/06/02\",<BR>意思为:1号00:00:00到2号00:00:00之间,如果直接用\"等于 2005/06/01\",只查出1号00:00:00的数据'
		        })
              
		         
		     ],
            buttonAlign:'center',
            modal:true
        });
	_querywindow.show();
	_querywindow.center();
}

function $addtogrid(){
	var n = Ext.getCmp("extqueryGrid2");
	var u = n.getStore();
	  var w = {
	   c1 : Ext.get('c1').getValue(),
	   c2 : Ext.get('c2').getValue(),
	   c2_show : $("#c2").find("option:selected").text(),
	   c3 : Ext.get('c3').getValue(),
	   c4 : Ext.get('c4').getValue(),
	   c5 : Ext.get('c5').getValue(),
	   c6 : Ext.get('c6').getValue()
	  };
	  var r = new u.recordType(w);
	  n.stopEditing();
	  u.add(r);
}

function $deleteFromgrid(){
 var p = Ext.getCmp("extqueryGrid2");
 var q = p.getSelectionModel().getSelections();
 var n = p.getStore();
 for ( var o = 0; o < q.length; o++) {
  p.stopEditing();
  n.remove(q[o]);
 }
}

function $add_all(){//添加
	var t = Ext.getCmp("extqueryGrid3");
	var n = Ext.getCmp("extqueryGrid4");
	var u = n.getStore();
	var x = t.getSelectionModel().getSelections();
	for ( var p = 0; p < x.length; p++) {
	 var q = x[p].data.column;
	 var v = x[p].data.columnid;
	 var s = false;
	 for ( var o = 0; o < u.getCount(); o++) {
	  if (u.getAt(o).data.columnid == v) {
	   s = true;
	   break;
	  }
	 }
	 if (!s) {
	  var w = {
	   column : q,columnid :v
	  };
	  var r = new u.recordType(w);
	  n.stopEditing();
	  u.add(r);
	 }
	}
}

function $rem_all(){//移除
 var p = Ext.getCmp("extqueryGrid4");
 var q = p.getSelectionModel().getSelections();
 var n = p.getStore();
 for ( var o = 0; o < q.length; o++) {
  p.stopEditing();
  n.remove(q[o]);
 }
}
/** getdata1,getdata2方法必须添加 */

function $enterCondition(){//确认条件

	var s1 = Ext.getCmp('extqueryGrid2').getStore();
	var s2 = Ext.getCmp('extqueryGrid4').getStore();
	var deliter1 = '';var deliter2='';
	var jsonStr1='';var jsonStr2='';
	for (var i = 0; i < s1.getCount(); i++) {
	  var rs = s1.getAt(i);
	  var jsonRecord = {
         c1: rs.get('c1'),
         c2: rs.get('c2'),
         c3: rs.get('c3'),
         c4: rs.get('c4'),
         c5: rs.get('c5'),
         c6: rs.get('c6')
      }
      jsonStr1 += deliter1 + Ext.util.JSON.encode(jsonRecord);
      deliter1 = ',';
	}
	for (var i = 0; i < s2.getCount(); i++) {
	  var rs = s2.getAt(i);
	  var jsonRecord = {
         ordercolumnid: rs.get('columnid')
      }
      jsonStr2 += deliter2 + Ext.util.JSON.encode(jsonRecord);
      deliter2 = ',';
	}
	var jsonStr = '[{"condition":[' + jsonStr1+']},{"order":['+jsonStr2 + ']}]';
	//alert(jsonStr);
	document.getElementById('extquery').value="1";
	extconditions=jsonStr;
	refresh();
}

