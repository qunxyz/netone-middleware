function humanadd(){
	window.open("humanAdd.do?task=add","useradd","width=700,height=450,resizable=yes,left=150,status=yes");
}

function humanmodify(){
	var k = 0;
	var value;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				value = form1.elements[i].value;
				k++;
			}
		}
	}
	if(k==0){
		alert("请先选中需要修改的选项");
		return;
	}
	if(k>1){
		alert("只能选择单项进行修改");
		return;
	}
	window.open("humanModify.do?task=modify&description="+value,"usermod","width=700,height=450,resizable=yes,left=150,status=yes,scrollbars=auto")
}

function humandel(){
	var k = 0;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				k = 1;
				break;
			}
		}
	}
	if(k==0){
		alert("请先选中需要删除的选项");
		return;
	} else {
		if(confirm("您确认执行删除操作？")){
			document.all.task.value="";
			form1.action="humanDel.do";
			form1.submit();
		}
	}
}

function humancopy(){
	var k = 0;
	var value;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				value = form1.elements[i].value;
				k++;
			}
		}
	}
	if(k==0){
		alert("请先选中需要复制的选项");
		return;
	}
	if(k>1){
		alert("只能选择单项进行复制");
		return;
	}
	window.open("humanCopy.do?task=copy&description="+value,"usercopy","width=700,height=450,resizable=yes,left=150,status=yes,scrollbars=auto");
}

function humanfileinput(){
	window.open("human/humanfile.jsp?id=","create",'width=600,height=100,resizable=yes,left=150,status=yes,scrollbars=auto');
}

function humanfileoutput(){
	var k = 0;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				k = 1;
				break;
			}
		}
	}
	if(k==0){
		if(confirm("未选择用户,是否导出当前所有查询出的用户?")){
			document.all.task.value="output";
			form1.action="humanIndex.do";
			form1.submit();
		} else {
			return;
		}
	} else {
		if(confirm("是否导出当前所选中的用户?")){
			document.all.task.value="output";
			form1.action="humanFile.do";
			form1.submit();
		}
	}
	document.all.task.value="";
}

function doSearch(){
	document.all.task.value="search";
	if(document.all.sname.value=="" && document.all.sdepartment.value==""){
		document.all.id.value="";
	}
	form1.action="humanIndex.do";
	document.forms[0].submit();
}

function link(value){
	window.open("humanModify.do?task=modify&description="+value,"usermod","width=700,height=450,resizable=yes,left=150,status=yes,scrollbars=auto")
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

function humanpass(){
	var k = 0;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				k = 1;
				break;
			}
		}
	}
	if(k==0){
		alert("请先选中需要密码重置的选项");
		return;
	} else {
		if(confirm("是否要重置密码")){
			document.all.task.value="passreset";
			form1.action="humanDel.do";
			form1.submit();
			document.all.task.value="";
		}
	}
}

function humanforbitx(){
	var k = 0;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				k = 1;
				break;
			}
		}
	}
	if(k==0){
		alert("请先选中需要禁止的选项");
		return;
	} else {
		if(confirm("是否要禁止帐户")){
			document.all.task.value="passreset";
			form1.action="humanDel.do?passConfus=yes";
			form1.submit();
			document.all.task.value="";
		}
	}
}

function deptselect(){
	window.open("SelectSvl?pagename=deptlist&appname=DEPT",'组织选择','width=400,height=450,resizable=yes,left=250,top=100,status=yes,scrollbars=auto');
}

function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
	document.all.sdepartment.value = name;
	document.all.sdepartment2.value = id;
}
