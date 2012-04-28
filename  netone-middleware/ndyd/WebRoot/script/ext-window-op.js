
var openModalDialogWindow = function (title, url, dialogLeft,dialogTop,dialogWidth, dialogHeight,resizable,_maximize) {
	var _win = new ModalDialogWindow();
	var arr = new Array();
	arr[0] = title;
	arr[1] = dialogLeft;
	arr[2] = dialogTop;
	arr[3] = dialogWidth;
	arr[4] = dialogHeight;
	if (resizable==null) resizable=false;
	arr[5] = resizable;
	if (_maximize==null) _maximize=false;
	arr[6] = _maximize;	
	_win.showModalDialog(url,arr);
};
var ModalDialogWindow = function () {
	this.constructor();
	this.window = Ext.extend(Ext.Window, {stateful :false,/** 状态位*/closeable:true, closeAction:"close", draggable:true, maximizable :true,plain:true, modal:true});
};
ModalDialogWindow.prototype.initialize = function (arr) {
	this.title= arr[0]==null?'':arr[0];
	this.dialogLeft = arr[1]==null?'0':arr[1];
	this.dialogTop = arr[2]==null?'0':arr[2];	
	this.dialogWidth = arr[3]==null?'600':arr[3];
	this.dialogHeight = arr[4]==null?'600':arr[4];
	this.resizable = arr[5]==null?false:arr[5];
	this.scroll= true;
	this._maximize = arr[6]==null?false:arr[6];
};
ModalDialogWindow.prototype.constructor = function () {
	this.obj = this;
	this.returnValue = null;	
	this.win = {}; 
	// showModalDialog arguments 
	this.sURL = "";
	this.dialogArguments = null; 
	//sFeatures arguments 
	this.dialogHeight = 100;
	this.dialogWidth = 100;
	this.dialogLeft = 20;
	this.dialogTop = 20;
	this.resizable = true;
	this.scroll = true;
	this.title = "";
	this.html = "";
	this._maximize=false;
};
ModalDialogWindow.prototype.destroy = function () {
	this.constructor.call(this);
};
ModalDialogWindow.prototype.showModalDialog = function (sURL, sFeatures) {
	if (this.sURL != "") {
		this.destroy();
	}
	this.sURL = sURL;
	try {
		this.initialize(sFeatures);
	}
	catch (e) {
	}
	window.win = this.obj;
	this.html = "<div class=\"loading-indicator\" id=\"_loading\">loading...</div><iframe id=\"mdFrame\" name=\"mdFrame\" width=\"" + this.dialogWidth + "\" height=\"" + this.dialogHeight + "\" frameborder=\"0\" src=\"" + this.sURL + "\"></iframe>";
	this.win = new this.window({id:"mdWin", title:this.title,width:this.dialogWidth, height:this.dialogHeight-10, html:this.html});
	if (this.dialogLeft && this.dialogTop) {
		this.win.setPosition(this.dialogLeft, this.dialogTop);
	}
	this.win.addListener({"show":function () {
	}, "close":function () {
		window.win = null;
	},"resize":function (obj,width,height) {
		var mdFrame = document.getElementById('mdFrame');
		mdFrame.width=width;
		mdFrame.height=height-30;
	}});
	this.win.show();
	if (Ext.isIE) {
		// iframe load 
		var iframe = document.getElementById("mdFrame");
		if (iframe.attachEvent) {
			iframe.attachEvent("onload", function() {
				_hideLoading();
			});
		} else {
			iframe.onload = function () {
				hideLoading();
			};
		}		
	} else {
		_hideLoading();
	}
	if (this._maximize){
		this.win.maximize();
	}
	
	//this.win.center();
};
function _hideLoading() {
 	var loader = document.getElementById("_loading");
	loader.innerHTML = "";
	loader.style.display = "none";
	var iframe = document.getElementById("mdFrame");
	iframe.style.visibility = "visible";
}
