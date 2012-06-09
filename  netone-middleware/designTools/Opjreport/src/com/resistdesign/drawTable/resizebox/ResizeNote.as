package com.resistdesign.drawTable.resizebox
{
	import flash.utils.Dictionary;
	
	import mx.containers.HBox;
	import mx.controls.Alert;
	import mx.events.FlexEvent;
	
	
	public class ResizeNote extends HBox
	{
		private var _boxlabel:String;
		
		private var _idx2:int;
		
		
		
		
		public function get idx2():int
		{
			_idx2=this.parent.getChildIndex(this);
			return _idx2;
		}

		[Bindable]
		public function get boxlabel():String
		{
			return _boxlabel;
		}

		public function set boxlabel(value:String):void
		{
			_boxlabel = value;
		}

		public function ResizeNote()
		{
			super();
			this.addEventListener(FlexEvent.CREATION_COMPLETE,init)
			setStyle("borderSides",["top"]);
			setStyle("borderThickness",1);
			setStyle("borderStyle","solid");
			setStyle("borderColor",0x000000);
			setStyle("backgroundColor","#ffffff");
			
		}
		
		private function init(e:FlexEvent):void
		{

			var idx:int=this.parent.getChildIndex(this);
			String.fromCharCode(65+idx); 
			var str:String;
			if(idx/25<=1)
			{
				str=String.fromCharCode(65+idx); 
			}
			else
			{
				str=String.fromCharCode(65+idx/25-1)+String.fromCharCode(65+idx%26);
				
			}
			boxlabel=str;
		}
		public function updatebox()
		{
			var idx:int=this.parent.getChildIndex(this);
			String.fromCharCode(65+idx); 
			var str:String;
			if(idx/25<=1)
			{
				str=String.fromCharCode(65+idx); 
			}
			else
			{
				str=String.fromCharCode(65+idx/25-1)+String.fromCharCode(65+idx%26);
				
			}
			boxlabel=str;
		}

	}
}