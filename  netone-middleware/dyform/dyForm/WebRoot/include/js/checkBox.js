
function checkBoxCheck(formName){
  this.formName = formName;
  this.form;
}
//初始化
checkBoxCheck.prototype.init=function(){
   if(this.formName == "" ){
	    this.form=document.forms[0];
	}else{
	    this.form=document.forms[this.formName];
	}
}
//选择一条记录
checkBoxCheck.prototype.isCheckOne = function (){
  var els = this.form.elements;
  var count = 0;
  for(var i=0;i<els.length;i++){
    if(els[i].type == "checkbox" && els[i].checked == true ){
	  count++;
	}
  }
  if(count == 0 || count >1 ){
	   alert("请选择一条记录");
	   return false;
   }
   return true;
}
 // 是否要删除
 checkBoxCheck.prototype.deleteCheck=function(){
   if(this.isCheckOne()){
	    if(confirm("确定要删除流程?")){
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