var opener = window.dialogArguments;
function event(){
 this.lineName = document.getElementById("lineName");
 this.lineValue = document.getElementById("lineValue");
}

/*--  ����--*/
event.prototype.reset = function(){
  this.lineName.value = "";
  this.lineValue.value = "";
}

/*--  �ύ--*/
event.prototype.submit =function(){
  var windowObj = opener.document.frames("tlistFrame");
  var listObj = windowObj.document.getElementsByName("checkExtend");
  var extendNameObj = windowObj.document.getElementsByName("extendLineName");
  if(document.getElementById("lineName").value.trim()==""){
    alert("���֣�ֵ���뱻���壬��������ȡ����");
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
/*--��ʼ��--*/
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

/*-������ʽ-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}