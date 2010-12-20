
//在界面上隐蔽一个按钮,该按钮被一个Selvet所调用,实现不同页面间的交互
function addgroupcore(){
	var targetdiv = $("container").firstChild;
	if(targetdiv){
		var select = $("valueid").value;
		if(select==''){
			return;
		}
		addDiv(select,select,$(targetdiv));
	}
	else{
		alert("请先选择一个Poterlet！");
	}
}

function addgroup(){
   var selJs=$('selJs').value;
   if(selJs=='0'){
   	   alert('请先选择元素');
   	   return;
   }
   $("valueid").value=selJs;
   addgroupcore();
}


function dispLayOut(){
	
	if($('dispSuper').checked){
		
		$('superDesign').style.display='';
		$('superDesignHr').style.display='';
		return;
		
		 var parser = new Ajax.Request(
						"/cmsWeb/servlet/AjaxCellListSvl",
						{method:"get",parameters:"elementType=BLK",asynchronous:false}
						);
		 var restr = parser.transport.responseText;
		 var sHTML=restr;
		 DivBlock.innerHTML = sHTML ;
	}else{
		$('superDesign').style.display='none';
		$('superDesignHr').style.display='none';
	}

}

function savelayout(){

	var url = keepSessionUrl("/servlet/DivLayoutSvl");

	var pars = "layout="+encodeURI(getLayout())+"&modelid="+modelid;

    var layoutajax = new Ajax.Request(
	                 url,
	                 {method: 'get', parameters: pars, onComplete: endsavelayout}
	                 );
}

function reView(){
	window.open('vs.do?model=ok&id='+modelid);
}

function removeFDiv(focusdiv){
	var fdiv = focusdiv.parentNode.parentNode;

	Element.remove(fdiv);
//	var select = $("noshowgroup");
//	var option = document.createElement("option");
//	option.text = fdiv.name;
//	option.value = groupid;
//	select.add(option);
}

function removeFDiv2(focusdiv){
	var fdiv = focusdiv;
	Element.remove(fdiv);
//	var select = $("noshowgroup");
//	var option = document.createElement("option");
//	option.text = fdiv.name;
//	option.value = groupid;
//	select.add(option);
}


function endsavelayout(xmlhttp){
	//alert(xmlhttp.responseText);
}


function getLayout(){
	var boxdiv = $("container").childNodes;
	var layout = "";
	var perwidth = "";
	for(var i=0 ; i<boxdiv.length ; i++){
		if(boxdiv[i].id){
			var children = boxdiv[i].childNodes;
			var str = "";
			for(var j=0 ; j<children.length ; j++){
				if(children[j].id){
					str += children[j].id+",";
				}
			}
			layout += str+";";
			
			if(i!=0){
				perwidth += ",";
			}
			perwidth += Resize.getPercentSize(boxdiv[i]);
		}
	}
	layout += "#"+perwidth;
	return layout;
}


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

