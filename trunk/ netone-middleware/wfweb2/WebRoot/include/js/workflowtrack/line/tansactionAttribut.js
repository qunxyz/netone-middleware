/*******************打开轨迹扩展属性的编辑页面  START***************************/
var opener = window.dialogArguments;
function openWindow(){
  var windowObj = document.frames("tlistFrame");
  this.checkObj = windowObj.document.getElementsByName("checkExtend");
  this.length = this.checkObj.length;
}
/*--编辑轨迹扩展属性窗口--*/
openWindow.prototype.editLineExtend = function (){
  var flag = 0;
  for(var i=0;i<this.length;i++){
    if(this.checkObj[i].checked){
	   flag++;
	}
  }
  if(flag==1){
  
  window.showModalDialog("EditlineExtend.html",window,"status:0;help:0;dialogWidth=300px;dialogHeight=200px");
   return;
  }else if(flag==0){
     alert("选择正确的元素之前你没权限修改！");
	 return;
  }else{
      alert("你只能选种一个编辑对象！");
	  return;
  }
}
/*--新建轨迹扩展属性窗口--*/
openWindow.prototype.newLineExtend = function (){
  var flag = 0;
  for(var i=0;i<this.length;i++){
    if(this.checkObj[i].checked){
	  this.checkObj[i].checked=false;
	}
  }
   window.showModalDialog("newLineExtend.html",window,"status:0;help:0;dialogWidth=400px;dialogHeight=300px");
   return;
}

/*******************打开轨迹扩展属性的编辑页面    END***************************/





/************相对本页面的操作     START    *************************************/
function fetchLineObj(){
  var nameObj = opener.document.getElementById("defineLineName");
  this.lineObj = opener.document.getElementById("work"+nameObj.value);
  this.lineObject = this.lineObj.parentElement;
  this.id = document.getElementById("lineId");
  this.name = document.getElementById("lineName");
  this.from = document.getElementById("lineFrom");
  this.to = document.getElementById("lineTo");
  this.describe = document.getElementById("lineDescribe");
  this.condition = document.getElementById("lineCondition");//2006-3-30
}
fetchLineObj.prototype.initializeLineObj = function (){
   var extendContent = this.lineObject.extendContent;
   var link = this.lineObject.name.split("->");
   this.id.value = this.lineObject.lineTrueId;
   this.name.value = this.lineObject.trueName;
   this.from.value = opener.document.getElementById("work"+link[0]).value;
   this.to.value = opener.document.getElementById("work"+link[1]).value;
   this.describe.value = this.lineObject.depict;
   this.condition.value = this.lineObject.condition;//2006-3-30
   var extend = extendContent.split(",");
   var extendName,extendValue;
   if(extend!=""){
     for(var i=0;i<extend.length;i++){
       extendName = (extend[i].split("&"))[0];
       extendValue = (extend[i].split("&"))[1];
	     (new operateTansactionAttributeList()).createExtend(extendName,extendValue);
     }
   }
}

fetchLineObj.prototype.setLineObj =function(){
   var extendStr = ""; 
   var allLineId = opener.document.getElementById("defineLineTrueId");
   if(allLineId.value.indexOf("&",0)!=-1){
   var allId = allLineId.value.split("&");
   var allIdLength = allId.length-1;
   var flag = true;
   for(var i=0;i<allIdLength;i++){
    if(this.id.value==this.lineObject.lineTrueId){
	  flag = false;
	  break;
	}else if(this.id.value==allId[i]){
	  alert("该活动ID已存在!!!");
	  flag = false;
	  this.id.select();
	  return;
	 }
    }
  }
  if(flag){
   allLineId.value = allLineId.value.replace(this.lineObject.lineTrueId+"&",this.id.value+"&");
   this.lineObject.lineTrueId = this.id.value;
   }
   this.lineObject.trueName = this.name.value;
   this.lineObject.depict = this.describe.value;
   this.lineObject.condition = this.condition.value;//2006-3-30
   var windowExtendList = document.frames("tlistFrame");
   var extendObj = windowExtendList.document.getElementsByName("extendLineName");
   var extendValueObj = windowExtendList.document.getElementsByName("extendLineValue");
   var extendLength = extendObj.length;
   for(var i=0;i<extendLength;i++){
    if(i==0){
	 extendStr=extendObj[i].value+"&"+extendValueObj[i].value;
	}else {
	    extendStr+=","+extendObj[i].value+"&"+extendValueObj[i].value;
	  }
   }
   this.lineObject.extendContent = extendStr;
   self.close();
}
/************相对本页面的操作     END    *************************************/







/************相对 tansactionAttributeList.html 的操作 STRAT*********************/

function operateTansactionAttributeList(){
  this.windowExtendList = document.frames("tlistFrame");
}

/*----------新建扩展属性  ------------*/
operateTansactionAttributeList.prototype.createExtend =function (extendName,extendValue){
var divObj = this.windowExtendList.document.createElement("div");
var isCheck = "";
if(extendName==""){
  isCheck = "checked";
}
var trObj=  "<div id='extendLineList'><tr><td><input name='checkExtend' type='checkbox' "+isCheck+"></td>"
            +"<td align='center'><input name='extendLineName' size='15' type='text' name='textfield' value='"+extendName+"' readOnly></td>"
            +"<td align='center'><input name='extendLineValue' size='18' type='text' name='textfield' value='"+extendValue+"' readOnly></td></tr></div>";
	divObj.innerHTML = trObj;
this.windowExtendList.document.body.appendChild(divObj);
}


/*--------删除扩展属性 --------------*/
operateTansactionAttributeList.prototype.deleteExtend = function(){
  var  checkObj = this.windowExtendList.document.getElementsByName("checkExtend");
  var length = checkObj.length;
  var parentObj;
  var index=0;
  for(var i=0;i<length;i++){
   if(checkObj[i].checked){
     index++;
    }
  }
  if(length==0){
    alert("你的扩展属性为空，无法删除！");
	return ;
  }else if (index==0)
  { 
     alert("你至少要选种一个删除对象！");
	 return ;
  }
  if (confirm("您确定要执行此操作!!!")) {
  for(var i=0;i<length;i++){
   if(checkObj[i].checked){
    parentObj = (checkObj[i]).parentElement.parentElement;
	parentObj.removeChild(checkObj[i].parentElement);
	checkObj = this.windowExtendList.document.getElementsByName("checkExtend");
	length--;
	i=-1;
	}
  }
 }
}


/************相对 tansactionAttributeList.html 的操作 END*********************/