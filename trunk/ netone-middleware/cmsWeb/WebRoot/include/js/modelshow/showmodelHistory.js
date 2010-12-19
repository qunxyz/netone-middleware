function addDiv(groupid,groupname,pardiv,date){
	var templatediv = $("templatediv");
	var newdiv = templatediv.cloneNode();
	newdiv.innerHTML = templatediv.innerHTML;
	newdiv.id = groupid;
	newdiv.name = groupname;
	newdiv.style.display = "block";
	$(pardiv).appendChild(newdiv);
	setDivInnerHtml(newdiv,date);
	return newdiv;
}

function setDivInnerHtml(div,date){
	var url = keepSessionUrl("/servlet/DivInnerHtmlSvlHistory");
	var pars = "cellid="+div.id+"&date="+date+"&forshow=y";
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