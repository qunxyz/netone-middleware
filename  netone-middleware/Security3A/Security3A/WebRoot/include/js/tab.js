/**
 *  Ò³¿òÇÐ»»
 */
var preTabId = null;
function changeTab(tab){
	if(preTabId == null){
		preTabId = "tab1";
	}
	
	var preTab = document.getElementById(preTabId)

	preTab.childNodes[0].style.display="none";	
	preTab.childNodes[1].style.display="block";
	
	tab.childNodes[0].style.display="block";	
	tab.childNodes[1].style.display="none";
	
	var prediv = document.getElementById("div_"+preTabId);
	var div = document.getElementById("div_"+ tab.id);
	
	prediv.style.display = "none";
	div.style.display = "block";
	
	preTabId = tab.id ;
}