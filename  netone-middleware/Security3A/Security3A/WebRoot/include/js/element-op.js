/**
 *          element-op.js
 *<p>常用JS脚本封装</p>
 * <description>
 *   脚本封装是基于prototype.js开发，prototype语义请参考网络相关文档
 *</description>
 *
 *@author zhang.chao.yi (Jim)
 *        mail: eduzcy@126.com qq: 44034945
 *
 *@histroy 1.0   date 2008-6-3   created by zhang.chao.yi
 *@histroy 1.1.0 date 2008-7-20  modified by zhang.chao.yi
 *@histroy 1.1.1 data 2008-11-17 modified by zhang.chao.yi
 */

  
/**
 *添加Select options
 *
 *@param element select元素
 *@param hash options集合
 *   eg:
 *          var hash = new Hash();
 *			hash.set('01','女');
 *		    $('rSelect').append(hash); 
 *   说明： rSelect就是多选select表单元素
 *		   
 */
Form.Element.Methods.append = function(element,hash){
	   element = $(element);
   
	   if(!(element.tagName.toLowerCase() == 'select')){
	     return ;
	   }
     
	   hash = $H(hash);
       hash.each(function(pair){
		  var _options = element.cleanWhitespace().immediateDescendants(); 
		  var flag = true;
		  _options.each(function(option){
			 if(option.value == pair.key){
                flag = false;
                throw $break;
			 }
			 
		  });
		  
		  if(flag){
			   var option = document.createElement("OPTION")
			   option.value = pair.key;
			   option.innerText = pair.value;
			   element.appendChild(option);
		  }
	  });

	  return element;
}

/**
 *移除Select选中的options
 *
 *@parm element select元素
 */
Form.Element.Methods.removeSelected = function(element){
	   element = $(element);
	   
	   if(!(element.tagName.toLowerCase() == 'select' && element.hasAttribute('multiple')) ){
	      return element;
	   }

	   var _options = element.cleanWhitespace().immediateDescendants(); 
	   _options.reverse();
	   var i = _options.size() - 1;
	   _options.each(function(option){
			if(option.selected){
			  element.remove(i); 
			}
			i--;
		});
	   
	   return element;
}

/**
 *移除Select所有的options(多选情况)
 *
 *@parm element select元素
 */
Form.Element.Methods.removeAll = function(element){
     element = $(element);
 
	 if(!(element.tagName.toLowerCase() == 'select' && element.hasAttribute('multiple')) ){
	     return element;
	 }

	 var _options = element.cleanWhitespace().immediateDescendants();
     _options.each(function(option){
	    option.remove();
	 });

	 return element;
}

/**
 *移除select所有的options（单选情况）
 *
 *@param element select元素
 */
Form.Element.Methods.clearAll = function(element){
	 element = $(element);
	 if(element.tagName.toLowerCase() !=  'select'){
		  return element;
	 }
	 
	 var _options = element.cleanWhitespace().immediateDescendants();
	 _options.each(function(option){
		 option.remove();
	 });
	 
	 return element;
}

/**
 *使Select所有的options都选中
 *
 *@param element select元素
 */
Form.Element.Methods.selectAll = function(element){
	var _name = element;
    element = $(element);

    if(!(element.tagName.toLowerCase() == 'select' && element.hasAttribute('multiple')) ){
	     return element;
    }

    var _options = element.cleanWhitespace().immediateDescendants();
     _options.each(function(option){
	     if(!option.selected){
	        option.selected = 'selected';
		 }
	 });

	return element;
}

/**
 *使Select所有的options都不选中
 *
 * @param element select元素
 */
Form.Element.Methods.clearSelected = function(element){
	element = $(element);

    if(!(element.tagName.toLowerCase() == 'select' && element.hasAttribute('multiple')) ){
	     return element;
    }

    var _options = element.cleanWhitespace().immediateDescendants();
     _options.each(function(option){
	     if(option.selected){
	        option.selected = '';
		 }
	 });

	return element;
}

/**
 *向上移动Select中选中的Options
 *
 *@param element select元素
 *@param step 移动的步长
 */
