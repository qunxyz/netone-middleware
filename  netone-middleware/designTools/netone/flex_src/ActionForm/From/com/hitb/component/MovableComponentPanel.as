package ActionForm.From.com.hitb.component
{
	import ActionForm.From.CustomControl.*;
	import ActionForm.From.com.hitb.event.*;
	import ActionForm.From.com.hitb.util.GlobalManager;
	import ActionForm.vivid;
	
	import flash.display.Shape;
	import flash.events.ContextMenuEvent;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.net.SharedObject;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	import flash.utils.getQualifiedClassName;
	
	import mx.containers.Canvas;
	import mx.controls.TextInput;
	import mx.core.UIComponent;
	import mx.events.DragEvent;
	import mx.managers.CursorManager;
	import mx.managers.CursorManagerPriority;
	import mx.managers.DragManager;
	import mx.managers.PopUpManager;
	public class MovableComponentPanel extends Canvas
	{   
		private var curComponent:UIComponent;
		private var orgPoint:Point=new Point(0,0);
		private var orgX:int;
		private var orgY:int;
	    private var orgWidth:int;
	    private var orgHeight:int;
		private var isAction:Boolean;
	    public var  cross:Number;
		public var  row:Number;
		
		private var newX:int;
		private var newY:int;
		private var newWidth:int;
		private var newHeight:int;
		[Bindable]
		public var sc:XML;
	    [Bindable]
	    public var  pxml:XML;
	    [Bindable]
	    public var  expand:XML;
	    [Bindable]
		public var androidfu:XML;
		[Bindable]
		public var andriodHorizontal:XML;
		[Bindable]
		public var androidsc:XML;
	     [Bindable]
	    public var  androidpxml:XML;
	     [Bindable]
	    public var  androidtext:XML;
	     [Bindable]
	    public var  arrStrin:XML;
	      [Bindable]
	    public var  FileStrin:XML;
	   [Bindable]
	   public var andrionform:Array=new Array();
	    [Bindable]
	    public var count:Array=new Array(); //创建一个 数组保存id
	     [Bindable]
	    public var typename:Array=new Array(); //创建一个 数组保存控件类型
	    [Bindable]
	   public var andrioncomp:Array=new Array();
	    [Bindable]
	    public var androidfather:XML;
	     [Bindable]
	    public var androider:XML;
	     [Bindable]
	   public   var Table:XML;
	    [Bindable]
    	 public  var so:SharedObject;
    	 [Bindable]
         public var crow:int=0;
        [Bindable]
        public var ccolumn:int=0;
    
		[Embed(source="ActionForm/From/assets/mouseMove.gif")]
		private static const CURSOR_MOVE:Class;
		[Embed(source="ActionForm/From/assets/verticalSize.gif")]
		private static const VERTICAL_SIZE:Class;
		[Embed(source="ActionForm/From/assets/horizontalSize.gif")]
		private static const HORIZONTAL_SIZE:Class;
		[Embed(source="ActionForm/From/assets/leftObliqueSize.gif")]
		private static const LEFT_OBLIQUE_SIZE:Class;
		[Embed(source="ActionForm/From/assets/rightObliqueSize.gif")]
		private static const RIGHT_OBLIQUE_SIZE:Class;
		

	    private static var grapWidth: Number = 45;	    
	    private static const SIDE_OTHER:Number = 0;
		private static const SIDE_TOP:Number = 1;
		private static const SIDE_BOTTOM:Number = 2;
		private static const SIDE_LEFT:Number = 4;
		private static const SIDE_RIGHT:Number = 8;
		private static const SIDE_CENTER:Number=16;
		
		public static var mouseMargin:Number = 6;
		private  var currentType:Class = null;
		private  var mouseState:Number = 0;
		public  var defaultCursor:Class = null;
		public  var defaultCursorOffX:Number = 0;
		public  var defaultCursorOffY:Number = 0;
		public var a1:String="<![CDATA[";
        public var a2:String="]]";
		public var a3:String=">";
		private var flag:Boolean=false;
		
		private var shape:Shape;
		[Bindable]
        private var menu:ContextMenu = new ContextMenu();    
		public  function setDefaultCursor(cursor:Class = null, offX:Number = 0, offY:Number = 0):void{
			defaultCursor = cursor;
			defaultCursorOffX = offX;
			defaultCursorOffY = offY;
		}

		protected override function createChildren():void{
		    	super.createChildren();
	            this.contextMenu=menu;		
	 		    shape=new Shape();
	         
			shape.graphics.lineStyle(1,0x00FF00);
			shape.graphics.drawRect(0,0,shape.width,shape.height);
		 /*    this.rawChildren.addChild((this.onInitialize())); */
			this.rawChildren.addChild(shape);
		}
		 
		private  function changeCursor(type:Class, xOffset:Number = 0, yOffset:Number = 0):void{
			if(currentType != type){
				currentType = type;
				CursorManager.removeCursor(CursorManager.currentCursorID);
				if(type != null)
				{
					CursorManager.setCursor(type, CursorManagerPriority.MEDIUM, xOffset, yOffset);
				}
			}
		}
	
		public function MovableComponentPanel()
		{       
			 	this.mouseChildren=true; //确定对象的子项是否支持鼠标。如果对象支持鼠标，则用户可以使用鼠标与其交互。默认值为 true。 
			 	this.mouseEnabled=true;
			    initMenu();
			    
                this.doubleClickEnabled = true;

			    GlobalManager.getInstance().addEventListener(Data.Event_data,pitchup);
			    this.addEventListener(KeyboardEvent.KEY_DOWN,deletemenu);
				this.addEventListener(MouseEvent.MOUSE_DOWN,onMouseDownHandler);
				this.addEventListener(MouseEvent.MOUSE_UP,onMouseUpHandler);
				this.addEventListener(MouseEvent.MOUSE_MOVE,onMouseMove);
				this.addEventListener(MouseEvent.DOUBLE_CLICK,Doubleclick);
				GlobalManager.getInstance().addEventListener(EventTypeConstants.Event_Arrry,arrDataColumn);
				addEventListener(DragEvent.DRAG_ENTER,onDragEnterHandler);
		        addEventListener(DragEvent.DRAG_DROP,onDragDropHandler);
		}

		//键盘上删除的方法
		public function deletemenu(e:KeyboardEvent):void{
		   var keycode:int=e.keyCode; 
          if(keycode==46){
		      if(curComponent!=null)
				  {      			          
		         GlobalManager.getInstance().dispatchEvent(new removeChildren(EventTypeConstants.Event_removeUI,curComponent));
			     this.removeChild(curComponent);
		       }	
		   } 
		   if(keycode==13){
		   	
		     curComponent["PLANB"]=TextInput(curComponent.getChildByName("textinput")).text;
		               GlobalManager.getInstance().dispatchEvent(new GlobalEvent(Data.Event_data,this,curComponent,ccolumn,crow));
		   }
		}
		public function pitchup(Component:GlobalEvent):void{
		 newX= Component.getComponent2().x;
		 newY=Component.getComponent2().y;
	     newWidth=Component.getComponent2().width;
	     newHeight=Component.getComponent2().height;
		  drawBorder();
		}
		//右击在菜单上的方法
		public function initMenu():void
		{
		  var clear:ContextMenuItem=new ContextMenuItem("移除");
			menu.customItems.push(clear);
			clear.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT,clearAction);
		
		}
		//删除控件的
		public function clearAction(event:ContextMenuEvent):void
		{   
		
			  if(curComponent!=null)
		  {      		          
		         GlobalManager.getInstance().dispatchEvent(new removeChildren(EventTypeConstants.Event_removeUI,curComponent));
			     this.removeChild(curComponent);
		   }	
		}
 
		  public var androidarr:Array=new Array();
		 //面板上的数据构造成xml保存到资源里面
		 public function  arrDataColumn(event:saveXmlEvent):void{
		    vivid.AddCount=new Array();
         	androidarr=new Array();
         	 if(sc!=null){     	
       		  delete sc.control;
       	      }
       	      if(androidfu!=null){
       	      androidfu=null;
       	      }
             this.flag=event.flag;
             var TableRow:XML;
            
              if(this.flag){	
             	    for(var k1:int=0; k1<this.numChildren;k1++){
         	         var compstr1:UIComponent=this.getChildAt(k1) as UIComponent;
         	         rule(compstr1);
         	         androidarr[k1]=compstr1;
                	}
         	 
           for(var index:int=0;index<androidarr.length;index++){
                 for(var j:int=0;j<androidarr.length;j++){
                   if(UIComponent(androidarr[index]).y<UIComponent(androidarr[j]).y){
                        var  uicomp:UIComponent;
                        uicomp =androidarr[index];
	                    androidarr[index]=androidarr[j];
	                    androidarr[j]=uicomp;
               		}
               		
               		if(UIComponent(androidarr[index]).y==UIComponent(androidarr[j]).y){
               		   if(UIComponent(androidarr[index]).x<UIComponent(androidarr[j]).x){
	               		    var  uicomp1:UIComponent;
	               		    uicomp1=androidarr[index];
		                    androidarr[index]=androidarr[j];
		                    androidarr[j]=uicomp1;
               		   }
               		}
               }
            }
             vivid.AddCount=androidarr;
		 }
		}
 
		 private function onDragEnterHandler(event:DragEvent):void{
                if(event.dragSource.hasFormat("treeItems")){
                      DragManager.acceptDragDrop(this);
                      
       	       }
         }
       
         private var comarr:Array=new Array();
         [Bindable]
         public var comarrX:Array=new Array(100);
         public var _intx:int=0;
          public  var arr:Array=new Array();
          //这是计算行列
      private  function rule(comrule:UIComponent):void
       {  
       if(comrule!=null)
       	{   
       		 var diff:int=((comrule.y+comrule.height)/50);
             
       	if(((comrule.y+comrule.height)%50)!=0)
       	{ 
       	  diff=diff+1;
       	 
       	  var comcolumn:int= diff*50 ; 
          var gap:int =comcolumn%(comrule.y+comrule.height);    	       
            comrule.y=newY=comrule.y+gap; 
        }  
         crow=diff;
        drawBorder();
         var s:int=0;
         comarr=this.getChildren();
         for(var j:int=0; j<this.numChildren;j++)
           {
              var di:int=((UIComponent(comarr[j]).y+UIComponent(comarr[j]).height)/50);
              
              if(diff==di)
             	{               	
             	arr[s]=	UIComponent(comarr[j]);      
             	s++;              
           }
           }
       for(var i:int=0;i<arr.length;i++)
             {    
           for(var k:int=0; k<arr.length;k++)
             {        		
 
           if(i<arr.length){
           	    
               	if(UIComponent(arr[i]).x<UIComponent(arr[k]).x)
               	{  
                    var hui:UIComponent;
               		hui=arr[i];
                 	arr[i]=arr[k];
                  	arr[k]=hui;
                } 
           }
           }
          
        }
           for(var index:int=0;index<arr.length;index++)
           {
                if(UIComponent(arr[index]).name==comrule.name){
                  ccolumn=index+1;
                 
                   if(index==0){
			            comrule.x=0;
			          }else{ 
               	 comrule.x=UIComponent(arr[index-1]).x+UIComponent(arr[index-1]).width;       
          			} 
                }   
             }
         arr =new Array(); 
           }   
           
              Setproperty(comrule);    
        }     

         public var i:int=3;
         //拖拽到面板上的方法
       private function onDragDropHandler(event:DragEvent):void{
       	 
       	    var items:Array=event.dragSource.dataForFormat("treeItems") as Array;
       	    var xml:XML=items[0] as XML;
       	    var comp:UIComponent=ComponentFactory.getComponent(xml.@data);
       	    comp.x=this.mouseX;
       	    comp.y=this.mouseY;
       	    addChild(comp);
       	    rule(comp);
       	    Setproperty(comp);
			vivid.Control.push(comp);
       	    comp["_ID"]="column"+vivid.ID;
       	    vivid.ID++;
       	   GlobalManager.getInstance().dispatchEvent(new GlobalEvent(EventTypeConstants.Event_ADD,this,comp,ccolumn,crow));
           GlobalManager.getInstance().dispatchEvent(new GlobalEvent(Data.Event_data,this,comp,ccolumn,crow));
           GlobalManager.getInstance().dispatchEvent(new Component(Data.Event_popextend,comp));
           addrow(comp);
       }
        
        /**2    8     4
            ----------
        6   |         | 5
            |         |
        3   ----------   1
                7
          右 下 正方向
          左 上 反方向
        */
        private function onMouseMove(event:MouseEvent):void{
        	var xPos:int=this.mouseX;
            var yPos:int=this.mouseY;
           
            var xPlus:int;
            var yPlus:int;
            if(isAction){
            	xPlus=xPos-orgPoint.x;
            	yPlus=yPos-orgPoint.y;
            
            }
        	if(curComponent!=null && !isAction){
        		if(xPos>=orgX+orgWidth-mouseMargin &&
        		   xPos<=orgX+orgWidth && 
        		   yPos>=orgY+orgHeight-mouseMargin && 
        		   yPos<=orgY+orgHeight){   //右下角
        			changeCursor(LEFT_OBLIQUE_SIZE, -6, -6); 
					mouseState = SIDE_RIGHT | SIDE_BOTTOM;
        		}else if(xPos>=orgX && xPos<=orgX+mouseMargin && yPos>=orgY && yPos<=orgY+mouseMargin){//左上角
        			changeCursor(LEFT_OBLIQUE_SIZE, -6, -6);
        			mouseState=SIDE_LEFT|SIDE_TOP;
        		}else if(xPos>=orgX && xPos<=orgX+mouseMargin && yPos>=orgY+orgHeight-mouseMargin && yPos<=orgY+orgHeight){//左下角
        			changeCursor(RIGHT_OBLIQUE_SIZE, -6, -6);
					mouseState = SIDE_LEFT | SIDE_BOTTOM;
					
        		}else if(xPos>orgX+orgWidth-mouseMargin&&xPos<orgX+orgWidth &&  //右上角
        		         yPos>orgY && yPos<orgY+mouseMargin){
        		         	changeCursor(RIGHT_OBLIQUE_SIZE, -6, -6);
					        mouseState = SIDE_RIGHT | SIDE_TOP;
        		 }else if(xPos>=orgX+orgWidth-mouseMargin && xPos<=orgX+orgWidth){ //右边
        		 	  changeCursor(HORIZONTAL_SIZE, -9, -9);
					  mouseState = SIDE_RIGHT;
        		 }else if(xPos>orgX-mouseMargin && xPos<orgX+mouseMargin){//左边 
        		 	  changeCursor(HORIZONTAL_SIZE, -9, -9);
					  mouseState = SIDE_LEFT;
        		 }else if(yPos>=orgY+orgHeight-mouseMargin && yPos<=orgY+orgHeight){//下边
        		 	changeCursor(VERTICAL_SIZE, -9, -9);
					mouseState = SIDE_BOTTOM;
        		 }else if(yPos>orgY && yPos<orgY+mouseMargin){//上边
        		 	changeCursor(VERTICAL_SIZE, -9, -9);
					mouseState = SIDE_TOP;
        		 }else{
        		 	  if(xPos>orgX && xPos<orgX+orgWidth && yPos<orgY+orgHeight && yPos>orgY){
        		 	 	changeCursor(CURSOR_MOVE,-6,-6);
						mouseState=SIDE_CENTER;
        		 	  }else{
        		 	  	 changeCursor(defaultCursor, defaultCursorOffX, defaultCursorOffY);
					     mouseState = SIDE_OTHER;
        		 	  }
        		 }
        	}
        		 if(isAction){
        		
        		 	switch(mouseState){
        		 		case SIDE_RIGHT | SIDE_BOTTOM:
	        		 		//curComponent.width=orgWidth+xPlus;
							//curComponent.height=orgHeight+yPlus;
						 if(this.mouseX>0 && this.mouseY>0){
							if(this.mouseX>curComponent.x && this.mouseY>curComponent.y){
							newWidth=orgWidth+xPlus;
							newHeight=orgHeight+yPlus;
						
							drawBorder();
							}else{
							  newX=this.mouseX;
							  newY=this.mouseY;
							  newWidth=curComponent.x-this.mouseX;
							  newHeight=curComponent.y-this.mouseY;
							  drawBorder();
							}
							 }
							break;
						case SIDE_LEFT|SIDE_TOP:
//	        				curComponent.x=orgX+xPlus;
//	        				curComponent.y=orgY+yPlus;
//							curComponent.width=orgWidth-xPlus;
//							curComponent.height=orgHeight-yPlus;
 						if(this.mouseX>0 && this.mouseY>0){
                           if(this.mouseX<(curComponent.x+curComponent.width) && this.mouseY<(curComponent.y+curComponent.height) ){
                            newX=orgX+xPlus;
	        				newY=orgY+yPlus;						
	        				newWidth=orgWidth-xPlus;
							newHeight=orgHeight-yPlus;
					
							drawBorder();
                        }else{
                            newX=curComponent.x+curComponent.width;
	        				newY=curComponent.y+curComponent.height;						
	        				newWidth=this.mouseX-newX;
							newHeight=this.mouseY-newY;
                            	
                            drawBorder();
                           } 
							 }
						    break;
					    case SIDE_LEFT | SIDE_BOTTOM:
//							curComponent.width=orgWidth-xPlus;
//							curComponent.height=orgHeight+yPlus;
 						if(this.mouseX>0 && this.mouseY>0){
 						  if(this.mouseY>curComponent.y && this.mouseX<(curComponent.x+curComponent.width)){
						 newX=this.mouseX;
							newWidth=orgWidth-xPlus;
							newHeight=orgHeight+yPlus;
							
							drawBorder();
 								}else{
 						    newX=curComponent.x+curComponent.width;	
 						    newY=this.mouseY;		
 							newWidth=this.mouseX-(curComponent.x+curComponent.width);
							newHeight=this.mouseY-curComponent.y;
							drawBorder();
 								}
 							}
						    break;
					    case SIDE_RIGHT | SIDE_TOP:
//					    	curComponent.width=orgWidth+xPlus;
//					        curComponent.height=orgHeight-yPlus
//					        curComponent.y=orgY+yPlus;
						if(this.mouseX>0 && this.mouseY>0){
						   if(this.mouseX>curComponent.x && this.mouseY<curComponent.y){
					        newWidth=orgWidth+xPlus;
					        newHeight=orgHeight-yPlus
					        newY=orgY+yPlus;
					        
					        drawBorder();}else{
					        
					        
					        }}
					 //      }
					        break;
				        case SIDE_RIGHT:
				            //curComponent.width=orgWidth+xPlus;
				            if(this.mouseX>0 && this.mouseY>0){
				            if(this.mouseX>curComponent.x){
				            newWidth=orgWidth+xPlus;
				         
				            drawBorder();
				            }else{
				             newX=curComponent.x;
				             newY=curComponent.y;
				             newWidth=curComponent.width;
				             newHeight=curComponent.height;
				             drawBorder();
				            }
				            }
					        break;
					    case SIDE_LEFT:
//						  	curComponent.width=orgWidth-xPlus;
//						  	curComponent.x=orgX+xPlus;
 							 if(this.mouseX>0 && this.mouseY>0){
 						if(this.mouseX<(curComponent.x+curComponent.width)){
                            newWidth=orgWidth-xPlus;
						  	newX=orgX+xPlus;
						  	
						  	drawBorder();
 							 	}else{
 							 	 
 							 	  newWidth=orgWidth+xPlus;
				                  
				                  drawBorder();
 							 	}
						  	}
		                    break;
		                case SIDE_BOTTOM:
		                    //curComponent.height=orgHeight+yPlus;
		                    if( this.mouseX>0 && this.mouseY>0){
		             	if(this.mouseY>curComponent.y){ 
		                    newHeight=orgHeight+yPlus;
		                   
		                    drawBorder();
		                       }else{
		                     newX=curComponent.x;
		                     newY=curComponent.y;
		                    newHeight=orgHeight-yPlus;
						    newY=orgY+yPlus;
						  
		                       }
		                    }
			                break;
			            case SIDE_TOP:
//			                curComponent.height=orgHeight-yPlus;
//						    curComponent.y=orgY+yPlus;
                    		if( this.mouseX>0 && this.mouseY>0){
						     if(this.mouseY<(curComponent.y+curComponent.height)){
						     newHeight=orgHeight-yPlus;
						    newY=orgY+yPlus;
						   
						    drawBorder();
						        }else{
						     newX=curComponent.x;
						     newY=curComponent.y;	
						    newHeight=orgHeight+yPlus;
		                 
		                    drawBorder();        
						        }
                    		}
					        break;
					    case SIDE_CENTER:
//					        curComponent.x=orgX+xPlus;
//							curComponent.y=orgY+yPlus;
                            newX=orgX+xPlus;
							newY=orgY+yPlus;
							if(newY<0){
					       	    	newY=0;
					       	    }
					       	    if(newX<0){
					       	     newX=this.mouseY;
					       	    }
							
							drawBorder();
							break;
        		 }
        	
        	}
        }
        
        private function onMouseDownHandler(event:MouseEvent):void{
        	    var point:Point=new Point();
        	    var comp:UIComponent;
		    	point.x=this.mouseX;
		    	point.y=this.mouseY;
		    	this.setFocus();     
		    	//point=this.localToGlobal(point);
		        var o:Array=this.getObjectsUnderPoint(point);
		        var isFound:Boolean;
		        for(var i:int=0;i<o.length;i++){
		        	trace(o[i]);
		        	//var comp:UIComponent=getUIComponent(o[i]);
		        	comp=getUIComponent2(point);
		        	trace(comp);
		        	if(comp){
		        		//comp.drawFocus(true);
		        		curComponent=comp;
		                rule(comp);
		                crow=(curComponent.y/50)+1;
		        	    GlobalManager.getInstance().dispatchEvent(new GlobalEvent(Data.Event_data,this,comp,ccolumn,crow));
		        		isFound=true;
		        		break;
		        	}
		        }
		      if(!isFound){
		         curComponent=null;
		         isAction=false;
		         changeCursor(defaultCursor, defaultCursorOffX, defaultCursorOffY);
		         drawNullBorder();
		      }else{
		      	 isAction=true;
		      	 orgPoint=point;
		      	 newX=orgX=curComponent.x;
		      	 newY=orgY=curComponent.y;
		      	 newWidth=orgWidth=curComponent.width;
		      	 newHeight=orgHeight=curComponent.height;
		      	 drawBorder();
		      }
		      Setproperty(comp);
		     addrow(curComponent);
		     
        }
          //   画图面板，超出的问题解决 
		  private function addrow(comui:UIComponent):void
		    {  
            if(comui!=null){
               var mycrow:int=(comui.y/50)+1;
               var mylastclumn:Number=comui.x+comui.width;
               if(mycrow/10==1)
		       {   
		       	  var  extendHeight:Number=this.height*2;
		       	  this.height=extendHeight;
		       }
		       if(mylastclumn>=this.width)
		       {   var extendwidth:Number=this.width*2;
		           this.width=extendwidth;
		       }
		    }}
        public function Doubleclick(event:MouseEvent):void{
            var point:Point=new Point();
        	    var comp:UIComponent;
		    	point.x=this.mouseX;
		    	point.y=this.mouseY;
		    	this.setFocus();     
		    	//point=this.localToGlobal(point);
		        var o:Array=this.getObjectsUnderPoint(point);
		        var isFound:Boolean;
		        for(var i:int=0;i<o.length;i++){
		        	trace(o[i]);
		        	//var comp:UIComponent=getUIComponent(o[i]);
		        	comp=getUIComponent2(point);
		        	trace(comp);
		        	if(comp){
		        		//comp.drawFocus(true);
		        		curComponent=comp;
		                 rule(comp);
		                 crow=(curComponent.y/50)+1;         
			             var titleWin:MyTitleWin; 
			             titleWin = PopUpManager.createPopUp(this.parent.parent.parent.parent, MyTitleWin, true) as MyTitleWin; 
			             titleWin.y=-200;
			             PopUpManager.centerPopUp(titleWin); 
			           GlobalManager.getInstance().dispatchEvent(new GlobalEvent(Data.Event_data,this,comp,ccolumn,crow));
		        		break;
		        	}
		        }
        }
        //赋值给控件行列
        public function Setproperty(comp:UIComponent):void{
        	  var strname:String=getQualifiedClassName(comp).replace("::",".");
		     if(strname=="ActionForm.From.CustomControl.LableComboBox" ){
                LableComboBox(comp)._cross=ccolumn;
                LableComboBox(comp)._row=crow;
              }          
              if(strname=="ActionForm.From.CustomControl.LableComboBoxKV"){
               LableComboBoxKV(comp)._cross=ccolumn;
                LableComboBoxKV(comp)._row=crow;
              }
				if( strname=="ActionForm.From.CustomControl.LableCheckBox"){
				 LableCheckBox(comp)._cross=ccolumn;
                 LableCheckBox(comp)._row=crow;
				}
				if( strname=="ActionForm.From.CustomControl.LableText"){
				 LableText(comp)._cross=ccolumn;
                LableText(comp)._row=crow;
				}
			  if(strname=="ActionForm.From.CustomControl.LableURL"){
				 LableURL(comp)._cross=ccolumn;
			     LableURL(comp)._row=crow;
				}
				if( strname=="ActionForm.From.CustomControl.LableDateField"){
				   LableDateField(comp)._cross=ccolumn;
                  LableDateField(comp)._row=crow;
				}if( strname=="ActionForm.From.CustomControl.LableEmail"){
				  LableEmail(comp)._cross=ccolumn;
                  LableEmail(comp)._row=crow;
				}if( strname=="ActionForm.From.CustomControl.LablePORTAL"){
				  LablePORTAL(comp)._cross=ccolumn;
                 LablePORTAL(comp)._row=crow;
				}if( strname=="ActionForm.From.CustomControl.LableTextButton"){
				    LableTextButton(comp)._cross=ccolumn;
                    LableTextButton(comp)._row=crow;
				}if(strname=="ActionForm.From.CustomControl.LableTextButtonzhuzhi"){
				    LableTextButtonzhuzhi(comp)._cross=ccolumn;
                    LableTextButtonzhuzhi(comp)._row=crow;
				}if( strname=="ActionForm.From.CustomControl.LableTextButtonzhuzhiduo"){
				   LableTextButtonzhuzhiduo(comp)._cross=ccolumn;
                    LableTextButtonzhuzhiduo(comp)._row=crow; 
				}if(strname=="ActionForm.From.CustomControl.LableTextInput"){
				  LableTextInput(comp)._cross=ccolumn;
                    LableTextInput(comp)._row=crow; 
				}if( strname=="ActionForm.From.CustomControl.LableTextInputIP"){
				    LableTextInputIP(comp)._cross=ccolumn;
                    LableTextInputIP(comp)._row=crow; 
				}if( strname=="ActionForm.From.CustomControl.LableTextLarge"){
				   LableTextLarge(comp)._cross=ccolumn;
                    LableTextLarge(comp)._row=crow; 
				}if( strname=="ActionForm.From.CustomControl.LableTextpersonnel"){
				    LableTextpersonnel(comp)._cross=ccolumn;
                    LableTextpersonnel(comp)._row=crow; 
				}if(strname=="ActionForm.From.CustomControl.LableTextrole"){
				    LableTextrole(comp)._cross=ccolumn;
                    LableTextrole(comp)._row=crow; 
				}if(strname=="ActionForm.From.CustomControl.LableTime"){
				    LableTime(comp)._cross=ccolumn;
                    LableTime(comp)._row=crow; 
				}if(strname=="ActionForm.From.CustomControl.LableTimeField"){
				    LableTimeField(comp)._cross=ccolumn;
                    LableTimeField(comp)._row=crow; 
				}if( strname=="ActionForm.From.CustomControl.leLableButtonMultip"){
				    leLableButtonMultip(comp)._cross=ccolumn;
                    leLableButtonMultip(comp)._row=crow; 
				}if( strname=="ActionForm.From.CustomControl.LableColourfulea"){
				    LableColourfulea(comp)._cross=ccolumn;
                    LableColourfulea(comp)._row=crow; 
				}
				if( strname=="ActionForm.From.CustomControl.Lablezhuzhijigou"){
				     
				    Lablezhuzhijigou(comp)._cross=ccolumn;
                    Lablezhuzhijigou(comp)._row=crow; 
				}
				if( strname=="ActionForm.From.CustomControl.Lablezhuzhijiguoduo"){
				    Lablezhuzhijiguoduo(comp)._cross=ccolumn;
                    Lablezhuzhijiguoduo(comp)._row=crow; 
                    
				}
				if( strname=="ActionForm.From.CustomControl.LableMulCheckBox"){
				    LableMulCheckBox(comp)._cross=ccolumn;
                    LableMulCheckBox(comp)._row=crow; 
                }if( strname=="ActionForm.From.CustomControl.LableButton"){
				    LableButton(comp)._cross=ccolumn;
                    LableButton(comp)._row=crow; 
                }if( strname=="ActionForm.From.CustomControl.LableFile"){
				    LableFile(comp)._cross=ccolumn;
                    LableFile(comp)._row=crow; 
                }if( strname=="ActionForm.From.CustomControl.LableImage"){
				    LableImage(comp)._cross=ccolumn;
                    LableImage(comp)._row=crow; 
                }if( strname=="ActionForm.From.CustomControl.LableRadioGroup"){
				    LableRadioGroup(comp)._cross=ccolumn;
                    LableRadioGroup(comp)._row=crow; 
                }else if(strname=="ActionForm.From.CustomControl.LableTAndF"){
                 	LableTAndF(comp)._cross=ccolumn;
                    LableTAndF(comp)._row=crow; 
                }
        }
         
         private function  drawBorder():void{
         	
            shape.graphics.clear();
		    shape.graphics.lineStyle(2,0x7FCEFF);
		    	    //shape.graphics.moveTo(x,y);
			shape.graphics.drawRect(newX,newY,newWidth,newHeight);
			shape.graphics.endFill();
         }
         
          private function  drawNullBorder():void{
        
            shape.graphics.clear();
         }
       
         public var maxwidth:int=0;
         public var carray:Array=new Array();
         private function onMouseUpHandler(event:MouseEvent):void{
         	 isAction=false;
         	 if(curComponent!=null){
                if(curComponent.width!=newWidth)curComponent.width=newWidth;
         	 	if(curComponent.height!=newHeight)curComponent.height=newHeight;
         	 	if(curComponent.x!=newX)curComponent.x=newX;
                if(curComponent.y!=newY)curComponent.y=newY;
        	     addrow(curComponent);
              
         	} 
         }
       private function getUIComponent(o:Object):UIComponent{
		    	 while(o!=null){
		    	 	if(o.name!=null){
		    	 	   	var d:UIComponent=UIComponent(this.getChildByName(o.name));
		    	 	    if(d){
		    	 		return d;
		    	 	}	
		    	 	}
		    	 	o=UIComponent(o).parentDocument;
		    	 }
		    	 return null;
		    } 
		    
		     private function getUIComponent2(point:Point):UIComponent{
		    	 var array:Array=this.getChildren();
		    	
		    	 for(var i:int=0;i<array.length;i++){
		    	 	  var comp:UIComponent=array[i] as UIComponent;
		    	 	  if(point.x>=comp.x && point.x<=comp.x+comp.width &&
		    	 	     point.y>=comp.y && point.y<=comp.y+comp.height){		    	 	     	
		    	 	     	return comp;
		    	 	     	}
		    	 	
		    	 }
		    	 return null;
		    } 
		  		
        }

}