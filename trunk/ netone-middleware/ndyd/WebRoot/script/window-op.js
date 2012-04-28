var winId ;
function openWinCenter(name,url,winWidth,winHeight,allowScroll) {
   var  left = (screen.width-winWidth)/2;
   var  top = (screen.height-winHeight)/2-10;
    if(left<0){
        left = 5;
    }
    if(top<0){
         top = 5;
    }

	var par = ' toolbar=no,location=no,status=no,menubar=no,resizable=yes,';
	par += ' width=' + winWidth + ', height=' + winHeight + ', left=' + left + ', top='+ top + ',';
	par += ' scrollbars= ' + allowScroll ;

	if(winId) {
    	if(!winId.closed){
	  		winId.close();
     	}
     	winId = window.open(url,name,par);	
  	} else {	
    	winId = window.open(url,name,par);
  	}
	
	winId.opener = window.self;
}
var mwin;
/**
window.showModalDialog参数说明：
url--
必选参数，类型：字符串。用来指定对话框要显示的文档的URL。
arguments--
可选参数，类型：变体。用来向对话框传递参数。传递的参数类型不限，包括数组等。对话框通过window.dialogArguments来取得传递进来的参数。
sFeatures--
可选参数，类型：字符串。用来描述对话框的外观等信息，可以使用以下的一个或几个，用分号“;”隔开。
**/
function openModalDialog(url,arguments,width,height){//打开模式窗口
	mwin=window.showModalDialog(url, arguments, "dialogWidth="+width+"px;dialogHeight="+height+"px");
}

/** 打开fck编辑窗口 */
function openwindowFckEdit(path){
	var popupwin;
	popupwin = window.open('','','width=800,height=400,left=260,top=260,scrollbars=auto');
	popupwin.location.href = path;
}


function  resetForm(form){
	var form = form || document.getElementById(form);
	if(typeof (form) == 'undefined'){
		 form = document.getElementsByName(form)[0];
	}
	
	var inputObjSet = form.getElementsByTagName('input');
	for(var i=0; i < inputObjSet.length; i++){
		inputObjSet[i].value = ''; 
	}
	
	var textareaSet = form.getElementsByTagName('textarea');
	for(var j=0;  j < textareaSet.length; j++){
		textareaSet[j].value= '';
	}
}
