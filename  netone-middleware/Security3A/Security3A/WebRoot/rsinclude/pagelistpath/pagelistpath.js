
//·������
function link(id) {
	window.location.href = "PagelistpathRightSvl?id=" + id+"&appid="+document.all.appid.value+"&pagename="+document.all.pagename.value;
}

//�����inclusion���½�ҳ��
function newInclusion(){
	var id = document.all.id.value ;
	var appid = document.all.appid.value ;
	var ou = document.all.ou.value;
	window.open("PagelistAddSvl?inclusion=1&task=addx&id="+id+"&appid="+appid+"&ou="+ou+"&pagename="+document.all.pagename.value,"create",'width=600,height=400,resizable=yes,left=300,top=180,status=yes');
}
//�����½���Ŀ¼ҳ��
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
		alert("����ѡ��һ��Ŀ¼");
		return;
	}
	if(k>1){
		alert("ֻ��ѡ��һ��Ŀ¼");
		return;
	} 
	window.open("PagelistAddSvl?inclusion=1&task=addsub&chkid="+value+"&pagename="+document.all.pagename.value,"create",'width=600,height=400,resizable=yes,left=300,top=180,status=yes');

}

//��Ŀ¼����
function inclusionlink(id,ou){
	form1.action="PagelistpathRightSvl?id=" + id + "&ou=" + ou;
	form1.submit();
}
