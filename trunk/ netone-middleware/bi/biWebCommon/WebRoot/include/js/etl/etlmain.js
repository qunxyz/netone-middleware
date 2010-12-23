function timePartCheck(){
	if($('selpoint2').value=='4'){
		$('hour').disabled=true;
	}else{
	    $('hour').disabled=false;
	}
	    
}

/**
 * 分析页面javascript
 * author: hls
 */
 

//选择的维度，存放nodeobj
var selectedDimList = new List();
//选择的时间，存放nodeobj
var selectedTimeList = new List();
//选择的指标，存放指标id
var selectedTgList = new List();

var tgIdNameMap = new Map();

var selectedTgGroup = null;

/** 页面控件事件 **/
function selectAction_timeSelType(){
	var timeSelType = $("timeSelType");
	if(timeSelType.value == 0){
		//显示树选择
		$("timeTreeDiv").style.display = "block";
		$("calendardiv").style.display = "none";
	}
	else if(timeSelType.value == 1){
		//显示忙时选择
		$("timeTreeDiv").style.display = "none";
		$("calendardiv").style.display = "block";
	}
}

function selectAction_dimTreeList(){



    clearDimAndTarget(); //清除维度和指标
	var dimTreeList = $("dimTreeList");
	if(dimTreeList.value == -1){
		//复位树节点
		dimTree.setText("维度");
    	dimTree.src = "";
		while (dimTree.childNodes.length > 0) {
			dimTree.remove(dimTree.childNodes[dimTree.childNodes.length - 1]);
		}
	}
	else{
	    var timeLevel=$("selpoint1").value;
	    var selectDimText=dimTreeList.options[dimTreeList.selectedIndex].text;
	    var selectDim=dimTreeList.value;
	    var appinfo='';
	    var appinfoTest='';
	    
		dimTree.setText(selectDimText+appinfoTest);
    	dimTree.src = "";
    	dimTree.setSrc("servlet/EtlTreeSvl?treeModelId="+selectDim+appinfo);
    	dimTree.expand();
    	clearDimSelect();
	}
	//处理时段,如果类型选择之后的是非小时,那么时段不可用

	if($('selpoint1').value=='4'){
		$('hour').disabled=false;
	}else{
	    $('hour').disabled=true;
	}

}


function clearDimSelect(){
	var dimResult = $("dimResult");
	dimResult.options.length=0 ;
	selectedDimList.items.length = 0 ;
	selectedTgList.items.length = 0 ;
	do_updateTgGroup(new Map());
}

//指标过滤
function btntgfilter(){
	if(selectedTgList.size()==0){
		alert("没有选择指标!");
		return ;
	}
	var namelist = new List();
	for(var i=0 ; i<selectedTgList.size() ; i++){
		namelist.add(tgIdNameMap.get(selectedTgList.get(i)));
	}

	
	var param = "tgids="+selectedTgList.toString()+"&tgnames="+encodeURIComponent(namelist.toString());
	window.open('tgFilter.do?'+param,"","scrollbars=yes,resizable=yes,width=470,height=210,left=290,top=250");
	
}

//由tgfilte.jsp页面调用该方法
function dotgfilter(tgfiltvalue){
	document.all.tgfiltvalue.value = tgfiltvalue;
}

/**
 * 点击维度树图节点
 */
function nodeAction(nodeid , nodelevel){
	dimTreeNodeAction(nodeid,nodelevel);
}

function timeTreeNodeAction(nodeid , nodelevel){

	var node = timeTree.getSelected();
	//设置复选框变化
	changeCbxChecked(node);
	var nodeinfo = getNodeObjInfo(node);

	var timeResult = $("timeResult");
	var value = nodeinfo.get("nodeid");
	if(isNodeCbxSelected(node)){
		//判断是否在同一等级
		if(selectedTimeList.size() > 0){
			var levelname = selectedTimeList.get(0).get("levelname");
			if(levelname != nodeinfo.get("levelname")){
				alert("不是同一等级的节点，无法添加到选择框中!")
				changeCbxChecked(node);
				return ;
			}
		}
		
		var option = new Option(value,value);
		timeResult.add(option);
		selectedTimeList.add(nodeinfo);
	}
	else{
		//从下拉框中删除
	    if(timeResult.options.length >0 ){
	    	for(var i=0 ; i<timeResult.options.length ; i++){
	    		if(timeResult.options[i].value == value){
	    			do_delSelectedTime(i);
	    			break ;
	    		}
	    	}
    	}
	}
}

