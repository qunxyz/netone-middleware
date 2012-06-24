package com.resistdesign.drawTable.resizebox
{
	import com.resistdesign.DataReport;
	
	import mx.containers.VBox;
	import mx.controls.Alert;
	import mx.events.FlexEvent;

	public class ResizeVnote extends VBox
	{
		private var _boxlabel:String;
		
		private var _idx2:int;
		
		
		
		
		public function get idx2():int
		{
			_idx2=this.parent.getChildIndex(this);
			return _idx2;
		}

		public function set idx2(value:int):void
		{
			_idx2 = value;
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
		public function ResizeVnote()
		{
			super();
			this.addEventListener(FlexEvent.CREATION_COMPLETE,init)
			setStyle("borderSides",["left"]);
			setStyle("borderThickness",1);
			setStyle("borderStyle","solid");
			setStyle("borderColor",0x000000);
			setStyle("backgroundColor","#ffffff");
		}
		private function init(e:FlexEvent):void
		{
			
			var idx:int=this.parent.getChildIndex(this);
			var str:String=idx.toString();
			boxlabel=str;
		}
		public function updatebox()
		{
			var idx:int=this.parent.getChildIndex(this);
			var str:String=idx.toString();
			boxlabel=str;
			DataReport.zhongshu=idx;
		}

	}
}