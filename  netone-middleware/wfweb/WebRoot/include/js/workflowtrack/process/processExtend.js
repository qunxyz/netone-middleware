var opener = window.dialogArguments;
/*�����չ���������չ���Ը�ֵ*/
function bean(){
	this.parentObj = opener.document.frames("pExtendFrame");
	this.processExtendName = document.getElementById("processExtendName");
	this.processExtendValue = document.getElementById("processExtendValue");
}
/*��ñ�����    �½���չ����*/
bean.prototype.getValue = function(){
 if(this.processExtendName.value.trim()==""){
	alert("���֣�ֵ���뱻���壬��������ȡ����ff");
	return ;
 }else {
    var divObj = this.parentObj.document.createElement("div");
    var trObj=  "<div id='extendProcessList'><tr><td><input name='checkExtend' type='checkbox' ></td>"
            +"<td align='center'><input name='processExtendName' size='27' type='text' name='textfield' value='"+(this.processExtendName.value)+"' readOnly></td>"
            +"<td align='center'><input name='processExtendValue' size='28' type='text' name='textfield' value='"+(this.processExtendValue.value)+"' readOnly></td></tr></div>";
	divObj.innerHTML = trObj;
    this.parentObj.document.body.appendChild(divObj);
    self.close();
  }
}

/*��ñ�����   �༭��չ����*/
bean.prototype.getEditValue = function(){
 if(this.processExtendName.value.trim()==""){
	alert("���֣�ֵ���뱻���壬��������ȡ����");
	return ;
 }else {
	var checkObj = this.parentObj.document.getElementsByName("checkExtend");
    var length = checkObj.length;
	for(var i=0;i<length;i++){
		if(checkObj[i].checked){
		   checkObj[i].parentElement.children["processExtendName"].value = this.processExtendName.value;
           checkObj[i].parentElement.children["processExtendValue"].value = this.processExtendValue.value ;
		   checkObj[i].checked=false;
		   self.close();
		   return;
		}
	}
	 
  }
}
/*������ֵ*/
bean.prototype.setValue = function(){
    var checkObj = this.parentObj.document.getElementsByName("checkExtend");
    var length = checkObj.length;
	for(var i=0;i<length;i++){
		if(checkObj[i].checked){
		  this.processExtendName.value = checkObj[i].parentElement.children["processExtendName"].value;
          this.processExtendValue.value = checkObj[i].parentElement.children["processExtendValue"].value;
		  return;
		}
	}
}
/*������չ���Ե�ֵ*/
bean.prototype.reset = function (){
	this.processExtendName.value = "";
	this.processExtendValue.value = ""
}

/*-������ʽ-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}