
 package ActionForm
 {
 import flash.events.Event;
	public class viewevent extends Event
	{
		public static const SOApoint:String = "SOApoint";
		public static const OsWorkFlow:String = "OsWorkFlow";
		
		private var $data:Object;
		
		public function viewevent(type:String, $data:Object=null, bubbles:Boolean=true, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			this.$data = $data;
		}
      public function get data():Object
		{
			return $data;
		}
		
		public function set data($data:Object):void
		{
			this.$data = $data;
		}
	}
 }