var opener = window.dialogArguments;
/*获得扩展属性与给扩展属性付值*/
function bean(){
	this.parentObj = opener.document.frames("pExtendFrame");
	this.processExtendName = document.getElementById("processExtendName");
	this.processExtendValue = document.getElementById("processExtendValue");
}
/*获得表单数据    新建扩展属性*/
bean.prototype.getValue = function(){
 if(this.processExtendName.value.trim()==""){
	alert("名字：值必须被定义，定义它或按取消！ff");
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

/*获得表单数据   编辑扩展属性*/
bean.prototype.getEditValue = function(){
 if(this.processExtendName.value.trim()==""){
	alert("名字：值必须被定义，定义它或按取消！");
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
/*给表单付值*/
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
/*重置扩展属性的值*/
bean.prototype.reset = function (){
	this.processExtendName.value = "";
	this.processExtendValue.value = ""
}

/*-正则表达式-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}