function dimTreeNodeAction(nodeid , nodelevel){
	
	var node = dimTree.getSelected();
	//设置复选框变化
	changeCbxChecked(node);
	var nodeinfo = getNodeObjInfo(node);
	
	var dimResult = $("dimResult");
    var value = nodeinfo.get("treeModelId")+"_"+nodeinfo.get("nodeid");
    
     var flag=isSelectAll();
		if(flag){
		 clearDimAndTarget(); //清除维度和指标
		}
    if(isNodeCbxSelected(node)){
    	//判断是否在同一等级
		if(selectedDimList.size() > 0){
			var treemodelid = nodeinfo.get("treeModelId");
			for(var k = 0 ; k<selectedDimList.size() ; k++){
				if(treemodelid == selectedDimList.get(k).get("treeModelId")){
					if(nodeinfo.get("levelname") != selectedDimList.get(k).get("levelname")){
						alert("不是同一等级的节点，无法添加到选择框中!");
						changeCbxChecked(node);
						return ;
					}else{
						break ;
					}
				}
			}
		}    	
    	var dimTreeList = $("dimTreeList");
	    var text1 = dimTreeList.options[dimTreeList.selectedIndex].text;
	    var text2 = node.getText();
	    var text = text1+"->"+text2;
	    var option = new Option(text,value);
	    dimResult.add(option);
	    option.selected = true ;
	    selectedDimList.add(nodeinfo);
	    //触发设置指标组
	    showTgbox();
		do_changeTgGroup();
    }
    else{
	    //从下拉框中删除
	    if(dimResult.options.length >0 ){
	    	for(var i=0 ; i<dimResult.options.length ; i++){
	    		if(dimResult.options[i].value == value){
	    			do_delSelectedDim(i);
	    			break ;
	    		}
	    	}
    	}
    }
}


//删除选定的维度选择值
function btnAction_delSelDim(){
	var dimResult = $("dimResult");
	if(dimResult.selectedIndex != -1){
		do_delSelectedDim(dimResult.selectedIndex);
	}
}


//删除选定的时间选择值
function btnAction_delSelTime(){
	var timeResult = $("timeResult");
	while(timeResult.selectedIndex != -1){
		timeResult.options.remove(timeResult.selectedIndex);
		//删除selectedDimList
		selectedTimeList.remove(timeResult.selectedIndex);
	}
}


function selectAction_tgGroup(){
	do_changeTgList();
}



function selectAction_dimResult(){
	showTgbox();
	do_changeTgGroup();
}

function btnAction_submitCalendar(){

	var valuex=document.getElementById('grownum').value;
	if(valuex==null || valuex==0){
		alert('步长不允许为空或小于1');
		return;
	}

	submitCalendar();
}

function selectAction_timeSelectType(){
	var timeSelectType = $("timeSelectType");
	if(timeSelectType.value==1){
		$("hour").disabled = true ;
	}
	else{
		$("hour").disabled = false ;
	}
}

function getRegExpStr(selpoint){
	if(selpoint == 1){
		return [/\d{4}/ , /\d{4}/];
	}
	else if(selpoint == 2){
		return [/\d{4}-\d{2}/,/(?:\d{4}-)(\d{2})/];
	}
	else if(selpoint == 3){
		return [/\d{4}-\d{2}-\d{2}/,/(?:\d{4}-\d{2}-)(\d{2})/];
	}
	else if(selpoint == 4){
		return [/\d{4}-\d{2}-\d{2} \d{2}/,/(?:\d{4}-\d{2}-\d{2} )(\d{2})/];
	}
	else if(selpoint == 5){
		return [/\d{4}-\d{2}-\d{2} \d{2}:\d{2}/,/(?:\d{4}-\d{2}-\d{2} \d{2}:)(\d{2})/];
	}
	else if(selpoint == 6){
		return [/\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}/,/(?:\d{4}-\d{2}-\d{2} \d{2}:\d{2}:)(\d{2})/];
	}
}

