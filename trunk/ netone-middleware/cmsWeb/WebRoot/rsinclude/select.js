//���ڵ����¼�
function nodeAction(id, appid, ou, naturalname, parentdir, url) {
	var name = functree.getSelected().getText();
	opener.addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid);
	window.close();
}
//ҳ���ύ
function submitform() {
	document.forms[0].submit();
}

