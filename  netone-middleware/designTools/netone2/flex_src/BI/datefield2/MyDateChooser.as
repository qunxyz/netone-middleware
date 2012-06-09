package BI.datefield2
{
	import mx.controls.DateChooser;
	
	/**
	 * 	重写了的DateChooser
	 *  设置默认中文显示
	 *	添加了时分秒的选择
 	 *  xuwei(2011-11-22)
	 */
	public class MyDateChooser extends DateChooser
	{
		public var times:TimesChoose;
		
		public function MyDateChooser()
		{
			super();
			this.width=320;
			this.height=225;
			this.dayNames=["日","一","二","三","四","五","六"];
			this.monthNames=["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"];
			this.setStyle("fontSize","12");
			times=new TimesChoose();
			times.x=90;
			times.y=195;
			
		}
		
		protected override function createChildren():void
		{
			super.createChildren();
			addChild(times);
		}
		
//		private function setDateTime(event:CalendarLayoutChangeEvent):void
//	   {
//			selectedDate.hours=times.nmsHour.value;
//			selectedDate.minutes=times.nmsMinute.value;
//			selectedDate.seconds=times.nmsSecond.value;
//		}
	}
}