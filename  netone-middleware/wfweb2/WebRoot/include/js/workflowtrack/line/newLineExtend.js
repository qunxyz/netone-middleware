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
 var extendName = this.lineName.value;
 var extendValue = this.lineValue.value;
  if(extendName.trim()==""){
    alert("名字：值必须被定义，定义它或按取消！");
	return ;
  }else{
     newExtendObj(extendName,extendValue);
	 self.close();
	 return ;
  }

}
/*----新建一个扩展属性对象----*/
function newExtendObj(extendName,extendValue){
var windowObj = opener.document.frames("tlistFrame");
var divObj =    windowObj.document.createElement("div");
var trObj=  "<div id='extendLineList'><tr><td><input name='checkExtend' type='checkbox'></td>"
            +"<td align='center'><input name='extendLineName' size='15' type='text' name='textfield' value='"+extendName+"' readOnly></td>"
            +"<td align='center'><input name='extendLineValue' size='18' type='text' name='textfield' value='"+extendValue+"' readOnly></td></tr></div>";
	divObj.innerHTML = trObj;
windowObj.document.body.appendChild(divObj);
}
/*-正则表达式-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}