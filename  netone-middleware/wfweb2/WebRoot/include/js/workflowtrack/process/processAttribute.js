/////////���̴���������/////////////
function bean(){
	this.extendObj = document.frames("pExtendFrame");
	this.processId = document.getElementById("processId");
	this.processName = document.getElementById("processName");

	this.processCreateDate = document.getElementById("processCreateDate");
    this.processExtendsName = this.extendObj.document.getElementsByName("processExtendName");
	this.processExtendsValue = this.extendObj.document.getElementsByName("processExtendValue");
}

//////////�༭��������Ǵӱ��л�õ�����//////////
bean.prototype.editGetValue = function (){
	 var processInfo = ""; 
	
	 var id = this.processId.value;
	
	 if(id.trim()==""){
	 	alert("����ID,������Ϊ��!");
	 	return ;
     }
	 var processName = this.processName.value;
     var length = this.processExtendsName.length;
	 var extend="";
	 for(var i=0;i<length;i++){
         extend+=this.processExtendsName[i].value+"&"+this.processExtendsValue[i].value+"|";
	 }
	 processInfo = id+","+this.processName.value+","+extend+","+this.processCreateDate.value;
	 var isCreate=booleanProcess();
	
	    var updataProcess = opener.document.getElementById("updataProcess");
	    var newProcess = opener.document.getElementById("newProcess");
	    var processOpe = newProcess;
	    if(updataProcess!=null){

	      updataProcess.value = processInfo;
	    }else {

	      newProcess.value = processInfo;
	    }
		self.close();
		return;

}

bean.prototype.newGetValue = function (){
	 var processInfo = ""; 
	
	 var id = this.processId.value;
	
	 if(id.trim()==""){
	 	alert("����ID,������Ϊ��!");
	 	return ;
     }
	 var processName = this.processName.value;
     var length = this.processExtendsName.length;
	 var extend="";
	 for(var i=0;i<length;i++){
         extend+=this.processExtendsName[i].value+"&"+this.processExtendsValue[i].value+"|";
	 }
	 processInfo = id+","+this.processName.value+","+extend+","+this.processCreateDate.value;
	 var isCreate=booleanProcess();
	

	var updataProcess = opener.document.getElementById("updataProcess");
    var newProcess = document.getElementById("newProcess");
	newProcess.value = processInfo;
	document.forms["editProcessForm"].submit();

}

//////////////���ñ�///////////////
bean.prototype.reset = function(){
	this.processId.value = "";
	this.processName.value = "";
}

////////////����չ���ԵĲ���///////////
function openWindow(){
}

///////////���½�����///////////
openWindow.prototype.newExtend = function(){
var pathinfo=document.getElementById('pathinfo').value;

window.showModalDialog(pathinfo+"/workflow/resource/track/extend/process/newProcessExtend.html",window,"status:0;help:0;dialogWidth=350px;dialogHeight=250px");

 }

/////////////�򿪱༭��չ���Դ���/////////
openWindow.prototype.editExtend = function(){
   var extendObj = document.frames("pExtendFrame");
   var cleckObj = extendObj.document.getElementsByName("checkExtend");
   var flag = 0;
   var length = cleckObj.length;
   var checkindex;
   for(var i=0;i<length;i++){
     if(cleckObj[i].checked){
            var cando=checkModifyDeleteAvail(cleckObj[i]);
            if(cando=='no'){
            	alert('ϵͳ���Բ��������!');
            	return;
            }
	   flag++;
	}
   }
   if(flag==1){

    var pathinfo=document.getElementById('pathinfo').value;
    window.showModalDialog(pathinfo+"/workflow/resource/track/extend/process/EditProcessExtend.html",window,"status:0;help:0;dialogWidth=350px;dialogHeight=250px");
   
  		   return ;
    }else if(flag==0){
          alert("����ѡ����չ���ԣ�");
	      return;
       }else{
          alert("ֻ�ܱ༭һ����չ���ԣ�");
	    return;
  }
}

