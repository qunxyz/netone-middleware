//�����������
function updateData(){
			var contextpath=document.getElementById("pathinfo").value;
 	 		document.forms[0].action=contextpath+'/vdata.do?ope=update';
	 		document.forms[0].submit();
}
//����Ƿ���ѡ��Ԫ��
function fetchCheck(){
			var processidTmp =  document.getElementsByName("radioid");
	
		  	for(var i=0;i<processidTmp.length;i++){
		  		if(processidTmp[i].checked){
		  			return processidTmp[i].value;
		  			
		  		}
		  }
}

//ѡ��̬���� 
function choiceprocess(){
		   	var runtimeid=fetchCheck();
		   	if(runtimeid==null){
		   	 alert("��ѡ��һ����������ʵ����");
		   	 return;
		   	}
		   	var contextpath=document.getElementById("pathinfo").value;
 	 		document.forms[0].action=contextpath+"/vprocess.do?runtimeid="+runtimeid;
	 		document.forms[0].submit();
}



//  ��ʾ�����ϸ��Ϣ
function showDetail(runtimeid,activityid){
 	 var contextpath=document.getElementById("pathroot").value;
 	 document.forms[0].action=contextpath+"/commitact.do?runtimeid="+runtimeid+"&activityid="+activityid;
	 document.forms[0].submit();		
}

//��ʾ����Դ���
function showDetailUse(runtimeid,activityid,url){
   var contextpath=document.getElementById("pathroot").value;
   window.open(contextpath+"/commitact.do?runtimeid="+runtimeid+"&activityid="+activityid+"&url="+url,activityid,320,110,false,false);
}
//���̵��Կ���̨����
function todosome(type){
 var contextpath=document.getElementById("pathroot").value;
 var runtimeid=document.getElementById("runtimeid").value;
 var url=contextpath;
 if(type==1){//��������
     
 	url+='/runprocess.do?runtimeid='+runtimeid;
 	window.location.href=url;
 }else if(type==2){//��ʼ������
	url+='/initprocess.do?runtimeid='+runtimeid;
	window.location.href=url;
 }else if(type==3){//������̱�
 	url+='/vdata.do?runtimeid='+runtimeid+'&debug=y';
 	window.open(url,"_blank","width=400,height=300,scrollbars=no,resizable=no");
 }else if(type==4){//����������
 	url+='/listprocess.do?runtimeid='+runtimeid+'&opedo=subflow';
 	window.open(url,"_blank","width=500,height=360,scrollbars=yes,resizable=yes");
 }else if(type==5){//�쿴��ʷ������Ϣ
 	url+='/listprocess.do?runtimeid='+runtimeid+'&opedo=historyinstance';
 	window.open(url,"_blank","width=500,height=360,scrollbars=yes,resizable=yes");
 }
 
}
