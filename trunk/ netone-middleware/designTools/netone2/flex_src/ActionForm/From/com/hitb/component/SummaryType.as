package    ActionForm.From.com.hitb.component{
	import ActionForm.From.com.hitb.event.Component;
	import ActionForm.From.com.hitb.event.GlobalEvent;
	import ActionForm.From.com.hitb.util.GlobalManager;
	
	import flash.events.MouseEvent;
	
	import mx.containers.Canvas;
	import mx.containers.TitleWindow;
	import mx.controls.Button;
	import mx.controls.ComboBox;
	import mx.controls.Label;
	import mx.controls.TextInput;
	import mx.core.IFlexDisplayObject;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.events.ListEvent;
	import mx.managers.PopUpManager;
	

public class SummaryType extends TitleWindow{
	  public  var  _lable:Label;
	  public var comp:UIComponent=null;
	  public  var  _comboboc:ComboBox;
	  public  var _canvas:Canvas;
	  public  var textinput:TextInput;
	  public var arr:Array=[{key:"sum",value:"总和"},{key:"average",value:"平均"},{key:"max",value:"最大值"}
	  ,{key:"min",value:"最小值"},{key:"count",value:"计数"}]
	  
	  public function SummaryType(){ 
	    GlobalManager.getInstance().addEventListener(Data.Event_summarytype,GetonMOUSEDOWN);
	  }
	  
	   public function GetonMOUSEDOWN(Component1:Component):void{
            comp=Component1.getComponent1();
            _canvas.removeAllChildren();
            if(comp["summarytype"]==null || comp["summarytype"]==""){
            		     textinput=new TextInput();
				         textinput.text="2";
				         textinput.y=0;
				         textinput.x=10;
				         textinput.width=200;
				         textinput.restrict="0-9";
	                  	 _canvas.addChild(textinput);
            }else{
            	var str:String=String(comp["summarytype"]);
                var SrtDate:String=str.substring(str.indexOf("$:")+2,str.indexOf("$!"))
	                if(SrtDate=="max" || SrtDate=="min" || SrtDate=="count"){
	                  for(var i:int=0;i<arr.length;i++){
	                   		if(arr[i].key==SrtDate){
	                   		 _comboboc.selectedIndex=i;
	                   		}
	                  }
	                }else{
	                var StrType:Array=SrtDate.split(".");
	                   for(var i:int=0;i<arr.length;i++){
	                   		if(arr[i].key==StrType[0]){
	                   		 _comboboc.selectedIndex=i;
	                   		}
	                  	}
	                  	var strtext:String=StrType[1] as String;
	                  	 textinput=new TextInput();
				         textinput.y=0;
				         textinput.x=10;
				         textinput.width=200;
				         textinput.restrict="0-9";
	                  	 textinput.text=strtext.substring(strtext.indexOf("(")+1,strtext.indexOf(")"));
	                  	 _canvas.addChild(textinput);
	                }
            }
         }
     protected  override function createChildren():void{
     	super.createChildren();
     	_lable=new Label();
     	_lable.x=5;
     	_lable.y=10;
     	_lable.percentWidth=100;;
     	_lable.height=40;
     	_lable.text="选择类型:";
     	this.addChild(_lable);
     	
     	_comboboc=new  ComboBox();
     	_comboboc.x=100;
     	_comboboc.y=10;
     	_comboboc.width=100;
     	_comboboc.height=30;
     	_comboboc.labelField="value";
     	_comboboc.dataProvider=arr;
     	_comboboc.addEventListener(ListEvent.CHANGE,ChangeType);
     	this.addChild(_comboboc);
     	
     	_canvas=new Canvas();
     	_canvas.height=50;
     	_canvas.percentWidth=260;
     	_canvas.y=70;
     	_canvas.x=0;
     	this.addChild(_canvas);
 
     	var  Button1:Button=new Button();
		Button1.x=145;
		Button1.y=150;
		Button1.width=80;
		Button1.height=35;
		Button1.label="提交";
		Button1.addEventListener(MouseEvent.CLICK,MouseClick);
     	this.addChild(Button1);
     	
     	this.layout="absolute";
     	this.title="汇总类型";
     	this.width=300;
     	this.height=300;
     	this.setStyle("fontSize","18");
     	this.showCloseButton=true;
     	this.addEventListener(CloseEvent.CLOSE,Exit);
     }
     //取消界面
    public function Exit(event:CloseEvent):void{
      PopUpManager.removePopUp(this as  IFlexDisplayObject);
      GlobalManager.getInstance().removeEventListener(Data.Event_summarytype,GetonMOUSEDOWN);
    }
    //  选择框的改变事件
    public function  ChangeType(envet:ListEvent):void{
    	_canvas.removeAllChildren();
      if(_comboboc.selectedItem.key=="sum" || _comboboc.selectedItem.key=="average"){
         textinput=new TextInput();
         textinput.text="2";
         textinput.y=10;
         textinput.x=0;
         textinput.width=200;
          textinput.restrict="0-9";
         _canvas.addChild(textinput);
      } 
    }
    //点击保存事件
    public function  MouseClick(event:MouseEvent):void{
      if(_comboboc.selectedItem.key=="sum"){
       comp["summarytype"]="summarytype$:sum.$float("+textinput.text+");$!";
      }
      if(_comboboc.selectedItem.key=="average"){
       comp["summarytype"]="summarytype$:average.$float("+textinput.text+");$!";
      }
      if(_comboboc.selectedItem.key=="max"){
       comp["summarytype"]="summarytype$:max$!";
      }
      if(_comboboc.selectedItem.key=="min"){
       comp["summarytype"]="summarytype$:min$!";
      }
      if(_comboboc.selectedItem.key=="count"){
       comp["summarytype"]="summarytype$:count$!";
      }
     GlobalManager.getInstance().dispatchEvent(new GlobalEvent(Data.Event_data,comp,comp,comp["_cross"],comp["_row"]));
  	 PopUpManager.removePopUp(this as IFlexDisplayObject); 
  	 GlobalManager.getInstance().removeEventListener(Data.Event_summarytype,GetonMOUSEDOWN);
    }
}
}
