var className = "oe.security3a.seucore.permission.js.AjaxPermission";
var serviceurl = "/Newland3A/servlet/AjaxServiceSvl?class="+className;
function getCookie(){
	var ck = document.cookie;
	var key = "SsoToken=";
	if(ck.indexOf(key)!=-1){
		ck=ck.substring(ck.indexOf(key)+key.length);
		if(ck.indexOf(";") != -1){
			ck=ck.substring(0,ck.indexOf(";"));
		}
		return ck ;
	}
	return "";
}

function getTop(){
	var par = window;
	while(par.parent != par){
		par = par.parent ;
	}
	return par ;
}

function checkLogin(){
	var token = getCookie();
	var ajax = xmlhttp(serviceurl+"&task=checkLogin&cookie="+token);
	var remgr = ajax.responseText;
	var map = new Map();
	map.load(remgr);
	if(map.get("result")=='false'){
		var url = map.get("url");
		getTop().location.href = url ;
	}
}

function checkPagePermission(resource,action){
	var token = getCookie();
	var ajax = xmlhttp(serviceurl+"&task=checkPagePermission&cookie="+token
				+"&resource="+resource+"&action="+action);
	var remgr = ajax.responseText;
	var map = new Map();
	map.load(remgr);
	if(map.get("result")=='false'){
		var url = map.get("url");
		getTop().location.href = url ;
	}
}

function checkPermission(resource,action){
	var token = getCookie();
	var ajax = xmlhttp(serviceurl+"&task=checkPermission&cookie="+token
				+"&resource="+resource+"&action="+action);
	var remgr = ajax.responseText;
	var map = new Map();
	map.load(remgr);
	if(map.get("result")=='true'){
		return true ;
	}
	return false ;
}

function getButton(resource,action,extattr){
	if(!checkPermission(resource,action)){
		extattr = "disabled=disabled "+extattr;
	}
	document.write("<input type='button' "+extattr+" >")
}
