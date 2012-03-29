var xmlblank1='	';
var xmlblank2=' 	';
var xmlblank3=' 		';
var xmlblank4=' 			';
var xmlblank5=' 				';
var xmlblank6=' 					';
var xmlblank7=' 						';

var offsetX,offsetY;
var selectActionObj=null;//被选种的活动对象
var menuAction = null;//在那活动对象弹出的菜单
var menuLine = null;//在那条线对象弹出的菜单
var menuIcon = null;//在那图片对象弹出的菜单

//创建折线时所需的参数///////////
var createLineObj = null;//创建轨迹线对象
var createActivityObj = null;//创建活动对象对象
var lineStartName = null;//线开始端的名字
var lineEndName = null;//线结束端的名字
var fixX = null;//固定heightPoint 的纵坐标
var iconObj = null;//开始跟结束的图片对象
//////////////////////////////////

//创建直线时所需的参数///////////////////
var beenLineObj = null;
var beenLineStartName = null;
var beenLineEndName = null;
///////////////////////////////////

//活动的对象//////////////////////
var trackNextActionName = null;
var trackForwardActionName = null;
/////////////////////////////////
var actionTrueId = "";
var lineTrueId = "";
var forwardTrackAction = null;//上次点击的活动；
var createLineType = null;
var off = 47;
var offx = 40;

var activitycount=0; //活动总数管理
var activitymaxcount=0; //活动最大个数管理
var initmaxcount="false";// 该变量是开关作用,用于第一次初始化activitymaxcount时使用,一旦初始化后为true;
var maxactivitycount=20;

var othercount=0;//其他元素总数管理

var updateNow="false";

var objectWidth = 0;
var objectHeight = 0;



//获得所有的流程对象
var trackHandle = {
   action :{length:0},
   lineStr :{length:0},
   line :{length:0}
}
//定义一个点
function point(x,y){
this.x = x;
this.y = y;
}
//遍历所有的轨迹对象
function lineItem(){
 try{
	 var lineobjs  = document.getElementsByName("lineId");
	 var length = lineobjs.length;
	 trackHandle.line.length = 0;
	 for(var i=0;i<length;i++){
	     var lineobj = lineobjs[i];
	     var name = lineobj.name;
		 trackHandle.line[name] = trackHandle.line[trackHandle.line.length] = lineobj;
	     trackHandle.line.length++;
	   }

 }catch(err){
      alert(err+" 1");
 }
}
//获得流程对象的属性
function TrackAttribute(trackObj){ 
   this.id = trackObj.id;
   this.name = trackObj.name;
   this.style = trackObj.style;
   this.tracklink = trackObj.tracklink;
   this.forward = trackObj.forward;
   this.actionType = trackObj.actionType;
   this.deadline = trackObj.deadline;
   this.actionTrueId = trackObj.actionTrueId;
   this.actionExtendAttribute = trackObj.actionExtendAttribute;
   this.forwardCondition = trackObj.forwardCondition;
   this.afterCondition = trackObj.afterCondition;
   this.isLink = trackObj.isLink;
   this.actionName =trackObj.children[0].children[0].children[0].children[0].children[0].value;


}
//遍历所有的活动对象
function TrackItem(){
try{
   var actionCode;
   var actionObj  = document.getElementsByName("trackAction");
   var startIconObj  = document.getElementsByName("start");
   var endIconObj  = document.getElementsByName("end");
   var length =actionObj.length;
   var startlength = startIconObj.length;
   var endlength = endIconObj.length;
   trackHandle.action.length=0;
   for(var i=0;i<length;i++){
        trackObj = actionObj[i];
        actionCode = trackObj.name;
		trackHandle.action[actionCode] = trackHandle.action[i] = new TrackAttribute(trackObj);
        trackHandle.action.length++;
   }
   for(var i=0;i<startlength;i++){
        trackObj = startIconObj[i];
        actionCode = trackObj.name;
		trackHandle.action[actionCode] = trackHandle.action[trackHandle.action.length] = trackObj;
        trackHandle.action.length++;
   }
   for(var i=0;i<endlength;i++){
        trackObj = endIconObj[i];
        actionCode = trackObj.name;
		trackHandle.action[actionCode] = trackHandle.action[trackHandle.action.length] = trackObj;
        trackHandle.action.length++;
   }

	  }catch(err){
	     alert(err);
	  }
}

/////随着活动移动线条（包括直线和破折线）/////
function dynamicLine(){
    if(selectActionObj!=null){
       var name = selectActionObj.name;
       var tracklink = selectActionObj.tracklink;
       var forward = selectActionObj.forward;

	   /*  获得下一点 */
		if(tracklink!="null"){
			 var link = tracklink.split(",");
			 var linkLength = link.length-1;
			 for(var k=0;k<linkLength;k++){
			 	var lineType = (link[k].split("&"))[1];
                var nextlinkName =name+"->"+(link[k].split("&"))[0];

				if(lineType=="ZLine"){
					//new moveTrack(nextlinkName,link[k],"next");
					   (new moveTrack(nextlinkName,link[k],"next")).moveZLine();
				 }
				if(lineType=="beenLine"){
					//new moveTrack(nextlinkName,link[k],"next");
					   (new moveTrack(nextlinkName,link[k],"next")).moveBeenLine();
				 } 
			  }
		 }
			 
		 /*  获得上一点 */
		 if(forward!="null"){
			     var forwardlink = forward.split(",");
				 var forwardlinkLength = forwardlink.length-1;
				 
				 for(var k=0;k<forwardlinkLength;k++){
				  var   forlineName = (forwardlink[k].split("&")[0])+"->"+name;
				  var lineType = forwardlink[k].split("&")[1];

				  if(lineType=="ZLine"){
				     (new moveTrack(forlineName,forwardlink[k],"forward")).moveZLine()
					}
				   if(lineType=="beenLine"){
				     (new moveTrack(forlineName,forwardlink[k],"forward")).moveBeenLine()
					}
				 }
			  }
    } 
}
/*移动轨迹线*/
function moveTrack(lineName,linkName,linkType){
   this.lineName = lineName;//轨迹名称
   this.linkName = linkName;//要连接活动的名称
   this.linkType = linkType;//连接类型（上一个，下一个）
}
/*移动活动两端的直线*/
moveTrack.prototype.moveBeenLine = function(){
   if(this.linkType=="next"){
         toNextBeenLinePoint(this.lineName,this.linkName);
   }
   if(this.linkType=="forward"){
         toForwardBeenLinePoint(this.lineName,this.linkName);
   }
}

/*移动活动两端的折线*/
moveTrack.prototype.moveZLine = function(){
   if(this.linkType=="next"){
         toNextPoint(this.lineName,this.linkName);
   }
   if(this.linkType=="forward"){
         toForwardPoint(this.lineName,this.linkName);
   }
}


//该活动连接下一个活动的点(折线移动是的下一个接点)
function toNextPoint(nextlinkName,nextActName){

                  var nextName = (nextActName.split("&"))[0];
				  var lineType = (nextActName.split("&"))[1];
				  var lineObj = trackHandle.line[nextlinkName];
                  if((selectActionObj!=null)&&(lineObj!=null)){
				   var naxtActObj =  trackHandle.action[nextName];
                   var startPoint = selectActionObj.style.left+","+(parseint(selectActionObj.style.top)-off)+"";
				   var centerPoint = (parseint(selectActionObj.style.left)+110)+"px,"+(parseint(selectActionObj.style.top)-off)+"";
				   var endPoint = (parseint(selectActionObj.style.left)+110)+"px,"+(parseint(naxtActObj.style.top)-off)+"";

			       lineObj.children[0].from = startPoint;
				   lineObj.children[0].to = centerPoint
				   lineObj.children[1].from = centerPoint;
			       
				   lineObj.children[1].to= endPoint;
				  
				   lineObj.children[2].from= endPoint;
					}					
}
//该活动连接上一个活动的点(折线移动的上一个接点)
function toForwardPoint(forlineName,forwardActName){

 var adjustNumy = 0;
 var forwardName = (forwardActName.split("&"))[0];
 var type = (forwardActName.split("&"))[1];
  var lineObj = trackHandle.line[forlineName];

  if((selectActionObj!=null)&&(lineObj!=null)){
			 var forwardActObj = trackHandle.action[forwardName];
			 var  startPoint = (parseint(forwardActObj.style.left)+110)+"px,"+(parseint(selectActionObj.style.top)-off)+"";
			  var endPoint = (parseint(selectActionObj.style.left)-10)+","+(parseint(selectActionObj.style.top)-off)+"px";
				   lineObj.children[1].to= startPoint;
				  
				   lineObj.children[2].from= startPoint;
				   lineObj.children[2].to= endPoint;
				   }
}

function toNextBeenLinePoint(nextlinkName,nextActName){

 	var adjustNumx = 0;
 	var adjustNumy = 0;
    var nextName = (nextActName.split("&"))[0];
	var nextdObj = trackHandle.action[nextName];
	var fobjectHeight;
    var fx,fy,tx,ty; 
    var fobjectWidth = parseint(selectActionObj.style.width)/2+2;
	
    if(selectActionObj.style.height == "27px"){
		adjustNumy = 27;
	}else
		adjustNumy = 12;
	var fobjectHeight = parseint(selectActionObj.style.height)/2+adjustNumy;
		//alert(nextdObj.style.height +"   " +selectActionObj.style.height);
    objectWidth = parseint(nextdObj.style.width)/2;
    objectHeight = parseint(nextdObj.style.height)/2;
    tx = parseint(nextdObj.style.left)+parseint(nextdObj.style.width)/2;
    ty = parseint(nextdObj.style.top)-parseint(nextdObj.style.height)/2
    fx = parseint(selectActionObj.style.left)+objectWidth;
    fy = parseint(selectActionObj.style.top)-objectHeight;
    
    
    
    var naPoint = new  position(fx,fy,tx,ty,objectWidth,objectHeight);
	var lineObj = trackHandle.line[nextlinkName];
                  if((selectActionObj!=null)&&(lineObj!=null)){
                   var startPoint = (parseint(selectActionObj.style.left)+fobjectWidth)+","+(parseint(selectActionObj.style.top)-fobjectHeight)+"";
			       lineObj.children[0].from = startPoint;
				   var endPoint = naPoint.point.x+","+naPoint.point.y;
				   lineObj.children[0].to = endPoint;
				}
  }
  
  
  //该活动连接上一个活动的点(直线移动的上一个接点)
