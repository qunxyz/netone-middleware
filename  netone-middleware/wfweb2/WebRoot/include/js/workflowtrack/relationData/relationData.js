var opener = window.dialogArguments;

function openWindow(){
  
}
/*�½������������*/
openWindow.prototype.newRelationDataAttribute = function(){
  window.showModalDialog("newRelationDataAttribute.htm",window,"status:0;help:0;dialogWidth=400px;dialogHeight=320px;");
}
/*�༭�����������*/
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
		 alert("�������Ϊ���޷����д˲�����");
		 return ;
	 }
	 if(flag>0){
        alert("��ֻ��Ҫѡ��һ���༭����");
		return ;
	 }else if(flag==-1){
		  alert("�����ѡ��һ���༭����");
		  return ;
	 }
	  window.showModalDialog("editRelationDataAttribute.htm",window,"status:0;help:0;dialogWidth=400px;dialogHeight=320px;");
 }

/*ɾ���¼�*/
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
		 alert("�������Ϊ���޷����д˲�����");
		 return ;
	 }
	 if(flag==-1){
        alert("������Ҫѡ��һ������");
		return ;
	 }
	if (confirm("��ȷ��Ҫִ�д˲���!!!")) {
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

/*�ӱ��л��ֵ*/
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

/*������ֵ*/
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

/*������������б�*/
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

/*-������ʽ-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}