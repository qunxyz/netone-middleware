//更新相关数据
function updateData(){
			var contextpath=document.getElementById("pathinfo").value;
 	 		document.forms[0].action=contextpath+'/vdata.do?ope=update';
	 		document.forms[0].submit();
}
//检查是否有选择元素
function fetchCheck(){
			var processidTmp =  document.getElementsByName("radioid");
	
		  	for(var i=0;i<processidTmp.length;i++){
		  		if(processidTmp[i].checked){
		  			return processidTmp[i].value;
		  			
		  		}
		  }
}

//选择动态流程 
function choiceprocess(){
		   	var runtimeid=fetchCheck();
		   	if(runtimeid==null){
		   	 alert("请选择一个流程运行实例！");
		   	 return;
		   	}
		   	var contextpath=document.getElementById("pathinfo").value;
 	 		document.forms[0].action=contextpath+"/vprocess.do?runtimeid="+runtimeid;
	 		document.forms[0].submit();
}



//  显示活动的详细信息
function showDetail(runtimeid,activityid){
 	 var contextpath=document.getElementById("pathroot").value;
 	 document.forms[0].action=contextpath+"/commitact.do?runtimeid="+runtimeid+"&activityid="+activityid;
	 document.forms[0].submit();		
}

//显示活动调试窗口
function showDetailUse(runtimeid,activityid,url){
   var contextpath=document.getElementById("pathroot").value;
   window.open(contextpath+"/commitact.do?runtimeid="+runtimeid+"&activityid="+activityid+"&url="+url,activityid,320,110,false,false);
}
//流程调试控制台操作
function todosome(type){
 var contextpath=document.getElementById("pathroot").value;
 var runtimeid=document.getElementById("runtimeid").value;
 var url=contextpath;
 if(type==1){//启动流程
     
 	url+='/runprocess.do?runtimeid='+runtimeid;
 	window.location.href=url;
 }else if(type==2){//初始化流程
	url+='/initprocess.do?runtimeid='+runtimeid;
	window.location.href=url;
 }else if(type==3){//监控流程表单
 	url+='/vdata.do?runtimeid='+runtimeid+'&debug=y';
 	window.open(url,"_blank","width=400,height=300,scrollbars=no,resizable=no");
 }else if(type==4){//调试子流程
 	url+='/listprocess.do?runtimeid='+runtimeid+'&opedo=subflow';
 	window.open(url,"_blank","width=500,height=360,scrollbars=yes,resizable=yes");
 }else if(type==5){//察看历史流程信息
 	url+='/listprocess.do?runtimeid='+runtimeid+'&opedo=historyinstance';
 	window.open(url,"_blank","width=500,height=360,scrollbars=yes,resizable=yes");
 }
 
}
