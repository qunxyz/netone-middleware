
function checksyn() {
	var syn = document.all.syn;
	if (syn.checked) {
		document.all.syn.value = "true";
	} else {
		document.all.syn.value = "false";
	}
}
function changeio() {
	var io = document.all.io.value;
	var result2 = document.all.result2.options;
	var result4 = document.all.result4.options;
	var result5 = document.all.result5.options;
	if (document.all.checks.value == "be") {
		if (io == "out") {
			if (result2.length > 0) {
				result2.length = 0;
			}
			for (var i = 0; i < result4.length; i++) {
				var option = new Option(result4[i].text, result4[i].value);
				result2.add(option);
			}
		}
		if (io == "in") {
			if (result2.length > 0) {
				result2.length = 0;
			}
			for (var i = 0; i < result5.length; i++) {
				var option = new Option(result5[i].text, result5[i].value);
				result2.add(option);
			}
		}
	}
}
function checksubmit() {
	if (document.all.txt.value == "") {
		alert("\xc7\xeb\xcf\xc8\xd1\xa1\xd4\xf1");
		return false;
	}
	return true;
}
function add() {
	document.all.result3.selectedIndex = -1;
	var result = document.all.result.value;
	var result2 = document.all.result2.value;
	var io = document.all.io.value;
	var resulttext = document.all.result.options[document.all.result.selectedIndex].text;
	var result2text = document.all.result2.options[document.all.result2.selectedIndex].text;
	var tmp = result + "." + result2;
	var tmptext;
	if (io == "in") {
		tmptext = resulttext + " <- " + result2text;
	} else {
		if (io == "out") {
			tmptext = resulttext + " -> " + result2text;
		}
	}
	var result3 = document.all.result3;
	var b = true;
	for (var i = 0; i < result3.length; i++) {
		var v = result3.options[i].value.split(".");
		var t = v[0] + "." + v[1] + "." + v[3];
		var m = tmp + "." + io;
		if (t == m) {
			b = false;
			alert(result3.options[i].text + "\xd2\xd1\xbe\xb4\xe6\xd4\xda!");
			break;
		}
	}
	if (b) {
		tmp = tmp + "." + document.all.text1.value + "." + io;
		document.all.result3.add(new Option(tmptext, tmp));
	}
	document.all.text1.value = "";
}
function laws() {
	var result = document.all.result.value;
	var result2 = document.all.result2.value;
	var io = document.all.io.value;
	var resulttext = document.all.result.options[document.all.result.selectedIndex].text;
	var result2text = document.all.result2.options[document.all.result2.selectedIndex].text;
	var tmp = result + "." + result2;
	var tmptext;
	if (io == "in") {
		tmptext = resulttext + " <- " + result2text;
	} else {
		if (io == "out") {
			tmptext = resulttext + " -> " + result2text;
		}
	}
	var result3 = document.all.result3;
	var b = true;
	for (var i = 0; i < result3.length; i++) {
		var v = result3.options[i].value.split(".");
		var t = v[0] + "." + v[1] + "." + v[3];
		var m = tmp + "." + io;
		if (t == m && i != document.all.result3.selectedIndex) {
			b = false;
			alert(result3.options[i].text + "\xd2\xd1\xbe\xb4\xe6\xd4\xda!");
			break;
		}
	}
	if (b) {
		tmp = tmp + "." + document.all.text1.value + "." + io;
		document.all.result3.options[document.all.result3.selectedIndex].value = tmp;
		document.all.result3.options[document.all.result3.selectedIndex].text = tmptext;
	}
}
function drop() {
	var s = document.all.result3;
	var i = s.selectedIndex;
	while (i != -1) {
		s.remove(i);
		i = s.selectedIndex;
	}
}
function changeresult3() {
	var tmp = document.all.result3.options[document.all.result3.selectedIndex].value;
	var script = tmp.split(".");
	document.all.result.value = script[0];
	document.all.text1.value = script[2];
	document.all.io.value = script[3];
	changeio();
	document.all.result2.value = script[1];
}
function show() {
	var result3 = document.all.result3;
	var value = "";
	for (var i = 0; i < result3.length; i++) {
		value += result3.options[i].value + "@";
	}
	document.all.totalresult.value = value;
	var syn = document.all.syn;
	if (syn.checked) {
		document.all.syn.value = "true";
	} else {
		document.all.syn.value = "false";
	}
	form1.action = "Show.do?task=show";
	form1.submit();
}
function modifyshow() {
	var result3 = document.all.result3;
	var value = "";
	for (var i = 0; i < result3.length; i++) {
		value += result3.options[i].value + "@";
	}
	document.all.totalresult.value = value;
	document.all.checks.disabled = false;
	form1.action = "Show.do?task=show";
	form1.submit();
}
function createA(value) {
	window.open("Createsubinfo.do?chkid=" + value, "_blank");
}
function createSoa() {
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
		alert("\xc7\xeb\xcf\xc8\xd1\xa1\xd6\xd0\xd0\xe8\xd2\xaa\xd0\xde\xb8\xc4\xb5\xc4\xd1\xa1\xcf\xee");
		return;
	}
	if (k > 1) {
		alert("\xd6\xbb\xc4\xdc\xd1\xa1\xd4\xf1\xb5\xa5\xcf\xee\xbd\xf8\xd0\xd0\xd0\xde\xb8\xc4");
		return;
	}
	form1.target = "_blank";
	form1.action = "Show.do?task=beforeshow&chkid=" + value;
	form1.submit();
	form1.target = "";
}
function createSoa(id) {
	form1.target = "_blank";
	form1.action = "Show.do?task=beforeshow&chkid=" + id;
	form1.submit();
	form1.target = "";
}