function submitCalendar(){
  		var eselpoint1 = $("selpoint1");
  		var selpoint1 = eselpoint1.value;
  		var startTime = $("selecttime1").value;
  		var endTime = $("selecttime2").value;

  		if(startTime==''){
  			alert('未选择开始时间');
  			return;
  		}
  		if(endTime==''){
  			alert('未选择结束时间');
  			return;  		
  		}

  		var startHour=startTime.substring(11,13);
  		var endHour=endTime.substring(11,13);
  		if(endHour!=startHour){
  			alert('开始时间的时段 与结束时间的时段建议相同!');
  			//return;  			
  		}
  		
  		var hourtime=document.getElementById('hour').value;
  		if(hourtime=='0'){
  			hourtime='00';
  		}
  		if(hourtime!=endHour){
  			alert('系统默认时段与结束时间中的时段不同');
  			//return;  		
  		}
  		
  				var regS = new RegExp("-","gi");
	      	  	if(parseInt(startTime.replace(regS,'')) >= parseInt(endTime.replace(regS,''))){
					alert("结束时间不能小于等于分析的起始时间！");
					return ;
				}
  		
  		
  		var selpoint2 = $("selpoint2").value;
  		var interval = $("grownum").value;
  		
  		//
  		var timeSelectType = $("timeSelectType");
  		if(timeSelectType.value == 0){
  			//分段选择
  			var hour = $("hour").value;
  			if(hour.length == 1){
  				hour = "0"+hour ;
  			}
  			startTime = startTime.substring(0,11)+hour+startTime.substring(13);
  		}
  		else{
  			//连续选择
  			selpoint2 = 4 ;  //设置为小时
  		}
  		
  		if(startTime && endTime){
  			var prestr1 = "["+(parseInt(selpoint1)+1)+"]";
  			var prestr2 = "["+startTime.match(getRegExpStr(selpoint1)[0])+"$"
  						 + (parseInt(selpoint1)+1)+"&"
  						 + (parseInt(startTime.match(getRegExpStr(selpoint1)[1])[1])+eselpoint1.options[eselpoint1.selectedIndex].text+",")
  						 +"]";
  			var prestr3 = "["+selpoint1+",]";
  			var prestr = prestr1+prestr2+prestr3 ;
  			var endtimeprestr = endTime.match(getRegExpStr(selpoint1)[0]);
  			var src = "servlet/EtlTimeSelectSvl?prestr="+encodeURIComponent(prestr)+"&seltype="+selpoint2+"&interval="+interval+"&endTime="+endtimeprestr;
			var rexml = xmlhttp(src);
			var restr = rexml.responseText;
			var times = restr.split("#");
			var timeResult = $("timeResult");
			timeResult.options.length = 0 ;
			selectedTimeList.items.length = 0 ;
			for(var k =0 ; k<times.length ; k++){
				var map = new Map();
				map.load(times[k]);
				var nodeid = map.get("nodeid");
				timeResult.add(new Option(nodeid,nodeid));
				selectedTimeList.add(map);
			}
  		}
  		else{
  			alert("未选择时间范围！")
  		}
}


function getselTimelevel(){
	if(selectedTimeList.size()>0){
		return selectedTimeList.get(0).get("levelname");
	}
}


function treesonload(){

}



//点击分析按钮
	function analyse(){
		if(selectedDimList.size() == 0){
			alert("没有选择维度!");
			return ;
		}
		if(selectedTimeList.size() == 0){
			alert("没有选择时间!");
			return ;
		}
		if(selectedTgList.size() == 0){
			alert("没有选择指标!");
			return ;
		}
		if(selectedTgList.size() > 16){
			alert("选择的指标不能超过16个,请重新选择!");
			return ;
		}
		
		//设置datamodelid为tggroup的id;
		document.all.datamodelid.value = selectedTgGroup ;
		
		var selectedDimValue = selectedDimList.join("#");
		var selectedTimeValue = selectedTimeList.join("#");
		document.all.seldimvalue.value = selectedDimValue+"#"+selectedTimeValue;
		document.all.seltgvalue.value = selectedTgList.toString();

		var dimTreeList = $("dimTreeList");
		if(dimTreeList.value=="RADIO_TREE_G_HW"||dimTreeList.value=="RADIO_TREE_G_MOTO"||dimTreeList.value=="RADIO_TREE_G_ZTE"){
		  if(isSelectAll()&&selectedDimList.get(0).get("levelname")!="省PLMN"&&selectedDimList.get(0).get("levelname")!="REGION")
		  	document.all.exterValue.value =getManufacturerValue(dimTreeList.value);
		  else
		    document.all.exterValue.value ="";
		}else{
		document.all.exterValue.value ="";
		}
		
		
		document.forms[0].submit();
		document.all.tgfiltvalue.value = "";
		document.all.tgfiltSqlvalue.value = "";
		
		//分析按钮设置成不可用
		//document.all.btnAnalyse.disabled = true ;
	}
