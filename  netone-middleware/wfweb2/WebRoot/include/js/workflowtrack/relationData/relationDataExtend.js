var opener = window.dialogArguments;

/*获得扩展属性与给扩展属性付值*/
function bean(){
	this.parentObj = opener.document.frames("dExtendFrame");
	this.dataExtendName = document.getElementById("dataExtendName");
	this.dataExtendValue = document.getElementById("dataExtendValue");
}
/*获得表单数据    新建扩展属性*/
bean.prototype.getValue = function(){
 if(this.dataExtendName.value.trim()==""){
	alert("名字：值必须被定义，定义它或按取消！");
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

/*获得表单数据   编辑扩展属性*/
bean.prototype.getEditValue = function(){
 if(this.dataExtendName.value.trim()==""){
	alert("名字：值必须被定义，定义它或按取消！");
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
/*给表单付值*/
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
/*重置扩展属性的值*/
bean.prototype.reset = function (){
	this.dataExtendName.value = "";
	this.dataExtendValue.value = ""
}

/*-正则表达式-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}