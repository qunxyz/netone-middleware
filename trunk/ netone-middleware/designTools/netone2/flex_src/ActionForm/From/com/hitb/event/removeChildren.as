package ActionForm.From.com.hitb.event
{
	import flash.events.Event;
	
	import mx.core.UIComponent;

	public class removeChildren extends Event
	{   public var ui:UIComponent;
		public function removeChildren(type:String,ui:UIComponent)
		{    this.ui=ui;
			 super(type);
		}
	public function getComponent1():UIComponent{
			return this.ui;
		}
	     
	}
}
