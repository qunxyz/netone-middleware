//上传图片
function uploaddo(){
	var choice=this.document.forms[0].tpmc.value;
	if(choice==null || choice.length==0){
		alert("请先选择图片");
	}else{
	    	this.document.forms[0].action = '/dyForm/data/datatp/upload.do';
			this.document.forms[0].method = "post";
			this.document.forms[0].target = "_self";
			this.document.forms[0].submit();
	}
}
//保存图片
function saveInfodo(){
	    this.document.forms[0].action = '/dyForm/data/datatp/saveinfo.do';
		this.document.forms[0].method = "post";
		this.document.forms[0].target = "_self";
		this.document.forms[0].submit();
}

//另存为
function savedo(){
	if(checkFileDelSelected()){
	    this.document.forms[0].action = '/dyForm/data/datatp/download.do';
		this.document.forms[0].method = "post";
		this.document.forms[0].target = "_self";
		this.document.forms[0].submit();
	}
}


//删除操作
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
  alertMsg = "您确定要删除"+val+"？" ;
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
  	alert("请先选择一个图片") ;
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

//初始化
function init(){
	this.document.forms[0].tpmc.value='';
	this.document.forms[0].tplink.style.display='none';
	this.document.forms[0].tpsize.style.display='none';
}
//选择图片
function choicePicList(){
  var id = this.document.forms[0].tplist.selectedIndex;
  var linkUrl=this.document.forms[0].tplink.options[id].text;
  var size=this.document.forms[0].tpsize.options[id].text;
  this.document.forms[0].linkurl.value=linkUrl;
  this.document.forms[0].dispsize.value=size;
}

