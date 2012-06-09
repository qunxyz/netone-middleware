package BI.datefield2
{
	import mx.controls.DateField;
	import mx.core.ClassFactory;
	import mx.formatters.DateFormatter;
	
	/**
	 * 	重写了的DateField
	 *  设置默认中文显示
	 *	添加了时分秒的选择
	 *  xuwei(2011-11-22)
	 */
	public class MyDateField extends DateField
	{
		public var chooser:MyDateChooser;
		
		public function MyDateField()
		{
			super();
			this.formatString="YYYY-MM-DD";
			this.dayNames=["日","一","二","三","四","五","六"];
			this.monthNames=["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"];
			this.dropdownFactory=new ClassFactory(MyDateChooser);
			this.editable=true;
			this.labelFunction=formatDate;
		}
		
		private function formatDate(currentDate:Date):String
		{
//			var chooser:MyDateChooser=this.dropdown as MyDateChooser;
			if(!chooser)
			{
				chooser=this.dropdown as MyDateChooser;
			}
			
			if(chooser.times.nmsHour)
			{
				currentDate.hours=chooser.times.nmsHour.value;
				currentDate.minutes=chooser.times.nmsMinute.value;
				currentDate.seconds=chooser.times.nmsSecond.value;
			}
			
			var df:DateFormatter = new DateFormatter();
			df.formatString = "YYYY-MM-DD JJ";
			
			var times:String=df.format(currentDate);
			return times;
		}
		
	}
}