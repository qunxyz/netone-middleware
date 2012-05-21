package BI
{
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;

	public class EvTjFs extends EventDispatcher
	{    
		private  static var manager:EvTjFs;
		public function EvTjFs(target:IEventDispatcher=null)
		{
			super(target);
		}
		
		public static function getInstance():EvTjFs{
			 if(manager==null){
			 	manager=new EvTjFs();
			 }
			 return manager;
		}
		
	}
}