Form.Element.Methods.moveUp = function(element,step){
	element = $(element);
	
    if(!(element.tagName.toLowerCase() == 'select' && element.hasAttribute('multiple')) ){
	     return element;
	}

	if(arguments.length == 1){
	  step = -1;
	}else if(arguments.length ==2){
	     step = -step;
	}

	var _options = element.cleanWhitespace().immediateDescendants();
	var i = 0;
	_options.each(function(option){
		if(option.selected){
		   var  selectedIndex = i;
		   
           if(selectedIndex == 0 ){
		       throw $break;
		   }
		   var  afterIndex = selectedIndex + step;
           var  value = $(_options[afterIndex]).value;
	       var  text = $(_options[afterIndex]).text;

           _options[afterIndex].value = _options[selectedIndex].value;
	       _options[afterIndex].text = _options[selectedIndex].text;
           _options[afterIndex].selected = 'selected';

	       _options[selectedIndex].value = value;
	       _options[selectedIndex].text = text;
           _options[selectedIndex].selected = '';
		}
		i++;
	});

	return element;
}

/**
 *向下移移动Select中选中的Options
 *
 *@param element select元素
 *@param step 移动的步长
 */
Form.Element.Methods.moveDown = function(element,step){
    element = $(element);
	
	if(!(element.tagName.toLowerCase() == 'select' && element.hasAttribute('multiple')) ){
	     return element;
	}

	if(arguments.length == 1){
	    step = -1;
	}else if(arguments.length ==2){
	    step = -step;
	}

	var _options = element.cleanWhitespace().immediateDescendants();
	_options.reverse();
	var i = 0;
	_options.each(function(option){
		if(option.selected){
		   var  selectedIndex = i;
           if(selectedIndex == 0 ){
		       throw $break;
		   }

		   var  afterIndex = selectedIndex + step;
           var  value = $(_options[afterIndex]).value;
	       var  text = $(_options[afterIndex]).text;

           _options[afterIndex].value = _options[selectedIndex].value;
	       _options[afterIndex].text = _options[selectedIndex].text;
           _options[afterIndex].selected = 'selected';

	       _options[selectedIndex].value = value;
	       _options[selectedIndex].text = text;
           _options[selectedIndex].selected = '';
		}
		i++;
	});

    return element;
}

/*
 *把Select1 中的选中Options移到Select2去
 *
 *@param element 原Select
 *@param toElement 目标Select
 */
Form.Element.Methods.moveSelected = function(element,toElement){
    element = $(element);

    if(!(element.tagName.toLowerCase() == 'select' && element.hasAttribute('multiple')) ){
	     return element;
	}

    toElement = $(toElement);

    var fromOptions = element.cleanWhitespace().immediateDescendants();
	var toOptions = toElement.cleanWhitespace().immediateDescendants();

	fromOptions.each(function(option){
	   if(option.selected){
	       var flag = false;//表示该选择项不存在目标select中

		   toOptions.each(function(toOption){
		      if(option.value == toOption.value){
				   flag = true;
			  }
		   });
		  	
		   if(!flag){
		       var obj = document.createElement("OPTION")
			   obj.value = option.value;
			   obj.innerText = option.innerText;
			   toElement.appendChild(obj); 
		   }
	   }
	});
	
    
	
	return element;
}

/*
 *把Select1 中的所有Options移到Select2去
 *
 *@param element 原Select
 *@param toElement 目标Select
 */
Form.Element.Methods.moveAll = function(element,toElement){
    element = $(element);

    if(!(element.tagName.toLowerCase() == 'select' && element.hasAttribute('multiple')) ){
	     return element;
	}

    toElement = $(toElement);

    var fromOptions = element.cleanWhitespace().immediateDescendants();
	var toOptions = toElement.cleanWhitespace().immediateDescendants();

	fromOptions.each(function(option){
	       var flag = false;//表示该选择项不存在目标select中

		   toOptions.each(function(toOption){
		      if(option.value == toOption.value){
				   flag = true;
			  }
		   });
		  	
		   if(!flag){
		       var obj = document.createElement("OPTION")
			   obj.value = option.value;
			   obj.innerText = option.innerText;
			   toElement.appendChild(obj); 
		   }
	});

	return element;
}

/**
 *设置checkbox或radio对应值为选中
 *@param element  
 *       checkbox/radio 
 *@para  values
 ×       选中的值
 *
 */
