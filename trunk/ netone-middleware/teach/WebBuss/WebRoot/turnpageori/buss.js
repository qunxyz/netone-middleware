		//����
		function create(path){
			window.open(path,'_blank',"width=700,height=350,resizable=yes,left=150,status=yes");
		}
		//����
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
					alert("����ѡ����Ҫ�޸ĵ�ѡ��");
					return;
				}
				if(k>1){
					alert("ֻ��ѡ��������޸�");
					return;
				}
	
			window.open(path+'?lsh='+value,'_blank',"width=700,height=350,resizable=yes,left=150,status=yes");
		}
		//ɾ��
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
					alert("����ѡ����Ҫ�޸ĵ�ѡ��");
					return;
				}

			window.open(path+'?lsh='+value,'_blank',"width=700,height=350,resizable=yes,left=150,status=yes");
		}
		//��ѯ
		function search(){
			document.forms[0].submit();
		}
