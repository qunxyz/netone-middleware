package BI.components
{
	import flash.events.Event;

	public class TreeComboBoxEvent extends Event
	{   public var uiobject:Object;
		public function TreeComboBoxEvent(type:String,uo:Object)
		{  
			super(type);
			 this.uiobject=uo;
		}
		
	}
}