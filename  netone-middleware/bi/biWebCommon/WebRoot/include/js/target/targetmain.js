
var targetcollist = new Array();


function addTgcol(tgcol){
   	targetcollist.length ++;
   	targetcollist[targetcollist.length-1] = tgcol ;
}


function deletetgcol(indexs){
	for(i=0 ; i<indexs.length ;i++){	
		delete targetcollist[indexs[i]];
	}
	
	var tmp = new Array() ;
	
	for(j =0 ; j<targetcollist.length ; j++){	
		if(targetcollist[j]){	
			tmp.length++;
			tmp[tmp.length-1] = targetcollist[j];
		}
	}
	
	targetcollist = tmp ;
	
}




function getColByDssetId(dssetid){	

	xmldoc = xmldatalist.documentElement ;
	
	var node = xmldoc.selectSingleNode("//dataset[@id='"+dssetid+"']");
	if(node){
		var arr = new Array();
		var nodelist = node.childNodes;
		for(i=0 ; i<nodelist.length ; i++){
			var colid = nodelist[i].getAttribute("id");
			arr.length++;
			arr[arr.length-1]=colid ;
		}
		return arr ;
	}
	
}
