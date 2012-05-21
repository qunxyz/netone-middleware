package ActionForm.From.com.hitb.component
{
	import ActionForm.ControltreeResouce;
	import ActionForm.From.com.hitb.*;
	import ActionForm.From.com.hitb.event.*;
	import ActionForm.From.com.hitb.util.*;
	import ActionForm.PlanBcontent;
	import ActionForm.kvcomcontent;
	import ActionForm.vivid;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.controls.ComboBox;
	import mx.controls.DataGrid;
	import mx.controls.Label;
	import mx.controls.List;
	import mx.controls.TextInput;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.ClassFactory;
	import mx.core.IFlexDisplayObject;
	import mx.core.UIComponent;
	import mx.events.AdvancedDataGridEvent;
	import mx.events.DataGridEvent;
	import mx.events.ListEvent;
	import mx.managers.PopUpManager;
	public  class Data1 extends Canvas
	{
		[Bindable] 
		 public static var dataGrid:DataGrid;
	    [Bindable]
	     public static var cl:UIComponent=null;
	    [Bindable]
        public static var properties:ArrayCollection=new ArrayCollection();
        [Bindable]
        public static var results:Array=null;
       
       [Bindable]
        public static var str:String=null;
		public function Data1()
		{
		   this.doubleClickEnabled=true;
		   GlobalManager.getInstance().addEventListener(Data1.Event_data,onMOUSEDOWN);
		}
		//弹出扩展属性编辑窗口
		private var titleWin:popextend;
		private var controustree:ControltreeResouce;
	  public function clickevent(event:ListEvent):void  
		{      
	     var col:int=event.columnIndex;
	     var row:int=event.rowIndex;
	     var itemColumn:DataGridColumn=dataGrid.columns[col] as DataGridColumn;
	     var cla:ClassFactory=new ClassFactory();
	      if(col==1){
		     cla.generator=TextInput;		      
		     itemColumn.itemEditor=cla;
		     dataGrid.editedItemPosition={"columnIndex":col,"rowIndex":row};
		   }
	     switch(row){
	      case 10:
		      if(col==1){
		      	
		       cla.generator=ComboBox;
		       cla.properties={"dataProvider":["是","否"]};
		       itemColumn.itemEditor=cla;
		       dataGrid.editedItemPosition={"columnIndex":col,"rowIndex":row};
		      }
	        break;
	      case 11:
	     	if(col==1){
	          cla.generator=ComboBox;
	          cla.properties={"dataProvider":["是","否"]};
	          itemColumn.itemEditor=cla;
	          dataGrid.editedItemPosition={"columnIndex":col,"rowIndex":row};
	     	 }
	        break;
	     case 12:
	     	if(col==1){
	          cla.generator=ComboBox;
	          cla.properties={"dataProvider":["是","否"]};
	          itemColumn.itemEditor=cla;
	          dataGrid.editedItemPosition={"columnIndex":col,"rowIndex":row};
	     	 }
	        break;
	     case 7:   
	     
		       if(String(cl["expand"])==""){
		       	vivid.Expand="";
		        }else{
		        vivid.Expand=cl["expand"];
		         }
		    	titleWin = PopUpManager.createPopUp(this, popextend, true) as popextend; 
	            PopUpManager.centerPopUp(titleWin); 
	           GlobalManager.getInstance().dispatchEvent(new Component(Data1.Event_popextend,cl));
              break;
       case 16:   
             vivid.comp=cl;
             controustree= PopUpManager.createPopUp(this, ControltreeResouce, true) as ControltreeResouce;
                break;
       case 14:
		        if(dataGrid.selectedItem.name=="备选值"&&cl.className.toString()=="LableColourfulea")
		        { 
		          var pb:PlanBcontent=PopUpManager.createPopUp(this, PlanBcontent, true) as PlanBcontent; 
		          PopUpManager.centerPopUp(pb); 
		          GlobalManager.getInstance().dispatchEvent(new Component(Data.Event_popextend,cl));
		        
		        }
		       if(dataGrid.selectedItem.name=="备选值"&& cl.className.toString()=="LableComboBoxKV")
		       { 
		       	var kv:kvcomcontent=new kvcomcontent();
		          if(String(cl["PLANB"])==""){
		          }else{
		            vivid.kvlisting=cl["PLANB"];
		          }
		          PopUpManager.addPopUp(kv,this,true);
		          PopUpManager.centerPopUp(kv as IFlexDisplayObject); 
		          GlobalManager.getInstance().dispatchEvent(new Component(Data.Event_popextend,cl));
		       }
		       if(dataGrid.selectedItem.name=="备选值"&& cl.className.toString()=="LableMulCheckBox")
		       { 
		       	var kv:kvcomcontent=new kvcomcontent();
		          if(String(cl["PLANB"])==""){
		          }else{
		            vivid.kvlisting=cl["PLANB"];
		          }
		          PopUpManager.addPopUp(kv,this,true);
		          PopUpManager.centerPopUp(kv as IFlexDisplayObject); 
		          GlobalManager.getInstance().dispatchEvent(new Component(Data.Event_popextend,cl));
		       }
		        if(dataGrid.selectedItem.name=="备选值"&& cl.className.toString()=="LableRadioGroup")
		       { 
		       	var kv:kvcomcontent=new kvcomcontent();
		          if(String(cl["PLANB"])==""){
		          }else{
		            vivid.kvlisting=cl["PLANB"];
		          }
		          PopUpManager.addPopUp(kv,this,true);
		          PopUpManager.centerPopUp(kv as IFlexDisplayObject); 
		          GlobalManager.getInstance().dispatchEvent(new Component(Data.Event_popextend,cl));
		       }
		   break;
		 case 13:
				 var st:SummaryType=new SummaryType();
		          PopUpManager.addPopUp(st,this,true);
		          PopUpManager.centerPopUp(st as IFlexDisplayObject); 
		          GlobalManager.getInstance().dispatchEvent(new Component(Data.Event_summarytype,cl));
		     break;
	     }
	  }  

	 protected override function createChildren():void
			{   
			    super.createChildren();
			    dataGrid = new DataGrid();
			    var te:List=new List();
			    dataGrid.x = 0;
			    dataGrid.y = 0;  
			    dataGrid.percentHeight=100;
			    dataGrid.percentWidth=100;
			    dataGrid.name="DATA";
				dataGrid.sortableColumns=false; 
			    dataGrid.editable = true;  
				dataGrid.rowHeight=30;
			    dataGrid.doubleClickEnabled=true;
			    dataGrid.dataProvider = properties;	
			    GlobalManager.getInstance().addEventListener("sentTreeSource",displaydata);
			    dataGrid.addEventListener(ListEvent.ITEM_CLICK,clickevent);
			    dataGrid.addEventListener(AdvancedDataGridEvent.ITEM_EDIT_END, onGridEditEnd, false, -51);			  
                addChild(dataGrid);
			}          
              /**
			 * 修改属性值
			 */
            public function displaydata(event:Component):void
            {
            
            }
			public function onGridEditEnd(event:DataGridEvent): void{
				var tf:String = event.itemRenderer.data.value.toString();
		        
				trace(event.rowIndex);
				var value: String = tf;				
				 
                //如果只有一个图标被选择
			    var comp:UIComponent=cl;
				if(comp != null){
				
					switch(event.itemRenderer.data.name.toString()){
						case "ID":
						//	comp["ID"] = value;
							break;
						case "类型":
							break;
						case "高度":
							var hn: Number = Number(value);
							comp.height = hn;
							break;
						case "宽度":
							var wn: Number = Number(value);
							comp.width = wn;
							break;
						case "X坐标":
							var xn: Number = Number(value);
							if(xn < 0) xn = 0;
							comp.x = xn;
							tf = xn + "";
							break;
						case "Y坐标":
							var yn:Number = Number(value);
							if(yn < 0) yn = 0;
							comp.y = yn;
							tf = yn + "";
							break;
				    	case "是否只读":	
				    	     comp["read"]=value;		    	   
							break;
						case "备选值":	
				    	     comp["PLANB"]=value;		    	   
							break;
						case "名字": 
					        Label(comp.getChildByName("lable")).text=value;
					        comp["text"]=value;
						     break;
					   case "行":
				       /*  comp["cross"]=value; */
				          break;
					   case "列":
				      /*    comp["row"]=value; */
				            break;
				       case "扩展脚本":			       
				         comp["expand"]=value;
				          break;
				       case "汇总类型":			       
				          comp["summarytype"]=value;
				          break;
				      case "是否隐藏":			       
				          comp["conceal"]=value;
				          break;
				       case "是否必须":
				          comp["must"]=value;
				          break;
				       case "输入长度":
				          comp["length"]=value;
				          break;
					}
				} 
					 GlobalManager.getInstance().dispatchEvent(new Component(Data.Event_revamp,comp));
			}
			 public function onMOUSEDOWN(Component:GlobalEvent):void{
   		     	properties.removeAll();
   		        cl=UIComponent(Component.getComponent2());
			 	properties.addItem({name: "ID",  value:   Component.getComponent2()["_ID"]});	 	  
				properties.addItem({name: "类型", value:  Component.getComponent2()["_type"]}); 
			    properties.addItem({name: "名字", value: Component.getComponent2()["text"]});
				properties.addItem({name: "高度", value:  Component.getComponent2().height}); 
				properties.addItem({name: "宽度", value:  Component.getComponent2().width}); 
				properties.addItem({name: "X坐标",value:  Component.getComponent2().x});	
				properties.addItem({name: "Y坐标", value: Component.getComponent2().y});

				properties.addItem({name:"扩展脚本", value:Component.getComponent2()["expand"]});
			    properties.addItem({name: "行", value: Component.crow });	
				properties.addItem({name: "列", value: Component.ccloumn});	 
				properties.addItem({name: "是否只读", value: Component.getComponent2()["read"]});
				properties.addItem({name:"是否必须", value:Component.getComponent2()["must"]});
				properties.addItem({name:"是否隐藏", value:Component.getComponent2()["conceal"]});
				properties.addItem({name:"汇总类型", value:Component.getComponent2()["summarytype"]});	
				properties.addItem({name: "备选值", value: Component.getComponent2()["PLANB"]});	
			    properties.addItem({name:"输入长度", value:Component.getComponent2()["length"]});	
 	
				if(Component.getComponent2().className=="LableTextButton"||Component.getComponent2().className=="leLableButtonMultip" ||Component.getComponent2().className=="LableColourfulea"||Component.getComponent2().className=="LablePORTAL"
				||Component.getComponent2().className=="LableComboBoxKV"||Component.getComponent2().className=="LableTextButtonzhuzhiduo"||Component.getComponent2().className=="LableTextButtonzhuzhi"){
					properties.addItem({name:"选择树图", value:Component.getComponent2()["treeProvider"]});	
		         }		
				 str="text";
			}
		   public static var  Event_tree:String="sentTreeSource";
		   public static var  Event_data:String="发送数据";
           public static var  ITEM_CLICK:String="修改数据";
           public static var Event_popextend:String="发生属性";
	 }
	
}
