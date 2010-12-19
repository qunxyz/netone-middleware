var opener = window.dialogArguments;
function event(){
 this.lineName = document.getElementById("lineName");
 this.lineValue = document.getElementById("lineValue");
}

/*--  重置--*/
event.prototype.reset = function(){
  this.lineName.value = "";
  this.lineValue.value = "";
}

/*--  提交--*/
event.prototype.submit =function(){
  var windowObj = opener.document.frames("tlistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  var extendNameObj = windowObj.document.getElementsByName("extendLineName");
  if(document.getElementById("lineName").value.trim()==""){
    alert("名字：值必须被定义，定义它或按取消！");
	return ;
  }
  var extendValueObj = windowObj.document.getElementsByName("extendLineValue");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
	 extendNameObj[i].value =this.lineName.value;
     extendValueObj[i].value =this.lineValue.value;
	 listObj[i].checked = false;
	 self.close();
	}
   }
}
/*--初始化--*/
event.prototype.setExtend = function(){
  var windowObj = opener.document.frames("tlistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  var extendNameObj = windowObj.document.getElementsByName("extendLineName");
  var extendValueObj = windowObj.document.getElementsByName("extendLineValue");
  var listLength = listObj.length;
  for(var i=0;i<listLength;i++){
    if(listObj[i].checked){
	 this.lineName.value = extendNameObj[i].value;
     this.lineValue.value = extendValueObj[i].value;
	}
   }
}

/*-正则表达式-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}