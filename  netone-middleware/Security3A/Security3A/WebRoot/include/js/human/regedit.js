
var mail = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
var chinese = /^.*[\u0391-\uFFE5]+.*$/;
function regedit() {
	var check = checkdo();
	if (1 == check) {
		this.document.forms[0].submit();
	}
}


function nocn(){
	for(i=0;i<document.getElementsByName("name")[0].value.length;i++){
		var c = document.getElementsByName("name")[0].value.substr(i,1);
		var ts = escape(c);
		if(ts.substring(0,2) == "%u"){
			document.getElementsByName("name")[0].value = "";
		}
	}
}


function checkEmail(str) {
    var pattern = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
    if (pattern.test(str)) {
        return true;
    } else {
        return false;
    }
}
        //////////�ύ/////////////////
function checkdo() {
	var loginname = this.document.forms[0].name.value;
	var email = this.document.forms[0].email.value;
	var truename = this.document.forms[0].name_zh.value;
	var checknum= this.document.forms[0].checknum.value;
	var deptName = this.document.forms[0].deptName.value;

	if (email == "") {
		alert("email������Ϊ��");
		return 0;
	}
	
	if(!checkEmail(email)){
		alert("�Բ��𣬵����ʼ���������");
		this.document.forms[0].email.value = '';
		return 0;
	}
	
	if (loginname == "") {
		alert("�������ʺ�");
		return 0;
	}
	if(truename == ""){
		alert("����������");
		return 0;
	}
	if(checknum ==""){
		alert("��������֤��");
		return 0;
	}
	if(deptName ==""){
		alert("��ѡ�����ڲ���");
		return 0;
	}
	return 1;
}

function deptselect(){
	window.open("../SelectSvl?pagename=deptlist&appname=DEPT",'��֯ѡ��','width=400,height=450,resizable=yes,left=250,top=100,status=yes,scrollbars=auto');
}

function dept(){
	window.open("../SelectSvl?pagename=deptlist2&appname=DEPT",'��֯ѡ��','width=400,height=450,resizable=yes,left=250,top=100,status=yes,scrollbars=auto');
}

function addSelectedOuteruse(name, id, ou, naturalname, parentdir, url, appid){
	document.all.deptName.value = name;
	document.all.deptId.value = id;
}