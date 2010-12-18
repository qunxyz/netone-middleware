//树节点点击事件
function nodeAction(id, appid, ou, naturalname, parentdir, url) {
	var name = functree.getSelected().getText();
	opener.addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid);
	window.close();
}
//页面提交
function submitform() {
	document.forms[0].submit();
}