function addDivPortal(groupid,groupname,pardiv,mode){

	var templatediv = $("templatediv");
	var newdiv = templatediv.cloneNode();
	newdiv.innerHTML = templatediv.innerHTML;
	newdiv.id = groupid;
	newdiv.name = groupname;
	newdiv.style.display = "block";
	$(pardiv).appendChild(newdiv);
	setDivInnerHtmlPortal(newdiv,mode);
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

function refreshdivView(div){
	var div1 = $(div);
	if(div1){
		div1.innerHTML="loading...";
		setDivInnerHtmlPortal(div1,3);
	}
	else{
		alert("没有找到要刷新的模块！")
	}
}



function addBoxdiv(){
	var container = $("container");
	var tmpboxdiv = $("tmpboxdiv");
	var newdiv = tmpboxdiv.cloneNode();
	newdiv.id = "boxdiv"+(container.childNodes.length+1);
	newdiv.style.display = "block";
	container.appendChild(newdiv);
	Resize.setResizeAble(newdiv);
	preboxdiv = newdiv.previousSibling;
	if(preboxdiv){
		preboxdiv.style.posWidth  = preboxdiv.style.posWidth - 5;
		Resize.cancelResizeAble(preboxdiv);
		Resize.setResizeAble(preboxdiv,newdiv);
	}
}

function removeBoxdiv(){
	var container = $("container");
	var lastdiv = container.lastChild;
	if(lastdiv){
		if(lastdiv.childNodes.length != 0){
			for(var m=0 ; m<lastdiv.childNodes.length; m++){
				if(lastdiv.childNodes[m].id){
					alert("最后一列中含有内容，请先清空列再删除列！")
					return ;
				}
			}
		}
		var preboxdiv = lastdiv.previousSibling;
		if(preboxdiv){
			Resize.cancelResizeAble(preboxdiv);	
			Resize.setResizeAble(preboxdiv);
		}
		Resize.cancelResizeAble(lastdiv);
		Element.remove(lastdiv);
	}
	
}

function avgBoxdiv(){
	var container = $("container");
	var children = container.childNodes;
	var size = children.length;
	if(size > 0){
		var perwidth = (99/size).toFixed(1)+ "%";
		for(var i=0 ;i<size ; i++){
			children[i].style.width = perwidth;
		}
	}
}

/******** 页面事件 end ********/

/******** drag&drop begin ********/
var dragingdiv ;
var clonediv ;
var synlock = false ;

Event.observe(window,"load",init);

function init(){
}

function initfloatdiv(div){
	//添加事件
	var templatediv = div.firstChild;
	Event.observe(templatediv,"mouseover",div_mouseover);
	Event.observe(templatediv,"mouseleave",div_mouseleave);
	Event.observe(templatediv,"mousedown",div_mousedown);
}

function setDivInnerHtml(div){
	var url = keepSessionUrl("/servlet/DivInnerHtmlSvl");
	var pars = "groupid="+div.id+"&modelid="+modelid;
	var divajax = new Ajax.Updater(
				div,
	            url,
	            {method: 'get', parameters: pars , evalScripts:true, onComplete: endSetDivHtml(div)}
	            );
}

function setDivInnerHtmlPortal(div,mode){
	var url = keepSessionUrl("/servlet/DivInnerHtmlPortalSvl");
	var pars = "groupid="+div.id+"&mode="+mode;
	var divajax = new Ajax.Updater(
				div,
	            url,
	            {method: 'get', parameters: pars , evalScripts:true, onComplete: endSetDivHtml(div)}
	);
}

function endSetDivHtml(div){
	return function(xmlhttp){
		initfloatdiv(div);
		log(div.outerHTML);
	};
}

function div_mouseover(){
	var divid = Event.element(event).id;
	changeCursor("move");
}

function div_mouseleave(){
	var divid = Event.element(event).id;
	changeCursor("default");
}

function findDivElement(event){
	var element = Event.element(event);
	log( element.className);
    if(element.className == "focusdiv"){
    	return element.parentNode.parentNode ;
    }
    return null;
}

function div_mousedown(){
	var div = findDivElement(event);
	if(!div) {
		return ;
	}
	var divNextSib = div.nextSibling;
	var pardiv = div.parentNode;
	
	dragingdiv = new DragingDiv(div);
	var x = Event.pointerX(event);
	var y = Event.pointerY(event);
	var divx = getLeft(div);
	var divy = getTop(div);
	div.style.position = "absolute";
	div.style.left = divx ;
	div.style.top = divy ;
	dragingdiv.mouseoffsetx = x - divx ;
	dragingdiv.mouseoffsety = y - divy ;
	dragingdiv.middlexy = [divx+dragingdiv.div.offsetWidth/2,
		                   divy+dragingdiv.div.offsetHeight/2];
	
	Event.observe(document,"mousemove",doc_mousemove);
	Event.observe(document,"mouseup",doc_mouseup);

	clonediv = document.createElement("div");
	clonediv.id = "tmpdiv";
	Position.clone(div,clonediv);
	clonediv.style.border = "2 dashed red";
	clonediv.style.position = "static";
	//clonediv.innerHTML = div.innerHTML ;
	
	if(divNextSib){
		pardiv.insertBefore(clonediv,divNextSib);
	}
	else{
		pardiv.appendChild(clonediv);
	}
}


function doc_mouseup(){
	if(dragingdiv){
		//把div放到原位
		dragingdiv.div.style.position = "static";
		if(clonediv.nextSibling){
			clonediv.parentNode.insertBefore(dragingdiv.div,clonediv.nextSibling);
		}
		else{
			clonediv.parentNode.appendChild(dragingdiv.div);
		}
		clonediv.parentNode.removeChild(clonediv);
		
		//还原状态
		changeCursor("default");
		dragingdiv = null ;
		clonediv = null ;
		synlock = false;
		Event.stopObserving(document,"mousemove",doc_mousemove);
		Event.stopObserving(document,"mouseup",doc_mouseup);
		
	}
}

function doc_mousemove(){
	if(dragingdiv){
		if(!synlock){
			synlock = true ;
			var ediv = dragingdiv.div;
			var x = Event.pointerX(event);
			var y = Event.pointerY(event);
			dragingdiv.divxy = [x - dragingdiv.mouseoffsetx,y - dragingdiv.mouseoffsety];
			dragingdiv.middlexy = [dragingdiv.divxy[0]+dragingdiv.div.offsetWidth/2,
			                       dragingdiv.divxy[1]+dragingdiv.div.offsetHeight/2];
			ediv.style.left = dragingdiv.divxy[0];
			ediv.style.top =  dragingdiv.divxy[1];
			setCloneDivPosi();
			synlock = false ;
		}
	}
}


function setCloneDivPosi(){
	if(clonediv){
		//先判断在哪个大的div中
		var pardiv = clonediv.parentNode;
		if(middlexyInDiv(pardiv)){
		}
		else{
			var parpardiv = pardiv.parentNode;
			var ppchild = parpardiv.childNodes;
			for(var i=0 ;i<ppchild.length ; i++){
				if(ppchild[i]!=pardiv){
					if(middlexyInDiv(ppchild[i])){
						ppchild[i].appendChild(clonediv);
						pardiv = ppchild[i];
						break;
					}
				}
			}
		}
		
		if(!middlexyInDiv(clonediv)){
			var pchild = pardiv.childNodes;
			for(var i=0 ; i<pchild.length ; i++){
				if(pchild[i] == dragingdiv.div ){
					continue;
				}
				if(middlexyInDiv(pchild[i])){
					var pcy = getTop(pchild[i])+pchild[i].offsetHeight/2;
					if(pcy>dragingdiv.middlexy[1]){
						if(pchild[i].previousSibling != clonediv){
							pardiv.insertBefore(clonediv,pchild[i]);
						}
					}
					else{
						if(pchild[i].nextSibling){
							if(pchild[i].nextSibling != clonediv){	
								pardiv.insertBefore(clonediv,pchild[i].nextSibling);
							}
						}
						else{
							pardiv.appendChild(clonediv)
						}
					}
					break;
				}
			}
		}
	}
}


function middlexyInDiv(ele){
	if(dragingdiv){
		return Position.within(ele,dragingdiv.middlexy[0],dragingdiv.middlexy[1]);
	}
}

function DragingDiv(div){
	this.div = div ;
	this.mouseoffsetx = 0;
	this.mouseoffsety = 0;
	this.divxy = [0,0];
	this.middlexy = [0,0];
}

/******** drag&drop end ********/

/******** 工具 ********/
function changeCursor(type){
	document.body.style.cursor=type;
}

function keepSessionUrl(url){
	return contextpath+url+";jsessionid="+sessionid;
}

function getNextSibling(ele){
	ele = $(ele);
	if(ele){
		return ele.nextSibling;
	}
}
