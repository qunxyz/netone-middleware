package  PolymerizationShow
{
	import flash.events.Event;
	
	import mx.core.UIComponent;

	public class PolymerizeEvent extends Event
	{   
		public var Urlstr:String;
		public function PolymerizeEvent(type:String,urlstr:String)
		{    this.Urlstr=urlstr;
			super(type);
		}
	    public function UrlStr():String{
				return this.Urlstr;
		 }
	}
}