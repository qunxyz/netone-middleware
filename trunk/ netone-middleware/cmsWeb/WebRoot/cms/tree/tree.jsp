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
font-family: "����";
font-size: 12px;
margin-left: 0px;
margin-top: 10px;
margin-right: 0px;
margin-bottom: 0px;
}
/*����˵��������ʽ1*/
.skin0 {
position:absolute;
padding-top:4px;
text-align:left;
width:100px; /*��ȣ����Ը���ʵ�ʵĲ˵���Ŀ���Ƶĳ��Ƚ����ʵ��ص���*/
border:1px solid black;
background-color:menu; /*�˵��ı�����ɫ����������ѡ����ϵͳĬ�ϵĲ˵���ɫ*/
font-family:"����";
line-height:20px;
cursor:default;
visibility:hidden; /*��ʼʱ������Ϊ���ɼ�*/
}
/*����˵��������ʽ2*/
.skin1 {
padding-top:4px;
cursor:default;
font:menutext;
position:absolute;
text-align:left;
font-family: "����";
font-size: 10pt;
width:100px; /*��ȣ����Ը���ʵ�ʵĲ˵���Ŀ���Ƶĳ��Ƚ����ʵ��ص���*/
background-color:menu; /*�˵��ı�����ɫ����������ѡ����ϵͳĬ�ϵĲ˵���ɫ*/
border:1 solid buttonface;
visibility:hidden; /*��ʼʱ������Ϊ���ɼ�*/
border:2 outset buttonhighlight;
}

/*����˵�������ʾ��ʽ*/
.menuitems {
padding:2px 1px 2px 10px;
}
-->
</style>
		<script language="javascript">
<!--
//����˵���ʾ����ۣ����Դ����涨���2�ָ�ʽ��ѡ����һ
var menuskin = "skin0";
//�Ƿ�����������ڵ�״̬������ʾ�˵���Ŀ����Ӧ�������ַ���
var display_url = 0;

function showmenuie5() {
//��ȡ��ǰ����Ҽ����º��λ�ã��ݴ˶���˵���ʾ��λ��
var rightedge = document.body.clientWidth-event.clientX;
var bottomedge = document.body.clientHeight-event.clientY;

//��������λ�õ������ұߵĿռ�С�ڲ˵��Ŀ�ȣ��Ͷ�λ�˵��������꣨Left��Ϊ��ǰ���λ������һ���˵����
if (rightedge <ie5menu.offsetWidth)
ie5menu.style.left = document.body.scrollLeft + event.clientX - ie5menu.offsetWidth;
else
//���򣬾Ͷ�λ�˵���������Ϊ��ǰ���λ��
ie5menu.style.left = document.body.scrollLeft + event.clientX;

//��������λ�õ������±ߵĿռ�С�ڲ˵��ĸ߶ȣ��Ͷ�λ�˵��������꣨Top��Ϊ��ǰ���λ������һ���˵��߶�
if (bottomedge <ie5menu.offsetHeight)
ie5menu.style.top = document.body.scrollTop + event.clientY - ie5menu.offsetHeight;
else
//���򣬾Ͷ�λ�˵���������Ϊ��ǰ���λ��
ie5menu.style.top = document.body.scrollTop + event.clientY;

//���ò˵��ɼ�
ie5menu.style.visibility = "visible";
return false;
}
function hidemenuie5() {
//���ز˵�
//�ܼ򵥣�����visibilityΪhidden��OK��
ie5menu.style.visibility = "hidden";

tree.setSelected(false);
}

function highlightie5() {
//��������꾭���Ĳ˵�����Ŀ

//�����꾭���Ķ�����menuitems�����������ñ���ɫ��ǰ��ɫ
//event.srcElement.className��ʾ�¼����Զ�������ƣ����������ж����ֵ�������Ҫ��
if (event.srcElement.className == "menuitems") {
event.srcElement.style.backgroundColor = "highlight";
event.srcElement.style.color = "white";

//��������Ϣ��ʾ��״̬��
//event.srcElement.url��ʾ�¼����Զ����ʾ������URL
if (display_url)
window.status = event.srcElement.url;
}
}

function lowlightie5() {
//�ָ��˵�����Ŀ��������ʾ

if (event.srcElement.className == "menuitems") {
event.srcElement.style.backgroundColor = "";
event.srcElement.style.color = "black";
window.status = "";
}
}

//�Ҽ������˵�������ת
function jumptoie5() {
//ת���µ�����λ��
var seltext=window.document.selection.createRange().text

if (event.srcElement.className == "menuitems") {
//������ڴ����ӵ�Ŀ�괰�ڣ������Ǹ������д�����
if (event.srcElement.getAttribute("target") != null){
window.open(event.srcElement.url, event.srcElement.getAttribute("target"));
}
else
//�����ڵ�ǰ���ڴ�����
window.location = event.srcElement.url;
}
}
//-->

function insertNode(){
//����
var node = tree.getSelected();
  if(!node){
   alert("��ѡ��һ���ڵ�");
   return ;
 }else{
  var action=node.action;
  var src = node.src;
  
   if(src){
     if(src.indexOf('insertFlag')>0){           
           window.open('/cmsWeb/cms/infogroup/infoGroupNew.jsp','mainFrame','');
            return ;
     }else{
        alert('���������˽ڵ�');
     }
   }else{
         window.open('/cmsWeb/infocellnew.do','mainFrame','');
         return ;
   }
 }
}

//�༭
function editNode(){
var node = tree.getSelected();
  if(!node){
   alert("��ѡ��һ���ڵ�");
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
        alert('���ܱ༭�˽ڵ�');
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
   alert("��ѡ��һ���ڵ�");
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
        alert('����ɾ���˽ڵ�');
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
		<input type="button" class="butt" value=" ˢ���� "
			onclick="window.open('/cmsWeb/cms/tree/tree.jsp','_self','')" />
		<input type="button" class="butt" value=" ��ѯԪ�� "
			onclick="window.open('/cmsWeb/infocelllist.do?intime=on','_blank','height=600, width=800, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, status=no')" />

		<script type="text/javascript">
		var tree = new WebFXLoadTree("PORTALET��Դ","<%=path%>/servlet/TreeGroup?grouptype=root");		
		tree.write();
		tree.setExpanded(true);
	</script>

		<div onselectstart="return false" id="ie5menu" class="skin0"
			onMouseover="highlightie5()" onMouseout="lowlightie5()"
			onClick="jumptoie5();">
			<div class="menuitems" url="javascript:insertNode();">
				����
			</div>
			<div class="menuitems" url="javascript:editNode();">
				�޸�
			</div>
			<div class="menuitems" url="javascript:deleteNode();">
				ɾ��
			</div>
		</div>
	</body>

	<script language="JavaScript1.2">
//�����ǰ�������Internet Explorer��document.all�ͷ�����
if (document.all && window.print) {

//ѡ��˵��������ʾ��ʽ
ie5menu.className = menuskin;

//�ض�������Ҽ��¼��Ĵ������Ϊ�Զ������showmenuie5
document.oncontextmenu = showmenuie5;

//�ض����������¼��Ĵ������Ϊ�Զ������hidemenuie5
document.body.onclick = hidemenuie5;
}
</script>
</html>
