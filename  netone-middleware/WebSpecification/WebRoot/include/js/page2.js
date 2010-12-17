
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
	
	//pginfostr的格式为：总记录数，总页数，每页显示数，当前页，
	
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
	if(this.itemCount){
		document.writeln("<input type='hidden' id="+PageInfo_KEY+" name="+PageInfo_KEY+" value='"+this.toPginfostr()+"'>");
		document.writeln("<input type='hidden' id='"+PageEvent_KEY+"' name='"+PageEvent_KEY+"' value='' />");
		document.writeln('<table align="center"><tr><td align="right">');
		document.writeln('共<b>['+this.pageCount+']</b>页&nbsp;');
		document.writeln('记录数<b>['+this.itemCount+']</b> &nbsp;');
		document.writeln('<input type="text" style="width: 30px;display:none" id="page_perpage" value='+this.perPage+' onkeydown="_this.setperpage();">');		
		
		document.writeln('<a href="javascript:_this.goFirst();">首页</a> ');
		document.writeln('<a href="javascript:_this.previous();">上一页</a> ');
		document.writeln('<a href="javascript:_this.next();">下一页</a> ');
		document.writeln('<a href="javascript:_this.goLast();">尾页</a> &nbsp;');
		
		document.writeln('<a href="javascript:_this.gotopage();">跳转</a><input type="text" id="page_selpage" value="0">');
		
		document.writeln('</td></tr></table><br>');
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