function getManufacturerValue(manufacturer){

  if(manufacturer=="RADIO_TREE_G_HW")
  return " and manufacturer='HUAWEI' ";
  if(manufacturer=="RADIO_TREE_G_MOTO")
  return " and manufacturer='MOTOROLA'";
  if(manufacturer=="RADIO_TREE_G_ZTE")
  return " and manufacturer='ZHONGXING' ";
  else
   return "";

}

function hiddenTgbox(){
	var analyzebox = $("analyzebox");
	var tgbox = $("tgbox");
	analyzebox.style.width = 200;
	tgbox.style.display = "none";
}

function showTgbox(){
	var tgbox = $("tgbox");
	if(tgbox.style.display == "none"){
		var analyzebox = $("analyzebox");	
		analyzebox.style.width = 400;
		tgbox.style.display = "block";
	}
}


/*** 业务运算 ***/

//index为在以选择的维度值的列表中的序号。
function do_delSelectedDim(index){
	if(index != -1){
	
	
		var dimResult = $("dimResult");
		dimResult.options.remove(index);
		
		//删除selectedDimList
		selectedDimList.remove(index);
		
		if(dimResult.options.length==0){  //删除指标和指标组
			selectedTgList.items.length = 0 ;
			var targetResult=$("targetResult");
			 targetResult.options.length = 0 ;
			 do_changeTgGroup(); //更新指标组
		}else{
		dimResult.selectedIndex = index-1<0?0:index-1; //回到上一个维度
		}
		
		/*删除已选择的指标
		var tglistdiv = $("tglistdiv");
		var tgs = tglistdiv.childNodes;
		for(var k=0 ; k<tgs.length ; k++){
			if(tgs[k].tagName == "INPUT"){
				if(tgs[k].checked){
					selectedTgList.removeItem(tgs[k].value);
				}
			}
		}
		*/
		//更新指标组下拉框
		
	}
}

//更新指标组的值
function do_changeTgGroup(){
	var dimResult = $("dimResult");
	var timelevel=$("selpoint1").value;
	var timelevelinfo='1h';
		if('1'==timelevel){
	    	timelevelinfo='1y';
	    }else if('2'==timelevel){
			timelevelinfo='1m';
	    }else if('3'==timelevel){
			timelevelinfo='1d';
	    }
	
	timelevelinfo='timelevel='+timelevelinfo+'&';

	var index = dimResult.selectedIndex;
	if(index == -1){
		var map = new Map();
		do_updateTgGroup(map);
	}
	else{
		var nodeobj = selectedDimList.get(index);
		var preps = nodeobj.toString();
		preps = preps.replace(/,/g,"&");
		var url = "servlet/EtlTargetSelectSvl";
		var pars = timelevelinfo+"method=targetGroupList&"+preps;
		var tgajax = new Ajax.Request(
		                 url,
		                 {method: 'get', parameters: pars, onSuccess: success_do_changeTgGroup, onFailure: failure_do_changeTgGroup}
		                 );
	}
}

function success_do_changeTgGroup(xmlhttp){
	var restr = xmlhttp.responseText;
	var map = new Map();
	map.load(restr);
	do_updateTgGroup(map);
}


function failure_do_changeTgGroup(xmlhttp){
	alert("该节点没有定义指标组，请选择其他节点！");
}


function do_updateTgGroup(map){
	var keyarr = map.keyarr;
	var tgGroup = $("tgGroup");
	tgGroup.options.length = 0 ;
	
	//var targetResult=$("tgGroup");
	for(var i=0 ; i<keyarr.length ; i++){
		tgGroup.options.add(new Option(map.get(keyarr[i]),keyarr[i]));
	}
	//更新完指标组更新指标列表
	do_changeTgList();
}



