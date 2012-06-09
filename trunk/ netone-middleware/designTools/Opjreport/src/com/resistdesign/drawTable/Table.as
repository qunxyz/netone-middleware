package com.resistdesign.drawTable
{
	import com.resistdesign.DataReport;
	import com.resistdesign.drawTable.resizebox.resizeHbox;
	
	import components.reports.reportmanage;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.geom.Matrix;
	import flash.text.engine.TabAlignment;
	import flash.utils.ByteArray;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Grid;
	import mx.containers.GridRow;
	import mx.controls.Alert;
	import mx.controls.Image;
	import mx.core.DragSource;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.managers.DragManager;
	import mx.states.OverrideBase;
	
	import spark.components.Label;

	public class Table extends Grid
	{
		private var _rows:int;
		private var _cols:int;
		private var _w:Number;
		private var _h:Number;
		private var _merge:Boolean = false;
		public var itemw:Number;
		public var itemh:Number;
		private var _selectitem:Tableitem;
		private var _selectrow:int;
		public var selectcol:int;
		public var _colw:Number;
		public var _colh:Number;
		public var selectitemwidth:Number;
		 
		
		public function get selectrow():int
		{
			return _selectrow;
		}

		public function set selectrow(value:int):void
		{
			_selectrow = value;
			selectitem=((this.getChildAt(_selectrow) as Tablerow).getChildAt(0) as Tableitem);
			
			
			
		}

		public function get selectitem():Tableitem
		{
			return _selectitem;
		}

		public function set selectitem(value:Tableitem):void
		{
//			selectrow=value.itemrow;
////			selectcol=value.itemcol;
//			selectitemwidth=value.width;
			_selectitem = value;
		}

		public function get merge():Boolean
		{
			return _merge;
		}

		public function set merge(value:Boolean):void
		{
			_merge = value;
		}

		public function get h():Number
		{
			return _h;
		}

		public function set h(value:Number):void
		{
			_h = value;
		}

		public function get w():Number
		{
			return _w;
		}

		public function set w(value:Number):void
		{
			_w = value;
		}

		public function get cols():int
		{
			return _cols;
		}

		public function set cols(value:int):void
		{
			_cols = value;
		}

		public function get rows():int
		{
			return _rows;
		}
		
		public function set rows(value:int):void
		{
			_rows = value;
		}
 
		public function Table(rows:int,cols:int,w:Number,h:Number)
		{
			setStyle("horizontalGap",0);
			setStyle("verticalGap",0);
			setStyle("borderThickness",1);
			setStyle("borderSides",["bottom","right"]);
			setStyle("borderStyle","solid");
			setStyle("borderColor",0x000000);
			_rows = rows;
			_cols = cols;
			verticalScrollPolicy ="off";
			horizontalScrollPolicy ="off";

			width = w;
			height = h;

			x =30;
			y =30;
			
			_w = width;
			_h = height;
			createGrid();
		}
		//创建表格
        private function createGrid()
		{   
			
 			var rp:Number = height%_rows;
			if(rp!=0){
				height = height-rp+1;
			}
			var cp:Number = width%_cols;
			if(cp!=0){
				width = width - cp+1;
			}
			var row_h:Number = height/_rows;
			var item_w:Number = width/_cols;
			itemw=item_w;
			itemh=row_h;
		
			var childtype:String;
//			if(DataReport.modeltype){
			for(var i:int=0;i<_rows;i++){
				var tr:Tablerow = new Tablerow(row_h);
 
				for(var j:int=0;j<_cols;j++){
	 
							var item:Tableitem =new  Tableitem(item_w,tr,this);
							item.itemcol=j;
							item.itemrow=i;
							tr.additem(item);
			 
//					item.childtype=childtype;
				}
				addChild(tr);
			}
			
	      var tr:Tablerow = new Tablerow(0);
			for(var j:int=0;j<_cols+1;j++){
				var item:Tableitem =new  Tableitem(item_w,tr,this);
				item.itemcol=j;
				item.itemrow=i;
				tr.additem(item);
				// item.childtype=childtype;
			}
			
			addChild(tr); 
			tr.visible=false; 
	 
			//__报表加的
			
			_colw=item_w;
			_colh=row_h; 
		}
		 
		//单元格内拖拽操作
		//开始
		public function dragSource(event:MouseEvent):void
		{
			var dragInitiator:UIComponent=event.currentTarget as UIComponent;
			
			
			
			var bd :BitmapData = new BitmapData(dragInitiator.width,dragInitiator.height,true,0);
			var m:Matrix = new Matrix();
			bd.draw(dragInitiator, m);
			
			
			
			var dragSource:DragSource=new DragSource();
			
			
			
			dragSource.addData({"x": event.localX, "y":event.localY,"soure":"celldrag"},"celldrag");
			var dragProxy:Image=new Image();
			dragProxy.source=new Bitmap( bd );
			
			DragManager.doDrag(dragInitiator,dragSource,event,dragProxy);
			event.stopPropagation();
			event.stopImmediatePropagation();
		}
		
		
		
		public function insertrow()
		{
		   
			var tr:Tablerow = new  Tablerow(itemh);
//			 addChildAt(tr,selectitem._tr.parent.getChildIndex(this.selectitem._tr));
			row_regulation(tr,(getChildAt(selectrow) as Tablerow));
			
			var array:ArrayCollection= (getChildAt(selectrow) as Tablerow).mergeArray;
			for(var i:int;i<array.length;i++)
			{
				tr.setmerge(array[i].item as Tableitem);
			}
			addChildAt(tr,selectrow);
			this.height+=tr.height;
			this.invalidateDisplayList();
		}
		private function row_regulation(tr:Tablerow,selectrow:Tablerow)
		{
//			Alert.show(selectrow.mergeArray.length.toString());
			var rowspan:int=0;
			for(var j:int=0;j<_cols;j++){
				var item:Tableitem =new  Tableitem(itemw,tr,this);
				rowspan=rowspan+selectrow.getmergerow(j+rowspan);
				item.itemcol=j+rowspan;
				item.setStyle("backgroundColor","#9999E9");
				if(j+rowspan>=_cols)
				{
					break;
				}
				else
				{
				tr.additem(item);
				}
 	
			}
//			Alert.show(rowspan.toString());
		}
		
		
		
		
		public function removerow():void
		{
			 
		
		   try{
			   var _h:Number=getChildAt(selectrow).height;
			   
			   if(selectrow==numChildren-1)
			   {
				   Alert.show("已经到最后一行了");
				   return;
			   }
			   
			   var itrow:Tablerow=   getChildAt(selectrow) as  Tablerow;
			   var removeArray:ArrayCollection=itrow.removemergerow();
			   
			   
			   for(var i:int=0;i<removeArray.length;i++)
			   {
				   var itemrow:Tablerow =  getChildAt(selectrow+1) as  Tablerow;
				   var item:Tableitem=(removeArray[i].item as Tableitem)
//				   item.colSpan= (removeArray[i].item as Tableitem).colSpan;
				   item.rowSpan--;
//				   item.itemcol= (removeArray[i].item as Tableitem).itemcol;
				   
				   itemrow.addChildAt(item,(removeArray[i].item as Tableitem).parent.getChildIndex(removeArray[i].item as DisplayObject)+i);
				  
//				   Alert.show(item.itemcol.toString());
				  
			   }
			   
//			   var array:ArrayCollection= (getChildAt(selectrow) as Tablerow).mergeArray;
//			   Alert.show(array.length.toString());
//			   for(var i:int;i<array.length;i++)
//			   {
//				   (array[i].item as Tableitem).rowSpan--;
//			   }
			   
			   
			   removeChildAt(selectrow);
			  
			   
			   height=height-_h;
			 
		      }
		catch(e:Error)
			  {
				Alert.show("已经到最后一行了");
			  }
		}
		
		public function insertcol():void
		{
		   for(var i:int=0;i<numChildren;i++)
		   {
			  
			   i=i+(this.getChildAt(i) as Tablerow).getmergecol(selectcol,itemw);
			   
		   }
		   _cols++;
		   this.width+=itemw;
		}
		public function removecol():void
		{
			for(var i:int=0;i<numChildren;i++)
			{
				try
				{
				i=i+(this.getChildAt(i) as Tablerow).removemergecol(selectcol);
				}
				catch(e:Error)
				{
					Alert.show("已经到最后一列了");
					break;
				}
			}
			_cols--;
			this.width=width-selectitemwidth;
		}
		
	}
}