Form.Element.Methods.setCheckedValue =  function(element, values){
	var type = element.type.toLowerCase();
	
    if('checkbox' == type){
		var checkBoxes = document.getElementsByName(element.name);
		$A(checkBoxes).each(function(checkBox){
		     if($A(values).include(checkBox.value)){
				  checkBox.checked = 'checked';
			 }
		});
	}

	if('radio' == type){
	   var radioes = document.getElementsByName(element.name);
	   $A(radioes).each(function(radio){
		   if(radio.value == values){
              radio.checked = 'radio';
		   }
	   });
	}
}

/**
 *获取checkbox或radio选中的序列化值
 *@param element
 *      checkbox或radio元素
 */
Form.Element.Methods.getCheckedValue =  function(element){
	var serResultArray = [];

	var type = element.type.toLowerCase();
    if('checkbox' == type){
       var checkBoxes = document.getElementsByName(element.name);

	   serResultArray =  $A(checkBoxes).inject([], function(result,checkbox, index){
			 if($(checkbox).checked){
				result.push($(checkbox).serialize());
			 }
							   
			 return result;
	   });
	}

	if('radio' == type){
	   var radioes = document.getElementsByName(element.name);

       serResultArray = $A(radioes).inject([], function(result, radio,index){
		   if($(radio.checked)){
			   result.push($(radio).serialize());
		   }

		   return result;
	   })
	}

   return serResultArray.join('&');
}

//--------------------------------------------------------------------------------------------------------
 /* @update by zhang.chao.yi  date  2008-6-4
         1 全选、反选某个表单的一组checkbox
		 2 Enter键循环聚焦表单元素
 
//--------------------------------------------------------------------------------------------------------*/



var tabIndex = -1;

Object.extend(Form.Methods, {
  toggleCheckBox: function(formName, checkboxName){
	  form = $(formName);
	  
	  var inputs = form.getInputs('checkbox', checkboxName);
	  var flag = 'unSelected';
	  if(inputs && inputs[0].checked){
		  flag = 'selected';
	  }
	  
	  if('unSelected' == flag){
		    $A(inputs).each(function(element){
				 element.checked	=true;				 
			});
	  }else{
		   $A(inputs).each(function(element){
				 element.checked	=false;				 
		   });   
	  } 
	  
	 return form;
  },
  
  loopFocus : function(form){
	  form.focusFirstElement();
	  
	  var elements = $(form).getElements().findAll(function(element) {
       return 'hidden' != element.type && !element.disabled;
      });
		
	  $A(elements).each(function(element){
			Event.observe(element,'keypress', function(){
			   var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
			   if (keyCode == 13) {
				   
				   if(tabIndex >= 0){
					     $($A(elements)[tabIndex]).style.borderColor = '#97C8F4';
				   }
				   
				   tabIndex = (tabIndex + 1) % $A(elements).size();
				   $($A(elements)[tabIndex]).style.borderColor ='#FF0000';
				   $($A(elements)[tabIndex]).activate();
			   }
			});		     
	   });

	  return form;
  }
});

/**
 *判断form中checkbox是否按要求选择
 *
 *@param form 表单名称
 *@param　choiceOne　是否只选择一个标志
 */
Form.Methods.checkSelected = function(form, choiceOne){
	var flag = false;
	
	var cks = form.getInputs('checkbox');
	cks = cks.findAll(function(ck){
			return ck.checked;
		  });
	
   if(choiceOne){
	    if(cks.length == 0 || cks.length > 1){
		    alert('对不起，请你选择一条记录操作！');
		}else{
			 flag = true;
		}
	}else{
		if(cks.length == 0){
		   alert('对不起，请你选择记录操作！');
		}else{
		   flag = true;
		}
	}

	return flag;
}



Element.addMethods();

//--------------------------------------------------------------------------------------------------------
 /* @update by zhang.chao.yi  date  2008-6-4
                                   粘附层脚本，该层将会粘赋与某个元素下边框
 
//--------------------------------------------------------------------------------------------------------*/
var PopDiv = Class.create();