function toForwardBeenLinePoint(forlineName,forwardActName){

 var forwardName = (forwardActName.split("&"))[0];
 var forwardObj = trackHandle.action[forwardName];
 var fx,fy,tx,ty;
 objectWidth = parseint(selectActionObj.style.width)/2;
 objectHeight = parseint(selectActionObj.style.height)/2;
 
 fx = parseint(forwardObj.style.left)+parseint(forwardObj.style.width)/2;
 fy = parseint(forwardObj.style.top)-parseint(forwardObj.style.height)/2;
 tx = parseint(selectActionObj.style.left)+objectWidth;
 ty = parseint(selectActionObj.style.top)-objectHeight;


 var fwPoint = new  position(fx,fy,tx,ty,objectWidth,objectHeight);
 var lineObj = trackHandle.line[forlineName];
      if((selectActionObj!=null)&&(lineObj!=null)){
             var lineObj = trackHandle.line[forlineName];

			 var endPoint = fwPoint.point.x+"px,"+fwPoint.point.y+"px";
			 lineObj.children[0].to= endPoint;
	}
  }

//****************控制移动节点时与该节点相连的线跟着移动  END *************************************//

//******************************通过已经存在的节点画出轨迹   START ******************************/
function staticLine(){
   var length = trackHandle.action.length;
   var fx,fy,tx,ty;
   if(length!=0){
   for(var i=0;i<length;i++){
        var actionObj = trackHandle.action[i];
	    fx = actionObj.style.left;
		fy = actionObj.style.top;
		fp = new point(fx,fy);
        var nextlink = actionObj.tracklink;
	    var link = nextlink.split(",");
		var linkLength = link.length-1;
		if(linkLength!=0&&link!="null"){
		    for(var k=0;k<linkLength;k++){
			    var lineType = (link[k].split("&"))[1];
			    var nextActionObj = trackHandle.action[(link[k].split("&"))[0]];
				var lineName = actionObj.name+"->"+nextActionObj.name;
                tx = nextActionObj.style.left;
				ty = nextActionObj.style.top;
                tp = new point(tx,ty);
				
				if(lineType=="ZLine"){
                  trackHandle.lineStr[trackHandle.lineStr.length++] = 
                 (new trackline(fp,tp,lineName)).createZLine();
				}
				if(lineType=="beenLine"){
                  trackHandle.lineStr[trackHandle.lineStr.length++] = 
                 (new trackline(fp,tp,lineName)).createBeenLine();
				}
			}
		}
	   }
   }  
}

/*画静态轨迹方法*/
function trackline(fp,tp,lineName){
     this.f = parseint(fp.x);
	 this.t = parseint(tp.x);
	 this.lineName = lineName;
	 this.fp = fp;
	 this.tp = tp;
}
/*画静态直线*/
trackline.prototype.createBeenLine = function(){
   
    this.htm = '<div id="lineId" name="'+this.lineName+'">'
			 +'<v:line style="position:absolute; zIndex:100" '
	         +'from="'+(parseint(this.fp.x)+offx)+','+(parseint(this.fp.y)-off)+'px" to="'+(this.tp.x)+','+(parseint(this.tp.y)-off)+'px">'
    		 +'<v:stroke EndArrow="Classic" Color="#FF0066"/></v:line>'
             +'</div>\n';
			  return this.htm;
}
/*画静态折线*/
trackline.prototype.createZLine = function() {
        this.htm = '<div id="lineId" name="'+this.lineName+'">'
             +'<v:line style="position:absolute; zIndex:100;" strokecolor="red" '
	         +'from="'+this.f+'px,'+(parseint(this.fp.y)-off)+'px" to="'+(this.f+100)+'px,'+(parseint(this.fp.y)-off)+'px">'
    		 +'</v:line>'
			 +'<v:line style="position:absolute; zIndex:100"  strokecolor="red" '
	         +'from="'+(this.f+100)+'px,'+(parseint(this.fp.y)-off)+'px" to="'+(this.f+100)+'px,'+(parseint(this.tp.y)-off)+'px">'
    		 +'</v:line>'
			 +'<v:line style="position:absolute; zIndex:100" '
	         +'from="'+(this.f+100)+'px,'+(parseint(this.tp.y)-off)+'px" to="'+this.t+'px,'+(parseint(this.tp.y)-off)+'px">'
    		 +'<v:stroke EndArrow="Classic" Color="#FF0066"/></v:line>'
             +'</div>\n';
			  return this.htm;
}
function lineTostring(){
    var lg =  trackHandle.lineStr.length;
	this.htm = "";
	for(var i=0;i<lg;i++){
    this.htm += trackHandle.lineStr[i];
	}
	return this.htm;
}


//**********************通过已经存在的节点画出轨迹   END **************************************//

//******************************创建新的流程   START*************************************************

/*------流程轨迹元素工厂 ----------------*/
function trackFactory(sort){

if(initmaxcount=="false"){
	initmaxcount="true";
	var actmaxnumber = document.getElementById("activitymaxcount");
	var actnumber = document.getElementById("activitycount");
	var othermaxnumber= document.getElementById("othercount");
	if(actmaxnumber!="null"){
	    updateNow="true";
	    if(actmaxnumber!=null){
  			activitymaxcount = actmaxnumber.value;
  		}
  		if(actnumber!=null){
  			activitycount=actnumber.value;
  		}
  		if(othermaxnumber!=null){
  			othercount=othermaxnumber.value;
  		}
	}
  }

  createLineType = sort;

  /*创建折线*/
  if(sort=="ZLine"){
   	this.createTrackZLine();

  } else if(sort=="beenLine"){//创建直线
   	this.createTrackBeenLine();

  }else {

  /*创建节点*/
  if(sort=="trackAction"){
  this.createTrackAction();
 
  }
  /*创建子流程*/
  if(sort=="subWorkFlow"){

	this.createSubWorkFlow();

  }
  /*创建空活动*/
  if(sort=="route"){

    this.createTrackRoute();

  }
  /*创建转折点*/
  if(sort=="turningpoint"){

    this.createTurningPoint();

  }
  /*创建空活动分支*/
  if(sort=="routesplit"){

    this.createTrackRoute1();

  }
    /*创建空活动汇集*/
  if(sort=="routejoin"){
    this.createTrackRoute2();
  }
  /*创建开始图片*/
  if(sort=="start"){
  	this.createStartIcon();
  }
  /*创建结束图片*/
  if(sort=="end"){
  	this.createEndIcon();
  }
  }

}
/*-------创建折线------------------------*/
trackFactory.prototype.createTrackZLine = function(){
  var staticline = document.createElement("div");
      staticline.innerHTML = '<div id="lineId" start="null" end="null" condition="" name="" trueName ="" extendContent="" lineTrueId=""; depict="" type="ZLine"> '
      +'<v:line id="" style="Z-INDEX:0;POSITION:absolute;cursor:hand;" from="500px,60px" to="530px,60px" ' +'strokecolor="blue"></v:line> '
      +'<v:line style="Z-INDEX:0;POSITION:absolute;cursor:hand;" from="530px,60px" to="530px,30px" ' +'strokecolor="blue"></v:line> '
      +'<v:line style="Z-INDEX:0;POSITION:absolute;cursor:hand;" from="530px,30px" to="560px,30px" ' +'strokecolor="blue"><v:Stroke endarrow=\'classic\'/></v:line>'
      +'</div>';
      document.body.appendChild(staticline);
      createLineObj =  staticline.children[0];
}
 /*-------创建直线------------------------*/
trackFactory.prototype.createTrackBeenLine = function(){

   var beenLine = document.createElement("div");
       beenLine.innerHTML ='<div id="lineId" start="null" end="null"  condition="" name="" trueName ="" extendContent="" lineTrueId="" depict="" type="beenLine" >'
       +'<v:line id="" style="Z-INDEX:0;POSITION:absolute;cursor:hand;" from="335px,10px" to="345px,0px"' 
	    +'strokecolor="blue"><v:Stroke endarrow=\'classic\'/></v:line>'
       +'</div>';
       document.body.appendChild(beenLine);
       beenLineObj = beenLine.children[0];
       
}
/*--------创建普通活动------------------------*/
trackFactory.prototype.createTrackAction = function(){
	this._createTrack("节点","tools","#000000","activity");	
}

/*--------创建子流程------------------------*/
trackFactory.prototype.createSubWorkFlow = function(){
	this._createTrack("子流程","subflow","#0000EE","subflow");
}
/*创建路由*/
trackFactory.prototype.createTrackRoute = function(){
	this._createTrackRoute("路由","route","#000000","route");
		
}

/*创建转折点*/
trackFactory.prototype.createTurningPoint = function(){
	this._createTurningPoint("转折点","turningpoint","#000000","turningpoint");
		
}

/*创建路由分支*/
trackFactory.prototype.createTrackRoute1 = function(){
	this._createTrackRoute("分支","routesplit","#000000","route");
		
}

/*创建路由汇集*/
trackFactory.prototype.createTrackRoute2 = function(){
	this._createTrackRoute("汇集","routejoin","#000000","route");
		
}
//创建节点///
trackFactory.prototype._createTrack = function(dispname,type,bordercolor,activityColor){
   	if(activitycount==maxactivitycount){
   		alert("节点个数超出20个");
   		return;
   }
   activitycount++;
   activitymaxcount++;
   var newAction = document.createElement("span");
   dispname=dispname+activitymaxcount;
   var id=new Date();
   var actName = "trackAction"+id.getTime();

   newAction.innerHTML ="<div id='trackAction' name='"+actName+"'isLink='false' "
   +" forward='null' tracklink='null' actionType='"+type+ "' deadline='' actionTrueId='"+actName+"' actionExtendAttribute =''  forwardCondition='null' afterCondition ='null' style='position:absolute;visibility: visible; padding:2px; height:45px; width:110px; left:200px; top:50px;zIndex:10'>"
   +"<table id = 'table2' cellspacing='0' cellpadding='0' width='100%' height='100%' " +"style='cursor:hand;font-size:12px;zIndex:50' oncontextmenu='return false' >"
   +"<tr><td id ='ping' name = 'track' align='center'  >"
   +"<input id='work"+actName+"' name='textarea' type='button' value='"+dispname+"' class='"+activityColor+"'>"
   +"</td></tr></table></div>";
   document.body.appendChild(newAction);
   var length = trackHandle.action.length;
   trackHandle.action[actName] = trackHandle.action[length]=new TrackAttribute(newAction.children[0]);
   trackHandle.action.length++;

    document.getElementById("defineActionTrueId").value += actName+"&";
	createActivityObj = newAction.children[0];
}


