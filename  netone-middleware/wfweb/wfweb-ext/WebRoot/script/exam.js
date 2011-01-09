var winId ;
function openWinExam(name,url,allowScroll) {
	var par = ' toolbar=no,location=no,status=no,menubar=no,resizable=yes,fullscreen=yes,';	
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