/////////////����չ���Ե�ɾ������///////////////
function deleteOpe(){
   var extendObj = document.frames("pExtendFrame");
   var cleckObj = extendObj.document.getElementsByName("checkExtend");
   var length = cleckObj.length;
   var flag = 0;
   for(var i=0;i<length;i++){
     if(cleckObj[i].checked){
       flag++;
      }
    }
   if(length==0){
     alert("��չ����Ϊ�գ��޷�ɾ����");
	 return ;
    }else if(flag==0){ 
           alert("����Ҫѡ��һ��ɾ������");
	       return ;
         }
   if (confirm("ȷ��Ҫִ�д˲���!!!")) {
      for(var i=0;i<length;i++){
           if(cleckObj[i].checked){
            var cando=checkModifyDeleteAvail(cleckObj[i]);
            if(cando=='no'){
            	alert('ϵͳ���Բ��������!');
            	return;
            }
		    var removeObj = (cleckObj[i]).parentElement.parentElement;
		    removeObj.removeChild(cleckObj[i].parentElement);
            cleckObj = extendObj.document.getElementsByName("checkExtend");
		    length--;
		    i=-1;
	     }
      }
   } 
}

function checkModifyDeleteAvail(checkobj){
	var cleckObjKey=checkobj.parentElement.children["processExtendName"].value;
	if('activitycount'==cleckObjKey||'activitymaxcount'==cleckObjKey||'othercount'==cleckObjKey){
		return 'no';
	}
	return 'yes'

}

////////������ʽ///////////
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}

//////////�޸���������/////////////
function updataProcessAttribute(){
 	var  newProcess = opener.document.getElementById("newProcess");
 	var updataProcess = opener.document.getElementById("updataProcess");
 	var processObj = newProcess;
 	if(updataProcess!=null){
  		processObj = updataProcess;
  	}
  	var processArray = processObj.value.split(",");
  	var processExtend = processArray[2].split("|");
  	var extendLength = processExtend.length-1;
  	var processIdObj = document.getElementById("processId");
  	var processNameObj = document.getElementById("processName");
  	var createDate = document.getElementById("processCreateDate");
  	processIdObj.value = processArray[0];
  	processNameObj.value = processArray[1];
  	createDate.value = processArray[3];
  	for(var i=0;i<extendLength;i++){
   		newCreateExtend( processExtend[i].split("&")[0],processExtend[i].split("&")[1])
 	}
}

///////////�½���չ����/////////////
function newCreateExtend(extendName,extendValue){
	var extendObj = document.frames("pExtendFrame");
	var divObj = extendObj.document.createElement("div");
    var trObj=  "<div id='extendProcessList'><tr><td><input name='checkExtend' type='checkbox' ></td>"
            +"<td align='center'><input name='processExtendName' size='27' type='text' name='textfield' value='"+extendName+"' readOnly></td>"
            +"<td align='center'><input name='processExtendValue' size='28' type='text' name='textfield' value='"+extendValue+"' readOnly></td></tr></div>";
	 divObj.innerHTML = trObj;
     extendObj.document.body.appendChild(divObj);
}

///////////��õ�ǰʱ��////////////
function fetchNowTime(){
   var d, s = "";           // ����������
   d = new Date();                           // ���� Date ����
   var year = d.getYear();                         // ��ȡ��ݡ�
   var month = (d.getMonth() + 1)          // ��ȡ�·ݡ�
   var date = d.getDate()                  // ��ȡ�ա�
   var hours = d.getHours()                 //Сʱ
   var minutes = d.getMinutes()              //����
   var seconds = d.getSeconds()             //��
    month=month>9?month:("0"+month)
    date=date>9?date:("0"+date)
    hours=hours>9?hours:("0"+hours)
	minutes=minutes>9?minutes:("0"+minutes)
    seconds=seconds>9?seconds:("0"+seconds)
	s = year+"-"+month+"-"+date+"  "+hours+":"+minutes+":"+seconds;

    return(s);  
}

function makeProcessIdNumber(){
	var d = new Date();
	return d.getTime();
}

////////�½�����//////
function newProcess(){
	var processCreateDate = document.getElementById("processCreateDate");
	var processId=document.getElementById("processId");
	var systemid=document.getElementById("systemid");
    processCreateDate.value = fetchNowTime();
    processId.value=systemid.value+"_wor"+makeProcessIdNumber();
}

/*�ж����½����̣������޸�����*/
function booleanProcess(){
	var url = document.URL;
	var urlArray = url.split("/");
	var flag = urlArray[urlArray.length-1];
   	if(flag=="updataProcess.html"){
     	return "update";
	}else {
	  	return "new";
  	}
}