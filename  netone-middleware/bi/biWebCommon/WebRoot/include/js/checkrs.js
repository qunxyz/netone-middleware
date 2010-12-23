
function checkNaturalname(naturalname) {
    if(naturalname==''){
    	return '名称不允许为空';
    }
	//检查是否包含中文
	var chinese = /^[\u0391-\uFFE5]+$/;
	for (var i = 0; i < naturalname.length; i++) {
		var substr = naturalname.substring(i, i + 1);
		if (substr.match(chinese)) {
			return '不符合格式要求[不允许有中文!]';
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
