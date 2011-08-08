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
		alert("«Îœ»—°‘Ò");
		return false;
	}
	return true;
}

var nowcheckwf;
function checkwf(element){
	nowcheckwf = getPreviousSibling(element);
	form1.target="_blank";
	form1.action = "Show4.do?task=openout";
	form1.submit();
	form1.target="";
}

function checknew(element){
	nowcheckwf = getPreviousSibling(element);
	form1.target="_blank";
	form1.action = "Show4.do?task=openin";
	form1.submit();
	form1.target="";
}

function createSoax(id){
	form1.target="_blank";
	form1.action = "Showx.do?task=first&chkid="+id;
	form1.submit();
	form1.target="";
}