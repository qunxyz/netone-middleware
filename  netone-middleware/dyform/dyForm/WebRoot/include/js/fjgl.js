//�ϴ�
function uploaddo(){
	var choice=this.document.forms[0].fjmc.value;
	if(choice==null || choice.length==0){
		alert("����ѡ�񸽼�");
	}else{
	    this.document.forms[0].action = '/dyForm/data/datafj/upload.do';
		this.document.forms[0].method = "post";
		this.document.forms[0].target = "_self";
		this.document.forms[0].submit();
	}
}
//���Ϊ
function savedo(){
	if(checkFileDelSelected()){
	    this.document.forms[0].action = '/dyForm/data/datafj/download.do';
		this.document.forms[0].method = "post";
		this.document.forms[0].target = "_self";
		this.document.forms[0].submit();
	}
}




//ɾ������
function deletedo(){
	if(checkFileDelSelected()&&delConfirm()){
	    this.document.forms[0].action = '/dyForm/data/datafj/delete.do';
		this.document.forms[0].method = "post";
		this.document.forms[0].target = "_self";
		this.document.forms[0].submit();
	}
}

function delConfirm(){
  var id = this.document.forms[0].fjlist.selectedIndex;
  var val = this.document.forms[0].fjlist.options[id].text;
  alertMsg = "��ȷ��Ҫɾ�� "+val+" ��" ;
  if (!confirm(alertMsg)) {
    return false ;
  }
  else {
    return true ;
  }
}

function checkFileDelSelected(){
  var bzvalue=this.document.forms[0].fjlist.value;
  if ((bzvalue==null)||(bzvalue.length==0)){
  	alert("����ѡ��һ������") ;
    this.document.forms[0].fjlist.focus() ;
    return false;
	}
    return true;
}

//ѡ���ļ�
function setFileName(fileUrl){
        if(!fileUrl) return "";
        var ind = fileUrl.lastIndexOf("/");
        if(ind==-1) ind = fileUrl.lastIndexOf("\\");
        var str = fileUrl.substr( ind+1 );
        this.document.forms[0].fjmc.value = str;
}