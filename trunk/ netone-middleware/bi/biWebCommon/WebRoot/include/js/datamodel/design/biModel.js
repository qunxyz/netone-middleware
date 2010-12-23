

var offsetX,offsetY,offsetTop;
var currentNodeID;
var selectActionObj=null;//当前选中的节点对象
var menuAction = null;//在当前节点对象弹出的菜单
var menuLine = null;//在当前线对象弹出的菜单
var menuIcon = null;//在那图片对象弹出的菜单
//////创建折线时所需的参数///////////
var createLineObj = null;//创建轨迹线对象
var createActivityObj = null;//创建活动对象对象
var lineStartName = null;//线开始端的名字
var lineEndName = null;//线结束端的名字
var fixX = null;//固定heightPoint 的纵坐标
var iconObj = null;//开始跟结束的图片对象
//////创建折线时所需的参数///////////

//////////////////创建直线时所需的参数///////////////////
var beenLineObj = null;
var beenLineStartName = null;
var beenLineEndName = null;
//////////////////创建直线时所需的参数///////////////////

/////////////////活动的对象//////////////////////
var trackNextActionName = null;
var trackForwardActionName = null;
/////////////////活动的对象//////////////////////
var actionTrueId = "";
var lineTrueId = "";
var forwardTrackAction = null;//上次点击的活动；
var createLineType = null;
var off = 37;

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


