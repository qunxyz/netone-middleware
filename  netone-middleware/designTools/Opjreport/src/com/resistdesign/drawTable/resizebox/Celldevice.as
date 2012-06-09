package com.resistdesign.drawTable.resizebox
{
	import com.resistdesign.drawTable.Table;
	import com.resistdesign.drawTable.Tableitem;
	import com.resistdesign.drawTable.Tablerow;
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.managers.CursorManager;
	import mx.managers.CursorManagerPriority;

	public class Celldevice extends UIComponent
	{
		
		[Embed(source="/com/resistdesign/drawTable/resizebox/imgcursor/cellCursor.gif")]
		private static const CURSOR_MOVE:Class;
		
		
		private static const RESIZE_OLD_POINT:String = "oldPoint";
		
		private var isresize:Boolean=false;
		private var _resizeobj:Object;
		private var ow:Number;
		private var oh:Number;
		private var point:Point;
		public var table:Table;
		
		public function Celldevice()
		{
			super();
			this.visible = true; 
			this.width=4;
			this.graphics.lineStyle(1,0xffffff,0); 
			this.graphics.beginFill( 0xF91111,0.6 ); 
			this.graphics.moveTo(0,0); 
			this.graphics.lineTo(0,20); 
			this.graphics.endFill();
			this.graphics.lineStyle(1,0xffffff,0); 
			this.graphics.beginFill( 0xF91111,0.6 ); 
			this.graphics.moveTo(1,0); 
			this.graphics.lineTo(1,20); 
			this.graphics.endFill();
			this.graphics.lineStyle(1,0xffffff,0); 
			this.graphics.beginFill( 0xF91111,0.6 ); 
			this.graphics.moveTo(2,0); 
			this.graphics.lineTo(2,20); 
			this.graphics.endFill();
			
			this.graphics.lineStyle(1,0x000000); 
			this.graphics.beginFill( 0xF91111,0.6 ); 
			this.graphics.moveTo(3,0); 
			this.graphics.lineTo(3,20); 
			this.graphics.endFill();
	
			Application.application.parent.addEventListener(MouseEvent.MOUSE_MOVE, do_resize);
			
			
			this.addEventListener(MouseEvent.MOUSE_OVER,setcursor);
			this.addEventListener(MouseEvent.MOUSE_OUT,removecursor);
			this.addEventListener(MouseEvent.MOUSE_DOWN,start_resize);

			this.addEventListener(FlexEvent.CREATION_COMPLETE,init);
			
		}

	

		public function get resizeobj():Object
		{
			return _resizeobj;
		}

		public function set resizeobj(value:Object):void
		{
			_resizeobj = value;
		}

		private function init(e:FlexEvent):void
		{
		
			_resizeobj.parent.parent.addEventListener(MouseEvent.MOUSE_UP, end_resize);
			this.table=_resizeobj.table;
		
		}
		private function start_resize(e:MouseEvent):void
		{
			isresize=true;
			ow=_resizeobj.width;
			
		    point= new Point();
			point.x = _resizeobj.mouseX;
			point.y = _resizeobj.mouseY;
			
			point = _resizeobj.localToGlobal(point);
			
			
			
			resizemanage.hbox=resizeobj.parentDocument;
			resizemanage.ridx=(resizeobj as Cellbox).idx2;
			resizemanage.resizeitem=(resizeobj as Cellbox) ;
			resizemanage.isrowresize=false;
			
			
		}
		private function do_resize(e:MouseEvent):void
		{
			if(isresize)
			{
				var newwidth:int=ow+Application.application.parent.mouseX - point.x;
				
				
				if (newwidth>=18)
				{
				_resizeobj.width=newwidth;
				resizemanage.newtablewidth=newwidth-ow+table.width;
				}
			}
		}
		private function end_resize(e:MouseEvent):void
		{
			
		    if(isresize)
			{
				try
				{
					if(!resizemanage.isrowresize)
					{
						for(var i:int;i<table.numChildren;i++)
						{
							var row:Tablerow=(table.getChildAt(i) as Tablerow);
							row.setcolwidth(resizemanage.ridx);
							
						}
						table.width=resizemanage.hbox.width;
					}
				}
				catch(e:Error)
				{
					
				}
			}
			removecur();
			isresize=false;
		}
		
		private function setcursor(e:MouseEvent):void
		{
			
			CursorManager.setCursor(CURSOR_MOVE, CursorManagerPriority.MEDIUM, -9, -9);
		}
		private function removecursor(e:MouseEvent):void
		{
			if(!isresize)
			{
				removecur();
			}
		}
		
		private function removecur():void
		{
			CursorManager.removeCursor(CursorManager.currentCursorID);
		}
		
		
		

	}
}