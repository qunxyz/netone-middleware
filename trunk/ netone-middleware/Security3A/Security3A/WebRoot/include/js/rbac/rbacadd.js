function rbacaddsave(){
	document.all.form1.action="rbac/rbacAdd.do?task=save";
	form1.submit();
}

function rbacmodsave(){
	form1.action="rbac/rbacModify.do?task=save";
	form1.submit();
}

function addSelectedRole(text , value){
	document.all.parentId.value = value;
	document.all.parentName.value = text ;
}

function submitadd(){
	if(document.all.naturalname.value==""){
		alert("��ɫ���Ʋ�����Ϊ��!");
		return;
	}
	if(document.all.name.value==""){
		alert("�������Ʋ�����Ϊ��!");
		return;
	}
	if(document.all.belonging_name.value==""){
		alert("��ɫ���಻����Ϊ��!");
		return;
	}
	//����Ȩ��
	setPermissions();
	//�����ɫ�û�
	setRoleUsers();
	document.all.form1.submit();
}

function submitmodify(){
	//����Ȩ��  jssq2.jsp
	setPermissions();
	//�����ɫ�û�  glyh.jsp
	setRoleUsers();
	document.all.form1.submit();
}

function belongadd(){
	window.open("../SelectSvl?appname=SYSROLE.SYSROLE&pagename=deptlist",'��֯ѡ��','width=400,height=350,resizable=yes,left=250,top=100,status=yes');
}

function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
	document.all.belonging.value = naturalname ;
	document.all.belonging_name.value = name;
}

function openuserselect(appname){
	window.open('../MSelectSvl?pagename=humanlist&appname='+appname,'��Աѡ��','width=500,height=350,resizable=yes,left=250,top=100,status=yes');
}

function addSelectedUser3(options){
	if(options!=null){
		var b = true;
		for(var i=0;i<options.length;i++){
			var userlength = document.all.select_roleusers.length;
			for(var j=0;j<userlength;j++){
				if(options[i].value==document.all.select_roleusers.options[j].value){
					b=false;
					alert("��Ҫ�����ͬ���û�!");
					break;
				}
			}
		}
		if(b){
			for(var i=0;i<options.length;i++){
				var op = new Option(options[i].text,options[i].value);
				document.all.select_roleusers.add(op);
			}
		}
	}
}

function roleselect(appname){
	window.open('../SSelectSvl?pagename=rolelist&appname='+appname,'��ɫѡ��','width=700,height=350,resizable=yes,left=150,top=100,status=yes')
}

function setRoleUsers(){
	var ops = document.all.select_roleusers.options ;
	var roleusers = "";
	for(var i=0 ; i<ops.length ; i++){
		roleusers += ops[i].value + ",";
	}
	document.all.roleusers.value = roleusers;
}

function selectdrop(){
	var s = document.all.select_roleusers;
	var i = s.selectedIndex;
	while(i != -1){
		s.remove(i);
		i = s.selectedIndex;
	}
}