//更新指标列表
function do_changeTgList(){
	var tgGroup = $("tgGroup");
 	var index = tgGroup.selectedIndex;
 	if(index != -1 ){
 		var url = "servlet/EtlTargetSelectSvl";
		var pars = "method=targetElementList&groupid="+encodeURI(tgGroup.value) ; 
		var tgajax = new Ajax.Request(
	                 url,
	                 {method: 'get', parameters: pars, onSuccess: success_do_changeTgList, onFailure: failure_do_changeTgList}
	                 );
 	}
 	else{
 		//判断下拉框中是否有值
 		if(tgGroup.options.length==0){
 			var tglistdiv = $("tglistdiv");
 			tglistdiv.innerHTML = "";
 		}
 	}
}

function success_do_changeTgList(xmlhttp){
	var tglistdiv = $("tglistdiv");
	var tgGroup = $("tgGroup");
	tglistdiv.innerHTML = "";
	var restr = xmlhttp.responseText;
	var map = new Map();
	map.load(restr);
	var keyarr = map.keyarr;
	
	for(var i=0 ; i<keyarr.length ; i++){
		var cbx = document.createElement("input") ;
		cbx.type = "checkbox" ;
		cbx.attachEvent("onclick",cbxAction_tgcbx);
		cbx.value = keyarr[i];
		tglistdiv.appendChild(cbx);
		
		//if(tgGroup.value == selectedTgGroup){
			if(selectedTgList.indexOf(cbx.value+":"+tgGroup.value) != -1){
				cbx.checked = true;
			}
		//}
		tglistdiv.appendChild(document.createTextNode(map.get(keyarr[i])));
		tglistdiv.appendChild(document.createElement("br"));
	}
}

function failure_do_changeTgList(xmlhttp){
	alert("读取指标组中的指标出错！");
}


//点击指标删除事件

function btnAction_delSelTarget(){
var targetResult = $("targetResult");
	while(targetResult.selectedIndex != -1){
		do_delSelectedTarget(targetResult.selectedIndex);
	}

}

//点击指标事件
function cbxAction_tgcbx(){
	var tgcbx = event.srcElement;
	var tgGroup = $("tgGroup");
	var  targetResult=$("targetResult");
	var name = tgcbx.nextSibling.nodeValue;
	var value=tgcbx.value+":"+tgGroup.value;
	if(tgcbx.checked){		
	/*	if(tgGroup.value != selectedTgGroup){
			selectedTgList.items.length = 0 ;
			selectedTgGroup = tgGroup.value ;
		}
		*/
		selectedTgList.add(value);
		targetResult.add(new Option(name,value));
		//添加到tgIdNameMap
		if(tgIdNameMap.indexOf(value) == -1){
			tgIdNameMap.put(value,name);
		}
	}
	else{
	 //从下拉框中删除
	    if(targetResult.options.length >0 ){
	    	for(var i=0 ; i<targetResult.options.length ; i++){
	    		if(targetResult.options[i].value == value){
	    			do_delSelectedTarget(i);
	    			break ;
	    		}
	    	}
    	}
	}
}


function do_delSelectedTarget(index){
	if(index != -1){
		var targetResult = $("targetResult");
		targetResult.options.remove(index);
		selectedTgList.remove(index);
	}
}

function do_delSelectedTime(index){
	if(index != -1){
		var timeResult = $("timeResult");
		timeResult.options.remove(index);
		selectedTimeList.remove(index);
	}
}

function  clearDimAndTarget(){
		//删除维度
		var dimResult = $("dimResult");
		  dimResult.options.length = 0 ;
		  selectedDimList.items.length = 0 ;
		  //删除指标组与指标
		  var targetResult = $("targetResult");
		   targetResult.options.length = 0 ;
		  selectedTgList.items.length = 0 ;

}

/** 树图操作方法 **/
function getNodeObjInfo(node){
	var src = node.src ;
	if(src){
		var str = src.substring(src.indexOf("?")+1);
		str = str.replace(/&/g,",");
		var prepMap = new Map();
		prepMap.load(str);
		return prepMap ;
	}
}


