//���ڵ����¼�
function nodeAction(id, appid, ou, naturalname, parentdir, url) {
	var name = functree.getSelected().getText();
	opener.addselect(name+'['+naturalname+']');
	window.close();
}
//ҳ���ύ
function submitform() {
	document.forms[0].submit();
}

