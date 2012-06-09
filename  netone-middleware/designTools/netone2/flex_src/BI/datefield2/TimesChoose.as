package BI.datefield2
{
	import flash.events.MouseEvent;
	
	import mx.containers.HBox;
	import mx.controls.Label;
	import mx.controls.NumericStepper;
	
	/**
	 * 	重写了的HBox
	 *	实现时分秒的选择
	 * 	Flex群	124616997
	 *  
	 *  @author	QQ825129090	还有不完善之处有待补充,发现bug,有望通知,谢谢
	 */
	public class TimesChoose extends HBox
	{
		public var labHour:Label;
		public var labMinute:Label;
		public var labSecond:Label;
		
		public var nmsHour:NumericStepper;
		public var nmsMinute:NumericStepper;
		public var nmsSecond:NumericStepper;
		
		public function TimesChoose()
		{
			super();
			this.width=200;
			this.height=22;
			this.setStyle("borderStyle","solid");
			this.setStyle("borderThickness","1");
			this.setStyle("horizontalGap","0");
			this.setStyle("verticalGap","0");
			this.setStyle("verticalAlign","middle");
//			this.setStyle("paddingTop","2");
//			this.setStyle("paddingBottom","2");
//			this.setStyle("paddingLeft","0");
//			this.setStyle("paddingRight","0");
		}
		
		protected override function createChildren():void
		{
			super.createChildren();
			
			if(!labHour)
			{
				labHour=new Label();
				labHour.text="时:";
				this.addChild(labHour);	
			}
			
			if(!nmsHour)
			{
				nmsHour=new NumericStepper();
				nmsHour.width=45;
				nmsHour.height=18;
				nmsHour.minimum=0;
				nmsHour.maximum=23;
				nmsHour.setStyle("borderStyle","none");
				nmsHour.setStyle("fontSize","12");
				nmsHour.addEventListener(MouseEvent.CLICK,displayChooser);
				this.addChild(nmsHour);
			}
			
			if(!labMinute)
			{
				labMinute=new Label();
				labMinute.text="分:";
				this.addChild(labMinute);	
			}
			
			if(!nmsMinute)
			{
				nmsMinute=new NumericStepper();
				nmsMinute.width=45;
				nmsMinute.height=18;
				nmsMinute.minimum=0;
				nmsMinute.maximum=59;
				nmsMinute.setStyle("borderStyle","none");
				nmsMinute.setStyle("fontSize","12");
				nmsMinute.addEventListener(MouseEvent.CLICK,displayChooser);
				this.addChild(nmsMinute);
			}
			
			if(!labSecond)
			{
				labSecond=new Label();
				labSecond.text="秒:";
				this.addChild(labSecond);	
			}
			
			if(!nmsSecond)
			{
				nmsSecond=new NumericStepper();
				nmsSecond.width=45;
				nmsSecond.height=18;
				nmsSecond.minimum=0;
				nmsSecond.maximum=59;
				nmsSecond.setStyle("borderStyle","none");
				nmsSecond.setStyle("fontSize","12");
				nmsSecond.addEventListener(MouseEvent.CLICK,displayChooser);
				this.addChild(nmsSecond);
			}
		}
		
		private function displayChooser(event:MouseEvent):void
		{
			if(this.parent is MyDateChooser)
			{
				var dateChooser:MyDateChooser=this.parent as MyDateChooser;
				if(dateChooser.owner is MyDateField)
				{
					var dateField:MyDateField=dateChooser.owner as MyDateField;
					dateField.open();
					(event.currentTarget as NumericStepper).removeEventListener(MouseEvent.CLICK,displayChooser);
				}
			}
		}
	}
}