Object.extend(PopDiv.prototype,{
   /*
    *初始化
    */
   initialize: function(){
	 if (arguments.length < 2) {
		  alert('对不起，你设置的参数错误！');
		  return;
	 }

	 this.id = arguments[0] || 'popDiv1';
	 this.options = {
		element: 'undefined',
        containerE: document.body,
	    url: null,
		offsetT: 16,
		offsetL: 12,
	    width: 260,
        height: 230		
     };

     Object.extend(this.options, arguments[1] || {});
	 this.element = $(this.options.element);
	 this.containerE = $(this.options.containerE);

	 if(!$(this.id)){
		 this._createDiv(this.id);
     }
	
	 this.content = $(this.id + "_content");
	
   },

   /*
    * 计算层位置
	*/
   _getPoint: function(){
	   var top = 0, left = 0;
	   var point = [top, left];

	   if(this.containerE.tagName.toLowerCase() == 'body'){
		  var vPoint =Position.positionedOffset(this.element);
          top = vPoint[1] +  this.options.offsetT;
          left= vPoint[0] +  this.options.offsetL;
		 
	   }else{
		  var temp = this.element;
	      while(temp != this.containerE){
		    left += temp.offsetLeft;
		    top += temp.offsetTop;
		    temp = temp.offsetParent;
	      }
	    }
     
	  
	   if(top + this.element.offsetHeight + this.options.height > 
		 this.containerE.scrollTop + this.containerE.offsetHeight){
		 top = top - this.options.height - 16;
	   }else{
		 top = top + this.element.offsetHeight + 2;
	   }

	   point.top = top;
	   point.left = left;

     return point;
   },

   /*
    *重新设置层位置
	*/
   _reset: function(){
     var point = this._getPoint();
   
	 $(this.id).setStyle({
		 width: this.options.width,
		 height: this.options.height,
         position: 'absolute',
		 top: point.top,
		 left: point.left,
		 zIndex: '9999'
	 });

     PopDiv.id = this.id;
	 PopDiv.top = point.top;
	 PopDiv.left = point.left;
	 PopDiv.width= this.options.width;
	 PopDiv.height = this.options.height;
   },
   /*
    *创建层
	*
	*id 下拉层ID
	*/
   _createDiv: function(id){
     var win = document.createElement("div");
     win.setAttribute('id', id);

     var content = '';
	 if (this.options.url){
		 content= '<iframe  id="' + id + '_content" src="' + this.options.url + '" frameborder="0" scrolling="auto"  height="100%" > </iframe>';
	 }else{
		 content = '<div id="' + id + '_content" style="width:100%;height:100%;margin:0px;padding:0px;overflow:auto"></div>';
     }
 
	 var closeLabel = '<label onclick="Element.hide($(' + this.id + '))" style="cursor:hand;color:#00CCFF; font-size:12px;" >关闭</label>';
	 var htmlStr =  '<table  cellSpacing="0" cellPadding="3"  border="1"  borderColor="#9fd6ff"  style="border-collapse: collapse;border: #9fd6ff 1px solid;">\n';
	 htmlStr += '       <tr>';
	 htmlStr += '           <td id="' + id + '_content" height="*" valign="top" align="left">' + content + '</td>';
     htmlStr += '      </tr>';
	 htmlStr += '       <tr>';
     htmlStr += '           <td id="' + id + '_close" height="16px" align="right">' + closeLabel + '</td>';
	 htmlStr += '       </tr>';
	 htmlStr += '    </table>';
	 win.innerHTML = htmlStr;
	 
     this.containerE.appendChild(win);
	 Element.hide(win);
	 
	 return win;
   },
   getContent: function(){
	  return this.content;
   },
   getId: function(){
	  return  this.id;
   },
   show: function(){
	   Element.show($(this.id));  
	   this._reset();
   }
});

