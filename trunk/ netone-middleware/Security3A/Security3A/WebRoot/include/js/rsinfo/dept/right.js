function uploadfile(){
	if(form1.filename.value==""){
		alert("�ļ�������Ϊ��");
		form1.filename.focus();
		return;
	}
	form1.action="rsinfo/dept/RSUpdateLoad.do?task=uploadfile";
	form1.submit();
}

function changefile(){
	var filename = document.all.files.value;
	filename = filename.substring(filename.lastIndexOf('\\')+1,filename.length);
	document.all.filename.value = filename;
	document.all.naturalname.value = filename;
}

function docheck(){
	if(document.all.checkall.checked){
		var checkids = document.getElementsByTagName("input");
		for(var i=0 ; i<checkids.length ; i++){
			if(checkids[i].type=="checkbox"){
				if(checkids[i].checked){
					
				}else{
					checkids[i].checked=true;
				}
			}
		}
	}
	if(document.all.checkall.checked==false){
		var checkids = document.getElementsByTagName("input");
		for(var i=0 ; i<checkids.length ; i++){
			if(checkids[i].type=="checkbox"){
				if(checkids[i].checked){
					checkids[i].checked=false;
				}
			}
		}
	}
}
function edit(){
	var k = 0;
	var value;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				value = form1.elements[i].value;
				k++;
			}
		}
	}
	if(k==0){
		alert("����ѡ����Ҫ�޸ĵ�ѡ��");
		return;
	}
	if(k>1){
		alert("ֻ��ѡ��������޸�");
		return;
	}
	window.open(document.all.jsppath.value+"/DepartmentModify.do?task=edit&chkid="+value,"usermod","width=700,height=350,resizable=yes,left=150,status=yes");
}

function del(){
	var k = 0;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				k = 1;
				break;
			}
		}
	}
	if(k==0){
		alert("����ѡ����Ҫɾ����ѡ��");
		return;
	} else {
		if(confirm("��ȷ��ִ��ɾ������ô��")){
			form1.action="rsinfo/dept/DepartmentDelete.do?task=del";
			form1.submit();
		}
	}
}

function doModify(){
	if(confirm("ȷ���޸���")){
		form1.action="rsinfo/dept/DepartmentModify.do?task=modify";
		form1.submit();
	}
}


function doDelete1(){
	if(confirm("ȷ��ɾ����")){
	   	   var objecttypexx=$('objecttype').value;
	       var actionurl=$('actionurl');
		   if(objecttypexx=='jxs'){
		       deleteTargetx(actionurl.value);
		       deleteStorex(actionurl.value);
		       deleteClientinfox(actionurl.value);
		   }else if(objecttypexx=='fxs'){
		        deleteTargetx(actionurl.value);
		        deleteClientinfox(actionurl.value);
		   }else if(objecttypexx=='store'){
		        deleteStorex(actionurl.value);
		   }
		   
	    form1.action="rsinfo/dept/DepartmentDelete.do?task=delete";
		form1.submit();
	}
   
   
}

function deleteTargetx(code){
		var market = new Ajax.Request("/iss/deleteSellTargetSvl?namex=" +code, {method:"get", parameters:"", asynchronous:false});
		var marketinfo = market.transport.responseText;
}
function deleteStorex(code){
		var market = new Ajax.Request("/iss/deleteStorageSvl?namex=" + code, {method:"get", parameters:"", asynchronous:false});
		var marketinfo = market.transport.responseText;	
}
function deleteClientinfox(code){
		var market = new Ajax.Request("/iss/DeleteClientInfoSvl?namex=" + code, {method:"get", parameters:"", asynchronous:false});
		var marketinfo = market.transport.responseText;	
}



function afterCreate(){
	var id = document.all.id.value ;
	var tree = parent.frames["left"].functree;
	var node = tree.getSelected();
	if(!node.src && node.getParent()){ 
		node.getParent().reload();
	}else{
		node.reload();
		node.expand();
	}
}

function afterModify(name){
	var tree = parent.frames["left"].functree;

	tree.getSelected().setText(name);
	
}

function afterEdit(){
	var tree = opener.parent.frames["left"].functree;
	var node = tree.getSelected();
	if(!node.src && node.getParent()){ 
		node.getParent().reload();
	}else{
		node.reload();
		node.expand();
	}
	window.close();
}

function afterDelete(){
	var tree = parent.frames["left"].functree;
	tree.getSelected().remove();
	alert("ɾ���ɹ���");
}

function afterDel(){
	var tree = parent.frames["left"].functree;
	var node = tree.getSelected();
	if(!node.src && node.getParent()){ 
		node.getParent().reload();
	}else{
		node.reload();
		node.expand();
	}
	alert("ɾ���ɹ���");
}

function afterMove(){
	alert("�ƶ��ɹ���");
	var tree = parent.frames["left"].functree;
	tree.reload();
	tree.expand();
}

function changeToCreateModel(){
	var id = document.all.id.value ;
	var appid = document.all.appid.value ;
	var ou = document.all.ou.value;
	window.open(document.all.jsppath.value+"/rsinfo/dept/DepartmentAdd.do?id="+id+"&appid="+appid+"&ou="+ou,"create",'width=700,height=500,resizable=yes,left=300,top=180,status=yes');
}

function changeToCreateModel2(){
	window.open(document.all.jsppath.value+"/RSUpdateLoad.do?task=addfile&dirid="+document.all.id.value+"&appid="+document.all.appid.value,"create",'width=600,height=300,resizable=yes,left=300,top=180,status=yes');
}

function humaninput(){
	var id = document.all.id.value;
	window.open(document.all.jsppath.value+"/DepartmentInput.do?task=addfile&id="+id,"create",'width=500,height=100,resizable=yes,left=150,status=yes');
}

function humanoutput(){
	form1.action = "rsinfo/dept/DepartmentOutput.do";
	form1.submit();
}

function filecreate(){
	var filename = document.all.upfile.value;
	if(filename.indexOf(".xls")!=-1){
		document.forms[0].submit();
	} else {
		alert("��ѡ��Excel�ļ�")
	}
}

function movedept(){
	var deptid = document.all.id.value;
	window.open(document.all.jsppath.value+"/DepartmentSelect.do?func=movedept&deptid="+deptid,'��֯ѡ��','width=400,height=350,resizable=yes,left=250,top=100,status=yes');
}

function doMove(oldid,newid){
	document.all.oldid.value = oldid;
	document.all.newid.value = newid;
	form1.action = "rsinfo/dept/DepartmentMove.do";
	form1.submit();
}

function relatinghuman(){
	var id = document.all.id.value;
	var name = document.all.name.value;
	window.open(document.all.jsppath.value+"/DepartmentRelate.do?&id="+id+"&description="+name,'�����û�','width=300,height=300,resizable=yes,left=300,top=180,status=yes');
}

function newoper(){
	if(document.all.naturalname.value=="" || document.all.name.value==""){
		alert("�������������Ʋ�����Ϊ��!");
		return;	
	}
		//����Ƿ��������
	var chinese = /^.*[\u0391-\uFFE5]+.*$/;

	if(document.all.naturalname.value.match(chinese)){
	   	alert("ƴ��������Ϊ����");
		return;	
	}
	document.forms[0].submit(); 
}

function refresh(id,ou){
	parent.right.location.href.location.href = "DepartmentRight.do?id=" + id + "&ou=" + ou;
}

function lookuser(){
	var value = document.all.users.value;
	if(value!=""){
		window.open(document.all.jsppath.value+"/human/humanModify.do?task=modify&description="+value,"open","width=700,height=350,resizable=yes,left=150,top=180,status=yes");
	}
}