function filecreate(){
	var filename = document.all.upfile.value;
	if(filename.indexOf(".xls")!=-1){
		document.forms[0].submit();
	} else {
		alert("��ѡ��Excel�ļ�")
	}
}