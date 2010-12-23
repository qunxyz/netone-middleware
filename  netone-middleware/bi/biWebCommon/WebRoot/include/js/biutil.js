/***���÷���(begin)***/
    	
    	//��ȡelement
    	function el(i) {
  			return document.getElementById(i);
		}
		
		//��õ�index�����ַ�index��
		function getThIndex(value  , index , schstr){
			var tmp = -1 ;
			for(m=0 ; m<index ; m++){
				tmp = value.indexOf(schstr,tmp+1);
			}
			return tmp ;
		}
		
		//��õ�index��ֵ
		function getThText(value, index){
			var i = getThIndex(value,index,"[");
			var j = value.indexOf("]",i);
			if(i!=-1){
				return value.substring(i+1,j);
			}
		}
		
		//����[][]...��ʽ��ֵ��ֵ��ӵ�����
		function appendThText(value , index , text){
			var i = getThIndex(value,index,"[");
			var j = value.indexOf("]",i);
			if(i!=-1){
				var newvalue = value.substring(0,j)+text+value.substring(j);
    			return newvalue;
    		}
		}
		
		//����[][]...��ʽ��ֵ,����ԭ����ֵ��
		function setThText(value, index , text){
			var i = getThIndex(value,index,"[");
			var j = value.indexOf("]",i);
			if(i!=-1){
				var newvalue = value.substring(0,i+1)+text+value.substring(j);
				return newvalue ;
			}
		}
		
		//xmlhttp
		function xmlhttp(src){	
    		var xmlhttp ;
    		if (document.all) 
  				xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
			else 
   				xmlhttp = new XMLHttpRequest();
    		
    		xmlhttp.open("GET",src, false);
    		xmlhttp.send("");
    		//alert(xmlhttp.responseXML.documentElement.xml);
    		return xmlhttp;
    	}
		
		
		function getLeft(e)
		{
		   var l=e.offsetLeft;
		   while(e=e.offsetParent){
		     l+=e.offsetLeft;
		   }
		   return l;
		}
			
		function getTop(e){
		   var t=e.offsetTop;
		   while(e=e.offsetParent){
		     t+=e.offsetTop;
		   }
		   return t;
		}
			
		
		//�����ͼ�ϵĸ�ѡ��
    	function getTreeCheckbox(treeelement){
    		var cbx = treeelement.firstChild.lastChild.firstChild;
    		if(cbx.type){
    			if(cbx.type=="checkbox")
	    			return cbx ;
    		}
    	}
		
		//��ѡ��ֵ����෴��ֵ
		function changeCbxChecked(node){
			var cbx = getTreeCheckbox(node.getElement());
			if(cbx){
				if(cbx.disabled != true){
					cbx.checked = !cbx.checked ;
					node.checkbox = cbx.checked ;
					return cbx.checked;
				}
			}
			return false ;
		}
		
		//������ͼ�и�ѡ���ֵ
		function setCbxChecked(node,b){
			var cbx = getTreeCheckbox(node.getElement());
			if(cbx){
				if(cbx.disabled != true){
					node.checkbox = b ;
					cbx.checked = b ;
					return cbx.checked;
				}
			}
			return false ;
    	}
		
		//��ýڵ�Ĳ�Σ����ڵ�Ϊ��1�㣻
		function getNodeLevel(node){
			var i = 1 ;
			var par = node.getParent();
			while(par != null){
				i++;
				par = par.getParent();
			}
			return i ;
		}
		
		
		function getNodeActionId(node){
			var action = node.action;
			if(action){
				var i = action.indexOf("(");
				var j = action.indexOf(",",i);
				return action.substring(i+1,j).replace(/\'/g,"").replace(/\"/g,"");
			}
		}
		
		function getNodeActionLevel(node){
			var action = node.action;
			if(action){
				var i = action.indexOf("(");
				var j = action.indexOf(",",i);
				var end = action.indexOf(")",j);
				return action.substring(j+1,end).replace(/\'/g,"").replace(/\"/g,"");
			}
		}
		
		function getNextSibling(element){
			var nextele = element.nextSibling;
			while(nextele){
				if(nextele.style){
					return nextele;
				}
				nextele = nextele.nextSibling;
			}
			return null;
		}

		function getPreviousSibling(element){
			var nextele = element.previousSibling;
			while(nextele){
				if(nextele.style){
					return nextele;
				}
				nextele = nextele.previousSibling;
			}
			return null;
		}

		function getOffsetSibling(element , offset){
			var reobj = element ;
			if(offset > 0){
				for(var i=0 ; i<offset ; i++){
					reobj = getNextSibling(reobj);
					if(!reobj){
						return null ;
					}
				}
			}
			else{
				for(var i=offset ; i<0 ; i++){
					reobj = getPreviousSibling(reobj);
					if(!reobj){
						return null ;
					}
				}
			}
			return reobj ;	
		}
		
		
		/***���÷���(end)***/
		
/* --- List����  begin ---*/
function List(){	
	this.items = new Array();
}

//����item����list�б��
List.prototype.indexOf = function(obj){
	for (var i = 0; i < this.items.length; i++) {
		if (this.items[i] == obj) {
			return i;
		}
	}
	return -1;
};

List.prototype.get = function(index){
	return this.items[index];
};

//str����Ϊ��","�ָ�Ķ��ֵ;
List.prototype.add = function(){
	for(var i=0 ; i<arguments.length ; i++){
		if(typeof arguments[i] == 'string'){
			this.items = this.items.concat(arguments[i].split(","));
		}
		else{
			this.items.push(arguments[i]) ;
		}
	}
};

//�����¼��ָ����λ��
List.prototype.insert = function(index, obj){
	this.items.splice(index, 0 , obj );
};

//ɾ����i����¼
List.prototype.remove = function(index){
	this.items.splice(index , 1 );
};

//ɾ��ֵ
List.prototype.removeItem=function(obj){
	var i = this.indexOf(obj);
	if(i != -1){
		this.remove(i);
	}
};

//����
List.prototype.sort = function(){
	this.items.sort();
};

//ȡ��List�ж������
List.prototype.size = function(){
	return this.items.length ;
};

//ת�����ַ���,
List.prototype.toString = function(){
	return this.items.toString();
};
/* --- List����  end ---*/