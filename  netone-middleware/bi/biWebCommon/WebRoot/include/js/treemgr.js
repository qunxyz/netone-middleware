
var selectobj ;
var offsetX;
var offsetY;

function click(){
	selectobj = window.event.srcElement;
	offsetX = window.event.offsetX;
	offsetY = window.event.offsetY;
	rightMouseEvent();
	leftMouseEvent();
}

function rightMouseEvent(){
	if(selectobj.id != null){
	 	var objid = selectobj.id;
	 	var menuLeft = event.clientX;
        var menuTop =  event.clientY;
        
        popmenu1.style.display="none";
        
	 	if(objid.indexOf("folder")!=-1){
	 		//文件夹
	 		popmenu1.style.left=menuLeft;
	 		popmenu1.style.top = menuTop;
	 		popmenu1.style.display="block";
	 	}
	 	else if(objid.indexOf("busobj")!=-1){
	 		//业务节点
	 	
	 	}
	}
}


function  mouseAction(){
	
	rightMouseEvent();

}