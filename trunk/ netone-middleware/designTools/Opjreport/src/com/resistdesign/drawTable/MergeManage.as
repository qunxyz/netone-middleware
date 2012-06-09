package com.resistdesign.drawTable
{
	import flash.display.DisplayObject;
	import flash.text.engine.TabAlignment;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;

	public class MergeManage
	{

		public static var _startItem:Tableitem;
		public static var _endItem:Tableitem;
		
		public static var startrow:int;
		public static var endrow:int;
		public static var startcol:int;
		public static var endcol:int;
		
		public static var srow:int;
		public static var erow:int;
		public static var scol:int;
		public static var ecol:int;
		
		public static var hasMerge:ArrayCollection;
		public static var isselection:Boolean=false;
		
		public static var table:Table;
		
		[Bindable]
		public static var canmerge:Boolean=true;
		
		public function MergeManage()
		{
			
		}
		
		public static function startitem(startitem:Tableitem)
		{
		 
			clean();
			_startItem=startitem;
			canmerge=false;
			_endItem=null;
			startrow=table.getChildIndex(_startItem.tr as DisplayObject);
			startcol=_startItem.itemcol;
		}
		
		public  static function enditem(enditem:Tableitem)
		{
		
			clean();
			_endItem=enditem;
			canmerge=true;
			endrow=table.getChildIndex(_endItem.tr as DisplayObject);
			endcol=_endItem.itemcol;
			setselection();
		}
		
		public static function setselection()
		{
 
			
		    if(startrow>endrow)
			{
				erow=startrow;
				srow=endrow;
				
			}
			else
			{
				erow=endrow;
				srow=startrow;
					
			}
			
			
			
			if(startcol>endcol)
			{
				scol=endcol;
				ecol=startcol;
			}
			else
			{
				ecol=endcol;
				scol=startcol;
			}
			
//			if(_endItem._asMerge !=null)
//			{
//				ecol+=_endItem._asMerge.colSpan-1;
//				erow+=_endItem._asMerge.rowSpan-1;
//			}
			
			var shouldselect:Boolean=false;
			
//			for(var i:int=srow;i<erow+1;i++)
//			{
//				var k:Boolean=true;
//				for(var j:int=scol;j<ecol+1;j++)
//				{
//					var item:Tableitem=((table.getChildAt(i) as Tablerow).getChildAt(j) as Tableitem);
//					if(item._asMerge !=null && item.isMerge)
//					{
//						//上
//							if(item._asMerge.itemrow>=_startItem.itemrow && item._asMerge.itemrow>=_endItem.itemrow)
//							{   
//								//左
//								if( item._asMerge.itemcol>_startItem.itemcol && item._asMerge.itemcol>=_endItem.itemcol)
//								{
//									if(endrow <(item._asMerge.itemrow+ item._asMerge.rowSpan-1))
//									{
//										if(endcol<(item._asMerge.itemcol+ item._asMerge.colSpan-1))
//										{ 
//											//结束
//											ecol+=item._asMerge.colSpan-1;
//											erow+=item._asMerge.rowSpan-1;
//										}
//										else
//										{
//											//上
//											erow+=(_endItem.itemrow-item._asMerge.itemrow+item._asMerge.rowSpan-1);
//										}
//									}
//									else 
//									{
//										//上左
//										ecol+=(_endItem.itemcol-item._asMerge.itemcol+item._asMerge.colSpan-1);
//									}
//								}
//								else //右
//								{
//									if(endrow<(item._asMerge.itemrow+ item._asMerge.rowSpan-1))
//									{
//										if(startcol<(item._asMerge.itemcol+ item._asMerge.colSpan-1))
//										{
//											erow+=(_endItem.itemrow-item._asMerge.itemrow+item._asMerge.rowSpan-1);
//										}
//										else
//										{
//											erow+=(_endItem.itemrow-item._asMerge.itemrow+item._asMerge.rowSpan-1);
//										}
//									}
//									else 
//									{
//										ecol+=(_endItem.itemcol-item._asMerge.itemcol+item._asMerge.colSpan-1);
//									}
//								}
//							}
//							else   //下
//							{
//								
//							}
//					}
//
//								
//				}
//		
//				
//			}
			toselect();
		}
		
		public static function toselect()
		{
			for(var i:int=srow;i<erow+1;i++)
			{
				
				for(var j:int=scol;j<ecol+1;j++)
				{
					var item:Tableitem=((table.getChildAt(i) as Tablerow).getitemAt(j) as Tableitem);
					try
					{
					item.setStyle("backgroundColor","#8FE3DC");
					}
					catch(e:Error)
					{
						
					}
				}
				
			}
		}
		
		public static function clean()
		{
			for(var i:int=0;i<table.numChildren;i++)
			{
				for(var j:int=0;j<(table.getChildAt(i) as Tablerow).numChildren;j++)
				{
					((table.getChildAt(i) as Tablerow).getChildAt(j) as Tableitem).setStyle("backgroundColor","#ffffff");
				}
			}
		}
		
		public static function mergeItem():void
		{
			
			
			if(_endItem==null)
			{    
				Alert.show("未选中多个单元格");
				return;
			}
			
			var merge_item:Tableitem;
		
		    if(startrow > endrow)
			{
				
				if(startcol>endcol)
				{
					merge_item=_endItem;
				}
				else
				{
					merge_item=_endItem.tr.getChildAt((_endItem.tr.getChildIndex(_endItem as DisplayObject)-Math.abs(startcol-endcol))) as Tableitem;
				}
			}
			else
			{
				if(startcol<endcol)
				{
					merge_item=_startItem;
				}
				else
				{
				
					merge_item=_startItem.tr.getChildAt((_startItem.tr.getChildIndex(_startItem as DisplayObject)-Math.abs(startcol-endcol))) as Tableitem;
				}
			}
			
			
			merge_item.rowSpan=Math.abs(startrow-endrow)+1;
			merge_item.colSpan=Math.abs(startcol-endcol)+1;
			
			
			
			
			var row_idx:int=  table.getChildIndex(merge_item.tr as DisplayObject);
			var item_idx:int = merge_item.tr.getitemCol(merge_item);
			for(var i:int=row_idx;i<(row_idx+merge_item.rowSpan);i++)
			{
				for(var j:int=item_idx+merge_item.colSpan-1;j>item_idx-1;j--)
				{
					var item:Tableitem=((table.getChildAt(i) as Tablerow).getitemAt(j) as Tableitem);	
					
					if(item==merge_item)
					{
						item._asMerge=merge_item;
						(table.getChildAt(i) as Tablerow).setmerge(merge_item);
					}
					else
					{
						try
						{
						(table.getChildAt(i) as Tablerow).removeitemAt(j);
						(table.getChildAt(i) as Tablerow).setmerge(merge_item);
						}
						catch(e:Error)
						{
						}
					}	
				}
			}
			merge_item.visible=true;
			merge_item.isMerge=true;
			table.invalidateDisplayList();
		}
	

	}
}