//�ϴ�ͼƬ
function uploaddo(){
	var choice=this.document.forms[0].tpmc.value;
	if(choice==null || choice.length==0){
		alert("����ѡ��ͼƬ");
	}else{
	    	this.document.forms[0].action = '/dyForm/data/datatp/upload.do';
			this.document.forms[0].method = "post";
			this.document.forms[0].target = "_self";
			this.document.forms[0].submit();
	}
}
//����ͼƬ
function saveInfodo(){
	    this.document.forms[0].action = '/dyForm/data/datatp/saveinfo.do';
		this.document.forms[0].method = "post";
		this.document.forms[0].target = "_self";
		this.document.forms[0].submit();
}

//���Ϊ
function savedo(){
	if(checkFileDelSelected()){
	    this.document.forms[0].action = '/dyForm/data/datatp/download.do';
		this.document.forms[0].method = "post";
		this.document.forms[0].target = "_self";
		this.document.forms[0].submit();
	}
}


//ɾ������
function deletedo(){
	if(checkFileDelSelected()&&delConfirm()){
	    this.document.forms[0].action = '/dyForm/data/datatp/delete.do';
		this.document.forms[0].method = "post";
		this.document.forms[0].target = "_self";
		this.document.forms[0].submit();
	}
}

function delConfirm(){
  var id = this.document.forms[0].tplist.selectedIndex;
  var val = this.document.forms[0].tplist.options[id].text;
  alertMsg = "��ȷ��Ҫɾ��"+val+"��" ;
  if (!confirm(alertMsg)) {
    return false ;
  }
  else {
    return true ;
  }
}

function checkFileDelSelected(){
  var bzvalue=this.document.forms[0].tplist.value;
  if ((bzvalue==null)||(bzvalue.length==0)){
  	alert("����ѡ��һ��ͼƬ") ;
    this.document.forms[0].tplist.focus() ;
    return false;
	}
    return true;
}

function setFileName(fileUrl){
        	if(!fileUrl) return "";
         	var ind = fileUrl.lastIndexOf("/");
         	if(ind==-1) ind = fileUrl.lastIndexOf("\\");
         	var str = fileUrl.substr( ind+1 );
         	this.document.forms[0].tpmc.value = str;
}

//��ʼ��
function init(){
	this.document.forms[0].tpmc.value='';
	this.document.forms[0].tplink.style.display='none';
	this.document.forms[0].tpsize.style.display='none';
}
//ѡ��ͼƬ
function choicePicList(){
  var id = this.document.forms[0].tplist.selectedIndex;
  var linkUrl=this.document.forms[0].tplink.options[id].text;
  var size=this.document.forms[0].tpsize.options[id].text;
  this.document.forms[0].linkurl.value=linkUrl;
  this.document.forms[0].dispsize.value=size;
}

