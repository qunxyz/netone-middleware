function rbacsearch(){
	document.all.task.value = "search";
	document.all.form1.submit();
}

function rbacadd() {
	var appid = document.all.appid.value ;
	var belongto = document.all.naturalname.value;
	window.open(document.all.jsppath.value+"/rbac/rbacAdd.do?task=add&belongto="+belongto,'roleopen','width=700,height=650,resizable=yes,left=150,status=yes,scrollbars=auto');
}

function rbacmodify() {
	var k = 0;
	var value;
	for (var i = 0; i < form1.elements.length; i++) {
		if (form1.elements[i].name == "chkid") {
			if (form1.elements[i].checked == true) {
				value = form1.elements[i].value;
				k++;
			}
		}
	}
	if (k == 0) {
		alert("请先选择一个角色！");
		return;
	}
	if (k > 1) {
		alert("只能选择一个角色！");
		return;
	}
	window.open(document.all.jsppath.value+"/rbac/rbacModify.do?task=modify&chkid="+value,'roleopen','width=700,height=650,resizable=yes,left=150,status=yes,scrollbars=auto');
}

function rbacdel() {
	var k = 0;
	for (var i = 0; i < form1.elements.length; i++) {
		if (form1.elements[i].name == "chkid") {
			if (form1.elements[i].checked == true) {
				k = 1;
				break;
			}
		}
	}
	if (k == 0) {
		alert("请先选择至少一个角色！");
		return;
	} else {
		if (confirm("您确认执行删除操作么？")) {
			form1.action = "rbac/rbacDel.do?task=del&task2=dept";
			form1.submit();
		}
	}
}

function close() {
	self.parent.close();
}

function link(value){
	window.open(document.all.jsppath.value+"/rbac/rbacModify.do?task=modify&chkid="+value,'roleopen','width=700,height=650,resizable=yes,left=150,status=yes,scrollbars=auto');
}

function docheck(){
	if(document.all.checkall.checked){
		var checkids = document.getElementsByTagName("input");
		for(var i=0 ; i<checkids.length ; i++){
			if(checkids[i].type=="checkbox"){
				if(checkids[i].checked){
					
				}else{
					checkids[i].checked=true;
				}
			}
		}
	}
	if(document.all.checkall.checked==false){
		var checkids = document.getElementsByTagName("input");
		for(var i=0 ; i<checkids.length ; i++){
			if(checkids[i].type=="checkbox"){
				if(checkids[i].checked){
					checkids[i].checked=false;
				}
			}
		}
	}
}