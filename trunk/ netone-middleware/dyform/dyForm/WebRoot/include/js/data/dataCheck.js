
function refreshdivAll(cellid,viewmode) {
	if (cellid == "") {
		return;
	}
	var cellarr = cellid.split(",");
	if("yes"==viewmode){
			for (var i = 0; i < cellarr.length; i++) {
			refreshdivView(cellarr[i]);
		}
	
	}else{
			for (var i = 0; i < cellarr.length; i++) {
			refreshdiv(cellarr[i]);
		}

	}
}

//��ǰ��Դ
var curResource = "";
function setCurResource(id) {
	curResource = id;
}
function addselect(value) {
	$(curResource).value = value;
}

//////////������ʽ///////////////////////////////
var number = /^[-]?([0-9]+)\.?([0-9]*)$/;
var mail = /^[_a-z0-9]+@([_a-z0-9]+\.)+[a-z0-9]{2,3}$/;
var english = /^[_A-Za-z0-9]+$/;
var chinese = /^[\u0391-\uFFE5]+$/;
var ip = /^([0-2]{1}[0-9]{2}\.){3}[0-2]{1}[0-9]{2}$/;
   ///////////////////////////////////////////////////
   
   
function   commitX(url){
	this.document.forms[0].action = url;
	this.document.forms[0].method = "post";
	this.document.forms[0].target = "_self";
	this.document.forms[0].submit();
}
   
   ///////////���У����е��ֶ�///////////////////
function checkAndCommit(commitaction) {

     //��ñ����ֶ�У�������б�
	var checkInColumnSyntaxList = this.document.forms[0].checkinSl;
   	 //��ñ��е��ֶ����б�
	var checkInColumnNameList = this.document.forms[0].checkinNl;
   	 //��ñ��е��ֶ��Ƿ����Ҫ���б�
	var checkInColumnMuskList = this.document.forms[0].checkinMl;
   	 //��ñ����ֶθ���(����checkbox,image,file��)
	var len = 0;
	if (checkInColumnMuskList != null) {
		len = checkInColumnSyntaxList.length;
	}
	
   	 
   	 ////����У����еĸ����ֶ�////
	for (var i = 0; i < len; i++) {
		var columnid = checkInColumnMuskList[i].value;
		var columnMusk = checkInColumnMuskList.options[i].text;
		if (columnMusk == "1") {//����Ǳ�����ֶ�
			var columnValueTmp = this.document.forms[0][columnid];//?????columnid????????
			var columnValue = null;
			if (columnValueTmp != null) {
				columnValue = columnValueTmp.value;
			}
			if (columnValue == null || columnValue == "") {//��������ֶ�Ϊ��
				var columnName = checkInColumnNameList.options[i].text;
				alert(columnName + "������д");
				return;
			}
		}
	}
   	 ////���μ��������ֶ�ֵ�Ƿ�Ϸ�////
	for (var i = 0; i < len; i++) {
		var columnid = checkInColumnSyntaxList[i].value;
		var checkColumncheck = checkInColumnSyntaxList.options[i].text;
		var columnValueTemp = this.document.forms[0][columnid];
		var columnValue = "";
		if (columnValueTemp != null) {
			columnValue = columnValueTemp.value;
		}
		if (columnValue != "") {//���δ��д�ֶ�ֵ����ô���ԣ������������Ƿ�Ϸ�
			if (checkColumncheck == "number") {
				
				if (!columnValue.match(number)) {
					var columnName = checkInColumnNameList.options[i].text;
					alert(columnName + "\u7684\u8f93\u5165\u503c\u5fc5\u987b\u8f93\u5165\u6570\u5b57");
					return;
				}
			} else {
				if (checkColumncheck == "mail") {
					if (!columnValue.match(mail)) {
						var columnName = checkInColumnNameList.options[i].text;
						alert(columnName + "\u7684\u8f93\u5165\u503c\u4e3a\u65e0\u6548\u90ae\u4ef6\u5730\u5740");
						return;
					}
				} else {
					if (checkColumncheck == "english") {
						if (!columnValue.match(english)) {
							var columnName = checkInColumnNameList.options[i].text;
							alert(columnName + "\u7684\u8f93\u5165\u503c\u5fc5\u987b\u662f\u82f1\u6587\u5b57\u6bcd");
							return;
						}
					} else {
						if (checkColumncheck == "chinese") {
							if (!columnValue.match(chinese)) {
								var columnName = checkInColumnNameList.options[i].text;
								alert(columnName + "\u7684\u8f93\u5165\u503c\u5fc5\u987b\u662f\u4e2d\u6587");
								return;
							}
						} else {
							if (checkColumncheck == "ip") {
								if (!columnValue.match(ip)) {
									var columnName = checkInColumnNameList.options[i].text;
									alert(columnName + "\u7684\u8f93\u5165\u503c\u4e3a\u65e0\u6548IP\u5730\u5740");
									return;
								}
							}
						}
					}
				}
			}
		}
	}
	
	//��ñ��е��ֶ��� �ֶδ����й涨��������ʽ����
	var checkInColumnXCList = this.document.forms[0].checkinXC;
	var checkInColumnXCTIPList = this.document.forms[0].checkinXCNAME;
	for (var i = 0; i < len; i++) {
		var columnid = checkInColumnSyntaxList[i].value;
		var checkColumncheck = checkInColumnXCList.options[i].text;
		var columnValueTemp = this.document.forms[0][columnid];
		var columnValue = "";
		if (columnValueTemp != null) {
			columnValue = columnValueTemp.value;
		}
		if (columnValue != "") {//���δ��д�ֶ�ֵ����ô���ԣ������������Ƿ�Ϸ�

			if (!columnValue.match(checkColumncheck)) {
				var columnName = checkInColumnNameList.options[i].text;
				alert(columnName +'  '+ checkInColumnXCTIPList.options[i].text);
				return;
			}
		}
	}
	this.document.forms[0].action = commitaction;
	this.document.forms[0].method = "post";
	this.document.forms[0].target = "_blank";
	this.document.forms[0].submit();
	
	//window.opener.location.reload();
	//window.close();
}
   
   ////////��������У��Ĳο���Ϣ///////
