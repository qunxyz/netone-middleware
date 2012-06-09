package com.resistdesign.drawTable.resizebox
{
	
	import com.resistdesign.drawTable.Table;
	import com.resistdesign.drawTable.Tablerow;
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.controls.Alert;
	import mx.core.Application;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.managers.CursorManager;
	import mx.managers.CursorManagerPriority;


	public class Rowdevice extends UIComponent
	{
		
		
		[Embed(source="/com/resistdesign/drawTable/resizebox/imgcursor/rowCursor.gif")]
		private static const CURSOR_MOVE:Class;
		
		private var isresize:Boolean=false;
		private var _resizeobj:Object;
		private var ow:Number;
		private var oh:Number;
		private var point:Point;
		public var table:Table;
		public function Rowdevice()
		{
			super();
			this.visible = true; 
			this.height=4; 
			this.width=20;
			
			
			this.graphics.lineStyle(1,0xF91111,0); 
			this.graphics.beginFill( 0xF91111,0.6 ); 
			this.graphics.moveTo(0,0); 
			this.graphics.lineTo(20,0); 
			this.graphics.endFill();
			
			this.graphics.lineStyle(1,0xF91111,0); 
			this.graphics.beginFill( 0xF91111,0.6 ); 
			this.graphics.moveTo(0,1); 
			this.graphics.lineTo(20,1); 
			this.graphics.endFill();
			
			this.graphics.lineStyle(1,0xF91111,0); 
			this.graphics.beginFill( 0xF91111,0.6 ); 
			this.graphics.moveTo(0,2); 
			this.graphics.lineTo(20,2); 
			this.graphics.endFill();
			
			this.graphics.lineStyle(1,0x000000); 
			this.graphics.beginFill( 0xF91111,0.6 ); 
			this.graphics.moveTo(0,3); 
			this.graphics.lineTo(20,3); 
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
			oh=_resizeobj.height;
			
			point= new Point();
			point.x = _resizeobj.mouseX;
			point.y = _resizeobj.mouseY;
			
			point = _resizeobj.localToGlobal(point);
			
			
			resizemanage.vbox=resizeobj.parentDocument;
			resizemanage.ridx=(resizeobj as Rowbox).idx2;
			resizemanage.isrowresize=true;
			resizemanage.resizerow=(resizeobj as Rowbox) ;
			
			
		}
		private function do_resize(e:MouseEvent):void
		{
			if(isresize)
			{
				var newheight:int=oh+Application.application.parent.mouseY - point.y;
				
				
				if (newheight>=18)
				{
					_resizeobj.height=newheight;
					resizemanage.newtablewidth=newheight-ow+table.height;
				}
			}
		}
		private function end_resize(e:MouseEvent):void
		{
			
			if(isresize)
			{
				try
				{
					if(resizemanage.isrowresize)
					{
					for(var i:int;i<table.numChildren;i++)
					{
						if(i==resizemanage.ridx)
						{
						var row:Tablerow=(table.getChildAt(i) as Tablerow);
						row.height=resizemanage.resizerow.height;
						}
					}
					table.height=resizemanage.vbox.height;
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