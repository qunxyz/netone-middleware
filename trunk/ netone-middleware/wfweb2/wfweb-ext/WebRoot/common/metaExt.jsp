<%
	String cssURL = request.getContextPath() + "/theme/styles/default";//报表
	String cssURL1 = request.getContextPath() + "/theme/erp/default";
	String iconcssURL = request.getContextPath() + "/theme";
	String scriptsURL = request.getContextPath() + "/theme";
	String imgURL = request.getContextPath() + "/images/default";
%>
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link rel="stylesheet" href="<%=iconcssURL%>/extCommon.css" type="text/css" />
<!--  -->
<link rel="stylesheet" href="<%=cssURL1%>/style.jsp" type="text/css" />

<!-- 普通样式 -->
<link rel="stylesheet" href="<%=cssURL%>/styles.css" type="text/css" />
<!-- ext样式 -->
<!-- 红色样式 -->
<link rel="stylesheet" type="text/css" href="<%=scriptsURL%>/ext/resources/css/ext-all.css">
<!-- 蓝色样式 
 <link rel="stylesheet" type="text/css" href="<%=scriptsURL%>/ext/resources/css/xtheme-blue.css"> -->

<script type="text/javascript" src="<%=scriptsURL%>/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=scriptsURL%>/ext/ext-all.js"></script>
<script type="text/javascript" src="<%=scriptsURL%>/ext/ext-lang-zh_CN.js"></script>

<script type="text/javascript">
<!--
Ext.BLANK_IMAGE_URL = '<%=scriptsURL%>/ext/resources/images/default/s.gif';//Load default image
//-->
</script>
<script type="text/javascript" src="<%=scriptsURL%>/window-op.js"></script>

<script type="text/javascript" src="<%=scriptsURL%>/ext/ComboBoxTree.js"></script>
<script type="text/javascript" src="<%=scriptsURL%>/ext/ext-extend.js"></script>

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
<!--
if  (!Ext.grid.GridView.prototype.templates) {   
    Ext.grid.GridView.prototype.templates = {};   
}   
Ext.grid.GridView.prototype.templates.cell =  new  Ext.Template(   
     '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} x-selectable {css}" style="{style}" tabIndex="0" {cellAttr}>' ,   
     '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>' ,   
     '</td>'   
);  
//-->
</script>



