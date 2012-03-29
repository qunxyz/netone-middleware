var opener = window.dialogArguments;
function bean(){
	this.extendObj = document.frames("dExtendFrame");
	this.dataId = document.getElementById("dataId");
	this.dataName = document.getElementById("dataName");
	this.dataInitial = document.getElementById("dataInitial");
	this.dataLength = document.getElementById("dataLength");
	this.dataDescribe = document.getElementById("dataDescribe");
	this.dataExtendsName = this.extendObj.document.getElementsByName("dataExtendName");
	this.dataExtendsValue = this.extendObj.document.getElementsByName("dataExtendValue");
}

/*从表单中获得数据*/
bean.prototype.getValue =function(){
	var id = this.dataId.value;
	var dataName = this.dataName.value;
	var dataInitial = this.dataInitial.value;
	var dataLength = this.dataLength.value;
	var dataDescribe = this.dataDescribe.value;
    var length = this.dataExtendsName.length;
	var extend="";
	var relationData = "";
	for(var i=0;i<length;i++){
         extend+=this.dataExtendsName[i].value+"&"+this.dataExtendsValue[i].value+"|";
	}
   relationData=id+","+dataName+","+dataInitial+","+dataLength+","+dataDescribe+","+extend;
   
   if(id.trim()==""){
	 alert("名字：值必须被定义，定义它或按取消！");
	 return ;
    }else if(dataLength!=null&&isNaN(dataLength)){
		alert("长度只能用数字！");
		this.dataLength.select();
		return ;
	}else if(judgeIdCreate(id)) {
	   var openerObj  = opener.document.frames("dlistFrame");
       var divObj = openerObj.document.createElement("div");
       var trObj=  "<div id='extendDataList'><tr><td><input name='checkData' type='checkbox' ></td>&nbsp;"
            +"<td align='center'><input name='dataId' size='15' type='text' name='textfield' value='"+id+"' readOnly></td>&nbsp;"
            +"<td align='center' style='margin-right: 10px;'><input name='dataName' size='16' type='text' name='textfield' value='"+dataName+"' readOnly>"
			+"<input name='relationData' type='hidden' value='"+relationData+"'>"
			+"</td></tr></div>";
	 divObj.innerHTML = trObj;
     openerObj.document.body.appendChild(divObj);
     self.close();
     }
}

/*编辑属性*/
bean.prototype.editRelationAttribute = function(){
  var parentObj = opener.document.frames("dlistFrame");
  var checkObj = parentObj.document.getElementsByName("checkData");
  var length = checkObj.length;

  	 for(var i=0;i<length;i++){
		 if(checkObj[i].checked){
          var checkParentObj = checkObj[i].parentElement;
		  var allObj = (checkParentObj.children["relationData"].value).split(",");
          this.dataId.value = allObj[0];
	      this.dataName.value = allObj[1];
	      this.dataInitial.value = allObj[2];
	      this.dataLength.value = allObj[3];
	      this.dataDescribe.value = allObj[4];
		  if((allObj[5].trim())!=""){
			    var extend = allObj[5].split("|");
				var extendLength = extend.length-1;
			    for(var i=0;i<extendLength;i++){
					creatExtend((extend[i].split("&"))[0],(extend[i].split("&"))[1]);
				}
		  }
		 }
	 }
}

/*当编辑相关数据时,创建的扩展属性*/
function creatExtend(extendName,extendValue){
    var parentObj = document.frames("dExtendFrame");
	var divObj =parentObj.document.createElement("div");
    var trObj=  "<div id='extendDataList'><tr><td><input name='checkExtend' type='checkbox' ></td>&nbsp;"
            +"<td align='center'><input name='dataExtendName' size='15' type='text' name='textfield' value='"+extendName+"' readOnly></td>&nbsp;"
            +"<td align='center'><input name='dataExtendValue' size='16' type='text' name='textfield' value='"+extendValue+"' readOnly></td></tr></div>";
	 divObj.innerHTML = trObj;
    parentObj.document.body.appendChild(divObj);
}

