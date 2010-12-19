//显示自己的JPP模板DEMO
function dispJppScriptDemoSelf() {
	var parser = new Ajax.Request("/cmsWeb/servlet/AjaxJppListMoreSvl", {method:"get", parameters:"onlyme=yes", asynchronous:false});
	var restr = parser.transport.responseText;
	var sHTML = restr;
	JppScriptDemoDIY.innerHTML = sHTML;
}
function add() {
	window.open("/cmsWeb/cellModelDIY.do?opeType=view", "JppModelDiyBodyFrame");
}

function edit() {
	var cellmodel = $("selJppJsMore");
	if('0'==cellmodel.value){
		alert('请选择');
		return;
	}
	var cellmodelid=cellmodel.value;
	window.open("/cmsWeb/cellModelDIY.do?opeType=view&jppmid=" + cellmodelid+"&editindex="+cellmodel.selectedIndex, "JppModelDiyBodyFrame");

}
function dele() {
	var cellmodel = $("selJppJsMore");
	if('0'==cellmodel.value){
		alert('请选择');
		return;
	}
	window.open("/cmsWeb/cellModelDIY.do?opeType=dele&jppmid=" + cellmodel.value, "JppModelDiyBodyFrame");
	cellmodel.remove(cellmodel.selectedIndex);
	cellmodel.options[0].selected;
}



