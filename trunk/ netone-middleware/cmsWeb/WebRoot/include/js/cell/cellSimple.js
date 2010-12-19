
function testEle() {
	var id = $("cellid").value;
	var url = "/cmsWeb/cms/detailinfo.jsp?cellid=" + id;
	window.open(url, "\u6d4b\u8bd5", "width=800,height=600,left=100,top=100,resizable=yes");
}
function initvalue() {
	document.forms[0].fiCopy.value = document.forms[0].fiColumnId.value;
	document.forms[0].cellToolCopy.value = document.forms[0].cellTool.value;
	selecttool();
}
function selecttool() {
	var selectele = document.forms[0].select.value;
	if (selectele == 0 || selectele == null) {
		document.getElementById("td1").style.display = "none";
		document.getElementById("td2").style.display = "none";
		document.getElementById("td3").style.display = "none";
		document.getElementById("td4").style.display = "none";
		return;
	}
	if (selectele == 1) {
		document.getElementById("td1").style.display = "";
		document.getElementById("td2").style.display = "none";
		document.getElementById("td3").style.display = "none";
		document.getElementById("td4").style.display = "none";
		return;
	}
	if (selectele == 2) {
		document.getElementById("td1").style.display = "none";
		document.getElementById("td2").style.display = "";
		document.getElementById("td3").style.display = "none";
		document.getElementById("td4").style.display = "none";
		return;
	}
	if (selectele == 3) {
		document.getElementById("td1").style.display = "none";
		document.getElementById("td2").style.display = "none";
		document.getElementById("td3").style.display = "";
		document.getElementById("td4").style.display = "none";
		return;
	}
	if (selectele == 4) {
		document.getElementById("td1").style.display = "none";
		document.getElementById("td2").style.display = "none";
		document.getElementById("td3").style.display = "none";
		document.getElementById("td4").style.display = "";
		return;
	}
}
	//更新文件
function changefi() {
	var path = document.forms[0].path.value;
	document.forms[0].action = path + "/infocelltool.do?newFlag=changeFi";
	document.forms[0].submit();
}
	

	//更新组
function changeGroup() {
	var path = document.forms[0].path.value;
	document.forms[0].action = path + "/infocelltool.do?newFlag=changeGroup";
	document.forms[0].submit();
}
		
	//选择组
function changeCellGroup() {
	var path = document.forms[0].path.value;
	document.forms[0].action = path + "/infocelltool.do?newFlag=changeCellGroup";
	document.forms[0].submit();
}
function commitQuick(url) {
	var group = document.forms[0].belongto.value;
	if (group == null || group == "") {
		alert("\u8bf7\u9009\u62e9\u7ec4");
		return;
	}
	var cellname = document.forms[0].cellname;
	if (cellname == null || cellname == "") {
		alert("\u8bf7\u586b\u5199Portalet\u540d");
		return;
	}
	document.forms[0].action = url;
	document.forms[0].submit();
}
function editDone() {
	var frame = window.parent.JpptopFrame;
	var selJs = frame.document.getElementById("selJs");
	selJs.remove(selJs.selectedIndex);
	var oOption = document.createElement("OPTION");
	oOption.text = $("cellname").value;
	oOption.value = $("cellid").value;
	selJs.add(oOption);
	document.forms[0].action = "infocelleditSimple.do?editFlag=update";
	document.forms[0].submit();
}
function editDoneSimple() {
	document.forms[0].action = "infocelleditSimple.do?editFlag=update&quick=ok";
	document.forms[0].submit();
}
function addjs() {
	document.forms[0].action = "infocellnewSimple.do?newFlag=new";
	document.forms[0].submit();
}
function intimetodo() {
	var intime = document.getElementById("intime");
	if (intime.value == 1) {
		intime.value = 0;
	} else {
		intime.value = 1;
	}
	alert(intime.value);
}