function hidenCheckRef() {
	//this.document.forms[0].checkinSl.style.display = "none";
	//this.document.forms[0].checkinNl.style.display = "none";
	//this.document.forms[0].checkinMl.style.display = "none";
	//this.document.forms[0].checkinXC.style.display = "none";
	//this.document.forms[0].checkinXCNAME.style.display = "none";
}

///��ʽ���� columnid�ǹ�ʽ���ֶ�ID,paramer�ǹ�ʽ�Ĳ���ID,����ID��ͨ��@99@���ָ��
function calculate(columnid, paramer, calcaulateid) {
	var paramarLink = "&paraminfo=";
	if (paramer != "") {
		var paramarr = paramer.split("@99@");
		for (var i = 0; i < paramarr.length; i++) {
			if (paramarr[i] != "") {
				if ($(paramarr[i]).value == "") {
					alert("\u516c\u5f0f\u4e2d\u7684\u53c2\u6570\u6ca1\u6709\u5168\u90e8\u8d4b\u503c");
					return;
				}
				paramarLink += $(paramarr[i]).value + ",";
			}
		}
	}
	var parser = new Ajax.Request("/cmsWeb/servlet/CalculateSvl", {method:"get", parameters:"calcaulateid=" + calcaulateid + paramarLink, asynchronous:false});
	var restr = parser.transport.responseText;
	$(columnid).value = restr;
}
function test(columnid, paramer, calcaulateid) {
	var paramarLink = "&paraminfo=";
	if (paramer != "") {
		var paramarr = paramer.split("@99@");
		for (var i = 0; i < paramarr.length; i++) {
			if (paramarr[i] != "") {
				if ($(paramarr[i]).value == "") {
					alert("\u53c2\u6570\u6ca1\u6709\u5168\u90e8\u8d4b\u503c");
					return;
				}
				paramarLink += $(paramarr[i]).value + ",";
			}
		}
	}
	var parser = new Ajax.Request("/cmsWeb/servlet/CalculateSvl", {method:"get", parameters:"calcaulateid=" + calcaulateid + paramarLink, asynchronous:false});
	var restr = parser.transport.responseText;
	DISP.innerHTML = restr;
}

