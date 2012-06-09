package  com.resistdesign
{
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;

	public class GlobalManager extends EventDispatcher
	{    
		private  static var manager:GlobalManager;
		public function GlobalManager(target:IEventDispatcher=null)
		{
			super(target);
		}
		
		public static function getInstance():GlobalManager{
			 if(manager==null){
			 	manager=new GlobalManager();
			 }
			 return manager;
		}
 
	}
}