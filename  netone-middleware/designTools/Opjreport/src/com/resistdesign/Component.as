package com.resistdesign
{
	import flash.events.Event;
	
	import mx.core.UIComponent;

	public class Component extends Event
	{   
		public var comp:UIComponent;
		public var valer:String;
		public function Component(type:String,comp:UIComponent,valer:String)
		{    this.comp=comp;
			this.valer=valer;
			super(type);
		}
	  public function getComponent1():UIComponent{
			return comp;
		}
	  public function getvaler():String{
		  return valer;
	  }
		
	}
}