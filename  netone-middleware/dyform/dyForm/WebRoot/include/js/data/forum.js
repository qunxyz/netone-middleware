function checkBoxCheck(){}
checkBoxCheck.prototype.isValid=function(){
  	 	 return confirm("???????");
}

win.validates['deleteConfig']=new checkBoxCheck();


function createdo(){
	    var lsh=makelsh();
	    
	  	this.document.forms[0].action = "/dyForm/data/showdata/createforumview.do?lsh="+lsh;
		this.document.forms[0].method = "post";
		this.document.forms[0].target = "_self";
		this.document.forms[0].submit();
	   
}

function makelsh(){
	var d = new Date();
	return d.getTime();
}
