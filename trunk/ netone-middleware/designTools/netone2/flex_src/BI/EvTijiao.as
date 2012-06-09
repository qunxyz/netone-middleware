package  BI
{
	import flash.events.Event;

	public class EvTijiao extends Event
	{   
		private var formcode:String;

		public function EvTijiao(type:String,formcode:String)
		{
			this.formcode=formcode;
			super(type);
		}
		public function Formcode():String{
				return this.formcode;
		 }
	}
}