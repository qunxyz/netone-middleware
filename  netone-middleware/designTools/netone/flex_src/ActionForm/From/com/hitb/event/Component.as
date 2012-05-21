package ActionForm.From.com.hitb.event
{
	import flash.events.Event;
	
	import mx.core.UIComponent;

	public class Component extends Event
	{   
		public var comp:UIComponent;
		public function Component(type:String,comp:UIComponent)
		{    this.comp=comp;
			super(type);
		}
	  public function getComponent1():UIComponent{
			return comp;
		}
		
		
	}
}