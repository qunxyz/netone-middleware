var xmlblank1='	';
var xmlblank2=' 	';
var xmlblank3=' 		';
var xmlblank4=' 			';
var xmlblank5=' 				';
var xmlblank6=' 					';
var xmlblank7=' 						';

var offsetX,offsetY;
var selectActionObj=null;//��ѡ�ֵĻ����
var menuAction = null;//���ǻ���󵯳��Ĳ˵�
var menuLine = null;//�������߶��󵯳��Ĳ˵�
var menuIcon = null;//����ͼƬ���󵯳��Ĳ˵�

//��������ʱ����Ĳ���///////////
var createLineObj = null;//�����켣�߶���
var createActivityObj = null;//������������
var lineStartName = null;//�߿�ʼ�˵�����
var lineEndName = null;//�߽����˵�����
var fixX = null;//�̶�heightPoint ��������
var iconObj = null;//��ʼ��������ͼƬ����
//////////////////////////////////

//����ֱ��ʱ����Ĳ���///////////////////
var beenLineObj = null;
var beenLineStartName = null;
var beenLineEndName = null;
///////////////////////////////////

//��Ķ���//////////////////////
var trackNextActionName = null;
var trackForwardActionName = null;
/////////////////////////////////
var actionTrueId = "";
var lineTrueId = "";
var forwardTrackAction = null;//�ϴε���Ļ��
var createLineType = null;
var off = 47;
var offx = 40;

var activitycount=0; //���������
var activitymaxcount=0; //�����������
var initmaxcount="false";// �ñ����ǿ�������,���ڵ�һ�γ�ʼ��activitymaxcountʱʹ��,һ����ʼ����Ϊtrue;
var maxactivitycount=20;

var othercount=0;//����Ԫ����������

var updateNow="false";

var objectWidth = 0;
var objectHeight = 0;



//������е����̶���
var trackHandle = {
   action :{length:0},
   lineStr :{length:0},
   line :{length:0}
}
//����һ����
function point(x,y){
this.x = x;
this.y = y;
}
//�������еĹ켣����
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
//������̶��������
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
//�������еĻ����
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

