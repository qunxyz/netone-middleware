function rbacsearch(){
	document.all.task.value = "search";
	document.all.form1.submit();
}

function rbacadd() {
	var appid = document.all.appid.value ;
	var source = document.all.source.value;
	window.open('rbacAdd.do?task=add&source='+source,'roleopen','width=500,height=650,resizable=yes,left=150,status=yes,scrollbars=auto');
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
	var belongingness = document.all.belongingness.value ;
	var source = document.all.source.value;
	window.open("rbacModify.do?task=modify&chkid="+value+"&source="+source,'roleopen','width=700,height=650,resizable=yes,left=150,status=yes,scrollbars=auto');
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
			document.all.task.value = "del";
			form1.action = "rbac/rbacDel.do?";
			form1.submit();
		}
	}
}

function close() {
	self.parent.close();
}

function link(value){
	var belongingness = document.all.belongingness.value ;
	var source = document.all.source.value;
	window.open("rbacModify.do?task=modify&chkid="+value+"&source="+source,'roleopen','width=700,height=650,resizable=yes,left=150,status=yes,scrollbars=auto');
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

function deptselect(){
	window.open("../SelectSvl?pagename=deptlist&appname=DEPT",'组织选择','width=400,height=450,resizable=yes,left=250,top=100,status=yes,scrollbars=auto');
}

function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
	document.all.sclass.value = name;
	document.all.sclass2.value = naturalname;
}