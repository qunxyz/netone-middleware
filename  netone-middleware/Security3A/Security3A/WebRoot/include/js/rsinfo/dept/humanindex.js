
function selectdepttype(type) { //根据部门类型显示和隐蔽信息

	checktypex = type;
	if (checktypex == "yxbm" || checktypex == "store") {
		
		codeline.style.display = "";//需要显示编码
		 
	} else {
		if (checktypex == "qy") {
		 
		   codeline.style.display = "none";//需要显示编码
		 
		   $('actionurl').value='';//切换的时候要，把该值清空
		} else {
			
			codeline.style.display = "";//需要显示编码
		}

	}

	
}
function initCleintTypeinfo(obj, type) {
	var market = new Ajax.Request("/iss/SystemConfectSvl?systemConfectCode=" + type, {method:"get", parameters:"", asynchronous:false});
	var marketinfo = market.transport.responseText;
	var marketTypeoptions = obj.options;
	for (var i = 0; i < marketTypeoptions.length; i++) {
		marketTypeoptions.remove("");
	}
	for (var i = 0; i < marketTypeoptions.length; i++) {
		marketTypeoptions.remove("");
	}
	var marketinfoall = marketinfo.split("#");
	for (var i = 0; i < marketinfoall.length - 1; i++) {
		var oOption = document.createElement("OPTION");
		oOption.text = marketinfoall[i].split(":")[1];
		oOption.value = marketinfoall[i].split(":")[0];
		obj.add(oOption);
	}
}
function initclientinfo() {//装载客户类型
	initCleintTypeinfo($("clientType"), "04");//装载客户类型
		
		//市场类型
	initCleintTypeinfo($("marketType"), "03");
}
function editview(extendattribute, type) {//修改进来的处理，将扩展属性的值分配到字段中去
	selectdepttype(type);
}


//DRP 组织指标


function newhumanx() {//保存,处理其他信息将其转换成 字符串写入 extendattribute
    //DRP 人员 验证


	newoper();
}
function updatehumanx() {//保存,处理其他信息将其转换成 字符串写入 extendattribute

	var chinese = /^.*[\u0391-\uFFE5]+.*$/;
	if (document.all.naturalname.value.match(chinese)) {
		alert("\u90e8\u95e8\u62fc\u97f3\u4e0d\u5141\u8bb8\u4e3a\u4e2d\u6587");
		return;
	}
	form1.submit();
}

function storeadd() {
	var code = document.getElementById("actionurl").value;
	var naturalname = document.getElementById("naturalname").value;
	var namerl = document.getElementById("name").value;
	window.open(rootpath+"/storage/storage.do?method=onEditView&naturalname=" + naturalname + "&name=" + namerl+"&code="+code, "store", "width=700,height=450,resizable=yes,left=150,status=yes");
}
function targetadd() {
	var name = document.getElementById("actionurl").value;
	window.open(rootpath+"/client/sellTargetNew.do?method=onMainView&clientLoginName=" + name, "target", "width=700,height=450,resizable=yes,left=150,status=yes");
} 
function clientdeptadd(clienttype) {
    var acturl=rootpath+'/client/client.do?method=onEditView&namex=';
    if(clienttype=='fxs'){
        acturl=rootpath+'/client/client.do?method=onEditChildView&namex=';
    }
	var name = document.getElementById("actionurl").value;
	window.open(acturl + name, "target", "width=700,height=450,resizable=yes,left=150,status=yes");
}

function clientdeptinit(clienttype) {
    var acturl=rootpath+'/DeleteClientInfoSvl?namex=';
	var name = document.getElementById("actionurl").value;
	window.open(acturl + name, "target", "width=700,height=450,resizable=yes,left=150,status=yes");
}


