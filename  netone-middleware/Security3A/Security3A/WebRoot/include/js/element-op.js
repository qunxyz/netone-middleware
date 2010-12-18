/**
 *          element-op.js
 *<p>����JS�ű���װ</p>
 * <description>
 *   �ű���װ�ǻ���prototype.js������prototype������ο���������ĵ�
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
 *���Select options
 *
 *@param element selectԪ��
 *@param hash options����
 *   eg:
 *          var hash = new Hash();
 *			hash.set('01','Ů');
 *		    $('rSelect').append(hash); 
 *   ˵���� rSelect���Ƕ�ѡselect��Ԫ��
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
 *�Ƴ�Selectѡ�е�options
 *
 *@parm element selectԪ��
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
 *�Ƴ�Select���е�options(��ѡ���)
 *
 *@parm element selectԪ��
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
 *�Ƴ�select���е�options����ѡ�����
 *
 *@param element selectԪ��
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
 *ʹSelect���е�options��ѡ��
 *
 *@param element selectԪ��
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
 *ʹSelect���е�options����ѡ��
 *
 * @param element selectԪ��
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
 *�����ƶ�Select��ѡ�е�Options
 *
 *@param element selectԪ��
 *@param step �ƶ��Ĳ���
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
 *�������ƶ�Select��ѡ�е�Options
 *
 *@param element selectԪ��
 *@param step �ƶ��Ĳ���
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
 *��Select1 �е�ѡ��Options�Ƶ�Select2ȥ
 *
 *@param element ԭSelect
 *@param toElement Ŀ��Select
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
	       var flag = false;//��ʾ��ѡ�������Ŀ��select��

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
 *��Select1 �е�����Options�Ƶ�Select2ȥ
 *
 *@param element ԭSelect
 *@param toElement Ŀ��Select
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
	       var flag = false;//��ʾ��ѡ�������Ŀ��select��

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
 *����checkbox��radio��ӦֵΪѡ��
 *@param element  
 *       checkbox/radio 
 *@para  values
 ��       ѡ�е�ֵ
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
 *��ȡcheckbox��radioѡ�е����л�ֵ
 *@param element
 *      checkbox��radioԪ��
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
         1 ȫѡ����ѡĳ������һ��checkbox
		 2 Enter��ѭ���۽���Ԫ��
 
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
 *�ж�form��checkbox�Ƿ�Ҫ��ѡ��
 *
 *@param form ������
 *@param��choiceOne���Ƿ�ֻѡ��һ����־
 */
Form.Methods.checkSelected = function(form, choiceOne){
	var flag = false;
	
	var cks = form.getInputs('checkbox');
	cks = cks.findAll(function(ck){
			return ck.checked;
		  });
	
   if(choiceOne){
	    if(cks.length == 0 || cks.length > 1){
		    alert('�Բ�������ѡ��һ����¼������');
		}else{
			 flag = true;
		}
	}else{
		if(cks.length == 0){
		   alert('�Բ�������ѡ���¼������');
		}else{
		   flag = true;
		}
	}

	return flag;
}



Element.addMethods();

//--------------------------------------------------------------------------------------------------------
 /* @update by zhang.chao.yi  date  2008-6-4
                                   ճ����ű����ò㽫��ճ����ĳ��Ԫ���±߿�
 
//--------------------------------------------------------------------------------------------------------*/
var PopDiv = Class.create();

Object.extend(PopDiv.prototype,{
   /*
    *��ʼ��
    */
   initialize: function(){
	 if (arguments.length < 2) {
		  alert('�Բ��������õĲ�������');
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
    * �����λ��
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
    *�������ò�λ��
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
    *������
	*
	*id ������ID
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
 
	 var closeLabel = '<label onclick="Element.hide($(' + this.id + '))" style="cursor:hand;color:#00CCFF; font-size:12px;" >�ر�</label>';
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


   //��ʽ������ 
        function format(v, formatString){ 
            var parterns = formatString.split("."); 
            var numbers = v.split("."); 
            
            var lparterns = parterns[0].split(""); 
            var lparternsbak = parterns[0].split(""); 
            var lnumbers = numbers[0].split(""); 
            
            var lkeep = ""; 
            var rkeep = ""; 
            
            //�õ����Ҫ�滻�Ĳ��� 
            var lplaces = []; 
            for(var i=0;i <lparterns.length;i++){ 
                var parternchar = lparterns[i]; 
                if (parternchar == "#" || parternchar == "0"){ 
                    lplaces.push(i); 
                } 
            } 

            //�滻��࣬��������ֲ�Ҫ�滻���Ա���v = .99�͵����ֶ��������� 
            if (lnumbers[0] && lnumbers[0].length>0){ 
                var numberIndex = lnumbers.length - 1; 
                var replaced = 0; 
                for(var i=lplaces.length - 1;i>=0;i--){ 
                    replaced ++;    //���滻���ַ����� 
                    var place = lplaces[i]; 
                    lparterns[place] = lnumbers[numberIndex]; 
                    
                    if (numberIndex == 0) { 
                        break; 
                    } 
                    numberIndex--; 
                } 
                
                //������#Ϊ��һ����ʽ��#ǰ�����з�0��������Ҳ�ڴ˷�Χ���ĸ�ʽ����������#��ͷ�ĸ�ʽ�����������ȡ���ִ���Ҫ���� 
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
            
            //�滻�Ҳ� 
            if (parterns[1] && parterns[1].length > 0){ 
                var rparterns = parterns[1].split(""); 
                var rparternsbak = parterns[1].split(""); 
                
                if (numbers[1] && numbers[1].length>0){ 
                    var rnumbers = numbers[1].split(""); 

                    //�õ��ҲཫҪ�滻�Ĳ��� 
                    var rplaces = []; 
                    for(var i=0;i <rparterns.length;i++){ 
                        var parternchar = rparterns[i]; 
                        if (parternchar == "#" || parternchar == "0"){ 
                            rplaces.push(i); 
                        } 
                    } 
                    
                    var replaced = 0; 
                    for(var i=0;i <rplaces.length;i++){ 
                        replaced ++;    //���滻���ַ����� 
                        var place = rplaces[i]; 
                        rparterns[place] = rnumbers[i]; 
                        
                        if (i==rnumbers.length - 1){ 
                            break; 
                        } 
                    } 
                    
                    //������#�����ģ�#���з�0�Ĵ�Ҳ�ڴ˷�Χ�� 
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
            
            //��һλ����Ϊ,�� 
            if (result.substring(0,1)==","){ 
                result = result.substring(1); 
            } 
            
            //���һλҲ����Ϊ,�� 
            if (result.substring(result.length-1)==","){ 
                result = result.substring(0,result.length); 
            } 
            return result; 
        } 
