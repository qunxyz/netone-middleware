/*****************　get set workTrackProperty.html　　　START***********************/
var opener = window.dialogArguments;


function event(){
  this.name = opener.document.getElementById("defineActionObjName").value;
  this.perantObj = opener.document.getElementById("work"+this.name);
  this.actionObj = this.perantObj.parentElement.parentElement.parentElement.parentElement.parentElement;
  this.actionId = document.getElementById("actionId");
  this.actionName = document.getElementById("actionName");
  this.actionType = document.getElementById("actionType");
  this.deadline = document.getElementById("deadline");
  if((this.deadline!=null)&&(isNaN(this.deadline.value))){
	  alert("期限必须为数字！");
      this.deadline.select();
	  return;
  }
  this.forwardObj =  document.getElementById("forwardConditionId");
  this.afterObj =  document.getElementById("afterConditionId");
}
/**--------给节点赋值------***/
event.prototype.setAttribute = function(){
  var allActionId = opener.document.getElementById("defineActionTrueId").value;
  if(allActionId.indexOf("&",0)!=-1){
  var allId = allActionId.split("&");
  var allIdLength = allId.length-1;
  var flag = true;
   for(var i=0;i<allIdLength;i++){
    if(this.actionId.value==this.actionObj.actionTrueId){
	  flag = false;
	  break;
	 }else if(this.actionId.value==allId[i]){
	  alert("该活动ID已存在!!!");
	  flag = false;
	  this.actionId.select();
	  return;
	 }
    }
  }
  if(flag){
  opener.document.getElementById("defineActionTrueId").value = allActionId.replace(this.actionObj.actionTrueId+"&",this.actionId.value+"&");
  this.actionObj.actionTrueId = this.actionId.value;
  }
  this.perantObj.value=this.actionName.value;
  this.actionObj.actionType =this.actionType.value; 
  this.actionObj.deadline =this.deadline.value; 
  this.actionObj.forwardCondition = this.forwardObj.value;
  this.actionObj.afterCondition = this.afterObj.value;
  this.actionObj.actionExtendAttribute =(new actionExtend()).toString();
  self.close();
}
/*------ 给workTrackProperty.html页面赋值----------*/
event.prototype.getAttribute = function(){
  var idObj = document.getElementById("actionId");
  idObj.value = this.actionObj.actionTrueId;
  this.actionName.value=this.perantObj.value;
  this.actionType.value =this.actionObj.actionType;
  this.deadline.value = this.actionObj.deadline;
  if(this.actionObj.forwardCondition!=""&&this.actionObj.forwardCondition!="null")
  {
  this.forwardObj.value = this.actionObj.forwardCondition;
  }
   if((this.actionObj.afterCondition!="")&&(this.actionObj.afterCondition!="null"))
   {
    this.afterObj.value   = this.actionObj.afterCondition;
   }
   var extend =  this.actionObj.actionExtendAttribute.split(",");
   var extendName,extendValue;
   if(extend!=""){
     for(var i=0;i<extend.length;i++){
       extendName = (extend[i].split("&"))[0];
       extendValue = (extend[i].split("&"))[1];
	   (new operateExtendAttributeList()).createExtend(extendName,extendValue);
     }
   }
}

function actionExtend(){
   this.extendStr = ""; 
   var windowExtendList = document.frames("elistFrame");
   var extendObj = windowExtendList.document.getElementsByName("actionExtendName");
   var extendValueObj = windowExtendList.document.getElementsByName("actionExtendValue");
   var extendLength = extendObj.length;
   for(var i=0;i<extendLength;i++){
    if(i==0){
	 this.extendStr=extendObj[i].value+"&"+extendValueObj[i].value;
	}else {
	    this.extendStr+=","+extendObj[i].value+"&"+extendValueObj[i].value;
	  }
   }

}
actionExtend.prototype.toString = function(){
 return this.extendStr;
}
/*****************　get set workTrackProperty.html　　　END***********************/



//**************************重置     START *******************************************
function reset(type){
  if(type=="basic")
   this.resetBasic();
  if(type=="extended")
      this.resetExtended();
   if(type=="tool")
     this.resetTool();
  if(type=="forwardCondition")
     this.resetForwardCondition();
  if(type=="afterCondition")
     this.resetAfterCondition();
}

