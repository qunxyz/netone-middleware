var opener = window.dialogArguments;

function event(){
 this.actionExtendName = document.getElementById("actionExtendName");
 this.actionExtendValue = document.getElementById("actionExtendValue");
}

/*--  ����--*/
event.prototype.reset = function(){
  this.actionExtendName.value = "";
  this.actionExtendValue.value = "";
}

/*--  �ύ--*/
event.prototype.submit =function(){
 
  var windowObj = opener.document.frames("elistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  var extendNameObj = windowObj.document.getElementsByName("actionExtendName");
  if(document.getElementById("actionExtendName").value.trim()==""){
    alert("����ֵ���붨��!");
	return ;
  }
  var extendValueObj = windowObj.document.getElementsByName("actionExtendValue");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
	 extendNameObj[i].value =this.actionExtendName.value;
     extendValueObj[i].value =this.actionExtendValue.value;
	 listObj[i].checked = false;
	 self.close();
	}
   }
}
/*--��ʼ��--*/
event.prototype.setExtend = function(){
  var windowObj = opener.document.frames("elistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  var extendNameObj = windowObj.document.getElementsByName("actionExtendName");
  var extendValueObj = windowObj.document.getElementsByName("actionExtendValue");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
	 this.actionExtendName.value = extendNameObj[i].value;
     this.actionExtendValue.value = extendValueObj[i].value;
	}
   }
}

/*-������ʽ-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}