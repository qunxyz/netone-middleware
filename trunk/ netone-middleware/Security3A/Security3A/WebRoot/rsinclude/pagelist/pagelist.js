

//�½����ϴ��ļ�
function uploadfile(){
	if(form1.filename.value==""){
		alert("�ļ�������Ϊ��");
		form1.filename.focus();
		return;
	}
	var filename = document.all.filename.value;
	var naturalname = document.all.naturalname.value;
	var extendattribute = document.all.extendattribute.value;
	var dirid = document.all.dirid.value;
	var appid = document.all.appid.value;
	var pagename = document.all.pagename.value;
	var active = document.all.active.value;
	var objecttype=document.all.objecttype.value;
	form1.action="PagelistUploadSvl?task=uploadfile&filename="+filename+"&naturalname="+naturalname+"&extendattribute="+extendattribute+"&dirid="+dirid+"&appid="+appid+"&pagename="+pagename+"&active="+active+"&objecttype="+objecttype;
	form1.submit();
}
//�Զ���д�ϴ��ļ���Ϣ
function autofile(){
	var filename = document.all.files.value;
	filename = filename.substring(filename.lastIndexOf('\\')+1,filename.length);
	document.all.filename.value = filename;
	document.all.naturalname.value = filename;
}
//ȫѡ
function allcheck(){
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
//�����޸�ҳ��
function edit(chkid){
	if(chkid){
		window.open("PagelistModifySvl?task=edit&chkid="+chkid+"&pagename="+document.all.pagename.value,"usermod","width=700,height=350,resizable=yes,left=150,status=yes,scrollbars=yes");
	} else {
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
		window.open("PagelistModifySvl?task=edit&chkid="+value+"&pagename="+document.all.pagename.value,"usermod","width=700,height=350,resizable=yes,left=150,status=yes,scrollbars=yes");
	}
}

function view(chkid){
	window.open("PagelistViewSvl?chkid="+chkid+"&pagename="+document.all.pagename.value,"usermod","width=600,height=450,resizable=yes,left=150,status=yes,scrollbars=yes");
}
//ִ��ɾ��
function del(chkid){
	if(chkid){
		if(confirm("ȷ��ɾ����")){
			form1.action="PagelistDeleteSvl?task=delone&delid="+chkid;
			form1.target='_blank';
			form1.submit();
			form1.target='_self';
		}
	} else {
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
				form1.action="PagelistDeleteSvl?task=del";
				form1.target='_blank';
				form1.submit();
				form1.target='_self';
			}
		}
	}
}
//�½���ˢ��
function afterCreate(){
	form1.action="PagelistRightSvl";
	form1.submit();
}
//�޸ĺ�ˢ��
function afterEdit(){
	opener.form1.action="PagelistRightSvl";
	opener.form1.submit();
	window.close();
}
//�����½�ҳ��
function newElemnt(){
	var id = document.all.id.value ;
	var appid = document.all.appid.value ;
	var ou = document.all.ou.value;
	window.open("PagelistAddSvl?id="+id+"&appid="+appid+"&ou="+ou+"&pagename="+document.all.pagename.value,"create",'width=600,height=400,resizable=yes,left=300,top=180,status=yes,scrollbars=yes');
}
//�����ϴ�ҳ��
function newElementBinary(){
	window.open("PagelistUploadSvl?task=addfile&dirid="+document.all.id.value+"&appid="+document.all.appid.value+"&pagename="+document.all.pagename.value,"create",'width=600,height=400,resizable=yes,left=300,top=180,status=yes,scrollbars=yes');
}
//ִ���½�
function create(){
	if(document.all.naturalname.value=="" || document.all.name.value==""){
		alert("�������������Ʋ�����Ϊ��!");
		return;	
	}
	document.forms[0].submit(); 
}
//����
function up(){
	var k = 0;
	var value;
	var value2 = null;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				value = form1.elements[i].value;
				if(form1.elements[i-1].name=="chkid"){
					value2 = form1.elements[i-1].value;
				}
				k++;
			}
		}
	}
	if(k==0){
		alert("����ѡ����Ҫ���Ƶ�ѡ��");
		return;
	}
	if(k>1){
		alert("ֻ��ѡ�����������");
		return;
	}
	if(value2==null){
		alert("�Ѿ��Ƕ���,�޷�����");
		return;
	}
	window.open("MoveSvl?value="+value+"&value2="+value2);

}
//����
function down(){
	var k = 0;
	var value;
	var value2 = null;
	for(var i=0 ; i<form1.elements.length ; i++) {
		if (form1.elements[i].name=="chkid") {
			if(form1.elements[i].checked==true){
				value = form1.elements[i].value;
				if(form1.elements[i+1].name=="chkid"){
					value2 = form1.elements[i+1].value;
				}
				k++;
			}
		}
	}
	if(k==0){
		alert("����ѡ����Ҫ���Ƶ�ѡ��");
		return;
	}
	if(k>1){
		alert("ֻ��ѡ�����������");
		return;
	}
	if(value2==null){
		alert("�Ѿ��ǵײ�,�޷�����");
		return;
	}

	window.open("MoveSvl?value="+value+"&value2="+value2);
}
