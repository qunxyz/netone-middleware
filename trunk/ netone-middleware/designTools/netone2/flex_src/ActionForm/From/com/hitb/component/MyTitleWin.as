package ActionForm.From.com.hitb.component
{
	import ActionForm.From.com.hitb.*;
	import ActionForm.From.com.hitb.event.*;
	import ActionForm.From.com.hitb.util.*;
	
	import flash.text.TextFormat;
	
	import mx.collections.ArrayCollection;
	import mx.containers.*;
	import mx.core.IFlexDisplayObject;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
   public  class MyTitleWin extends  TitleWindow
	  {
		
		[Bindable]
       public static var properties:ArrayCollection=new ArrayCollection();
       public static var comp:UIComponent;
       public var data1:Data1;
		public function MyTitleWin()
		{
            GlobalManager.getInstance().addEventListener(Data.Event_data,PopeMOUSEDOWN);
		}
		public function PopeMOUSEDOWN(event:GlobalEvent):void{
		  comp=event.getComponent2();
          GlobalManager.getInstance().dispatchEvent(new Component(Data.Event_popextend,comp));
		}
		 protected override function createChildren():void
		  {  
		  	 super.createChildren();
		     this.showCloseButton=true;
		     this.addEventListener(CloseEvent.CLOSE,titleWin_close);
		   /*   this.y=-100; */
		     this.title="控件属性";
		     this.width=400;
             this.height=540;
             this.setStyle("fontSize","15");
             this.setStyle("color","blue");
             var format:TextFormat = new TextFormat();
			 format.color=0x000000;
			 format.size=70;
			 format.font="宋体";
			 this.setStyle("textFormat",format);
		     data1=new Data1();
		     data1.percentHeight=100;
		     data1.percentWidth=100;
	         addChild(data1);
		   }

	    private function titleWin_close(evt:CloseEvent):void { 
	    	  GlobalManager.getInstance().removeEventListener(Data.Event_data,PopeMOUSEDOWN);
	    	  GlobalManager.getInstance().removeEventListener(Data1.Event_data,data1.onMOUSEDOWN);
                PopUpManager.removePopUp(evt.target as IFlexDisplayObject); 
            } 
     }
            
}