Event.observe(document, 'click', function(){

   if(!Object.isUndefined(PopDiv.top)){
		var divTop =  PopDiv.top;
	    var divLeft = PopDiv.left;
	    var divWidth =  PopDiv.width;
		var divHeight = PopDiv.height;
        var CONST = 60;

	    var y1 = divTop - CONST;
		var y2 = divTop + divHeight +CONST;
		var x1 = divLeft - CONST;
	    var x2 = divLeft + divWidth + CONST;
	    
		var clientX =  window.event.clientX;
		var clientY = window.event.clientY;

       // alert('ciientX:' + clientX + ', cilentY: ' +  clientY);
		//alert('top: ' +  divTop + ', left: ' + divLeft + ', width: ' + divWidth + ', height: ' + divHeight);
		//alert('x1: ' + x1 + ',  x2:' + x2 + ', y1: ' + y1  + ', y2: ' + y2);
		if( !((x1 < clientX) &&(clientX < x2)&&(clientY > y1) && (clientY < y2))){
		   Element.hide($(PopDiv.id));
		}
	}
}); 


   //格式化数字 
        function format(v, formatString){ 
            var parterns = formatString.split("."); 
            var numbers = v.split("."); 
            
            var lparterns = parterns[0].split(""); 
            var lparternsbak = parterns[0].split(""); 
            var lnumbers = numbers[0].split(""); 
            
            var lkeep = ""; 
            var rkeep = ""; 
            
            //得到左侧要替换的部分 
            var lplaces = []; 
            for(var i=0;i <lparterns.length;i++){ 
                var parternchar = lparterns[i]; 
                if (parternchar == "#" || parternchar == "0"){ 
                    lplaces.push(i); 
                } 
            } 

            //替换左侧，左侧有数字才要替换，以避免v = .99型的数字而产生错误 
            if (lnumbers[0] && lnumbers[0].length>0){ 
                var numberIndex = lnumbers.length - 1; 
                var replaced = 0; 
                for(var i=lplaces.length - 1;i>=0;i--){ 
                    replaced ++;    //被替换的字符数量 
                    var place = lplaces[i]; 
                    lparterns[place] = lnumbers[numberIndex]; 
                    
                    if (numberIndex == 0) { 
                        break; 
                    } 
                    numberIndex--; 
                } 
                
                //处理以#为第一个格式（#前可能有非0的其他串也在此范围）的格式串，对于以#开头的格式串，将不会截取数字串，要补齐 
                var lstartIdx = lplaces[0]; 
                
                if (lparternsbak[lstartIdx]=="#"){ 
                    if (lnumbers.length > replaced){ 
                        var idx = lnumbers.length - replaced; 
                        for(var i=0;i <idx;i++){ 
                            lkeep += lnumbers[i]; 
                        } 
                        
                        lparterns[lstartIdx] = lkeep + lparterns[lstartIdx]; 
                    } 
                } 
            } 
            
            //替换右侧 
            if (parterns[1] && parterns[1].length > 0){ 
                var rparterns = parterns[1].split(""); 
                var rparternsbak = parterns[1].split(""); 
                
                if (numbers[1] && numbers[1].length>0){ 
                    var rnumbers = numbers[1].split(""); 

                    //得到右侧将要替换的部分 
                    var rplaces = []; 
                    for(var i=0;i <rparterns.length;i++){ 
                        var parternchar = rparterns[i]; 
                        if (parternchar == "#" || parternchar == "0"){ 
                            rplaces.push(i); 
                        } 
                    } 
                    
                    var replaced = 0; 
                    for(var i=0;i <rplaces.length;i++){ 
                        replaced ++;    //被替换的字符数量 
                        var place = rplaces[i]; 
                        rparterns[place] = rnumbers[i]; 
                        
                        if (i==rnumbers.length - 1){ 
                            break; 
                        } 
                    } 
                    
                    //处理以#结束的（#后有非0的串也在此范围） 
                    var rlastIdx = rplaces[rplaces.length-1]; 
                    if (rparternsbak[rlastIdx]=="#"){ 
                        for(var i=replaced-1;i <rnumbers.length;i++){ 
                            rkeep += rnumbers[i]; 
                        } 
                        
                        rparterns[rlastIdx] += rkeep; 
                    } 
                } 
            } 
            
            for(var i=0;i <lparterns.length;i++){ 
                if (lparterns[i]=="#"){ 
                    lparterns[i] = ""; 
                } 
            } 
            
            var result = lparterns.join(""); 
            if (parterns[1]){ 
                for(var i=0;i <rparterns.length;i++){ 
                    if (rparterns[i] == "#"){ 
                        rparterns[i] = ""; 
                    } 
                } 
                result += "." + rparterns.join(""); 
            } 
            
            //第一位不能为,号 
            if (result.substring(0,1)==","){ 
                result = result.substring(1); 
            } 
            
            //最后一位也不能为,号 
            if (result.substring(result.length-1)==","){ 
                result = result.substring(0,result.length); 
            } 
            return result; 
        } 
