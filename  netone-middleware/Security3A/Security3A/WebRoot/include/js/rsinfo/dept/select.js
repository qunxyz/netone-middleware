function nodeAction(id,ou,naturalname,parentdir,url){

	var func = document.all.func.value;
	if (func == "humandept") {
		var name = functree.getSelected().getText();
		self.parent.opener.addSelectedDept(name, id, url);
		self.parent.close();
	} else if (func == "movedept") {
		var deptid = document.all.deptid.value;
		opener.doMove(deptid, id);
		self.parent.close();
	}
}
function submitform() {
	document.forms[0].submit();
}