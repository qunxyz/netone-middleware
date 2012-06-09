package com.resistdesign.drawTable.resizebox
{
	import com.resistdesign.drawTable.Table;
	import com.resistdesign.drawTable.TableCav;
	import com.resistdesign.drawTable.resizebox.insertbox.deletecellbtn;
	import com.resistdesign.drawTable.resizebox.insertbox.insertcellbtn;
	
	import mx.events.MoveEvent;
	import mx.events.ResizeEvent;

	public class resizemanage
	{
		public static var ridx:int;
		public static var isrowresize:Boolean;
		public static var resizeitem:Cellbox;
		public static var resizerow:Rowbox;
		public static var newtablewidth:Number;
		public static var selecttable:TableCav;
		public static var _table:Table;
		public static var ilbox:insertcellbtn;
		public static var irbox:insertcellbtn;
		public static var itbox:deletecellbtn;
		public static var hbox:resizeHbox;
		public static var vbox:resizeVbox;
		public static var newcell:Cellbox;
		public static var oldcell:Cellbox;
		
		[Bindable]
		public static var canins:Boolean;
		
		
		public function resizemanage()
		{
		}
		
//		public static function colsetmove(e:MoveEvent)
//		{
//			try
//			{
//				ilbox.x=e.currentTarget.x+30-5;
//				ilbox.insertcellidx=e.currentTarget.idx2;
//				
//				itbox.x=e.currentTarget.x+30+(e.currentTarget.width/2)-5;
//
//				irbox.x=e.currentTarget.x+30+e.currentTarget.width-5;
//				irbox.insertcellidx=e.currentTarget.idx2+1;
//			}
//			catch(e:Error)
//			{}
//		}
	}
}