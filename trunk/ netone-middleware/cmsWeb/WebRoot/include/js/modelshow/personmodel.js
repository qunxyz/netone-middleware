function makeurl(){
				   var modelid=$('selModel').value;
				   var url=$('pagetypeinfo').value;
				   $('actionurl').value=url+modelid;

}

function listStyle(){
window.open('/cmsWeb/PagelistpathRightSvl?pagename=stylelist&appname=CSSFILE');
}

function listTemplate(){
 window.open('/cmsWeb/SSelectSvl?pagename=pagegroup&appname=PORTALPG');
}

//显示所有JPP模板DEMO
function listModel() {
	var parser = new Ajax.Request("/cmsWeb/servlet/AjaxInfoModelSvl", {method:"get", parameters:"", asynchronous:false});
	var restr = parser.transport.responseText;
	var sHTML = restr;
	pagegroup.innerHTML = sHTML;
}

function editModel(){
   var modelid=$('selModel').value;
   if(modelid==0){
   	alert('请先选择一个页组');
   	return;
   }
   window.open('/cmsWeb/ds.do?model=ok&id='+modelid);
}

function createModel(){
   window.open('/cmsWeb/infomodelnew.do');
}

function delModel(){
var modelid=$('selModel').value;
  		if (confirm("您确定要删除该页组?")) {
		      window.open('/cmsWeb/infomodellist.do?flag=delete&id='+modelid);
		 }

}

function createpage(){
  window.open($('apptype').value);
}

function createtemplate(){
	window.open("/cmsWeb/cms/cellDIY/cellDIY.html");
}
function createResource(){
	window.open("/WebSpeci/ApplistRightSvl?pagename=applist");
}