//创建空节点
trackFactory.prototype._createTrackRoute = function(dispname,type,bordercolor,activityColor){
   othercount++;
   var newAction = document.createElement("span");
   dispname=dispname;
   var time = new Date();
   var actName = type+time.getTime();

   newAction.innerHTML ="<div id='trackAction' name='"+actName+"'isLink='false' "
   +" forward='null' tracklink='null' actionType='"+type+ "' deadline='' actionTrueId='"+actName+"' actionExtendAttribute =''  forwardCondition='null' afterCondition ='null' style='position:absolute;visibility: visible; padding:2px; height:45px; width:110px; left:200px; top:50px;zIndex:10'>"
   +"<table id = 'table2' cellspacing='0' cellpadding='0' width='100%' height='100%' " +"style='cursor:hand;font-size:12px;zIndex:50' oncontextmenu='return false' >"
   +"<tr><td id ='ping' name = 'track' align='center'  >"
   +"<input id='work"+actName+"' name='textarea' type='button' value='"+dispname+"' class='"+activityColor+"'>"
   +"</td></tr></table></div>";
   document.body.appendChild(newAction);
   var length = trackHandle.action.length;
   trackHandle.action[actName] = trackHandle.action[length]=new TrackAttribute(newAction.children[0]);
   trackHandle.action.length++;

    document.getElementById("defineActionTrueId").value += actName+"&";
	createActivityObj = newAction.children[0];
}

//创建转折点
trackFactory.prototype._createTurningPoint = function(dispname,type,bordercolor,activityColor){
   othercount++;
   var newAction = document.createElement("span");
   var time = new Date();
   var actName = type+time.getTime();
   newAction.innerHTML ="<div id='trackAction' name='"+actName+"'isLink='false' "
   +" forward='null' tracklink='null' actionType='"+type+ "' deadline='' actionTrueId='"+actName+"' actionExtendAttribute =''  forwardCondition='null' afterCondition ='null' style='position:absolute;visibility: visible; padding:2px; height:27px; width:27px; left:100px; top:50px;zIndex:10'>"
   +"<table id = 'table3' cellspacing='0' cellpadding='0' width='100%' height='100%' " +"style='cursor:hand;font-size:12px;zIndex:50' oncontextmenu='return false' >"
   +"<tr><td id ='ping' name = 'turningpoint' align='center'  >"
   +"<input id='work"+actName+"' name='textarea' type='button' value='' class='"+activityColor+"'>"
   +"</td></tr></table></div>";
   document.body.appendChild(newAction);
   var length = trackHandle.action.length;
   trackHandle.action[actName] = trackHandle.action[length]=new TrackAttribute(newAction.children[0]);
   trackHandle.action.length++;

    document.getElementById("defineActionTrueId").value += actName+"&";
	createActivityObj = newAction.children[0];
}

	var initRelationData = "busstype,业务类型,,,,;busstip,业务提示,,,,;bussid,业务参数,,,,;bussurl,业务地址,,,,;customer,参与者,,,,;worklisttitle,待办提示,,,,;";
//创建轨迹表格//
function submitInitField(){
	this.relationDataObj = document.getElementById("relationData");
	if(this.relationDataObj.value == "" || this.relationDataObj.value == null )
		this.relationDataObj.value = "busstype,业务类型,,,,;busstip,业务提示,,,,;bussid,业务参数,,,,;bussurl,业务地址,,,,;customer,参与者,,,,;worklisttitle,待办提示,,,,;";
}

/*-------创建开始图片-----------*/
trackFactory.prototype.createStartIcon = function(){
 
   othercount++;
   var newIcon = document.createElement("span");
   newIcon.innerHTML ='<v:image id="start" name="start_'+(othercount)+'"  isLink="false"  forward="null" tracklink="null" style="POSITION:absolute;Z-INDEX:1;LEFT:20px;TOP:50px;width:39;height:39;" fillcolor="#007FFF">'
                +'<v:Textbox name ="textbox" class=startIcon print="t" inset="1pt,1pt,1pt,1pt"></v:Textbox>'
				+"<input id=\"work2start_"+(othercount)+"\" type=\"hidden\">"
                +'</v:image>';
   document.body.appendChild(newIcon);
   createActivityObj = newIcon.children[0];
}
/*-------创建结束图片-----------*/
trackFactory.prototype.createEndIcon = function(){
	
   othercount++;
   var newIcon = document.createElement("span");
   newIcon.innerHTML ='<v:image id="end" name="end_'+(othercount)+'" isLink="false"  forward="null" tracklink="null" style="POSITION:absolute;Z-INDEX:1;LEFT:50px;TOP:50px;width:39;height:39;" fillcolor="#FFFF55">'
                +'<v:Textbox name ="textbox" class=endIcon print="t" inset="1pt,1pt,1pt,1pt"></v:Textbox>'
                +"<input id=\"work2end_"+(othercount)+"\" type=\"hidden\">"
				+'</v:image>';
   document.body.appendChild(newIcon);
   createActivityObj = newIcon.children[0];
}
/*----------控制刚创建的线跟着鼠标走-------*/
function moveLine(){
	
    this.x = event.clientX;
    this.y = event.clientY;

    if( this.x<0){
    	 this.x=5;
    }
   	if( this.y<60){
    	 this.y=62;
    }
	if(createLineObj!=null){
       this.moveCreateZLine();
	  }
    if(beenLineObj!=null){
       this.moveCreateBeenLine();
      }
 
}

 /*     ------------控制折线跟着鼠标走       START---------------      */
moveLine.prototype.moveCreateZLine = function(){
	var startPoint = (this.x-45)+"px,"+(this.y-40)+"px";
	var centerPoint = (this.x-30)+"px,"+(this.y-40)+"px";
	var heightPoint =  (this.x-30)+"px,"+(this.y-60)+"px";
	var endPoint =  (this.x-15)+"px,"+(this.y-60)+"px";
	 if(createLineObj.start=="null"){
	 createLineObj.children[0].from = startPoint;
     createLineObj.children[0].to = centerPoint;
	 createLineObj.children[1].from = centerPoint;
	 }else if(createLineObj.start == "start"){
       heightPoint =  (fixX)+"px,"+(this.y-60)+"px";   
	 }
	 if(createLineObj.end=="null"){
	 createLineObj.children[1].to = heightPoint;
	 createLineObj.children[2].from = heightPoint;
	 createLineObj.children[2].to = endPoint;
	 }
}
   	/*     ------------控制直线跟着鼠标走     ---------------------      */

 	/*     ------------控制活动跟着鼠标走     ---------------------      */
function moveActivity(){

    var left = event.clientX;
    var top = event.clientY;
   if( left<0){
    	 left=5;
    }
   if( top<60){
    	 top=62;
    }
    if(createActivityObj!=null){
      createActivityObj.style.left = left-20;
	  createActivityObj.style.top = top-10;
	}
}
/*     ------------控制活动跟着鼠标走     ---------------------      */


moveLine.prototype.moveCreateBeenLine=function(){

   var startPoint = (this.x-20)+"px,"+(this.y-40)+"px";
   var endPoint =  (this.x+5)+"px,"+(this.y-60)+"px";
   if(beenLineObj.start=="null"){
   		beenLineObj.children[0].from = startPoint;
   }
   if(beenLineObj.end=="null"){
   		beenLineObj.children[0].to = endPoint;
   }
}

/*        -----------固定刚创建的线---------------------------------*/
function fixLine(){

if(iconObj!=null){
  selectActionObj=iconObj;
}
var obj = window.event.srcElement; 
    if((selectActionObj==null)&&(iconObj==null)&&(createLineObj!=null)&&(obj.parentElement.name!="ZLine")){
        createLineObj.parentElement.removeChild(createLineObj);
        createLineObj = null;
		return;
     }else if((selectActionObj!=null)&&(createLineObj!=null)){
             this.fixZLine();
     }
    if((selectActionObj==null)&&(iconObj==null)&&(beenLineObj!=null)&&(obj.parentElement.name!="beenLine")){
            
             beenLineObj.parentElement.removeChild(beenLineObj);
             beenLineObj = null;
			 return;
     }else if((selectActionObj!=null)&&(beenLineObj!=null)){//在节点上绘制直线的事件
             
             this.fixBeenLine();

	 }
}
  
   　/*     ------------控制折线固定　---------------      */
fixLine.prototype.fixZLine = function(){
       var x = parseint(selectActionObj.style.left);
	   var y = parseint(selectActionObj.style.top);
	   if((createLineObj.start=="null")&&(createLineObj.end=="null")){
	       var able = (new judgeNode(selectActionObj,"")).nextNodeIsEnd();
	       if(!able){
	       createLineObj.parentElement.removeChild(createLineObj);
           createLineObj = null;
		   selectActionObj=null;
		   release();
		   alert("流程中缺少开始或者结束点");
		   return;
	      }
	      if(selectActionObj.id=="end"||selectActionObj.isLink=="true"){
		   createLineObj.parentElement.removeChild(createLineObj);
           createLineObj = null;
		   selectActionObj=null;
		   release();
		   alert("结束点不允许有一个以上的连接")
		   return;
		  }
	      var startPoint = x+"px,"+(y-off)+"px";
		  var centerPoint =(x+110)+"px,"+(y-off)+"px";
		  var endPoint = (x+70)+"px,"+(event.clientY-off)+"px";
		  createLineObj.children[0].from = startPoint;
		  createLineObj.children[0].to = centerPoint;
		  createLineObj.children[1].from = centerPoint;
		  createLineObj.start = "start";
          fixX = x+70;
		  lineStartName = selectActionObj.name;
		  forwardTrackAction = selectActionObj;
		  trackHandle.action[lineStartName]=selectActionObj;
		  selectActionObj = null;	
	   }
	   if((createLineObj.end=="null")&&(selectActionObj!=null)){
	      if(forwardTrackAction.name==selectActionObj.name){
	       createLineObj.parentElement.removeChild(createLineObj);
           createLineObj = null;
		   release();
		   return;
	      }
	      var able = (new judgeNode(forwardTrackAction,selectActionObj)).nextNodeIsEnd();
		  if(!able){
		   createLineObj.parentElement.removeChild(createLineObj);
           createLineObj = null;
		   release();
		   alert("流程中出现断路");
		   return;
		  }
	      if(selectActionObj.id=="start"||selectActionObj.isLink=="true"){
		   createLineObj.parentElement.removeChild(createLineObj);
           createLineObj = null;
		   release();
		   alert("此操作无法进行！ 11")
		   return;
		  }else if(forwardTrackAction.id=="start"&&(selectActionObj.forward!="null")&&(selectActionObj.forward!="")){
		   forwardTrackAction.isLink = "false";
		   createLineObj.parentElement.removeChild(createLineObj);
           createLineObj = null;
		   release();
		   alert("此操作无法进行!! 10");
		   return;
		  }
	      var startPoint = (fixX)+"px,"+(y-off)+"px";
		  var centerPoint =(x-10)+"px,"+(y-off)+"px";
		  createLineObj.children[1].to = startPoint;
		  createLineObj.children[2].from = startPoint;
		  createLineObj.children[2].to = centerPoint;
		  createLineObj.end = "end";
		  createLineObj.start = "null";
		  createLineObj.end = "null";
		  if(lineStartName!=selectActionObj.name){
		  createLineObj.name = lineStartName+"->"+selectActionObj.name;
		  createLineObj.lineTrueId = lineStartName+"_"+selectActionObj.name;
           document.getElementById("defineLineTrueId").value += lineStartName+"_"+selectActionObj.name+"&";
		  createLineObj.children[0].id = "work"+lineStartName+"->"+selectActionObj.name;
		  var tracklink = forwardTrackAction.tracklink;
		  if(tracklink=="null"){
		     tracklink = selectActionObj.name+"&"+createLineType+",";
		  }else if(tracklink.indexOf(selectActionObj.name+"&",0)!=-1){
		        createLineObj.parentElement.removeChild(createLineObj);
                createLineObj = null;
		        release();
		        alert("此操作无法进行!!!"); 
			    return;
		      
		  }else {
		      tracklink+=selectActionObj.name+"&"+createLineType+",";
		  }
		  
		  var forwardLink = selectActionObj.forward;
		  if(forwardLink=="null"){
		     forwardLink = forwardTrackAction.name+"&"+createLineType+",";

		  }else if((forwardLink.split("_"))[0]=="start"){
		        createLineObj.parentElement.removeChild(createLineObj);
		        createLineObj = null;
		        release();
		        alert("活动不能有汇集转移!!!"); 
			    return;
		  }else{
		     forwardLink+=forwardTrackAction.name+"&"+createLineType+",";
		  }
		  if(forwardTrackAction.id=="start"){
		       forwardTrackAction.isLink="true";
		  }
		  if(selectActionObj.id=="end"){
		       selectActionObj.isLink="true";
		  }
          selectActionObj.forward =forwardLink;
		  trackHandle.action[selectActionObj.name]=selectActionObj;
		  forwardTrackAction.tracklink = tracklink;
		  trackHandle.line[createLineObj.name] = trackHandle.line[trackHandle.line.length] = createLineObj;
          trackHandle.line.length++;
		  lineStartName = null;
		  fixX = null;
		  createLineObj = null;
		  forwardTrackAction = null;
		  selectActionObj = null;
		  createLineType = null;
		  return;
	   }
	   }
}
    /*     ------------控制直线固定　　---------------      */
