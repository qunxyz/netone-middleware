var opener = window.dialogArguments;

function event(){
 this.actionExtendName = document.getElementById("actionExtendName");
 this.actionExtendValue = document.getElementById("actionExtendValue");
}

/*--  重置--*/
event.prototype.reset = function(){
  this.actionExtendName.value = "";
  this.actionExtendValue.value = "";
}

/*--  提交--*/
event.prototype.submit =function(){
 
  var windowObj = opener.document.frames("elistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  var extendNameObj = windowObj.document.getElementsByName("actionExtendName");
  if(document.getElementById("actionExtendName").value.trim()==""){
    alert("名字值必须定义!");
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
/*--初始化--*/
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

/*-正则表达式-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}