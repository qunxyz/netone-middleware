		 
function choiceprocess0(processid){
		  var url= pathinfo+'listBiRuntimeprocess.do?processid='+processid;
		  window.open(url,"_blank","toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

function choiceprocess1(processid){
		  var url= pathinfo+'/viewprocess.do?processid='+processid;
		  window.open(url,"_blank","toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}



function choiceprocess2(processid){

		  var url= pathinfo+'/viewreadonlyprocess.do?processid='+processid;
		  window.open(url,"_blank","toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

function choiceprocess3(processid){

		  var url= pathinfo+'/useprocess.do?processid='+processid;
		  window.open(url,"_blank","toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

function choiceprocess4(processid){
                  
		  var url= pathinfo+'/listUseprocess.do?processid='+processid;
		  window.open(url,"_blank","toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
}

function choiceprocess5(processid,name){
 			
		  var url=name+'/PLShow.html?naturalname='+processid;
		   window.open(url,"_blank");
}

function choiceprocess6(processid,name){
		  var url= name+'/BiAnalysis.html?naturalname='+processid;
		  window.open(url,"_blank");
}

function newprocess(pagepath){
		
		  var url= pathinfo+'newprocessfile.do?pagepath='+pagepath;
		
		  window.open(url,"_blank","left=200,top=200,height=500,height=400,toolbar=no, menubar=no,scrollbars=yes, resizable=yes");
		
}
		
function downprocess(processid){

		  var url= pathinfo+'/dowloadprocess.do?processid='+processid;
		  window.open(url,"_blank");
}
function dropprocess(processid){

		  var url= pathinfo+'/dropprocessfile.do?processid='+processid;
		  window.open(url,"_self","width=500,height=400");
}
