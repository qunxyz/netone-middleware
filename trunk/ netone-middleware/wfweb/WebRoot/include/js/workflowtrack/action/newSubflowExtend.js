

//�ύ������
function submitSubflow(name,packagecode,mode){

  		
       newExtendObj("subflowName",name);//�����������

       newExtendObj("subflowId",packagecode);//���������ID
       newExtendObj("syncMode",mode);//��������̵�ͬ������
       
	   return ;
}

/*----��������̵�������Ϣ----*/
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