/**
* 为div等添加能够在页面上调整大小的功能
* author: hls  
* date: 2006-10-17
*/
var Resize = {
	resizing : false ,
	synlock : false,
	synlock2 : false,
	lastMouseP : [],
	resizeType : -1 ,
	resizingEle : undefined,
	abResizingEle : undefined,
	eventmap : new Map(),
	
	//设置div等元素可以手动调整大小。
	setResizeAble: function(ele,abele){
		ele = $(ele);
		abele = $(abele);
		if(ele){
			var fbeforeResize = Resize.beforeResize(ele,abele);
			Resize.eventmap.put(ele.id+"mousemove",fbeforeResize);
			var fstartResize = Resize.startResize(ele,abele);
			Resize.eventmap.put(ele.id+"mousedown",fstartResize);
			Event.observe(ele,"mousemove",fbeforeResize);
			Event.observe(ele,"mousedown",fstartResize);
			Event.observe(ele,"mouseout",Resize.changeCursor);
		}
	},
	
	cancelResizeAble: function(ele){
		ele = $(ele);
		var fbeforeResize = Resize.eventmap.get(ele.id+"mousemove");
		var fstartResize = Resize.eventmap.get(ele.id+"mousedown");
		Event.stopObserving(ele,"mousemove",fbeforeResize);
		Event.stopObserving(ele,"mousedown",fstartResize);
		Event.stopObserving(ele,"mouseout",Resize.changeCursor);
		Resize.eventmap.remove(ele.id+"mousemove");
		Resize.eventmap.remove(ele.id+"mousedown");
	},
	
	beforeResize: function(ele,abele){
		return function(){
			if(Resize.resizing){
				return ;
			}
			if(!Resize.synlock){
				Resize.synlock = true ;
				var eoffset = Position.cumulativeOffset(ele);
				var esize = [ele.offsetWidth,ele.offsetHeight];
				var mousep = [Event.pointerX(event),Event.pointerY(event)];
				if(mousep[0]-eoffset[0]<5 ){
					Resize.resizeType = 1 ;
					//document.body.style.cursor = "w-resize";
				}
				else if(eoffset[0]+esize[0]-mousep[0]<5){
					Resize.resizeType = 2 ;
					document.body.style.cursor = "w-resize";
				}
				else{
					document.body.style.cursor = "default";
				}
				Resize.synlock = false;
			}
		}
	},
	
	startResize : function(ele,abele){
		return function(){
			if(document.body.style.cursor == "w-resize"){
				Event.observe(document,"mousemove",Resize.resizeing);
				Event.observe(document,"mouseup",Resize.endResize);
				Resize.lastMouseP = [Event.pointerX(event),Event.pointerY(event)];
				Resize.resizingEle = ele;
				Resize.abResizingEle = abele;
				Resize.resizing = true ;
			}
		}
	},
	
	changeCursor : function(){
		if(Resize.resizing){
			return ;
		}
		document.body.style.cursor = "default";
	},
	
	resizeing : function(){
		if(!Resize.synlock2){
			Resize.synlock2 = true ;	
			var ele = Resize.resizingEle;
			var abele = Resize.abResizingEle;
			var eoffset = Position.cumulativeOffset(ele);
			var esize = [ele.offsetWidth,ele.offsetHeight];
			var mousep = [Event.pointerX(event),Event.pointerY(event)];
			if(abele){
				var abelesize = [abele.offsetWidth,abele.offsetHeight];
			}
			var mouseoffset ;
			if(Resize.resizeType == 2){	
				mouseoffset = mousep[0] - Resize.lastMouseP[0];
			}	
			var nowwidth = ele.offsetWidth + mouseoffset;
			if(abele){
				var abnowwidth = abele.offsetWidth - mouseoffset;
				if(nowwidth > 5 && abnowwidth>5){
					ele.style.width = nowwidth;
					abele.style.width = abnowwidth;
				}
			}
			else{
				if(nowwidth > 5){
					ele.style.width = nowwidth;
				}
			}
			Resize.lastMouseP = mousep ;
			Resize.synlock2 = false;
		}
	},
	
	endResize : function(){
		Resize.resizing = false;
		Resize.synlock = false;
		Resize.synlock2 = false;
		Resize.resizingEle = undefined;
		Resize.abResizingEle = undefined;
		document.body.style.cursor = "default";
		Event.stopObserving(document,"mousemove",Resize.resizeing);
		Event.stopObserving(document,"mouseup",Resize.endResize);
	},
	
	//获取以百分比显示的div的大小。
	getPercentSize : function(ele){
		ele = $(ele);
		if(ele){
			var parentele = ele.parentNode;
			return ((ele.offsetWidth/parentele.offsetWidth*100).toFixed(1)+ "%");
		}
	}
}

