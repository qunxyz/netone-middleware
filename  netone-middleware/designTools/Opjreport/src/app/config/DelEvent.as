package   app.config
{
	import flash.events.Event;
	
	import mx.core.UIComponent;

	public class DelEvent extends Event
	{   
		private var comp:UIComponent;

		public function DelEvent(type:String,comp:UIComponent)
		{
			this.comp=comp;
			super(type);
		}
		public function Comp():UIComponent{
				return this.comp;
		 }
	}
}