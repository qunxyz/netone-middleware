
/**
 * ��ҳ�ű�
 *<ul>
 * <li><li>
 *</ul>
 *
 *@author zhang.chao.yi
 *@author 
 *@version 1.0 Date 2007-6-19
 */
 
var NumPage = Class.create();
var pageForm = document.forms[0];

NumPage.prototype = {
 /**
  *���ò���
  *
  *options ��������
  */
 setOptions : function(options){
     this.options ={
	   form : document.forms[0],
	   
	   current : 1,
	   size : 20,
	   totalPage : 0,
	   length : 10,
	   
	   pageImageDirector : ''
	 }
	 Object.extend(this.options, options || {});
	  	 
 },
 
 /**
  *������ʽ
  *
  *styles ��ʽ
  */
 setStyles : function(styles){
     this.styles ={
	    background_repeat : 'background-repeat:no-repeat;',
	    textStyle  : 'font-size: 12px;color: #999999;text-decoration: none;text-align: center;vertical-align: top;',
	    numStyle :  'font-size: 9px;color: #999999;text-decoration: none;text-align: center;cursor: hand;vertical-align: top;',
	    curNumStyle : 'font-size: 9px;color: #ff0000;text-decoration: none;text-align: center;cursor: hand;vertical-align: top;'
	 }
	 Object.extend(this.styles, styles || {});

 },
 
 /**
  *��ʼ��
  *
  *options ����
  *styles ��ʽ
  */
 initialize : function(options, styles){
    this.setOptions(options);
	this.setStyles(styles);
	pageForm = this.options.form;
	
	this.printNumPage();

	 
 },
 
 /**
  *��ӡNumPage
  *
  *
  */
 printNumPage : function(){
    var buffer = '';
	buffer += '<table width=420 border=0 align=center cellpadding=0 cellspacing=0>';
	buffer+=' <tr> ';
	
	buffer += '<td width=70 valign=top> ';
	buffer += '<table width=100% height=16 border=0 align=right cellpadding=0 cellspacing=0 >';
	buffer += '<tr> ';
	buffer += '  <td style="'+ this.styles.textStyle + '">�ܹ�:<span style="' + this.styles.curNumStyle+ '">'
	             + this.options.totalPage + '</span>ҳ</td>';
	buffer += ' </tr>';
	buffer += '</table>';
	buffer += ' </td>';
	
	//�ж��Ƿ��ṩ��һҳ����
	if(this.options.current > 1){
	   buffer += '<td width=20 valign=top>';
	   buffer += '<a title="��һҳ" href="javascript:" onclick="javascript:goPage(' + (this.options.current-1) + ')">';
	   buffer += '<img src=' + this.options.pageImageDirector + '/previous.gif  height=17 border=0>';
	   buffer += '</a></td>';
	}
	
	//��һҳ����
    buffer += '<td width=20 height=19 valign=top>';
	buffer += '<a title="��һҳ" href="javascript:" onclick="javascript:goPage(1)">';
	buffer += '<img src=' + this.options.pageImageDirector + '/first.gif  height=17 border=0> ';
	buffer += '</a></td>';
	 
    //�м�����ҳ����
	var first = this.options.current - (this.options.current - 1) % this.options.length;
	var last =  (first + this.options.length) - 1;
    if(last > this.options.totalPage){
	  last = this.options.totalPage;
	}
	
	var _style;
	for(var k = first; k <= last; k++){
       buffer += '<td width="20" align="center" valign="top" background="'
	     + this.options.pageImageDirector + '/numbg.gif" style="' + this.styles.background_repeat + '">';
	   buffer += '<table width="20"  border="0" align="center" cellpadding="0" cell��spacing="0" >';
       
	   if(k == this.options.current){
	     _style = this.styles.curNumStyle;
	   }else{
	     _style = this.styles.numStyle;
	   }
	   
	   buffer += '<tr>';
	   buffer += '<td width=100% align=center valign=top >';
	   buffer += '<a  href="javascript:" onclick="javascript:goPage(' + k + ')">';
	   buffer += '<font style="' + _style + '">' + k + '</font>';
	   buffer += '</a>';
	   buffer += '</td>';
	   buffer += '</tr></table></td>';
	}

    //���һҳ����
	buffer += '<td width=20 valign=top>';
    buffer += '<table width=19 border=0 cellspacing=0 cellpadding=0>';
    buffer += '<tr> ';
    buffer += '<td><div align=right> <a title=���һҳ href="javascript:" onclick="javascript:goPage(' + this.options.totalPage +')"> ';
    buffer += '<img src=' + this.options.pageImageDirector + '/last.gif  height=17 border=0> ';
    buffer += '</a> </div></td>';
	buffer += ' </tr>';
	buffer += '</table>';
	buffer += '</td>';
	
	//�ж��Ƿ�����һҳ
	if(this.options.current < this.options.totalPage){
		buffer += '<td width=20 valign=top> ';
		buffer += '<a title=��һҳ href="javascript:" onclick="javascript:goPage(' + (this.options.current+1) + ')"> ';
		buffer += '<img src='+this.globalPath+'/images/next.gif  height=17 border=0> ';
		buffer += '</a></td>';
	}
	
	//������ת����
	buffer += '<td valign="top" style="' + this.styles.textStyle  + '">';
    buffer += '����';
	buffer += '<input name="currentPage" type="text" style="height:16; font-size:10px" size="2" value="'+ this.options.current 
              + '" onChange="javascript:goPage(this.value)" />';
    buffer += 'ҳ';
    buffer += '</td>';
	
	//ÿҳ��������
	buffer += '<td style="' + this.styles.textStyle + '">';
    buffer += 'ÿҳ';
    buffer += '<input id="pageSize" name="pageSize" type="text" style="height:16; font-size:10px" size="2" value="'+ this.options.size + ' onChange="javascript:goPage($F(currentPage))" />';
    buffer += '��</td>';
    buffer += '<td>';
	
	//��β����
	buffer += '</tr></table>';
	
	document.write(buffer);
 }
 
}

/**
  *ҳ��ת�¼�
  *
  *currentPage ��ǰҳ��
  */
function goPage(currentPage){
	if($('loadingPrompt')){//��ҳ���ݲ�ѯ����ʾ�ȴ���Ϣ
		$('loadingPrompt').show();
	}
	
    $('current').value = currentPage;
	
	if(pageForm == null){
	  pageForm = document.forms[0];
	}
	
	$(pageForm).submit();
}	