fixLine.prototype.fixBeenLine = function(){

		//parseint(forwardTrackAction.style.height)/2
       var x = parseint(selectActionObj.style.left)+parseint(selectActionObj.style.width)/2;
	   var y = parseint(selectActionObj.style.top)-parseint(selectActionObj.style.height)/2;
       if((beenLineObj.start=="null")&&(beenLineObj.end=="null")){
           var able = (new judgeNode(selectActionObj,"")).nextNodeIsEnd();
	       if(!able){
	       beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   selectActionObj=null;
		   release();
		   alert("此操作无法进行！");
		   return;
	      }
	      if(selectActionObj.id=="end"||selectActionObj.isLink=="true"){
		   beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   selectActionObj=null;
		   release();
		   alert("结束点，不允许有传出现");
		   return;
		  }
		  var adjustmentY = 0;
		  if(selectActionObj.style.height == "27px"){
		  	adjustmentY = 28;
		  }else{
		  	adjustmentY = 13;
		  }
	      var  point = (x)+"px,"+(y-adjustmentY)+"px";
	      beenLineObj.children[0].from = point;
          beenLineObj.start = "start";
		  beenLineStartName = selectActionObj.name;
		  forwardTrackAction = selectActionObj;
		  trackHandle.action[selectActionObj.name]=selectActionObj;
		  selectActionObj = null;
		  return;
	   }
	   if((selectActionObj!=null)&&(beenLineObj.end=="null")){
	   if(forwardTrackAction.name==selectActionObj.name){
	       beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   release();
		   return;
	   	}
	      var able = (new judgeNode(forwardTrackAction,selectActionObj)).nextNodeIsEnd();
		  if(!able){
		   beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   release();
		   alert("该活动已经有分支，不能指向结束点");
		   return;
		  }
	      if(selectActionObj.id=="start"||selectActionObj.isLink=="true"){
		   beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   release();
		   alert("开始点，不允许有传入线");
		   return;
		  }else
		  if((forwardTrackAction.id=="start")&&(selectActionObj.forward!="null")&&(selectActionObj.forward!="")){
		   forwardTrackAction.isLink = "false";
		   beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   release();
		   alert("此操作无法进行!!");
		   return;
		  }
		  
		  objectWidth = parseint(selectActionObj.style.width)/2;
	      objectHeight = parseint(selectActionObj.style.height)/2;
	      
	      var fromX = parseint(forwardTrackAction.style.left)+parseint(forwardTrackAction.style.width)/2;
	      var fromY = parseint(forwardTrackAction.style.top)-parseint(forwardTrackAction.style.height)/2;
	      

	  
          var positionPoint = new position(fromX,fromY,x,y,objectWidth,objectHeight);
	      var  point = positionPoint.point.x+"px,"+positionPoint.point.y+"px";
		       beenLineObj.children[0].to = point;
               beenLineObj.end = "end";

		if(beenLineStartName!=selectActionObj.name){
		  beenLineObj.name = beenLineStartName+"->"+selectActionObj.name;
		  beenLineObj.lineTrueId = beenLineStartName+"_"+selectActionObj.name;
		   document.getElementById("defineLineTrueId").value +=beenLineStartName+"_"+selectActionObj.name+"&";
          beenLineObj.children[0].id = "work"+beenLineStartName+"->"+selectActionObj.name;
		  var tracklink = forwardTrackAction.tracklink;
		  if(tracklink=="null"){
		     tracklink = selectActionObj.name+"&"+createLineType+",";
		  }else if(tracklink.indexOf(selectActionObj.name+"&",0)!=-1){
		        beenLineObj.parentElement.removeChild(beenLineObj);
                beenLineObj = null;
		        release();
		        alert("重复线"); 
				return;
		  }else{
		     tracklink+=selectActionObj.name+"&"+createLineType+",";
		  }
		  
		  var forwardLink = selectActionObj.forward;
		  if(forwardLink=="null"){
		     forwardLink = forwardTrackAction.name+"&"+createLineType+",";
		  }else if((forwardLink.split("_"))[0]=="start"){
		        beenLineObj.parentElement.removeChild(beenLineObj);
                beenLineObj = null;
		        release();
		        alert("活动不能有汇集转移!"); 
				return;
		  }else {
		      forwardLink+=forwardTrackAction.name+"&"+createLineType+",";
		  }	 
		  if(forwardTrackAction.id=="start"){
		       forwardTrackAction.isLink="true";
		  }
		  if(selectActionObj.id=="end"){
		       selectActionObj.isLink="true";
		  }
          selectActionObj.forward =forwardLink;
		  trackHandle.action[selectActionObj.name]=selectActionObj;
		  forwardTrackAction.tracklink = tracklink;
		  trackHandle.line[beenLineObj.name] = trackHandle.line[trackHandle.line.length] = beenLineObj;
          trackHandle.line.length++;

		  beenLineObj = null;
		  selectActionObj = null;
		  beenLineStartName = null;
		  forwardTrackAction= null;
		  //lineObj();
		  createLineType = null;
		  }
	   }
}

/*-------判断节点的下一个是否为 end或上一个是否为start---------*/
function judgeNode(fromAction,toAction){
 this.fromAction = fromAction;
 this.toAction = toAction;
}

judgeNode.prototype.nextNodeIsEnd =function (){
  var id = this.fromAction.id;
  if(id!="start"&&id!="end")
  { 
   var name = this.fromAction.name;
   var obj = trackHandle.action[name];
  var tracklink =  obj.tracklink;
  if((tracklink!="null")&&(tracklink!="")&&(this.toAction=="")){
   var num = tracklink.indexOf("_",0);
   var substr = tracklink.substr(0,num);
     if(substr=="end"){
	   return false;
	 }
  }
  }

  if(this.toAction!=""){
    if((this.toAction.id=="end")&&(this.fromAction.tracklink!="null")&&(this.fromAction.tracklink!="")){  
	   return false;
	}
	if((this.fromAction.id=="start")&&(this.toAction.id=="end")){
	 this.fromAction.isLink = "false";
	 this.toAction.isLink = "false";
	 return false;
	}
  }
  return true;
  
}
//******************************创建新的流程   START*************************************************




//*****************************工作流删除事件        START****************************************
function deleteEvent(obj){
  // var obj = window.event.srcElement;
    if(obj=="line"){
	 this.deleteTrack();
    }
    if(obj=="action"){
     this.deleteAction();
	 }

}

