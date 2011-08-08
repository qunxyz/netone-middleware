//树节点点击事件
function nodeAction(id, appid, ou, naturalname, parentdir, url,extendattribute) {

	var name = functree.getSelected().getText();
	opener.addSelectedOuteruse(name, url, ou, naturalname, parentdir, url, appid,extendattribute);
	window.close();
}
//页面提交
function submitform() {
	document.forms[0].submit();
}