//遍历所有的线对象
function lineItem(){
try{
 var lineobjs  = document.getElementsByName("lineId");
 var length = lineobjs.length;
 var strLines = "";
     trackHandle.line.length = 0;
      for(var i=0;i<length;i++){
       var lineobj = lineobjs[i];
       var  name = lineobj.name;
       
        strLines += name + ",";  //把每一条线放到数组中 dataNode1->dataNode2  代表从节点1到节点2
        
	    trackHandle.line[name] = trackHandle.line[trackHandle.line.length] = lineobj;
        trackHandle.line.length++;
   }
   
   return strLines;
   
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


//遍历所有的节点对象
function TrackItem(){
  
   var actionObj  = document.getElementsByName("dataNode");
   
   return actionObj;
  
}






//****************控制移动节点时与该节点相连的线跟着移动  START *************************************//


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


//该活动连接上一个活动的点(直线移动的下一个接点)
  function toNextBeenLinePoint(nextlinkName,nextActName){
    var nextName = (nextActName.split("&"))[0];
	var nextdObj = trackHandle.action[nextName];
    var fx,fy,tx,ty;
    tx = parseint(nextdObj.style.left)+25;
    //ty = parseint(nextdObj.style.top)-off
    ty = parseint(nextdObj.style.top)-offsetTop - off ;
    fx = parseint(selectActionObj.style.left)+25;
    //fy = parseint(selectActionObj.style.top)-off;
    fy = parseint(selectActionObj.style.top)-offsetTop - off;
    var naPoint = new  position(fx,fy,tx,ty);
	var lineObj = trackHandle.line[nextlinkName];
                  if((selectActionObj!=null)&&(lineObj!=null)){
                   var startPoint = (parseint(selectActionObj.style.left)+25)+","+(parseint(selectActionObj.style.top)-offsetTop-25)+"";
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
 fx = parseint(forwardObj.style.left)+25
 //fy = parseint(forwardObj.style.top)-off
 fy = parseint(forwardObj.style.top)-offsetTop ;
 tx = parseint(selectActionObj.style.left)+25;
 //ty = parseint(selectActionObj.style.top)-off;
 ty = parseint(selectActionObj.style.top)-offsetTop - off;
 
 var fwPoint = new  position(fx,fy,tx,ty);
 var lineObj = trackHandle.line[forlineName];
      if((selectActionObj!=null)&&(lineObj!=null)){
             var lineObj = trackHandle.line[forlineName];
			 var endPoint = fwPoint.point.x+"px,"+fwPoint.point.y+"px";
			 lineObj.children[0].to= endPoint;
	}
  }

//****************控制移动节点时与该节点相连的线跟着移动  END *************************************//



//******************************创建新的图形   START*************************************************

/*------图形创建工厂 ----------------*/
function trackFactory(sort,datasetid,datasetname,dataSourceId){

   this.datasetName = datasetname;    //数据集节点的名称
   this.datasetID = datasetid;        //数据集节点的ID
   this.idNum =  (new Date()).valueOf()//时间对象用于生成ID
   this.dataSourceID = dataSourceId;    //数据源ID
   
   //判断当前节点是否已经存在
   var exist = false;
   
   var nodes = document.getElementsByName('dataNode');
   
   for( var i=0;i<nodes.length;i++){
   
       if(nodes[i].nodeID == datasetid){
       
            alert("当前数据集已经存在!!!");
            
            exist = true;
       }
   }
   
   if( !exist ){
   
	  createLineType = sort;
	 
	  /*创建直线*/
	  if(sort=="beenLine"){
	   this.createTrackBeenLine();
	  }
	  /*创建数据集节点*/
	  if(sort=="dataNode"){
	  this.createDataSetNode();
	  }
  }
}

 /*-------创建直线------------------------*/
trackFactory.prototype.createTrackBeenLine = function(){
   var beenLine = document.createElement("div");
       beenLine.innerHTML ='<div id="lineId" start="null" end="null" condition="" name="" trueName ="" extendContent="" lineTrueId="" depict="" type="beenLine">'
       +'<v:line id="line" style="Z-INDEX:0;POSITION:absolute;cursor:hand;" from="0px,10px" to="0px,0px"' 
	    +'strokecolor="red"><v:Stroke endarrow=\'classic\'/></v:line>'
       +'</div>';
       document.body.appendChild(beenLine);
       beenLineObj = beenLine.children[0];
       
       offsetTop = beenLineObj.offsetTop -55;
       
}
  /*--------创建数据集节点------------------------*/
trackFactory.prototype.createDataSetNode = function(){
   var newAction = document.createElement("span");
   //var actName = "dataNode"+this.idNum;
   //var actName = "dataNode"+this.datasetID;
   var actName = this.datasetName;
   newAction.innerHTML ="<div id='dataNode' nodeID=" +this.datasetID +" nodeName="+ this.datasetName+" dataSourceID="+this.dataSourceID+" filtCondition='' connectColumn='' extendattribute='' name='"+actName+"'isLink='false' "
   +" forward='null' tracklink='null' actionType='tools' deadline='' actionTrueId='"+actName+"' actionExtendAttribute =''  forwardCondition='null' afterCondition ='null' style='position:absolute;visibility: visible;border: 1px " +"solid #FF0066; padding:2px; height:35px; width:75px; left:110px; top:50px;zIndex:10'>"
   +"<table id = 'table2' cellspacing=0 cellpadding=0 width=100% height='100%' " +"style='cursor:hand;font-size:12px;zIndex:50' oncontextmenu='return false' >"
   +"<tr><td id ='ping' name = 'track' align=center bgcolor='#00FF99' >"
   +'<input id="node'+actName+'" name="textarea" type="button" value="'+this.datasetID+'" class=activity>'
   +"</td></tr></table></div>";
   document.body.appendChild(newAction);
   var length = trackHandle.action.length;
   trackHandle.action[actName] = trackHandle.action[length]=new TrackAttribute(newAction.children[0]);
   trackHandle.action.length++;
    document.getElementById("defineActionTrueId").value += actName+"&";
	createActivityObj = newAction.children[0];
}


/*----------控制刚创建的线跟着鼠标走-------*/
function moveLine(){
    this.x = document.body.scrollLeft+event.clientX;
    this.y = document.body.scrollTop+event.clientY;
	if(createLineObj!=null){
       this.moveCreateZLine();
	  }
    if(beenLineObj!=null){
       this.moveCreateBeenLine();
      }
 
}

 

 /*     ------------控制数据集节点跟着鼠标走     ---------------------      */
function moveActivity(){
    var left = event.clientX;
    var top = event.clientY;
    if(createActivityObj!=null){
      createActivityObj.style.left = left-20;
	  createActivityObj.style.top = top-10;
	}
}


/*     ------------控制直线跟着鼠标走     ---------------------      */

moveLine.prototype.moveCreateBeenLine=function(){

   this.x = event.clientX;
   this.y = event.clientY-offsetTop ;
   
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
             //this.fixZLine();
            }
    if((selectActionObj==null)&&(iconObj==null)&&(beenLineObj!=null)&&(obj.parentElement.name!="beenLine")){
             beenLineObj.parentElement.removeChild(beenLineObj);
             beenLineObj = null;
			 return;
     }else if((selectActionObj!=null)&&(beenLineObj!=null)){
             this.fixBeenLine();
	        }
}
 
    /*     ------------控制直线固定　　---------------      */
fixLine.prototype.fixBeenLine = function(){

       
       var x = parseint(selectActionObj.style.left);
	   //var y = parseint(selectActionObj.style.top)-off;
	   var y = parseint(selectActionObj.style.top) -offsetTop - off;
	   
	   //----------select the first node in the line ------------
       if((beenLineObj.start=="null")&&(beenLineObj.end=="null")){
       
           var able = (new judgeNode(selectActionObj,"")).nextNodeIsEnd();
           
	       if(!able){
	       beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   selectActionObj=null;
		   release();
		   alert("此操作无法进行！7");
		   return;
	      }
	      
	      if(selectActionObj.id=="end"||selectActionObj.isLink=="true"){
		   beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   selectActionObj=null;
		   release();
		   alert("此操作无法进行！6");
		   return;
		  }
	      var  point = (x+25)+"px,"+y+"px";
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
		   alert("此操作无法进行! 5");
		   return;
		  }
	      if(selectActionObj.id=="start"||selectActionObj.isLink=="true"){
		   beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   release();
		   alert("此操作无法进行! 4");
		   return;
		  }else
		  if((forwardTrackAction.id=="start")&&(selectActionObj.forward!="null")&&(selectActionObj.forward!="")){
		   forwardTrackAction.isLink = "false";
		   beenLineObj.parentElement.removeChild(beenLineObj);
           beenLineObj = null;
		   release();
		   alert("此操作无法进行!! 3");
		   return;
		  }
	      var fromX = parseint(forwardTrackAction.style.left)+25;
	      //var fromY = parseint(forwardTrackAction.style.top)-off;
	      var fromY = parseint(forwardTrackAction.style.top)-offsetTop - off;
          var positionPoint = new position(fromX,fromY,(x+25),y);
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
		        alert("此操作无法进行!!! 2"); 
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
		        alert("活动不能有汇集转移!!! 1"); 
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
		 
		  
		  //关联线终点的节点数据集ID
		  var toDsId = selectActionObj.nodeID;
		  //关联线终点的节点数据源ID
		  var toDSourceID = selectActionObj.dataSourceID;
		 //关联线起点的节点数据集ID
		  var fromDsId =  forwardTrackAction.nodeID;
		  //关联线起点的节点数据源ID
		  var fromDSourceID = forwardTrackAction.dataSourceID;
		 
          selectActionObj.forward =forwardLink;
		  trackHandle.action[selectActionObj.name]=selectActionObj;
		  forwardTrackAction.tracklink = tracklink;
		  
		  trackHandle.line[beenLineObj.name] = trackHandle.line[trackHandle.line.length] = beenLineObj;
          trackHandle.line.length++;
              
          var toDsName = selectActionObj.nodeName;
          
          var fromDsName = forwardTrackAction.nodeName;
          
		  beenLineObj = null;
		  selectActionObj = null;
		  beenLineStartName = null;
		  forwardTrackAction= null;
		  //lineObj();
		  createLineType = null;
		  }
	   }
	   
	   
	   //弹出节点集关联设置页面
	   window.open("/biWeb/bi/datamodel/design/unitColumn.jsp?toDsName="+toDsName+"&fromDsName="+fromDsName+"","","toolbar=no,location=no,directories=no,status=no,"
  +"menubar=no,scrollbars=no,resizable=no,copyhistory=no,width=350,height=210,left=400,top=300");
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




//*****************************图形删除事件        START****************************************
function deleteEvent(obj){
  // var obj = window.event.srcElement;
    if(obj=="line"){
	 this.deleteTrack();
    }
    if(obj=="action"){
     this.deleteDataNode();
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
//删除数据集节点
deleteEvent.prototype.deleteDataNode = function(){
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
	 } document.getElementById("defineActionTrueId").value=document.getElementById("defineActionTrueId").value.replace(menuAction.actionTrueId+"&","");
     menuAction.parentElement.removeChild(menuAction);
     menuAction = null;
	 menuIcon =null;
  }
  }
}


