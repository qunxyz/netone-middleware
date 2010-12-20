
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

//当前资源
var curResource = "";
function setCurResource(id) {
	curResource = id;
}
function addselect(value) {
	$(curResource).value = value;
}

//////////正则表达式///////////////////////////////
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
   
   ///////////检查校验表单中的字段///////////////////
function checkAndCommit(commitaction) {

     //获得表单中字段校验类型列表
	var checkInColumnSyntaxList = this.document.forms[0].checkinSl;
   	 //获得表单中的字段名列表
	var checkInColumnNameList = this.document.forms[0].checkinNl;
   	 //获得表单中的字段是否必须要求列表
	var checkInColumnMuskList = this.document.forms[0].checkinMl;
   	 //获得表单中字段个数(除了checkbox,image,file外)
	var len = 0;
	if (checkInColumnMuskList != null) {
		len = checkInColumnSyntaxList.length;
	}
	
   	 
   	 ////依次校验表单中的各个字段////
	for (var i = 0; i < len; i++) {
		var columnid = checkInColumnMuskList[i].value;
		var columnMusk = checkInColumnMuskList.options[i].text;
		if (columnMusk == "1") {//如果是必填的字段
			var columnValueTmp = this.document.forms[0][columnid];//?????columnid????????
			var columnValue = null;
			if (columnValueTmp != null) {
				columnValue = columnValueTmp.value;
			}
			if (columnValue == null || columnValue == "") {//如果必填字段为空
				var columnName = checkInColumnNameList.options[i].text;
				alert(columnName + "必须填写");
				return;
			}
		}
	}
   	 ////依次检查输入的字段值是否合法////
	for (var i = 0; i < len; i++) {
		var columnid = checkInColumnSyntaxList[i].value;
		var checkColumncheck = checkInColumnSyntaxList.options[i].text;
		var columnValueTemp = this.document.forms[0][columnid];
		var columnValue = "";
		if (columnValueTemp != null) {
			columnValue = columnValueTemp.value;
		}
		if (columnValue != "") {//如果未填写字段值，那么忽略，否则检查输入是否合法
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
	
	//获得表单中的字段在 字段创建中规定的正则表达式处理
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
		if (columnValue != "") {//如果未填写字段值，那么忽略，否则检查输入是否合法

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
   
   ////////隐蔽用于校验的参考信息///////
function hidenCheckRef() {
	//this.document.forms[0].checkinSl.style.display = "none";
	//this.document.forms[0].checkinNl.style.display = "none";
	//this.document.forms[0].checkinMl.style.display = "none";
	//this.document.forms[0].checkinXC.style.display = "none";
	//this.document.forms[0].checkinXCNAME.style.display = "none";
}

///公式计算 columnid是公式的字段ID,paramer是公式的参数ID,参数ID是通过@99@来分割的
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

