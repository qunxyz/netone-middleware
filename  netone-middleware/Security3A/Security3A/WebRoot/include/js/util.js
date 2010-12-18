/* javascirpt工具类
 * author: hls 
 * 2007-08-14
 */

 
/* --- Map对象  begin ---*/
function Map(){
	this.keyarr = new Array();
	this.valuearr = new Array();
}

//添加对象，key,value可以为个数相同的以，分割的串
Map.prototype.put=function(key,value){
	this.remove(key);
	this.keyarr.push(key);
	this.valuearr.push(value);
};

//导入k1=v1,k2=v2,k3=v3格式的串
Map.prototype.load = function(){
	var item = [];
	for(var i=0 ; i<arguments.length ; i++){
		if(typeof arguments[i] == "string"){
			item = item.concat(arguments[i].split(","));
		}
	}
	for(var i=0 ; i<item.length ; i++){
		var tmp = item[i];
		var index = tmp.indexOf("=");
		if(index != -1){
			this.keyarr.push(tmp.substring(0,index));
			this.valuearr.push(tmp.substring(index+1));
		}
	}
};

Map.prototype.indexOf = function(key){
	for (var i = 0; i < this.keyarr.length; i++) {
		if (this.keyarr[i] == key) {
			return i;
		}
	}
	return -1;
};

Map.prototype.get = function(key){
	var i = this.indexOf(key);
	if( i != -1){
		return this.valuearr[i];
	}
	return null ;
};

Map.prototype.remove = function(key){
	var i = this.indexOf(key);
	if(i != -1){
		this.keyarr.splice(i , 1);
		this.valuearr.splice(i , 1);
	}
};

Map.prototype.removeIndex = function(index){
	if(index > 0 ){
		this.keyarr.splice(index , 1);
		this.valuearr.splice(index , 1);
	}
};

Map.prototype.clear = function(){
	this.keyarr.length=0 ;
	this.valuearr.length = 0 ;
};

Map.prototype.size = function(){
	return this.keyarr.length ;
};

Map.prototype.join = function(sp){
	var str = "";
	if(this.keyarr.length > 0)
	{
		for(var i = 0 ; i<this.keyarr.length-1 ; i++){
			var itemstr = this.keyarr[i]+"="+this.valuearr[i]+sp;
			str += itemstr;
		}
		str += this.keyarr[this.keyarr.length-1]+"="+this.valuearr[this.keyarr.length-1];
	}
	return str ;
}

Map.prototype.toString = function(){
	var str = "";
	if(this.keyarr.length > 0)
	{
		for(var i = 0 ; i<this.keyarr.length-1 ; i++){
			var itemstr = this.keyarr[i]+"="+this.valuearr[i]+",";
			str += itemstr;
		}
		str += this.keyarr[this.keyarr.length-1]+"="+this.valuearr[this.keyarr.length-1];
	}
	return str ;
};
/* --- Map对象  end ---*/


//获得checkbox组的选择的值，以“,”分割。
function getCbxGroupValue(cbxname) {
    var cbx = document.getElementsByName(cbxname);
    var restr = new Array();
    for (var i = 0; i < cbx.length; i++) {
        if (cbx[i].checked) {
            restr.push(cbx[i].value);
        }
    }
    return restr.toString();
}

//获得radio组选择的值，
function getRadioGroupValue(radioname){
	var radios = document.getElementsByName(radioname);
	for(var i=0 ;i<radios.length ; i++){
		if(radios[i].checked){
			return radios[i].value;
		}
	}
}

//xmlhttp
function xmlhttp(src){	
	var xmlhttp ;
	if (document.all) {
		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	}
	else {
		xmlhttp = new XMLHttpRequest();
	}
 	xmlhttp.open("GET",src, false);
 	xmlhttp.send("");
 	return xmlhttp;
}

//字符串全部替换
function  replaceAll(str,AFindText,ARepText){
	if(str){
	  raRegExp = new RegExp(AFindText,"g");
	  return str.replace(raRegExp,ARepText);
  	}
  	return str ;
}

//trim
function trimSpacing(str) {
    var str1 = new String(str);
    var i = 0;
    for (i = 0; i < str1.length; i++) {
        if (str1.charAt(i) != " ") {
            break;
        }
    }
    if (i == str1.length) {
        return "";
    } else {
        var j = i;
        for (i = str1.length - 1; i > -1; i--) {
            if (str1.charAt(i) != " ") {
                break;
            }
        }
        return str1.substring(j, i + 1);
    }
}
//check the phone
function isMobiTel(s) {
//    var patrn = /13[0-9]{9}|15[0-9]{9}/;
//    if (!patrn.exec(s)) {      
//        return false;
 //   }
    return true;
}
//check the email
function checkEmail(str) {
    var pattern = /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;
    if (pattern.test(str)) {
        return true;
    } else {
        return false;
    }
}

String.prototype.trim=function(){
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
