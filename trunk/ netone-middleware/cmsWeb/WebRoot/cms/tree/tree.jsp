<%@ page contentType="text/html; charset=GBK"%>

<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>test.jsp</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="<%=path%>/include/js/xtree.js"></script>
		<script type="text/javascript" src="<%=path%>/include/js/xloadtree.js"></script>
		<link type="text/css" rel="stylesheet"
			href="<%=path%>/include/css/xtree2.css" />
		<link href="<%=path%>/cms/include/css/css.css" rel="stylesheet"
			type="text/css">

		<style>
body {
font-family: "宋体";
font-size: 12px;
margin-left: 0px;
margin-top: 10px;
margin-right: 0px;
margin-bottom: 0px;
}
/*定义菜单方框的样式1*/
.skin0 {
position:absolute;
padding-top:4px;
text-align:left;
width:100px; /*宽度，可以根据实际的菜单项目名称的长度进行适当地调整*/
border:1px solid black;
background-color:menu; /*菜单的背景颜色方案，这里选择了系统默认的菜单颜色*/
font-family:"宋体";
line-height:20px;
cursor:default;
visibility:hidden; /*初始时，设置为不可见*/
}
/*定义菜单方框的样式2*/
.skin1 {
padding-top:4px;
cursor:default;
font:menutext;
position:absolute;
text-align:left;
font-family: "宋体";
font-size: 10pt;
width:100px; /*宽度，可以根据实际的菜单项目名称的长度进行适当地调整*/
background-color:menu; /*菜单的背景颜色方案，这里选择了系统默认的菜单颜色*/
border:1 solid buttonface;
visibility:hidden; /*初始时，设置为不可见*/
border:2 outset buttonhighlight;
}

/*定义菜单条的显示样式*/
.menuitems {
padding:2px 1px 2px 10px;
}
-->
</style>
		<script language="javascript">
<!--
//定义菜单显示的外观，可以从上面定义的2种格式中选择其一
var menuskin = "skin0";
//是否在浏览器窗口的状态行中显示菜单项目条对应的链接字符串
var display_url = 0;

function showmenuie5() {
//获取当前鼠标右键按下后的位置，据此定义菜单显示的位置
var rightedge = document.body.clientWidth-event.clientX;
var bottomedge = document.body.clientHeight-event.clientY;

//如果从鼠标位置到窗口右边的空间小于菜单的宽度，就定位菜单的左坐标（Left）为当前鼠标位置向左一个菜单宽度
if (rightedge <ie5menu.offsetWidth)
ie5menu.style.left = document.body.scrollLeft + event.clientX - ie5menu.offsetWidth;
else
//否则，就定位菜单的左坐标为当前鼠标位置
ie5menu.style.left = document.body.scrollLeft + event.clientX;

//如果从鼠标位置到窗口下边的空间小于菜单的高度，就定位菜单的上坐标（Top）为当前鼠标位置向上一个菜单高度
if (bottomedge <ie5menu.offsetHeight)
ie5menu.style.top = document.body.scrollTop + event.clientY - ie5menu.offsetHeight;
else
//否则，就定位菜单的上坐标为当前鼠标位置
ie5menu.style.top = document.body.scrollTop + event.clientY;

//设置菜单可见
ie5menu.style.visibility = "visible";
return false;
}
function hidemenuie5() {
//隐藏菜单
//很简单，设置visibility为hidden就OK！
ie5menu.style.visibility = "hidden";

tree.setSelected(false);
}

function highlightie5() {
//高亮度鼠标经过的菜单条项目

//如果鼠标经过的对象是menuitems，就重新设置背景色与前景色
//event.srcElement.className表示事件来自对象的名称，必须首先判断这个值，这很重要！
if (event.srcElement.className == "menuitems") {
event.srcElement.style.backgroundColor = "highlight";
event.srcElement.style.color = "white";

//将链接信息显示到状态行
//event.srcElement.url表示事件来自对象表示的链接URL
if (display_url)
window.status = event.srcElement.url;
}
}

