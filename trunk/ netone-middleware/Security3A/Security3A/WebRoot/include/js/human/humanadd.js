var preTabId = null;
function changeTab(tab){
	if(preTabId == null){
		preTabId = "tab1";
	}
	
	var preTab = document.getElementById(preTabId)

	preTab.childNodes[0].style.display="none";	
	preTab.childNodes[1].style.display="block";
	
	tab.childNodes[0].style.display="block";	
	tab.childNodes[1].style.display="none";
	
	var prediv = document.getElementById("div_"+preTabId);
	var div = document.getElementById("div_"+ tab.id);
	
	prediv.style.display = "none";
	div.style.display = "block";
	
	preTabId = tab.id ;
	if(tab.id=="tab3"){
		document.all.tablebutton.style.display="none";
	} else {
		document.all.tablebutton.style.display="";
	}
}

function humanaddsave(){
	if(form1.name.value==""){
		alert("��������Ϊ��");
		form1.name.focus();
		return;
	}
	var naturalname=form1.naturalname.value;
	if(naturalname==""){
		alert("�����ʺŲ���Ϊ��");
		form1.naturalname.focus();
		return;
	}
	
	var actionurl=form1.actionurl.value;
	
   //�ɼ����������

	if(form1.faxNO.value==""){
		alert("���Ų���Ϊ��,��ѡ����");
		form1.faxNO.focus();
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
	setroles();
	form1.action="humanAdd.do?task=save";
	form1.submit();
}

function humanroleadd(appname){
	window.open('MSelectSvl?pagename=rolelist&appname='+appname,'��ɫѡ��','width=700,height=350,resizable=yes,left=150,top=100,status=yes')
}

function humanroledrop(){
	var s = document.all.humanrole;
	var i = s.selectedIndex;
	while(i != -1){
		s.remove(i);
		i = s.selectedIndex;
	}
}

function humangroupadd(){
	window.open("SelectSvl?pagename=deptlist&appname=DEPT",'��֯ѡ��','width=400,height=350,resizable=yes,left=250,top=100,status=yes');
}

function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
	document.all.faxNO.value = name;
	document.all.newhiddenid.value = id;
}

function addSelectedDuty(text,value){
	document.all.company.value = text;
}

function addSelectedRole2(options){
	if(options!=null){
		var b = true;
		for(var i=0;i<options.length;i++){
			var rolelength = document.all.humanrole.length;
			for(var j=0;j<rolelength;j++){
				if(options[i].value==document.all.humanrole.options[j].value){
					b=false;
					alert("��Ҫ�����ͬ�Ľ�ɫ!");
					break;
				}
			}
		}
		if(b){
			for(var i=0;i<options.length;i++){
				var op = new Option(options[i].text,options[i].value);
				document.all.humanrole.add(op);
			}
		}
	}
}

function setroles(){
	var role = document.all.humanrole;
	var value = "";
	for(var i=0 ; i<role.length ; i++){
		value += role.options[i].value + "@" ;
	}
	document.all.roles.value = value ;
}

function setgroups(){
	var group = document.all.humangroup;
	var value = "";
	for(var i=0 ; i<group.length ; i++){
		value += group.options[i].value + "@" ;
	}
	document.all.groups.value = value ;
}
