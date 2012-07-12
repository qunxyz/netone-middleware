var AppStyleUtils = window.AppStyleUtils || {};
AppStyleUtils = {
   removeStyleSheet : function(id){
       var existing = document.getElementById(id);
       if(existing){
           existing.parentNode.removeChild(existing);
       }
   },

   swapStyleSheet : function(id, url){
       this.removeStyleSheet(id);
       var ss = document.createElement("link");
       ss.setAttribute("rel", "stylesheet");
       ss.setAttribute("type", "text/css");
       ss.setAttribute("id", id);
       ss.setAttribute("href", url);
       document.getElementsByTagName("head")[0].appendChild(ss);
   }
}

function setCookie(b, d, a, f, c, e) {
	document.cookie = b + "=" + escape(d) + ((a) ? "; expires=" + a.toGMTString() : "") + ((f) ? "; path=" + f : "") + ((c) ? "; domain=" + c : "") + ((e) ? "; secure" : "");
}
function getCookie(b) {
	var d = b + "=";
	var e = document.cookie.indexOf(d);
	if (e == -1) {
		return null;
	}
	var a = document.cookie.indexOf(";", e + d.length);
	if (a == -1) {
		a = document.cookie.length;
	}
	var c = document.cookie.substring(e + d.length, a);
	return unescape(c);
}
function deleteCookie(a, c, b) {
	if (getCookie(a)) {
		document.cookie = a + "=" + ((c) ? "; path=" + c : "") + ((b) ? "; domain=" + b : "") + "; expires=Thu, 01-Jan-70 00:00:01 GMT";
	}
}
String.prototype.trim = function () {
	return (this.replace(/^[\s\xA0]+/, "").replace(/[\s\xA0]+$/, ""));
};

/*  This function is to select all options in a multi-valued <select> */
function selectAll(elementId) {
    var element = document.getElementById(elementId);
	len = element.length;
	if (len != 0) {
		for (i = 0; i < len; i++) {
			element.options[i].selected = true;
		}
	}
}

/* This function is used to select a checkbox by passing
 * in the checkbox id
 */
function toggleChoice(elementId) {
    var element = document.getElementById(elementId);
    if (element.checked) {
        element.checked = false;
    } else {
        element.checked = true;
    }
}

/* This function is used to select a radio button by passing
 * in the radio button id and index you want to select
 */
function toggleRadio(elementId, index) {
    var element = document.getElementsByName(elementId)[index];
    element.checked = true;
}

/* Function to hide form elements that show through
   the search form when it is visible */
function toggleForm(frmObj, iState) // 1 visible, 0 hidden 
{
	for(var i = 0; i < frmObj.length; i++) {
		if (frmObj.elements[i].type.indexOf("select") == 0 || frmObj.elements[i].type.indexOf("checkbox") == 0) {
            frmObj.elements[i].style.visibility = iState ? "visible" : "hidden";
		}
	} 
}

/* This function is used to change the style class of an element */
function swapClass(obj, newStyle) {
    obj.className = newStyle;
}

function isUndefined(value) {   
    var undef;   
    return value == undef; 
}

function checkAll(theForm) { // check all the checkboxes in the list
  for (var i=0;i<theForm.elements.length;i++) {
    var e = theForm.elements[i];
		var eName = e.name;
    	if (eName != 'allbox' && 
            (e.type.indexOf("checkbox") == 0)) {
        	e.checked = theForm.allbox.checked;		
		}
	} 
}

/* Function to clear a form of all it's values */
function clearForm(frmObj) {
	for (var i = 0; i < frmObj.length; i++) {
        var element = frmObj.elements[i];
		if(element.type.indexOf("text") == 0 || 
				element.type.indexOf("password") == 0) {
					element.value="";
		} else if (element.type.indexOf("radio") == 0) {
			element.checked=false;
		} else if (element.type.indexOf("checkbox") == 0) {
			element.checked = false;
		} else if (element.type.indexOf("select") == 0) {
			for(var j = 0; j < element.length ; j++) {
				element.options[j].selected=false;
			}
            element.options[0].selected=true;
		}
	} 
}
/****JS 验证类****/		
function validator(obj){
  var str = obj.value;
  var result=str.match(/^[+-]?[0-9]{1,12}\.{0,1}[0-9]{0,2}$/);
          //if(result==null) return false;
          //return true;
  if (result==null){
	alert('输入数值金额非法!');
	obj.value = (Math.round(str*100)/100);
  }
}

function isDigit(){//只能输入数字
  return ((event.keyCode >= 48) && (event.keyCode <= 57));
}

function isDigitEvent(){//只能输入"数字" "."
  return (((event.keyCode >= 48) && (event.keyCode <= 57)) || (event.keyCode == 46) );
}