reset.prototype.resetBasic = function(){
  var actionId = document.getElementById("actionId");
  var actionName = document.getElementById("actionName");
  var actionType = document.getElementById("actionType");
  var deadline = document.getElementById("deadline");
  actionName.value = "";
  deadline.value = "";
}
reset.prototype.resetExtended = function(){
    var extend = document.getElementById("extend");
    var extendValue = document.getElementById("extendValue"); 
    extend.value = "";
	extendValue.value = "";
}

reset.prototype.resetForwardCondition = function(){
    var forwardConditionObj = document.getElementById("forwardConditionId");
    forwardConditionObj.value = "";
}
reset.prototype.resetAfterCondition = function(){
    var afterConditionObj = document.getElementById("afterConditionId");
    afterConditionObj.value = "";
}

reset.prototype.resetTool = function(){
}
//**************************重置     END *******************************************




//*************************隐藏及改变按钮颜色　　START******************************
function button(type){
 var basicObj = document.getElementById("basic");
 var extendedObj = document.getElementById("extended");
 var forwardConditionObj =  document.getElementById("forwardCondition");
 var afterConditionObj =  document.getElementById("afterCondition");
 var bB = document.getElementById("buttonBasic");
 var bE = document.getElementById("buttonExtended");
 var bF = document.getElementById("buttonForwardCondition");
 var bA = document.getElementById("buttonAfterCondition");
  if(type=="basic"){
       basicObj.style.visibility="visible";
       extendedObj.style.visibility="hidden";
      
	   forwardConditionObj.style.visibility="hidden";
       afterConditionObj.style.visibility="hidden";
       if(bB!=null){
       	bB.style.backgroundImage = "url(../../../../../../image/wf/titChange.jpg)";
       }
       if(bE!=null){
       	bE.style.backgroundImage= "url(../../../../../../image/wf/lct.jpg)";
       }
       if(bF!=null){
	   	bF.style.backgroundImage = "url(../../../../../../image/wf/lct.jpg)";
	   }
	   if(bA!=null){
	   	bA.style.backgroundImage = "url(../../../../../../image/wf/lct.jpg)";
	   }
     }

  if(type=="extended"){
       basicObj.style.visibility="hidden";
       extendedObj.style.visibility="visible";
      
	   forwardConditionObj.style.visibility="hidden";
       afterConditionObj.style.visibility="hidden";
       if(bB!=null){
       	bB.style.backgroundImage = "url(../../../../../../image/wf/lct.jpg)";
       }
       if(bE!=null){
       	bE.style.backgroundImage= "url(../../../../../../image/wf/titChange.jpg)";
       }
       if(bF!=null){
	   	bF.style.backgroundImage = "url(../../../../../../image/wf/lct.jpg)";
	   }
	   if(bA!=null){
	   	bA.style.backgroundImage = "url(../../../../../../image/wf/lct.jpg)";
	   }

     }

	if(type=="afterCondition"){
       basicObj.style.visibility="hidden";
       extendedObj.style.visibility="hidden";
      
	   forwardConditionObj.style.visibility="hidden";
       afterConditionObj.style.visibility="visible";
       if(bB!=null){
       	bB.style.backgroundImage = "url(../../../../../../image/wf/lct.jpg)";
       }
       if(bE!=null){
       	bE.style.backgroundImage= "url(../../../../../../image/wf/lct.jpg)";
       }
       if(bF!=null){
	   	bF.style.backgroundImage = "url(../../../../../../image/wf/lct.jpg)";
	   }
	   if(bA!=null){
	   	bA.style.backgroundImage = "url(../../../../../../image/wf/titChange.jpg)";
	   }

     }
	 if(type=="forwardCondition"){
       basicObj.style.visibility="hidden";
       extendedObj.style.visibility="hidden";
      
	   forwardConditionObj.style.visibility="visible";
       afterConditionObj.style.visibility="hidden";
       if(bB!=null){
       	bB.style.backgroundImage= "url(../../../../../../image/wf/lct.jpg)";
       }
       if(bE!=null){
       	bE.style.backgroundImage= "url(../../../../../../image/wf/lct.jpg)";
       }
       if(bF!=null){
	   	bF.style.backgroundImage = "url(../../../../../../image/wf/titChange.jpg)";
	   }
	   if(bA!=null){
	   	bA.style.backgroundImage = "url(../../../../../../image/wf/lct.jpg)";
	   }

     }
}

//*************************隐藏及改变按钮颜色　　END******************************





//*************************活动的前提条件及后置条件　START************************

