var openModalDialogWindow = function (title, url,dialogWidth, dialogHeight,scrolling,resizable,modal) {
	if (resizable==null){ resizable=true};
	if (modal==null){modal=true};
	$('#_openDiv').dialog({
		title : title,
		'bgiframe': false,
		autoOpen: false, 
		/*'closeOnEscape': true,
		'closeText': 'hide',
		'stack': true,
		*/
		'modal': modal,
		resizable: resizable,
		draggable: true,
		width: dialogWidth+30,
		height: dialogHeight+50,
		position: 'center',
		/*dialogClass: 'alert'
		buttons: {"稍后提交": function(){
				 },"提交审核": function(){
				 }
		},*/
		open: function(event, ui) {
			if (scrolling==null){ scrolling='no'};
			var html = "<div class=\"loading-indicator\" id=\"_loading\">loading...</div><iframe id=\"mdFrame\" name=\"mdFrame\" width=\"" + dialogWidth + "\" height=\"" + dialogHeight + "\" frameborder=\"0\" scrolling=\""+scrolling+"\" src=\"" + url + "\"></iframe>";
			$('#_openDiv #_innerHtml').html(html);
			
			$("#mdFrame").load(function(){
				_hideLoading();
    		});
		},
		close: function(event, ui) {
			$('#_openDiv').dialog("destroy");
		}
		
	});
	$("#_openDiv").dialog('open').show(); 
}

function _jwinClose(){//关闭窗口
	var o = $('#_openDiv');
	if (o) o.dialog("destroy");
}

function _hideLoading() {
 	var loader = document.getElementById("_loading");
	loader.innerHTML = "";
	loader.style.display = "none";
	var iframe = document.getElementById("mdFrame");
	iframe.style.visibility = "visible";
}