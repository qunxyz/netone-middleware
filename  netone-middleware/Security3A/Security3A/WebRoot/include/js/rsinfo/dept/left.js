
function nodeAction(id, ou, naturalname, parentdir, url) {
	var urlx = window.location.href;
	var pos = urlx.lastIndexOf("\\");//����������ļ���·���ָ��Ϊ"\"    
	if (pos == -1) {     //pos==-1��ʾΪ�����ļ�      
	}
	pos = urlx.lastIndexOf("/");   //�����ļ�·���ָ��Ϊ"/"      
	var path = urlx.substring(0, pos + 1);   //��url�л���ļ���    
	var frame = parent.frames["right"];
	frame.location.href = path + "DepartmentRight.do?id=" + id + "&ou=" + ou;
}
function submitform() {
	document.forms[0].submit();
}
function addapp() {
	window.open("newsystem.jsp", "addapp", "width=700,height=350,resizable=yes,left=150,status=yes");
}
function doaddapp() {
	form1.action = "rsinfo/dept/DepartmentTree.do?task=add";
	form1.submit();
}
function modapp() {
	var editdiv = document.getElementById("editdiv");
	editdiv.style.display = "block";
	document.all.task.value = "mod";
}
function delapp() {
	if (confirm("\u662f\u5426\u5220\u9664")) {
		document.all.task.value = "del";
		document.forms[0].submit();
	}
}
function cancle() {
	var editdiv = document.getElementById("editdiv");
	editdiv.style.display = "none";
}

