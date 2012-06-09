package com.resistdesign.drawTable
{
	import com.resistdesign.ColorPicker;
	import com.resistdesign.Component;
	import com.resistdesign.DataReport;
	import com.resistdesign.EvTijiao;
	import com.resistdesign.GlobalManager;
	
	import components.reports.reportmanage;
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.text.engine.TabAlignment;
	
	import mx.containers.Box;
	import mx.containers.GridItem;
	import mx.containers.GridRow;
	import mx.controls.Alert;
	import mx.controls.Button;
	import mx.controls.Label;
	import mx.controls.TextInput;
	import mx.core.IFlexDisplayObject;
	import mx.core.IUIComponent;
	import mx.core.UIComponent;
	import mx.managers.PopUpManager;
	
	import spark.primitives.Rect;
    
	public class Tableitem  extends GridItem
	{
		private var _tr:Tablerow;
		public var _table:Table;
		public var _asMerge:Tableitem=null;
		private var _isMerge:Boolean=false;
		private var _itemrow:int;
		
		private var _itemcol:int;
		
		public var itemchild:DisplayObject;
        

		private var _childtype:String;
		private var _havechild:Boolean=false;
		private var itlabel:Label;
 
		public function get tr():Tablerow
		{
			_tr=this.parent as Tablerow
			return _tr;
		}

		public function set tr(value:Tablerow):void
		{
			_tr = value;
		}

		public function get havechild():Boolean
		{
			return _havechild;
		}

		public function set havechild(value:Boolean):void
		{
			_havechild = value;
		}

		public function get childtype():String
		{
			_childtype=initem.childtyp;
			return _childtype;
		}

		

		public function get itemcol():int
		{
			return _itemcol;
		}

		public function set itemcol(value:int):void
		{
			_itemcol = value;
//			itlabel.text="."+_itemcol.toString();
			
		}

		public function get itemrow():int
		{
			var itemrow:int=this._tr.parent.getChildIndex(this._tr);
			return itemrow;
		}

		public function set itemrow(value:int):void
		{
			_itemrow = value;
		}

		public function get isMerge():Boolean
		{
			return _isMerge;
		}
		
		public function set isMerge(value:Boolean):void
		{
			_isMerge = value;
		}
 
		public var initem:TableRader;
			public function Tableitem(w:int,tr:Tablerow,table:Table)
			{
				
				setStyle("borderSides",["left","top"]);
				setStyle("borderThickness",1);
				setStyle("borderStyle","solid");
				setStyle("borderColor",0x000000);
				setStyle("backgroundColor","#ffffff");
				setStyle("horizontalAlign","center");
				setStyle("verticalAlign","middle");
				
				width = w;
				_tr = tr;
			
				_table = table;
				verticalScrollPolicy ="off";
				horizontalScrollPolicy ="off";
				this.doubleClickEnabled=true;
				addEventListener(MouseEvent.MOUSE_DOWN,mouseDownHandler);
				addEventListener(MouseEvent.MOUSE_OVER,mouseOverHandler);
				addEventListener(MouseEvent.DOUBLE_CLICK,mouseDoubleclick);
				addEventListener(MouseEvent.CLICK,mouseclick);
				addEventListener(MouseEvent.MOUSE_UP,mouseUpHandler);
				addEventListener(MouseEvent.MOUSE_OUT,mouseOutHandler);
				
			    
			
				 initem =new TableRader();
				 initem.name="initem";
				this.addChild(initem);
			
 
			}
			
	
			
	
		private function mouseDownHandler(e:MouseEvent):void{
			
//			Alert.show(this.initem.childtyp);
			
			
			MergeManage.table=_table;
			
			_table.merge=true;
			_table.selectitem=this; 
			MergeManage.isselection=false;
			MergeManage.startitem(this); 
				
		}
		//单元格单击事件
		public function mouseclick(e:MouseEvent):void{
			var comp:UIComponent=e.target as UIComponent; 
		 
			if(comp.parent.parent.parent.parent.name=="table" ){
		 GlobalManager.getInstance().dispatchEvent(new Component(DataReport.Evnt_addCLICK,comp,""));
	  		}
			if(comp.parent.parent.parent.parent.name=="tablewei"){
				GlobalManager.getInstance().dispatchEvent(new Component(DataReport.Evnt_Addclickwei,comp,""));
			}
		}
		//单元格双击时间内
		private function mouseDoubleclick(e:MouseEvent):void{
			
			var comp:UIComponent=e.target as UIComponent; 
			DataReport.comp=comp;
			 GlobalManager.getInstance().addEventListener(DataReport.Evnt_Data,Datareturn);  
  			if( comp.parent.parent.parent.parent["sqlstr"]=="-NULL-" || comp.parent.parent.parent.parent["sqlstr"]=="" || comp.parent.parent.parent.parent["focdstr"]=="" ){
						 Alert.show("选择表单");
					 }else{
						 DataReport.type="dyrecord"; 
						 DataReport.focdstr=comp.parent.parent.parent.parent["focdstr"];
						 var cp1:ColorPicker=new ColorPicker();
						 PopUpManager.addPopUp(cp1,comp.parent.parent.parent.parent.parent,true);
			             PopUpManager.centerPopUp(cp1 as IFlexDisplayObject);
		             }
			 
		}
		 public function Datareturn(event:EvTijiao):void{
		  GlobalManager.getInstance().dispatchEvent(new Component(DataReport.Evnt_select,DataReport.comp,event.Formcode()));
		} 
		
		private function mouseOverHandler(e:MouseEvent):void{
			  if(_table.merge && MergeManage._startItem !=this )
			   { 
				   MergeManage.isselection=true;
			       MergeManage.enditem(this); 
			   }
			  else  if( MergeManage.isselection)
			  {
				  
			  }
			   else
			   {
				this.setStyle("backgroundColor","#8FE3DC");
			   }

		}
		
		//鼠标移出
		private function mouseOutHandler(e:MouseEvent):void{
			if(_table.merge || MergeManage.isselection)
			{
			}
			else
			{
				this.setStyle("backgroundColor","#ffffff");
			}

		}
		
		private function mouseUpHandler(e:MouseEvent):void{
			_table.merge=false;
		}
		
		
	}
}