package ActionForm.From.com.hitb.event
{
	import flash.events.Event;

	public class sendEvent extends Event
	{   
		public var Sendxml:XML;
		public function sendEvent(type:String,Sendxml:XML)
		{    this.Sendxml=Sendxml;
			super(type);
		}
		
		
	}
}