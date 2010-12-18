
//路径链接
function link(id) {
	window.location.href = "PagelistpathRightSvl?id=" + id+"&appid="+document.all.appid.value+"&pagename="+document.all.pagename.value;
}

//进入带inclusion的新建页面
function newInclusion(){
	var id = document.all.id.value ;
	var appid = document.all.appid.value ;
	var ou = document.all.ou.value;
	window.open("PagelistAddSvl?inclusion=1&task=addx&id="+id+"&appid="+appid+"&ou="+ou+"&pagename="+document.all.pagename.value,"create",'width=600,height=400,resizable=yes,left=300,top=180,status=yes');
}
//进入新建子目录页面
function newSubInclusion(){
	var k = 0;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				value = form1.elements[i].value;
				k++;
			}
		}
	}
	if(k==0){
		alert("请先选中一个目录");
		return;
	}
	if(k>1){
		alert("只能选择一个目录");
		return;
	} 
	window.open("PagelistAddSvl?inclusion=1&task=addsub&chkid="+value+"&pagename="+document.all.pagename.value,"create",'width=600,height=400,resizable=yes,left=300,top=180,status=yes');

}

//子目录链接
function inclusionlink(id,ou){
	form1.action="PagelistpathRightSvl?id=" + id + "&ou=" + ou;
	form1.submit();
}