/** 右键点击事件.....  **/
function refer(selectAllKey){
var node = dimTree.getSelected();
if(node==null){
		alert("不是树节点,无法选中");
		return;
	}	
	var nodeinfo = getNodeObjInfo(node);
	var  nodeid=nodeinfo.get("nodeid");
	var nodelevel=nodeinfo.get("levelname");	
	if(nodeid==null){
		alert("不是树节点,无法选中");
		return;
	}	
	dimTreeNode_RightClickAction(nodeid,nodelevel,selectAllKey);
}

function dimTreeNode_RightClickAction(nodeid , nodelevel,selectAllKey){
	
	var node = dimTree.getSelected();
	//设置复选框变化
	//changeCbxChecked(node);
	var nodeinfo = getNodeObjInfo(node);	
	var dimResult = $("dimResult");
    var value = nodeinfo.get("treeModelId")+"_"+nodeinfo.get("nodeid");
    
    var flag=isSelectAll();
		if(!flag||selectAllKey!=selectedDimList.get(0).get("levelname")){
		 clearDimAndTarget();
		}
		
    	//判断是否在同一等级
		if(selectedDimList.size() > 0){
			var treemodelid = nodeinfo.get("treeModelId");
			for(var k = 0 ; k<selectedDimList.size() ; k++){
				if(treemodelid == selectedDimList.get(k).get("treeModelId")){		  
					if(nodeinfo.get("levelname") != selectedDimList.get(k).get("levelnameOld")){
						alert("不是同一等级的节点，无法添加到选择框中!");						
						return ;
					}else{
						break ;
					}
				}
			}
		}
		
		 nodeinfo.put("levelnameOld",nodeinfo.get("levelname"));//之前的结点类型
	      var  nodeLevelReflect=getSelectAllValue(nodeinfo.get("levelname")); //当前结点要查找所有节点的 节点类型
	      if(nodeLevelReflect=="badNode"){
	        alert("此节点类型与数据库节点类型无法匹配,无法选择此节点的所有下属节点");
	        return;
	      }
	    nodeinfo.put("selectAllFlag",nodeLevelReflect);
	      nodeinfo.put("levelname",selectAllKey);
	      
	      
    	var dimTreeList = $("dimTreeList");
	    var text1 = dimTreeList.options[dimTreeList.selectedIndex].text;
	    var text2 = node.getText();
	    var text = text1+"->"+text2+"->所有"+selectAllKey+"节点";
	    var option = new Option(text,value);
	    dimResult.add(option);
	    option.selected = true ;
	    selectedDimList.add(nodeinfo);
	    //触发设置指标组
	    showTgbox();
		do_changeTgGroup();

    }

