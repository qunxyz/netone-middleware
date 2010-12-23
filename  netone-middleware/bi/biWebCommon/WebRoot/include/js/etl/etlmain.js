function timePartCheck(){
	if($('selpoint2').value=='4'){
		$('hour').disabled=true;
	}else{
	    $('hour').disabled=false;
	}
	    
}

/**
 * ����ҳ��javascript
 * author: hls
 */
 

//ѡ���ά�ȣ����nodeobj
var selectedDimList = new List();
//ѡ���ʱ�䣬���nodeobj
var selectedTimeList = new List();
//ѡ���ָ�꣬���ָ��id
var selectedTgList = new List();

var tgIdNameMap = new Map();

var selectedTgGroup = null;

/** ҳ��ؼ��¼� **/
function selectAction_timeSelType(){
	var timeSelType = $("timeSelType");
	if(timeSelType.value == 0){
		//��ʾ��ѡ��
		$("timeTreeDiv").style.display = "block";
		$("calendardiv").style.display = "none";
	}
	else if(timeSelType.value == 1){
		//��ʾæʱѡ��
		$("timeTreeDiv").style.display = "none";
		$("calendardiv").style.display = "block";
	}
}

function selectAction_dimTreeList(){



    clearDimAndTarget(); //���ά�Ⱥ�ָ��
	var dimTreeList = $("dimTreeList");
	if(dimTreeList.value == -1){
		//��λ���ڵ�
		dimTree.setText("ά��");
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
	//����ʱ��,�������ѡ��֮����Ƿ�Сʱ,��ôʱ�β�����

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

//ָ�����
function btntgfilter(){
	if(selectedTgList.size()==0){
		alert("û��ѡ��ָ��!");
		return ;
	}
	var namelist = new List();
	for(var i=0 ; i<selectedTgList.size() ; i++){
		namelist.add(tgIdNameMap.get(selectedTgList.get(i)));
	}

	
	var param = "tgids="+selectedTgList.toString()+"&tgnames="+encodeURIComponent(namelist.toString());
	window.open('tgFilter.do?'+param,"","scrollbars=yes,resizable=yes,width=470,height=210,left=290,top=250");
	
}

//��tgfilte.jspҳ����ø÷���
function dotgfilter(tgfiltvalue){
	document.all.tgfiltvalue.value = tgfiltvalue;
}

/**
 * ���ά����ͼ�ڵ�
 */
function nodeAction(nodeid , nodelevel){
	dimTreeNodeAction(nodeid,nodelevel);
}

function timeTreeNodeAction(nodeid , nodelevel){

	var node = timeTree.getSelected();
	//���ø�ѡ��仯
	changeCbxChecked(node);
	var nodeinfo = getNodeObjInfo(node);

	var timeResult = $("timeResult");
	var value = nodeinfo.get("nodeid");
	if(isNodeCbxSelected(node)){
		//�ж��Ƿ���ͬһ�ȼ�
		if(selectedTimeList.size() > 0){
			var levelname = selectedTimeList.get(0).get("levelname");
			if(levelname != nodeinfo.get("levelname")){
				alert("����ͬһ�ȼ��Ľڵ㣬�޷���ӵ�ѡ�����!")
				changeCbxChecked(node);
				return ;
			}
		}
		
		var option = new Option(value,value);
		timeResult.add(option);
		selectedTimeList.add(nodeinfo);
	}
	else{
		//����������ɾ��
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
	//���ø�ѡ��仯
	changeCbxChecked(node);
	var nodeinfo = getNodeObjInfo(node);
	
	var dimResult = $("dimResult");
    var value = nodeinfo.get("treeModelId")+"_"+nodeinfo.get("nodeid");
    
     var flag=isSelectAll();
		if(flag){
		 clearDimAndTarget(); //���ά�Ⱥ�ָ��
		}
    if(isNodeCbxSelected(node)){
    	//�ж��Ƿ���ͬһ�ȼ�
		if(selectedDimList.size() > 0){
			var treemodelid = nodeinfo.get("treeModelId");
			for(var k = 0 ; k<selectedDimList.size() ; k++){
				if(treemodelid == selectedDimList.get(k).get("treeModelId")){
					if(nodeinfo.get("levelname") != selectedDimList.get(k).get("levelname")){
						alert("����ͬһ�ȼ��Ľڵ㣬�޷���ӵ�ѡ�����!");
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
	    //��������ָ����
	    showTgbox();
		do_changeTgGroup();
    }
    else{
	    //����������ɾ��
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


//ɾ��ѡ����ά��ѡ��ֵ
function btnAction_delSelDim(){
	var dimResult = $("dimResult");
	if(dimResult.selectedIndex != -1){
		do_delSelectedDim(dimResult.selectedIndex);
	}
}


//ɾ��ѡ����ʱ��ѡ��ֵ
function btnAction_delSelTime(){
	var timeResult = $("timeResult");
	while(timeResult.selectedIndex != -1){
		timeResult.options.remove(timeResult.selectedIndex);
		//ɾ��selectedDimList
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
		alert('����������Ϊ�ջ�С��1');
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
  			alert('δѡ��ʼʱ��');
  			return;
  		}
  		if(endTime==''){
  			alert('δѡ�����ʱ��');
  			return;  		
  		}

  		var startHour=startTime.substring(11,13);
  		var endHour=endTime.substring(11,13);
  		if(endHour!=startHour){
  			alert('��ʼʱ���ʱ�� �����ʱ���ʱ�ν�����ͬ!');
  			//return;  			
  		}
  		
  		var hourtime=document.getElementById('hour').value;
  		if(hourtime=='0'){
  			hourtime='00';
  		}
  		if(hourtime!=endHour){
  			alert('ϵͳĬ��ʱ�������ʱ���е�ʱ�β�ͬ');
  			//return;  		
  		}
  		
  				var regS = new RegExp("-","gi");
	      	  	if(parseInt(startTime.replace(regS,'')) >= parseInt(endTime.replace(regS,''))){
					alert("����ʱ�䲻��С�ڵ��ڷ�������ʼʱ�䣡");
					return ;
				}
  		
  		
  		var selpoint2 = $("selpoint2").value;
  		var interval = $("grownum").value;
  		
  		//
  		var timeSelectType = $("timeSelectType");
  		if(timeSelectType.value == 0){
  			//�ֶ�ѡ��
  			var hour = $("hour").value;
  			if(hour.length == 1){
  				hour = "0"+hour ;
  			}
  			startTime = startTime.substring(0,11)+hour+startTime.substring(13);
  		}
  		else{
  			//����ѡ��
  			selpoint2 = 4 ;  //����ΪСʱ
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
  			alert("δѡ��ʱ�䷶Χ��")
  		}
}


function getselTimelevel(){
	if(selectedTimeList.size()>0){
		return selectedTimeList.get(0).get("levelname");
	}
}


function treesonload(){

}



//���������ť
	function analyse(){
		if(selectedDimList.size() == 0){
			alert("û��ѡ��ά��!");
			return ;
		}
		if(selectedTimeList.size() == 0){
			alert("û��ѡ��ʱ��!");
			return ;
		}
		if(selectedTgList.size() == 0){
			alert("û��ѡ��ָ��!");
			return ;
		}
		if(selectedTgList.size() > 16){
			alert("ѡ���ָ�겻�ܳ���16��,������ѡ��!");
			return ;
		}
		
		//����datamodelidΪtggroup��id;
		document.all.datamodelid.value = selectedTgGroup ;
		
		var selectedDimValue = selectedDimList.join("#");
		var selectedTimeValue = selectedTimeList.join("#");
		document.all.seldimvalue.value = selectedDimValue+"#"+selectedTimeValue;
		document.all.seltgvalue.value = selectedTgList.toString();

		var dimTreeList = $("dimTreeList");
		if(dimTreeList.value=="RADIO_TREE_G_HW"||dimTreeList.value=="RADIO_TREE_G_MOTO"||dimTreeList.value=="RADIO_TREE_G_ZTE"){
		  if(isSelectAll()&&selectedDimList.get(0).get("levelname")!="ʡPLMN"&&selectedDimList.get(0).get("levelname")!="REGION")
		  	document.all.exterValue.value =getManufacturerValue(dimTreeList.value);
		  else
		    document.all.exterValue.value ="";
		}else{
		document.all.exterValue.value ="";
		}
		
		
		document.forms[0].submit();
		document.all.tgfiltvalue.value = "";
		document.all.tgfiltSqlvalue.value = "";
		
		//������ť���óɲ�����
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


/*** ҵ������ ***/

//indexΪ����ѡ���ά��ֵ���б��е���š�
function do_delSelectedDim(index){
	if(index != -1){
	
	
		var dimResult = $("dimResult");
		dimResult.options.remove(index);
		
		//ɾ��selectedDimList
		selectedDimList.remove(index);
		
		if(dimResult.options.length==0){  //ɾ��ָ���ָ����
			selectedTgList.items.length = 0 ;
			var targetResult=$("targetResult");
			 targetResult.options.length = 0 ;
			 do_changeTgGroup(); //����ָ����
		}else{
		dimResult.selectedIndex = index-1<0?0:index-1; //�ص���һ��ά��
		}
		
		/*ɾ����ѡ���ָ��
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
		//����ָ����������
		
	}
}

//����ָ�����ֵ
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
	alert("�ýڵ�û�ж���ָ���飬��ѡ�������ڵ㣡");
}


function do_updateTgGroup(map){
	var keyarr = map.keyarr;
	var tgGroup = $("tgGroup");
	tgGroup.options.length = 0 ;
	
	//var targetResult=$("tgGroup");
	for(var i=0 ; i<keyarr.length ; i++){
		tgGroup.options.add(new Option(map.get(keyarr[i]),keyarr[i]));
	}
	//������ָ�������ָ���б�
	do_changeTgList();
}



//����ָ���б�
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
 		//�ж����������Ƿ���ֵ
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
	alert("��ȡָ�����е�ָ�����");
}


//���ָ��ɾ���¼�

function btnAction_delSelTarget(){
var targetResult = $("targetResult");
	while(targetResult.selectedIndex != -1){
		do_delSelectedTarget(targetResult.selectedIndex);
	}

}

//���ָ���¼�
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
		//��ӵ�tgIdNameMap
		if(tgIdNameMap.indexOf(value) == -1){
			tgIdNameMap.put(value,name);
		}
	}
	else{
	 //����������ɾ��
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
		//ɾ��ά��
		var dimResult = $("dimResult");
		  dimResult.options.length = 0 ;
		  selectedDimList.items.length = 0 ;
		  //ɾ��ָ������ָ��
		  var targetResult = $("targetResult");
		   targetResult.options.length = 0 ;
		  selectedTgList.items.length = 0 ;

}

/** ��ͼ�������� **/
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


/** �Ҽ�����¼�.....  **/
function refer(selectAllKey){
var node = dimTree.getSelected();
if(node==null){
		alert("�������ڵ�,�޷�ѡ��");
		return;
	}	
	var nodeinfo = getNodeObjInfo(node);
	var  nodeid=nodeinfo.get("nodeid");
	var nodelevel=nodeinfo.get("levelname");	
	if(nodeid==null){
		alert("�������ڵ�,�޷�ѡ��");
		return;
	}	
	dimTreeNode_RightClickAction(nodeid,nodelevel,selectAllKey);
}

function dimTreeNode_RightClickAction(nodeid , nodelevel,selectAllKey){
	
	var node = dimTree.getSelected();
	//���ø�ѡ��仯
	//changeCbxChecked(node);
	var nodeinfo = getNodeObjInfo(node);	
	var dimResult = $("dimResult");
    var value = nodeinfo.get("treeModelId")+"_"+nodeinfo.get("nodeid");
    
    var flag=isSelectAll();
		if(!flag||selectAllKey!=selectedDimList.get(0).get("levelname")){
		 clearDimAndTarget();
		}
		
    	//�ж��Ƿ���ͬһ�ȼ�
		if(selectedDimList.size() > 0){
			var treemodelid = nodeinfo.get("treeModelId");
			for(var k = 0 ; k<selectedDimList.size() ; k++){
				if(treemodelid == selectedDimList.get(k).get("treeModelId")){		  
					if(nodeinfo.get("levelname") != selectedDimList.get(k).get("levelnameOld")){
						alert("����ͬһ�ȼ��Ľڵ㣬�޷���ӵ�ѡ�����!");						
						return ;
					}else{
						break ;
					}
				}
			}
		}
		
		 nodeinfo.put("levelnameOld",nodeinfo.get("levelname"));//֮ǰ�Ľ������
	      var  nodeLevelReflect=getSelectAllValue(nodeinfo.get("levelname")); //��ǰ���Ҫ�������нڵ�� �ڵ�����
	      if(nodeLevelReflect=="badNode"){
	        alert("�˽ڵ����������ݿ�ڵ������޷�ƥ��,�޷�ѡ��˽ڵ�����������ڵ�");
	        return;
	      }
	    nodeinfo.put("selectAllFlag",nodeLevelReflect);
	      nodeinfo.put("levelname",selectAllKey);
	      
	      
    	var dimTreeList = $("dimTreeList");
	    var text1 = dimTreeList.options[dimTreeList.selectedIndex].text;
	    var text2 = node.getText();
	    var text = text1+"->"+text2+"->����"+selectAllKey+"�ڵ�";
	    var option = new Option(text,value);
	    dimResult.add(option);
	    option.selected = true ;
	    selectedDimList.add(nodeinfo);
	    //��������ָ����
	    showTgbox();
		do_changeTgGroup();

    }

//�ж��Ƿ���ѡ�����е��¼� 
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
		//��ȡ��ǰ����Ҽ����º��λ�ã��ݴ˶���˵���ʾ��λ��
		var rightedge = document.body.clientWidth-event.clientX;
		var bottomedge = document.body.clientHeight-event.clientY;

		//��������λ�õ������ұߵĿռ�С�ڲ˵��Ŀ�ȣ��Ͷ�λ�˵��������꣨Left��Ϊ��ǰ���λ������һ���˵����
		if (rightedge <ie5menu.offsetWidth)
			ie5menu.style.left = document.body.scrollLeft + event.clientX - ie5menu.offsetWidth;
		else
		//���򣬾Ͷ�λ�˵���������Ϊ��ǰ���λ��
			ie5menu.style.left = document.body.scrollLeft + event.clientX;

		//��������λ�õ������±ߵĿռ�С�ڲ˵��ĸ߶ȣ��Ͷ�λ�˵��������꣨Top��Ϊ��ǰ���λ������һ���˵��߶�
		if (bottomedge <ie5menu.offsetHeight)
			ie5menu.style.top = document.body.scrollTop + event.clientY - ie5menu.offsetHeight;
		else
		//���򣬾Ͷ�λ�˵���������Ϊ��ǰ���λ��
			ie5menu.style.top = document.body.scrollTop + event.clientY;


     	//�����Ҽ������˵�����ʾ
		setRightClickMenu();
		//���ò˵��ɼ�
		ie5menu.style.visibility = "visible";
		return false;
	}
	function hidemenuie5() {
		//���ز˵�  ����visibilityΪhidden��OK��
		ie5menu.style.visibility = "hidden";
	}

	function highlightie5() {
		//��������꾭���Ĳ˵�����Ŀ
		//�����꾭���Ķ�����menuitems�����������ñ���ɫ��ǰ��ɫ
		//event.srcElement.className��ʾ�¼����Զ�������ƣ����������ж����ֵ�������Ҫ��
		if (event.srcElement.className == "menuitems") {
			event.srcElement.style.backgroundColor = "highlight";
			event.srcElement.style.color = "white";

		//��������Ϣ��ʾ��״̬��
		//event.srcElement.url��ʾ�¼����Զ����ʾ������URL
		if (display_url)
			window.status = event.srcElement.url;
		}
}

	function lowlightie5() {
		//�ָ��˵�����Ŀ��������ʾ

		if (event.srcElement.className == "menuitems") {
			event.srcElement.style.backgroundColor = "";
			event.srcElement.style.color = "black";
			window.status = "";
		}
	}

	//�Ҽ������˵�������ת
	function jumptoie5() {
		//ת���µ�����λ��
		var seltext=window.document.selection.createRange().text

		if (event.srcElement.className == "menuitems") {
		//������ڴ����ӵ�Ŀ�괰�ڣ������Ǹ������д�����
			if (event.srcElement.getAttribute("target") != null){
				window.open(event.srcElement.url, event.srcElement.getAttribute("target"));
			}
			else
			//�����ڵ�ǰ���ڴ�����
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