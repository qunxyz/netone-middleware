//���ڵ����¼�
function nodeAction(id, appid, ou, naturalname, parentdir, url,extendattribute) {

	var name = functree.getSelected().getText();
	opener.addSelectedOuteruse(name, url, ou, naturalname, parentdir, url, appid,extendattribute);
	window.close();
}
//ҳ���ύ
function submitform() {
	document.forms[0].submit();
}

