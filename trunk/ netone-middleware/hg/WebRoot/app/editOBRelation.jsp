<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%	
String path = request.getContextPath();
String lshId = (String) request.getAttribute("lshId");
%>	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="/WEB-INF/jsp/common/metaExt.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/common/metaJQuery-min.jsp"></jsp:include>
		<title>网点频率配置新增</title>
	</head>
	<body>
	<div class="panel" style="text-align: center;">
	<input type="hidden" id="lshId" name="lshId" value="<%=lshId%>">
		<table>
			<tr>
				<td><font color="blue" size="2">已选择网点</font></td>
				<td></td>
				<td><font color="red" size="2">未选择网点</font></td>
			</tr>
			<tr>
				<td>
					<fieldset>
						<select id="select" name="select" multiple="multiple" size="18"
							style="width: 250px;" ondblclick="moveSelected1();">
						</select>
					</fieldset>
					
				</td>
				<td>
					<div>
						<table>
							<tr>
								<td>
									<input type="button" name="add" id="add" value="←"
										style="width: 60px" onClick="moveSelected();" />
								</td>
							</tr>
							<tr>
								<td>
									<input style="width: 60px" name="delete" id="delete"
										type="button" value="→" onClick="moveSelected1();" />
								</td>
							</tr>
						</table>
					</div>
				</td>
				<td>
				<input type="text" id="Jmytext" name="Jmytext"
									style="width: 250px;font:12px/14px arial;color:#999;" 
									value="" 
									title="请输入网点名称"
									/>
					<fieldset>
						<select id="unselect" name="unselect" multiple="multiple" class="search_locate"
							size="15" style="width: 250px;" ondblclick="moveSelected();">
							
						</select>
					</fieldset>
					
				</td>
			</tr>
			<tr></tr>
			<tr>
				<td colspan="4" align="center">
					<font color="red" size="2">每周几次*:</font>
					<input type="text" id="times" name="times">
					备注:<input type="text" id="note" name="note">
				</td>
			</tr>
		</table>
	</div>
	</body>
</html>
<script type="text/javascript">

function moveSelected(){//添加
    var oSourceSel=document.getElementById('unselect');
    var oTargetSel=document.getElementById('select');
    var arrSelValue = new Array();//建立存储value和text的缓存数组
    var arrSelText = new Array();
    var arrValueTextRelation = new Array();//此数组存贮选中的options，以value来对应
    var index = 0;//用来辅助建立缓存数组
    for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
        if(oSourceSel.options[i].selected){
            arrSelValue[index] = oSourceSel.options[i].value;//存储
            arrSelText[index] = oSourceSel.options[i].text;
            arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i];//建立value和选中option的对应关系
            index++;
        }
    }
    for(var i=0; i<arrSelText.length; i++){  //增加缓存的数据到目的列表框中，并删除源列表框中的对应项
        oTargetSel.options.add(new Option(arrSelText[i],arrSelValue[i]));
        oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);//删除源列表框中的对应项
    }
}

function moveSelected1(){//移除 
  	var oSourceSel=document.getElementById('select');
    var oTargetSel=document.getElementById('unselect');
    var arrSelValue = new Array();//建立存储value和text的缓存数组
    var arrSelText = new Array();
    var arrValueTextRelation = new Array();//此数组存贮选中的options，以value来对应
    var index = 0;//用来辅助建立缓存数组
    for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
        if(oSourceSel.options[i].selected){
            arrSelValue[index] = oSourceSel.options[i].value; //存储
            arrSelText[index] = oSourceSel.options[i].text;
            arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i];//建立value和选中option的对应关系
            index++;
        }
    }
    for(var i=0; i<arrSelText.length; i++){//增加缓存的数据到目的列表框中，并删除源列表框中的对应项
        oTargetSel.options.add(new Option(arrSelText[i],arrSelValue[i]));
        oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);//删除源列表框中的对应项
    }
}

function loadAndClearAll(obj){//全选
  	var oSourceSel = '';
	var oTargetSel = '';
	if(obj.trim()=='loadAll'){//<-
	  oSourceSel=document.getElementById('unselect');
	  oTargetSel=document.getElementById('select');
	}
	if(obj.trim()=='clearAll'){//->
	  oSourceSel=document.getElementById('select');
      oTargetSel=document.getElementById('unselect');
	}
     var arrSelValue = new Array();//建立存储value和text的缓存数组
     var arrSelText = new Array();
     var arrValueTextRelation = new Array();//此数组存贮选中的options，以value来对应
     var index = 0;//用来辅助建立缓存数组
     for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
             arrSelValue[index] = oSourceSel.options[i].value;
             arrSelText[index] = oSourceSel.options[i].text;
             arrValueTextRelation[arrSelValue[index]] = oSourceSel.options[i]; //建立value和选中option的对应关系
             index++;
     }
     for(var i=0; i<arrSelText.length; i++){//增加缓存的数据到目的列表框中，并删除源列表框中的对应项
         oTargetSel.options.add(new Option(arrSelText[i],arrSelValue[i]));
         oSourceSel.removeChild(arrValueTextRelation[arrSelValue[i]]);
     }
}

