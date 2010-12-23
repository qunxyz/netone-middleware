
/**
 * 扩展xtree2添加了复选框,复选框的值通过{cb}设置，
 * 0: 未选中， 1: 选中 , 2：失效未选中 ， 3: 失效选中
 * author hls 
 */
 
//节点是否被选中
function isNodeCbxSelected(node){
	var cbx = getTreeCheckbox(node);
	if(cbx){
		return cbx.checked;
	}
	return false;
}

 
//获得树图上的复选框
function getTreeCheckbox(node) {
	var treeelement = node.getElement();
    var cbx = treeelement.firstChild.lastChild.firstChild;
    if (cbx.type) {
        if (cbx.type == "checkbox") {
            return cbx;
        }
    }
}
		
//复选框值变成相反的值
function changeCbxChecked(node) {
    var cbx = getTreeCheckbox(node);
    if (cbx) {
        if (cbx.disabled !== true) {
            cbx.checked = !cbx.checked;
            if(cbx.checked){
            	node.checkbox = 1;
            }
            else{
            	node.checkbox = 0 ;
            }
            return cbx.checked;
        }
    }
    return false;
}
		
//设置树图中复选框的值
function setCbxChecked(node, b) {
    var cbx = getTreeCheckbox(node);
    if (cbx) {
        if (cbx.disabled !== true) {
            if(b){
            	node.checkbox = 1;
            }
            else{
            	node.checkbox = 0 ;
            }
            cbx.checked = b;
            return cbx.checked;
        }
    }
    return false;
}

//设置树图中复选框为disabled ;
function setCbxDisabled(node , b){
	var cbx = getTreeCheckbox(node);
	if(cbx){
		cbx.disabled = b ;
		if(b){
			if(cbx.checked){
				node.checkbox = 3;
			}
			else{
				node.checkbox = 2;
			}
		}
		else{
			if(cbx.checked){
				node.checkbox = 1;
			}
			else{
				node.checkbox = 0;
			}
		}
	}
}

//获取选中的孩子节点
function getSelectedChildNodes(node){
	var list = new Array();
	if(node.hasChildren()){
		var child = node.childNodes;
		for(var i=0 ; i<child.length ; i++){
			if(isNodeCbxSelected(child[i])){
				list.push(child[i]);
			}
		}
	}
	return list ;
}