function condition(){
  var name = opener.document.getElementById("defineActionObjName").value;
  var perantObj = opener.document.getElementById("work"+name);
  this.actionObj = perantObj.parentElement.parentElement.parentElement.parentElement.parentElement;
  this.forwardCond();
  this.afterCond();
}
/*------------------前提条件-----------------*/
condition.prototype.forwardCond = function(){
  var forwardLink = this.actionObj.forward;

  if((forwardLink!="null")||(forwardLink!="")){
    var length = (forwardLink.split(",")).length;
	if(length<3){
	  var forwardObj =  document.getElementById("forwardConditionId");
	  forwardObj.disabled=true;
      forwardObj.value ="";
	}
  }
}
/*------------------后置条件-----------------*/
condition.prototype.afterCond = function(){
    var tracklink = this.actionObj.tracklink;

	if((tracklink!="null")||(tracklink!="")){
	  var length = (tracklink.split(",")).length;
	  if(length<3){
	   var afterObj =  document.getElementById("afterConditionId");
	   afterObj.disabled=true;
	   afterObj.value ="";
	  }
	}
}

//*************************活动的前提条件及后置条件　END**************************



/*******************打开轨迹扩展属性的编辑页面  START***************************/
function openWindow(){
  var windowObj = document.frames("elistFrame");
  this.checkObj = windowObj.document.getElementsByName("checkExtend");
  this.length = this.checkObj.length;
}
/*--编辑节点扩展属性窗口--*/
openWindow.prototype.editActionExtend = function (){
  var flag = 0;
  for(var i=0;i<this.length;i++){
    if(this.checkObj[i].checked){
	   flag++;
	}
  }
  if(flag==1){
  	window.showModalDialog("EditActionExtend.html",window,"status:0;help:0;dialogWidth=300px;dialogHeight=250px;");
  	return;
  }else if(flag==0){
     alert("选择正确的元素之前你没权限修改！");
	 return;
  }else{
      alert("你只能选种一个编辑对象！");
	  return;
  }
}
/*--新建节点扩展属性窗口--*/
   openWindow.prototype.newActionExtend = function (){
   window.showModalDialog("newActionExtend.html",window,"status:0;help:0;dialogWidth=300px;dialogHeight=250px;");
   return;
}
/*******************打开轨迹扩展属性的编辑页面    END***************************/




/************相对 extendAttributeList.html 的操作 STRAT*********************/

function operateExtendAttributeList(){
  this.windowExtendList = document.frames("elistFrame");
}

/*----------新建扩展属性  ------------*/
operateExtendAttributeList.prototype.createExtend =function (extendName,extendValue){
var divObj = this.windowExtendList.document.createElement("div");
var isCheck = "";
if(extendName==""){
  isCheck = "checked";
}
var trObj=  "<div id='extendLineList'><tr><td><input name='checkExtend' type='checkbox' "+isCheck+"></td>"
            +"<td align='center'><input name='actionExtendName' size='12' type='text' name='textfield' value='"+extendName+"' readOnly></td>"
            +"<td align='center'><input name='actionExtendValue' size='12' type='text' name='textfield' value='"+extendValue+"' readOnly></td></tr></div>";
	divObj.innerHTML = trObj;
this.windowExtendList.document.body.appendChild(divObj);
}


/*--------删除扩展属性 --------------*/
operateExtendAttributeList.prototype.deleteExtend = function(){
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

/*--------删除子流程的扩展属性 --------------*/
function deleteSubflowExtend(){
  var  checkObj = document.frames("elistFrame").document.getElementsByName("checkExtend");
  var length = checkObj.length;
  var parentObj;
  for(var i=0;i<length;i++){
    parentObj = (checkObj[i]).parentElement.parentElement;
	parentObj.removeChild(checkObj[i].parentElement);
	checkObj = document.frames("elistFrame").document.getElementsByName("checkExtend");
	length--;
	i=-1;
  }
}
/*--打开子流程选择窗口-*/
function subflow(){
  var pathinfo =  document.getElementById("pathinfo").value;
  var  checkObj = document.frames("elistFrame").document.getElementsByName("checkExtend");
  var length = checkObj.length;
  if(length>0){
  	alert("请先删除旧流程的信息");
  	return;
  }
  window.open(pathinfo+"/SSelectSvl?pagename=subflow&appname=BUSSWF",'_blank','height=400, width=500, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no, status=no');
  //var attribute = window.showModalDialog(pathinfo+"/SSelectSvl?pagename=flow&appname=BUSSWF",window,"status:0;help:0;dialogWidth=600px;dialogHeight=500px");
}
