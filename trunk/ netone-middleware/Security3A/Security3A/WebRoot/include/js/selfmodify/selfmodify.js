
function exports(){
	document.all.task.value="output";
	form1.submit();
}

function modify(){
	document.all.task.value="modify";
	var truenamex = document.all.truenamex.value;
	var outemail = document.all.outemail.value;
	var oldpass = document.all.oldpass.value;
	var newpass = document.all.newpass.value;
	var pass = document.all.pass.value;
	if(truenamex==""){
		alert("�ǳƲ���Ϊ��");
		return;
	}
	if(outemail==""){
		alert("���䲻��Ϊ��");
		return;
	}
	if(!checkEmail(outemail)){
		alert("��������ʽ����");
		return;
		}
	if(oldpass==""){
		alert("�����벻��Ϊ��");
		return;
	}
	if(pass==""){
		alert("�����벻��Ϊ��");
		return;
	}
	if(newpass==""){
		alert("�����벻��Ϊ��");
		return;
	}
	if(newpass!=pass){
		alert("��������������벻һ��");
		return;
	} else if(oldpass!="" && newpass!="" && newpass==pass){
		form1.submit();
	}
}

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
	if(tab.id=="tab2" || tab.id=="tab3"){
		document.all.buttons.style.display="none";
	} else {
		document.all.buttons.style.display="";
	}
}

function humangroupadd(){
	window.open("../resource/listFunctions.do?function=humandept&source=ldap",'��֯ѡ��','width=400,height=350,resizable=yes,left=250,top=100,status=yes');
}

function addSelectedGroup(text,value){
	document.all.faxNO.value = text;
}


function CharMode(iN){
	if (iN>=48 && iN <=57){ //����
		return 1; 
	}
	if (iN>=65 && iN <=90){ //��д��ĸ
		return 2;
	}
	if (iN>=97 && iN <=122){ //Сд
		return 4;
	}
	else{
		return 8; //�����ַ�
	}		
}

//�������ǰ���뵱��һ���ж�����ģʽ
function bitTotal(num){
	modes=0;
	for (i=0;i<=6;i++){
		if (num & 1) modes++;
		num>>>=1;
	}
	return modes;
}

//���������ǿ�ȼ���
function checkStrong(sPW){
	if (sPW.length<=6){
		return 0; //����̫��
	}
	Modes=0;
	for (i=0;i<sPW.length;i++){
		//����ÿһ���ַ������ͳ��һ���ж�����ģʽ.
		Modes|=CharMode(sPW.charCodeAt(i));
	}
	return bitTotal(Modes);
} 

//���û��ſ����̻����������ʧȥ����ʱ,���ݲ�ͬ�ļ�����ʾ��ͬ����ɫ
function pwStrength(pwd){
	O_color="#eeeeee";
	L_color="#FF0000";
	M_color="#FF9900";
	H_color="#33CC00";
	if (pwd==null||pwd==''){
		Lcolor=Mcolor=Hcolor=O_color;
	} else{
		S_level=checkStrong(pwd);
		switch(S_level) {
			case 0:
				Lcolor=Mcolor=Hcolor=O_color; 
			case 1:
				Lcolor=L_color;
				Mcolor=Hcolor=O_color;
				break;
			case 2:
				Lcolor=Mcolor=M_color;
				Hcolor=O_color;
				break;
			default:
				Lcolor=Mcolor=Hcolor=H_color;
		}
	} 
	document.getElementById("strength_L").style.background=Lcolor;
	document.getElementById("strength_M").style.background=Mcolor;
	document.getElementById("strength_H").style.background=Hcolor;
	return;
}


function checkEmail(str) {
    var pattern = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
    if (pattern.test(str)) {
        return true;
    } else {
        return false;
    }
}