
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
		alert("昵称不能为空");
		return;
	}
	if(outemail==""){
		alert("邮箱不能为空");
		return;
	}
	if(!checkEmail(outemail)){
		alert("邮箱名格式错误");
		return;
		}
	if(oldpass==""){
		alert("旧密码不能为空");
		return;
	}
	if(pass==""){
		alert("新密码不能为空");
		return;
	}
	if(newpass==""){
		alert("新密码不能为空");
		return;
	}
	if(newpass!=pass){
		alert("两次输入的新密码不一致");
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
	window.open("../resource/listFunctions.do?function=humandept&source=ldap",'组织选择','width=400,height=350,resizable=yes,left=250,top=100,status=yes');
}

function addSelectedGroup(text,value){
	document.all.faxNO.value = text;
}


function CharMode(iN){
	if (iN>=48 && iN <=57){ //数字
		return 1; 
	}
	if (iN>=65 && iN <=90){ //大写字母
		return 2;
	}
	if (iN>=97 && iN <=122){ //小写
		return 4;
	}
	else{
		return 8; //特殊字符
	}		
}

//计算出当前密码当中一共有多少种模式
function bitTotal(num){
	modes=0;
	for (i=0;i<=6;i++){
		if (num & 1) modes++;
		num>>>=1;
	}
	return modes;
}

//返回密码的强度级别
function checkStrong(sPW){
	if (sPW.length<=6){
		return 0; //密码太短
	}
	Modes=0;
	for (i=0;i<sPW.length;i++){
		//测试每一个字符的类别并统计一共有多少种模式.
		Modes|=CharMode(sPW.charCodeAt(i));
	}
	return bitTotal(Modes);
} 

//当用户放开键盘或密码输入框失去焦点时,根据不同的级别显示不同的颜色
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