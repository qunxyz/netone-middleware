
function getTipInfo(value,params) {
	var p = new RegExp("\\{\\d\\}", "g");
	
    var arr = value.match(p);
    if (arr) arr = arr.toString();
	var split1 = arr.split(',');
	for(var i=0;i<split1.length;i++){
		value = value.replace(split1[i],params[i])
	}
	return value;
}
