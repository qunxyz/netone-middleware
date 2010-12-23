
/**
 * ��չxtree2����˸�ѡ��,��ѡ���ֵͨ��{cb}���ã�
 * 0: δѡ�У� 1: ѡ�� , 2��ʧЧδѡ�� �� 3: ʧЧѡ��
 * author hls 
 */
 
//�ڵ��Ƿ�ѡ��
function isNodeCbxSelected(node){
	var cbx = getTreeCheckbox(node);
	if(cbx){
		return cbx.checked;
	}
	return false;
}

 
//�����ͼ�ϵĸ�ѡ��
function getTreeCheckbox(node) {
	var treeelement = node.getElement();
    var cbx = treeelement.firstChild.lastChild.firstChild;
    if (cbx.type) {
        if (cbx.type == "checkbox") {
            return cbx;
        }
    }
}
		
//��ѡ��ֵ����෴��ֵ
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
		
//������ͼ�и�ѡ���ֵ
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

//������ͼ�и�ѡ��Ϊdisabled ;
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

//��ȡѡ�еĺ��ӽڵ�
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
