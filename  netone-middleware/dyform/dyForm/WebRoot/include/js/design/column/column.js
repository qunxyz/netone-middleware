function addselect(name) {
	$('valuelist').value = name;
	$('dispvalue').value = name;
}

function openTree() {
	var viewtype = document.getElementById('viewtype').value;
	if (viewtype == '21') {
	  window.open('/dyForm/ApplistRightSvl?pagename=fckapplist', '_blank');
	}else if (viewtype == '20') {
	   window.open('/dyForm/ApplistRightSvl?pagename=portalapplist', '_blank');
	}else 	if (viewtype == '22' || viewtype == '23'||viewtype == '27' || viewtype == '28') {
		window.open('/dyForm/ApplistRightSvl?pagename=orgapplist', '_blank')
	} else {
		window.open('/dyForm/ApplistRightSvl?pagename=dyapplist', '_blank')
	}

}

function flashScrpit(valuelist) {
	var parser = new Ajax.Request("/cmsWeb/servlet/AjaxJppListMoreSvl", {
		method :"get",
		parameters :"valuelist=" + valuelist,
		asynchronous :false
	});
	var restr = parser.transport.responseText;
	alert(restr);
	JppScriptDemoMore.innerHTML = restr;
}

function addScrpitToValueList() {

	$("valuelist").value = $("selJppJsMore").value;
}

function addTreeToValueList() {
	$("valuelist").value = $("selJs").value;
}

var tip = '说明:值(":"符号右边是值),值和依次上传的图片相对应,多个值之间可通过逗号来分隔';
var defaultExtendAttribute = '#图片大小# size:200-200 ;\r\n#图片链接地址# link:;';
function hideFilebutton() {
	var f = document.forms[0];
	f['tpsc'].style.display = 'none';
	f['fjsc'].style.display = 'none';
	f['tip'].style.display = 'none';
}

// 该方法主要是针对不同的字段需要不同的输入操作而设计的
function choiceFile() {
	var valuelist = document.getElementById('valuelist').value;
	var viewtype = document.getElementById('viewtype').value;
	choiceFileCore(valuelist, viewtype);
}

// 该方法主要是针对不同的字段需要不同的输入操作而设计的
function choiceFileCore(valuelist, viewtype) {

	// 关于资源的处理 14是普通资源,17是带目录的资源
	if (viewtype == '18' || viewtype == '17' || viewtype == '22'
			|| viewtype == '23'|| viewtype == '21'|| viewtype == '20'||viewtype=='11'||viewtype == '27' || viewtype == '28') {
		// flashTree(valuelist);
		document.getElementById('treemantr').style.display = '';
		document.getElementById('bkvalue').style.display = 'none';

		$('dispvalue').value = cname = $('valuelist').value;
	} else {
		document.getElementById('treemantr').style.display = 'none';
		document.getElementById('bkvalue').style.display = '';
	}
	// 关于脚本的处理
	if (viewtype == '19') {
		flashScrpit(valuelist);
		document.getElementById('scriptinfo').style.display = '';
		document.getElementById('bkvalue').style.display = 'none';

	} else {
		document.getElementById('scriptinfo').style.display = 'none';
		document.getElementById('bkvalue').style.display = '';
	}

	var f = document.forms[0];

	// 关于标题属性处理
	if (viewtype.value == '18') {

		f['musk'].disabled = true;
		f['opemode'].disabled = true;
		f['useable'].disabled = true;
		f['opemode'].disabled = true;
		document.getElementById('extendattribute').disabled = true;
	} else {
		document.getElementById('opemode').disabled = false;
		document.getElementById('useable').disabled = false;
		document.getElementById('opemode').disabled = false;
		f['extendattribute'].disabled = false;
		f['musk'].disabled = false;
	}

	// 其他处理
	if (viewtype == '08' || viewtype == '09') {
		f['valuelist'].style.display = 'none';
		f['opemode'].style.display = 'none';
		f['musk'].style.display = 'none';
		if (viewtype == '08') {
			f['tpsc'].style.display = 'none';
			f['fjsc'].style.display = '';
			f['tip'].style.display = 'none';
		} else {
			f['tpsc'].style.display = '';
			f['tip'].style.display = '';
			f['extendattribute'].value = defaultExtendAttribute;
			f['tip'].value = tip;
			f['fjsc'].style.display = 'none';
		}
	} else {
		f['valuelist'].style.display = '';
		f['opemode'].style.display = '';
		f['musk'].style.display = '';
		f['tpsc'].style.display = 'none';
		f['fjsc'].style.display = 'none';
		f['tip'].style.display = 'none';
	}

	//2009-3-2 增加默认值 dxl
	if (viewtype == '22' || viewtype == '23') {
		document.getElementById("dispvalue").value = "组织机构[DEPT]";
	}
	if (viewtype == '20') {
		document.getElementById("dispvalue").value = "Portal[PORTALPG]";
	}
	if (viewtype == '21') {
		document.getElementById("dispvalue").value = "多彩文档[FCK]";
	}
}

function goback() {
	var f = document.forms[0];
	location.href = 'list.do?formcode=' + f['formcode'].value + '&systemid='
			+ f['systemid'].value;
}