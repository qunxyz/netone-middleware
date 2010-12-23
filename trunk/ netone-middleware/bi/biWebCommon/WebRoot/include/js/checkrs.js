
function checkNaturalname(naturalname) {
    if(naturalname==''){
    	return '���Ʋ�����Ϊ��';
    }
	//����Ƿ��������
	var chinese = /^[\u0391-\uFFE5]+$/;
	for (var i = 0; i < naturalname.length; i++) {
		var substr = naturalname.substring(i, i + 1);
		if (substr.match(chinese)) {
			return '�����ϸ�ʽҪ��[������������!]';
		}
	}
	return '';
}

function checkNumber(number){
    if(number==''){
    	return false;
    }
    var numberEl=/^[1-9]\d*$/;
    if(!number.match(numberEl)){
    	return false
    }
    return true;
}