function lowlightie5() {
//恢复菜单条项目的正常显示

if (event.srcElement.className == "menuitems") {
event.srcElement.style.backgroundColor = "";
event.srcElement.style.color = "black";
window.status = "";
}
}

//右键下拉菜单功能跳转
function jumptoie5() {
//转到新的链接位置
var seltext=window.document.selection.createRange().text

if (event.srcElement.className == "menuitems") {
//如果存在打开链接的目标窗口，就在那个窗口中打开链接
if (event.srcElement.getAttribute("target") != null){
window.open(event.srcElement.url, event.srcElement.getAttribute("target"));
}
else
//否则，在当前窗口打开链接
window.location = event.srcElement.url;
}
}
//-->

function insertNode(){
//新增
var node = tree.getSelected();
  if(!node){
   alert("请选择一个节点");
   return ;
 }else{
  var action=node.action;
  var src = node.src;
  
   if(src){
     if(src.indexOf('insertFlag')>0){           
           window.open('/cmsWeb/cms/infogroup/infoGroupNew.jsp','mainFrame','');
            return ;
     }else{
        alert('不能新增此节点');
     }
   }else{
         window.open('/cmsWeb/infocellnew.do','mainFrame','');
         return ;
   }
 }
}

//编辑
function editNode(){
var node = tree.getSelected();
  if(!node){
   alert("请选择一个节点");
   return ;
 }else{
 var  action=node.action;
 var  src = node.src;
   if(src){
     if(src.indexOf('editFlag')>0){    
          var id=src.split('id=')[1].split('&')[0];
           window.open('/cmsWeb/infogroupedit.do?id='+id,'mainFrame','');
            return ;
     }else{
        alert('不能编辑此节点');
     }
   }else {
     var id=action.split('id=')[1].split('&')[0];
     window.open('/cmsWeb/infocelledit.do?id='+id,'mainFrame','');
     return ;

 }
}
}


function deleteNode(){

var node = tree.getSelected();
  if(!node){
   alert("请选择一个节点");
   return ;
 }else{
  var action=node.action;
  var src = node.src;
  if(src){
     if(src.indexOf('deleteFlag')>0){
          var id=src.split('id=')[1].split('&')[0];
          window.open('/cmsWeb/infogroupdelete.do?id='+id,'_blank','height=100, width=400, top=200, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
          return ;
     }else{
        alert('不能删除此节点');
     }
   }else{
     var id=action.split('id=')[1].split('&')[0];
     window.open('/cmsWeb/infocelldelete.do?id='+id,'_blank','height=100, width=400, top=200, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');

 }
}
}
</script>
	</head>
	<body bgcolor='#E2E2E2'>
		<input type="button" class="butt" value=" 刷新树 "
			onclick="window.open('/cmsWeb/cms/tree/tree.jsp','_self','')" />
		<input type="button" class="butt" value=" 查询元素 "
			onclick="window.open('/cmsWeb/infocelllist.do?intime=on','_blank','height=600, width=800, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')" />

		<script type="text/javascript">
		var tree = new WebFXLoadTree("PORTALET资源","<%=path%>/servlet/TreeGroup?grouptype=root");		
		tree.write();
		tree.setExpanded(true);
	</script>

		<div onselectstart="return false" id="ie5menu" class="skin0"
			onMouseover="highlightie5()" onMouseout="lowlightie5()"
			onClick="jumptoie5();">
			<div class="menuitems" url="javascript:insertNode();">
				新增
			</div>
			<div class="menuitems" url="javascript:editNode();">
				修改
			</div>
			<div class="menuitems" url="javascript:deleteNode();">
				删除
			</div>
		</div>
	</body>

	<script language="JavaScript1.2">
//如果当前浏览器是Internet Explorer，document.all就返回真
if (document.all && window.print) {

//选择菜单方块的显示样式
ie5menu.className = menuskin;

//重定向鼠标右键事件的处理过程为自定义程序showmenuie5
document.oncontextmenu = showmenuie5;

//重定向鼠标左键事件的处理过程为自定义程序hidemenuie5
document.body.onclick = hidemenuie5;
}
</script>
</html>