/*编辑相关数据是从表单中获得的数据*/
bean.prototype.editGetValue = function (){
	var id = this.dataId.value;
	 if(id.trim()==""){
	 alert("名字：值必须被定义，定义它或按取消！");
	 return ;
    }else if(dataLength!=null&&isNaN(dataLength)){
		alert("长度只能用数字！");
		this.dataLength.select();
		return ;
	}else if(!(judgeIdEdit(id))){
      return ;
	}
	var dataName = this.dataName.value;
	var dataInitial = this.dataInitial.value;
	var dataLength = this.dataLength.value;
	var dataDescribe = this.dataDescribe.value;
    var length = this.dataExtendsName.length;
	var extend="";
	var relationData = "";
	for(var i=0;i<length;i++){
         extend+=this.dataExtendsName[i].value+"&"+this.dataExtendsValue[i].value+"|";
	}
   relationData=id+","+dataName+","+dataInitial+","+dataLength+","+dataDescribe+","+extend;
   var parentObj = opener.document.frames("dlistFrame");
   var checkObj = parentObj.document.getElementsByName("checkData");
   var checkLength = checkObj.length;
   for(var i=0;i<checkLength;i++){
	   if(checkObj[i].checked){
		  var parentCheckObj = checkObj[i].parentElement;
		  parentCheckObj.children["dataId"].value = id;
          parentCheckObj.children["dataName"].value = dataName;
          parentCheckObj.children["relationData"].value = relationData;
		  self.close();
          checkObj[i].checked=false;
		  return ;
	   }
   }


}

/*对扩展属性的操作*/
function openWindow(){
}

/*打开新建窗口*/
openWindow.prototype.newExtend = function(){

window.showModalDialog("newRelationDataExtend.html",window,"status:0;help:0;dialogWidth=290px;dialogHeight=250px;");

}

/*打开编辑扩展属性窗口*/
openWindow.prototype.editExtend = function(){
   var extendObj = document.frames("dExtendFrame");
   var cleckObj = extendObj.document.getElementsByName("checkExtend");
   var flag = 0;
   var length = cleckObj.length;
   for(var i=0;i<length;i++){
     if(cleckObj[i].checked){
	   flag++;
	}
   }
   if(flag==1){
   	window.showModalDialog("EditRelationDataExtend.html",window,"status:0;help:0;dialogWidth=290px;dialogHeight=250px;");
    return ;
    }else if(flag==0){
          alert("选择正确的元素之前你没权限修改！");
	      return;
       }else{
          alert("你只能选种一个编辑对象！");
	    return;
  }
}

/*对扩展属性的删除操作*/
function deleteOpe(){
   var extendObj = document.frames("dExtendFrame");
   var cleckObj = extendObj.document.getElementsByName("checkExtend");
   var length = cleckObj.length;
   var flag = 0;
   for(var i=0;i<length;i++){
     if(cleckObj[i].checked){
       flag++;
      }
    }
   if(length==0){
     alert("你的扩展属性为空，无法删除！");
	 return ;
    }else if(flag==0){ 
           alert("你至少要选种一个删除对象！");
	       return ;
         }
   if (confirm("您确定要执行此操作!!!")) {
      for(var i=0;i<length;i++){
           if(cleckObj[i].checked){
		    var removeObj = (cleckObj[i]).parentElement.parentElement;
		    removeObj.removeChild(cleckObj[i].parentElement);
            cleckObj = extendObj.document.getElementsByName("checkExtend");
		    length--;
		    i=-1;
	     }
      }
   } 
}
  /*重置表单*/
bean.prototype.reset = function(){
	this.dataId.value = "";
	this.dataName.value = "";
	this.dataInitial.value = "";
	this.dataLength.value = "";
	this.dataDescribe.value = "";
}
/*创建时判断该ID是否已经存在*/
function judgeIdCreate(id){
   var parentObj = opener.document.frames("dlistFrame");
   var idObj = parentObj.document.getElementsByName("dataId");
   var length = idObj.length;
   for(var i=0;i<length;i++){
      if(id==(idObj[i].value)){
		  alert("该ID已存在,请更改或按关闭!");
		  return false;
	  }
   }
   return true;
   
}
/*编辑相关数据时判断该ID是否已经存在*/
function judgeIdEdit(id){
   var parentObj = opener.document.frames("dlistFrame");
   var checkObj = parentObj.document.getElementsByName("checkData");
   var length = checkObj.length;
   var updataId;
   var parentCheckObj = null;
   for(var i=0;i<length;i++){
	   parentCheckObj = checkObj[i].parentElement;
      if(!(checkObj[i].checked)){
        updataId = parentCheckObj.children["dataId"].value;
		if(updataId==id){
          alert("该ID已存在,请更改或按关闭!");
		  return false;
		}
	  }
   }
   return true;
}
/*-正则表达式-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}