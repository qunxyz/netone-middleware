

//提交子流程
function submitSubflow(name,packagecode,mode){

  		
       newExtendObj("subflowName",name);//添加子流程名

       newExtendObj("subflowId",packagecode);//添加子流程ID
       newExtendObj("syncMode",mode);//添加子流程的同步属性
       
	   return ;
}

/*----添加子流程的属性信息----*/
function newExtendObj(extendName,extendValue){

var windowObj =opener.document.frames("elistFrame");

var divObj =    windowObj.document.createElement("div");

var trObj=  "<div id='extendLineList'><tr><td><input name='checkExtend' type='checkbox' checked='checked'></td>"
            +"<td align='center'><input name='actionExtendName' size='12' type='text' name='textfield' value='"+extendName+"' readOnly></td>"
            +"<td align='center'><input name='actionExtendValue' size='19' type='text' name='textfield' value='"+extendValue+"'></td></tr></div>";

	divObj.innerHTML = trObj;

	windowObj.document.body.appendChild(divObj);

}
/*-?????-*/
String.prototype.trim = function() { 
  return this.replace(/(^\s*)|(\s*$)/g, ""); 
}