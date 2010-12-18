

//新建并上传文件
function uploadfile(){
	if(form1.filename.value==""){
		alert("文件名不能为空");
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
//自动填写上传文件信息
function autofile(){
	var filename = document.all.files.value;
	filename = filename.substring(filename.lastIndexOf('\\')+1,filename.length);
	document.all.filename.value = filename;
	document.all.naturalname.value = filename;
}
//全选
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
//进入修改页面
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
			alert("请先选中需要修改的选项");
			return;
		}
		if(k>1){
			alert("只能选择单项进行修改");
			return;
		}
		window.open("PagelistModifySvl?task=edit&chkid="+value+"&pagename="+document.all.pagename.value,"usermod","width=700,height=350,resizable=yes,left=150,status=yes,scrollbars=yes");
	}
}

function view(chkid){
	window.open("PagelistViewSvl?chkid="+chkid+"&pagename="+document.all.pagename.value,"usermod","width=600,height=450,resizable=yes,left=150,status=yes,scrollbars=yes");
}
//执行删除
function del(chkid){
	if(chkid){
		if(confirm("确定删除吗？")){
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
			alert("请先选中需要删除的选项");
			return;
		} else {
			if(confirm("您确认执行删除操作么？")){
				form1.action="PagelistDeleteSvl?task=del";
				form1.target='_blank';
				form1.submit();
				form1.target='_self';
			}
		}
	}
}
//新建后刷新
function afterCreate(){
	form1.action="PagelistRightSvl";
	form1.submit();
}
//修改后刷新
function afterEdit(){
	opener.form1.action="PagelistRightSvl";
	opener.form1.submit();
	window.close();
}
//进入新建页面
function newElemnt(){
	var id = document.all.id.value ;
	var appid = document.all.appid.value ;
	var ou = document.all.ou.value;
	window.open("PagelistAddSvl?id="+id+"&appid="+appid+"&ou="+ou+"&pagename="+document.all.pagename.value,"create",'width=600,height=400,resizable=yes,left=300,top=180,status=yes,scrollbars=yes');
}
//进入上传页面
function newElementBinary(){
	window.open("PagelistUploadSvl?task=addfile&dirid="+document.all.id.value+"&appid="+document.all.appid.value+"&pagename="+document.all.pagename.value,"create",'width=600,height=400,resizable=yes,left=300,top=180,status=yes,scrollbars=yes');
}
//执行新建
function create(){
	if(document.all.naturalname.value=="" || document.all.name.value==""){
		alert("名称与中文名称不允许为空!");
		return;	
	}
	document.forms[0].submit(); 
}
//上移
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
		alert("请先选中需要上移的选项");
		return;
	}
	if(k>1){
		alert("只能选择单项进行上移");
		return;
	}
	if(value2==null){
		alert("已经是顶层,无法上移");
		return;
	}
	window.open("MoveSvl?value="+value+"&value2="+value2);

}
//下移
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
		alert("请先选中需要下移的选项");
		return;
	}
	if(k>1){
		alert("只能选择单项进行下移");
		return;
	}
	if(value2==null){
		alert("已经是底层,无法下移");
		return;
	}

	window.open("MoveSvl?value="+value+"&value2="+value2);
}