//删除轨迹
deleteEvent.prototype.deleteTrack =function(){
   if (confirm("您确定要执行此操作!!!")) {
   var link = menuLine.parentElement.name.split("->");
   var type = menuLine.parentElement.type;
  
   //从上个活动中删除与该线连接的下个活动
   var nextAction = trackHandle.action[link[0]];
   if(nextAction.id=="start"){
     nextAction.isLink = "false";
   }
   nextAction.tracklink = nextAction.tracklink.replace(link[1]+"&"+type+",","");
   trackHandle.action[link[0]] = nextAction;
   if(document.getElementById("work"+link[0])!=null){
    document.getElementById("work"+link[0]).parentElement.parentElement.parentElement.parentElement.parentElement.tracklink=nextAction.tracklink;
   }else if(document.getElementById("work2"+link[0])!=null){
	  document.getElementById("work2"+link[0]).parentElement.isLink = "false";
   }
   //从下个活动中删除与该线连接的上个活动
   var forwardAction = trackHandle.action[link[1]];
   if(forwardAction.id=="end"){
     forwardAction.isLink = "false";
   }
   forwardAction.forward = forwardAction.forward.replace(link[0]+"&"+type+",","");
   trackHandle.action[link[1]] = forwardAction;
   if(document.getElementById("work"+link[1])!=null){
    document.getElementById("work"+link[1]).parentElement.parentElement.parentElement.parentElement.parentElement.forward=forwardAction.forward ;
   }else if(document.getElementById("work2"+link[1])!=null){
	  document.getElementById("work2"+link[1]).parentElement.isLink = "false";
   }
   var allLineId =document.getElementById("defineLineTrueId").value;
   document.getElementById("defineLineTrueId").value = allLineId.replace(menuLine.parentElement.lineTrueId+"&","");
   menuLine.parentElement.parentElement.removeChild(menuLine.parentElement);
   trackHandle.line[menuLine.parentElement.name] = null;
   menuLine = null;
   }
}
//删除活动
deleteEvent.prototype.deleteAction = function(){
  
  if (confirm("您确定要执行此操作!!!")) {

  if(menuIcon!=null){
    menuAction = menuIcon;
  }
  if(menuAction!=null){
     var tracklink = (menuAction.tracklink).split(",");
	 var nextAction ,forwardAction,lineName;
	 var forward = (menuAction.forward).split(",");
	 if(tracklink!="null"&&tracklink!=""){
	   var nextlength = tracklink.length-1;
	    for(var i=0;i<nextlength;i++){
		  nextAction =  trackHandle.action[(tracklink[i].split("&"))[0]];
		  //alert("nextAction.forward 1"+nextAction.forward);
          nextAction.forward = nextAction.forward.replace(menuAction.name+"&"+(tracklink[i].split("&"))[1]+",","");
		  //alert("nextAction.forward 2 "+nextAction.forward);
		  if(nextAction.id=="end"){
		    nextAction.isLink = "false";
		  }
          trackHandle.action[(tracklink[i].split("&"))[0]] = nextAction;
          lineName = menuAction.name+"->"+(tracklink[i].split("&"))[0];
		  if(document.getElementById("work"+(tracklink[i].split("&"))[0])!=null){
           document.getElementById("work"+(tracklink[i].split("&"))[0]).parentElement.parentElement.parentElement.parentElement.parentElement.forward=nextAction.forward ;
          }else if(document.getElementById("work2"+(tracklink[i].split("&"))[0])!=null){
	           document.getElementById("work2"+(tracklink[i].split("&"))[0]).parentElement.isLink = "false";
           }
          var lineObj = trackHandle.line[lineName];
		  if(lineObj!=null){
         document.getElementById("defineLineTrueId").value = document.getElementById("defineLineTrueId").value.replace(lineObj.lineTrueId+"&","");
          lineObj.parentElement.removeChild(lineObj);
		  trackHandle.line[lineName] = null;
		  lineObj = null;
		  }
	   }
	 }
	 if(forward!="null"&&forward!=""){
	     var forwardlength = forward.length-1;
	      for(var i=0;i<forwardlength;i++){
		    forwardAction =  trackHandle.action[(forward[i].split("&"))[0]];
			//alert(forwardAction.tracklink+" forwardAction.tracklink 1 ");
			forwardAction.tracklink=
			forwardAction.tracklink.replace(menuAction.name+"&"+(forward[i].split("&"))[1]+",","");
			//alert(forwardAction.tracklink+" forwardAction.tracklink 2 ");
			if(forwardAction.id=="start"){
		     forwardAction.isLink = "false";
		    }
            trackHandle.action[(forward[i].split("&"))[0]] = forwardAction;
			if(document.getElementById("work"+(forward[i].split("&"))[0])!=null){
              document.getElementById("work"+(forward[i].split("&"))[0]).parentElement.parentElement.parentElement.parentElement.parentElement.tracklink=forwardAction.tracklink ;
            }else if(document.getElementById("work2"+(forward[i].split("&"))[0])!=null){
	           document.getElementById("work2"+(forward[i].split("&"))[0]).parentElement.isLink = "false";
           }

			lineName = (forward[i].split("&"))[0]+"->"+menuAction.name;
			var lineObj = trackHandle.line[lineName];
			if(lineObj!=null){
			 document.getElementById("defineLineTrueId").value = document.getElementById("defineLineTrueId").value.replace(lineObj.lineTrueId+"&","");
            lineObj.parentElement.removeChild(lineObj);
		    trackHandle.line[lineName] = null;
			}
	      }
	 } 
     
     //实现说明：目的是检查元素是否是节点，即非开始，结束 和 路由（也包括分支和汇集）
     var elementid=document.getElementById("defineActionTrueId").value;
	 if(elementid!=""){//如果elementid非空意味着该被删除的节点不是开始和结束点
	   
	   if(elementid.substr(0,5)!="route"){
   		   activitycount--;//是节点，那么需要把总数减1，用于统计节点总数是否超过20个
   		}
   	   if(elementid.substr(0,12)!="turningpoint")
   	   	   activitycount--;//是节点，那么需要把总数减1，用于统计节点总数是否超过20个
 	}
         
     menuAction.parentElement.removeChild(menuAction);
     menuAction = null;
	 menuIcon =null;
  }
  }
}


//*****************************工作流删除事件        END****************************************






//*****************************鼠标右键事件        START****************************************
function rightMouseEvent(){

   var obj = window.event.srcElement;
   var workTrackMenu = document.getElementById("workTrackMenu");
   var iconMenu = document.getElementById("iconMenu");
   var lineMenu = document.getElementById("lineMenu");		
   var iconLineMenu = document.getElementById("iconLineMenu");
   var subflowMenu = document.getElementById("subflowMenu");
  //活动对象
   if((window.event.button==0||window.event.button==2)&&(selectActionObj!=null)&&(obj.parentElement.id!="lineId")){
      createActivityObj = null;
	  var menuLeft = event.clientX;
      var menuTop =  event.clientY;

	  //var menuObj = document.getElementsByName("workTrackMenu");
	   if(selectActionObj.actionType!="subflow"){

	    workTrackMenu.style.display = "";
        workTrackMenu.style.left = menuLeft;
		workTrackMenu.style.top = menuTop;
		//关闭其他menu
		if((iconMenu!=null)){
			iconMenu.style.display="none";
		}
		if((lineMenu!=null)){
			lineMenu.style.display="none";
		}
		if((iconLineMenu!=null)){
			iconLineMenu.style.display="none";
		}
		if((subflowMenu!=null)){//2006-4-3
			subflowMenu.style.display="none";
		}
	   }else if(selectActionObj.actionType=="subflow"){
	                subflowMenu.style.display = "";
                    subflowMenu.style.left = menuLeft;
		            subflowMenu.style.top = menuTop;
		            //关闭其他menu
		            if((iconMenu!=null)){
			              iconMenu.style.display="none";
		            }
		            if((lineMenu!=null)){
			              lineMenu.style.display="none";
		            }
		           if((iconLineMenu!=null)){
			          iconLineMenu.style.display="none";
		           }
				   	if((workTrackMenu!=null)){//2006-4-3
			          workTrackMenu.style.display="none";
		           }
	         } 
   }
   //轨迹对象
  if((window.event.button==0||window.event.button==2)&&(obj.parentElement.id=="lineId")){
        createActivityObj = null;
    	var menuLeft = event.clientX;
        var menuTop =  event.clientY;
		var menuObj;
		if(((obj.parentElement.name.indexOf("start_",0))!=-1)||((obj.parentElement.name.indexOf("end_",0))!=-1)){
	             iconLineMenu.style.display = "";
                 iconLineMenu.style.left = menuLeft;
		         iconLineMenu.style.top = menuTop;
				//关闭其他menu
				if((iconMenu!=null)){
					iconMenu.style.display="none";
				}
				if((workTrackMenu!=null)){
					workTrackMenu.style.display="none";
				}	
				if((lineMenu!=null)){
					lineMenu.style.display="none";
				}
 				if((subflowMenu!=null)){
					subflowMenu.style.display="none";
				}				
		}else{
			    lineMenu.style.display = "";
		        lineMenu.style.left = menuLeft;
		        lineMenu.style.top = menuTop;
				//关闭其他menu		
				if((iconMenu!=null)){
					iconMenu.style.display="none";
				}
				if((workTrackMenu!=null)){
					workTrackMenu.style.display="none";
				}	
				if((iconLineMenu!=null)){
					iconLineMenu.style.display="none";
				}
				if((subflowMenu!=null)){
					subflowMenu.style.display="none";
				}	
		}
		
        menuLine = obj;				
  }

  //开始结束点
  if((window.event.button==0||window.event.button==2)&&(obj.name=="textbox")){
		createActivityObj = null;
		var menuLeft = event.clientX;
        var menuTop =  event.clientY;
        iconMenu.style.display = "";
        iconMenu.style.left = menuLeft;
		iconMenu.style.top = menuTop;
        menuIcon  = obj.parentElement;
		//关闭其他menu	
		if((workTrackMenu!=null)){
			workTrackMenu.style.display="none";
		}		
		if((lineMenu!=null)){
			lineMenu.style.display="none";
		}
		if((iconLineMenu!=null)){
			iconLineMenu.style.display="none";
		}			
		if((subflowMenu!=null)){
			subflowMenu.style.display="none";
		}		
		
  }
  
  //点击空位置清楚menu
  if((window.event.button==0||window.event.button==2)&&((obj.parentElement.id==null)||(obj.parentElement.id==""))){
		createActivityObj = null;
		if((iconMenu!=null)){
			iconMenu.style.display="none";
		}
		if((workTrackMenu!=null)){
			workTrackMenu.style.display="none";
		}		
		if((lineMenu!=null)){
			lineMenu.style.display="none";
		}
		if((iconLineMenu!=null)){
			iconLineMenu.style.display="none";
		}
		if(subflowMenu!=null){
            subflowMenu.style.display="none";
		}
  }
  
}

//*****************************鼠标右键事件          END****************************************


//*****************************鼠标左键事件          START****************************************
function leftMouseEvent(){

  var obj = window.event.srcElement;

  //点击空位置清楚menu
  if((window.event.button==1)&&!(obj.parentElement.id=="iconMenu"||obj.parentElement.id=="workTrackMenu"||obj.parentElement.id=="lineMenu"||obj.parentElement.id=="iconLineMenu"||obj.parentElement.id=="subflowMenu")){
  		var iconMenu = document.getElementById("iconMenu");
  		var workTrackMenu = document.getElementById("workTrackMenu");		
		var lineMenu = document.getElementById("lineMenu");		
		var iconLineMenu = document.getElementById("iconLineMenu");	
		var subflowMenu = document.getElementById("subflowMenu");	
		if((iconMenu!=null)){
			iconMenu.style.display="none";
		
		}
		if((workTrackMenu!=null)){
			workTrackMenu.style.display="none";
		
		}		
		if((lineMenu!=null)){

			lineMenu.style.display="none";
		
		}
		if((iconLineMenu!=null)){
			iconLineMenu.style.display="none";
		
		}
	   if((subflowMenu!=null)){
			subflowMenu.style.display="none";
		
		}	
		
  }
  if((window.event.button==1)&&(createActivityObj!=null)){
  
     if((obj.name)=="create"){
	  createActivityObj.parentElement.removeChild(createActivityObj);
	 }
	  createActivityObj = null;
  }
}
//*****************************鼠标左键事件          END****************************************


