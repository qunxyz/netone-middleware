
Ext.ux.Toast = function () {
	var b;
	function a(c, d) {
		//return ["<div class=\"msg\">", "<div class=\"x-box-tl\"><div class=\"x-box-tr\"><div class=\"x-box-tc\"></div></div></div>", "<div class=\"x-box-ml\"><div class=\"x-box-mr\"><div class=\"x-box-mc\"><h3>", c, "</h3>", d, "</div></div></div>", "<div class=\"x-box-bl\"><div class=\"x-box-br\"><div class=\"x-box-bc\"></div></div></div>", "</div>"].join("");
		return ["<div style=\"background:#fff;border:3px solid #B6B8BA;color: #222;padding: 10px 15px;margin: 0;\"><h3>", c, "</h3>", d, "</div>"].join("");
	}
	return {msg:function (f, e) {
		if (!b) {
			b = Ext.DomHelper.insertFirst(document.body, {id:"msg-div", style:"position:absolute;z-index:10000"}, true);
		}
		var d = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
		var c = Ext.DomHelper.append(b, {html:a(f, d)}, true);
		b.alignTo(document, "t-t");
		c.slideIn("t").pause(3).ghost("t", {remove:true});
	}};
}();

