function addDiv(groupid,groupname,pardiv){
	var newdiv =  $(groupid);
	
	$(pardiv).appendChild(newdiv);
	
	setDivInnerHtmlX(newdiv);
	
	return newdiv;
}

function setDivInnerHtmlX(div){
	var url = keepSessionUrl("/servlet/DivInnerHtmlSvl");
	var pars = "groupid="+div.id+"&modelid="+modelid+"&forshow=y";
	var divajax = new Ajax.Updater(
				div,
	            url,
	            {method: 'get', parameters: pars , evalScripts:true, onComplete: endSetDivHtml(div)}
	            );
}

function addDivOld(groupid,groupname,pardiv){
	var templatediv = $("templatediv");
	var newdiv = templatediv.cloneNode();
	newdiv.innerHTML = templatediv.innerHTML;
	newdiv.id = groupid;
	newdiv.name = groupname;
	newdiv.style.display = "block";
	$(pardiv).appendChild(newdiv);
	setDivInnerHtml(newdiv);
	return newdiv;
}


function addDivX(groupid,groupname,pardiv,value){
	
	var templatediv = $("templatediv");
	var newdiv = templatediv.cloneNode();
	newdiv.innerHTML = templatediv.innerHTML;
	newdiv.id = groupid;
	newdiv.name = groupname;
	newdiv.style.display = "block";
	$(pardiv).appendChild(newdiv);
	newdiv.innerHTML=value;
}

function refreshdiv(div){
	var div1 = $(div);
	if(div1){
		div1.innerHTML="loading...";
		setDivInnerHtml(div1);
	}
	else{
		alert("没有找到要刷新的模块！")
	}
}

function setDivInnerHtml(div){
	var url = keepSessionUrl("/servlet/DivInnerHtmlSvl");
	var pars = "groupid="+div.id+"&modelid="+modelid+"&forshow=y";
	var divajax = new Ajax.Updater(
				div,
	            url,
	            {method: 'get', parameters: pars , evalScripts:true, onComplete: endSetDivHtml(div)}
	            );
}

function endSetDivHtml(div){
	return function(xmlhttp){ 
	};
}

function keepSessionUrl(url){
	return contextpath+url+";jsessionid="+sessionid;
}