//*****************************图形删除事件        END****************************************






//*****************************鼠标右键事件        START****************************************
function rightMouseEvent(){
   
   var obj = window.event.srcElement;
   var dataNodeMenu = document.getElementById("dataNodeMenu");
   var iconMenu = document.getElementById("iconMenu");
   var lineMenu = document.getElementById("lineMenu");		
   var iconLineMenu = document.getElementById("iconLineMenu");
   var subflowMenu = document.getElementById("subflowMenu");	//2006-4-3
  //活动对象
   if((window.event.button==0||window.event.button==2)&&(selectActionObj!=null)&&(obj.parentElement.id!="lineId")){
	  createActivityObj = null;
	  var menuLeft = event.clientX;
      var menuTop =  event.clientY;
	  //var menuObj = document.getElementsByName("dataNodeMenu");
	   if(selectActionObj.actionType!="subflow"){
	   
	    //alert(selectActionObj.nodeID);
        document.forms["mainForm"].currentNodeID.value = selectActionObj.nodeID;
        
        //alert(document.forms["mainForm"].currentNodeID.value);
        
	    dataNodeMenu.style.display = "";
        dataNodeMenu.style.left = menuLeft;
		dataNodeMenu.style.top = menuTop;
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
				   	if((dataNodeMenu!=null)){//2006-4-3
			          dataNodeMenu.style.display="none";
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
				if((dataNodeMenu!=null)){
					dataNodeMenu.style.display="none";
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
				if((dataNodeMenu!=null)){
					dataNodeMenu.style.display="none";
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
		if((dataNodeMenu!=null)){
			dataNodeMenu.style.display="none";
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
  
  //点击空位置清除menu
  if((window.event.button==0||window.event.button==2)&&((obj.parentElement.id==null)||(obj.parentElement.id==""))){
		createActivityObj = null;
		if((iconMenu!=null)){
			iconMenu.style.display="none";
		}
		if((dataNodeMenu!=null)){
			dataNodeMenu.style.display="none";
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
  //点击空位置清除menu
  if((window.event.button==1)&&!(obj.parentElement.id=="iconMenu"||obj.parentElement.id=="dataNodeMenu"||obj.parentElement.id=="lineMenu"||obj.parentElement.id=="iconLineMenu"||obj.parentElement.id=="subflowMenu")){
  		var iconMenu = document.getElementById("iconMenu");
  		var dataNodeMenu = document.getElementById("dataNodeMenu");		
		var lineMenu = document.getElementById("lineMenu");		
		var iconLineMenu = document.getElementById("iconLineMenu");	
		var subflowMenu = document.getElementById("subflowMenu");	
		if((iconMenu!=null)){
			iconMenu.style.display="none";
		}
		if((dataNodeMenu!=null)){
			dataNodeMenu.style.display="none";
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



//*****************************定义活动的属性       END****************************


//*********************   显示数据集过滤页面  START   *********************************
function showDataSetFilter(id){

    var datasetid = id;
    
    window.open("/biWeb/bi/datamodel/design/filterCondition.jsp?datasetid="+datasetid,"","toolbar=no,location=no,directories=no,status=no,"
  +"menubar=no,scrollbars=no,resizable=no,copyhistory=no,width=300,height=150,left=350,top=250");
  
}
//************************  显示数据集过滤页面   END  ******************************


//********************  保存数据集过滤    START   *******************************
function saveDataSetFilter(currentDataSetID){

    var filterCondition = document.forms["filterForm"].filtCondition.value;
    
    var nodes = window.opener.document.getElementsByName("dataNode");
    
    var length = nodes.length;
    
    for(var i=0;i<length;i++){
    
        if(nodes[i].nodeID == currentDataSetID){
        
            nodes[i].filtCondition = filterCondition;
             
            }
    }
    
    window.self.close();
   
}

//***********************    保存数据集过滤   END  ******************************


//*********************   保存数据集字段关联信息    START*****************************************

//为了避免关联信息的重复保存，只将关联信息保存在连接线的开始数据集节点中。
function saveUnitColumn(fromDsId,fromDsName,toDsName){

   var fromColumn = document.forms["unitForm"].formColumn.value;
   var toColumn = document.forms["unitForm"].toColumn.value;
   
   //列出当前业务模型下所有的数据集节点对象
  var nodes = window.opener.document.getElementsByName("dataNode");
  
  var length = nodes.length;
  
  //查找连接线的开始数据集节点对象
  for(var i=0;i<length;i++){
  
        if(nodes[i].nodeID == fromDsId){
        
            //如果连接信息为空值 说明这是节点的第一条连接线
            if(nodes[i].connectColumn == "")
        
                nodes[i].connectColumn = fromDsName +"."+fromColumn+"$" + toDsName+"."+toColumn;
            
            else{  //否则，该节点存在多条连接线
            
                //将要被追加的关联信息
                var addConn = toDsName+"."+toColumn; 
                
                if(nodes[i].connectColumn.indexOf(addConn) < 0)
                
                    nodes[i].connectColumn += ","+addConn;
                
            }
             
        }
 }
    
    window.self.close();
    
}

//*********************    保存数据集字段关联信息   END*************************



//****************************定义数据集节点连接线的属性     START********************************
function lineAttribute(){
var nameObj = document.getElementById("defineLineName");
var lineObj = menuLine.parentElement;
nameObj.value = lineObj.name;

// lineObj.name的内容为开始数据节点的名称 +"->" + 结束数据集节点的名称
var tmp = lineObj.name.split('->');

var fromDsName = tmp[0];

var toDsName = tmp[1];

window.open("/biWeb/bi/datamodel/design/unitColumn.jsp?toDsName="+toDsName+"&fromDsName="+fromDsName+"","","toolbar=no,location=no,directories=no,status=no,"
  +"menubar=no,scrollbars=no,resizable=no,copyhistory=no,width=350,height=210,left=400,top=300");
}

//*****************************定义数据集节点连接线的属性       END****************************


/***********************************         鼠标事件  START ****************************/
function engage(){
  offsetX = window.event.offsetX;
  offsetY = window.event.offsetY;
  setSelectObj();
  rightMouseEvent();
  leftMouseEvent();
  new fixLine();//固定创建的线条
 
}
//获得一个对象
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






/**********************      一些参数的转化    START     *******************/
 

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
function position(fx,fy,tx,ty){
   var rate = 35/75;//底除于高
   var rate1 = 1;
   var offY,offX;
   var objX,objY;
    if (fx != tx) {
      rate1 = (fy - ty) / (fx - tx);
      rate1 = rate1 > 0 ? rate1 : -rate1;
    }
    if (rate1 <= rate) {
      if (fx> tx) {
        offX = 38;
		offY=0;
      }
      else if (fx<tx) {
        offX =  - 38;
		offY = 0;
      }
    }
    if (rate1 > rate) {
      if (fy > ty) {
	    offX = 0
        offY = 20;
      }
      else if (fy < ty) {
	    offX = 0
        offY = -20;
      }
    }
   objX = tx + offX;
   objY = ty + offY;
   this.point = new point(objX,objY);
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

/************************       保存已定制的模型对象     START*****************************/

function saveBusModel(){
  
     var datasetList = TrackItem();   //数据集节点列表
     
     var dslength = datasetList.length;
     
     var lines = lineItem();         //遍历每条线
     
     var modelXml = "";
     
     var modelID = document.getElementById("modelID").value;
     
   
     
     
     //指标配置测试数据初始化
     test();
 
     var modelName = document.getElementById("modelName").value;
    
	 modelXml += '<?xml version="1.0" encoding="GB2312"?>\n\n'
	          +'<DataModelObjs>\n\n'
				 +'<DataModelObj modelid="'+modelID+'" modelname="'+modelName+'" extendattribute="extendattribute" description="description">\n\n'
						+'<TargetColumnObjs>\n\n';
						
						var strTargetColumn = "";
						
						  for(var j=0;j<targetcollist.length;j++){
						  
				            var tgcol = targetcollist[j];
						  
							strTargetColumn +='<TargetColumnObj columnId="'+tgcol.tgcolid+'" columnName="'+tgcol.tgcolname+'" belongDatasetId="'+tgcol.datasetid+'" unitedRule="'+tgcol.unitedrule+'" flitconditoins="'+tgcol.extcondi+'"  dataarea="'+tgcol.dataarea+'"  extendattribute="'+tgcol.extattr+'" description="'+tgcol.desc+'"/>\n\n';
							
						  }
						
						 modelXml += strTargetColumn;
						 
						 modelXml +='</TargetColumnObjs>\n\n'
						+'<DataSetObjs>\n\n';
						
	                    var strDataSetObj = "";					
	
						for(var i=0;i<dslength;i++){
						    
						    var dataSetNode = datasetList[i];
						    
						    dataSetNode.extendattribute = "xoffset:"+dataSetNode.offsetLeft+";yOffset:"+dataSetNode.offsetTop+"";
						    
							strDataSetObj +='<DataSetObj datasetId="'+dataSetNode.nodeID+'" datasetName="'+dataSetNode.nodeName+'" dataSourceId="'+dataSetNode.dataSourceID+'" connectColumn="'+dataSetNode.connectColumn+'"  filtCondition="'+dataSetNode.filtCondition+'" extendattribute="'+dataSetNode.extendattribute+'">\n\n';
						}
					
						modelXml += strDataSetObj;
						
						modelXml+='</DataSetObjs>\n\n'
					+'</DataModelObj>\n\n'
				+'</DataModelObjs>\n\n';
					
	
     alert(modelXml);
     
     //以下代码使用ajax提交请求到处理action
     
     
}