//判断是否有选择所有的下级 
 function  isSelectAll(){    
  		if(selectedDimList.size() > 0){
			for(var k = 0 ; k<selectedDimList.size() ; k++){
				if(selectedDimList.get(k).get("levelnameOld")&&selectedDimList.get(k).get("levelnameOld")!=""){				  
					return true;
				}
			}
   	}
   	return false;
   }
   
   function  getSelectAllValue(str){
      var  rstr="msc";
     if("region"==str.toLowerCase())   {
     	rstr="region_sys_int_id";
     }else if("msc"==str.toLowerCase()){
    	 rstr="msc_sys_int_id";
     }else if("bsc"==str.toLowerCase()){
    	 rstr="bsc_sys_int_id";
     }else if("bts"==str.toLowerCase()){
    	 rstr="bts_sys_int_id";
     }else if("cell"==str.toLowerCase()){
    	 rstr="cell_sys_int_id";
     }else if("carrier"==str.toLowerCase()){
    	 rstr="carrier_sys_int_id";
     }else if("linkset"==str.toLowerCase()){
    	 rstr="linkset_sys_int_id";
     }else if("link"==str.toLowerCase()){
    	 rstr="link_sys_int_id";
     }else if("trunkgroup"==str.toLowerCase()){
    	 rstr="trunkgroup_sys_int_id";
     }else{
      rstr="badNode";
     }
     return rstr;    
   }
   
   
   
   
   function showmenuie5() {
		//获取当前鼠标右键按下后的位置，据此定义菜单显示的位置
		var rightedge = document.body.clientWidth-event.clientX;
		var bottomedge = document.body.clientHeight-event.clientY;

		//如果从鼠标位置到窗口右边的空间小于菜单的宽度，就定位菜单的左坐标（Left）为当前鼠标位置向左一个菜单宽度
		if (rightedge <ie5menu.offsetWidth)
			ie5menu.style.left = document.body.scrollLeft + event.clientX - ie5menu.offsetWidth;
		else
		//否则，就定位菜单的左坐标为当前鼠标位置
			ie5menu.style.left = document.body.scrollLeft + event.clientX;

		//如果从鼠标位置到窗口下边的空间小于菜单的高度，就定位菜单的上坐标（Top）为当前鼠标位置向上一个菜单高度
		if (bottomedge <ie5menu.offsetHeight)
			ie5menu.style.top = document.body.scrollTop + event.clientY - ie5menu.offsetHeight;
		else
		//否则，就定位菜单的上坐标为当前鼠标位置
			ie5menu.style.top = document.body.scrollTop + event.clientY;


     	//设置右键弹出菜单的显示
		setRightClickMenu();
		//设置菜单可见
		ie5menu.style.visibility = "visible";
		return false;
	}
	function hidemenuie5() {
		//隐藏菜单  设置visibility为hidden就OK！
		ie5menu.style.visibility = "hidden";
	}

	function highlightie5() {
		//高亮度鼠标经过的菜单条项目
		//如果鼠标经过的对象是menuitems，就重新设置背景色与前景色
		//event.srcElement.className表示事件来自对象的名称，必须首先判断这个值，这很重要！
		if (event.srcElement.className == "menuitems") {
			event.srcElement.style.backgroundColor = "highlight";
			event.srcElement.style.color = "white";

		//将链接信息显示到状态行
		//event.srcElement.url表示事件来自对象表示的链接URL
		if (display_url)
			window.status = event.srcElement.url;
		}
}

	function lowlightie5() {
		//恢复菜单条项目的正常显示

		if (event.srcElement.className == "menuitems") {
			event.srcElement.style.backgroundColor = "";
			event.srcElement.style.color = "black";
			window.status = "";
		}
	}

	//右键下拉菜单功能跳转
	function jumptoie5() {
		//转到新的链接位置
		var seltext=window.document.selection.createRange().text

		if (event.srcElement.className == "menuitems") {
		//如果存在打开链接的目标窗口，就在那个窗口中打开链接
			if (event.srcElement.getAttribute("target") != null){
				window.open(event.srcElement.url, event.srcElement.getAttribute("target"));
			}
			else
			//否则，在当前窗口打开链接
			window.location = event.srcElement.url;
		}
	}
	
	function  setRightClickMenu(){
		var  treeValue=document.all.dimTreeList.value;
		//alert(treeValue);
		if(treeValue=="COMMUTE_G_MSC_ALL"){
			document.all.allBSC.style.display="none";
			document.all.allBTS.style.display="none";
			document.all.allCELL.style.display="none";
			document.all.allCARRIER.style.display="none";
			document.all.allLINKSET.style.display="none";
			document.all.allLINK.style.display="none";
			document.all.allTRUNKGROUP.style.display="none";
		}else if(treeValue=="COMMUTE_G_LINKSET_ALL"){
			document.all.allBSC.style.display="none";
			document.all.allBTS.style.display="none";
			document.all.allCELL.style.display="none";
			document.all.allCARRIER.style.display="none";
			document.all.allLINKSET.style.display="";
			document.all.allLINK.style.display="";
			document.all.allTRUNKGROUP.style.display="none";
		
		}else if(treeValue=="COMMUTE_G_TRUNKGROUP_ALL"){
			document.all.allBSC.style.display="none";
			document.all.allBTS.style.display="none";
			document.all.allCELL.style.display="none";
			document.all.allCARRIER.style.display="none";
			document.all.allLINKSET.style.display="none";
			document.all.allLINK.style.display="none";	
			document.all.allTRUNKGROUP.style.display="";	
		}else{
			document.all.allBSC.style.display="";
			document.all.allBTS.style.display="";
			document.all.allCELL.style.display="";
			document.all.allCARRIER.style.display="";
			document.all.allLINKSET.style.display="none";
			document.all.allLINK.style.display="none";
			document.all.allTRUNKGROUP.style.display="none";
		
		}
	
	}