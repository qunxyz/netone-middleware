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

var tip = '˵��:ֵ(":"�����ұ���ֵ),ֵ�������ϴ���ͼƬ���Ӧ,���ֵ֮���ͨ���������ָ�';
var defaultExtendAttribute = '#ͼƬ��С# size:200-200 ;\r\n#ͼƬ���ӵ�ַ# link:;';
function hideFilebutton() {
	var f = document.forms[0];
	f['tpsc'].style.display = 'none';
	f['fjsc'].style.display = 'none';
	f['tip'].style.display = 'none';
}

// �÷�����Ҫ����Բ�ͬ���ֶ���Ҫ��ͬ�������������Ƶ�
function choiceFile() {
	var valuelist = document.getElementById('valuelist').value;
	var viewtype = document.getElementById('viewtype').value;
	choiceFileCore(valuelist, viewtype);
}

// �÷�����Ҫ����Բ�ͬ���ֶ���Ҫ��ͬ�������������Ƶ�
function choiceFileCore(valuelist, viewtype) {

	// ������Դ�Ĵ��� 14����ͨ��Դ,17�Ǵ�Ŀ¼����Դ
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
	// ���ڽű��Ĵ���
	if (viewtype == '19') {
		flashScrpit(valuelist);
		document.getElementById('scriptinfo').style.display = '';
		document.getElementById('bkvalue').style.display = 'none';

	} else {
		document.getElementById('scriptinfo').style.display = 'none';
		document.getElementById('bkvalue').style.display = '';
	}

	var f = document.forms[0];

	// ���ڱ������Դ���
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

	// ��������
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

	//2009-3-2 ����Ĭ��ֵ dxl
	if (viewtype == '22' || viewtype == '23') {
		document.getElementById("dispvalue").value = "��֯����[DEPT]";
	}
	if (viewtype == '20') {
		document.getElementById("dispvalue").value = "Portal[PORTALPG]";
	}
	if (viewtype == '21') {
		document.getElementById("dispvalue").value = "����ĵ�[FCK]";
	}
}

function goback() {
	var f = document.forms[0];
	location.href = 'list.do?formcode=' + f['formcode'].value + '&systemid='
			+ f['systemid'].value;
}