
//��ʾ��ι���
function managerthis(id,type,model) {

	if(type=='path'){
		window.open("PagelistpathRightSvl?pagename="+model+"&appname="+id);
	}else{
		window.open("PagelistRightSvl?pagename="+model+"&appname="+id);
	}
}

		//��ʾ��
		function viewthis(id,model) {
			window.open("SelectSvl?appid="+id+"&pagename="+model,'_blank', 'height=380, width=600, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
		}
		//��ʾ��ѡ��
		function viewthismulti(id,model) {
			window.open("MSelectSvl?appid="+id+"&pagename="+model,'_blank', 'height=380, width=600, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
		}
		//��ʾ��ѡ��
		function viewthissingle(id,model) {
			window.open("SSelectSvl?appid="+id+"&pagename="+model,'_blank', 'height=380, width=500, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
		}

//�����޸�ҳ��
function edit(chkid){
	if(chkid){
		window.open("ApplistModifySvl?task=edit&chkid="+chkid+"&pagename="+document.all.pagename.value,"usermod","width=700,height=350,resizable=yes,left=150,status=yes");
	} else {
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
			alert("����ѡ����Ҫ�޸ĵ�ѡ��");
			return;
		}
		if(k>1){
			alert("ֻ��ѡ��������޸�");
			return;
		}
		window.open("ApplistModifySvl?task=edit&chkid="+value+"&pagename="+document.all.pagename.value,"usermod","width=700,height=350,resizable=yes,left=150,status=yes");
	}
}
//ִ��ɾ��
function del(chkid){
	form1.target='_blank';
	if(chkid){
		if(confirm("ȷ��ɾ����")){
			form1.action="ApplistDeleteSvl?task=delone&delid="+chkid;
			
			form1.submit();

		}
	} else {
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
			alert("����ѡ����Ҫɾ����ѡ��");
			return;
		} else {
			if(confirm("��ȷ��ִ��ɾ������ô��")){
				form1.action="ApplistDeleteSvl?task=del";
				form1.submit();
			}
		}
	}
	form1.target='_self';
}
//�½���ˢ��
function afterCreate(){
	form1.action="ApplistRightSvl";
	form1.submit();
}
//�޸ĺ�ˢ��
function afterEdit(){
	opener.form1.action="ApplistRightSvl";
	opener.form1.submit();
	window.close();
}
//�����½�ҳ��
function newElemnt(){
	window.open("ApplistAddSvl?pagename="+document.all.pagename.value,"create",'width=600,height=400,resizable=yes,left=300,top=180,status=yes');
}
//ִ���½�
function create(){
	if(document.all.naturalname.value=="" || document.all.name.value==""){
		alert("�������������Ʋ�����Ϊ��!");
		return;	
	}
	document.forms[0].submit(); 
}
