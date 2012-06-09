package ActionForm.From.com.hitb.event
{
	import flash.events.Event;
	
	import mx.core.UIComponent;

	public class GlobalEvent extends Event
	{   
		private var comp1:UIComponent;
		private var comp2:UIComponent;
		public  var ccloumn:int;
		public var crow:int;
		
		public function GlobalEvent(type:String,comp1:UIComponent,comp2:UIComponent,ccloumn:int,crow:int)
		{
			super(type);
			this.comp1=comp1;
			this.comp2=comp2;
			this.ccloumn=ccloumn;
			this.crow=crow;
		}
		public function getComponent1():UIComponent{
			return comp1;
		}
		
		public function getComponent2():UIComponent{
			return comp2;
		}
		public function getcloumn():int{
			return ccloumn;
		}
		public function getcrow():int{
			return crow;
		}
	}
}