//*****************************定义活动的属性        START****************************
function normalActivityAttribute(){
  var nameObj = document.getElementById("defineActionObjName");
  nameObj.value  = menuAction.name;
  var xtype=menuAction.name.substr(0,5);//获得route
  var xtypeturning=menuAction.name.substr(0,12);//获得turningpoint
  var xtypenextjoin=menuAction.name.substr(0,9);//获得routesplit
  var xtypenextsplit=menuAction.name.substr(0,10);//获得routejoin;
 var pathinfo =  document.getElementById("pathinfo").value;
  if(xtypenextsplit=="routesplit"){
     var attribute =window.showModalDialog(pathinfo+"/workflow/resource/track/extend/action/route/workTrackPropertysplit.html",window,"status:0;help:0;dialogWidth=320px;dialogHeight=290px");
     return;
  }
  if(xtypenextjoin=="routejoin"){
       var attribute =window.showModalDialog(pathinfo+"/workflow/resource/track/extend/action/route/workTrackPropertyjoin.html",window,"status:0;help:0;dialogWidth=320px;dialogHeight=290px");
  	   return;
  }
  if(xtype=="route"){
  	 var attribute =window.showModalDialog(pathinfo+"/workflow/resource/track/extend/action/route/workTrackProperty.html",window,"status:0;help:0;dialogWidth=320px;dialogHeight=290px");
  	 return;
  }
  if(xtypeturning=="turningpoint"){
  	 var attribute =window.showModalDialog(pathinfo+"/workflow/resource/track/extend/action/route/workTrackProperty.html",window,"status:0;help:0;dialogWidth=320px;dialogHeight=290px");
  	 return;
  }
  var attribute =window.showModalDialog(pathinfo+"/workflow/resource/track/extend/action/normal/workTrackProperty.html",window,"status:0;help:0;dialogWidth=320px;dialogHeight=290px");
}

function subflowActivityAttribute(){
var pathinfo =  document.getElementById("pathinfo").value;
  var nameObj = document.getElementById("defineActionObjName");
  nameObj.value  = menuAction.name;
  var attribute =window.showModalDialog(pathinfo+"/workflow/resource/track/extend/action/subflow/workTrackProperty.jsp",window,"status:0;help:0;dialogWidth=330px;dialogHeight=300px");
}

//*****************************定义活动的属性       END****************************

//****************************定义轨迹的属性     START********************************
function lineAttribute(){
var pathinfo =  document.getElementById("pathinfo").value;
var nameObj = document.getElementById("defineLineName");
var lineObj = menuLine.parentElement;
nameObj.value = lineObj.name;
window.showModalDialog(pathinfo+"/workflow/resource/track/extend/line/tansactionAttribut.html",window,"status:0;help:0;dialogWidth=600px;dialogHeight=450px");

}

//*****************************定义轨迹的属性       END****************************

//****************************定义子流程的属性     START********************************

//*****************************定义子流程的属性END****************************


/***********************************鼠标事件  START ****************************/
//选择工具条中的元素
function engage(){	

  offsetX = window.event.offsetX;//获得鼠标点，到当前Table的起始位置的X坐标，通常只用在活动和开始，结束 节点上
  offsetY = window.event.offsetY;//获得鼠标点，到当前Table的起始位置的Y坐标，通常只用在活动和开始，结束 节点上

  setSelectObj();
  rightMouseEvent();
  leftMouseEvent();
  new fixLine();//固定已经创建的线条
}

/* 获得一个对象，并且定义好层的位置 */
function setSelectObj() {
    var  selectObj = window.event.srcElement;
	/*移动开始图片，跟结束图片*/
	if(selectObj.id=="icon"){
	   iconObj = selectObj;
	   setZIndex(iconObj,100);
	}else if(selectObj.name=="textbox"){
	   iconObj = selectObj.parentElement;
	   setZIndex(iconObj,100);
	}
	if(selectObj.name == "track"){
        selectActionObj= selectObj.parentElement.parentElement.parentElement.parentElement;
	    setZIndex(selectActionObj,100);
	}else if(selectObj.name == "textarea"){
	    selectActionObj= selectObj.parentElement.parentElement.parentElement.parentElement.parentElement;
		menuAction = selectActionObj;
	    setZIndex(selectActionObj,100);
	}
	if(selectObj.parentElement.id=="createElement"){
	     trackFactory(selectObj.parentElement);
	}
}
//把选中对象的zIndex变成最大
function setZIndex(selectActionObj,zorder){
   selectActionObj.style.zIndex = zorder;
}
function dragIt(){
   this.x = event.clientX-offsetX;
   this.y = event.clientY-offsetY;
   
   if( this.x<0){
   this.x=5;
   }
   if(this.y<60){
   this.y=62;
   }
   
   new moveLine();//创建一个跟着鼠标走的线
   moveActivity();//创建一个跟着鼠标走的活动
   if(selectActionObj!=null){
     selectActionObj.style.left = this.x+"px";
     selectActionObj.style.top = this.y+"px";
     dynamicLine();
   }
   if(iconObj!=null){
      iconObj.style.left = this.x+"px";
      iconObj.style.top = this.y+"px";
   }
 
}
function release(){
 if(selectActionObj!=null){
     setZIndex(selectActionObj,50);
	  selectActionObj =null;
 }
  
 if(iconObj!=null){
     setZIndex(iconObj,50);
	 iconObj =null;
 }
}

/***********************        鼠标事件 END  *****************************/










