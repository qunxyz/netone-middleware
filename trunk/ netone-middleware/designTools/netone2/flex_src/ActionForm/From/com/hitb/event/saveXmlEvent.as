package ActionForm.From.com.hitb.event
{
	import flash.events.Event;

	public class saveXmlEvent extends Event
	{   
	    public var flag:Boolean;
		public function saveXmlEvent(type:String,flag:Boolean)
		{   this.flag=flag;
			super(type);
		}
		
	}
}