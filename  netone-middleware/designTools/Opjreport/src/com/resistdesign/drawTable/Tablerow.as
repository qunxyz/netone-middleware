package com.resistdesign.drawTable
{
	import com.resistdesign.drawTable.resizebox.resizemanage;
	
	import flash.display.DisplayObject;
	import flash.text.engine.TabAlignment;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.containers.GridRow;
	import mx.controls.Alert;
	
	public class Tablerow extends GridRow
	{
		
		public var _size:Number;
		public var _merge:Boolean = false;
		private var _c:Canvas;
		public var mergeArray:ArrayCollection=new ArrayCollection();
		public function Tablerow(h:int=30)
		{
			height = h;
			_size = 0;
			
		}
		
		//行方法
		public function additem(td:Tableitem):void{
			addChild(td);
			_size++;
		}
		
		public function additemAt(td:Tableitem,index:int):void{
			addChildAt(td,index);
			_size++;
		}
		
		
		public function getitemCol(td:Tableitem):int
		{
			var index:int;
			for(var i:int;i<numChildren;i++)
			{
				 if((this.getChildAt(i) as Tableitem)==td)
				 {
					 index= (this.getChildAt(i) as Tableitem).itemcol;
					 break;
				 }
			}
			return index;
		}
		public function getitemAt(col:int):Tableitem
		{
			var item:Tableitem;
			
			for(var i:int;i<numChildren;i++)
			{
				if((this.getChildAt(i) as Tableitem).itemcol==col)
				{
					item=(this.getChildAt(i) as Tableitem);
				}
			}
			return item;
		}
		
		
		public function removeitemAt(col:int):void{
			
			for(var i:int;i<numChildren;i++)
			{
				if((this.getChildAt(i) as Tableitem).itemcol==col)
				{
					removeChildAt(i);
				}
			}
		}
		
		public function removeitem(td:Tableitem):void{
			removeChild(td);
			_size--;
		}
//		合并对象数组
		
		public function setmerge(td:Tableitem):void
		{   
			var havemerge:Boolean=true;
			for (var i:int;i<mergeArray.length;i++)
			{
				if(mergeArray[i].item==td)
				{
					havemerge=false;
				}
			}
		   if(havemerge)
		   {
			   if(mergeArray.length==0)
			   {
				   mergeArray.addItem({item:td});
			   }
			   else
			   {
				   var ismax:Boolean=false;
//				   Alert.show("外部");
					   for (var i:int=0;i<mergeArray.length;i++)
					   {
//						   Alert.show("内部 ");
				           if(td.itemcol<(mergeArray[i].item as Tableitem).itemcol)
						   {		
							   mergeArray.addItemAt({item:td},i);
							   ismax=false;
							   break;
						   }
						   else
						   {
							   ismax=true;
						   }
					   }
					   if(ismax)
					   {
						   mergeArray.addItem({item:td});
					   }
			   }
		   }
		}
		
		
		//行的方法
		public function getmergerow(index:int):int
		{
			var mergecol:int;
			var idx:int=index;
			for (var i:int;i<mergeArray.length;i++)
			{
				 var col:Tableitem=  (mergeArray[i].item as Tableitem)
					 if(col.itemcol==idx && col.parent != (this.parent as Table).selectitem.parent)
					 {
						 mergecol+=col.colSpan;
						 idx+=col.colSpan;
						 col.rowSpan+=1;
					 }
			}
			return mergecol;
		}
		
		public function removemergerow():ArrayCollection
		{
			var mergerry:ArrayCollection=new ArrayCollection();
			for (var i:int;i<mergeArray.length;i++)
			{
				var col:Tableitem=  (mergeArray[i].item as Tableitem);
				if(col.parent == (this.parent as Table).selectitem.parent  && col.rowSpan>1)
				{
					mergerry.addItem({item:col});
					
				}
				else if( col.rowSpan>1)
				{
					col.rowSpan=col.rowSpan-1;
				}
			}
			return mergerry;
		}
		
		
		
		//列的方法
		
		public function setcolwidth(index:int)
		{
			for(var i:int=0;i<numChildren;i++)
			{
				var item:Tableitem=getChildAt(i) as Tableitem;
				if(item.itemcol==index)
				{
					item.width=resizemanage.resizeitem.width;
				}
			}
		}
		
		
		
		
		public function getmergecol(index:int,width:int):int
		{
			var havemerge:Boolean=false;
			var rowspan:int=0;
			var colspan:int=0;
			var itemidx:int=0;
			var mergecol:int=0;
			var merge:Tableitem=null;
			for(var j:int;j<mergeArray.length;j++)
			{
				 var mergeitem:Tableitem= mergeArray[j].item as Tableitem
	
					 
					 if(mergeitem.itemcol<index && (mergeitem.itemcol+mergeitem.colSpan)>index)
					 {
						 mergeitem.colSpan++;
						 merge=mergeitem;
						 havemerge=true;
						 if(mergeitem.rowSpan>1)
						 {
							 rowspan=mergeitem.rowSpan-1;
						 }
						 colspan=index-mergeitem.itemcol;
						 
						 
						 
						 if(mergeitem.parent==this)
						 {
						 itemidx=this.getChildIndex(mergeitem);
						 }
						 else
						 {
							 itemidx=mergeitem.parent.getChildIndex(mergeitem);
						 }
					 }
					 
					 
					 if(merge!=null)
					 {
						 j=mergeArray.length;
					 }
					 else if(((mergeArray[j].item as Tableitem).parent as Tablerow)==this && mergeitem.itemcol<index)
					 {
						 mergecol+=(mergeArray[j].item as Tableitem).colSpan-1;
					 }
					 else if(mergeitem.itemcol<index)
					 {
						 mergecol+=(mergeArray[j].item as Tableitem).colSpan;
					 }
				
			}
			if(!havemerge)
			{
			var item:Tableitem = new Tableitem(width,this,this.parent as Table);
			this..addChildAt(item,index-mergecol);
			item.setStyle("backgroundColor","#9999E9");
			item.itemcol=index;
			
				
			}
			

			for(var i:int=index-colspan+1-mergecol;i<numChildren;i++)
			{
				var item:Tableitem= this.getChildAt(i) as Tableitem;
				item.itemcol=item.itemcol+1;
					
			}
		
		    
			
			
			
			if(rowspan != 0)
			{
			edititemcol(index,colspan,rowspan,merge);
			}
			
			return rowspan;
			
		}
		
		
		private function edititemcol(index:int,colspan:int,rowspan:int,merge:Tableitem)
		{
			
		    var startidx:int=this.parent.getChildIndex(this as DisplayObject);
			var table:Table=this.parent as Table;
			
			for (var j:int=startidx+1;j<startidx+1+rowspan;j++)
			{
				 var row:Tablerow=  table.getChildAt(j) as Tablerow;
				 
				 var mrray:ArrayCollection=row.mergeArray;
				 var mergecol:int=0;

				 for(var k:int=0;k<mrray.length;k++)
				 {
					 if(merge==(mrray[k].item as Tableitem))
					 {
						k=mrray.length;
					 }
					 else if(((mrray[k].item as Tableitem).parent as Tablerow)==row )
					 {
					 mergecol+=(mrray[k].item as Tableitem).colSpan-1;
					 }
					 else
					 {
						 mergecol+=(mrray[k].item as Tableitem).colSpan;
					 }
					 
				 }
//				 
				 
				 
				 for(var i:int=index-colspan-mergecol;i<row.numChildren;i++)
				 {
					 var item:Tableitem= row.getChildAt(i) as Tableitem;
					 item.itemcol=item.itemcol+1;
				 }
			}
			
			
			
			
			
			
			
		}
		
		public function removemergecol(index:int):int
		{
			var havemerge:Boolean=false;
			var rowspan:int=0;
			var colspan:int=0;
			var mergecol:int=0;
			var merge:Tableitem=null;
			var havemergecol:Boolean=false;
			for(var j:int;j<mergeArray.length;j++)
			{
				var mergeitem:Tableitem= mergeArray[j].item as Tableitem
				
				
				if(mergeitem.itemcol <= index && (mergeitem.itemcol+mergeitem.colSpan)>index && mergeitem.parent==this)
				{
					mergeitem.colSpan--;
					merge=mergeitem;
					havemerge=true;
					if(mergeitem.rowSpan>1)
					{
						rowspan=mergeitem.rowSpan-1;
					}
					colspan=index-mergeitem.itemcol;	
					
				}
				
				if(merge!=null)
				{
					j=mergeArray.length;
				}
				else if(((mergeArray[j].item as Tableitem).parent as Tablerow)==this && mergeitem.itemcol<index)
				{
					mergecol+=(mergeArray[j].item as Tableitem).colSpan-1;
				}
				else if(mergeitem.itemcol<index)
				{
					mergecol+=(mergeArray[j].item as Tableitem).colSpan;
				}
				
				
				
			}
			
			if(!havemerge)
			{
				
				this.removeChildAt(index-colspan-mergecol);
				
				for(var q:int=index-colspan-mergecol;q<numChildren;q++)
				{
					var item:Tableitem= this.getChildAt(q) as Tableitem;
					item.itemcol=item.itemcol-1;
					item.setStyle("backgroundColor","#EEECCC");
				}
				
			}
			else
			{
				for(var i:int=index-colspan+1-mergecol;i<numChildren;i++)
				{
					var item:Tableitem= this.getChildAt(i) as Tableitem;
					item.itemcol=item.itemcol-1;
					item.setStyle("backgroundColor","#ECE9E9");
				}
			}
			
			
			if(rowspan != 0)
			{
				removeitemcol(index,colspan,rowspan,merge);
			}
			
			return rowspan;
			
			
			
		}
		
		
		private function removeitemcol(index:int,colspan:int,rowspan:int,merge:Tableitem)
		{
			var startidx:int=this.parent.getChildIndex(this as DisplayObject);
			var table:Table=this.parent as Table;
			
			for (var j:int=startidx+1;j<startidx+1+rowspan;j++)
			{
				var row:Tablerow=  table.getChildAt(j) as Tablerow;
				
				var mrray:ArrayCollection=row.mergeArray;
				var mergecol:int=0;
				
				for(var k:int=0;k<mrray.length;k++)
				{
					if(merge==(mrray[k].item as Tableitem))
					{
						k=mrray.length;
					}
					else if(((mrray[k].item as Tableitem).parent as Tablerow)==row )
					{
						mergecol+=(mrray[k].item as Tableitem).colSpan-1;
					}
					else
					{
						mergecol+=(mrray[k].item as Tableitem).colSpan;
					}
					
				}
				//				 
				
				
				for(var i:int=index-colspan-mergecol;i<row.numChildren;i++)
				{
					var item:Tableitem= row.getChildAt(i) as Tableitem;
					item.itemcol=item.itemcol-1;
					item.setStyle("backgroundColor","#ECE9E9");
				}
			}
		}
		
	}
}