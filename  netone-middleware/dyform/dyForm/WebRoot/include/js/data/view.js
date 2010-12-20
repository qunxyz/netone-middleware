//显示帖子(论坛左边的 按钮事件)
function listview(lsh, partcipant,formcode) {
	if (formcode == "") {
		alert("无效子表单");
		return;
	}
	//window.open('/fm/'+partcipant+'_'+formcode+'_'+lsh+'.htm','forumlist');
	this.document.forms[0].action = "/dyForm/data/showdata/sublistview.do?lsh=" + lsh + "&formcode=" + formcode;
	this.document.forms[0].method = "post";
	this.document.forms[0].target = "forumlist";
	this.document.forms[0].submit();
}

function listsubview(lsh,subEnter) {
	if (subEnter == "") {
		alert("无效子表单");
		return;
	}
	var subformcode=this.document.getElementById(subEnter).value;
	this.document.forms[0].action = "/dyForm/data/showdata/sublistview.do?lsh=" + lsh + "&subform=" + subformcode;
	this.document.forms[0].method = "post";
	this.document.forms[0].target = "forumlist";
	this.document.forms[0].submit();
}


function listviewLink(lsh, formcode, nextSubflowcode) {
	if (nextSubflowcode == "") {
		alert("无效子表单");
		return;
	}
	this.document.forms[0].action = "/dyForm/data/showdata/sublistview.do?lsh=" + lsh + "&formcode=" + formcode;
	this.document.forms[0].method = "post";
	this.document.forms[0].target = "_blank";
	this.document.forms[0].submit();
}
function openList(formcode, lsh) {
	var url = "/dyForm/data/data/list.do?lsh=" + lsh + "&formcode=" + formcode;
	window.open(url, "_blank", "height=400, width=800, top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no");
}
function makelsh() {
	var d = new Date();
	return d.getTime();
}
function openCreate(formcode, lsh) {
	var url = "/dyForm/data/showdata/createview.do?lsh=" + makelsh() + "&fatherlsh=" + lsh + "&formcode=" + formcode;
	window.open(url, "_blank", "height=360, width=670, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
}
function fathergo(lsh, formcode) {
	this.document.forms[0].action = "/dyForm/data/showdata/listview.do?lsh=" + lsh + "&formcode=" + formcode;
	this.document.forms[0].method = "post";
	this.document.forms[0].target = "_self";
	this.document.forms[0].submit();
}

