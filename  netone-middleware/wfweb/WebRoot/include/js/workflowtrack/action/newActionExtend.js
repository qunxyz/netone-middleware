var opener = window.dialogArguments;
function event(){

}

/*--  ����--*/
event.prototype.reset = function(){
  this.actionExtendName.value = "";
  this.actionExtendValue.value = "";
}

/*--  �ύ--*/
event.prototype.submit =function(){
 var extendName = document.getElementById("actionExtendName").value;
 var extendValue = document.getElementById("actionExtendValue").value;
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
var windowObj = opener.document.frames("elistFrame");
var divObj =    windowObj.document.createElement("div");
var trObj=  "<div id='extendLineList'><tr><td><input name='checkExtend' type='checkbox'></td>"
            +"<td align='center'><input name='actionExtendName' size='10' type='text' name='textfield' value='"+extendName+"' readOnly></td>"
            +"<td align='center'><input name='actionExtendValue' size='20' type='text' name='textfield' value='"+extendValue+"'></td></tr></div>";
	divObj.innerHTML = trObj;
windowObj.document.body.appendChild(divObj);
}
/*-������ʽ-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}