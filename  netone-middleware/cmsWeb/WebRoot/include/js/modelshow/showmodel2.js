function addDiv(groupid,groupname,pardiv){
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
	var url = keepSessionUrl("/servlet/ShowInnerHtmlSvl2");
	var pars = "groupid="+div.id+"&modelid="+modelid;
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
