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
