function humanmodifysave(){
	if(form1.name.value==""){
		alert("姓名不能为空");
		form1.name.focus();
		return;
	}
	if(form1.naturalname.value==""){
		alert("姓名拼音不能为空");
		form1.naturalname.focus();
		return;
	}
	if(form1.phoneNO.value==""){
		alert("移动电话不能为空,请选择移动电话");
		form1.phoneNO.focus();
		return;
	}
	if(form1.email.value==""){
		alert("电子邮件不能为空,请选择电子邮件");
		form1.email.focus();
		return;
	}
	if(trimSpacing(document.all.phoneNO.value)!=""){
		if(!isMobiTel(document.all.phoneNO.value)){
			alert("对不起，移动电话输入有误！");
			document.all.phoneNO.focus();
			return;
		}
	}
	if(trimSpacing(document.all.email.value)!=""){
		if(!checkEmail(document.all.email.value)){
			alert("对不起，电子邮件输入有误！");
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