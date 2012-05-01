		//创建
		function create(path){
			window.open(path,'_blank',"width=700,height=350,resizable=yes,left=150,status=yes");
		}
		//更新
		function update(path){
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
	
			window.open(path+'?lsh='+value,'_blank',"width=700,height=350,resizable=yes,left=150,status=yes");
		}
		//删除
		function dele(path){
				var k = 0;
				var value='';
				for(var i=0 ; i<form1.elements.length ; i++) {
					if (form1.elements[i].name=="chkid") {
						if(form1.elements[i].checked==true){
							value =value+","+ form1.elements[i].value;
							k++;
						}
					}
				}
				if(k==0){
					alert("请先选中需要修改的选项");
					return;
				}

			window.open(path+'?lsh='+value,'_blank',"width=700,height=350,resizable=yes,left=150,status=yes");
		}
		//查询
		function search(){
			document.forms[0].submit();
		}
