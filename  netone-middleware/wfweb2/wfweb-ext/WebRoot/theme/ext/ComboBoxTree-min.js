
Ext.ux.ComboBoxTree = function () {
	this.treeId = Ext.id() + "-tree";
	this.maxHeight = arguments[0].maxHeight || arguments[0].height || this.maxHeight;
	this.tpl = new Ext.Template("<tpl for=\".\"><div style=\"height:" + this.maxHeight + "px\"><div id=\"" + this.treeId + "\"></div></div></tpl>");
	this.store = new Ext.data.SimpleStore({fields:[], data:[[]]});
	this.selectedClass = "";
	this.mode = "local";
	this.triggerAction = "all";
	this.onSelect = Ext.emptyFn;
	this.editable = false;
	this.selectNodeModel = arguments[0].selectNodeModel || "exceptRoot";
	Ext.ux.ComboBoxTree.superclass.constructor.apply(this, arguments);
};
Ext.extend(Ext.ux.ComboBoxTree, Ext.form.ComboBox, {expand:function () {
	Ext.ux.ComboBoxTree.superclass.expand.call(this);
	if (!this.tree.rendered) {
		this.tree.height = this.maxHeight;
		this.tree.border = false;
		this.tree.autoScroll = true;
		if (this.tree.xtype) {
			this.tree = Ext.ComponentMgr.create(this.tree, this.tree.xtype);
		}
		this.tree.render(this.treeId);
		var B = this;
		this.tree.on("click", function (F) {
			var E = (F == B.tree.getRootNode());
			var D = B.selectNodeModel;
			var C = F.isLeaf();
			if (E && D != "all") {
				return;
			} else {
				if (D == "folder" && C) {
					return;
				} else {
					if (D == "leaf" && !C) {
						return;
					}
				}
			}
			B.setValue(F);
			B.collapse();
		});
		var A = this.tree.getRootNode();
		if (!A.isLoaded()) {
			A.reload();
		}
	}
}, setValue:function (A) {
	var B = A.text;
	this.lastSelectionText = B;
	if (this.hiddenField) {
		this.hiddenField.value = A.id;
	}
	Ext.form.ComboBox.superclass.setValue.call(this, B);
	this.value = A.id;
}, getValue:function () {
	return typeof this.value != "undefined" ? this.value : "";
}});
Ext.reg("combotree", Ext.ux.ComboBoxTree);