/***********************        保存流程  STRAT  *********************************/
function saveProcess(){

	if(this.relationDataObj.value == "" || this.relationDataObj.value == null )
		this.relationDataObj.value = "busstype,业务类型,,,,;busstip,业务提示,,,,;bussid,业务参数,,,,;bussurl,业务地址,,,,;customer,参与者,,,,;worklisttitle,待办提示,,,,;";
	
   saveRelation();
  
   TrackItem();
   lineItem();
   var processObj = new editProcess();
   var pageId = (new Date()).valueOf()-1142838336468;
   var length = trackHandle.action.length;
   if(length<1){
   	  alert('无法保存空流程');
   	  return;
   }
   var actionObj = null;
   var trueId, id,name,x,y,forward,tracklink,actionType,deadline,actionExtendAttribute,forwardCondition,afterCondition;
   var xpdl = "";
   var startStr ="";
   var endStr = "";
   var lineString="";
   var actionConditionStr = "";
   var nextAId;
	
	xpdl+='<?xml version="1.0" encoding="GB2312"?>\n'
	+'<Package Id="'+pageId+'" Name="page" xmlns="http://www.wfmc.org/2002/XPDL1.0" xmlns:xpdl="http://www.wfmc.org/2002/XPDL1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.wfmc.org/2002/XPDL1.0 http://wfmc.org/standards/docs/TC-1025_schema_10_xpdl.xsd">\n'
	+xmlblank1+'<PackageHeader>\n'
	+xmlblank2+'<XPDLVersion>1.0</XPDLVersion>\n'
	+xmlblank2+'<Vendor>oesee</Vendor>\n'
	+xmlblank2+'<Created>2006-03-08 15:45:54</Created>\n'
	+xmlblank1+'</PackageHeader>\n'
	+xmlblank1+'<RedefinableHeader PublicationStatus="UNDER_TEST"/>\n'
	+xmlblank1+'<ConformanceClass GraphConformance="NON_BLOCKED"/>\n'
	+xmlblank1+'<WorkflowProcesses>\n'
	+processObj.processHead()
	+xmlblank2+'<RedefinableHeader PublicationStatus="UNDER_TEST"/>\n'
	+saveRelation()
	+xmlblank2+'<Activities>\n';

	//节点****************************************8
	var actionExtend = "";
	var startx,starty;
	var endx,endy;
	var actionEditType=xmlblank4+'<Implementation>\n'+xmlblank5+'<No/>\n'+xmlblank4+'</Implementation>\n';//在编辑活动属性页面上的类型
	for(var i=0;i<length;i++){
	 actionEditType=xmlblank4+'<Implementation>\n'+xmlblank5+'<No/>\n'+xmlblank4+'</Implementation>\n';
     actionExtend="";

     var actionObj = trackHandle.action[i];

	 if(actionObj.id=="trackAction"){
	 	var Icontype = "";
	 	actionConditionStr = "";
     	id = actionObj.name;
	 	trueId = actionObj.actionTrueId;

	 	x = parseint(actionObj.style.left);
	 	name = actionObj.actionName;
	 	y = parseint(actionObj.style.top);
	 	forward = actionObj.forward;
	 	tracklink = actionObj.tracklink;
	 	actionType = actionObj.actionType;
	 	
		if(actionType==null ||actionType==""){
			actionType='tools';
		}
	 	if(actionType.substr(0,5)=='route' || actionType.substr(0,12)=='turningpoint'){
	 		actionEditType =xmlblank4+'<Route/>';
	 	}
	 	//if(actionType.substr(0,12)=='turningpoint'){
	 	//	actionEditType =xmlblank4+'<Rurningpoint/>';
	 	//}

	 	deadline = actionObj.deadline;
	 	actionExtendAttribute = actionObj.actionExtendAttribute;
	 	if('subflow'==actionType && (actionExtendAttribute==null||actionExtendAttribute=='')){
	 		if(!confirm('子流程:'+name+' 中没有定义子流程信息! 要将其转换成节点?')){
	 		 	return;
	 		}
	 	}
	 	forwardCondition = actionObj.forwardCondition;
	 	afterCondition = actionObj.afterCondition;
	 	actionConditionStr+=xmlblank4+'<TransitionRestrictions>\n'
                       	  +xmlblank5+'<TransitionRestriction>\n';
	 	if((forwardCondition=="XOR")||(forwardCondition=="AND")){
	  		actionConditionStr +=xmlblank6+'<Join Type="'+forwardCondition+'"/>';
	 	}
	 	if((afterCondition=="XOR")||(afterCondition=="AND")){
	 		var linkCon = tracklink.split(",");        
      		actionConditionStr +=xmlblank6+'<Split Type="'+afterCondition+'">\n'
                        	   +xmlblank6+'<TransitionRefs>\n';
			for(var k=0;k<linkCon.length-1;k++){
				nextAId = linkCon[k].split("&")[0];
      			actionConditionStr+=xmlblank7+'<TransitionRef Id="'+trackHandle.line[id+'->'+nextAId].lineTrueId+'"/>\n';
			}
 
      		actionConditionStr +=xmlblank6+'</TransitionRefs>\n</Split>\n'
	 	}
	
     	actionConditionStr+=xmlblank5+'</TransitionRestriction>\n'+
     					xmlblank4+'</TransitionRestrictions>\n';

	 	if((forward=="null")||(forward=="")){
	  	 	alert("一个或多个过程有不适当的连接元素!");
	     	return;
	 	}else if(forward.indexOf("start_",0)!=-1){

			
		 	startx = parseint((trackHandle.action[forward.split("&")[0]]).style.left);

		 	starty = parseint((trackHandle.action[forward.split("&")[0]]).style.top);

	   	if((forward.split("&"))[1]=="ZLine,"){Icontype="SIMPLEROUTING";}

       	if((forward.split("&"))[1]=="beenLine,"){Icontype="NOROUTING";}

	    startStr +=xmlblank4+'<ExtendedAttribute Name="StartOfWorkflow" Value="FreeTextExpressionParticipant;'+trueId+';'+startx+';'+starty+';'+Icontype+'"/>\n';
	 }

	 if((tracklink=="null")||(tracklink=="")){
	   alert("一个或多个过程有不适当的连接元素!");
	   return;
	 }else if(tracklink.indexOf("end_",0)!=-1){
	
		  endx = parseint((trackHandle.action[tracklink.split("&")[0]]).style.left);
		 
		  endy = parseint((trackHandle.action[tracklink.split("&")[0]]).style.top);
		
	      if((tracklink.split("&"))[1]=="ZLine,"){Icontype="SIMPLEROUTING";}
          if((tracklink.split("&"))[1]=="beenLine,"){Icontype="NOROUTING";}
	   	  endStr +=xmlblank4+'<ExtendedAttribute Name="EndOfWorkflow" Value="FreeTextExpressionParticipant;'+trueId+';'+endx+';'+endy+';'+Icontype+'"/>\n';
	 }
	var limit = "";
	    if((deadline!=null)&&(deadline!="")){
			limit=xmlblank4+"<Limit>"+deadline+"</Limit>";
       }

	xpdl+=xmlblank3+'<Activity Id="'+trueId+'" Name="'+name+'">\n'
		+limit
		+actionEditType
		+xmlblank4+'<StartMode>\n'
		+xmlblank5+'<Automatic/>\n'
		+xmlblank4+'</StartMode>\n'
		+xmlblank4+'<FinishMode>\n'
		+xmlblank5+'<Automatic/>\n'
		+xmlblank4+'</FinishMode>\n'
		+actionConditionStr
		+xmlblank4+'<ExtendedAttributes>\n'
		+xmlblank5+'<ExtendedAttribute Name="ParticipantID" Value="FreeTextExpressionParticipant"/>\n'
		+xmlblank5+'<ExtendedAttribute Name="XOffset" Value="'+x+'"/>\n'
		+xmlblank5+'<ExtendedAttribute Name="YOffset" Value="'+y+'"/>\n';
		
        if((actionExtendAttribute!=null)&&(actionExtendAttribute!="")){
			  var extend = actionExtendAttribute.split(",");
			  var extendLength = extend.length;
			  for(var n=0;n<extendLength;n++){
              	actionExtend+=xmlblank5+'<ExtendedAttribute Name="'+(extend[n].split("&"))[0]+'" Value="'+(extend[n].split("&"))[1]+'"/>\n';
			  }
	    }
	xpdl+=actionExtend
		+xmlblank4+'</ExtendedAttributes>\n'+xmlblank3+'</Activity>\n';
		}
	}

	xpdl+=xmlblank3+'</Activities>\n';

	xpdl+=xmlblank3+'<Transitions>\n';
	//路径********************************
	var lineLength = trackHandle.line.length;
	var fromId,toId,lineType,extendName,extendValue,extend,extendLength,extendStr="";
	var lineObj=null;
   	for(var i=0;i<lineLength;i++){
   		lineObj = trackHandle.line[i];
   		if(lineObj!=null){
     		if(lineObj.type=="ZLine"){lineType="SIMPLEROUTING";}
     		if(lineObj.type=="beenLine"){lineType="NOROUTING";}
     		fromId = lineObj.name.split("->")[0];
	 		toId = lineObj.name.split("->")[1];
	 		if((toId.indexOf("end_",0)==-1)&&(fromId.indexOf("start_",0)==-1)){
	 			extend = lineObj.extendContent.split(",");
	 			extendLength = extend.length;
	 			extendStr="";
	 			if(extend!=""){
	  				for(var k=0;k<extendLength;k++){
	   						extendName = (extend[k].split("&"))[0];
	   						extendValue = (extend[k].split("&"))[1];
	   						extendStr +=xmlblank5+'<ExtendedAttribute Name="'+extendName+'" Value="'+extendValue+'"/>\n';
	  				}
	 			}
	var condition = "";
	if((lineObj.condition)!=""){
		condition = xmlblank5+'<Condition Type=\"CONDITION\">'+lineObj.condition+'</Condition>\n';
	}
	
	
	 lineString+=xmlblank4+'<Transition Name="'+lineObj.trueName+'" From="'+trackHandle.action[fromId].actionTrueId+'" Id="'+trackHandle.line[fromId+'->'+toId].lineTrueId+'" To="'+trackHandle.action[toId].actionTrueId+'">\n'
                +condition
                +xmlblank5+'<Description>'+lineObj.depict+'</Description>\n'
                +xmlblank5+'<ExtendedAttributes>\n'               
     			+xmlblank6+'<ExtendedAttribute Name="RoutingType" Value="'+lineType+'"/>\n'
                +extendStr
                +xmlblank5+'</ExtendedAttributes>\n'
            	+xmlblank4+'</Transition>\n';
            	
            	
	  }
   }
  }

	xpdl+=lineString
		+xmlblank3+'</Transitions>\n'+xmlblank3+'<ExtendedAttributes>\n'
		+startStr
		+endStr
		+xmlblank4+'<ExtendedAttribute Name="ParticipantVisualOrder" Value="FreeTextExpressionParticipant;"/>\n'
		+processObj.processHeadExtend()
		+xmlblank3+'</ExtendedAttributes>\n'
		+xmlblank2+'</WorkflowProcess>\n'
		+xmlblank1+'</WorkflowProcesses>\n'
		+xmlblank1+'<ExtendedAttributes>\n'
		+xmlblank2+'<ExtendedAttribute Name="MadeBy" Value="www.oesee.org"/>\n'
		+xmlblank2+'<ExtendedAttribute Name="Version" Value="1.0"/>\n'
		+xmlblank1+'</ExtendedAttributes>\n'
		+'</Package>\n';



  	this.document.forms[0].xpdlContent.value=xpdl;

  	this.document.forms[0].processid.value=processObj.processId;
  	this.document.forms[0].description.value=processObj.processName ;

  	
  	var isCreate=this.document.forms[0].createprocess.value;
    var pathinfo =  document.getElementById("pathinfo").value;
  	if("yes"==isCreate){
  	    
  		this.document.forms[0].action =pathinfo +'/saveprocess.do';	
  		
  	}else{
  		this.document.forms[0].action = pathinfo+'/updateprocess.do'
  	}

  	this.document.forms[0].method = "post";
  	this.document.forms[0].target = "_self";
  	this.document.forms[0].submit();

}



/***********************        保存流程  END  *********************************/





/***********************        重置流程  START       *************************/
function resetFlow(){
 var lineobjs  = document.getElementsByName("lineId");
 var actionObj  = document.getElementsByName("trackAction");
 var startIconObj = document.getElementsByName("start");
 var endIconObj = document.getElementsByName("end");
 var lineLength = lineobjs.length;
 var actionLength = actionObj.length;
 var startLength = startIconObj.length;
 var endLength = endIconObj.length;
 while(lineLength>0){
  lineobjs[0].parentElement.removeChild(lineobjs[0]);
  lineobjs  = document.getElementsByName("lineId");
  lineLength = lineobjs.length;
 }
 while(actionLength>0){
  actionObj[0].parentElement.removeChild(actionObj[0]);
  actionObj  = document.getElementsByName("trackAction");
  actionLength = actionObj.length;
 }
  while(startLength>0){
  startIconObj[0].parentElement.removeChild(startIconObj[0]);
  startIconObj = document.getElementsByName("start");
  startLength = startIconObj.length;
 }
  while(endLength>0){
  endIconObj[0].parentElement.removeChild(endIconObj[0]);
  endIconObj = document.getElementsByName("end");
  endLength = endIconObj.length;
 }

}






/***********************        重置流程  END         *************************/










/************************      一些参数的转化    START     *******************/
 

//把字符的换成整型
function parseint(str){
 var num = str.indexOf("px",0);
 var substr = str.substr(0,num);
 var p = parseInt(substr,10);
 return p;
}

//把字符的换成整型
function parseintPt(str){
 str =str+" ";
 var num = str.indexOf("pt,",0);
 var substr = str.substr(0,num);
 var p = parseInt(substr,10);
 return p;
}
//箭头的指向位置
function position(fx,fy,tx,ty,objectwidth,objectheight){

   var rate = 45/110;//底除于高
   var rate1 = 1;
   var offY=0,offX=0;
   var objX=0,objY=0;
    if (fx != tx) {
	  if((fx - tx) == 0)    
      	rate1 = (fy - ty) / 1;
      else
      	rate1 = (fy - ty) / (fx - tx);
      rate1 = rate1 > 0 ? rate1 : -rate1;
    }
    var lenx = Math.abs(fx - tx);
    var leny = Math.abs(fy - ty);
    if(objectwidth == 13.5){
	    if (rate1 <= rate) {
	      if (fx> tx) {
	
	        offX = objectwidth;
			offY=-26;
	      }
	      else if (fx<tx) {
	
	        offX =  -objectwidth;
			offY = -26;
	      }
	    }
	    else{
	      if (fy > ty) {
	
		    offX = 0
	        offY = objectheight-21;
	      }
	      else if (fy < ty) {
		    offX = 0
	        offY = -objectheight-28;
	      }
	    }
    }else{
    	if (rate1 <= rate) {
		    if (fx> tx) {
		
		        offX = objectwidth;
				offY=-13;
		      }
		      else if (fx<tx) {
		
		        offX =  -objectwidth;
				offY = -13;
		      }
		    }
		    else{
		      if (fy > ty) {
		
			    offX = 0
		        offY = objectheight-8;
		      }
		      else if (fy < ty) {
			    offX = 0
		        offY = -objectheight-15;
		      }
		    }

    }

   objX = tx + offX;
   objY = ty + offY;
   				   //if(isNaN(objX) || isNaN(objY)){
				   		//document.getElementById('xpo').innerHTML = offX;
					    //document.getElementById('ypo').innerHTML = offY;
				   		//alert("有报错了！");
				   		//return;
				   //}
   
   this.point = new point(objX,objY);
}


