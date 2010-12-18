function nodeAction(id,appid,ou,naturalname,parentdir,url,ext){
	var func = document.all.func.value;
	var appid = document.all.application.value;
	if (func == "") {
		var name = functree.getSelected().getText();
		opener.addSelectedSystem(name, id, ou, naturalname, parentdir, url, appid);
		window.close();
	} else if(func=="jssq"){
		var name = functree.getSelected().getText();
	
		document.all.selectedNode.value = ou + "#" + name;
	}
}
function submitform() {
	document.forms[0].submit();
}