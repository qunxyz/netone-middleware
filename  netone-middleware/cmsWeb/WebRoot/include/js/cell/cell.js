	
	function initvalue(){
			
			document.forms[0].fiCopy.value=document.forms[0].fiColumnId.value;
			document.forms[0].cellToolCopy.value=document.forms[0].cellTool.value;
			selecttool();
	}
	
	
	function selecttool(){
		var selectele=document.forms[0].select.value;
		if(selectele==0||selectele==null){
			document.getElementById('td1').style.display='none';
			document.getElementById('td2').style.display='none';
			document.getElementById('td3').style.display='none';
			document.getElementById('td4').style.display='none';
			return;
		}
		if(selectele==1){
		   
			document.getElementById('td1').style.display='';
			document.getElementById('td2').style.display='none';
			document.getElementById('td3').style.display='none';
			document.getElementById('td4').style.display='none';
			return;
		}
		if(selectele==2){
		
			document.getElementById('td1').style.display='none';
			document.getElementById('td2').style.display='';
			document.getElementById('td3').style.display='none';
			document.getElementById('td4').style.display='none';
			return;
		}		
		if(selectele==3){
		
			document.getElementById('td1').style.display='none';
			document.getElementById('td2').style.display='none';
			document.getElementById('td3').style.display='';
			document.getElementById('td4').style.display='none';
			return;
		}
		if(selectele==4){
		
			document.getElementById('td1').style.display='none';
			document.getElementById('td2').style.display='none';
			document.getElementById('td3').style.display='none';
			document.getElementById('td4').style.display='';
			return;
		}
	}
	//更新文件
	function changefi(){
	    var path=document.forms[0].path.value;
		document.forms[0].action=path+"/infocelltool.do?newFlag=changeFi";
		document.forms[0].submit();	
	}
	

	//更新组
	function changeGroup(){
		    var path=document.forms[0].path.value;
			document.forms[0].action=path+"/infocelltool.do?newFlag=changeGroup";
			document.forms[0].submit();	
	}
		
	//选择组
	function changeCellGroup(){
			var path=document.forms[0].path.value;
			document.forms[0].action=path+"/infocelltool.do?newFlag=changeCellGroup";
			document.forms[0].submit();	
	}
	
	function commit(url){
			var group=document.forms[0].belongto.value;
			if(group==null||group==""){
				alert('请选择组');
				return;
			}
			var cellname=document.forms[0].cellname;
			if(cellname==null||cellname==""){
				alert('请填写Portalet名');
				return;
			}

			document.forms[0].action=url;
			document.forms[0].submit();	
	}
	
	function commitQuick(url){
			var group=document.forms[0].belongto.value;
			if(group==null||group==""){
				alert('请选择组');
				return;
			}
			var cellname=document.forms[0].cellname;
			if(cellname==null||cellname==""){
				alert('请填写Portalet名');
				return;
			}

			document.forms[0].action=url;
			document.forms[0].submit();	
	}
	

	
