

function commit(runtimeid,activityid){
 	 var contextpath=document.getElementById("pathroot").value;
 	 var url=contextpath+"/useAccessCommit.do?runtimeid="+runtimeid+"&activityid="+activityid;
	 
	 //window.open(url,'',"left=300,top=300,width=400,height=300,scrollbars=no,resizable=no");
	 opener.location.href=url;
	 window.close();	
}

function jump(runtimeid,activityid){
 	 var contextpath=document.getElementById("pathroot").value;
 	 var activityto=document.getElementById("activityto").value;
 	 var url=contextpath+"/useAccessCommit.do?runtimeid="+runtimeid+"&activityid="+activityid+"&activityto="+activityto;
	 opener.location.href=url;
	 //window.open(url,'_useflow',"left=300,top=300,width=400,height=300,scrollbars=no,resizable=no");
	 window.close();
}

//  显示活动的详细信息
function showDetail(runtimeid,activityid){
 	 var contextpath=document.getElementById("pathroot").value;
 	 var url=contextpath+"/useAccess.do?runtimeid="+runtimeid+"&activityid="+activityid;
 	 
 	 window.open(url,'_blank',"left=300,top=300,width=500,height=300,scrollbars=yes,resizable=yes");
	
}



