 /****
 *
 *
 *		JS 验证类
 *
 ****/		
 		function validator(obj){
 			  var str = obj.value;
  			  var result=str.match(/^[+-]?[0-9]{1,12}\.{0,1}[0-9]{0,2}$/);
              //if(result==null) return false;
              //return true;
			  if (result==null)
			  {
				alert('输入数值金额非法!');
				obj.value = (Math.round(str*100)/100);
			  }
  		}
  		
  		/***
  		* obj 页面验证对象
  		* regex 正则表达式
  		* msg 提示信息
  		
  		function validator(obj,regex,msg){
 			  var str = obj.value;
  			  var result=str.match(regex);
              //if(result==null) return false;
              //return true;
			  if (result==null)
			  {
				alert(msg);
				obj.value = '';
			  }
  		}*/
  		
