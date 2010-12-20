
/* ------------------------------------------------------------------
 
    Cross-browser set innerHTML method.
    The HTML code to be inserted can contains script and style tag.
 
    Tested browsers:
        IE 5.5+
        Firefox 1.0+
        Opera 8.5+
 
    Author: kenxu <kenxu at ajaxwing dot com>
    Version: 0.1.5
    Date: 2006-08-04

    Usage:
        setInnerHTML(element, htmlCode);
 
    For more informations, visit:
        http://www.ajaxwing.com/index.php?id=3
 
   ------------------------------------------------------------------ */
var setInnerHTML = (function () {
	var element_stack = [];
	var input_stack = [];
	var html_stack = [];
	var timer = null;
	var ua = navigator.userAgent.toLowerCase();
	var isIE = (ua.indexOf("msie") >= 0 && ua.indexOf("opera") < 0);
	var old_document_write = document.write;
	var old_document_writeln = document.writeln;
	var loding_script = false;
	var callback = function () {
		if (loding_script) {
			return;
		}
		if (element_stack.length == 0) {
			clearInterval(timer);
			timer = null;
			document.write = old_document_write;
			document.writeln = old_document_writeln;
			return;
		}
		var index = element_stack.length - 1;
		var input = input_stack[index];
		if (input.length == 0) {
			input_stack.pop();
			var element = element_stack.pop();
			var html = html_stack.pop();
			element.innerHTML = "";
			if (typeof beforeInsert == "function") {
				html = beforeInsert(html);
			}
			if (html.match(/<script([^>]*>)((.|\r|\n)*?)<\/script>/i) != null) {
				setInnerHTML(element, html);
				return;
			}
			if (isIE) {
				html = "<div style=\"display:none\">for IE</div>" + html;
				element.innerHTML = html;
				element.removeChild(element.firstChild);
			} else {
				element.innerHTML = html;
			}
			return;
		}
		var item = input[input.length - 1];
		if (typeof item == "string") {
			html_stack[index] += item;
			input.pop();
		} else {
			if (typeof item == "object") {
				if (item.src) {
					loding_script = true;
					var script = document.createElement("script");
					script.src = item.src;
					script.__index = index;
					if (isIE) {
						script.onreadystatechange = script_loaded;
					} else {
						script.onload = script_loaded;
					}
					var head = document.getElementsByTagName("head")[0];
					head.appendChild(script);
				}
				if (item.text) {
					var script = document.createElement("script");
					script.text = item.text;
					var head = document.getElementsByTagName("head")[0];
					head.appendChild(script);
					input.pop();
				}
			} else {
				input.pop();
			}
		}
	};
	var script_loaded = function () {
		if (isIE && this.readyState.toLowerCase() != "loaded" && this.readyState.toLowerCase() != "complete") {
			return;
		}
		var index = this.__index;
		input_stack[index].pop();
		loding_script = false;
	};
	var new_document_write = function () {
		for (var i = 0; i < arguments.length; i++) {
			html_stack[element_stack.length - 1] += arguments[i];
		}
	};
	var new_document_writeln = function () {
		for (var i = 0; i < arguments.length; i++) {
			new_document_write(arguments[i] + "\n");
		}
	};
	return function (element, htmlCode) {
		element_stack.push(element);
		html_stack.push("");
		var input = [];
		while (true) {
			if ((m = htmlCode.match(/<script([^>]*>)((.|\r|\n)*?)<\/script>/i)) == null) {
				break;
			}
			input.unshift(htmlCode.substr(0, m.index));
			htmlCode = htmlCode.substr(m.index + m[0].length);
			if ((m2 = m[1].match(/src\s*=\s*(['"]?)([^'">\s]*)\1/i)) != null) {
				input.unshift({src:m2[2]});
			} else {
				input.unshift({text:m[2]});
			}
		}
		input.unshift(htmlCode);
		input_stack.push(input);
		if (timer == null) {
			document.write = new_document_write;
			document.writeln = new_document_writeln;
			timer = setInterval(callback, 10);
		}
	};
})();