function humanadd() {
	var name = document.getElementById("naturalname").value;
	var actionurl = document.getElementById("actionurl").value;
	var objecttype = document.getElementById("objecttype").value;
	var url = document.all.jsppath.value + "/humanAdd.do?actionurl="+actionurl+"&objecttype="+objecttype+"&task2=dept&task=add&id=" + document.all.id.value + "&name=" + document.all.name.value;

	window.open(url, "useradd", "width=700,height=450,resizable=yes,left=150,status=yes");
}
function humanmodify() {
	var k = 0;
	var value;
	for (var i = 0; i < form1.elements.length; i++) {
		if (form1.elements[i].name == "chkid") {
			if (form1.elements[i].checked == true) {
				value = form1.elements[i].value;
				k++;
			}
		}
	}
	if (k == 0) {
		alert("\u8bf7\u5148\u9009\u4e2d\u9700\u8981\u4fee\u6539\u7684\u9009\u9879");
		return;
	}
	if (k > 1) {
		alert("\u53ea\u80fd\u9009\u62e9\u5355\u9879\u8fdb\u884c\u4fee\u6539");
		return;
	}
	var name = document.getElementById("naturalname").value;
	var actionurl = document.getElementById("actionurl").value;
	var url = document.all.jsppath.value + "/humanModify.do?actionurl="+actionurl+"&task=modify&description=" + value;

	window.open(url, "usermod", "width=700,height=450,resizable=yes,left=150,status=yes,scrollbars=auto");
}
function humandel() {

	var k = 0;
	for (var i = 0; i < form1.elements.length; i++) {
		if (form1.elements[i].name == "chkid") {
			if (form1.elements[i].checked == true) {
				k = 1;
				break;
			}
		}
	}
	if (k == 0) {
		alert("\u8bf7\u5148\u9009\u4e2d\u9700\u8981\u5220\u9664\u7684\u9009\u9879");
		return;
	} else {
		if (confirm("\u60a8\u786e\u8ba4\u6267\u884c\u5220\u9664\u64cd\u4f5c\u4e48\uff1f")) {
				form1.action = "humanDel.do?task2=dept";
				document.all.task.value = "";
				form1.submit();
		}
	}
}
function humancopy() {
	var k = 0;
	var value;
	for (var i = 0; i < form1.elements.length; i++) {
		if (form1.elements[i].name == "chkid") {
			if (form1.elements[i].checked == true) {
				value = form1.elements[i].value;
				k++;
			}
		}
	}
	if (k == 0) {
		alert("\u8bf7\u5148\u9009\u4e2d\u9700\u8981\u590d\u5236\u7684\u9009\u9879");
		return;
	}
	if (k > 1) {
		alert("\u53ea\u80fd\u9009\u62e9\u5355\u9879\u8fdb\u884c\u590d\u5236");
		return;
	}
	window.open(document.all.jsppath.value + "/humanCopy.do?task=copy&description=" + value, "usercopy", "width=700,height=450,resizable=yes,left=150,status=yes,scrollbars=auto");
}
function humanfileinput() {
	window.open(document.all.jsppath.value + "/human/humanfile.jsp?id=" + document.all.id.value, "create", "width=600,height=100,resizable=yes,left=150,status=yes,scrollbars=auto");
}
function humanfileoutput() {
	var k = 0;
	for (var i = 0; i < form1.elements.length; i++) {
		if (form1.elements[i].name == "chkid") {
			if (form1.elements[i].checked == true) {
				k = 1;
				break;
			}
		}
	}
	if (k == 0) {
		if (confirm("\u672a\u9009\u62e9\u7528\u6237,\u662f\u5426\u5bfc\u51fa\u5f53\u524d\u6240\u6709\u67e5\u8be2\u51fa\u7684\u7528\u6237?")) {
			document.all.task.value = "output";
			form1.action = "humanIndex.do";
			form1.submit();
		} else {
			return;
		}
	} else {
		if (confirm("\u662f\u5426\u5bfc\u51fa\u5f53\u524d\u6240\u9009\u4e2d\u7684\u7528\u6237?")) {
			document.all.task.value = "output";
			form1.action = "humanFile.do";
			form1.submit();
		}
	}
	document.all.task.value = "";
}
function doSearch() {
	document.all.task.value = "search";
	if (document.all.sname.value == "" && document.all.sdepartment.value == "") {
		document.all.id.value = "";
	}
	form1.action = "humanIndex.do";
	document.forms[0].submit();
}
function link(value) {
	window.open(document.all.jsppath.value + "/humanModify.do?task=modify&description=" + value, "usermod", "width=700,height=450,resizable=yes,left=150,status=yes,scrollbars=auto");
}
function docheck() {
	if (document.all.checkall.checked) {
		var checkids = document.getElementsByTagName("input");
		for (var i = 0; i < checkids.length; i++) {
			if (checkids[i].type == "checkbox") {
				if (checkids[i].checked) {
				} else {
					checkids[i].checked = true;
				}
			}
		}
	}
	if (document.all.checkall.checked == false) {
		var checkids = document.getElementsByTagName("input");
		for (var i = 0; i < checkids.length; i++) {
			if (checkids[i].type == "checkbox") {
				if (checkids[i].checked) {
					checkids[i].checked = false;
				}
			}
		}
	}
}
function humanpass() {
	var k = 0;
	for (var i = 0; i < form1.elements.length; i++) {
		if (form1.elements[i].name == "chkid") {
			if (form1.elements[i].checked == true) {
				k = 1;
				break;
			}
		}
	}
	if (k == 0) {
		alert("\u8bf7\u5148\u9009\u4e2d\u9700\u8981\u5bc6\u7801\u91cd\u7f6e\u7684\u9009\u9879");
		return;
	} else {
		if (confirm("\u662f\u5426\u8981\u91cd\u7f6e\u5bc6\u7801")) {
			document.all.task.value = "passreset";
			form1.action = "humanDel.do?task2=dept";
			form1.submit();
			document.all.task.value = "";
		}
	}
}

