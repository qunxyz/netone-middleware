<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>选择教师</title>
<jsp:include page="../common/metaExt.jsp"></jsp:include>
<script type="text/javascript">
	
	/*
     * 加载类别
     */
	function loadCategories(){//加载类别
			var vUrl = '<c:url value="/products/products.do?method=onFindPCByType"/>';
			Ext.Ajax.request({
			   url:  vUrl,
			   success: function(response, options){
							  var responseArray = Ext.util.JSON.decode(response.responseText);     
							  var categoriesSelect = document.getElementById('categoriesId');
							  for(var i=0; i< responseArray.length; i++){
							     var option = document.createElement('option');
								 option.text = responseArray[i].categoriesName ;
				                 option.value = responseArray[i].categoriesId;
								 categoriesSelect.options.add(option);
							  }         
			  			},
				failure: function (response, options) {
				    checkAjaxStatus(response);
				}
			});
  		}	

	/*
     * 根据分类获得产品数据
     */
	function loadProduct(){
		var categoriesId=document.getElementById('categoriesId').value;
		
		var vUrl= '<c:url value="/products/product.do?method=onChangeProductByCId"/> ';
		Ext.Ajax.request({
			url: vUrl,
			sync:true,
			params:{categoriesId: categoriesId},
			success:function(response,options){
				//获取并解码Json对象的返回值，存放入数组中
				var responseArray = Ext.util.JSON.decode(response.responseText);
				
				var productSelecet = document.getElementById('product');
				var vOptions = productSelecet.options;
				
				//首先，清空对应select的options数据
				for(var j=vOptions.length-1;j>=0;j--){
					productSelecet.remove(j);				
				}
				
				/*
				 * 重新添加options数据
				 */
				for(var i=0;i < responseArray.length;i++){
					var option =document.createElement('option');
					option.value = responseArray[i].productId;
					option.text = responseArray[i].productName;					
					productSelecet.options.add(option);
				}
			},
			failure: function (response, options) {
			    checkAjaxStatus(response);
			}
		});
	}
	
	/*
	 *页面加载时执行的方法
	 */
	Ext.onReady(function(){
		
		loadCategories();
		/*-
		 *　已经选择的数据重展现出来,加载父窗口传过来的值
		 */
		var valueEStr = document.getElementById('valueE').value;
		var nameEStr  = document.getElementById('nameE').value;
		var teacherSelect = document.getElementById('product');
		
		var value = '', text = '';
		with(opener){
		   var valueEObj = document.getElementById(valueEStr);
	       var nameEObj = document.getElementById(nameEStr);
	       value = valueEObj.value;
	       text = nameEObj.value;
		}
		
		if(value != ''){
		  var option  = document.createElement('option');
	      option.value = valueEObj.value;
	      option.text =  nameEObj.value;
	      teacherSelect.options.add(option);
		}
		
	});
	
	
	function onOk(){//确定事件
	    var  teacherObj = document.getElementById('product');
	    var options = teacherObj.options;
	    
	    var count = 0, teacherId='', teacherName='';
	    for(var i=0; i < options.length; i++){
	        if(options[i].selected){
	          teacherId =  options[i].value;
	          teacherName =  options[i].text;
	          
	          count++;
	        }
	    }
	    
	    if(count >1){
	       Ext.MessageBox.alert('系统提示','请选择一个产品!');
	       return;
	    }
	    
	    var valueEStr = document.getElementById('valueE').value;
	    var nameEStr = document.getElementById('nameE').value;
	    with(opener){
	      
	       var valueEObj = document.getElementById(valueEStr);
	       var nameEObj = document.getElementById(nameEStr);
	       
	       valueEObj.value =  teacherId;
	       nameEObj.value = teacherName;
	    }
		
		window.close();
	 }
	  
	 function onReset(){//重置
	    var valueEStr = document.getElementById('valueE').value;
	    var nameEStr = document.getElementById('nameE').value;
	    with(opener){
	      
	       var valueEObj = document.getElementById(valueEStr);
	       var nameEObj = document.getElementById(nameEStr);
	       
	       valueEObj.value =  '';
	       nameEObj.value = '';
	    }
	 }
</script>
<style>
  html, body{
   padding:0px;
   margin:0px;
  }
</style>
</head>
<body>
<form action="" method="post">
  <input type="hidden" id="valueE" name="valueE" value="${param.valueE}"/>
  <input type="hidden" id="nameE" name="nameE" value="${param.nameE}"/>
  <table width="280">
    <tr>
      <td width="272"  valign="top"><table width="100%" border="3" borderColor="#DFC68E"  style="border-collapse:collapse;">
          <tr>
            <td class="label">物料分类：</td>
            <td><select name="categoriesId" id="categoriesId" style="width: 150px;">
					<option value="">
						----所有----
					</option>
		 	 	</select>
		  </td>
          </tr>
          <tr>
            <td class="label">物料编号：</td>
            <td><select name="select" size="15" multiple="multiple"
															id="productId" style="width:198px; height:200px;" ondblclick="onOk();" >
              </select></td>
          </tr>
          <tr>
            <td colspan="2" align="center"><input name="product" id="product" type="button" onClick="onOk();" value="确　定" />
             <!--  <input name="button" type="button" onClick="onReset();" value="重 置"  style="margin-left:10px"/> -->
            </td>
          </tr>
        </table></td>
    </tr>
  </table>
</form>
</body>
</html>