//************************************   START**************************************************
//重置
function reset(type){
  	if(type=="tools")
   		this.resetTools();
  	if(type=="workFlowProperty")
      	this.resetWorkFlowProperty();
	if(type=="extendValue")
		this.resetOptions();
}

reset.prototype.resetTools = function(){
  var actionId = document.getElementById("actionId");
  var actionName = document.getElementById("actionName");
  var actionType = document.getElementById("actionType");
  var deadline = document.getElementById("deadline");
  actionId.value = "";
  actionName.value = "";
  actionType.value = "";
  deadline.value = "";
}
reset.prototype.resetWorkFlowProperty = function(){
    var extend = document.getElementById("extend");
    var extendValue = document.getElementById("extendValue"); 
    extend.value = "";
	extendValue.value = "";
}

reset.prototype.resetOptions = function(){
}

function button(type){
 var basicObj = document.getElementById("tools");
 var extendedObj = document.getElementById("workFlowProperty");
 var toolObj =  document.getElementById("options");
 var simuobj =  document.getElementById("simu");
  if(type=="tools"){
	   basicObj.style.visibility="visible";
	   var sub1 = document.getElementById("s1");
	   sub1.style.backgroundImage="url(../../image/wf/titChange.jpg)";
	   sub1.style.color="";
	   var sub2 = document.getElementById("s2");
	   sub2.style.background=""
	   sub2.style.color="";
	   var sub3 = document.getElementById("s3");
	   sub3.style.background=""
	   sub3.style.color="";
	   var sub4 = document.getElementById("s4");
	   sub4.style.background=""
	   sub4.style.color="";
	   extendedObj.style.visibility="hidden";
	   toolObj.style.visibility="hidden";
	   simuobj.style.visibility="hidden";
     }

  if(type=="workFlowProperty"){
  	   extendedObj.style.visibility="visible";
	   var sub1 = document.getElementById("s1");
	   sub1.style.background=""
	   sub1.style.color="";
	   var sub2 = document.getElementById("s2");
	   sub2.style.backgroundImage="url(../../image/wf/titChange.jpg)";
	   var sub3 = document.getElementById("s3");
	   sub3.style.background=""
	   sub3.style.color="";
	   var sub4 = document.getElementById("s4");
	   sub4.style.background="";
	   sub4.style.color="";
	   basicObj.style.visibility="hidden";
	   toolObj.style.visibility="hidden";
	   simuobj.style.visibility="hidden";
     }

   if(type=="options"){
   	   toolObj.style.visibility="visible";
	   var sub1 = document.getElementById("s1");
	   sub1.style.background=""
	   sub1.style.color="";
	   var sub2 = document.getElementById("s2");
	   sub2.style.background=""
	   sub2.style.color="";
	   var sub4 = document.getElementById("s4");
	   sub4.style.background=""
	   sub4.style.color="";
	   var sub3 = document.getElementById("s3");
       sub3.style.backgroundImage="url(../../image/wf/titChange.jpg)";
       extendedObj.style.visibility="hidden";
	   basicObj.style.visibility="hidden";
	   simuobj.style.visibility="hidden";
     }
     
     if(type=="simu"){
   	   simuobj.style.visibility="visible";
	   var sub1 = document.getElementById("s1");
	   sub1.style.background=""
	   sub1.style.color="";
	   var sub2 = document.getElementById("s2");
	   sub2.style.background=""
	   sub2.style.color="";
	   var sub3 = document.getElementById("s3");
	   sub3.style.background=""
	   sub3.style.color="";
	   var sub4 = document.getElementById("s4");
       sub4.style.backgroundImage="url(../../image/wf/titChange.jpg)";
       extendedObj.style.visibility="hidden";
	   basicObj.style.visibility="hidden";
	   toolObj.style.visibility="hidden";
     }
}
//************************************   END**************************************************


/*打开相关数据窗口*/
function relationData(){
 	var pathinfo =  document.getElementById("pathinfo").value;
	window.showModalDialog(pathinfo+"/workflow/resource/track/extend/relationData/relationData.html",window,"status:0;help:0;dialogWidth=350px;dialogHeight=300px");
}

/*保存相关数据*/
function saveRelation(){
  var relationDates  = (document.getElementById("relationData")).value;
  var dataSplit;
  var dataId = "";
  var dataName = "";
  var InitialValue = "";
  var Length = "";
  var Description = "";
  var extend = "";
  var dataString = "";
  if((relationDates!=null)&&(relationDates!="")){
      dataString = xmlblank3+"<DataFields>\n";
	  var relationDate = relationDates.split(";");
	  var dataLength = relationDate.length-1;
	  for(var i=0;i<dataLength;i++){
          dataSplit = relationDate[i].split(",");
		  dataId = dataSplit[0];
		  dataName = dataSplit[1];
		  InitialValue = dataSplit[2];
		  Length = dataSplit[3];
		  Description = dataSplit[4];
		  extend = dataSplit[5];
		  dataString+=saveRelationStr(dataId,dataName,InitialValue,Length,Description,extend);
	  }
     dataString+=xmlblank3+"</DataFields>\n";

  }
  return dataString;
}
function saveRelationStr(dataId, dataName, InitialValue, Length, Description, extend){
	var dataStr = "";
	dataStr=  xmlblank4+"<DataField Id=\""+dataId+"\" IsArray=\"FALSE\" Name=\""+dataName+"\">\n"
             +xmlblank5+"<DataType>\n"
             +xmlblank6+"<BasicType Type=\"STRING\"/>\n"
             +xmlblank5+"</DataType>\n"
             +xmlblank5+"<InitialValue>"+InitialValue+"</InitialValue>\n"
             +xmlblank5+"<Length>"+Length+"</Length>\n"
             +xmlblank5+"<Description>"+Description+"</Description>\n"
             +xmlblank5+"<ExtendedAttributes>\n";
       
    var extendSplit = extend.split("|");
	var length = extendSplit.length-1;
    for(var i=0;i<length;i++){
        dataStr+=xmlblank6+"<ExtendedAttribute Name=\""+((extendSplit[i].split("&"))[0])+"\" Value=\""+((extendSplit[i].split("&"))[1])+"\"/>\n";
	}

    dataStr  +=xmlblank5+"</ExtendedAttributes>\n"
             +xmlblank4+"</DataField>\n";
			  return dataStr;
}

/*流程属性*/
function editProcess(){
 	var newProcessObj =  document.getElementById("newProcess");
 	var updataProcessObj =  document.getElementById("updataProcess");
 	var processObj = newProcessObj;
 	this.createTime = fetchNowTime();
 	if(updataProcessObj!=null){
  		 processObj = updataProcessObj;
 	}
 	var processAttribute = processObj.value;
 	var processArray = processAttribute.split(",");
 	
 	this.processId = processArray[0];
 	this.processName = processArray[1];
 	this.processExtend = processArray[2];
 	if(updataProcessObj!=null){
    	this.createTime = processArray[3];
 	}
 
 	document.getElementById("processid").value = this.processId;
 
}

/*获得流程的头部信息*/
editProcess.prototype.processHead = function(){
 var processHeadStr =xmlblank2+'<WorkflowProcess AccessLevel="PUBLIC" Id="'+this.processId+'" Name="'+this.processName+'">\n'
                    +xmlblank3+'<ProcessHeader DurationUnit="D">\n'
                    +xmlblank4+'<Created>'+this.createTime+'</Created>\n'
                    +xmlblank3+'</ProcessHeader>\n';
  return processHeadStr;
}

/*获得流程的扩展属性*/
editProcess.prototype.processHeadExtend = function(){
	var extend =  (this.processExtend).split("|");
	var length = extend.length-1;
	var extendStr = "";
	if(updateNow="false" &&  activitymaxcount!=0 ){
	//如果不是更新的时候，那么需要创建总数扩展属性，其中activitymaxcount！=0的判断是出于这种情况：更新但是还没触发更新事件trackFactory时updateNow="false"，这时识别是否是更新主要看activitymaxcount是否不为0
		extendStr +=xmlblank4+'<ExtendedAttribute Name="activitymaxcount" Value="'+activitymaxcount+'"/>\n';
		extendStr +=xmlblank4+'<ExtendedAttribute Name="activitycount" Value="'+activitycount+'"/>\n';
		extendStr +=xmlblank4+'<ExtendedAttribute Name="othercount" Value="'+othercount+'"/>\n';
	}

	for(var i=0;i<length;i++){
		 extendStr +=xmlblank4+'<ExtendedAttribute Name="'+(extend[i].split("&"))[0]+'" Value="'+(extend[i].split("&"))[1]+'"/>\n';
	}
	return extendStr;
}

/*当修改流程图时从xpdl文件中获得相关数据*/
function read(){
  var xpdlRelationData =document.getElementById("xpdlRelationData");
  var relationData = document.getElementById("relationData");
  if(xpdlRelationData!=null){
    relationData.value = xpdlRelationData.value;
  }
}
/*获得当前时间*/
function fetchNowTime(){
   var d, s = "";           // 声明变量。
   d = new Date();                           // 创建 Date 对象。
   var year = d.getYear();                         // 获取年份。
   var month = (d.getMonth() + 1)          // 获取月份。
   var date = d.getDate()                  // 获取日。
   var hours = d.getHours()                 //小时
   var minutes = d.getMinutes()              //分钟
   var seconds = d.getSeconds()             //秒
    month=month>9?month:("0"+month)
    date=date>9?date:("0"+date)
    hours=hours>9?hours:("0"+hours)
	minutes=minutes>9?minutes:("0"+minutes)
    seconds=seconds>9?seconds:("0"+seconds)
	s = year+"-"+month+"-"+date+"  "+hours+":"+minutes+":"+seconds;
    return(s);  
}

function openWindowUpdataProcess(){
var pathinfo =  document.getElementById("pathinfo").value;
window.open(pathinfo+"/workflow/resource/updataProcess.jsp","mmmm","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,width=520,height=350,left=180,top=100");
}

function simu(){
    var isCreate=this.document.forms[0].createprocess.value;
    var pathinfo =  document.getElementById("pathinfo").value;
    saveProcess();
   	var processid =  document.getElementById("processid").value;
	var url=pathinfo+'/newprocess.do?processid='+processid;
	window.open(url,"_blank","width=800,height=600,scrollbars=yes,resizable=yes");

}

function simuInSave(){
	var pathinfo =  document.getElementById("pathinfo").value;

    var processid =  document.getElementById("processid").value;
	var url=pathinfo+'/newprocess.do?processid='+processid;
	window.open(url,"_blank","width=800,height=600,scrollbars=yes,resizable=yes");

    }