/////���Ż�ƶ�����������ֱ�ߺ������ߣ�/////
function dynamicLine(){
    if(selectActionObj!=null){
       var name = selectActionObj.name;
       var tracklink = selectActionObj.tracklink;
       var forward = selectActionObj.forward;

	   /*  �����һ�� */
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
			 
		 /*  �����һ�� */
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
/*�ƶ��켣��*/
function moveTrack(lineName,linkName,linkType){
   this.lineName = lineName;//�켣����
   this.linkName = linkName;//Ҫ���ӻ������
   this.linkType = linkType;//�������ͣ���һ������һ����
}
/*�ƶ�����˵�ֱ��*/
moveTrack.prototype.moveBeenLine = function(){
   if(this.linkType=="next"){
         toNextBeenLinePoint(this.lineName,this.linkName);
   }
   if(this.linkType=="forward"){
         toForwardBeenLinePoint(this.lineName,this.linkName);
   }
}

/*�ƶ�����˵�����*/
moveTrack.prototype.moveZLine = function(){
   if(this.linkType=="next"){
         toNextPoint(this.lineName,this.linkName);
   }
   if(this.linkType=="forward"){
         toForwardPoint(this.lineName,this.linkName);
   }
}


//�û������һ����ĵ�(�����ƶ��ǵ���һ���ӵ�)
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
//�û������һ����ĵ�(�����ƶ�����һ���ӵ�)
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
  
  
  //�û������һ����ĵ�(ֱ���ƶ�����һ���ӵ�)
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

//****************�����ƶ��ڵ�ʱ��ýڵ��������߸����ƶ�  END *************************************//

//******************************ͨ���Ѿ����ڵĽڵ㻭���켣   START ******************************/
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

/*����̬�켣����*/
function trackline(fp,tp,lineName){
     this.f = parseint(fp.x);
	 this.t = parseint(tp.x);
	 this.lineName = lineName;
	 this.fp = fp;
	 this.tp = tp;
}
/*����ֱ̬��*/
trackline.prototype.createBeenLine = function(){
   
    this.htm = '<div id="lineId" name="'+this.lineName+'">'
			 +'<v:line style="position:absolute; zIndex:100" '
	         +'from="'+(parseint(this.fp.x)+offx)+','+(parseint(this.fp.y)-off)+'px" to="'+(this.tp.x)+','+(parseint(this.tp.y)-off)+'px">'
    		 +'<v:stroke EndArrow="Classic" Color="#FF0066"/></v:line>'
             +'</div>\n';
			  return this.htm;
}
/*����̬����*/
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


//**********************ͨ���Ѿ����ڵĽڵ㻭���켣   END **************************************//

//******************************�����µ�����   START*************************************************

/*------���̹켣Ԫ�ع��� ----------------*/
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

  /*��������*/
  if(sort=="ZLine"){
   	this.createTrackZLine();

  } else if(sort=="beenLine"){//����ֱ��
   	this.createTrackBeenLine();

  }else {

  /*�����ڵ�*/
  if(sort=="trackAction"){
  this.createTrackAction();
 
  }
  /*����������*/
  if(sort=="subWorkFlow"){

	this.createSubWorkFlow();

  }
  /*�����ջ*/
  if(sort=="route"){

    this.createTrackRoute();

  }
  /*����ת�۵�*/
  if(sort=="turningpoint"){

    this.createTurningPoint();

  }
  /*�����ջ��֧*/
  if(sort=="routesplit"){

    this.createTrackRoute1();

  }
    /*�����ջ�㼯*/
  if(sort=="routejoin"){
    this.createTrackRoute2();
  }
  /*������ʼͼƬ*/
  if(sort=="start"){
  	this.createStartIcon();
  }
  /*��������ͼƬ*/
  if(sort=="end"){
  	this.createEndIcon();
  }
  }

}
/*-------��������------------------------*/
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
 /*-------����ֱ��------------------------*/
trackFactory.prototype.createTrackBeenLine = function(){

   var beenLine = document.createElement("div");
       beenLine.innerHTML ='<div id="lineId" start="null" end="null"  condition="" name="" trueName ="" extendContent="" lineTrueId="" depict="" type="beenLine" >'
       +'<v:line id="" style="Z-INDEX:0;POSITION:absolute;cursor:hand;" from="335px,10px" to="345px,0px"' 
	    +'strokecolor="blue"><v:Stroke endarrow=\'classic\'/></v:line>'
       +'</div>';
       document.body.appendChild(beenLine);
       beenLineObj = beenLine.children[0];
       
}
/*--------������ͨ�------------------------*/
trackFactory.prototype.createTrackAction = function(){
	this._createTrack("�ڵ�","tools","#000000","activity");	
}

/*--------����������------------------------*/
trackFactory.prototype.createSubWorkFlow = function(){
	this._createTrack("������","subflow","#0000EE","subflow");
}
/*����·��*/
trackFactory.prototype.createTrackRoute = function(){
	this._createTrackRoute("·��","route","#000000","route");
		
}

/*����ת�۵�*/
trackFactory.prototype.createTurningPoint = function(){
	this._createTurningPoint("ת�۵�","turningpoint","#000000","turningpoint");
		
}

/*����·�ɷ�֧*/
trackFactory.prototype.createTrackRoute1 = function(){
	this._createTrackRoute("��֧","routesplit","#000000","route");
		
}

/*����·�ɻ㼯*/
trackFactory.prototype.createTrackRoute2 = function(){
	this._createTrackRoute("�㼯","routejoin","#000000","route");
		
}
//�����ڵ�///
trackFactory.prototype._createTrack = function(dispname,type,bordercolor,activityColor){
   	if(activitycount==maxactivitycount){
   		alert("�ڵ��������20��");
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


//�����սڵ�
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

//����ת�۵�
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

	var initRelationData = "busstype,ҵ������,,,,;busstip,ҵ����ʾ,,,,;bussid,ҵ�����,,,,;bussurl,ҵ���ַ,,,,;customer,������,,,,;worklisttitle,������ʾ,,,,;";
//�����켣���//
function submitInitField(){
	this.relationDataObj = document.getElementById("relationData");
	if(this.relationDataObj.value == "" || this.relationDataObj.value == null )
		this.relationDataObj.value = "busstype,ҵ������,,,,;busstip,ҵ����ʾ,,,,;bussid,ҵ�����,,,,;bussurl,ҵ���ַ,,,,;customer,������,,,,;worklisttitle,������ʾ,,,,;";
}

/*-------������ʼͼƬ-----------*/
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
/*-------��������ͼƬ-----------*/
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
/*----------���Ƹմ������߸��������-------*/
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

 /*     ------------�������߸��������       START---------------      */
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
   	/*     ------------����ֱ�߸��������     ---------------------      */

 	/*     ------------���ƻ���������     ---------------------      */
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
/*     ------------���ƻ���������     ---------------------      */


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

/*        -----------�̶��մ�������---------------------------------*/
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
     }else if((selectActionObj!=null)&&(beenLineObj!=null)){//�ڽڵ��ϻ���ֱ�ߵ��¼�
             
             this.fixBeenLine();

	 }
}
  
   ��/*     ------------�������߹̶���---------------      */
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
		   alert("������ȱ�ٿ�ʼ���߽�����");
		   return;
	      }
	      if(selectActionObj.id=="end"||selectActionObj.isLink=="true"){
		   createLineObj.parentElement.removeChild(createLineObj);
           createLineObj = null;
		   selectActionObj=null;
		   release();
		   alert("�����㲻������һ�����ϵ�����")
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
		   alert("�����г��ֶ�·");
		   return;
		  }
	      if(selectActionObj.id=="start"||selectActionObj.isLink=="true"){
		   createLineObj.parentElement.removeChild(createLineObj);
           createLineObj = null;
		   release();
		   alert("�˲����޷����У� 11")
		   return;
		  }else if(forwardTrackAction.id=="start"&&(selectActionObj.forward!="null")&&(selectActionObj.forward!="")){
		   forwardTrackAction.isLink = "false";
		   createLineObj.parentElement.removeChild(createLineObj);
           createLineObj = null;
		   release();
		   alert("�˲����޷�����!! 10");
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
		        alert("�˲����޷�����!!!"); 
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
		        alert("������л㼯ת��!!!"); 
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
    /*     ------------����ֱ�߹̶�����---------------      */
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
		   alert("�˲����޷����У�");
		   return;
	      }
	      if(selectActionObj.id=="end"||selectActionObj.isLink=="true"){
		   beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   selectActionObj=null;
		   release();
		   alert("�����㣬�������д�����");
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
		   alert("�û�Ѿ��з�֧������ָ�������");
		   return;
		  }
	      if(selectActionObj.id=="start"||selectActionObj.isLink=="true"){
		   beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   release();
		   alert("��ʼ�㣬�������д�����");
		   return;
		  }else
		  if((forwardTrackAction.id=="start")&&(selectActionObj.forward!="null")&&(selectActionObj.forward!="")){
		   forwardTrackAction.isLink = "false";
		   beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   release();
		   alert("�˲����޷�����!!");
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
		        alert("�ظ���"); 
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
		        alert("������л㼯ת��!"); 
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

/*-------�жϽڵ����һ���Ƿ�Ϊ end����һ���Ƿ�Ϊstart---------*/
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
//******************************�����µ�����   START*************************************************




//*****************************������ɾ���¼�        START****************************************
function deleteEvent(obj){
  // var obj = window.event.srcElement;
    if(obj=="line"){
	 this.deleteTrack();
    }
    if(obj=="action"){
     this.deleteAction();
	 }

}

//ɾ���켣
deleteEvent.prototype.deleteTrack =function(){
   if (confirm("��ȷ��Ҫִ�д˲���!!!")) {
   var link = menuLine.parentElement.name.split("->");
   var type = menuLine.parentElement.type;
  
   //���ϸ����ɾ����������ӵ��¸��
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
   //���¸����ɾ����������ӵ��ϸ��
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
//ɾ���
deleteEvent.prototype.deleteAction = function(){
  
  if (confirm("��ȷ��Ҫִ�д˲���!!!")) {

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
     
     //ʵ��˵����Ŀ���Ǽ��Ԫ���Ƿ��ǽڵ㣬���ǿ�ʼ������ �� ·�ɣ�Ҳ������֧�ͻ㼯��
     var elementid=document.getElementById("defineActionTrueId").value;
	 if(elementid!=""){//���elementid�ǿ���ζ�Ÿñ�ɾ���Ľڵ㲻�ǿ�ʼ�ͽ�����
	   
	   if(elementid.substr(0,5)!="route"){
   		   activitycount--;//�ǽڵ㣬��ô��Ҫ��������1������ͳ�ƽڵ������Ƿ񳬹�20��
   		}
   	   if(elementid.substr(0,12)!="turningpoint")
   	   	   activitycount--;//�ǽڵ㣬��ô��Ҫ��������1������ͳ�ƽڵ������Ƿ񳬹�20��
 	}
         
     menuAction.parentElement.removeChild(menuAction);
     menuAction = null;
	 menuIcon =null;
  }
  }
}


//*****************************������ɾ���¼�        END****************************************






//*****************************����Ҽ��¼�        START****************************************
function rightMouseEvent(){

   var obj = window.event.srcElement;
   var workTrackMenu = document.getElementById("workTrackMenu");
   var iconMenu = document.getElementById("iconMenu");
   var lineMenu = document.getElementById("lineMenu");		
   var iconLineMenu = document.getElementById("iconLineMenu");
   var subflowMenu = document.getElementById("subflowMenu");
  //�����
   if((window.event.button==0||window.event.button==2)&&(selectActionObj!=null)&&(obj.parentElement.id!="lineId")){
      createActivityObj = null;
	  var menuLeft = event.clientX;
      var menuTop =  event.clientY;

	  //var menuObj = document.getElementsByName("workTrackMenu");
	   if(selectActionObj.actionType!="subflow"){

	    workTrackMenu.style.display = "";
        workTrackMenu.style.left = menuLeft;
		workTrackMenu.style.top = menuTop;
		//�ر�����menu
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
		            //�ر�����menu
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
   //�켣����
  if((window.event.button==0||window.event.button==2)&&(obj.parentElement.id=="lineId")){
        createActivityObj = null;
    	var menuLeft = event.clientX;
        var menuTop =  event.clientY;
		var menuObj;
		if(((obj.parentElement.name.indexOf("start_",0))!=-1)||((obj.parentElement.name.indexOf("end_",0))!=-1)){
	             iconLineMenu.style.display = "";
                 iconLineMenu.style.left = menuLeft;
		         iconLineMenu.style.top = menuTop;
				//�ر�����menu
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
				//�ر�����menu		
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

  //��ʼ������
  if((window.event.button==0||window.event.button==2)&&(obj.name=="textbox")){
		createActivityObj = null;
		var menuLeft = event.clientX;
        var menuTop =  event.clientY;
        iconMenu.style.display = "";
        iconMenu.style.left = menuLeft;
		iconMenu.style.top = menuTop;
        menuIcon  = obj.parentElement;
		//�ر�����menu	
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
  
  //�����λ�����menu
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

//*****************************����Ҽ��¼�          END****************************************


//*****************************�������¼�          START****************************************
function leftMouseEvent(){

  var obj = window.event.srcElement;

  //�����λ�����menu
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
//*****************************�������¼�          END****************************************


//*****************************����������        START****************************
function normalActivityAttribute(){
  var nameObj = document.getElementById("defineActionObjName");
  nameObj.value  = menuAction.name;
  var xtype=menuAction.name.substr(0,5);//���route
  var xtypeturning=menuAction.name.substr(0,12);//���turningpoint
  var xtypenextjoin=menuAction.name.substr(0,9);//���routesplit
  var xtypenextsplit=menuAction.name.substr(0,10);//���routejoin;
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

//*****************************����������       END****************************

//****************************����켣������     START********************************
function lineAttribute(){
var pathinfo =  document.getElementById("pathinfo").value;
var nameObj = document.getElementById("defineLineName");
var lineObj = menuLine.parentElement;
nameObj.value = lineObj.name;
window.showModalDialog(pathinfo+"/workflow/resource/track/extend/line/tansactionAttribut.html",window,"status:0;help:0;dialogWidth=600px;dialogHeight=450px");

}

//*****************************����켣������       END****************************

//****************************���������̵�����     START********************************

//*****************************���������̵�����END****************************


/***********************************����¼�  START ****************************/
//ѡ�񹤾����е�Ԫ��
function engage(){	

  offsetX = window.event.offsetX;//������㣬����ǰTable����ʼλ�õ�X���꣬ͨ��ֻ���ڻ�Ϳ�ʼ������ �ڵ���
  offsetY = window.event.offsetY;//������㣬����ǰTable����ʼλ�õ�Y���꣬ͨ��ֻ���ڻ�Ϳ�ʼ������ �ڵ���

  setSelectObj();
  rightMouseEvent();
  leftMouseEvent();
  new fixLine();//�̶��Ѿ�����������
}

/* ���һ�����󣬲��Ҷ���ò��λ�� */
function setSelectObj() {
    var  selectObj = window.event.srcElement;
	/*�ƶ���ʼͼƬ��������ͼƬ*/
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
//��ѡ�ж����zIndex������
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
   
   new moveLine();//����һ����������ߵ���
   moveActivity();//����һ����������ߵĻ
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

/***********************        ����¼� END  *****************************/










/***********************        ��������  STRAT  *********************************/
function saveProcess(){

	if(this.relationDataObj.value == "" || this.relationDataObj.value == null )
		this.relationDataObj.value = "busstype,ҵ������,,,,;busstip,ҵ����ʾ,,,,;bussid,ҵ�����,,,,;bussurl,ҵ���ַ,,,,;customer,������,,,,;worklisttitle,������ʾ,,,,;";
	
   saveRelation();
  
   TrackItem();
   lineItem();
   var processObj = new editProcess();
   var pageId = (new Date()).valueOf()-1142838336468;
   var length = trackHandle.action.length;
   if(length<1){
   	  alert('�޷����������');
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

	//�ڵ�****************************************8
	var actionExtend = "";
	var startx,starty;
	var endx,endy;
	var actionEditType=xmlblank4+'<Implementation>\n'+xmlblank5+'<No/>\n'+xmlblank4+'</Implementation>\n';//�ڱ༭�����ҳ���ϵ�����
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
	 		if(!confirm('������:'+name+' ��û�ж�����������Ϣ! Ҫ����ת���ɽڵ�?')){
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
	  	 	alert("һ�����������в��ʵ�������Ԫ��!");
	     	return;
	 	}else if(forward.indexOf("start_",0)!=-1){

			
		 	startx = parseint((trackHandle.action[forward.split("&")[0]]).style.left);

		 	starty = parseint((trackHandle.action[forward.split("&")[0]]).style.top);

	   	if((forward.split("&"))[1]=="ZLine,"){Icontype="SIMPLEROUTING";}

       	if((forward.split("&"))[1]=="beenLine,"){Icontype="NOROUTING";}

	    startStr +=xmlblank4+'<ExtendedAttribute Name="StartOfWorkflow" Value="FreeTextExpressionParticipant;'+trueId+';'+startx+';'+starty+';'+Icontype+'"/>\n';
	 }

	 if((tracklink=="null")||(tracklink=="")){
	   alert("һ�����������в��ʵ�������Ԫ��!");
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
	//·��********************************
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



/***********************        ��������  END  *********************************/





/***********************        ��������  START       *************************/
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






/***********************        ��������  END         *************************/










/************************      һЩ������ת��    START     *******************/
 

//���ַ��Ļ�������
function parseint(str){
 var num = str.indexOf("px",0);
 var substr = str.substr(0,num);
 var p = parseInt(substr,10);
 return p;
}

//���ַ��Ļ�������
function parseintPt(str){
 str =str+" ";
 var num = str.indexOf("pt,",0);
 var substr = str.substr(0,num);
 var p = parseInt(substr,10);
 return p;
}
//��ͷ��ָ��λ��
function position(fx,fy,tx,ty,objectwidth,objectheight){

   var rate = 45/110;//�׳��ڸ�
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
				   		//alert("�б����ˣ�");
				   		//return;
				   //}
   
   this.point = new point(objX,objY);
}


//************************************   START**************************************************
//����
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


/*��������ݴ���*/
function relationData(){
 	var pathinfo =  document.getElementById("pathinfo").value;
	window.showModalDialog(pathinfo+"/workflow/resource/track/extend/relationData/relationData.html",window,"status:0;help:0;dialogWidth=350px;dialogHeight=300px");
}

/*�����������*/
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

/*��������*/
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

/*������̵�ͷ����Ϣ*/
editProcess.prototype.processHead = function(){
 var processHeadStr =xmlblank2+'<WorkflowProcess AccessLevel="PUBLIC" Id="'+this.processId+'" Name="'+this.processName+'">\n'
                    +xmlblank3+'<ProcessHeader DurationUnit="D">\n'
                    +xmlblank4+'<Created>'+this.createTime+'</Created>\n'
                    +xmlblank3+'</ProcessHeader>\n';
  return processHeadStr;
}

/*������̵���չ����*/
editProcess.prototype.processHeadExtend = function(){
	var extend =  (this.processExtend).split("|");
	var length = extend.length-1;
	var extendStr = "";
	if(updateNow="false" &&  activitymaxcount!=0 ){
	//������Ǹ��µ�ʱ����ô��Ҫ����������չ���ԣ�����activitymaxcount��=0���ж��ǳ���������������µ��ǻ�û���������¼�trackFactoryʱupdateNow="false"����ʱʶ���Ƿ��Ǹ�����Ҫ��activitymaxcount�Ƿ�Ϊ0
		extendStr +=xmlblank4+'<ExtendedAttribute Name="activitymaxcount" Value="'+activitymaxcount+'"/>\n';
		extendStr +=xmlblank4+'<ExtendedAttribute Name="activitycount" Value="'+activitycount+'"/>\n';
		extendStr +=xmlblank4+'<ExtendedAttribute Name="othercount" Value="'+othercount+'"/>\n';
	}

	for(var i=0;i<length;i++){
		 extendStr +=xmlblank4+'<ExtendedAttribute Name="'+(extend[i].split("&"))[0]+'" Value="'+(extend[i].split("&"))[1]+'"/>\n';
	}
	return extendStr;
}

/*���޸�����ͼʱ��xpdl�ļ��л���������*/
function read(){
  var xpdlRelationData =document.getElementById("xpdlRelationData");
  var relationData = document.getElementById("relationData");
  if(xpdlRelationData!=null){
    relationData.value = xpdlRelationData.value;
  }
}
/*��õ�ǰʱ��*/
function fetchNowTime(){
   var d, s = "";           // ����������
   d = new Date();                           // ���� Date ����
   var year = d.getYear();                         // ��ȡ��ݡ�
   var month = (d.getMonth() + 1)          // ��ȡ�·ݡ�
   var date = d.getDate()                  // ��ȡ�ա�
   var hours = d.getHours()                 //Сʱ
   var minutes = d.getMinutes()              //����
   var seconds = d.getSeconds()             //��
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


