
function checkBoxCheck(formName){
  this.formName = formName;
  this.form;
}
//��ʼ��
checkBoxCheck.prototype.init=function(){
   if(this.formName == "" ){
	    this.form=document.forms[0];
	}else{
	    this.form=document.forms[this.formName];
	}
}
//ѡ��һ����¼
checkBoxCheck.prototype.isCheckOne = function (){
  var els = this.form.elements;
  var count = 0;
  for(var i=0;i<els.length;i++){
    if(els[i].type == "checkbox" && els[i].checked == true ){
	  count++;
	}
  }
  if(count == 0 || count >1 ){
	   alert("��ѡ��һ����¼");
	   return false;
   }
   return true;
}
 // �Ƿ�Ҫɾ��
 checkBoxCheck.prototype.deleteCheck=function(){
   if(this.isCheckOne()){
	    if(confirm("ȷ��Ҫɾ������?")){
		   return true;
		}else{
		  return false;
		}
   }
}  
//
checkBoxCheck.prototype.isValid=function(){
  this.init();
  if(this.deleteCheck()){
     return true; 
   }
	 return false;
}   