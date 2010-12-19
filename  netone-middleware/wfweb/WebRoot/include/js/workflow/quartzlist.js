function changeMonth(){
    var startYear=document.getElementById("startYear").value;
    var startMonth=document.getElementById("startMonth").value;
    var length=document.getElementById("startDay").options.length;
    if(startMonth=="*"){
        for(var i=length;i>0;i--){
            document.getElementById("startDay").options.remove(i);
        }
        for(var i=0;i<28;i++){
            var op=new Option(i+1,i+1);
            op.onchange="selectDay()";
            document.getElementById("startDay").options.add(op);
        }
    }else{
        var numOfDay = (new Date(startYear,startMonth,0)).getDate();
        for(var i=length;i>0;i--){
            document.getElementById("startDay").options.remove(i);
        }
        for(var i=0;i<numOfDay;i++){
            var op=new Option(i+1,i+1);
            op.onchange="selectDay()";
            document.getElementById("startDay").options.add(op);
        }
    }
}
function chooseByDay(){
    document.getElementById("selectDaySpan").style.display="";
    document.getElementById("selectWeekSpan").style.display="none";
    document.getElementById("chooseMode").value="chooseByDay";
}
function chooseByWeek(){
    document.getElementById("selectDaySpan").style.display="none";
    document.getElementById("selectWeekSpan").style.display="";
    document.getElementById("chooseMode").value="chooseByWeek";
}
function createExtendAttribute(){
    if(document.getElementById("startYear").value==""){
        document.getElementById("startYear").value="0";
    }
    var extendAttribute=document.getElementById("startTime").value+"@"
                       +document.getElementById("chooseMode").value+"@"
                       +parseInt(document.getElementById("startYear").value)+"@"
                       +document.getElementById("startMonth").value+"@"
                       +document.getElementById("startDay").value+"@"
                       +document.getElementById("startNumWeek").value+"@"
                       +document.getElementById("startWeek").value+"@"
                       +document.getElementById("startHour").value+"@"
                       +document.getElementById("startMinute").value+"@"
                       +document.getElementById("startSecond").value+"@"
                       +document.getElementById("stopTime").value;
    document.getElementById("actionurl").value=extendAttribute;
}
function initPage(){
    var extendAttribute=document.getElementById("actionurl").value;
    var extendArray=extendAttribute.split("@");
    document.getElementById("startTime").value=extendArray[0];
    if(extendArray[1]=="chooseByDay"){
        document.form1.chooseByRadio[0].checked="checked";
        chooseByDay();
    }
    if(extendArray[1]=="chooseByWeek"){
        document.form1.chooseByRadio[1].checked="checked";
        chooseByWeek();
    }
    document.getElementById("startYear").value=extendArray[2];
    document.getElementById("startMonth").value=extendArray[3];
    changeMonth();
    document.getElementById("startDay").value=extendArray[4];
    document.getElementById("startNumWeek").value=extendArray[5];
    document.getElementById("startWeek").value=extendArray[6];
    document.getElementById("startHour").value=extendArray[7];
    document.getElementById("startMinute").value=extendArray[8];
    document.getElementById("startSecond").value=extendArray[9];
    document.getElementById("stopTime").value=extendArray[10];
}
function startNow(actionurl,name){
    window.open("PagelistStartSvl?actionurl="+actionurl+"&name="+name);
}