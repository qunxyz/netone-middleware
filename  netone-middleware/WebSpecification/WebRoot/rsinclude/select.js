//树节点点击事件
function nodeAction(id, appid, ou, naturalname, parentdir, url) {
	var name = functree.getSelected().getText();
	window.open('PagelistpathRightSvl?pagename=pagelist&appname='+naturalname);
}
//页面提交
function submitform() {
	document.forms[0].submit();
}

