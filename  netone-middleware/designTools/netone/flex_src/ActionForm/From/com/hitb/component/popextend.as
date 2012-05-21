package ActionForm.From.com.hitb.component
{
	import ActionForm.From.com.hitb.*;
	import ActionForm.From.com.hitb.event.*;
	import ActionForm.From.com.hitb.util.*;
	import ActionForm.vivid;
	
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.containers.*;
	import mx.controls.Alert;
	import mx.controls.Button;
	import mx.controls.Label;
	import mx.controls.TextArea;
	import mx.core.IFlexDisplayObject;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;
   public  class popextend extends TitleWindow
	{
		
	   public var comp:UIComponent=null;
	    public var label1:Label;
	    public   var label2:Label;
	    public var label3:Label;
	    public   var label4:Label;
	    public   var label5:Label;
	    public var TextArea1:TextArea;
	    public var TextArea2:TextArea;
	    public var TextArea3:TextArea;
	    public var TextArea4:TextArea;
	    public var TextArea5:TextArea;
	    [Bindable]
	    public var str1:String="";
	    [Bindable]
	    public var str2:String="";
	    [Bindable]
	    public var str3:String="";
	    [Bindable]
	    public var str4:String="";
	    [Bindable]
	    public var str5:String="";
		[Bindable]
       public static var properties:ArrayCollection=new ArrayCollection();
		public function popextend()
		{
            GlobalManager.getInstance().addEventListener(Data.Event_popextend,GetonMOUSEDOWN);
		}
	  public function GetonMOUSEDOWN(Component1:Component):void{
            comp=Component1.getComponent1();
         }
		 protected override function createChildren():void
		  {    super.createChildren();
		  if(vivid.Expand==""){
		  str1="";
		  str2="";
		  str3="";
		  str4="";
		  str5="";
		  }else{
		   var arr:Array=String(vivid.Expand).split("$!"); 
		   for(var i:int=0;i<arr.length;i++){
		   	var arrs:Array=arr[i].toString().split("$:");
		   	if(arrs[0]=="initscript"){
		   	 str1=arr[i].toString().substr(12,arr[i].toString().length);
		   	}
		   	if(arrs[0]=="blurscript"){
		    str2=arr[i].toString().substr(12,arr[i].toString().length);
		   	}
		   	 if(arrs[0]=="focusscript"){
		   	 str3=arr[i].toString().substr(13,arr[i].toString().length);
		   	}
		   	if(arrs[0]=="checkrule"){
		   	 str4=arr[i].toString().substr(11,arr[i].toString().length);
		   	}
		    if(arrs[0]=="onchangescript"){
		   	 str5=arr[i].toString().substr(16,arr[i].toString().length);
		   	}
		   }
		  }
		    this.showCloseButton=true;
           this.addEventListener(CloseEvent.CLOSE,titleWin_close);
           this.title="扩展属性";		
           this.layout="absolute";
           this.x=100;
           this.y=100;
		   this.width=700;
           this.height=434;
           this.setStyle("fontSize","18");
           label1=new Label();
           label1.x=10;
           label1.y=20;
           label1.text="初 始 化 事 件";
           label1.height=41;
           label1.width=136;
           
            label2=new Label();
           label2.x=10;
           label2.y=80;
           label2.text="失去焦点事件";
           label2.height=41;
           label2.width=136;
           
          
           
           
           label3=new Label();
           label3.x=10;
           label3.y=160;
           label3.text="获得焦点事件";
           label3.height=41;
           label3.width=136;
         
           label4=new Label();
           label4.x=10;
           label4.y=220;
           label4.text="检 验 格 式"; 
           label4.height=41;
           label4.width=136;
           label5=new Label();
           label5.x=10;
           label5.y=280;
           label5.text="数据改变事件";
           label5.height=41;
           label5.width=136;
           
           TextArea1=new TextArea();
           TextArea1.id="chushihua";
           TextArea1.x=label1.width+label1.x;
           TextArea1.y=20;
           TextArea1.text=str1;
           TextArea1.setStyle("fontSize","14");
           TextArea1.width=450;
           TextArea1.height=53;
           
            TextArea2=new TextArea();
           TextArea2.id="siqujiaodain";
           TextArea2.x=label2.width+label2.x;
           TextArea2.y=83;
           TextArea2.text=str2;
           TextArea2.setStyle("fontSize","13");
           TextArea2.width=450;
           TextArea2.height=53;
           
             TextArea3=new TextArea();
           TextArea3.id="huoqujiaodain";
           TextArea3.x=label3.width+label3.x;
           TextArea3.y=143;
           TextArea3.text=str3;
           TextArea3.setStyle("fontSize","13");
           TextArea3.width=450;
           TextArea3.height=53;
		 
		    TextArea4=new TextArea();
           TextArea4.id="yanzhanggeishi";
           TextArea4.x=label4.width+label4.x;;
           TextArea4.y=204;
           TextArea4.text=str4;
           TextArea4.setStyle("fontSize","13");
           TextArea4.width=450;
           TextArea4.height=53;
           
           TextArea5=new TextArea();
           TextArea5.id="shujubianhua";
           TextArea5.x=label5.width+label5.x;;
           TextArea5.y=263;
           TextArea5.text=str5;
           TextArea5.setStyle("fontSize","13");
           TextArea5.width=450;
           TextArea5.height=53;
           
		var  Button1:Button=new Button();
		Button1.x=378;
		Button1.y=325;
		Button1.label="提交";
		Button1.width=80;
		Button1.height=35;
		Button1.addEventListener(MouseEvent.CLICK,tijiao);
           addChild(label1);
           addChild(label2);
           addChild(label3);
           addChild(label4);
           addChild(label5);
           addChild(TextArea1);
           addChild(TextArea2);
           addChild(TextArea3);
           addChild(TextArea4);
           addChild(TextArea5);
           addChild(Button1);
   
	    }
	    private function titleWin_close(evt:CloseEvent):void { 
                PopUpManager.removePopUp(evt.target as IFlexDisplayObject); 
                GlobalManager.getInstance().removeEventListener(Data.Event_popextend,GetonMOUSEDOWN);
            } 
            
          public function  tijiao(event:MouseEvent):void{
          	vivid.Expand="";
         	var popexStr:String=new String();
         	if(TextArea1.text!=""){
             popexStr=popexStr+"initscript$:"+TextArea1.text+"$!;";
         	}
         	if(TextArea2.text!=""){
             popexStr=popexStr+"blurscript$:"+TextArea2.text+"$!;";
         	}
           if(TextArea3.text!=""){
             popexStr=popexStr+"focusscript$:"+TextArea3.text+"$!;";
         	}
          if(TextArea4.text!=""){
             popexStr=popexStr+"checkrule$:"+TextArea4.text+"$!;"  ;
         	}
        	if(TextArea5.text!=""){
             popexStr=popexStr+"onchangescript$:"+TextArea5.text+"$!;";
         	}
         	comp["expand"]=popexStr;
          GlobalManager.getInstance().dispatchEvent(new GlobalEvent(Data.Event_data,comp,comp,comp["_cross"],comp["_row"]));
          PopUpManager.removePopUp(this as IFlexDisplayObject); 
          GlobalManager.getInstance().removeEventListener(Data.Event_popextend,GetonMOUSEDOWN);
         }
       }
}