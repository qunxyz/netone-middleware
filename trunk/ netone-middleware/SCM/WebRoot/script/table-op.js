
 /**
 * 表格操作脚本封装
 *<ul>
 * <li><li>
 *</ul>
 *
 *@author zhang.chao.yi
 *@author 
 *@version 1.1 Date 2007-5-23
 */

if(!window.Table) var Table = {};

Table.Methods ={
	/*-
	 *  设置表格操作的选者项
	 *
	 */
   setOptions : function(element,options){
	 element = $(element);
	
	 element.options = {
		  onDbColor :  '', //单击行、双击行颜色
		  onMouseOverColor : '#FF9900', //鼠标移到行的颜色
		  onMouseOutColor : '', //鼠标离开行的颜色
		  template : 'appoint',//添加一行的模板，参数可以是'frist':第一行, 'checked'：checkbox选中行, 'click'：单击行
		  templateIndex : 0
	 }
	 
	 Object.extend(element.options, options || {});

	 return element;
   },
   
   /*-
	 *  取表格第一行对象
	 *
	 */
   firstTr : function(element){
	   element = $(element);
	   
	   var trs = element.getElementsByTagName('tr');
	   trs = $A(trs);
	
	   return trs.first()?  Element.extend(trs.first()) : null;
   },
   
   /*-
	 *  取表格最后一行对象
	 *
	 */ 
   lastTr : function(element){
	   element = $(element);
	   
	   var trs = element.getElementsByTagName('tr');
	   trs = $A(trs);
	  
	   return trs.last()?  Element.extend(trs.last()) : null;
   },
   
   /*
    *获取处于激活行(该行前的checkbox选中，或者单击该行)的行号
	*/
   getActivityRowsIndexs : function(element){
	  var rowsIndexs = [];//返回行值
	  //alert(element.options.template);
      if(element.options.template&&element.options.template == 'checked'){//以checkbox 来判断要处理的行，"要处理行“
	                                                                   //例如：添加类似该行、删除该行等
		  var rows = $(element).rows;
		  $A(rows).each(function(row){
			 var cell = row.cells[0];
			 if($(cell).cleanWhitespace().firstDescendant() && $(cell).cleanWhitespace().firstDescendant().checked){
				rowsIndexs.push(row.rowIndex);
			 }
		  });
	  }else if(element.options.template&&element.options.template == 'appoint'){ //第一行 
		     rowsIndexs[0] = element.options.templateIndex;
	  }else if(element.options.template&&element.options.template == 'click'){//选中行
		    rowsIndexs[0] =  element.activateRowIndex;
	  }
	  
	  return rowsIndexs;
   },
   
   /*-
    * 通过行中放置对象的触发事件获取该行所在表格的索引位置(暂时没用到)
	*
	*/
   getRowIndexByElement : function(element,childElement){
	   element = $(element);
	   childElement = $(childElement);
	   
	   var i=0;
	   while(childElement.parentElement.parentElement.id !=  element.id && i++<10){
		   childElement=childElement.parentElement;
	   }
	 
	   if(i>9){
		   return -1;
	   }
	   
	   return childElement.rowIndex; 
   },
   
   /*-
    *
	*单击行
	*
	*
	*/
   trClick : function(element,trElement, options){
	 element = $(element);
	 
	 element.activateRowIndex = trElement.rowIndex;//记录当前聚焦行的行号
	 
	 /*-
	  *  判断单击行是否需要颜色区分
	  *     遍历取表格行原来颜色
	  *       使当前行背景颜色为指定颜色
	  */
	 if(element.options.onDbColor != ''){
		 var trs = element.getElementsByTagName('tr');
	     trs = $A(trs);

		 var oColor;
		 for(var i = 0; i < trs.size(); i++){
		   if(trs[i].style.backgroundColor.toLowerCase()!= element.options.onDbColor.toLowerCase()){
				oColor = trs[i].style.backgroundColor;
		   }
		 }
		 
		 trs.each(function(tr){
				           if(tr == rowElement){
					         tr.style.backgroundColor = element.options.onDbColor;
				           }else{
					          tr.style.backgroundColor = oColor;
				           }
		 });
	 }
	 
	 return element;
   },
   
   /*-
    * 增加行
	*
	*/
   appendRow : function(element){
	   element = $(element);
      
	   
	   /*-
	    *处理模板行
		*/
	   var indexs =  element.getActivityRowsIndexs();
	   if(indexs.size() == 0 || typeof indexs[0] == 'undefined'){
		  alert('请选择增加的模板行');
		  return ;
	   }
	   var templateRow =  element.rows[indexs[0]];
	  
	   /*-
	    *添加和模板一样的行
		*/
	   var tr = element.insertRow(-1);
	   var cells = templateRow.cells;
	   for(var i=0; i < cells.length; i++){
		 var  c = tr.insertCell(i);
		 c.innerHTML = cells[i].innerHTML ;  
	   }
	   
	   /*-
	    *为新添加行增加事件
		*/
	   tr.onmouseover = function(){
		   if(element.options.onMouseOverColor){
			   tr.style.backgroundColor = element.options.onMouseOverColor;
		   }
	   }
	   tr.onmouseout = function(){
		   if(element.options.onMouseOutColor){
			  tr.style.backgroundColor = element.options.onMouseOutColor; 
		   }else{
			  tr.style.backgroundColor = '';
		   }
	   }
	   tr.onclick = function(){
	      element.trClick(tr,{onDbColor : element.options.onDbColor ? element.options.onDbColor: ''});
	   };
	 
	   return Element.extend(tr);
   },
   
   /*-
    * 删除行 
	*
	*/
   removeRow : function(element){
	   element = $(element);
	 
	   var indexs =  element.getActivityRowsIndexs();
	   indexs.reverse();
       indexs.each(function(index){
			element.deleteRow(index);
	   });
	   
	   return element;
   },
   
   /*-
    *
	* 交换行
	*/
   swrapTr : function(element, tr1, tr2){
	   element = $(element);
	
	   var tr ;
       if(tr1.rowIndex > tr2.rowIndex){
	     tr = tr1;
		 tr1 = tr2;
		 tr2 =  tr;
	   }
	   
	   var temp = tr1.nextSibling == tr2 ? tr1 : tr1.nextSibling;
	   var tBody=tr2.parentNode;
	
       tBody.replaceChild(tr1,tr2);
       tBody.insertBefore(tr2,temp);
	   
	   return element;
   },
   
   /*-
    * 移动行 
	*
	*/
   moveRows : function(element, direct){
	  element = $(element);
	  var trs = $A(element.rows);//表格所有行
	  
	  var setTrChecked = function(tr){ //设置Trcheckbox 选中
           var inputs = $A(tr.getElementsByTagName('input'));
		   inputs.each(function(input){
						  if(input.type == 'checkbox'){
							  input.checked = true;
							  throw $break;
						  }
	       });
	  };
	  
	  var getCheckbox = function(tr){ //获取tr中checkbox对象
	     var checkbox;//返回对象
		 
	     var inputs = $A(tr.getElementsByTagName('input'));
		
		 inputs.each(function(input){
						  if(input.type == 'checkbox'){
							 checkbox = input;
							 throw $break;
						  }
		 });	
		 
		 return checkbox;
	  };
		  
	  
	 var moveOne =  function(tr){//移动该行，通过direct判断向那个方向移动，#1下移#-1上移
		if(direct < 0){
			if(tr.rowIndex > 1){
			    element.swrapTr(tr, $A(element.rows)[tr.rowIndex-1]);
		        setTrChecked(tr);
			}
		}else{
		   if(tr.rowIndex < $A(element.rows).length -1){
			   element.swrapTr(tr, $A(element.rows)[tr.rowIndex+1]);
		       setTrChecked(tr);
		   } 
		}
	  };
		
     
	  /*-
	   * 有两种情况不参与移动
	   *    已经移到第一行或者移到最后一行
	   *      紧接着第一行或最后一行的连续行
	   *
	   */
	  if(direct  < 0){
	    trs = trs.reverse();
		trs.pop();//移除第一行标题行
	  }else{
		  trs.shift();//移除第一行标题行
	  }
	
	  if( trs.length > 0 && getCheckbox(trs[trs.length-1]).checked){
		   for(var i = trs.length-1;  i >= 0; i--){
			    if(getCheckbox(trs[i]).checked){
					 trs.pop();
				}else{
				   break;
				}
		   }
	  }
	  
	  trs.reverse().each(function(tr){	
		 if(getCheckbox(tr).checked){
			 moveOne(tr);
		 }
	  });
	  
	  return element;   
   }
 
};

Element.addMethods('table',Table.Methods);

