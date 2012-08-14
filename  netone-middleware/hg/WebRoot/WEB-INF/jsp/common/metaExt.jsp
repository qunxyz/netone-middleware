<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%
	String iconcssURL = request.getContextPath() + "/script";
	String scriptsURL = request.getContextPath() + "/script";
	String path = request.getContextPath();
%>
<!--<script type="text/javascript" src="<%=scriptsURL %>/btn.js"></script>
 提示 JS 标签 -->
<script language="javascript" type="text/javascript" src="<%=path%>/script/tipInfo.js"></script>
<!-- 普通样式 
<link rel="stylesheet" id="appTheme" href="<%=request.getContextPath()%>/script/theme/brown/style.css" type="text/css" />
-->
<!-- EXT 按钮图标 -->
<link rel="stylesheet" href="<%=iconcssURL%>/extCommon.css" type="text/css" />

<!-- ext样式 -->
<!-- 默认样式 必须 -->
<link rel="stylesheet" type="text/css" href="<%=scriptsURL%>/ext/resources/css/ext-all.css">
<!-- 通过样式 按钮 -->
<link rel="stylesheet" type="text/css" href="<%=scriptsURL%>/theme/main/common.css">

<!-- 褐色样式
<link rel="stylesheet" type="text/css" href="<%=scriptsURL%>/ext/resources/css/xtheme-brown.css">
-->
<!-- 通用样式
<link rel="stylesheet" type="text/css" href="<%=scriptsURL%>/ext/resources/css/xtheme-gray.css"> -->

<script type="text/javascript" src="<%=scriptsURL%>/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=scriptsURL%>/ext/ext-all.js"></script>
<script type="text/javascript" src="<%=scriptsURL%>/ext/ext-lang-zh_CN.js"></script>
<!-- JS 打开窗口 window -->
<script type="text/javascript" src="<%=scriptsURL%>/window-op.js"></script>
<script type="text/javascript" src="<%=scriptsURL%>/ext/ComboBoxTree.js"></script>
<script type="text/javascript" src="<%=scriptsURL%>/ext/ext-extend.js"></script>
<script type="text/javascript" src="<%=scriptsURL%>/ext/extend/Toast.js"></script>

<link rel="stylesheet" type="text/css" href="<%=scriptsURL%>/ext/extend/PagingTreeLoader.css">
<script type="text/javascript" src="<%=scriptsURL%>/ext/extend/PagingTreeLoader.js"></script>
<script type="text/javascript">
<!--
Ext.BLANK_IMAGE_URL = '<%=scriptsURL%>/ext/resources/images/default/s.gif';//Load default image
//-->
</script>
<!-- 
Ext.grid.GridPanel有一个重大缺陷，就是单元格的内容不能选中，没法选中就没法复制，给用户带来很多不便。
分析： 用IE Developer Toolbar打开ExtJs输出的代码研究了一下，发现每个单元格的div都有一个属性：unselectable="on"，看来是css在作怪。
-->
<style type= "text/css" >   
    .x-selectable, .x-selectable * {   
        -moz-user-select: text! important ;   
        -khtml-user-select: text! important ;   
    }   
</style>
<script type="text/javascript">
if  (!Ext.grid.GridView.prototype.templates) {   
    Ext.grid.GridView.prototype.templates = {};   
}   
Ext.grid.GridView.prototype.templates.cell =  new  Ext.Template(   
     '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>' ,   
     '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>' ,   
     '</td>'   
);  

Ext.override(Ext.grid.RowSelectionModel, {   
		onEditorKey : function(field, e) {   
			// alert('go');   
			var k = e.getKey(), newCell, g = this.grid, ed = g.activeEditor;   
			var shift = e.shiftKey;   
			if (k == e.ENTER) {   
				e.stopEvent();   
				ed.completeEdit();   
				if (shift) {   
					newCell = g.walkCells(ed.row, ed.col - 1, -1,   
							this.acceptsNav, this);   
				} else {   
					// alert('go');   
					newCell = g.walkCells(ed.row, ed.col + 1, 1,   
							this.acceptsNav, this);   
				}   
			} else if (k == e.TAB) {   
				e.stopEvent();   
				ed.completeEdit();   
				if (this.moveEditorOnEnter !== false) {   
					if (shift) {   
						newCell = g.walkCells(ed.row - 1, ed.col, -1,   
								this.acceptsNav, this);   
					} else {   
						// alert('go');   
						newCell = g.walkCells(ed.row + 1, ed.col, 1,   
								this.acceptsNav, this);   
					}   
				}   
			} else if (k == e.ESC) {   
				ed.cancelEdit();   
			}   
			if (newCell) {   
				g.startEditing(newCell[0], newCell[1]);   
			}   
		}   
});
</script>
<script type="text/javascript" src="<%=scriptsURL%>/AppUtil.js"></script>
<script type="text/javascript" src="<%=scriptsURL%>/AppChoiceUtil.js"></script>
<script type="text/javascript" src="<%=scriptsURL%>/AppChoiceUtil2.js"></script>
<script type="text/javascript">
Ext.Ajax.timeout = 900000;//900秒 解决AJAX超时问题 
function checkAjaxStatus(response){//检查AJAX超时状态
	if(response.status==0){
        Ext.Msg.alert('提示', '会话超时,请尝试重新刷新页面!');
        return;
    }
}
<!--
  ///Ext.onReady(function(){
   	  var extTheme=getCookie('extTheme');
   	  var appTheme=getCookie('appTheme');
   	  if(extTheme==null || extTheme==''){
	   	//extTheme='xtheme-brown.css';
	   	
	   	//blue
	   	extTheme='ext-all.css';
	   	
	   	//red
	   	//extTheme='xtheme-silverCherry.css';
   	  }
   	  if(appTheme==null || appTheme==''){
	   	 //appTheme='brown';
	   	 appTheme='blue';
	   	 //appTheme='red';
   	  } 
   	  Ext.util.CSS.swapStyleSheet('appTheme', '<%=scriptsURL%>/theme/main/'+appTheme+'/style.css'); 
      Ext.util.CSS.swapStyleSheet('extTheme', '<%=scriptsURL%>/ext/resources/css/'+ extTheme);
  /// });
//-->
</script>
