/* javascirpt工具类
 * author hls
*/

/***** javascript相关 --begin-- *****/

//判断字符串是不是数字
function isDigit(s)
{
	var patrn = /^(-\+)?([0-9]+)\.?([0-9]*)$/;
	if (!patrn.exec(s)){
		return false ;
	}
	return true ;
}


//xmlhttp
function xmlhttp(src){	
  		var xmlhttp ;
  		if (document.all) 
				xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
		else 
 				xmlhttp = new XMLHttpRequest();
  		
  		xmlhttp.open("GET",src, false);
  		xmlhttp.send("");
  		return xmlhttp;
}


/* --- List对象  begin ---*/
function List(){	
	this.items = new Array();
}

//返回item是在list中编号
List.prototype.indexOf = function(obj){
	for (var i = 0; i < this.items.length; i++) {
		if (this.items[i] == obj) {
			return i;
		}
	}
	return -1;
};

List.prototype.get = function(index){
	return this.items[index];
};

//str可以为以","分割的多个值;
List.prototype.add = function(){
	for(var i=0 ; i<arguments.length ; i++){
		if(typeof arguments[i] == 'string'){
			this.items = this.items.concat(arguments[i].split(","));
		}
		else{
			this.items.push(arguments[i]) ;
		}
	}
};

//插入记录到指定的位置
List.prototype.insert = function(index, obj){
	this.items.splice(index, 0 , obj );
};

//删除第i个记录
List.prototype.remove = function(index){
	this.items.splice(index , 1 );
};

//删除值
List.prototype.removeItem=function(obj){
	var i = this.indexOf(obj);
	if(i != -1){
		this.remove(i);
	}
};

//排序
List.prototype.sort = function(){
	this.items.sort();
};

//取得List中对象个数
List.prototype.size = function(){
	return this.items.length ;
};

//连接
List.prototype.join = function(str){
	return this.items.join(str);
};

//转化成字符串,
List.prototype.toString = function(){
	return this.items.toString();
};
/* --- List对象  end ---*/


/* --- Map对象  begin ---*/
function Map(){
	this.keyarr = new Array();
	this.valuearr = new Array();
}

//添加对象，key,value可以为个数相同的以，分割的串
Map.prototype.put=function(key,value){
	if(this.indexOf(key) != -1){
		this.remove(key);
	}
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

Map.prototype.size = function(){
	return this.keyarr.length ;
};

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


/***** javascript相关 --end-- *****/



/***** web页面相关 --begin-- *****/

//获取element copy form prototype.js
function $() {
  var elements = new Array();

  for (var i = 0; i < arguments.length; i++) {
    var element = arguments[i];
    if (typeof element == 'string')
      element = document.getElementById(element);

    if (arguments.length == 1)
      return element;

    elements.push(element);
  }

  return elements;
}

function getNextSibling(element){
	element = $(element);
	var nextele = element.nextSibling;
	while(nextele){
		if(nextele.style){
			return nextele;
		}
		nextele = nextele.nextSibling;
	}
	return null;
}

function getPreviousSibling(element){
	element = $(element);
	var nextele = element.previousSibling;
	while(nextele){
		if(nextele.style){
			return nextele;
		}
		nextele = nextele.previousSibling;
	}
	return null;
}

function getOffsetSibling(element , offset){
	element = $(element);
	var reobj = element ;
	if(offset > 0){
		for(var i=0 ; i<offset ; i++){
			reobj = getNextSibling(reobj);
			if(!reobj){
				return null ;
			}
		}
	}
	else{
		for(var i=offset ; i<0 ; i++){
			reobj = getPreviousSibling(reobj);
			if(!reobj){
				return null ;
			}
		}
	}
	return reobj ;	
}


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


//设置checkbox组的选择的值，值为以“,”分隔的格式，若cbxvalue的值为空则不设置。
function setCbxGroupValue(cbxname, cbxvalue){
	if(cbxvalue){
		var cbxes = document.getElementsByName(cbxname);
		var list = new List();
		list.add(cbxvalue);
		for (var i=0 ; i<cbxes.length ; i++){
			if(list.indexOf(cbxes[i].value != -1)){
				cbxes[i].checked = true;
			}
		}
	}
}

//设置radio组选择的值。
function setRadioGroupValue(radioname, radiovalue){
	if(radiovalue){
		var radios = document.getElementsByName(radioname);
		for(var i=0 ; i<radios.length ; i++){
			if(radios[i].value == radiovalue){
				radios[i].checked = true ;
				break;
			}
		}
	}
}

//设置select选择的值。
function setSelectValue(selectname,value){
	if(value){
		document.all[selectname].value = value ;
	}
}


//取得元素到左边的距离
function getLeft(e) {
    var l = e.offsetLeft;
    while (e = e.offsetParent) {
        l += e.offsetLeft;
    }
    return l;
}

//取得元素到顶部的距离
function getTop(e) {
    var t = e.offsetTop;
    while (e = e.offsetParent) {
        t += e.offsetTop;
    }
    return t;
}

/***** web页面相关 --end-- *****/


/*************** debug ***************/
var debug = false ;
var debugdiv ;
if(debug){
//	Event.observe(window, 'load', debuginit );
	window.attachEvent("onload",debuginit);
}
function debuginit(){
	debugdiv = document.createElement("div");
	debugdiv.style.border = "1 solid blue";
	debugdiv.style.width = "500px";
	debugdiv.style.height = "20px";
	debugdiv.style.overflow = "visible";
	document.body.appendChild(debugdiv);
}
function log(str){
	if(debug){
		var textnode = document.createTextNode(str);
		var br = document.createElement("br");
		debugdiv.appendChild(textnode);
		debugdiv.appendChild(br);
	}
}

