var opener = window.dialogArguments;

/*�����չ���������չ���Ը�ֵ*/
function bean(){
	this.parentObj = opener.document.frames("dExtendFrame");
	this.dataExtendName = document.getElementById("dataExtendName");
	this.dataExtendValue = document.getElementById("dataExtendValue");
}
/*��ñ�����    �½���չ����*/
bean.prototype.getValue = function(){
 if(this.dataExtendName.value.trim()==""){
	alert("���֣�ֵ���뱻���壬��������ȡ����");
	return ;
 }else {
    var divObj = this.parentObj.document.createElement("div");
    var trObj=  "<div id='extendDataList'><tr><td><input name='checkExtend' type='checkbox' ></td>"
            +"<td align='center'><input name='dataExtendName' size='15' type='text' name='textfield' value='"+(this.dataExtendName.value)+"' readOnly></td>"
            +"<td align='center'><input name='dataExtendValue' size='16' type='text' name='textfield' value='"+(this.dataExtendValue.value)+"' readOnly></td></tr></div>";
	 divObj.innerHTML = trObj;
    this.parentObj.document.body.appendChild(divObj);
    self.close();
  }
}

/*��ñ�����   �༭��չ����*/
bean.prototype.getEditValue = function(){
 if(this.dataExtendName.value.trim()==""){
	alert("���֣�ֵ���뱻���壬��������ȡ����");
	return ;
 }else {
	var checkObj = this.parentObj.document.getElementsByName("checkExtend");
    var length = checkObj.length;
	for(var i=0;i<length;i++){
		if(checkObj[i].checked){
		   checkObj[i].parentElement.children["dataExtendName"].value = this.dataExtendName.value;
           checkObj[i].parentElement.children["dataExtendValue"].value = this.dataExtendValue.value ;
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
		  this.dataExtendName.value = checkObj[i].parentElement.children["dataExtendName"].value;
          this.dataExtendValue.value = checkObj[i].parentElement.children["dataExtendValue"].value;
		  return;
		}
	}
}
/*������չ���Ե�ֵ*/
bean.prototype.reset = function (){
	this.dataExtendName.value = "";
	this.dataExtendValue.value = ""
}

/*-������ʽ-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}