function checkBoxCheck(){}
checkBoxCheck.prototype.isValid=function(){
  	 	 return confirm("���Ҫɾ����");
}

win.validates['deleteConfig']=new checkBoxCheck();


function createdo(){
	   
	    var fatherlsh=document.getElementById('fatherlsh').value;
	    var formcode=document.getElementById('formcode').value;

		window.open("/dyForm/data/showdata/createview.do?fatherlsh="+fatherlsh+'&formcode='+formcode,"newdata");
	   
}

function modifydo(){
	   
	    var fatherlsh=document.getElementById('fatherlsh').value;
	    var formcode=document.getElementById('formcode').value;
	    
	var k = 0;
	var check=document.getElementsByName('lsh');
	var lsh;
	for(var i=0 ; i<check.length ; i++) {
			if(check[i].checked==true){
				lsh = check[i].value;
				k++;
			}
	}
	if(k==0){
		alert("����ѡ��һ������");
		return;
	}
	if(k>1){
		alert("ֻ��ѡ��һ������");
		return;
	} 

	window.open("/dyForm/data/showdata/modifyview.do?fatherlsh="+fatherlsh+'&formcode='+formcode+'&lsh='+lsh,"newdata");
	   
}

function modifydoview(lsh){
	   
	    var fatherlsh=document.getElementById('fatherlsh').value;
	    var formcode=document.getElementById('formcode').value;

	window.open("/dyForm/data/showdata/modifyview.do?fatherlsh="+fatherlsh+'&formcode='+formcode+'&lsh='+lsh,"newdata");
	   
}


function makelsh(){
	var d = new Date();
	return d.getTime();
}


