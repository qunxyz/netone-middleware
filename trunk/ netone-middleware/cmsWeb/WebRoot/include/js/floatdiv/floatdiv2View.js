
/***** designSimple Use ********/
function newweb(path) {
	var url = document.getElementById("srctext").value;
	window.open(path + "/floatdiv/cutWeb.jsp?srcinfo=" + url + "&modelid=" + modelid);
}
function designFace() {
	window.open("/cmsWeb/indexTree.html");
}
function layoutman(path) {
	window.open(path + "/showFloatDiv2.do?man=1&modelid=" + modelid);
}
/***** designSimple Use ********/
function addgroupsimple() {
	var targetdiv = $("container").firstChild;
	if (targetdiv) {
		var select = $("noshowgroup");
		var groupid = select.value;
		var groupname = select.options[select.selectedIndex].text;
		select.remove(select.selectedIndex);
		select.selectedIndex = 0;
		addDiv(groupid, groupname, $(targetdiv));
	} else {
		alert("\u8bf7\u5148\u9009\u62e9\u4e00\u4e2aPoterlet\uff01");
	}
}
/******** 页面事件 begin ********/
function addgroup() {
	var targetdiv = $("container").firstChild;
	if (targetdiv) {
		var select = $("noshowgroup");
		var groupid = select.value;
		var groupname = select.options[select.selectedIndex].text;
		select.remove(select.selectedIndex);
		select.selectedIndex = 0;
		addDiv(groupid, groupname, $(targetdiv));
	} else {
		alert("\u8bf7\u5148\u9009\u62e9\u4e00\u4e2aPoterlet\uff01");
	}
}
function removeFDiv(focusdiv) {
	var fdiv = focusdiv.parentNode.parentNode;
	var groupid = fdiv.id;
	Element.remove(fdiv);
	var select = $("noshowgroup");
	var option = document.createElement("option");
	option.text = fdiv.name;
	option.value = groupid;
	select.add(option);
}
function endsavelayout(xmlhttp) {
	alert(xmlhttp.responseText);
}
function getLayout() {
	var boxdiv = $("container").childNodes;
	var layout = "";
	var perwidth = "";
	for (var i = 0; i < boxdiv.length; i++) {
		if (boxdiv[i].id) {
			var children = boxdiv[i].childNodes;
			var str = "";
			for (var j = 0; j < children.length; j++) {
				if (children[j].id) {
					str += children[j].id + ",";
				}
			}
			layout += str + ";";
			if (i != 0) {
				perwidth += ",";
			}
			perwidth += Resize.getPercentSize(boxdiv[i]);
		}
	}
	layout += "#" + perwidth;
	return layout;
}
function addDiv(groupid, groupname, pardiv) {
	var templatediv = $("templatediv");
	var newdiv = templatediv.cloneNode(true);//兼容FF don modify
	newdiv.innerHTML = templatediv.innerHTML;
	newdiv.id = groupid;
	newdiv.name = groupname;
	newdiv.style.display = "block";
	$(pardiv).appendChild(newdiv);
	setDivInnerHtml(newdiv);
	return newdiv;
}
function refreshdiv(div) {
	var div1 = $(div);
	if (div1) {
		div1.innerHTML = "loading...";
		setDivInnerHtml(div1);
	} else {
		alert("\u6ca1\u6709\u627e\u5230\u8981\u5237\u65b0\u7684\u6a21\u5757\uff01");
	}
}
function addBoxdiv() {
	var container = $("container");
	var tmpboxdiv = $("tmpboxdiv");
	var newdiv = tmpboxdiv.cloneNode(true);//兼容FF don modify
	newdiv.id = "boxdiv" + (container.childNodes.length + 1);
	newdiv.style.display = "block";
	container.appendChild(newdiv);
	Resize.setResizeAble(newdiv);
	preboxdiv = newdiv.previousSibling;
	if (preboxdiv) {
		preboxdiv.style.posWidth = preboxdiv.style.posWidth - 5;
		Resize.cancelResizeAble(preboxdiv);
		Resize.setResizeAble(preboxdiv, newdiv);
	}
}
function removeBoxdiv() {
	var container = $("container");
	var lastdiv = container.lastChild;
	if (lastdiv) {
		if (lastdiv.childNodes.length != 0) {
			for (var m = 0; m < lastdiv.childNodes.length; m++) {
				if (lastdiv.childNodes[m].id) {
					alert("\u6700\u540e\u4e00\u5217\u4e2d\u542b\u6709\u5185\u5bb9\uff0c\u8bf7\u5148\u6e05\u7a7a\u5217\u518d\u5220\u9664\u5217\uff01");
					return;
				}
			}
		}
		var preboxdiv = lastdiv.previousSibling;
		if (preboxdiv) {
			Resize.cancelResizeAble(preboxdiv);
			Resize.setResizeAble(preboxdiv);
		}
		Resize.cancelResizeAble(lastdiv);
		Element.remove(lastdiv);
	}
}
function avgBoxdiv() {
	var container = $("container");
	var children = container.childNodes;
	var size = children.length;
	if (size > 0) {
		var perwidth = (99 / size).toFixed(1) + "%";
		for (var i = 0; i < size; i++) {
			children[i].style.width = perwidth;
		}
	}
}
/******** 页面事件 end ********/
/******** drag&drop begin ********/
var dragingdiv;
var clonediv;
var synlock = false;
Event.observe(window, "load", init);
function init() {
}
function initfloatdiv(div) {
	//添加事件
	var templatediv = div.firstChild;
	Event.observe(templatediv, "mouseover", div_mouseover);
	Event.observe(templatediv, "mouseleave", div_mouseleave);
	Event.observe(templatediv, "mousedown", div_mousedown);
}
function setDivInnerHtml(div) {
	var url = keepSessionUrl("/servlet/ShowInnerHtmlSvl2");
	var pars = "groupid=" + div.id + "&modelid=" + modelid;
	var divajax = new Ajax.Updater(div, url, {method:"get", parameters:pars, evalScripts:true, onComplete:endSetDivHtml(div)});
}
function endSetDivHtml(div) {
	return function (xmlhttp) {
		initfloatdiv(div);
		log(div.outerHTML);
	};
}
function div_mouseover() {
}
function div_mouseleave() {
}
function findDivElement(event) {
	var element = Event.element(event);
	log(element.className);
	if (element.className == "focusdiv") {
		return element.parentNode.parentNode;
	}
	return null;
}
function div_mousedown() {
}
function doc_mouseup() {
}
function doc_mousemove() {
}
function setCloneDivPosi() {
	if (clonediv) {
		//先判断在哪个大的div中
		var pardiv = clonediv.parentNode;
		if (middlexyInDiv(pardiv)) {
		} else {
			var parpardiv = pardiv.parentNode;
			var ppchild = parpardiv.childNodes;
			for (var i = 0; i < ppchild.length; i++) {
				if (ppchild[i] != pardiv) {
					if (middlexyInDiv(ppchild[i])) {
						ppchild[i].appendChild(clonediv);
						pardiv = ppchild[i];
						break;
					}
				}
			}
		}
		if (!middlexyInDiv(clonediv)) {
			var pchild = pardiv.childNodes;
			for (var i = 0; i < pchild.length; i++) {
				if (pchild[i] == dragingdiv.div) {
					continue;
				}
				if (middlexyInDiv(pchild[i])) {
					var pcy = getTop(pchild[i]) + pchild[i].offsetHeight / 2;
					if (pcy > dragingdiv.middlexy[1]) {
						if (pchild[i].previousSibling != clonediv) {
							pardiv.insertBefore(clonediv, pchild[i]);
						}
					} else {
						if (pchild[i].nextSibling) {
							if (pchild[i].nextSibling != clonediv) {
								pardiv.insertBefore(clonediv, pchild[i].nextSibling);
							}
						} else {
							pardiv.appendChild(clonediv);
						}
					}
					break;
				}
			}
		}
	}
}
function middlexyInDiv(ele) {
	if (dragingdiv) {
		return Position.within(ele, dragingdiv.middlexy[0], dragingdiv.middlexy[1]);
	}
}
function DragingDiv(div) {
	this.div = div;
	this.mouseoffsetx = 0;
	this.mouseoffsety = 0;
	this.divxy = [0, 0];
	this.middlexy = [0, 0];
}
/******** drag&drop end ********/
/******** 工具 ********/
function changeCursor(type) {
	document.body.style.cursor = type;
}
function keepSessionUrl(url) {
	return contextpath + url + ";jsessionid=" + sessionid;
}
function getNextSibling(ele) {
	ele = $(ele);
	if (ele) {
		return ele.nextSibling;
	}
}


