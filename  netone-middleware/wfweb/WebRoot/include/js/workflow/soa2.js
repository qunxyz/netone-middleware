function checksyn(){
	var syn = document.all.syn;
	if(syn.checked){
		document.all.syn.value="true";
	} else {
		document.all.syn.value="false";
	}
}

function checksubmit(){
	if(document.all.txt.value==''){
		alert("����ѡ��");
		return false;
	}
	return true;
}

var nowcheckwf;
function checkwf(element){
	nowcheckwf = getPreviousSibling(element);
	form1.target="_blank";
	form1.action = "Show2.do?task=openout";
	form1.submit();
	form1.target="";
}

function checknew(element){
	nowcheckwf = getPreviousSibling(element);
	form1.target="_blank";
	form1.action = "Show2.do?task=openin";
	form1.submit();
	form1.target="";
}

function createSoa2(){
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
	form1.target="_blank";
	form1.action = "Show2.do?task=beforeshow&chkid="+value;
	form1.submit();
	form1.target="";
}

function createSoa2(id){
	form1.target="_blank";
	form1.action = "Show2.do?task=first&chkid="+id;
	form1.submit();
	form1.target="";
}
