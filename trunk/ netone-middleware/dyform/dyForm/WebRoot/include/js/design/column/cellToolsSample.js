
function editmodel() {
	var url = "/cmsWeb/infomodeledit.do?simple=yes&id=" + modelid;
	window.open(url, "_blank", "width=800,height=400,left=300,top=100,resizable=yes");
}
function testEle() {
	var id = $("cellid").value;
	var url = "/cmsWeb/cms/detailinfo.jsp?cellid=" + id;
	window.open(url, "\u6d4b\u8bd5", "width=800,height=600,left=100,top=100,resizable=yes");
}
function designJppDemoMore(){
	var url = "/cmsWeb/cms/cellDIY/cellDIY.html";
	window.open(url, "\u6d4b\u8bd5", "width=800,height=500,left=100,top=100,resizable=yes");

}

function addScriptToValueList(){
//�÷�����cmsWeb��û����,ֻ��Ϊ��ӭ��dyForm�е��ֶ�ѡ��� ��Ҫ���ӵ�
}

//��ʾJppģ��
function openJppDemoDIY() {
	if ($("listJppScriptx").checked) {
		var parser = new Ajax.Request("/cmsWeb/servlet/AjaxJppListMoreSvl", {method:"get", parameters:"", asynchronous:false});
		restr = parser.transport.responseText;
		var sHTML = restr;
		JppScriptDemoMore.innerHTML = sHTML;
		JppScriptDemoMore.style.display = "";
		JppScriptDemoMoreUse.style.display = "";
	}else{
		JppScriptDemoMore.style.display = "none";
		JppScriptDemoMoreUse.style.display = "none";
	}
}
function addJppDemoMore() {
	var selJs = $("selJppJsMore").value;
	if (selJs == "0") {
		alert("\u8bf7\u5148\u9009\u62e9");
		return;
	}
	var parser = new Ajax.Request("/cmsWeb/servlet/AjaxJppAddMoreSvl", {method:"get", parameters:"jppid=" + selJs, asynchronous:false});
	var restr = parser.transport.responseText;
	var frame = window.parent;
	var body = frame.document.getElementById("body");
	if (body == null) {
		alert("\u6ca1\u6709\u53ef\u63d2\u5165\u7684\u5730\u65b9");
	}
	var inserScript = restr;
	body.innerText = body.innerText + "\r\n\r\n" + inserScript;
}

//չ�����е�Ԫ�� 
function openElementAutoWithOutPama(){
	var rs=document.getElementsByName('selGroup');
	for(var i=0;i<rs.length;i=i+1){
	   if(rs[i].selected){
	     openElementAuto(rs[i].value);
	   }
	}

}
function openElementAuto(selegroupValue) {
    resourceelement.style.display='';
	var fmlist = $("fmlist");
	iddisp2.style.display='none';

	if ("FM" == selegroupValue) {
		Fmlisttd.style.display = "";
		iddisp2.style.display="";
	} else {
		Fmlisttd.style.display = "none";
		iddisp2.style.display='none';
	}
	
	if("DF"==selegroupValue){
	    Dflist.style.display='';
	    Fmlisttd.style.display = "none";
iddisp2.style.display="";
		var parser = new Ajax.Request("/dyForm/formListSvl", {method:"get", parameters:"", asynchronous:false});
		restr = parser.transport.responseText;
		Dflist.innerHTML = restr;
		$("iddisp").value=$("selDf").value;

	}else{
	  Dflist.style.display='none';
     
	}
	openElement(selegroupValue);
}
function openElement(selegroupValue) {

	var restr;
	if ("SC" == selegroupValue) {
		var parser = new Ajax.Request("/cmsWeb/servlet/AjaxCellListSvl", {method:"get", parameters:"", asynchronous:false});
		restr = parser.transport.responseText;
		//alert('���Ϸ��Ĺ�������,ѡ���б��м���ѡ��OeseeJ++�ű�,��Ӧ�Ľű�ID���б��������������ʾ');
		//return;
	} else {
		if ("RS" == selegroupValue) {
			var parser = new Ajax.Request("/cmsWeb/servlet/AjaxRsListSvl", {method:"get", parameters:"", asynchronous:false});
			restr = parser.transport.responseText;
		} else if ("FM" == selegroupValue) {
				var fmlist = $("fmlist").value;
				$("iddisp2").value = fmlist;
				var parser = new Ajax.Request("/cmsWeb/servlet/AjaxFmListSvl", {method:"get", parameters:"formcode=" + fmlist, asynchronous:false});
				restr = parser.transport.responseText;
		} else if ("JPP" == selegroupValue) {
					var parser = new Ajax.Request("/cmsWeb/servlet/AjaxCellListSvl", {method:"get", parameters:"", asynchronous:false});
					restr = parser.transport.responseText;
		}else if("DF"==selegroupValue){
                
				var selDf = $("selDf").value;
				if(selDf!=''){
					$("iddisp2").value = selDf;
					var parser = new Ajax.Request("/dyForm/formColumnListSvl", {method:"get", parameters:"formcode=" + selDf, asynchronous:false});
					restr = parser.transport.responseText;
				}else{
					restr="";
				}
				
				
	     
		}
	
	}
	var sHTML = restr;
	NextElement.innerHTML = sHTML;

}


