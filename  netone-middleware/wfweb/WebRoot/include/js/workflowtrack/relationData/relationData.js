var opener = window.dialogArguments;

function openWindow(){
  
}
/*新建相关数据属性*/
openWindow.prototype.newRelationDataAttribute = function(){
  window.showModalDialog("newRelationDataAttribute.htm",window,"status:0;help:0;dialogWidth=400px;dialogHeight=320px;");
}
/*编辑相关数据属性*/
openWindow.prototype.editRelationDataAttribute = function(){
 	 var listObj = window.document.frames("dlistFrame");
     var checkObj = listObj.document.getElementsByName("checkData");
	 var length = checkObj.length;
	 var flag = -1;
	 for(var i=0;i<length;i++){
		 if(checkObj[i].checked){
			 flag++;
		 }
	 }
	 if(length==0){
		 alert("相关数据为空无法进行此操作！");
		 return ;
	 }
	 if(flag>0){
        alert("你只能要选择一个编辑对象！");
		return ;
	 }else if(flag==-1){
		  alert("你必须选择一个编辑对象！");
		  return ;
	 }
	  window.showModalDialog("editRelationDataAttribute.htm",window,"status:0;help:0;dialogWidth=400px;dialogHeight=320px;");
 }

/*删除事件*/
function deleteEvent(){
	 var listObj = window.document.frames("dlistFrame");
     var checkObj = listObj.document.getElementsByName("checkData");
	 var length = checkObj.length;
	 var flag = -1;
	 for(var i=0;i<length;i++){
		 if(checkObj[i].checked){
			 flag++;
		 }
	 }
	 if(length==0){
		 alert("相关数据为空无法进行此操作！");
		 return ;
	 }
	 if(flag==-1){
        alert("你至少要选择一个对象！");
		return ;
	 }
	if (confirm("您确定要执行此操作!!!")) {
	 for(var i=0;i<length;i++){
         if(checkObj[i].checked){
          var dirParentObj  = checkObj[i].parentElement.parentElement;
          dirParentObj.removeChild(checkObj[i].parentElement);
		  checkObj =  listObj.document.getElementsByName("checkData");
		  length = checkObj.length;
		  i=-1;
		 }
	 }
	}
}

function bean(){
  this.parentObj = document.frames("dlistFrame");
  this.relationDataObj = opener.document.getElementById("relationData");
}

/*从表单中获得值*/
bean.prototype.getValue =function (){
   var relationdata = this.parentObj.document.getElementsByName("relationData");
   var datacontent="";
   var length = relationdata.length;
   for(var i=0;i<length;i++){
    datacontent+=relationdata[i].value+";"
   }
   this.relationDataObj.value = datacontent;
   self.close();
}

/*给表单付值*/
bean.prototype.setValue =function (){
 var dataValue = this.relationDataObj.value
 var dataId ="";
 var dataName = "";
	if(dataValue.trim()!=""){
      var dataArray =  dataValue.split(";");
	  var length = dataArray.length-1;
       for(var i=0;i<length;i++){
         dataId = dataArray[i].split(",")[0];
		 dataName = dataArray[i].split(",")[1];
		 relationDataValue = dataArray[i];
		 createRelation(dataId,dataName,relationDataValue);
	   }
    }
}

/*创建相关数据列表*/
function createRelation(dataId,dataName,relationData){
	 var openerObj = document.frames("dlistFrame");
	 var divObj = openerObj.document.createElement("div");
     var trObj=  "<div id='extendDataList'><tr><td><input name='checkData' type='checkbox' ></td>"
            +"<td align='center'><input name='dataId' size='15' type='text' name='textfield' value='"+dataId+"' readOnly></td>"
            +"<td align='center'><input name='dataName' size='16' type='text' name='textfield' value='"+dataName+"' readOnly>"
			+"<input name='relationData' type='hidden' value='"+relationData+"'>"
			+"</td></tr></div>";
	 divObj.innerHTML = trObj;
     openerObj.document.body.appendChild(divObj);
}

/*-正则表达式-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}