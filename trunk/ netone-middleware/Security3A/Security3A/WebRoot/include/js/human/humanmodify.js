function humanmodifysave(){
	if(form1.name.value==""){
		alert("��������Ϊ��");
		form1.name.focus();
		return;
	}
	if(form1.naturalname.value==""){
		alert("����ƴ������Ϊ��");
		form1.naturalname.focus();
		return;
	}
	if(form1.phoneNO.value==""){
		alert("�ƶ��绰����Ϊ��,��ѡ���ƶ��绰");
		form1.phoneNO.focus();
		return;
	}
	if(form1.email.value==""){
		alert("�����ʼ�����Ϊ��,��ѡ������ʼ�");
		form1.email.focus();
		return;
	}
	if(trimSpacing(document.all.phoneNO.value)!=""){
		if(!isMobiTel(document.all.phoneNO.value)){
			alert("�Բ����ƶ��绰��������");
			document.all.phoneNO.focus();
			return;
		}
	}
	if(trimSpacing(document.all.email.value)!=""){
		if(!checkEmail(document.all.email.value)){
			alert("�Բ��𣬵����ʼ���������");
			document.all.email.focus();
			return;
		}
	}
	var role = document.all.humanrole;
	var rolevalue = "";
	for(var i=0 ; i<role.length ; i++){
		rolevalue += role.options[i].value + "@" ;
	}
	document.all.roles.value = rolevalue ;
	//var group = document.all.humangroup;
	//var groupvalue = "";
	//for(var i=0 ; i<group.length ; i++){
	//	groupvalue += group.options[i].value + "@" ;
	//}
	//document.all.groups.value = groupvalue ;
	form1.action="humanModify.do?task=save";
	form1.submit();
}