//���г����е�JPPԪ��
function listCell() {
	var parser = new Ajax.Request("/cmsWeb/servlet/AjaxCellListSvl?elementType=JPP", {method:"get", parameters:"", asynchronous:false});
	var sHTML = parser.transport.responseText;
	NextElementJpp.innerHTML = sHTML;
	JppElementDo.style.display = "";
}
//����J++�ű�
function addJPP() {
	window.open("/cmsWeb/infocellnewSimple.do?simple=yes", "JppmainFrame");
}
//�޸�J++�ű�
function editJPP() {
	var selegroupValue = $("selJs").value;
	if (selegroupValue == "0") {
		alert("\u8bf7\u5148\u9009\u62e9\u811a\u672c");
		return;
	}
	window.open("/cmsWeb/infocelleditSimple.do?id=" + selegroupValue, "JppmainFrame");
}
//��J++Ԫ������벼����ƽ���
function toFace() {
	var selegroupValue = $("selJs").value;
	if (selegroupValue == "0") {
		alert("\u8bf7\u5148\u9009\u62e9\u811a\u672c");
		return;
	}
	var sel = window.parent.opener.document.getElementById("valueid").value = selegroupValue;
	window.parent.opener.document.getElementById("valuedo").onclick();
	alert("\u6dfb\u52a0\u6210\u529f");
}

//���Ԫ��
function addEle() {
	var selegroupValue = $("selGroup").value;
	if (selegroupValue == "0") {
		alert("\u8bf7\u5148\u9009\u62e9\u4e00\u4e2a\u5206\u7ec4");
		return;
	}
	addEleCore(selegroupValue);
}
function addEleCore(selegroupValue) {
	window.open("/cmsWeb/blog/AddPage.jsp?modelid="+modelid, "_BlkDsa", "height=500, width=400, top=200, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no");

}

//ɾ��Ԫ��
function delEle() {
	var selJs = $("selJs");
	if ("0" == selJs.value) {
		alert("\u8bf7\u5148\u9009\u62e9\u8981\u5220\u9664\u7684J++\u811a\u672c");
		return;
	}
	var parser = new Ajax.Request("/cmsWeb/servlet/AjaxCellDelSvl", {method:"get", parameters:"elementId=" + selJs.value, asynchronous:false});
	var sHTML = parser.transport.responseText;
	if ("yes" == sHTML) {
		selJs.remove(selJs.selectedIndex);
		alert("\u5220\u9664\u6210\u529f");
	} else {
		alert("\u60a8\u6ca1\u6709\u5220\u9664\u8be5\u811a\u672c\u7684\u6743\u9650");
	}
}




//����ű�
function manEle() {
  
	var selegroupValue = $("selGroup").value;
	if("DF"==selegroupValue){
		window.open("dyForm/design/system/form/list.do?systemid=oec", "_blank");
	  
	}else if ("RS" == selegroupValue) {
		window.open("/fileupload/fi/filelistX.jsp", "_blank", "height=120, width=400, top=200, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
	} else {
		if ("FM" == selegroupValue) {
			window.open("/cavserweb/forum.htm");
		} else {
			if ("SC" == selegroupValue || "ARI" == selegroupValue || "JPP" == selegroupValue) {
				alert("\u672c\u9875\u9762\u65e2\u662fOESEEJ++\u7ba1\u7406\u754c\u9762");
			}
		}
	}
}

//��ʾ����JPPģ��DEMO
function dispJppScriptDemo() {
	var parser = new Ajax.Request("/cmsWeb/servlet/AjaxJppListSvl", {method:"get", parameters:"", asynchronous:false});
	var restr = parser.transport.responseText;
	var sHTML = restr;
	JppScriptDemo.innerHTML = sHTML;
}



//���һ��JPPģ��DEMO
function addJppDemo() {
	var selJs = $("selJppJs").value;
	if (selJs == "0") {
		alert("\u8bf7\u5148\u9009\u62e9");
		return;
	}
	var parser = new Ajax.Request("/cmsWeb/servlet/AjaxJppAddSvl", {method:"get", parameters:"jppid=" + selJs, asynchronous:false});
	var restr = parser.transport.responseText;
	var frame = window.parent;
	var body = frame.document.getElementById("body");
	if (body == null) {
		alert("\u6ca1\u6709\u53ef\u63d2\u5165\u7684\u5730\u65b9");
	}
	var inserScript = restr;
	body.innerText = body.innerText + "\r\n\r\n" + inserScript;
}

//��ʾ�����Դ��ID,�ṩ��JPPEL���ʽ������ʹ��
function getId() {
	var iddisp = $("iddisp");
	if (iddisp != null) {
		var selJs = $("selJs").value;
		iddisp.value = selJs;
	}
}

//��ʾ�����Դ��ID,�ṩ��JPPEL���ʽ������ʹ��
function getId() {
	var iddisp = $("iddisp");
	if (iddisp != null) {
		var selJs = $("selJs").value;
		iddisp.value = selJs;
	}
}

function exports() {
	window.open("/fileupload/fi/AllResourceExport.jsp", "_blank", "height=120, width=400, top=200, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
}

function inports() {
	window.open("/fileupload/fi/AllResourceInport.jsp", "_blank", "height=120, width=400, top=200, left=300, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no");
}

function manelementlist(){
	window.open('/cmsWeb/infocelllist.do?intime=on');
 
}

function viewPortal(mode){
 if(1==mode){// need portal  bar
   window.open('/cmsWeb/portal.do?toolbar=true&border=true&id='+modelid+'&portalmode='+mode);
 }
 if(2==mode){
   window.open('/cmsWeb/portal.do?border=true&id='+modelid+'&portalmode='+mode);
 }
 if(3==mode){
   window.open('/cmsWeb/portal.do?id='+modelid+'&portalmode='+mode);
 }
}
