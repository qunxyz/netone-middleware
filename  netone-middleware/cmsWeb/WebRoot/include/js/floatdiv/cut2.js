


function getPosition(){
	var realsrc = $("realsrc");
	var ifarme = $("ifarme");
	var cutdiv = $("cutdiv");

	var id = $("id").value;

	if(!realsrc.value){
		alert("未输入网址!");
		return ;
	}
	
	if(cutdiv.offsetWidth<10){
		alert("未选择区域！");
		return ;
	}

	var y = getTop(cutdiv)-getTop(ifarme);
	var x = getLeft(cutdiv) ;
	var pl = "foffset,"+x+":"+y;
	var pr = "toffset,"+(x+cutdiv.offsetWidth)+":"+(y+cutdiv.offsetHeight);
	var url = "url,"+realsrc.value;
	$("args").value = pl+";"+pr+";"+url ;
	
			//最终结束的时候相关的处理逻辑需要提交给Servlet来完成                          
			this.document.forms[0].action='/cmsWeb/servlet/AjaxPageItemSvl';
			//将相关的表单参数全部传递给上面的Servlet
			this.document.forms[0].submit();

	
	

}

function setiframesrc(){
	var ifarme = $("ifarme");
	var srctext = $("srctext");
	var realsrc = $("realsrc");
	realsrc.value = srctext.value;
	ifarme.src = srctext.value;
}


/*********************/
var cutting = false ;
var synlock = false ;
Event.observe(window,"load",winonload,false);

function winonload(){ 
	initoverdiv();
	initcutdiv();
}

function initoverdiv(){
	Event.observe($("overdiv"),"mousedown",overdivmousedown);
}

function initcutdiv(){
	var cutdiv = $("cutdiv");
	Event.observe(cutdiv,"dblclick",getPosition,false);
	Event.observe(cutdiv,"mousedown",cutdivmousedown,false);
}


function overdivmousedown(){
	cutting = true ;
	document.body.style.cursor="crosshair";
	var cutdiv = $("cutdiv");
	cutdiv.style.display="block";
	cutdiv.style.width="0px";
	cutdiv.style.height="0px";
	cutdiv.style.top = Event.pointerY(event)-1;
	cutdiv.style.left = Event.pointerX(event)-1;
	
	var sizediv = $("sizediv");
	sizediv.style.display = "";
	sizediv.innerHTML="<span style='font-size: 15px;width:50;height:20;'>"+0+","+0+"</span>"+
								 "<input type='button' value='提交' onclick='getPosition()' />";
	sizediv.style.left = Event.pointerX(event);
	sizediv.style.top = Event.pointerY(event);
	
	Event.observe(document,"mousemove",overdivmousemove);
	Event.observe(document,"mouseup",overdivmouseup);
}

function overdivmousemove(){
	if(cutting){
		if(!synlock){
			synobj = true;
			var cutdiv = $("cutdiv");
			var offx = Event.pointerX(event) - getLeft(cutdiv);
			var offy = Event.pointerY(event) - getTop(cutdiv);
			if( offx>0 && offy>0 ){
				cutdiv.style.width=offx;
				cutdiv.style.height=offy;
			}
			
			var sizediv = $("sizediv");
			sizediv.innerHTML="<span style='font-size: 15px;width:50;height:20;'>"+offx+","+offy+"</span>"+
								 "<input type='button' value='提交' onclick='getPosition()' />";
			sizediv.style.left = Event.pointerX(event);
			sizediv.style.top = Event.pointerY(event);
			synobj = false;
		}
	}
}

function overdivmouseup(){
	if(cutting){
		cutting = false ;		
		document.body.style.cursor="default";	
		Event.stopObserving(document,"mousemove",overdivmousemove);
	    Event.stopObserving(document,"mouseup",overdivmouseup);	
	}
}

function cutdivmousedown(){
	if(event.button == 1){
		document.body.style.cursor="crosshair";
		cutting=true;
		Event.observe(document,"mousemove",overdivmousemove);
		Event.observe(document,"mouseup",overdivmouseup);
		overdivmousemove();
	}
}
