
var targetcollist = new Array();

function targetcol(){
    var datasetid ;
    var tgcolid ;
    var tgcolname ;
    var unitedrule ;
    var extcondi ;
    var dataarea ;
    var extattr ;
    var desc ;
}


function addTgcol(tgcol){
   	targetcollist.length ++;
   	targetcollist[targetcollist.length-1] = tgcol ;
}

function test(){
   
    for( var i=0;i<3;i++){
	    var tgcol = new targetcol();
	    tgcol.datasetid = "datasetid"+i;
	    tgcol.tgcolid ="tgcolid"+i;
	    tgcol.tgcolname = "tgcolname" + i;
	    tgcol.unitedrule = "unitedrule" + i;
	    tgcol.extcondi = "extcondi" + i;
	    tgcol.dataarea = "dataarea" + i;
	    tgcol.extattr =  "extattr" + i;
	    tgcol.desc =  "desc" + i;
	  
	    addTgcol(tgcol);
    }
}