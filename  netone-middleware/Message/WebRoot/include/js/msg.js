
function sendmessage() {
	if (!document.all.title.value) {
		alert("\u8bf7\u8f93\u5165\u6807\u9898\uff01");
		return;
	}
	if (!document.all.content.value) {
		alert("\u8bf7\u8f93\u5165\u5185\u5bb9!");
		return;
	}
	if (!document.all.recevier.value) {
		alert("\u8bf7\u9009\u62e9\u63a5\u6536\u4eba!");
		return;
	}
	if (document.all.ext.value != "") {
		var ext = document.all.ext;
		var ids = "";
		for (var i = 0; i < ext.options.length; i++) {
			if (i == 0) {
				ids = ext.options[i].value + "=" + ext.options[i].text;
			} else {
				ids = ids + "," + ext.options[i].value + "=" + ext.options[i].text;
			}
		}
		document.all.extattr3.value = ids;
	}
	document.all.task.value = "sendmessage";
	document.forms[0].submit();
}
function addSelectedFile(options) {
	var ext = document.all.ext;
	if (options) {
		for (var i = 0; i < options.length; i++) {
			var op = new Option(options[i].text, options[i].value);
			ext.add(op);
		}
	}
}
function delfile() {
	var ext = document.all.ext;
	var i = ext.selectedIndex;
	if (i != -1) {
		ext.remove(i);
	}
}
function openuserselect() {
	window.open("<%=basePath%>MSelectSvl?appname=DEPT&pagename=human", "\u4eba\u5458\u9009\u62e9");
}
function openfileselect() {
	window.open("<%=basePath%>MSelectSvl?appid=2&pagename=file", "\u9644\u4ef6\u9009\u62e9");
}
function addSelectedUser3(options) {
	if (options) {
		var users = "";
		var userids = "";
		for (var i = 0; i < options.length; i++) {
			userids += options[i].value + "[" + options[i].text + "],";
		}
		document.all.recevier.value = userids;
	}
}
function init() {
	//处理 ext3
	var extattr3 = document.all.extattr3.value;
	var ext = document.all.ext;
	if (extattr3 != "") {
		extattr3 = extattr3.split(",");
		for (var i = 0; i < extattr3.length; i++) {
			var files = extattr3[i].split("=");
			if (files[1] != "") {
				var option = new Option(files[1], files[0]);
				ext.add(option);
			}
		}
	}
				
				//处理 ext4
	var extattr4 = document.all.extattr4.value;
	
	var ext2 = document.all.ext2;
	if (extattr4 != "") {
		extattr4 = extattr4.split(",");
		for (var i = 0; i < extattr4.length; i++) {
			var files = extattr4[i].split("#");
			if (files[1] != "") {
				var option = new Option(files[1], files[0]);
				ext2.add(option);
			}
		}
	}
}
function bussdo() {
	var url = document.getElementById("ext2").value;
	window.open(url, "_blank");
}

function download(){
    var extattr4 = document.all.extattr4.value;
    var extattr3=document.all.ext.value;
    window.open(extattr4+extattr3);
}