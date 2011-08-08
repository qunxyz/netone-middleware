
//显示层次管理
function managerthis(id,type,model) {

	if(type=='path'){
		window.open("PagelistpathRightSvl?pagename="+model+"&appname="+id);
	}else{
		window.open("PagelistRightSvl?pagename="+model+"&appname="+id);
	}
}

		//显示树
		function viewthis(id,model) {
			window.open("SelectSvl?appid="+id+"&pagename="+model,'_blank', 'height=380, width=600, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
		}
		//显示多选择
		function viewthismulti(id,model) {
			window.open("MSelectSvl?appid="+id+"&pagename="+model,'_blank', 'height=380, width=600, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
		}
		//显示但选择
		function viewthissingle(id,model) {
			window.open("SSelectSvl?appid="+id+"&pagename="+model,'_blank', 'height=380, width=500, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=yes,location=no, status=no');
		}

//进入修改页面
function edit(chkid){
	if(chkid){
		window.open("ApplistModifySvl?task=edit&chkid="+chkid+"&pagename="+document.all.pagename.value,"usermod","width=700,height=350,resizable=yes,left=150,status=yes");
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
		window.open("ApplistModifySvl?task=edit&chkid="+value+"&pagename="+document.all.pagename.value,"usermod","width=700,height=350,resizable=yes,left=150,status=yes");
	}
}
//执行删除
function del(chkid){
	form1.target='_blank';
	if(chkid){
		if(confirm("确定删除吗？")){
			form1.action="ApplistDeleteSvl?task=delone&delid="+chkid;
			
			form1.submit();

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
				form1.action="ApplistDeleteSvl?task=del";
				form1.submit();
			}
		}
	}
	form1.target='_self';
}
//新建后刷新
function afterCreate(){
	form1.action="ApplistRightSvl";
	form1.submit();
}
//修改后刷新
function afterEdit(){
	opener.form1.action="ApplistRightSvl";
	opener.form1.submit();
	window.close();
}
//进入新建页面
function newElemnt(){
	window.open("ApplistAddSvl?pagename="+document.all.pagename.value,"create",'width=600,height=400,resizable=yes,left=300,top=180,status=yes');
}
//执行新建
function create(){
	if(document.all.naturalname.value=="" || document.all.name.value==""){
		alert("名称与中文名称不允许为空!");
		return;	
	}
	document.forms[0].submit(); 
}
