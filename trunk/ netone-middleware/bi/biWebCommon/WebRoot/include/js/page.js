
var PageInfo_KEY = "page_pginfo";

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
}

PageInfo.prototype.write=function(){
	if(this.itemCount){
		document.writeln("<input type='hidden' id="+PageInfo_KEY+" name="+PageInfo_KEY+" value='"+this.toPginfostr()+"'>");
		document.writeln('<table align="center"><tr><td align="center">');
		document.writeln('共 <b>'+this.itemCount+'</b> 条 &nbsp;');
		document.writeln('第<b>'+this.nowpage+'/'+this.pageCount+'</b>页&nbsp;');
//		document.writeln('每页<b>'+this.perPage+'</b>条 &nbsp;');
		document.writeln('每页<input type="text" style="width: 30px" id="page_perpage" value='+this.perPage+' onkeydown="_this.setperpage();">条 &nbsp;');		
		
		document.writeln('<a href="javascript:_this.goFirst();">首页</a> ');
		document.writeln('<a href="javascript:_this.previous();">上一页</a> ');
		document.writeln('<a href="javascript:_this.next();">下一页</a> ');
		document.writeln('<a href="javascript:_this.goLast();">尾页</a> &nbsp;');
		
		document.writeln('转到 <select id="page_selpage" onchange="_this.gotopage()" >');
		for(i=1 ;i<=this.pageCount ;i++){
			document.writeln('<option value='+i+' '+ (this.nowpage==i?'selected':'')+'>第'+i+'页</option>');
		}
		document.writeln('</select>');
		
		document.writeln('</td></tr></table>');
	}
}

PageInfo.prototype.goFirst=function(){
	if(this.nowpage==1){
		return ;
	}
	else{
		this.nowpage = 1 ;
		document.getElementById(PageInfo_KEY).value=this.toPginfostr();	
		this.submitform.submit();
	}
}

PageInfo.prototype.goLast=function(){
	if(this.nowpage==this.pageCount){
		return ;
	}
	else{
		this.nowpage = this.pageCount ;
		document.getElementById(PageInfo_KEY).value=this.toPginfostr();	
		this.submitform.submit();
	}
}

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
		this.submitform.submit();
	}
}

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
		this.submitform.submit();
	}
}

PageInfo.prototype.gotopage=function(){
	this.nowpage = document.getElementById("page_selpage").value;
	document.getElementById(PageInfo_KEY).value=this.toPginfostr();
	this.submitform.submit();
}

PageInfo.prototype.setperpage=function(){
	if(event.keyCode==13) {
		var newper = document.getElementById("page_perpage").value ;
		if(isDigit(newper)){
			if(newper != this.perPage){
				this.perPage = newper;
				document.getElementById(PageInfo_KEY).value=this.toPginfostr();
				this.submitform.submit();
			}
		}
	}
}


function isDigit(s)
{
	var patrn=/^[0-9]{1,20}$/;
	if (!patrn.exec(s)) return false
	return true
}

