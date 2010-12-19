function createExtendAttribute(){
    var extendAttribute=document.getElementById("startTime").value+"@"
                       +"chooseMode@"
                       +document.getElementById("cronSecond").value+" "
                       +document.getElementById("cronMinute").value+" "
                       +document.getElementById("cronHour").value+" "
                       +document.getElementById("cronDay").value+" "
                       +document.getElementById("cronMonth").value+" "
                       +document.getElementById("cronWeek").value+" "
                       +document.getElementById("cronYear").value+"@"
                       +document.getElementById("stopTime").value;
    document.getElementById("actionurl").value=extendAttribute;
}
function initPage(){
    var extendAttribute=document.getElementById("actionurl").value;
    var extendArray=extendAttribute.split("@");
    document.getElementById("startTime").value=extendArray[0];
    document.getElementById("cron").value=extendArray[2];
    document.getElementById("stopTime").value=extendArray[3];
}

function addmark(key){
	var recent=document.getElementById("recent").value;

	if(recent!=null&&recent!=''){
		document.getElementById(recent).value+=key;
	}else{
	   alert('请选择一个焦点!');
	}

}

function startNow(actionurl,name){
    window.open("PagelistStartSvl?actionurl="+actionurl+"&name="+name);
}
function pause(actionurl){
    window.open("PagelistPauseSvl?actionurl="+actionurl);
}
function resume(actionurl){
    window.open("PagelistResumeSvl?actionurl="+actionurl);
}
function shutdown(actionurl){
    window.open("PagelistShutdownSvl?actionurl="+actionurl);
}