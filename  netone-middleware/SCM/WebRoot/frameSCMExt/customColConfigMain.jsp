<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<title>自定义列配置</title>
	</head>
	<body>
		${form}
	</body>
</html>
<script type="text/javascript">
Ext.ns('Frame.Layout');

Frame.Layout.Viewport =  Ext.extend(Ext.Viewport, {
	 
     initComponent: function(){
      var clientHeight = 0;
      if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
		   clientHeight = document.documentElement.clientHeight;
      } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
		   clientHeight = document.body.clientHeight;
	  }
      var config = {
			border: true,
			layout:"border", 
			items: [
				{
				     id:'toolbar',
					 xtype:'toolbar',
					 region:"north",
					 cls:'',
					 items:[
						{
						  text:' 保 存 ',
						  id:'ext_b_edit',
						  iconCls:"editIcon",
						  handler: function (){
								_save();
						  }
						},
						{text:' 取 消 ',id:'ext_b_cancel',iconCls:'exitIcon',handler: function(){window.close();}}
						
                	]},
				  {
				     id: 'tabpanel',
					 xtype: 'tabpanel',
					 region:'center',
					 //autoDestroy:true,
					 autoScroll:true,
					 activeTab:0,
					 height:clientHeight-100
		    	  }]
		  
	}
	         
	Ext.apply(this, Ext.apply(this.initialConfig, config));
	Frame.Layout.Viewport.superclass.initComponent.apply(this, arguments);
   }
});

/**
* 添加选项
*　@param tabId　选项ID
* @param tabTitle 选项标题
* @param targetUrl　加载目标地址
*/
function addTab(tabId, tabTitle, contentEl){   
     var tab = Ext.getCmp('tabpanel').add({   
	     title: tabTitle,   
	     id:'tab_'+tabId,
	     autoScroll:true,
	     //iconCls: 'tabs',
	     contentEl: contentEl
     });
     return tab;
}

Ext.onReady(function() {
	var viewport =  new Frame.Layout.Viewport();
	${addtabscript}
});


function HTMLEncode(html) 
{ 
var temp = document.createElement ("div"); 
(temp.textContent != null) ? (temp.textContent = html) : (temp.innerText = html); 
var output = temp.innerHTML; 
temp = null; 
return output; 
} 

function $todo(thisObj,formcode,jsonStr){
	var deliter = '';
	if (jsonStr!='')  deliter = ',';
	var column1,column2,column3,column4,column5,column6,column7,column8,column9,column10;
	var column11,column12,column13,column14,column15,column16,column17,column18,column19,column20;
	var column21,column22,column23,column24,column25,column26,column27,column28,column29,column30;
	var column31,column32,column33,column34,column35,column36,column37,column38,column39,column40;
	var column41,column42,column43,column44,column45,column46,column47,column48,column49,column50;
 	
 	var len = 0;
	for(var i=1;i<=50;i++){
		var _o = thisObj.find('#column'+i);
		if (_o){
			var $val_ =  _o.val();
			if ($val_=='undefined') $val_=null;
			if ($val_!=null && $val_!=''){
				eval("column" + i + " = '"+HTMLEncode($val_)+"';");
			} else {
				$isnull = true;len=len+1;
			}
		} else {
			len=len+1;
		}
	}
	    
	 var w = {
		formcode : formcode,
		column1 : column1,column2 : column2,column3 : column3,column4 : column4,column5 : column5,
		column6 : column6,column7 : column7,column8 : column8,column9 : column9,column10 : column10,
		column11 : column11,column12 : column12,column13 : column13,column14 : column14,column15 : column15,
		column16 : column16,column17 : column17,column18 : column18,column19 : column19,column20 : column20,
		column21 : column21,column22 : column22,column23 : column23,column24 : column24,column25 : column25,
		column26 : column26,column27 : column27,column28 : column28,column29 : column29,column30 : column30,
		column31 : column31,column32 : column32,column33 : column33,column34 : column34,column35 : column35,
		column36 : column36,column37 : column37,column38 : column38,column39 : column39,column40 : column40,
		column41 : column41,column42 : column42,column43 : column43,column44 : column44,column45 : column45,
		column46 : column46,column47 : column47,column48 : column48,column49 : column49,column50 : column50
	};
	var json___ = Ext.util.JSON.encode(w);
	if (json___.indexOf('column')>0 && len!=50){
		jsonStr += deliter + json___;
	    deliter = ',';
	}
	return jsonStr;
}

function _save(){
		var msgTip = Ext.MessageBox.show({
	        title: '提示',
	        width: 250,
	        closable:false,
	        msg: '正在保存信息请稍候......'
	    });
	    
	    var $isnull = false;
	     var jsonStr = '';
	    $("body").find("table").each(function(){   
	    	  var formcode = $(this).attr('id');
			  $(this).each(function(){jsonStr=$todo($(this),formcode,jsonStr);});
		});
	    /**
	     * - 保存信息及明细
	     */
	    jsonStr = '[' + jsonStr + ']';
	    Ext.Ajax.request({
	        url: "<c:url value='/frame.do?method=savecolconfig&formcode=${param.formcode}' />",
	        // 请求的服务器地址
	        // 指定要提交的表单id
	        method: 'POST',
	        sync: true,
	        params: {
	            subform: jsonStr
	        },
	        success: function (response, options) {
	            msgTip.hide();
	            var result = Ext.util.JSON.decode(response.responseText);
	            var auditing_ = true;
	
	            if (result.error != null) {
	                Ext.MessageBox.alert('提示', result.tip);
	            } else {
	                Ext.ux.Toast.msg("", result.tip);
	            }
	        },
	        failure: function (response, options) {
	            msgTip.hide();
	            checkAjaxStatus(response);
	            var result = Ext.util.JSON.decode(response.responseText);
	            Ext.MessageBox.alert('提示', result.tip);
	        }
	    });
    
}

</script>
