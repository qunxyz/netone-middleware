//bind to cms use to choice style by cms page
function selectthis(name,naturalname){
			document.getElementById('styleinfo').value=name+"["+naturalname+"]";
}
//bind to cms use to choice style
function listStyle(){
window.open('/cmsWeb/PagelistpathRightSvl?pagename=stylelist&appname=CSSFILE');
}


function addselect(id) {
	document.getElementById(curselect).value = id;

}

function choiceTree(name,cname){
	    	document.getElementById('dimdata').value=cname+'['+name+']';
}

function selectDimType() {
curselect='dimlevel';
	window.open("/dyForm/PagelistpathRightSvl?pagename=dimlist&appname=BUSSENV.BUSSENV.DYSER.BUSSLEVEL", "_blank");
}

function selectDimData() {
	curselect='dimdata';
	window.open("/dyForm/ApplistRightSvl?pagename=dyapplist", "_blank");
}
function goback() {
	var f = document.forms[0];
	location.href = "list.do?systemid=" + f["systemid"].value;
}
function enableChoice() {
	if (document.forms[0].checkFather.checked) {
		document.forms[0].subform.style.display = "";
	} else {
		document.forms[0].subform.style.display = "none";
	}
}

function defaultBut() {
	var value = document.forms[0].defaultbut.value;
	document.forms[0].butinfo.value = value;
}
function defaultViewBut() {
	var value = document.forms[0].defaultviewbut.value;
	document.forms[0].viewbutinfo.value = value;
}

function mselected(options){
	
	var subform='';
	for(var i=0;i<options.length;i++){
		subform+=options[i].text+'['+options[i].value+']mode=1,';
	}
	document.getElementById('subform').value=subform;
}

