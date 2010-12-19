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
		alert("请先选择");
		return false;
	}
	return true;
}

var nowcheckwf;
function checkwf(element){
	nowcheckwf = getPreviousSibling(element);
	form1.target="_blank";
	form1.action = "Show3.do?task=openout";
	form1.submit();
	form1.target="";
}

function checknew(element){
	nowcheckwf = getPreviousSibling(element);
	form1.target="_blank";
	form1.action = "Show3.do?task=openin";
	form1.submit();
	form1.target="";
}

function createSoa3(){
	window.open("Show3.do?task=zero&id="+document.all.id.value+"&appid="+document.all.appid.value+"&pagename="+document.all.pagename.value+"&chkid=");
}

function editSoa3(chkid){
	if(chkid){
		window.open("Show3.do?task=zero&id="+document.all.id.value+"&appid="+document.all.appid.value+"&pagename="+document.all.pagename.value+"&chkid="+chkid);
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
			alert("请先选中需要修改的选项");
			return;
		}
		if(k>1){
			alert("只能选择单项进行修改");
			return;
		}
		window.open("Show3.do?task=zero&id="+document.all.id.value+"&appid="+document.all.appid.value+"&pagename="+document.all.pagename.value+"&chkid="+value);
	}
}
