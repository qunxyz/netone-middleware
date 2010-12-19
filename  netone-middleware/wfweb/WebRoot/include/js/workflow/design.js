

function fetchCheck() {
	var processidTmp = document.getElementsByName("radioid");

	for (var i = 0; i < processidTmp.length; i++) {
		if (processidTmp[i].checked) {
			return processidTmp[i].value;

		}
	}
}

function choiceprocess1() {
	var processid = fetchCheck();
	if (processid == null) {
		alert("请选择流程");
		return;
	}
	var pathinfo = document.getElementById("pathinfo").value;
	var url = pathinfo + '/viewprocess.do?processid=' + processid;
	window.open(url, "_blank",
			"toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

function choiceprocess2() {
	var processid = fetchCheck();
	if (processid == null) {
		alert("请选择流程");
		return;
	}
	var pathinfo = document.getElementById("pathinfo").value;
	var url = pathinfo + '/viewreadonlyprocess.do?processid=' + processid;
	window.open(url, "_blank",
			"toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

function choiceprocess3() {
	var processid = fetchCheck();
	if (processid == null) {
		alert("请选择流程");
		return;
	}
	var pathinfo = document.getElementById("pathinfo").value;
	var url = pathinfo + '/useprocess.do?processid=' + processid;
	window.open(url, "_useflow",
			"toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

function choiceprocess4() {
	var processid = fetchCheck();
	if (processid == null) {
		alert("请选择流程");
		return;
	}
	var pathinfo = document.getElementById("pathinfo").value;
	var url = pathinfo + '/listUseprocess.do?processid=' + processid;
	window.open(url, "_useflow",
			"toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

function choiceprocess5() {
	var processid = fetchCheck();
	if (processid == null) {
		alert("请选择流程");
		return;
	}
	var pathinfo = document.getElementById("pathinfo").value;
	var url = pathinfo + '/listSubUseprocess.do?opedo=subflow&runtimeid='
			+ processid;
	window.open(url, "_useflow",
			"toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

function choiceprocess6() {
	var runtimeid = fetchCheck();
	if (runtimeid == null) {
		alert("请选择流程");
		return;
	}
	var pathinfo = document.getElementById("pathinfo").value;
	var url = pathinfo + '/listRuntimeprocess.do?runtimeid=' + runtimeid;
	window.open(url, "_useflow",
			"toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

function newprocess() {

	var pathinfo = document.getElementById("pathinfo").value;

	var url = pathinfo + '/newprocessfile.do';

	window
			.open(
					url,
					"_blank",
					"left=200,top=200,height=500,height=400,toolbar=no, menubar=no,scrollbars=yes, resizable=yes");

}

function downprocess() {
	var processid = fetchCheck();
	if (processid == null) {
		alert("请选择流程");
		return;
	}
	var pathinfo = document.getElementById("pathinfo").value;
	var url = pathinfo + '/dowloadprocess.do?processid=' + processid;
	window.open(url, "_blank", "width=500,height=400");
}
function dropprocess() {
	var processid = fetchCheck();
	if (processid == null) {
		alert("请选择流程");
		return;
	}
	var pathinfo = document.getElementById("pathinfo").value;
	var url = pathinfo + '/dropprocessfile.do?processid=' + processid;
	window.open(url, "_self", "width=500,height=400");
}

function useprocess(processid, mode, runtimeid) {
	var pathinfo = document.getElementById("pathinfo").value;
	var url = pathinfo + '/useprocess.do?mode=' + mode;
	if (mode == 'new') {
		url += "&processid=" + processid;
	} else {
		url += "&runtimeid=" + runtimeid;
	}
	window.open(url, "_blank",
			"toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

function useprocessview(mode, runtimeid) {
	var pathinfo = document.getElementById("pathinfo").value;
	var url = '';
	if (mode == 'subflow') {
		url = pathinfo + '/listSubUseprocess.do?runtimeid=' + runtimeid;
	} else {
		url = pathinfo + '/listRuntimeprocess.do?runtimeid=' + runtimeid;
	}
	window.open(url, "_useflow",
			"toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

// 流程活动实例子信息
function worklistinfo(runtimeid) {
	var pathinfo = document.getElementById("pathinfo").value;
	var url = pathinfo + '/WorkListInfoSvl?runtimeid=' + runtimeid;
	window.open(url);

}
// 流程想关变量信息
function Relevantvarinfo(runtimeid) {
	var pathinfo = document.getElementById("pathinfo").value;
	var url = pathinfo + '/RelevantvarSvl?runtimeid=' + runtimeid;
	window.open(url);

}