/*function findByCode(obj){//根据关键字查询
    var setObj = '';
    var code = '';
    if(obj=='queryUnSelectCode'){//未选择
    	code = Ext.get('findCodeU').getValue();
    	setObj = document.getElementById('unselect');
    	if(code=='请输入网点名称'){
    		code='';
    	}
    }
    if(obj=='querySelectCode'){//已选择
    	code = Ext.get('findCode').getValue();
    	setObj = document.getElementById('select');
    	if(code=='请输入产品编号'){
    		code='';
    	}
    }
    
    if (code.indexOf(',')>0){//插入多个编号
    	var codeArr = code.split(',');
    	for(var i=0;i<setObj.options.length;i++){
	    	var str = setObj.options[i].text;
	    	var selectObject = str.trim();
	    	setObj.options[i].selected = false;
	    	
	    	for(var j=0;j<codeArr.length;j++)
	    	if(selectObject == codeArr[j].trim()){
	    		setObj.options[i].selected = true;
	    	}
    	}
    } else {//优化
        for(var i=0;i<setObj.options.length;i++){
	    	var str = setObj.options[i].text;
	    	var selectObject = str.trim();
	    	setObj.options[i].selected = false;
	    	if(selectObject == code.trim()){
	    		setObj.options[i].selected = true;
	    	}
    	}
    }
}*/
//声明两个全局的变量分别存放左与右侧的数据
var leftSelectData = [];
/**
* 加载未分配网点
* @param related 1.已关联 0.未关联
* @param o 加载关联信息的SELECT对象ID
* @param id 当前选择树的ID
*/
function loadOutletInfo(related,o,id){
	Ext.Ajax.request({
		url : "<c:url value='/app.do?method=onLoadOutletsInfo' />",// 请求的服务器地址
		params : {
			related : related,
			id : id
		},
		success : function(response, options) {
			var json = Ext.util.JSON.decode(response.responseText);

			var reader = new Ext.data.JsonReader({
						root : "info"
					}, [{name : 'outId'}, {name : 'outName'}]);
			var data = reader.readRecords(json);
			var records = data.records;
			var count = records.length;

			for (var i = 0; i < count; i++) {
				var rec = records[i];
				var outId = rec.get('outId');
				var outName = rec.get('outName');
				text = outName;
				value = outId;
				document.getElementById(o).options.add(new Option(text, value));
				leftSelectData.push("<option value='"+value+"'>"+text+"</option>"); 
			}
		},
		failure: function (response, options) {
		    checkAjaxStatus(response);
		}
	});
}

String.prototype.Trim = function() { 
   return this.replace(/(^\s*)|(\s*$)/g, ""); 
}


//var leftSelectData = new Array(); 

$(document).ready(function () {
	var id = '${param.lshId}';
	if (id != ''){
		loadOutletInfo('0','unselect',id);//加载
	} else {
		Ext.MessageBox.alert('提示','请选择网点!');
	}
	if(navigator.userAgent.indexOf("MSIE")>0){ 
		document.getElementById('Jmytext').attachEvent("onpropertychange",txChange); 
		//document.getElementById('Jothertext').attachEvent("onpropertychange",txChange2); 
	}else{
		document.getElementById('Jmytext').addEventListener("input",txChange,false);
		///document.getElementById('Jothertext').addEventListener("input",txChange2,false);
	}
});
  
function txChange(){ 
		var content = $("#Jmytext").val().Trim();
		//先清空掉原来的数据		
		$("#unselect").empty();  
		//判断如果关键字为空的话就提取全部
		var oSourceSel=document.getElementById('select');
		if(content==''){	
			$.each(leftSelectData,function(n,value) {
				//$("#unselect").append('<option value="'+value+'">'+value+'</option>');
				 
				 var pass = false;
				 for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
			             var valuex = oSourceSel.options[i].value;
			             var textx = oSourceSel.options[i].text;
			             var vv = "<option value='"+valuex+"'>"+textx+"</option>";
			             if (vv==value){
			             	pass=true;
			             	return;
			             }
			     }
			     if(pass==false) $("#unselect").append(value);
	 		});
		} else {
			//如果不为空就通过关键字重新搜索
			var reg = new RegExp(content,'i');	
			$.each(leftSelectData,function(n,value) {
				if(reg.test(value)){
					//$("#unselect").append('<option value="'+value+'">'+value+'</option>');
					var pass = false;
					for(var i=0; i<oSourceSel.options.length; i++){//存储源列表框中所有的数据到缓存中，并建立value和选中option的对应关系
			             var valuex = oSourceSel.options[i].value;
			             var textx = oSourceSel.options[i].text;
			             var vv = "<option value='"+valuex+"'>"+textx+"</option>";
			             if (vv==value){
			             	pass=true;
			             	return;
			             }
			     	}
			     	if(pass==false) $("#unselect").append(value);
				}
	 		});
		}
	} 


/*Ext.onReady(function(){
	document.getElementById('unselect').options.length=0;
	var id = '${param.lshId}';
	if (id != ''){
		loadOutletInfo('0','unselect',id);//加载
	} else {
		Ext.MessageBox.alert('提示','请选择网点!');
	}

});*/


</script>