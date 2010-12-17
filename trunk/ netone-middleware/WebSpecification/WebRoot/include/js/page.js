
var PageInfo_KEY = "page_pginfo";
var PageEvent_KEY = "page_event";

var _this ;

function PageInfo(pginfostr ,form){
	
	_this = this;
	this.itemCount;
	this.pageCount;
	this.perPage;
	this.nowpage;
	this.submitform = form ;
	
	//pginfostr�ĸ�ʽΪ���ܼ�¼������ҳ����ÿҳ��ʾ������ǰҳ��
	
	var items = pginfostr.split(",");
	if(items.length > 0)
	{
		this.itemCount = items[0];
		this.pageCount = items[1];
		this.perPage = items[2];
		this.nowpage = items[3];
	}
}

PageInfo.prototype.toPginfostr = function(){
	var str = this.itemCount + "," + this.pageCount + "," + this.perPage + "," + this.nowpage + "," ;
	return str ;
};

PageInfo.prototype.setPageEvent = function(){
	document.getElementById(PageEvent_KEY).value = "y";
};


PageInfo.prototype.write=function(){
	if(this.itemCount){ var d1= this.perPage*(this.nowpage-1)+1;
	    var d2= this.perPage*this.nowpage;
	    if(d2>this.itemCount){
	      d2=this.itemCount;
	    }
	    
	    document.writeln("<span class='pagetext'>");
		document.writeln("<input type='hidden' id="+PageInfo_KEY+" name="+PageInfo_KEY+" value='"+this.toPginfostr()+"'>");
		document.writeln("<input type='hidden' id='"+PageEvent_KEY+"' name='"+PageEvent_KEY+"' value='' />");
	    document.writeln('');
		document.writeln('��'+this.nowpage+'ҳ/�ܹ�'+this.pageCount+'ҳ &nbsp;');
		document.writeln('��ҳ��ʾ��'+d1+'������'+d2+'����¼');		
		document.writeln('&nbsp;��'+this.itemCount+'����¼&nbsp;</td> ');
		
		document.writeln('<a href="javascript:_this.goFirst();" class=\"btn_page_end\" title="��һҳ">��һҳ</a> ');
		document.writeln('<a href="javascript:_this.previous();" class="btn_page_end" title="ǰһҳ">ǰһҳ</a> ');
		document.writeln('<a href="javascript:_this.next();" class="btn_page_end" title="��һҳ" >��һҳ</a>');
		document.writeln('<a href="javascript:_this.goLast();" class="btn_page_end" title="���һҳ">���һҳ</a>');
		document.writeln('��ת��<input type="text" id="page_selpage"  value="" style="width:30px" title="���س�����ת��ָ����ҳ" onkeydown="if(event.keyCode==13) {_this.gotopage();}">ҳ'  );
		document.writeln('</span>');
		
		}
};

PageInfo.prototype.goFirst=function(){
	if(this.nowpage==1){
		return ;
	}
	else{
		this.nowpage = 1 ;
		document.getElementById(PageInfo_KEY).value=this.toPginfostr();	
		this.setPageEvent();
		this.submitform.submit();
	}
};

PageInfo.prototype.goLast=function(){
	if(this.nowpage==this.pageCount){
		return ;
	}
	else{
		this.nowpage = this.pageCount ;
		document.getElementById(PageInfo_KEY).value=this.toPginfostr();	
		this.setPageEvent();
		this.submitform.submit();
	}
};

PageInfo.prototype.previous=function(){
	if(this.nowpage==1){
		return ;
	}
	else{
		this.nowpage = parseInt(this.nowpage)-1 ; 
		if(this.nowpage < 1){
			this.nowpage = 1 ;
		}
		document.getElementById(PageInfo_KEY).value=this.toPginfostr();	
		this.setPageEvent();
		this.submitform.submit();
	}
};

PageInfo.prototype.next=function(){
	if(this.nowpage==this.pageCount){
		return ;
	}
	else{
		this.nowpage = parseInt(this.nowpage)+1 ;
		if(this.nowpage > this.pageCount ){
			this.nowpage = this.pageCount ;
		}
		document.getElementById(PageInfo_KEY).value=this.toPginfostr();
		this.setPageEvent();
		this.submitform.submit();
	}
};

PageInfo.prototype.gotopage=function(){
	this.nowpage = document.getElementById("page_selpage").value;
	document.getElementById(PageInfo_KEY).value=this.toPginfostr();
	this.setPageEvent();
	this.submitform.submit();
};

PageInfo.prototype.setperpage=function(){
	if(event.keyCode==13) {
		var newper = document.getElementById("page_perpage").value ;
		if(isDigit(newper)){
			if(newper != this.perPage){
				this.perPage = newper;
				document.getElementById(PageInfo_KEY).value=this.toPginfostr();
				this.setPageEvent();
				this.submitform.submit();
			}
		}
	}
};


function isDigit(s)
{
	var patrn=/^[0-9]{1,20}$/;
	if (!patrn.exec(s)) return false
	return true
}

