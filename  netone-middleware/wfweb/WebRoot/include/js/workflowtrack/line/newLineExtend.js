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
 var extendName = this.lineName.value;
 var extendValue = this.lineValue.value;
  if(extendName.trim()==""){
    alert("���֣�ֵ���뱻���壬��������ȡ����");
	return ;
  }else{
     newExtendObj(extendName,extendValue);
	 self.close();
	 return ;
  }

}
/*----�½�һ����չ���Զ���----*/
function newExtendObj(extendName,extendValue){
var windowObj = opener.document.frames("tlistFrame");
var divObj =    windowObj.document.createElement("div");
var trObj=  "<div id='extendLineList'><tr><td><input name='checkExtend' type='checkbox'></td>"
            +"<td align='center'><input name='extendLineName' size='15' type='text' name='textfield' value='"+extendName+"' readOnly></td>"
            +"<td align='center'><input name='extendLineValue' size='18' type='text' name='textfield' value='"+extendValue+"' readOnly></td></tr></div>";
	divObj.innerHTML = trObj;
windowObj.document.body.appendChild(divObj);
}
/*-������ʽ-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}